package ru.job4j.repository;

import lombok.AllArgsConstructor;
/*import org.springframework.context.annotation.Primary;*/
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
/*@Primary*/
public class AccidentJdbcTemplate implements AccidentMem {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Accident> findAll() {
        return jdbc.query(
                "SELECT a.accident_id,"
                        + " a.accident_name,"
                        + " a.accident_text,"
                        + " a.accident_address,"
                        + " t.accidentType_id,"
                        + " t.accidentType_name,"
                        + " r.accidentRule_id,"
                        + " r.accidentRule_name "
                        + "FROM accidents a "
                        + "JOIN accidentTypes t ON t.accidentType_id = a.accidentTypes_id "
                        + "JOIN accidentRules r ON r.accidentRule_id IN (SELECT rules_id FROM link WHERE accidentLink_id = a.accident_id) "
                        + "GROUP BY a.accident_id, a.accident_name, a.accident_text, a.accident_address, t.accidentType_id, t.accidentType_name,"
                        + " r.accidentRule_id , r.accidentRule_name ORDER BY a.accident_id",
                new AccidentResultSetExtractor()
        );
    }

    @Override
    public Accident add(Accident accident) {
        String sql = "INSERT INTO accidents (accident_name, accident_text, accident_address, accidentTypes_id) "
                + "VALUES (:accident_name, :accident_text, :accident_address, :accidentTypes_id)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("accident_name", accident.getName())
                .addValue("accident_text", accident.getText())
                .addValue("accident_address", accident.getAddress())
                .addValue("accidentTypes_id", accident.getType().getId());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        assert keys != null;
        int accidentId = (int) keys.get("accident_id");
        accident.setId(accidentId);
        String sqlLink = "INSERT INTO link (rules_id, accidentLink_id) VALUES (:rule_id, :accident_id)";
        Set<Rule> rules = accident.getRules();
        for (Rule rule : rules) {
            MapSqlParameterSource linkParams = new MapSqlParameterSource()
                    .addValue("accident_id", accidentId)
                    .addValue("rule_id", rule.getId());
            jdbc.update(sqlLink, linkParams);
        }
        return accident;
    }

    @Override
    public boolean update(Accident accident, int id) {
        String sql = "UPDATE accidents SET "
                + "accident_name = :accident_name, "
                + "accident_text = :accident_text, "
                + "accident_address = :accident_address, "
                + "accidentTypes_id = :accidentTypes_id "
                + "WHERE accident_id = :accident_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accident_name", accident.getName());
        params.addValue("accident_text", accident.getText());
        params.addValue("accident_address", accident.getAddress());
        params.addValue("accidentTypes_id", accident.getType().getId());
        params.addValue("accident_id", id);
        String deleteLink = "DELETE FROM link WHERE accidentLink_id  = :accident_id";
            MapSqlParameterSource linkDelParams = new MapSqlParameterSource()
                    .addValue("accident_id", id);
            jdbc.update(deleteLink, linkDelParams);
        String sqlLink = "INSERT INTO link (rules_id, accidentLink_id) VALUES (:rules_id, :accidentLink_id)";
        Set<Rule> rules = accident.getRules();
        for (Rule rule : rules) {
            MapSqlParameterSource linkParams = new MapSqlParameterSource()
                    .addValue("accidentLink_id", id)
                    .addValue("rules_id", rule.getId());
            jdbc.update(sqlLink, linkParams);
        }
        return (jdbc.update(sql, params)) > 0;
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
                + "JOIN accidentRules r ON r.accidentRule_id IN (SELECT rules_id FROM link WHERE accidentLink_id = a.accident_id) "
                + "WHERE a.accident_id = :id";
       return (Objects.requireNonNull(jdbc.query(sql, params, new AccidentResultSetExtractor()))).stream().findAny();
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
                + "JOIN accidentRules r ON r.accidentRule_id IN (SELECT rules_id FROM link WHERE accidentLink_id = a.accident_id)"
                + "WHERE a.accident_name LIKE :key",
                params, new AccidentResultSetExtractor()
        );

    }

    @Override
    public boolean delete(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.update(
                "DELETE FROM accidents WHERE id = :id", params
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
           Map<Integer, Accident> result = new HashMap<>();
           while (rs.next()) {
                Accident accident = new Accident(
                       (int) rs.getLong("accident_id"),
                       rs.getString("accident_name"),
                       rs.getString("accident_text"),
                       rs.getString("accident_address"),
                       new AccidentType(
                               rs.getInt("accidentType_id"),
                               rs.getString("accidentType_name")
                       ),
                       new HashSet<>()
               );
               result.putIfAbsent(accident.getId(), accident);
               result.get(accident.getId()).getRules().add(
                       new Rule(rs.getInt("accidentRule_id"),
                               rs.getString("accidentRule_name")));
           }
           return new ArrayList<>(result.values());
       }


    }


}
