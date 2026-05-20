package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PopupDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String popupSeq;
    private String popupSubject;
    private String widthSize;
    private String heightSize;
    private String xcrd;
    private String ycrd;
    private String popupSbst;
    private String popupUrl;
    private String popupOpenStat;
    private String currentUrl;
    private String menuCode;
    private String outputMenu = "";
    private String pstngStartDate;
    private String pstngEndDate;
    private String platformCd;
    private String popupOutputCd = "";
    private String indcOdrg;
    private String popupShowUrl = "";
    private String filePathNm = "";
    private String zipNo;
    private String roadAddr;
    private String scrollYn = "N"; // 스크롤여부
    private String usageType;
    private String oneTimePopupGrp;
    private String contentType;
    private String device;

}
