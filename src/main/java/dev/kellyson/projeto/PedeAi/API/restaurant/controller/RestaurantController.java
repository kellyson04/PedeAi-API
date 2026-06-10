package dev.kellyson.projeto.PedeAi.API.restaurant.controller;

import dev.kellyson.projeto.PedeAi.API.restaurant.dto.MyRestaurantResponseDTO;
import dev.kellyson.projeto.PedeAi.API.restaurant.dto.RegisterRestaurantResponseDTO;
import dev.kellyson.projeto.PedeAi.API.restaurant.dto.RestaurantRequestDTO;
import dev.kellyson.projeto.PedeAi.API.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/register")
    public ResponseEntity<RegisterRestaurantResponseDTO> registerMyRestaurant(@RequestBody @Valid RestaurantRequestDTO request,
                                                                              Authentication authentication) {
        restaurantService.registerRestaurant(request, authentication);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<MyRestaurantResponseDTO>> myRestaurant(Authentication authentication) {

        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.myRestaurants(authentication));
    }

    @PatchMapping("/{restaurantId}/close")
    public ResponseEntity<String> closeMyRestaurant(@PathVariable Long restaurantId,Authentication authentication) {
        restaurantService.closeRestaurant(restaurantId,authentication);

        return ResponseEntity.ok().build();
    }
}