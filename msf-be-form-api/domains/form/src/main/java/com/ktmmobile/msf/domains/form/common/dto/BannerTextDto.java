package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BannerTextDto implements Serializable {

    private static final long serialVersionUID = -1207742026359034810L;

    private String bannTxtSeq;
    private String txtDtlSeq;
    private String bannTxtType;
    private String bgColor;
    private String bannUseYn;
    private String bannPstngStartDate;
    private String bannPstngEndDate;
    private String txtContent;
    private String indcOdrg;
    private String txtUseYn;
    private String txtPstngStartDate;
    private String txtPstngEndDate;

}
