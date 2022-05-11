package com.teamY.angryBox.vo.oauth;

public abstract interface OAuthURL {
    public abstract String getTokenURL(String code);
    public abstract String getUserInfoUri();
    public abstract String getContentType();
    public abstract String sendUnLinkURL(String oauthId);
}
