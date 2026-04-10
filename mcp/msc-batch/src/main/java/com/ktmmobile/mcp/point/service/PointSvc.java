package com.ktmmobile.mcp.point.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.point.dao.PointDao;
import com.ktmmobile.mcp.point.dto.CustPointTxnDtlDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;
import com.ktmmobile.mcp.point.dto.CustPointGiveTgtBasDto;
import com.ktmmobile.mcp.point.dto.CustPointGiveBaseBasDto;


@Service
public class PointSvc {

    private static final Logger logger = LoggerFactory.getLogger(PointSvc.class);

	@Autowired
	private PointDao pointDao;

	public List<CustPointTxnDtlDto> selectExpireCustPoint() {
		return pointDao.selectExpireCustPoint();
	}

	public int updateCustPointAcuTxnDtl(CustPointTxnDtlDto custPointTxnDtlDto) {
		return pointDao.updateCustPointAcuTxnDtl(custPointTxnDtlDto);
	}
	
	public int insertCustPointTxn(CustPointTxnDto custPointTxnDto) {
		return pointDao.insertCustPointTxn(custPointTxnDto);
	}
	
	public int insertCustPointTxnDtl(CustPointTxnDtlDto custPointTxnDtlDto) {
		return pointDao.insertCustPointTxnDtl(custPointTxnDtlDto);
	}

	public int updateCustPointGiveBaseBas(CustPointTxnDto custPointTxnDto) {
		return pointDao.updateCustPointGiveBaseBas(custPointTxnDto);
	}

	public List<CustPointGiveTgtBasDto> selectCustPointGiveTgtList() {
		return pointDao.selectCustPointGiveTgtList();
	}

	public CustPointGiveBaseBasDto selectCustPointGiveBaseBas(CustPointGiveBaseBasDto custPointGiveBaseBasDto) {
		return pointDao.selectCustPointGiveBaseBas(custPointGiveBaseBasDto);
	}

	public int insertCustPointGiveBaseBas(CustPointGiveBaseBasDto custPointGiveBaseBasDto) {
		return pointDao.insertCustPointGiveBaseBas(custPointGiveBaseBasDto);
	}

	public int insertCustPointAcuTxnDtl(CustPointTxnDtlDto custPointTxnDtlDto) {
		return pointDao.insertCustPointAcuTxnDtl(custPointTxnDtlDto);
	}
	
	public int updateCustPointGiveTgtList(CustPointGiveTgtBasDto custPointGiveTgtBasDto) {
		return pointDao.updateCustPointGiveTgtList(custPointGiveTgtBasDto);
	}

	
}
