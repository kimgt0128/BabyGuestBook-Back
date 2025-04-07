package com.example.guestbook.global.auth.handlers;

import com.example.guestbook.global.auth.exception.OauthErrorCode;
import com.example.guestbook.global.error.exception.BadRequestException;
import com.example.guestbook.global.error.exception.UnauthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 응답 상태 코드 설정 (401 Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        // 오류 응답 본문 생성
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", OauthErrorCode.INVALID_TOKEN.getCode());
        errorResponse.put("message", OauthErrorCode.INVALID_TOKEN.getMessage());

        // 응답 작성
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
