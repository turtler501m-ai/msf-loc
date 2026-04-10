package com.ktmmobile.mcp.mcash.dao;

import com.ktmmobile.mcp.mcash.dto.McashDto;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class McashDao {

    private static final Logger logger = LoggerFactory.getLogger(McashDao.class);

    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSession bootoraSqlSession;

    // MCASH 해지연동 공통코드 기준일자
    public int getBaseDate(){
        return bootoraSqlSession.selectOne("McashMapper.getBaseDate");
    }
    // MCASH 해지 전체 대상 리스트 미대상처리 (T)
    public int updateAllMcashCanTrg(int baseDate) {
        return bootoraSqlSession.update("McashMapper.updateAllMcashCanTrg", baseDate);
    }
    // MCASH 미연동 대상 리스트 미대상처리 (X)
    public int updateMcashNotCanTrg() {
        return bootoraSqlSession.update("McashMapper.updateMcashNotCanTrg");
    }
    // MCASH USER 테이블 정보로 해지테이블 업데이트
    public int updateMcashCanTrgInfo() {
        return bootoraSqlSession.update("McashMapper.updateMcashCanTrgInfo");
    }
    // MCASH 해지 연동 대상 조회
    public List<McashDto> getMcashCanTrg(){
        return bootoraSqlSession.selectList("McashMapper.getMcashCanTrg");
    }
    // MCASH 해지 연동 UPDATE
    public int updateMcashCanTrg(Map<String,Object> paramMap) {
        return bootoraSqlSession.update("McashMapper.updateMcashCanTrg",paramMap);
    }
    // MCASH 회원 정보 UPDATE
    public int updateMcashUserInfo(Map<String,Object> paramMap) {
        return bootoraSqlSession.update("McashMapper.updateMcashUserInfo",paramMap);
    }

    // MCASH 회원정보이력 INSERT
    public int insertMcashUserHist(Map<String,Object> paramMap) {
        return bootoraSqlSession.insert("McashMapper.insertMcashUserHist", paramMap);
    }

    // MCASH 해지연동 공통코드 기준일자
    public int getRetryDate(){
        return bootoraSqlSession.selectOne("McashMapper.getRetryDate");
    }

    // MCASH 해지 전체 대상 리스트 미대상 재처리 (T)
    public int updateAllMcashCanRetryTrg(int baseDate) {
        return bootoraSqlSession.update("McashMapper.updateAllMcashCanRetryTrg", baseDate);
    }

}
