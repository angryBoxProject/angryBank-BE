package com.teamY.angryBox.service;

import com.teamY.angryBox.enums.OAuthProviderEnum;
import com.teamY.angryBox.utils.HeaderUtil;
import com.teamY.angryBox.vo.oauth.KakaoURL;
import com.teamY.angryBox.vo.oauth.OAuthURL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthService {
    private final RestTemplate restTemplate;

    private final KakaoURL kakaoURL;

    // 나중에 리턴 타입 바꾸기. 지금은 테스트용으로 String
    public String OAuthLogin(OAuthProviderEnum providerEnum, String code) {

        OAuthURL url = getURL(providerEnum);

        String accessToken = getAccessToken(url.getTokenURL(code));
        log.info("accessToken : " + accessToken);
        String userEmail = getUserEmail(url.getUserInfoUri(), accessToken, url.getContentType());

        return userEmail;
    }

    public String getUserEmail(String uri, String accessToken, String contentType) {

        HttpHeaders header = new HttpHeaders();
        header.add(HeaderUtil.HEADER_AUTHORIZATION,HeaderUtil.TOKEN_PREFIX + accessToken);
        header.setContentType(MediaType.valueOf(contentType));

        HttpEntity<?> entity = new HttpEntity<>(header);

        log.info("getUserInfo URI" + uri);

        ResponseEntity<Map> resultEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, Map.class);
        log.info("userinfo result : " + resultEntity.toString());
        // kakao_account 이것도 분기 만들어줘야됨
        Map<String, String> kakaoAccount = (Map<String, String>) resultEntity.getBody().get("kakao_account");

        log.info("" + kakaoAccount);

        return kakaoAccount.get("email");
    }
    public String getAccessToken(String uri) {
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);

        log.info("getAccessToken URI" + uri);
        ResponseEntity<Map> resultEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, Map.class);

        // 리프레쉬 토큰은 어떻게 받아오지?(구글)
        return (String) resultEntity.getBody().get("access_token");
    }

    private OAuthURL getURL(OAuthProviderEnum providerEnum) {

        switch(providerEnum) {
            case KAKAO:
                return kakaoURL;
            //case GOOGLE:
        }

        return null;
    }

}
