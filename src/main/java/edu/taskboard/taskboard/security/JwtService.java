package edu.taskboard.taskboard.security;

import edu.taskboard.taskboard.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refreshTokenExpiration}")
    private Long jwtRefreshTokenExpiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    //accessToken
    public String generateAccessToken(User user) {

        //claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("jwt", generateUniqId());
        //user

        //tokenExp

        return buildTOken(user, claims, jwtExpiration);
    }

    //refreshToken
    public String generateRefreshToken(User user) {

        //claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("jwt", generateUniqId());
        //user

        //tokenExp

        return buildTOken(user, claims, jwtRefreshTokenExpiration);
    }

    private String buildTOken(User user, Map<String, Object> claims, Long expiration) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claims(claims)
                .issuedAt(new java.util.Date(System.currentTimeMillis()))
                .expiration(Date.from(Instant.now().plus(expiration, ChronoUnit.MILLIS)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public static String  generateUniqId() {
        return UUID.randomUUID().toString();
    }


    public String extractUserName(String token) {
        return  extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllCalaims(token);
        return claims != null ? claimsResolver.apply(claims) : null;
    }


    private Claims extractAllCalaims(String token) {

        try {
            return Jwts.parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    public boolean isTokenExpired(String token) {
        Date date = extractExpirationDate(token);
        if (Objects.nonNull(date)) {
            return date.before(new Date());
        }

        return true;
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractJtiFromJwt(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);

            return jws.getBody().get("jwt", String.class);
        } catch (Exception e) {
            return null;
        }
    }

}
