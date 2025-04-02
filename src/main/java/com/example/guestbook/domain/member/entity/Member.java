package com.example.guestbook.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Table(name = "members")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long socialId;
    private String nickname;
    private String email;
    private String registerType;

    @Enumerated(EnumType.STRING)
    private Role role; // 사용자 역할 (예: USER, ADMIN)


    @Builder
    public Member(String nickname, String email, String registerType, Long socialId, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.registerType = registerType;
        this.socialId = socialId;
        this.role = role;
    }

    public static Member of(String name, String email, String registerType, boolean active) {
        return Member.builder()
                .nickname(name)
                .email(email)
                .registerType(registerType)
                .build();
    }

}
