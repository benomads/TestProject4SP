package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.exception.OrderNotFoundException;
import kz.benomads.testproject4sp.mapper.OrderDtoMapper;
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


    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderDtoMapper orderDtoMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository,
                            OrderDtoMapper orderDtoMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderDtoMapper = orderDtoMapper;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        if (orderDto == null) {
            throw new IllegalArgumentException("OrderDto cannot be null");
        }

        Double calculateTotalPrice = calculateTotalPrice(orderDto.getProductId(),
                                                         orderDto.getQuantity());
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
        return orderDtoMapper.apply(savedOrder);
    }

    private Double calculateTotalPrice(Long productId,
                                       Integer quantity) {
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
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(
                String.format("Question with id=%d not found", id)));

        // Convert fetched Order to OrderDto and return
        return orderDtoMapper.apply(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found");
        }

        // Convert each Order to OrderDto and collect to a list
        return orders.stream()
            .map(orderDtoMapper)
            .collect(Collectors.toList());

    }


    @Override
    public List<OrderDto> getOrdersByCategory(Category category) {

        if (category == null || category.toString().isEmpty()) {
            throw new IllegalArgumentException(
                String
                    .format("Category category=%s cannot be null or empty", category));
        }

        List<Order> orders = orderRepository.findAllByProductCategory(category);

        // Convert each Order to OrderDto and collect to a list
        return orders.stream()
            .map(orderDtoMapper)
            .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        if (orderDto == null || orderDto.getId() == null) {
            throw new IllegalArgumentException(
                String
                    .format("OrderDto or Order id=%d cannot be null", id));
        }

        Order order = orderRepository.findOrderById(orderDto.getId())
            .orElseThrow(() -> new OrderNotFoundException("No orders found"));

        // Check and Update Order with new values
        Order updatedOrder = checkOrderDtoFields(order, orderDto);

        // Save the updated Order back to the database
        orderRepository.save(updatedOrder);

        // Convert updated Order to OrderDto and return
        return orderDtoMapper.apply(updatedOrder);
    }

    private Order checkOrderDtoFields(Order order, OrderDto orderDto) {
        if (orderDto.getUserId() != null) {
            order.setUser(userRepository.findUserById(orderDto.getUserId()));
        }
        if (orderDto.getProductId() != null) {
            order.setProduct(productRepository.findProductById(orderDto.getProductId()));
        }
        if (orderDto.getQuantity() != null) {
            order.setQuantity(orderDto.getQuantity());
        }
        if (orderDto.getAddress() != null && !orderDto.getAddress().isEmpty()) {
            order.setAddress(orderDto.getAddress());
        }
        if (orderDto.getPhoneNumber() != null && !orderDto.getPhoneNumber().isEmpty()) {
            order.setPhoneNumber(orderDto.getPhoneNumber());
        }

        order.setTotalPrice(calculateTotalPrice(orderDto.getProductId(),
                                                orderDto.getQuantity()));

        return order;
    }



    @Override
    public void deleteOrder(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("No orders found");
        }

        // Delete Order by id
        orderRepository.deleteById(id);
    }
}