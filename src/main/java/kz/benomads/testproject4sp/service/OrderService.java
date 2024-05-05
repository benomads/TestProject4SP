package kz.benomads.testproject4sp.service;

import kz.benomads.testproject4sp.dto.CategoryDto;
import kz.benomads.testproject4sp.dto.OrderRequestDto;
import kz.benomads.testproject4sp.dto.OrderResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    OrderResponseDto getOrderById(Long id);
    List<OrderResponseDto> getAllOrders();
    List<OrderResponseDto> getOrdersByCategory(CategoryDto category);
    OrderResponseDto updateOrder(Long id, OrderRequestDto orderRequestDto);

    void deleteOrder(Long id);

    List<OrderResponseDto> getOrdersByUserId(Long userId);
}
