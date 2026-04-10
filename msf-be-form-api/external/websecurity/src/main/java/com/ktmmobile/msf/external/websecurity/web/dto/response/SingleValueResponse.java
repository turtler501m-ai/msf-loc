package com.ktmmobile.msf.external.websecurity.web.dto.response;

import java.util.HashMap;

public class SingleValueResponse<V> extends HashMap<String, V> {

    public static <V> SingleValueResponse<V> of(String fieldName, V value) {
        SingleValueResponse<V> data = new SingleValueResponse<>();
        data.put(fieldName, value);
        return data;
    }
}
