package com.ktmmobile.mcp.requestreview.dto;

import static com.ktmmobile.mcp.cmmn.constants.Constants.REQ_BUY_TYPE_PHONE;
import static com.ktmmobile.mcp.cmmn.constants.Constants.REQ_BUY_TYPE_USIM;

import java.util.Date;
import java.util.List;

public class RequestReviewDto {

    private static final long serialVersionUID = 1L;

    /** 이미지 리스트정보 */
    List<McpRequestReviewImgDto> reviewImgList;

    /** 서식지 키 */
    private int requestKey;

    /** 계약번호 */
    private String contractNum;

    /** 구매유형 단말 : USIM(유심)단독 구매:UU , 단말 구매 :MM */
    private String reqBuyType;

    /** 모델ID */
    private String modelId;

    /** 상품아이디 : 단말구매에서만 존재*/
    private String prodId;

    /** 상품후기 */
    private String reviewSbst;

    /** 추천여부 : 1 :추천 ,0 :비추천 */
    private String commendYn;

    /** 이벤트 코드 : 공통코드 */
    private String eventCd;

    /** SNS공유 URL */
    private String snsInfo;

    /** 공지여부 */
    private String ntfYn;

    /** 당첨내용 */
    private String prizeSbst;

    /** 상태여부  : 1 : 표현 , 0 : 미표현 */
    private String sttusVal;

    /** 등록아이피 */
    private String rIP;

    /** 등록일시 */
    private Date sysRdate;

    /** 등록일 */
    private String sysRdateDd;

    /** 작성자 */
    private String regNm;

    private String agentCode;

    /** Y : 작성가능/ N : 90일이상 경과  */
    private String dayOver;

    private String phone;
    private String name;

    private String reqBuyTypeNm;

    /** 검색 기능*/
    private String searchCategory;
    private String searchValue;

    private List<String> contractNumList;
    private String subLinkName;
    private double percent;
    private String viewLimitYn;
    private int limitPer=0;
    private int totalCount=0;
    private int imdSeq =-1;

    private String startDate;

    private String endDate;

    private String img1;

    private String img2;

    private String dobyyyymmdd;
    private String subscriberNo;
    private String subAdrPrimaryLn;
    private String subAdrSecondaryLn;

