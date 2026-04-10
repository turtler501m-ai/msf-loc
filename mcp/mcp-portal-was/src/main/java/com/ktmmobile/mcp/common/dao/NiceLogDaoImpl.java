package com.ktmmobile.mcp.common.dao;

import com.ktmmobile.mcp.common.dto.NiceTryLogDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.mcp.common.dto.NiceLogDto;

@Repository
public class NiceLogDaoImpl implements NiceLogDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    @Transactional
    public void insert(NiceLogDto nicelogDto) {
        // TODO Auto-generated method stub
        sqlSessionTemplate.insert("NiceLogMapper.insertLog",nicelogDto);
    }

    @Override
    public long insertMcpNiceHist(NiceLogDto niceLogDto){
        sqlSessionTemplate.insert("NiceLogMapper.insertMcpNiceHist",niceLogDto);
        return niceLogDto.getNiceHistSeq();
    }

    @Override
    public boolean updateMcpNiceHist(NiceLogDto niceLogDto){
        return  0 < sqlSessionTemplate.update("NiceLogMapper.updateMcpNiceHist", niceLogDto);
    }

    @Override
    public NiceLogDto getMcpNiceHist(NiceLogDto niceLogDto){
        return sqlSessionTemplate.selectOne("NiceLogMapper.getMcpNiceHist" ,niceLogDto);
    }
    @Override
    public NiceLogDto getMcpNiceHistWithSeq(long niceHistSeq){
        return sqlSessionTemplate.selectOne("NiceLogMapper.getMcpNiceHistWithSeq" ,niceHistSeq);
    }


    @Override
    public NiceLogDto getMcpNiceHistWithReqSeq(NiceLogDto niceLogDto) {
        return sqlSessionTemplate.selectOne("NiceLogMapper.getMcpNiceHistWithReqSeq" ,niceLogDto);
    }

    @Override
    public NiceLogDto getMcpNiceHistWithReqSeq2(NiceLogDto niceLogDto) {
        return sqlSessionTemplate.selectOne("NiceLogMapper.getMcpNiceHistWithReqSeq2" ,niceLogDto);
    }

	@Override
	public int dupChkSelfSmsAuth(NiceLogDto niceLogDto) {
		return sqlSessionTemplate.selectOne("NiceLogMapper.dupChkSelfSmsAuth", niceLogDto);
	}

	@Override
	public long insertSelfSmsAuth(NiceLogDto niceLogDto) {
		return sqlSessionTemplate.insert("NiceLogMapper.insertSelfSmsAuth", niceLogDto);
	}

    @Override
    public long insertMcpNiceTryHist(NiceTryLogDto niceTryLogDto) {
        sqlSessionTemplate.insert("NiceLogMapper.insertMcpNiceTryHist", niceTryLogDto);
        return niceTryLogDto.getNiceTryHistSeq();
    }

    @Override
    public NiceTryLogDto getMcpNiceTryHist(NiceTryLogDto niceTryLogDto) {
        return sqlSessionTemplate.selectOne("NiceLogMapper.getMcpNiceTryHist" ,niceTryLogDto);
    }

    @Override
    public boolean updateMcpNiceTryHist(NiceTryLogDto niceTryLogDto) {
        return  0 < sqlSessionTemplate.update("NiceLogMapper.updateMcpNiceTryHist", niceTryLogDto);
    }

    @Override
    public NiceLogDto getMcpNiceHistWithTime(NiceLogDto niceLogDto) {
        return sqlSessionTemplate.selectOne("NiceLogMapper.getMcpNiceHistWithTime" ,niceLogDto);
    }

}
