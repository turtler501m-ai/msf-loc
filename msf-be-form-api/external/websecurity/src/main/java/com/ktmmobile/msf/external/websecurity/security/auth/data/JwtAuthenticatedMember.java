package com.ktmmobile.msf.external.websecurity.security.auth.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtAuthenticatedMember {

    private final int userKey;
    private final String userId;
    private final String email;
}
