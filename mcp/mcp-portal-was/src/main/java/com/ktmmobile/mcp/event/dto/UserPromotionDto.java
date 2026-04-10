package com.ktmmobile.mcp.event.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ktmmobile.mcp.common.util.MaskingUtil;

public class UserPromotionDto implements Serializable {


    private static final long serialVersionUID = 1L;

    /** 회원가입 프로모션 일련번호 MCP_USER_PROMOTION_SEQ SEQUENCE */

    private long userPromoId;

    /** 아이디 */

    private String userId;

    /** 관심사 코드  01, 02 .... */
    private String interestCode;

    /** 프로모션 코드 공통코드  01, 02 .... */

    private String promotionCode;


    /** 이용중 통신사 정보 공통코드 NSC  SKT : SKT ,  LGT : LG U+  ,  KTF : KT ,   ETC : 타알뜰폰(공통코드 없음) */

    private String useTelCode;


    /** 당첨여부 Y : 당첨 N : 미당첨 */

    private String prizeYn;


    /** 경품명 */

    private String prizenm;


    /** 결과발표일 */

    private Date rsltAndt;



    /** 생성일 */

    private String cretDd;


    /** 생성자ID */

    private String cretId;


    /** 수정자ID */

    private String amdId;


    /** 생성일시 */

    private Date cretDt;


    /** 수정일시 */

    private Date amdDt;

    /** 등록아이피 */

    private String rip;

    /** 프로모션 명 */
    private String dtlCdNm;

    public long getUserPromoId() {
        return userPromoId;
    }

    public void setUserPromoId(long userPromoId) {
        this.userPromoId = userPromoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getUseTelCode() {
        return useTelCode;
    }

    public void setUseTelCode(String useTelCode) {
        this.useTelCode = useTelCode;
    }

    public String getPrizeYn() {
        return prizeYn;
    }

    public void setPrizeYn(String prizeYn) {
        this.prizeYn = prizeYn;
    }

    public String getPrizenm() {
        return prizenm;
    }

    public void setPrizenm(String prizenm) {
        this.prizenm = prizenm;
    }

    public Date getRsltAndt() {
        return rsltAndt;
    }

    public void setRsltAndt(Date rsltAndt) {
        this.rsltAndt = rsltAndt;
    }

    public String getCretDd() {
        return cretDd;
    }

    public void setCretDd(String cretDd) {
        this.cretDd = cretDd;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getAmdId() {
        return amdId;
    }

    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }

    public Date getCretDt() {
        return cretDt;
    }

    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }

    public Date getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
    }

    public String getInterestCode() {
        return interestCode;
    }

    public void setInterestCode(String interestCode) {
        this.interestCode = interestCode;
    }

    public String getDtlCdNm() {
        return dtlCdNm;
    }

    public void setDtlCdNm(String dtlCdNm) {
        this.dtlCdNm = dtlCdNm;
    }


}
