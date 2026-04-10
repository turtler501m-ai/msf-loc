package com.ktis.msp.m2m.usimprdtgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.ktis.msp.cmn.board.service.BoardService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.m2m.usimprdtgmt.service.UsimPrdtMngService;
import com.ktis.msp.m2m.usimprdtgmt.vo.UsimPrdtMngVO;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.common.vo.FileInfoVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
@Controller
public class UsimPrdtMngController extends BaseController {
	
	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
    protected EgovPropertyService propertyService;

	@Autowired
	private UsimPrdtMngService usimPrdtMngService;

	@Autowired
	private FileUpload2Service  fileUp2Service;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	
	/**
	 * @Description : M2M USIM 제품 관리 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimPrdtMng/usimPrdtMng.do")
	public ModelAndView usimPrdtMng(@ModelAttribute("searchVO") UsimPrdtMngVO searchVO, 
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
			modelAndView.setViewName("m2m/usimprdtgmt/usimPrdtMng");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
		
	
	/**
	 * @Description : M2M USIM 제품 관리 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimPrdtMng/usimPrdtMngList.json")
	public String usimPrdtMngList(@ModelAttribute("searchVO") UsimPrdtMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 제품관리 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = usimPrdtMngService.usimPrdtMngList(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000011", "M2M USIM 제품 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	/**
	 * @Description : M2M USIM 제품 이력 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimPrdtMng/usimPrdtMngHist.json")
	public String usimPrdtMngHist(@ModelAttribute("searchVO") UsimPrdtMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 제품 이력 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = usimPrdtMngService.usimPrdtMngHist(searchVO, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000011", "M2M USIM 제품 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	/**
	 * @Description : M2M USIM 제품 등록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimPrdtMng/usimPrdtInsert.json")
	public String usimPrdtInsert(@ModelAttribute("searchVO") UsimPrdtMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap,
					FileInfoVO fileInfoVO) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 제품 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			usimPrdtMngService.usimPrdtInsert(searchVO, pRequestParamMap);
			
			String rprsPrdtId = searchVO.getRprsPrdtId();
			
			fileInfoVO.setOrgnId(rprsPrdtId);
			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_0())){
				orgCommonService.updateFile(fileInfoVO);
			}

			

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000011", "M2M USIM 제품 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}		

	
	/**
	 * @Description : M2M USIM 제품 수정
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimPrdtMng/pusimPrdtUpdate.json")
	public String pusimPrdtUpdate(@ModelAttribute("searchVO") UsimPrdtMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap,
					FileInfoVO fileInfoVO) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M2M USIM 제품 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			usimPrdtMngService.usimPrdtUpdate(searchVO, pRequestParamMap);

			

			String rprsPrdtId = searchVO.getRprsPrdtId();
			
			fileInfoVO.setOrgnId(rprsPrdtId);
			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_0())){
				orgCommonService.updateFile(fileInfoVO);
			}

			
			
			
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					"", "M2M1000011", "M2M USIM 제품 관리"))
			{
                throw new MvnoErrorException(e);
			} 	
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	

	/**
	 * @Description : M2M USIM 제품 관리 엑셀다운
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/m2m/usimPrdtMng/usimPrdtMngListEx.json")
	public String usimPrdtMngListEx(@ModelAttribute("searchVO") UsimPrdtMngVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제휴부가서비스관리 엑셀다운 START."));
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
			excelMap.put("MENU_NM","M2M USIM 제품관리");
			
			String searchVal = "대표제품ID:"+searchVO.getRprsPrdtId()+
					"|제조사:"+searchVO.getPrdtCode()+
					"|제품명:"+searchVO.getPrdtNm()+
					"|사용여부:"+searchVO.getUseYn()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			//----------------------------------------------------------------
				
			List<?> usimPrdtMngListEx = usimPrdtMngService.usimPrdtMngListEx(searchVO, pRequestParamMap);
			
			String serverInfo = "";
			try {
				serverInfo = propertiesService.getString("excelPath");
			} catch(Exception e1) {
				logger.error("ERROR Exception");
				throw new MvnoErrorException(e1);
			}
			
			String strFilename = serverInfo  + "M2M_USIM_제품관리_";//파일명
			String strSheetname = "M2M_USIM_제품관리";//시트명
			
			String [] strHead = {"대표제품ID","제조사","제품명","단가","이미지파일명","사용여부","적용시작일시","적용종료일시"
								,"등록자"}; //9
			String [] strValue = {"rprsPrdtId","prdtCode","prdtNm","outUnitPric","orgImgFile","useYn","unitPricApplDttm","unitPricExprDttm"
								, "usrNm"}; //9
			int[] intWidth = {9000, 9000, 9000, 6000, 6000, 6000, 9000, 9000
							 ,6000};	
			int[] intLen = {0, 0, 0, 1, 0, 0, 0, 0, 0}; // 9

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값

			logger.info("#################### 데이터 엑셀로 변환 시작 #####################");
			String strFileName = "";
			

			/*try {
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}*/
			strFileName = fileDownService.excelDownProc(strFilename, strSheetname, usimPrdtMngListEx, strHead, intWidth, strValue, request, response, intLen);

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
	 * @Description :  파일업로드
	 * @Param  : model
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 10. 15.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/m2m/usimPrdtMng/fileUpLoad.do")
	public String fileUpLoad(ModelMap model, 
									HttpServletRequest pRequest, 
									HttpServletResponse pResponse, 
									@RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		FileInfoVO fileInfoVO = new FileInfoVO();
		
		String lSaveFileName = "";
		String lFileNamePc = "";
		
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
			
			fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			fileInfoVO.setRvisnId(loginInfo.getUserId());
			
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);

