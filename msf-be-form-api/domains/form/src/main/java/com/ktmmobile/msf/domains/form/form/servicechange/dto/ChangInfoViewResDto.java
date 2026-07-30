package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;

@Getter
@Setter
@NoArgsConstructor
public class ChangInfoViewResDto {

    private List<McpUserCntrMngDto> cntrList;
    private MyPageSearchDto searchVO;
    private String ncn;
    private String contractNum;
    private String ctn;
    private String custId;
    private String modelName;
    private String rprsPrdtId;
    private String prvRateGrpNm;
    private String rateAdsvcLteDesc;
    private String rateAdsvcCallDesc;
    private String rateAdsvcSmsDesc;
    private String initActivationDate;
    private String zipNo;
    private String address;
    private String detailAddress;
    private String addr;
    private String homeTel;
    private String email;
    private Map<String, String> payData;
    private Map<String, String> billData;
    private String maskingBtn;
    private String maskingSession;
    private String remindBlckYn;
    private String subStatus;
    private String reqBuyType;
}
