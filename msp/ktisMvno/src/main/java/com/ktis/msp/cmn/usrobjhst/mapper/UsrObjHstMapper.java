package com.ktis.msp.cmn.usrobjhst.mapper;

import java.util.List;

import com.ktis.msp.cmn.usrobjhst.vo.UsrObjHstVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("usrObjHstMapper")
public interface UsrObjHstMapper {

    List<UsrObjHstVO> getObjHstList(UsrObjHstVO searchVO);

    List<UsrObjHstVO> getObjSource(UsrObjHstVO searchVO);
}
