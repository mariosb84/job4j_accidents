package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Rule;

public interface AccidentRulesRepository extends CrudRepository<Rule, Integer> {

}
