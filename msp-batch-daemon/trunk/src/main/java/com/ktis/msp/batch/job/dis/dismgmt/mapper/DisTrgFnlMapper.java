package com.ktis.msp.batch.job.dis.dismgmt.mapper;

import com.ktis.msp.batch.job.dis.dismgmt.vo.DisTrgFnlVO;
import com.ktis.msp.batch.job.dis.dismgmt.vo.DisVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Mapper("DisTrgFnlMapper")
public interface DisTrgFnlMapper {

    /**
     * MSP_DIS_TRG_MST 추출대상 전체 처리상태 UPDATE (N: 미처리, T: 진행중, Y: 완료)
     * @return int
     */
    int updateDisTrgMst(DisVO disVo);

    /**
     * MSP_DIS_APD 계약번호 UPDATE (계약번호가 NULL인 대상)
     * @return int
     */
    int updateDisApdCntrNum(DisVO disVo);

    /**
     * 가입대상 상세이력 초기 INSERT (MSP_DIS_TRG_MST 추출대상 전체 MSP_DIS_TRG_FNL_INF로 INSERT)
     * @return int
     */
    int insertDisTrgFnlInfo(DisVO disVo);

    /**
     * MSP_DIS_TRG_FNL_INF 통합 UPDATE
     * @param paramMap
     * @return int
     */
    int updateDisTrgFnlInfo(Map<String, String> paramMap);

    /**
     * MSP_DIS_TRG_FNL_INF 개별 UPDATE
     * @param disTrgFnlInfoVO
     * @return int
     */
    int updateEachDisTrgFnlInfo(DisTrgFnlVO disTrgFnlInfoVO);


    /**
     * 프로모션 조건 조회
     * @return List<DisTrgFnlVO>
     */
    List<DisTrgFnlVO> getProcDisTrgFnlInfo();

    /**
     * 평생할인 부가서비스 가입 배치 호출 (CMN_BATCH_REQUEST INSERT)
     * @param disTrgFnlInfoVO
     */
    void insertBatchRequest(DisTrgFnlVO disTrgFnlInfoVO);

    /**
     * 특정일자를 기준으로, 해당 일자 이후의 기처리 이력 조회 (기처리 이력과 평생할인 프로모션 타켓의 날짜 비교)
     * @param disTrgFnlInfoVO
     * @return
     */
    int compareDateWithApd(DisTrgFnlVO disTrgFnlInfoVO);

    /**
     * 조건에 맞는 프로모션 조회
     * @param disTrgFnlInfoVO
     * @return List<String>
     */
    List<String> findDisPrmtId(DisTrgFnlVO disTrgFnlInfoVO);

    /**
     * 부가서비스 중복 검사 (프로모션 부가서비스를 가지고 있는지 확인)
     * @param disTrgFnlInfoVO
     * @return boolean
     */
    int checkDuplicateSoc(DisTrgFnlVO disTrgFnlInfoVO);

    /**
     * 평생할인 부가서비스 가입 대상 최종(MSP_DIS_TRG_FNL) INSERT
     * @param disTrgFnlVO
     * @return int
     */
    int insertDisTrgFnl(DisTrgFnlVO disTrgFnlVO);

    /**
     * 유효한 약정기간 카운트
     * @param disTrgFnlVO
     * @return String
     */
    int checkEnggDt(DisTrgFnlVO disTrgFnlVO);


}
