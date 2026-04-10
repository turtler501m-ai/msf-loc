package com.ktmmobile.mcp.usim.dao;

import com.ktmmobile.mcp.common.mplatform.vo.RetvUsimChgAcceptPsblVO;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReplaceUsimDaoImpl implements ReplaceUsimDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public long generateReplaceUsimOsstNo() {
		return sqlSessionTemplate.selectOne("ReplaceUsimMapper.generateReplaceUsimOsstNo");
	}

	@Override
	public int saveFreeUsimOsst(RetvUsimChgAcceptPsblVO retvUsimChgAcceptPsblVO) {
		return sqlSessionTemplate.insert("ReplaceUsimMapper.saveFreeUsimOsst", retvUsimChgAcceptPsblVO);
	}

	@Override
	public int saveFreeUsimReqMst(ReplaceUsimDto replaceUsimDto) {
		return sqlSessionTemplate.insert("ReplaceUsimMapper.saveFreeUsimReqMst", replaceUsimDto);
	}

	@Override
	public int saveFreeUsimReqDtl(RetvUsimChgAcceptPsblVO retvUsimChgAcceptPsblVO) {
		return sqlSessionTemplate.insert("ReplaceUsimMapper.saveFreeUsimReqDtl", retvUsimChgAcceptPsblVO);
	}

	@Override
	public List<RetvUsimChgAcceptPsblVO> selectFreeUsimOsstResult(long osstNo) {
		return sqlSessionTemplate.selectList("ReplaceUsimMapper.selectFreeUsimOsstResult", osstNo);
	}

}
