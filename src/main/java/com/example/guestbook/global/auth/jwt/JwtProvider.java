package com.example.guestbook.global.auth.jwt;

import com.example.guestbook.global.auth.exception.OauthErrorCode;
import com.example.guestbook.global.auth.provider.OAuthUserImpl;
import com.example.guestbook.global.error.exception.BadRequestException;
import com.example.guestbook.global.error.exception.UnauthorizedException;
import com.example.guestbook.global.error.exception.UnsupportedMediaTypeException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.nimbusds.oauth2.sdk.token.BearerTokenError.INVALID_TOKEN;

@Component
public class JwtProvider {
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.access-token.expired}")
    private Long accessTokenExpired;

    @Value("${jwt.refresh-token.expired}")
    private Long refreshTokenExpired;

    private SecretKey secretKey;
    private JwtParser jwtParser;
    //private final RedisTemplate<String, String> redisTemplate;

    // 나는 secret ket 정의해주지 않았는데 이 방식이 jwt 토큰 생성할 때 매번 해시함수 정해주지 않아도 돼서 좋은거 같음
    public JwtProvider(@Value("${jwt.secret-key}") String secretKeyString, RedisTemplate<String, String> redisTemplate) {
        // 안전한 키 생성 방식으로 변경
        this.secretKey = Jwts.SIG.HS256.key().build();

        this.jwtParser = Jwts.parser()
                .verifyWith(this.secretKey)
                .build();
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        if(StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            token = token.substring(BEARER_PREFIX.length());
        }

        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new BadRequestException(OauthErrorCode.INVALID_TOKEN, "토큰 검증 실패1: " + e); // 잘못된 서명 또는 형식 오류
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(OauthErrorCode.EXPIRED_TOKEN, "토큰 검증 실패2: " + e); // 만료된 토큰
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedMediaTypeException(OauthErrorCode.UNSUPPORTED_TOKEN, "토큰 검증 실패3: " + e); // 지원되지 않는 토큰 형식
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(OauthErrorCode.INVALID_TOKEN, "토큰 검증 실패4: " + e); // 잘못된 토큰 값
        }
    }
    // 예외를 따로 다 정해주니까 조금 번거로운듯 차라리 customException이였으면 throw new customeException 하나로 해결 간으

    public String generateAccessToken(OAuthUserImpl oauthUser, Date now) {
        // 권한 정보를 문자열로 변환
        String authorities = oauthUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(oauthUser.getMember().getEmail())
                .claim("id", oauthUser.getMember().getId())
                .claim("username",oauthUser.getName())
                .claim("role",authorities)
                .issuedAt(now)
                .expiration(new Date(now.getTime()+accessTokenExpired))
                .signWith(secretKey)
                .compact();
    }

    // todo :: 일단 만들어두기는 했는데 refresh는 사용안하고 accessToken으로만 이용할 예정 시간남으면 refresh 추가 작업
/*    public String generateRefreshToken(OAuthUserImpl oauthUser, Date now) {
        return Jwts.builder()
                .subject(oauthUser.getMember().getEmail())
                .issuedAt(now)
                .expiration(new Date(now.getTime()+refreshTokenExpired))
                .signWith(secretKey)
                .compact();
    }*/

    public Long getAccessTokenExpiration() {
        return accessTokenExpired;
    }

    public Long getRefreshTokenExpiration() {
        return refreshTokenExpired;
    }

    public Claims getPayload(String token) {
        return jwtParser
                .parseSignedClaims(token) // 이 하나의 과정으로 토큰 형식 검사,서명 검사,만료 여부 확인 가능
                .getPayload();
    }

    public String getEmail(String token) {
        return getPayload(token).getSubject();
    }

    // 개별 메서드 추출
    public Long getUserId(String token) {
        return getPayload(token).get("id", Long.class);
    }

    public String getUsername(String token) {
        return getPayload(token).get("username", String.class);
    }

    public String getAuthorities(String token) {
        return getPayload(token).get("role", String.class);
    }

}
