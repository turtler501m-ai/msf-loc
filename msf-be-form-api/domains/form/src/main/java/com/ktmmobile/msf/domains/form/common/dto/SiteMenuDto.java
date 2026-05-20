package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class SiteMenuDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int menuSeq;
    private String menuCode;
    private String menuNm;
    private String groupKey;
    private String prntsKey;
    private int depthKey;
    private String urlAdr;
    private int repUrlSeq;
    private String statVal;
    private String acesAlwdYn;
    private String menuOutputCd;
    private String cntpntCd;
    private String platformCd;
    private String menuLinkCd;
    private String menuStressYn;
    private String mobileMenuUseYn;
    private String appMenuUseYn;
    private String pstngStartDate;
    private String pstngEndDate;
    private String chatbotTipSbst;
    private String cretId;
    private String amdId;
    private String cretDt;
    private String amdDt;
    private int sortKey;
    private String menuHierTypeCd;
    private MultipartFile listImg;
    private String imgDesc;
    private String linkUrlAdr;
    private String menuDesc;
    private String attYn;
    private String filePathNm;
    private String fileType;
    private int fileCapa;
    private MultipartFile listImg2;
    private String imgDesc2;
    private String linkUrlAdr2;
    private String attYn2;
    private int nextDepthCnt;
    private String autGradeCd = "";

}
