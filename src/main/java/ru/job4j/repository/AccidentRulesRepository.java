package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Rule;

import java.util.List;

public interface AccidentRulesRepository extends CrudRepository<Rule, Integer> {

    List<Rule> findAll();

}
