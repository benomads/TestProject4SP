package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.repository.RoleRepository;
import kz.benomads.testproject4sp.repository.UserRepository;
import kz.benomads.testproject4sp.dto.UserDto;
import kz.benomads.testproject4sp.dto.UserLoginDto;
import kz.benomads.testproject4sp.dto.UserRegisterDto;
import kz.benomads.testproject4sp.exception.UserAlreadyExistsException;
import kz.benomads.testproject4sp.exception.UserNotFoundException;
import kz.benomads.testproject4sp.mapper.UserDtoMapper;
import kz.benomads.testproject4sp.model.Role;
import kz.benomads.testproject4sp.model.UserEntity;
import kz.benomads.testproject4sp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, UserDtoMapper userDtoMapper, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDtoMapper = userDtoMapper;
        this.authenticationManager = authenticationManager;
    }
    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        if (!userRepository.existsByUsername(userLoginDto.getUsername())) {
            throw new UserNotFoundException(
                String.format("User %s not found", userLoginDto.getUsername()));
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(),
                userLoginDto.getPassword()
            )
        );
        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid username or password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity user = userRepository.findByUsername(userLoginDto.getUsername())
            .orElseThrow(() ->
                new UserNotFoundException(
                    String.format("User %s not found", userLoginDto.getUsername())));

        return userDtoMapper.apply(user);
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

        UserEntity user = UserEntity.builder()
            .fullName(userRegisterDto.getFullName())
            .username(userRegisterDto.getUsername())
            .password(passwordEncoder.encode(userRegisterDto.getPassword()))
            .email(userRegisterDto.getEmail())
            .phoneNumber(userRegisterDto.getPhoneNumber())
            .roles(Collections.singletonList(roles))
            .avatarUrl(userRegisterDto.getAvatarUrl())
            .build();

        userRepository.save(user);

        return userDtoMapper.apply(user);
    }
}
