package com.teamY.angryBox.config.security.oauth;

import com.teamY.angryBox.repository.MemberRepository;
import com.teamY.angryBox.service.MemberService;
import com.teamY.angryBox.vo.MemberVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
//@Component
public class AuthTokenProvider {
    //private final Key key;
    private static String key;
    private static final String AUTHORITIES_KEY = "role";

    //private final MemberService memberService;

    public AuthTokenProvider(String secret) { //, MemberService memberService
        // String 으로 전달 된 secret 을 바이트로 변환 후 Key 타입으로 다시 변환해서 넣어줌
        //this.key = Keys.hmacShaKeyFor(secret.getBytes());
        //this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        log.info("secret : " + secret);
        this.key = Base64.getEncoder().encodeToString(secret.getBytes());
        //this.key = secret;
        log.info("key : " + this.key);

        //this.memberService = memberService;
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

    public Authentication getAuthentication(AuthToken authToken) throws Exception {

        Claims claims = authToken.getTokenClaims();
        log.info("클레임스 : " + claims.get(AUTHORITIES_KEY).toString() + " " + (int) claims.get("memberId")/* + " " + (String) claims.get("email") + " " + (String)claims.get("nickname")*/);

        List<SimpleGrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        //User principal = new User((String) claims.get("email"), "", authorities);

        //log.info("   정보정보정보 : {}, {}, {}", (int) claims.get("id"),  (String) claims.get("email"), (String)claims.get("nickname"));
        MemberVO memberVO = new MemberVO((int) claims.get("memberId"), (String) claims.get("email"), (String)claims.get("nickname"));
        MemberPrincipal principal = MemberPrincipal.create(memberVO);

        return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);

    }


    public long getTokenExpire(String token) {
        //token = token.substring(7);
        Claims claims = Jwts
                .parser().setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.get("exp", Date.class);
        Date today = new Date();

        return expiration.getTime() - today.getTime();
    }
}
