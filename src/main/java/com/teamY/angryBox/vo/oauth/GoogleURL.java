package com.teamY.angryBox.vo.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleURL implements OAuthURL {


    private String contentType = "application/x-www-form-urlencoded;charset=utf-8";

    @Value("${spring.security.oauth2.client.provider.google.tokenUri}")
    private String tokenUrl;

    @Value("${spring.security.oauth2.client.provider.google.userInfoUri}")
    private String userInfoUri;

    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String clientID;

    @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirectUri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.google.unLinkUri}")
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
                "?token=";
    }
}
