package com.aadi.yummy.controller;


import com.aadi.yummy.Security.JwtService;
import com.aadi.yummy.dto.JwtResponse;
import com.aadi.yummy.dto.LoginRequest;
import com.aadi.yummy.dto.UserDto;
import com.aadi.yummy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginRequest loginRequest) {
        System.out.println("in login");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        authenticationManager.authenticate(authentication);
        String username = loginRequest.getEmail();
        System.out.println("username : " + username);
        String jwtToken = jwtService.generateToken(username);
        System.out.println("jwtToken : " + jwtToken);
        UserDto userDetails = userService.getUserByEmail(loginRequest.getEmail());

        JwtResponse build = JwtResponse.builder().token(jwtToken).build();
        return ResponseEntity.ok(build);
    }
}
