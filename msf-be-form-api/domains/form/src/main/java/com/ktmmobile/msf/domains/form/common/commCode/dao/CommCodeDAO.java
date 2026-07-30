package com.ktmmobile.msf.domains.form.common.commCode.dao;

import com.ktmmobile.msf.domains.form.common.commCode.dto.CommCodeInstDTO;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpLinkInfoDto;

import java.util.HashMap;
import java.util.List;

public interface CommCodeDAO {

    void commCodeInsertFst(CommCodeInstDTO commCodeInstDTO);

    CommCodeInstDTO getFstCodeTble(String idx);

    List<CommCodeInstDTO> getSndCodeList(String idx);

    void commCodeInsertSnd(HashMap<String, Object> map);

    void modifyFstTable(CommCodeInstDTO commCodeInstDTO);

    int commCodeIdDupCheckAjax(String cdGroupId);

    void updateBySndTable(HashMap<String, Object> map);

    /**
     * <pre>
     * 설명     : 고객포탈 링크 정보
     * @param nmcpLinkInfoDto
     * @return
     * @return: NmcpLinkInfoDto
     * </pre>
     */
    NmcpLinkInfoDto getLinkInfo(NmcpLinkInfoDto nmcpLinkInfoDto);

    /**
     * <pre>
     * 설명     : 고객포탈 링크 정보 업데이트
     * @param nmcpLinkInfoDto
     * @return
     * @return: int
     * </pre>
     */
    int updateLinkInfo(NmcpLinkInfoDto nmcpLinkInfoDto);

    void updateMnpCmpnListInit() ;

    void updateMnpCmpn(NmcpCdDtlDto cdDtlDto) ;

}
