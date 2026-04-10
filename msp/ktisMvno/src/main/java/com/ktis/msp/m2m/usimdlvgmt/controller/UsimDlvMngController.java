package com.ktis.msp.m2m.usimdlvgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.m2m.usimdlvgmt.service.UsimDlvMngService;
import com.ktis.msp.m2m.usimdlvgmt.vo.UsimDlvMngVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
@Controller
public class UsimDlvMngController extends BaseController {
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
    protected EgovPropertyService propertyService;

	@Autowired
	private UsimDlvMngService usimDlvMngService;

	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private LoginService loginService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	
	/**
	 * @Description : M2M USIM 배송 관리 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/usimDlvMng.do")
	public ModelAndView usimDlvMng(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response,
					ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
	        
			//////
			modelAndView.setViewName("m2m/usimdlvgmt/usimDlvMng");

			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
		
	
	/**
	 * @Description : M2M USIM 배송 관리 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/usimDlvMngList.json")
	public String usimDlvMngList(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 배송 관리 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = usimDlvMngService.usimDlvMngList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000031", "M2M USIM 배송 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	/**
	 * @Description : M2M USIM 배송 관리 등록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/usimDlvMngInsert.json")
	public String usimDlvMngInsert(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 배송 관리 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//배송번호
			String usimOrdId = usimDlvMngService.usimOrdId(searchVO, pRequestParamMap);
			searchVO.setOrdId(usimOrdId);
			
			//배송정보 저장
			usimDlvMngService.usimDlvMstInsert(searchVO, pRequestParamMap);

			//제품정보
			String itemData = searchVO.getItemData();
			logger.debug("itemData:" + itemData);			

			List list = getVoFromMultiJson(itemData, "ALL", UsimDlvMngVO.class );
			
			for ( int i = 0 ; i < list.size(); i++)
			{
				UsimDlvMngVO vo = (UsimDlvMngVO) list.get(i);
				
				vo.setOrdId(usimOrdId);

				logger.info("itemData:" + vo.toString());
				
				usimDlvMngService.usimOrdMdlInsert(vo, pRequestParamMap);

			}
			
			
			

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000031", "M2M USIM 배송 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	
	/**
	 * @Description : M2M USIM 배송 관리 수정
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/usimDlvMngUpdate.json")
	public String usimDlvMngUpdate(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 배송 관리 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuilder smsText = new StringBuilder("");
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			if ("Y".equals(maskingYn)) {
				usimDlvMngService.usimDlvMstUpdate(searchVO, pRequestParamMap);
			} else {
				usimDlvMngService.usimDlvMstUpdateMsk(searchVO, pRequestParamMap);
			}


			//제품정보
			String itemData = searchVO.getItemData();
			logger.debug("itemData:" + itemData);			

			List list = getVoFromMultiJson(itemData, "ALL", UsimDlvMngVO.class );
			
			for ( int i = 0 ; i < list.size(); i++)
			{
				UsimDlvMngVO vo = (UsimDlvMngVO) list.get(i);
				

				logger.info("itemData:" + vo.toString());
				if(vo.getOrdId() != null) {
					usimDlvMngService.usimOrdMdlUpdate(vo, pRequestParamMap);
					
				}else {
					vo.setOrdId(searchVO.getOrdId());
					usimDlvMngService.usimOrdMdlInsert(vo, pRequestParamMap);
					
				}

			}
			
			//배송중 상태이면 등록업체에 문자 전송

			if(searchVO.getOrdStatus().equals("4") ) {//배송중 일때 문자 전송

				List<?> resultList = usimDlvMngService.getM2mMngHTel(searchVO, pRequestParamMap);

				List<?> resultListTb = usimDlvMngService.getTbNm(searchVO, pRequestParamMap);
				
				for(int i = 0 ; i < resultList.size() ; i++) {
					EgovMap res = (EgovMap) resultList.get(i);
					
					KtMsgQueueReqVO vo = new KtMsgQueueReqVO();
					vo.setMsgType("1");
					vo.setRcptData((String) res.get("mblphnNum"));
					vo.setCallbackNum(propertyService.getString("otp.sms.callcenter"));
					
					smsText.append(loginInfo.getUserOrgnNm());
					smsText.append("에서 M2M USIM이 배송중입니다.\n");

					if(searchVO.getDlvMethod().equals("DLV01")) {   //택배
						if(resultListTb != null &&  resultListTb.size() > 0) {
							smsText.append("택배사:");

							EgovMap resTb = (EgovMap) resultListTb.get(0);
							
							smsText.append(resTb.get("text"));
							smsText.append("\n");
						}
						smsText.append("송장번호:");
						smsText.append(searchVO.getInvNo());
						
					}else if(searchVO.getDlvMethod().equals("DLV02")) {   //퀵
						smsText.append("퀵배송연락처:");
						smsText.append(searchVO.getDlvTelNo());
						
					}
					
					vo.setMessage(smsText.toString());							
					vo.setReserved01("MSP");
					vo.setReserved02("M2M1000001");
					vo.setReserved03(loginInfo.getUserId());

					usimDlvMngService.insertMsgQueue(vo);
					
				}
				
			}

			
			
			
			
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000031", "M2M USIM 배송 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	

	/**
	 * @Description : M2M USIM 배송 MDL 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/usimOrdMdlList.json")
	public String usimOrdMdlList(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 배송 MDL 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = usimDlvMngService.usimOrdMdlList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000031", "M2M USIM 배송 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	/**
	 * @Description : M2M USIM 배송 MDL 등록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/usimOrdMdlInsert.json")
	public String usimOrdMdlInsert(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 배송 MDL 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			usimDlvMngService.usimOrdMdlInsert(searchVO, pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000031", "M2M USIM 배송 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	
	/**
	 * @Description : M2M USIM 배송 MDL 수정
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/usimOrdMdlUpdate.json")
	public String usimOrdMdlUpdate(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 배송 MDL 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			usimDlvMngService.usimOrdMdlUpdate(searchVO, pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000031", "M2M USIM 배송 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	

	/**
	 * @Description : M2M USIM 배송 관리 엑셀다운
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/usimDlvMngListEx.json")
	public String usimDlvMngListEx(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 배송 관리 엑셀다운 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","M2M");
			excelMap.put("MENU_NM","M2M USIM 주문 관리");
			String searchVal = "주문번호:"+searchVO.getOrdId() +
	                                 "주문월:" + searchVO.getOrdDate() +
	                                 "USIM제품:"+searchVO.getRprsPrdtId() +
					                 "배송상태:"+searchVO.getOrdStatus() +
					                 "인수월:" +searchVO.getTakeDate() +
					                 "배송지명:"+searchVO.getAreaNm()					                  
					;
			
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			//----------------------------------------------------------------
				
			List<?> usimDlvMngListEx = usimDlvMngService.usimDlvMngListEx(searchVO, pRequestParamMap);
			
			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch(Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}
			
			String strFilename = serverInfo  + "M2M USIM 배송 관리_";//파일명
			String strSheetname = "M2M USIM 배송 관리";//시트명
			
			String [] strHead = {"주문번호","주문일자","출고일자","인수일자","배송상태","발주사","대표제품코드","제품명","단가(VAT별도)","수량","총 금액(VAT별도)","총 금액(VAT포함)","배송지담당자","유선전화번호","핸드폰전화번호","배송지명","등록자"}; //17
			String [] strValue = {"ordId","ordDate","sendDate","takeDate","ordStatusNm","orgnNm","rprsPrdtId","prdtNm","outUnitPric","ordCnt","pric","pricVat","maskMngNm","mngPhn","mngMblphn","areaNmText","usrNm"}; //17
			int[] intWidth = {6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000};	
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0}; 

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값

			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			

			/*try {
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}*/
			strFileName = fileDownService.excelDownProc(strFilename, strSheetname, usimDlvMngListEx, strHead, intWidth, strValue, request, response, intLen);

