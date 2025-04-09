package com.example.guestbook.global.auth.jwt;

import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.repository.MemberRepository;
import com.example.guestbook.global.auth.dto.TokenResponse;
import com.example.guestbook.global.auth.exception.OauthErrorCode;
import com.example.guestbook.global.auth.oauth.CustomUserDetailService;
import com.example.guestbook.global.auth.provider.OAuthUserImpl;
import com.example.guestbook.global.error.exception.BadRequestException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final CustomUserDetailService customUserDetailService;
    //  private final RedisTemplate<String, String> redisTemplate;

    /**
     * OAuth 사용자 정보로 토큰 생성 및 저장
     */
    public TokenResponse createTokens(OAuthUserImpl oauthUser) {
        Date now = new Date();
        String accessToken = jwtProvider.generateAccessToken(oauthUser, now);
        //String refreshToken = jwtProvider.generateRefreshToken(oauthUser,now);

        // 리프레시 토큰 저장
        // saveRefreshToken(oauthUser.getMember().getEmail(), refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                // .refreshToken(refreshToken)
                .build();
    }

    /**
     * 리프레시 토큰을 Redis에 저장
     */
//    private void saveRefreshToken(String email, String refreshToken) {
//        redisTemplate.opsForValue().set(
//                email,
//                refreshToken,
//                jwtProvider.getRefreshTokenExpiration(),
//                TimeUnit.MILLISECONDS
//        );
//        log.info("리프레시 토큰 저장 완료: {}", email);
//    }

    /**
     * 리프레시 토큰으로 새 액세스 토큰 발급
     */
/*
    public TokenResponse refreshAccessToken(String refreshToken) {
        // 리프레시 토큰 검증
        jwtProvider.validateToken(refreshToken);

        // 사용자 이메일 추출
        String email = jwtProvider.getEmail(refreshToken);

        // Redis에 저장된 리프레시 토큰 확인
        String savedRefreshToken = redisTemplate.opsForValue().get(email);
        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            throw new UnauthorizedException(OauthErrorCode.INVALID_TOKEN, "Invalid refresh token");
        }

        // 사용자 정보 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(OauthErrorCode.INVALID_REGISTERID, "User not found"));

        // 새 토큰 발급
        String newAccessToken = jwtProvider.generateAccessToken(createOAuthUserImpl(member),now);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken) // 기존 리프레시 토큰 유지
                .build();
    }
*/

//    /**
//     * 로그아웃 처리 (리프레시 토큰 삭제)
//     */
//    public void logout(String email) {
//        redisTemplate.delete(email);
//        log.info("로그아웃 처리 완료: {}", email);
//    }
    public Authentication getAuthentication(String token) {
        // 토큰에서 사용자 정보 추출
        Claims claims = jwtProvider.getPayload(token);

        // 권한 정보 추출 :: todo 어떻게 동작하는지 추가 공부해야 함
        Collection<SimpleGrantedAuthority> authorities = Stream.of(
                        String.valueOf(claims.get("role")).split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 로드
        UserDetails userDetails = customUserDetailService.loadUserByUsername(
                jwtProvider.getEmail(token));

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    /**
     * 토큰으로부터 사용자 정보 조회
     */
    public OAuthUserImpl getUserFromToken(String token) {
        jwtProvider.validateToken(token);
        Long userId = jwtProvider.getUserId(token);

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(OauthErrorCode.INVALID_REGISTERID, "User not found"));

        return createOAuthUserImpl(member);
    }

    /**
     * Member 엔티티로부터 OAuthUserImpl 객체 생성
     */
    private OAuthUserImpl createOAuthUserImpl(Member member) {
        Collection<GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", member.getId());
        attributes.put("email", member.getEmail());

        return OAuthUserImpl.of(member, attributes, authorities, "email");
    }

//    /**
//     * 토큰을 쿠키에 설정
//     */
//    public void setCookie(TokenResponse tokenResponse, HttpServletResponse response) {
//        Cookie accessTokenCookie = new Cookie("accessToken", tokenResponse.getAccessToken());
//        accessTokenCookie.setHttpOnly(true);
//        accessTokenCookie.setPath("/");
//        accessTokenCookie.setMaxAge((int) (jwtProvider.getAccessTokenExpiration() / 1000));
//
//        Cookie refreshTokenCookie = new Cookie("refreshToken", tokenResponse.getRefreshToken());
//        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setPath("/");
//        refreshTokenCookie.setMaxAge((int) (jwtProvider.getRefreshTokenExpiration() / 1000));
//
//        response.addCookie(accessTokenCookie);
//        response.addCookie(refreshTokenCookie);
//    }
}

