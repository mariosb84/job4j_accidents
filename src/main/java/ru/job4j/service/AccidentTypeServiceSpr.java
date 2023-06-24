package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.AccidentTypeMem;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class AccidentTypeServiceSpr implements AccidentTypeService {

    private final AccidentTypeMem store;

    public AccidentTypeServiceSpr(AccidentTypeMem store) {
        this.store = store;
    }

    @Override
    public List<AccidentType> findAll() {
        return store.findAll();
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        return store.add(accidentType);
    }

    @Override
    public boolean update(AccidentType accidentType, int id) {
        return store.update(accidentType, accidentType.getId());
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return store.findById(id);
    }

    @Override
    public List<AccidentType> findByName(String key) {
        return store.findByName(key);
    }

    @Override
    public boolean delete(int id) {
        return store.delete(id);
    }

}
