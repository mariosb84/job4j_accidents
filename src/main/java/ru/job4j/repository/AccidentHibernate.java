package ru.job4j.repository;

import lombok.AllArgsConstructor;
/*import org.springframework.stereotype.Repository;*/
import ru.job4j.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/*@Repository*/
@AllArgsConstructor
public class AccidentHibernate implements AccidentMem {

    private final HibernateCrudRepository crudRepository;


    @Override
    public List<Accident> findAll() {
        return crudRepository.query("select distinct a from  Accident a join fetch a.type "
                + "join fetch a.rules "
                + "order by a.id asc", Accident.class);
    }

    @Override
    public Accident add(Accident accident) {
        crudRepository.run(session -> session.persist(accident));
        return accident;
    }

    @Override
    public boolean update(Accident accident, int id) {
        crudRepository.run(session -> session.merge(accident));
        return true;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                "select distinct a from Accident a join fetch a.type "
                        + "join fetch a.rules "
                        + "where a.id = :fId", Accident.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Accident> findByName(String key) {
        return crudRepository.query(
                "from Accident a join fetch a.type "
                        + "join fetch a.rules "
                        + "where a.name = :fName", Accident.class,
                Map.of("fName", key)
        );
    }

    @Override
    public boolean delete(int id) {
        crudRepository.run(
                "delete from Accident where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }
}
