package dev.kellyson.projeto.PedeAi.API.auth.service;

import dev.kellyson.projeto.PedeAi.API.address.entity.Address;
import dev.kellyson.projeto.PedeAi.API.address.repository.AddressRepository;
import dev.kellyson.projeto.PedeAi.API.auth.dto.LoginRequestDTO;
import dev.kellyson.projeto.PedeAi.API.config.security.TokenProvider;
import dev.kellyson.projeto.PedeAi.API.auth.dto.RegisterRequestDTO;
import dev.kellyson.projeto.PedeAi.API.auth.dto.UserResponseDTO;
import dev.kellyson.projeto.PedeAi.API.config.viacep.dto.ViaCepResponseDTO;
import dev.kellyson.projeto.PedeAi.API.config.viacep.service.ViaCepService;
import dev.kellyson.projeto.PedeAi.API.exception.BadRequestException;
import dev.kellyson.projeto.PedeAi.API.exception.ConflictException;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import dev.kellyson.projeto.PedeAi.API.user.mapper.UserMapper;
import dev.kellyson.projeto.PedeAi.API.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final ViaCepService viaCepService;

    public UserResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByEmail(registerRequestDTO.email())) {
            throw new ConflictException("Email ja em uso");
        }

        ViaCepResponseDTO addressData = viaCepService.findAdressByCep(registerRequestDTO.cep());

        User user = User.builder()
                .name(registerRequestDTO.name())
                .email(registerRequestDTO.email())
                .password(passwordEncoder.encode(registerRequestDTO.password()))
                .build();

        userRepository.save(user);

        Address address = Address.builder()
                .user(user)
                .street(addressData.logradouro())
                .number(registerRequestDTO.number())
                .neighborhood(addressData.bairro())
                .city(addressData.localidade())
                .state(addressData.uf())
                .zipCode(addressData.cep())
                .complement(registerRequestDTO.complement())
                .build();

        addressRepository.save(address);

        return UserMapper.toResponse(user);
    }

    public String login(LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDTO.email(),loginRequestDTO.password());

        try{
            Authentication userAuthenticated = authenticationManager.authenticate(authenticationToken);

            String token = tokenProvider.generateToken(userAuthenticated);

            return token;

        } catch (BadCredentialsException e) {
            throw new BadRequestException("credenciais invalidas");
        }
    }

}