			logger.info("strFileName : "+strFileName);
			logger.info("#################### 데이터 엑셀로 변환 종료 #####################");
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
			
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");
			   
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();
			
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"PTNR");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				pRequestParamMap.put("EXCL_DNLD_ID", exclDnldId);
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		}finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------	
		model.addAttribute("result", resultMap);
		return "jsonView"; 
	}
	

	/**
	 * @Description : M2M USIM 배송 관리 발주사 Combo 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/getOrgIdComboList.json")
	public String getOrgIdComboList(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = usimDlvMngService.getOrgIdComboList(searchVO, pRequestParamMap);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	/**
	 * @Description : M2M USIM 배송 관리 Mdl Combo 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/getMdlIdComboList.json")
	public String getMdlIdComboList(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = usimDlvMngService.getMdlIdComboList(searchVO, pRequestParamMap);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	/**
	 * @Description : M2M USIM 배송 관리 Area Nm Combo 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/getAreaNmComboList.json")
	public String getAreaNmComboList(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = usimDlvMngService.getAreaNmComboList(searchVO, pRequestParamMap);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	/**
	 * @Description : M2M USIM 배송 관리 배송지 정보 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/getM2mAddr.json")
	public String getM2mAddr(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = usimDlvMngService.getM2mAddr(searchVO, pRequestParamMap);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	/**
	 * @Description : M2M USIM 제품 정보 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/getM2mMdl.json")
	public String getM2mMdl(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = usimDlvMngService.getM2mMdl(searchVO, pRequestParamMap);
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	/**
	 * @Description : 배송방식 combo 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimDlvMng/getDlvMethodCombo.json")
	public String getDlvMethodCombo(@ModelAttribute("searchVO") UsimDlvMngVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = usimDlvMngService.getDlvMethodCombo();
			
			// 페이징처리를 위한 부분
			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
}
