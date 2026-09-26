package com.antony.madr.users;


import com.antony.madr.infra.exceptions.ConflictException;
import com.antony.madr.infra.security.AuthService;
import com.antony.madr.utils.RDefaultResponse;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@Validated
public class UserController {
    UserService userService;
    AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public RDefaultResponse loginByUsername(@RequestBody @Valid RUserDto body){
    return authService.loginByUsername(body);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public RDefaultResponse registerUser(@RequestBody @Valid RUserRegisterDto body) throws ConflictException {
    return userService.registerUser(body);
    }


}
