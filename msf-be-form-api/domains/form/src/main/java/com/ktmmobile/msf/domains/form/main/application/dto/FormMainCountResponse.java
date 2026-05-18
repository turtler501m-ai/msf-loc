package com.ktmmobile.msf.domains.form.main.application.dto;

import java.util.List;

public record FormMainCountResponse(
    List<ChartCountResponse> statusList,
    List<ChartCountResponse> serviceList
) {
    public static FormMainCountResponse of(List<ChartCountResponse> statusList, List<ChartCountResponse> serviceList) {
        return new FormMainCountResponse(statusList, serviceList);
    }
}
