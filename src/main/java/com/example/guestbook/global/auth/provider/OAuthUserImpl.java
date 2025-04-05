package com.example.guestbook.global.auth.provider;

import com.example.guestbook.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class OAuthUserImpl implements OAuth2User {
    private final Member member;
    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String nameAttributeKey;


    private OAuthUserImpl(Member member,
                          Map<String, Object> attributes,
                          Collection<? extends GrantedAuthority> authorities,
                          String nameAttributeKey) {
        this.member = member;
        this.attributes = attributes;
        this.authorities = authorities;
        this.nameAttributeKey = nameAttributeKey;
    }

    // 팩토리 메서드 추가
    public static OAuthUserImpl of(Member member, Map<String, Object> attributes,
                                   Collection<? extends GrantedAuthority> authorities,
                                   String nameAttributeKey) {
        return new OAuthUserImpl(member, attributes, authorities, nameAttributeKey);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return attributes.get(nameAttributeKey).toString();
    }

    // UserDetails 관련 메서드 모두 제거
    // getPassword(), getUsername(), isAccountNonExpired() 등
}
