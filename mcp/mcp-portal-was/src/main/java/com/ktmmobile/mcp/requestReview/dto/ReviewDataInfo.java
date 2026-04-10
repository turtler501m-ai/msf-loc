package com.ktmmobile.mcp.requestReview.dto;

import com.ktmmobile.mcp.common.util.MaskingUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;

import java.util.Date;
import java.util.List;

import static com.ktmmobile.mcp.common.constants.Constants.REQ_BUY_TYPE_PHONE;
import static com.ktmmobile.mcp.common.constants.Constants.REQ_BUY_TYPE_USIM;

public class ReviewDataInfo {

    private static final long serialVersionUID = -1450641467345654698L;

    /** 사용후기 일련번호 MCP_REQUEST_REVIEW_SEQ SEQUENCE */
    private int reviewId;

    /** 서식지 키 */
    private int requestKey;

    /** 이미지 리스트정보 */
    List<McpRequestReviewImgDto> reviewImgList;

    /** 사용후기 질문,답변 리스트정보 */
    List<ReviewQuestion> reviewQuestionList;


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


    /** 등록일 */
    private String sysRdateDd;


    private String reqBuyTypeNm;

    private String rateCd;
    private int hitCnt;
    private int ranking;


    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(int requestKey) {
        this.requestKey = requestKey;
    }

    public String getReqBuyTypeNm() {
        return reqBuyTypeNm;
    }
    public void setReqBuyTypeNm(String reqBuyTypeNm) {
        this.reqBuyTypeNm = reqBuyTypeNm;
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
            return "" ;
        }
    }

    public String getPrizeSbst() {
        return prizeSbst;
    }
    public void setPrizeSbst(String prizeSbst) {
        this.prizeSbst = prizeSbst;
    }


    public String getSysRdateDd() {
        return sysRdateDd;
    }
    public void setSysRdateDd(String sysRdateDd) {
        this.sysRdateDd = sysRdateDd;
    }
    public List<McpRequestReviewImgDto> getReviewImgList() {
        return reviewImgList;
    }
    public void setReviewImgList(List<McpRequestReviewImgDto> reviewImgList) {
        this.reviewImgList = reviewImgList;
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


    public List<ReviewQuestion> getReviewQuestionList() {
        return reviewQuestionList;
    }

    public void setReviewQuestionList(List<ReviewQuestion> reviewQuestionList) {
        this.reviewQuestionList = reviewQuestionList;
    }
}