			//--------------------------------
			// upload base directory를 구함
			// 존재하지 않으면 exception 발생
			//--------------------------------
			String lBaseDir = propertyService.getString("orgPath");
			//--------------------------------
			// 모듈별 directory가 존재하지 않으면 생성
			//--------------------------------
			File lDir = new File(lBaseDir );
			if ( !lDir.exists())
			{
				lDir.mkdirs();
			}

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

				try
				{

					//----------------------------------------------------------------
					// upload 이름 구함
					//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file"))
					{
						//----------------------------------------------------------------
						// UPLOAD 확장자 체크
						//----------------------------------------------------------------
						lFileNamePc = FilenameUtils.getName(item.getName());
						int pos = lFileNamePc.lastIndexOf(".");
						String strExt = lFileNamePc.substring( pos + 1).toUpperCase(); //확장자
						
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("ext", strExt);
						
						//v2018.11 PMD 적용 소스 수정
						//boolean bExtChk = false;
				    	int iCnt = boardService.getFileExtList(map);
												
						if(iCnt < 1) {
							resultMap.put("state", false);
							resultMap.put("name", lFileNamePc.replace("'","\\'"));
							resultMap.put("size",  111);

							model.addAttribute("result", resultMap);
							return "jsonViewArray";
						}
						
						//----------------------------------------------------------------
						// 파일 이름 구함
						//----------------------------------------------------------------
//						lFileNamePc = FilenameUtils.getName(item.getName());

						fileInfoVO.setAttRout(lBaseDir);

//						System.out.println("UUID -> " + generationUUID().replaceAll("-",""));

						lSaveFileName = fileUp2Service.getAlternativeFileName(  lBaseDir   ,  lFileNamePc);
						String lTransferTargetFileName = lBaseDir  + "/" + lSaveFileName;

						//----------------------------------------------------------------
						// 파일 write
						//----------------------------------------------------------------
						InputStream filecontent = item.getInputStream();
						File f=new File(lTransferTargetFileName);

						OutputStream fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;

						try {

							while((len=filecontent.read(buf))>0) {
								fout.write(buf,0,len);
								filesize+=len;
							}
						} catch (Exception e1) {
						    throw new MvnoErrorException(e1);
						} finally
						{
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
						
					}
				}catch (Exception e) {
					throw new MvnoErrorException(e);
				}
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
		} catch (Exception e) {
			resultMap.put("state", false);
			resultMap.put("name", lFileNamePc.replace("'","\\'"));
			resultMap.put("size",  111);
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "M2M1000011", "M2M USIM 제품 관리"))
			{
			   //v2018.11 PMD 적용 소스 수정
			   throw new MvnoErrorException(e);
			}

			model.addAttribute("result", resultMap);
			return "jsonViewArray";
		}

		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(lSaveFileName);
		fileInfoVO.setFileSeq("9999999999");//초기값 조직ID
		fileInfoVO.setAttSctn("M2M");//M2M USIM IMAGE 파일첨부

		//파일 테이블 insert
		orgCommonService.insertFile(fileInfoVO);

		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", "" + 111);

		model.addAttribute("result", resultMap);
		return "jsonViewArray";
	}

	/**
	 * @Description : 파일 삭제 기능
	 * @Param  : SalePlcyMgmtVo
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/m2m/usimPrdtMng/deleteFile.json")
	public String deleteFile( @ModelAttribute("searchVO") UsimPrdtMngVO searchVO, 
							Model model, 
							HttpServletRequest request, 
							HttpServletResponse response,
							@RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일 삭제 START."));
		logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		File file = null;
		String returnMsg = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pReqParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			//파일서버에 이미지 삭제
			String strFileName = orgCommonService.getFileNm(searchVO.getFileId1());

			file = new File(strFileName);

			file.delete();

			int delcnt = orgCommonService.deleteFile(searchVO.getFileId1());
			logger.info(generateLogMsg("삭제건수 = " + delcnt));

			FileInfoVO fileInfoVO = new FileInfoVO();
			
			fileInfoVO.setOrgnId(searchVO.getRprsPrdtId());
			
			fileInfoVO.setAttSctn("M2M");
			int fileCnt = orgCommonService.getFileCnt(fileInfoVO);
			logger.info(generateLogMsg("getFileCnt(fileInfoVO); fileCnt :  = " + fileCnt));

			resultMap.put("fileTotCnt", String.valueOf(fileCnt));
			resultMap.put("deleteCnt", String.valueOf(delcnt));

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "파일삭제성공");
			
		} catch (Exception e) {
			resultMap.put("fileTotCnt", "0");
			resultMap.put("deleteCnt", "0");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					returnMsg, "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}		
		}
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
	}


	/**
	 * @Description : UUID 생성
	 * @Param  :
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 8. 13.
	 */
	public static String generationUUID(){
		  return UUID.randomUUID().toString();
		 }

	/**
	 * @Description : 첨부된 파일의 명을 찾아온다.
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 8. 29.
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/m2m/usimPrdtMng/getImageFile.json")
	public String getImageFile(@ModelAttribute("searchVO") UsimPrdtMngVO searchVO, 
							FileInfoVO fileInfoVO, 
							Model model, 
							HttpServletRequest request, 
							HttpServletResponse response,
							@RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("첨부 파일 명 찾기 START."+fileInfoVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			List<?> resultList = usimPrdtMngService.getImageFile(searchVO);

			for (int i = 0; i < resultList.size(); i++) {
				EgovMap tempMap = (EgovMap)resultList.get(i);
				
				if(i==0)
				{
					resultMap.put("fileNm", tempMap.get("fileNm"));
					resultMap.put("fileId", tempMap.get("fileId"));
					
					
				}
			}
			resultMap.put("fileTotalCnt", String.valueOf(resultList.size()));
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.debug("result = " + resultMap);
		} catch (Exception e) {			
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					e.getMessage(), "M2M1000011", "M2M USIM 제품 관리"))
			{
				throw new MvnoErrorException(e);
			}			
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 첨부된 파일을 리턴한다.
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2014. 8. 29.
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/m2m/usimPrdtMng/getImageFile.do")
	public String getImageFileLink(@ModelAttribute("searchVO") UsimPrdtMngVO searchVO, 
							FileInfoVO fileInfoVO, 
							Model model, 
							HttpServletRequest request, 
							HttpServletResponse response,
							@RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("첨부 파일 찾기 START."+fileInfoVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

	    FileInputStream f = null;
	    ServletOutputStream bout = null;

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			List<?> resultList = usimPrdtMngService.getImageFile(searchVO);
			
			/** 20230518 PMD 조치 */
			response.setContentType("image/jpg");
	    	bout = response.getOutputStream();

		    
			for (int i = 0; i < resultList.size(); i++) {
				EgovMap tempMap = (EgovMap)resultList.get(i);
				
				if(i==0)
				{
					resultMap.put("fileNm", tempMap.get("fileNm"));
					resultMap.put("fileId", tempMap.get("fileId"));
					
					String sSourceFile = (String)tempMap.get("attRout") + tempMap.get("fileNm");
					String sTargetFile = "";
					
				    //파일의 경로
				    //String imgpath = map.get("path")+File.separator+result.get("fileName");
				    

					File srcFile = new File(sSourceFile);
					if(srcFile.exists()) {
					    f = new FileInputStream(sSourceFile);
					    int length;
					    byte[] buffer = new byte[10];
					    while((length=f.read(buffer)) != -1){
					    	bout.write(buffer,0,length);
					    }
					}
					
					
					logger.error(generateLogMsg("===sSourceFile="+sSourceFile));
					logger.error(generateLogMsg("===sTargetFile="+sTargetFile));
				}
			}

			logger.debug("result = " + resultMap);
		} catch (Exception e) {			
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					e.getMessage(), "M2M1000011", "M2M USIM 제품 관리"))
			{
				throw new MvnoErrorException(e);
			}			
		}finally {
			if (f != null) {
				try {
					f.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (bout != null) {
				try {
					bout.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}			
		}

		return null;
	}

	
}
