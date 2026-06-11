package dev.kellyson.projeto.PedeAi.API.user.service;

import dev.kellyson.projeto.PedeAi.API.user.dto.UserProfileResponseDTO;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import dev.kellyson.projeto.PedeAi.API.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    public UserProfileResponseDTO me(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return UserMapper.toProfileResponse(user);
    }
}
