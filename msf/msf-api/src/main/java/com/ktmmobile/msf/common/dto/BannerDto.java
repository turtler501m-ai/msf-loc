package com.ktmmobile.msf.common.dto;

import com.ktmmobile.msf.common.constants.Constants;
import com.ktmmobile.msf.form.common.constant.PhoneConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Map;

public class BannerDto implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -1207742026359034810L;

    private String bannApdDesc; //배너추가설명
    private String mobileBannImgNm; //모바일배너이미지
    private String bannBgColor; //배너배경색상
    private String mobileLinkUrl; //모바일링크url
    private int bannSeq; //배너일련번호
    private String bannerCd ; // 배넌 일련번호 (인자값)
    private String bannCtg; //배너카테고리
    private String bannType; //배너종류
    private String bannNm; //배너명
    private String bannDesc; //배너설명
    private String bannImg; //배너이미지
    private String imgDesc; //이미지설명(alt)
    private String linkTarget; //링크타겟
    private String linkUrlAdr; //링크URL
    private String statVal; //상태
    private String prodNm; //상품명
    private int prodId; //상품아이디
    private int sntyProdSeq; //단품상품일련번호
    private String sbscrbTypeCd; //가입유형코드
    private String agrmTypeCd;  //약정유형코드
    private String chrgPlanCd; //요금제코드
    private String cretId; //생성자아이디
    private String amdId;	//수정자아이디
    private String cretDt;	//생성일시
    private String amdDt;	//수정일시
    private String useYn; // 사용여부
    private String cdGroupId = "MBN"; //코드그룹아이디
    private String dtlCd;   //상세코드
    private String dtlCdNm;  //상세코드명
    private String dtlCdDesc;  //상세코드설명
    private int indcOdrg;  //표시순서
    private int dtlCnt; // 하위 배너 숫자
    private int rowNum; // 게시글 넘버
    private String resultCode; // 처리 코드
    private int totalCnt;
    private String bannSeqArr;
    private String bannOdrgArr;
    private MultipartFile file; // 파일
    private String fileRootPath;
    private String updateYn;
    private String atribValCd1; //색상
    private String atribValCd2;	//용량
    private String atribValNm1; //색상명
    private String atribValNm2; //용량명
    private String sdoutYn; //품절여부
    private String salePlcyCd; //요금제코드
    private String agrmTrm; // 요금제명
    private String rateCd;  // 약정명
    private String rateNm;  // 약정코드
    private String newYn;	// 가입유형 신규
    private String mnpYn;   // 가입유형 번호이동
    private String hcnYn;   // 가입유형 기기변경
    private String[] editorPhotoSeqArr; // 에디터 사진업로드 seqarr
    private String expnsnStrVal1; //분류
    private String expnsnStrVal2; //분류 2
    private String expnsnStrVal3; //분류 3
    private String bgColor; // 배너배경색
    private String textColor1; // 서브타이틀색상1
    private String textColor2; // 서브타이틀색상2



    private Map<String, Object> pageMap; // 페이징

    private String searchNm;
    private String searchOpt;
    private int rstCnt;

    /** 핸드폰,유심 배너 링크 재가공 처리  */
    private String rebuildLinkl;

    /** 핸드폰 새창처리  */
    private String newTarget;

    private String payClCd;
    private String phoneYn;
    private  String dataType ; //LTE,3G

    //페이징
    private int skipResult;   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
    private int maxResult;  // Pagesize
    private int pageNo;
    private int boardNum;

    private String adDesc;
    private String eventType; //이벤트배너타입 이벤트:E,제휴:J,제휴카드:C
    private String eventStartDt;  // 이벤트시작일
    private String eventEndDt;    // 이벤트종료일
    private MultipartFile moreFile;  // 더보기 파일
    private String moreBannImg;   // 더보기 배너이미지

    //상세
    private String bannDtlSeq;
    private String imgNm;
    private String linkNm;
    private String sortOdrg;
    private String pstngStartDate;
    private String pstngEndDate;

    private String bannImgSec; //배너이미지
    private String imgDescSec; //이미지설명(alt)
    private String bgColorSec; // 배너배경색
    private String mobileBannImgNmSec; //모바일배너이미지
    private String pstngStartDateSec;
    private String pstngEndDateSec;

	public String getMoreBannImg() {
		return moreBannImg;
	}
	public void setMoreBannImg(String moreBannImg) {
		this.moreBannImg = moreBannImg;
	}
	public MultipartFile getMoreFile() {
		return moreFile;
	}
	public void setMoreFile(MultipartFile moreFile) {
		this.moreFile = moreFile;
	}
	public String getEventStartDt() {
		return eventStartDt;
	}
	public void setEventStartDt(String eventStartDt) {
		this.eventStartDt = eventStartDt;
	}
	public String getEventEndDt() {
		return eventEndDt;
	}
	public void setEventEndDt(String eventEndDt) {
		this.eventEndDt = eventEndDt;
	}
    public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getAdDesc() {
        return adDesc;
    }
    public void setAdDesc(String adDesc) {
        this.adDesc = adDesc;
    }
    public String getPayClCd() {
        return payClCd;
    }
    public void setPayClCd(String payClCd) {
        this.payClCd = payClCd;
    }
    public String getPhoneYn() {
        return phoneYn;
    }
    public void setPhoneYn(String phoneYn) {
        this.phoneYn = phoneYn;
    }

    public String getRebuildLinkl() {

        String agrmTypeCdNull = StringUtils.defaultIfEmpty(agrmTypeCd,"");
        String operTypeCh  = "";

        if ("Y".equals(newYn)) {
            operTypeCh = PhoneConstant.OPER_NEW;
        } else if ("Y".equals(mnpYn)) {
            operTypeCh = PhoneConstant.OPER_PHONE_NUMBER_TRANS;
        } else if ("Y".equals(hcnYn)) {
            operTypeCh = Constants.OPER_TYPE_EXCHANGE;
        }

        if ("M".equals(bannType)) {	//모바일
            if(null != linkUrlAdr && linkUrlAdr.indexOf("/m/") > -1){
                rebuildLinkl = "/m/product/phone/phoneView.do"+"?bannerCd="+bannSeq+"&prodId="+prodId+"&"
                        + "instNom="+agrmTrm+"&rateCd="+rateCd
                        + "&sprtTp="+agrmTypeCdNull+"&operType="+operTypeCh+"&hndsetModelId="+sntyProdSeq;
            }else{
                rebuildLinkl = "/product/phone/phoneView.do"+"?bannerCd="+bannSeq+"&prodId="+prodId+"&"
                    + "instNom="+agrmTrm+"&rateCd="+rateCd
                    + "&sprtTp="+agrmTypeCdNull+"&operType="+operTypeCh+"&hndsetModelId="+sntyProdSeq;
            }
        } else if ("U".equals(bannType)) {	//유심
            String prodViewPath = "";
            if("PO".equals(payClCd)) {		//후불유심
                prodViewPath = "/appForm/reqSelfDlvry.do";
            } else if("PP".equals(payClCd)) {	//선불유심
                prodViewPath = "/appForm/reqSelfDlvry.do";
            }
            rebuildLinkl = prodViewPath+"?bannerCd="+bannSeq+"&prodId="+prodId+"&atribValCd1="+atribValCd1+"&atribValCd2="+atribValCd2
                            +"&rateCd="+rateCd;
        } else {
            rebuildLinkl = linkUrlAdr;
        }
        return rebuildLinkl;
    }

    public String getNewTarget() {
        if ("Y".equals(linkTarget)) {
            newTarget = "target=\"_blank\", title=\"새창열림\"";
        } else {
            newTarget = "";
        }
        return newTarget;
    }

    public int getBannSeq() {
        return bannSeq;
    }
    public void setBannSeq(int bannSeq) {
        this.bannSeq = bannSeq;
    }
    public String getBannCtg() {
        return bannCtg;
    }
    public void setBannCtg(String bannCtg) {
        this.bannCtg = bannCtg;
    }
    public String getBannType() {
        return bannType;
    }
    public void setBannType(String bannType) {
        this.bannType = bannType;
    }
    public String getBannNm() {
        return bannNm;
    }
    public void setBannNm(String bannNm) {
        this.bannNm = bannNm;
    }
    public String getBannDesc() {
        return bannDesc;
    }
    public void setBannDesc(String bannDesc) {
        this.bannDesc = bannDesc;
    }
    public String getBannImg() {
        return bannImg;
    }
    public void setBannImg(String bannImg) {
        this.bannImg = bannImg;
    }
    public String getImgDesc() {
        return imgDesc;
    }
    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }
    public String getLinkTarget() {
        return linkTarget;
    }
    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;
    }
    public String getLinkUrlAdr() {
        return linkUrlAdr;
    }
    public void setLinkUrlAdr(String linkUrlAdr) {
        this.linkUrlAdr = linkUrlAdr;
    }
    public String getStatVal() {
        return statVal;
    }
    public void setStatVal(String statVal) {
        this.statVal = statVal;
    }
    public String getProdNm() {
        return prodNm;
    }
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public int getProdId() {
        return prodId;
    }
    public void setProdId(int prodId) {
        this.prodId = prodId;
    }
    public int getSntyProdSeq() {
        return sntyProdSeq;
    }
    public void setSntyProdSeq(int sntyProdSeq) {
        this.sntyProdSeq = sntyProdSeq;
    }
    public String getSbscrbTypeCd() {
        return sbscrbTypeCd;
    }
    public void setSbscrbTypeCd(String sbscrbTypeCd) {
        this.sbscrbTypeCd = sbscrbTypeCd;
    }
    public String getAgrmTypeCd() {
        return agrmTypeCd;
    }
    public void setAgrmTypeCd(String agrmTypeCd) {
        this.agrmTypeCd = agrmTypeCd;
    }
    public String getChrgPlanCd() {
        return chrgPlanCd;
    }
    public void setChrgPlanCd(String chrgPlanCd) {
        this.chrgPlanCd = chrgPlanCd;
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
    public String getCretDt() {
        return cretDt;
    }
    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }
    public String getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(String amdDt) {
        this.amdDt = amdDt;
    }
    public String getSearchNm() {
        return searchNm;
    }
    public void setSearchNm(String searchNm) {
        this.searchNm = searchNm;
    }
    public String getSearchOpt() {
        return searchOpt;
    }
    public void setSearchOpt(String searchOpt) {
        this.searchOpt = searchOpt;
    }
    public int getRstCnt() {
        return rstCnt;
    }
    public void setRstCnt(int rstCnt) {
        this.rstCnt = rstCnt;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getUseYn() {
        return useYn;
    }
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public String getCdGroupId() {
        return cdGroupId;
    }
    public void setCdGroupId(String cdGroupId) {
        this.cdGroupId = cdGroupId;
    }
    public String getDtlCd() {
        return dtlCd;
    }
    public void setDtlCd(String dtlCd) {
        this.dtlCd = dtlCd;
    }
    public String getDtlCdNm() {
        return dtlCdNm;
    }
    public void setDtlCdNm(String dtlCdNm) {
        this.dtlCdNm = dtlCdNm;
    }
    public String getDtlCdDesc() {
        return dtlCdDesc;
    }
    public void setDtlCdDesc(String dtlCdDesc) {
        this.dtlCdDesc = dtlCdDesc;
    }
    public int getIndcOdrg() {
        return indcOdrg;
    }
    public void setIndcOdrg(int indcOdrg) {
        this.indcOdrg = indcOdrg;
    }
    public int getDtlCnt() {
        return dtlCnt;
    }
    public void setDtlCnt(int dtlCnt) {
        this.dtlCnt = dtlCnt;
    }
    public int getRowNum() {
        return rowNum;
    }
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
    public Map<String, Object> getPageMap() {
        return pageMap;
    }
    public void setPageMap(Map<String, Object> pageMap) {
        this.pageMap = pageMap;
    }
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public int getTotalCnt() {
        return totalCnt;
    }
    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }
    public String getBannSeqArr() {
        return bannSeqArr;
    }
    public void setBannSeqArr(String bannSeqArr) {
        this.bannSeqArr = bannSeqArr;
    }
    public String getBannOdrgArr() {
        return bannOdrgArr;
    }
    public void setBannOdrgArr(String bannOdrgArr) {
        this.bannOdrgArr = bannOdrgArr;
    }
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    public String getFileRootPath() {
        return fileRootPath;
    }
    public void setFileRootPath(String fileRootPath) {
        this.fileRootPath = fileRootPath;
    }
    public String getUpdateYn() {
        return updateYn;
    }
    public void setUpdateYn(String updateYn) {
        this.updateYn = updateYn;
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
    public String getAtribValCd1() {
        return atribValCd1;
    }
    public void setAtribValCd1(String atribValCd1) {
        this.atribValCd1 = atribValCd1;
    }
    public String getAtribValCd2() {
        return atribValCd2;
    }
    public void setAtribValCd2(String atribValCd2) {
        this.atribValCd2 = atribValCd2;
    }
    public String getAgrmTrm() {
        return agrmTrm;
    }
    public void setAgrmTrm(String agrmTrm) {
        this.agrmTrm = agrmTrm;
    }
    public String getRateNm() {
        return rateNm;
    }
    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }
    public String getSdoutYn() {
        return sdoutYn;
    }
    public void setSdoutYn(String sdoutYn) {
        this.sdoutYn = sdoutYn;
    }
    public String getAtribValNm1() {
        return atribValNm1;
    }
    public void setAtribValNm1(String atribValNm1) {
        this.atribValNm1 = atribValNm1;
    }
    public String getAtribValNm2() {
        return atribValNm2;
    }
    public void setAtribValNm2(String atribValNm2) {
        this.atribValNm2 = atribValNm2;
    }
    public String getNewYn() {
        return newYn;
    }
    public void setNewYn(String newYn) {
        this.newYn = newYn;
    }
    public String getMnpYn() {
        return mnpYn;
    }
    public void setMnpYn(String mnpYn) {
        this.mnpYn = mnpYn;
    }
    public String getHcnYn() {
        return hcnYn;
    }
    public void setHcnYn(String hcnYn) {
        this.hcnYn = hcnYn;
    }
    public String getSalePlcyCd() {
        return salePlcyCd;
    }
    public void setSalePlcyCd(String salePlcyCd) {
        this.salePlcyCd = salePlcyCd;
    }
    public String getRateCd() {
        return rateCd;
    }
    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }
    public String[] getEditorPhotoSeqArr() {
        return editorPhotoSeqArr;
    }
    public void setEditorPhotoSeqArr(String[] editorPhotoSeqArr) {
        this.editorPhotoSeqArr = editorPhotoSeqArr;
    }

    public String getExpnsnStrVal1() {
        return expnsnStrVal1;
    }

    public void setExpnsnStrVal1(String expnsnStrVal1) {
        this.expnsnStrVal1 = expnsnStrVal1;
    }

    public String getExpnsnStrVal2() {
        return expnsnStrVal2;
    }

    public void setExpnsnStrVal2(String expnsnStrVal2) {
        this.expnsnStrVal2 = expnsnStrVal2;
    }

    public String getExpnsnStrVal3() {
        return expnsnStrVal3;
    }

    public void setExpnsnStrVal3(String expnsnStrVal3) {
        this.expnsnStrVal3 = expnsnStrVal3;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public int getBoardNum() {
        return boardNum;
    }

    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }

    public String getBannerCd() {
        return bannerCd;
    }

    public void setBannerCd(String bannerCd) {
        this.bannerCd = bannerCd;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
	/**
	 * @return the bannDtlSeq
	 */
	public String getBannDtlSeq() {
		return bannDtlSeq;
	}
	/**
	 * @param bannDtlSeq the bannDtlSeq to set
	 */
	public void setBannDtlSeq(String bannDtlSeq) {
		this.bannDtlSeq = bannDtlSeq;
	}
	/**
	 * @return the imgNm
	 */
	public String getImgNm() {
		return imgNm;
	}
	/**
	 * @param imgNm the imgNm to set
	 */
	public void setImgNm(String imgNm) {
		this.imgNm = imgNm;
	}
	/**
	 * @return the linkNm
	 */
	public String getLinkNm() {
		return linkNm;
	}
	/**
	 * @param linkNm the linkNm to set
	 */
	public void setLinkNm(String linkNm) {
		this.linkNm = linkNm;
	}
	/**
	 * @return the sortOdrg
	 */
	public String getSortOdrg() {
		return sortOdrg;
	}
	/**
	 * @param sortOdrg the sortOdrg to set
	 */
	public void setSortOdrg(String sortOdrg) {
		this.sortOdrg = sortOdrg;
	}
	/**
	 * @return the pstngStartDate
	 */
	public String getPstngStartDate() {
		return pstngStartDate;
	}
	/**
	 * @param pstngStartDate the pstngStartDate to set
	 */
	public void setPstngStartDate(String pstngStartDate) {
		this.pstngStartDate = pstngStartDate;
	}
	/**
	 * @return the pstngEndDate
	 */
	public String getPstngEndDate() {
		return pstngEndDate;
	}
	/**
	 * @param pstngEndDate the pstngEndDate to set
	 */
	public void setPstngEndDate(String pstngEndDate) {
		this.pstngEndDate = pstngEndDate;
	}
	public String getBannApdDesc() {
		return bannApdDesc;
	}
	public void setBannApdDesc(String bannApdDesc) {
		this.bannApdDesc = bannApdDesc;
	}
	public String getMobileBannImgNm() {
		return mobileBannImgNm;
	}
	public void setMobileBannImgNm(String mobileBannImgNm) {
		this.mobileBannImgNm = mobileBannImgNm;
	}
	public String getBannBgColor() {
		return bannBgColor;
	}
	public void setBannBgColor(String bannBgColor) {
		this.bannBgColor = bannBgColor;
	}
	public String getMobileLinkUrl() {
		return mobileLinkUrl;
	}
	public void setMobileLinkUrl(String mobileLinkUrl) {
		this.mobileLinkUrl = mobileLinkUrl;
	}
	public void setRebuildLinkl(String rebuildLinkl) {
		this.rebuildLinkl = rebuildLinkl;
	}
	public void setNewTarget(String newTarget) {
		this.newTarget = newTarget;
	}

    public void setTextColor1(String textColor1) {
        this.textColor1 = textColor1;
    }

    public String getTextColor1() {
        return this.textColor1;
    }

    public void setTextColor2(String textColor1) {
        this.textColor2 = textColor2;
    }

    public String getTextColor2() {
        return this.textColor2;
    }

    public String getBannImgSec() {
        return bannImgSec;
    }

    public void setBannImgSec(String bannImgSec) {
        this.bannImgSec = bannImgSec;
    }

    public String getImgDescSec() {
        return imgDescSec;
    }

    public void setImgDescSec(String imgDescSec) {
        this.imgDescSec = imgDescSec;
    }

    public String getBgColorSec() {
        return bgColorSec;
    }

    public void setBgColorSec(String bgColorSec) {
        this.bgColorSec = bgColorSec;
    }

    public String getMobileBannImgNmSec() {
        return mobileBannImgNmSec;
    }

    public void setMobileBannImgNmSec(String mobileBannImgNmSec) {
        this.mobileBannImgNmSec = mobileBannImgNmSec;
    }

    public String getPstngStartDateSec() {
        return pstngStartDateSec;
    }

    public void setPstngStartDateSec(String pstngStartDateSec) {
        this.pstngStartDateSec = pstngStartDateSec;
    }

    public String getPstngEndDateSec() {
        return pstngEndDateSec;
    }

    public void setPstngEndDateSec(String pstngEndDateSec) {
        this.pstngEndDateSec = pstngEndDateSec;
    }
}