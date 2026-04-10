package com.ktmmobile.msf.domains.form.common.mplatform.dto;

public class MoscMvnoComInfo {

    private String combSvcNo; //결합 모회선 조회값
    private String svcNo; //서비스번호
    private String combYn; //결합여부
    private String svcDivCd; //"홈IoT''Device단독''모바일''인터넷''IPTV''SoIp''WiBro''PSTN''WiFi''스마트렌탈'	'기타'"
    private String indvInfoAgreeMsgSbst; //sameCustKtRetvYn(동일명의 KT회선 조회여부) = 'Y'로 조회 시미동의 회선의 정보 응답  / 형식 : 서비스종류|회선수,서비스종류|회선수 ex) 모바일|2,인터넷|1
    private String corrNm; //모집경로 MVNOKIS, MVNOLIV 해당 MVNO사업자를 통해 가입한 인터넷이 있을 경우 한하여 출력. KT에서 가입하거나 타 MVNO사업자를 통해 가입한 인터넷의 경우 해당 필드를 출력하지 않음.
    private String svcContOpnDt; // 가입일  yyyyMMdd
    private String seq; // 시퀀스
    private String mskSvcNo; // 마스킹서비스번호

    public String getCombSvcNo() {
        return combSvcNo;
    }
    public void setCombSvcNo(String combSvcNo) {
        this.combSvcNo = combSvcNo;
    }
    public String getSvcNo() {
        return svcNo;
    }
    public void setSvcNo(String svcNo) {
        this.svcNo = svcNo;
    }
    public String getCombYn() {
        if (combYn == null) {return "";}
        return combYn;
    }
    public void setCombYn(String combYn) {
        this.combYn = combYn;
    }
    public String getSvcDivCd() {
        return svcDivCd;
    }
    public void setSvcDivCd(String svcDivCd) {
        this.svcDivCd = svcDivCd;
    }
    public String getIndvInfoAgreeMsgSbst() {
        return indvInfoAgreeMsgSbst;
    }
    public void setIndvInfoAgreeMsgSbst(String indvInfoAgreeMsgSbst) {
        this.indvInfoAgreeMsgSbst = indvInfoAgreeMsgSbst;
    }
    public String getCorrNm() {
        return corrNm;
    }
    public void setCorrNm(String corrNm) {
        this.corrNm = corrNm;
    }
    public String getSvcContOpnDt() {
        return svcContOpnDt;
    }
    public void setSvcContOpnDt(String svcContOpnDt) {
        this.svcContOpnDt = svcContOpnDt;
    }
    public String getSeq() {
        return seq;
    }
    public void setSeq(String seq) {
        this.seq = seq;
    }
    public String getMskSvcNo() {
        return mskSvcNo;
    }
    public void setMskSvcNo(String mskSvcNo) {
        this.mskSvcNo = mskSvcNo;
    }
}
