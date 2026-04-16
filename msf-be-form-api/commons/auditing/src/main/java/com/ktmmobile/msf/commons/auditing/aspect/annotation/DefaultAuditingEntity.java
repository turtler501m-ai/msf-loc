package com.ktmmobile.msf.commons.auditing.aspect.annotation;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.ktmmobile.msf.commons.auditing.data.AuditModifier;

@Getter
@NoArgsConstructor
public class DefaultAuditingEntity implements AuditingEntity {

    private final AuditModifier audit = new AuditModifier();

    public static AuditingEntity create() {
        return new DefaultAuditingEntity();
    }
}
