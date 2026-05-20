package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class McpIpStatisticDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String parameter;           // 접근 파라미터
    private String accessIp;            // 접근 아이피
    private String sysDt;               // 입력일(년월일)
    private String sysRdate;            // 입력일
    private String accessUrl;           // 접근 URL
    private String userid;              // 접근자
    private String prcsMdlInd;
    private String prcsSbst;
    private String trtmRsltSmst;
    private String loginDivCd;
    private String loginSeq;
    private String platformCd;
    private String menuSeq;
    private String urlSeq;
    private String rateResChgSeq;
    private String svcCntrNo;           // 서비스계약번호
    private String mobileNo;            // 모바일번호
    private String eventCode;           // 이벤트코드
    private String resChgRateCd;        // 예약변경요금제코드
    private String param;               // 파라미터
    private String resChgDate;          // 예약변경일
    private String resChgApyDate;       // 예약변경신청일자
    private String trtMdlDiv;           // 처리모듈구분
    private String globalNo;            // 글로벌넘버
    private String batchRsltCd;         // 성공 00000 실패 9999
    private String cretIp;              // 생성일시
    private String befChgRateCd;        // 변경전요금제코드
    private int befChgRateAmnt = 0;     // 변경전요금제금액

    // isBlank 방어 처리 포함 getter들
    public String getPrcsMdlInd() {
        if (StringUtils.isBlank(prcsMdlInd)) { return ""; }
        return prcsMdlInd;
    }
    public String getPrcsSbst() {
        if (StringUtils.isBlank(prcsSbst)) { return ""; }
        return prcsSbst;
    }
    public String getTrtmRsltSmst() {
        if (StringUtils.isBlank(trtmRsltSmst)) { return ""; }
        return trtmRsltSmst;
    }
    public String getParameter() {
        if (StringUtils.isBlank(parameter)) { return ""; }
        return parameter;
    }
    public String getSysRdate() {
        if (StringUtils.isBlank(sysRdate)) { return ""; }
        return sysRdate;
    }
    public String getUserid() {
        if (StringUtils.isBlank(userid)) { return ""; }
        return userid;
    }
    public String getSysDt() {
        if (StringUtils.isBlank(sysDt)) { return ""; }
        return sysDt;
    }

    // 비표준명 getter/setter — accessIp/accessUrl을 ip/url 이름으로 노출
    public String getIp() {
        if (StringUtils.isBlank(accessIp)) { return ""; }
        return accessIp;
    }
    public void setIp(String accessIp) { this.accessIp = accessIp; }
    public String getUrl() {
        if (StringUtils.isBlank(accessUrl)) { return ""; }
        return accessUrl;
    }
    public void setUrl(String accessUrl) { this.accessUrl = accessUrl; }

}
