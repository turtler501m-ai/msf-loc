package com.ktis.msp.cmn.filedown.controller;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;

import egovframework.rte.fdl.property.EgovPropertyService;



/**  
 * @Class Name : 
 * @Description : 
 * @Modification Information  
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @
 * 
 * @author 
 * @version 1.0
 * @see
 * 
 */

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: FileDownController.java
 * 3. Package	: com.ktis.msp.cmn.filedown.controller
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:53:47
 * </PRE>
 */
@Controller
public class FileDownController extends BaseController { 

	protected Log log = LogFactory.getLog(this.getClass());
	
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Autowired
	private FileDownService  fileDownService;

	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
	private OrgCommonService orgCommonService;

	
	/**
	 * <PRE>
	 * 1. MethodName: fileDown
	 * 2. ClassName	: FileDownController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:53:49
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	//현재 미사용. 취약점 조치 대상이어서 주석처리
	//@RequestMapping(value="/cmn/filedown/fileDown.do")  
//	public String fileDown(ModelMap model, 
//						HttpServletRequest pRequest, 
//						HttpServletResponse pResponse, 
//						@RequestParam Map<String, Object> pRequestParamMap) 
//	{
//
//		try
//		{
//			logger.info("===========================================");
//			logger.info("====== FileDownController.fileDownById.do ======");
//			logger.info("===========================================");
//			logger.info(">>>>requestParamMap.toString() : " + pRequestParamMap.toString());
//			printRequest(pRequest);
//			logger.info("===========================================");
//			
//			//----------------------------------------------------------------
//			// Login check
//			//----------------------------------------------------------------
//			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
//			loginInfo.putSessionToParameterMap(pRequestParamMap);
//			
//			//---------------------------------
//			// 
//			//---------------------------------
//			String lBaseDir = propertyService.getString("fileUploadBaseDirectory");
//		
//			//=====================
//			pResponse.setContentType("application/octet-stream;charset=utf-8"); 
//			pResponse.setCharacterEncoding("UTF-8"); 
//
//			//--------------------------------
//			// 파일 이름
//			//--------------------------------
//			String lFileName = StringUtils.defaultString((String)pRequest.getParameter("fileName"),"file_name");
//			lFileName =  URLDecoder.decode(lFileName, "UTF-8")   ;
//			logger.debug(">>>>>>>>>>>>decoded l_file_name:" + lFileName);
//
//			//--------------------------------
//			// PC 파일 이름
//			//--------------------------------
//			String lFileNamePc = StringUtils.defaultString((String)pRequest.getParameter("fileNamePc"),lFileName);
//			lFileNamePc =  URLDecoder.decode(lFileNamePc, "UTF-8")   ;
//			logger.debug(">>>>>>>>>>>>decoded l_file_name_pc:" + lFileNamePc);
//
//			//--------------------------------
//			// 서버의 경로(모율별)
//			//--------------------------------
//			String lFilePath = pRequest.getParameter("filePath");
//			if ( lFilePath.contains(".."))
//			{
//					return null;
//			}
//			
//			int    lFileSize = 0; 
//
//			logger.debug(">>>l_base_dir:"+ lBaseDir );
//			logger.debug(">>>l_file_name:"+ lFileName     );
//			logger.debug(">>>l_file_path:"+ lFilePath     );
//			logger.debug(">>>l_file_name_pc:"+ lFileNamePc );
//
//			//--------------------------------
//			// 파일명이 없으면 return
//			//--------------------------------
//			if( lFileName == null ) 
//			{
//				return null;
//			}
//			
//			String fileNameFullPath = lBaseDir + lFilePath  + "/" +  lFileName;
//			File file = new File(fileNameFullPath);
//			String lFileNamePcEncode = URLEncoder.encode(lFileNamePc, "UTF-8");
//
//			//--------------------------------
//			// 파일이 존재하면 down 시작
//			//--------------------------------
//			if (file.exists()){		
////	        	out.clear();
////	        	out = pageContext.pushBody();
//				
//				pResponse.setHeader("Content-Disposition","attachment;filename="+lFileNamePcEncode);
//				lFileSize = (int) file.length();
//				byte b[] = new byte[lFileSize]; 
//				//byte b[] = new byte[4062];
//			
//				BufferedInputStream fin   = null;
//				BufferedOutputStream outs = null;
//				
//				try {
//					fin   = new BufferedInputStream(new FileInputStream(file));
//					outs = new BufferedOutputStream(pResponse.getOutputStream());
//					
//					int read = 0;
//					while ((read = fin.read(b)) != -1){
//					  	outs.write(b,0,read);
//					}
//				} catch(Exception e) {
//					throw new MvnoErrorException(e);
//				} finally {
//					if(outs != null){
//						outs.close();
//					}
//					
//					if(fin != null){
//						fin.close();
//					}
//				}
//								
//				if (StringUtils.defaultString((String)pRequestParamMap.get("writeFileUpDownLog"),"N").equals("Y") )
//				{
//					pRequestParamMap.put("FILE_NM"   ,lFileName);
//					
//					String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
//					
//					if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
//						ipAddr = pRequest.getHeader("REMOTE_ADDR");
//				   
//					if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
//						ipAddr = pRequest.getRemoteAddr();
//					
//
//					
//					pRequestParamMap.put("IP_INFO"   ,ipAddr);
//					pRequestParamMap.put("DUTY_NM"   ,"");
//					pRequestParamMap.put("FILE_ROUT" ,fileNameFullPath);
//					pRequestParamMap.put("FILE_SIZE" ,lFileSize);
//					
//					pRequestParamMap.put("DATA_CNT", 0);
//					
//					fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
//				}	            
//			}else{
//				logger.debug(lFileName + " : does not exist." );
//				pResponse.setContentType("text/html;charset=utf-8");
//				//20200513 소스코드점검 으로 추가
//				lFileName = lFileName.replaceAll("<","&lt;");
//				lFileName = lFileName.replaceAll(">","&gt;");
//				pResponse.getWriter(). println(lFileName + " : not exists.");
//			}
//			
//		} catch (Exception e)
//		{
//			//e.printStackTrace();
//			//logger.error(e.getMessage());
//			throw new MvnoErrorException(e);
//		}
//		return null;
////        return "/cmn/fileDownLoad/fileDownLoad";
//	} 	


	

	
	/**
	 * <PRE>
	 * 1. MethodName: fileDownById
	 * 2. ClassName	: FileDownController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:53:49
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */

