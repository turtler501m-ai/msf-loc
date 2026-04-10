package com.ktmmobile.mcp.telcounsel.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * @Class Name : TelCounselDto
 * @Description : 전화상담 기본정보
 *
 * @author : ant
 * @Create Date : 2016. 3. 4.
 */
public class TelCounselDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 전화상담 상세 리스트 */
    List<TelCounselDtlDto> telCounselDtlDtoList;

    /** 마지막 전화상담 상세 */
    TelCounselDtlDto telCounselDtlDto;

    /** 전화상담일련번호 */
    private int counselSeq;

    /** 전화상담카테고리구분(핸드폰:01,유심:02) */
    private String counselCtg;

    /** 상담신청자이름 */
    private String counselNm;

    /** 상담신청자 전화번호 */
    private String mobileNo;

    /** 작성자id */
    private String cretId;

    /** 수정자id */
    private String amdId;

    /** 작성일 */
    private Timestamp cretDt;

    /** 수정일  */
    private Timestamp amdDt;

    /** 지역코드(공통코드) */
    private String regionCd;

    /** 연령코드(공통코드) */
    private String ageCd;

    /** 성별코드 (남자:01,여자:02,그외:03) */
    private String genderCd;

    /** 검색값 구분 */
    private String searchCategory;

    /** 검색 키워드 */
    private String searchValue;

    /** 작성일(검색용) */
    private String searchDtReq;

    /** 답변일(검색용) */
    private String searchDtAnswer;

    /** MSP 프로시저 호출시에 풀텍스 전달을 위한 text*/
    private String fullTextForMsp;

    /** 마지막 전화상담상세 일련번호 */
    private String lastCounselDtlSeq;

    /** 마지막 전화상담 상태코드 (검색용) */
    private String replyStatCd;

    /** 캡챠 입력값 */
    private String answer;
    
    /** 가입유형 
     * MNP3 : 번호이동
     * NAC3 : 신규
     * */
    private String operType="";    
    /** 요금제명 */ 
    private String rateNm="";
    /** 유심번호 */
    private String reqUsimSn="";
    /** 번호이동 전화번호 */
    private String moveMobileNo="";
    
    
    public String getMoveMobileNo() {
		return moveMobileNo;
	}

	public void setMoveMobileNo(String moveMobileNo) {
		this.moveMobileNo = moveMobileNo;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getRateNm() {
		return rateNm;
	}

	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}

	public String getReqUsimSn() {
		return reqUsimSn;
	}

	public void setReqUsimSn(String reqUsimSn) {
		this.reqUsimSn = reqUsimSn;
	}

	public String getFullTextForMsp() {
        fullTextForMsp = getRateNm() +"/"+ getOperType() +"/"+ getReqUsimSn()+ "/" + getMoveMobileNo();
        return fullTextForMsp;
    }

    public void setFullTextForMsp(String fullTextForMsp) {
        this.fullTextForMsp = fullTextForMsp;
    }

    public String getSearchDtReq() {
        return searchDtReq;
    }

    public void setSearchDtReq(String searchDtReq) {
        this.searchDtReq = searchDtReq;
    }

    public String getSearchDtAnswer() {
        return searchDtAnswer;
    }

    public void setSearchDtAnswer(String searchDtAnswer) {
        this.searchDtAnswer = searchDtAnswer;
    }

    public String getReplyStatCd() {
        return replyStatCd;
    }

    public void setReplyStatCd(String replyStatCd) {
        this.replyStatCd = replyStatCd;
    }

    public int getCounselSeq() {
        return counselSeq;
    }

    public void setCounselSeq(int counselSeq) {
        this.counselSeq = counselSeq;
    }

    public String getCounselCtg() {
        return counselCtg;
    }

    public void setCounselCtg(String counselCtg) {
        this.counselCtg = counselCtg;
    }

    public String getCounselNm() {
        return counselNm;
    }

    public void setCounselNm(String counselNm) {
        this.counselNm = counselNm;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public Timestamp getCretDt() {
        return cretDt;
    }

    public void setCretDt(Timestamp cretDt) {
        this.cretDt = cretDt;
    }

    public Timestamp getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(Timestamp amdDt) {
        this.amdDt = amdDt;
    }

    public String getRegionCd() {
        return regionCd;
    }

    public void setRegionCd(String regionCd) {
        this.regionCd = regionCd;
    }

    public String getAgeCd() {
        return ageCd;
    }

    public void setAgeCd(String ageCd) {
        this.ageCd = ageCd;
    }

    public String getGenderCd() {
        return genderCd;
    }

    public void setGenderCd(String genderCd) {
        this.genderCd = genderCd;
    }

    public List<TelCounselDtlDto> getTelCounselDtlDtoList() {
        return telCounselDtlDtoList;
    }

    public void setTelCounselDtlDtoList(List<TelCounselDtlDto> telCounselDtlDtoList) {
        this.telCounselDtlDtoList = telCounselDtlDtoList;
    }

    public String getLastCounselDtlSeq() {
        return lastCounselDtlSeq;
    }

    public void setLastCounselDtlSeq(String lastCounselDtlSeq) {
        this.lastCounselDtlSeq = lastCounselDtlSeq;
    }

    public TelCounselDtlDto getTelCounselDtlDto() {
        return telCounselDtlDto;
    }

    public void setTelCounselDtlDto(TelCounselDtlDto telCounselDtlDto) {
        this.telCounselDtlDto = telCounselDtlDto;
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

    /** 전화상담 카테고리 label */
    public String getCounselCtgLabel() {
        if (counselCtg != null && counselCtg.equals("01")) {
            return "핸드폰";
        }
        if (counselCtg != null && counselCtg.equals("02")) {
            return "유심";
        }
        return "알수없음";
    }

    public String getGenderLabel() {
        if (counselCtg != null && counselCtg.equals("01")) {
            return "남";
        }
        if (counselCtg != null && counselCtg.equals("02")) {
            return "여";
        }
        return "알수없음";
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
