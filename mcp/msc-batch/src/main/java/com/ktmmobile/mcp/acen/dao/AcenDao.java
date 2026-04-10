package com.ktmmobile.mcp.acen.dao;

import com.ktmmobile.mcp.acen.dto.AcenDto;
import com.ktmmobile.mcp.common.mplatform.dto.MPhoneNoVo;
import com.ktmmobile.mcp.common.mplatform.dto.McpRequestOsstDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AcenDao {

    @Autowired
    private SqlSession bootoraSqlSession;

    /** 설명 : ACEN MNP 제외 일자 조회 */
    public int getMnpLimitDayCnt(String nowDate) {
        return bootoraSqlSession.selectOne("AcenMapper.getMnpLimitDayCnt", nowDate);
    }

    /** 설명 : ACEN 연동 대상 추출 (OSST 진행 건) - 종결 상태 UPDATE */
    public int updateAcenOsstTrg(Integer limitCnt) {
        return bootoraSqlSession.update("AcenMapper.updateAcenOsstTrg", limitCnt);
    }

    /** 설명 : ACEN 연동 대상 추출 - 종결 상태 UPDATE */
    public int updateAcenTrg(Map<String, Object> paraMap) {
        return bootoraSqlSession.update("AcenMapper.updateAcenTrg", paraMap);
    }

    /** 설명 : ACEN 연동 대상 조회 */
    public List<AcenDto> getAcenPendingTrg() {
        return bootoraSqlSession.selectList("AcenMapper.getAcenPendingTrg");
    }

    /** 설명 : ACEN 현행화 상태 UPDATE */
    public int updateAcenReqInfo(AcenDto acenDto) {
        return bootoraSqlSession.update("AcenMapper.updateAcenReqInfo", acenDto);
    }

    /** 설명 : 신청서 상태 UPDATE */
    public int updateMcpReqInfo(AcenDto acenDto) {
        return bootoraSqlSession.update("AcenMapper.updateMcpReqInfo", acenDto);
    }

    /** 설명 : OSST 응답 결과 조회 */
    public McpRequestOsstDto getOsstResult(McpRequestOsstDto mcpRequestOsstDto) {
        return bootoraSqlSession.selectOne("AcenMapper.getOsstResult", mcpRequestOsstDto);
    }

    /** 설명 : 신청정보의 희망번호 조회 */
    public Map<String, String> getWantNumbers(String requestKey) {
        return bootoraSqlSession.selectOne("AcenMapper.getWantNumbers", requestKey);
    }

    /** 설명 : 번호예약 요청 OSST 테이블 INSERT */
    public int insertOsstResvTlhp(MPhoneNoVo mPhoneNoVo) {
        return bootoraSqlSession.insert("AcenMapper.insertOsstResvTlhp", mPhoneNoVo);
    }

    /** 설명 : ACEN 연동 미완료건 (사전체크 요청은 진행됐으나 개통완료가 되지 않은 케이스) 조회 */
    public List<AcenDto> getAcenEndTrg() {
        return bootoraSqlSession.selectList("AcenMapper.getAcenEndTrg");
    }

    /** 설명 : ACEN 연동 기간 종결건 조회 */
    public List<AcenDto> getAcenPeriodEndTrg() {
        return bootoraSqlSession.selectList("AcenMapper.getAcenPeriodEndTrg");
    }
    /** 설명 : ACEN 신청서 내역 조회 */
    public AcenDto getAppformReq(String requestKey) {
        return bootoraSqlSession.selectOne("AcenMapper.getAppformReq", requestKey);
    }

    /** 설명 : 자동화실패+개통완료인 신청 건 정상+개통완료 상태로 update */
    public int updateNormalPState() {
        return bootoraSqlSession.update("AcenMapper.updateNormalPState");
    }

    /** 설명 : 신청상태 조회 */
    public AcenDto getMcpRequestStateInfo(String requestKey) {
        return bootoraSqlSession.selectOne("AcenMapper.getMcpRequestStateInfo", requestKey);
    }

    /** 설명 : ACEN 실패이력 저장 */
    public int insertAcenFailHist(String requestKey) {
        return bootoraSqlSession.insert("AcenMapper.insertAcenFailHist", requestKey);
    }

    /** 설명 : 당일 사전체크 성공이력 조회 */
    public int getPreCheckSuccessCnt(Map<String, String> paramMap) {
        return bootoraSqlSession.selectOne("AcenMapper.getPreCheckSuccessCnt", paramMap);
    }

    /** 설명 : 번호 예약/취소 최근 이벤트 조회 */
    public String getLatestResvTlphIfType(String resNo) {
        return bootoraSqlSession.selectOne("AcenMapper.getLatestResvTlphIfType", resNo);
    }

    /** 설명 : ACEN 해피콜 미응답건 조회 */
    public List<AcenDto> getAcenNoResponseTrg() {
        return bootoraSqlSession.selectList("AcenMapper.getAcenNoResponseTrg");
    }

    /** 설명 : ACEN 해피콜 연동 최대 건수 조회 */
    public Integer getMaxProcCnt() {
        return bootoraSqlSession.selectOne("AcenMapper.getMaxProcCnt");
    }

}