	@RequestMapping(value="/cmn/filedown/fileDownById.do") 
	public String fileDownById(    		
												ModelMap model, 
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
		
		try
		{
			fileDownService.getFileMgmtById(pRequest, pResponse, pRequestParamMap);
			
		} catch (Exception e)
		{
			throw new MvnoErrorException(e);
		}
		return null;
//        return "/cmn/fileDownLoad/fileDownLoad";
	} 	


	



	/**
	 * <PRE>
	 * 1. MethodName: fileDownInfoPop
	 * 2. ClassName	: FileDownController
	 * 3. Commnet	: 파일 다운로드 정보 입력 화면
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:54:03
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/filedown/fileDownInfoPop.do")
	public ModelAndView fileDownInfoPop (
									ModelMap model, 
									HttpServletRequest pRequest, 
									HttpServletResponse pResponse, 
									@RequestParam Map<String, Object> pRequestParamMap
								) 
	{

		logger.info("===========================================");
		logger.info("======  FileDownController.fileDownInfoPop.do ======");
		logger.info("===========================================");
		logger.info(">>>>requestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
		modelAndView.getModelMap().addAttribute("requestParamMap",pRequestParamMap);
		
		//----------------------------------------------------------------
		// jsp 지정
		//----------------------------------------------------------------
		modelAndView.setViewName("/cmn/filedown/fileDownInfoPop");
		
		return modelAndView; 
		
	}
 
	
	@RequestMapping(value = "/cmn/filedown/fileDownCause.do")
	public ModelAndView fileDownCause (
									ModelMap model, 
									HttpServletRequest pRequest, 
									HttpServletResponse pResponse, 
									@RequestParam Map<String, Object> pRequestParamMap
								) 
	{

		logger.info("===========================================");
		logger.info("======  FileDownController.fileDownCause.do ======");
		logger.info("===========================================");
		logger.info(">>>>requestParamMap.toString() : " + pRequestParamMap.toString());
		logger.info("===========================================");
		
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
		modelAndView.getModelMap().addAttribute("requestParamMap",pRequestParamMap);
		
		//----------------------------------------------------------------
		// jsp 지정
		//----------------------------------------------------------------
		modelAndView.setViewName("/cmn/filedown/fileDownCause");
		
		return modelAndView; 
		
	}
	
	/**
	 * 엑셀리스트
	 * @param searchVO - 조회할 정보가 담긴
	 * @param model
	 * @return "/bnd/bondsalemgmt/msp_bnd_bond_1011"
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/filedown/excelDown.do")
	public ModelAndView excelDown (
					HttpServletRequest pRequest, 
					HttpServletResponse pResponse,
					ModelMap model,
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		ModelAndView modelAndView = new ModelAndView();
		try {
			
			model.addAttribute("startDate",orgCommonService.getToDay());
			model.addAttribute("endDate",orgCommonService.getToDay());
			modelAndView.getModelMap().addAttribute("info",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/cmn/filedown/excelDown");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
		
	/**
	 * @Description : 저장된 엑셀리스트를 가져온다
	 * @Param  : 
	 * @Return : ModelAndView
	 * @Author : 김연우
	 * @Create Date : 2015. 10. 13.
	 */
	@RequestMapping("/cmn/exceldown/getExcelList.json")
	public String selectBondExcel(
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("저장된 엑셀리스트 가져오기 START."));
		logger.info(generateLogMsg("Return Vo [BondMgmtVO] = " + pRequestParamMap.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try { 
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<?> resultList = fileDownService.selectExcelList(String.valueOf(pRequestParamMap.get("SESSION_USER_ID")), String.valueOf(pRequestParamMap.get("startDt")), String.valueOf(pRequestParamMap.get("endDt")), Integer.valueOf(pRequest.getParameter("pageIndex")), Integer.valueOf(pRequest.getParameter("pageSize"))  );
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
//			logger.debug(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap))
				throw new MvnoErrorException(e);
		}
		model.addAttribute("result", resultMap);
		
		
		return "jsonView"; 
	}


	/**
	 * @Description : 엑셀 다운로드
	 * @Param  : 
	 * @Return : ModelAndView
	 * @Author : 김연우
	 * @Create Date : 2015. 10. 14.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/cmn/exceldown/excelDownProc.json")
	@ResponseBody
	public String excelDownProc(
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap)
	{
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		int excelDownSeqNum = Integer.parseInt((String)pRequest.getParameter("excelDownSeqNum"));
		String strFilePath;
		String strFileName;
		String result = "";
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			Map<String,Object> dbRow = null;
			List<?> resultList = fileDownService.selectExcelFilePath(excelDownSeqNum);
			
			if ( resultList.size() > 0 )
			{
				dbRow = (Map<String,Object>)  resultList.get(0);
				
				strFilePath = (String)dbRow.get("filePath");
				strFileName = (String)dbRow.get("fileNm");
			} else {
				throw new MvnoRunException(-1, "선택하신 파일이 존재하지 않습니다.");
			}
			
			//---------------------------------
			// 
			//---------------------------------
			String lBaseDir = propertyService.getString("fileUploadBaseDirectory");
		
			//=====================
			pResponse.setContentType("application/octet-stream;charset=utf-8"); 
			pResponse.setCharacterEncoding("UTF-8"); 
	
			//--------------------------------
			// 파일 이름
			//--------------------------------
			String lFileName = strFileName;
			lFileName =  URLDecoder.decode(lFileName, "UTF-8")   ;
			logger.debug(">>>>>>>>>>>>decoded l_file_name:" + lFileName);
	
			//--------------------------------
			// PC 파일 이름
			//--------------------------------
			String lFileNamePc = strFileName;
			lFileNamePc =  URLDecoder.decode(lFileNamePc, "UTF-8")   ;
			logger.debug(">>>>>>>>>>>>decoded l_file_name_pc:" + lFileNamePc);
	
			//--------------------------------
			// 서버의 경로(모율별)
			//--------------------------------
			String lFilePath = strFilePath;
			if ( lFilePath.contains(".."))
			{
					return null;
			}
			
			int    lFileSize = 0; 
	
			logger.debug(">>>l_base_dir:"+ lBaseDir );
			logger.debug(">>>l_file_name:"+ lFileName     );
			logger.debug(">>>l_file_path:"+ lFilePath     );
			logger.debug(">>>l_file_name_pc:"+ lFileNamePc );
	
			//--------------------------------
			// 파일명이 없으면 return
			//--------------------------------
			if( lFileName == null ) 
			{
				logger.debug("파일이 존재하지 않습니다 = [" + lFilePath + "]");
				return null;
			}
			
			String fileNameFullPath = strFilePath;
//	        String fileNameFullPath = lBaseDir + lFilePath  + "/" +  lFileName;
			File file = new File(fileNameFullPath);
			String lFileNamePcEncode = URLEncoder.encode(lFileNamePc, "UTF-8");
	
			//--------------------------------
			// 파일이 존재하면 down 시작 a
			//--------------------------------
			if (file.exists()){		
				
				pResponse.setHeader("Content-Disposition","attachment;filename="+lFileNamePcEncode);
				lFileSize = (int) file.length();
				byte b[] = new byte[lFileSize]; 
				//byte b[] = new byte[4062];
	
				BufferedInputStream fin   = null;
				BufferedOutputStream outs = null;
				try {

					fin   = new BufferedInputStream(new FileInputStream(file));
					outs = new BufferedOutputStream(pResponse.getOutputStream());
					
					int read = 0;
					while ((read = fin.read(b)) != -1){
						outs.write(b,0,read);
					}
				} catch(Exception e) {
					//e.printStackTrace();
					throw new MvnoErrorException(e);
				} finally {
					if(outs != null){
						outs.close();
					}
					if(fin != null){
						fin.close();
					}
				}
				
				if (StringUtils.defaultString((String)pRequestParamMap.get("writeFileUpDownLog"),"N").equals("Y") )
				{
					pRequestParamMap.put("FILE_NM"   ,lFileName);
					
					String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
					
					if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
						ipAddr = pRequest.getHeader("REMOTE_ADDR");
				
					if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
						ipAddr = pRequest.getRemoteAddr();
					
	
					
					pRequestParamMap.put("IP_INFO"   ,ipAddr);
					pRequestParamMap.put("DUTY_NM"   ,"");
					pRequestParamMap.put("FILE_ROUT" ,fileNameFullPath);
					pRequestParamMap.put("FILE_SIZE" ,lFileSize);
					
					pRequestParamMap.put("DATA_CNT", 0);
					
					fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
				}
			}else{
				logger.debug(lFileName + " : does not exist." );
				result = "The file does not exist.";
//				pResponse.setContentType("text/html;charset=utf-8");
//				pResponse.getWriter(). println(lFileName + " : not exists.");
			}
			
		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
				   throw new MvnoErrorException(e);
		}
		
//		model.addAttribute("result", resultMap);
		
		return result; 
		
	}
	

	/**
	 * @Description : 엑셀 다운로드
	 * @Param  : 
	 * @Return : ModelAndView
	 * @Author : 김연우
	 * @Create Date : 2015. 10. 14.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/cmn/exceldown/excelDownProcByExclDnldId.json")
	@ResponseBody
	public String excelDownProcByExclDnldId(
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap)
	{
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		int exclDnldId = Integer.parseInt((String)pRequest.getParameter("exclDnldId"));
		String strFilePath;
		String strFileName;
		String result = "";
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			Map<String,Object> dbRow = null;
			List<?> resultList = fileDownService.selectExcelFilePathByExclDnldId(exclDnldId);
			
			if ( resultList.size() > 0 )
			{
				dbRow = (Map<String,Object>)  resultList.get(0);
				
				strFilePath = (String)dbRow.get("filePath");
				strFileName = (String)dbRow.get("fileNm");
			} else {
				throw new MvnoRunException(-1, "선택하신 파일이 존재하지 않습니다.");
			}
			
			//---------------------------------
			// 
			//---------------------------------
			String lBaseDir = propertyService.getString("fileUploadBaseDirectory");
		
			//=====================
			pResponse.setContentType("application/octet-stream;charset=utf-8"); 
			pResponse.setCharacterEncoding("UTF-8"); 
	
			//--------------------------------
			// 파일 이름
			//--------------------------------
			String lFileName = strFileName;
			lFileName =  URLDecoder.decode(lFileName, "UTF-8")   ;
			logger.debug(">>>>>>>>>>>>decoded l_file_name:" + lFileName);
	
			//--------------------------------
			// PC 파일 이름
			//--------------------------------
			String lFileNamePc = strFileName;
			lFileNamePc =  URLDecoder.decode(lFileNamePc, "UTF-8")   ;
			logger.debug(">>>>>>>>>>>>decoded l_file_name_pc:" + lFileNamePc);
	
			//--------------------------------
			// 서버의 경로(모율별)
			//--------------------------------
			String lFilePath = strFilePath;
			if ( lFilePath.contains(".."))
			{
					return null;
			}
			
			int    lFileSize = 0; 
	
			logger.debug(">>>l_base_dir:"+ lBaseDir );
			logger.debug(">>>l_file_name:"+ lFileName     );
			logger.debug(">>>l_file_path:"+ lFilePath     );
			logger.debug(">>>l_file_name_pc:"+ lFileNamePc );
	
			//--------------------------------
			// 파일명이 없으면 return
			//--------------------------------
			if( lFileName == null ) 
			{
				logger.debug("파일이 존재하지 않습니다 = [" + lFilePath + "]");
				return null;
			}
			
			String fileNameFullPath = strFilePath;
//	        String fileNameFullPath = lBaseDir + lFilePath  + "/" +  lFileName;
			File file = new File(fileNameFullPath);
			String lFileNamePcEncode = URLEncoder.encode(lFileNamePc, "UTF-8");
	
			//--------------------------------
			// 파일이 존재하면 down 시작 a
			//--------------------------------
			if (file.exists()){		
				
				pResponse.setHeader("Content-Disposition","attachment;filename="+lFileNamePcEncode);
				lFileSize = (int) file.length();
				byte b[] = new byte[lFileSize]; 
				//byte b[] = new byte[4062];
	
				BufferedInputStream fin   = null;
				BufferedOutputStream outs = null;
				try {

					fin   = new BufferedInputStream(new FileInputStream(file));
					outs = new BufferedOutputStream(pResponse.getOutputStream());
					
					int read = 0;
					while ((read = fin.read(b)) != -1){
						outs.write(b,0,read);
					}
				} catch(Exception e) {
					//e.printStackTrace();
					throw new MvnoErrorException(e);
				} finally {
					if(outs != null){
						outs.close();
					}
					if(fin != null){
						fin.close();
					}
				}
				
				if (StringUtils.defaultString((String)pRequestParamMap.get("writeFileUpDownLog"),"N").equals("Y") )
				{
					pRequestParamMap.put("FILE_NM"   ,lFileName);
					
					String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
					
					if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
						ipAddr = pRequest.getHeader("REMOTE_ADDR");
				
					if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
						ipAddr = pRequest.getRemoteAddr();
					
	
					
					pRequestParamMap.put("IP_INFO"   ,ipAddr);
					pRequestParamMap.put("DUTY_NM"   ,"");
					pRequestParamMap.put("FILE_ROUT" ,fileNameFullPath);
					pRequestParamMap.put("FILE_SIZE" ,lFileSize);
					
					pRequestParamMap.put("DATA_CNT", 0);
					
					fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
				}
			}else{
				logger.debug(lFileName + " : does not exist." );
				result = "The file does not exist.";
//				pResponse.setContentType("text/html;charset=utf-8");
//				pResponse.getWriter(). println(lFileName + " : not exists.");
			}
			
		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
				   throw new MvnoErrorException(e);
		}
		
//		model.addAttribute("result", resultMap);
		
		return result; 
		
	}
}


