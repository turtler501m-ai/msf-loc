package com.ktmmobile.msf.domains.form.common.commCode.dao;

import com.ktmmobile.msf.domains.form.common.commCode.dto.CommCodeInstDTO;
import com.ktmmobile.msf.domains.form.common.dto.NmcpLinkInfoDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CommCodeDAOImpl implements CommCodeDAO {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    /**
     * detail - 1차 코드 insert
     */
    @Override
    public void commCodeInsertFst(CommCodeInstDTO commCodeInstDTO) {
        sqlSessionTemplate.insert("CommCodeMapper.commCodeInsertByFstTable", commCodeInstDTO);
    }

    /**
     * detail - 2차 코드 insert 새로운 시도
     */
    @Override
    public void commCodeInsertSnd(HashMap<String, Object> map) {
        sqlSessionTemplate.insert("CommCodeMapper.commCodeInsertBySndTable", map);
    }

    /**
     * detail - 1차 코드 getOne
     */
    @Override
    public CommCodeInstDTO getFstCodeTble(String idx) {
        CommCodeInstDTO commCodeInstDTO = sqlSessionTemplate.selectOne("CommCodeMapper.commCodeSelectOneByFstTble", idx);
        return commCodeInstDTO;
    }

    /**
     * detail - 2차 코드 getList
     */
    @Override
    public List<CommCodeInstDTO> getSndCodeList(String idx) {
        return sqlSessionTemplate.selectList("CommCodeMapper.commCodeSelectListBySndTble", idx);
    }

    /**
     * modify - 1차 코드 update
     */
    @Override
    public void modifyFstTable(CommCodeInstDTO commCodeInstDTO) {
         sqlSessionTemplate.update("CommCodeMapper.commCodeUploadByFstTble", commCodeInstDTO);
    }

    /**
     * modify - 2차 코드 update
     */
    @Override
    public void updateBySndTable(HashMap<String, Object> map) {
        sqlSessionTemplate.update("CommCodeMapper.updateBySndTable", map);
    }

    /**
     * 2차 코드 duplecheck
     */
    @Override
    public int commCodeIdDupCheckAjax(String cdGroupId) {
        return (Integer)sqlSessionTemplate.selectOne("CommCodeMapper.commCodeIdDupCheckAjax",cdGroupId);
    }

    @Override
    public NmcpLinkInfoDto getLinkInfo(NmcpLinkInfoDto nmcpLinkInfoDto) {
        return sqlSessionTemplate.selectOne("CommCodeMapper.listLinkInfo",nmcpLinkInfoDto);
    }

    @Override
    public int updateLinkInfo(NmcpLinkInfoDto nmcpLinkInfoDto) {
        return sqlSessionTemplate.update("CommCodeMapper.updateLinkInfo",nmcpLinkInfoDto);
    }

    @Override
    public void updateMnpCmpnListInit() {
        sqlSessionTemplate.update("CommCodeMapper.updateMnpCmpnListInit");
    }

    @Override
    public void updateMnpCmpn(NmcpCdDtlDto cdDtlDto) {
        sqlSessionTemplate.update("CommCodeMapper.updateMnpCmpn", cdDtlDto);
    }

}
