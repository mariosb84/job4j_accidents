package ru.job4j.repository;

import ru.job4j.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccidentRulesMem {

    List<Rule> findAll();

    Rule add(Rule rule);

    boolean update(Rule rule, int id);

    Optional<Rule> findById(int id);

    List<Rule> findByName(String key);

    boolean delete(int id);

    Set<Rule> findByAccidentGet(List<Integer> rulesIds);
}
