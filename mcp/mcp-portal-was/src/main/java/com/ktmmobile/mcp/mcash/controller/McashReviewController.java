package com.ktmmobile.mcp.mcash.controller;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mcash.dto.McashUserDto;
import com.ktmmobile.mcp.mcash.service.McashService;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.review.dto.ReviewDto;
import com.ktmmobile.mcp.review.dto.ReviewImgDto;
import com.ktmmobile.mcp.review.service.ReviewService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.common.constants.Constants.*;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;
import static com.ktmmobile.mcp.review.util.ReviewUtil.validateImgFile;

@Controller
public class McashReviewController {

    private static Logger logger = LoggerFactory.getLogger(McashReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private McashService mcashService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private FormDtlSvc formDtlSvc;

    @Autowired
    private IpStatisticService ipStatisticService;

    @Autowired
    private CertService certService;

    /**
     * 설명 : M쇼핑할인 사용 후기 화면
     * @Date 2024.12.02
     * @param reviewDto
     * @param model
     * @return
     */
    @RequestMapping(value = {"/mcash/review/mcashReview.do", "/m/mcash/review/mcashReview.do"})
    public String mcashReview (@ModelAttribute ReviewDto reviewDto, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO, HttpServletRequest request) {
        String returnUrl = "/portal/review/mcashReview";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/review/mcashReview";
        }

        String checkUserType = "N"; // 정회원 여부(Y/N)
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        if (userSession != null && !StringUtils.isEmpty(userSession.getUserId())) {
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
            if (chk) {
                checkUserType = "Y";
            }
        }
        model.addAttribute("checkUserType", checkUserType);

        //메인 사용후기에서 타고 들어올경우
        String reviewMoveVal =  request.getParameter("choice");

        if(reviewMoveVal != null) {
            try {
                int reviewVal = Integer.parseInt(reviewMoveVal)+1;
                model.addAttribute("reviewVal", reviewVal);
            } catch (ArithmeticException e) {
                logger.error("ArithmeticException e : {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        return returnUrl;
    }

    /**
     * 설명 : M쇼핑할인 후기 작성 화면
     * @Date 2024.12.04
     * @param reviewDto
     * @param model
     * @return
     */
    @RequestMapping(value = {"/mcash/review/mcashReviewForm.do", "/m/mcash/review/mcashReviewForm.do"})
    public String mcashReviewForm(@ModelAttribute ReviewDto reviewDto, Model model) {

        String returnUrl = "/portal/review/mcashReviewForm";
        String redirectUrl = "/mcash/review/mcashReview.do";
        String loginUrl = "/loginForm.do";
//        String loginUrl = "/loginForm.do?redirectUrl=";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/review/mcashReviewForm";
            redirectUrl = "/m/mcash/review/mcashReview.do";
            loginUrl = "/m/loginForm.do";
//                loginUrl = "/m/loginForm.do?redirectUrl=";
        }
//        loginUrl += URLEncoder.encode(returnUrl, StandardCharsets.UTF_8);

        // 로그인 여부
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonException("M쇼핑할인 사용후기는 로그인 후 작성 가능합니다.", loginUrl);
        }

        // M쇼핑할인 가입 확인
        McashUserDto mcashUserDto = mcashService.getMcashUserByUserid(userSession.getUserId());
        if (mcashUserDto == null || !MCASH_USER_STATUS_ACTIVE.equals(mcashUserDto.getStatus())) {
            throw new McpCommonException("M쇼핑할인에 가입해야<br/>사용후기 작성이 가능합니다.<br/>M쇼핑할인 가입을 부탁드립니다.", redirectUrl);
        }

        // 계약번호로 전화번호, 이름 조회
        String phone = "";
        McpUserCntrMngDto cntrMngDto = mypageService.getCntrInfoByContNum(mcashUserDto.getContractNum());
        if (cntrMngDto != null) {
            phone = cntrMngDto.getMkMobileNo();
        }

        ReviewDto checkDto = new ReviewDto();
        checkDto.setContractNum(mcashUserDto.getContractNum());
        int total = reviewService.selectReviewTotalCnt(checkDto);
        if (total > 0) {
            throw new McpCommonException("하나의 사용후기만 작성할 수 있습니다.", redirectUrl);
        }

        // 이벤트 코드
        List<NmcpCdDtlDto> eventCdList = NmcpServiceUtils.getCodeList(MCASH_REVIEW_EVENT);
        List<NmcpCdDtlDto> eventList = eventCdList.stream()
                                            .filter(eventCd -> "Y".equals(eventCd.getExpnsnStrVal1()))
                                            .collect(Collectors.toList());

        FormDtlDTO formDtlDTO = new FormDtlDTO();
        formDtlDTO.setCdGroupId1("INFOPRMT");
        formDtlDTO.setCdGroupId2("INSTRUCTION003");
        FormDtlDTO eventHolder = formDtlSvc.FormDtlView(formDtlDTO);

        model.addAttribute("menuType", "mcashReviewReg");
        model.addAttribute("eventList", eventList);
        model.addAttribute("eventHolder", eventHolder);
        model.addAttribute("phone", phone);
        model.addAttribute("cntrNum", mcashUserDto.getContractNum());
        return returnUrl;
    }

    /**
     * 설명 : M쇼핑할인 사용 후기 등록하기 Ajax
     * @Date 2024.12.11
     * @param request
     * @param reviewDto
     * @param reviewImgDto
     * @param isLogin
     * @return
     */
    @RequestMapping(value = "/mcash/review/regstReviewAjax.do")
    @ResponseBody
    public Map<String, Object> regstReviewAjax(HttpServletRequest request, @ModelAttribute ReviewDto reviewDto,
                                               @ModelAttribute ReviewImgDto reviewImgDto) {
        Map<String, Object> rtnMap = new HashMap<>();

        // 1. session check
        boolean isCheck = false;
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("0001", NO_FRONT_SESSION_EXCEPTION);
        }

        // 로그인 회원일시 계약번호로 전화번호 가져오기
        if (!StringUtils.isBlank(reviewDto.getContractNum())) {
            McpUserCntrMngDto cntrMngDto = mypageService.getCntrInfoByContNum(reviewDto.getContractNum());
            if (cntrMngDto != null) {
                reviewDto.setPhone(cntrMngDto.getCntrMobileNo());
            } else {
                throw new McpCommonJsonException("0002", NOT_FOUND_DATA_EXCEPTION);
            }
        }

        // 2.인증 받고 넘어왔는지 확인
        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setMenu("mcashReviewReg");
        authSmsDto = SessionUtils.getAuthSmsBean(authSmsDto);

        String phone = reviewDto.getPhone();
        String name = reviewDto.getName();
        if (authSmsDto == null) {
            throw new McpCommonJsonException("0003", "SMS인증 후 등록가능합니다.");
        }

        boolean result = authSmsDto.isResult();
        String smsPhone = StringUtil.NVL(authSmsDto.getPhoneNum(), "");
        if(!result || (!smsPhone.equals(phone))){
            throw new McpCommonJsonException("0003", "SMS인증 후 등록가능합니다.");
        }

        // 3. userId로 가져온 회선과 M쇼핑할인 사용자 회선 정보 비교
        String contractNum = "";

        String userId = userSession.getUserId();
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userId);
        McashUserDto mcashUserDto = mcashService.getMcashUserByUserid(userId);
        if (mcashUserDto == null || StringUtils.isEmpty(mcashUserDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("0004", "M쇼핑할인 사용자만 사용 후기를 등록할 수 있습니다.");
        }
        reviewDto.setUserId(userId);
        reviewDto.setRegstId(userId);
        reviewDto.setRegstNm(userSession.getName());
        reviewDto.setrIp(ipStatisticService.getClientIp());
        reviewImgDto.setRegstId(userId);

        for (McpUserCntrMngDto cntr : cntrList) {
            if (cntr.getSvcCntrNo().equals(mcashUserDto.getSvcCntrNo())) {
                contractNum = cntr.getContractNum();
            }
        }
        if (StringUtils.isEmpty(contractNum)) {
            throw new McpCommonJsonException("0005", "M쇼핑할인 사용자만 사용 후기를 등록할 수 있습니다.");
        }

        ReviewDto checkReviewDto = new ReviewDto();
        checkReviewDto.setContractNum(mcashUserDto.getContractNum());
        int total = reviewService.selectReviewTotalCnt(checkReviewDto);
        if (total > 0) {
            throw new McpCommonJsonException("0006", "하나의 사용후기만 작성할 수 있습니다.");
        }

        //============ STEP START ============
        // 1. 최소 스텝 수 확인
        if(certService.getStepCnt() < 2 ){
            throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
        }

        // 2. 최종 데이터 확인: 스텝종료여부, 이름, 핸드폰번호, 계약번호
        String[] certKey = {"urlType", "stepEndYn", "name", "mobileNo", "contractNum"};
        String[] certValue = {"mcashReview", "Y", name, phone, contractNum};
        Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))){
            throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
        }
        //============ STEP END ============

        // 4. 파일 형식과 용량
        boolean checkFile = true;
        if (Optional.ofNullable(reviewImgDto.getFile()).isPresent()) {
            MultipartFile[] arrFile = reviewImgDto.getFile();
            if (arrFile != null && arrFile.length > 0) {
                for (MultipartFile file : arrFile) {
                    if (!validateImgFile(file)) {
                        checkFile = false;
                        break;
                    }
                }
                // 파일 유효성 검사 실패
                if (!checkFile) {
                    throw new McpCommonJsonException("0007", "이미지 파일 형식(jpg, jpeg, png, bmp, gif)이나 크기(2MB 이하)가 유효하지 않습니다.");
                }
            }
        }
        // 5. 후기 등록
        try {
            reviewService.insertReviewAndImage(reviewDto, reviewImgDto);
        } catch (Exception e) {
            logger.error("exception e=>"+e.getMessage());
            throw new McpCommonJsonException("0008", DB_EXCEPTION);
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    /**
     * 설명 : M쇼핑할인 가입 여부
     * @Date 2024.12.04
     * @return
     */
    @RequestMapping(value = "/mcash/review/checkWriteAuthAjax.do")
    @ResponseBody
    public Map<String, Object> checkWriteAuthAjax() {
        Map<String, Object> rtnMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("0001", NO_SESSION_EXCEPTION);
        }

        McashUserDto mcashUserDto = mcashService.getMcashUserByUserid(userSession.getUserId());
        if (mcashUserDto == null || !MCASH_USER_STATUS_ACTIVE.equals(mcashUserDto.getStatus())) {
            throw new McpCommonJsonException("0002", "M쇼핑할인 사용자만 사용 후기를 작성할 수 있습니다.");
        }

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setContractNum(mcashUserDto.getContractNum());
        int total = reviewService.selectReviewTotalCnt(reviewDto);
        if (total > 0) {
            throw new McpCommonJsonException("0003", "하나의 사용후기만 작성할 수 있습니다.");
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    /**
     * 설명 : 사용 후기 sms 인증 팝업 호출Ajax
     * @Date 20214.12.19
     * @param request
     * @param model
     * @param menuType
     * @param popType
     * @param buttonType
     * @return
     */
    @RequestMapping(value = {"/mcash/review/mcashReviewCertifyPop.do", "/m/mcash/review/mcashReviewCertifyPop.do"})
    public String requestReviewCertifyPop(HttpServletRequest request, Model model,
                                          @RequestParam(value="menuType", required=false) String menuType,
                                          @RequestParam(value="popType", required=false) String popType,
                                          @RequestParam(value="buttonType", required=true) String buttonType) {
        String returnUrl = "portal/review/mcashReviewCertifyPop";

        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "mobile/review/mcashReviewCertifyPop";
        }

        model.addAttribute("menuType", menuType);
        model.addAttribute("popType", popType);
        model.addAttribute("buttonType", buttonType);
        return returnUrl;
    }
}
