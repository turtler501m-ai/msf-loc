package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpBaseRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class MplatFormFMP0InfoRequest extends MpBaseRequest {

    private BaseInfo baseInfo;
    private InFrmpapDto inFrmpapDto;

    // =========================
    // baseInfo
    // =========================
    @Data
    public static class BaseInfo {

        private String custNo;    //고객번호
        private String tlphNo;    //전화번호
        private String svcContId;    //서비스계약번호
        private String rcvCustNo;    //양수인고객번호
        private String rcvBillAcntNo;    //양수인청구계정번호
        private String mcnStatRsnCd;    //명변 사유코드
        private String iccId;    //유심 ICCID
        private String usimPymnMthdCd;    //USIM 수납방법 코드
        private String docConfirmYn;    //증빙서류 확인 여부
        private String followupYn;    //사후점검 이행 여부
        private String slsCmpnCd;    //판매회사코드
        private String sbscPrtlstRcvEmlAdrsNm;    //가입내역서수신이메일주소명
        private String cntpntCd;    //접점코드
        private String cntplcNo;    //연락처번호
        private String chgRqsshtEmlAdrsNm;    //청구서이메일주소명
    }
    // =========================

    // =========================
    // inFrmpapDto
    // =========================
    @Data
    public static class InFrmpapDto {

        private String frmpapId;            // 서식지아이디
    }
    // =========================

}
