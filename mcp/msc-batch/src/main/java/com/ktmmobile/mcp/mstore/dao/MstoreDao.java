package com.ktmmobile.mcp.mstore.dao;

import com.ktmmobile.mcp.mstore.dto.MstoreContentDto;
import com.ktmmobile.mcp.mstore.dto.MstoreContentItemDto;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class MstoreDao {

    private static final Logger logger = LoggerFactory.getLogger(MstoreDao.class);


    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSession bootoraSqlSession;


    /** 설명 : Mstore 연동 결과 update (MSTORE 활성회원이 아닌 경우) */
    public int updateNotMstoreCanTrg(Map<String,Object> userStatusMap) {
        return bootoraSqlSession.update("MstoreMapper.updateNotMstoreCanTrg",userStatusMap);
    }

    /** 설명 : MSTORE 인사정보 탈퇴 연동 응답 로그 insert */
    public int insertMstoreProcHist(Map<String, Object> objectMap) {
        return bootoraSqlSession.insert("MstoreMapper.insertMstoreProcHist",objectMap);
    }

    /** 설명 : Mstore 연동 결과 update (MSTORE 활성회원인 경우) */
    public int updateMstoreCanTrg(Map<String, String> seqMap) {
        return bootoraSqlSession.update("MstoreMapper.updateMstoreCanTrg",seqMap);
    }

    /** 설명 : M스토어 약관 만료처리 (준회원 탈퇴대상) */
    public int updateAgreeAssociateMember(Map<String, String> paraMap) {
        return bootoraSqlSession.update("MstoreMapper.updateAgreeAssociateMember",paraMap);
    }

    /** 설명 : MSTORE 탈퇴 연동 대상 회원 insert */
    public int insertMstoreCanTrg(Map<String, String> paraMap) {
        return bootoraSqlSession.insert("MstoreMapper.insertMstoreCanTrg",paraMap);
    }

    /** 설명 : MSTORE 탈퇴 연동 대상 SSO INFO 제거  */
    public int deleteMstoreSsoInfo(Map<String, String> paraMap) {
        return bootoraSqlSession.insert("MstoreMapper.deleteMstoreSsoInfo",paraMap);
    }

    /** 설명 : Mstore 연동 결과 재처리 update (MSTORE 활성회원인 경우)*/
    public int updateMstoreRtyCanTrg(Map<String, String> seqMap) {
        return bootoraSqlSession.update("MstoreMapper.updateMstoreRtyCanTrg",seqMap);
    }

    public Long getContentSeqByHash(MstoreContentDto contentDto) {
        return bootoraSqlSession.selectOne("MstoreMapper.getContentSeqByHash", contentDto);
    }

    public int insertMstoreContent(MstoreContentDto contentDto) {
        return bootoraSqlSession.insert("MstoreMapper.insertMstoreContent", contentDto);
    }

    public int insertMstoreContentItem(MstoreContentItemDto itemDto) {
        return bootoraSqlSession.insert("MstoreMapper.insertMstoreContentItem", itemDto);
    }
}
