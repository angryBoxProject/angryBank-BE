package com.teamY.angryBox.config.security.oauth;

import com.teamY.angryBox.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {

    @Getter
    private final String token;
    private final String key;

    private static final String AUTHORITIES_KEY = "role";

    AuthToken(String id, Date expiry, String key, Map<String, Object> claims) {
        this.key = key;
        this.token = createAuthToken(id, expiry, claims);
    }

    AuthToken(String id, String role, Date expiry, String key) {
        this.key = key;
        this.token = createAuthToken(id, role, expiry);
    }

    private String createAuthToken(String id, Date expiry, Map<String, Object> claims) {

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .claim(AUTHORITIES_KEY, "ROLE_USER")
                .setSubject("accessToken")
                .signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(expiry)
                .compact();
    }

    private String createAuthToken(String id, String role, Date expiry) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate(ServletRequest request) {
        try{
            return this.getTokenClaims() != null;
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
            request.setAttribute("error", ErrorCode.TOKEN_MALFORMED_JWT);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            request.setAttribute("error", ErrorCode.TOKEN_MALFORMED_JWT);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            request.setAttribute("error", ErrorCode.TOKEN_EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            request.setAttribute("error",ErrorCode.TOKEN_UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            request.setAttribute("error", ErrorCode.TOKEN_ILLEGAL_JWT);
        } catch(RuntimeException e) {
            log.info("no token");
            request.setAttribute("error", ErrorCode.TOKEN_NOT_FOUND);
        } catch (Exception e) {
           log.info("something wrong with token");
            request.setAttribute("error", ErrorCode.TOKEN_ILLEGAL_JWT);
        }
        return false;
    }

    public Claims getTokenClaims() throws Exception {
        if (token == null) {
            throw new RuntimeException("토큰이 존재하지 않음");
        }

        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

    }

    public Claims getExpiredTokenClaims() {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            return e.getClaims();
        }
        return null;
    }
}
