package dev.kellyson.projeto.PedeAi.API.order.repository;

import dev.kellyson.projeto.PedeAi.API.order.entity.Order;
import dev.kellyson.projeto.PedeAi.API.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}
