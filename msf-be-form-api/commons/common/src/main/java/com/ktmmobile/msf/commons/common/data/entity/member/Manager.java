package com.ktmmobile.msf.commons.common.data.entity.member;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.data.type.MemberRole;

@Getter
@RequiredArgsConstructor
public class Manager implements Member, Serializable {

    private final int managerKey;
    private final String managerId;
    private final String email;
    private MemberRole memberRole;

    @Override
    public int getKey() {
        return managerKey;
    }

    @Override
    public MemberRole getMemberRole() {
        return memberRole;
    }

    @Override
    public void setMemberRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }
}
