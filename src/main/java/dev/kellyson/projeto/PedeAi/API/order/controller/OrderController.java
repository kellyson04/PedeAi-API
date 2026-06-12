package dev.kellyson.projeto.PedeAi.API.order.controller;

import dev.kellyson.projeto.PedeAi.API.order.dto.CreateOrderRequestDTO;
import dev.kellyson.projeto.PedeAi.API.order.dto.OrderResponseDTO;
import dev.kellyson.projeto.PedeAi.API.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid CreateOrderRequestDTO request,
                                                        Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request, authentication));
    }
}
