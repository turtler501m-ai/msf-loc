package com.ktmmobile.mcp.common.mplatform.vo;

import com.ktmmobile.mcp.common.util.XmlParse;

import java.io.UnsupportedEncodingException;


public class MpCustInfoAgreeVO extends CommonXmlVO{

    /**
     *  타사정보광고수신동의여부(31)
     *   5-1항. 고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의
     *   MSP_MRKT_AGR_MGMT.PERSONAL_INFO_COLLECT_AGREE
     *   MCP_REQUEST.PERSONAL_INFO_COLLECT_AGREE
     */
    private String othcmpInfoAdvrRcvAgreYn;

    /**
     *  타사정보광고위탁동의여부(32)
     *   5-2항. 개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의
     *   MSP_MRKT_AGR_MGMT.AGR_YN
     *   MCP_REQUEST.CLAUSE_PRI_AD_FLAG
     */
    private String othcmpInfoAdvrCnsgAgreYn;

    /**
     *  그룹사결합서비스가입동의여부(09)
     *   6-1항. 혜택 제공을 위한 제 3자 제공 동의 : KT
     *   MSP_MRKT_AGR_MGMT.OTHERS_TRNS_KT_AGREE
     *   MCP_REQUEST.OTHERS_TRNS_KT_AGREE
     */
    private String grpAgntBindSvcSbscAgreYn;

    /**
     *  카드보험상품동의여부(10)
     *   6-2항. 제3자 제공관련 광고 수신 동의
     *   MSP_MRKT_AGR_MGMT.OTHERS_AD_RECEIVE_AGREE
     *   MCP_REQUEST.OTHERS_AD_RECEIVE_AGREE
     */
    private String cardInsrPrdcAgreYn;

    /**
     *  금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의(46)
     *   6-1항. 혜택 제공을 위한 제 3자 제공 동의 : M모바일
     *   MSP_MRKT_AGR_MGMT.OTHERS_TRNS_AGREE
     *   MCP_REQUEST.OTHERS_TRNS_AGREE
     */
    private String fnncDealAgreeYn;

    /**
     *  위탁정보광고수신동의여부(30)
     *   고객 편의제공을 위한 이용 및 취급위탁, 정보/광고 수신
     *   MCP_REQUEST.CLAUSE_JEHU_FLAG
     */
    private String cnsgInfoAdvrRcvAgreYn;

    public MpCustInfoAgreeVO() {}

    public MpCustInfoAgreeVO(String othcmpInfoAdvrRcvAgreYn, String othcmpInfoAdvrCnsgAgreYn, String grpAgntBindSvcSbscAgreYn, String cardInsrPrdcAgreYn, String fnncDealAgreeYn, String cnsgInfoAdvrRcvAgreYn) {
        this.othcmpInfoAdvrRcvAgreYn = othcmpInfoAdvrRcvAgreYn;
        this.othcmpInfoAdvrCnsgAgreYn = othcmpInfoAdvrCnsgAgreYn;
        this.grpAgntBindSvcSbscAgreYn = grpAgntBindSvcSbscAgreYn;
        this.cardInsrPrdcAgreYn = cardInsrPrdcAgreYn;
        this.fnncDealAgreeYn = fnncDealAgreeYn;
        this.cnsgInfoAdvrRcvAgreYn = cnsgInfoAdvrRcvAgreYn;
    }

    public static MpCustInfoAgreeVOBuilder builder() {
        return new MpCustInfoAgreeVOBuilder();
    }

    public static class MpCustInfoAgreeVOBuilder {
        private String othcmpInfoAdvrRcvAgreYn;
        private String othcmpInfoAdvrCnsgAgreYn;
        private String grpAgntBindSvcSbscAgreYn;
        private String cardInsrPrdcAgreYn;
        private String fnncDealAgreeYn;
        private String cnsgInfoAdvrRcvAgreYn;

        MpCustInfoAgreeVOBuilder() {}

        public MpCustInfoAgreeVOBuilder othcmpInfoAdvrRcvAgreYn(String othcmpInfoAdvrRcvAgreYn) {
            this.othcmpInfoAdvrRcvAgreYn = othcmpInfoAdvrRcvAgreYn;
            return this;
        }
        public MpCustInfoAgreeVOBuilder othcmpInfoAdvrCnsgAgreYn(String othcmpInfoAdvrCnsgAgreYn) {
            this.othcmpInfoAdvrCnsgAgreYn = othcmpInfoAdvrCnsgAgreYn;
            return this;
        }
        public MpCustInfoAgreeVOBuilder grpAgntBindSvcSbscAgreYn(String grpAgntBindSvcSbscAgreYn) {
            this.grpAgntBindSvcSbscAgreYn = grpAgntBindSvcSbscAgreYn;
            return this;
        }
        public MpCustInfoAgreeVOBuilder cardInsrPrdcAgreYn(String cardInsrPrdcAgreYn) {
            this.cardInsrPrdcAgreYn = cardInsrPrdcAgreYn;
            return this;
        }
        public MpCustInfoAgreeVOBuilder fnncDealAgreeYn(String fnncDealAgreeYn) {
            this.fnncDealAgreeYn = fnncDealAgreeYn;
            return this;
        }
        public MpCustInfoAgreeVOBuilder cnsgInfoAdvrRcvAgreYn(String cnsgInfoAdvrRcvAgreYn) {
            this.cnsgInfoAdvrRcvAgreYn = cnsgInfoAdvrRcvAgreYn;
            return this;
        }

