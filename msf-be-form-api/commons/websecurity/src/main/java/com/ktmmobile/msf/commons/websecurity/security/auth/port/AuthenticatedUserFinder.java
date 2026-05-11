package com.ktmmobile.msf.commons.websecurity.security.auth.port;

import java.util.Optional;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.common.data.type.UserType;

public interface AuthenticatedUserFinder {

    Optional<MsfUser> findUser(UserType userType, String userId);
}
