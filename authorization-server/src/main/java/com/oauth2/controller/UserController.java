package com.oauth2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.oauth2.controller.dto.UserDto;
import com.oauth2.model.AuthUser;
import com.oauth2.service.AuthUserService;

@RestController
@RequestMapping("/oauth/users")
public class UserController {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthUser register(@RequestBody UserDto userDto) {
        return authUserService.register(userDto);
    }

}
