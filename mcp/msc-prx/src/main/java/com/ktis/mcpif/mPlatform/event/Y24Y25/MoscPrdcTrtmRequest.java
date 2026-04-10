package com.ktis.mcpif.mPlatform.event.Y24Y25;

import com.ktis.mcpif.mPlatform.event.BaseRequest;

import java.util.List;

import static com.ktis.mcpif.common.XmlUtil.createXmlElementIfNotEmpty;

public class MoscPrdcTrtmRequest extends BaseRequest {
    private String actCode;
    private List<PrdcTrtmVO> prdcList;

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public List<PrdcTrtmVO> getPrdcList() {
        return prdcList;
    }

    public void setPrdcList(List<PrdcTrtmVO> prdcList) {
        this.prdcList = prdcList;
    }

    @Override
    public String toBodyContentXml() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(super.toSelfCareInXml());
        stringBuilder.append("<inDto>");
        stringBuilder.append("<actCode>").append(actCode).append("</actCode>");

        for (PrdcTrtmVO prdcTrtmVO : prdcList) {
            stringBuilder.append("<prdcList>");
            stringBuilder.append(createXmlElementIfNotEmpty("ftrNewParam", prdcTrtmVO.getFtrNewParam()));
            stringBuilder.append(createXmlElementIfNotEmpty("prdcCd", prdcTrtmVO.getPrdcCd()));
            stringBuilder.append(createXmlElementIfNotEmpty("prdcSbscTrtmCd", prdcTrtmVO.getPrdcSbscTrtmCd()));
            stringBuilder.append(createXmlElementIfNotEmpty("prdcSeqNo", prdcTrtmVO.getPrdcSeqNo()));
            stringBuilder.append(createXmlElementIfNotEmpty("prdcTypeCd", prdcTrtmVO.getPrdcTypeCd()));
            stringBuilder.append("</prdcList>");
        }

        stringBuilder.append("</inDto>");

        return stringBuilder.toString();
    }
}
