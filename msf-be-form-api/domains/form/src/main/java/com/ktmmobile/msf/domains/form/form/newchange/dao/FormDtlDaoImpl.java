package com.ktmmobile.msf.domains.form.form.newchange.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.form.newchange.dto.FormDtlDTO;

@Repository
public class FormDtlDaoImpl implements FormDtlDao {

    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int FormDtlListCNT(FormDtlDTO formDtlDTO) {
        return (Integer) sqlSessionTemplate.selectOne("FormDtlMapper.FormDtlListCNT", formDtlDTO);
    }

    @Override
    public List<FormDtlDTO> FormDtlList(FormDtlDTO formDtlDTO, int skipResult, int maxResult) {
        return sqlSessionTemplate.selectList("FormDtlMapper.FormDtlList", formDtlDTO, new RowBounds(skipResult, maxResult));
    }

    @Override
    public boolean FormDtlInsert(FormDtlDTO formDtlDTO) {
        return 0 < sqlSessionTemplate.insert("FormDtlMapper.FormDtlinsert", formDtlDTO);
    }

    @Override
    public FormDtlDTO FormDtlView(FormDtlDTO formDtlDTO) {
        return sqlSessionTemplate.selectOne("FormDtlMapper.FormDtlView", formDtlDTO);
    }

    @Override
    public boolean FormDtlUpdate(FormDtlDTO formDtlDTO) {
        return 0 < sqlSessionTemplate.update("FormDtlMapper.FormDtlUpdate", formDtlDTO);
    }


    @Override
    public String FormDtlGetVersionAjax(String cdGroupId1, String cdGroupId2) {
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("cdGroupId1", cdGroupId1);
        hm.put("cdGroupId2", cdGroupId2);
        return sqlSessionTemplate.selectOne("FormDtlMapper.FormDtlGetVersionAjax", hm);
    }

    @Override
    public List<FormDtlDTO> getFormDtlList() {
        return sqlSessionTemplate.selectList("FormDtlMapper.getFormDtlList");
    }


    @Override
    public List<FormDtlDTO> getFormList(FormDtlDTO formDtlDTO) {
        return sqlSessionTemplate.selectList("FormDtlMapper.getFormList", formDtlDTO);
    }

    @Override
    public int etcNoticeListCnt(FormDtlDTO formDtlDTO) {
        return (Integer) sqlSessionTemplate.selectOne("FormDtlMapper.etcNoticeListCnt", formDtlDTO);
    }

    @Override
    public List<FormDtlDTO> etcNoticeList(FormDtlDTO formDtlDTO, int skipResult, int maxResult) {
        return sqlSessionTemplate.selectList("FormDtlMapper.etcNoticeList", formDtlDTO, new RowBounds(skipResult, maxResult));
    }

    @Override
    public List<FormDtlDTO> getCdGroupIdList(FormDtlDTO formDtlDTO) {
        return sqlSessionTemplate.selectList("FormDtlMapper.getCdGroupIdList", formDtlDTO);
    }

    @Override
    public List<FormDtlDTO> getEtcCdGroupIdList(FormDtlDTO formDtlDTO) {
        return sqlSessionTemplate.selectList("FormDtlMapper.getEtcCdGroupIdList", formDtlDTO);
    }

    @Override
    public FormDtlDTO FormDtlViewByDate(FormDtlDTO formDtlDTO) {
        return sqlSessionTemplate.selectOne("FormDtlMapper.FormDtlViewByDate", formDtlDTO);
    }
}
