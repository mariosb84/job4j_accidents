package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Authority;
import ru.job4j.repository.AuthorityRepository;

@Service
@AllArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public Authority findByAuthority(String authority) {
        return authorityRepository.findByAuthority(authority);
    }

}
