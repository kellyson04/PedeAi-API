package dev.kellyson.projeto.PedeAi.API.product.repository;

import dev.kellyson.projeto.PedeAi.API.product.entity.Product;
import dev.kellyson.projeto.PedeAi.API.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByRestaurantAndNameIgnoreCase(Restaurant restaurant, String name);
    List<Product> findByRestaurantAndIsAvailableTrue(Restaurant restaurant);
}
