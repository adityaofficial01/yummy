package com.aadi.yummy.Security;

import com.aadi.yummy.dto.UserDto;
import com.aadi.yummy.entities.User;
import com.aadi.yummy.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String autherization = request.getHeader("Autherization");
        if(autherization != null && autherization.startsWith("Bearer ")) {
            String token = autherization.substring(7);
            if(jwtService.validateToken(token)) {
                String userName = jwtService.getUsername(token);
                UserDto user = userService.getUserByName(userName);

                if(SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null);
                    authenticationToken.setDetails(authenticationToken.getDetails());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }
        }
        filterChain.doFilter(request, response);
    }
}
