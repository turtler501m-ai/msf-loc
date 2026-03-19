package com.ktmmobile.msf.formComm.controller;

import com.ktmmobile.msf.formComm.dto.JoinInfoReqDto;
import com.ktmmobile.msf.formComm.dto.JoinInfoResVO;
import com.ktmmobile.msf.formComm.service.JoinInfoSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 서비스변경 공통 REST 컨트롤러. 가입자정보조회 엔드포인트 제공.
 * /api/v1/join-info, /api/v1/{domain}/join-info 모두 처리 (내부 로직 동일).
 */
@RestController
@RequestMapping("/api/v1")
public class SvcChgRestController {

    @Autowired
    private JoinInfoSvc joinInfoSvc;

    /**
     * 가입자정보조회 (서비스변경).
     * POST /api/v1/join-info
     */
    @PostMapping("/join-info")
    public JoinInfoResVO joinInfo(@RequestBody JoinInfoReqDto req, HttpServletRequest httpRequest) {
        return joinInfoSvc.joinInfo(req, httpRequest);
    }

    /**
     * 가입자정보조회 (명의변경).
     * POST /api/v1/ident/join-info
     */
    @PostMapping("/ident/join-info")
    public JoinInfoResVO identJoinInfo(@RequestBody JoinInfoReqDto req, HttpServletRequest httpRequest) {
        return joinInfoSvc.joinInfo(req, httpRequest);
    }

    /**
     * 가입자정보조회 (서비스해지).
     * POST /api/v1/cancel/join-info
     */
    @PostMapping("/cancel/join-info")
    public JoinInfoResVO cancelJoinInfo(@RequestBody JoinInfoReqDto req, HttpServletRequest httpRequest) {
        return joinInfoSvc.joinInfo(req, httpRequest);
    }

    /**
     * 가입자정보조회 (부가서비스).
     * POST /api/v1/addition/join-info
     */
    @PostMapping("/addition/join-info")
    public JoinInfoResVO additionJoinInfo(@RequestBody JoinInfoReqDto req, HttpServletRequest httpRequest) {
        return joinInfoSvc.joinInfo(req, httpRequest);
    }
}
