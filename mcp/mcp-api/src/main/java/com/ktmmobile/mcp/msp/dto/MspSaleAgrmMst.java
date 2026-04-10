package com.ktmmobile.mcp.msp.dto;

import java.sql.Date;
import java.io.Serializable;


/**
 * @Class Name : MspSaleAgrmMst
 * @Description : MSP 정책별 약정개월수 정보 (MSP_SALE_AGRM_MST 테이블)
 *				  MSP 정책별 할부기간정보랑 같이 사용한다.(ORG_INST_NOM_MST 테이블)
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
public class MspSaleAgrmMst implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 정책코드 */
    private String salePlcyCd;

    /** 약정기간 */
    private String agrmTrm;

    /** 약정기간 label */
    private String agrmTrmLabel;

    /** 할부기간  */
    private String instNom;

    /** 등록자id */
    private String regstId;

    /** 등록일시  */
    private Date regstDttm;

    /** 수정자id */
    private String rvisnId;

    /** 수정일시  */
    private Date rvisnDttm;

    /** 조직코드 */
    private String orgnId;

    public String getAgrmTrmLabel() {
        if ("0".equals(agrmTrm)) {
            agrmTrmLabel = "무약정";
        } else {
            agrmTrmLabel = agrmTrm + "개월";
        }
        return agrmTrmLabel;
    }

    public String getSalePlcyCd() {
        return salePlcyCd;
    }

    public void setSalePlcyCd(String salePlcyCd) {
        this.salePlcyCd = salePlcyCd;
    }

    public String getAgrmTrm() {
        return agrmTrm;
    }

    public void setAgrmTrm(String agrmTrm) {
        this.agrmTrm = agrmTrm;
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


    public String getInstNom() {
        return instNom;
    }

    public void setInstNom(String instNom) {
        this.instNom = instNom;
    }

    public String getOrgnId() {
        return orgnId;
    }
    
    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }
}
