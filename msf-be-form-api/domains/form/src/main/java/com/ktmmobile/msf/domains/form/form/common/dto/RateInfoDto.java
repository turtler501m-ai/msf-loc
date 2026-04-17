package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RateInfoDto {

    /**
     * 정책코드
     */
    private String salePlcyCd;
    /**
     * 요금제코드
     */
    private String rateCd;

    /**
     * 요금제명
     */
    private String rateNm;

    /**
     * 서비스유형
     **/
    private String serviceType;

    /**
     * 요금제혜택
     **/
    private String cmnt;

    /**
     * 요금제제휴처
     **/
    private String jehuProdType;

    private String jehuProdNm;

    // /**요금제그룹코드 */
    // private String rateGrpCd;
    //
    // /**선후불구분 */
    // private String payClCd;
    //
    // /** 요금제유형(ORG0008)*/
    // private String rateType;
    //
    // /** 데이터유형 (ORG0008)*/
    // private String dataType;
    //
    // /**기본료 */
    // private int baseAmt;
    //
    // /** 망내외무료통화구분 */
    // private String freeCallClCd;
    //
    // /**무료통화건수 */
    // private String freeCallCnt;
    //
    // /**망내무료통화건수 */
    // private String nwInCallCnt;
    //
    // /** 망외무료통화건수 */
    // private String nwOutCallCnt;
    //
    // /** 무료문자건수 */
    // private String freeSmsCnt;
    //
    // /** 무료데이터건수 */
    // private String freeDataCnt;
    //
    // /** 비고 */
    // private String rmk;
    //
    // /** 등록자ID */
    // private String regstId;
    //
    // /** 등록일시 */
    // private Date regstDttm;
    //
    // /** 수정자ID*/
    // private String rvisnId;
    //
    // /** 수정일시 */
    // private Date rvisnDttm;
    //
    // /** 온라인유형코드 */
    // private String onlineTypeCd;
    //
    // /** 알요금제구분자 */
    // private String alFlag;
    //
    // /** 서비스유형  */
    // private String serviceType;
    //
    // /** 약정 개월수 */
    // private String agrmTrm;
    //
    // /** 해지 가능 여부 */
    // private String onlineCanYn;
    //
    // /** 해지 안내 문구 */
    // private String canCmnt;
    //
    // /** 해지 가능 날짜(개통일 + 해지 가능 날짜의 합 이까지) */
    // private int onlineCanDay;
}
