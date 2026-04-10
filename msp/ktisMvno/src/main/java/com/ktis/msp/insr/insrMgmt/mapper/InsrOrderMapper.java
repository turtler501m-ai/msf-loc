package com.ktis.msp.insr.insrMgmt.mapper;

import java.util.List;

import com.ktis.msp.insr.insrMgmt.vo.InsrOrderVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("InsrOrderMapper")
public interface InsrOrderMapper {
	
	List<InsrOrderVO> getInsrOrderList(InsrOrderVO insrOrderVO);
	
	List<InsrOrderVO> getInsrOrderListByExcel(InsrOrderVO insrOrderVO);
	
	int regIntmInfoByUsimCust(InsrOrderVO insrOrderVO);
	
	int regVrfyRslt(InsrOrderVO insrOrderVO);
	
	int regAdmsn(InsrOrderVO insrOrderVO);
	
	int regIntmInsrCanInfo(InsrOrderVO insrOrderVO);
	
	List<InsrOrderVO> getInsrReadyList(InsrOrderVO insrOrderVO);
	
	List<InsrOrderVO> getInsrReadyListByExcel(InsrOrderVO insrOrderVO);
}
