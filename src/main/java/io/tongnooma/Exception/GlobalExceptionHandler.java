package io.tongnooma.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Gère les ressources non trouvées (ex: ID inexistant).*/
    @ExceptionHandler(RessourceIntrouvableException.class)
    public ResponseEntity<Map<String, Object>> handleRessourceIntrouvable(RessourceIntrouvableException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("erreur", "Ressource introuvable");
        error.put("message", ex.getMessage());
        error.put("timestamp", LocalDateTime.now());
        error.put("code", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /** Gère toutes les autres erreurs inattendues (fail-safe).*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobal(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("erreur", "Erreur interne");
        error.put("message", ex.getMessage());
        error.put("timestamp", LocalDateTime.now());
        error.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

