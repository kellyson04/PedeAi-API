package dev.kellyson.projeto.PedeAi.API.auth.controller;


import dev.kellyson.projeto.PedeAi.API.auth.service.AuthService;
import dev.kellyson.projeto.PedeAi.API.user.dto.RegisterRequestDTO;
import dev.kellyson.projeto.PedeAi.API.user.dto.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {

        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
