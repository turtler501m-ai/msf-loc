package com.ktmmobile.mcp.cs.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.cs.dto.TransferDto;

@Repository
public class TransferDaoImpl implements TransferDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int countTransferList(TransferDto transferDto) {
		return (Integer)sqlSessionTemplate.selectOne("CsTransfer_Query.countTransferList", transferDto);
	}

	@Override
	public List<TransferDto> transferList(TransferDto transferDto, int skipResult, int maxResult) {
		return sqlSessionTemplate.selectList("CsTransfer_Query.transferList", transferDto, new RowBounds(skipResult, maxResult));
	}

	@Override
	public TransferDto transferDetail(int trnsApyNo) {
		return sqlSessionTemplate.selectOne("CsTransfer_Query.transferDetail", trnsApyNo);
	}

	@Override
	public int trnsStausEdit() {
		return (Integer)sqlSessionTemplate.update("CsTransfer_Query.trnsStausEdit", null);
	}
	
	@Override
	public int transferDetailSave(TransferDto transferDto) {
		return sqlSessionTemplate.update("CsTransfer_Query.transferDetailSave", transferDto);
	}
	
	@Override
	public int transferDetailDel(TransferDto transferDto) {
		return sqlSessionTemplate.update("CsTransfer_Query.transferDetailDel", transferDto);
	}
}
