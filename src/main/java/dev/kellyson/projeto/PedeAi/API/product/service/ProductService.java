package dev.kellyson.projeto.PedeAi.API.product.service;

import dev.kellyson.projeto.PedeAi.API.category.entity.Category;
import dev.kellyson.projeto.PedeAi.API.category.repository.CategoryRepository;
import dev.kellyson.projeto.PedeAi.API.exception.CategoryNotFoundException;
import dev.kellyson.projeto.PedeAi.API.exception.ConflictException;
import dev.kellyson.projeto.PedeAi.API.exception.RestaurantNotFoundException;
import dev.kellyson.projeto.PedeAi.API.product.dto.ProductRequestDTO;
import dev.kellyson.projeto.PedeAi.API.product.dto.ProductResponseDTO;
import dev.kellyson.projeto.PedeAi.API.product.entity.Product;
import dev.kellyson.projeto.PedeAi.API.product.mapper.ProductMapper;
import dev.kellyson.projeto.PedeAi.API.product.repository.ProductRepository;
import dev.kellyson.projeto.PedeAi.API.restaurant.entity.Restaurant;
import dev.kellyson.projeto.PedeAi.API.restaurant.repository.RestaurantRepository;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponseDTO registerProduct(Long restaurantId,
                                              ProductRequestDTO productRequestDTO,
                                              Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante nao encontrado"));
        Category category = categoryRepository.findById(productRequestDTO.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Categoria nao encontrada"));

        if (productRepository.existsByRestaurantAndNameIgnoreCase(restaurant, productRequestDTO.name())) {
            throw  new ConflictException("Este restaurante já possui esse produto cadastrado");
        }

        if (!restaurant.getOwner().getId().equals(user.getId())) {
            throw new ConflictException("Voce não possui vinculo com este restaurante");
        }

        if (category.getIsActive() == false) {
            throw new ConflictException("Categoria inativa, não é possível cadastrar produto");
        }

        Product product = Product.builder()
                .restaurant(restaurant)
                .category(category)
                .name(productRequestDTO.name())
                .description(productRequestDTO.description())
                .price(productRequestDTO.price())
                .stockQuantity(productRequestDTO.stockQuantity())
                .isAvailable(true)
                .build();



        productRepository.save(product);

        return ProductMapper.toResponse(product);
    }

}
