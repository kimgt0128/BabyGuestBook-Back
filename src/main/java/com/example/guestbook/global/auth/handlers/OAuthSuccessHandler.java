package com.example.guestbook.global.auth.handlers;

import com.example.guestbook.global.auth.dto.TokenResponse;
import com.example.guestbook.global.auth.jwt.JwtProvider;
import com.example.guestbook.global.auth.jwt.JwtService;
import com.example.guestbook.global.auth.provider.OAuthUserImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler { //인증성공핸들러

    @Value("${client.url}") //localhost:3000
    private String clientUrl;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Authentication에서 OAuth2User 추출
        OAuthUserImpl oauthUser = (OAuthUserImpl) authentication.getPrincipal(); //인증된 사용자 객체 반환

        TokenResponse tokenResponse =jwtService.createTokens(oauthUser);

        String redirectUrl = UriComponentsBuilder.fromUriString(clientUrl)
                .queryParam("accessToken", tokenResponse.getAccessToken())
                .queryParam("refreshToken", tokenResponse.getRefreshToken())
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }
}
