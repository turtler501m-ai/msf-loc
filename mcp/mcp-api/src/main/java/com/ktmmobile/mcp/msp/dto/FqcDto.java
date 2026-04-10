package com.ktmmobile.mcp.msp.dto;

import java.io.Serializable;

public class FqcDto implements Serializable{
    private static final long serialVersionUID = 1L;

    private String fqcPlcyCd;         	// 프리퀀시 정책 코드 (ex FQC0001)
    private String fqcPlcyNm;         	// 프리퀀시 제목
    private String fqcPlcyDesc;       	// 프리퀀시 설명
    private String fqcTrgtCust;       	// 참여 대상
    private String fqcPlanInclYn;     	// 요금제 포함 여부
    private String strtDttm;          	// 시작일
    private String endDttm;           	// 종료일
    private String stateCode;         	// 프리퀀시 상태값 (A: 활성, C: 완료, D: 삭제)
    private String socCode;			  	// 요금제 코드
    private String rateNm;				// 요금제 명
    private String baseAmt;				// 요금제 가격
    private String fqcPlcyMsnCd;	  	// 프리퀀시 정책미션 코드 (ex FQC0001_01)
    private String fqcPlcyMsnNm;		// 프리퀀시 미션 내용
    private String msnTpCd;			  	// 미션 종류 코드 (ex 01:신규가입, 02:이벤트공유, 03:친구초대)
    private int msnCnt;			  	  	// 미션 수행 건수
    private String bnfCd;			  	// 혜택코드
    private int bnfLvl;			  	  	// 혜택레벨
    private String bnfNm;				// 혜택내용
    private int skipResult;
    private int maxResult;
    private String searchCategory;	  		// 검색조건
    private String searchValue;	      		// 검색 값
    private String strtDt;					// 프리퀀시 참여 시작일
    private String endDt;					// 프리퀀시 참여 종료일
    private String userId;					// 프리퀀시 회원 아이디
    private String contractNum;				// 프맄뭔시 회원 계약번호
    private String name;					// 프리퀀시 회원 이름
    private String msnStatus;				// 프리퀀시 회원 미션 참여상태 (ex OOXOOX)
    private String lstComActvDate;			// 프리퀀시 회원 개통일
    private String subStatus;				// 프리퀀시 회원 회선상태

    /*엑셀 다운로드*/
    private String commendId;				// 추천인 ID
    private String cntrMobileNo;			// 연락처
    private String reqInday;				// 신청일자
    private String fstRateCd;				// 최초요금제 코드
    private String lstRateCd;				// 현재요금제 코드
    private String orgnNm;					// 대리점
    private String usimOrgNm; 				// 유심접점
    private String moveCompany;				// 이동전 통신사
    private String reviewRegDt;				// 사용후기 작성일자
    private String ktRegDt;					// KT 유선 가입일자
    private String ktOpenDt;				// KT 유선 설치완료일자
    private String mrkStrtDttm;				// 마케팅 수신 동의 일자
    private String fstRateNm;				// 최초요금제 명
    private String lstRateNm;				// 현재요금제 명
    private String sysRdateDd;				// 참여일자

