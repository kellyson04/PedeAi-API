package dev.kellyson.projeto.PedeAi.API.restaurant.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RegisterRestaurantResponseDTO(
        String name,
        LocalDateTime createdAt
) {
}