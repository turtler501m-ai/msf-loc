package com.ktmmobile.mcp.content.controller;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.dto.MoscCombChkReqDto;
import com.ktmmobile.mcp.common.dto.MoscCombReqDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mplatform.dto.MoscCombChkResDto;
import com.ktmmobile.mcp.common.mplatform.dto.MoscCombDtlListOutDTO;
import com.ktmmobile.mcp.common.mplatform.dto.MoscCombDtlResDTO;
import com.ktmmobile.mcp.common.mplatform.dto.MoscCombInfoResDTO;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.content.dto.MyCombinationReqDto;
import com.ktmmobile.mcp.content.dto.MyCombinationResDto;
import com.ktmmobile.mcp.content.service.MyCombinationSvc;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MypageService;

@Controller
public class MyCombinationController {

	private static final Logger logger = LoggerFactory.getLogger(MyCombinationController.class);

	@Autowired
	MyCombinationSvc myCombinationSvc;

	@Autowired
	MypageService mypageService;

	/**
	 * 추가혜택 > 유무선 결합 소개 <인증전>
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param request
	 * @param model
	 * @return
	 */

	@RequestMapping(value = { "/myCombinationInfo.do", "/m/myCombinationInfo.do" })
	public String doMyCombinationInfo(HttpServletRequest request, Model model) {


		MyCombinationResDto myCombinationResDto = myCombinationSvc.selectMspCombRateMapp("KTILTEM72");
		logger.info("myCombinationResDto==>"+ObjectUtils.convertObjectToString(myCombinationResDto));


		String rtnUrl ="/content/myCombinationView.do";
		String returnUrl = "/portal/content/myCombinationInfo";



		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			returnUrl = "/mobile/content/myCombinationInfo";
			rtnUrl ="/m/content/myCombinationView.do";
		}





