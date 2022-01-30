package com.jego.pokemon.securityservice.repository;

import com.jego.pokemon.securityservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsernameOrEmail(String username, String email);
}
