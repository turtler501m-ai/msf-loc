package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyCombinationReqDto {

	private String custId;	//고객번호
	private String ncn;		//사용자 서비스계약번호
	private String ctn; 	//사용자 전화번호
	private String clntIp;  //Client IP
	private String reqSvc; 	 //선택한 회선에 전화번호
	private String contractNum; //가입계약번호
	private String combiChkYn; //결합 사전체크여부

}
