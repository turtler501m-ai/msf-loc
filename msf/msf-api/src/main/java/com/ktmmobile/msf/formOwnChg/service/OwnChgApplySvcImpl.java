package com.ktmmobile.msf.formOwnChg.service;

import com.ktmmobile.msf.formOwnChg.dto.OwnChgApplyDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgApplyVO;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgCustReqInsertDto;
import com.ktmmobile.msf.formOwnChg.mapper.OwnChgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 명의변경 신청서 등록 서비스 구현 (TO-BE).
 * ASIS MyNameChgServiceImpl.myNameChgRequest() 참조.
 * DB 저장: MSF_CUST_REQUEST_MST + MSF_CUST_REQUEST_NAME_CHG + MSF_CUST_REQ_NAME_CHG_AGENT.
 */
@Service
public class OwnChgApplySvcImpl implements OwnChgApplySvc {

    private static final Logger logger = LoggerFactory.getLogger(OwnChgApplySvcImpl.class);
    private static final String MINOR_TYPE = "NM";

    private final OwnChgMapper ownChgMapper;

    public OwnChgApplySvcImpl(OwnChgMapper ownChgMapper) {
        this.ownChgMapper = ownChgMapper;
    }

    @Override
    @Transactional
    public OwnChgApplyVO apply(OwnChgApplyDto request) {
        // ── 1. 기본 검증 ──
        if (request == null) {
            return OwnChgApplyVO.fail("요청 정보가 없습니다.");
        }
        OwnChgApplyDto.CustomerFormDto cf = request.getCustomerForm();
        if (cf == null) {
            return OwnChgApplyVO.fail("고객 정보가 없습니다.");
        }
        if (isBlank(cf.getName())) {
            return OwnChgApplyVO.fail("양도고객 이름을 입력해 주세요.");
        }
        if (isBlank(cf.getPhone())) {
            return OwnChgApplyVO.fail("휴대폰 번호를 입력해 주세요.");
        }
        if (!Boolean.TRUE.equals(cf.getPhoneAuthCompleted())) {
            return OwnChgApplyVO.fail("휴대폰 인증을 완료해 주세요.");
        }
        OwnChgApplyDto.TransfereeDto tf = cf.getTransferee();
        if (tf == null || isBlank(tf.getName())) {
            return OwnChgApplyVO.fail("양수고객 이름을 입력해 주세요.");
        }
        if (isBlank(request.getAgencyCode())) {
            return OwnChgApplyVO.fail("대리점을 선택해 주세요.");
        }
        if (!Boolean.TRUE.equals(cf.getTermsAgreed())) {
            return OwnChgApplyVO.fail("약관 동의를 완료해 주세요.");
        }

        try {
            // ── 2. OwnChgApplyDto → OwnChgCustReqInsertDto 매핑 ──
            OwnChgCustReqInsertDto dto = buildInsertDto(request, cf, tf);

            // ── 3. 시퀀스 & 예약번호 발급 ──
            Long custReqSeq = ownChgMapper.nextCustReqSeq();
            if (custReqSeq == null) {
                return OwnChgApplyVO.fail("접수번호 발급에 실패했습니다.");
            }
            dto.setCustReqSeq(custReqSeq);

            String mcnResNo = ownChgMapper.generateResNo();
            dto.setMcnResNo(mcnResNo);

            // ── 4. MSF_CUST_REQUEST_MST 저장 (양도인 마스터) ──
            int mstRows = ownChgMapper.insertCustReqMst(dto);
            if (mstRows <= 0) {
                return OwnChgApplyVO.fail("양도인 정보 저장에 실패했습니다.");
            }
            logger.info("명의변경 MST 저장 완료: custReqSeq={}", custReqSeq);

            // ── 5. MSF_CUST_REQUEST_NAME_CHG 저장 (양수인 상세) ──
            int chgRows = ownChgMapper.insertCustReqNameChg(dto);
            if (chgRows <= 0) {
                return OwnChgApplyVO.fail("양수인 정보 저장에 실패했습니다.");
            }
            logger.info("명의변경 NAME_CHG 저장 완료: custReqSeq={}", custReqSeq);

            // ── 6. 미성년자 처리 ──
            boolean grantorMinor = MINOR_TYPE.equals(dto.getGrCstmrType());
            boolean assigneeMinor = MINOR_TYPE.equals(dto.getCstmrType());

            if (grantorMinor || assigneeMinor) {
                // MSF_CUST_REQ_NAME_CHG_AGENT 저장
                int agentRows = ownChgMapper.insertCustReqNameChgAgent(dto);
                if (agentRows <= 0) {
                    return OwnChgApplyVO.fail("법정대리인 정보 저장에 실패했습니다.");
                }
                logger.info("명의변경 AGENT 저장 완료: custReqSeq={}", custReqSeq);

                // 미성년 양도인: 마스터 인증정보 초기화 (법정대리인 인증으로 대체)
                if (grantorMinor) {
                    ownChgMapper.updateCustReqMstAuth(dto);
                }
                // 미성년 양수인: 상세 인증정보 초기화 (법정대리인 인증으로 대체)
                if (assigneeMinor) {
                    ownChgMapper.updateCustReqNameChgAuth(dto);
                }
            }

            String applicationNo = "NC-" + custReqSeq;
            logger.info("명의변경 신청 완료: applicationNo={}, mcnResNo={}", applicationNo, mcnResNo);
            return OwnChgApplyVO.ok(applicationNo);

        } catch (Exception e) {
            logger.error("명의변경 신청서 저장 오류: {}", e.getMessage(), e);
            return OwnChgApplyVO.fail("신청서 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 프론트 OwnChgApplyDto → DB INSERT용 flat DTO 변환.
     */
    private OwnChgCustReqInsertDto buildInsertDto(OwnChgApplyDto request,
                                                    OwnChgApplyDto.CustomerFormDto cf,
                                                    OwnChgApplyDto.TransfereeDto tf) {
        OwnChgCustReqInsertDto dto = new OwnChgCustReqInsertDto();
        OwnChgApplyDto.ProductFormDto pf = request.getProductForm();

        // ── 공통 ──
        dto.setAgencyCode(request.getAgencyCode());

        // ── MST (양도인) ──
        dto.setReqType("NC");
        dto.setUserid("MFORM");
        dto.setGrCstmrName(cf.getName());
        dto.setMobileNo(normalizePhone(cf.getPhone()));
        dto.setContractNum(cf.getNcn());
        dto.setGrCstmrType(cf.getCustType());
        dto.setEtcMobile(normalizePhone(cf.getEtcMobile()));

        // 양도인 미성년자 법정대리인
        dto.setGrMinorAgentName(cf.getGrMinorAgentName());
        dto.setGrMinorAgentRelation(cf.getGrMinorAgentRelation());
        dto.setGrMinorAgentRrn(cf.getGrMinorAgentRrn());
        dto.setGrMinorAgentTel(normalizePhone(cf.getGrMinorAgentTel()));
        dto.setGrMinorAgentAgrmYn(cf.getGrMinorAgentAgrmYn());

        // ── NAME_CHG (양수인) ──
        dto.setCstmrName(tf.getName());
        dto.setCstmrType(tf.getCustType());
        dto.setCstmrNativeRrn(tf.getResidentNo());
        dto.setCstmrForeignerNation(tf.getForeignerNation());

        // 약관동의
        dto.setClauseCntrDelFlag(tf.getClauseCntrDelFlag());
        dto.setClausePriCollectFlag(tf.getClausePriCollectFlag());
        dto.setClausePriOfferFlag(tf.getClausePriOfferFlag());
        dto.setClauseEssCollectFlag(tf.getClauseEssCollectFlag());
        dto.setClauseConfidenceFlag(tf.getClauseConfidenceFlag());
        dto.setClausePriAdFlag(tf.getClausePriAdFlag());
        dto.setClauseJehuFlag(tf.getClauseJehuFlag());
        dto.setPersonalInfoCollectAgree(tf.getPersonalInfoCollectAgree());
        dto.setOthersTrnsAgree(tf.getOthersTrnsAgree());
        dto.setOthersTrnsKtAgree(tf.getOthersTrnsKtAgree());
        dto.setOthersAdReceiveAgree(tf.getOthersAdReceiveAgree());
        dto.setSelfInqryAgrmYn(tf.getSelfInqryAgrmYn());

        // 가입정보 변경
        dto.setReqInfoChgYn(tf.getReqInfoChgYn());
        dto.setSoc(tf.getSoc());
        dto.setSocNm(tf.getSocNm());
        dto.setJehuProdType(tf.getJehuProdType());

        // 주소
        dto.setCstmrPost(tf.getPost());
        dto.setCstmrAddr(tf.getAddress());
        dto.setCstmrAddrDtl(tf.getAddressDtl());
        dto.setCstmrMail(tf.getEmail());
        dto.setCstmrBillSendCode(tf.getBillSendCode());

        // 납부방법
        dto.setReqPayType(tf.getReqPayType());
        dto.setReqBank(tf.getReqBank());
        dto.setReqAccountNumber(tf.getReqAccountNumber());
        dto.setReqCardCompany(tf.getReqCardCompany());
        dto.setReqCardNo(tf.getReqCardNo());
        dto.setReqCardYy(tf.getReqCardYy());
        dto.setReqCardMm(tf.getReqCardMm());

        // 신분증
        dto.setSelfCertType(tf.getIdDocType());
        dto.setSelfIssuExprDt(tf.getIdDocIssuExprDt());
        dto.setSelfIssuNum(tf.getIdDocIssuNum());

        // 양수인 연락처 3분할 (11자리: 010-XXXX-XXXX / 10자리: 02-XXXX-XXXX)
        String contactPhone = normalizePhone(tf.getContactPhone());
        if (contactPhone != null && contactPhone.length() >= 10) {
            String[] parts = splitPhoneNumber(contactPhone);
            dto.setCstmrReceiveTelFn(parts[0]);
            dto.setCstmrReceiveTelMn(parts[1]);
            dto.setCstmrReceiveTelRn(parts[2]);
        }

        // 양수인 미성년자 법정대리인
        dto.setMinorAgentName(tf.getMinorAgentName());
        dto.setMinorAgentRelation(tf.getMinorAgentRelation());
        dto.setMinorAgentRrn(tf.getMinorAgentRrn());
        dto.setMinorAgentTel(normalizePhone(tf.getMinorAgentTel()));
        dto.setMinorAgentAgrmYn(tf.getMinorAgentAgrmYn());

        // MC0 연동 기본값 (ProductFormDto에서 오버라이드 가능)
        dto.setMcnStatRsnCd("RCMCMCN");
        dto.setUsimSuccYn("Y");
        dto.setProcCd("RQ");

        if (pf != null) {
            if (!isBlank(pf.getMcnStatRsnCd())) {
                dto.setMcnStatRsnCd(pf.getMcnStatRsnCd());
            }
            if (!isBlank(pf.getUsimSuccYn())) {
                dto.setUsimSuccYn(pf.getUsimSuccYn());
            }
            dto.setIccId(pf.getUsimNo());
        }

        return dto;
    }

    /**
     * 전화번호를 [국번, 중간, 끝] 3부분으로 분할.
     * 11자리(010/011/016/017/018/019): fn=3, mn=4, rn=4
     * 10자리(02+8자리): fn=2, mn=4, rn=4
     */
    private static String[] splitPhoneNumber(String phone) {
        if (phone == null || phone.length() < 10) {
            return new String[]{"", "", ""};
        }
        if (phone.length() == 10 && phone.startsWith("02")) {
            return new String[]{phone.substring(0, 2), phone.substring(2, 6), phone.substring(6)};
        }
        // 11자리 또는 기타: 앞 3자리 국번
        return new String[]{
            phone.substring(0, 3),
            phone.substring(3, phone.length() - 4),
            phone.substring(phone.length() - 4)
        };
    }

    private static String normalizePhone(String phone) {
        if (phone == null) return null;
        return phone.replaceAll("[^0-9]", "");
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
