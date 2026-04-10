package com.ktis.msp.org.grpcdmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : GrpCdMgmtVO
 * @Description : 공통 코드 업무 VO
 * @
 * @ 수정일       수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @2014.08.28  고은정 최초생성
 * @
 * @author : 고은정
 * @Create Date : 2014. 8. 28.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="grpCdMgmtVO")
public class GrpCdMgmtVO extends BaseVo implements Serializable{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2497114198411602808L;
    
    private String grpId; /** 그룹 ID */
    private String cdVal; /** 코드 값 */
    private String cdValOld; /** 코드 값 */
    private String cdDsc; /** 코드 내용 */
    private String usgYn; /** 사용 여부 */
    private String regstId; /** 등록자 ID */
    private String rvisnId; /** 수정자 ID */
    private String etc1; /** 기타1 */
    private String etc2; /** 기타2 */
    private String etc3; /** 기타3 */
    private String etc4; /** 기타4 */
    private String etc5; /** 기타5 */
    private String etc6; /** 기타6 */
    
    public String getGrpId() {
        return grpId;
    }
    public void setGrpId(String grpId) {
        this.grpId = grpId;
    }
    public String getCdVal() {
        return cdVal;
    }
    public void setCdVal(String cdVal) {
        this.cdVal = cdVal;
    }
    public String getCdValOld() {
        return cdValOld;
    }
    public void setCdValOld(String cdValOld) {
        this.cdValOld = cdValOld;
    }
    public String getCdDsc() {
        return cdDsc;
    }
    public void setCdDsc(String cdDsc) {
        this.cdDsc = cdDsc;
    }
    public String getUsgYn() {
        return usgYn;
    }
    public void setUsgYn(String usgYn) {
        this.usgYn = usgYn;
    }
    public String getRegstId() {
        return regstId;
    }
    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }
    public String getRvisnId() {
        return rvisnId;
    }
    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }
    public String getEtc1() {
        return etc1;
    }
    public void setEtc1(String etc1) {
        this.etc1 = etc1;
    }
    public String getEtc2() {
        return etc2;
    }
    public void setEtc2(String etc2) {
        this.etc2 = etc2;
    }
    public String getEtc3() {
        return etc3;
    }
    public void setEtc3(String etc3) {
        this.etc3 = etc3;
    }
    public String getEtc4() {
        return etc4;
    }
    public void setEtc4(String etc4) {
        this.etc4 = etc4;
    }
    public String getEtc5() {
        return etc5;
    }
    public void setEtc5(String etc5) {
        this.etc5 = etc5;
    }
    public String getEtc6() {
        return etc6;
    }
    public void setEtc6(String etc6) {
        this.etc6 = etc6;
    }
    
    
    
}
