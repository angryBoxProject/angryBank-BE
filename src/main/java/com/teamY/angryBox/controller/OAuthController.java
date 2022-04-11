package com.teamY.angryBox.controller;

import com.teamY.angryBox.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Slf4j
@RequestMapping("/oauth2/")
@RequiredArgsConstructor
@RestController
public class OAuthController {

    private final RestTemplate restTemplate;
    @PostMapping("kakao")
    public String kakaoLogin(@RequestParam String code){
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);

        StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/token?");
//        url.append("grant_type=authorization_code");
//        url.append("&client_id=c23e69e54e1be94497eeed61a74f451e"); // TODO REST_API_KEY 입력
//        url.append("&client_secret=FiTXFMhA3ChNpRtbAwqPsKVznAhk5MNx");
//        url.append("&redirect_uri=http://localhost:8080/login/oauth2/code/kakao"); // TODO 인가코드 받은 redirect_uri 입력
//        url.append("&code=" + code);
//
        URI uri = URI.create(url.toString());
//
//        ResponseEntity<Map> resultEntity = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
//
//        log.info(resultEntity.toString());
//        log.info(resultEntity.getBody().toString());
//        resultEntity.getBody().get("access_token");



        header.add(HeaderUtil.HEADER_AUTHORIZATION, "Bearer " + "_cBDqRlK9KFwuXEs9h_f8pfsvjIiFciETJC4BAo9dGkAAAGAGW8xig");
        header.setContentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=utf-8"));
        StringBuffer url2 = new StringBuffer("https://kapi.kakao.com/v2/user/me");
        uri = URI.create(url2.toString());
        ResponseEntity<Map> resultEntity = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
        log.info(resultEntity.toString());
        log.info(resultEntity.getBody().toString());
        return code;
    }

    @PostMapping("google")
    public ResponseEntity googleLogin(){

        return null;
    }
}
