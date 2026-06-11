package dev.kellyson.projeto.PedeAi.API.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotNull
        Long categoryId,

        @NotBlank
        @Size(min = 2, max = 100)
        String name,

        @NotBlank
        @Size(min = 10, max = 255)
        String description,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal price,

        @NotNull
        @Min(0)
        Integer stockQuantity
) {
}
