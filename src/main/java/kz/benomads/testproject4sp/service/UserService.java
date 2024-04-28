package kz.benomads.testproject4sp.service;

import kz.benomads.testproject4sp.dto.UserDto;
import kz.benomads.testproject4sp.dto.UserRegisterDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto getUserById(Long id);
    UserDto getUserByUsername(String username);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);

}
