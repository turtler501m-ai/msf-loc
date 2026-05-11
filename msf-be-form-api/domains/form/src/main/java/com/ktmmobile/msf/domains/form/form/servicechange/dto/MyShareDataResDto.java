package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyShareDataResDto {

	private String svcNo; //본인핸드폰번호
	private String socNm; //본인요금제
	private String opmdSvcSocNm; //데이터쉐어링 가입하는 요금제
	private String opmdSvcNo; //데이터쉐어링 가입한사람 핸드폰번호

}
