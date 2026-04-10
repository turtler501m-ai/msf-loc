package com.ktis.msp.bnd.bondmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.bnd.bondmgmt.mapper.BondMgmtMapper;
import com.ktis.msp.bnd.bondmgmt.vo.BondMgmtVO;

@Service
public class BondMgmtService extends BaseService {
	
	@Autowired
	private BondMgmtMapper bondMgmtMapper;
	
	// 채권판매정보 판매회차 가져오기
	public List<?> selectBondSaleInfoNum(BondMgmtVO searchVO) {
		logger.debug("채권판매정보 판매회차 가져오기..................................");
		logger.debug("searchVO=" + searchVO);
		
		return bondMgmtMapper.selectBondSaleInfoNum(searchVO);
	}

	// 판매채권 수납내역서 수납월 정보 가져오기
	public List<?> selectBillYm(BondMgmtVO searchVO) {
		logger.debug("판매채권 수납내역서 수납월 정보 가져오기..................................");
		logger.debug("searchVO=" + searchVO);
		
		if(searchVO.getBondSeqNo() == null || "".equals(searchVO.getBondSeqNo())){
			throw new MvnoRunException(-1, "판매회차 누락");
		}
		
		return bondMgmtMapper.selectBillYm(searchVO);
	}
}
	
