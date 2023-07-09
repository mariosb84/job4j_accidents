package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*@Repository*/
public class SimpleAccidentMem implements AccidentMem {

    private final AtomicInteger id = new AtomicInteger();

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private SimpleAccidentMem() {
        add(new Accident(1, "One", "TextOne", "AddressOne",
                new AccidentType(1, "Две машины"),
                new HashSet<>(Arrays.asList(new Rule(1, "Статья. 1"),
                                            new Rule(2, "Статья. 2"),
                                            new Rule(3, "Статья. 3")))));
        add(new Accident(2, "Two", "TextTwo", "AddressTwo",
                new AccidentType(2, "Машина и человек"),
                new HashSet<>(Arrays.asList(new Rule(1, "Статья. 1"),
                        new Rule(2, "Статья. 2"),
                        new Rule(3, "Статья. 3")))));
        add(new Accident(3, "Three", "TextThree", "AddressThree",
                new AccidentType(3, "Машина и велосипед"),
                new HashSet<>(Arrays.asList(new Rule(1, "Статья. 1"),
                        new Rule(2, "Статья. 2"),
                        new Rule(3, "Статья. 3")))));
    }

    @Override
    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

    @Override
    public Accident add(Accident accident) {
        accident.setId(id.incrementAndGet());
        return accidents.put(accident.getId(), accident);
    }

    @Override
    public boolean update(Accident accident, int id) {
        return accidents.computeIfPresent(id, (k, v) -> new Accident(
                v.getId(),
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType(),
                accident.getRules()
        )) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        Optional<Accident> result;
        result = Optional.ofNullable(accidents.get(id));
        return result;
    }

    @Override
    public List<Accident> findByName(String key) {
        return accidents.values().stream().
                filter(accident -> accident.getName().
                        equals(key)).toList();
    }

    @Override
    public boolean delete(int id) {
        return accidents.remove(id, accidents.get(id));
    }

}
