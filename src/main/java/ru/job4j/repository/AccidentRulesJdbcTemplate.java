package ru.job4j.repository;

import lombok.AllArgsConstructor;
/*import org.springframework.context.annotation.Primary;*/
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
/*import org.springframework.stereotype.Repository;*/
import ru.job4j.model.Rule;

import java.util.*;

/*@Repository*/
@AllArgsConstructor
/*@Primary*/
public class AccidentRulesJdbcTemplate implements AccidentRulesMem {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Rule> findAll() {
        return jdbc.query("SELECT accidentRule_id, accidentRule_name FROM accidentRules",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("accidentRule_id"));
                    rule.setName(rs.getString("accidentRule_name"));
                    return rule;
                });
    }

    @Override
    public Rule add(Rule rule) {
        jdbc.update("INSERT INTO accidentRules (accidentRule_name) VALUES (:accidentRule_name)",
                Map.of("accidentRule_name", rule.getName()));
        return rule;
    }

    @Override
    public boolean update(Rule rule, int id) {
        return jdbc.update(
                "UPDATE accidentRules SET accidentRule_name = :accidentRule_name WHERE accidentRule_id = :accidentRuleId ",
                Map.of("accidentRule_name", rule.getName(),
                       "accidentRuleId", id)) > 0;
    }

    @Override
    public Optional<Rule> findById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        String sql = "SELECT accidentRule_id, accidentRule_name FROM accidentRules WHERE accidentRule_id = :id";
        try {
            Rule rule = jdbc.queryForObject(sql, params, (rs, rowNum) -> {
                Rule r = new Rule();
                r.setId(rs.getInt("accidentRule_id"));
                r.setName(rs.getString("accidentRule_name"));
                return r;
            });
            return Optional.ofNullable(rule);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Rule> findByName(String key) {
        Map<String, Object> params = Collections.singletonMap("key", "%" + key + "%");
        String sql = "SELECT accidentRule_id, accidentRule_name FROM accidentRules WHERE accidentRule_name LIKE :key";
        return jdbc.query(sql, params, (rs, rowNum) -> {
            Rule rule = new Rule();
            rule.setId(rs.getInt("accidentRule_id"));
            rule.setName(rs.getString("accidentRule_name"));
            return rule;
        });
    }

    @Override
    public boolean delete(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.update(
                "DELETE FROM accidentRules WHERE accidentRule_id = :id", params) > 0;
    }

  @Override
  public Set<Rule> findByAccidentGet(List<Integer> rulesIds) {
      Map<String, Object> params = Collections.singletonMap("rulesIds", rulesIds);
      return new HashSet<>(jdbc.query("SELECT accidentRule_id, accidentRule_name FROM accidentRules WHERE accidentRule_id IN (:rulesIds)",
              params,
              (rs, row) -> {
                  Rule rule = new Rule();
                  rule.setId(rs.getInt("accidentRule_id"));
                  rule.setName(rs.getString("accidentRule_name"));
                  return rule;
              }));
       }


}
