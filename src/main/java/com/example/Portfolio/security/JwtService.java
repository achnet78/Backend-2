package com.example.Portfolio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private Long expiration;

    private Key getKey(){
        byte[] b= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(b);
    }

    private String buildToken(UserDetails us , Map<String , Object> claims ,Long expiration){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(us.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getKey())
                .compact();
    }
    public String generateToken(Map<String , Object> claims , UserDetails us){
        return buildToken(us,claims,expiration);
    }
    public String generateToken(UserDetails us){
        return generateToken(new HashMap<String , Object>() , us);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private  <T> T extractClaim(String token , Function<Claims , T> ext){
        Claims claims=extractAllClaims(token);
        return ext.apply(claims);
    }
    public String extractEmail(String token){
        return extractClaim(token , Claims::getSubject);
    }
    public Date getExpiration(String token){
        return extractClaim(token , Claims::getExpiration);
    }
    public boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
    public boolean isTokenValid(UserDetails us , String token){
        String username=us.getUsername();
        return username.equals(extractEmail(token))&& !isTokenExpired(token);

    }


}