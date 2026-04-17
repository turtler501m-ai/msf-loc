package com.ktmmobile.msf.domains.form.common.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;

/**
 * MSP DB 직접 조회 DAO.
 *
 * McpApiClient 내부에서 경로(path) 기반으로 자동 호출된다.
 * (mcp-api 연결 실패 또는 use-mcp=false 정책 전환 시)
 *
*/

@Repository
public class MspApiDirectRepository {

    private static final Logger logger = LoggerFactory.getLogger(MspApiDirectRepository.class);

    @Autowired
    @Qualifier("mcpSqlSession")
    private SqlSessionTemplate mspSession;

    /**
     * 경로(path) 기반으로 MSP 직접 조회를 실행한다.
     *
     * @param path         mcp-api 엔드포인트 경로
     * @param request      원래 요청 파라미터
     * @param responseType 응답 타입
     * @return MSP 조회 결과 (매핑된 직접 조회 없으면 null)
     */

    @SuppressWarnings("unchecked")
    public <T> T query(String path, Object request, Class<T> responseType) {
        logger.debug("[MspApiDirectRepository] query: path={}, request={}", path, request);
        switch (path) {
            // [ASIS] MypageController.prePayment() — @RequestMapping("/mypage/prePayment")
            case "/mypage/prePayment":
                return (T) selectPrePayment((String) request);
            // [ASIS] MypageController.mspAddInfo() — @RequestMapping("/mypage/mspAddInfo")
            case "/mypage/mspAddInfo":
                return (T) selectMspAddInfo((String) request);
            default:
                logger.warn("[MspApiDirectRepository] 직접 조회 미구현 경로: {}", path);
                return null;
        }
    }

    // ──────────────────────────────────────────────
    //  직접 조회 메서드
    // ──────────────────────────────────────────────

    // [ASIS] MypageMapper.selectPrePayment() — mcp-api MypageMapper.xml 동일 SQL 이관
    //   mcp/mcp-api/src/main/resources/mcp/mapper/MypageMapper.xml#selectPrePayment
    /** 선불 요금제 여부 조회 — /mypage/prePayment */
    private Integer selectPrePayment(String contractNum) {
        logger.debug("[MspApiDirectRepository] selectPrePayment: contractNum={}", contractNum);
        Integer count = mspSession.selectOne("McpMyPageMapper.selectPrePayment", contractNum);
        logger.debug("[MspApiDirectRepository] selectPrePayment: result={}", count);
        return count == null ? 0 : count;
    }

    // [ASIS] MypageMapper.selectMspAddInfo() — mcp-api MypageMapper.xml 동일 SQL 이관
    //   mcp/mcp-api/src/main/java/.../mypage/controller/MypageController.java#mspAddInfo
    /** 할부원금 조회 — /mypage/mspAddInfo */
    private MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo) {
        logger.debug("[MspApiDirectRepository] selectMspAddInfo: svcCntrNo={}", svcCntrNo);
        MspJuoAddInfoDto result = mspSession.selectOne("McpMyPageMapper.selectMspAddInfo", svcCntrNo);
        logger.debug("[MspApiDirectRepository] selectMspAddInfo: result={}", result);
        return result;
    }





}
