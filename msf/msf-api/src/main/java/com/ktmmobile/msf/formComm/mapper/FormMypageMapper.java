package com.ktmmobile.msf.formComm.mapper;

import com.ktmmobile.msf.formComm.dto.McpFarPriceDto;
import com.ktmmobile.msf.formComm.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.formComm.dto.McpSubInfoDto;
import com.ktmmobile.msf.formComm.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.formSvcChg.dto.McpRegServiceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ASIS MypageService DB 처리 Mapper.
 * ASIS: mcp-api MypageMapper.xml 중 TOBE 필요 항목 이식.
 * MCP_* / NMCP_* → MSF_* 로 접두사 교체, MSP_*@DL_MSP 그대로 사용.
 */
@Mapper
public interface FormMypageMapper {

    /* =====================================================================
     * 요금제 변경 (5006 series) — selectFarPricePlan / selectFarPricePlanList
     * ===================================================================== */

    /**
     * 현재 계약의 요금제 정보 조회.
     * ASIS: MypageMapper.selectFarPricePlan (MSP_RATE_MST + MSP_JUO_FEATURE_INFO).
     *
     * @param ncn 서비스계약번호 (9자리)
     */
    McpFarPriceDto selectFarPricePlan(@Param("ncn") String ncn);

    /**
     * 현재 요금제 기준 변경 가능 요금제 목록 조회.
     * ASIS: MypageMapper.selectFarPricePlanList (VW_GROUP_BY_RATE_INFO@DL_MSP).
     *
     * @param rateCd 현재 요금제 코드
     */
    List<McpFarPriceDto> selectFarPricePlanList(@Param("rateCd") String rateCd);

    /**
     * 변경 가능 요금제 수 조회.
     * ASIS: MypageMapper.countFarPricePlanList (VW_GROUP_BY_RATE_INFO@DL_MSP).
     */
    int countFarPricePlanList(@Param("rateCd") String rateCd);

    /**
     * 요금제 변경 적용일(YYYYMM) 조회.
     * ASIS: MypageMapper.selectFarPriceAddInfo (MSP_JUO_FEATURE_INFO@DL_MSP).
     *
     * @param cntrNo 계약번호
     * @param soc    현재 요금제 SOC 코드
     */
    String selectFarPriceAddInfo(@Param("cntrNo") String cntrNo, @Param("soc") String soc);

    /* =====================================================================
     * 약정 / 부가서비스 정보
     * ===================================================================== */

    /**
     * 약정 정보 조회 (약정여부 enggYn, 약정종료일 appEndDate).
     * ASIS: MypageMapper.getEnggInfo (VW_CNTR_ENGG_SELF@DL_MSP).
     */
    MspJuoAddInfoDto getEnggInfo(@Param("contractNum") String contractNum);

    /**
     * 요금제 변경 시 해지해야 할 부가서비스 목록.
     * ASIS: MypageMapper.getCloseSubList (MSP_JUO_FEATURE_INFO + CMN_GRP_CD_MST).
     */
    List<McpRegServiceDto> getCloseSubList(@Param("contractNum") String contractNum);

    /**
     * 변경할 요금제에 가입해야 할 프로모션 부가서비스 목록.
     * ASIS: MypageMapper.getromotionDcList (VW_CHRG_PRMT_DTL@DL_MSP + MCP_ADDITION → MSF_ADDITION).
     */
    List<McpRegServiceDto> getromotionDcList(@Param("rateCd") String rateCd);

    /* =====================================================================
     * 서비스 변경 이력 (insertServiceAlterTrace / checkAllreadPlanchgCount)
     * ===================================================================== */

    /**
     * 서비스 변경 이력 저장.
     * ASIS: MypageMapper.insertServiceAlterTrace (MCP_SERVICE_ALTER_TRACE → MSF_SERVICE_ALTER_TRACE).
     */
    void insertServiceAlterTrace(McpServiceAlterTraceDto dto);

    /**
     * 최근 60분 내 요금제 변경 성공 이력 수 조회.
     * ASIS: MypageMapper.checkAllreadPlanchgCount (MCP_SERVICE_ALTER_TRACE → MSF_SERVICE_ALTER_TRACE).
     */
    int checkAllreadPlanchgCount(McpServiceAlterTraceDto dto);

    /* =====================================================================
     * 평생할인 (insertDisApd / getChrgPrmtIdSocChg)
     * ===================================================================== */

    /**
     * 요금제 변경 후 평생할인 부가서비스 MSP_DIS_APD@DL_MSP INSERT.
     * ASIS: MypageMapper.insertDisApd.
     * socCode 필드와 contractNum 필드 사용.
     */
    void insertDisApd(McpServiceAlterTraceDto dto);

    /**
     * 요금제 변경 온라인 프로모션 ID 조회 (평생할인 프로모션).
     * ASIS: MypageMapper.getChrgPrmtIdSocChg (VW_CHRG_PRMT_DTL@DL_MSP).
     */
    String getChrgPrmtIdSocChg(@Param("rateCd") String rateCd);

    /* =====================================================================
     * 요금제 변경 실패 처리
     * ===================================================================== */

    /**
     * 요금제 변경 실패 이력 MERGE.
     * ASIS: MypageMapper.insertSocfailProcMst (MSP_SOCFAIL_PROC_MST@DL_MSP).
     */
    void insertSocfailProcMst(McpServiceAlterTraceDto dto);

    /* =====================================================================
     * 비회원 계약 조회 / QoS 정보
     * ===================================================================== */

    /**
     * 비회원 계약 현행화 정보 조회 (이름/전화번호 조건 동적 처리).
     * ASIS: MypageMapper.selectCntrListNoLogin (MSP_JUO_SUB_INFO@DL_MSP).
     */
    McpSubInfoDto selectCntrListNoLogin(McpSubInfoDto param);

    /**
     * 요금제 QoS 데이터/음성 정보 조회.
     * ASIS: MypageMapper.selectRateMst (NMCP_RATE_MST → MSF_RATE_MST).
     */
    Map<String, Object> selectRateMst(@Param("rateCd") String rateCd);
}
