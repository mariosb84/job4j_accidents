package ru.job4j.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
/*import org.springframework.stereotype.Service;*/
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentRulesMem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ThreadSafe
/*@Service*/
@AllArgsConstructor
public class SimpleAccidentRuleService implements AccidentRuleService {

    private final AccidentRulesMem store;

    @Override
    public List<Rule> findAll() {
        return store.findAll();
    }

    @Override
    public Rule add(Rule rule) {
        return store.add(rule);
    }

    @Override
    public boolean update(Rule rule, int id) {
        return store.update(rule, rule.getId());
    }

    @Override
    public Optional<Rule> findById(int id) {
        return store.findById(id);
    }

    @Override
    public List<Rule> findByName(String key) {
        return store.findByName(key);
    }

    @Override
    public boolean delete(int id) {
        return store.delete(id);
    }

    @Override
    public Set<Rule> findByAccidentGet(List<Integer> rulesIds) {
        return store.findByAccidentGet(rulesIds);
    }

}
