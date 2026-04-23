package com.ktis.mcpif.mPlatform.event.Y51;

import com.ktis.mcpif.mPlatform.event.BaseRequest;

import static com.ktis.mcpif.common.XmlUtil.createXmlElementIfNotEmpty;

public class MoscContCustInfoAgreeChgInVO extends BaseRequest {

    private String othcmpInfoAdvrRcvAgreYn;
    private String othcmpInfoAdvrCnsgAgreYn;
    private String grpAgntBindSvcSbscAgreYn;
    private String cardInsrPrdcAgreYn;
    private String fnncDealAgreeYn;
    private String cnsgInfoAdvrRcvAgreYn;

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

    @Override
    public String toBodyContentXml() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(super.toSelfCareInXml());
        stringBuilder.append("<inDto>");

        stringBuilder.append(createXmlElementIfNotEmpty("othcmpInfoAdvrRcvAgreYn", this.othcmpInfoAdvrRcvAgreYn));
        stringBuilder.append(createXmlElementIfNotEmpty("othcmpInfoAdvrCnsgAgreYn", this.othcmpInfoAdvrCnsgAgreYn));
        stringBuilder.append(createXmlElementIfNotEmpty("grpAgntBindSvcSbscAgreYn", this.grpAgntBindSvcSbscAgreYn));
        stringBuilder.append(createXmlElementIfNotEmpty("cardInsrPrdcAgreYn", this.cardInsrPrdcAgreYn));
        stringBuilder.append(createXmlElementIfNotEmpty("fnncDealAgreeYn", this.fnncDealAgreeYn));
        stringBuilder.append(createXmlElementIfNotEmpty("cnsgInfoAdvrRcvAgreYn", this.cnsgInfoAdvrRcvAgreYn));

        stringBuilder.append("</inDto>");

        return stringBuilder.toString();
    }
}
