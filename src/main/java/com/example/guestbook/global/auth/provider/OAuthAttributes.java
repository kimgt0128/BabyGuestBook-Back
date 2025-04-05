package com.example.guestbook.global.auth.provider;

import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.entity.Role;
import com.example.guestbook.global.auth.exception.OauthErrorCode;
import com.example.guestbook.global.error.exception.BadRequestException;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private final Map<String,Object> attributes;
    private final String nameAttributeKey;
    private final String email;
    private final String nickname;
    private final String registerType; // kakao,google
    private final Long socialId; // 소셜 사용자 고유 id

    //여기서 엔티티로 변화시켜줌
    // 각각의 레지스터 타입에 따라 어트리뷰트 객체 넘겨주기

    @Builder
    public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey, String email, String nickname, String registerType, Long socialId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.email = email;
        this.nickname = nickname;
        this.registerType = registerType;
        this.socialId = socialId;
    }

    // registerId,
    public static OAuthAttributes of(String registerName,String userNameAttribute,Map<String, Object> attributes) { //여기서 받는건 다른거임
        if ("kakao".equals(registerName)) {
            return OAuthAttributes.ofKakao(attributes,userNameAttribute);
        }
        // todo :: 예외처리 추가
        throw new BadRequestException(OauthErrorCode.INVALID_REGISTERID);
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes, String nameAttribute) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuthAttributes.builder()
                .attributes(attributes)
                .email(profile.get("email").toString())
                .nickname(profile.get("nickname").toString())
                .registerType("kakao")
                .socialId(Long.valueOf(attributes.get("id").toString())) // 사용자 고유 id인거지 client-id랑은 다른 값
                //tostring으로 변환하고 넣어주는 이유는 널 예외를 방지하고, integer 값일때도 안전하게 long으로 변환가능해서
                .build();
    }
    // 저장할때 쓰려고
    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .registerType("kakao")
                .role(Role.USER)
                .socialId(socialId)
                .build();
    }
}