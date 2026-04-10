package com.ktis.msp.m2m.usimrephistgmt.mapper;

import java.util.List;

import com.ktis.msp.m2m.usimrephistgmt.vo.UsimRepHistVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("usimRepHistMapper")
public interface UsimRepHistMapper {

    
    List<?> usimRepHistList(UsimRepHistVO searchVO);

    void usimRepHistInsert(UsimRepHistVO searchVO);
            
    List<?> usimRepHistListEx(UsimRepHistVO searchVO);

    List<?> getOrgIdComboList(UsimRepHistVO searchVO);

    List<?> getMdlIdComboList(UsimRepHistVO searchVO);
    
    List<?> getAreaNmComboList(UsimRepHistVO searchVO);

}

