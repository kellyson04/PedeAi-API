package dev.kellyson.projeto.PedeAi.API.user.mapper;

import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import dev.kellyson.projeto.PedeAi.API.auth.dto.UserResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
