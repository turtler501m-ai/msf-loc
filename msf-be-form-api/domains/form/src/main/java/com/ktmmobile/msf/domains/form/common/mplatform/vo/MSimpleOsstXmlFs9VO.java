package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class MSimpleOsstXmlFs9VO extends CommonXmlVO{
	
	
	private String fathTransacId;   		// 안면인증 트랜잭션 아이디
	private String fathProgrStepCd;    		// 안면인증진행단계코드 
											// 00 안면인증 내역 등록 
											// 01 사진인증 트랜잭션 아이디 맵핑
											// 02 안면인증 서식지 아이디 맵핑 
											// 03 고객거리제한 여부 결과 업데이트 
											// 04 공통시스템 사진인증 수행 
											// 05 공통시스템 신분증 이미지 전달
											// 06 안면인증 완료
											// 07 서비스계약아이디 맵핑
											// 08 오더정보 초기화,맵핑
	
	private String frmpapId;				// 서식지아이디
	private String fathSbscDivCd;			// 안면인증가입구분코드
	private String fathSkipCd;				// 안면인증생략코드
	private String onlineOfflnDivCd;		// 온라인오프라인구분코드
	private String fathOrgId;				// 안면인증조직아이디
	private String fathOrgNm;				// 안면인증조직명
	private String fathCpntId;				// 안면인증접점아이디
	private String saleCmpnId;				// 판매회사아이디
	private String smsRcvTelNo;				// 단문메시지수신전화번호
	private String photoAthnTxnSeq;			// 사진인증내역일련번호
	private String photoAthnTxnDt;			// 사진인증내역일시
	private String photoAthnRqtDivCd;		// 사진인증요청구분코드
	private String photoAthnRetvDivCd;		// 사진인증조회구분코드
	private String photoAthnIndvDivCd;		// 사진인증개인구분코드
	private String photoAthnSvcDivCd;		// 사진인증서비스구분코드
	private String photoAthnSbscDivCd;		// 사진인증가입구분코드
	private String photoAthnSbscChCd;		// 사진인증가입채널코드
	private String photoAthnRetvPotimCd;	// 사진인증조회시점코드
	private String photoAthnAgreeDivYn;		// 사진인증동의구분여부
	private String photoAthnConnIpadr;		// 사진인증접속IP주소
	private String photoAthnRetvPrsnId;		// 사진인증조회자아이디
	private String photoAthnSalerCd;		// 사진인증판매자코드
	private String photoAthnAgncyId;		// 사진인증대리점아이디
	private String photoAthnAgncyNm;		// 사진인증대리점명
	private String fathUrlAdr;				// 안면인증URL주소
	private String fathUrlRqtDt;			// 안면인증URL요청일시
	private String orgLatitVal;				// 조직위도값
	private String orgLngitVal;				// 조직경도값
	private String custLatitVal;			// 고객위도값
	private String custLngitVal;			// 고객경도값
	private String distCalcVal;				// 거리계산값
	private String distRstrtnYn;			// 거리제한여부
	private String distRstrtnDtrYn; 		// 거리제한우회여부
	private String distRstrtnDtrWhySbst; 	// 거리제한우회사유내용
	private String fathCustIdfyNo;			// 안면인증고객식별번호
	private String fathTmscnt;				// 안면인증횟수
	private String fathIdcardTypeCd;		// 안면인증신분증유형코드
	private String fathCustNm;				// 안면인증고객명
	private String fathIdcardIssDate;		// 안면인증신분증발급일자
	private String fathDriveLicnsNo;		// 안면인증운전면허번호
	private String fathEngCitizCd;			// 안면인증영문국적코드
	private String fathBthday;				// 안면인증생년월일
	private String fathRwomNo;				// 안면인증보훈번호
	private String fathQrSbst;				// 안면인증QR내용
	private String fathIdntyConfWayCd;		// 안면인증신원확인수단코드
	private String faceImgStoreResltCd;		// 안면이미지저장결과코드
	private String fathResltCd;				// 안면인증결과코드
	private String fathResltMsgSbst;		// 안면인증결과메시지내용
	private String fathCmpltNtfyUrlDivCd;	// 안면인증완료통지URL구분코드
	private String fathCmpltNtfyDt;			// 안면인증완료통지일시
	private String custId;					// 고객아이디
	private String svcContId;				// 서비스계약아이디
	private String fathDecideCd;			// 안면인증최종결과코드
	private String idcardPhotoImg;			// 신분증사진
	private String idcardCopiesImg;			// 신분증 사본
	private String mblIdcardQrImg;			// 모바일 신분증QR
	
    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
		this.fathTransacId = XmlParse.getChildValue(this.body, "fathTransacId");
		this.fathProgrStepCd = XmlParse.getChildValue(this.body, "fathProgrStepCd");
		this.frmpapId = XmlParse.getChildValue(this.body, "frmpapId");
		this.fathSbscDivCd = XmlParse.getChildValue(this.body, "fathSbscDivCd");
		this.fathSkipCd = XmlParse.getChildValue(this.body, "fathSkipCd");
		this.onlineOfflnDivCd = XmlParse.getChildValue(this.body, "onlineOfflnDivCd");
		this.fathOrgId = XmlParse.getChildValue(this.body, "fathOrgId");
		this.fathOrgNm = XmlParse.getChildValue(this.body, "fathOrgNm");
		this.fathCpntId = XmlParse.getChildValue(this.body, "fathCpntId");
		this.saleCmpnId = XmlParse.getChildValue(this.body, "saleCmpnId");
		this.smsRcvTelNo = XmlParse.getChildValue(this.body, "smsRcvTelNo");
		this.photoAthnTxnSeq = XmlParse.getChildValue(this.body, "photoAthnTxnSeq");
		this.photoAthnTxnDt = XmlParse.getChildValue(this.body, "photoAthnTxnDt");
		this.photoAthnRqtDivCd = XmlParse.getChildValue(this.body, "photoAthnRqtDivCd");
		this.photoAthnRetvDivCd = XmlParse.getChildValue(this.body, "photoAthnRetvDivCd");
		this.photoAthnIndvDivCd = XmlParse.getChildValue(this.body, "photoAthnIndvDivCd");
		this.photoAthnSvcDivCd = XmlParse.getChildValue(this.body, "photoAthnSvcDivCd");
		this.photoAthnSbscDivCd = XmlParse.getChildValue(this.body, "photoAthnSbscDivCd");
		this.photoAthnSbscChCd = XmlParse.getChildValue(this.body, "photoAthnSbscChCd");
		this.photoAthnRetvPotimCd = XmlParse.getChildValue(this.body, "photoAthnRetvPotimCd");
		this.photoAthnAgreeDivYn = XmlParse.getChildValue(this.body, "photoAthnAgreeDivYn");
		this.photoAthnConnIpadr = XmlParse.getChildValue(this.body, "photoAthnConnIpadr");
		this.photoAthnRetvPrsnId = XmlParse.getChildValue(this.body, "photoAthnRetvPrsnId");
		this.photoAthnSalerCd = XmlParse.getChildValue(this.body, "photoAthnSalerCd");
		this.photoAthnAgncyId = XmlParse.getChildValue(this.body, "photoAthnAgncyId");
		this.photoAthnAgncyNm = XmlParse.getChildValue(this.body, "photoAthnAgncyNm");
		this.fathUrlAdr = XmlParse.getChildValue(this.body, "fathUrlAdr");
		this.fathUrlRqtDt = XmlParse.getChildValue(this.body, "fathUrlRqtDt");
		this.orgLatitVal = XmlParse.getChildValue(this.body, "orgLatitVal");
		this.orgLngitVal = XmlParse.getChildValue(this.body, "orgLngitVal");
		this.custLatitVal = XmlParse.getChildValue(this.body, "custLatitVal");
		this.custLngitVal = XmlParse.getChildValue(this.body, "custLngitVal");
		this.distCalcVal = XmlParse.getChildValue(this.body, "distCalcVal");
		this.distRstrtnYn = XmlParse.getChildValue(this.body, "distRstrtnYn");
		this.distRstrtnDtrYn = XmlParse.getChildValue(this.body, "distRstrtnDtrYn");
		this.distRstrtnDtrWhySbst = XmlParse.getChildValue(this.body, "distRstrtnDtrWhySbst");
		this.fathCustIdfyNo = XmlParse.getChildValue(this.body, "fathCustIdfyNo");
		this.fathTmscnt = XmlParse.getChildValue(this.body, "fathTmscnt");
		this.fathIdcardTypeCd = XmlParse.getChildValue(this.body, "fathIdcardTypeCd");
		this.fathCustNm = XmlParse.getChildValue(this.body, "fathCustNm");
		this.fathIdcardIssDate = XmlParse.getChildValue(this.body, "fathIdcardIssDate");
		this.fathDriveLicnsNo = XmlParse.getChildValue(this.body, "fathDriveLicnsNo");
		this.fathEngCitizCd = XmlParse.getChildValue(this.body, "fathEngCitizCd");
		this.fathBthday = XmlParse.getChildValue(this.body, "fathBthday");
		this.fathRwomNo = XmlParse.getChildValue(this.body, "fathRwomNo");
		this.fathQrSbst = XmlParse.getChildValue(this.body, "fathQrSbst");
		this.fathIdntyConfWayCd = XmlParse.getChildValue(this.body, "fathIdntyConfWayCd");
		this.faceImgStoreResltCd = XmlParse.getChildValue(this.body, "faceImgStoreResltCd");
		this.fathResltCd = XmlParse.getChildValue(this.body, "fathResltCd");
		this.fathResltMsgSbst = XmlParse.getChildValue(this.body, "fathResltMsgSbst");
		this.fathCmpltNtfyUrlDivCd = XmlParse.getChildValue(this.body, "fathCmpltNtfyUrlDivCd");
		this.fathCmpltNtfyDt = XmlParse.getChildValue(this.body, "fathCmpltNtfyDt");
		this.custId = XmlParse.getChildValue(this.body, "custId");
		this.svcContId = XmlParse.getChildValue(this.body, "svcContId");
		this.fathDecideCd = XmlParse.getChildValue(this.body, "fathDecideCd");
		this.idcardPhotoImg = XmlParse.getChildValue(this.body, "idcardPhotoImg");
		this.idcardCopiesImg = XmlParse.getChildValue(this.body, "idcardCopiesImg");
		this.mblIdcardQrImg = XmlParse.getChildValue(this.body, "mblIdcardQrImg");
	}

	public String getFathTransacId() {
		return fathTransacId;
	}

	public void setFathTransacId(String fathTransacId) {
		this.fathTransacId = fathTransacId;
	}

	public String getFathProgrStepCd() {
		return fathProgrStepCd;
	}

	public void setFathProgrStepCd(String fathProgrStepCd) {
		this.fathProgrStepCd = fathProgrStepCd;
	}

	public String getFrmpapId() {
		return frmpapId;
	}

	public void setFrmpapId(String frmpapId) {
		this.frmpapId = frmpapId;
	}

	public String getFathSbscDivCd() {
		return fathSbscDivCd;
	}

	public void setFathSbscDivCd(String fathSbscDivCd) {
		this.fathSbscDivCd = fathSbscDivCd;
	}

	public String getFathSkipCd() {
		return fathSkipCd;
	}

	public void setFathSkipCd(String fathSkipCd) {
		this.fathSkipCd = fathSkipCd;
	}

	public String getOnlineOfflnDivCd() {
		return onlineOfflnDivCd;
	}

	public void setOnlineOfflnDivCd(String onlineOfflnDivCd) {
		this.onlineOfflnDivCd = onlineOfflnDivCd;
	}

	public String getFathOrgId() {
		return fathOrgId;
	}

	public void setFathOrgId(String fathOrgId) {
		this.fathOrgId = fathOrgId;
	}

	public String getFathOrgNm() {
		return fathOrgNm;
	}

	public void setFathOrgNm(String fathOrgNm) {
		this.fathOrgNm = fathOrgNm;
	}

	public String getFathCpntId() {
		return fathCpntId;
	}

	public void setFathCpntId(String fathCpntId) {
		this.fathCpntId = fathCpntId;
	}

	public String getSaleCmpnId() {
		return saleCmpnId;
	}

	public void setSaleCmpnId(String saleCmpnId) {
		this.saleCmpnId = saleCmpnId;
	}

	public String getSmsRcvTelNo() {
		return smsRcvTelNo;
	}

	public void setSmsRcvTelNo(String smsRcvTelNo) {
		this.smsRcvTelNo = smsRcvTelNo;
	}

	public String getPhotoAthnTxnSeq() {
		return photoAthnTxnSeq;
	}

	public void setPhotoAthnTxnSeq(String photoAthnTxnSeq) {
		this.photoAthnTxnSeq = photoAthnTxnSeq;
	}

	public String getPhotoAthnTxnDt() {
		return photoAthnTxnDt;
	}

	public void setPhotoAthnTxnDt(String photoAthnTxnDt) {
		this.photoAthnTxnDt = photoAthnTxnDt;
	}

	public String getPhotoAthnRqtDivCd() {
		return photoAthnRqtDivCd;
	}

	public void setPhotoAthnRqtDivCd(String photoAthnRqtDivCd) {
		this.photoAthnRqtDivCd = photoAthnRqtDivCd;
	}

	public String getPhotoAthnRetvDivCd() {
		return photoAthnRetvDivCd;
	}

	public void setPhotoAthnRetvDivCd(String photoAthnRetvDivCd) {
		this.photoAthnRetvDivCd = photoAthnRetvDivCd;
	}

	public String getPhotoAthnIndvDivCd() {
		return photoAthnIndvDivCd;
	}

	public void setPhotoAthnIndvDivCd(String photoAthnIndvDivCd) {
		this.photoAthnIndvDivCd = photoAthnIndvDivCd;
	}

	public String getPhotoAthnSvcDivCd() {
		return photoAthnSvcDivCd;
	}

	public void setPhotoAthnSvcDivCd(String photoAthnSvcDivCd) {
		this.photoAthnSvcDivCd = photoAthnSvcDivCd;
	}

	public String getPhotoAthnSbscDivCd() {
		return photoAthnSbscDivCd;
	}

	public void setPhotoAthnSbscDivCd(String photoAthnSbscDivCd) {
		this.photoAthnSbscDivCd = photoAthnSbscDivCd;
	}

	public String getPhotoAthnSbscChCd() {
		return photoAthnSbscChCd;
	}

	public void setPhotoAthnSbscChCd(String photoAthnSbscChCd) {
		this.photoAthnSbscChCd = photoAthnSbscChCd;
	}

	public String getPhotoAthnRetvPotimCd() {
		return photoAthnRetvPotimCd;
	}

	public void setPhotoAthnRetvPotimCd(String photoAthnRetvPotimCd) {
		this.photoAthnRetvPotimCd = photoAthnRetvPotimCd;
	}

	public String getPhotoAthnAgreeDivYn() {
		return photoAthnAgreeDivYn;
	}

	public void setPhotoAthnAgreeDivYn(String photoAthnAgreeDivYn) {
		this.photoAthnAgreeDivYn = photoAthnAgreeDivYn;
	}

	public String getPhotoAthnConnIpadr() {
		return photoAthnConnIpadr;
	}

	public void setPhotoAthnConnIpadr(String photoAthnConnIpadr) {
		this.photoAthnConnIpadr = photoAthnConnIpadr;
	}

	public String getPhotoAthnRetvPrsnId() {
		return photoAthnRetvPrsnId;
	}

	public void setPhotoAthnRetvPrsnId(String photoAthnRetvPrsnId) {
		this.photoAthnRetvPrsnId = photoAthnRetvPrsnId;
	}

	public String getPhotoAthnSalerCd() {
		return photoAthnSalerCd;
	}

	public void setPhotoAthnSalerCd(String photoAthnSalerCd) {
		this.photoAthnSalerCd = photoAthnSalerCd;
	}

	public String getPhotoAthnAgncyId() {
		return photoAthnAgncyId;
	}

	public void setPhotoAthnAgncyId(String photoAthnAgncyId) {
		this.photoAthnAgncyId = photoAthnAgncyId;
	}

	public String getPhotoAthnAgncyNm() {
		return photoAthnAgncyNm;
	}

	public void setPhotoAthnAgncyNm(String photoAthnAgncyNm) {
		this.photoAthnAgncyNm = photoAthnAgncyNm;
	}

	public String getFathUrlAdr() {
		return fathUrlAdr;
	}

	public void setFathUrlAdr(String fathUrlAdr) {
		this.fathUrlAdr = fathUrlAdr;
	}

	public String getFathUrlRqtDt() {
		return fathUrlRqtDt;
	}

	public void setFathUrlRqtDt(String fathUrlRqtDt) {
		this.fathUrlRqtDt = fathUrlRqtDt;
	}

	public String getOrgLatitVal() {
		return orgLatitVal;
	}

	public void setOrgLatitVal(String orgLatitVal) {
		this.orgLatitVal = orgLatitVal;
	}

	public String getOrgLngitVal() {
		return orgLngitVal;
	}

	public void setOrgLngitVal(String orgLngitVal) {
		this.orgLngitVal = orgLngitVal;
	}

	public String getCustLatitVal() {
		return custLatitVal;
	}

	public void setCustLatitVal(String custLatitVal) {
		this.custLatitVal = custLatitVal;
	}

	public String getCustLngitVal() {
		return custLngitVal;
	}

	public void setCustLngitVal(String custLngitVal) {
		this.custLngitVal = custLngitVal;
	}

	public String getDistCalcVal() {
		return distCalcVal;
	}

	public void setDistCalcVal(String distCalcVal) {
		this.distCalcVal = distCalcVal;
	}

	public String getDistRstrtnYn() {
		return distRstrtnYn;
	}

	public void setDistRstrtnYn(String distRstrtnYn) {
		this.distRstrtnYn = distRstrtnYn;
	}

	public String getDistRstrtnDtrYn() {
		return distRstrtnDtrYn;
	}

	public void setDistRstrtnDtrYn(String distRstrtnDtrYn) {
		this.distRstrtnDtrYn = distRstrtnDtrYn;
	}

	public String getDistRstrtnDtrWhySbst() {
		return distRstrtnDtrWhySbst;
	}

	public void setDistRstrtnDtrWhySbst(String distRstrtnDtrWhySbst) {
		this.distRstrtnDtrWhySbst = distRstrtnDtrWhySbst;
	}

	public String getFathCustIdfyNo() {
		return fathCustIdfyNo;
	}

	public void setFathCustIdfyNo(String fathCustIdfyNo) {
		this.fathCustIdfyNo = fathCustIdfyNo;
	}

	public String getFathTmscnt() {
		return fathTmscnt;
	}

	public void setFathTmscnt(String fathTmscnt) {
		this.fathTmscnt = fathTmscnt;
	}

	public String getFathIdcardTypeCd() {
		return fathIdcardTypeCd;
	}

	public void setFathIdcardTypeCd(String fathIdcardTypeCd) {
		this.fathIdcardTypeCd = fathIdcardTypeCd;
	}

	public String getFathCustNm() {
		return fathCustNm;
	}

	public void setFathCustNm(String fathCustNm) {
		this.fathCustNm = fathCustNm;
	}

	public String getFathIdcardIssDate() {
		return fathIdcardIssDate;
	}

	public void setFathIdcardIssDate(String fathIdcardIssDate) {
		this.fathIdcardIssDate = fathIdcardIssDate;
	}

	public String getFathDriveLicnsNo() {
		return fathDriveLicnsNo;
	}

	public void setFathDriveLicnsNo(String fathDriveLicnsNo) {
		this.fathDriveLicnsNo = fathDriveLicnsNo;
	}

	public String getFathEngCitizCd() {
		return fathEngCitizCd;
	}

	public void setFathEngCitizCd(String fathEngCitizCd) {
		this.fathEngCitizCd = fathEngCitizCd;
	}

	public String getFathBthday() {
		return fathBthday;
	}

	public void setFathBthday(String fathBthday) {
		this.fathBthday = fathBthday;
	}

	public String getFathRwomNo() {
		return fathRwomNo;
	}

	public void setFathRwomNo(String fathRwomNo) {
		this.fathRwomNo = fathRwomNo;
	}

	public String getFathQrSbst() {
		return fathQrSbst;
	}

	public void setFathQrSbst(String fathQrSbst) {
		this.fathQrSbst = fathQrSbst;
	}

	public String getFathIdntyConfWayCd() {
		return fathIdntyConfWayCd;
	}

	public void setFathIdntyConfWayCd(String fathIdntyConfWayCd) {
		this.fathIdntyConfWayCd = fathIdntyConfWayCd;
	}

	public String getFaceImgStoreResltCd() {
		return faceImgStoreResltCd;
	}

	public void setFaceImgStoreResltCd(String faceImgStoreResltCd) {
		this.faceImgStoreResltCd = faceImgStoreResltCd;
	}

	public String getFathResltCd() {
		return fathResltCd;
	}

	public void setFathResltCd(String fathResltCd) {
		this.fathResltCd = fathResltCd;
	}

	public String getFathResltMsgSbst() {
		return fathResltMsgSbst;
	}

	public void setFathResltMsgSbst(String fathResltMsgSbst) {
		this.fathResltMsgSbst = fathResltMsgSbst;
	}

	public String getFathCmpltNtfyUrlDivCd() {
		return fathCmpltNtfyUrlDivCd;
	}

	public void setFathCmpltNtfyUrlDivCd(String fathCmpltNtfyUrlDivCd) {
		this.fathCmpltNtfyUrlDivCd = fathCmpltNtfyUrlDivCd;
	}

	public String getFathCmpltNtfyDt() {
		return fathCmpltNtfyDt;
	}

	public void setFathCmpltNtfyDt(String fathCmpltNtfyDt) {
		this.fathCmpltNtfyDt = fathCmpltNtfyDt;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getSvcContId() {
		return svcContId;
	}

	public void setSvcContId(String svcContId) {
		this.svcContId = svcContId;
	}

	public String getFathDecideCd() {
		return fathDecideCd;
	}

	public void setFathDecideCd(String fathDecideCd) {
		this.fathDecideCd = fathDecideCd;
	}

	public String getIdcardPhotoImg() {
		return idcardPhotoImg;
	}

	public void setIdcardPhotoImg(String idcardPhotoImg) {
		this.idcardPhotoImg = idcardPhotoImg;
	}

	public String getIdcardCopiesImg() {
		return idcardCopiesImg;
	}

	public void setIdcardCopiesImg(String idcardCopiesImg) {
		this.idcardCopiesImg = idcardCopiesImg;
	}

	public String getMblIdcardQrImg() {
		return mblIdcardQrImg;
	}

	public void setMblIdcardQrImg(String mblIdcardQrImg) {
		this.mblIdcardQrImg = mblIdcardQrImg;
	}
}