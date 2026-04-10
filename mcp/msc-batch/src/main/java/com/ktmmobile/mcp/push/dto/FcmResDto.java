package com.ktmmobile.mcp.push.dto;

import java.io.Serializable;


public class FcmResDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int successCount;
    private final int failureCount;

    // 기본 생성자
    public FcmResDto() {
        this.successCount = 0;
        this.failureCount = 0;
    }

    public FcmResDto(int successCount, int failureCount) {
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }
}
