package com.ktmmobile.msf.domains.form.common.mspservice.dto;

import com.ktmmobile.msf.domains.form.common.dto.CommonSearchDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Class Name : MspNoticSupportMstDto
 * @Description :
 * MSP 의 공시지원금 DTO 이다.
 * @author : ant
 * @Create Date : 2016. 9. 5.
 */
@Getter
@Setter
@NoArgsConstructor
public class MspNoticSupportMstDto extends CommonSearchDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String rateCd; // 요금제 코드
	private String rateNm; // 요금제 명
	private String prdtNm; // 모델명
	private String prdtIndCd; // 단말유형
	private String outUnitPric; // 출고가
	private String subsdAmt; // 공시지원급
	private String pricAmt; // 판매가
	private String applStrtDt; // 공시일
	private String sortType; // 정렬방식
	private String dataType;
}
