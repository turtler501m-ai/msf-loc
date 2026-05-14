package com.ktmmobile.msf.domains.form.form.termination.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceResDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.EncryptUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfCustRequestScanService;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;
import com.ktmmobile.msf.domains.form.form.termination.repository.CancelConsultRepositoryImpl;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.AJAX_SUCCESS;


@Service
@Deprecated
public class MsfCancelConsultSvcImpl implements MsfCancelConsultSvc {

    private static final Logger logger = LoggerFactory.getLogger(MsfCancelConsultSvcImpl.class);

    @Autowired
    private CancelConsultRepositoryImpl cancelConsultRepository;

    @Autowired
    private MsfMypageSvc msfMypageSvc;

    @Autowired
    private MsfCustRequestScanService custRequestScanService;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Override
    public int countCancelConsult(CancelConsultDto cancelConsultDto) {
        return cancelConsultRepository.countCancelConsult(cancelConsultDto);
    }

    @Override
    @Transactional
    public FormResponse<Void> cancelConsultAjax(CancelConsultDto cancelConsultDto, HttpSession session) {
        // 로그인 세션이 없으면 해지 상담 신청을 진행하지 않는다.
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            return response("00001", "로그인 후 다시 시도 부탁드립니다.");
        }

        try {
            // 해지 상담 신청은 성인 정회원만 가능하므로 생년월일 기준 만 나이를 확인한다.
            String today = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
            int age = NmcpServiceUtils.getBirthDateToAmericanAge(userSession.getBirthday().substring(0, 8), today);
            if (age < 19) {
                return response("00002", "미성년자는 신청할 수 없습니다.");
            }

            // 로그인 회원의 보유 회선 목록에서 신청 대상 계약번호 또는 휴대폰번호를 검증한다.
            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
            if (cntrList.size() <= 0) {
                return response("00003", "정회원 정보가 없습니다.");
            }

            // 선택 입력은 계약번호 기준, 직접 입력은 휴대폰번호 기준으로 대상 회선을 찾는다.
            String ncn = StringUtil.NVL(cancelConsultDto.getContractNum(), "");
            String cancelMobileNo = StringUtil.NVL(cancelConsultDto.getCancelMobileNo(), "");
            boolean isManualInput = "manual".equals(ncn);
            if (!isManualInput && "".equals(ncn) && "".equals(cancelMobileNo)) {
                return response("00004", "해지 신청 번호가 없습니다.");
            }

            boolean isCheck = false;
            String ctn = "";
            String cstmrNativeRrn = "";
            for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                // 계약번호 선택 방식: 선택된 계약번호와 회원 회선의 계약번호가 일치해야 한다.
                if (ncn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                    ctn = mcpUserCntrMngDto.getCntrMobileNo();
                    cstmrNativeRrn = mcpUserCntrMngDto.getUnUserSSn();
                    isCheck = true;
                    break;
                }

                // 휴대폰번호 직접 입력 방식: 입력 번호가 회원 보유 회선에 포함되어야 한다.
                if (cancelMobileNo.equals(mcpUserCntrMngDto.getCntrMobileNo())) {
                    ctn = cancelMobileNo;
                    ncn = mcpUserCntrMngDto.getSvcCntrNo();
                    cstmrNativeRrn = mcpUserCntrMngDto.getUnUserSSn();
                    isCheck = true;
                    break;
                }
            }

            if (!isCheck) {
                return response("00005", "해지 신청 번호 불일치");
            }

            // 동일 회선의 미처리 해지 상담 신청이 있으면 중복 접수를 막는다.
            int isDuplicate = countCancelConsult(cancelConsultDto);
            if (isDuplicate > 0) {
                return response("00006", "이미 동일한 해지 신청이 접수 중입니다.");
            }

