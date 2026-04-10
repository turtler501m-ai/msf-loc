package com.ktmmobile.msf.commons.common.data.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    FO_MEMBER("ROLE_FO", "FO", "/fo", "일반 사용자", MemberType.FO_MEMBER, RoleType.NORMAL),
    BO_MANAGER("ROLE_BO", "BO", "/bo", "BO 관리자", MemberType.MANAGER, RoleType.NORMAL),

    TEMPORARY_FO_MEMBER("ROLE_FO_TEMP", "FO_TEMP", "/fotmp", "일반 사용자 (임시)", MemberType.FO_MEMBER, RoleType.TEMPORARY),
    TEMPORARY_BO_MANAGER("ROLE_BO_TEMP", "BO_TEMP", "/botmp", "BO 관리자 (임시)", MemberType.MANAGER, RoleType.TEMPORARY),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "--", "", "Invalid Member Role", null, null);


    private final String code;
    private final String simpleCode;
    private final String prefixPath;
    private final String title;
    private final MemberType memberType;
    private final RoleType roleType;

    public boolean isFoMember() {
        return this.getMemberType() == MemberType.FO_MEMBER;
    }

    public boolean isManager() {
        return this.getMemberType() == MemberType.MANAGER;
    }

    public boolean isNormalRole() {
        return this.getRoleType() == RoleType.NORMAL;
    }

    public boolean isTemporaryRole() {
        return this.getRoleType() == RoleType.TEMPORARY;
    }

    public static MemberRole valueOfCode(String code) {
        for (MemberRole value: values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return getInvalidValue();
    }

    public static MemberRole getInvalidValue() {
        return UNDEFINED;
    }

    public boolean isValid() {
        return this != getInvalidValue();
    }

    public String simpleCodeToLowerCase() {
        return this.getSimpleCode().toLowerCase();
    }


    private enum MemberType {
        FO_MEMBER, MANAGER
    }

    private enum RoleType {
        NORMAL, TEMPORARY
    }
}
