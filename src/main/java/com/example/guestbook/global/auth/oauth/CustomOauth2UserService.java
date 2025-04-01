package com.example.guestbook.global.auth.oauth;

import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.repository.MemberRepository;
import com.example.guestbook.global.auth.dto.OauthAttributes;
import com.example.guestbook.global.auth.dto.OauthUserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User); // 검증

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //ClientRegistration에서 자세한 변수 확인 가능
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // 정보를 뽑아내려면 필요한 변수

        OauthAttributes oauthAttributes = OauthAttributes.of(attributes, registrationId, userNameAttributeName);
        // 회원 정보는 저장하고
        Member member = getOrSave(oauthAttributes);
        // oauthuser 객체 생성
        return new OauthUserImpl(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes,
                oauthAttributes.getNameAttributeKey(),
                member
        );
    }
    private Member getOrSave(OauthAttributes oauthAttributes) {
        return memberRepository.findByEmail(oauthAttributes.getEmail())
                .orElseGet(()->memberRepository.save(oauthAttributes.toEntity()));
    }
}
