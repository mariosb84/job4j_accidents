package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.AccidentTypeHibernate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HbmAccidentTypeService implements AccidentTypeService {

    private final AccidentTypeHibernate accidentTypesStore;

    @Override
    public List<AccidentType> findAll() {
        return accidentTypesStore.findAll();
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        return accidentTypesStore.add(accidentType);
    }

    @Override
    public boolean update(AccidentType accidentType, int id) {
        return accidentTypesStore.update(accidentType, id);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypesStore.findById(id);
    }

    @Override
    public List<AccidentType> findByName(String key) {
        return accidentTypesStore.findByName(key);
    }

    @Override
    public boolean delete(int id) {
        return accidentTypesStore.delete(id);
    }

}
