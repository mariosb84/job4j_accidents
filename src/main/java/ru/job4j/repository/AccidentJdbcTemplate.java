package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.util.Objects.isNull;

@Repository
@AllArgsConstructor
@Primary
public class AccidentJdbcTemplate implements AccidentMem {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Accident> findAll() {
        return jdbc.query("SELECT a.accident_id,"
                        + " a.accident_name,"
                        + " a.accident_text,"
                        + " a.accident_address,"
                        + " t.accidentType_id,"
                        + " t.accidentType_name,"
                        + " r.accidentRule_id,"
                        + " r.accidentRule_name "
                        + "FROM accidents a "
                + "JOIN accidentTypes t ON t.accidentType_id = a.accidentTypes_id "
                + "JOIN accidentRules r ON r.accidentRule_id = a.rules_id",
                new AccidentResultSetExtractor()
                );
    }

   @Override
   public Accident add(Accident accident) {
       String sql = "INSERT INTO accidents ("
               + " accident_name,"
               + " accident_text,"
               + " accident_address,"
               + " accidentTypes_id,"
               + " rules_id) "
               + "VALUES (:accident_name, :accident_text, :accident_address, :accidentTypes_id, "
               + "(SELECT accidentRule_id FROM accidentRules WHERE accidentRule_name = :rule_name))";
       List<MapSqlParameterSource> batchParams = new ArrayList<>();
       Set<Rule> rules = accident.getRules();
       for (Rule rule : rules) {
           MapSqlParameterSource params = new MapSqlParameterSource()
                   .addValue("accident_name", accident.getName())
                   .addValue("accident_text", accident.getText())
                   .addValue("accident_address", accident.getAddress())
                   .addValue("accidentTypes_id", accident.getType().getId())
                   .addValue("rule_name", rule.getName());
           batchParams.add(params);
       }
       jdbc.batchUpdate(sql, batchParams.toArray(new SqlParameterSource[0]));
       return accident;
   }

    @Override
    public boolean update(Accident accident, int id) {
        String sql = "UPDATE accidents SET "
                + "accident_name = :accident_name, "
                + "accident_text = :accident_text, "
                + "accident_address = :accident_address, "
                + "accidentTypes_id = :accidentTypes_id, "
                + "rules_id = :accidentRules_id "
                + "WHERE accident_id = :accident_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accident_name", accident.getName());
        params.addValue("accident_text", accident.getText());
        params.addValue("accident_address", accident.getAddress());
        params.addValue("accidentTypes_id", accident.getType().getId());
        params.addValue("accident_id", id);
        List<Integer> ruleIds = new ArrayList<>();
        for (Rule rule : accident.getRules()) {
            ruleIds.add(rule.getId());
        }
        String ruleIdsString = StringUtils.join(ruleIds, ",");
        int accidentRulesId = Integer.parseInt(ruleIdsString);
        params.addValue("accidentRules_id", accidentRulesId);

        return jdbc.update(sql, params) > 0;
    }


    @Override
    public Optional<Accident> findById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        String sql = "SELECT a.accident_id,"
                + " a.accident_name,"
                + " a.accident_text,"
                + " a.accident_address,"
                + " t.accidentType_id,"
                + " t.accidentType_name,"
                + " r.accidentRule_id,"
                + " r.accidentRule_name "
                + "FROM accidents a "
                + "JOIN accidentTypes t ON t.accidentType_id = a.accidentTypes_id "
                + "JOIN accidentRules r ON r.accidentRule_id = a.rules_id "
                + "WHERE a.accident_id = :id";
        try {
            Accident accident = jdbc.queryForObject(sql, params, new AccidentMapper());
            assert accident != null;
            return Optional.of(accident);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Accident> findByName(String key) {
        Map<String, Object> params = Collections.singletonMap("key", key);
        return jdbc.query("SELECT a.accident_id,"
                + " a.accident_name,"
                + " a.accident_text,"
                + " a.accident_address,"
                + " t.accidentType_id,"
                + " t.accidentType_name,"
                + " r.accidentRule_id,"
                + " r.accidentRule_name "
                + "FROM accidents a "
                + "JOIN accidentTypes t ON t.accidentType_id = a.accidentTypes_id "
                + "JOIN accidentRules r ON r.accidentRule_id = a.rules_id "
                + "WHERE a.accident_name LIKE :id",
                params, new AccidentResultSetExtractor()
        );

    }

    @Override
    public boolean delete(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.update(
                "delete from accidents where id = :id", params
        ) > 0;
    }

    private static class AccidentMapper implements RowMapper<Accident> {

        @Override
        public Accident mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("accident_id");
            String name = resultSet.getString("accident_name");
            String text = resultSet.getString("accident_text");
            String address = resultSet.getString("accident_address");
            long typeId = resultSet.getLong("accidentType_id");
            String typeName = resultSet.getString("accidentType_name");
            AccidentType type = new AccidentType((int) typeId, typeName);
            long ruleId = resultSet.getLong("accidentRule_id");
            String ruleName = resultSet.getString("accidentRule_name");
            Set<Rule> rules = new HashSet<>();
            rules.add(new Rule((int) ruleId, ruleName));
            return new Accident((int) id, name, text, address, type, rules);
        }
    }

    public static class AccidentResultSetExtractor implements ResultSetExtractor<List<Accident>> {

        @Override
        public List<Accident> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Accident> accidentMap = new HashMap<>();
            while (rs.next()) {
                long accidentId = rs.getLong("accident_id");
                long typeId = rs.getLong("accidentType_id");
                String typeName = rs.getString("accidentType_name");
                long ruleId = rs.getLong("accidentRule_id");
                String ruleName = rs.getString("accidentRule_name");
               var accident = accidentMap.get(accidentId);
        if (isNull(accident)) {
             accident = new Accident(
                    (int) accidentId,
                    rs.getString("accident_name"),
                    rs.getString("accident_text"),
                    rs.getString("accident_address"),
                    new AccidentType((int) typeId, typeName),
                    new HashSet<>()
            );
                accidentMap.put(accidentId, accident);
                }
                accident.getRules().add(new Rule((int) ruleId, ruleName));

            }
               return new ArrayList<>(accidentMap.values());
        }


    }


}
