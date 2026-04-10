package com.ktmmobile.mcp.mypage.controller;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.BIDING_EXCEPTION;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.mcp.common.exception.SelfServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.dto.CoupInfoDto;
import com.ktmmobile.mcp.common.mplatform.dto.InqrCoupInfoRes;
import com.ktmmobile.mcp.common.mspservice.MspService;
import com.ktmmobile.mcp.common.util.MaskingUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.coupon.service.CouponService;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyGiftDto;
import com.ktmmobile.mcp.mypage.service.MyBenefitService;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.point.dto.CustPointDto;

@Controller
public class SimpleController {

	private static final Logger logger = LoggerFactory.getLogger(SimpleController.class);

	@Autowired
    MspService mspService;

	@Autowired
	private MypageService mypageService;

	@Autowired
	private MplatFormService mPlatFormService;

	@Autowired
	private MyBenefitService myBenefitService;

	@Autowired
	private CouponService couponService;

	@Value("${api.interface.server}")
    private String apiInterfaceServer;

	public static final String SIMPLE_AUTH_MENU = "simple";



	/**
	 * 설명 : 간편조회 - 임시세션 로그아웃
	 * @Author : 신유석
	 * @Date : 2021.12.30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/simple/sessionEndAjax.do","/m/simple/sessionEndAjax.do"})
	public String sessionEndAjax(HttpServletRequest request, Model model ) {

		String SessionYn = "N";
		String loginYn = "N";
		String pageAuthYn = "N";

		model.addAttribute("SessionYn", SessionYn);
        model.addAttribute("loginYn", loginYn);
        model.addAttribute("pageAuthYn", pageAuthYn);

        String returnUrl;

		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			returnUrl = "/mobile/simple/certSmsNomemberView";
		} else {
			returnUrl = "/portal/simple/certSmsNomemberView";
		}

		return returnUrl;
	}


    /**
     * 설명 : 간편조회 - 포인트 조회 내역
     * @Author : 신유석
     * @Date : 2021.12.30
     * @param request
     * @return
     */
    @RequestMapping( value = { "/simple/myPointInfoAjax.do", "/m/simple/myPointInfoAjax.do" })
    @ResponseBody
    public  Map<String, Object> myPointInfoAjax(HttpServletRequest request)  {

    	Map<String, Object> rtnJsonMap = new HashMap<String, Object>();

    	AuthSmsDto aut = SessionUtils.getSmsSession("simple");

    	// 세션에서 조회
        String ctn = aut != null ? aut.getCtn() : null;
        String custId = aut != null ? aut.getCustId() : null;
        String subLinkName = aut != null ? aut.getSubLinkName() : null;
        String svcCntrNo = aut != null ? aut.getSvcCntrNo() : null;
        String birthday = aut != null ? aut.getBirthday() : null;

    	if(svcCntrNo == null) {
    		// 오류 메시지 노출
    		rtnJsonMap.put("result", "00001");
        	return rtnJsonMap;
    	}


    	McpUserCntrMngDto mcpUserCntrMngDto = new McpUserCntrMngDto();
    	mcpUserCntrMngDto.setSubLinkName(MaskingUtil.getMaskedName(subLinkName));
		mcpUserCntrMngDto.setCntrMobileNo(MaskingUtil.getMaskedTelNo(com.ktmmobile.mcp.common.util.StringUtil.getMobileFullNum(ctn)));


		LocalDate now = LocalDate.now();  // 현재 날짜 구하기
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd"); // 포맷 정의
		String formatedNow = now.format(formatter);  // 포맷 적용

		CustPointDto custPoint = myBenefitService.selectCustPoint(svcCntrNo);

    	rtnJsonMap.put("mcpUserCntrMngDto", mcpUserCntrMngDto);
    	rtnJsonMap.put("custPoint", custPoint);
    	rtnJsonMap.put("formatedNow", formatedNow);


    	if (custPoint == null) {
    		rtnJsonMap.put("result", "00001");
    	}else {
    		rtnJsonMap.put("result", "00000");
    	}

    	return rtnJsonMap;
    }

