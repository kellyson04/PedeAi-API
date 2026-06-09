package dev.kellyson.projeto.PedeAi.API.auth.service;

import dev.kellyson.projeto.PedeAi.API.config.viacep.dto.ViaCepResponseDTO;
import dev.kellyson.projeto.PedeAi.API.config.viacep.service.ViaCepService;
import dev.kellyson.projeto.PedeAi.API.user.dto.LoginRequestDTO;
import dev.kellyson.projeto.PedeAi.API.user.dto.RegisterRequestDTO;
import dev.kellyson.projeto.PedeAi.API.user.dto.UserResponseDTO;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import dev.kellyson.projeto.PedeAi.API.user.mapper.UserMapper;
import dev.kellyson.projeto.PedeAi.API.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ViaCepService viaCepService;

    public UserResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByEmail(registerRequestDTO.email())) {
            throw new RuntimeException("Email ja em uso");
        }

        ViaCepResponseDTO adress = viaCepService.findAdressByCep(registerRequestDTO.cep());

        User user = User.builder()
                .name(registerRequestDTO.name())
                .email(registerRequestDTO.email())
                .uf(adress.uf())
                .city(adress.localidade())
                .password(passwordEncoder.encode(registerRequestDTO.password()))
                .build();

        userRepository.save(user);

        return UserMapper.toResponse(user);
    }


}
