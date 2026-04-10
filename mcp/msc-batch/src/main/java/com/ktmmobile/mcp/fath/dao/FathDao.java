package com.ktmmobile.mcp.fath.dao;


import com.ktmmobile.mcp.fath.dto.FathDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public class FathDao {

    @Autowired
    private SqlSession bootoraSqlSession;

    /** 셀프안면인증 URL 발송 대상조회 */
    public List<FathDto> selectFathSelfTrg() {
        return bootoraSqlSession.selectList("FathMapper.selectFathSelfTrg");
    }

    /** 셀프안면인증 URL 발급 */

    public void insertFathSelfUrl(FathDto fathDto){
        bootoraSqlSession.insert("FathMapper.insertFathSelfUrl", fathDto);
    }

    /** 신청서 진행상태 변경 */
    public void updateReqStateCode(String resNo){
        bootoraSqlSession.update("FathMapper.updateReqStateCode", resNo);
    }

}