	/**
	 * 설명 : 간편조회 - 쿠폰 목록 조회
	 * @Author : 신유석
	 * @Date : 2021.12.30
	 * @param request
	 * @param coupInfoDto
	 * @param model
	 * @param pageInfoBean
	 * @return
	 */
	@RequestMapping(value={"/simple/couponListAjax.do","/m/simple/couponListAjax.do"})
	public String couponListAjax(HttpServletRequest request, CoupInfoDto coupInfoDto, Model model, PageInfoBean pageInfoBean) {

		// CoupInfoDto 에서 받는다
		// ncn 변수명으로 휴대폰 번호 받는다.

		String rtnCode = "";
		String rtnMsg = "";


		String returnUrl;
		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			returnUrl = "/mobile/simple/html/simpleMyInfoCouponHtml";
		} else {
			returnUrl = "/portal/simple/html/simpleMyInfoCouponHtml";
		}


		AuthSmsDto aut = SessionUtils.getSmsSession("simple");

		// 세션에서 조회
        String ctn = aut != null ? aut.getCtn() : null;
        String custId = aut != null ? aut.getCustId() : null;
        String subLinkName = aut != null ? aut.getSubLinkName() : null;
        String svcCntrNo = aut != null ? aut.getSvcCntrNo() : null;
        String birthday = aut != null ? aut.getBirthday() : null;


    	if(svcCntrNo == null) {
    		// 오류 메시지 노출
			rtnCode = "9999";
			rtnMsg = "서비스 처리중 오류가 발생 하였습니다.";

			return returnUrl;
    	}


			// X74데이터값
			coupInfoDto.setCtn(ctn);
			coupInfoDto.setCustId(custId);
			coupInfoDto.setCoupSerialNo("");
			coupInfoDto.setCoupTypeCd("");
			coupInfoDto.setCoupStatCd("BPCO");
			coupInfoDto.setSearchTypeCd("02");
			coupInfoDto.setSvcTypeCd("");
			coupInfoDto.setPageNo(1);
			coupInfoDto.setNcn(svcCntrNo);

			List<CoupInfoDto> coupInfoList = null;

			// 쿠폰 정보조회(X74)
			try {

				InqrCoupInfoRes inqrCoupInfoRes = mPlatFormService.inqrCoupInfo(coupInfoDto);
				coupInfoList = new ArrayList<CoupInfoDto>();
				if( inqrCoupInfoRes !=null ){
					coupInfoList = inqrCoupInfoRes.getCoupInfoList();
					rtnCode = inqrCoupInfoRes.getRtnCode();
					rtnMsg = inqrCoupInfoRes.getRtnMsg();
				}
			} catch (SelfServiceException e) {
				rtnCode = "9999";
				rtnMsg = "서비스 처리중 오류가 발생 하였습니다.";
			} catch (Exception e) {
				rtnCode = "9999";
				rtnMsg = "서비스 처리중 오류가 발생 하였습니다.";
			}

			coupInfoDto.setSvcCntrNo(svcCntrNo);
			List<CoupInfoDto> mPortalcouponList = couponService.getUseCoupInfoList(coupInfoDto);  //NMCP_COUPN_OUTSIDE_BAS


			model.addAttribute("mPortalcouponList", mPortalcouponList);
			model.addAttribute("coupInfoList", coupInfoList);


