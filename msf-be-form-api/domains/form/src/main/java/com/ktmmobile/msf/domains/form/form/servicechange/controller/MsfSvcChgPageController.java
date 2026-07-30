package com.ktmmobile.msf.domains.form.form.servicechange.controller;


import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChangInfoViewResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ImageSystemUploadReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ScanIdUpdateReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfSvcChgPageServiceImpl;


@Slf4j
@RestController
public class MsfSvcChgPageController {

    @Autowired
    private MsfSvcChgPageServiceImpl msfSvcChgPageServiceImpl;

    /**
     * 서비스변경 화면에서 사용할 가입정보 데이터를 조회한다.
     */
    @PostMapping(value = "/api/msf/formServiceChange/changinfo/view")
    public CommonResponse<FormResponse<ChangInfoViewResDto>> getChangInfoView(
        HttpServletRequest request,
        @RequestBody MyPageSearchDto searchVO
    ) {
        return ResponseUtils.ok(msfSvcChgPageServiceImpl.getChangInfoView(request, searchVO));
    }

    /**
     * 서비스변경 신청서키 사전 채번
     *
     * 고객 정보 입력 완료(다음 버튼) 시점에 eform에 전달할 requestKey를 미리 발급한다.
     * 서비스해지의 잔여요금 조회 시 requestKey를 발급하는 패턴과 동일.
     *
     * TOBE: POST /api/msf/formServiceChange/requestKey/get
     *
     * @return requestKey (신청서키)
     */
    @PostMapping("/api/msf/formServiceChange/requestKey/get")
    public CommonResponse<FormResponse<ServiceChangeCompleteResVO>> getRequestKey() {
        return ResponseUtils.ok(msfSvcChgPageServiceImpl.generateRequestKey());
    }

    /**
     * 서비스변경 신청서 최종 저장
     *
     * 선택된 서비스 변경 항목(요금제·부가서비스·번호변경 등)을
     * MSF DB(MSF_REQUEST_SVC_CHG / MSF_REQUEST_CSTMR / MSF_REQUEST_SVC_CHG_DTL)에 저장.
     *
     * ASIS: 별도 저장 로직 없음 (M포탈 자체 처리)
     * TOBE: POST /api/msf/formServiceChange/complete
     *
     * @param req 서비스변경 신청 전체 정보
     * @return 저장 결과 (success/requestKey)
     */
    @PostMapping("/api/msf/formServiceChange/complete")
    public CommonResponse<FormResponse<ServiceChangeCompleteResVO>> complete(
        @RequestBody ServiceChangeCompleteReqDto req
    ) throws IOException {
        FormResponse<ServiceChangeCompleteResVO> result = msfSvcChgPageServiceImpl.complete(req);
        if (result != null && !"0000".equals(result.resCode())) {
            return CommonResponse.of(result.resCode(), result.resMessage(), result);
        }
        return ResponseUtils.ok(result);
    }

    /**
     * 이폼서버 업로드 완료 후 SCAN_ID 후처리 업데이트 (서비스변경 전용)
     *
     * TOBE: POST /api/msf/formServiceChange/scanId/modify
     *
     * @param req requestKey + documentId 목록 + serviceSelect
     */
    @PostMapping("/api/msf/formServiceChange/scanId/modify")
    public CommonResponse<FormResponse<Void>> updateScanId(
        @RequestBody ScanIdUpdateReqDto req
    ) {
        return ResponseUtils.ok(msfSvcChgPageServiceImpl.updateScanId(req));
    }

    /** 서비스변경 신청서 및 구비서류 이미징 시스템 업로드 */
    @PostMapping("/api/msf/formServiceChange/imageSystem/upload")
    public CommonResponse<FormResponse<Void>> uploadImageSystem(
        @RequestBody ImageSystemUploadReqDto req
    ) {
        return ResponseUtils.ok(msfSvcChgPageServiceImpl.uploadImageSystem(req));
    }

}
