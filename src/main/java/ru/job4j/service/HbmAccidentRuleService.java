package ru.job4j.service;

import lombok.AllArgsConstructor;
/*import org.springframework.stereotype.Service;*/
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentRulesHibernate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/*@Service*/
@AllArgsConstructor
public class HbmAccidentRuleService implements AccidentRuleService {

    private final AccidentRulesHibernate accidentRulesStore;

    @Override
    public List<Rule> findAll() {
        return accidentRulesStore.findAll();
    }

    @Override
    public Rule add(Rule rule) {
        return accidentRulesStore.add(rule);
    }

    @Override
    public boolean update(Rule rule, int id) {
        return accidentRulesStore.update(rule, id);
    }

    @Override
    public Optional<Rule> findById(int id) {
        return accidentRulesStore.findById(id);
    }

    @Override
    public List<Rule> findByName(String key) {
        return accidentRulesStore.findByName(key);
    }

    @Override
    public boolean delete(int id) {
        return accidentRulesStore.delete(id);
    }

    @Override
    public Set<Rule> findByAccidentGet(List<Integer> rulesIds) {
        return accidentRulesStore.findByAccidentGet(rulesIds);
    }

}
