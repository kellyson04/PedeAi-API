package dev.kellyson.projeto.PedeAi.API.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartItemRequestDTO(
        @NotNull
        Long productId,

        @NotNull
        @Min(1)
        Integer quantity
) {
}
