package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
public class AccidentJdbcTemplate implements AccidentMem {

    private final JdbcTemplate jdbc;

    @Override
    public List<Accident> findAll() {
        return jdbc.query("select id, name, text, address, accidentTypes_id, rules_id from accidents",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    /*accident.setType(rs.getInt("accidentTypes_id"));   ????????
                    accident.setRules(rs.getInt("rules_id"));            ????????????????????*/
                    accident.setType(new AccidentType());
                    accident.setRules(new HashSet<>());
                    return accident;
                });
    }

    @Override
    public Accident add(Accident accident) {
        jdbc.update("insert into accidents (name, text, address, accidentTypes_id, rules_id) values (?, ?, ?, ?, ?)",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType(),
                accident.getRules());
        return accident;
    }

    @Override
    public boolean update(Accident accident, int id) {
        return jdbc.update(
                "update accidents set name = ?, text = ?, address = ?, accidentTypes_id = ?, rules_id = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType(),
                accident.getRules(),
                id) > 0;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return jdbc.queryForObject("select id, name, text, address, accidentTypes_id, rules_id  from accidents where id = ?",
                (rs, row) -> {
                    Optional<Accident> accident = Optional.of(new Accident());
                    accident.get().setId(rs.getInt("id"));
                    accident.get().setName(rs.getString("name"));
                    accident.get().setText(rs.getString("text"));
                    accident.get().setAddress(rs.getString("address"));
                    /*accident.get().setType(rs.getInt("accidentTypes_id"));   ????????
                    accident.get().setRules(rs.getInt("rules_id"));            ????????????????????*/
                    accident.get().setType(new AccidentType());
                    accident.get().setRules(new HashSet<>());
                    return accident;
                },
                id);
    }

    @Override
    public List<Accident> findByName(String key) {
        return jdbc.query("select id, name, text, address, accidentTypes_id, rules_id  from accidents where name like ?",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    /*accident.setType(rs.getInt("accidentTypes_id"));   ????????
                    accident.setRules(rs.getInt("rules_id"));           ????????????????????*/
                    accident.setType(new AccidentType());
                    accident.setRules(new HashSet<>());
                    return accident;
                },
                key);
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update(
                "delete from accidents where id = ?",
                (long) id) > 0;
    }

}
