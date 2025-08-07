package io.tongnooma.Exception;

public class RessourceIntrouvableException extends RuntimeException {

    public RessourceIntrouvableException(String message) {

        super(message);
    }

    public RessourceIntrouvableException(String resourceName, Long id) {
        super(resourceName + " introuvable avec l'id : " + id);
    }
}
