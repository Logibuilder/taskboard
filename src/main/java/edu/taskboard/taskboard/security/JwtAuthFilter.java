package edu.taskboard.taskboard.security;

import edu.taskboard.taskboard.model.User;
import edu.taskboard.taskboard.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtAuFilter extends OncePerRequestFilter {

    UserService userService;
    JWTService jwtService;

    public JWTFilter(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        Boolean isTokenExpired = true;


        final String   authorisation = request.getHeader("Authorization");
        if (authorisation != null && authorisation.startsWith("Bearer ")) {
            token = authorisation.substring(7);
            isTokenExpired = jwtService.isTokenExpired(token);
            username = jwtService.getUserName(token);
        }

        if (!isTokenExpired &&  username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetail  =  userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
