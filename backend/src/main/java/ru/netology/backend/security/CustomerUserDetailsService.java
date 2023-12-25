package ru.netology.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.netology.backend.exception.BadRequest;
import ru.netology.backend.model.User;
import ru.netology.backend.repository.UserRepository;


@Component
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public JwtUser loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new BadRequest("Bad credentials");
        }
        JwtUser jwtUser = new JwtUser(user.getId(), user.getLogin(), user.getPassword(), user.getRole());
        return jwtUser;
    }
}
