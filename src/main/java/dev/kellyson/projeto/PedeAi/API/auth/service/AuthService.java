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
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final ViaCepService viaCepService;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCK_TIME_MINUTES = 30;

    @Transactional
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

            User user = (User) userAuthenticated.getPrincipal();

            user.setFailedLoginAttempts(0);
            user.setLockedUntil(null);
            userRepository.save(user);

            String token = tokenProvider.generateToken(userAuthenticated);

            return token;

        } catch (LockedException e) {
            throw new BadRequestException
                    ("Este usuario foi bloqueado por tentativas invalidas. Tente novamente em " + LOCK_TIME_MINUTES + " minutos.");
        }
        catch (BadCredentialsException e) {
            User user = userRepository.findByEmail(loginRequestDTO.email())
                    .orElse(null);

            if (user != null) {
                int attempts = user.getFailedLoginAttempts() + 1;
                user.setFailedLoginAttempts(attempts);

                if (attempts < MAX_FAILED_ATTEMPTS) {
                    userRepository.save(user);
                    throw new BadRequestException("credenciais invalidas.");
                }

                user.setLockedUntil(java.time.LocalDateTime.now().plusMinutes(LOCK_TIME_MINUTES));
                userRepository.save(user);

                throw new BadRequestException
                        ("Usuario bloqueado por tentativas invalidas. Tente novamente em " + LOCK_TIME_MINUTES + " minutos.");
            }
            throw new BadRequestException("credenciais invalidas");
        }
    }

}
