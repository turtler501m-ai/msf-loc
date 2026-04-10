package com.ktmmobile.mcp.mypage.controller;

import static com.ktmmobile.mcp.common.constants.Constants.POINT_RSN_CD_U61;
import static com.ktmmobile.mcp.common.constants.Constants.POINT_TRT_USE;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.service.BarcodeSvc;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.MaskingUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.coupon.dto.CouponCategoryDto;
import com.ktmmobile.mcp.coupon.dto.CouponOutsideDto;
import com.ktmmobile.mcp.coupon.service.CouponService;
import com.ktmmobile.mcp.mypage.dto.BenefitSearchDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MyBenefitService;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.point.dto.CustPointDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;
import com.ktmmobile.mcp.point.service.PointService;

@Controller
public class MyBenefitController {

    private static Logger logger = LoggerFactory.getLogger(MyBenefitController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MyBenefitService myBenefitService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private PointService pointService;

    @Autowired
    SmsSvc smsSvc;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private BarcodeSvc barcodeSvc;

    @Value("${sms.callcenter}")
    private String callCenter;

    /** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    /** 파일업로드 접근가능한 웹경로 */
    @Value("${common.fileupload.web}")
    private String fileuploadWeb;

    /**
     * 설명 : My 포인트 - 메인
     * @Author : 신유석
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */
    @RequestMapping( value = {"/mypage/myPointView.do", "/m/mypage/myPointView.do" })
    public String myPointView(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = new ArrayList<>();
        if ( userSession != null ) {
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }

        if(cntrList.size() > 0) {
            String cntrNo;
            cntrNo = cntrList.get(0).getSvcCntrNo();
            CustPointDto custPoint = myBenefitService.selectCustPoint(cntrNo);
            List<CustPointTxnDto> monthlyPointList = myBenefitService.selectMothlyPointList(cntrNo);
            model.addAttribute("custPoint",custPoint);
            model.addAttribute("monthlyPointList",monthlyPointList);
        }

        if(!cntrList.isEmpty()) {
            for(McpUserCntrMngDto boardDto : cntrList) {
                boardDto.setCntrMobileNo(MaskingUtil.getMaskedTelNo(boardDto.getCntrMobileNoMasking()));
            }
        }

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        // 포맷 적용
        String formatedNow = now.format(formatter);


        model.addAttribute("formatedNow",formatedNow);
        model.addAttribute("cntrList",cntrList);

        String returnUrl;

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/myPointView";
        } else {
            returnUrl = "/portal/mypage/myPointView";
        }

        return returnUrl;
    }

    /**
     * 설명 : My 포인트 - 포인트 조회
     * @Author : 신유석
     * @Date : 2021.12.30
     * @param request
     * @return
     */
    @RequestMapping( value = { "/mypage/myPointInfoAjax.do", "/m/mypage/myPointInfoAjax.do" })
    @ResponseBody
    public  Map<String, Object> myPointInfoAjax(HttpServletRequest request)  {
        Map<String, Object> rtnJsonMap = new HashMap<String, Object>();
        String cntrNo = request.getParameter("cntrNo");
        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        if(Optional.ofNullable(cntrList).isEmpty()) {
            rtnJsonMap.put("result", "00004");
            rtnJsonMap.put("message", "회선정보 조회 중 에러가 발생했습니다. 확인후 다시 이용해주세요.");
            rtnJsonMap.put("location", request.getHeader("Referer"));
            return rtnJsonMap;
        }else {
            if( ! this.checkUserIsSame( cntrList, cntrNo) ){
                rtnJsonMap.put("result", "00003");
                rtnJsonMap.put("message", "로그인정보가 일치하지 않습니다.");
                rtnJsonMap.put("location", request.getHeader("Referer"));
                return rtnJsonMap;
            }
        }
        CustPointDto custPoint = myBenefitService.selectCustPoint(cntrNo);
        rtnJsonMap.put("custPoint", custPoint);
        if (custPoint == null) {
            rtnJsonMap.put("result", "00000");
        }else {
            rtnJsonMap.put("result", "00001");
        }
        return rtnJsonMap;
    }

