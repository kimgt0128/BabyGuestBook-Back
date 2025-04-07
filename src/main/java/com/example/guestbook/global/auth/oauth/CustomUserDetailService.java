package com.example.guestbook.global.auth.oauth;

import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.exception.MemberErrorCode;
import com.example.guestbook.domain.member.repository.MemberRepository;
import com.example.guestbook.global.auth.provider.OAuthUserImpl;
import com.example.guestbook.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(MemberErrorCode.INVALID_EMAIL));

        return OAuthUserImpl.of(
                member,
                createAttributes(member),
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                "email"
        );
    }

    private Map<String, Object> createAttributes(Member member) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("socialId", member.getSocialId());
        attributes.put("nickname", member.getNickname());
        attributes.put("email", member.getEmail());
        attributes.put("registerType", member.getRegisterType());
        return attributes;
    }
}
