package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@Primary
public class AccidentRulesJdbcTemplate implements AccidentRulesMem {

    private final JdbcTemplate jdbc;

    @Override
    public List<Rule> findAll() {
        return jdbc.query("select id, name from accidentRules",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    @Override
    public Rule add(Rule rule) {
        jdbc.update("insert into accidentRules (name) values (?)",
                rule.getName());
        return rule;
    }

    @Override
    public boolean update(Rule rule, int id) {
        return jdbc.update(
                "update accidentRules set name = ? where id = ?",
                rule.getName(),
                id) > 0;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return jdbc.queryForObject("select id, name from accidentRules where id = ?",
                (rs, row) -> {
                    Optional<Rule> rule = Optional.of(new Rule());
                    rule.get().setId(rs.getInt("id"));
                    rule.get().setName(rs.getString("name"));
                    return rule;
                },
                id);
    }

    @Override
    public List<Rule> findByName(String key) {
        return jdbc.query("select id, name from accidentRules where name like ?",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                key);
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update(
                "delete from accidentRules where id = ?",
                (long) id) > 0;
    }

    @Override
    public Set<Rule> findByAccidentGet(List<Integer> rulesIds) {
        return new HashSet<>(jdbc.query("select id, name from accidentRules  where id IN (:?)",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                rulesIds));
    }

}
