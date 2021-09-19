package com.oauth2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oauth2.model.AuthUser;

@Repository
public interface UserRepository extends JpaRepository<AuthUser, Long> {
	
    Optional<AuthUser> findByUserName(String username);

    Optional<AuthUser> findByUserNameOrEmail(String username, String email);
}
