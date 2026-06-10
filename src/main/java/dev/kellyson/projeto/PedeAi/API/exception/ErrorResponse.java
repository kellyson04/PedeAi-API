package dev.kellyson.projeto.PedeAi.API.exception;

import lombok.Builder;

@Builder
public record ErrorResponse (

        Integer status,
        String error,
        String message

) {
}
