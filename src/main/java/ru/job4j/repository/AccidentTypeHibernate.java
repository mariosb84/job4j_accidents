package ru.job4j.repository;

import lombok.AllArgsConstructor;
/*import org.springframework.stereotype.Repository;*/
import ru.job4j.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/*@Repository*/
@AllArgsConstructor
public class AccidentTypeHibernate implements AccidentTypeMem {

    private final HibernateCrudRepository crudRepository;

    @Override
    public List<AccidentType> findAll() {
        return crudRepository.query("from  AccidentType order by id asc", AccidentType.class);
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        crudRepository.run(session -> session.persist(accidentType));
        return accidentType;
    }

    @Override
    public boolean update(AccidentType accidentType, int id) {
        crudRepository.run(session -> session.merge(accidentType));
        return true;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional(
                "from AccidentType where id = :fId", AccidentType.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<AccidentType> findByName(String key) {
        return crudRepository.query(
                "from AccidentType where name = :fName", AccidentType.class,
                Map.of("fName", key)
        );
    }

    @Override
    public boolean delete(int id) {
        crudRepository.run(
                "delete from AccidentType where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }

}
