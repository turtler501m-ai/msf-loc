package com.ktmmobile.msf.external.websecurity.web.dto.response;

import java.util.HashMap;

public class CreatedResponse<K> extends HashMap<String, K> {

    public static <K> CreatedResponse<K> of(String fieldName, K domainKey) {
        CreatedResponse<K> data = new CreatedResponse<>();
        data.put(fieldName, domainKey);
        return data;
    }
}
