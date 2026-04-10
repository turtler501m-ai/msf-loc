package com.ktis.msp.rcp.custMgmt.controller;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.rcp.custMgmt.service.CustMgmtService;
import com.ktis.msp.rcp.custMgmt.vo.FileInfoCuVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller
public class CustMgmtController extends BaseController {

	/** custMgmtService */
	@Autowired
	private CustMgmtService custMgmtService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileUpload2Service  fileUp2Service;
	
	// 녹취파일업로드
	@RequestMapping("/rcp/custMgmt/fileUpLoad.do")
	public String fileUpUsingService(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pRequestParamMap.toString()));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInfoCuVO fileInfoVO = new FileInfoCuVO();
		
		String contractNum = "";
		Object temp = pRequestParamMap.get("contractNum");
		
		if(temp != null) {
			contractNum = String.valueOf(temp);
			fileInfoVO.setContractNum(contractNum);
		}
		
		String lSaveFileName = "";
		String lFileNamePc = "";

		Integer filesize = 0;
		
		try{	
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(fileInfoVO);
			
			fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			fileInfoVO.setRvisnId(loginInfo.getUserId());
			fileInfoVO.setFileSeq(loginInfo.getUserOrgnId());//초기값 조직ID
			
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);
	
			//--------------------------------
			// upload base directory를 구함
			// 존재하지 않으면 exception 발생
			//--------------------------------
			String lBaseDir = propertiesService.getString("orgPath");
			if ( !new File(lBaseDir ).exists())
			{
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
				if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertiesService.getString("fileUploadSizeLimit")) )
					continue;

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
						/** 20230518 PMD 조치 */
						InputStream filecontent = null;
						File f = null;
	
						OutputStream fout = null;
						
						try {
							filecontent = item.getInputStream();
							f=new File(lTransferTargetFileName);
							fout=new FileOutputStream(f);
							
							byte buf[]=new byte[1024];
							int len;

							while((len=filecontent.read(buf))>0) {
								fout.write(buf,0,len);
								filesize+=len;
							}
						} catch (Exception e1) {
//							20200512 소스코드점검 수정
//					    	e.printStackTrace();
//							20210706 소스코드점검 수정
//							System.out.println("Connection Exception occurred");
							logger.error("Connection Exception occurred");
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
			
			model.addAttribute("result", resultMap);
			return "jsonViewArray";
		}
		
		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(lSaveFileName);
		fileInfoVO.setAttSctn("RCP");//조직 파일첨부
		
		//파일 테이블 insert
		custMgmtService.insertFileByCust(fileInfoVO);
		
		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", "" + 111);
		
		model.addAttribute("result", resultMap);
		return "jsonViewArray";
	}
  	
	public static String generationUUID(){
		return UUID.randomUUID().toString();
	}
}