    public String getFqcPlcyCd() {
        return fqcPlcyCd;
    }
    public void setFqcPlcyCd(String fqcPlcyCd) {
        this.fqcPlcyCd = fqcPlcyCd;
    }
    public String getFqcPlcyNm() {
        return fqcPlcyNm;
    }
    public void setFqcPlcyNm(String fqcPlcyNm) {
        this.fqcPlcyNm = fqcPlcyNm;
    }
    public String getFqcPlcyDesc() {
        return fqcPlcyDesc;
    }
    public void setFqcPlcyDesc(String fqcPlcyDesc) {
        this.fqcPlcyDesc = fqcPlcyDesc;
    }
    public String getFqcTrgtCust() {
        return fqcTrgtCust;
    }
    public void setFqcTrgtCust(String fqcTrgtCust) {
        this.fqcTrgtCust = fqcTrgtCust;
    }
    public String getFqcPlanInclYn() {
        return fqcPlanInclYn;
    }
    public void setFqcPlanInclYn(String fqcPlanInclYn) {
        this.fqcPlanInclYn = fqcPlanInclYn;
    }
    public String getStrtDttm() {
        return strtDttm;
    }
    public void setStrtDttm(String strtDttm) {
        this.strtDttm = strtDttm;
    }
    public String getEndDttm() {
        return endDttm;
    }
    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }
    public String getStateCode() {
        return stateCode;
    }
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
    public String getSocCode() {
        return socCode;
    }
    public void setSocCode(String socCode) {
        this.socCode = socCode;
    }
    public String getRateNm() {
        return rateNm;
    }
    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }
    public String getBaseAmt() {
        return baseAmt;
    }
    public void setBaseAmt(String baseAmt) {
        this.baseAmt = baseAmt;
    }
    public String getFqcPlcyMsnCd() {
        return fqcPlcyMsnCd;
    }
    public void setFqcPlcyMsnCd(String fqcPlcyMsnCd) {
        this.fqcPlcyMsnCd = fqcPlcyMsnCd;
    }
    public String getFqcPlcyMsnNm() {
        return fqcPlcyMsnNm;
    }
    public void setFqcPlcyMsnNm(String fqcPlcyMsnNm) {
        this.fqcPlcyMsnNm = fqcPlcyMsnNm;
    }
    public String getMsnTpCd() {
        return msnTpCd;
    }
    public void setMsnTpCd(String msnTpCd) {
        this.msnTpCd = msnTpCd;
    }
    public int getMsnCnt() {
        return msnCnt;
    }
    public void setMsnCnt(int msnCnt) {
        this.msnCnt = msnCnt;
    }
    public String getBnfCd() {
        return bnfCd;
    }
    public void setBnfCd(String bnfCd) {
        this.bnfCd = bnfCd;
    }
    public int getBnfLvl() {
        return bnfLvl;
    }
    public void setBnfLvl(int bnfLvl) {
        this.bnfLvl = bnfLvl;
    }
    public String getBnfNm() {
        return bnfNm;
    }
    public void setBnfNm(String bnfNm) {
        this.bnfNm = bnfNm;
    }
    public int getSkipResult() {
        return skipResult;
    }
    public void setSkipResult(int skipResult) {
        this.skipResult = skipResult;
    }
    public int getMaxResult() {
        return maxResult;
    }
    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }
    public String getSearchCategory() {
        return searchCategory;
    }
    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }
    public String getSearchValue() {
        return searchValue;
    }
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    public String getStrtDt() {
        return strtDt;
    }
    public void setStrtDt(String strtDt) {
        this.strtDt = strtDt;
    }
    public String getEndDt() {
        return endDt;
    }
    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMsnStatus() {
        return msnStatus;
    }
    public void setMsnStatus(String msnStatus) {
        this.msnStatus = msnStatus;
    }
    public String getLstComActvDate() {
        return lstComActvDate;
    }
    public void setLstComActvDate(String lstComActvDate) {
        this.lstComActvDate = lstComActvDate;
    }
    public String getSubStatus() {
        return subStatus;
    }
    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }
    public String getCommendId() {
        return commendId;
    }
    public void setCommendId(String commendId) {
        this.commendId = commendId;
    }
    public String getCntrMobileNo() {
        return cntrMobileNo;
    }
    public void setCntrMobileNo(String cntrMobileNo) {
        this.cntrMobileNo = cntrMobileNo;
    }
    public String getReqInday() {
        return reqInday;
    }
    public void setReqInday(String reqInday) {
        this.reqInday = reqInday;
    }
    public String getFstRateCd() {
        return fstRateCd;
    }
    public void setFstRateCd(String fstRateCd) {
        this.fstRateCd = fstRateCd;
    }
    public String getLstRateCd() {
        return lstRateCd;
    }
    public void setLstRateCd(String lstRateCd) {
        this.lstRateCd = lstRateCd;
    }
    public String getOrgnNm() {
        return orgnNm;
    }
    public void setOrgnNm(String orgnNm) {
        this.orgnNm = orgnNm;
    }
    public String getUsimOrgNm() {
        return usimOrgNm;
    }
    public void setUsimOrgNm(String usimOrgNm) {
        this.usimOrgNm = usimOrgNm;
    }
    public String getMoveCompany() {
        return moveCompany;
    }
    public void setMoveCompany(String moveCompany) {
        this.moveCompany = moveCompany;
    }
    public String getReviewRegDt() {
        return reviewRegDt;
    }
    public void setReviewRegDt(String reviewRegDt) {
        this.reviewRegDt = reviewRegDt;
    }
    public String getKtRegDt() {
        return ktRegDt;
    }
    public void setKtRegDt(String ktRegDt) {
        this.ktRegDt = ktRegDt;
    }
    public String getKtOpenDt() {
        return ktOpenDt;
    }
    public void setKtOpenDt(String ktOpenDt) {
        this.ktOpenDt = ktOpenDt;
    }
    public String getMrkStrtDttm() {
        return mrkStrtDttm;
    }
    public void setMrkStrtDttm(String mrkStrtDttm) {
        this.mrkStrtDttm = mrkStrtDttm;
    }
    public String getFstRateNm() {
        return fstRateNm;
    }
    public void setFstRateNm(String fstRateNm) {
        this.fstRateNm = fstRateNm;
    }
    public String getLstRateNm() {
        return lstRateNm;
    }
    public void setLstRateNm(String lstRateNm) {
        this.lstRateNm = lstRateNm;
    }
    public String getSysRdateDd() {
        return sysRdateDd;
    }
    public void setSysRdateDd(String sysRdateDd) {
        this.sysRdateDd = sysRdateDd;
    }

}
