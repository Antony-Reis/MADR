package com.antony.madr.users;


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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public RDefaultResponse loginByUsername(@RequestBody @Valid RUserDto body){
    return userService.loginByUsername(body);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public RDefaultResponse registerUser(@RequestBody @Valid RUserRegisterDto body) throws BadRequestException {
    return userService.registerUser(body);
    }


}
