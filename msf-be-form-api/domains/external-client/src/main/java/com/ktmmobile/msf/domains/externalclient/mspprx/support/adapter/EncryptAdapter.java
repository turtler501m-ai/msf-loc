package com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import com.ktmmobile.msf.commons.crypto.support.util.KisaSeedUtils;

/**
 * JAXB 필드 암복호화 어댑터
 */
public class EncryptAdapter extends XmlAdapter<String, String> {

    @Override
    public String marshal(String value) {
        if (value == null) {
            return null;
        }

        return KisaSeedUtils.encrypt(value);
    }

    @Override
    public String unmarshal(String value) {
        if (value == null) {
            return null;
        }

        return KisaSeedUtils.decrypt(value);
    }
}
