package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import lombok.Data;

@Data
public class MplatFormFMP0InfoResponse {

    /** baseInfo **/
    private String osstOrdNo; // OSST 오더 번호 (필수)
    private String custNo; // 고객번호 (필수)
    private String tlphNo; // 전화번호 (필수)
    private String svcContId; // 서비스계약번호 (필수)
    private String rcvCustNo; // 양수인고객번호 (필수)
    private String rcvBillAcntNo; // 양수인청구계정번호 (필수)
    private String mcnStatRsnCd; // 명변 사유코드 (필수)
    private String iccId; // 유심 ICCID
    private String usimSuccYn; // 유심 승계여부
    private String usimPymnMthdCd; // USIM 수납방법 코드
    private String docConfirmYn; // 증빙서류 확인 여부 (필수)
    private String followupYn; // 사후점검 이행 여부 (필수)
    private String slsCmpnCd; // 판매회사코드 (필수)
    private String sbscPrtlstRcvEmlAdrsNm; // 가입내역서수신이메일주소명
    private String cntpntCd; // 접점코드 (필수)
    private String cntplcNo; // 연락처번호 (필수)
    private String chgRqsshtEmlAdrsNm; // 청구서이메일주소명

    /** inFrmpopDto **/
    private String frmpapId; // 서식지 아이디 (필수)

    private String mvnoOrdNo;
    private String mcnResNo;
}
