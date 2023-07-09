package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*@Repository*/
public class SimpleAccidentTypeMem implements AccidentTypeMem {

    private final AtomicInteger id = new AtomicInteger();

    private final Map<Integer, AccidentType> accidentsTypes = new ConcurrentHashMap<>();

    private SimpleAccidentTypeMem() {
        add(new AccidentType(1, "Две машины"));
        add(new AccidentType(2, "Машина и человек"));
        add(new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public List<AccidentType> findAll() {
        return accidentsTypes.values().stream().toList();
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        accidentType.setId(id.incrementAndGet());
        return accidentsTypes.put(accidentType.getId(), accidentType);
    }

    @Override
    public boolean update(AccidentType accidentType, int id) {
        return accidentsTypes.computeIfPresent(id, (k, v) -> new AccidentType(
                v.getId(),
                accidentType.getName()
        )) != null;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        Optional<AccidentType> result;
        result = Optional.ofNullable(accidentsTypes.get(id));
        return result;
    }

    @Override
    public List<AccidentType> findByName(String key) {
        return accidentsTypes.values().stream().
                filter(accidentType -> accidentType.getName().
                        equals(key)).toList();
    }

    @Override
    public boolean delete(int id) {
        return accidentsTypes.remove(id, accidentsTypes.get(id));
    }

}
