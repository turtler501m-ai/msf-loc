package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 데이터 쉐어링  req
 * @author bsj
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class MyShareDataReqDto implements Serializable  {

	private static final long serialVersionUID = 987580018599047263L;
	private String iccId; //유심유효성
	private String custId;	//고객번호
	private String ncn;	//사용자 서비스계약번호
	private String ctn;	//사용자 전화번호
	private String clntIp;	//Client IP
	private String clntUsrId;	//사용자 User ID
	private String crprCtn; //결합할 전화번호
	private String opmdSvcNo; //데이터쉐어링 대상 전화번호
	private String opmdWorkDivCd; //처리구분코드 A:결합, C:해지
	private String birthday; //생년월일
	private String name; // 성명
	private String contractNum; // 계약번호
	private String selfShareYn; //셀프개통 사전체크 Y , 쉐어링 가입 N

}
