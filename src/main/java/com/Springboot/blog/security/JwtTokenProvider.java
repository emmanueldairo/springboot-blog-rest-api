package com.Springboot.blog.security;

import com.Springboot.blog.exception.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private String jwtExpirationInMs;

    //generate token
    public String generateToken(Authentication authentication){
        String userName = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return token;
    }

    // method to get username from the token
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }catch (ExpiredJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        }catch (UnsupportedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }

    }
}
