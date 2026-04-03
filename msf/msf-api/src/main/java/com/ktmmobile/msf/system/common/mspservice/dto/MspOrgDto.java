package com.ktmmobile.msf.system.common.mspservice.dto;

import java.io.Serializable;
/**
 * @Class Name : MspOrgDto
 * @Description : MSP의 제조자공급사 정보
 *
 * @author : ant
 * @Create Date : 2016. 1. 4.
 */
public class MspOrgDto implements Serializable {
    private static final long serialVersionUID = 1L;

      /** 제조사ID */
      private String mnfctId;

      /** 제조사명 */
      private String mnfctNm;

      /** 사업자등록번호 */
      private String bizRegNum;

      /** 대표자명 */
      private String rprsenNm;

      /** 우편번호 */
      private String zipCd;

      /** 주소 */
      private String addr;

      /** 상세 주소*/
      private String dtlAddr;

      /** 제조사여부 아닐경우(공급사)*/
      private String mnfctYn;

      /** 전화번호 */
      private String telnum;

      /** 팩스번호 */
      private String fax;

      /** 이메일 */
      private String email;

    public String getMnfctId() {
        return mnfctId;
    }

    public void setMnfctId(String mnfctId) {
        this.mnfctId = mnfctId;
    }

    public String getMnfctNm() {
        return mnfctNm;
    }

    public void setMnfctNm(String mnfctNm) {
        this.mnfctNm = mnfctNm;
    }

    public String getBizRegNum() {
        return bizRegNum;
    }

    public void setBizRegNum(String bizRegNum) {
        this.bizRegNum = bizRegNum;
    }

    public String getRprsenNm() {
        return rprsenNm;
    }

    public void setRprsenNm(String rprsenNm) {
        this.rprsenNm = rprsenNm;
    }

    public String getZipCd() {
        return zipCd;
    }

    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDtlAddr() {
        return dtlAddr;
    }

    public void setDtlAddr(String dtlAddr) {
        this.dtlAddr = dtlAddr;
    }

    public String getMnfctYn() {
        return mnfctYn;
    }

    public void setMnfctYn(String mnfctYn) {
        this.mnfctYn = mnfctYn;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
