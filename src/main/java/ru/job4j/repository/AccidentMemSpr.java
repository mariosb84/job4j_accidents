package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@AllArgsConstructor
public class AccidentMemSpr implements AccidentMem {

    private final ConcurrentHashMap<Integer, Accident> accidents;

    @Override
    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

    @Override
    public Accident add(Accident accident) {
        return accidents.put(accident.getId(), accident);
    }

    @Override
    public boolean update(Accident accident, int id) {
         accidents.replace(id, accident);
         return true;
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
        accidents.remove(id);
        return true;
    }

}
