package com.funlearn.server.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private static String authToken;

    public AuthenticationService(
            @Value("${spring.security.api-secret}")
            String authToken
    ) {
        AuthenticationService.authToken = authToken;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String authTokenHeaderName = "X-API-KEY";
        String apiKey = request.getHeader(authTokenHeaderName);
        if (apiKey == null || !apiKey.equals(authToken)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}