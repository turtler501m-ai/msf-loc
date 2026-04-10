package com.ktmmobile.mcp.point.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDtlDto;
import com.ktmmobile.mcp.point.dto.CustPointGiveTgtBasDto;
import com.ktmmobile.mcp.point.dto.CustPointGiveBaseBasDto;


@Mapper
public interface PointMapper {

	List<CustPointTxnDtlDto> selectExpireCustPoint();
	
	public void updateCustPointAcuTxnDtl(CustPointTxnDtlDto custPointTxnDtlDto);

	public int insertCustPointTxn(CustPointTxnDto custPointTxnDto);
	
	public int insertCustPointTxnDtl(CustPointTxnDtlDto custPointTxnDtlDto);
	
	public int updateCustPointGiveBaseBas(CustPointTxnDto custPointTxnDto);

	List<CustPointGiveTgtBasDto> selectCustPointGiveTgtList();
	
	CustPointGiveBaseBasDto selectCustPointGiveBaseBas(CustPointGiveBaseBasDto custPointGiveBaseBasDto);
	
	public int insertCustPointGiveBaseBas(CustPointGiveBaseBasDto custPointGiveBaseBasDto);
	
	public int insertCustPointAcuTxnDtl(CustPointTxnDtlDto custPointTxnDtlDto);
	
	public void updateCustPointGiveTgtList(CustPointGiveTgtBasDto custPointGiveTgtBasDto);
	
}