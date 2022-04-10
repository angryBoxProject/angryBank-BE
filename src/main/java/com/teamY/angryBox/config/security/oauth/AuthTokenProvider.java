package com.teamY.angryBox.config.security.oauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class AuthTokenProvider {
    //private final Key key;
    private static String key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(String secret) {
        // String 으로 전달 된 secret 을 바이트로 변환 후 Key 타입으로 다시 변환해서 넣어줌
        //this.key = Keys.hmacShaKeyFor(secret.getBytes());
        //this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        log.info("secret : " + secret);
        this.key = Base64.getEncoder().encodeToString(secret.getBytes());
        //this.key = secret;
        log.info("key : " + this.key);
    }

    public AuthToken createAuthToken(String id, Date expiry, Map<String, Object> claims) {
        return new AuthToken(id, expiry, key, claims);
    }

    public AuthToken createAuthToken(String id, String role, Date expiry) {
        return new AuthToken(id, role, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken) {

        if(authToken.validate()) {

            Claims claims = authToken.getTokenClaims();
            log.info("클레임스 : " + claims.get(AUTHORITIES_KEY).toString());
//            Collection<? extends GrantedAuthority> authorities =
//                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
//                            .map(SimpleGrantedAuthority::new)
//                            .collect(Collectors.toList());
            List<SimpleGrantedAuthority> authorities =
                    Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

//            Stream<SimpleGrantedAuthority> authorities =
//                    Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new);
//            authorities.forEach(role -> System.out.println("롤롤 : " + role));

//            log.debug("claims subject := [{}]", claims.getSubject());
            User principal = new User((String) claims.get("email"), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);

        } else {
            //throw new TokenValidFailedException();
            throw new RuntimeException("Failed to generate Token.");
        }
    }
}
