package com.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oauth2.model.AuthRole;

@Repository
public interface UserRoleRepository extends JpaRepository<AuthRole, Long>{
	 AuthRole findByRoleNameContaining(String roleName);
}
