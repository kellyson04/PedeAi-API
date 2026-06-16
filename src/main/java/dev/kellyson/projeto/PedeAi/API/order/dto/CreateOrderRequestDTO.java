package dev.kellyson.projeto.PedeAi.API.order.dto;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequestDTO(
        @NotNull
        Long addressId
) {
}
