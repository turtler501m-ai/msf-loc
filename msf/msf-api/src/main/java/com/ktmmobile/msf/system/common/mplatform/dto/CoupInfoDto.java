package com.ktmmobile.msf.system.common.mplatform.dto;

import java.io.Serializable;

public class CoupInfoDto implements Serializable {

    /*
     * M - 필수
     * C - 부분필수
     * O - 선택
     */

    private static final long serialVersionUID = 1L;

    private String ncn ;

    private String ctn ;

    private String custId ;

    /*조회 구분 코드    2
    - 01 : 쿠폰번호 조회
    - 02 : 고객에게 배포된 쿠폰번호 조회
    */
    private String searchTypeCd  ;

    /*쿠폰 일련 번호    15  C
    - 조회 구분코드가 01인 경우 필수
    */
    private String coupSerialNo  ;

    /* 쿠폰 상태 코드
    - 조회 구분코드가 02인 경우 필수
    */
    private String coupStatCd  ;
    private String coupStatCdNm  ;

    /* 서비스유형코드 3   O
    - 쿠폰관련코드
    */
    private String  svcTypeCd  ;

    /*
     * 쿠폰유형코드  2   O
     *  - 쿠폰관련코드
     */
    private String coupTypeCd  ;

    /*
     * 페이지번호
     */
    private int pageNo = 1 ;

    /*
     * 쿠폰명 50
     */
    private String coupNm ;

    /*
     * 쿠폰분류코드  2
     *     - PM : 프로모션형"
     */
    private String coupCategoryCd ;

    /*
     * 할인유형코드  3
     - FXM : 정액
     - FXR : 정율"
     */
    private String dscnTypeCd ;


    /*쿠폰 value    9       "쿠폰의 값어치(할인값 or 판매형 리소스)
     - ex) 1000"
     */
    private String coupValu    ;


    /*
     * 사용유효 시작 일시  14      쿠폰의 사용유효기간 시작일시
     */
    private String useStrtDt   ;


    /*
     * 사용유효 종료 일시  14      쿠폰의 사용유효기간 종료일시
     */
    private String useEndDt    ;

    /*
     * 쿠폰 등록 일시    14      쿠폰 등록 일시
     */
    private String rgstStrtDt  ;


    /*
     * 사용 요청 일시    14      사용 요청 일시
     */
    private String useReqDt   ;

    /*
     * 예약사용가능 여부   1
     * Y : 예약 사용 가능
     * N : 예약 사용 불가
     */
    private String rsvPsblYn  ;

    /*
     * 쿠폰 생성 ID    20      쿠폰의 생성ID
     */
    private String  coupCreId  ;

    /*
     * 쿠폰 적용 범위 코드 3       "할인된 금액으로 프로모션 쿠폰 제공시 고객에게 청구되는 금액
     * -ex) 1000"
     */
    private String coupAplyLimitCd ;

    /*
     * 쿠폰 SMS 수신 고객    11      쿠폰 문자배포시 수신자 전화번호
     */
    private String smsRcvCtn   ;

    /*
     * 사용유형코드
     * CRU : 등록사용(등록동시사용)-즉시사용
     * URA : 사용예약
     * UCR : 사용예약취소
     */
    private String useTypeCd ;

    /*
     * 사용자 User Id
     */
    private String clntUsrId ;

    /*
     * 쿠폰 사용일 기준 검색조건 FROM (검색기간 최대3개월)
     */
    private String useDateFrom;

    /*
     * 쿠폰 사용일 기준 검색조건 TO (검색기간 최대3개월)
     */
    private String useDateTo ;

    // 테이블 데이터
    private String svcCntrNo;
    private String coupnNo;
    private String coupnCtgNm;
    private String pstngStartDate;
    private String pstngEndDate;
    private String coupnDivCd;
    private String coupnCtgCd;
    private String useStartDate;
    private String useEndDate;
    private String smsSndPosblYn;

