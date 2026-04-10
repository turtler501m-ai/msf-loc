/**
 * 
 */
package com.ktis.msp.cmn.help.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.help.service.HelpMgmtService;
import com.ktis.msp.cmn.help.vo.HelpMgmtVO;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.org.common.vo.FileInfoVO;

import egovframework.rte.fdl.property.EgovPropertyService;

import com.ktis.msp.base.exception.MvnoErrorException;

/**
 * @Class Name : HelpMgmtController
 * @Description : 도움말관리
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 
 * @
 * @author : 심정보
 * @Create Date : 
 */
@Controller
public class HelpMgmtController extends BaseController {

	@Autowired
	private HelpMgmtService helpService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private FileUpload2Service  fileUp2Service;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	public HelpMgmtController() {
		setLogPrefix("[HelpMgmtController] ");
	}
	
	/**
	 * 도움말조회(팝업)
	 * @param request
	 * @param response
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cmn/help/help.do")
	public ModelAndView viewHelp(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("helpMgmtVO") HelpMgmtVO searchVO,
								ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("도움말 팝업조회 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		logger.debug("request=" + request);
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			
			modelAndView.setViewName("/cmn/help/msp_cmn_pu_9001");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	
	/**
	 * 도움말
	 * @param request
	 * @param response
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cmn/help/helpMgmt.do")
	public ModelAndView helpMgmt(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("helpMgmtVO") HelpMgmtVO searchVO,
								ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("도움말 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
						
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("/cmn/help/msp_cmn_bs_9001");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 도움말목록조회
	 * @param request
	 * @param response
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cmn/help/selectHelpMgmtList.json")
	public String getHelpMgmtList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("helpMgmtVO") HelpMgmtVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("도움말 목록조회"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			List<HelpMgmtVO> resultList = helpService.getHelpMgmtList(searchVO);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, totalCount);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	/**
	 * UUID 생성
	 */
	public static String generationUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 파일업로드
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/cmn/help/fileUpLoad.do")
	public String fileUpLoad(ModelMap model, 
								HttpServletRequest pRequest, 
								HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일업로드"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		FileInfoVO fileInfoVO = new FileInfoVO();
		
		String lSaveFileName = "";
		String lFileNamePc = "";
		String menuId = "";
		
		Integer filesize = 0;
		
		try{
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(fileInfoVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			if( pRequestParamMap.get("menuId") == null || "".equals(pRequestParamMap.get("menuId")) ){
				throw new MvnoRunException(-1, "메뉴ID가 존재 하지 않습니다.");
			}
			menuId = pRequestParamMap.get("menuId").toString();
			
			fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			fileInfoVO.setRvisnId(loginInfo.getUserId());
			
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);
			
			//--------------------------------
			// upload base directory를 구함
			// 존재하지 않으면 exception 발생
			//--------------------------------
			String lBaseDir = propertyService.getString("helpPath");
			
			for (FileItem item : items) {
				
				//--------------------------------
				// 파일 타입이 아니면 skip
				//--------------------------------
				if (item.isFormField())
					continue;
				
				//--------------------------------
				// 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
				//--------------------------------
				if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) )
					continue;
				
				/** 20230518 PMD 조치 */
				InputStream filecontent = null;
				File f = null;						
				OutputStream fout = null;	
				try
				{
					//--------------------------------
					// 모듈별 directory가 존재하지 않으면 생성
					//--------------------------------
					File lDir = new File(lBaseDir );
					if ( !lDir.exists())
					{
						lDir.mkdirs();
					}
					
					//----------------------------------------------------------------
					// upload 이름 구함
					//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file"))
					{
						//----------------------------------------------------------------
						// 파일 이름 구함
						//----------------------------------------------------------------
						lFileNamePc = FilenameUtils.getName(item.getName());
						
						fileInfoVO.setAttRout(lBaseDir);
						
						lSaveFileName = fileUp2Service.getAlternativeFileName(  lBaseDir   ,  lFileNamePc);
						String lTransferTargetFileName = lBaseDir  + "/" + lSaveFileName;
						
						//----------------------------------------------------------------
						// 파일 write
						//----------------------------------------------------------------
						filecontent = item.getInputStream();
						f=new File(lTransferTargetFileName);						
						fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;
						while((len=filecontent.read(buf))>0) {
							fout.write(buf,0,len);
							filesize+=len;
						}
						
					}
				}catch (Exception e) {
					throw new MvnoErrorException(e);
				} finally {
					if (fout != null) {
						try {
							fout.close();
						} catch (Exception e) {
							throw new MvnoErrorException(e);
						}
					}
					if (filecontent != null) {
						try {
							filecontent.close();
						} catch (Exception e) {
							throw new MvnoErrorException(e);
						}
					}
				}
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
		} catch (Exception e) {
			resultMap.put("state", false);
			resultMap.put("name", lFileNamePc.replace("'","\\'"));
			resultMap.put("size", filesize);
			
			model.addAttribute("result", resultMap);
			return "jsonViewArray";
		}
		
		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(lSaveFileName);
		fileInfoVO.setOrgnId(menuId);
		fileInfoVO.setAttSctn("HLP");
		
		HelpMgmtVO helpMgmtVo = new HelpMgmtVO();
		
		helpMgmtVo.setMenuId(menuId);
		
		List<HelpMgmtVO> helpMgmtList = helpService.getHelpMgmtList(helpMgmtVo);
		
		int fileSeq = 0;
		
		if(helpMgmtList.size() > 0){
			
			for(HelpMgmtVO item : helpMgmtList) {
				if(fileSeq != Integer.parseInt(item.getFileSeq())){
					break;
				}
				
				fileSeq++;
			}
		}
		
		fileInfoVO.setFileSeq(String.valueOf(fileSeq));
		
		//파일 테이블 insert
		helpService.insertFile(fileInfoVO);
		
		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", filesize);
		
		model.addAttribute("result", resultMap);
		return "jsonViewArray";
	}
	
	/**
	 * 파일 보기
	 */
	@RequestMapping("/cmn/help/viewFile.json")
	public String viewFile( Model model,
							HttpServletRequest request,
							HttpServletResponse response,
							@RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일 보기"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		ByteArrayOutputStream baos = null;
		String returnMsg = null;
		String strFileName = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if( pReqParamMap.get("fileId") == null || "".equals(pReqParamMap.get("fileId")) ){
				throw new MvnoRunException(-1, "파일경로가 존재 하지 않습니다.");
			}
			
			strFileName = helpService.getHelpFileNm(URLDecoder.decode((String) pReqParamMap.get("fileId"),"UTF-8"));
			
			file = new File(strFileName);
			
			in = new FileInputStream(file);
			
			baos = new ByteArrayOutputStream();
			
			byte[] buf = new byte[1024];
			
			int readlength = 0;
			
			while( (readlength =in.read(buf)) != -1 )
			{
				baos.write(buf,0,readlength);
			}
			
			byte[] imgbuf = null;
			
			imgbuf = baos.toByteArray();
			
			baos.close();
			in.close();
			
			int length = imgbuf.length;
			
			out = response.getOutputStream();
			
			out.write(imgbuf , 0, length);
			out.close();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "성공");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
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
			if (baos != null) {
				try {
					baos.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
}
