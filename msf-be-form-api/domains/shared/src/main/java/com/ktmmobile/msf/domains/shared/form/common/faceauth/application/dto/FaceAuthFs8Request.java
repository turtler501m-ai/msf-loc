package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Data
@XmlRootElement(name = "inDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class FaceAuthFs8Request {
    private CustFathInfoDTO custFathInfoDTO;
    private PhotoAthnRqtInDTO photoAthnRqtInDTO;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CustFathInfoDTO {
        private String orgId;
        private String cpntId;
        private String onlineOfflnDivCd;
        private String fathSbscDivCd;
        private String photoAthnNcstYn;
        private String scanTypeCd;
        private String photoAthnTxnSeq;
        private String frmpapId;
        private String retvCdVal;
        private String fathRglsEnvTestYn;
        private String crprAgntYn;
        private String fathBizrNo;
        @XmlJavaTypeAdapter(EncryptAdapter.class)
        private String fathAgntCustNm;
        @XmlJavaTypeAdapter(EncryptAdapter.class)
        private String fathAgntBthday;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PhotoAthnRqtInDTO {
        private String photoAthnRqtDivCd;
        private String photoAthnIndvDivCd;
        private String photoAthnSvcDivCd;
        private String photoAthnSbscChCd;
        private String photoAthnSbscDivCd;
        private String photoAthnRetvPotimCd;
        private String photoAthnAgreeDivYn;
        private String photoAthnConnIpadr;
        private String photoAthnAgncyId;
        private String photoAthnRetvPrsnId;
        private String photoAthnAgncyNm;
        private String photoAthnSalerCd;
    }
}
