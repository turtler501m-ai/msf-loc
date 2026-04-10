package com.ktis.msp.pps.sucmgmt.controller;
/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.ktis.msp.pps.sucmgmt.service.SucMgmtService;
import com.ktis.msp.pps.sucmgmt.vo.SucListVo;
import com.ktis.msp.pps.sucmgmt.vo.SucVo;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : SucMgmtController.java
 * @Description : SucMgmtController Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.07.15           최초생성
 *
 * @author 
 * @since 2015. 07.15
 * @version 1.0
 * @see
 *
 * 
 */

@Controller
public class SucMgmtController extends BaseController {

//	@Autowired
//	private OrgCommonService orgCommonService;	
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private SucMgmtService sucMgmtService;
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	@Autowired
    private FileDownService  fileDownService;
	
	@Autowired
	private LoginService loginService;
	
	/** Constructor */
	public SucMgmtController() {
		setLogPrefix("[SucMgmtController] ");
	}
	
	@RequestMapping(value = "/pps/sucMgmt/getSucList.do")	
	public ModelAndView getRcpList( @ModelAttribute("SucVo") SucVo sucVo, 
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse 
			//@RequestParam Map<String, Object> pRequestParamMap
			) 
	{
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);

			ModelAndView modelAndView = new ModelAndView();
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			
			String mcpUrl =  propertiesService.getString("mcp.url");
			//modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			//modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			
			modelAndView.getModelMap().addAttribute("mcpUrl", mcpUrl);

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}
			
			String agentVersion = (String) resultMap.get("AGENT_VERSION");	// 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");		// 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			String scanSearchUrl =  propertiesService.getString("scan.search.url");			
			String scanDownloadUrl =  propertiesService.getString("scan.download.url");
			
			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);

			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("maskingYn", maskingYn);
			model.addAttribute("scanSearchUrl", scanSearchUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);			
			

    		//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/pps/suc/sucmgmt");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	//
	@RequestMapping(value = "/pps/sucMgmt/getSucListAjax.json")
	public String ListData(@ModelAttribute("SucVo") SucVo sucVo, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> rowsMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사, 대리점 권한
	    	if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(paramMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}

			int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
			if(typeCd == 20){//대리점
				sucVo.setCntpntShopId(loginInfo.getUserOrgnId());
			}

			List<?> custList = sucMgmtService.getSucList(sucVo, paramMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			rowsMap.put("pageIndex", paramMap.get("pageIndex"));
			rowsMap.put("pageSize", paramMap.get("pageSize"));
			rowsMap.put("total",  custList.size() == 0 ?  "0" : ((Map) custList.get(0)).get("totalCount"));
			rowsMap.put("rows", custList);

			resultMap.put("data", rowsMap);

		} catch (Exception e) {
			//logger.error(e.getMessage());
			//logger.error(e);
			//e.printStackTrace();

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			 throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	@RequestMapping(value = "/pps/sucMgmt/ssateUpdateAjax.json")
	public String updateData(@ModelAttribute("SucVo") SucVo sucVo, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사, 대리점 권한
	    	if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(paramMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			sucMgmtService.updateSsate(request, paramMap, sucVo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("retData", paramMap);

			
		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("retData", paramMap);
			
			 throw new MvnoErrorException(e);

		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	@RequestMapping(value = "/pps/sucMgmt/getSucImageView.do")	
	public void imageView(HttpServletRequest pRequest,HttpServletResponse pResponse){
		/*try {
			
			FileInputStream fis = null;
			BufferedInputStream in = null;
		    ByteArrayOutputStream bStream = null;
//		    String path = null;
		    StringBuffer path = new StringBuffer();
		    String imsiPath[];
		    File tmpF= null;
		    if(pRequest.getServerName().toLowerCase().indexOf("mspdev.ktmmobile.com") != -1 || pRequest.getServerName().toLowerCase().indexOf("mspoper.ktmmobile.com") != -1){
		    	*//*path="/jboss/nfs/mcpdev/default/upload/sellusim/";*//*
		    	//2016-01-05 이미지 경로 수정 
//		    	path="/jboss/data/mcp_dev/upload/sellusim/";
		    	path.append("/jboss/data/mcp_dev/upload/sellusim/");
		    }else{
		    	*//*path="/jboss/nfs/mcp/default/upload/sellusim/";*//*
		    	//2016-01-05 이미지 경로 수정
//		    	path="/jboss/data/mcp/upload/sellusim/";
		    	path.append("/jboss/data/mcp/upload/sellusim/");
		    }
		    
		    //이전 파일명일때
		    if(pRequest.getParameter("fileMask").toLowerCase().indexOf("_") ==-1 ){
		    	//파일 이전 확인
		    	tmpF=new File(path+pRequest.getParameter("fileMask"));
		    	if(!tmpF.isFile()){
		    		String imsiPath2[] = pRequest.getParameter("fileMask").split("\\.");
//			    	Date now =new Date( Long.valueOf(imsiPath2[0]).longValue());
			    	Date now =new Date( Long.valueOf(imsiPath2[0]));
			    	SimpleDateFormat sdf1 =new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
					String imsi= sdf1.format(now);
//					path = path+"/"+imsi.substring(0,4)+"/"+imsi.substring(4,6)+"/";
					path.append("/" + imsi.substring(0,4) + "/" + imsi.substring(4,6) + "/");
		    	}
		    }else{//파일명에 날짜가있을때
		    	//파일 이전 확인
		    	tmpF=new File(path+pRequest.getParameter("fileMask"));
		    	if(!tmpF.isFile()){
		    		imsiPath = pRequest.getParameter("fileMask").split("_");
//			    	path = path+"/"+imsiPath[0].substring(0,4)+"/"+imsiPath[0].substring(4,6)+"/";
		    		path.append("/" + imsiPath[0].substring(0,4) + "/" + imsiPath[0].substring(4,6) + "/");
		    	}
		    }
		    
//		    StringBuffer tmpPath = new StringBuffer();
//		    tmpPath.append(path);
//		    tmpPath.append(pRequest.getParameter("fileMask"));
//		    path= tmpPath.toString();
		    path.append(pRequest.getParameter("fileMask"));
		    
			try{
				  File file = new File(path.toString());
		          fis = new FileInputStream(file);
		          in = new BufferedInputStream(fis);
		          bStream = new ByteArrayOutputStream();
		          int imgByte;
				  while ((imgByte = in.read()) != -1) {
				     bStream.write(imgByte);
				  }
				  pResponse.setContentLength(bStream.size());
				  bStream.writeTo(pResponse.getOutputStream());
			      pResponse.getOutputStream().flush();
			      pResponse.getOutputStream().close();

		      } catch (Exception e) {
		    	 throw new MvnoRunException(-1, "");
		      } finally {
		    	  
			      if (bStream != null) {
					   try {
					      bStream.close();
					   } catch (Exception est) {
						   throw new MvnoErrorException(est);
					   }
			      }
			      
		          if (in != null) {
		        	  try {in.close();}
		        	  catch (Exception ei) { throw new MvnoErrorException(ei);
	}
		          }
		          
				  if (fis != null) {
					 	try {fis.close();}
			            catch (Exception efis) {  throw new MvnoErrorException(efis);
 }
				  }
				  
		     }
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}*/
		
	}
	
	@RequestMapping(value = "/pps/sucMgmt/getSucImagePage.do")	
	public ModelAndView imagePage( @ModelAttribute("SucVo") SucVo sucVo, 
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse 
			) 
	{
		try {
			ModelAndView modelAndView = new ModelAndView();
			model.addAttribute("fileMask", pRequest.getParameter("fileMask"));
			modelAndView.setViewName("/pps/suc/sucmgmtPage");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	@RequestMapping(value = "/pps/sucMgmt/deleteSellUsim.json")	
	public String deleteSellUsim(@RequestBody String reData,   
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse 
			) 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사, 대리점 권한
	    	if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(paramMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			  ObjectMapper om = new ObjectMapper();
		      om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		      SucListVo sucListVo = om.readValue(reData, SucListVo.class);
		      int result_cnt=0;
		      for(int i=0 ; i <sucListVo.getItems().size() ; i++){
		    	  result_cnt = sucMgmtService.deleteSellUsim(sucListVo.getItems().get(i));
		      }
		      logger.info(result_cnt);
		      
		      resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			  resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.DUP", null, Locale.getDefault()) );
	 		resultMap.put("msg", "중복데이터가 존재합니다.");
	 		 throw new MvnoErrorException(e);
		}
		model.addAttribute("result", resultMap);
		logger.debug("resultMap:" + resultMap);
		return "jsonView";
	}

	
	@RequestMapping("/pps/sucMgmt/getExcelList.json")
	public String getExcelList(@ModelAttribute("sucVo") SucVo sucVo, 
					Model model, 
					@RequestParam Map<String, Object> pRequestParamMap,
					HttpServletRequest request, 
					HttpServletResponse response)
	{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("개통USIM 등록관리 엑셀 저장 START"));
		logger.info(generateLogMsg("Return Vo [sucVo] = " + sucVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
			
			List<EgovMap> excelList = sucMgmtService.getExcelList(sucVo);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "개통USIM 등록관리_";//파일명
			String strSheetname = "개통USIM 등록관리";//시트명

			//엑셀 첫줄
			String [] strHead = {"대리점", "유심번호","고객명", "휴대폰번호", "등록일자", "진행상태"};

			String [] strValue = {"agentId", "usimNo","frgnrName", "mobileNo", "rdate", "sstateName"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {9000, 3000, 3000, 5000, 3000, 8000, 9000};


			int[] intLen = {0, 0, 0, 0, 0, 0};


			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, excelList, strHead, intWidth, strValue, request, response, intLen);

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
                pRequestParamMap.put("DUTY_NM"   ,"ORG");                       //업무명
                pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
                pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
                pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
                pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
                
                fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
            }
            //=======파일다운로드사유 로그 END==========================================================
			
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			//logger.error(e.getMessage());

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
			
			 throw new MvnoErrorException(e);
		}finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					//logger.error(e);
					 throw new MvnoErrorException(e);

				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					//logger.error(e);
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
	
	@RequestMapping(value = "/pps/sucMgmt/getSucPathCheckAjax.json")
	public String getSucPathCheckAjax(@ModelAttribute("SucVo") SucVo sucVo, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> rowsMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사, 대리점 권한
	    	if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(paramMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}

			int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
			if(typeCd == 20){//대리점
				sucVo.setCntpntShopId(loginInfo.getUserOrgnId());
			}

			SucVo resultVo = sucMgmtService.getSucInfo(sucVo, paramMap);

			String filePathStr = "";
			String etcFilePathStr = "";
			
			filePathStr = sucMgmtService.getFilePath(resultVo.getFileMask(), pRequest.getServerName());
			etcFilePathStr = sucMgmtService.getFilePath(resultVo.getEtcFileMask(), pRequest.getServerName());

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			rowsMap.put("pageIndex", paramMap.get("pageIndex"));
			rowsMap.put("pageSize", paramMap.get("pageSize"));
			rowsMap.put("checkImgPath", filePathStr);
			rowsMap.put("checkEtcImgPath", etcFilePathStr);

			resultMap.put("data", rowsMap);

		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			 throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	@RequestMapping(value = "/pps/sucMgmt/getSucStatisticsList.do")	
	public ModelAndView getSucStatisticsList( @ModelAttribute("SucVo") SucVo sucVo, 
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse 
			//@RequestParam Map<String, Object> pRequestParamMap
			) 
	{
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);

			ModelAndView modelAndView = new ModelAndView();
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			
			String mcpUrl =  propertiesService.getString("mcp.url");
			//modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			//modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			
			modelAndView.getModelMap().addAttribute("mcpUrl", mcpUrl);

    		//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/pps/suc/sucmgmt_0020");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	@RequestMapping(value = "/pps/sucMgmt/getSucStatisticsListAjax.json")
	public String getSucStatisticsListAjax (@ModelAttribute("SucVo") SucVo sucVo, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> rowsMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사, 대리점 권한
	    	if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(paramMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}

			int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
			if(typeCd == 20){//대리점
				sucVo.setCntpntShopId(loginInfo.getUserOrgnId());
			}

			List<?> custList = sucMgmtService.getSucStatisticsListAjax(sucVo, paramMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			rowsMap.put("pageIndex", paramMap.get("pageIndex"));
			rowsMap.put("pageSize", paramMap.get("pageSize"));
			rowsMap.put("total",  custList.size() == 0 ?  "0" : ((Map) custList.get(0)).get("totalCount"));
			rowsMap.put("rows", custList);

			resultMap.put("data", rowsMap);

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			 throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
}
