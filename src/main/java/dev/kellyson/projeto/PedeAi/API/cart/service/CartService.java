package dev.kellyson.projeto.PedeAi.API.cart.service;

import dev.kellyson.projeto.PedeAi.API.cart.dto.AddCartItemRequestDTO;
import dev.kellyson.projeto.PedeAi.API.cart.model.Cart;
import dev.kellyson.projeto.PedeAi.API.cart.model.CartItem;
import dev.kellyson.projeto.PedeAi.API.exception.BadRequestException;
import dev.kellyson.projeto.PedeAi.API.exception.ConflictException;
import dev.kellyson.projeto.PedeAi.API.exception.ProductNotFoundException;
import dev.kellyson.projeto.PedeAi.API.product.entity.Product;
import dev.kellyson.projeto.PedeAi.API.product.repository.ProductRepository;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Cart> redisTemplate;

    private static final Duration CART_TTL = Duration.ofHours(2);

    @Transactional(readOnly = true)
    public Cart addItem(AddCartItemRequestDTO request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ProductNotFoundException("Produto nao encontrado"));

        if (Boolean.FALSE.equals(product.getIsAvailable())) {
            throw new ConflictException("Produto indisponivel");
        }

        if (request.quantity() <= 0) {
            throw new BadRequestException("Quantidade deve ser maior que zero");
        }

        String cartKey = "cart:user:" + user.getId();

        Cart cart = redisTemplate.opsForValue().get(cartKey);

        if (cart == null) {
            cart = Cart.builder()
                    .userId(user.getId())
                    .restaurantId(product.getRestaurant().getId())
                    .items(new ArrayList<>())
                    .build();
        }

        if (!cart.getRestaurantId().equals(product.getRestaurant().getId())) {
            throw new ConflictException("O carrinho possui produtos de outro restaurante");
        }

        List<CartItem> items = cart.getItems();

        CartItem existingItem = items.stream()
                .filter(cartItem -> cartItem.getProductId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        int totalQuantity = request.quantity();

        if (existingItem != null) {
            totalQuantity += existingItem.getQuantity();
        }

        if (product.getStockQuantity() < totalQuantity) {
            throw new BadRequestException("Quantidade maior que o estoque disponivel");
        }

        BigDecimal totalPrice = product.getPrice()
                .multiply(BigDecimal.valueOf(totalQuantity));

        if (existingItem == null) {
            items.add(CartItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .unitPrice(product.getPrice())
                    .quantity(totalQuantity)
                    .totalPrice(totalPrice)
                    .build());
        } else {
            existingItem.setProductName(product.getName());
            existingItem.setUnitPrice(product.getPrice());
            existingItem.setQuantity(totalQuantity);
            existingItem.setTotalPrice(totalPrice);
        }


        redisTemplate.opsForValue().set(cartKey, cart, CART_TTL);

        return cart;
    }

    public void cleanCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        String cartKey = "cart:user:" + user.getId();

        Cart cart = redisTemplate.opsForValue().get(cartKey);

        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new BadRequestException("Carrinho vazio");
        }

        redisTemplate.delete(cartKey);
    }
}
