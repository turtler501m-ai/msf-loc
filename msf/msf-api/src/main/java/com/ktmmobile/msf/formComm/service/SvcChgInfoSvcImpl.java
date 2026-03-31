package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpUsimCheckVO;
import com.ktmmobile.msf.formComm.dto.AccountCheckReqDto;
import com.ktmmobile.msf.formComm.dto.ContractInfoDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoReqDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoResVO;
import com.ktmmobile.msf.formComm.mapper.ContractInfoMapper;
import com.ktmmobile.msf.formSvcChg.dto.UsimCheckReqDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 공통 인터페이스 연계 서비스 구현.
 * ASIS 흐름: M전산(계약정보) → Y04(인증) → X01(가입정보) 동일.
 * IF_0006 계좌인증: ASIS NiceCertifySvcImpl.checkNiceAccount() 동일 구조.
 * 인터페이스 연계 없는 공통 기능(카드번호 검증 등)은 FormCommSvcImpl 참조.
 */
@Service
public class SvcChgInfoSvcImpl implements SvcChgInfoSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgInfoSvcImpl.class);

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    @Autowired
    private MplatFormSvc mplatFormSvc;

    private final RestTemplate restTemplate = new RestTemplate();

    /** 로컬 개발 시 DB 조회 실패 허용 여부 */
    @Value("${msf.join-info.mock-when-no-db:false}")
    private boolean mockWhenNoDb;

    /** NICE 계좌인증 외부 URL (미설정 시 Mock) */
    @Value("${nice.ext.url:}")
    private String niceExtUrl;

    /** NICE UID */
    @Value("${nice.uid:}")
    private String niceUid;

    /** NICE 서비스 비밀번호 */
    @Value("${nice.svc.pwd:}")
    private String niceSvcPwd;

    @Override
    public SvcChgInfoResVO joinInfo(SvcChgInfoReqDto req, HttpServletRequest httpRequest) {
        if (req == null || isBlank(req.getName()) || isBlank(req.getCtn())) {
            return SvcChgInfoResVO.fail("고객명과 전화번호를 입력해 주세요.");
        }
        logger.debug("[X01] 휴대폰 인증 Start: name={}, ctn={}", req.getName(), req.getCtn());

        // 1. M전산 DB: 고객명+전화번호 → ncn, custId
        ContractInfoDto contractInfo = null;
        try {
            contractInfo = contractInfoMapper.selectContractInfo(req.getName(), req.getCtn());
            logger.debug("[X01] M전산 계약정보 조회 완료: ncn={}", contractInfo != null ? contractInfo.getNcn() : "null");
        } catch (Exception e) {
            logger.warn("[X01] M전산 계약정보 조회 실패: {}", e.getMessage());
            if (!mockWhenNoDb) {
                return SvcChgInfoResVO.fail("계약정보 조회에 실패했습니다.");
            }
        }

        String ncn;
        String custId;

        if (contractInfo != null && !isBlank(contractInfo.getNcn())) {
            ncn = contractInfo.getNcn();
            custId = contractInfo.getCustId();
        } else if (mockWhenNoDb) {
            // 로컬 개발용 Mock (DB 없는 경우)
            ncn = "100000001";
            custId = "MOCK001";
            logger.warn("[X01] M전산 계약정보 Mock 적용: ncn={}", ncn);
        } else {
            return SvcChgInfoResVO.fail("고객 정보를 찾을 수 없습니다. 고객명과 전화번호를 확인해 주세요.");
        }

        String clientIp = httpRequest.getRemoteAddr();

        // 2. Y04: 계약정보 유효성 체크 (휴대폰 인증)
        logger.debug("[Y04] 계약유효성 체크 Start: ncn={}, ctn={}", ncn, req.getCtn());
        boolean valdOk = mplatFormSvc.contractValdChk(clientIp, null, ncn, req.getCtn(), custId);
        if (!valdOk) {
            logger.warn("[Y04] 계약유효성 체크 실패: ncn={}", ncn);
            return SvcChgInfoResVO.fail("휴대폰 인증에 실패했습니다.");
        }
        logger.debug("[Y04] 계약유효성 체크 완료: ncn={}", ncn);

        // 3. X01: 가입정보 조회
        logger.debug("[X01] 가입정보 조회 Start: ncn={}", ncn);
        MpPerMyktfInfoVO x01 = mplatFormSvc.perMyktfInfo(ncn, req.getCtn(), custId);

        SvcChgInfoResVO res = new SvcChgInfoResVO();
        res.setSuccess(true);
        res.setNcn(ncn);
        res.setCtn(req.getCtn());
        res.setCustId(custId);
        res.setUserName(req.getName());
        if (x01 != null && x01.isSuccess()) {
            res.setEmail(x01.getEmail());
            res.setAddr(x01.getAddr());
            res.setHomeTel(x01.getHomeTel());
            res.setInitActivationDate(x01.getInitActivationDate());
        }
        logger.debug("[X01] 휴대폰 인증 완료: ncn={}, custId={}", ncn, custId);
        return res;
    }

    /**
     * X85 USIM 번호 유효성 체크 (공통).
     * ASIS: AppformController /msp/moscIntmMgmtAjax.do → MplatFormService.moscIntmMgmtSO(X85)
     */
    @Override
    public Map<String, Object> checkUsim(UsimCheckReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn()) || isBlank(req.getUsimNo())) {
            result.put("success", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, usimNo)");
            return result;
        }
        logger.debug("[X85] USIM 유효성 체크 Start: ncn={}, ctn={}, usimNo={}", req.getNcn(), req.getCtn(), req.getUsimNo());

        try {
            MpUsimCheckVO vo = mplatFormSvc.moscIntmMgmtSO(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getUsimNo());

            if (vo.isSuccess()) {
                logger.debug("[X85] USIM 유효성 체크 완료: usimSts={}, globalNo={}", vo.getUsimSts(), vo.getGlobalNo());
                result.put("success", true);
                result.put("resultCode", vo.getResultCode());
                result.put("globalNo", vo.getGlobalNo());
                result.put("usimNo", vo.getUsimNo());
                result.put("usimSts", vo.getUsimSts());
                result.put("usimStsCd", vo.getUsimStsCd());
                result.put("usimType", vo.getUsimType());
                result.put("message", "");
            } else {
                logger.warn("[X85] USIM 유효성 체크 실패: resultCode={}, msg={}", vo.getResultCode(), vo.getSvcMsg());
                result.put("success", false);
                result.put("resultCode", vo.getResultCode());
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("[X85] USIM 유효성 체크 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "USIM 유효성 확인 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * IF_0006 NICE 계좌인증 — 계좌번호 유효성 체크.
     * ASIS NiceCertifySvcImpl.checkNiceAccount() 와 동일 연동 구조.
     *
     * nice.ext.url 미설정(LOCAL) 시 Mock 성공 반환.
     * 운영: POST {nice.ext.url}/rlnmCheck.do → 응답 파이프 구분자 파싱 → results[1]="0000" 성공.
     *
     * 주요 파라미터:
     *   service=2(계좌성명확인), svcGbn=2, strGbn=1(개인),
     *   strBankCode, strAccountNo, strNm, strOrderNo(yyyyMMdd+난수10자리), inq_rsn=10
     */
    @Override
    public Map<String, Object> checkAccount(AccountCheckReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getBankCode()) || isBlank(req.getAccountNo())) {
            result.put("success", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (bankCode, accountNo)");
            return result;
        }

        // nice.ext.url 미설정 → LOCAL Mock 성공
        if (!StringUtils.hasText(niceExtUrl)) {
            logger.warn("nice.ext.url 미설정 — IF_0006 Mock 성공 반환");
            result.put("success", true);
            result.put("resultCode", "0000");
            result.put("message", "");
            return result;
        }

        try {
            // 중복 방지용 주문번호: yyyyMMdd + 10자리 난수 (ASIS 동일)
            String strOrderNo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + String.format("%010d", ThreadLocalRandom.current().nextLong(0, 10_000_000_000L));

            String service = isBlank(req.getService()) ? "2" : req.getService();
            String svcGbn  = isBlank(req.getSvcGbn())  ? "2" : req.getSvcGbn();

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("niceUid",     niceUid);
            body.add("svcPwd",      niceSvcPwd);
            body.add("service",     service);
            body.add("strGbn",      "1");           // 1: 개인
            body.add("strResId",    "");            // 주민번호 (service=2 불필요)
            body.add("strNm",       isBlank(req.getName()) ? "" : req.getName());
            body.add("strBankCode", req.getBankCode());
            body.add("strAccountNo",req.getAccountNo());
            body.add("svcGbn",      svcGbn);
            body.add("strOrderNo",  strOrderNo);
            body.add("svc_cls",     "");            // 내/외국인 구분 (기본 내국인)
            body.add("inq_rsn",     "10");          // 조회사유: 10=회원가입

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            logger.debug("*** IF_0006 NICE 계좌인증 Start: bankCode={}", req.getBankCode());
            String responseStr = restTemplate.postForObject(niceExtUrl + "/rlnmCheck.do", request, String.class);

            if (!StringUtils.hasText(responseStr)) {
                result.put("success", false);
                result.put("message", "계좌인증 응답이 없습니다.");
                return result;
            }

            // ASIS 동일: "|" 구분자, results[1] = resultCd
            String[] results = responseStr.split("\\|");
            String resultCd = (results.length > 1) ? results[1] : "";
            String resultMsg = (results.length > 2) ? results[2] : "";

            if ("0000".equals(resultCd)) {
                result.put("success", true);
                result.put("resultCode", resultCd);
                result.put("message", "");
            } else {
                // "0006": 특수 코드(OTP 등 추가 확인 필요), 그 외: 실패
                result.put("success", false);
                result.put("resultCode", "0006".equals(resultCd) ? resultCd : "0001");
                result.put("message", StringUtils.hasText(resultMsg) ? resultMsg : "계좌 유효성 확인에 실패했습니다.");
            }
        } catch (Exception e) {
            logger.error("IF_0006 NICE 계좌인증 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "계좌 유효성 확인 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * 청구계정ID(BAN) 조회.
     * ASIS MypageServiceImpl.selectBanSel() → MypageMapper.selectBanSel() 동일 구조.
     * SELECT BAN FROM MSP_JUO_SUB_INFO WHERE CONTRACT_NUM = #{contractNum}
     */
    @Override
    public Map<String, Object> lookupBillingAccount(String contractNum) {
        Map<String, Object> result = new HashMap<>();
        if (isBlank(contractNum)) {
            result.put("success", false);
            result.put("message", "계약번호(ncn)가 없습니다.");
            return result;
        }
        try {
            String ban = contractInfoMapper.selectBanByContractNum(contractNum);
            if (isBlank(ban)) {
                result.put("success", false);
                result.put("message", "청구계정ID를 조회할 수 없습니다.");
            } else {
                result.put("success", true);
                result.put("ban", ban);
                result.put("message", "");
            }
        } catch (Exception e) {
            logger.error("청구계정ID 조회 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "청구계정ID 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
