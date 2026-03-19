package com.ktmmobile.msf.formSvcCncl.service;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclRemainChargeResVO;
import com.ktmmobile.msf.formSvcCncl.mapper.SvcCnclInsertDto;
import com.ktmmobile.msf.formSvcCncl.mapper.SvcCnclMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 서비스해지 신청서 서비스 구현.
 * 설계서 S104010101~S104040101 기준.
 * X18(잔여요금) 연동 예정. 현재 빈 응답 반환.
 */
@Service
public class SvcCnclSvcImpl implements SvcCnclSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcCnclSvcImpl.class);

    private final SvcCnclMapper svcCnclMapper;

    public SvcCnclSvcImpl(SvcCnclMapper svcCnclMapper) {
        this.svcCnclMapper = svcCnclMapper;
    }

    /**
     * 해지 가능 여부. MC0 연동 전까지 항상 true 반환.
     */
    @Override
    public boolean eligible(SvcChgInfoDto req) {
        return true;
    }

    /**
     * 잔여요금/위약금 조회.
     * X18 연동 전까지 빈 응답 반환 (프론트는 null 처리).
     * ASIS: MplatFormService.getRemainCharge(ncn, ctn) → X22/X18 호출.
     */
    @Override
    public SvcCnclRemainChargeResVO getRemainCharge(SvcChgInfoDto req) {
        if (req == null || isBlank(req.getCtn())) {
            return SvcCnclRemainChargeResVO.empty();
        }
        // X18 연동 예정 - 현재 빈 응답
        return SvcCnclRemainChargeResVO.empty();
    }

    /**
     * 해지 신청서 등록.
     * ASIS: DB 저장(NMCP_NFL_CNCL_APY 등) 후 M플랫폼 연동.
     * TO-BE: DB 저장까지만 구현, M플랫폼 연동 예정.
     */
    @Override
    public SvcCnclApplyVO apply(SvcCnclApplyDto req) {
        if (req == null) {
            return SvcCnclApplyVO.fail("요청 정보가 없습니다.");
        }
        SvcCnclApplyDto.CustomerFormDto cf = req.getCustomerForm();
        if (cf == null) {
            return SvcCnclApplyVO.fail("고객 정보가 없습니다.");
        }
        if (isBlank(cf.getName())) {
            return SvcCnclApplyVO.fail("고객명을 입력해 주세요.");
        }
        if (isBlank(cf.getPhone())) {
            return SvcCnclApplyVO.fail("휴대폰 번호를 입력해 주세요.");
        }
        if (!Boolean.TRUE.equals(cf.getPhoneAuthCompleted())) {
            return SvcCnclApplyVO.fail("휴대폰 인증을 완료해 주세요.");
        }
        if (isBlank(req.getAgencyCode())) {
            return SvcCnclApplyVO.fail("대리점을 선택해 주세요.");
        }
        SvcCnclApplyDto.ProductFormDto pf = req.getProductForm();
        if (pf == null || isBlank(pf.getUseType())) {
            return SvcCnclApplyVO.fail("사용여부를 선택해 주세요.");
        }

        try {
            Long cancelApyNo = svcCnclMapper.nextCancelApyNo();
            if (cancelApyNo == null) {
                return SvcCnclApplyVO.fail("접수번호 발급에 실패했습니다.");
            }

            SvcCnclInsertDto dto = new SvcCnclInsertDto();
            dto.setCancelApyNo(cancelApyNo);
            dto.setCustNm(cf.getName());
            dto.setMobileNo(normalizePhone(cf.getPhone()));
            dto.setCustType(cf.getCustType());
            dto.setUseType(pf.getUseType());
            dto.setRemainCharge(pf.getRemainCharge());
            dto.setPenalty(pf.getPenalty());
            dto.setInstallmentRemain(pf.getInstallmentRemain());
            dto.setCancelReason(pf.getCancelReason());
            dto.setMemo(pf.getMemo());
            dto.setAgncCd(req.getAgencyCode());
            dto.setCretId("MFORM");

            int rows = svcCnclMapper.insertCancel(dto);
            if (rows <= 0) {
                return SvcCnclApplyVO.fail("해지 신청서 저장에 실패했습니다.");
            }

            String applicationNo = "CNC-" + cancelApyNo;
            return SvcCnclApplyVO.ok(applicationNo);
        } catch (Exception e) {
            logger.error("서비스해지 신청서 저장 오류: {}", e.getMessage());
            return SvcCnclApplyVO.fail("신청서 저장 중 오류가 발생했습니다.");
        }
    }

    private static String normalizePhone(String phone) {
        if (phone == null) return null;
        return phone.replaceAll("[^0-9]", "");
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
