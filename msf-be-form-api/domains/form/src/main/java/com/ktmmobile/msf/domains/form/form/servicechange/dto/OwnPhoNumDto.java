package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class OwnPhoNumDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cstmrName;
    private String cstmrNativeRrn;
    private String cstmrNativeRrn01;
    private String cstmrNativeRrn02;
    private String onlineAuthType;
    private String onlineAuthInfo;
    private String cntrMobileNo;
    private String lstComActvDate;    
    
	private String reqSeq;				//요청일련번호
	private String resSeq;              //응답일련번호

    

}
