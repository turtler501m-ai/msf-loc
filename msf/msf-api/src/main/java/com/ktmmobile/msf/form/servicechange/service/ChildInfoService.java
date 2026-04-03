package com.ktmmobile.msf.form.servicechange.service;

import com.ktmmobile.msf.form.servicechange.dto.ChildInfoDto;

import java.util.List;

public interface ChildInfoService {

    /**
     * @Description : 법정대리인 조회
     * @param : svcCntrNo
     * @return :ChildInfoDto
     * @Create Date : 2024. 09. 24
     */
    public ChildInfoDto getMinorAgentInfo(String minorAgentSvcCntrNo);

    /**
     * @Description : 자녀 회선 목록 조회
     * @param : childInfoDto
     * @return :List<ChildInfoDto>
     * @Create Date : 2024. 09. 24
     */
    public List<ChildInfoDto> selectChildCntrList(String famSeq);

    /**
     * @Description : 자녀 회선 조회
     * @param : svcCntrNo
     * @param : childSvcCntrNo
     * @return :ChildInfoDto
     * @Create Date : 2024. 09. 27
     */
    public ChildInfoDto selectChildCntr(ChildInfoDto childInfoDto);
}
