package com.ktis.msp.m2m.usimordgmt.mapper;

import java.util.List;

import com.ktis.msp.m2m.usimordgmt.vo.MsgQueueVO;
import com.ktis.msp.m2m.usimordgmt.vo.UsimOrdMngVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("usimOrdMngMapper")
public interface UsimOrdMngMapper {

    
    List<?> usimOrdMngList(UsimOrdMngVO searchVO);
    
    String usimOrdId(UsimOrdMngVO searchVO);

    void usimOrdMstInsert(UsimOrdMngVO searchVO);
    
    int usimOrdMstUpdate(UsimOrdMngVO searchVO);
    
    int usimOrdMstUpdateMsk(UsimOrdMngVO searchVO);

    List<?> usimOrdMdlList(UsimOrdMngVO searchVO);

    void usimOrdMdlInsert(UsimOrdMngVO searchVO);

    int usimOrdMdlUpdate(UsimOrdMngVO searchVO);

    List<?> usimOrdMngListEx(UsimOrdMngVO searchVO);
    
    List<?> usimOrdMngListEx2(UsimOrdMngVO searchVO);

    List<?> getOrgIdComboList(UsimOrdMngVO searchVO);

    List<?> getMdlIdComboList(UsimOrdMngVO searchVO);
    
    List<?> getAreaNmComboList(UsimOrdMngVO searchVO);

    List<?> getM2mAddr(UsimOrdMngVO searchVO);

    List<?> getM2mMdl(UsimOrdMngVO searchVO);

    List<?> getDlvMethodCombo();

    List<?> getM2mMngHTel(UsimOrdMngVO searchVO);

    List<?> getTbNm(UsimOrdMngVO searchVO);
    

}
