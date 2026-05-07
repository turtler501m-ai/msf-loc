package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MspSaleSubsdMstResponse {
    String salePlcyCd; //판매정책코드
    String rateCd; //요금제코드
    String prdtId; //제품id
    String oldYn; //중고여부
    String agrmTrm; // 약정기간
    String operType; //업무유형
    String orgnId; //조직id
    String hndstAmt; //단말금액(vat포함)
    String subsdAmt; //공시지원금(vat포함)
    String instAmt; // 할부원금(vat포함)
    String instCmsn; //할부수수료(vat포함)
    String agncySubsdMax; //대리점보조금max(vat포함)
    String agncySubsdAmt; //대리점보조금(vat포함)
    String regstId; //등록자id
    String regstDttm; //등록일시
    String rvisnId; //수정자id
    String rvisnDttm; //수정일시
    String sprtTp; //지원금유형
    String baseAmt; //기본료
    String dcAmt; //기본할인금액
    String addDcAmt; //추가할인금액
}
