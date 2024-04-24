package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.dao.UserRepository;
import kz.benomads.testproject4sp.dto.UserDto;
import kz.benomads.testproject4sp.model.Role;
import kz.benomads.testproject4sp.model.User;
import kz.benomads.testproject4sp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto register(UserDto userDto) {
        if (!Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Login already exists");
        }

        User user = User.builder()
                .fullName(userDto.getFullName())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .avatarUrl(userDto.getAvatarUrl())
                .role(Role.valueOf("USER"))
                .build();

        userRepository.save(user);

        return userDto;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findAllByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return UserDto.builder()
            .fullName(user.getFullName())
            .username(user.getUsername())
            .avatarUrl(user.getAvatarUrl())
            .build();
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (!userRepository.existsByUsername(userDto.getUsername()))
            throw new IllegalArgumentException("User not found");


        User user = User.builder()
            .fullName(userDto.getFullName())
            .username(userDto.getUsername())
            .password(passwordEncoder.encode(userDto.getPassword()))
            .avatarUrl(userDto.getAvatarUrl())
            .role(Role.valueOf("USER"))
            .build();

        userRepository.save(user);

        return userDto;
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.findAllByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.delete(user);
    }
}