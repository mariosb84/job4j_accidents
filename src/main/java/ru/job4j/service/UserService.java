package ru.job4j.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> save(User user) {
        try {
            userRepository.save(user);
            return Optional.of(user);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
        }
            return Optional.empty();
    }

    public Optional<User> findById(User user) {
        return userRepository.findById(user.getId());
    }

    public List<User> findByNameUsers(String userName) {
        List<User> list = userRepository.findAll();
    return list.stream().filter(u -> u.getUsername().
            equals(userName)).collect(Collectors.toList());
    }

}
