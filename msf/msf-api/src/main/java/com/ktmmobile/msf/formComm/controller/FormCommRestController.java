package com.ktmmobile.msf.formComm.controller;

import com.ktmmobile.msf.formComm.dto.FormSendReqDto;
import com.ktmmobile.msf.formComm.dto.FormSendResVO;
import com.ktmmobile.msf.formComm.service.FormCommRestSvc;
import org.springframework.web.bind.annotation.*;

/**
 * 업무공통 외부 인터페이스 연계 REST 컨트롤러.
 * SCAN 서버(포시에스), eFormSign 등 외부 시스템 연동 엔드포인트 모음.
 * 인터페이스 연계 없는 공통 기능은 FormCommController 참조.
 * ASIS: AppformController.sendScanAjax() / CustRequestScanServiceImpl.prodSendScan()
 *
 * POST /api/v1/comm/form-send
 *   reqType: NC(명의변경), CC(해지상담)
 */
@RestController
@RequestMapping("/api/v1/comm")
public class FormCommRestController {

    private final FormCommRestSvc formCommRestSvc;

    public FormCommRestController(FormCommRestSvc formCommRestSvc) {
        this.formCommRestSvc = formCommRestSvc;
    }

    @PostMapping("/form-send")
    public FormSendResVO formSend(@RequestBody FormSendReqDto req) {
        return formCommRestSvc.sendScan(req);
    }
}
