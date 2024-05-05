package kz.benomads.testproject4sp;


import kz.benomads.testproject4sp.dto.UserDto;
import kz.benomads.testproject4sp.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDtoTest {

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        List<Role> roles = Arrays.asList(new Role(1, "USER"), new Role(2, "ADMIN"));
        userDto = new UserDto(1L, "John Doe", "johndoe", "avatarUrl", "johndoe@example.com", "1234567890", roles);
    }

    @Test
    public void testUserDto() {
        assertEquals(1L, userDto.getId());
        assertEquals("John Doe", userDto.getFullName());
        assertEquals("johndoe", userDto.getUsername());
        assertEquals("avatarUrl", userDto.getAvatarUrl());
        assertEquals("johndoe@example.com", userDto.getEmail());
        assertEquals("1234567890", userDto.getPhoneNumber());
        assertEquals(2, userDto.getRole().size());
    }
}
