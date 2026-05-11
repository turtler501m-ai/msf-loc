package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class BillWayChgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String thisMonth;
    private String billTypeCd;
    private String email;
    private String billAdInfo;
    private String year;
    private String month;

    private int seq;
	private String globalNo;
	private String contractNum;
	private String mobileNo;
	private String userId;
	private String userNm;
	private String subLinkNm;
	private String gender;
	private String age;
	private String lstComActvDate;
	private String openAgntCd;
	private String onOffType;
	private String orgId;
	private String successYn;
	private String errMsg;
	private String rdate;
	private String rdt;

}
