package com.ktis.msp.pps.orgmgmt.controller;




import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.pps.orgmgmt.service.PpsHdofcOrgMgmtService;
import com.ktis.msp.pps.orgmgmt.vo.PpsOnlineOrderVo;

import egovframework.rte.fdl.property.EgovPropertyService;




/**
 * @Class Name : PpsHdofcOrgMgmtController.java
 * @Description : PpsHdofcOrgMgmtController class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.08.08           최초생성
 *
 * @author 장익준
 * @since 2014. 08.08
 * @version 1.0
 */

@Controller
public class PpsHdofcOrgMgmtController  extends BaseController {
	
	@Autowired
	private PpsHdofcOrgMgmtService orgService;
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	/**
	 * 선불 대리점 예치금 입출금내역 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/AgentDepositMgmtForm.do", method={POST, GET} )
	public ModelAndView selectAgentDepositMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		ModelAndView modelAndView = new ModelAndView();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info("PpsHdofcOrgMgmtController.selectAgentDepositMgmtListForm : 대리점 예치금입출금내역 목록조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// 본사, 대리점 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/orgmgmt/hdofc_orgmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
    	
		
	}
	
	/**
	 * 선불 예치금 내역 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/AgentDepositMgmtList.json" )
	public String selectAgentDepositMgmtListJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		
		logger.info("PpsHdofcOrgMgmtController.selectAgentDepositMgmtListJson : 본사  예치금 입출금 내역 목록조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사, 대리점 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
    		
	    	 List<?> resultList = orgService.getAgentDepositMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	 		
	    	 
        
   	 	} catch (Exception e) {
   	 	 resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
        }
    	model.addAttribute("result", resultMap);
        //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	
	/**
	 * 선불 예치금 입출금 내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/AgentDepositMgmtListExcel.json" )
	public String selectAgentDepositMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		
		logger.info("PpsHdofcOrgMgmtController.selectAgentDepositMgmtListExcelJson :본사  예치금 입출금 내역 목록조회 엑셀출력");
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			orgService.getAgentDepositMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
		/**
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	FileInputStream in  = null;
		OutputStream out = null;
		File file = null;

    	try{
	    	 List<?> resultList = orgService.getAgentDepositMgmtListExcel(pRequestParamMap);
	    	
	    	 	
	    	 	String serverInfo= null;
	    	 	serverInfo = propertyService.getString("excelPath");
	    	 
	    	 	
	    	 	String strFilename = serverInfo  + "선불예치금사용내역_"; //파일명
	    		String strSheetname = "선불예치금사용내역";//시트명

	    		//엑셀 첫줄
	            String [] strHead = {"대리점명","예치금구분","입출금일자","입금액","출금액","처리금액","충전계약번호","잔액","관리자","메모"};

	            String [] strValue = {"agentName","depositTypeName","depositDate","plusDeposit","minusDeposit","recharge","contractNum","remains","adminNm","remark"};

	            //엑셀 컬럼 사이즈
	            int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 10000};
	            
	            int[] intLen = {0, 0, 0, 1, 1, 1, 0, 1, 0,0};
	            String strFileName = hdofcCommonService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse,intLen);

	           
	            
	             file = new File(strFileName);
	            
	            pResponse.setContentType("applicaton/download");
	            pResponse.setContentLength((int) file.length());
	            
	            String userAgent = pRequest.getHeader("User-Agent");
	            boolean ie = userAgent.indexOf("Trident") > -1;

//	            logger.debug("strFileName 123= " + strFileName);
	            if(ie){
	            	strFileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
	            }
	            else
	            {
	            	strFileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1").replace("+", "%20");
	            }
//	            logger.debug("strFileName = " + strFileName);
	            strFileName = strFileName.substring(13);
	            
	            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strFileName +  "\";");

	             in = new FileInputStream(file);
	            
	             out = pResponse.getOutputStream();
	            
	            int temp = -1;
	            while((temp = in.read()) != -1){
	            	out.write(temp);
	            }
	            
	           
	 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	 			resultMap.put("msg", "다운로드성공");
	 			
	 		} catch (Exception e) {
	 			 resultMap.clear();
	  			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
	  				logger.error(e);
	  			}
	 		} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			            logger.error(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	  logger.error(e);
			          }
			        }
		            file.delete();
			    }
	 		//----------------------------------------------------------------
	 		// return json 
	 		//----------------------------------------------------------------	
	 		model.addAttribute("result", resultMap);
		*/
    	return "jsonView";
	}

	
	/**
	 * 선불 온라인주문내역 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/OnlineOrderMgmtForm.do", method={POST, GET} )
	public 	ModelAndView selectOnlineOrderMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		ModelAndView modelAndView = new ModelAndView();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info("PpsHdofcOrgMgmtController.selectOnlineOrderMgmtListForm: 선불 온라인주문내역 목록조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/orgmgmt/hdofc_orgmgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
    	
		
	}
	
	
	/**
	 * 선불 온라인주문내역 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/OnlineOrderMgmtList.json" )
	public String selectOnlineOrderMgmtListJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		
		logger.info("PpsHdofcOrgMgmtController.selectOnlineOrderMgmtListJson :온라인주문 내역 목록조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
	    	 List<?> resultList = orgService.getOnlineOrderMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	
        
   	 	} catch (Exception e) {
   	 	 resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
        }
    	 model.addAttribute("result", resultMap);
         //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 선불 본사 온라인 주문내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/OnlineOrderMgmtListExcel.json" )
	public String selectOnlineOrderMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	
		
		logger.info("PpsHdofcOrgMgmtController.selectOnlineOrderMgmtListExcelJson: 선불 본사 온라인 주문내역 목록조회 엑셀출력 ");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			orgService.getOnlineOrderMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	/**
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	FileInputStream in  = null;
		OutputStream out = null;
		File file = null;
    	try{
	    	List<?> resultList = orgService.getOnlineOrderMgmtListExcel(pRequestParamMap);
	    	
	    	//logger.debug(" resultList : \n"+ resultList.toString());
	    	 
	    	String serverInfo= null;
    	 	serverInfo = propertyService.getString("excelPath");
	    	 
	    	
	    	String strFilename = serverInfo  + "선불예치금입출금내역_"; //파일명
    		String strSheetname = "선불본사 온라인주문내역";//시트명

    		//엑셀 첫줄
            String [] strHead = {"대리점명","요청일자","품목","모델명","수량","희망배송일자","배송주소","담당자","메모","처리상태","본사관리자","관리자메모","완료일자","주문번호"};

            String [] strValue = {"agentName","reqDate","orderItemNm","modelNm","amount","shipDate","shipAddress","opNm","agentMemo","statusNm","bonsaAdminId","adminMemo","finishDate","orderSeq"};

            //엑셀 컬럼 사이즈
            int[] intWidth = {3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
            int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0,0};
            
            String strFileName = hdofcCommonService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse,intLen);

	           
            
             file = new File(strFileName);
            
            pResponse.setContentType("applicaton/download");
            pResponse.setContentLength((int) file.length());
            
            String userAgent = pRequest.getHeader("User-Agent");
            boolean ie = userAgent.indexOf("Trident") > -1;

//            logger.debug("strFileName 123= " + strFileName);
            if(ie){
            	strFileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
            }
            else
            {
            	strFileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1").replace("+", "%20");
            }
//            logger.debug("strFileName = " + strFileName);
            strFileName = strFileName.substring(13);
            
            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strFileName +  "\";");

             in = new FileInputStream(file);
            
             out = pResponse.getOutputStream();
            
            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }
            
           
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");
 			
 		} catch (Exception e) {
 			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				logger.error(e);
			}
 		} finally {
	        if (in != null) {
		          try {
		        	  in.close();
		          } catch (Exception e) {
		            logger.error(e);
		          }
		        }
		        if (out != null) {
		          try {
		        	  out.close();
		          } catch (Exception e) {
		        	  logger.error(e);
		          }
		        }
	            file.delete();
		    }
 		//----------------------------------------------------------------
 		// return json 
 		//----------------------------------------------------------------	
 		model.addAttribute("result", resultMap);
		*/
    	return "jsonView";
	}
	
	
	/**
	 * 온라인주문처리 
	 * @param model
	 * @param pRequest
	 * @param searchPpsOnlineOrderVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/ppsOnlineOrderAdminProc.json" )
	public String selectppsOnlineOrderAdminProcJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsOnlineOrderVo")PpsOnlineOrderVo searchPpsOnlineOrderVo,
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap
		) 
{

		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		pRequestParamMap.put("adminId", loginInfo.getUserId());
		
		
		
		logger.info("PpsHdofcOrgMgmtController.ppsOnlineOrderAdminProcJson: 선불 온라인 주문처리");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap= orgService.getPpsOnlineOrderAdminProc(pRequestParamMap);
					
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		//logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		return "jsonView";
	}
	
	
	/**
	 * 온라인주문 대리점 
	 * @param model
	 * @param pRequest
	 * @param searchPpsOnlineOrderVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/ppsOnlineOrderAgentProc.json" )
	public String selectppsOnlineOrderAgentProcJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsOnlineOrderVo")PpsOnlineOrderVo searchPpsOnlineOrderVo,
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap
		) 
{

			
		
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
		 pRequestParamMap.put("adminId", loginInfo.getUserId());
		 
		 
		 
		 logger.info("PpsHdofcOrgMgmtController.ppsOnlineOrderAgentProcJson: 선불 대리점 온라인 주문처리");
		
		 //logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		 //printRequest(pRequest);
		
			
		 
	
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
			resultMap= orgService.getPpsOnlineOrderAgentProc(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		//logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		return "jsonView";
	}
	
	/**
	 * 선불 대리점 예치금 상세 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/AgentDepositMgmtDetailForm.do", method={POST, GET} )
	public ModelAndView selectAgentDepositMgmtDetailForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			logger.info("PpsHdofcOrgMgmtController.selectAgentDepositMgmtDetailForm:  대리점 예치금상세 페이지 호출 ");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/orgmgmt/hdofc_orgmgmt_0040");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	
	/**
	 * 선불 개통대리점 목록조회  폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/OpenAgentMgmtForm.do", method={POST, GET} )
	public ModelAndView selectgetOpenAgentMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		ModelAndView modelAndView = new ModelAndView();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info("PpsHdofcOrgMgmtController.selectgetOpenAgentMgmtListForm: 선불 개통대리점 목록조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/orgmgmt/hdofc_orgmgmt_0030");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	
	/**
	 * 선불 개통 대리점 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/OpenAgentMgmtList.json" )
	public String selectOpenAgentMgmtListJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		
		logger.info("PpsHdofcOrgMgmtController.selectOpenAgentMgmtListJson : 선불 개통대리점 목록조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
	    	 List<?> resultList = orgService.getOpenAgentInfoList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	 
   	 	} catch (Exception e) {
	   	 	resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
        }
    	 
    	model.addAttribute("result", resultMap);
        // logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
         
    	return "jsonView";
	}
	
	
	/**
	 * 선불 개통 대리점 목록 엑셀저장
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/orgmgmt/OpenAgentMgmtListExcel.json" )
	public String selectOpenAgentMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	
		
		logger.info("PpsHdofcOrgMgmtController.selectOpenAgentMgmtListExcelJson : 선불 본사 개통대리점  목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			orgService.getOpenAgentInfoListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	/**
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	FileInputStream in  = null;
		OutputStream out = null;
		File file = null;
    	try{
	    	List<?> resultList = orgService.getOpenAgentInfoListExcel(pRequestParamMap);
	    	
	    	//logger.debug(" resultList : \n"+ resultList.toString());
	    	 
	    	String serverInfo= null;
    	 	serverInfo = propertyService.getString("excelPath");
	    	 
	    	
	    	String strFilename = serverInfo  + "선불개통대리점관리_"; //파일명
    		String strSheetname = "선불개통대리점관리";//시트명

    		//엑셀 첫줄
            String [] strHead = {"대리점코드","대리점명","대표자명","담당자연락처","유선연락처","팩스","예치금","가상계좌","은행명","등록일자"};

            String [] strValue = {"agentId","agentNm","rprsenNm","respnPrsnNum","telnum","fax","deposit","virAccountId","virBankNm","regDttm"};

            //엑셀 컬럼 사이즈
            int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
            int[] intLen = {0, 0, 0, 0, 0, 0, 1, 0, 0,0};
            
            String strFileName = hdofcCommonService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse,intLen);

	           
            
             file = new File(strFileName);
            
            pResponse.setContentType("applicaton/download");
            pResponse.setContentLength((int) file.length());
            
            String userAgent = pRequest.getHeader("User-Agent");
            boolean ie = userAgent.indexOf("Trident") > -1;

//            logger.debug("strFileName 123= " + strFileName);
            if(ie){
            	strFileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
            }
            else
            {
            	strFileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1").replace("+", "%20");
            }
//            logger.debug("strFileName = " + strFileName);
            strFileName = strFileName.substring(13);
            
            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strFileName +  "\";");

             in = new FileInputStream(file);
            
             out = pResponse.getOutputStream();
            
            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }
            
            
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");
 			
 		} catch (Exception e) {
 			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				logger.error(e);
			}
 		} finally {
	        if (in != null) {
		          try {
		        	  in.close();
		          } catch (Exception e) {
		            logger.error(e);
		          }
		        }
		        if (out != null) {
		          try {
		        	  out.close();
		          } catch (Exception e) {
		        	  logger.error(e);
		          }
		        }
	            file.delete();
		    }
 		//----------------------------------------------------------------
 		// return json 
 		//----------------------------------------------------------------	
 		model.addAttribute("result", resultMap);
		*/
    	return "jsonView";
	}
	

}
