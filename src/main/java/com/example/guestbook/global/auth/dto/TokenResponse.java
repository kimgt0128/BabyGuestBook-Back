package com.example.guestbook.global.auth.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
