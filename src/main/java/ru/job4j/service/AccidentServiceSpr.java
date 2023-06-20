package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.AccidentMem;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class AccidentServiceSpr implements AccidentService {

    private final AccidentMem store;

    public AccidentServiceSpr(AccidentMem store) {
        this.store = store;
    }

    @Override
    public List<Accident> findAll() {
        return store.findAll();
    }

    @Override
    public Accident add(Accident accident) {
        return store.add(accident);
    }

    @Override
    public boolean update(Accident accident, int id) {
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
