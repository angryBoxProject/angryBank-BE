package com.teamY.angryBox.config.security.oauth.handler;

import com.teamY.angryBox.utils.HeaderUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader(HeaderUtil.HEADER_AUTHORIZATION, "daksdjflaksjdfklajsdklfjasklfdj");
        response.setContentType("application/json;charset=UTF-8");

        getRedirectStrategy().sendRedirect(request, response, "/");
//        Success<TokenDto> success = new Success<>(jwt);
//        response.getWriter().write(objectMapper.writeValueAsString(success));
    }
}
