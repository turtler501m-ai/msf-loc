package com.ktmmobile.msf.form.servicechange.controller;

import java.net.SocketTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ktmmobile.msf.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionAvailableResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionMyListResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionReqDto;
import com.ktmmobile.msf.form.servicechange.service.MsfRegSvcService;

// [ASIS] @Controller — Stateless REST 전환
// [ASIS] 세션/Model/HttpSession 의존 제거 — Stateless REST로 전환
// [ASIS] MsfMypageSvc, CertService, MsfMaskingSvc, IpStatisticService — ASIS 전용, TOBE에서 미사용

@RestController
@RequestMapping("/api/v1/addition")
public class MsfRegSvcController {

    @Autowired
    private MsfRegSvcService regSvcService;

    // =====================================================
    // TOBE API
    // =====================================================

    /**
     * 이용중 부가서비스 목록 조회 (X97)
     * ASIS: myAddSvcListAjax.do
     */
    @PostMapping("/my-list")
    public ResponseEntity<AdditionMyListResVO> myAddSvcList(@RequestBody AdditionReqDto req) {
        return ResponseEntity.ok(regSvcService.getMyAddSvcList(req));
    }

    /**
     * 가입가능 부가서비스 목록 조회 (X97 + DB)
     * ASIS: addSvcListAjax.do
     */
    @PostMapping("/available-list")
    public ResponseEntity<AdditionAvailableResVO> availableAddSvcList(@RequestBody AdditionReqDto req) {
        return ResponseEntity.ok(regSvcService.getAvailableAddSvcList(req));
    }

    /**
     * 부가서비스 해지 (X38)
     * ASIS: moscRegSvcCanChgAjax.do
     */
    @PostMapping("/cancel")
    public ResponseEntity<AdditionApplyResVO> cancelAddSvc(@RequestBody AdditionApplyReqDto req)
            throws SocketTimeoutException {
        return ResponseEntity.ok(regSvcService.cancelAddSvc(req));
    }

    /**
     * 부가서비스 신청 (Y25)
     * ASIS: regSvcChgAjax.do
     */
    @PostMapping("/reg")
    public ResponseEntity<AdditionApplyResVO> regAddSvc(@RequestBody AdditionApplyReqDto req)
            throws SocketTimeoutException {
        return ResponseEntity.ok(regSvcService.regAddSvc(req));
    }

    // =====================================================
    // [ASIS] 기존 메서드 — TOBE 전환으로 주석 처리
    // =====================================================

    // [ASIS] View 진입 — join-info 기구현으로 대체
    // @RequestMapping(value = { "/mypage/regServiceView.do", "/m/mypage/regServiceView.do" })
    // public String regServiceView(HttpServletRequest request, ModelMap model,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO) { ... }

    // [ASIS] 이용중 부가서비스 목록 — myAddSvcList()로 대체
    // @RequestMapping(value = { "/mypage/myAddSvcListAjax.do", "/m/mypage/myAddSvcListAjax.do" })
    // @ResponseBody
    // public Map<String, Object> myAddSvcListAjax(HttpServletRequest request, Model model,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO) { ... }

    // [ASIS] 가입가능 부가서비스 목록 — availableAddSvcList()로 대체
    // @RequestMapping(value = { "/mypage/addSvcListAjax.do", "/m/mypage/addSvcListAjax.do" })
    // @ResponseBody
    // public HashMap<String, Object> addSvcListAjax(HttpServletRequest request, Model model,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO) { ... }

    // [ASIS] 팝업 View — 프론트 처리로 대체
    // @RequestMapping(value = { "/mypage/addSvcViewPop.do", "/m/mypage/addSvcViewPop.do" })
    // public String addSvcViewPop(...) { ... }

    // [ASIS] 부가서비스 해지 — cancelAddSvc()로 대체
    // @RequestMapping(value = { "/mypage/moscRegSvcCanChgAjax.do", "/m/mypage/moscRegSvcCanChgAjax.do" })
    // @ResponseBody
    // public Map<String, Object> moscRegSvcCanChgAjax(HttpServletRequest request, Model model,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO,
    //         @RequestParam(value = "rateAdsvcCd", required = true) String rateAdsvcCd) throws SocketTimeoutException { ... }

    // [ASIS] 부가서비스 신청 — regAddSvc()로 대체
    // @RequestMapping(value = { "/mypage/regSvcChgAjax.do", "/m/mypage/regSvcChgAjax.do" })
    // @ResponseBody
    // public JsonReturnDto regSvcChgAjax(HttpServletRequest request, ModelMap model,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO, String soc, String ftrNewParam,
    //         String ctnVal, String couoponPrice, String flag) throws SocketTimeoutException { ... }

    // [ASIS] 로밍 관련 메서드 다수 — 로밍 부가서비스 TOBE 미이관
    // roamingView.do / myRoamJoinListAjax.do / regRoamListAjax.do / roamingCanChgAjax.do / roamingViewPop.do
}
