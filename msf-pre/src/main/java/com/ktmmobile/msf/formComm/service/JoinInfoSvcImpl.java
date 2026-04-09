package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.formComm.dto.ContractInfoDto;
import com.ktmmobile.msf.formComm.dto.JoinInfoReqDto;
import com.ktmmobile.msf.formComm.dto.JoinInfoResVO;
import com.ktmmobile.msf.formComm.mapper.ContractInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 가입자정보조회 서비스 구현.
 * ASIS 흐름: M전산(계약정보) → Y04(인증) → X01(가입정보) 동일.
 */
@Service
public class JoinInfoSvcImpl implements JoinInfoSvc {

    private static final Logger logger = LoggerFactory.getLogger(JoinInfoSvcImpl.class);

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    @Autowired
    private MplatFormSvc mplatFormSvc;

    /** 로컬 개발 시 DB 조회 실패 허용 여부 */
    @Value("${msf.join-info.mock-when-no-db:false}")
    private boolean mockWhenNoDb;

    @Override
    public JoinInfoResVO joinInfo(JoinInfoReqDto req, HttpServletRequest httpRequest) {
        if (req == null || isBlank(req.getName()) || isBlank(req.getCtn())) {
            return JoinInfoResVO.fail("고객명과 전화번호를 입력해 주세요.");
        }

        // 1. M전산 DB: 고객명+전화번호 → ncn, custId
        ContractInfoDto contractInfo = null;
        try {
            contractInfo = contractInfoMapper.selectContractInfo(req.getName(), req.getCtn());
        } catch (Exception e) {
            logger.warn("M전산 계약정보 조회 실패: {}", e.getMessage());
            if (!mockWhenNoDb) {
                return JoinInfoResVO.fail("계약정보 조회에 실패했습니다.");
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
            logger.warn("M전산 계약정보 Mock 적용: ncn={}", ncn);
        } else {
            return JoinInfoResVO.fail("고객 정보를 찾을 수 없습니다. 고객명과 전화번호를 확인해 주세요.");
        }

        String clientIp = httpRequest.getRemoteAddr();

        // 2. Y04: 계약정보 유효성 체크 (휴대폰 인증)
        boolean valdOk = mplatFormSvc.contractValdChk(clientIp, null, ncn, req.getCtn(), custId);
        if (!valdOk) {
            return JoinInfoResVO.fail("휴대폰 인증에 실패했습니다.");
        }

        // 3. X01: 가입정보 조회
        MpPerMyktfInfoVO x01 = mplatFormSvc.perMyktfInfo(ncn, req.getCtn(), custId);

        JoinInfoResVO res = new JoinInfoResVO();
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
        return res;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
