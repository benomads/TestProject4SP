package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.dao.RoleRepository;
import kz.benomads.testproject4sp.dao.UserRepository;
import kz.benomads.testproject4sp.dto.UserDto;
import kz.benomads.testproject4sp.dto.UserRegisterDto;
import kz.benomads.testproject4sp.exception.UserAlreadyExistsException;
import kz.benomads.testproject4sp.mapper.UserDtoMapper;
import kz.benomads.testproject4sp.model.Role;
import kz.benomads.testproject4sp.model.UserEntity;
import kz.benomads.testproject4sp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByUsername(userRegisterDto.getUsername())) {
            throw new UserAlreadyExistsException(
                String
                    .format("Login username=%s already exists", userRegisterDto.getUsername()));
        }
        if (!Objects.equals(userRegisterDto.getPassword(),
            userRegisterDto.getMatchingPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        Role roles = roleRepository.findByRoleName("USER");

        UserEntity user = new UserEntity();
user.setFullName(userRegisterDto.getFullName());
user.setUsername(userRegisterDto.getUsername());
user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
user.setEmail(userRegisterDto.getEmail());
user.setPhoneNumber(userRegisterDto.getPhoneNumber());
user.setRoles(Collections.singletonList(roles));
user.setAvatarUrl(userRegisterDto.getAvatarUrl());


//        UserEntity user = UserEntity.builder()
//            .fullName(userRegisterDto.getFullName())
//            .username(userRegisterDto.getUsername())
//            .password(passwordEncoder.encode(userRegisterDto.getPassword()))
//            .email(userRegisterDto.getEmail())
//            .phoneNumber(userRegisterDto.getPhoneNumber())
//            .roles(Collections.singletonList(roles))
//            .avatarUrl(userRegisterDto.getAvatarUrl())
//            .build();

        userRepository.save(user);

        return userDtoMapper.apply(user);
    }
}