		return returnUrl;
	}


	/**
	 * 설명 : 간편조회 - 사은품 신청 내역
	 * @Author : 신유석
	 * @Date : 2021.12.30
	 * @param request
	 * @param pageInfoBean
	 * @param model
	 * @param myGiftDto
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping( value = { "/simple/myGiftList.do", "/m/simple/myGiftList.do" })
	public String myGiftList(HttpServletRequest request, @ModelAttribute PageInfoBean pageInfoBean, Model model, MyGiftDto myGiftDto) throws ParseException  {



		String rtnPageNm;

		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			rtnPageNm = "/mobile/simple/html/simpleMyInfoGiftHtml";
		} else {
			rtnPageNm = "/portal/simple/html/simpleMyInfoGiftHtml";
		}


		AuthSmsDto aut = SessionUtils.getSmsSession("simple");

		// 세션에서 조회
        String ctn = aut != null ? aut.getCtn() : null;
        String custId = aut != null ? aut.getCustId() : null;
        String subLinkName = aut != null ? aut.getSubLinkName() : null;
        String contractNum = aut != null ? aut.getSvcCntrNo() : null;
        String birthday = aut != null ? aut.getBirthday() : null;

        myGiftDto.setContractNum(contractNum);
    	if(contractNum == null) {
    		// 오류 메시지 노출
			return rtnPageNm;
    	}

    	if (pageInfoBean.getPageNo() == 0) {
			pageInfoBean.setPageNo(1);
		}

		// 한페이지당 보여줄 리스트 수
		pageInfoBean.setRecordCount(10);

		int totalCount = myBenefitService.getCustListCount(myGiftDto);
		pageInfoBean.setTotalCount(totalCount);

		if (pageInfoBean.getTotalPageCount() < pageInfoBean.getPageNo()) {
			throw new McpCommonJsonException(BIDING_EXCEPTION);
		}

		int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
		int maxResult = pageInfoBean.getRecordCount(); // Pagesize

		List<MyGiftDto> getGiftCustList;

		getGiftCustList = myBenefitService.getGiftCustList(myGiftDto, skipResult, maxResult);

		int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + getGiftCustList.size();

    	model.addAttribute("contractNum", contractNum);
    	model.addAttribute("pageInfoBean", pageInfoBean);
		model.addAttribute("giftCustList", getGiftCustList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("listCount", listCount);
		model.addAttribute("pageNo", pageInfoBean.getPageNo());

		return rtnPageNm;
	}

	/**
	 * 설명 : 간편조회 - 사은품 신청 내역
	 * @Author : 신유석
	 * @Date : 2021.12.30
	 * @param request
	 * @param pageInfoBean
	 * @param model
	 * @param myGiftDto
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = { "/simple/myGiftListAjax.do", "/m/simple/myGiftListAjax.do" })
	@ResponseBody
	public HashMap<String, Object> myGiftListAjax(HttpServletRequest request, @ModelAttribute PageInfoBean pageInfoBean,
			Model model, MyGiftDto myGiftDto) throws ParseException {

		HashMap<String, Object> rtnMap = new HashMap<String, Object>();

		AuthSmsDto aut = SessionUtils.getSmsSession("simple");

		// 세션에서 조회
		String ctn = aut != null ?  aut.getCtn(): null;
		String custId = aut != null ?  aut.getCustId(): null;
		String subLinkName = aut != null ?  aut.getSubLinkName(): null;
		String contractNum = aut != null ?  aut.getSvcCntrNo(): null;
		String birthday = aut != null ?  aut.getBirthday(): null;

		myGiftDto.setContractNum(contractNum);

		if (pageInfoBean.getPageNo() == 0) {
			pageInfoBean.setPageNo(1);
		}

		// 한페이지당 보여줄 리스트 수
		pageInfoBean.setRecordCount(10);

		int totalCount = myBenefitService.getCustListCount(myGiftDto);
		pageInfoBean.setTotalCount(totalCount);

		if (pageInfoBean.getTotalPageCount() < pageInfoBean.getPageNo()) {
			throw new McpCommonJsonException(BIDING_EXCEPTION);
		}

		int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
		int maxResult = pageInfoBean.getRecordCount(); // Pagesize

		List<MyGiftDto> getGiftCustList;

		getGiftCustList = myBenefitService.getGiftCustList(myGiftDto, skipResult, maxResult);

		int listCount = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount() + getGiftCustList.size();

		rtnMap.put("contractNum", contractNum);
		rtnMap.put("pageInfoBean", pageInfoBean);
		rtnMap.put("giftCustList", getGiftCustList);
		rtnMap.put("totalCount", totalCount);
		rtnMap.put("listCount", listCount);
		rtnMap.put("pageNo", pageInfoBean.getPageNo());

		return rtnMap;
	}

    /**
     * 설명 : 간편조회 - 비회원 로그인 입력폼
     * @Author : 신유석
     * @Date : 2021.12.30
     * @param request
     * @param redirectUrl
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = {"/simple/certSmsNomemberView.do","/m/simple/certSmsNomemberView.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String certSmsNomemberView(HttpServletRequest request, @RequestParam(defaultValue="",required=false) String redirectUrl, Model model) throws ParseException {

        String returnUrl = "/portal/simple/certSmsNomemberView";

        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "/mobile/simple/certSmsNomemberView";
        }

        model.addAttribute("simpleType","noMember");

       	return returnUrl;
    }


}
