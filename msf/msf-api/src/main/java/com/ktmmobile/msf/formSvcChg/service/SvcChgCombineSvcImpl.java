package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpMoscCombDtlResVO;
import com.ktmmobile.msf.common.mplatform.vo.MpMoscSubMstCombChgRes;
import com.ktmmobile.msf.formSvcChg.dto.CombineCheckReqDto;
import com.ktmmobile.msf.formSvcChg.mapper.SvcChgCombineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 아무나 SOLO 결합 서비스 구현.
 * ASIS CombineController.checkCombineSelfAjax(X87+DB), regCombine(Y44) 와 동일.
 */
@Service
public class SvcChgCombineSvcImpl implements SvcChgCombineSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgCombineSvcImpl.class);

    @Autowired
    private MplatFormSvc mplatFormSvc;

    @Autowired
    private SvcChgCombineMapper combineMapper;

    /**
     * 아무나 SOLO 결합 가입 가능 여부 체크.
     * X87로 기존 결합 유무(IS_COMBIN) 조회 → DB에서 해당 요금제의 할인SOC 매핑 확인.
     */
    @Override
    public Map<String, Object> checkCombineSelf(CombineCheckReqDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        try {
            // X87 기존 결합 서비스 조회
            MpMoscCombDtlResVO combVo = mplatFormSvc.moscCombSvcInfoList(
                req.getCustId(), req.getNcn(), req.getCtn());

            boolean alreadyCombined = combVo.isSuccess()
                && combVo.getCombTypeNm() != null
                && !combVo.getCombTypeNm().isEmpty();

            result.put("success", true);
            result.put("alreadyCombined", alreadyCombined);
            result.put("combTypeNm", combVo.getCombTypeNm());
            result.put("combProdNm", combVo.getCombProdNm());

            // DB에서 요금제-할인SOC 매핑 조회
            if (!isBlank(req.getRateCd())) {
                List<Map<String, Object>> rateMappList =
                    combineMapper.selectCombRateMapp(req.getRateCd());
                result.put("combSocList", rateMappList);
                result.put("combinePossible", !rateMappList.isEmpty() && !alreadyCombined);
            } else {
                result.put("combinePossible", !alreadyCombined);
            }
        } catch (Exception e) {
            logger.error("아무나SOLO 결합 가입가능여부 체크 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "체크 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * 요금제별 아무나 SOLO 결합 할인SOC 목록 (DB).
     * ASIS: selectMspCombRateMapp → rateCd별 COMB_SOC 조회.
     */
    @Override
    public Map<String, Object> getCombineSoloType(String rateCd) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String, Object>> list = isBlank(rateCd)
                ? combineMapper.selectMasterCombineLine()
                : combineMapper.selectCombRateMapp(rateCd);
            result.put("success", true);
            result.put("list", list);
        } catch (Exception e) {
            logger.error("아무나SOLO 결합SOC 조회 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * Y44 아무나 SOLO 결합 신청 처리.
     * ASIS: CombineController.regCombine → moscSubMstCombChg(Y44).
     */
    @Override
    public Map<String, Object> regCombineSelf(CombineCheckReqDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        if (isBlank(req.getMstSvcContId())) {
            result.put("success", false);
            result.put("message", "마스터 서비스 계약 ID(mstSvcContId)가 없습니다.");
            return result;
        }
        try {
            MpMoscSubMstCombChgRes vo = mplatFormSvc.moscSubMstCombChg(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getMstSvcContId());

            result.put("success", vo.isSuccess());
            result.put("resultCode", vo.getResultCode());
            result.put("globalNo", vo.getGlobalNo());
            result.put("message", vo.isSuccess()
                ? "아무나 SOLO 결합 신청이 완료되었습니다."
                : vo.getSvcMsg());
        } catch (Exception e) {
            logger.error("Y44 아무나SOLO 결합 신청 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "결합 신청 중 오류가 발생했습니다.");
        }
        return result;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
