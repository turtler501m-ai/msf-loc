package com.ktmmobile.mcp.event.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ktmmobile.mcp.common.util.MaskingUtil;

public class EventJoinDto implements Serializable {


    private static final long serialVersionUID = 1L;

    /** 이벤트 참여 프로모션 일련번호 SQ_REVIEW_PROMOTION_SEQ SEQUENCE */

    private long reviewPromotionSeq;

    /** 프로모션 코드 공통코드 EventJoinInfo 01, 02 .... */

    private String promotionCode;

    /** 회원구분 01 : 정회원 02:준회원 99:비회원 */

    private String userDivision;

    /** 계약번호 */

    private String contractNum;

    /** 회원아이디_등록 아이디 */

    private String userId;

    /** 이용중 통신사 정보 공통코드 NSC  SKT : SKT ,  LGT : LG U+  ,  KTF : KT ,   ETC : 타알뜰폰(공통코드 없음) */

    private String useTelCode;

    /** 리뷰 내용 */

    private String reviewContent;

    /** 노출여부 Y : 표현 N : 미표현  */

    private String showYn;

    /** 당첨여부 Y : 당첨 N : 미당첨 */

    private String prizeYn;

    /** 정렬순서 */

    private long indcOdrg;

    /** 등록일 */

    private String sysRdt;

    /** 등록일시 */

    private Date sysRdate;

    /** 등록자아이피 */

    private String rip;

    /** 수정자ID */

    private String rvisnId;

    /** 수정일시 */

    private Date rvisnDttm;

    /** 수정자아이피 */

    private String uip;

    /** 계약번호 리스트 */

    private List<String> contractNumList;

    /** 인증 성명 */
    private String name;
    private String mkRegNm;

    /** 이벤트명 */
    private String dtlCdNm;

    /** 인증 휴대폰번호 */
    private String mobileNo;

    /** DUP_INFO */
    private String dupInfo;

    /** 공유횟수 */
    private int shareCnt;

    /** 일련번호 기본키 */
    private String reqSeq;

    /** 요청 일련번호 */
    private String resSeq;

    /** 사용여부(어드민제어)*/
    private String useYn;

    /** 상위그룹코드 */
    private String upGrpCd;

    /** CI */
    private String connInfo;

    /** 생년월일 */
    private String birthDate;

    public long getReviewPromotionSeq() {
        return reviewPromotionSeq;
    }

    public void setReviewPromotionSeq(long reviewPromotionSeq) {
        this.reviewPromotionSeq = reviewPromotionSeq;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getUserDivision() {
        return userDivision;
    }

    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUseTelCode() {
        return useTelCode;
    }

    public void setUseTelCode(String useTelCode) {
        this.useTelCode = useTelCode;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getShowYn() {
        return showYn;
    }

    public void setShowYn(String showYn) {
        this.showYn = showYn;
    }

    public String getPrizeYn() {
        return prizeYn;
    }

    public void setPrizeYn(String prizeYn) {
        this.prizeYn = prizeYn;
    }

    public long getIndcOdrg() {
        return indcOdrg;
    }

    public void setIndcOdrg(long indcOdrg) {
        this.indcOdrg = indcOdrg;
    }

    public String getSysRdt() {
        return sysRdt;
    }

    public void setSysRdt(String sysRdt) {
        this.sysRdt = sysRdt;
    }

    public Date getSysRdate() {
        return sysRdate;
    }

    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
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

    public String getUip() {
        return uip;
    }

    public void setUip(String uip) {
        this.uip = uip;
    }

    public List<String> getContractNumList() {
        return contractNumList;
    }

    public void setContractNumList(List<String> contractNumList) {
        this.contractNumList = contractNumList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDtlCdNm() {
        return dtlCdNm;
    }

    public void setDtlCdNm(String dtlCdNm) {
        this.dtlCdNm = dtlCdNm;
    }

    public String getMkRegNm() {
        return mkRegNm +" 님";
    }
    public void setMkRegNm(String mkRegNm) {
        this.mkRegNm = MaskingUtil.getMaskedName(mkRegNm);
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDupInfo() {
        return dupInfo;
    }

    public void setDupInfo(String dupInfo) {
        this.dupInfo = dupInfo;
    }

    public int getShareCnt() {
        return shareCnt;
    }

    public void setShareCnt(int shareCnt) {
        this.shareCnt = shareCnt;
    }

    public String getReqSeq() {
        return reqSeq;
    }

    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }

    public String getResSeq() {
        return resSeq;
    }

    public void setResSeq(String resSeq) {
        this.resSeq = resSeq;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getUpGrpCd() {
        return upGrpCd;
    }

    public void setUpGrpCd(String upGrpCd) {
        this.upGrpCd = upGrpCd;
    }

    public String getConnInfo() {
        return connInfo;
    }

    public void setConnInfo(String connInfo) {
        this.connInfo = connInfo;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

}
