package com.ktmmobile.msf.domains.externalclient.mspprx.support.serializer;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

import com.ktmmobile.msf.commons.crypto.support.util.KisaSeedUtils;

public class PrxDecryptValueDeserializer extends ValueDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) {
        String value = p.getValueAsString();
        if (value == null || value.isEmpty()) {
            return null;
        }
        return KisaSeedUtils.decrypt(value);
    }
}
