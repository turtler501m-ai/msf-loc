package com.ktmmobile.msf.domains.form.common.dto.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : NmcpCdDtlDto.java
 * 날짜     : 2017. 5. 17.
 * 작성자   : papier
 * 설명     : 메인 상품 추천 정보
 * </pre>
 */
public class NmcpProdCommendDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 상품 추천 일련번호 (PK) */
    private int prodCommendId;
    /** 상품 분류 (휴대폰 :01,유심 :02) */
    private String prodType;
    /** NMCP_PROD_BAS PK 휴대폰 , 유심 상품코드  */
    private String prodId;
    /** 상품명 */
    private String prodNm;
    /** 판매정책코드 */
    private String salePlcyCd;
    /** 제품구분코드(LTE,3G)(유심타입) */
    private String prdtSctnCd;
    /** 선불,후불 */
    private String payClCd;
    /** 제품ID(단말기코드)(유심형태 : LTE 일반유심,LTE 일반유심) */
    private String prdtId;
    /** 가입유형 ( * HCN3 : 기기변경, MNP3 : 번호이동, NAC3 : 신규개통) */
    private String operType;
    /** 약정 기간 (약정유형) */
    private String agrmTrm;
    /** 요금제코드 */
    private String rateCd;
    /** 요금제명 */
    private String rateNm;
    /** 월납부 금액.. */
    private int payMnthAmt;
    /** 상품옵션 인기:01,최대지원금:02,신규:03,사은품:04,특가:05,추천:06 */
    private String prodOption;
    /** 노출문구 */
    private String showText;
    /** 상품 이미지 경로 */
    private String imgPath;
    /** 이미지설명 */
    private String imgDesc;
    /** 링크 타겟 (현재창 :01,새창 :02) */
    private String linkType;
    /** LINK_URL */
    private String linkUrl;
    /** 정렬순서 */
    private int indcOdrg;
    /** 상태값 A : 활성, C: 취소 */
    private String status;
    /** 등록자 아이디 */
    private String cretId;
    /** 수정자 아이디 */
    private String amdId;
    /** 등록일 */
    private Date cretDt;
    /** 수정일 */
    private Date amtDt;

    /** 유심 별 갯수 */
    private String usimStar;
    /** 무료데이터 */
    private String freeData;
    /** 무료음성 */
    private String freeVoice;
    /** 무료문자 */
    private String freeMsg;
    /** 휴대폰종류 핫딜폰:A,리퍼폰:B,최저0원폰:C */
    private String prodTypePhone;
    /** backgroundcolor(유심용) */
    private String bgColor;

    private String payMnthChargeAmt;
    private String payMnthInstAmt;

    private String ctgGroupCode; // mainRatePlan : 추천요금제, mainPhoneRatePlan : 추천휴대폰
    private String ctgCode;

    private String pcRateNm;
    private String moRateNm;
    private String pcRateDesc;
    private String moRateDesc;
    private String pageNo;

    private String requestKey;
    private String shareYn;
    private String modelId;
    private String modelMonthly;
    private String socCode;
    private String modelSalePolicyCode;
    private String onOffType;
    private String orgnId;
    private String prodCtgType;
    private String selPric;

    public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPcRateNm() {
		return pcRateNm;
	}
	public void setPcRateNm(String pcRateNm) {
		this.pcRateNm = pcRateNm;
	}
	public String getMoRateNm() {
		return moRateNm;
	}
	public void setMoRateNm(String moRateNm) {
		this.moRateNm = moRateNm;
	}
	public String getPcRateDesc() {
		return pcRateDesc;
	}
	public void setPcRateDesc(String pcRateDesc) {
		this.pcRateDesc = pcRateDesc;
	}
	public String getMoRateDesc() {
		return moRateDesc;
	}
	public void setMoRateDesc(String moRateDesc) {
		this.moRateDesc = moRateDesc;
	}
	public String getCtgGroupCode() {
		return ctgGroupCode;
	}
	public void setCtgGroupCode(String ctgGroupCode) {
		this.ctgGroupCode = ctgGroupCode;
	}
	public String getCtgCode() {
		return ctgCode;
	}
	public void setCtgCode(String ctgCode) {
		this.ctgCode = ctgCode;
	}
	public String getPayMnthChargeAmt() {
		return payMnthChargeAmt;
	}
	public void setPayMnthChargeAmt(String payMnthChargeAmt) {
		this.payMnthChargeAmt = payMnthChargeAmt;
	}
	public String getPayMnthInstAmt() {
		return payMnthInstAmt;
	}
	public void setPayMnthInstAmt(String payMnthInstAmt) {
		this.payMnthInstAmt = payMnthInstAmt;
	}
	public String getBgColor() {
		return bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	public String getProdTypePhone() {
		return prodTypePhone;
	}
	public void setProdTypePhone(String prodTypePhone) {
		this.prodTypePhone = prodTypePhone;
	}
	public String getUsimStar() {
		return usimStar;
	}
	public void setUsimStar(String usimStar) {
		this.usimStar = usimStar;
	}
	public String getFreeData() {
		return freeData;
	}
	public void setFreeData(String freeData) {
		this.freeData = freeData;
	}
	public String getFreeVoice() {
		return freeVoice;
	}
	public void setFreeVoice(String freeVoice) {
		this.freeVoice = freeVoice;
	}
	public String getFreeMsg() {
		return freeMsg;
	}
	public void setFreeMsg(String freeMsg) {
		this.freeMsg = freeMsg;
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
    public Date getAmtDt() {
        return amtDt;
    }
    public void setAmtDt(Date amtDt) {
        this.amtDt = amtDt;
    }
    public int getProdCommendId() {
        return prodCommendId;
    }
    public void setProdCommendId(int prodCommendId) {
        this.prodCommendId = prodCommendId;
    }
    public String getProdType() {
        return prodType;
    }

    public String getProdTypeNm() {
        if ("01".equals(prodType)) {
            return "휴대폰" ;
        } else if("02".equals(prodType)){
            return "유심" ;
        } else {
            return "" ;
        }
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }
    public String getProdId() {
        return prodId;
    }
    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
    public String getProdNm() {
        return prodNm;
    }
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }
    public String getSalePlcyCd() {
        return salePlcyCd;
    }
    public void setSalePlcyCd(String salePlcyCd) {
        this.salePlcyCd = salePlcyCd;
    }
    public String getPrdtId() {
        return prdtId;
    }
    public void setPrdtId(String prdtId) {
        this.prdtId = prdtId;
    }
    public String getOperType() {
        return operType;
    }
    public String getOperTypeNm() {
        if ("MNP3".equals(operType)) {
            return "번호이동" ;
        } else if("NAC3".equals(operType)){
            return "신규가입" ;
        } else if("HCN3".equals(operType)){
            return "기기변경" ;
        }
        return operType ;
    }
    public void setOperType(String operType) {
        this.operType = operType;
    }
    public String getAgrmTrm() {
        return agrmTrm;
    }
    public void setAgrmTrm(String agrmTrm) {
        this.agrmTrm = agrmTrm;
    }
    public String getRateCd() {
        return rateCd;
    }
    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }
    public String getRateNm() {
        return rateNm;
    }
    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }
    public int getPayMnthAmt() {
        return payMnthAmt;
    }
    public void setPayMnthAmt(int payMnthAmt) {
        this.payMnthAmt = payMnthAmt;
    }
    public String getProdOption() {
        return prodOption;
    }
    public void setProdOption(String prodOption) {
        this.prodOption = prodOption;
    }
    public String getShowText() {
        return showText;
    }
    public void setShowText(String showText) {
        this.showText = showText;
    }
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public String getImgDesc() {
        return imgDesc;
    }
    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }
    public String getLinkType() {
        return linkType;
    }
    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }
    public String getLinkUrl() {
        return linkUrl;
    }
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
    public int getIndcOdrg() {
        return indcOdrg;
    }
    public void setIndcOdrg(int indcOdrg) {
        this.indcOdrg = indcOdrg;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPrdtSctnCd() {
        return prdtSctnCd;
    }
    public void setPrdtSctnCd(String prdtSctnCd) {
        this.prdtSctnCd = prdtSctnCd;
    }
    public String getPayClCd() {
        return payClCd;
    }
    public void setPayClCd(String payClCd) {
        this.payClCd = payClCd;
    }
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getShareYn() {
		return shareYn;
	}
	public void setShareYn(String shareYn) {
		this.shareYn = shareYn;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getModelMonthly() {
		return modelMonthly;
	}
	public void setModelMonthly(String modelMonthly) {
		this.modelMonthly = modelMonthly;
	}
	public String getSocCode() {
		return socCode;
	}
	public void setSocCode(String socCode) {
		this.socCode = socCode;
	}
	public String getModelSalePolicyCode() {
		return modelSalePolicyCode;
	}
	public void setModelSalePolicyCode(String modelSalePolicyCode) {
		this.modelSalePolicyCode = modelSalePolicyCode;
	}
	public String getOnOffType() {
		return onOffType;
	}
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getProdCtgType() {
		return prodCtgType;
	}
	public void setProdCtgType(String prodCtgType) {
		this.prodCtgType = prodCtgType;
	}
	public String getSelPric() {
		return selPric;
	}
	public void setSelPric(String selPric) {
		this.selPric = selPric;
	}
}
