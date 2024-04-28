package kz.benomads.testproject4sp.service;

import kz.benomads.testproject4sp.dto.OrderDto;
import kz.benomads.testproject4sp.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderById(Long id);
    List<OrderDto> getAllOrders();
    List<OrderDto> getOrdersByCategory(Category category);
    OrderDto updateOrder(Long id, OrderDto orderDto);

    void deleteOrder(Long id);

    List<OrderDto> getOrdersByUserId(Long userId);
}