    public String getSvcCntrNo() {
		return svcCntrNo;
	}

	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}

	public String getCoupnNo() {
		return coupnNo;
	}

	public void setCoupnNo(String coupnNo) {
		this.coupnNo = coupnNo;
	}

	public String getCoupnCtgNm() {
		return coupnCtgNm;
	}

	public void setCoupnCtgNm(String coupnCtgNm) {
		this.coupnCtgNm = coupnCtgNm;
	}

	public String getPstngStartDate() {
		return pstngStartDate;
	}

	public void setPstngStartDate(String pstngStartDate) {
		this.pstngStartDate = pstngStartDate;
	}

	public String getPstngEndDate() {
		return pstngEndDate;
	}

	public void setPstngEndDate(String pstngEndDate) {
		this.pstngEndDate = pstngEndDate;
	}

	public String getCoupStatCdNm() {
        return coupStatCdNm;
    }

    public void setCoupStatCdNm(String coupStatCdNm) {
        this.coupStatCdNm = coupStatCdNm;
    }

    public String getNcn() {
        return ncn;
    }

    public void setNcn(String ncn) {
        this.ncn = ncn;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getSearchTypeCd() {
        return searchTypeCd;
    }

    public void setSearchTypeCd(String searchTypeCd) {
        this.searchTypeCd = searchTypeCd;
    }

    public String getCoupSerialNo() {
        return coupSerialNo;
    }

    public void setCoupSerialNo(String coupSerialNo) {
        this.coupSerialNo = coupSerialNo;
    }

    public String getCoupStatCd() {
        return coupStatCd;
    }

    public void setCoupStatCd(String coupStatCd) {
        this.coupStatCd = coupStatCd;
    }

    public String getSvcTypeCd() {
        return svcTypeCd;
    }

    public void setSvcTypeCd(String svcTypeCd) {
        this.svcTypeCd = svcTypeCd;
    }

    public String getCoupTypeCd() {
        return coupTypeCd;
    }

    public void setCoupTypeCd(String coupTypeCd) {
        this.coupTypeCd = coupTypeCd;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getCoupNm() {
        return coupNm;
    }

    public void setCoupNm(String coupNm) {
        this.coupNm = coupNm;
    }

    public String getCoupCategoryCd() {
        return coupCategoryCd;
    }

    public void setCoupCategoryCd(String coupCategoryCd) {
        this.coupCategoryCd = coupCategoryCd;
    }

    public String getDscnTypeCd() {
        return dscnTypeCd;
    }

    public void setDscnTypeCd(String dscnTypeCd) {
        this.dscnTypeCd = dscnTypeCd;
    }

    public String getCoupValu() {
        return coupValu;
    }

    public void setCoupValu(String coupValu) {
        this.coupValu = coupValu;
    }

    public String getUseStrtDt() {
        return useStrtDt;
    }

    public void setUseStrtDt(String useStrtDt) {
        this.useStrtDt = useStrtDt;
    }

    public String getUseEndDt() {
        return useEndDt;
    }

    public void setUseEndDt(String useEndDt) {
        this.useEndDt = useEndDt;
    }

    public String getRgstStrtDt() {
        return rgstStrtDt;
    }

    public void setRgstStrtDt(String rgstStrtDt) {
        if(rgstStrtDt !=null && !"".equals(rgstStrtDt)){
            this.rgstStrtDt  = rgstStrtDt.replace(" ", "");
        } else {
            this.rgstStrtDt = rgstStrtDt;
        }

    }

    public String getUseReqDt() {
        return useReqDt;
    }

    public void setUseReqDt(String useReqDt) {
        if(useReqDt !=null && !"".equals(useReqDt)){
            this.useReqDt = useReqDt.replace(" ", "");
        } else {
            this.useReqDt = useReqDt;
        }
    }

    public String getRsvPsblYn() {
        return rsvPsblYn;
    }

    public void setRsvPsblYn(String rsvPsblYn) {
        this.rsvPsblYn = rsvPsblYn;
    }

    public String getCoupCreId() {
        return coupCreId;
    }

    public void setCoupCreId(String coupCreId) {
        this.coupCreId = coupCreId;
    }

    public String getCoupAplyLimitCd() {
        return coupAplyLimitCd;
    }

    public void setCoupAplyLimitCd(String coupAplyLimitCd) {
        this.coupAplyLimitCd = coupAplyLimitCd;
    }

    public String getSmsRcvCtn() {
        return smsRcvCtn;
    }

    public void setSmsRcvCtn(String smsRcvCtn) {
        this.smsRcvCtn = smsRcvCtn;
    }

    public String getUseTypeCd() {
        return useTypeCd;
    }

    public void setUseTypeCd(String useTypeCd) {
        this.useTypeCd = useTypeCd;
    }

    public String getClntUsrId() {
        return clntUsrId;
    }

    public void setClntUsrId(String clntUsrId) {
        this.clntUsrId = clntUsrId;
    }

    public String getUseDateFrom() {
        return useDateFrom;
    }

    public void setUseDateFrom(String useDateFrom) {
        this.useDateFrom = useDateFrom;
    }

    public String getUseDateTo() {
        return useDateTo;
    }

    public void setUseDateTo(String useDateTo) {
        this.useDateTo = useDateTo;
    }

	public String getCoupnDivCd() {
		return coupnDivCd;
	}

	public void setCoupnDivCd(String coupnDivCd) {
		this.coupnDivCd = coupnDivCd;
	}

	public String getCoupnCtgCd() {
		return coupnCtgCd;
	}

	public void setCoupnCtgCd(String coupnCtgCd) {
		this.coupnCtgCd = coupnCtgCd;
	}

	public String getUseStartDate() {
		return useStartDate;
	}

	public void setUseStartDate(String useStartDate) {
		this.useStartDate = useStartDate;
	}

	public String getUseEndDate() {
		return useEndDate;
	}

	public void setUseEndDate(String useEndDate) {
		this.useEndDate = useEndDate;
	}

	public String getSmsSndPosblYn() {
		return smsSndPosblYn;
	}

	public void setSmsSndPosblYn(String smsSndPosblYn) {
		this.smsSndPosblYn = smsSndPosblYn;
	}


}
