package ru.job4j.service;

import ru.job4j.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentService {

    List<Accident> findAll();

    Accident add(Accident accident, List<Integer> rulesIds);

    boolean update(Accident accident, int id, List<Integer> rulesIds);

    Optional<Accident> findById(int id);

    List<Accident> findByName(String key);

    boolean delete(int id);
}
