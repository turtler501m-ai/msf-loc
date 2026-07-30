package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyPageSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userType;
    private String userDivisionYn;
    private String userName;
    private String ncn;
    private String ctn;
    private String billSeqNo; // 청구일련번호
    private String billMonth; // 청구년월
    private String message;
    private String custId;
    private String modelName;
    private String rprsPrdtId;
    private String contractNum; // 계약번호
    private Integer menuKey;
    private String backUrl;
    private String pageNo;
    private String tabBtnIndex;
    private String subStatus; // 상태값 SUB_STATUS
    private int moCtn;
    private String seq;
    private String addDivCd;
    private String soc; // 요금제 코드
    private Boolean roadAddrChk;
    private Boolean skipPerMyktfInfo;
}
