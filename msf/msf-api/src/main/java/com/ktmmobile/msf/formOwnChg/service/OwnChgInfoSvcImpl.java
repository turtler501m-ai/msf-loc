package com.ktmmobile.msf.formOwnChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpNameChgPreChkVO;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgCheckTelNoReqDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgCheckTelNoVO;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgGrantorReqChkReqDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgGrantorReqChkVO;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 명의변경 신청서 - 가능 여부·연락처 검증 서비스 구현.
 */
@Service
public class OwnChgInfoSvcImpl implements OwnChgInfoSvc {

    private static final Logger logger = LoggerFactory.getLogger(OwnChgInfoSvcImpl.class);

    private static final String CHK_TYPE_NOR = "NOR"; // 양도인
    private static final String CHK_TYPE_NEE = "NEE"; // 양수인

    @Autowired
    private MplatFormSvc mplatFormSvc;

    /**
     * 명의변경 가능 여부 — MC0 사전체크.
     * ASIS: MyNameChgServiceImpl → MplatFormService.nameChgPreChk(MC0)
     * rsltCd "0000" 이면 가능, 그 외 불가 (정지/미납/90일 미만 가입 등).
     */
    @Override
    public boolean eligible(SvcChgInfoDto req) {
        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn())) {
            return false;
        }
        try {
            MpNameChgPreChkVO vo = mplatFormSvc.nameChgPreChk(
                req.getNcn(), req.getCtn(), req.getCustId());
            if (!vo.isSuccess()) {
                logger.warn("MC0 명의변경 사전체크 실패: resultCode={}, msg={}", vo.getResultCode(), vo.getSvcMsg());
                return false;
            }
            boolean ok = "0000".equals(vo.getRsltCd());
            if (!ok) {
                logger.warn("MC0 명의변경 불가: rsltCd={}, rsltMsg={}", vo.getRsltCd(), vo.getRsltMsg());
            }
            return ok;
        } catch (Exception e) {
            logger.error("MC0 명의변경 사전체크 오류: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public OwnChgGrantorReqChkVO grantorReqChk(OwnChgGrantorReqChkReqDto req) {
        if (req == null || isBlank(req.getContractNum())) {
            return OwnChgGrantorReqChkVO.fail(OwnChgGrantorReqChkVO.CODE_ERROR, "계약번호를 입력해 주세요.");
        }
        // AS-IS: M전산 cntrListNmChg 후 STOP/NONPAY/LESSNINETY/ERROR 판단. TO-BE: 연동 예정까지 SUCCESS 반환.
        return OwnChgGrantorReqChkVO.success();
    }

    @Override
    public OwnChgCheckTelNoVO checkTelNo(OwnChgCheckTelNoReqDto req) {
        if (req == null || isBlank(req.getGrantorPhone())) {
            return OwnChgCheckTelNoVO.fail(OwnChgCheckTelNoVO.CODE_SAME_ETC, "명의변경 회선 번호가 없습니다.");
        }
        String mobileNo = normalizePhone(req.getGrantorPhone());

        if (CHK_TYPE_NOR.equals(req.getChkTelType())) {
            // 양도인: 명변회선 ≠ 미납연락처
            if (!isBlank(req.getEtcMobile()) && mobileNo.equals(normalizePhone(req.getEtcMobile()))) {
                return OwnChgCheckTelNoVO.fail(OwnChgCheckTelNoVO.CODE_SAME_ETC,
                    "명의변경 회선과 양도인 미납을 위한 연락처는 달라야 합니다.");
            }
            // 양도인 미성년자: 명변회선 ≠ 법정대리인 연락처
            if (!isBlank(req.getGrMinorAgentTel()) && mobileNo.equals(normalizePhone(req.getGrMinorAgentTel()))) {
                return OwnChgCheckTelNoVO.fail(OwnChgCheckTelNoVO.CODE_SAME_MINOR,
                    "명의변경 회선과 법정대리인 연락처는 달라야 합니다.");
            }
        } else if (CHK_TYPE_NEE.equals(req.getChkTelType())) {
            // 양수인 미성년자: 명변회선 ≠ 양수인 법정대리인 연락처
            if (!isBlank(req.getMinorAgentTel()) && mobileNo.equals(normalizePhone(req.getMinorAgentTel()))) {
                return OwnChgCheckTelNoVO.fail(OwnChgCheckTelNoVO.CODE_SAME_MINOR,
                    "명의변경 회선과 법정대리인 연락처는 달라야 합니다.");
            }
            // 연락가능 연락처 ≠ 양수인 법정대리인 연락처
            if (!isBlank(req.getContactPhone()) && !isBlank(req.getMinorAgentTel())
                && normalizePhone(req.getContactPhone()).equals(normalizePhone(req.getMinorAgentTel()))) {
                return OwnChgCheckTelNoVO.fail(OwnChgCheckTelNoVO.CODE_SAME_MINOR2,
                    "연락가능 연락처와 법정대리인 연락처는 달라야 합니다.");
            }
        }

        return OwnChgCheckTelNoVO.success();
    }

    private static String normalizePhone(String phone) {
        if (phone == null) return "";
        return phone.replaceAll("[^0-9]", "");
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
