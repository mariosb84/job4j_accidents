package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
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