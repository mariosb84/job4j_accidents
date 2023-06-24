package ru.job4j.repository;

import ru.job4j.model.AccidentType;

import java.util.List;
import java.util.Optional;

public interface AccidentTypeMem {

    List<AccidentType> findAll();

    AccidentType add(AccidentType accidentType);

    boolean update(AccidentType accidentType, int id);

    Optional<AccidentType> findById(int id);

    List<AccidentType> findByName(String key);

    boolean delete(int id);
}
