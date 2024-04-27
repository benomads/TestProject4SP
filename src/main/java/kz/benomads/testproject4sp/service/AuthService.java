package kz.benomads.testproject4sp.service;

import kz.benomads.testproject4sp.dto.UserDto;
import kz.benomads.testproject4sp.dto.UserRegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public UserDto register(UserRegisterDto userRegisterDto);
}
