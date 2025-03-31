package com.example.guestbook.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "members")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String registerType;

    private boolean active;

    @Builder
    public Member(String name, String email, String registerType, boolean active) {
        this.name = name;
        this.email = email;
        this.registerType = registerType;
        this.active = active;
    }

    public static Member of(String name, String email, String registerType, boolean active) {
        return Member.builder()
                .name(name)
                .email(email)
                .registerType(registerType)
                .active(active)
                .build();
    }

}
