package kz.benomads.testproject4sp.mapper;

import kz.benomads.testproject4sp.dto.UserDto;
import kz.benomads.testproject4sp.model.Order;
import kz.benomads.testproject4sp.model.Product;
import kz.benomads.testproject4sp.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
            user.getId(),
            user.getFullName(),
            user.getUsername(),
            user.getAvatarUrl(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getRole(),
            user.getOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList()),
            user.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList())
        );
    }
}