		return returnUrl;
	}


	/**
	 *  조회_유무선 결합 가능 여부 AJAX
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param request
	 * @param model
	 * @param searchVO
	 * @return
	 */

	@RequestMapping(value = { "/content/combinationCheckAjax.do", "/m/content/combinationCheckAjax.do" })
	@ResponseBody
	public HashMap<String, Object> combinationCheckAjax(HttpServletRequest request, Model model,
			@ModelAttribute("searchVO") MyPageSearchDto searchVO) {

		UserSessionDto userSession = SessionUtils.getUserCookieBean();
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();

		UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
		List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSessionDto.getUserId());

		if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
	        throw new McpCommonJsonException("0001" , NOT_FULL_MEMBER_EXCEPTION);
		}

		MyCombinationResDto myCombinationResDto = new MyCombinationResDto();

		//결합가능여부조회
		//일시정지 / 변경불가능한 요금제
		myCombinationResDto = myCombinationSvc.selectMspCombRateMapp("");

		//개인고객체크
		String customerType = mypageService.selectCustomerType(searchVO.getCustId());

		if("G".equals(customerType) || "B".equals(customerType)) {
			customerType = "Y";
		}

		myCombinationResDto.setCustTypeYn(customerType);
		if("Y".equals(searchVO.getUserType())) {
			rtnMap.put("userType", "Y");
		}

		rtnMap.put("myCombinationResDto", myCombinationResDto);
		rtnMap.put("RESULT_CODE", "S");
		return rtnMap;

	}

	/**
	 * 모바일 추가혜택 > 유무선 결합 인증 후 VIEW
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param request
	 * @param model
	 * @param searchVO
	 * @return
	 */

	@RequestMapping(value = { "/content/myCombinationView.do", "/m/content/myCombinationView.do" })
	public String doMyCombinationView(HttpServletRequest request, Model model,
			@ModelAttribute("searchVO") MyPageSearchDto searchVO) {

		String returnUrl = "/portal/content/myCombinationView";
		String rtnUrl = "/myCombinationInfo.do";

		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			returnUrl = "/mobile/content/myCombinationView";
			rtnUrl ="/m/myCombinationInfo.do";
		}

		UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

		if(userSessionDto == null) {
			return "redirect:"+rtnUrl;
		}

		String userId = userSessionDto.getUserId();
		List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();
		cntrList = mypageService.selectCntrList(userId);

		if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
			ResponseSuccessDto responseSuccessDto = getMessageBox();
			model.addAttribute("responseSuccessDto", responseSuccessDto);
			return "/common/successRedirect";
		}

		//로그인한 사용자 이용중인 요금제조회
		McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(searchVO.getContractNum());

		model.addAttribute("mcpUserCntrMngDto", mcpUserCntrMngDto); // 현재
		model.addAttribute("cntrList", cntrList); // 현재 서비스계약번호

		return returnUrl;
	}

	/**
	 * 모바일 추가혜택 > 유무선 결합내역 조회 팝업
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param request
	 * @param model
	 * @param contractNum
	 * @return
	 */

	@RequestMapping(value = { "/content/myCombinationList.do", "/m/content/myCombinationList.do" })
	public String doMyCombinationList(HttpServletRequest request, Model model,
			@RequestParam(value = "contractNum", required = true) String contractNum) {

		String returnUrl = "/portal/content/myCombinationList";

		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			returnUrl = "/mobile/content/myCombinationList";
		}

		UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
		MyCombinationReqDto myCombinationReqDto = new MyCombinationReqDto();

		String socNm = ""; //요금제 이름
		String cntrMobileNo= ""; //핸드폰번호
		List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSessionDto.getUserId());
		if (cntrList != null && cntrList.size() > 0) {
			for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
				if (contractNum.equals(mcpUserCntrMngDto.getContractNum())) {
					myCombinationReqDto.setCtn(mcpUserCntrMngDto.getContractNum());
					myCombinationReqDto.setCustId(mcpUserCntrMngDto.getCustId());
					myCombinationReqDto.setNcn(mcpUserCntrMngDto.getContractNum()); // 사용자서비스계약번호
					myCombinationReqDto.setCtn(mcpUserCntrMngDto.getCntrMobileNo()); // 사용자전화번호
					cntrMobileNo = StringMakerUtil.getPhoneNum(mcpUserCntrMngDto.getCntrMobileNo());
					break;
				}
			}
		}

		McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(contractNum);
		socNm = mcpUserCntrMngDto.getRateNm(); //요금제이름

		MoscCombDtlResDTO moscCombDtlResDTO = new MoscCombDtlResDTO();

		//x87결합내역조회
		moscCombDtlResDTO = myCombinationSvc.selectCombiSvcList(myCombinationReqDto);

		String resultCode = "";
		String resultMsg = "";

		if(moscCombDtlResDTO.isSuccess()) {
			resultCode = "S";
		 } else {
			 resultCode = "E";
			 resultMsg = moscCombDtlResDTO.getSvcMsg();
		 }

		model.addAttribute("resultCode", resultCode);
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("moscCombDtlResDTO", moscCombDtlResDTO);
		model.addAttribute("cntrMobileNo", cntrMobileNo);
		model.addAttribute("socNm", socNm);
		return returnUrl;
	}

	/**
	 * 결합신청 ajax
	 * 사전체크 포함
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param request
	 * @param model
	 * @param searchVO
	 * @param moscCombChkReqDto
	 * @return
	 */

	@RequestMapping(value = { "/content/insertCombinationAjax.do", "/m/content/insertCombinationAjax.do" })
	@ResponseBody
	public JsonReturnDto doInsertCombinationAjax(HttpServletRequest request, Model model,
			@ModelAttribute("searchVO") MyPageSearchDto searchVO
			,MoscCombChkReqDto moscCombChkReqDto) {

		UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
		List<McpUserCntrMngDto> cntrList = null;
		if (userSessionDto != null) { // 취약성 266
			cntrList = mypageService.selectCntrList(userSessionDto.getUserId());
		}
		if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
	        throw new McpCommonJsonException("0001" , NOT_FULL_MEMBER_EXCEPTION);
		}

		moscCombChkReqDto.setCustId(searchVO.getCustId());
		moscCombChkReqDto.setNcn(searchVO.getNcn()); //서비스계약번호
		moscCombChkReqDto.setCtn(searchVO.getCtn()); //사용자전화번호

		//결합여부가 Y일경우에는회선추가, 그외에는 신규결합신청
//		if("Y".equals(moscCombChkReqDto.getCombYnKt()) || "Y".equals(moscCombChkReqDto.getCombYnMb())) {
//			moscCombChkReqDto.setJobGubun("A");
//		}else {
//			moscCombChkReqDto.setJobGubun("N");
//		}

		//회선사업자코드
