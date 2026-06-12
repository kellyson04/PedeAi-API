package dev.kellyson.projeto.PedeAi.API.order.service;

import dev.kellyson.projeto.PedeAi.API.address.entity.Address;
import dev.kellyson.projeto.PedeAi.API.address.repository.AddressRepository;
import dev.kellyson.projeto.PedeAi.API.exception.AddressNotFoundException;
import dev.kellyson.projeto.PedeAi.API.exception.RestaurantNotFoundException;
import dev.kellyson.projeto.PedeAi.API.order.dto.CreateOrderRequestDTO;
import dev.kellyson.projeto.PedeAi.API.order.dto.OrderResponseDTO;
import dev.kellyson.projeto.PedeAi.API.order.entity.Order;
import dev.kellyson.projeto.PedeAi.API.order.mapper.OrderMapper;
import dev.kellyson.projeto.PedeAi.API.order.repository.OrderRepository;
import dev.kellyson.projeto.PedeAi.API.restaurant.entity.Restaurant;
import dev.kellyson.projeto.PedeAi.API.restaurant.repository.RestaurantRepository;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public OrderResponseDTO createOrder(CreateOrderRequestDTO request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante nao encontrado"));

        Address address = addressRepository.findById(request.addressId())
                .orElseThrow(() -> new AddressNotFoundException("Endereco nao encontrado"));

        Order order = OrderMapper.toEntity(request, user, restaurant, address);

        orderRepository.save(order);

        return OrderMapper.toResponse(order);
    }
}
