package com.ktmmobile.msf.commons.common.data.entity.user;


import com.ktmmobile.msf.commons.common.data.type.UserType;

/**
 * 전체 사용자(관리자 및 일반 사용자) 엔터티
 */
public interface MsfUser {

    String getId();

    String getName();

    UserType getUserType();
}
