package dev.kellyson.projeto.PedeAi.API.product.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ProductResponseDTO(
        Long id,
        Long restaurantId,
        String restaurantName,
        Long categoryId,
        String categoryName,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        Boolean isAvailable,
        LocalDateTime createdAt
) {
}