    private String reqBuyEventTypeNm;

    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public List<String> getContractNumList() {
        return contractNumList;
    }
    public void setContractNumList(List<String> contractNumList) {
        this.contractNumList = contractNumList;
    }
    public String getSubLinkName() {
        return subLinkName;
    }
    public void setSubLinkName(String subLinkName) {
        this.subLinkName = subLinkName;
    }
    public double getPercent() {
        return percent;
    }
    public void setPercent(double percent) {
        this.percent = percent;
    }
    public String getViewLimitYn() {
        return viewLimitYn;
    }
    public void setViewLimitYn(String viewLimitYn) {
        this.viewLimitYn = viewLimitYn;
    }
    public int getLimitPer() {
        return limitPer;
    }
    public void setLimitPer(int limitPer) {
        this.limitPer = limitPer;
    }
    public String getReqBuyTypeNm() {
        return reqBuyTypeNm;
    }
    public void setReqBuyTypeNm(String reqBuyTypeNm) {
        this.reqBuyTypeNm = reqBuyTypeNm;
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
    public String getDayOver() {
        return dayOver;
    }
    public void setDayOver(String dayOver) {
        this.dayOver = dayOver;
    }
    public String getAgentCode() {
        return agentCode;
    }
    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
    public int getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(int requestKey) {
        this.requestKey = requestKey;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getReqBuyType() {
        return reqBuyType;
    }

    public String getReqBuyTypeTxt() {
        if (REQ_BUY_TYPE_PHONE.equals(reqBuyType)) {
            return "휴대폰";
        } else if (REQ_BUY_TYPE_USIM.equals(reqBuyType)) {
            return "유심";
        } else {
            return "";
        }
    }

    public void setReqBuyType(String reqBuyType) {
        this.reqBuyType = reqBuyType;
    }

    public String getModelId() {
        return modelId;
    }
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    public String getProdId() {
        return prodId;
    }
    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getReviewSbst() {
        return reviewSbst;
    }

    public void setReviewSbst(String reviewSbst) {
        this.reviewSbst = reviewSbst;
    }

    public String getReviewTitle() {
        if (reviewSbst != null) {
            //개행 문자 제거
            String value = reviewSbst.replaceAll("(\r\n|\r|\n|\n\r)", " ");
            int valueInt = value.length();
            if (valueInt > 30) {
                return value.substring(0,30)+"..." ;
            } else {
                return value  ;
            }
        } else {
            return "";
        }
    }


    public String getCommendYn() {
        return commendYn;
    }

    public void setCommendYn(String commendYn) {
        this.commendYn = commendYn;
    }

    public String getCommendYnTxt() {
        if ("1".equals(commendYn)) {
            return "<span class='color_red'>추천</span>";
        } else {
            return "비추천";
        }
    }

    public String getCommendYnFrontTxt() {
        if ("1".equals(commendYn)) {
            return "/resources/images/requestReview/icon_recommend.jpg";
        } else {
            return "/resources/images/requestReview/icon_non_recommend.jpg" ;
        }
    }


    public String getEventCd() {
        return eventCd;
    }

    public void setEventCd(String eventCd) {
        this.eventCd = eventCd;
    }

    /* 사용유무 확인 필요함, 2021.09.29
    public String getEventNm() {
        return NmcpServiceUtils.getCodeNm("ReviewEventInfo", eventCd);
    }
    */


    public String getSnsInfo() {
        return snsInfo;
    }
    public void setSnsInfo(String snsInfo) {
        this.snsInfo = snsInfo;
    }

    public String getNtfYn() {
        return ntfYn;
    }
    public void setNtfYn(String ntfYn) {
        this.ntfYn = ntfYn;
    }

    public String getNtfYnTxt() {
        if ("1".equals(ntfYn)) {
            return "<span class='noticeY'>공지</span>" ;
        } else {
            return this.requestKey +"";
        }
    }



    public String getPrizeSbst() {
        return prizeSbst;
    }
    public void setPrizeSbst(String prizeSbst) {
        this.prizeSbst = prizeSbst;
    }

    public String getSttusVal() {
        return sttusVal;
    }

    public void setSttusVal(String sttusVal) {
        this.sttusVal = sttusVal;
    }

    public String getSttusValTxt() {
        if ("1".equals(sttusVal)) {
            return "<button class='active_Y'></button>" ;
        } else {
            return "<button class='active_N'></button>" ;
        }
    }

    public String getrIP() {
        return rIP;
    }
    public void setrIP(String rIP) {
        this.rIP = rIP;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getSysRdateDd() {
        return sysRdateDd;
    }
    public void setSysRdateDd(String sysRdateDd) {
        this.sysRdateDd = sysRdateDd;
    }
    public String getRegNm() {
        return regNm;
    }
    public void setRegNm(String regNm) {
        this.regNm = regNm;
    }
    public List<McpRequestReviewImgDto> getReviewImgList() {
        return reviewImgList;
    }
    public void setReviewImgList(List<McpRequestReviewImgDto> reviewImgList) {
        this.reviewImgList = reviewImgList;
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
    public int getImdSeq() {
        return imdSeq;
    }
    public void setImdSeq(int imdSeq) {
        this.imdSeq = imdSeq;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg2() {
        return img2;
    }

    public void setDobyyyymmdd(String dobyyyymmdd) {
        this.dobyyyymmdd = dobyyyymmdd;
    }

    public String getDobyyyymmdd() {
        return dobyyyymmdd;
    }

    public void setSubAdrPrimaryLn(String subAdrPrimaryLn) {
        this.subAdrPrimaryLn = subAdrPrimaryLn;
    }

    public String getSubAdrPrimaryLn() {
        return subAdrPrimaryLn;
    }

    public void setSubAdrSecondaryLn(String subAdrSecondaryLn) {
        this.subAdrSecondaryLn = subAdrSecondaryLn;
    }

    public String getSubAdrSecondaryLn() {
        return subAdrSecondaryLn;
    }

    public void setSubscriberNo(String subscriberNo) {
        this.subscriberNo = subscriberNo;
    }

    public String getSubscriberNo() {
        return subscriberNo;
    }

    public void setReqBuyEventTypeNm(String reqBuyEventTypeNm) {
        this.reqBuyEventTypeNm = reqBuyEventTypeNm;
    }

    public String getReqBuyEventTypeNm() {
        return reqBuyEventTypeNm;
    }

}
