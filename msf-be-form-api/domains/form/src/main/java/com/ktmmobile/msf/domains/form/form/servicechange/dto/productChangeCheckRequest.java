package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.List;

import lombok.Data;

@Data
public class productChangeCheckRequest {

    private String ncn; // 서비스계약번호
    private String ctn; // 사용자 전화번호
    private String custId; // 고객번호
    private String actCode; // 작업구분코드(PCN: 요금제 변경, SRG: 부가서비스 변경, RSV: 예약처리)
    private List<ProductInfo> prdcList;
    // 프로모션ID -> prx에서 prmtId로 /msp/mspDisAddList에 조회하여 넘어온 값으로 세팅해주고, param을 단 건으로 사용하고 있어
    // prdcList를 list로 넘겨도 되는지 확인 불가 param은 HashMap<String, String>으로 사용중임
    private String prmtId;

    @Data
    public static class ProductInfo {

        private String prdcCd; // 상품코드 (front에 rateCd)
        private String prdcSbscTrtmCd; // 상품가입처리코드 (A:가입, C:해지, U:PARAM변경)
        private String prdcTypeCd; // 상품유형코드 (R: 부가서비스, P: 요금제)
        private String prdcSeqNo; // 상품일련번호 (중복 가입 부가서비스 해지시 일련번호 필수)
        private String ftrNewParam; // 상품 파람 (부가파람 필요한 상품의 가입 or 변경시 필수)

    }
}