    /**
     * 설명 : My 포인트 - 이용/적립 내역 조회
     * @Author : 신유석
     * @Date : 2021.12.30
     * @param request
     * @return
     */
    @RequestMapping( value = { "/mypage/myPointHistListAjax.do", "/m/mypage/myPointHistListAjax.do" })
    @ResponseBody
    public  Map<String, Object> myPointHistListAjax(HttpServletRequest request)  {
        Map<String, Object> rtnJsonMap = new HashMap<String, Object>();
        String cntrNo = request.getParameter("cntrNo");
        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        if(Optional.ofNullable(cntrList).isEmpty()) {
            rtnJsonMap.put("result", "00001");
            rtnJsonMap.put("message", "회선정보 조회 중 에러가 발생했습니다. 확인후 다시 이용해주세요.");
            rtnJsonMap.put("location", request.getHeader("Referer"));
            return rtnJsonMap;
        }else {
            if( ! this.checkUserIsSame( cntrList, cntrNo) ){
                rtnJsonMap.put("result", "00002");
                rtnJsonMap.put("message", "로그인정보가 일치하지 않습니다.");
                rtnJsonMap.put("location", request.getHeader("Referer"));
                return rtnJsonMap;
            }
        }

        rtnJsonMap.put("result", "00000");
        List<CustPointTxnDto> monthlyPointList = myBenefitService.selectMothlyPointList(request.getParameter("cntrNo"));
        rtnJsonMap.put("monthlyPointList", monthlyPointList);
        return rtnJsonMap;
    }

    /**
     * 설명 : My 포인트 - 이용/적립 내역 조회
     * @Author : 신유석
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */
    @RequestMapping( value = { "/mypage/myPointSaveHistList.do", "/m/mypage/myPointSaveHistList.do" })
    public String myPointSaveHistList(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") BenefitSearchDto searchVO)  {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

        if(Optional.ofNullable(cntrList).isEmpty()) {
            throw new McpCommonException("회선정보 조회 중 에러가 발생했습니다. 확인후 다시 이용해주세요.",request.getHeader("Referer"));
        }else {
            if( ! this.checkUserIsSame( cntrList, searchVO.getCntrNo()) ){
                throw new McpCommonException("로그인정보가 일치하지 않습니다.",request.getHeader("Referer"));
            }
        }

        if(!cntrList.isEmpty()) {
            for(McpUserCntrMngDto boardDto : cntrList) {
                boardDto.setCntrMobileNo(MaskingUtil.getMaskedTelNo(boardDto.getCntrMobileNoMasking()));
            }
        }

        LocalDate now = LocalDate.now();
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        // 포맷 적용
        String formatedNow = now.format(formatter);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM");
        // 포맷 적용
        String formatedNow2 = now.format(formatter2);
        model.addAttribute("cntrNo",request.getParameter("cntrNo"));

        model.addAttribute("formatedNow",formatedNow);
        model.addAttribute("formatedNow2",formatedNow2);

        model.addAttribute("cntrList",cntrList);

        String returnUrl;

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/html/pointDetailListHtml";
        } else {
            returnUrl = "/portal/mypage/html/pointDetailListHtml";
        }

