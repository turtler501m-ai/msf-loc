package com.ktmmobile.msf.domains.form.form.servicechange.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.DataSharingReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.DataSharingResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfSvcMyShareDataSvcImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsfSvcMyShareDataController {

    @Autowired
    private MsfSvcMyShareDataSvcImpl myShareDataSvc;



    // 서비스변경 화면에서 데이터 쉐어링 가입 상태를 조회한다.
    @RequestMapping("/api/msf/formServiceChange/dataSharingStep2")
    public CommonResponse<FormResponse<DataSharingResDto>> dataSharingList(
        @RequestBody DataSharingReqDto req) {
        return ResponseUtils.ok(myShareDataSvc.dataSharingStep2(req));
    }


    // 데이터 쉐어링 신청 가능 여부를 JSON API 응답 형식으로 반환한다.
    @RequestMapping("/api/msf/formServiceChange/dataSharingCheckAjax")
    public CommonResponse<FormResponse<DataSharingResDto>> doDataSharingCheckAjax(
        @RequestBody DataSharingReqDto req) {
        return ResponseUtils.ok(myShareDataSvc.dataSharingCheck(req));
    }




    // 데이터 쉐어링 진입 전 로그인 여부를 확인하고 인증 후 화면으로 이동할 URL을 반환한다.
    @RequestMapping(value = { "/mySharingCntrInfo.do", "/m/mySharingCntrInfo.do" })
    public Map<String, Object> doMySharingCntrInfo(HttpServletRequest request) {
        return myShareDataSvc.doMySharingCntrInfo(request);
    }

    // 데이터 쉐어링 회선 현황과 가입 가능 여부를 조회한다.
    // @RequestMapping(value = { "/content/mySharingView.do", "/m/content/mySharingView.do" })
    // public Map<String, Object> doMySharingView(
    //         HttpServletRequest request,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO,
    //         @RequestParam(value = "menuType", required = false) String menuType,
    //         @RequestParam(value = "phoneNum", required = false) String phoneNum,
    //         HttpSession session) {
    //     return myShareDataSvc.doMySharingView(request, searchVO, menuType, phoneNum, session);
    // }

    // // 신규 데이터 쉐어링 신청의 첫 단계로 비회원 세션을 초기화하고 다음 화면 정보를 반환한다.
    // @RequestMapping(value = { "/content/dataSharingStep1.do", "/m/content/dataSharingStep1.do" })
    // public Map<String, Object> dataSharingStep1() {
    //     return myShareDataSvc.dataSharingStep1();
    // }
    //
    // // 신규 데이터 쉐어링 신청 대상 회선과 고객 유형을 조회한다.
    // @RequestMapping(value = { "/content/dataSharingStep2.do", "/m/content/dataSharingStep2.do" })
    // public Map<String, Object> dataSharingStep2(
    //         HttpServletRequest request,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO,
    //         @RequestParam(value = "menuType", required = false) String menuType,
    //         @RequestParam(value = "phoneNum", required = false) String phoneNum,
    //         HttpSession session) {
    //     return myShareDataSvc.dataSharingStep2(request, searchVO, menuType, phoneNum, session);
    // }
    //
    // // 선택한 회선의 개통 사전 검증과 신청 화면 진입 정보를 조회한다.
    // @RequestMapping(value = { "/content/dataSharingStep3.do", "/m/content/dataSharingStep3.do" })
    // public Map<String, Object> dataSharingStep3(
    //         HttpServletRequest request,
    //         @RequestParam(value = "contractNum", required = false) String contractNum,
    //         @RequestParam(value = "onOffType", required = false) String onOffType,
    //         @RequestParam(value = "cstmrType", required = true) String cstmrType,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO,
    //         HttpSession session) {
    //     return myShareDataSvc.dataSharingStep3(request, contractNum, onOffType, cstmrType, searchVO, session);
    // }
    //
    // // 데이터 쉐어링 개통 신청 전 최종 확인 정보를 조회한다.
    // @RequestMapping(value = { "/content/dataSharingStep4.do", "/m/content/dataSharingStep4.do" })
    // public Map<String, Object> dataSharingStep4(
    //         HttpServletRequest request,
    //         @ModelAttribute("myShareDataReqDto") MyShareDataReqDto myShareDataReqDto,
    //         HttpSession session) {
    //     return myShareDataSvc.dataSharingStep4(request, myShareDataReqDto, session);
    // }

    // TOBESKIP: 사전 체크 Ajax는 사용하지 않아 URL 매핑만 막고 원본 로직은 보존한다.
    // @RequestMapping(value = { "/content/preOpenCheckAjax.do", "/m/content/preOpenCheckAjax.do" })
    // @Deprecated
    // public JsonReturnDto domyCntrListAjax(
    //         HttpServletRequest request,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO,
    //         @RequestParam(value = "contractNum", required = false) String contractNum,
    //         HttpSession session) {
    //     return myShareDataSvc.domyCntrListAjax(request, searchVO, contractNum, session);
    // }

    // 데이터 쉐어링 신청 화면 진입 시 본인 인증 상태와 대상 회선을 검증한다.
    // @RequestMapping(value = { "/content/reqSharingView.do", "/m/content/reqSharingView.do" })
    // public Map<String, Object> dorReqSharingView(
    //         HttpServletRequest request,
    //         @RequestParam(value = "contractNum", required = false) String contractNum,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO,
    //         HttpSession session) {
    //     return myShareDataSvc.dorReqSharingView(request, contractNum, searchVO, session);
    // }

    // 데이터 쉐어링 개통을 신청하고 처리 결과를 반환한다.
    // 서비스변경 화면의 최종 가입/해지는 /api/msf/formServiceChange/complete에서 처리한다.
    // @RequestMapping(value = { "/content/insertOpenRequestAjax.do", "/m/content/insertOpenRequestAjax.do" })
    // public HashMap<String, Object> doinsertOpenRequestAjax(
    //         HttpServletRequest request,
    //         @ModelAttribute("myShareDataReqDto") MyShareDataReqDto myShareDataReqDto,
    //         HttpSession session) {
    //     return myShareDataSvc.doinsertOpenRequestAjax(request, myShareDataReqDto, session);
    // }

    // 데이터 쉐어링 개통 완료 화면에 필요한 결과 정보를 조회한다.
    // @RequestMapping(value = { "/content/reqSharingCompleteView.do", "/m/content/reqSharingCompleteView.do" })
    // public Map<String, Object> doReqSharingCompleteView(
    //         HttpServletRequest request,
    //         @ModelAttribute("myShareDataReqDto") MyShareDataReqDto myShareDataReqDto,
    //         HttpSession session) {
    //     return myShareDataSvc.doReqSharingCompleteView(request, myShareDataReqDto, session);
    // }

}
