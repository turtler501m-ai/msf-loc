package com.ktis.msp.m2m.usimordaddrgmt.mapper;

import java.util.List;

import com.ktis.msp.m2m.usimordaddrgmt.vo.UsimOrdAddrVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("usimOrdAddrMapper")
public interface UsimOrdAddrMapper {

    
    List<?> usimOrdAddrList(UsimOrdAddrVO searchVO);

    void usimOrdAddrInsert(UsimOrdAddrVO searchVO);
    
    int usimOrdAddrUpdate(UsimOrdAddrVO searchVO);
        
    List<?> usimOrdAddrListEx(UsimOrdAddrVO searchVO);

    List<?> getOrgIdComboList(UsimOrdAddrVO searchVO);
    
    int usimOrdAddrDelete(UsimOrdAddrVO searchVO);
    
}
