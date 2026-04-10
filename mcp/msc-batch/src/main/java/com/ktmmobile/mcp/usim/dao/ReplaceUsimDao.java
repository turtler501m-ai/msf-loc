package com.ktmmobile.mcp.usim.dao;

import com.ktmmobile.mcp.common.mplatform.dto.UsimChgAcceptVO;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReplaceUsimDao {

	@Autowired
	private SqlSession bootoraSqlSession;

	public String selectFreeUsimSettings(String dtlCd) {
		return bootoraSqlSession.selectOne("ReplaceUsimMapper.selectFreeUsimSettings", dtlCd);
	}

	public long generateReplaceUsimOsstNo(long seq) {
		return bootoraSqlSession.selectOne("ReplaceUsimMapper.generateReplaceUsimOsstNo", seq);
	}

	public int saveFreeUsimOsst(UsimChgAcceptVO usimChgAcceptVO) {
		return bootoraSqlSession.insert("ReplaceUsimMapper.saveFreeUsimOsst", usimChgAcceptVO);
	}

	public int updateFreeUsimReqDtl(ReplaceUsimDto updateDto) {
		return bootoraSqlSession.update("ReplaceUsimMapper.updateFreeUsimReqDtl", updateDto);
	}

	public int updateFreeUsimReqMst(ReplaceUsimDto updateDto) {
		return bootoraSqlSession.update("ReplaceUsimMapper.updateFreeUsimReqMst", updateDto);
	}

}
