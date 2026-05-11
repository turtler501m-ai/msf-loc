package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RateAdsvcGdncBasDTO implements Serializable{

    private static final long serialVersionUID = 1L;
	
    /////// 요금제 부가서비스 안내 관리 //////////////////////    
	/** 요금제부가서비스안내일련번호 */    
	private int rateAdsvcGdncSeq;
	/** 요금제부가서비스구분 */
	private String rateAdsvcDivCd;
	/** 요금제구분코드 */
	private String rateDivCd;
	/** 요금제부가서비스구분명 */
	private String rateAdsvcDivNm;
	/** 요금제부가서비스명 */
	private String rateAdsvcNm;
	/** 요금제부가서비스추가명 */
	private String rateAdsvcApdNm;
	/** 요금제부가서비스기본설명 */
	private String rateAdsvcBasDesc;	
	/** 요금제부가서비스이미지명 */
	private String rateAdsvcImgNm;	
	/** 월기본금액설명 */
	private String mmBasAmtDesc;	
	/** 월기본금액vat(포함)설명 */
	private String mmBasAmtVatDesc;	
    /** 프로모션요금설명 */
	private String promotionAmtDesc;	
    /** 프로모션요금vat(포함)설명 */
	private String promotionAmtVatDesc;	
	
	/** 약정시 기본료 */
	private String contractAmtVatDesc;
	/** 요금할인 시 기본료 */
	private String rateDiscntAmtVatDesc;
	/** 시니어 할인 기본료 */
	private String seniorDiscntAmtVatDesc;
	
    /** 안내파일명(xml) */
	private String gdncFileNm;	
	/** 사용유효여부 */
	private String useYn;
	/** 게시시작일 */
	private String pstngStartDate;
	/** 게시종료일 */
	private String pstngEndDate;	
    
    ////////////////////////////////////////////////
    /** 등록아이피 */
	private String cretIp;
	/** 생성일시 */
	private String cretDt;
	/** 생성자아이디 */
	private String cretId;
	/** 변경아이피 */
	private String amdIp;
	/** 수정일시 */
	private String amdDt;
	/** 수정자아이디 */
	private String amdId;
	
    
    //////// 페이징 설정 //////////////////////////////
    /**  */
    private int rownum;
    /**  */
    private int pageNo=0;
    /** 페이지 시작 변수 */
    private int pagingStartNo;
    /** 페이지 끝 변수 */
    private int pagingEndNo;
    /** 페이지 사이즈 */
    private int pagingSize;
    
	////////검색 설정 //////////////////////////////
	private String searchRrateAdsvcDivCd;
	private String searchRateAdsvcCtgCd1;
	private String searchRateAdsvcCtgCd2;
	private String searchInput;

    //////// 기타 설정 //////////////////////////////
    /** I:쓰기, U:수정 */
    private String modIu;
    /** 중복 유무 */
    private String dupYn;
    /** 카테고리  갯수 */
    private String ctgCnt;
        
	
	
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	
	
	
	
	
	
	
	
}
