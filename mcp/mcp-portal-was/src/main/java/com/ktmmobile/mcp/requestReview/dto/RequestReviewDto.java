package com.ktmmobile.mcp.requestReview.dto;

import static com.ktmmobile.mcp.common.constants.Constants.REQ_BUY_TYPE_PHONE;
import static com.ktmmobile.mcp.common.constants.Constants.REQ_BUY_TYPE_USIM;

import java.util.Date;
import java.util.List;

import com.ktmmobile.mcp.common.util.MaskingUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;

public class RequestReviewDto {

    private static final long serialVersionUID = 1L;

    /** 이미지 리스트정보 */
    List<McpRequestReviewImgDto> reviewImgList;

    /** 사용후기 질문,답변 리스트정보 */
    List<RequestReviewDto> reviewQuestionList;

    /** 사용후기 일련번호 MCP_REQUEST_REVIEW_SEQ SEQUENCE */
    private int reviewId;

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
    private String mkRegNm;

    private String agentCode;

    /** Y : 작성가능/ N : 90일이상 경과  */
    private String dayOver;

    private String phone;
    private String name;

    private String reqBuyTypeNm;

    private String prodDivCd;
    private String reqBuyProdMsn;
    private String rateCd;
    private int hitCnt;
    private int ranking;

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



    /** 비교날짜 */
    private Date baseDate;


    /** 사용후기 질문 답변 */

    /** 질문키 */
    private int questionId;
    /** 질문 내용 및 설명 */
    private String questionDesc;
    /** 노출 제목 */
    private String questionMm;
    /** 사용 여부*/
    private String useYn;
    /** 통계 노출 여부*/
    private String statViewYn;
    /** 개인정보 수집 및 이용동의 필요*/
    private String authFlagYn;
    /** 답변형태*/
    private String answerType;
    /** 정렬순서*/
    private String indcOdrg;
    /** 생성자아이디*/
    private String cretId;
    /** 수정자아이디*/
    private String amdId;
    /** 생성일시 */
    private Date cretDt;
    /** 수정일시 */
    private Date amdDt;
    /** 답변키 */
    private int answerId;
    /** 답변 내용 및 설명 */
    private String answerDesc;
    /** 생성일 */
    private String cretDd;
    /** 사용후기백분율 */
    private int ReviewPerCent;
   /** 사용후기 작성 질문 리스트 */
    private List<RequestReviewDto> subList;

    private int answerCnt = 0;
    private int totalCnt = 0;

    private String mustVal;


    /** 프리퀀시 시작일 */
    private String strtDttm;

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

    public String getEventNm() {
        return NmcpServiceUtils.getCodeNm("ReviewEventInfo", eventCd);
    }


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
    public String getProdDivCd() {
        return prodDivCd;
    }
    public void setProdDivCd(String prodDivCd) {
        this.prodDivCd = prodDivCd;
    }
    public String getReqBuyProdMsn() {
        return reqBuyProdMsn;
    }
    public void setReqBuyProdMsn(String reqBuyProdMsn) {
        this.reqBuyProdMsn = reqBuyProdMsn;
    }
    public String getRateCd() {
        return rateCd;
    }
    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }
    public int getHitCnt() {
        return hitCnt;
    }
    public void setHitCnt(int hitCnt) {
        this.hitCnt = hitCnt;
    }
    public int getRanking() {
        return ranking;
    }
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    /*
     * 3개월이상이면 이름비노출
     */
    public String getMkRegNm() {
        if (sysRdate !=null && baseDate != null) {
            //compareTo메서드를 통한 날짜비교

            int compare = sysRdate.compareTo(baseDate);
            if(compare < 0) {
                return "";
            } else {
                return mkRegNm +"님";
            }
        }

        return mkRegNm +"님";
    }
    public void setMkRegNm(String mkRegNm) {
        this.mkRegNm = MaskingUtil.getMaskedName(mkRegNm);
    }
    public Date getBaseDate() {
        return baseDate;
    }
    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }
    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    public String getQuestionDesc() {
        return questionDesc;
    }
    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }
    public String getQuestionMm() {
        return questionMm;
    }
    public void setQuestionMm(String questionMm) {
        this.questionMm = questionMm;
    }
    public String getUseYn() {
        return useYn;
    }
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    public String getStatViewYn() {
        return statViewYn;
    }
    public void setStatViewYn(String statViewYn) {
        this.statViewYn = statViewYn;
    }
    public String getAuthFlagYn() {
        return authFlagYn;
    }
    public void setAuthFlagYn(String authFlagYn) {
        this.authFlagYn = authFlagYn;
    }
    public String getAnswerType() {
        return answerType;
    }
    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }
    public String getIndcOdrg() {
        return indcOdrg;
    }
    public void setIndcOdrg(String indcOdrg) {
        this.indcOdrg = indcOdrg;
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
    public Date getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }
    public int getAnswerId() {
        return answerId;
    }
    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
    public String getAnswerDesc() {
        return answerDesc;
    }
    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }
    public String getCretDd() {
        return cretDd;
    }
    public void setCretDd(String cretDd) {
        this.cretDd = cretDd;
    }
    public List<RequestReviewDto> getReviewQuestionList() {
        return reviewQuestionList;
    }
    public void setReviewQuestionList(List<RequestReviewDto> reviewQuestionList) {
        this.reviewQuestionList = reviewQuestionList;
    }
    public int getReviewPerCent() {
        return ReviewPerCent;
    }
    public void setReviewPerCent(int reviewPerCent) {
        ReviewPerCent = reviewPerCent;
    }
    public List<RequestReviewDto> getSubList() {
        return subList;
    }
    public void setSubList(List<RequestReviewDto> subList) {
        this.subList = subList;
    }
    public int getAnswerCnt() {
        return answerCnt;
    }
    public void setAnswerCnt(int answerCnt) {
        this.answerCnt = answerCnt;
    }
    public int getTotalCnt() {
        return totalCnt;
    }
    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }
    public String getMustVal() {
        return mustVal;
    }
    public void setMustVal(String mustVal) {
        this.mustVal = mustVal;
    }
    public int getReviewId() {
        return reviewId;
    }
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getStrtDttm() {
        return strtDttm;
    }

    public void setStrtDttm(String strtDttm) {
        this.strtDttm = strtDttm;
    }
}
