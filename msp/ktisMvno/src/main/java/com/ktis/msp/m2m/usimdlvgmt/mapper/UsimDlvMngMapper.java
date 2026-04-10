package com.ktis.msp.m2m.usimdlvgmt.mapper;

import java.util.List;

import com.ktis.msp.m2m.usimdlvgmt.vo.MsgQueueVO;
import com.ktis.msp.m2m.usimdlvgmt.vo.UsimDlvMngVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("usimDlvMngMapper")
public interface UsimDlvMngMapper {

    
    List<?> usimDlvMngList(UsimDlvMngVO searchVO);
    
    String usimOrdId(UsimDlvMngVO searchVO);

    void usimDlvMstInsert(UsimDlvMngVO searchVO);
    
    int usimDlvMstUpdate(UsimDlvMngVO searchVO);
    
    int usimDlvMstUpdateMsk(UsimDlvMngVO searchVO);

    List<?> usimOrdMdlList(UsimDlvMngVO searchVO);

    void usimOrdMdlInsert(UsimDlvMngVO searchVO);

    int usimOrdMdlUpdate(UsimDlvMngVO searchVO);

    List<?> usimDlvMngListEx(UsimDlvMngVO searchVO);

    List<?> getOrgIdComboList(UsimDlvMngVO searchVO);

    List<?> getMdlIdComboList(UsimDlvMngVO searchVO);
    
    List<?> getAreaNmComboList(UsimDlvMngVO searchVO);

    List<?> getM2mAddr(UsimDlvMngVO searchVO);

    List<?> getM2mMdl(UsimDlvMngVO searchVO);

    List<?> getDlvMethodCombo();

    List<?> getM2mMngHTel(UsimDlvMngVO searchVO);

    List<?> getTbNm(UsimDlvMngVO searchVO);

}
