package dev.kellyson.projeto.PedeAi.API.restaurant.repository;

import dev.kellyson.projeto.PedeAi.API.restaurant.entity.Restaurant;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    boolean existsByName(String name);
    boolean existsByPhone(String phone);
    List<Restaurant> findByOwner(User owner);
}
