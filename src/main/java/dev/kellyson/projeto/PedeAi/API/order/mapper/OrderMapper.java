package dev.kellyson.projeto.PedeAi.API.order.mapper;

import dev.kellyson.projeto.PedeAi.API.address.entity.Address;
import dev.kellyson.projeto.PedeAi.API.order.dto.CreateOrderRequestDTO;
import dev.kellyson.projeto.PedeAi.API.order.dto.OrderResponseDTO;
import dev.kellyson.projeto.PedeAi.API.order.entity.Order;
import dev.kellyson.projeto.PedeAi.API.restaurant.entity.Restaurant;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderMapper {

    public static Order toEntity(CreateOrderRequestDTO request, User customer, Restaurant restaurant, Address address) {
        return Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .address(address)
                .subtotal(request.subtotal())
                .deliveryFee(request.deliveryFee())
                .discount(request.discount())
                .total(request.total())
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
}
