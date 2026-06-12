package dev.kellyson.projeto.PedeAi.API.order.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateOrderRequestDTO(
        @NotNull
        Long restaurantId,

        @NotNull
        Long addressId,

        @NotNull
        BigDecimal subtotal,

        @NotNull
        BigDecimal deliveryFee,

        @NotNull
        BigDecimal discount,

        @NotNull
        BigDecimal total
) {
}
