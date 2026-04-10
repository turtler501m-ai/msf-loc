package com.ktmmobile.msf.commons.common.data.entity.member;


import com.ktmmobile.msf.commons.common.data.type.MemberRole;

/**
 * 전체 멤버(FO 사용자 및 BO 관리자 포함) 엔터티
 */
public interface Member {

    int getKey();

    MemberRole getMemberRole();

    default void setMemberRole(MemberRole memberRole) {

    }
}
