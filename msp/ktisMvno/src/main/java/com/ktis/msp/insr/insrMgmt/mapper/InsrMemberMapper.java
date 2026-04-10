package com.ktis.msp.insr.insrMgmt.mapper;

import java.util.List;

import com.ktis.msp.insr.insrMgmt.vo.InsrMemberVO;
import com.ktis.msp.insr.insrMgmt.vo.InsrRegVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("InsrMemberMapper")
public interface InsrMemberMapper {
	
	List<InsrMemberVO> getInsrMemberList(InsrMemberVO insrMemberVO);
	
	List<InsrMemberVO> getInsrMemberListByExcel(InsrMemberVO insrMemberVO);
	
	InsrRegVO getInsrCntrInfo(InsrRegVO insrRegVO);
	
	String getIntmInsrOrderYn(InsrRegVO insrRegVO);
	
	int updMspIntmInsrOrderByCancel(InsrRegVO insrRegVO);
	
	int regMspIntmInsrOrder(InsrRegVO insrRegVO);
}
