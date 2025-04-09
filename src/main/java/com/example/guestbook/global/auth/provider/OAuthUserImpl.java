package com.example.guestbook.global.auth.provider;

import com.example.guestbook.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class OAuthUserImpl extends DefaultOAuth2User implements UserDetails {
    private final Member member;
    private final String nameAttributeKey;

    private OAuthUserImpl(Member member,
                          Map<String, Object> attributes,
                          Collection<? extends GrantedAuthority> authorities,
                          String nameAttributeKey) {
        super(authorities, attributes, nameAttributeKey);
        this.member = member;
        this.nameAttributeKey = nameAttributeKey;
    }

    // 팩토리 메서드 추가
    public static OAuthUserImpl of(Member member, Map<String, Object> attributes,
                                   Collection<? extends GrantedAuthority> authorities,
                                   String nameAttributeKey) {
        return new OAuthUserImpl(member, attributes, authorities, nameAttributeKey);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return member.getId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Member getMember() {
        return member;
    }
}
