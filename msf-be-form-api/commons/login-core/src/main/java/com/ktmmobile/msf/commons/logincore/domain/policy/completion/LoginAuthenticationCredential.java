package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import com.ktmmobile.msf.commons.common.data.type.UserType;

public interface LoginAuthenticationCredential extends LoginCompletionCredential {

    UserType userType();
}
