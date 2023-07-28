package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HbmAccidentService implements AccidentService {

    private final AccidentHibernate store;

    private final AccidentTypeHibernate accidentTypesStore;

    private final AccidentRulesHibernate accidentRulesStore;


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
