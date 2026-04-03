package com.ktmmobile.msf.system.common.legacy.etc.dto;

import java.io.Serializable;
import java.util.List;

public class GiftPromotionDto implements Serializable{

    private static final long serialVersionUID = 1L;

    // 인증 파라미터
    private String name;
    private String phone;
    private String certifySms;


    // MSP_GIFT_PRMT@DL_MSP
    // 프로모션 코드
    private String prmtId;
    // 프로모션명
    private String prmtNm;
    // 신규(NAC3)
    private String nacYn;
    // 번호이동(MNP3)
    private String mnpYn;
    // 구매유형(MM:단말구매, UU:유심단독)
    private String reqBuyType;
    // 총금액제한
    private String amountLimit;

    // MSP_GIFT_PRDT@DL_MSP
    // 제품ID
    private String prdtId;
    // 제품명
    private String prdtNm;
    // 제품상세설명
    private String prdtDesc;
    // 단가
    private String outUnitPric;
    // 저장된이미지파일명
    private String imgFile;
    // 이미지파일명
    private String webUrl;

    // MSP_JUO_SUB_INFO@DL_MSP
    // 계약번호
    private String contractNum;
    // 핸드폰 번호
    private String subscriberNo;
    // 사용자 이름
    private String subLinkName;
    // 현재부터 가입일까지 지난 일수
    private int nowToActvDate;

    /* 접점 코드 */
    private String orgnId = "1100011741";  //모바일(직영온라인) 기본값 설정

    /* 선택 가능한 숫 */
    private int limitCnt = 0 ; //LIMIT_CNT

    private int cnt;

    // 저장파라미터
    // 사은품 제품id
    private String[] prdtIdArry;
    // 사은품 연락처
    private String telFn1;
    private String telMn1;
    private String telRn1;
    // 사은품 주소
    private String post;
    private String addr;
    private String addrDtl;

    private int inResult;

 // 프로모션 설명
 	private String prmtText;

 	private String smsSendDate;

    private String onOffType;
    private String operType;
    private String agrmTrm;
    private String rateCd;
    private List<String> prmtIdList;

    // 프로모션 단말 추가
    private String modelId;

    public String getModelId() {
        return modelId;
    }
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    public String getPrmtText() {
		return prmtText;
	}


	public void setPrmtText(String prmtText) {
		this.prmtText = prmtText;
	}


	public String getSmsSendDate() {
		return smsSendDate;
	}


	public void setSmsSendDate(String smsSendDate) {
		this.smsSendDate = smsSendDate;
	}


	public int getInResult() {
        return inResult;
    }


    public void setInResult(int inResult) {
        this.inResult = inResult;
    }


    public String getTelFn1() {
        return telFn1;
    }


    public void setTelFn1(String telFn1) {
        this.telFn1 = telFn1;
    }


    public String getTelMn1() {
        return telMn1;
    }


    public void setTelMn1(String telMn1) {
        this.telMn1 = telMn1;
    }


    public String getTelRn1() {
        return telRn1;
    }


    public void setTelRn1(String telRn1) {
        this.telRn1 = telRn1;
    }


    public String getPost() {
        return post;
    }


    public void setPost(String post) {
        this.post = post;
    }


    public String getAddr() {
        return addr;
    }


    public void setAddr(String addr) {
        this.addr = addr;
    }


    public String getAddrDtl() {
        return addrDtl;
    }


    public void setAddrDtl(String addrDtl) {
        this.addrDtl = addrDtl;
    }


    public String[] getPrdtIdArry() {
        return prdtIdArry;
    }


    public void setPrdtIdArry(String[] prdtIdArry) {
        this.prdtIdArry = prdtIdArry;
    }


    public int getCnt() {
        return cnt;
    }


    public void setCnt(int cnt) {
        this.cnt = cnt;
    }


    public String getCertifySms() {
        return certifySms;
    }


    public void setCertifySms(String certifySms) {
        this.certifySms = certifySms;
    }


    public int getNowToActvDate() {
        return nowToActvDate;
    }


    public void setNowToActvDate(int nowToActvDate) {
        this.nowToActvDate = nowToActvDate;
    }


    public String getContractNum() {
        return contractNum;
    }


    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }


    public String getSubscriberNo() {
        return subscriberNo;
    }


    public void setSubscriberNo(String subscriberNo) {
        this.subscriberNo = subscriberNo;
    }


    public String getSubLinkName() {
        return subLinkName;
    }


    public void setSubLinkName(String subLinkName) {
        this.subLinkName = subLinkName;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrmtId() {
        return prmtId;
    }


    public void setPrmtId(String prmtId) {
        this.prmtId = prmtId;
    }


    public String getPrmtNm() {
        return prmtNm;
    }


    public void setPrmtNm(String prmtNm) {
        this.prmtNm = prmtNm;
    }


    public String getNacYn() {
        return nacYn;
    }


    public void setNacYn(String nacYn) {
        this.nacYn = nacYn;
    }


    public String getMnpYn() {
        return mnpYn;
    }


    public void setMnpYn(String mnpYn) {
        this.mnpYn = mnpYn;
    }


    public String getReqBuyType() {
        return reqBuyType;
    }


    public void setReqBuyType(String reqBuyType) {
        this.reqBuyType = reqBuyType;
    }


    public String getAmountLimit() {
        return amountLimit;
    }


    public void setAmountLimit(String amountLimit) {
        this.amountLimit = amountLimit;
    }


    public String getPrdtId() {
        return prdtId;
    }


    public void setPrdtId(String prdtId) {
        this.prdtId = prdtId;
    }


    public String getPrdtNm() {
        return prdtNm;
    }


    public void setPrdtNm(String prdtNm) {
        this.prdtNm = prdtNm;
    }


    public String getPrdtDesc() {
        return prdtDesc;
    }


    public void setPrdtDesc(String prdtDesc) {
        this.prdtDesc = prdtDesc;
    }


    public String getOutUnitPric() {
        return outUnitPric;
    }


    public void setOutUnitPric(String outUnitPric) {
        this.outUnitPric = outUnitPric;
    }


    public String getImgFile() {
        return imgFile;
    }


    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }


    public String getWebUrl() {
        return webUrl;
    }


    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }


    public String getOnOffType() {
        return onOffType;
    }


    public void setOnOffType(String onOffType) {
        this.onOffType = onOffType;
    }


    public String getOperType() {
        return operType;
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


    public List<String> getPrmtIdList() {
        return prmtIdList;
    }


    public void setPrmtIdList(List<String> prmtIdList) {
        this.prmtIdList = prmtIdList;
    }


    public String getOrgnId() {
        return orgnId;
    }


    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }

    public int getLimitCnt() {
        return limitCnt;
    }


    public void setLimitCnt(int limitCnt) {
        this.limitCnt = limitCnt;
    }



}
