package com.antony.madr.users;

import com.antony.madr.infra.security.TokenService;
import com.antony.madr.utils.RDefaultResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final IUserRepository iUserRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserService(IUserRepository iUserRepository, @Lazy AuthenticationManager authenticationManager, TokenService tokenService) {
        this.iUserRepository = iUserRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserRepository.findByUsername(username);
    }


    public RDefaultResponse loginByUsername(RUserDto body){
        var usernamePassword = new UsernamePasswordAuthenticationToken(body.username(), body.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((UsersEntity) auth.getPrincipal());

        return new RDefaultResponse(token);
    }

    public RDefaultResponse registerUser(RUserRegisterDto body) throws BadRequestException {
        if (iUserRepository.findByUsername(body.username()) != null) {
            throw new BadRequestException("User alread exits");}

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());

        iUserRepository.save(new UsersEntity(body.username(), encryptedPassword, body.role()));

        return new RDefaultResponse("Successful register");
    }
}