        return returnUrl;
    }

    /**
     * 설명 : My 포인트 - 이용/적립 내역 조회
     * @Author : 신유석
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param pageInfoBean
     * @param searchVO
     * @return
     */
    @RequestMapping( value = { "/mypage/myPointSaveHistListHtml.do", "/m/mypage/myPointSaveHistListHtml.do" })
    public String myPointSaveHistListHtml(HttpServletRequest request, ModelMap model,PageInfoBean pageInfoBean,
            @ModelAttribute("searchVO") BenefitSearchDto searchVO)  {


        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

        if(Optional.ofNullable(cntrList).isEmpty()) {
            throw new McpCommonException("회선정보 조회 중 에러가 발생했습니다. 확인후 다시 이용해주세요.",request.getHeader("Referer"));
        }else {
            if( ! this.checkUserIsSame( cntrList, searchVO.getCntrNo()) ){
                throw new McpCommonException("로그인정보가 일치하지 않습니다.",request.getHeader("Referer"));
            }
        }

        /* 현재 페이지 번호 초기화 */
        if (pageInfoBean.getPageNo() == 0) {
            pageInfoBean.setPageNo(1);
        }

        /* 한페이지당 보여줄 리스트 수 */
        pageInfoBean.setRecordCount(10);

        /* 페이지 토탈 카운트 */
        int totalCount = myBenefitService.getSelectPointDetailListCnt(searchVO);
        pageInfoBean.setTotalCount(totalCount);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount(); // Pagesize

        List<CustPointTxnDto> pointDetailList = myBenefitService.getSelectPointDetailList(searchVO, skipResult, maxResult);

        int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + pointDetailList.size();

        model.addAttribute("total", totalCount);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("maxPage", pageInfoBean.getTotalCount());
        model.addAttribute("listCount", listCount);
        model.addAttribute("pointDetailList",pointDetailList);


        String returnUrl;

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/html/pointDetailListContentsHtml";
        } else {
            returnUrl = "/portal/mypage/html/pointDetailListContentsHtml";
        }

        return returnUrl;
    }

    /**
     * 설명 : 포인트로 쿠폰 교환 - 쿠폰 리스트
     * @Author : 신유석
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */
    @RequestMapping( value = { "/mypage/pointGiftRegView.do", "/m/mypage/pointGiftRegView.do" })
    public String pointGiftRegView(HttpServletRequest request, ModelMap model, BenefitSearchDto searchVO)  {

          UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);

          List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

          if(Optional.ofNullable(cntrList).isEmpty()) {
              throw new McpCommonException("회선정보 조회 중 에러가 발생했습니다. 확인후 다시 이용해주세요.",request.getHeader("Referer"));
          }else {
              if( ! this.checkUserIsSame( cntrList, searchVO.getCntrNo()) ){
                  throw new McpCommonException("로그인정보가 일치하지 않습니다.",request.getHeader("Referer"));
              }
          }

        CustPointDto custPoint = myBenefitService.selectCustPoint(request.getParameter("cntrNo"));

        LocalDate now = LocalDate.now();
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY.MM.dd");
        // 포맷 적용
        String formatedNow = now.format(formatter);
        model.addAttribute("formatedNow",formatedNow);
        model.addAttribute("custPoint",custPoint);

        String returnUrl;

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/html/pointGiftRegView";
        } else {
            returnUrl = "/portal/mypage/html/pointGiftRegView";
        }

        return returnUrl;
    }

    /**
     * 설명 : 포인트로 쿠폰 교환 - 쿠폰 리스트
     * @Author : 신유석
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param couponCategoryDto
     * @param pageInfoBean
     * @param point
     * @return
     */
    @RequestMapping( value = { "/mypage/pointGiftRegList.do", "/m/mypage/pointGiftRegList.do" } )
    public String pointGiftRegList(HttpServletRequest request, ModelMap model,CouponCategoryDto couponCategoryDto,PageInfoBean pageInfoBean, String point)  {

        /* 현재 페이지 번호 초기화 */
        if (pageInfoBean.getPageNo() == 0) {
            pageInfoBean.setPageNo(1);
        }

        String paramPoint = StringUtil.NVL(point, "0");
        int pointt = Integer.parseInt(paramPoint);
        // 한페이지당 보여줄 리스트 수
        pageInfoBean.setRecordCount(4);

        // 페이지 토탈 카운트
        int totalCount = couponService.countByselectCouponCategoryList(couponCategoryDto);
        pageInfoBean.setTotalCount(totalCount);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();
        List<CouponCategoryDto> couponList = couponService.selectCouponCategoryList(couponCategoryDto, skipResult, maxResult);
        int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + couponList.size();

        model.addAttribute("total", totalCount);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("maxPage", pageInfoBean.getTotalCount());
        model.addAttribute("listCount", listCount);
        model.addAttribute("couponList",couponList);
        model.addAttribute("totRemainPoint",pointt);

        String returnUrl;

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/html/pointGiftRegList";
        } else {
            returnUrl = "/portal/mypage/html/pointGiftRegList";
        }

        return returnUrl;
    }

    /**
     * 설명 : 포인트로 쿠폰 교환
     * @Author : 신유석
     * @Date : 2021.12.30
     * @param request
     * @param couponCategoryDto
     * @return
     */
    @RequestMapping( value = { "/mypage/insertCouponGiftAjax.do", "/m/mypage/insertCouponGiftAjax.do" })
    @ResponseBody
    public  Map<String, Object> insertCouponGiftAjax(HttpServletRequest request,CouponCategoryDto couponCategoryDto)  {
        Map<String, Object> rtnJsonMap = new HashMap<String, Object>();

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if ( userSessionDto != null) { // 취약성 327
            cntrList = mypageService.selectCntrList(userSessionDto.getUserId());
        }

        //UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        String userId = userSessionDto.getUserId() != null ? userSessionDto.getUserId()  : "";
        String ip = request.getRemoteAddr();

        String cntrNo = request.getParameter("cntrNo");

        if(Optional.ofNullable(cntrList).isEmpty()) {
            rtnJsonMap.put("resultCode", "20001");
            rtnJsonMap.put("message", "회선정보 조회 중 에러가 발생했습니다. 확인후 다시 이용해주세요.");
            rtnJsonMap.put("location", request.getHeader("Referer"));
            return rtnJsonMap;
        }else {
            if( ! this.checkUserIsSame( cntrList, cntrNo) ){
                rtnJsonMap.put("resultCode", "20002");
                rtnJsonMap.put("message", "로그인정보가 일치하지 않습니다.");
                rtnJsonMap.put("location", request.getHeader("Referer"));
                return rtnJsonMap;
            }
        }

        String svcNo = "";
        for(McpUserCntrMngDto dto : cntrList) {
            if(dto.getContractNum().equals(request.getParameter("cntrNo"))) {
                svcNo = dto.getUnSvcNo();
                break;
            }
        }

        //POINT_TXN_DTL_RSN_DESC
        request.getParameter("coupnCtgCd");

        /**
         * 쿠폰카테고리 정보 조회
         */
        CouponCategoryDto couponList = couponService.selectCouponCategory(couponCategoryDto);

        if(couponList == null) {
            rtnJsonMap.put("resultCode", "10001");
            return rtnJsonMap;
        }
        int couoponPrice = Integer.parseInt(couponList.getExchPoint());

        // 재고수량
        int coupnQntyCnt = couponList.getCoupnQntyCnt();
        if(coupnQntyCnt < 1) {
            rtnJsonMap.put("resultCode", "10002");
            return rtnJsonMap;
        }


        /**
         * 고객 포인트 정보
         */

        CustPointDto custPoint = myBenefitService.selectCustPoint(request.getParameter("cntrNo"));

        if(custPoint == null) {
            rtnJsonMap.put("resultCode", "00002"); // 포인트 발생 전
            return rtnJsonMap;
        }

        if(custPoint.getTotRemainPoint() < couoponPrice) {
            rtnJsonMap.put("resultCode", "00003"); // 포인트부족
            return rtnJsonMap;
        }


        /**
         * 쿠폰 카테고리 내 쿠폰 정보 조회
         */
        CouponOutsideDto couponOutsideDto = new CouponOutsideDto();
        couponOutsideDto.setCoupnCtgCd(couponCategoryDto.getCoupnCtgCd());
        CouponOutsideDto couponOutside = couponService.getCouponOutside(couponOutsideDto);

        if(couponOutside == null) {
            rtnJsonMap.put("resultCode", "10001");
            return rtnJsonMap;
        }


        /**
         * 쿠폰 매핑 처리
         */
        couponOutsideDto.setCoupnCtgCd(couponCategoryDto.getCoupnCtgCd());
        couponOutsideDto.setCoupnNo(couponOutside.getCoupnNo());
        couponOutsideDto.setUserId(userId);
        couponOutsideDto.setSvcCntrNo(request.getParameter("cntrNo"));
        couponOutsideDto.setSmsRcvNo(svcNo);
        couponOutsideDto.setSmsSndYn("N");
        couponOutsideDto.setSmsSndSuccessYn("N");
           couponOutsideDto.setSmsSndDt("");
        couponOutsideDto.setCoupnGiveDate(DateTimeUtil.getFormatString("yyyyMMddHHmmss"));
        couponOutsideDto.setCretIp(ip);
        couponOutsideDto.setCretId(userId);
        int couponCnt = couponService.updateCouponOutside(couponOutsideDto);

        if(couponCnt == 0) {
            rtnJsonMap.put("resultCode", "10003");
            return rtnJsonMap;
        }



        /**
         * 포인트 차감 처리
         */
        CustPointTxnDto custPointTxnDto = new CustPointTxnDto();

        custPointTxnDto.setSvcCntrNo(request.getParameter("cntrNo")); //cntrNo
        custPointTxnDto.setPoint(couoponPrice); // 사용포인트
        custPointTxnDto.setPointTrtCd(POINT_TRT_USE); // 사용사유코드
        custPointTxnDto.setPointTxnDtlRsnDesc("홈페이지 쿠폰 교환(" + request.getParameter("coupnCtgNm") + ")");		// 사용 설명
        custPointTxnDto.setPointTxnRsnCd(POINT_RSN_CD_U61); // 사용사유
        custPointTxnDto.setRemainPoint(custPoint.getTotRemainPoint() - couoponPrice); // 잔액
        custPointTxnDto.setPointDivCd("MP"); // 포인트종류
        custPointTxnDto.setCretId(userId);
        custPointTxnDto.setCretIp(ip);


        custPointTxnDto.setPointCustSeq(custPoint.getPointCustSeq());
        pointService.editPoint(custPointTxnDto);


        /**
         * 쿠폰 발송 처리
         */
        if(couponList.getSmsSndPosblYn() == null) {
            couponList.setSmsSndPosblYn("N");
        }


        if(couponList.getSmsSndPosblYn().equals("Y")) {

            // 쿠폰번호
            String coupnNo = couponOutside.getCoupnNo();
            if(couponOutside.getRealCoupnNo() != null) {
                coupnNo = couponOutside.getRealCoupnNo();
            }


            int templateNo = 218; // 바코드 미포함 탬플릿
            MspSmsTemplateMstDto mspSmsTemplateMstDto =  fCommonSvc.getMspSmsTemplateMst(templateNo);


            // 바코드 포함한 MMS 발송할 경우 사용할 모듈
            String outputFile = null;
            /************
            templateNo = 219; // 바코드 포함 탬플릿
            mspSmsTemplateMstDto =  fCommonSvc.getMspSmsTemplateMst(templateNo);

            String barcodeData = coupnNo;
            String barcodeType = "code128";
            int dpi = 240;
            String fileName = coupnNo;
            String fileFormat = "png";

            barcodeSvc.createBarcode(barcodeData, barcodeType, dpi, fileName, fileFormat);

            String fPer = File.separator;
            String realDir = fileuploadBase + fPer + "barcode";
            outputFile = fileuploadBase+ fPer + "barcode" + fPer + fileName + "." + fileFormat;

            **************/

            String strPstngEndDateY = couponOutside.getPstngEndDate().substring(0, 4);
            String strPstngEndDateM = couponOutside.getPstngEndDate().substring(4, 6);
            String strPstngEndDateD = couponOutside.getPstngEndDate().substring(6, 8);
            String strPstngEndDate = strPstngEndDateY + "-" + strPstngEndDateM + "-" + strPstngEndDateD;


            mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{coupnNm}", couponList.getCoupnCtgNm()));
            mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{coupnNo}", coupnNo));
            mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{endDate}", strPstngEndDate));
            if (templateNo == 219) {
                mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{outputFile}", outputFile));
            }

            int fileSize=0; //파일 사이즈
            String subject="[kt M모바일] 포인트 교환 "; //MMS 제목
            String callBack = callCenter;//고객센터

            // MMS 발송 즉시 발송 대상
            //int smsResult = smsSvc.sendLms(subject,svcNo,mspSmsTemplateMstDto.getText(),callBack);
            int smsResult = smsSvc.sendLms(subject,svcNo,mspSmsTemplateMstDto.getText(),callBack,String.valueOf(templateNo),"SYSTEM");

            // 쿠폰 발송결과 등록
            couponOutsideDto.setSmsSndYn("Y"); // 발송완료
            couponOutsideDto.setSmsSndSuccessYn("Y"); // 발송성공여부
            couponOutsideDto.setCretIp(ip);
            couponOutsideDto.setCretId(userId);
            couponCnt = couponService.updateCouponOutside(couponOutsideDto);

            rtnJsonMap.put("resultMsg", ""); // 정상

        } else {
            // MMS 차후 발송 대상
            rtnJsonMap.put("resultMsg", "</br>쿠폰은 차후 발송됩니다.");

        }

        rtnJsonMap.put("resultCode", "00000"); // 정상
        return rtnJsonMap;
    }
    /**
     * 설명 : 쿠폰번호로 바코드 생성
     * @Author : 신유석
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param couponCategoryDto
     * @param pageInfoBean
     * @return
     */
    @RequestMapping(value = "/mypage/myPointBarcode.do"  )
    public String myPointBarcode(HttpServletRequest request, ModelMap model,CouponCategoryDto couponCategoryDto,PageInfoBean pageInfoBean)  {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = new ArrayList<>();
        if ( userSession != null) {
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        if(cntrList.size() > 0) {
            String cntrNo;
            cntrNo = cntrList.get(0).getSvcCntrNo();
            CustPointDto custPoint = myBenefitService.selectCustPoint(cntrNo);
            List<CustPointTxnDto> monthlyPointList = myBenefitService.selectMothlyPointList(cntrNo);
            model.addAttribute("custPoint",custPoint);
            model.addAttribute("monthlyPointList",monthlyPointList);
        }

        String barcodeData = "7899-QWER-1254-ZZRK";
        String barcodeType = "code128";
        int dpi = 240;
        String fileName = "7899-QWER-1254-ZZRK";
        String fileFormat = "PNG";


        barcodeSvc.createBarcode(barcodeData, barcodeType, dpi, fileName, fileFormat);

        String fPer = File.separator;
        String realDir = fileuploadBase + fPer + "barcode";
        String outputFile = fileuploadBase+ fPer + "barcode" + fPer + fileName + "." + fileFormat;

        model.addAttribute("outputFile", outputFile);

        return "/portal/mypage/myPointBarcode";
    }


    /**
     * 설명 :
     * @Author : 홍성민
     * @Date : 2021.12.30
     * @param searchVO
     * @param cntrList
     * @param userSession
     * @return
     */
    @Deprecated
    private boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList, UserSessionDto userSession) {

        if (!StringUtil.equals(userSession.getUserDivision(), "01")) {
            return false;
        }

        if(cntrList == null) {
            return false;
        }

        if (cntrList.size() <= 0) {
            return false;
        }

        String chkNcn = SessionUtils.getCurrPhoneNcn();

        if(chkNcn != null) {
            searchVO.setNcn(chkNcn);
        }

        String userType = "";
        if (StringUtil.isEmpty(searchVO.getNcn())) {
            searchVO.setNcn(cntrList.get(0).getSvcCntrNo());
            searchVO.setCtn(cntrList.get(0).getCntrMobileNo());
            searchVO.setCustId(cntrList.get(0).getCustId());
            searchVO.setModelName(cntrList.get(0).getModelName());
            searchVO.setContractNum(cntrList.get(0).getContractNum());
            searchVO.setSubStatus(cntrList.get(0).getSubStatus());
            userType = cntrList.get(0).getUnUserSSn();

            if(userType != null && userType.length()  == 13) {
                userType = userType.substring(6,7);
                if("5".equals(userType) || "6".equals(userType)) {
                    userType = "Y";
                    searchVO.setUserType(userType);
                }
            }
        }

        for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
            String ctn = mcpUserCntrMngDto.getCntrMobileNo();
            String ncn = mcpUserCntrMngDto.getSvcCntrNo();
            String custId = mcpUserCntrMngDto.getCustId();
            String modelName = mcpUserCntrMngDto.getModelName();
            String contractNum = mcpUserCntrMngDto.getContractNum();
            String subStatus = mcpUserCntrMngDto.getSubStatus();

            mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
            mcpUserCntrMngDto.setSvcCntrNo(ncn);
            mcpUserCntrMngDto.setCustId(custId);
            mcpUserCntrMngDto.setModelName(modelName);
            mcpUserCntrMngDto.setContractNum(contractNum);
            if (StringUtil.equals(searchVO.getNcn(), String.valueOf(mcpUserCntrMngDto.getSvcCntrNo()))) {
                searchVO.setNcn(ncn);
                searchVO.setCtn(ctn);
                searchVO.setCustId(custId);
                searchVO.setModelName(modelName);
                searchVO.setContractNum(contractNum);
                searchVO.setSubStatus(subStatus);
                userType = mcpUserCntrMngDto.getUnUserSSn();

                if(userType != null && userType.length() == 13) {
                    userType = userType.substring(6,7);
                    if("5".equals(userType) || "6".equals(userType)) {
                        userType = "Y";
                        searchVO.setUserType(userType);
                    }
                }
            }
        }
        return true;
    }


    /**
     * 설명 :
     * @Author : 홍성민
     * @Date : 2021.12.30
     * @param cntrList
     * @param cntrNo
     * @return
     */
    private boolean checkUserIsSame(List<McpUserCntrMngDto> cntrList, String cntrNo) {
        boolean result = false;
        if (StringUtil.isNotEmpty(cntrNo)) { // 취약성 323
            for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                if(cntrNo.equals(mcpUserCntrMngDto.getContractNum())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

}