//		if("K".equals(moscCombChkReqDto.getSvcNoTypeCdKt())) {
//			moscCombChkReqDto.setSvcNoCd("K");
//			moscCombChkReqDto.setCmbStndSvcNo(moscCombChkReqDto.getReqSvcKt());
//		} else  if("M".equals(moscCombChkReqDto.getSvcNoTypeCdMb())) {
//			moscCombChkReqDto.setSvcNoCd("M");
//			moscCombChkReqDto.setCmbStndSvcNo(moscCombChkReqDto.getReqSvc());
//		}
//
//		//회선구분코드 IT일때는 인터넷
//		if("IT".equals(moscCombChkReqDto.getCmbStndSvcNoEv())) {
//			moscCombChkReqDto.setSvcNoTypeCd("IT");
//		}else {
//			moscCombChkReqDto.setSvcNoTypeCd("MB");
//		}

		moscCombChkReqDto.setCustNm(userSessionDto.getName()); //고객이름
		moscCombChkReqDto.setSvcIdfyTypeCd("1"); ///서비스확인번호타입
		moscCombChkReqDto.setSvcIdfyNo(userSessionDto.getBirthday());

		//성별코드
		String gender = "";
		if("0".equals(userSessionDto.getGender())) {
			gender = "1";
			moscCombChkReqDto.setSexCd(gender);
		}else if("1".equals(userSessionDto.getGender())) {
			gender = "2";
			moscCombChkReqDto.setSexCd(gender);
		}

		moscCombChkReqDto.setAplyMethCd("MB");//신청방법
		moscCombChkReqDto.setAplyRelatnCd("01"); //신청관계
		moscCombChkReqDto.setAplyNm(userSessionDto.getName()); //신청자이름

		MoscCombChkResDto resultRes = new MoscCombChkResDto();

		//결합신청
		//x78 사전체크, x79 결합신청
		resultRes = myCombinationSvc.insertCombSaveInfo(moscCombChkReqDto);
	    JsonReturnDto result = new JsonReturnDto();

	    String returnCode = "";
	    String message = "";
	    if("Y".equals(resultRes.getSbscYn())) {
			returnCode = "S";
			message=resultRes.getResltMsg();
		} else {
			returnCode = "E";
			message=resultRes.getResltMsg();
		}

	    result.setMessage(message);
	    result.setReturnCode(returnCode);
		return result;

	}

	/**
	 * 모바일 추가혜택 > 유무선 결합 신청 VIEW
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param request
	 * @param model
	 * @param combiYn
	 * @param contractNum
	 * @param searchVO
	 * @return
	 */

	@RequestMapping(value = { "/content/myCombinationReq.do", "/m/content/myCombinationReq.do" })
	public String doCombinationReqView(HttpServletRequest request, Model model
			,@RequestParam(value = "combiYn", required = false) String combiYn
			,@RequestParam(value = "contractNum", required = false) String contractNum
			,@ModelAttribute("searchVO") MyPageSearchDto searchVO) {

		String returnUrl = "/portal/content/myCombinationReq";
		String useRtnUrl = "/myCombinationInfo.do";

		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			returnUrl = "/mobile/content/myCombinationReq";
			useRtnUrl = "/m/myCombinationInfo.do";
		}

		UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

		if(!"N".equals(combiYn)) {
			if ("Y".equals(NmcpServiceUtils.isMobile())) {
				return "redirect:/m/content/myCombinationView.do";
			}
			return "redirect:/content/myCombinationView.do";
		} else if("success".equals(combiYn)) {
			if ("Y".equals(NmcpServiceUtils.isMobile())) {
				return "redirect:"+useRtnUrl;
			}
			return "redirect:"+useRtnUrl;
		}

		if(userSessionDto == null) {
			return "redirect:"+useRtnUrl;
		}

		String userId = userSessionDto.getUserId();
		List<McpUserCntrMngDto> cntrList =  mypageService.selectCntrList(userId);

		if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
			ResponseSuccessDto responseSuccessDto = getMessageBox();
			model.addAttribute("responseSuccessDto", responseSuccessDto);
			return "/common/successRedirect";
		}

		// 현재 요금제 조회
		// 서비스계약번호
		McpUserCntrMngDto mcpUserCntrMngDto = new McpUserCntrMngDto();
		mcpUserCntrMngDto= mypageService.selectSocDesc(searchVO.getContractNum()); //현재 선택된 회선에 요금제 조회

		MoscCombReqDto moscCombReqDto = new MoscCombReqDto();

		List<MyCombinationResDto> svcDivList = new ArrayList<MyCombinationResDto>();

		MyCombinationReqDto combinationReqDto = new MyCombinationReqDto();
		combinationReqDto.setCustId(searchVO.getCustId()); //고객번호
		combinationReqDto.setNcn(searchVO.getNcn()); // 사용자 서비스계약번호
		combinationReqDto.setCtn(searchVO.getCtn()); //사용자전화번호

		//x87
		MoscCombDtlResDTO outRes = myCombinationSvc.selectCombiSvcList(combinationReqDto);
		List<MoscCombDtlListOutDTO> list = null;

