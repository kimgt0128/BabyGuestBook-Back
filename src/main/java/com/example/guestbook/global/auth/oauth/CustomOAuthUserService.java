package com.example.guestbook.global.auth.oauth;

import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.repository.MemberRepository;
import com.example.guestbook.global.auth.provider.OAuthAttributes;
import com.example.guestbook.global.auth.provider.OAuthUserImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("OAuth2User attributes: {}", attributes);
        // 중복 코드 제거를 위한 변수 추출
        ClientRegistration clientRegistration = userRequest.getClientRegistration();

        String registrationId = clientRegistration.getRegistrationId(); //kakao
        log.info("OAuth2 registrationId: {}", registrationId);

        String userNameAttributeName = clientRegistration.getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        log.info("OAuth2 userNameAttributeName: {}", userNameAttributeName);

        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, attributes);
        Member member = getOrSave(oAuthAttributes);
        log.info("nameAttributeKet: {}",oAuthAttributes.getNameAttributeKey());
        log.info("User authenticated: {} ({})", member.getNickname(), member.getEmail());

        // 팩토리 메서드를 사용하여 객체 생성
        return OAuthUserImpl.of(
                member,
                oAuthAttributes.getAttributes(),
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                oAuthAttributes.getNameAttributeKey()
        );
    }

    private Member getOrSave(OAuthAttributes attributes) {
        return memberRepository.findByEmail(attributes.getEmail())
                .orElseGet(() -> memberRepository.save(attributes.toEntity()));
    }
}
