package com.ktmmobile.msf.domains.form.common.commCode.dao;

import com.ktmmobile.msf.domains.form.common.commCode.dto.CommCodeInstDTO;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpLinkInfoDto;

import java.util.HashMap;
import java.util.List;

public interface CommCodeDAO {

    public void commCodeInsertFst(CommCodeInstDTO commCodeInstDTO);

    public CommCodeInstDTO getFstCodeTble(String idx);

    public List<CommCodeInstDTO> getSndCodeList(String idx);

    public void commCodeInsertSnd(HashMap<String, Object> map);

    public void modifyFstTable(CommCodeInstDTO commCodeInstDTO);

    public int commCodeIdDupCheckAjax(String cdGroupId);

    public void updateBySndTable(HashMap<String, Object> map);

    /**
     * <pre>
     * 설명     : 고객포탈 링크 정보
     * @param nmcpLinkInfoDto
     * @return
     * @return: NmcpLinkInfoDto
     * </pre>
     */
    public NmcpLinkInfoDto getLinkInfo(NmcpLinkInfoDto nmcpLinkInfoDto);

    /**
     * <pre>
     * 설명     : 고객포탈 링크 정보 업데이트
     * @param nmcpLinkInfoDto
     * @return
     * @return: int
     * </pre>
     */
    public int updateLinkInfo(NmcpLinkInfoDto nmcpLinkInfoDto);

    public void updateMnpCmpnListInit() ;

    public void updateMnpCmpn(NmcpCdDtlDto cdDtlDto) ;

}