        public MpCustInfoAgreeVO build() {
            return new MpCustInfoAgreeVO(this.othcmpInfoAdvrRcvAgreYn, this.othcmpInfoAdvrCnsgAgreYn, this.grpAgntBindSvcSbscAgreYn, this.cardInsrPrdcAgreYn, this.fnncDealAgreeYn, this.cnsgInfoAdvrRcvAgreYn);
        }
    }

    @Override
    public void parse() throws UnsupportedEncodingException {
        this.othcmpInfoAdvrRcvAgreYn = XmlParse.getChildValue(this.body, "othcmpInfoAdvrRcvAgreYn");
        this.othcmpInfoAdvrCnsgAgreYn = XmlParse.getChildValue(this.body, "othcmpInfoAdvrCnsgAgreYn");
        this.grpAgntBindSvcSbscAgreYn = XmlParse.getChildValue(this.body, "grpAgntBindSvcSbscAgreYn");
        this.cardInsrPrdcAgreYn = XmlParse.getChildValue(this.body, "cardInsrPrdcAgreYn");
        this.fnncDealAgreeYn = XmlParse.getChildValue(this.body, "fnncDealAgreeYn");
        this.cnsgInfoAdvrRcvAgreYn = XmlParse.getChildValue(this.body, "cnsgInfoAdvrRcvAgreYn");
    }

    public String getAgreeYn(String agreeName) {
        switch (agreeName) {
            case "othcmpInfoAdvrRcvAgreYn":
                return this.othcmpInfoAdvrRcvAgreYn;
            case "othcmpInfoAdvrCnsgAgreYn":
                return this.othcmpInfoAdvrCnsgAgreYn;
            case "grpAgntBindSvcSbscAgreYn":
                return this.grpAgntBindSvcSbscAgreYn;
            case "cardInsrPrdcAgreYn":
                return this.cardInsrPrdcAgreYn;
            case "fnncDealAgreeYn":
                return this.fnncDealAgreeYn;
            case "cnsgInfoAdvrRcvAgreYn":
                return this.cnsgInfoAdvrRcvAgreYn;
            default:
                return null;
        }
    }

    public String getOthcmpInfoAdvrRcvAgreYn() {
        return othcmpInfoAdvrRcvAgreYn;
    }

    public void setOthcmpInfoAdvrRcvAgreYn(String othcmpInfoAdvrRcvAgreYn) {
        this.othcmpInfoAdvrRcvAgreYn = othcmpInfoAdvrRcvAgreYn;
    }

    public String getOthcmpInfoAdvrCnsgAgreYn() {
        return othcmpInfoAdvrCnsgAgreYn;
    }

    public void setOthcmpInfoAdvrCnsgAgreYn(String othcmpInfoAdvrCnsgAgreYn) {
        this.othcmpInfoAdvrCnsgAgreYn = othcmpInfoAdvrCnsgAgreYn;
    }

    public String getGrpAgntBindSvcSbscAgreYn() {
        return grpAgntBindSvcSbscAgreYn;
    }

    public void setGrpAgntBindSvcSbscAgreYn(String grpAgntBindSvcSbscAgreYn) {
        this.grpAgntBindSvcSbscAgreYn = grpAgntBindSvcSbscAgreYn;
    }

    public String getCardInsrPrdcAgreYn() {
        return cardInsrPrdcAgreYn;
    }

    public void setCardInsrPrdcAgreYn(String cardInsrPrdcAgreYn) {
        this.cardInsrPrdcAgreYn = cardInsrPrdcAgreYn;
    }

    public String getFnncDealAgreeYn() {
        return fnncDealAgreeYn;
    }

    public void setFnncDealAgreeYn(String fnncDealAgreeYn) {
        this.fnncDealAgreeYn = fnncDealAgreeYn;
    }

    public String getCnsgInfoAdvrRcvAgreYn() {
        return cnsgInfoAdvrRcvAgreYn;
    }

    public void setCnsgInfoAdvrRcvAgreYn(String cnsgInfoAdvrRcvAgreYn) {
        this.cnsgInfoAdvrRcvAgreYn = cnsgInfoAdvrRcvAgreYn;
    }
}
