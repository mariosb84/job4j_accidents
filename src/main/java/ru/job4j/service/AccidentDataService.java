package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentRepository;
import ru.job4j.repository.AccidentRulesRepository;
import ru.job4j.repository.AccidentTypeRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentDataService implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final AccidentRulesRepository accidentRulesRepository;

    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Accident add(Accident accident, List<Integer> rulesIds) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        accident.setRules(new HashSet<>((Collection<? extends Rule>)
                accidentRulesRepository.findAllById(rulesIds)));
        return accidentRepository.save(accident);
    }

    @Override
    public boolean update(Accident accident, int id, List<Integer> rulesIds) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        accident.setRules(new HashSet<>((Collection<? extends Rule>)
                accidentRulesRepository.findAllById(rulesIds)));
        accidentRepository.save(accident);
        return true;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public List<Accident> findByName(String key) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        accidentRepository.deleteById(id);
        return true;
    }

}
