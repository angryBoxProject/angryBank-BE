package com.teamY.angryBox.vo.oauth;

import com.teamY.angryBox.service.OAuthService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static com.teamY.angryBox.utils.HeaderUtil.TOKEN_PREFIX;


@Configuration
public class KakaoURL implements OAuthURL{

    private String contentType = "application/x-www-form-urlencoded;charset=utf-8";

    @Value("${spring.security.oauth2.client.provider.kakao.tokenUri}")
    private String tokenUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.userInfoUri}")
    private String userInfoUri;

    @Value("${spring.security.oauth2.client.registration.kakao.clientId}")
    private String clientID;

    @Value("${spring.security.oauth2.client.registration.kakao.clientSecret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirectUri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.unLinkUri}")
    private String unLinkUri;

    private final String grantType = "authorization_code";

    @Override
    public String getTokenURL(String code) {
        return tokenUrl + "?grant_type=" + grantType
                + "&client_id=" + clientID + "&client_secret=" + clientSecret
                + "&redirect_uri=" + redirectUri + "&code=" + code;
    }

    @Override
    public String getUserInfoUri() {
        return userInfoUri;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String sendUnLinkURL() {
        return unLinkUri +
                "?Content-Type: application/x-www-form-urlencoded";
    }

}
