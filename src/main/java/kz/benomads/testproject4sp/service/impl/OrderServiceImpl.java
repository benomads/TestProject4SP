package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.dto.CategoryDto;
import kz.benomads.testproject4sp.dto.OrderRequestDto;
import kz.benomads.testproject4sp.dto.OrderResponseDto;
import kz.benomads.testproject4sp.exception.NullValueException;
import kz.benomads.testproject4sp.exception.OrderNotFoundException;
import kz.benomads.testproject4sp.mapper.OrderDtoMapper;
import kz.benomads.testproject4sp.model.UserEntity;
import kz.benomads.testproject4sp.repository.OrderRepository;
import kz.benomads.testproject4sp.repository.ProductRepository;
import kz.benomads.testproject4sp.repository.UserRepository;
import kz.benomads.testproject4sp.model.Order;
import kz.benomads.testproject4sp.model.Product;
import kz.benomads.testproject4sp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        if (orderRequestDto == null) {
            throw new NullValueException("OrderResponseDto cannot be null");
        }

        Double calculateTotalPrice = calculateTotalPrice(orderRequestDto.getProductId(),
            orderRequestDto.getQuantity());
        UserEntity user = userRepository.findAllByUsername(getCurrentUserName())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert OrderResponseDto to Order
        Order order = Order.builder()
            .user(user)
            .product(productRepository.findProductById(orderRequestDto.getProductId()))
            .quantity(orderRequestDto.getQuantity())
            .totalPrice(calculateTotalPrice)
            .address(orderRequestDto.getAddress())
            .phoneNumber(orderRequestDto.getPhoneNumber())
            .build();

        Order savedOrder = orderRepository.save(order);

        // Convert saved Order back to OrderResponseDto and return
        return orderDtoMapper.apply(savedOrder);
    }

    private Double calculateTotalPrice(Long productId,
                                       Integer quantity) {
        // Fetch Product by id and calculate total amount
        Product product = productRepository.findProductById(productId);
        return product.getPrice() * quantity;
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        return currentUserName;
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        if (id == null) {
            throw new NullValueException("Id cannot be null");
        }

        // Fetch Order by id
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(
                String.format("Question with id=%d not found", id)));

        // Convert fetched Order to OrderResponseDto and return
        return orderDtoMapper.apply(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found");
        }

        // Convert each Order to OrderResponseDto and collect to a list
        return orders.stream()
            .map(orderDtoMapper)
            .collect(Collectors.toList());

    }


    @Override
    public List<OrderResponseDto> getOrdersByCategory(CategoryDto category) {

        if (category == null || category.toString().isEmpty()) {
            throw new NullValueException(
                String
                    .format("Category category=%s cannot be null or empty", category));
        }

        List<Order> orders = orderRepository.findAllByProductCategory(category);

        // Convert each Order to OrderResponseDto and collect to a list
        return orders.stream()
            .map(orderDtoMapper)
            .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto updateOrder(Long id, OrderRequestDto orderRequestDto) {
        if (orderRequestDto == null || id == null) {
            throw new NullValueException(
                String
                    .format("OrderResponseDto or Order id=%d cannot be null", id));
        }

        Order order = orderRepository.findOrderById(id)
            .orElseThrow(() -> new OrderNotFoundException("No orders found"));
        String currentUserName = getCurrentUserName();

        // Check and Update Order with new values
        Order updatedOrder = checkOrderDtoFields(order, orderRequestDto, currentUserName);

        // Save the updated Order back to the database
        orderRepository.save(updatedOrder);

        // Convert updated Order to OrderResponseDto and return
        return orderDtoMapper.apply(updatedOrder);
    }

    private Order checkOrderDtoFields(Order order,
                                      OrderRequestDto orderRequestDto,
                                      String currentUserName) {
        if (currentUserName != null) {
            order.setUser(userRepository.findAllByUsername(currentUserName)
                            .orElseThrow(() -> new OrderNotFoundException("No orders found")));
        }
        if (orderRequestDto.getProductId() != null) {
            order.setProduct(productRepository.findProductById(orderRequestDto.getProductId()));
        }
        if (orderRequestDto.getQuantity() != null) {
            order.setQuantity(orderRequestDto.getQuantity());
        }
        if (orderRequestDto.getAddress() != null && !orderRequestDto.getAddress().isEmpty()) {
            order.setAddress(orderRequestDto.getAddress());
        }
        if (orderRequestDto.getPhoneNumber() != null && !orderRequestDto.getPhoneNumber().isEmpty()) {
            order.setPhoneNumber(orderRequestDto.getPhoneNumber());
        }

        order.setTotalPrice(calculateTotalPrice(orderRequestDto.getProductId(),
                                                orderRequestDto.getQuantity()));

        return order;
    }



    @Override
    public void deleteOrder(Long id) {
        if (id == null) {
            throw new NullValueException("Id cannot be null");
        }
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("No orders found");
        }

        // Delete Order by id
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        if (userId == null) {
            throw new NullValueException("User id cannot be null");
        }
            List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream()
            .map(orderDtoMapper)
            .collect(Collectors.toList());
    }
}