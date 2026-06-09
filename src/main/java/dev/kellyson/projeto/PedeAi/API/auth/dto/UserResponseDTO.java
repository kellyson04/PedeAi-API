package dev.kellyson.projeto.PedeAi.API.auth.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponseDTO(
        String name,
        LocalDateTime createdAt
) {
}
