package com.ktmmobile.msf.domains.form.common.mspservice.dto;

import java.sql.Date;
import java.io.Serializable;

import com.ktmmobile.msf.domains.form.common.dto.MspSalePlcyMstDto;


/**
 * @Class Name : MspSaleOrgnMstDto
 * @Description :
 * MSP 의 MSP_SALE_ORGN_MST 테이블과 대응되는 DTO 이다.
 * 조직별 정책등록정보를 가지고있다.
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
public class MspSaleOrgnMstDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 판매정책정보 */
    private MspSalePlcyMstDto mspSalePlcyMstDto;

    /** 판매정책코드 */
    private String salePlcyCd;

    /** 조직id */
    private String orgnId;

    /** 등록자id */
    private String regstId;

    /** 등록일시 */
    private Date regstDttm;

    /** 수정자id */
    private String rvisnId;

    /** 수정일시  */
    private Date rvisnDttm;

    public MspSalePlcyMstDto getMspSalePlcyMstDto() {
        return mspSalePlcyMstDto;
    }

    public void setMspSalePlcyMstDto(MspSalePlcyMstDto mspSalePlcyMstDto) {
        this.mspSalePlcyMstDto = mspSalePlcyMstDto;
    }

    public String getSalePlcyCd() {
        return salePlcyCd;
    }

    public void setSalePlcyCd(String salePlcyCd) {
        this.salePlcyCd = salePlcyCd;
    }

    public String getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
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
}
