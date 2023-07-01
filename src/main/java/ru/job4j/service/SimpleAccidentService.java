package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.AccidentMem;
import ru.job4j.repository.AccidentRulesMem;
import ru.job4j.repository.AccidentTypeMem;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleAccidentService implements AccidentService {

    private final AccidentMem store;

    private final AccidentTypeMem accidentTypesStore;

    private final AccidentRulesMem accidentRulesStore;

    public SimpleAccidentService(AccidentMem store, AccidentTypeMem accidentTypes, AccidentRulesMem accidentRulesStore) {
        this.store = store;
        this.accidentTypesStore = accidentTypes;
        this.accidentRulesStore = accidentRulesStore;
    }

    @Override
    public List<Accident> findAll() {
        return store.findAll();
    }

    @Override
    public Accident add(Accident accident, List<Integer> rulesIds) {
        accident.setType(accidentTypesStore.findById(accident.getType().getId()).get());
        accident.setRules(accidentRulesStore.findByAccidentGet(rulesIds));
        return store.add(accident);
    }

    @Override
    public boolean update(Accident accident, int id, List<Integer> rulesIds) {
        accident.setType(accidentTypesStore.findById(accident.getType().getId()).get());
        accident.setRules(accidentRulesStore.findByAccidentGet(rulesIds));
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
