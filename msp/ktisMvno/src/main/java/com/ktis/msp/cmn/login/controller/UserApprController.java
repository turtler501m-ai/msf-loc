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
package com.ktis.msp.cmn.login.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.crypto.encryptor.sha.SHA512Encryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.board.service.BoardService;
import com.ktis.msp.cmn.cdmgmt.service.CmnCdMgmtService;
import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.UserApprService;
import com.ktis.msp.cmn.login.vo.UserApprVO;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.vo.MsgQueueReqVO;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name :
 * @Description :
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.02.13           최초생성
 *
 * @author
 * @version 1.0
 * @see
 *
 */


/**
 * <PRE>
 * 1. ClassName	:
 * 2. FileName	: LoginController.java
 * 3. Package	: com.ktis.msp.cmn.login.controller
 * 4. Commnet	:
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:57:21
 * </PRE>
 */
@Controller
public class UserApprController   extends BaseController {

	protected Log log = LogFactory.getLog(this.getClass());

	@Autowired
	protected MessageSource messageSource;


	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Autowired
	private SHA512Encryptor  sHA512Encryptor;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CmnCdMgmtService cmnCdMgmtService;
	
	@Autowired
	private UserApprService userApprService;
	
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private FileUpload2Service  fileUp2Service;
	
	@Autowired
	private OrgCommonService orgCommonService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private UserInfoMgmtService userInfoMgmtService;

	/**
	 * <PRE>
	 * 1. MethodName: UserApprController
	 * 2. ClassName	: UserApprController
	 * 3. Commnet	:
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2019. 06. 19.
	 * </PRE>
	 * 		@return String
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@param model
	 * 		@return
	 * 		@throws Exception
	 */

	
	/**
	 * @Description : 회원가입신청 팝업 호출(사용자본인)
	 * @Param  :
	 * @Return : String
	 * @Author : 권오승
	 * @Create Date : 2019. 06. 19.
	 */
	@RequestMapping(value="/cmn/login/userApprPopup.do")
	public ModelAndView passChgPopup(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("회원가입 신청 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		log.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			modelAndView.setViewName("cmn/login/user_appr_popup");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	/**
	 * 공통코드Combo
	 * 
	 * @return List
	 * @author kos9878
	 * @version 1.0
	 * @created 2019.06.19
	 * @updated
	 */
	@RequestMapping("/cmn/login/getCommCombo.json")
	public String getCommCombo(HttpServletRequest paramReq, HttpServletResponse paramRes, @ModelAttribute("cmnCdMgmtVo") CmnCdMgmtVo cmnCdMgmtVo, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<?> resultList = cmnCdMgmtService.getCommCombo(cmnCdMgmtVo);
			
			resultMap =  makeResultMultiRow(cmnCdMgmtVo, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, paramReq.getServletPath(), "", "", "", ""))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	/**
	 * 신청가능한 id인지 중복체크
	 * 
	 * @return String
	 * @author kos9878
	 * @version 1.0
	 * @created 2019.06.19
	 * @updated
	 */
	@RequestMapping("/cmn/login/getIdCheck.json")
	public String getIdCheck(HttpServletRequest paramReq, HttpServletResponse paramRes, @ModelAttribute("userApprVO") UserApprVO userApprVO, ModelMap model) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 ID 중복체크 START."));
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("==============userApprVO.getUserId()==" + userApprVO.getUsrId() + "================"));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			userApprVO.setUsrId(userApprVO.getUsrId().toUpperCase());
			String AppYn = userApprService.getIdCheck(userApprVO.getUsrId());
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("result", AppYn);
		}
		catch(Exception e) {
			
			if (!getErrReturn(e, (Map<String, Object>) resultMap, paramReq.getServletPath(), "", "", "", ""))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	@RequestMapping(value = "/cmn/login/msp_org_pu_1010_1.do", method={POST, GET})
	public String searchPopupOrg1010(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("본사/대리점 조직팝업 화면 START." + request.getParameter("aaaa")));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		
		model.addAttribute("orgnLvlCd", loginInfo.getUserId());
		model.addAttribute("typeCd", request.getParameter("typeCd"));
		
		return "org/orgmgmt/msp_org_pu_1010_1";
	}
	
