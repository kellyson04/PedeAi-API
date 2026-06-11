package dev.kellyson.projeto.PedeAi.API.exception;

public class ZipCodeNotFoundException extends RuntimeException {

    public ZipCodeNotFoundException(String message) {
        super(message);
    }
}
