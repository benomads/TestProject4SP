package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.repository.UserRepository;
import kz.benomads.testproject4sp.dto.UserDto;
import kz.benomads.testproject4sp.exception.NullValueException;
import kz.benomads.testproject4sp.exception.UserNotFoundException;
import kz.benomads.testproject4sp.mapper.UserDtoMapper;
import kz.benomads.testproject4sp.model.UserEntity;
import kz.benomads.testproject4sp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }


    @Override
    public UserDto getUserById(Long id) {
        if (id == null) {
            throw new NullValueException("UserEntity id cannot be null");
        }
        UserEntity user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(
                String
                    .format("UserEntity id=%d not found", id)));

        return userDtoMapper.apply(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserEntity user = userRepository.findAllByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(
                String
                    .format("UserEntity username=%s not found", username)));

        return userDtoMapper.apply(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty())
            throw new UserNotFoundException("No users found");

        return users.stream()
            .map(userDtoMapper)
            .toList();
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        if (id == null) {
            throw new NullValueException("UserEntity id cannot be null");
        }

        UserEntity user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(
            String
                .format("UserEntity id=%d not found", id)));

        UserEntity checkedUserEntity = checkUserDtoFields(user, userDto);

        userRepository.save(checkedUserEntity);

        return userDtoMapper.apply(checkedUserEntity);
    }

    private UserEntity checkUserDtoFields(UserEntity user, UserDto userDto) {
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
            throw new NullValueException("UserEntity id cannot be null");
        }
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(
                String
                    .format("UserEntity id=%d not found", id));
        }

        userRepository.deleteById(id);
    }
}