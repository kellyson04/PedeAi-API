package dev.kellyson.projeto.PedeAi.API.restaurant.service;

import dev.kellyson.projeto.PedeAi.API.exception.ConflictException;
import dev.kellyson.projeto.PedeAi.API.restaurant.dto.MyRestaurantResponseDTO;
import dev.kellyson.projeto.PedeAi.API.restaurant.dto.RegisterRestaurantResponseDTO;
import dev.kellyson.projeto.PedeAi.API.restaurant.dto.RestaurantRequestDTO;
import dev.kellyson.projeto.PedeAi.API.restaurant.entity.Restaurant;
import dev.kellyson.projeto.PedeAi.API.restaurant.mapper.RestaurantMapper;
import dev.kellyson.projeto.PedeAi.API.restaurant.repository.RestaurantRepository;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RegisterRestaurantResponseDTO registerRestaurant(RestaurantRequestDTO restaurantRequestDTO, Authentication authentication) {
        if (restaurantRepository.existsByName(restaurantRequestDTO.name())) {
            throw new ConflictException("Esse nome de restaurante ja está em uso");
        }

        if (restaurantRepository.existsByPhone(restaurantRequestDTO.phone())) {
            throw new ConflictException("Esse número de telefone ja está em uso");
        }

        User owner = (User) authentication.getPrincipal();

        Restaurant restaurant = RestaurantMapper.toEntity(restaurantRequestDTO, owner);

        restaurantRepository.save(restaurant);

        return RestaurantMapper.toRegisterResponse(restaurant);
    }

    public List<MyRestaurantResponseDTO> myRestaurants(Authentication authentication) {
        User owner = (User) authentication.getPrincipal();

        return restaurantRepository.findByOwner(owner).stream()
                .map(restaurant -> RestaurantMapper.toMyRestaurantResponse(restaurant))
                .toList();
    }

    public String closeRestaurant(Long restaurantId,Authentication authentication) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Esse restaurante não existe"));

        User owner = (User) authentication.getPrincipal();

        List<Restaurant> restaurants = restaurantRepository.findByOwner(owner);

        if (restaurants.contains(restaurant)) {
            restaurant.setIsOpen(false);

            restaurantRepository.save(restaurant);
        }

        return "Seu restaurante foi fechado";
    }
}