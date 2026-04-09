package com.ktmmobile.msf.common.dto;

import java.io.Serializable;
import java.util.List;

import com.ktmmobile.msf.common.dto.db.NmcpCdDtlDto;

public class CdGroupBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 코드그룹아이디 */
    private String cdGroupId;

    /** 코드그룹이름 */
    private String cdGroupNm;

    /** 리스트 코드 */
    private List<NmcpCdDtlDto> listCdBean;

    public String getCdGroupId() {
        return cdGroupId;
    }

    public void setCdGroupId(String cdGroupId) {
        this.cdGroupId = cdGroupId;
    }

    public String getCdGroupNm() {
        return cdGroupNm;
    }

    public void setCdGroupNm(String cdGroupNm) {
        this.cdGroupNm = cdGroupNm;
    }

    public List<NmcpCdDtlDto> getListCdBean() {
        return listCdBean;
    }

    public void setListCdBean(List<NmcpCdDtlDto> listCdBean) {
        this.listCdBean = listCdBean;
    }




}
