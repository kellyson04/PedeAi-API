package dev.kellyson.projeto.PedeAi.API.order.entity;

public enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAID,
    ACCEPTED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELED
}
