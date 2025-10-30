package com.example.Portfolio.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final MongoTemplate temp;

    @Override
    public void logout(
            HttpServletRequest request
            , HttpServletResponse response
            , Authentication authentication) {
        String authHeader= request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return;
        }
        jwt=authHeader.substring(7);
        Query q=new Query();
        q.addCriteria(Criteria.where("token").is(jwt));
        Token t = temp.findOne(q, Token.class);
        if (t != null) {
            t.setRevoked(true);
            temp.save(t , "tokens");
        }
    }
}