package com.antony.madr.infra.security;

import com.antony.madr.users.RUserDto;
import com.antony.madr.users.UsersEntity;
import com.antony.madr.utils.RDefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public RDefaultResponse loginByUsername(RUserDto body) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(body.username(), body.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((UsersEntity) auth.getPrincipal());

        return new RDefaultResponse(HttpStatus.ACCEPTED,token);
    }
}
