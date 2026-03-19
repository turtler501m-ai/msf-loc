package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.common.mplatform.vo.SvcChgValdChkVO;
import com.ktmmobile.msf.formSvcChg.dto.InsrApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.InsrProductDto;
import com.ktmmobile.msf.formSvcChg.mapper.SvcChgInsrMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 단말보험 서비스 구현.
 * ASIS AppformController Y24(가입가능여부) / X21(신청) 처리 흐름과 동일.
 * DB 저장: MSF_CUST_REQUEST_INSR (가입신청보험정보)
 * 상품 목록: MSP_INTM_INSR_MST@DL_MSP (M플랫폼 DB링크)
 */
@Service
public class SvcChgInsrSvcImpl implements SvcChgInsrSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgInsrSvcImpl.class);

    @Value("${SERVER_NAME:LOCAL}")
    private String serverName;

    @Autowired
    private MplatFormSvc mplatFormSvc;

    @Autowired
    private SvcChgInsrMapper insrMapper;

    /**
     * 단말보험 상품 목록 조회.
     * ASIS: AppformSvcImpl.selectInsrProdList() 와 동일.
     * LOCAL 모드: Mock 데이터 반환 (DB링크 없음).
     */
    @Override
    public Map<String, Object> getInsuranceProducts(String reqBuyType) {
        Map<String, Object> result = new HashMap<>();

        if ("LOCAL".equals(serverName)) {
            // LOCAL Mock 데이터
            List<InsrProductDto> mockList = new ArrayList<>();
            InsrProductDto mock1 = new InsrProductDto();
            mock1.setInsrProdCd("IP001");
            mock1.setInsrProdNm("단말보험 기본형");
            mock1.setCmpnLmtAmt(500000L);
            mock1.setInsrEnggCnt(0);
            mock1.setInsrTypeCd("B");
            mockList.add(mock1);
            InsrProductDto mock2 = new InsrProductDto();
            mock2.setInsrProdCd("IP002");
            mock2.setInsrProdNm("단말보험 프리미엄");
            mock2.setCmpnLmtAmt(1000000L);
            mock2.setInsrEnggCnt(0);
            mock2.setInsrTypeCd("P");
            mockList.add(mock2);
            result.put("success", true);
            result.put("items", mockList);
            return result;
        }

        try {
            Map<String, String> params = new HashMap<>();
            params.put("reqBuyType", reqBuyType != null ? reqBuyType : "UU");
            List<InsrProductDto> list = insrMapper.selectInsrProdList(params);
            result.put("success", true);
            result.put("items", list);
        } catch (Exception e) {
            logger.error("단말보험 상품 목록 조회 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "단말보험 상품 목록 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * 단말보험 가입 가능 여부 확인 (Y24 사전체크).
     * ASIS: AppformSvcImpl 가입가능여부 검증 로직.
     */
    @Override
    public Map<String, Object> checkInsuranceEligibility(InsrApplyReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn()) || isBlank(req.getSoc())) {
            result.put("success", false);
            result.put("eligible", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, soc)");
            return result;
        }

        try {
            SvcChgValdChkVO vo = mplatFormSvc.chkMoscCombSvcInfo(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc());

            if (vo.isSuccess()) {
                result.put("success", true);
                result.put("eligible", true);
                result.put("resultCode", vo.getResultCode());
                result.put("message", "");
            } else {
                result.put("success", false);
                result.put("eligible", false);
                result.put("resultCode", vo.getResultCode());
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("Y24 단말보험 가입가능여부 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("eligible", false);
            result.put("message", "가입가능여부 확인 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * 단말보험 가입 신청.
     * ASIS: AppformController → Y24 사전체크 → X21 부가서비스 신청 → DB 저장.
     * DB 저장: MSF_CUST_REQUEST_INSR.
     */
    @Override
    public Map<String, Object> applyInsurance(InsrApplyReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn())
                || isBlank(req.getSoc()) || isBlank(req.getInsrProdCd())) {
            result.put("success", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, soc, insrProdCd)");
            return result;
        }

        try {
            // 1. Y24 가입가능여부 사전체크
            SvcChgValdChkVO chkVo = mplatFormSvc.chkMoscCombSvcInfo(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc());

            if (!chkVo.isSuccess()) {
                result.put("success", false);
                result.put("resultCode", chkVo.getResultCode());
                result.put("message", "단말보험 가입이 불가합니다: " + chkVo.getSvcMsg());
                return result;
            }

            // 2. X21 부가서비스 신청 (보험 SOC)
            MpRegSvcChgVO applyVo = mplatFormSvc.regSvcChg(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), null);

            if (!applyVo.isSuccess()) {
                result.put("success", false);
                result.put("resultCode", applyVo.getResultCode());
                result.put("message", applyVo.getSvcMsg());
                return result;
            }

            // 3. MSF_CUST_REQUEST_INSR 저장
            try {
                insrMapper.insertCustRequestInsr(req);
            } catch (Exception dbEx) {
                logger.warn("단말보험 가입 정보 저장 실패 (신청은 성공): {}", dbEx.getMessage());
            }

            result.put("success", true);
            result.put("resultCode", applyVo.getResultCode());
            result.put("globalNo", applyVo.getGlobalNo());
            result.put("message", "");

        } catch (Exception e) {
            logger.error("단말보험 가입 신청 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "단말보험 가입 처리 중 오류가 발생했습니다.");
        }
        return result;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
