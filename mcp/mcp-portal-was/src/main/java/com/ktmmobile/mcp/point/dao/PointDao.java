package com.ktmmobile.mcp.point.dao;

import com.ktmmobile.mcp.point.dto.CustPointDto;
import com.ktmmobile.mcp.point.dto.CustPointGiveTgtBasDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDtlDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;

import java.util.List;

public interface PointDao {
	public int updateCustPointGiveBaseBas(CustPointTxnDto pointTxnDto);
	public int insertCustPointTxn(CustPointTxnDto pointTxnDto);
	public List<CustPointTxnDtlDto> selectPsblCustPointTxnDtlList(CustPointTxnDto pointTxnDto);
	public int insertCustPointTxnDtl(CustPointTxnDtlDto useCustPointTxnDtlDto);
	public int updateCustPointTxnDtl(CustPointTxnDtlDto cstPointTxnDtlDto);
	public int insertCustPointAcuTxnDtl(CustPointTxnDto pointTxnDto);
	public CustPointDto selectCustPointGiveBaseBas(CustPointDto custPointDto);
	public int insertCustPointGiveBaseBas(CustPointDto custPointDto);
	public int insertCustPointTxnSUE(CustPointTxnDto custPointTxnDto);
	public int insertCustPointGiveTgtBas(CustPointGiveTgtBasDto custPointGiveTgtBasDto);
}

	

