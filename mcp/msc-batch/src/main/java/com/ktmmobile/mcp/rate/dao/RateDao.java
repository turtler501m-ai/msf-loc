package com.ktmmobile.mcp.rate.dao;

import java.util.List;

import com.ktmmobile.mcp.rate.dto.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.rate.service.RateSvc;


@Repository
public class RateDao {
	
	private static final Logger logger = LoggerFactory.getLogger(RateSvc.class);

	@Autowired
	@Qualifier(value="bootoraSqlSession")
	private SqlSession bootoraSqlSession;
	
    /**
     *  요금제 예약 변경 전문 대상(NMCP_RATE_RES_CHG_TRG)의 SEQ를 모듈라 연산(% 4)하여 입력받은 batchMod와 일치하는 대상의 건수 조회
     * @param batchMod
     * @return int
     */
	public int selectRateResChgTrgCntByMod(int batchMod) {
		return bootoraSqlSession.selectOne("RateMapper.selectRateResChgTrgCntByMod", batchMod);
	}
	
    /**
     *  요금제 예약 변경 전문 대상(NMCP_RATE_RES_CHG_TRG)의 SEQ를 모듈라 연산(% 4)하여 입력받은 batchMod와 일치하는 대상 목록 조회
     * @param batchMod
     * @return List<NmcpRateResChgTrgDto>
     */
	public List<RateResChgTrgDto> selectRateResChgTrgListByMod(int batchMod) {
		return bootoraSqlSession.selectList("RateMapper.selectRateResChgTrgListByMod", batchMod);
	}

    /**
     *  요금제 예약 변경 미처리 대상(BATCH_RSLT_CD = -1)과
     *  요금제 변경 처리 미완료 대상(BATCH_RSLT_CD = 99999)의
     *  건수 조회
     * @return int
     */
	public int selectRateResChgCntNotProcess() {
		return bootoraSqlSession.selectOne("RateMapper.selectRateResChgCntNotProcess");
	}

    /**
     *  요금제 예약 변경 미처리 대상(BATCH_RSLT_CD = -1)과
     *  요금제 변경 처리 미완료 대상(BATCH_RSLT_CD = 99999)의
     *  목록 조회
     * @return List<RateResChgBasDto>
     */
	public List<RateResChgBasDto> selectRateResChgListNotProcess() {
		return bootoraSqlSession.selectList("RateMapper.selectRateResChgListNotProcess");
	}
	
	/**
     *  요금제 예약 변경 후처리 중 선해지 부가서비스 해지 실패 대상
	 *  건수 조회
	 * @return int
	 */
	public int selectRateResChgCntFailCancel() {
		return bootoraSqlSession.selectOne("RateMapper.selectRateResChgCntFailCancel");
	}
	
	/**
     *  요금제 예약 변경 후처리 중 선해지 부가서비스 해지 실패 대상
	 *  목록 조회
	 * @return List<RateResChgBasDto>
	 */
	public List<RateResChgBasDto> selectRateResChgListFailCancel() {
		return bootoraSqlSession.selectList("RateMapper.selectRateResChgListFailCancel");
	}
	
	/**
	 *  요금제 예약 변경 후처리 중 부가서비스 가입 실패 이력
	 *  건수 조회
	 * @return int
	 */
	public int selectRateResChgCntFailSubscribe() {
		return bootoraSqlSession.selectOne("RateMapper.selectRateResChgCntFailSubscribe");
	}
	
	/**
	 *  요금제 예약 변경 후처리 중 부가서비스 가입 실패 이력
	 *  목록 조회
	 * @return List<McpServiceAlterTraceDto>
	 */
	public List<McpServiceAlterTraceDto> selectRateResChgListFailSubscribe() {
		return bootoraSqlSession.selectList("RateMapper.selectRateResChgListFailSubscribe");
	}
	
    /**
     *  요금제 예약 변경 대상(NMCP_RATE_RES_CHG_BAS) 조회
     * @param rateResChgSeq
     * @return RateResChgBasDto
     */
	public RateResChgBasDto selectRateResChgBas(String rateResChgSeq) {
		return bootoraSqlSession.selectOne("RateMapper.selectRateResChgBas", rateResChgSeq);
	}
	
    /**
     *  부가서비스 정보 조회(코드, 이름, 금액)
     * @param socCode
     * @return McpUserCntrMngDto
     */
	public McpUserCntrMngDto selectAddSvcInfo(String socCode) {
		return bootoraSqlSession.selectOne("RateMapper.selectAddSvcInfo", socCode);
	}
	
    /**
     *  요금제 예약 변경 전문 대상(NMCP_RATE_RES_CHG_TRG)의 처리여부를 처리('Y')로 업데이트
     * @param regChgTrgSeq
     * @return int
     */
	public int updateRateResChgTrgComplete(String regChgTrgSeq) {
		return bootoraSqlSession.update("RateMapper.updateRateResChgTrgComplete", regChgTrgSeq);
	}

    /**
     *  요금제 예약 변경 대상(NMCP_RATE_RES_CHG_BAS)의 BATCH_RSLT_CD를 검증 결과에 맞게 업데이트
     *  
     *  00000 : 검증 성공
     *  99991 : 회선 조회 불가
     *  99992 : 계약번호 조회 불가
     *  99993 : 요금제 조회 불가
     *  99999 : 요금제 변경 처리 미완료
     *  
     * @param rateResChgBasDto
     * @return int
     */
	public int updateBatchRsltCd(String rateResChgSeq, String batchRsltCd) {
		RateResChgBasDto rateResChgBasDto = new RateResChgBasDto();
		rateResChgBasDto.setRateResChgSeq(rateResChgSeq);
		rateResChgBasDto.setBatchRsltCd(batchRsltCd);
		return bootoraSqlSession.update("RateMapper.updateBatchRsltCd", rateResChgBasDto);
	}
	
    /**
     *  부가서비스 건별 가입/실패 이력 INSERT
     * @param mcpServiceAlterTraceDto
     * @return int
     */
	public int insertServiceAlterTrace(McpServiceAlterTraceDto mcpServiceAlterTraceDto) {
		return bootoraSqlSession.insert("RateMapper.insertServiceAlterTrace", mcpServiceAlterTraceDto);
	}
}