package com.ktmmobile.msf.formOwnChg.service;

import com.ktmmobile.msf.formOwnChg.dto.OwnChgApplyDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgApplyVO;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgTrnsInsertDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgTrnsfeInsertDto;
import com.ktmmobile.msf.formOwnChg.mapper.OwnChgMapper;
import org.springframework.stereotype.Service;

/**
 * 명의변경 신청서 등록 서비스 구현 (TO-BE).
 * CsTransferMapper grantorRegSave/assigneeRegSave 참조. 로컬 DB(nmcp_nfl_chg_trns/trnsfe) 저장.
 */
@Service
public class OwnChgApplySvcImpl implements OwnChgApplySvc {

    private final OwnChgMapper ownChgMapper;

    public OwnChgApplySvcImpl(OwnChgMapper ownChgMapper) {
        this.ownChgMapper = ownChgMapper;
    }

    @Override
    public OwnChgApplyVO apply(OwnChgApplyDto request) {
        if (request == null) {
            return OwnChgApplyVO.fail("요청 정보가 없습니다.");
        }
        OwnChgApplyDto.CustomerFormDto cf = request.getCustomerForm();
        if (cf == null) {
            return OwnChgApplyVO.fail("고객 정보가 없습니다.");
        }
        if (cf.getName() == null || cf.getName().trim().isEmpty()) {
            return OwnChgApplyVO.fail("양도고객 이름을 입력해 주세요.");
        }
        if (cf.getPhone() == null || cf.getPhone().trim().isEmpty()) {
            return OwnChgApplyVO.fail("휴대폰 번호를 입력해 주세요.");
        }
        if (!Boolean.TRUE.equals(cf.getPhoneAuthCompleted())) {
            return OwnChgApplyVO.fail("휴대폰 인증을 완료해 주세요.");
        }
        OwnChgApplyDto.TransfereeDto tf = cf.getTransferee();
        if (tf != null && (tf.getName() == null || tf.getName().trim().isEmpty())) {
            return OwnChgApplyVO.fail("양수고객 이름을 입력해 주세요.");
        }
        if (request.getAgencyCode() == null || request.getAgencyCode().trim().isEmpty()) {
            return OwnChgApplyVO.fail("대리점을 선택해 주세요.");
        }
        if (!Boolean.TRUE.equals(cf.getTermsAgreed())) {
            return OwnChgApplyVO.fail("약관 동의를 완료해 주세요.");
        }
        if (cf.getRatePlanCode() == null || cf.getRatePlanCode().trim().isEmpty()) {
            return OwnChgApplyVO.fail("요금제를 선택해 주세요.");
        }

        try {
            // AS-IS MC0 연동 시: request.getProductForm()의 mcnStatRsnCd(미입력 시 "RCMCMCN"), usimSuccYn(미입력 시 "Y") 사용 예정.

            Long trnsApyNo = ownChgMapper.nextTrnsApyNo();
            if (trnsApyNo == null) {
                return OwnChgApplyVO.fail("접수번호 발급에 실패했습니다.");
            }

            // 양도인 저장 (NMCP_NFL_CHG_TRNS) — ASIS grantorRegSave 참조
            OwnChgTrnsInsertDto trnsDto = new OwnChgTrnsInsertDto();
            trnsDto.setTrnsApyNo(trnsApyNo);
            trnsDto.setTrnsNm(cf.getName());
            trnsDto.setTrnsMobileNo(normalizePhone(cf.getPhone()));
            trnsDto.setTrnsPhoneNo(normalizePhone(cf.getPhone()));
            trnsDto.setTrnsPass("-");
            trnsDto.setTrnsMyslfConfMeth("1");
            trnsDto.setTrnsStatusVal("1");
            trnsDto.setCretId("MFORM");
            if (tf != null) {
                trnsDto.setTrnsTrnsfeNm(tf.getName());
                trnsDto.setTrnsTrnsfeMobileNo(normalizePhone(tf.getContactPhone()));
            }
            int grantorRows = ownChgMapper.insertGrantor(trnsDto);
            if (grantorRows <= 0) {
                return OwnChgApplyVO.fail("양도인 정보 저장에 실패했습니다.");
            }

            // 양수인 저장 (NMCP_NFL_CHG_TRNSFE) — ASIS assigneeRegSave 참조
            if (tf != null) {
                OwnChgTrnsfeInsertDto trnsfeDto = new OwnChgTrnsfeInsertDto();
                trnsfeDto.setTrnsfeApyNo(trnsApyNo);
                trnsfeDto.setTrnsfeNm(tf.getName());
                trnsfeDto.setTrnsfeMobileNo(normalizePhone(tf.getContactPhone()));
                trnsfeDto.setTrnsfeResno(tf.getResidentNo());
                trnsfeDto.setTrnsfeMyslfConfMeth(tf.getIdDocType() != null ? tf.getIdDocType() : "1");
                trnsfeDto.setCretId("MFORM");
                int transfereeRows = ownChgMapper.insertTransferee(trnsfeDto);
                if (transfereeRows <= 0) {
                    return OwnChgApplyVO.fail("양수인 정보 저장에 실패했습니다.");
                }
                // ASIS statusEdit: 양수인 입력 후 양도인 상태값 '2'(신청완료)로 변경
                ownChgMapper.updateGrantorStatus(trnsApyNo, "2");
            }

            String applicationNo = "IDN-" + trnsApyNo;
            return OwnChgApplyVO.ok(applicationNo);
        } catch (Exception e) {
            return OwnChgApplyVO.fail("신청서 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private static String normalizePhone(String phone) {
        if (phone == null) return null;
        return phone.replaceAll("[^0-9]", "");
    }
}
