package com.teamY.angryBox.service;

import com.teamY.angryBox.config.properties.AppProperties;
import com.teamY.angryBox.config.security.oauth.AuthToken;
import com.teamY.angryBox.config.security.oauth.AuthTokenProvider;
import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.controller.MemberController;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.enums.OAuthProviderEnum;
import com.teamY.angryBox.repository.MemberRepository;
import com.teamY.angryBox.utils.HeaderUtil;
import com.teamY.angryBox.vo.MemberVO;
import com.teamY.angryBox.vo.oauth.GoogleURL;
import com.teamY.angryBox.vo.oauth.KakaoURL;
import com.teamY.angryBox.vo.oauth.OAuthURL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthService {
    private final RestTemplate restTemplate;
    private final KakaoURL kakaoURL;
    private final GoogleURL googleURL;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 나중에 리턴 타입 바꾸기. 지금은 테스트용으로 String
    public String OAuthLogin(OAuthProviderEnum providerEnum, String code) {

        OAuthURL url = getURL(providerEnum);

        String accessToken = getAccessToken(url.getTokenURL(code));
        log.info("accessToken : " + accessToken);

        Map<String, Object> userInfoMap = getUserInfo(url.getUserInfoUri(), accessToken, url.getContentType(), providerEnum);

        log.info("userInfoMap : " + userInfoMap.toString());

        String userEmail = (String) userInfoMap.get("email");
        String userNickname = (String) userInfoMap.get("nickname");

        if(userEmail == null) {
            userEmail = userInfoMap.get("oauthId") + "@angrybox.link";
        }

        if(memberRepository.findByEmail(userEmail) == null) {

            log.info("회원 가입 해야됨");

            //UUID uuidPassword = UUID.randomUUID();
            //String password = "!" + providerEnum.getProviderName() + uuidPassword;
            String password = bCryptPasswordEncoder.encode("password");

            MemberVO member = new MemberVO(userEmail, userNickname, password, providerEnum.getProviderName());
            log.info(member.toString());

            memberRepository.insertMember(member);
        }

        return userEmail;
    }

    public Map<String, Object> getUserInfo(String uri, String accessToken, String contentType, OAuthProviderEnum providerEnum) {

        HttpHeaders header = new HttpHeaders();
        header.add(HeaderUtil.HEADER_AUTHORIZATION,HeaderUtil.TOKEN_PREFIX + accessToken);
        header.setContentType(MediaType.valueOf(contentType));

        HttpEntity<?> entity = new HttpEntity<>(header);


        ResponseEntity<Map> resultEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, Map.class);
        log.info("userinfo result : " + resultEntity.toString());


        Map<String, Object> userInfo = new HashMap<>();

        switch(providerEnum) {
            case KAKAO:
                getKakaoUserInfo((Map<String, Object>) resultEntity.getBody(), userInfo);
                break;
            case GOOGLE:
                getGoogleUserInfo((Map<String, Object>) resultEntity.getBody(), userInfo);
                break;
        }

        log.info("userInfo : " + userInfo.toString());

        return userInfo;
    }

    private void getKakaoUserInfo(Map<String, Object> kakaoAccount, Map<String, Object> userInfo) {

        userInfo.put("email", ((Map<String, String>)kakaoAccount.get("kakao_account")).get("email"));
        userInfo.put("nickname", ((Map<String, String>) kakaoAccount.get("properties")).get("nickname"));
        userInfo.put("oauthId", kakaoAccount.get("id"));

    }

    private void getGoogleUserInfo(Map<String, Object> googleAccount, Map<String, Object> userInfo) {

        userInfo.put("email", googleAccount.get("email"));
        userInfo.put("nickname", googleAccount.get("name"));
        userInfo.put("oauthId", googleAccount.get("id"));

    }

    public String getAccessToken(String uri) {
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);

        log.info("getAccessToken URI" + uri);
        ResponseEntity<Map> resultEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, Map.class);

        log.info("get token response body : " + resultEntity.getBody().toString());
        // 리프레쉬 토큰은 어떻게 받아오지?(구글)
        return (String) resultEntity.getBody().get("access_token");
    }

    private OAuthURL getURL(OAuthProviderEnum providerEnum) {

        switch(providerEnum) {
            case KAKAO:
                return kakaoURL;
            case GOOGLE:
                return googleURL;
        }

        return null;
    }

}
