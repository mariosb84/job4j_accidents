package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
public class AccidentTypeJdbcTemplate implements AccidentTypeMem {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<AccidentType> findAll() {
        return jdbc.query("SELECT accidentType_id, accidentType_name FROM accidentTypes",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("accidentType_id"));
                    accidentType.setName(rs.getString("accidentType_name"));
                    return accidentType;
                });
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        jdbc.update("INSERT INTO accidentTypes (accidentType_name) VALUES (:accidentType_name)",
                Map.of("accidentType_name", accidentType.getName()));
        return accidentType;
    }

    @Override
    public boolean update(AccidentType accidentType, int id) {
        return jdbc.update(
                "UPDATE accidentTypes SET accidentType_name = :accidentType_name WHERE accidentType_id = :accidentType_id",
                Map.of("accidentType_name", accidentType.getName(), "accidentType_id", id)) > 0;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        String sql = "SELECT accidentType_id, accidentType_name FROM accidentTypes WHERE accidentType_id = :id";
        try {
            AccidentType type = jdbc.queryForObject(sql, params, (rs, rowNum) -> {
                AccidentType t = new AccidentType();
                t.setId(rs.getInt("accidentType_id"));
                t.setName(rs.getString("accidentType_name"));
                return t;
            });
            return Optional.ofNullable(type);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<AccidentType> findByName(String key) {
        Map<String, Object> params = Collections.singletonMap("key", "%" + key + "%");
        String sql = "SELECT accidentType_id, accidentType_name FROM accidentTypes WHERE accidentType_name LIKE :key";
        return jdbc.query(sql, params, (rs, rowNum) -> {
            AccidentType type = new AccidentType();
            type.setId(rs.getInt("accidentType_id"));
            type.setName(rs.getString("accidentType_name"));
            return type;
        });
    }

    @Override
    public boolean delete(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.update(
                "delete from accidentTypes where accidentType_id = :id", params) > 0;
    }

}
