package com.ktis.msp.rcp.rcpMgmt.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.rcp.rcpMgmt.mapper.PointRecoveryMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.PointTxnVO;

@Service
public class PointRecoveryService extends BaseService{

	@Autowired
	private PointRecoveryMapper pointRecoveryMapper;

	//고객정보조회 select
	public PointTxnVO selectCustPointTxn(String requestKey) {
		return pointRecoveryMapper.selectCustPointTxn(requestKey);
	}
	

	@Transactional(rollbackFor=Exception.class)
	public void setTransactionPoint(PointTxnVO pointTxnVO) throws MvnoServiceException{
		
		try {
			//고객포인트지급기준기본 update
			pointRecoveryMapper.updateCustPointGiveBaseBas(pointTxnVO);
			
			pointTxnVO.setTotRemainPoint(0);
			
			//고객포인트내역 insert
			pointRecoveryMapper.insertCustPointTxn(pointTxnVO);
			
			//고객포인트적립내역 상세 insert
			pointRecoveryMapper.insertCustPointAcuTxnDtl(pointTxnVO);
		
		}catch(Exception e) {
			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.SQL_ERROR", null, Locale.getDefault()));
		}
		
	}


}
