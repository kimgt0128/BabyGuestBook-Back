package com.example.guestbook.global.auth.dto;

import com.example.guestbook.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

public class OauthUserImpl extends DefaultOAuth2User implements UserDetails {
    // 소셜 일반 로그인 통합
    private final Member member;

    public OauthUserImpl(Collection<? extends GrantedAuthority> authorities,
                         Map<String, Object> attributes,
                         String nameAttributeKey,
                         Member member) {
        super(authorities, attributes, nameAttributeKey);
        this.member = member;
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
        return member.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return member.isActive();
    }

}
