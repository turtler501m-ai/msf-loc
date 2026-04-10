package com.ktis.msp.pps.schedule.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CNPECMJava.CNPEncryptModule.crypto.FileHandle;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.pps.schedule.service.PpsEncScheduleService;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * @Class Name : PpsEncScheduleController.java
 * @Description : PpsEncScheduleController Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2016.04.6           최초생성
 *
 * @author 
 * @since 2016.04.06 
 * @version 1.0
 */

@Controller
public class PpsEncScheduleController extends BaseController {
	@Autowired
	private PpsEncScheduleService encScheduleService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	

	/**
	 * 서식지 파일 암호화 처리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cmn/login/pps/schedule/PpsEncSchedule.do" )
	public String selectUsimInfoMgmtListJson( ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
	   	
	   	String fileUrl = "";
	   	String fileEncUrl = "";
	   	String fileSeq = "";
	   	
	   	String retCd = "";
	   	String retMsg = "";
	   	
		    try {
		    	
				//파라미터 사이에 URL호출 프로시져 호출(기존URL,바뀌는URL,키값)
		    	pRequestParamMap.put("iOpCode", "REQ");
		    	
				encScheduleService.ppsEncScheduleProc(pRequestParamMap);
				
				if(pRequestParamMap.get("oRetCd").toString().equals("0000")){
					fileUrl = pRequestParamMap.get("oImgPath").toString();
					fileEncUrl = pRequestParamMap.get("oNewImgPath").toString();
					fileSeq = pRequestParamMap.get("oImgSeq").toString();
					retCd = pRequestParamMap.get("oRetCd").toString();
					retMsg = pRequestParamMap.get("oRetMsg").toString();
					
					//파일 암호화 처리
			    	CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
				    
				    fileHandle.Encrypt(fileUrl,fileEncUrl);
				    
					//기존 데이터 URL 및 FLAG UPDATE
				    pRequestParamMap.put("iOpCode", "RES");
				    pRequestParamMap.put("iNewImgPath", fileEncUrl);
				    pRequestParamMap.put("iImgSeq", fileSeq);
				    pRequestParamMap.put("iRetCd", retCd);
				    pRequestParamMap.put("iRetMsg", retMsg);
				    
				    encScheduleService.ppsEncScheduleProc(pRequestParamMap);
				    
				    if(pRequestParamMap.get("oRetCd").toString().equals("0000") && retCd.equals("0000")){
				    	File file = null;
						file = new File(fileUrl);
						
						file.delete();
				    }
				}
			    
		    } catch (Exception e) {
		    	
		    	//오류시 로그테이블에 저장
			    pRequestParamMap.put("iOpCode", "RES");
			    pRequestParamMap.put("iNewImgPath", pRequestParamMap.get("oNewImgPath").toString());
			    pRequestParamMap.put("iImgSeq", pRequestParamMap.get("oImgSeq").toString());
			    pRequestParamMap.put("iRetCd", "9999");
			    pRequestParamMap.put("iRetMsg",  "");
		    	encScheduleService.ppsEncScheduleProc(pRequestParamMap);
		    	throw new MvnoErrorException(e);
		      //e.printStackTrace();
		    }
		    
    	return null;
	}


}