//		if(outRes.isSuccess()) {
//			if(outRes != null) {
//				 list = outRes.getList();
//			}
//		 }

	    MyCombinationResDto res = new MyCombinationResDto();

		if(list !=  null && list.size() > 0) {
			 for(MoscCombDtlListOutDTO dto : list) {
				 if(!StringMakerUtil.getPhoneNum(searchVO.getCtn()).equals(dto.getSvcNo())) {
					 res.setStatusYn("Y");
					 res.setSvcNo(dto.getUnSvcNo());
				     res.setMskSvcNo(dto.getSvcNo());
					 break;
				 }
			 }
		}

		if (cntrList != null && cntrList.size() > 0 ) {

			for (McpUserCntrMngDto cntrMngDto : cntrList) {
			     McpUserCntrMngDto socMngDto= mypageService.selectSocDesc(cntrMngDto.getContractNum()); //전
			    if(!searchVO.getNcn().equals(cntrMngDto.getContractNum())) {
		    		 	MyCombinationResDto resOutDto = myCombinationSvc.selectMspCombRateMapp("");
		    		 	if(resOutDto != null) {
			    			 if(!"Y".equals(resOutDto.getSocYn()) && !"Y".equals(resOutDto.getStatusYn())
			    					 && !res.getSvcNo().equals(cntrMngDto.getUnSvcNo())){

			    				 MyCombinationResDto result = new MyCombinationResDto();
				    			 result.setMskSvcNo(cntrMngDto.getCntrMobileNo());
				    			 result.setSvcNo(cntrMngDto.getUnSvcNo());
				    			 result.setSocNm(socMngDto.getRateNm());
								 svcDivList.add(result);
			    			 }
		    		 	}
			     }
			}
		}

		moscCombReqDto.setCustId(searchVO.getCustId()); //고객번호
		moscCombReqDto.setNcn(searchVO.getNcn()); // 사용자 서비스계약번호
		moscCombReqDto.setCtn(searchVO.getCtn()); //사용자전화번호
		moscCombReqDto.setCombSvcNoCd("K"); //결합모회선 사업자구분코드 m:mvno, k:kt
		moscCombReqDto.setCmbStndSvcNo("");//	결합 모회선 조회값 MVNO회선은 전화번호 ,KT 회선은 이름
		moscCombReqDto.setSvcIdfyNo(userSessionDto.getBirthday()); //서비스확인번호 생년월일 kt회선일때 필수

		String gender = "";
		if("0".equals(userSessionDto.getGender())) {
			gender = "1";
		}else if("1".equals(userSessionDto.getGender())) {
			gender = "2";
		}

		moscCombReqDto.setSexCd(gender); //성별코드 1.남성 2.여성 kt회선일때 필수
		moscCombReqDto.setCombSrchId(searchVO.getUserName());

		// x77
		MoscCombInfoResDTO moscCombInfoResDTO = new MoscCombInfoResDTO();
		moscCombInfoResDTO =  myCombinationSvc.selectCombiMgmtList(moscCombReqDto);

		String resultCode = "";
		String resultMsg = "";

		if(moscCombInfoResDTO.isSuccess()) {
			resultCode = "S";
		 } else {
			 resultCode = "E";
			 resultMsg = moscCombInfoResDTO.getSvcMsg();
		 }

		String statYn="";
		if(svcDivList !=null && svcDivList.size() > 0) {
			if(svcDivList.get(0).getStatusYn() != null) {
				statYn = svcDivList.get(0).getStatusYn();
			}
		}

		model.addAttribute("resultMsg", resultMsg); // 현재 서비스계약번호
		model.addAttribute("resultCode", resultCode); // 현재 서비스계약번호
		model.addAttribute("cntrList", cntrList); // 현재 서비스계약번호
		model.addAttribute("mcpUserCntrMngDto", mcpUserCntrMngDto); // 현재요금제
		model.addAttribute("contractNum", contractNum); // 현재전화번호
		model.addAttribute("moscSrchCombInfoList", moscCombInfoResDTO.getMoscSrchCombInfoList()); // 결합가입가능한상품조회
		model.addAttribute("svcDivList", svcDivList); // mvno 전화번호
		model.addAttribute("combiYn", combiYn);
		model.addAttribute("combiYnList",list);
		model.addAttribute("statYn", statYn);
		model.addAttribute("combiChkYn",res.getStatusYn());
		return returnUrl;
	}

	/**
	 * 유무선 결합 완료 VIEW
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param request
	 * @param res
	 * @param model
	 * @param searchVO
	 * @param moscCombChkReqDto
	 * @return
	 */

	@RequestMapping(value = { "/content/myCombinationCompletView.do", "/m/content/myCombinationCompletView.do" })
	public String doMyCombinationCompletView(HttpServletRequest request, HttpServletResponse res,  Model model,
			@ModelAttribute("searchVO") MyPageSearchDto searchVO
			,MoscCombChkReqDto moscCombChkReqDto) {

		String returnUrl = "/portal/content/myCombinationCompletView";
		String rtnUrl = "/content/myCombinationView.do";
//		String combiChkYn = moscCombChkReqDto.getCombiChkYn();

		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			returnUrl = "/mobile/content/myCombinationCompletView";
			rtnUrl = "/m/content/myCombinationView.do";
		}

		//결합완료여부
