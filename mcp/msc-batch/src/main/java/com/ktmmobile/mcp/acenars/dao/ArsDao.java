package com.ktmmobile.mcp.acenars.dao;

import com.ktmmobile.mcp.acenars.dto.ArsDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArsDao {

	@Autowired
	private SqlSession bootoraSqlSession;

	public List<ArsDto> getAcenVocEndTrg() {
		return bootoraSqlSession.selectList("ArsMapper.getAcenVocEndTrg");
	}

	public int updateAcenVocInfo(String reqSeq) {
		return bootoraSqlSession.update("ArsMapper.updateAcenVocInfo", reqSeq);
	}

	public int insertAcenVocHist(String reqSeq) {
		return bootoraSqlSession.insert("ArsMapper.insertAcenVocHist", reqSeq);
	}

	public ArsDto getAcenVocInfo(String reqSeq) {
		return bootoraSqlSession.selectOne("ArsMapper.getAcenVocInfo", reqSeq);
	}

}
