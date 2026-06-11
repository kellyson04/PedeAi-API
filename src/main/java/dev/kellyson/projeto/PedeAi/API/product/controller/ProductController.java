package dev.kellyson.projeto.PedeAi.API.product.controller;

import dev.kellyson.projeto.PedeAi.API.product.dto.ProductRequestDTO;
import dev.kellyson.projeto.PedeAi.API.product.dto.ProductResponseDTO;
import dev.kellyson.projeto.PedeAi.API.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/{restaurantId}/register")
    public ResponseEntity<ProductResponseDTO> createProduct(@PathVariable Long restaurantId,
                                                            @RequestBody @Valid ProductRequestDTO productRequestDTO,
                                                            Authentication authentication) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.registerProduct(restaurantId,productRequestDTO,authentication));
    }

}
