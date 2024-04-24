package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.dao.OrderRepository;
import kz.benomads.testproject4sp.dao.ProductRepository;
import kz.benomads.testproject4sp.dao.UserRepository;
import kz.benomads.testproject4sp.dto.OrderDto;
import kz.benomads.testproject4sp.model.Category;
import kz.benomads.testproject4sp.model.Order;
import kz.benomads.testproject4sp.model.Product;
import kz.benomads.testproject4sp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        if (orderDto == null) {
            throw new IllegalArgumentException("OrderDto cannot be null");
        }

        Double calculateTotalPrice = calculateTotalPrice(orderDto.getProductId(), orderDto.getQuantity());
        // Convert OrderDto to Order
        Order order = Order.builder()
            .user(userRepository.findUserById(orderDto.getUserId()))
            .product(productRepository.findProductById(orderDto.getProductId()))
            .quantity(orderDto.getQuantity())
            .totalPrice(calculateTotalPrice)
            .address(orderDto.getAddress())
            .phoneNumber(orderDto.getPhoneNumber())
            .build();

        Order savedOrder = orderRepository.save(order);

        // Convert saved Order back to OrderDto and return
        return OrderDto.builder()
            .id(savedOrder.getId())
            .userId(savedOrder.getUser().getId())
            .productId(savedOrder.getProduct().getId())
            .quantity(savedOrder.getQuantity())
            .totalPrice(savedOrder.getTotalPrice())
            .address(savedOrder.getAddress())
            .phoneNumber(savedOrder.getPhoneNumber())
            .build();
    }

    private Double calculateTotalPrice(Long productId, Integer quantity) {
        // Fetch Product by id and calculate total amount
        Product product = productRepository.findProductById(productId);
        return product.getPrice() * quantity;
    }

    @Override
    public OrderDto getOrderById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        // Fetch Order by id
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Convert fetched Order to OrderDto and return
        return OrderDto.builder()
            .id(order.getId())
            .userId(order.getUser().getId())
            .productId(order.getProduct().getId())
            .quantity(order.getQuantity())
            .totalPrice(order.getTotalPrice())
            .address(order.getAddress())
            .phoneNumber(order.getPhoneNumber())
            .build();
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("No orders found");
        }


        // Convert each Order to OrderDto and collect to a list
        return orders.stream()
            .map(order -> OrderDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .productId(order.getProduct().getId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .build())
            .collect(Collectors.toList());

    }


    @Override
    public List<OrderDto> getOrdersByCategory(Category category) {

        if (category == null || category.toString().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }

        // Fetch all Orders by category
        List<Order> orders = orderRepository.findAllByProductCategory(category);

        // Convert each Order to OrderDto and collect to a list
        return orders.stream()
            .map(order -> OrderDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .productId(order.getProduct().getId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto) {
        if (orderDto == null || orderDto.getId() == null) {
            throw new IllegalArgumentException("OrderDto or Order id cannot be null");
        }

        // Fetch Order by id, update fields, save, and convert to OrderDto
        Order order = orderRepository.findById(orderDto.getId()).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        // Set fields from orderDto to order
        Order updatedOrder = orderRepository.save(order);
        OrderDto updatedOrderDto = new OrderDto();

        // Set fields from updatedOrder to updatedOrderDto
        return updatedOrderDto;
    }

    @Override
    public void deleteOrder(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        // Delete Order by id
        orderRepository.deleteById(id);
    }
}