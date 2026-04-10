package com.ktis.msp.bnd.bondmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.bnd.bondmgmt.vo.BondMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bondMgmtMapper")
public interface BondMgmtMapper {

	// 채권판매정보 판매회차 가져오기
	List<Map<String, Object>> selectBondSaleInfoNum(BondMgmtVO searchVO);
	
	// 판매채권 수납내역서 수납월 정보 가져오기
	List<?> selectBillYm(BondMgmtVO searchVO);
}


