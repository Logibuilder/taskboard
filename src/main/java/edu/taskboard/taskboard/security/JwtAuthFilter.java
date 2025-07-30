package edu.taskboard.taskboard.security;

import edu.taskboard.taskboard.model.User;
import edu.taskboard.taskboard.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.SocketOption;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtService jwtService;


    @Autowired
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String jwt;
        final String username;


        final String authHeader = request.getHeader("authorization");
        log.debug("Auth Header reçu : {}", authHeader);


        if ((authHeader == null) || (!authHeader.startsWith("Bearer ")))  {
            logger.error("Invalid or missing Authorization header");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        log.debug("Token extrait : {}", jwt);


        username = jwtService.extractUserName(jwt);

        if (Objects.isNull(username)) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = (User) customUserDetailsService.loadUserByUsername(username);
        if (Objects.isNull(user)) {
            filterChain.doFilter(request, response);
            return;
        }

        final boolean isExpired = jwtService.isTokenExpired(jwt);
        System.out.println("isExpired : " + isExpired);
        if (isExpired) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
