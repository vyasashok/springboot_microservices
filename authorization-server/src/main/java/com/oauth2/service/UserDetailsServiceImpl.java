package com.oauth2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oauth2.model.AuthUser;
import com.oauth2.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // it will be called at access token generation time
        Optional<AuthUser> optUser = userRepository.findByUserName(userName);
        if (optUser.isPresent()) {
            AuthUser user = optUser.get();
            List<GrantedAuthority> authorities = user.getRoles()
                    .stream().map(roles -> new SimpleGrantedAuthority(roles.getRoleName()))
                    .collect(Collectors.toList());
            return new User(user.getUserName(), user.getPassword(), authorities);
        }
        throw new RuntimeException("user not exist");
	}

}
