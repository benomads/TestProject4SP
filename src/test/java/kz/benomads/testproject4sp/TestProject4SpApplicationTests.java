package kz.benomads.testproject4sp;

import kz.benomads.testproject4sp.dto.OrderResponseDto;
import kz.benomads.testproject4sp.mapper.OrderDtoMapper;
import kz.benomads.testproject4sp.model.Order;
import kz.benomads.testproject4sp.model.Product;
import kz.benomads.testproject4sp.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TestProject4SpApplicationTests {
    //TODO write tests for each endpoint in the application

    @Test
    void contextLoads() {
    }

    @Test
    void testMain() {
        TestProject4SpApplication.main(new String[] {});
    }

    @Test
    void testOrderDtoMapper() {
        OrderDtoMapper orderDtoMapper = new OrderDtoMapper();
        Order order = new Order();
        order.setId(1L);
        order.setQuantity(1);
        order.setTotalPrice(1.0);
        order.setAddress("address");
        order.setPhoneNumber("1234567890");
        UserEntity user = new UserEntity();
        user.setId(1L);
        order.setUser(user);
        Product product = new Product();
        product.setId(1L);
        order.setProduct(product);
        OrderResponseDto orderResponseDto = orderDtoMapper.apply(order);
        assertEquals(order.getId(), orderResponseDto.getId());
        assertEquals(order.getUser().getId(), orderResponseDto.getUserId());
        assertEquals(order.getProduct().getId(), orderResponseDto.getProductId());
        assertEquals(order.getQuantity(), orderResponseDto.getQuantity());
        assertEquals(order.getTotalPrice(), orderResponseDto.getTotalPrice());
        assertEquals(order.getAddress(), orderResponseDto.getAddress());
        assertEquals(order.getPhoneNumber(), orderResponseDto.getPhoneNumber());
    }

}
