package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.commons.common.data.type.UserType;

public record LoginTwoFactorRequired(
    String loginSessionId,
    LocalDateTime expiresAt,
    String userId,
    UserType userType,
    String userName,
    String phoneNumber,
    Map<String, Object> attributes,
    List<LoginRequiredAction> requiredActions
) implements LoginResult {

    public LoginTwoFactorRequired {
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
        requiredActions = requiredActions == null ? new ArrayList<>() : new ArrayList<>(requiredActions);
    }
}
