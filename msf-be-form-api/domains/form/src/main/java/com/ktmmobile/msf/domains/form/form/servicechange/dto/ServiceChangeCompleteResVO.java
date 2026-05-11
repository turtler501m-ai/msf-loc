package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceChangeCompleteResVO {
    private String applicationNo;
    private int addCount;
    private int cancelCount;

    public static ServiceChangeCompleteResVO of(String applicationNo, int addCount, int cancelCount) {
        ServiceChangeCompleteResVO response = new ServiceChangeCompleteResVO();
        response.setApplicationNo(applicationNo);
        response.setAddCount(addCount);
        response.setCancelCount(cancelCount);
        return response;
    }
}
