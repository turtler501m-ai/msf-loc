package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

public class EncryptDto  implements Serializable  {

    private static final long serialVersionUID = 1L;


    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
