package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeDataService implements AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public List<AccidentType> findAll() {
        return (List<AccidentType>) accidentTypeRepository.findAll();
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        return accidentTypeRepository.save(accidentType);
    }

    @Override
    public boolean update(AccidentType accidentType, int id) {
        accidentTypeRepository.save(accidentType);
        return true;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }

    @Override
    public List<AccidentType> findByName(String key) {
        return null;
    }

    @Override
    public boolean delete(int id) {
       accidentTypeRepository.deleteById(id);
       return true;
    }

}
