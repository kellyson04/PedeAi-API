package dev.kellyson.projeto.PedeAi.API.user.mapper;

import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import dev.kellyson.projeto.PedeAi.API.auth.dto.UserResponseDTO;
import dev.kellyson.projeto.PedeAi.API.user.dto.UserProfileResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static UserProfileResponseDTO toProfileResponse(User user) {
        return UserProfileResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
