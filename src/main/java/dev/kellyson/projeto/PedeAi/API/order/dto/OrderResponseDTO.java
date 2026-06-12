package dev.kellyson.projeto.PedeAi.API.order.dto;

import dev.kellyson.projeto.PedeAi.API.order.entity.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderResponseDTO(
        Long id,
        Long customerId,
        Long restaurantId,
        Long addressId,
        OrderStatus status,
        BigDecimal subtotal,
        BigDecimal deliveryFee,
        BigDecimal discount,
        BigDecimal total,
        LocalDateTime createdAt
) {
}
