package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentRulesHibernate implements AccidentRulesMem {

    private final HibernateCrudRepository crudRepository;

    @Override
    public List<Rule> findAll() {
        return crudRepository.query("from  Rule order by id asc", Rule.class);
    }

    @Override
    public Rule add(Rule rule) {
        crudRepository.run(session -> session.persist(rule));
        return rule;
    }

    @Override
    public boolean update(Rule rule, int id) {
        crudRepository.run(session -> session.merge(rule));
        return true;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return crudRepository.optional(
                "from Rule where id = :fId", Rule.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Rule> findByName(String key) {
        return crudRepository.query(
                "from Rule where name = :fName", Rule.class,
                Map.of("fName", key)
        );
    }

    @Override
    public boolean delete(int id) {
        crudRepository.run(
                "delete from Rule where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }

    @Override
    public Set<Rule> findByAccidentGet(List<Integer> rulesIds) {
        return new HashSet<>(crudRepository.query(
                "from Rule r where r.id IN :rulesIds", Rule.class,
                Map.of("rulesIds", rulesIds)
        ));
    }

}
