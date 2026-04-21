package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestAgrmDto.java
 * 날짜     : 2019. 10. 2. 오후 5:52:42
 * 작성자   : papier
 * 설명     : 재약정 요청 정보  테이블(MCP_REQUEST_AGRM)
 * </pre>
 */
public class McpRequestAgrmDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 계약번호 */
    private String contractNum;
    /** 일련번호 */
    private long seq;
    /** mplatform 연동 globalNo */
    private String globalNo;
    /** 배송정보_이름 */
    private String dlvryName;
    /** 배송정보_휴대폰번호_앞자리 */
    private String dlvryMobileFn;
    /** 배송정보_휴대폰번호_중간자리 */
    private String dlvryMobileMn;
    /** 배송정보_휴대폰번호_뒷자리 */
    private String dlvryMobileRn;
    /** 배송정보_우편번호 */
    private String dlvryPost;
    /** 배송정보_주소 */
    private String dlvryAddr;
    /** 배송정보_상세주소 */
    private String dlvryAddrDtl;
    /** 사은품 상품 코드_공통코드_presentCode */
    private String presentCode;
    /** 등록일 */
    private Date regstDttm;
    /** 수정일 */
    private Date rvisnDttm;
    /** 01:마이페에지에서 신청, 02:sms발송으로 신청 */
    private String orderType;



    public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public long getSeq() {
        return seq;
    }
    public void setSeq(long seq) {
        this.seq = seq;
    }
    public String getGlobalNo() {
        return globalNo;
    }
    public void setGlobalNo(String globalNo) {
        this.globalNo = globalNo;
    }
    public String getDlvryName() {
        return dlvryName;
    }
    public void setDlvryName(String dlvryName) {
        this.dlvryName = dlvryName;
    }
    public String getDlvryMobileFn() {
        return dlvryMobileFn;
    }
    public void setDlvryMobileFn(String dlvryMobileFn) {
        this.dlvryMobileFn = dlvryMobileFn;
    }
    public String getDlvryMobileMn() {
        return dlvryMobileMn;
    }
    public void setDlvryMobileMn(String dlvryMobileMn) {
        this.dlvryMobileMn = dlvryMobileMn;
    }
    public String getDlvryMobileRn() {
        return dlvryMobileRn;
    }
    public void setDlvryMobileRn(String dlvryMobileRn) {
        this.dlvryMobileRn = dlvryMobileRn;
    }
    public String getDlvryPost() {
        return dlvryPost;
    }
    public void setDlvryPost(String dlvryPost) {
        this.dlvryPost = dlvryPost;
    }
    public String getDlvryAddr() {
        return dlvryAddr;
    }
    public void setDlvryAddr(String dlvryAddr) {
        this.dlvryAddr = dlvryAddr;
    }
    public String getDlvryAddrDtl() {
        return dlvryAddrDtl;
    }
    public void setDlvryAddrDtl(String dlvryAddrDtl) {
        this.dlvryAddrDtl = dlvryAddrDtl;
    }
    public String getPresentCode() {
        return presentCode;
    }
    public void setPresentCode(String presentCode) {
        this.presentCode = presentCode;
    }
    public Date getRegstDttm() {
        return regstDttm;
    }
    public void setRegstDttm(Date regstDttm) {
        this.regstDttm = regstDttm;
    }
    public Date getRvisnDttm() {
        return rvisnDttm;
    }
    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }




}
