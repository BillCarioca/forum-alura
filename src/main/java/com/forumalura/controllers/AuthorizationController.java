package com.forumalura.controllers;

import com.forumalura.infra.security.TokenService;
import com.forumalura.services.impl.AuthorizationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {
    final AuthenticationManager authenticationManager;
    final AuthorizationService authorizationService;
    final TokenService tokenService;

    public AuthorizationController(AuthenticationManager authenticationManager,
                                   AuthorizationService authorizationService,
                                   TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenService = tokenService;
    }


}
