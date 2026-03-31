package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;
import org.w3c.dom.Element;

/**
 * X54 스폰서 조회 응답 VO.
 * ASIS: MpMoscSpnsrItgInfoInVO (mcp-portal-was) 동일 구조.
 * 핵심 필드: trmnForecBprmsAmt (해지 시 예상 위약금).
 * saleEngtOptnCd=KD → outKDDto, PM → outPMDto 구분 파싱.
 */
public class MpMoscSpnsrItgInfoInVO extends CommonXmlVO {

    /** 스폰서 유형명 */
    private String saleEngtNm;
    /** 스폰서 유형 구분 코드 */
    private String saleEngtTypeDivCd;
    /** 스폰서 유형 옵션 코드 (KD: 단말할인, PM: 요금할인) */
    private String saleEngtOptnCd;
    /** 가입일 */
    private String engtAplyStDate;
    /** 경과 기간 (일) */
    private String engtUseDayNum;
    /** 만료 예정일 */
    private String engtExpirPamDate;
    /** 잔여 약정 기간 (일, 정지 포함) */
    private String engtRmndDate;

    // KD (단말할인) 전용
    /** 최초 지원금 */
    private String firstSuprtAmt;
    /** 공시 지원금 */
    private String punoSuprtAmt;
    /** 추가 지원금 */
    private String apdSuprtAmt;
    /** 위약금 (공시지원금) */
    private String ktSuprtPenltAmt;
    /** 위약금 (추가지원금) */
    private String storSuprtPenltAmt;
    /** 위약금 대상 (공시지원금) */
    private String tgtKtSuprtPenltAmt;
    /** 위약금 대상 (추가지원금) */
    private String tgtStorSuprtPenltAmt;
    /** 반환금 (요금할인) */
    private String rtrnAmtAndChageDcAmt;
    /** 현재까지 받은 금액 */
    private String realDcAmt;
    /** (해지 시) 예상 위약금 — 핵심 필드 */
    private String trmnForecBprmsAmt;

    // PM (요금할인) 전용
    /** 요금할인 (월) */
    private String chageDcAmt;
    /** 요금할인 (지원금)(월) */
    private String chageDcAmtSuprtMonth;
    /** 요금할인 (지원금) 반환금 */
    private String chageDcAmtSuprtRtrnAmt;

