package dev.kellyson.projeto.PedeAi.API.user.dto;

import dev.kellyson.projeto.PedeAi.API.user.entity.UserRole;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserProfileResponseDTO(
        Long id,
        String name,
        String email,
        UserRole role,
        Boolean isActive,
        LocalDateTime createdAt
) {
}
