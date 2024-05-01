package kz.benomads.testproject4sp.mapper;

import kz.benomads.testproject4sp.dto.OrderResponseDto;
import kz.benomads.testproject4sp.model.Order;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrderDtoMapper implements Function<Order, OrderResponseDto> {
    @Override
    public OrderResponseDto apply(Order order) {
        return new OrderResponseDto(
            order.getId(),
            order.getUser().getId(),
            order.getProduct().getId(),
            order.getQuantity(),
            order.getTotalPrice(),
            order.getAddress(),
            order.getPhoneNumber()
        );
    }
}
