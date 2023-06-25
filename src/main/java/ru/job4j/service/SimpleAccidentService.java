package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.AccidentMem;
import ru.job4j.repository.AccidentTypeMem;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleAccidentService implements AccidentService {

    private final AccidentMem store;

    private final AccidentTypeMem storeType;

    public SimpleAccidentService(AccidentMem store, AccidentTypeMem storeType) {
        this.store = store;
        this.storeType = storeType;
    }

    @Override
    public List<Accident> findAll() {
        return store.findAll();
    }

    @Override
    public Accident add(Accident accident) {
        accident.setType(storeType.findById(accident.getId()).get());
        return store.add(accident);
    }

    @Override
    public boolean update(Accident accident, int id) {
        accident.setType(storeType.findById(accident.getId()).get());
        return store.update(accident, accident.getId());
    }

    @Override
    public Optional<Accident> findById(int id) {
        return store.findById(id);
    }

    @Override
    public List<Accident> findByName(String key) {
        return store.findByName(key);
    }

    @Override
    public boolean delete(int id) {
        return store.delete(id);
    }

}
