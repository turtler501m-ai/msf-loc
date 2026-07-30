package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Data
@XmlRootElement(name = "inDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class UsimChangeUC0Request {

    @XmlTransient
    private Long requestKey;
    @XmlTransient
    private String parentScanId;
    @XmlTransient
    private String custId; //	고객번호
    @XmlTransient
    private String ncn; //	사용자 서비스계약번호
    @XmlTransient
    private String ctn; //	사용자 전화번호
    @XmlTransient
    private String agentCd;
    @XmlTransient
    private String simTypeCd;
    @XmlTransient
    private String cstmrTypeCd;
    @XmlTransient
    private String simPurchaseMethod;
    @XmlTransient
    private String usimPriceTypeCd;
    @XmlTransient
    private boolean hasSim;
    @XmlTransient
    private boolean usimSucc; // 유심 승계 여부
    private String mvnoOrdNo; //	MVNO 오더 번호
    private String custNo; //	고객번호
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String tlphNo; //	전화번호
    private String svcContId; //	서비스계약번호
    private String oderTypeCd = "H38"; //	오더유형코드 (H38 고정)
    private String usimPymnMthdCd; //	SIM 수납방법 코드(R:즉납, B:후청구, N:비구매)
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String iccId; //	SIM 일련번호(eSim인 경우 Null)
    private String cntpntCd; //	접점코드
    @XmlTransient
    private String slsPrsnId; //	판매자아이디
    private String usimChgRsnCd = "37"; //	SIM변경 사유코드(37:일반, 41:고장-고객, 42:고장-사업자, 33:분실, eSim-37고정)
    @JsonIgnore
    private String osstOrdNo;

    public void transPymnMthCd() {

        this.usimPymnMthdCd = this.simPurchaseMethod;
        this.usimPriceTypeCd = this.simPurchaseMethod;

        if (hasSim) {
            this.usimPymnMthdCd = "N";
            this.usimPriceTypeCd = "N";
            this.usimSucc = true; // 비구매면 현재 유심 사용
        }

        if ("ESIM".equals(simTypeCd)) {
            this.usimPymnMthdCd = "B";
            this.usimPriceTypeCd = "B";
            this.iccId = null;
        }
    }

}
