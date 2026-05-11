package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CustRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
	private long requestKeyOrg;
	private String scanIdOrg;
	
	/**
	 * NMCP_CUST_REQUEST_MST  
	*/
    private long custReqSeq;
    private String reqType;
    private String userId;
    private String cstmrName;
    private String mobileNo;
    private String cstmrNativeRrn;
    private String contractNum;
    private String cstmrType;
    private String onlineAuthType;
    private String onlineAuthInfo;
    private String cretId;
    private String sysRdate;
    private String custId;
    
    /**
     * NMCP_CUST_REQUEST_CALL_LIST 
    */
    private String scanId;
    private String startDate;
    private String endDate;
    private String callType;
    private String typeVoice;
    private String typeText;
    private String typeData;
    private String typeVoiceText;
    private String typeAll;
    private String reqRsn;
    private String recvType;
    private String recvText;
    private String callNum;
    private String mailAddr;
    private String etcMemo;
    private String updtId;
    private String sysUdate;
    
    /**
     * NMCP_CUST_REQUEST_JOIN_FORM
    */
    private String faxNo;
    private String cstmrPost;
    private String cstmrAddr;
    private String cstmrAddrDtl;
    
    /**
     * 안심보험 가입신청
     */
    private String insrType;
    private String insrProdCd;
    private String reqBuyType;
    private int maxFileNum;
    private String etcMobile;
    
	/** CERT */
	private String reqSeq;
	private String resSeq;
	private String ncn;

}
