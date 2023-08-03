package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Accident;

import java.util.List;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {

    List<Accident> findAll();

}