package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Data
@XmlRootElement(name = "inDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormMP0InfoRequest {

    private BaseInfo baseInfo;

    // =========================
    // baseInfo
    // =========================
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class BaseInfo {

        private String osstOrdNo;    // OSST 오더 번호 (YYYYMMDD + seq 6자리) 필수
        private String custNo;    // 고객번호 (양도인 고객ID) 필수
        @XmlJavaTypeAdapter(EncryptAdapter.class)
        private String tlphNo;    // 전화번호 (양도인 전화번호) 필수
        private String svcContId;    // 서비스계약번호 (양도인) 필수
        private String rcvCustNo;    // 양수인고객번호 필수
        private String rcvBillAcntNo;    // 양수인청구계정번호 필수
        private String mcnStatRsnCd;    // 명변 사유코드 필수
        @XmlJavaTypeAdapter(EncryptAdapter.class)
        private String iccId;    // 유심 ICCID (eSim은 Null)
        private String usimPymnMthdCd;    // USIM 수납방법 코드 (R:즉납, B:후청구, N:비구매 - eSim계약 비구매 고정)
        private String docConfirmYn;    // 증빙서류 확인 여부 필수
        private String followupYn;    // 사후점검 이행 여부 필수
        private String slsCmpnCd;    // 판매회사코드 필수
        @XmlJavaTypeAdapter(EncryptAdapter.class)
        private String sbscPrtlstRcvEmlAdrsNm;    // 가입내역서수신이메일주소명
        private String cntpntCd;    // 접점코드 필수
        @XmlJavaTypeAdapter(EncryptAdapter.class)
        private String cntplcNo;    // 연락처번호 (양도인미납을 위한 연락처) 필수
        @XmlJavaTypeAdapter(EncryptAdapter.class)
        private String chgRqsshtEmlAdrsNm;    // 청구서이메일주소명

    }
    // =========================
}
