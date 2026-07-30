package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import java.util.List;

import lombok.Data;

@Data
public class OwnerChangeFormDetailRequest {

    private Long requestKey;
    private String type;
    private String appEventCd;
    private String ctn;
    private String ncn;
    private String custId;
    private String cntpntCd;
    private String frmpapId;
    private String soc;
    private String mcnResNo;
    private String xml;
    private String serviceInfo;
    private String serviceName;
    private String serviceVo;
    private String encryptYn;
    private String mvnoOrdNo;
    private String trnsCstmrTypeCd;
    private String osstOrdNo;

    //Y13
    private String indCd;
    private String intmUniqIdntNo;

    private List<PrdcList> prdcList;

    private String mstSvcContId;

    @Data
    static class PrdcList {

        private String prdcCd;
        private String prdcSbscTrtmCd;
        private String prdcTypeCd;
    }

}
