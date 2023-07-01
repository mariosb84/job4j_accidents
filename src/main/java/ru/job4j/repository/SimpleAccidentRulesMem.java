package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class SimpleAccidentRulesMem implements AccidentRulesMem {

    private final AtomicInteger id = new AtomicInteger();

    private final Map<Integer, Rule> accidentsRules = new ConcurrentHashMap<>();

    private SimpleAccidentRulesMem() {
        add(new Rule(1, "Статья. 1"));
        add(new Rule(2, "Статья. 2"));
        add(new Rule(3, "Статья. 3"));
    }

    @Override
    public List<Rule> findAll() {
        return accidentsRules.values().stream().toList();
    }

    @Override
    public Rule add(Rule rule) {
        rule.setId(id.incrementAndGet());
        return accidentsRules.put(rule.getId(), rule);
    }

    @Override
    public boolean update(Rule rule, int id) {
        return accidentsRules.computeIfPresent(id, (k, v) -> new Rule(
                v.getId(),
                rule.getName()
        )) != null;
    }

    @Override
    public Optional<Rule> findById(int id) {
        Optional<Rule> result;
        result = Optional.ofNullable(accidentsRules.get(id));
        return result;
    }

    @Override
    public List<Rule> findByName(String key) {
        return accidentsRules.values().stream().
                filter(rule -> rule.getName().
                        equals(key)).toList();
    }

    @Override
    public boolean delete(int id) {
        return accidentsRules.remove(id, accidentsRules.get(id));
    }

    @Override
    public Set<Rule> findByAccidentGet(List<Integer> rulesIds) {
        return rulesIds.stream().map(accidentsRules::get).collect(Collectors.toSet());
    }

}
