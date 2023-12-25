package ru.netology.backend.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.netology.backend.exception.BadRequest;
import ru.netology.backend.model.User;

import java.security.SecureRandom;

@Repository
public class UserRepository {
    @Value("${bcrypt.salt}")
    private String salt;
    final private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByLogin(String login) {
        try {

            String sql = "SELECT u.id, login, password, r.role from USERS u  join roles r on r.id = u.role  WHERE login = ?";
            var user = jdbcTemplate.queryForObject(sql, new Object[]{login}, (rs, rowNum) ->
                    new User(
                            rs.getLong("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("role")
                    ));

            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest("Bad credentials");
        }
    }

    public User findByLogin(String login, String password) {
        try {
            String sql = "SELECT u.id, login, password, r.role from USERS u  join roles r on r.id = u.role  WHERE login = ?";
            var user = jdbcTemplate.queryForObject(sql, new Object[]{login}, (rs, rowNum) ->
                    new User(
                            rs.getLong("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("role")
                    ));

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12, new SecureRandom(new byte[20]));
            if (encoder.matches(password, user.getPassword())) {

                return user;
            } else {
                throw new BadRequest("sssss");
            }

        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest("Bad credentials");
        }
    }
}

