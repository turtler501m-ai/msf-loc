package com.ktmmobile.mcp.point.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.point.service.PointSvc;
import com.ktmmobile.mcp.point.dto.CustPointTxnDtlDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;
import com.ktmmobile.mcp.point.dto.CustPointGiveTgtBasDto;
import com.ktmmobile.mcp.point.dto.CustPointGiveBaseBasDto;


@Repository
public class PointDao {
	
	private static final Logger logger = LoggerFactory.getLogger(PointSvc.class);

	@Autowired
	@Qualifier(value="bootoraSqlSession")
	private SqlSession bootoraSqlSession;


	public List<CustPointTxnDtlDto> selectExpireCustPoint() {
		return bootoraSqlSession.selectList("PointMapper.selectExpireCustPoint");
	}
	
	public int updateCustPointAcuTxnDtl(CustPointTxnDtlDto custPointTxnDtlDto) {
		return bootoraSqlSession.update("PointMapper.updateCustPointAcuTxnDtl", custPointTxnDtlDto);
	}

	public int insertCustPointTxn(CustPointTxnDto custPointTxnDto) {
		return bootoraSqlSession.insert("PointMapper.insertCustPointTxn", custPointTxnDto);
	}

	public int insertCustPointTxnDtl(CustPointTxnDtlDto custPointTxnDtlDto) {
		return bootoraSqlSession.insert("PointMapper.insertCustPointTxnDtl", custPointTxnDtlDto);
	}
	
	public int updateCustPointGiveBaseBas(CustPointTxnDto custPointTxnDto) {
		return bootoraSqlSession.update("PointMapper.updateCustPointGiveBaseBas", custPointTxnDto);
	}

	public List<CustPointGiveTgtBasDto> selectCustPointGiveTgtList() {
		return bootoraSqlSession.selectList("PointMapper.selectCustPointGiveTgtList");
	}

	public CustPointGiveBaseBasDto selectCustPointGiveBaseBas(CustPointGiveBaseBasDto custPointGiveBaseBasDto) {
		return bootoraSqlSession.selectOne("PointMapper.selectCustPointGiveBaseBas", custPointGiveBaseBasDto);
	}

	public int insertCustPointGiveBaseBas(CustPointGiveBaseBasDto custPointGiveBaseBasDto) {
		return bootoraSqlSession.insert("PointMapper.insertCustPointGiveBaseBas", custPointGiveBaseBasDto);
	}

	public int insertCustPointAcuTxnDtl(CustPointTxnDtlDto custPointTxnDtlDto) {
		return bootoraSqlSession.insert("PointMapper.insertCustPointAcuTxnDtl", custPointTxnDtlDto);
	}
	
	public int updateCustPointGiveTgtList(CustPointGiveTgtBasDto custPointGiveTgtBasDto) {
		return bootoraSqlSession.update("PointMapper.updateCustPointGiveTgtList", custPointGiveTgtBasDto);
	}

	
}
