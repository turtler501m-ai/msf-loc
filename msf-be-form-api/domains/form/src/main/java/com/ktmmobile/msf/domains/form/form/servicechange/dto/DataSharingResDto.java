package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.ArrayList;
import java.util.List;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.OutDataSharingDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataSharingResDto {
    private boolean subscribed;
    private boolean available;
    private boolean parentAvailable;
    private String custId;
    private String ncn;
    private String contractNum;
    private String ctn;
    private String subStatus;
    private String targetNo;
    private String message;
    private String soc;
    private String rateNm;
    private String socChkYn;
    private String customerType;
    private String changeYn;
    private String subStatusYn;
    private String isMacTime;
    private String resultCode;
    private List<OutDataSharingDto> items = new ArrayList<>();
}
