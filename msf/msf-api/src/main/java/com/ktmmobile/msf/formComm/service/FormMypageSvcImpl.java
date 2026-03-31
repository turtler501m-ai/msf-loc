package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.formComm.dto.McpFarPriceDto;
import com.ktmmobile.msf.formComm.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.formComm.dto.McpSubInfoDto;
import com.ktmmobile.msf.formComm.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.formComm.mapper.FormMypageMapper;
import com.ktmmobile.msf.formSvcChg.dto.McpRegServiceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * FormMypageSvc 구현체.
 * ASIS MypageServiceImpl 중 TOBE 필요 함수 이식.
 * 모든 DB 처리는 FormMypageMapper 를 통해 수행.
 */
@Service
public class FormMypageSvcImpl implements FormMypageSvc {

    private static final Logger logger = LoggerFactory.getLogger(FormMypageSvcImpl.class);

    @Autowired
    private FormMypageMapper formMypageMapper;

    /* =====================================================================
     * 요금제 변경 — 조회
     * ===================================================================== */

    @Override
    public McpFarPriceDto selectFarPricePlan(String ncn) {
        if (ncn == null || ncn.trim().isEmpty()) {
            logger.warn("[FormMypage] selectFarPricePlan: ncn 없음");
            return null;
        }
        logger.debug("[FormMypage] selectFarPricePlan: ncn={}", ncn);
        return formMypageMapper.selectFarPricePlan(ncn);
    }

    @Override
    public List<McpFarPriceDto> selectFarPricePlanList(String rateCd) {
        if (rateCd == null || rateCd.trim().isEmpty()) {
            logger.warn("[FormMypage] selectFarPricePlanList: rateCd 없음");
            return Collections.emptyList();
        }
        logger.debug("[FormMypage] selectFarPricePlanList: rateCd={}", rateCd);
        List<McpFarPriceDto> list = formMypageMapper.selectFarPricePlanList(rateCd);
        return list != null ? list : Collections.emptyList();
    }

    @Override
    public int countFarPricePlanList(String rateCd) {
        if (rateCd == null || rateCd.trim().isEmpty()) {
            return 0;
        }
        return formMypageMapper.countFarPricePlanList(rateCd);
    }

    @Override
    public String selectFarPriceAddInfo(String cntrNo, String soc) {
        if (cntrNo == null || cntrNo.trim().isEmpty()) {
            logger.warn("[FormMypage] selectFarPriceAddInfo: cntrNo 없음");
            return null;
        }
        logger.debug("[FormMypage] selectFarPriceAddInfo: cntrNo={}, soc={}", cntrNo, soc);
        return formMypageMapper.selectFarPriceAddInfo(cntrNo, soc);
    }

    /* =====================================================================
     * 약정 / 부가서비스
     * ===================================================================== */

    @Override
    public MspJuoAddInfoDto getEnggInfo(String contractNum) {
        if (contractNum == null || contractNum.trim().isEmpty()) {
            logger.warn("[FormMypage] getEnggInfo: contractNum 없음");
            return null;
        }
        logger.debug("[FormMypage] getEnggInfo: contractNum={}", contractNum);
        return formMypageMapper.getEnggInfo(contractNum);
    }

    @Override
    public List<McpRegServiceDto> getCloseSubList(String contractNum) {
        if (contractNum == null || contractNum.trim().isEmpty()) {
            logger.warn("[FormMypage] getCloseSubList: contractNum 없음");
            return Collections.emptyList();
        }
        logger.debug("[FormMypage] getCloseSubList: contractNum={}", contractNum);
        List<McpRegServiceDto> list = formMypageMapper.getCloseSubList(contractNum);
        return list != null ? list : Collections.emptyList();
    }

    @Override
    public List<McpRegServiceDto> getromotionDcList(String toSocCode) {
        if (toSocCode == null || toSocCode.trim().isEmpty()) {
            logger.warn("[FormMypage] getromotionDcList: toSocCode 없음");
            return Collections.emptyList();
        }
        logger.debug("[FormMypage] getromotionDcList: toSocCode={}", toSocCode);
        List<McpRegServiceDto> list = formMypageMapper.getromotionDcList(toSocCode);
        return list != null ? list : Collections.emptyList();
    }

    /* =====================================================================
     * 서비스 변경 이력
     * ===================================================================== */

    @Override
    public void insertServiceAlterTrace(McpServiceAlterTraceDto dto) {
        if (dto == null) {
            logger.warn("[FormMypage] insertServiceAlterTrace: dto null");
            return;
        }
        logger.debug("[FormMypage] insertServiceAlterTrace: ncn={}, eventCode={}, tSocCode={}",
            dto.getNcn(), dto.getEventCode(), dto.getTSocCode());
        formMypageMapper.insertServiceAlterTrace(dto);
    }

    @Override
    public int checkAllreadPlanchgCount(McpServiceAlterTraceDto dto) {
        if (dto == null || dto.getNcn() == null) {
            return 0;
        }
        logger.debug("[FormMypage] checkAllreadPlanchgCount: ncn={}, tSocCode={}", dto.getNcn(), dto.getTSocCode());
        return formMypageMapper.checkAllreadPlanchgCount(dto);
    }

    /* =====================================================================
     * 평생할인
     * ===================================================================== */

    @Override
    public void insertDisApd(McpServiceAlterTraceDto dto) {
        if (dto == null || dto.getContractNum() == null) {
            logger.warn("[FormMypage] insertDisApd: dto 또는 contractNum null");
            return;
        }
        logger.debug("[FormMypage] insertDisApd: contractNum={}, tSocCode={}",
            dto.getContractNum(), dto.getTSocCode());
        formMypageMapper.insertDisApd(dto);
    }

    @Override
    public String getChrgPrmtIdSocChg(String rateCd) {
        if (rateCd == null || rateCd.trim().isEmpty()) {
            return null;
        }
        logger.debug("[FormMypage] getChrgPrmtIdSocChg: rateCd={}", rateCd);
        return formMypageMapper.getChrgPrmtIdSocChg(rateCd);
    }

    /* =====================================================================
     * 요금제 변경 실패 처리
     * ===================================================================== */

    @Override
    public void insertSocfailProcMst(McpServiceAlterTraceDto dto) {
        if (dto == null || dto.getContractNum() == null) {
            logger.warn("[FormMypage] insertSocfailProcMst: dto 또는 contractNum null");
            return;
        }
        logger.debug("[FormMypage] insertSocfailProcMst: contractNum={}, prcsMdlInd={}",
            dto.getContractNum(), dto.getPrcsMdlInd());
        formMypageMapper.insertSocfailProcMst(dto);
    }

    /* =====================================================================
     * 비회원 계약 조회 / QoS
     * ===================================================================== */

    @Override
    public McpSubInfoDto selectCntrListNoLogin(McpSubInfoDto param) {
        if (param == null) {
            logger.warn("[FormMypage] selectCntrListNoLogin: param null");
            return null;
        }
        logger.debug("[FormMypage] selectCntrListNoLogin: svcCntrNo={}, cntrMobileNo={}",
            param.getSvcCntrNo(), param.getCntrMobileNo());
        return formMypageMapper.selectCntrListNoLogin(param);
    }

    @Override
    public Map<String, Object> selectRateMst(String rateCd) {
        if (rateCd == null || rateCd.trim().isEmpty()) {
            return Collections.emptyMap();
        }
        logger.debug("[FormMypage] selectRateMst: rateCd={}", rateCd);
        Map<String, Object> result = formMypageMapper.selectRateMst(rateCd);
        return result != null ? result : Collections.emptyMap();
    }
}
