package com.teamY.angryBox.config.security.oauth.repository;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.teamY.angryBox.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class OAuth2AuthorizationRequestBasedOnCookieRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public final static String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    public final static String REFRESH_TOKEN = "refresh_token";
    private final static int cookieExpireSeconds = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        log.info("OAuth2AuthorizationRequestBasedOnCookieRepository : loadAuthorizationRequest");
        return CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("OAuth2AuthorizationRequestBasedOnCookieRepository : saveAuthorizationRequest");
        log.info("request uri : " + request.getRequestURI());
        log.info("getAuthorizationUri : " + authorizationRequest.getAuthorizationUri());
        log.info("getAuthorizationRequestUri : " + authorizationRequest.getAuthorizationRequestUri());
        log.info("getRedirectUri : " + authorizationRequest.getRedirectUri());
        log.info("getAttributes : " + authorizationRequest.getAttributes());
        if (authorizationRequest == null) {
            CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            return;
        }

        // oauth2_auth_request ?????? ????????? ????????? ????????? authorizationRequest ??? ?????? ???????????? ???????????? ??? response ??? ??????
        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtil.serialize(authorizationRequest), cookieExpireSeconds);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);

        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            // redirect_uri ?????? ????????? ????????? ????????? ??????????????? uri(????????? yml??? ????????? ??????????????? url) ??? ?????? ???????????? ???????????? ??? ??????????????? ??????
            CookieUtil.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        log.info("OAuth2AuthorizationRequestBasedOnCookieRepository : removeAuthorizationRequest");
        OAuth2AuthorizationRequest temp = this.loadAuthorizationRequest(request);
        return temp;
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        log.info("OAuth2AuthorizationRequestBasedOnCookieRepository : removeAuthorizationRequest");
        //return this.loadAuthorizationRequest(request);
        OAuth2AuthorizationRequest temp = this.loadAuthorizationRequest(request);
        return temp;
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
    }
}
