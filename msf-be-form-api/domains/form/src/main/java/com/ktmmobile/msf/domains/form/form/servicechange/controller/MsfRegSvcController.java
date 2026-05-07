package com.ktmmobile.msf.domains.form.form.servicechange.controller;

import java.net.SocketTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionAvailableResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionMyListResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfRegSvcServiceImpl;

@RestController
public class MsfRegSvcController {

    @Autowired
    private MsfRegSvcServiceImpl regSvcService;

    /**
     * 이용중 부가서비스 목록 조회
     *
     * M플랫폼 X97(가입중인 부가서비스 조회) 호출 후
     * MSP_RATE_MST@DL_MSP에서 해지 가능 여부(onlineCanYn) / 해지 안내 문구(canCmnt) 세팅.
     * "PL249Q800"(아무나SOLO 더미 SOC) 자동 제거.
     *
     * ASIS: POST /mypage/myAddSvcListAjax.do (X97 기반, 세션 검증 포함)
     * TOBE: POST /api/v1/addition/my-list (Stateless, 요청 바디에서 ncn/ctn/custId 수신)
     *
     * @param req ncn(계약번호), ctn(전화번호), custId(고객번호)
     * @return 이용중 부가서비스 목록 (AdditionMyListResVO.list)
     */

    @PostMapping("/api/form/servicechange/myaddsvclist")
    public CommonResponse<FormResponse<AdditionMyListResVO>> myAddSvcList(@RequestBody AdditionReqDto req) {
        return ResponseUtils.ok(regSvcService.myAddSvcList(req));
    }

    /**
     * 가입가능 부가서비스 목록 조회
     *
     * M플랫폼 X97로 현재 이용중인 SOC 목록을 추출하고,
     * MSF DB(MSF_REG_SVC_MST 등)에서 관리 중인 전체 부가서비스와 비교하여 useYn 세팅.
     * listA(유료) / listC(무료 및 번들) 분리 반환.
     *
     * ASIS: POST /mypage/addSvcListAjax.do (X20 기반 → TOBE에서 X97로 교체)
     * TOBE: POST /api/v1/addition/available-list
     *
     * @param req ncn(계약번호), ctn(전화번호), custId(고객번호)
     * @return 가입가능 부가서비스 목록 (list/listA/listC)
     */
    @PostMapping("/api/v1/addition/available-list")
    public CommonResponse<FormResponse<AdditionAvailableResVO>> addSvcList(@RequestBody AdditionReqDto req) {
        return ResponseUtils.ok(regSvcService.selectAddSvcInfoDto(req));
    }

    /**
     * 부가서비스 해지
     *
     * MSP_RATE_MST@DL_MSP에서 온라인 해지 가능 여부 사전 검증 후 M플랫폼 X38 호출.
     * prodHstSeq가 있으면 moscRegSvcCanChgSeq (이력 기반 해지),
     * 없으면 moscRegSvcCanChg (단순 해지).
     *
     * ASIS: POST /mypage/moscRegSvcCanChgAjax.do (세션 기반 사용자 검증 포함)
     * TOBE: POST /api/v1/addition/cancel (Stateless, 요청 바디에서 직접 수신)
     *
     * @param req ncn/ctn/custId/soc(SOC코드)/prodHstSeq(상품이력번호, 선택)
     * @return 해지 결과 (success/message)
     */
    @PostMapping("/api/v1/addition/cancel")
    public CommonResponse<FormResponse<AdditionApplyResVO>> moscRegSvcCanChg(@RequestBody AdditionApplyReqDto req)
            throws SocketTimeoutException {
        return ResponseUtils.ok(regSvcService.moscRegSvcCanChg(req));
    }

    /**
     * 부가서비스 신청
     *
     * flag="Y"이면 선해지 후 신청(변경), flag≠"Y"이면 바로 신청.
     * M플랫폼 Y25(상품변경처리 multi) 호출. ASIS X21에서 Y25로 교체.
     *
     * ASIS: POST /mypage/regSvcChgAjax.do (인증 STEP 검증·포인트 처리 포함)
     * TOBE: POST /api/v1/addition/reg (STEP 검증·포인트 처리 미이관 → 주석 처리)
     *
     * @param req ncn/ctn/custId/soc(SOC코드)/ftrNewParam(부가정보)/flag("Y"=변경)
     * @return 신청 결과 (success/message)
     */
    @PostMapping("/api/v1/addition/reg")
    public CommonResponse<FormResponse<AdditionApplyResVO>> regSvcChg(@RequestBody AdditionApplyReqDto req)
            throws SocketTimeoutException {
        return ResponseUtils.ok(regSvcService.regSvcChg(req));
    }

    // =====================================================
    // [ASIS] 기존 메서드 — TOBE 전환으로 주석 처리 (삭제 금지)
    // =====================================================

    // [ASIS] View 진입 — join-info 기구현으로 대체 (프론트 진입점 불필요)
    // @RequestMapping(value = { "/mypage/regServiceView.do", "/m/mypage/regServiceView.do" })
    // public String regServiceView(HttpServletRequest request, ModelMap model,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO) { ... }

    // [ASIS] 이용중 부가서비스 목록 — myAddSvcList() (ASIS: myAddSvcListAjax — "Ajax" 제거)로 대체
    // @RequestMapping(value = { "/mypage/myAddSvcListAjax.do", "/m/mypage/myAddSvcListAjax.do" })
    // @ResponseBody
    // public Map<String, Object> myAddSvcListAjax(HttpServletRequest request, Model model,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO) { ... }

    // [ASIS] 가입가능 부가서비스 목록 — availableAddSvcList()로 대체
    // @RequestMapping(value = { "/mypage/addSvcListAjax.do", "/m/mypage/addSvcListAjax.do" })
    // @ResponseBody
    // public HashMap<String, Object> addSvcListAjax(HttpServletRequest request, Model model,
    //         @ModelAttribute("searchVO") MyPageSearchDto searchVO) { ... }

    // [ASIS] 팝업 View — 프론트 처리로 대체 (서버 사이드 View 불필요)
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

    // [ASIS] 로밍 관련 메서드 다수 — 로밍 부가서비스 TOBE 미이관 (추후 구현)
    //   roamingView.do / myRoamJoinListAjax.do / regRoamListAjax.do
    //   roamingCanChgAjax.do / roamingViewPop.do
}
