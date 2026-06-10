package dev.kellyson.projeto.PedeAi.API.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RestaurantRequestDTO(
        @NotBlank
        @Size(min = 2, max = 100)
        String name,

        @NotBlank
        @Size(min = 10, max = 255)
        String description,

        @NotBlank
        @Size(min = 8, max = 20)
        String phone
) {
}