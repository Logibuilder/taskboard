package edu.taskboard.taskboard.controller;


import edu.taskboard.taskboard.dto.ApiResponse;
import edu.taskboard.taskboard.dto.LoginResponseDto;
import edu.taskboard.taskboard.dto.UserDTO;
import edu.taskboard.taskboard.exception.business.CodeFormatException;
import edu.taskboard.taskboard.exception.business.DuplicateResourceException;
import edu.taskboard.taskboard.exception.business.EmptyPasswordException;
import edu.taskboard.taskboard.model.User;
import edu.taskboard.taskboard.security.JwtService;
import edu.taskboard.taskboard.service.CustomUserDetailsService;
import edu.taskboard.taskboard.service.TokenRevokedService;
import edu.taskboard.taskboard.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    TokenRevokedService tokenRevokedService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Value("${jwt.secretKey}")
    private String secretKey;




    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody UserDTO userDTO) {
        log.info("Le mot de passe est : " + userDTO.password());
        if (userDTO.password() == null || userDTO.password().trim().isEmpty()) {
            throw  new EmptyPasswordException();
        }

        if ( userService.exist(userDTO.email())) {
            throw new DuplicateResourceException("Username", userDTO.email() );
        }

        User user = userDTO.toEntity();

        userService.save(user);

        ApiResponse<Void> response = new ApiResponse<>(201, "User created successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate")
    public ResponseEntity<ApiResponse<Void>> validate(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");
        if (code == null || !code.matches("\\d{6}")) {
            throw new CodeFormatException();
        }
        userService.activate(code);

        ApiResponse<Void> response = new  ApiResponse<>(200, "Account validated successfully", null);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> loginUser(@RequestBody UserDTO userDto) {
        LoginResponseDto loginResponseDto = userService.loginUSer(userDto.email(), userDto.password());

        ApiResponse<LoginResponseDto> response = new ApiResponse<LoginResponseDto>(200, "Login successful", loginResponseDto);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/logout")
    public  ResponseEntity<ApiResponse<Void>> logoutUser(@RequestParam("accessToken") String accessToken, @RequestParam("refreshToken") String refreshToken) {
        String accessTokenJti = jwtService.extractJtiFromJwt(accessToken);

        String refreshTokenJti = jwtService.extractJtiFromJwt(refreshToken);

        if (!tokenRevokedService.isTokenRevoked(accessTokenJti)) {
            tokenRevokedService.revokToken(accessTokenJti);
        }

        if (!tokenRevokedService.isTokenRevoked(refreshTokenJti)) {
            tokenRevokedService.revokToken(refreshTokenJti);
        }

        ApiResponse<Void> response = new ApiResponse<>(200, "Logout successful", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<LoginResponseDto>> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            String username = claims.getSubject();

            User user = (User) customUserDetailsService.loadUserByUsername(username);

            String newAccessToken = jwtService.generateAccessToken(user);

            LoginResponseDto loginResponseDto = new LoginResponseDto();

            loginResponseDto.setAccessToken(newAccessToken);
            loginResponseDto.setUser(UserDTO.fromEntity(user));
            loginResponseDto.setRefreshToken(refreshToken);

            ApiResponse<LoginResponseDto> response = new ApiResponse<>(200, "Refresh token successful", loginResponseDto);
            return ResponseEntity.ok(response);

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "Refresh token expired", null));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "Refresh token invalid", null));
        }

    }
}