//		if(!"Y".equals(combiChkYn)) {
//			if ("Y".equals(NmcpServiceUtils.isMobile())) {
//				return "redirect:"+rtnUrl;
//			}
//			return "redirect:"+rtnUrl;
//		}

		UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
		List<McpUserCntrMngDto> cntrList = null;
		if (userSessionDto != null) { // 취약성 262
			cntrList = mypageService.selectCntrList(userSessionDto.getUserId());
		}
		if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
			ResponseSuccessDto responseSuccessDto = getMessageBox();
			model.addAttribute("responseSuccessDto", responseSuccessDto);
			return "/common/successRedirect";
		}

		// 본인이 가지고 있는 회선정보
	    McpUserCntrMngDto socMngDto= mypageService.selectSocDesc(searchVO.getContractNum()); //요금제
	    MyCombinationResDto myCombinationResDto = new MyCombinationResDto();
	    myCombinationResDto.setSvcNo(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
		myCombinationResDto.setSocNm(socMngDto.getRateNm());

//		if("M".equals(moscCombChkReqDto.getSvcNoTypeCdMb())) {
//			myCombinationResDto.setCombiSvcType(moscCombChkReqDto.getSvcNoTypeCdMb());
//			myCombinationResDto.setCombiSvcNo(StringMakerUtil.getPhoneNum(moscCombChkReqDto.getReqSvc()));
//
//			String reqContractNum = "";
//
//			if(cntrList != null && cntrList.size() > 0) {
//				for(McpUserCntrMngDto dto : cntrList) {
//					if(dto.getCntrMobileNo().equals(myCombinationResDto.getCombiSvcNo())) {
//						reqContractNum = dto.getContractNum();
//						break;
//					}
//				}
//
//				McpUserCntrMngDto reqSocMngDto= mypageService.selectSocDesc(reqContractNum); //요금제
//			    if(reqSocMngDto != null) {
//			    	myCombinationResDto.setReqSocNm(reqSocMngDto.getRateNm());
//			    }
//			}
//		} else if("K".equals(moscCombChkReqDto.getSvcNoTypeCdMb())) {
//			myCombinationResDto.setCombiSvcType(moscCombChkReqDto.getCmbStndSvcNoEv());
//			myCombinationResDto.setCombiSvcNo(StringMakerUtil.getPhoneNum(moscCombChkReqDto.getReqSvcKt()));
//		}

		model.addAttribute("myCombinationResDto", myCombinationResDto);
		return returnUrl;
	}



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

	private ResponseSuccessDto getMessageBox() {
		ResponseSuccessDto mbox = new ResponseSuccessDto();
		String url = "/mypage/updateForm.do";
		if ("Y".equals(NmcpServiceUtils.isMobile())) {
			url = "/m/mypage/updateForm.do";
		}

		mbox.setRedirectUrl(url);
		mbox.setSuccessMsg(NOT_FULL_MEMBER_EXCEPTION);
		return mbox;
	}
}
