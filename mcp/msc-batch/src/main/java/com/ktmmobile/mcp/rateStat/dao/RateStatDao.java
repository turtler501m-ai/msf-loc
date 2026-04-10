package com.ktmmobile.mcp.rateStat.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class RateStatDao {
    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSession bootoraSqlSession;

    /** 유지고객 정보 갯수 확인 */
    public int getNmcpMonUseRateInfoCnt(String baseMon) {
        return bootoraSqlSession.selectOne("RateStatMapper.getNmcpMonUseRateInfoCnt", baseMon);
    }

    /** 유지고객 정보 삭제 */
    public int deleteNmcpMonUseRateInfo(String baseMon) {
        return bootoraSqlSession.delete("RateStatMapper.deleteNmcpMonUseRateInfo", baseMon);
    }

    /** 유지요금제 집계 갯수 확인 */
    public int getNmcpMonUseRateStatCnt(String baseMon) {
        return bootoraSqlSession.selectOne("RateStatMapper.getNmcpMonUseRateStatCnt", baseMon);
    }

    /** 유지요금제 집계 저장 */
    public int insertNmcpMonUseRateStat(String baseMon) {
        return bootoraSqlSession.insert("RateStatMapper.insertNmcpMonUseRateStat", baseMon);
    }

}
