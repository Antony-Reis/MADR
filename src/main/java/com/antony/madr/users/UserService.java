package com.antony.madr.users;

import com.antony.madr.infra.exceptions.ConflictException;
import com.antony.madr.infra.exceptions.EExceptionsRolesTypes;
import com.antony.madr.infra.exceptions.NotFoundException;
import com.antony.madr.utils.RDefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository iUserRepository, PasswordEncoder passwordEncoder) {
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserRepository.findByUsername(username);
    }


    public RDefaultResponse registerUser(RUserRegisterDto body) throws ConflictException {
        if (iUserRepository.findByUsername(body.username()) != null) {
            throw new ConflictException(EExceptionsRolesTypes.User);}

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());

        iUserRepository.save(new UsersEntity(body.username(), encryptedPassword, body.role()));

        return new RDefaultResponse(HttpStatus.OK,"Successful register");
    }

    public RDefaultResponse deleteUser(String username) throws NotFoundException {
        try {
        UserDetails user = iUserRepository.findByUsername(username);
        iUserRepository.delete((UsersEntity) user);
            return new RDefaultResponse(HttpStatus.OK, "User updated");
        }
        catch (NotFoundException ex){
            throw new NotFoundException(EExceptionsRolesTypes.User);
        }

        }

    public RDefaultResponse patchPassworByUsername(String username ,RUserDto body) throws NotFoundException{
        try{
        UsersEntity user = (UsersEntity) iUserRepository.findByUsername(username);
            if (body.password() != null){
                String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
                user.setPassword(encryptedPassword);
            }
            iUserRepository.save(user);
            return new RDefaultResponse(HttpStatus.OK, "Password updated");}

        catch (NotFoundException ex){
            throw new NotFoundException(EExceptionsRolesTypes.User);
        }
    }

}
