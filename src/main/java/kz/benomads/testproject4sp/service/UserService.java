package kz.benomads.testproject4sp.service;

import kz.benomads.testproject4sp.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto register(UserDto userDto);
    UserDto getUserByUsername(String username);
    UserDto updateUser(UserDto userDto);
    void deleteUser(String username);

}
