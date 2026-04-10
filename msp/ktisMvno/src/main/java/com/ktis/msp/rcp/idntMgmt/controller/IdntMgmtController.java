package com.ktis.msp.rcp.idntMgmt.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.rcp.idntMgmt.service.IdntMgmtService;
import com.ktis.msp.rcp.idntMgmt.vo.IdntMgmtVO;
import com.ktis.msp.rcp.mrktMgmt.vo.MrktMgmtVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class IdntMgmtController extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IdntMgmtController.class);
	
	@Resource(name = "idntMgmtService")
	private IdntMgmtService idntMgmtService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	/**
	 * 가입자확인 화면
	 */
	@RequestMapping(value="/rcp/idntmgmt/idntMgmtView.do")
	public ModelAndView idntMgmtView( HttpServletRequest request
									, HttpServletResponse response
									, @ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO
									, @RequestParam Map<String, Object> paramMap
									, ModelMap model) {
		LOGGER.info("**********************************************************");
		LOGGER.info("* 가입자확인 :  msp/rcp/idntmgmt/msp_rcp_idnt_1001 *");
		LOGGER.info("**********************************************************");
		
		try {
			ModelAndView modelAndView = new ModelAndView();
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(idntMgmtVO);

			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new EgovBizException("권한이 없습니다.");
			}
			
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			model.addAttribute("loginInfo", loginInfo);
			model.addAllAttributes(paramMap);
			
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 

			modelAndView.setViewName("/rcp/idntMgmt/msp_rcp_idnt_1001");
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 가입자확인 목록조회
	 */
	@RequestMapping(value = "/rcp/idntmgmt/getInvstReqList.json")
	public String getInvstReqList(HttpServletRequest request
								, HttpServletResponse response
								, @ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO
								, @RequestParam Map<String, Object> paramMap
								, ModelMap model) {
		LOGGER.info("**************************************************************");
		LOGGER.info("* 가입자 확인 이력 조회 START  " + idntMgmtVO.toString());
		LOGGER.info("**************************************************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(idntMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new EgovBizException("권한이 없습니다.");
			}
			
			List<?> resultList = idntMgmtService.getInvstReqList(idntMgmtVO, paramMap);
			
			resultMap = makeResultMultiRow(paramMap, resultList);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 가입자확인 엑셀다운로드
	 */
	@RequestMapping(value = "/rcp/idntmgmt/getInvstReqListEx.json")
	public String getInvstReqListEx(HttpServletRequest request
									, HttpServletResponse response
									, @ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO
									, @RequestParam Map<String, Object> paramMap
									, ModelMap model) {
		LOGGER.info("**************************************************************");
		LOGGER.info("* 가입자확인 엑셀다운 START  " + idntMgmtVO.toString());
		LOGGER.info("**************************************************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(idntMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(idntMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = idntMgmtService.getInvstReqListEx(idntMgmtVO, paramMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strfilename = serverInfo + "가입자확인_";
			String strSheetname = "가입자확인";
			
			String[] strHead = {"처리일자","처리번호","식별유형","식별번호","전화번호","요청종류","문서번호","접수처","이메일주소","이름","계약번호","상태","선후불","가입일","해지일","주소","접수처구분","지역",
											"요청자직책","요청자이름","결재권자직책","결재권자이름","사후요청","요청일자","처리자"};
			String[] strValue = {"procDt","seqNum","customerTypeNm","userSsn","subscriberNo","reqTypeNm","invstVal","invstNm","email","cstmrNm","contractNum","subStatusNm","pppo","openDt","tmntDt","cstmrAddr","invstTypeNm","invstLoc",
									"reqOdty","reqUser","appOdtyNm","appUser","reqRsn","reqDttm","procrNm"};

			int[] inWidth = {5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,15000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000};
			int[] inLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
			
			String strFileName = fileDownService.excelDownProc(strfilename, strSheetname, list, strHead, inWidth, strValue, request, response, inLen);
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			out = response.getOutputStream();
			
			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();
				
				paramMap.put("FILE_NM"   ,file.getName());              //파일명
				paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   ,"CMN");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");
 			
		} catch (Exception e) {
 			 			
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
 			resultMap.put("msg", "");
 			throw new MvnoErrorException(e);
		} finally {
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
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	/**
	 * 가입자확인 엑셀다운로드 Multi
	 */
	@RequestMapping(value = "/rcp/idntmgmt/getInvstReqListMultiEx.json")
	public String getInvstReqListMultiEx(HttpServletRequest request
									, HttpServletResponse response
									, @RequestBody String pJsonString
									, @ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO
									, @RequestParam Map<String, Object> paramMap
									, ModelMap model) {
		
		logger.debug("p_jsonString=" + pJsonString);
		

		List<String> seqNumList = new ArrayList<String>();
		String seqNumStr = idntMgmtVO.getSeqNumStr();
		logger.debug("seqNumStr:" + seqNumStr);
		
		//seqNumList = idntMgmtVO.getSeqNumStr();
		/*
	    JSONObject jSONObject = new JSONObject();
	    try {
			jSONObject = (JSONObject) new JSONParser().parse(pJsonString);
			idntMgmtVO.setSearchGb((String) jSONObject.get("searchGb"));
			idntMgmtVO.setSearchVal((String) jSONObject.get("searchVal"));
			idntMgmtVO.setInvstType((String) jSONObject.get("invstType"));
			idntMgmtVO.setInvstVal((String) jSONObject.get("invstVal"));
			idntMgmtVO.setReqDoc((String) jSONObject.get("reqDoc"));
			idntMgmtVO.setReqType((String) jSONObject.get("reqType"));
			String temp = (String) jSONObject.get("procDateS");
			
			temp = temp.substring(0,10);
			temp = temp.replace("-", "");
			
			idntMgmtVO.setProcDateS(temp);
			
			temp = (String) jSONObject.get("procDateE");
			temp = temp.substring(0,10);
			temp = temp.replace("-", "");
					
			idntMgmtVO.setProcDateE(temp);
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		
		
		
		LOGGER.info("**************************************************************");
		LOGGER.info("* 가입자확인 엑셀다운Multi START  " + idntMgmtVO.toString());
		LOGGER.info("* 가입자확인 엑셀다운Multi START  " + paramMap.toString());
		LOGGER.info("**************************************************************");
		

		try {
			
			List list = getVoFromMultiJson(seqNumStr, "ALL", IdntMgmtVO.class );
					
			for ( int i = 0 ; i < list.size(); i++)
			{
				IdntMgmtVO vo = (IdntMgmtVO) list.get(i);
				
				seqNumList.add(vo.getSeqNum());

				logger.info("seqNum:" + vo.getSeqNum());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			20210819 소스코드점검 수정
//			e.printStackTrace();
			LOGGER.error("Connection Exception occurred");
		}
		idntMgmtVO.setSeqNumList(seqNumList);
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(idntMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(idntMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			
			//엑셀다운로드(포함된 수사기관명,문서번호) 조회
			List<?> InvstNmValList = idntMgmtService.getInvstNmValListEx(idntMgmtVO, paramMap);

			List<String> sourceFiles = new ArrayList<String>();
			String serverInfo = propertiesService.getString("excelPath");

			
			for ( int i = 0 ; i < InvstNmValList.size(); i++)
			{
				//IdntMgmtVO vo = (IdntMgmtVO) InvstNmValList.get(i);
				Map<String, Object> map = (Map<String, Object>) InvstNmValList.get(i);
				
				//seqNumList.add(vo.getSeqNum());

				logger.info("invstNm:" + map.get("invstNm"));
				logger.info("invstVal:" + map.get("invstVal"));
				
				idntMgmtVO.setInvstVal((String) map.get("invstNm"));//접수처명
				idntMgmtVO.setReqDoc((String)map.get("invstVal"));//문서번호

				List<?> list = idntMgmtService.getInvstReqListSelectedEx(idntMgmtVO, paramMap);
				
				//파일명 깨짐 현상으로 한글부분 제거함
				String strfilename = serverInfo + map.get("invstNm") + "_" + map.get("invstVal") + "_";
				//String strfilename = serverInfo + map.get("invstVal") + "_";
				String strSheetname = "가입자확인";
				
				String[] strHead = {"처리일자","처리번호","식별유형","식별번호","전화번호","요청종류","문서번호","접수처","이메일주소","이름","계약번호","상태","선후불","가입일","해지일","주소","접수처구분","지역",
						"요청자직책","요청자이름","결재권자직책","결재권자이름","사후요청","요청일자","처리자"};
				String[] strValue = {"procDt","seqNum","customerTypeNm","userSsn","subscriberNo","reqTypeNm","invstVal","invstNm","email","cstmrNm","contractNum","subStatusNm","pppo","openDt","tmntDt","cstmrAddr","invstTypeNm","invstLoc",
								"reqOdty","reqUser","appOdtyNm","appUser","reqRsn","reqDttm","procrNm"};

				int[] inWidth = {5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,15000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000};
				int[] inLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
				
				String strFileName = fileDownService.excelSaveProc(strfilename, strSheetname, list, strHead, inWidth, strValue, request, response, inLen);
				sourceFiles.add(strFileName);
				
			}
			
			

			Date toDay = new Date();
			StringBuilder strFileNameZip = new StringBuilder();
			
			strFileNameZip.append(serverInfo);
			
			String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);

			strFileNameZip.append("가입자확인_");
			strFileNameZip.append(request.getSession().getAttribute("SESSION_USER_ID"));
			strFileNameZip.append("_");
			strFileNameZip.append(strToDay);
			strFileNameZip.append(".zip");
			
			
			/*
			FileOutputStream fout = new FileOutputStream(strFileNameZip.toString());
			ZipOutputStream zout = new ZipOutputStream(fout);
			
			for(int i=0; i< sourceFiles.size(); i++) {
				
				ZipEntry zipEntry = new ZipEntry(new File(sourceFiles.get(i)).getName());
				zout.putNextEntry(zipEntry);
				
				

				File inFile = new File(sourceFiles.get(i));
				
				FileInputStream fin = new FileInputStream(inFile);
				byte[] buffer = new byte[1024];
				int length;
				
				while((length = fin.read(buffer)) > 0) {
					zout.write(buffer,0,length);
				}
				
				zout.closeEntry();
				fin.close();
				
				inFile.delete();
				
				
			}
			
			zout.close();
			*/
			
			

			//fileList = file.list();
			int SIZE = 1024;
			byte[] buf = new byte[SIZE];
			String outCompressName =  "/compress.zip";

			FileInputStream fis = null;
			ZipArchiveOutputStream zos = null;
			BufferedInputStream bis = null;
			FileOutputStream fos = null;
			OutputStream bos = null;
			/** 20230518 PMD 조치 */

			try {
				// Zip 파일생성
				fos = new FileOutputStream(strFileNameZip.toString());
				bos = new BufferedOutputStream(fos);
				zos = new ZipArchiveOutputStream(bos);
				for(int i=0; i< sourceFiles.size(); i++) {

					//encoding 설정
					zos.setEncoding("UTF-8");

					//buffer에 해당파일의 stream을 입력한다.
					fis = new FileInputStream(sourceFiles.get(i));
					bis = new BufferedInputStream(fis,SIZE);

					//zip에 넣을 다음 entry 를 가져온다.
					zos.putArchiveEntry(new ZipArchiveEntry(new File(sourceFiles.get(i)).getName()));

					//준비된 버퍼에서 집출력스트림으로 write 한다.
					int len;
					while((len = bis.read(buf,0,SIZE)) != -1){
						zos.write(buf,0,len);
					}

					//bis.close();
					//fis.close();
					zos.closeArchiveEntry();

				}
				//zos.close();

			} catch(IOException e){
//				20210819 소스코드점검 수정
//				e.printStackTrace();
				LOGGER.error("Connection Exception occurred");
			} finally{
				try {
					if (zos != null) {
						zos.close();
					}
					if (fis != null) {
						fis.close();
					}
					if (bis != null) {
						bis.close();
					}
					if (bos != null) {
						bos.close();
					}
					if (fos != null) {
						fos.close();
					}
				} catch(IOException e){
//					20210819 소스코드점검 수정
//					e.printStackTrace();
					LOGGER.error("Connection Exception occurred");
				}
			}			
			
			
			
			
			
			
			
			file = new File(strFileNameZip.toString());
			
			response.setContentType("applicaton/zip");
			
			String encodingFileName = "";

			int serverInfoLen = Integer.parseInt(propertiesService.getString("excelPathLen"));
			
			try {
				encodingFileName = URLEncoder.encode(strFileNameZip.toString().substring(serverInfoLen), "UTF-8");
			} catch (UnsupportedEncodingException uee) {
				encodingFileName = strFileNameZip.toString();
			}
			

			response.setHeader("Cache-Control", "");
			response.setHeader("Pragma", "");

			response.setContentType("Content-type:application/octet-stream;");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			
			
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			out = response.getOutputStream();
			
			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();
				
				paramMap.put("FILE_NM"   ,file.getName());              //파일명
				paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   ,"CMN");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");
 			
		} catch (Exception e) {
 			 			
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
 			resultMap.put("msg", "");
 			throw new MvnoErrorException(e);
		} finally {
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
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 가입자조회
	 */
	@RequestMapping(value = "/rcp/idntmgmt/getInvstCusInfo.json")
	public String getInvstCusInfo(HttpServletRequest request
								, HttpServletResponse response
								, @ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO
								, @RequestParam Map<String, Object> paramMap
								, ModelMap model) {
		LOGGER.info("**************************************************************");
		LOGGER.info("* 가입자확인 조회 : " + idntMgmtVO.toString());
		LOGGER.info("**************************************************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(idntMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new EgovBizException("권한이 없습니다.");
			}
			
			List<?> resultList = idntMgmtService.getInvstCusInfo(idntMgmtVO, paramMap);
			
			resultMap = makeResultMultiRow(paramMap, resultList);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping(value = "/rcp/idntmgmt/savInvstReq.json")
	public String savInvstReq(HttpServletRequest request
							, HttpServletResponse response
							, @RequestBody String pJsonString
							, @ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO
							, @RequestParam Map<String, Object> paramMap
							, ModelMap model){
		
		logger.debug("p_jsonString=" + pJsonString);
		LOGGER.info("**************************************************************");
		LOGGER.info("* 가입자 확인 요청 이력 저장 START  ");
		LOGGER.info("**************************************************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(idntMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new EgovBizException("권한이 없습니다.");
			}

			List list = getVoFromMultiJson(pJsonString, "ALL", IdntMgmtVO.class );
			
			//사용자 정지상태 변경
			for ( int i = 0 ; i < list.size(); i++)
			{
				IdntMgmtVO vo = (IdntMgmtVO) list.get(i);
				vo.setSessionUserId(idntMgmtVO.getSessionUserId());
				
				logger.info("건수 >>>" + list.size());

				idntMgmtService.savInvstReq(vo);
			}
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
						
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	 		resultMap.put("msg", "");
	 		throw new MvnoErrorException(e);
	 	}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	@RequestMapping(value = "/rcp/idntmgmt/getBeforeSaveCheck.json")
	public String getBeforeSaveCheck(HttpServletRequest request
							, HttpServletResponse response
							, @RequestBody String pJsonString
							, @ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO
							, @RequestParam Map<String, Object> paramMap
							, ModelMap model){
		
		logger.debug("p_jsonString=" + pJsonString);
		LOGGER.info("**************************************************************");
		LOGGER.info("* 가입자 확인 요청 이력 저장 전 중복 체크 START  ");
		LOGGER.info("**************************************************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int count = 0;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(idntMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new EgovBizException("권한이 없습니다.");
			}

			List list = getVoFromMultiJson(pJsonString, "ALL", IdntMgmtVO.class );
			
			//사용자 정지상태 변경
			for ( int i = 0 ; i < list.size(); i++)
			{
				IdntMgmtVO vo = (IdntMgmtVO) list.get(i);
				vo.setSessionUserId(idntMgmtVO.getSessionUserId());
				
				logger.info("건수 >>>" + list.size());
				
				count = 0;

				count = idntMgmtService.getBeforeSaveCheck(vo);
				if(count > 0) {
					break;
				}
			}
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("count", count);
				
		} catch (Exception e) {
						
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	 		resultMap.put("msg", "");
	 		throw new MvnoErrorException(e);
	 	}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	

	/**
	 * @Description : 엑셀 가입자 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/idntmgmt/regInvstUpList.json")
	public String regInvstUpList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO,
								ModelMap model,
								@RequestParam Map<String, Object> pRequestParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(idntMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			if(!"10".equals(idntMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + idntMgmtVO.getFileName();

			String[] arrCell = {"searchGb", "searchVal", "reqType", "invstType", "invstNm", "invstLoc", "invstVal" , "reqOdty" , "reqUser" , "AppOdty", "AppOdtyNm" , "AppUser" , "ReqRsn" , "ReqDttm"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.rcp.idntMgmt.vo.IdntMgmtVO", sOpenFileName, arrCell);
						
			List<Object> list = new ArrayList<Object>();
			
			for(int i=0; i<uploadList.size(); i++){
				IdntMgmtVO vo = (IdntMgmtVO) uploadList.get(i);
				vo.setExcelYn("Y");
				
				List<?> resultList = idntMgmtService.getInvstCusInfo(vo, pRequestParamMap);
				
				for(int j=0; j<resultList.size(); j++){
					list.add(resultList.get(j));
				}
			}
			logger.info("list.size() ::: " + list.size());
			resultMap =  makeResultMultiRowNotEgovMap(pRequestParamMap, list, list.size());
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
			if(e.getMessage() == null || "".equals(e.getMessage())){
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	

	/**
	 * 마케팅동의 등록 양식다운
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/rcp/idntmgmt/regInvstTempExcelDown.json")
	public String regInvstTempExcelDown(@ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO,
										HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam Map<String, Object> pRequestParamMap,
										ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(idntMgmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(idntMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = new ArrayList<MrktMgmtVO>();
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "가입자확인등록양식_";//파일명
			String strSheetname = "가입자확인등록양식";//시트명
			
			String [] strHead = {"검색구분", "검색어", "요청종류", "접수처", "접수처명", "지역", "문서번호" , "요청자직책" , "요청자이름" , "결재권자직책코드", "결재권자직책명" , "결재권자이름" , "사후요청" , "요청일자"};
			String [] strValue = {};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 7000, 5000};
			int[] intLen = {};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
			
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = response.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
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
	 * 번호이동 상세 이력
	 */
	@RequestMapping(value = "/rcp/idntmgmt/getInvstCusInfoDtl.json")
	public String getInvstCusInfoDtl(HttpServletRequest request
								, HttpServletResponse response
								, @ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO
								, @RequestParam Map<String, Object> paramMap
								, ModelMap model) {
		LOGGER.info("**************************************************************");
		LOGGER.info("* 번호이동 상세 이력 조회 : " + idntMgmtVO.toString());
		LOGGER.info("**************************************************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(idntMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new EgovBizException("권한이 없습니다.");
			}
			
			List<?> resultList = idntMgmtService.getInvstCusInfoDtl(idntMgmtVO, paramMap);
			
			resultMap = makeResultMultiRow(paramMap, resultList);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 번호이동 상세이력 엑셀다운로드
	 */
	@RequestMapping(value = "/rcp/idntmgmt/getInvstCusInfoDtlListEx.json")
	public String getInvstCusInfoDtlListEx(HttpServletRequest request
									, HttpServletResponse response
									, @ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO
									, @RequestParam Map<String, Object> paramMap
									, ModelMap model) {
		LOGGER.info("**************************************************************");
		LOGGER.info("* 번호이동 상세이력 엑셀다운 START  " + idntMgmtVO.toString());
		LOGGER.info("**************************************************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(idntMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(idntMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = idntMgmtService.getInvstCusInfoDtl(idntMgmtVO, paramMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strfilename = serverInfo + "번호이동 상세이력_";
			String strSheetname = "번호이동 상세이력";
			
			String[] strHead = {"계약번호","전화번호","이벤트 구분","이벤트발생일자"};
			String[] strValue = {"contractNum","subscriberNoMsk","evntNm","evntChangeDate"};

			int[] inWidth = {5000,5000,5000,5000};
			int[] inLen = {0,0,0,0};
			
			String strFileName = fileDownService.excelDownProc(strfilename, strSheetname, list, strHead, inWidth, strValue, request, response, inLen);
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			out = response.getOutputStream();
			
			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();
				
				paramMap.put("FILE_NM"   ,file.getName());              //파일명
				paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   ,"CMN");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");
 			
		} catch (Exception e) {
 			 			
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
 			resultMap.put("msg", "");
 			throw new MvnoErrorException(e);
		} finally {
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
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	
	

	@RequestMapping(value = "/rcp/idntmgmt/delinvstReq.json")
	public String deleteInvstReq(HttpServletRequest request
							, HttpServletResponse response
							, @RequestBody String pJsonString
							, @ModelAttribute("searchVO") IdntMgmtVO idntMgmtVO
							, @RequestParam Map<String, Object> paramMap
							, ModelMap model){
		
		logger.debug("p_jsonString=" + pJsonString);
		
		//pJsonString = pJsonString.substring(0,pJsonString.indexOf(", SESSION_USER_ID"));
		//logger.debug("p_jsonString=" + pJsonString);
		
		

		String seqNumStr = idntMgmtVO.getSeqNumStr();
		logger.debug("seqNumStr:" + seqNumStr);
		
		
		
		LOGGER.info("**************************************************************");
		LOGGER.info("* 가입자 확인 삭제 START  ");
		LOGGER.info("**************************************************************");

		List<String> seqNumList = new ArrayList<String>();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(idntMgmtVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(request.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new EgovBizException("권한이 없습니다.");
			}


			List list = getVoFromMultiJson(seqNumStr, "ALL", IdntMgmtVO.class );
			
//			List list = getVoFromMultiJson(pJsonString, "ALL", IdntMgmtVO.class );
			

			for ( int i = 0 ; i < list.size(); i++)
			{
				IdntMgmtVO vo = (IdntMgmtVO) list.get(i);
				
				
				vo.setSessionUserId(idntMgmtVO.getSessionUserId());
				
				logger.info("건수 >>>" + list.size());

				idntMgmtService.savMspInvstReqHist(vo);
				idntMgmtService.deleteMspInvstReqMst(vo);
				
			}
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
						
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	 		resultMap.put("msg", "");
	 		throw new MvnoErrorException(e);
	 	}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
}
