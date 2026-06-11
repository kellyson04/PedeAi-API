package dev.kellyson.projeto.PedeAi.API.product.mapper;

import dev.kellyson.projeto.PedeAi.API.product.dto.ProductResponseDTO;
import dev.kellyson.projeto.PedeAi.API.product.entity.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {

    public static ProductResponseDTO toResponse(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .restaurantId(product.getRestaurant().getId())
                .restaurantName(product.getRestaurant().getName())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .isAvailable(product.getIsAvailable())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
