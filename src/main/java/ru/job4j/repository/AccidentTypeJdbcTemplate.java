package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
public class AccidentTypeJdbcTemplate implements AccidentTypeMem {

    private final JdbcTemplate jdbc;

    @Override
    public List<AccidentType> findAll() {
        return jdbc.query("select id, name from accidentTypes",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        jdbc.update("insert into accidentTypes (name) values (?)",
                accidentType.getName());
        return accidentType;
    }

    @Override
    public boolean update(AccidentType accidentType, int id) {
        return jdbc.update(
                "update accidentTypes set name = ? where id = ?",
                accidentType.getName(),
                id) > 0;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return jdbc.queryForObject("select id, name from accidentTypes where id = ?",
                (rs, row) -> {
                    Optional<AccidentType> accidentType = Optional.of(new AccidentType());
                    accidentType.get().setId(rs.getInt("id"));
                    accidentType.get().setName(rs.getString("name"));
                    return accidentType;
                },
                id);
    }

    @Override
    public List<AccidentType> findByName(String key) {
        return jdbc.query("select id, name from accidentTypes where name like ?",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                },
                key);
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update(
                "delete from accidentTypes where id = ?",
                (long) id) > 0;
    }

}
