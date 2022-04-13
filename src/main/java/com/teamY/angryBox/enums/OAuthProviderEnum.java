package com.teamY.angryBox.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthProviderEnum {
    KAKAO(1, "kakao"),
    GOOGLE(2, "google");

    private final int index;
    private final String providerName;
}
