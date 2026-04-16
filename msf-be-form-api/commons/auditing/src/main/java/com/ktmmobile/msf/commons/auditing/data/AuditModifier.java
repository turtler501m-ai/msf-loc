package com.ktmmobile.msf.commons.auditing.data;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AuditModifier {

    private String modifiedBy;
    private String modifiedIp;
    private boolean alreadySet;

    public void setModifier(String modifiedBy, String modifiedIp) {
        this.modifiedBy = modifiedBy;
        this.modifiedIp = modifiedIp;
        this.alreadySet = true;
    }
}
