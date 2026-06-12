package dev.kellyson.projeto.PedeAi.API.order.repository;

import dev.kellyson.projeto.PedeAi.API.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
