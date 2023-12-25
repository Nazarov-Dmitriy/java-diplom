package ru.netology.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.backend.dto.UserDto;
import ru.netology.backend.exception.BadRequest;
import ru.netology.backend.model.User;
import ru.netology.backend.security.JwtUtilities;
import ru.netology.backend.servise.UserServise;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
    private UserServise userServise;
    private  final JwtUtilities jwtUtilities ;

    public UserController(UserServise userServise, JwtUtilities jwtUtilities) {
        this.userServise = userServise;
        this.jwtUtilities = jwtUtilities;
    }

    @PostMapping("/login")
    public ResponseEntity<?> userAutorization(@RequestBody() UserDto body , HttpServletRequest request) {
        Optional<User> user = userServise.findByLogin(body.getLogin(), body.getPassword());
        if(user.isPresent()) {
            String token = jwtUtilities.generateToken(user.get().getLogin(), Collections.singletonList(user.get().getRole()));

            HttpSession session = request.getSession();
            session.setAttribute("username", user.get().getLogin());
            session.setAttribute("user-id", user.get().getId());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", user.get().getLogin());
            response.put("password",user.get().getPassword());
            response.put("auth-token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            throw new BadRequest("Bad credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody() UserDto body) {
        // доделать
        return new ResponseEntity<>(HttpStatus.OK);
    }
}




