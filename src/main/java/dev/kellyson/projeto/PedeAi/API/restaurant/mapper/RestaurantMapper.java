package dev.kellyson.projeto.PedeAi.API.restaurant.mapper;

import dev.kellyson.projeto.PedeAi.API.restaurant.dto.MyRestaurantResponseDTO;
import dev.kellyson.projeto.PedeAi.API.restaurant.dto.RegisterRestaurantResponseDTO;
import dev.kellyson.projeto.PedeAi.API.restaurant.dto.RestaurantRequestDTO;
import dev.kellyson.projeto.PedeAi.API.restaurant.entity.Restaurant;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantMapper {

    public static Restaurant toEntity(RestaurantRequestDTO restaurantRequestDTO, User owner) {
        return Restaurant.builder()
                .owner(owner)
                .name(restaurantRequestDTO.name())
                .description(restaurantRequestDTO.description())
                .phone(restaurantRequestDTO.phone())
                .build();
    }

    public static RegisterRestaurantResponseDTO toRegisterResponse(Restaurant restaurant) {
        return RegisterRestaurantResponseDTO.builder()
                .name(restaurant.getName())
                .createdAt(restaurant.getCreatedAt())
                .build();
    }

    public static MyRestaurantResponseDTO toMyRestaurantResponse(Restaurant restaurant) {
        return MyRestaurantResponseDTO.builder()
                .id(restaurant.getId())
                .ownerId(restaurant.getOwner().getId())
                .ownerName(restaurant.getOwner().getName())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .phone(restaurant.getPhone())
                .isOpen(restaurant.getIsOpen())
                .isActive(restaurant.getIsActive())
                .createdAt(restaurant.getCreatedAt())
                .build();
    }
}