    @Override
    protected void parse() throws Exception {
        Element outBasInfoDto = XmlParseUtil.getChildElement(this.body, "outBasInfoDto");
        if (outBasInfoDto == null) return;

        this.saleEngtNm          = XmlParseUtil.getChildValue(outBasInfoDto, "saleEngtNm");
        this.saleEngtTypeDivCd   = XmlParseUtil.getChildValue(outBasInfoDto, "saleEngtTypeDivCd");
        this.saleEngtOptnCd      = XmlParseUtil.getChildValue(outBasInfoDto, "saleEngtOptnCd");
        this.engtAplyStDate      = XmlParseUtil.getChildValue(outBasInfoDto, "engtAplyStDate");
        this.engtUseDayNum       = XmlParseUtil.getChildValue(outBasInfoDto, "engtUseDayNum");
        this.engtExpirPamDate    = XmlParseUtil.getChildValue(outBasInfoDto, "engtExpirPamDate");

        if ("KD".equals(this.saleEngtOptnCd)) {
            Element outKDDto = XmlParseUtil.getChildElement(this.body, "outKDDto");
            if (outKDDto != null) {
                this.engtRmndDate            = XmlParseUtil.getChildValue(outKDDto, "engtRmndDate");
                this.firstSuprtAmt           = XmlParseUtil.getChildValue(outKDDto, "firstSuprtAmt");
                this.punoSuprtAmt            = XmlParseUtil.getChildValue(outKDDto, "punoSuprtAmt");
                this.apdSuprtAmt             = XmlParseUtil.getChildValue(outKDDto, "apdSuprtAmt");
                this.ktSuprtPenltAmt         = XmlParseUtil.getChildValue(outKDDto, "ktSuprtPenltAmt");
                this.storSuprtPenltAmt       = XmlParseUtil.getChildValue(outKDDto, "storSuprtPenltAmt");
                this.tgtKtSuprtPenltAmt      = XmlParseUtil.getChildValue(outKDDto, "tgtKtSuprtPenltAmt");
                this.tgtStorSuprtPenltAmt    = XmlParseUtil.getChildValue(outKDDto, "tgtStorSuprtPenltAmt");
                this.rtrnAmtAndChageDcAmt    = XmlParseUtil.getChildValue(outKDDto, "rtrnAmtAndChageDcAmt");
                this.realDcAmt               = XmlParseUtil.getChildValue(outKDDto, "realDcAmt");
                this.trmnForecBprmsAmt       = XmlParseUtil.getChildValue(outKDDto, "trmnForecBprmsAmt");
            }
        } else if ("PM".equals(this.saleEngtOptnCd)) {
            Element outPMDto = XmlParseUtil.getChildElement(this.body, "outPMDto");
            if (outPMDto != null) {
                this.engtRmndDate            = XmlParseUtil.getChildValue(outPMDto, "engtRmndDate");
                this.chageDcAmt              = XmlParseUtil.getChildValue(outPMDto, "chageDcAmt");
                this.chageDcAmtSuprtMonth    = XmlParseUtil.getChildValue(outPMDto, "chageDcAmtSuprtMonth");
                this.chageDcAmtSuprtRtrnAmt  = XmlParseUtil.getChildValue(outPMDto, "chageDcAmtSuprtRtrnAmt");
                this.rtrnAmtAndChageDcAmt    = XmlParseUtil.getChildValue(outPMDto, "rtrnAmtAndChageDcAmt");
                this.realDcAmt               = XmlParseUtil.getChildValue(outPMDto, "realDcAmt");
                // PM 타입: trmnForecBprmsAmt 없음 — rtrnAmtAndChageDcAmt 가 반환금
                this.trmnForecBprmsAmt       = XmlParseUtil.getChildValue(outPMDto, "rtrnAmtAndChageDcAmt");
            }
        }
    }

    public String getSaleEngtNm()              { return saleEngtNm; }
    public String getSaleEngtTypeDivCd()       { return saleEngtTypeDivCd; }
    public String getSaleEngtOptnCd()          { return saleEngtOptnCd; }
    public String getEngtAplyStDate()          { return engtAplyStDate; }
    public String getEngtUseDayNum()           { return engtUseDayNum; }
    public String getEngtExpirPamDate()        { return engtExpirPamDate; }
    public String getEngtRmndDate()            { return engtRmndDate; }
    public String getFirstSuprtAmt()           { return firstSuprtAmt; }
    public String getPunoSuprtAmt()            { return punoSuprtAmt; }
    public String getApdSuprtAmt()             { return apdSuprtAmt; }
    public String getKtSuprtPenltAmt()         { return ktSuprtPenltAmt; }
    public String getStorSuprtPenltAmt()       { return storSuprtPenltAmt; }
    public String getTgtKtSuprtPenltAmt()      { return tgtKtSuprtPenltAmt; }
    public String getTgtStorSuprtPenltAmt()    { return tgtStorSuprtPenltAmt; }
    public String getRtrnAmtAndChageDcAmt()    { return rtrnAmtAndChageDcAmt; }
    public String getRealDcAmt()               { return realDcAmt; }
    /** (해지 시) 예상 위약금 — 위약금 계산의 주 필드 */
    public String getTrmnForecBprmsAmt()       { return trmnForecBprmsAmt; }
    public String getChageDcAmt()              { return chageDcAmt; }
    public String getChageDcAmtSuprtMonth()    { return chageDcAmtSuprtMonth; }
    public String getChageDcAmtSuprtRtrnAmt()  { return chageDcAmtSuprtRtrnAmt; }
}
