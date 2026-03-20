package com.ktmmobile.msf.formSvcChg.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 아무나 SOLO 결합 Mapper.
 * ASIS MypageMapper.selectMspCombRateMapp() 대응.
 */
@Mapper
public interface SvcChgCombineMapper {

    /**
     * 마스터 결합 회선 정보 목록 (공통코드 GROUP_CD='MasterCombineLineInfo').
     * 아무나 SOLO 신청 화면에서 마스터 회선 선택 콤보에 사용.
     */
    List<Map<String, Object>> selectMasterCombineLine();

    /**
     * 요금제-아무나SOLO 할인부가 매핑 조회 (MSP_MSC_COMB_RATE_MAPP@DL_MSP).
     * rateCd로 결합 가능 여부 및 할인 부가 서비스 코드(COMB_SOC) 조회.
     */
    List<Map<String, Object>> selectCombRateMapp(@Param("rateCd") String rateCd);
}
