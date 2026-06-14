package dev.kellyson.projeto.PedeAi.API.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException b) {
        ErrorResponse error = ErrorResponse.builder()
                .status(400)
                .error("BAD_REQUEST")
                .message(b.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException c) {
        ErrorResponse error = ErrorResponse.builder()
                .status(409)
                .error("CONFLICT")
                .message(c.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler({
            RestaurantNotFoundException.class,
            CategoryNotFoundException.class,
            ZipCodeNotFoundException.class,
            AddressNotFoundException.class,
            ProductNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException e) {
        ErrorResponse error = ErrorResponse.builder()
                .status(404)
                .error("NOT_FOUND")
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
