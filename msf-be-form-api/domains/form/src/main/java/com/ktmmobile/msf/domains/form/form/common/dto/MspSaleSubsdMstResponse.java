package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 가격정보조회 (단말, 요금, 지원금, 가입비, 유심비 등) Response
 */
@Getter
@Setter
@NoArgsConstructor
public class MspSaleSubsdMstResponse {

    String salePlcyCd; //판매정책코드
    String rateCd; //요금제코드
    String prdtId; //제품id
    String agrmTrm; // 약정기간
    String operType; //업무유형
    String hndstAmt; //단말금액(vat포함)
    String subsdAmt; //공시지원금(vat포함)
    String instAmt; // 할부원금(vat포함)
    String instCmsn; //할부수수료(vat포함)
    String agncySubsdMax; //대리점보조금max(vat포함)
    String agncySubsdAmt; //대리점보조금(vat포함)
    String sprtTp; //지원금유형
    String baseAmt; //기본료 (vat 제외된 금액)
    String dcAmt; //기본할인금액 (vat 제외된 금액)
    String addDcAmt; //추가할인금액 (vat 제외된 금액)

    String prmtAmt; //프로모션가격

    String totalInstCmsn; //총할부수수료

    //String oldYn; //중고여부
    //String orgnId; //조직코드
    //String regstId; //등록자id
    //String regstDttm; //등록일시
    //String rvisnId; //수정자id
    //String rvisnDttm; //수정일시

    //String usimKindsCd; //유심종류
    //String joinPrice; //가입비
    String joinIsPay; //가입비 납부여부 ( Y:납부, N:면제 )
    String simPrice; //유심가격
    String simIsPay; //일반유심비용 납부여부 ( Y:납부, N:면제 )
    String nfcSimIsPay; //NFC유심비용 납부여부 ( Y:납부, N:면제 )

    private String joinPriceTypeCd; //가입비 납부유형코드	o 코드관리(M포탈) (R:완납, I:분납 , P:면제)
    private String joinPayMthdCd; //가입비 납부방법코드 o 코드관리(M포탈) (1 : 면제 , 2 : 일시납, 3 : 3개월분납))
    private String joinPrice; //가입비

    private String usimPriceTypeCd; //유심비 납부유형코드
    private String usimPayMthdCd; //유심비 납부방법코드
    private String usimPrice; //유심가격

}
