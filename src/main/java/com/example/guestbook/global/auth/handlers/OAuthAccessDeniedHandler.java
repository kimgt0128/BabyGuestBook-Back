package com.example.guestbook.global.auth.handlers;

import com.example.guestbook.global.auth.exception.OauthErrorCode;
import com.example.guestbook.global.error.exception.UnauthorizedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class OAuthAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        throw new UnauthorizedException(OauthErrorCode.INVALID_TOKEN);
    }
}
