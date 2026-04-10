package com.ktis.msp.rcp.prepaidMgmt.mapper;

import java.util.List;

import com.ktis.msp.rcp.prepaidMgmt.vo.PrepaidVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("PrepaidMgmtMapper")
public interface PrepaidMgmtMapper {
	public abstract List<?> getList(PrepaidVo prepaidVo);
	
	public abstract List<?> getlistExcel(PrepaidVo prepaidVo);

}
