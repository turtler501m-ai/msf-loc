package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Y24 moscPrdcTrtmPreChk response.
 */
@Getter
@Setter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "outDto")
public class AdditionPreCheckResVO {

    private String rsltCd;
    private String resultCode;
    private String sbscYn;
    private String resltMsg;
    private String svcMsg;
    private String globalNo;
    private String prdcCd;
    private List<String> prdcCdList;
    private List<String> preCheckFailedPrdcCdList;
    private List<String> onlineCancelUnavailablePrdcCdList;
    private List<String> resltMsgList;
}
