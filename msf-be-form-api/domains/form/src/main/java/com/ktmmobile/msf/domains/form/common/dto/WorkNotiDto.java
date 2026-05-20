package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkNotiDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String urlSeq;
    private String urlNm;
    private String url;
    private String metaDesc;
    private String metaKywrd;
    private String cntpntCd;
    private String platformCd;
    private String urlDivCd;
    private String loginMustYn;
    private String statLdupYn;
    private String aiRecoShowYn;
    private String floatingShowYn;
    private String floatingTipSbst;
    private String chatbotTipSbst;
    private String sysWorkNotiRegYn;
    private String sysWorkNotiRegDt;
    private String menuSeq;

    // NMCP_MENU_URL_ATRIB_DTL
    private String urlAtribValCd;
    private String atribUseYn;
}
