package com.ktmmobile.mcp.msp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;


/**
 * @Class Name : MspSalePlcyMstDto
 * @Description :
 * MSP 의 MSP_SALE_PLCY_MST 테이블과 대응되는 DTO 이다.
 * 판매정책 마스터 정보로
 * 	판매정책 적용기간, 가입유형,할부이자율,지원금유형등의 정보들을 포함하고있다.
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
public class MspSalePlcyMstDto implements Serializable {

    /** 요금제리스트 */
    private List<MspRateMstDto> usimMspRateMstList;

    /** 가입유형리스트 */
    private List<MspPlcyOperTypeDto> usimMspPlcyOperTypeList;

    /** 조직코드 */
    private String orgnId;

    /** 판매정책코드 */
    private String salePlcyCd;

    /** 판매정책명 */
    private String salePlcyNm;

    /** 판매시작일시 */
    private String saleStrtDttm;

    /** 판매종료일시 */
    private String saleEndDttm;

    /** 정책유형코드(D:온라인) */
    private String plcyTypeCd;

    /** 조직유형 */
    private String orgnType;

    /** 정책구분코드(01:단말,02:유심) */
    private String plcySctnCd;

    /** 제품구분코드(02:3G , 03:LTE) */
    private String prdtSctnCd;

    /** 적용구분코드 (O:개통일, R:접수일) */
    private String applSctnCd;

    /** 할부이자율 */
    private BigDecimal instRate;

    /** 신규여부 NAC */
    private String newYn;

    /** 번호이동여부  MNP */
    private String mnpYn;

    /** 일반 기변여부 HCN */
    private String hcnYn;

    /** 우수 기변여부 HDN */
    private String hdnYn;

    /** 확정여부 */
    private String cnfmYn;

    /** 확정자 ID*/
    private String cnfmId;

    /** 확정일시 */
    private String cnfmDttm;

    /** 등록자ID */
    private String regstId;

    /**등록일시 */
    private Date regstDttm;

    /** 수정자ID */
    private String rvisnId;

    /** 수정일시 */
    private Date rvisnDttm;

    /** 약정기간 */
    private String agrmTrm;

    /** 선후불구분 */
      private String payClCd;

    /** 지원금유형 (단말할인:KD ,요금할인:PM) */
    private String sprtTp;

    /** NRDS(단품코드) */
    private String prdtId;

    private String noArgmYn;

    private String operType;

    public String getOrgnId() {
        return orgnId;
    }
    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }
    public String getSalePlcyCd() {
        return salePlcyCd;
    }
    public void setSalePlcyCd(String salePlcyCd) {
        this.salePlcyCd = salePlcyCd;
    }
    public String getSalePlcyNm() {
        return salePlcyNm;
    }
    public void setSalePlcyNm(String salePlcyNm) {
        this.salePlcyNm = salePlcyNm;
    }
    public String getSaleStrtDttm() {
        return saleStrtDttm;
    }
    public void setSaleStrtDttm(String saleStrtDttm) {
        this.saleStrtDttm = saleStrtDttm;
    }
    public String getSaleEndDttm() {
        return saleEndDttm;
    }
    public void setSaleEndDttm(String saleEndDttm) {
        this.saleEndDttm = saleEndDttm;
    }
    public String getPlcyTypeCd() {
        return plcyTypeCd;
    }
    public void setPlcyTypeCd(String plcyTypeCd) {
        this.plcyTypeCd = plcyTypeCd;
    }
    public String getOrgnType() {
        return orgnType;
    }
    public void setOrgnType(String orgnType) {
        this.orgnType = orgnType;
    }
    public String getPlcySctnCd() {
        return plcySctnCd;
    }
    public void setPlcySctnCd(String plcySctnCd) {
        this.plcySctnCd = plcySctnCd;
    }
    public String getPrdtSctnCd() {
        return prdtSctnCd;
    }
    public void setPrdtSctnCd(String prdtSctnCd) {
        this.prdtSctnCd = prdtSctnCd;
    }
    public String getApplSctnCd() {
        return applSctnCd;
    }
    public void setApplSctnCd(String applSctnCd) {
        this.applSctnCd = applSctnCd;
    }

    public BigDecimal getInstRate() {
        return instRate;
    }
    public void setInstRate(BigDecimal instRate) {
        this.instRate = instRate;
    }
    public String getNewYn() {
        return newYn;
    }
    public void setNewYn(String newYn) {
        this.newYn = newYn;
    }
    public String getMnpYn() {
        return mnpYn;
    }
    public void setMnpYn(String mnpYn) {
        this.mnpYn = mnpYn;
    }
    public String getHcnYn() {
        return hcnYn;
            }
    public void setHcnYn(String hcnYn) {
        this.hcnYn = hcnYn;
    }

    public String getHdnYn() {
        return hdnYn;
    }
    public void setHdnYn(String hdnYn) {
        this.hdnYn = hdnYn;
    }
    public String getCnfmYn() {
        return cnfmYn;
    }
    public void setCnfmYn(String cnfmYn) {
        this.cnfmYn = cnfmYn;
    }
    public String getCnfmId() {
        return cnfmId;
    }
    public void setCnfmId(String cnfmId) {
        this.cnfmId = cnfmId;
    }
    public String getCnfmDttm() {
        return cnfmDttm;
    }
    public void setCnfmDttm(String cnfmDttm) {
        this.cnfmDttm = cnfmDttm;
    }
    public String getRegstId() {
        return regstId;
    }
    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }
    public Date getRegstDttm() {
        return regstDttm;
    }
    public void setRegstDttm(Date regstDttm) {
        this.regstDttm = regstDttm;
    }
    public String getRvisnId() {
        return rvisnId;
    }
    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }
    public Date getRvisnDttm() {
        return rvisnDttm;
    }
    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }
    public String getSprtTp() {
        return sprtTp;
    }
    public void setSprtTp(String sprtTp) {
        this.sprtTp = sprtTp;
    }
    public String getPrdtId() {
        return prdtId;
    }
    public void setPrdtId(String prdtId) {
        this.prdtId = prdtId;
    }
    public String getAgrmTrm() {
        return agrmTrm;
    }
    public void setAgrmTrm(String agrmTrm) {
        this.agrmTrm = agrmTrm;
    }
    public String getPayClCd() {
        return payClCd;
    }
    public void setPayClCd(String payClCd) {
        this.payClCd = payClCd;
    }
    public List<MspRateMstDto> getUsimMspRateMstList() {
        return usimMspRateMstList;
    }
    public void setUsimMspRateMstList(List<MspRateMstDto> usimMspRateMstList) {
        this.usimMspRateMstList = usimMspRateMstList;
    }
    public List<MspPlcyOperTypeDto> getUsimMspPlcyOperTypeList() {
        return usimMspPlcyOperTypeList;
    }
    public void setUsimMspPlcyOperTypeList(
            List<MspPlcyOperTypeDto> usimMspPlcyOperTypeList) {
        this.usimMspPlcyOperTypeList = usimMspPlcyOperTypeList;
    }
    public String getNoArgmYn() {
        return noArgmYn;
    }
    public void setNoArgmYn(String noArgmYn) {
        this.noArgmYn = noArgmYn;
    }
    public String getOperType() {
        return operType;
    }
    public void setOperType(String operType) {
        this.operType = operType;
    }

}
