package com.ktmmobile.msf.domains.externalclient.mspprx.support.exception;

import com.ktmmobile.msf.commons.client.support.exception.ClientException;

public class MspPrxClientException extends ClientException {

    private final String responseCode;
    private final String globalNo;

    public MspPrxClientException(String message) {
        this(message, null, null, null);
    }

    public MspPrxClientException(String message, Throwable cause) {
        this(message, null, null, cause);
    }

    public MspPrxClientException(String message, String responseCode, String globalNo) {
        this(message, responseCode, globalNo, null);
    }

    private MspPrxClientException(String message, String responseCode, String globalNo, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode;
        this.globalNo = globalNo;
    }

    public String responseCode() {
        return responseCode;
    }

    public String globalNo() {
        return globalNo;
    }
}
