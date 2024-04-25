package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.dao.UserRepository;
import kz.benomads.testproject4sp.dto.UserDto;
import kz.benomads.testproject4sp.dto.UserRegistrationRequest;
import kz.benomads.testproject4sp.exception.UserAlreadyExistsException;
import kz.benomads.testproject4sp.exception.UserNotFoundException;
import kz.benomads.testproject4sp.mapper.UserDtoMapper;
import kz.benomads.testproject4sp.model.Role;
import kz.benomads.testproject4sp.model.User;
import kz.benomads.testproject4sp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
//                           PasswordEncoder passwordEncoder,
                           UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public UserDto register(UserRegistrationRequest userRequest) {
        if (!Objects.equals(userRequest.getPassword(),
                            userRequest.getMatchingPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new UserAlreadyExistsException(
                String
                    .format("Login username=%s already exists", userRequest.getUsername()));
        }

        User user = User.builder()
                .fullName(userRequest.getFullName())
                .username(userRequest.getUsername())
//                .password(passwordEncoder.encode(userRequest.getPassword()))
                .avatarUrl(userRequest.getAvatarUrl())
                .role(Role.valueOf("USER"))
                .build();

        userRepository.save(user);

        return userDtoMapper.apply(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(
                String
                    .format("User id=%d not found", id)));

        return userDtoMapper.apply(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findAllByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(
                String
                    .format("User username=%s not found", username)));

        return userDtoMapper.apply(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty())
            throw new UserNotFoundException("No users found");

        return users.stream()
            .map(userDtoMapper)
            .toList();
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(
            String
                .format("User id=%d not found", id)));

        User checkedUser = checkUserDtoFields(user, userDto);

        userRepository.save(checkedUser);

        return userDtoMapper.apply(checkedUser);
    }

    private User checkUserDtoFields(User user, UserDto userDto) {
        if (userDto.getFullName() != null) {
            user.setFullName(userDto.getFullName());
        }
        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getAvatarUrl() != null) {
            user.setAvatarUrl(userDto.getAvatarUrl());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }

        return user;
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(
                String
                    .format("User id=%d not found", id));
        }

        userRepository.deleteById(id);
    }
}