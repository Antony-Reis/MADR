package com.antony.madr.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserRepository extends JpaRepository<UsersEntity, Integer> {
    UserDetails findByEmail(String email);
}
