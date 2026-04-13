package com.ktmmobile.msf.domains.form.form.termination.service;

import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.domains.form.form.termination.dao.CancelConsultDao;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.TerminationSettlementDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationInsertDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class MsfCancelConsultSvcImpl implements MsfCancelConsultSvc {

    private static final Logger logger = LoggerFactory.getLogger(MsfCancelConsultSvcImpl.class);

    @Autowired
    private CancelConsultDao cancelConsultDao;

    @Autowired
    private MsfMplatFormService msfMplatFormService;

    /** requestView 위약금 블록(X54·X16·mspAddInfo) 호출을 위한 마이올레 서비스 */
    @Autowired
    private MsfMypageSvc msfMypageSvc;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public int countCancelConsult(CancelConsultDto cancelConsultDto) {
        return cancelConsultDao.countCancelConsult(cancelConsultDto);
    }

    @Override
    @Transactional
    public String cancelConsultRequest(CancelConsultDto cancelConsultDto) {
        String result = "";

        cancelConsultDao.insertNmcpCustReqMst(cancelConsultDto);
        cancelConsultDao.insertCancelConsult(cancelConsultDto);

        result = "SUCCESS";

        return result;
    }

    @Override
    public List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto) {
        return cancelConsultDao.selectCancelConsultList(cancelConsultDto);
    }

    @Override
    public RemainChargeResVO getRemainCharge(RemainChargeReqDto reqDto) {
        RemainChargeResVO resVO = new RemainChargeResVO();
        try {
            MpFarRealtimePayInfoVO mpVO = msfMplatFormService.farRealtimePayInfo(
                    reqDto.getNcn(), reqDto.getCtn(), reqDto.getCustId());

            if (mpVO == null) {
                resVO.setSuccess(false);
                resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
                return resVO;
            }

            resVO.setSuccess(true);
            resVO.setSearchDay(mpVO.getSearchDay());
            resVO.setSearchTime(mpVO.getSearchTime());
            resVO.setSumAmt(mpVO.getSumAmt());

            List<RemainChargeResVO.FareItem> items = new ArrayList<>();
            if (mpVO.getList() != null) {
                for (MpFarRealtimePayInfoVO.RealFareVO realFare : mpVO.getList()) {
                    if ("원단위절사금액".equals(realFare.getGubun())) {
                        continue;
                    }
                    RemainChargeResVO.FareItem item = new RemainChargeResVO.FareItem();
                    item.setGubun(realFare.getGubun());
                    item.setPayment(realFare.getPayment());
                    items.add(item);
                }
            }
            resVO.setItems(items);

            // --- 마이올레 서비스(requestView 위약금 블록) 호출 ---
            // X54(스폰서/위약금), X16(잔여할부금), mspAddInfo(할부원금) 조회
            try {
                TerminationSettlementDto settlement =
                    msfMypageSvc.getTerminationSettlement(reqDto.getNcn(), reqDto.getCtn(), reqDto.getCustId());
                resVO.setSettlement(settlement);
                logger.info("[getRemainCharge] 위약금 정산 조회 완료: ncn={}, prePayment={}, trmnForecBprmsAmt={}",
                    reqDto.getNcn(), settlement.isPrePayment(), settlement.getTrmnForecBprmsAmt());
            } catch (Exception e) {
                logger.warn("[getRemainCharge] 위약금 정산 조회 오류 (X18 결과는 유지): ncn={}, {}",
                    reqDto.getNcn(), e.getMessage());
            }
        } catch (com.ktmmobile.msf.domains.form.common.exception.SelfServiceException e) {
            logger.info("X18 ERROR: ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn(), e);
            resVO.setSuccess(false);
            resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
        } catch (java.net.SocketTimeoutException e) {
            logger.info("X18 ERROR (Timeout): ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn(), e);
            resVO.setSuccess(false);
            resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
        } catch (Exception e) {
            logger.error("X18 잔여요금 조회 오류: ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn(), e);
            resVO.setSuccess(false);
            resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
        }
        return resVO;
    }

    @Override
    public TerminationApplyResVO apply(TerminationApplyReqDto reqDto) {
        if (reqDto == null) {
            return TerminationApplyResVO.fail("요청 정보가 없습니다.");
        }
        if (reqDto.getCustomer() == null) {
            return TerminationApplyResVO.fail("고객 정보가 없습니다.");
        }
        if (reqDto.getProduct() == null) {
            return TerminationApplyResVO.fail("상품 정보가 없습니다.");
        }
        if (isBlank(reqDto.getCustomer().getUserName())) {
            return TerminationApplyResVO.fail("고객명을 입력해 주세요.");
        }
        if (isBlank(reqDto.getCustomer().getNcn())) {
            return TerminationApplyResVO.fail("계약번호가 없습니다.");
        }
        if (isBlank(reqDto.getProduct().getIsActive())) {
            return TerminationApplyResVO.fail("해지 후 사용 여부를 선택해 주세요.");
        }
        if (reqDto.getAgreement() == null || !reqDto.getAgreement().isAgreeCheck1()) {
            return TerminationApplyResVO.fail("혜택 소멸사항 동의가 필요합니다.");
        }

        String cancelMobileNo = joinPhone(
            reqDto.getCustomer().getCancelPhone1(),
            reqDto.getCustomer().getCancelPhone2(),
            reqDto.getCustomer().getCancelPhone3()
        );
        String receiveMobileNo = joinPhone(
            reqDto.getCustomer().getAfterTel1(),
            reqDto.getCustomer().getAfterTel2(),
            reqDto.getCustomer().getAfterTel3()
        );

        if (isBlank(cancelMobileNo)) {
            return TerminationApplyResVO.fail("해지 휴대폰번호를 입력해 주세요.");
        }
        if (isBlank(receiveMobileNo)) {
            return TerminationApplyResVO.fail("해지 후 연락처를 입력해 주세요.");
        }

        if (isLocal()) {
            return TerminationApplyResVO.ok("LOCAL-CANCEL-" + System.currentTimeMillis());
        }

        try {
            Long requestKey = cancelConsultDao.nextRequestKey();
            if (requestKey == null) {
                return TerminationApplyResVO.fail("신청 키 발급에 실패했습니다.");
            }

            TerminationInsertDto dto = new TerminationInsertDto();
            dto.setRequestKey(requestKey);
            dto.setAgentCd(reqDto.getCustomer().getAgencyName());
            dto.setOperTypeCd("CC");
            dto.setCstmrTypeCd(normalizeCustomerType(reqDto.getCustomer().getCustomerType()));
            dto.setCancelMobileNo(cancelMobileNo);
            dto.setContractNum(reqDto.getCustomer().getNcn());
            dto.setCstmrNm(reqDto.getCustomer().getUserName());
            dto.setReceiveMobileNo(receiveMobileNo);
            dto.setReceiveWayCd(normalizeReceiveWay(reqDto.getCustomer().getPostMethod()));
            dto.setCancelUseCompanyCd(normalizeUseType(reqDto.getProduct().getIsActive()));
            dto.setPayAmt(parseLong(reqDto.getProduct().getUsageFee()));
            dto.setPnltAmt(parseLong(reqDto.getProduct().getPenaltyFee()));
            dto.setLastSumAmt(parseLong(reqDto.getProduct().getFinalAmount()));
            dto.setInstamtMnthCnt(parseInteger(reqDto.getProduct().getRemainPeriod()));
            dto.setInstamtMnthAmt(parseLong(reqDto.getProduct().getRemainAmount()));
            dto.setBenefitAgreeYn(toYn(reqDto.getAgreement().isAgreeCheck1()));
            dto.setClauseCntrDelYn(toYn(reqDto.getAgreement().isAgreeCheck2()));
            dto.setEtcAgreeYn(toYn(reqDto.getAgreement().isAgreeCheck3()));
            dto.setMemo(reqDto.getProduct().getMemo());
            dto.setRegstId("MSF_FORM");
            dto.setCretId("MSF_FORM");
            dto.setAmdId("MSF_FORM");
            dto.setCretIp("127.0.0.1");
            dto.setAmdIp("127.0.0.1");

            int inserted = cancelConsultDao.insertRequestCancel(dto);
            if (inserted <= 0) {
                return TerminationApplyResVO.fail("서비스해지 신청 저장에 실패했습니다.");
            }

            return TerminationApplyResVO.ok(String.valueOf(requestKey));
        } catch (Exception e) {
            logger.error("서비스해지 신청 저장 오류", e);
            return TerminationApplyResVO.fail("서비스해지 신청 저장 중 오류가 발생했습니다.");
        }
    }

    private boolean isLocal() {
        return apiInterfaceServer != null && apiInterfaceServer.startsWith("LOCAL");
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String joinPhone(String p1, String p2, String p3) {
        String joined = String.format("%s%s%s", safe(p1), safe(p2), safe(p3)).replaceAll("[^0-9]", "");
        return joined.isEmpty() ? "" : joined;
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }

    private static String normalizeReceiveWay(String postMethod) {
        if ("postMethod1".equals(postMethod) || "mail".equalsIgnoreCase(safe(postMethod))) {
            return "P";
        }
        if ("postMethod2".equals(postMethod) || "email".equalsIgnoreCase(safe(postMethod))) {
            return "E";
        }
        return null;
    }

    private static String normalizeUseType(String isActive) {
        return switch (safe(isActive)) {
            case "MM" -> "KTM";
            case "KT" -> "KT";
            case "SK" -> "SKT";
            case "LG" -> "LGT";
            case "ETC" -> "ETC";
            default -> isActive;
        };
    }

    private static String normalizeCustomerType(String customerType) {
        return switch (safe(customerType)) {
            case "customerType1" -> "NA";
            case "customerType2" -> "MI";
            case "customerType3" -> "FO";
            case "customerType4" -> "FM";
            case "customerType5" -> "CO";
            case "customerType6" -> "PB";
            default -> customerType;
        };
    }

    private static String toYn(boolean value) {
        return value ? "Y" : "N";
    }

    private static Long parseLong(String value) {
        String normalized = safe(value).replaceAll("[^0-9-]", "");
        if (normalized.isEmpty()) {
            return null;
        }
        return Long.parseLong(normalized);
    }

    private static Integer parseInteger(String value) {
        String normalized = safe(value).replaceAll("[^0-9-]", "");
        if (normalized.isEmpty()) {
            return null;
        }
        return Integer.parseInt(normalized);
    }
}
