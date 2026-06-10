package dev.kellyson.projeto.PedeAi.API.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}