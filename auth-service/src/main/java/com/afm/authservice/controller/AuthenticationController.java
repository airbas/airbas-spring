package com.afm.authservice.controller;

import com.afm.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import model.auth.AuthProvider;
import model.auth.ERole;
import model.auth.UserBas;
import model.utils.LoginRequest;
import model.utils.UserPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @PostMapping("/signup")
    public UserPayload signUp(@RequestBody UserPayload payload)  {
        LoginRequest credential = new LoginRequest(payload.getEmail(), payload.getPassword());
        authenticationService.createUser(credential, AuthProvider.local, ERole.ROLE_USER);
        payload.setPassword("");
        logger.info("User created : " + payload.getEmail());
        return payload;
    }


    @PostMapping("/login")
    public UserBas logIn(@RequestBody LoginRequest loginReq) {
        UserBas user = authenticationService.authenticateUser(loginReq);
        logger.info("User login : " + user.getEmail());
        return user;
    }

    // @TODO to manage
    @GetMapping("/users")
    public List<UserBas> users() {
        return authenticationService.findAll();
    }


    @GetMapping("/users/delete/{email}")
    public UserBas deleteUser(@PathVariable String email){
        UserBas user = authenticationService.findUser(email);
        authenticationService.deleteUser(user);
        return user;
    }

}
