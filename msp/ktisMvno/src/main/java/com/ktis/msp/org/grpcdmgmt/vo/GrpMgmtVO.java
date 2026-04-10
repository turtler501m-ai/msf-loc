package com.ktis.msp.org.grpcdmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : GrpMgmtVO
 * @Description : 공통 코드 상세 업무 VO
 * @
 * @ 수정일       수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.28   고은정 최초생성
 * @
 * @author : 고은정
 * @Create Date : 2014. 8. 28.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="grpMgmtVO")
public class GrpMgmtVO extends BaseVo implements Serializable{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2497114198411602808L;
    
    private String grpId; /** 그룹 ID */
    private String grpKorNm; /** 그룹 한글명 */
    private String grpEngNm; /** 그룹 영어명 */
    private String grpDsc; /** 그룹 설명 */
    private String usgYn; /** 사용 여부 */
    private String colKorNm; /** 컬럼 한글명 */
    private String colEngNm; /** 컬럼 영어명 */
    private String dutySctn; /** 업무 구분 */
    private String dutySctnNm; /** 업무 구분 */
    private String remrk; /** 비고 */
    private String regstId; /** 등록자 ID */
    private String rvisnId; /** 수정자 ID */
    public String getGrpId() {
        return grpId;
    }
    public void setGrpId(String grpId) {
        this.grpId = grpId;
    }
    public String getGrpKorNm() {
        return grpKorNm;
    }
    public void setGrpKorNm(String grpKorNm) {
        this.grpKorNm = grpKorNm;
    }
    public String getGrpEngNm() {
        return grpEngNm;
    }
    public void setGrpEngNm(String grpEngNm) {
        this.grpEngNm = grpEngNm;
    }
    public String getGrpDsc() {
        return grpDsc;
    }
    public void setGrpDsc(String grpDsc) {
        this.grpDsc = grpDsc;
    }
    public String getUsgYn() {
        return usgYn;
    }
    public void setUsgYn(String usgYn) {
        this.usgYn = usgYn;
    }
    public String getColKorNm() {
        return colKorNm;
    }
    public void setColKorNm(String colKorNm) {
        this.colKorNm = colKorNm;
    }
    public String getColEngNm() {
        return colEngNm;
    }
    public void setColEngNm(String colEngNm) {
        this.colEngNm = colEngNm;
    }
    public String getDutySctn() {
        return dutySctn;
    }
    public void setDutySctn(String dutySctn) {
        this.dutySctn = dutySctn;
    }
    
    public String getDutySctnNm() {
        return dutySctnNm;
    }
    public void setDutySctnNm(String dutySctnNm) {
        this.dutySctnNm = dutySctnNm;
    }
    public String getRemrk() {
        return remrk;
    }
    public void setRemrk(String remrk) {
        this.remrk = remrk;
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
    
    
}
