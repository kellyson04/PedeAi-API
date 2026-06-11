package dev.kellyson.projeto.PedeAi.API.user.controller;

import dev.kellyson.projeto.PedeAi.API.user.dto.UserProfileResponseDTO;
import dev.kellyson.projeto.PedeAi.API.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> me(Authentication authentication) {
        return ResponseEntity.ok(userService.me(authentication));
    }
}
