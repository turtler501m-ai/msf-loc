package com.ktmmobile.msf.domains.form.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@EqualsAndHashCode(callSuper = true)
@Data
public class MplatFormX23Response extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    public static class OutDto {

        private String payMethod; // 납부방법
        private String payBizrCd; // 간편결제사업자코드
        private String billCycleDueDay; // 납부일
        private String blBankName; // 은행명
        private String blBankAcctNo; // 계좌번호
        private String bankAcctHolderName; // 납부자명
        private String blAddr; // 청구지 주소
        private String creditCardName; // 카드사 명
        private String prevCardNo; // 카드번호
        private String prevExpirDt; // 카드만료기간
        private String jointBillWithKt; // KT합산구분 아이디
        private String payTmsCd; // 납부회차

    }


}
