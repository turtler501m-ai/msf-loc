package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.msf.common.mplatform.vo.MpMoscRegSvcCanChgInVO;
import com.ktmmobile.msf.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCancelReqDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCurrentResVO;
import com.ktmmobile.msf.formSvcChg.dto.AdditionItemDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionRegReqDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 부가서비스 신청/해지 서비스 구현.
 * ASIS RegSvcServiceImpl.selectAddSvcInfoDto / regSvcChg / moscRegSvcCanChg 와 동일 처리 흐름.
 */
@Service
public class SvcChgRegSvcImpl implements SvcChgRegSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgRegSvcImpl.class);

    /** 무선데이터차단 SOC 코드 */
    private static final String SOC_WIRELESS_BLOCK = "DATABLOCK";

    @Autowired
    private MplatFormSvc mplatFormSvc;

    /**
     * X20 현재 가입 부가서비스 조회.
     * ncn/ctn/custId 있으면 M플랫폼 호출, 없으면 빈 목록 반환.
     */
    @Override
    public AdditionCurrentResVO selectAdditionCurrent(SvcChgInfoDto req) {
        AdditionCurrentResVO res = new AdditionCurrentResVO();
        List<AdditionItemDto> items = new ArrayList<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn())) {
            res.setItems(items);
            res.setAvailableItems(new ArrayList<>());
            return res;
        }

        try {
            MpAddSvcInfoDto x20 = mplatFormSvc.getAddSvcInfoDto(req.getNcn(), req.getCtn(), req.getCustId());
            if (x20 != null && x20.isSuccess() && x20.getList() != null) {
                List<MpSocVO> socList = x20.getList();

                for (MpSocVO soc : socList) {
                    AdditionItemDto item = toItemDto(soc);
                    items.add(item);

                    // 무선데이터차단 여부 판단
                    if (SOC_WIRELESS_BLOCK.equals(soc.getSoc())
                        || isWirelessBlockDesc(soc.getSocDescription())) {
                        res.setWirelessBlockInUse(true);
                    }

                    // 정보료 상한 금액 추출 (SOC 설명에 "정보료" + "상한" 포함 시)
                    if (isInfoLimitSoc(soc.getSocDescription())) {
                        int amount = parseAmount(soc.getSocRateValue());
                        if (amount > 0) {
                            res.setInfoLimitAmount(amount);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("X20 부가서비스 조회 실패: {}", e.getMessage());
        }

        res.setItems(items);
        // availableItems: X97 구현 전까지 빈 목록 (프론트엔드 null 처리 방지)
        res.setAvailableItems(new ArrayList<>());
        return res;
    }

    /**
     * X21 부가서비스 신청. ASIS regSvcChg 와 동일.
     */
    @Override
    public Map<String, Object> additionReg(AdditionRegReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn()) || isBlank(req.getSoc())) {
            result.put("success", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, soc)");
            return result;
        }

        try {
            MpRegSvcChgVO vo = mplatFormSvc.regSvcChg(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getFtrNewParam());

            if (vo.isSuccess()) {
                result.put("success", true);
                result.put("resultCode", vo.getResultCode());
                result.put("globalNo", vo.getGlobalNo());
                result.put("message", "");
            } else {
                result.put("success", false);
                result.put("resultCode", vo.getResultCode());
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("X21 부가서비스 신청 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "부가서비스 신청 처리 중 오류가 발생했습니다.");
        }

        return result;
    }

    /**
     * X38 부가서비스 해지. ASIS moscRegSvcCanChg 와 동일.
     */
    @Override
    public Map<String, Object> additionCancel(AdditionCancelReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn()) || isBlank(req.getSoc())) {
            result.put("success", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, soc)");
            return result;
        }

        try {
            MpMoscRegSvcCanChgInVO vo = mplatFormSvc.moscRegSvcCanChg(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc());

            if (vo.isSuccess()) {
                result.put("success", true);
                result.put("resultCode", vo.getResultCode());
                result.put("globalNo", vo.getGlobalNo());
                result.put("message", "");
            } else {
                result.put("success", false);
                result.put("resultCode", vo.getResultCode());
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("X38 부가서비스 해지 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "부가서비스 해지 처리 중 오류가 발생했습니다.");
        }

        return result;
    }

    private AdditionItemDto toItemDto(MpSocVO soc) {
        AdditionItemDto item = new AdditionItemDto();
        item.setSoc(soc.getSoc());
        item.setSocDescription(soc.getSocDescription());
        item.setSocRateValue(soc.getSocRateValue());
        item.setEffectiveDate(soc.getEffectiveDate());
        item.setProdHstSeq(soc.getProdHstSeq());
        item.setParamSbst(soc.getParamSbst());
        return item;
    }

    private boolean isWirelessBlockDesc(String desc) {
        if (desc == null) return false;
        return desc.contains("무선") && desc.contains("차단");
    }

    private boolean isInfoLimitSoc(String desc) {
        if (desc == null) return false;
        return desc.contains("정보료") && desc.contains("상한");
    }

    private int parseAmount(String rateValue) {
        if (isBlank(rateValue)) return 0;
        try {
            return Integer.parseInt(rateValue.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
