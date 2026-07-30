package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ServiceChangeCompleteResVO {
    private String requestKey;
    private int addCount;
    private int cancelCount;
    private int addSuccessCount;
    private int addFailCount;
    private int cancelSuccessCount;
    private int cancelFailCount;
    private List<ProcessResult> processResults = new ArrayList<>();

    public static ServiceChangeCompleteResVO of(String requestKey, int addCount, int cancelCount) {
        ServiceChangeCompleteResVO response = new ServiceChangeCompleteResVO();
        response.setRequestKey(requestKey);
        response.setAddCount(addCount);
        response.setCancelCount(cancelCount);
        return response;
    }

    public void addResult(ProcessResult result) {
        if (result == null) {
            return;
        }
        processResults.add(result);
        if ("CANCEL".equals(result.getAction())) {
            if (result.isSuccess()) {
                cancelSuccessCount++;
            } else {
                cancelFailCount++;
            }
            return;
        }
        if ("ADD".equals(result.getAction())) {
            if (result.isSuccess()) {
                addSuccessCount++;
            } else {
                addFailCount++;
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProcessResult {
        private String action;
        private String svcTgtCd;
        private String procTypeCd;
        private String soc;
        private String serviceName;
        private String prodHstSeq;
        private boolean success;
        private String resCode;
        private String resMessage;
        private String resNo;

        public static ProcessResult of(
            String action,
            String soc,
            String serviceName,
            String prodHstSeq,
            boolean success,
            String resCode,
            String resMessage
        ) {
            ProcessResult result = new ProcessResult();
            result.setAction(action);
            result.setSoc(soc);
            result.setServiceName(serviceName);
            result.setProdHstSeq(prodHstSeq);
            result.setSuccess(success);
            result.setResCode(resCode);
            result.setResMessage(resMessage);
            return result;
        }
    }
}