	@RequestMapping("/cmn/login/getHeadAgencyOrgnList.json")
	public String getHeadAgencyOrgnList(OrgMgmtVO orgMgmtVO, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대리점 조직 리스트 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인정보체크 */
			//LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(orgMgmtVO);
			
			List<?> resultList = userApprService.getHeadAgencyOrgnList(orgMgmtVO);
			
			//Masking
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("corpRegNum", "CORPORATE");
			maskFields.put("rprsenNm", "CUST_NAME");
			maskFields.put("telnum", "TEL_NO");
			maskFields.put("email", "EMAIL");
			maskFields.put("addr1", "ADDR");
			maskFields.put("addr2", "PASSWORD");
//			maskFields.put("respnPrsnId", "CUST_NAME");
//			maskFields.put("respnPrsnNum", "SYSTEM_ID");
//			maskFields.put("respnPrsnMblphn", "MOBILE_PHO");
//			maskFields.put("respnPrsnEmail", "EMAIL");
			
			maskingService.setMask(resultList, maskFields, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
//			logger.info(generateLogMsg("result = " + resultMap));
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		return "jsonView";
	}
	/**
	 * @Description :  파일업로드
	 * @Param  : model
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2019. 06. 19.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/cmn/login/fileUpLoad.do")
	public String fileUpUsingService(ModelMap model, 
									HttpServletRequest pRequest, 
									HttpServletResponse pResponse, 
									@RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserApprVO userApprVo = new UserApprVO();
		
		String lSaveFileName = "";
		String lFileNamePc = "";
		
		Integer filesize = 0;
		
		try{
			
			userApprVo.setRegstId("system");    /** 사용자ID */
			userApprVo.setRvisnId("system");
			
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);

			//--------------------------------
			// upload base directory를 구함
			// 존재하지 않으면 exception 발생
			//--------------------------------
			
			String lBaseDir = propertyService.getString("orgPath");
			logger.info(generateLogMsg("===============파일경로======"+lBaseDir + "================================"));
			//String lBaseDir = (String) pRequestParamMap.get("orgId");
			if ( !new File(lBaseDir ).exists())
			{
				logger.info(generateLogMsg("=================================파일경로X================================"));
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
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

						userApprVo.setAttRout(lBaseDir);

//						System.out.println("UUID -> " + generationUUID().replaceAll("-",""));

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
				}finally{
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
			resultMap.put("size",  111);
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1010011", "업무게시판"))
			{
			   //v2018.11 PMD 적용 소스 수정
			   throw new MvnoErrorException(e);
			}

			model.addAttribute("result", resultMap);
			return "jsonViewArray";
		}

		//파일 테이블에 데이터 저장하고.
		userApprVo.setFileId(generationUUID().replaceAll("-",""));
		userApprVo.setFileNm(lSaveFileName);
		userApprVo.setFileSeq("9999999999");//초기값 조직ID
		userApprVo.setAttSctn("NTC");//공지사항 파일첨부
		logger.info("fileInfoVO.getFile_upload1_count() >>>> " +pRequestParamMap.get("file_upload1_count"));
		logger.info("fileInfoVO.getFileId() >>> " + userApprVo.getFileId());
		//파일 테이블 insert
		userApprService.insertFile(userApprVo);

		resultMap.put("state", true);
		resultMap.put("name", userApprVo.getFileId());
		resultMap.put("fileId", lSaveFileName);
		resultMap.put("size", "" + 111);

		model.addAttribute("result", resultMap);
		return "jsonViewArray";
	}
	
	/**
     * @Description : 파일 삭제 기능
     * @Param  : Map
     * @Return : String
     * @Author : 권오승
     * @Create Date : 2019. 6. 20.
     */
