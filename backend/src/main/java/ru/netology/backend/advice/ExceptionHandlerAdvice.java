package ru.netology.backend.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.backend.exception.BadRequest;
import ru.netology.backend.exception.InternalServerError;
import ru.netology.backend.exception.Unauthorized;
import ru.netology.backend.loger.Loger;

import java.util.Date;


@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ErrorMessage> badRequest(BadRequest exception, HttpServletRequest req) {
        String serverScheme = req.getScheme();
        String serverHost = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getServletPath();
        String targetBase = serverScheme + "://" + serverHost + ":" + serverPort + contextPath;

        ErrorMessage errorMessage = new ErrorMessage(400, exception.getMessage(), new Date(), targetBase);
        Loger.write("ERROR", String.valueOf(errorMessage));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(Unauthorized.class)
    public ResponseEntity<ErrorMessage> unauthorized(Unauthorized exception, HttpServletRequest req) {
        String serverScheme = req.getScheme();
        String serverHost = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getServletPath();
        String targetBase = serverScheme + "://" + serverHost + ":" + serverPort + contextPath;

        ErrorMessage errorMessage = new ErrorMessage(401, exception.getMessage(), new Date(), targetBase);
        Loger.write("ERROR", String.valueOf(errorMessage));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorMessage> internalServerError(InternalServerError exception, HttpServletRequest req) {
        String serverScheme = req.getScheme();
        String serverHost = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getServletPath();
        String targetBase = serverScheme + "://" + serverHost + ":" + serverPort + contextPath;

        ErrorMessage errorMessage = new ErrorMessage(500, exception.getMessage(), new Date(), targetBase);
        Loger.write("ERROR", String.valueOf(errorMessage));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
