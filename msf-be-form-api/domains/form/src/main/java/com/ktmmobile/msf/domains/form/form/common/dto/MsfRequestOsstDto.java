package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestOsstDto {
    private String mvnoOrdNo;
    /**
     * 일련번호
     */
    private int seq;
    /**
     * OSST오더번호
     */
    private String osstOrdNo;
    /**
     * 진행상태코드
     */
    private String prgrStatCd;
    /**
     * 고객ID
     */
    private String custId;
    /**
     * 서비스계약번호
     */
    private String svcCntrNo;
    /**
     * 처리결과코드
     */
    private String rsltCd;
    /**
     * 처리결과내용
     */
    private String rsltMsg;
    /**
     * 처리일시
     */
    private String rsltDt;
    /**
     * NSTEP 에러 GLOBAL ID
     */
    private String nstepGlobalId;
    /**
     * 상품체크안내메세지
     */
    private String prdcChkNotiMsg;
    /**
     * 번호이동이전사업자계약유형코드(0: 선불, 1:후불)
     */
    private String npBcntrTypeCd;
    /**
     * 번호이동수수료
     */
    private long npFee;
    /**
     * 타사미청구금액
     */
    private long npNchrgAmt;
    /**
     * 번호이동위약금
     */
    private long npPnltAmt;
    /**
     * 번호이동미납금액
     */
    private long npUnpayAmt;
    /**
     * 번호이동단말할부금
     */
    private long npHndstInstAmt;
    /**
     * 번호이동선납금
     */
    private long npPrepayAmt;
    /**
     * 번호이동기본료
     */
    private long npBaseChrgAmt;
    /**
     * 번호이동국내통화료
     */
    private long npNtnlChrgAmt;
    /**
     * 번호이동국제통화료
     */
    private long npIntlChrgAmt;
    /**
     * 번호이동부가사용료
     */
    private long npAddChrgAmt;
    /**
     * 번호이동기타사용료
     */
    private long npEtcChrgAmt;
    /**
     * 번호이동부가세
     */
    private long npVat;
    /**
     * 번호이동수납대상시작일자
     */
    private String npRmnStrtDt;
    /**
     * 번호이동수납대상종료일자
     */
    private String npRmnEndDt;
    /**
     * 전화번호상태코드(NU1)
     */
    private String tlphNoStatCd;
    /**
     * 할당대리점ID(NU1)
     */
    private String asgnAgncId;
    /**
     * 번호소유통신사업자코드(NU1)
     */
    private String tlphNoOwnCmpnCd;
    /**
     * 개통서비스구분코드(NU1)
     */
    private String openSvcIndCd;
    /**
     * 암호화전화번호(NU1)
     */
    private String encdTlphNo;
    /**
     * 전화번호
     */
    private String tlphNo;
    /**
     * 연동유형
     */
    private String ifType;
    /**
     * 등록일시
     */
    private Date regstDttm;
}