            // 본인인증 완료 후 세션에 저장된 NICE 인증 결과가 있어야 저장을 진행한다.
            NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);
            if (niceResDto == null) {
                return response("00007", "본인인증 정보가 없습니다.");
            }

            // 저장용 신청 DTO에 인증 정보, 회선 정보, 고객 정보, 접속 IP를 보강한다.
            cancelConsultDto.setOnlineAuthInfo("ReqNo:" + cancelConsultDto.getReqSeq() + ", ResNo:" + cancelConsultDto.getResSeq());
            cancelConsultDto.setCancelMobileNo(ctn);
            cancelConsultDto.setContractNum(ncn);
            cancelConsultDto.setCstmrName(userSession.getName());
            cancelConsultDto.setCstmrNativeRrn(EncryptUtil.ace256Enc(cstmrNativeRrn));
            cancelConsultDto.setRegstId(userSession.getUserId());
            cancelConsultDto.setRip(RequestUtils.getClientIp());

            // 해지 상담 신청 마스터와 상세 데이터를 저장한다.
            FormResponse<Void> saveResponse = cancelConsultRequest(cancelConsultDto);
            if (!AJAX_SUCCESS.equals(saveResponse.resCode())) {
                return saveResponse;
            }

            try {
                // 저장 성공 후 서식지 스캔 서버로 신청 데이터를 전송한다.
                custRequestScanService.prodSendScan(Long.parseLong(cancelConsultDto.getCustReqSeq()), userSession.getUserId(), "CC");
                insertScanTrace(cancelConsultDto, "SUCCESS");
                return saveResponse;
            } catch (McpCommonJsonException e) {
                // 스캔 서버 연동 실패도 관리자 접근 이력에 실패로 남긴다.
                insertScanTrace(cancelConsultDto, "FAIL");
                return response("FAIL", "스캔 서버 전송에 실패했습니다.");
            } catch (Exception e) {
                // 스캔 전송 중 예외가 발생해도 사용자에게는 동일한 실패 응답을 반환한다.
                insertScanTrace(cancelConsultDto, "FAIL");
                return response("FAIL", "스캔 서버 전송에 실패했습니다.");
            }
        } catch (Exception e) {
            // 검증 또는 저장 전처리 중 예상하지 못한 예외는 공통 오류 응답으로 정리한다.
            logger.error("[cancelConsultAjax] exception", e);
            return response("99999", "해지 신청 처리 중 오류가 발생했습니다.");
        }
    }

    @Override
    @Transactional
    public FormResponse<Void> cancelConsultRequest(CancelConsultDto cancelConsultDto) {
        try {
            // 해지 상담 신청은 고객 요청 마스터와 해지 상담 상세를 함께 저장한다.
            int mstInserted = cancelConsultRepository.insertNmcpCustReqMst(cancelConsultDto);
            int cancelInserted = cancelConsultRepository.insertCancelConsult(cancelConsultDto);

            // 둘 중 하나라도 저장되지 않으면 동일 트랜잭션을 롤백한다.
            if (mstInserted <= 0 || cancelInserted <= 0) {
                logger.warn("[cancelConsultRequest] insert failed: custReqSeq={}, mstInserted={}, cancelInserted={}",
                    cancelConsultDto.getCustReqSeq(), mstInserted, cancelInserted);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return response("00008", "시스템에 문제가 발생하였습니다. 다시 진행 부탁드립니다.");
            }

            return new FormResponse<>(AJAX_SUCCESS, null, null);
        } catch (Exception e) {
            // 저장 중 예외가 발생하면 롤백 표시 후 업무 실패 응답으로 변환한다.
            logger.error("[cancelConsultRequest] exception: custReqSeq={}", cancelConsultDto.getCustReqSeq(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response("00008", "시스템에 문제가 발생하였습니다. 다시 진행 부탁드립니다.");
        }
    }

    @Override
    public List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto) {
        return cancelConsultRepository.selectCancelConsultList(cancelConsultDto);
    }

    private void insertScanTrace(CancelConsultDto cancelConsultDto, String result) {
        // 스캔 서버 전송 결과는 관리자 접근 이력 테이블에 성공/실패 상태로 남긴다.
        String custReqSeq = StringUtil.NVL(cancelConsultDto.getCustReqSeq(), "");
        McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
        mcpIpStatisticDto.setPrcsMdlInd("CANCEL_REQUEST_SCAN");
        mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" + custReqSeq + "]");
        mcpIpStatisticDto.setParameter(custReqSeq);
        mcpIpStatisticDto.setTrtmRsltSmst(result);
        ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
    }

    private FormResponse<Void> response(String code, String message) {
        return new FormResponse<>(code, message, null);
    }

}
