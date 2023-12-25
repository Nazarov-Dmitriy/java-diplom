package ru.netology.backend.servise;

import org.springframework.stereotype.Service;
import ru.netology.backend.model.User;
import ru.netology.backend.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServise {
    private final UserRepository repository;

    public UserServise(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> findByLogin(String login, String password ) {
        return Optional.ofNullable(repository.findByLogin(login, password));
    }
}
