package kz.benomads.testproject4sp.controller;

import jakarta.validation.Valid;
import kz.benomads.testproject4sp.dto.UserDto;
import kz.benomads.testproject4sp.dto.UserLoginDto;
import kz.benomads.testproject4sp.dto.UserRegisterDto;
import kz.benomads.testproject4sp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public RedirectView login(@RequestBody UserLoginDto userLoginDto) {
        UserDto userDto = authService.login(userLoginDto);
//     return ResponseEntity.ok(userDto);
        return new RedirectView("/api/v1/users" + "/" + userDto.getId());
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        UserDto userDto = authService.register(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

}
