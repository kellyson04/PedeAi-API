package dev.kellyson.projeto.PedeAi.API.restaurant.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MyRestaurantResponseDTO(
        Long id,
        Long ownerId,
        String ownerName,
        String name,
        String description,
        String phone,
        Boolean isOpen,
        Boolean isActive,
        LocalDateTime createdAt
) {
}