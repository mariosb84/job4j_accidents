package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentRulesRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentRuleDataService implements AccidentRuleService {

    private final AccidentRulesRepository accidentRulesRepository;

    @Override
    public List<Rule> findAll() {
        return (List<Rule>) accidentRulesRepository.findAll();
    }

    @Override
    public Rule add(Rule rule) {
        return accidentRulesRepository.save(rule);
    }

    @Override
    public boolean update(Rule rule, int id) {
        accidentRulesRepository.save(rule);
        return true;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return accidentRulesRepository.findById(id);
    }

    @Override
    public List<Rule> findByName(String key) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        accidentRulesRepository.deleteById(id);
        return true;
    }

    @Override
    public Set<Rule> findByAccidentGet(List<Integer> rulesIds) {
        return new HashSet<>((Collection<? extends Rule>)
                accidentRulesRepository.findAllById(rulesIds));
    }

}
