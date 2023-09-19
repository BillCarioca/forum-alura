package com.forumalura.controllers;

import com.forumalura.services.impl.AuthorizationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {
    final AuthenticationManager authenticationManager;
    final AuthorizationService authorizationService;

    public AuthorizationController(AuthenticationManager authenticationManager,
                                   AuthorizationService authorizationService) {
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
    }


}
