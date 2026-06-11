package dev.kellyson.projeto.PedeAi.API.address.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AddressResponseDTO(
        Long id,
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String complement,
        LocalDateTime createdAt
) {
}
