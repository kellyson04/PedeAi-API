package dev.kellyson.projeto.PedeAi.API.order.mapper;

import dev.kellyson.projeto.PedeAi.API.address.entity.Address;
import dev.kellyson.projeto.PedeAi.API.cart.model.CartItem;
import dev.kellyson.projeto.PedeAi.API.order.dto.OrderResponseDTO;
import dev.kellyson.projeto.PedeAi.API.order.entity.Order;
import dev.kellyson.projeto.PedeAi.API.order.entity.OrderItem;
import dev.kellyson.projeto.PedeAi.API.product.entity.Product;
import dev.kellyson.projeto.PedeAi.API.restaurant.entity.Restaurant;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class OrderMapper {

    public static Order toEntity(User customer,
                                 Restaurant restaurant,
                                 Address address,
                                 BigDecimal subtotal,
                                 BigDecimal deliveryFee,
                                 BigDecimal discount,
                                 BigDecimal total) {
        return Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .address(address)
                .subtotal(subtotal)
                .deliveryFee(deliveryFee)
                .discount(discount)
                .total(total)
                .build();
    }

    public static OrderResponseDTO toResponse(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .restaurantId(order.getRestaurant().getId())
                .addressId(order.getAddress().getId())
                .status(order.getStatus())
                .subtotal(order.getSubtotal())
                .deliveryFee(order.getDeliveryFee())
                .discount(order.getDiscount())
                .total(order.getTotal())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public static OrderItem toOrderItemEntity(CartItem item, Order order, Product product) {
        return OrderItem.builder()
                .order(order)
                .product(product)
                .productName(item.getProductName())
                .unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getTotalPrice())
                .build();
    }
}