//    @RequestMapping("/cmn/login/deleteFile2.json")
//    public String deleteFile2( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){
//
//        logger.info(generateLogMsg("================================================================="));
//        logger.info(generateLogMsg("파일 삭제 START."));
//        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
//        logger.info(generateLogMsg("================================================================="));
//
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        File file = null;
//        String returnMsg = null;
//
//        try {
//
//        	String strFileName = orgCommonService.getFileNm(pReqParamMap.get("fileKey").toString());
//
//            file = new File(strFileName);
//
//            file.delete();
//
//            int recYnCnt = 0;
//
//            if( pReqParamMap.get("orgnId") != null)
//            {
//                recYnCnt = orgCommonService.getRequestKeyCnt(pReqParamMap.get("orgnId").toString());
//            }
//            logger.error("녹취여부 건수" + recYnCnt);
//
//
//
//            if( recYnCnt == 1)
//            {
//            	int updateCnt = orgCommonService.getRequestKeyUpdate(pReqParamMap.get("orgnId").toString());
//            	logger.error("녹취여부 UPDATE" + updateCnt);
//            }
//
//            int delcnt = orgCommonService.deleteFile(pReqParamMap.get("fileKey").toString());
//            logger.info(generateLogMsg("삭제건수 = " + delcnt));
//
// 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
// 			resultMap.put("msg", "삭제성공");
//
// 		} catch (Exception e) {
//
// 			resultMap.put("fileTotCnt", "0");
//            resultMap.put("deleteCnt", "0");
// 			
//			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
//					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
//					returnMsg, "", ""))
//			{
//            //v2018.11 PMD 적용 소스 수정
//            throw new MvnoErrorException(e);
//			}	 			
//			throw new MvnoErrorException(e);
//	    }
// 		//----------------------------------------------------------------
// 		// return json
// 		//----------------------------------------------------------------
// 		model.addAttribute("result", resultMap);
// 		return "jsonView";
//    }
    /**
	* @Description : 사용자 ID 중복체크
	* @Param  :UserApprVO
	* @Return : String
	* @Author : 권오승
	* @Create Date : 2019. 6. 20.
	 */
	@RequestMapping("/cmn/login/isExistUsrId.json")
	public String isExistUsrId(UserApprVO userApprVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap, HttpServletRequest request, HttpServletResponse response){
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 ID 중복체크 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try{
			
			userApprVo.setUsrId(userApprVo.getUsrId().toUpperCase());

			int returnCnt = userApprService.isExistUsrId(userApprVo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", returnCnt);

			logger.info(generateLogMsg("중복 건수 = " + returnCnt));

		} catch (Exception e) {
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
			{
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
	 * @Description : 사용자 등록
	 * @Param  : orgMgmtVO.class
	 * @Return : String
	 * @Author : 권오승
	 * @Create Date : 2019. 6. 19.
	 */
	@RequestMapping("/cmn/login/insertUserInfoMgmt.json")
	public String insertUserInfoMgmt(UserApprVO userApprVo, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap, HttpServletRequest request, HttpServletResponse response)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("============pRequestParamMap >> " + pRequestParamMap.toString() +"=============="));
		logger.info(generateLogMsg("============pRequestParamMap >> " + pRequestParamMap.toString() +"=============="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int returnCnt = 0;
		String app_user = null;
		String file1 = null;
		String file2 = null;
		
		try {
		
			userApprVo.setRegstId("MSystem");    /** 사용자ID */
			userApprVo.setRvisnId("MSystem");
			
			//전화번호, 핸드폰번호, Fax번호 합치기
			userApprVo.setMblphnNum(userApprVo.getMblphnNum1()+userApprVo.getMblphnNum2()+userApprVo.getMblphnNum3());
			if(KtisUtil.isNotEmpty(userApprVo.getFax2())){
				userApprVo.setFax(userApprVo.getFax1()+userApprVo.getFax2()+userApprVo.getFax3());
			}
			
			if(!StringUtils.isEmpty(userApprVo.getFile_upload1_s_0()))
			{
				 file1 = pRequestParamMap.get("file_upload1_s_0").toString();
			}
			if (!StringUtils.isEmpty(userApprVo.getFile_upload1_s_1())){
				 file2 = pRequestParamMap.get("file_upload1_s_1").toString();
			}
			
			//파일ID가져와서 SET
			userApprVo.setAppFileId1(file1);
			userApprVo.setAppFileId2(file2);
//			
			logger.info(generateLogMsg("============파일명1 >> " + file1 +"=============="));
			logger.info(generateLogMsg("============파일명2 >> " + file2 +"=============="));
			
			//승인자 ID가져오기
			app_user = userApprService.getAppUserId(userApprVo.getOrgnId());
			
			if(!StringUtils.isEmpty(app_user)){
				userApprVo.setAppUsrId(app_user);
			}else{
				userApprVo.setAppUsrId("");
			}
				
			userApprVo.setUsrId(userApprVo.getUsrId().toUpperCase());
			userApprVo.setAppYn("P");
			userApprVo.setAttcSctnCd("20");	//소속구분코드 20 : 대리점
			userApprVo.setMacChkYn("N");
			userApprVo.setPassInit("Y");
						
			returnCnt = userApprService.insertUserInfoMgmt(userApprVo);
			String mblphnNum =  userApprService.getAppUsrTel(app_user);
			//if(returnCnt > 0 && !mblphnNum.equals(null)){
			if(returnCnt > 0 && mblphnNum != null){	
				MsgQueueReqVO vo = new MsgQueueReqVO();
				//승인자전화번호 가져오기
				
				vo.setMsgType("3");
				vo.setDstaddr(mblphnNum);
				vo.setCallback(propertiesService.getString("otp.sms.callcenter"));
				vo.setStat("0");
				vo.setSubject("사용자 요청 결과");
				vo.setText("["+ userApprVo.getOrgnNm() + "] 대리점의 계정신청이 접수 되었습니다. M전산 대리점계정신청관리 화면에서 승인 및 반려 처리 바랍니다.");
				vo.setExpiretime("7200");
				vo.setOptId("MSP");

				userInfoMgmtService.insertMsgQueue(vo);
			}
			
			
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "");
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
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
	 * @Create Date : 2019. 6. 19.
	 */
	public static String generationUUID(){
		  return UUID.randomUUID().toString();
		 }
}
