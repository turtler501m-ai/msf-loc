package com.ktmmobile.msf.formComm.controller;

import com.ktmmobile.msf.formComm.dto.FormSendReqDto;
import com.ktmmobile.msf.formComm.dto.FormSendResVO;
import com.ktmmobile.msf.formComm.service.FormSendSvc;
import org.springframework.web.bind.annotation.*;

/**
 * 서식지 SCAN 전송 Controller.
 * ASIS: AppformController.sendScanAjax() / CustRequestScanServiceImpl.prodSendScan()
 *
 * POST /api/v1/comm/form-send
 *   reqType: NC(명의변경), CC(해지상담)
 */
@RestController
@RequestMapping("/api/v1/comm")
public class FormSendController {

    private final FormSendSvc formSendSvc;

    public FormSendController(FormSendSvc formSendSvc) {
        this.formSendSvc = formSendSvc;
    }

    @PostMapping("/form-send")
    public FormSendResVO formSend(@RequestBody FormSendReqDto req) {
        return formSendSvc.sendScan(req);
    }
}
