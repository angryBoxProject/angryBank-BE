package com.teamY.angryBox.utils;

import javax.servlet.http.HttpServletRequest;

public class HeaderUtil {
    public final static String HEADER_AUTHORIZATION = "authorization";
    public final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }

        return headerValue;
    }
}
