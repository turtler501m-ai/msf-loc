package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JsonReturnDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private String returnCode;
    private Map<String, Object> resultMap;
    private Object result;

}
