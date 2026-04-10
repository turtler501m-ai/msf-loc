package com.ktmmobile.mcp.common.mplatform.dto;

import java.util.List;

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
}
