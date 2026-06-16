package dev.kellyson.projeto.PedeAi.API.order.service;

import dev.kellyson.projeto.PedeAi.API.address.entity.Address;
import dev.kellyson.projeto.PedeAi.API.address.repository.AddressRepository;
import dev.kellyson.projeto.PedeAi.API.cart.model.Cart;
import dev.kellyson.projeto.PedeAi.API.cart.model.CartItem;
import dev.kellyson.projeto.PedeAi.API.exception.AddressNotFoundException;
import dev.kellyson.projeto.PedeAi.API.exception.BadRequestException;
import dev.kellyson.projeto.PedeAi.API.exception.RestaurantNotFoundException;
import dev.kellyson.projeto.PedeAi.API.order.dto.CreateOrderRequestDTO;
import dev.kellyson.projeto.PedeAi.API.order.dto.OrderResponseDTO;
import dev.kellyson.projeto.PedeAi.API.order.entity.Order;
import dev.kellyson.projeto.PedeAi.API.order.entity.OrderItem;
import dev.kellyson.projeto.PedeAi.API.order.mapper.OrderMapper;
import dev.kellyson.projeto.PedeAi.API.order.repository.OrderItemRepository;
import dev.kellyson.projeto.PedeAi.API.order.repository.OrderRepository;
import dev.kellyson.projeto.PedeAi.API.product.entity.Product;
import dev.kellyson.projeto.PedeAi.API.product.repository.ProductRepository;
import dev.kellyson.projeto.PedeAi.API.restaurant.entity.Restaurant;
import dev.kellyson.projeto.PedeAi.API.restaurant.repository.RestaurantRepository;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final RedisTemplate<String, Cart> redisTemplate;

    @Transactional
    public OrderResponseDTO createOrder(CreateOrderRequestDTO request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Cart cart = redisTemplate.opsForValue().get("cart:user:" + user.getId());

        if (cart == null || cart.getItems().isEmpty()) {
            throw new BadRequestException("Seu Carrinho esta vazio");
        }

        Restaurant restaurant = restaurantRepository.findById(cart.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante nao encontrado"));

        Address address = addressRepository.findById(request.addressId())
                .orElseThrow(() -> new AddressNotFoundException("Endereco nao encontrado"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Endereco nao pertence ao usuario");
        }

        BigDecimal subtotal = cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal deliveryFee = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal total = subtotal.add(deliveryFee).subtract(discount);

        Order order = OrderMapper.toEntity(user, restaurant, address, subtotal, deliveryFee, discount, total);

        List<OrderItem> items = cart.getItems().stream()
                .map(cartItem -> {
                    Product product = productRepository.findById(cartItem.getProductId())
                            .orElseThrow(() -> new BadRequestException("Produto nao encontrado no carrinho"));

                    return OrderMapper.toOrderItemEntity(cartItem, order, product);
                })
                .toList();

        orderRepository.save(order);
        orderItemRepository.saveAll(items);

        return OrderMapper.toResponse(order);
    }
}
