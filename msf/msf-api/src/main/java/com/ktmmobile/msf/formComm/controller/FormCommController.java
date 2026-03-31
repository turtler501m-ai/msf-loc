package com.ktmmobile.msf.formComm.controller;

import com.ktmmobile.msf.formComm.dto.AgencyShopResVO;
import com.ktmmobile.msf.formComm.dto.CardCheckReqDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formComm.service.FormCommSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 업무공통 컨트롤러 — 인터페이스 연계 없는 공통 기능.
 * M플랫폼/외부 API 연동이 없는 내부 로직·DB 조회 엔드포인트만 모음.
 * 인터페이스 연계 엔드포인트(Y04/X01/X85/NICE 등)는 SvcChgRestController 참조.
 *
 * 엔드포인트 목록:
 *   GET  /api/v1/comm/agencies         — 대리점/판매점 목록 (MSF_AGENT_SHOP_INFO)
 *   POST /api/v1/comm/card-check       — 카드번호 유효성 (서버사이드 Luhn 검증)
 *   POST /api/v1/comm/activation-date  — N7003 개통일자 (MSP_JUO_SUB_INFO@DL_MSP)
 *   POST /api/v1/comm/agent-code       — N7002 접점코드로 대리점 조회 (ORG_ORGN_INFO_MST@DL_MSP)
 */
@RestController
@RequestMapping("/api/v1/comm")
public class FormCommController {

    @Autowired
    private FormCommSvc formCommSvc;

    /**
     * 대리점/판매점 목록 조회.
     * 서비스변경·명의변경·서비스해지 전 업무 공용.
     * LOCAL: Mock 반환 / 운영: MSF_AGENT_SHOP_INFO 테이블 조회.
     * GET /api/v1/comm/agencies
     */
    @GetMapping("/agencies")
    public Map<String, Object> agencies() {
        List<AgencyShopResVO> agencies = formCommSvc.getAgencyList();
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("agencies", agencies);
        return result;
    }

    /**
     * IF_0007 카드번호 유효성 체크.
     * ASIS myNameChg.js checkCardNumber() Luhn Algorithm + 유효기간 검증을 서버사이드로 이전.
     * 외부 API 연동 없음 — 순수 서버사이드 형식 검증.
     * POST /api/v1/comm/card-check
     */
    @PostMapping("/card-check")
    public Map<String, Object> cardCheck(@RequestBody CardCheckReqDto req) {
        return formCommSvc.checkCard(req);
    }

    /**
     * N7003 개통일자 조회.
     * MSP_JUO_SUB_INFO@DL_MSP.LST_COM_ACTV_DATE 직접 조회.
     * 서비스변경·명의변경·서비스해지 전 업무 공용.
     * POST /api/v1/comm/activation-date
     */
    @PostMapping("/activation-date")
    public Map<String, Object> activationDate(@RequestBody SvcChgInfoDto req) {
        return formCommSvc.getActivationDate(req);
    }

    /**
     * N7002 접점코드로 대리점 코드·정보 조회.
     * ASIS: AppformMapper.xml selectAgentCode (ORG_ORGN_INFO_MST@DL_MSP).
     * 서비스변경·명의변경·서비스해지 전 업무 공용.
     * POST /api/v1/comm/agent-code
     */
    @PostMapping("/agent-code")
    public Map<String, Object> agentCode(@RequestBody Map<String, String> req) {
        Map<String, Object> result = new HashMap<>();
        String cntpntShopId = req.get("cntpntShopId");
        Map<String, Object> agentCode = formCommSvc.getAgentCode(cntpntShopId);
        result.put("success", agentCode != null);
        result.put("agentCode", agentCode);
        return result;
    }
}
