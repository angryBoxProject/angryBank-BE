package com.teamY.angryBox.controller;

import com.teamY.angryBox.dto.LogInDTO;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.enums.OAuthProviderEnum;
import com.teamY.angryBox.service.MemberService;
import com.teamY.angryBox.service.OAuthService;
import com.teamY.angryBox.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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

    private final OAuthService oAuthService;
    private final MemberService memberService;

    private LogInDTO makeOAuthLoginDTO(String code){
        return new LogInDTO(oAuthService.OAuthLogin(OAuthProviderEnum.KAKAO, code), "password");
    }
    @PostMapping("kakao")
    public ResponseEntity<ResponseDataMessage> kakaoLogin(@RequestParam String code) {

        LogInDTO logInDTO = makeOAuthLoginDTO(code);
        log.info("auth logindto : " + logInDTO.toString());

        Map<String, Object> data = memberService.OAuthLogin(logInDTO);
        log.info("oauth jwt : " + data.get("jwt"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HeaderUtil.HEADER_AUTHORIZATION, HeaderUtil.TOKEN_PREFIX + data.get("jwt"));

        return new ResponseEntity<>(new ResponseDataMessage(true, "로그인 성공", "", data), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("google")
    public String googleLogin(@RequestParam String code){
//        HttpHeaders header = new HttpHeaders();
//        HttpEntity<?> entity = new HttpEntity<>(header);
//
//
//        StringBuffer url = new StringBuffer("https://oauth2.googleapis.com//token?");
//        url.append("Content-Type=application/x-www-form-urlencoded");
//        url.append("&code=" + code);
//        url.append("&client_id=870985780128-8qndd8es3ii98mud67bmr2c7h9ri27k0.apps.googleusercontent.com"); // TODO REST_API_KEY 입력
//        url.append("&client_secret=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");
//        url.append("&redirect_uri=http://localhost:8080/login/oauth2/code/google"); // TODO 인가코드 받은 redirect_uri 입력
//        url.append("&grant_type=authorization_code");
//
//        URI uri = URI.create(url.toString());
//
//        ResponseEntity<Map> resultEntity = restTemplate.exchange(uri.toString(), HttpMethod.POST, entity, Map.class);
//
//        String oauthAccessToken = (String) resultEntity.getBody().get("access_token");
//
//        header.add(HeaderUtil.HEADER_AUTHORIZATION, HeaderUtil.TOKEN_PREFIX  + oauthAccessToken);
//
////        StringBuffer url2 = new StringBuffer("https://www.googleapis.com/drive/v2/files");
//        StringBuffer url2 = new StringBuffer("https://www.googleapis.com/oauth2/v2/userinfo");
//        uri = URI.create(url2.toString());
//        ResponseEntity<Map> resultEntity2 = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
//        String email = (String) resultEntity2.getBody().get("email");
//        log.info("email : " +  email);
//
//        return email;
        return null;
    }

}
