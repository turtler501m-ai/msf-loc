package com.ktmmobile.msf.formSvcChg.mapper;

import com.ktmmobile.msf.formSvcChg.dto.McpRegServiceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 부가서비스 신청 관련 Mapper.
 * ASIS: MypageMapper.xml(selectRegService), RegSvcMapper.xml(getRoamCdList) 에 대응.
 */
@Mapper
public interface SvcChgRegMapper {

    /**
     * 5007-02 addSvcListAjax — 가입 가능 부가서비스 카탈로그 조회.
     * 현재 계약의 요금제 기준으로 가입 가능한 부가서비스 목록 조회.
     * ASIS: MypageMapper.xml selectRegService (MSP_RATE_MST@DL_MSP, MSP_PROD_REL_MST@DL_MSP, MSP_JUO_FEATURE_INFO@DL_MSP).
     *
     * @param ncn 서비스계약번호 (9자리)
     */
    List<McpRegServiceDto> selectRegService(@Param("ncn") String ncn);

    /**
     * 로밍 부가서비스 요금제 코드 목록 조회. getMpSocListByDiv 에서 일반/로밍 분리에 사용.
     * ASIS: RegSvcMapper.xml getRoamCdList (NMCP_RATE_ADSVC_GDNC_PROD_REL → MSF_RATE_ADSVC_GDNC_PROD_REL).
     */
    List<String> getRoamCdList();

    /**
     * MSP_RATE_MST@DL_MSP에서 SOC코드 기준 해지 가능 여부 정보 조회.
     * ASIS: fCommonSvc.getMspRateMst(soc) (RegSvcServiceImpl.selectAddSvcList:210).
     * 반환: onlineCanYn(해지가능여부), canCmnt(해지안내문구), onlineCanDay(해지가능일수).
     *
     * @param rateCd SOC 코드
     */
    Map<String, Object> getMspRateMstByRateCd(@Param("rateCd") String rateCd);

    /**
     * 5007-04 getContRateAdditionAjax — 부가서비스 안내 상세 조회.
     * ASIS: RegSvcServiceImpl.selectAddSvcDtl(rateAdsvcCd) — XML 파일 캐시 조회.
     * TOBE: DB(MSF_RATE_ADSVC_GDNC_PROD_REL + MSF_RATE_ADSVC_GDNC_BAS) 조회.
     *
     * @param rateAdsvcCd 부가서비스 코드
     */
    Map<String, Object> selectAddSvcDtl(@Param("rateAdsvcCd") String rateAdsvcCd);
}
