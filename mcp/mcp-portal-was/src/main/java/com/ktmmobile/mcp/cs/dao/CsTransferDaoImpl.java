package com.ktmmobile.mcp.cs.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.cs.dto.CsMcpUserCntrDto;
import com.ktmmobile.mcp.cs.dto.NflChgTrnsDto;
import com.ktmmobile.mcp.cs.dto.NflChgTrnsfeDto;

@Repository
public class CsTransferDaoImpl implements CsTransferDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<CsMcpUserCntrDto> telNoList(String userId) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("CsTransfer_Query.telNoList", userId);
	}

	@Override
	public int grantorRegSave(NflChgTrnsDto nflChgTrnsDto) {
		return sqlSessionTemplate.insert("CsTransfer_Query.grantorRegSave", nflChgTrnsDto);
	}

	@Override
	public NflChgTrnsfeDto assigneeReg(UserSessionDto userSessionDto) {
		return sqlSessionTemplate.selectOne("CsTransfer_Query.assigneeReg", userSessionDto);
	}

	@Override
	public NflChgTrnsDto checkTrnsApyNoAjax(NflChgTrnsfeDto nflChgTrnsfeDto) {
		return sqlSessionTemplate.selectOne("CsTransfer_Query.checkTrnsApyNoAjax", nflChgTrnsfeDto);
	}

	@Override
	public int assigneeRegSave(NflChgTrnsfeDto nflChgTrnsfeDto) {
		return sqlSessionTemplate.insert("CsTransfer_Query.assigneeRegSave", nflChgTrnsfeDto);
	}

	@Override
	public int checkDupl(NflChgTrnsfeDto nflChgTrnsfeDto) {
		return (Integer)sqlSessionTemplate.selectOne("CsTransfer_Query.checkDupl", nflChgTrnsfeDto);
	}

	@Override
	public int statusEdit(NflChgTrnsfeDto nflChgTrnsfeDto) {
		return sqlSessionTemplate.update("CsTransfer_Query.statusEdit", nflChgTrnsfeDto);
	}
}
