package dev.kellyson.projeto.PedeAi.API.cart.controller;

import dev.kellyson.projeto.PedeAi.API.cart.dto.AddCartItemRequestDTO;
import dev.kellyson.projeto.PedeAi.API.cart.model.Cart;
import dev.kellyson.projeto.PedeAi.API.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<Cart> addItem(@RequestBody @Valid AddCartItemRequestDTO request,
                                        Authentication authentication) {
        return ResponseEntity.ok(cartService.addItem(request, authentication));
    }
}
