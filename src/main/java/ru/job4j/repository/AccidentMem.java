package ru.job4j.repository;

import ru.job4j.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentMem {

    List<Accident> findAll();

    Accident add(Accident accident);

    boolean update(Accident accident, int id);

    Optional<Accident> findById(int id);

    List<Accident> findByName(String key);

    boolean delete(int id);



}
