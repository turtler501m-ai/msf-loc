package com.ktmmobile.msf.commons.common.data.entity.member;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.ktmmobile.msf.commons.common.data.type.MemberRole;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoMember implements Member, Serializable {

    private int memberKey;
    private String id;
    private String email;
    private MemberRole memberRole;

    @Override
    public int getKey() {
        return memberKey;
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
