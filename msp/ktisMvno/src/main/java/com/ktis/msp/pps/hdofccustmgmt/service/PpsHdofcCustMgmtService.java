package com.ktis.msp.pps.hdofccustmgmt.service;


import com.ktds.crypto.annotation.Crypto;
import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper;
import com.ktis.msp.pps.hdofccustmgmt.vo.*;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @param <PpsHdofcCustMgmtMapper>
 * @Class Name : CalcCreBasisService
 * @Description : 수수료계산요소정보조회
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */


@Service
public class PpsHdofcCustMgmtService  extends ExcelAwareService {

	@Autowired
	private PpsHdofcCustMgmtMapper  hdofcCustMngMapper;

	@Autowired
	private PpsHdofcCommonService  hdofcCommonService;

	@Autowired
	private CustEncService  encService;
	
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	public String[] getJuminNumber(String str){
		String [] result = new String[2];
		try {
			String temp = StringUtils.replace(str, "-", "");
			result[0] = StringUtils.substring(temp, 0, 6);
			result[1] = StringUtils.substring(temp, 6);

		} catch (Exception e) {
			logger.error(e);
		}

		return result;
	}
	
	public String[] getCardNumber(String str){
		String [] result = new String[4];
		try {
			String temp = StringUtils.replace(str, "-", "");
			result[0] = StringUtils.substring(temp, 0, 4);
			result[1] = StringUtils.substring(temp, 4, 8);
			result[2] = StringUtils.substring(temp, 8, 12);
			result[3] = StringUtils.substring(temp, 12);

		} catch (Exception e) {
			logger.error(e);
		}

		return result;
	}
	
	/**
	 * 본사-고객관리 개통정보 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public List<?> getOpenInfoMngList(Map<String, Object> pRequestParamMap) {

		
		List<EgovMap> resultList = new ArrayList<EgovMap>();

		resultList = (List<EgovMap>)hdofcCustMngMapper.getOpenInfoMngList(pRequestParamMap);
		
		List<EgovMap> result = encService.decryptCustMngtList(resultList);
		
		
		for( EgovMap map : result  ){
			  
			String ssn = (String)map.get("userSsn");
			String birthDay = "";
			
			String [] birth = getJuminNumber(ssn);
			
			 birthDay = birth[0];
			
		
			if(!StringUtils.isBlank(birthDay)){
				
				map.put("birthDay", birthDay);
				
				
			}
			if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
				String contractNum =  map.get("contractNum").toString().trim();
				
				if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
				{
					StringBuffer sb = new StringBuffer();
					String tmp = "^javaScript:goCustInfoData("+contractNum+");^_self";
					sb.append(contractNum).append(tmp);
					map.put("contractNumStr",sb.toString());
				}
			
			}
			
			if(map.get("agentNm")!=null && !map.get("agentNm").equals("")&&map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
				String agentNm =  map.get("agentNm").toString();
				String contractNum =  map.get("contractNum").toString().trim();
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustInfoChange("+contractNum+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
				sb.append(agentNm).append(tmp);
				map.put("agentNm",sb.toString());
			}
			
			/*if(map.get("paperImage1")!=null && !map.get("paperImage1").equals("") ){
				StringBuffer sb = new StringBuffer();
				sb.append("O^javaScript:userPaperImageView("+conNum+",1,"+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self");
				map.put("paperImage1Str",sb.toString());
			}else{
				StringBuffer sb = new StringBuffer();
				sb.append("X");
				map.put("paperImage1Str",sb.toString());
			}
			
			if(map.get("paperImage2")!=null && !map.get("paperImage2").equals("") ){
				StringBuffer sb = new StringBuffer();
				
				sb.append("O^javaScript:userPaperImageView("+conNum+",2,"+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self");
				map.put("paperImage2Str",sb.toString());
			}else{
				StringBuffer sb = new StringBuffer();
				sb.append("X");
				map.put("paperImage2Str",sb.toString());
			}
			if(map.get("paperImage3")!=null && !map.get("paperImage3").equals("") ){
				StringBuffer sb = new StringBuffer();
				
				sb.append("O^javaScript:userPaperImageView("+conNum+",3,"+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self");
				map.put("paperImage3Str",sb.toString());
			}else{
				StringBuffer sb = new StringBuffer();
				sb.append("X");
				map.put("paperImage3Str",sb.toString());
			}
			if(map.get("paperImage4")!=null && !map.get("paperImage4").equals("") ){
				StringBuffer sb = new StringBuffer();
				
				sb.append("O^javaScript:userPaperImageView("+conNum+",4,"+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self");
				map.put("paperImage4Str",sb.toString());
			}else{
				StringBuffer sb = new StringBuffer();
				sb.append("X");
				map.put("paperImage4Str",sb.toString());
			}
			if(map.get("paperImage5")!=null && !map.get("paperImage5").equals("") ){
				StringBuffer sb = new StringBuffer();
				
				sb.append("O^javaScript:userPaperImageView("+conNum+",5,"+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self");
				map.put("paperImage5Str",sb.toString());
			}else{
				StringBuffer sb = new StringBuffer();
				sb.append("X");
				map.put("paperImage5Str",sb.toString());
			}*/
			
			
			
			
		}


		return result;
	}

	/**
	 * 본사-고객관리 개통정보목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public void getOpenInfoMngExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","CTN","상태","모델명", "유심일련번호","고객명","식별구분","생년월일","개통일자","개통대리점","판매점","요금제","요금제코드","신규/이동","선후불","잔액","만료일","엠만료일","소진일","국적","충전가능여부","비자접수","체류기간","통신사","서류","모집경로");
		param.setStrValue("CONTRACT_NUM", "SUBSCRIBER_NO","SUB_STATUS_NM","MODEL_NAME", "ICC_ID","SUB_LINK_NAME","CUST_IDNT_NO_IND_CD_NM","BIRTHDAY","ENTER_DATE","AGENT_NM","AGENT_SALE_NM","SERVICE_NM","SOC","MNP_IND_CD_NM","SERVICE_TYPE_NM","BASIC_REMAINS","BASIC_EXPIRE","MVNO_BASIC_EXPIRE","BASIC_EMPT_DT","AD_NATION_NM","RECHARGE_FLAG","VIZA_FLAG","STAY_EXPIR_DT","SO_CD_NM","PAPER_IMAGE","SRL_IF_ID_NM");
		param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("개통관리");
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
					
		File f = makeBigDataExcel("com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper.getOpenInfoMngExcel", pRequestParamMap, param, handler);
		
		
		String fileName = "개통관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";

		//=======파일다운로드사유 로그 START==========================================================
        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
            
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getHeader("REMOTE_ADDR");
           
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getRemoteAddr();
            
            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수

            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
        }
        //=======파일다운로드사유 로그 END==========================================================
		
		try {
			doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
		} catch (UnsupportedEncodingException e) {

//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}

	}

	/**
	 * 본사-고객관리 상담내역목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getCnslDtlsMngList(Map<String, Object> pRequestParamMap) {

			//List<?> resultList = new ArrayList<PpsCdrPpDaily2014Vo>();

			List<EgovMap> resultList = (List<EgovMap>)hdofcCustMngMapper.getCnslDtlsMngList(pRequestParamMap);
			for(EgovMap map : resultList){
				if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
					String contractNum =  map.get("contractNum").toString().trim();
					if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
					{
						StringBuffer sb = new StringBuffer();
						String tmp = "^javaScript:goCustInfoData("+contractNum+");^_self";
						sb.append(contractNum).append(tmp);
						map.put("contractNum",sb.toString());
					}
				}
			}


		return resultList;
	}

	/**
	 * 본사-고객관리 상담내역목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getCnslDtlsMngExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


			ExcelParam param = new ExcelParam();
			param.setStrHead("계약번호","상담고객명","요청구분","처리구분","요청일자","요청내용","처리상태","처리내용","접수자","처리자","처리일자");
			param.setStrValue("CONTRACT_NUM","REQ_USER_NAME","REQ_TYPE_NM","RES_STATUS_NM","REG_DATE","REQ_BODY","STATUS_NM","RES_BODY","REG_ADMIN_NM","RES_ADMIN_NM","RES_END_DATE");
			param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
			param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0,0,0);
			param.setSheetName("상담내역관리");
			param.setExcelPath(path);
			param.setFileName("test");
			
			List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
			
			param.setCmnMaskGrpList(lCmnMskGrp);
				
			Decryptor dec = factory.getDecryptorByName("DBMSDec");
			
			TestHandler handler  = new TestHandler();
			handler.setDecryptor(dec);
				
				
			File f = makeBigDataExcel("com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper.getCnslDtlsMngExcel", pRequestParamMap, param, handler);
			String fileName = "상담내역정보_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
			
			//=======파일다운로드사유 로그 START==========================================================
	        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
	            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
	            
	            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
	                ipAddr = request.getHeader("REMOTE_ADDR");
	           
	            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
	                ipAddr = request.getRemoteAddr();
	            
	            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
	            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
	            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
	            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
	            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
	            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
	            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
	            
	            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
	        }
	        //=======파일다운로드사유 로그 END==========================================================
	        
			try {
				doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
			} catch (UnsupportedEncodingException e) {

//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
//				20210706 소스코드점검 수정
//				System.out.println("Connection Exception occurred");
				logger.error("Connection Exception occurred");
			}
			
	}

	/**
	 * 본사 -고객관리 문자발송내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getCustSmsDtlsMngList(Map<String, Object> pRequestParamMap) {
		
		//List<?> resultList = new ArrayList<PpsSmsVo>();
		
		List<EgovMap> resultList = (List<EgovMap>)hdofcCustMngMapper.getCustSmsDtlsMngList(pRequestParamMap);
		for(EgovMap map : resultList){
			if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
				String contractNum =  map.get("contractNum").toString().trim();
				if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
				{
					StringBuffer sb = new StringBuffer();
					String tmp = "^javaScript:goCustInfoData("+contractNum+");^_self";
					sb.append(contractNum).append(tmp);
					map.put("contractNum",sb.toString());
				}
			}
		}
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		
		maskFields.put("calledNumber","MOBILE_PHO");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 본사 고객관리 문자발송내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getCustSmsDtlsMngExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","발신번호","착신번호","전송결과","결과메세지","전송일자","전송내용");
		param.setStrValue("CONTRACT_NUM","CALLING_NUMBER","CALLED_NUMBER","SMS_RESULT_CODE","SMS_RESULT_NM","SMS_SEND_DATE","SMS_MSG");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("문자발송내역관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
		
		File f = makeBigDataExcel("com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper.getCustSmsDtlsMngExcel", pRequestParamMap, param, handler);
		String fileName = "문자발송내역정보_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
		//=======파일다운로드사유 로그 START==========================================================
		if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
			
			pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
			pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
			pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
			pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
			pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
			pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
			pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
			
			fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
		}
		//=======파일다운로드사유 로그 END==========================================================
		
		try {
			doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
		} catch (UnsupportedEncodingException e) {
			
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}	
		
	}
	
	/**
	 * 본사 -고객관리 문자발송내역 목록조회(과거)
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getCustSmsDtlsMngListOld(Map<String, Object> pRequestParamMap) {

			//List<?> resultList = new ArrayList<PpsSmsVo>();

			List<EgovMap> resultList = (List<EgovMap>)hdofcCustMngMapper.getCustSmsDtlsMngListOld(pRequestParamMap);
			for(EgovMap map : resultList){
				if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
					String contractNum =  map.get("contractNum").toString().trim();
					if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
					{
						StringBuffer sb = new StringBuffer();
						String tmp = "^javaScript:goCustInfoData("+contractNum+");^_self";
						sb.append(contractNum).append(tmp);
						map.put("contractNum",sb.toString());
					}
				}
			}

			HashMap<String, String> maskFields = new HashMap<String, String>();

			maskFields.put("calledNumber","MOBILE_PHO");
			
			maskingService.setMask(resultList, maskFields, pRequestParamMap);
			
		return resultList;
	}

	/**
	 * 본사 고객관리 문자발송내역 목록조회 엑셀출력(과거)
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getCustSmsDtlsMngExcelOld(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","발신번호","착신번호","전송결과","결과메세지","전송일자","전송내용");
		param.setStrValue("CONTRACT_NUM","CALLING_NUMBER","CALLED_NUMBER","SMS_RESULT_CODE","SMS_RESULT_NM","SMS_SEND_DATE","SMS_MSG");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("문자발송내역관리(과거)");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
		File f = makeBigDataExcel("com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper.getCustSmsDtlsMngExcelOld", pRequestParamMap, param, handler);
		String fileName = "문자발송내역정보_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
		//=======파일다운로드사유 로그 START==========================================================
        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
            
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getHeader("REMOTE_ADDR");
           
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getRemoteAddr();
            
            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
            
            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
        }
        //=======파일다운로드사유 로그 END==========================================================
        
		try {
			doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
		} catch (UnsupportedEncodingException e) {

//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}	

	}

	/**
	 * 본사 -고객관리 해지자관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getTmntCustMngList(Map<String, Object> pRequestParamMap) {


			//List<?> resultList = new ArrayList<PpsKtJuoSubVo>();

			List<EgovMap> resultList = (List<EgovMap>)hdofcCustMngMapper.getTmntCustMngList(pRequestParamMap);
			for(EgovMap map : resultList){
				if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
					String contractNum =  map.get("contractNum").toString().trim();
					if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
					{
						StringBuffer sb = new StringBuffer();
						String tmp = "^javaScript:goCustInfoData("+contractNum+");^_self";
						sb.append(contractNum).append(tmp);
						map.put("contractNum",sb.toString());
					}
				}
			}


		return resultList;
	}


	/**
	 * 본사 -고객관리 해지자관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getTmntCustMngExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","CTN","요금제","요금제코드","사용자명","유심일련번호","상태","해지사유","잔액","국적","개통대리점","개통일자","정지일자","만료일자","해지일자","서식지","음성(분)","문자(건)","데이터(M)","총충전금액");
		param.setStrValue("CONTRACT_NUM","SUBSCRIBER_NO","SERVICE_NM","SOC","SUB_LINK_NAME","ICC_ID","SUB_STATUS_NM","CANCEL_RSN_CODE_NM","BASIC_REMAINS","AD_NATION_NM","AGENT_NM","LST_COM_ACTV_DATE","STATUS_STOP_DT","BASIC_EXPIRE","STATUS_CANCEL_DT","PAPER_IMAGE","US_LC_VOICE","US_LC_SMS","US_LC_DATA","TOTAL_BASIC_CHG");
		param.setIntWidth(3000, 5000,5000,5000,5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,5000,5000,5000,5000);
		param.setIntLen(0, 0,0,0,0,0, 0,0, 1, 0, 0, 0, 0, 0, 0,1,1,1,0,1);
		param.setSheetName("해지자관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper.getTmntCustMngExcel", pRequestParamMap, param, handler);
		String fileName = "해지자관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
		//=======파일다운로드사유 로그 START==========================================================
        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
            
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getHeader("REMOTE_ADDR");
           
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getRemoteAddr();
            
            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
            
            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
        }
        //=======파일다운로드사유 로그 END==========================================================
        
		try {
			doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
		} catch (UnsupportedEncodingException e) {

//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
	
	}

	/**
	 * 본사 -고객관리 선불CDR관리  목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getPrepaidCdrMngList(Map<String, Object> pRequestParamMap) {

			//List<?> resultList = new ArrayList<PpsCdrPp2014Vo>();
			StringBuilder tableNm = new StringBuilder();
			StringBuilder idxNm = new StringBuilder();
			if(pRequestParamMap != null && pRequestParamMap.get("tableName") != null)
			{
				tableNm.append("PPS_CDR_PP_");
				tableNm.append(pRequestParamMap.get("tableName").toString());

				idxNm.append(tableNm).append("_IDX1");
				pRequestParamMap.put("tableName", tableNm);
				pRequestParamMap.put("idxName", idxNm);
			}
			List<EgovMap> resultList = (List<EgovMap>)hdofcCustMngMapper.getPrepaidCdrMngList(pRequestParamMap);
			
			for(EgovMap map : resultList){
				if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
					String contractNum =  map.get("contractNum").toString().trim();
					if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
					{
						StringBuffer sb = new StringBuffer();
						String tmp = "^javaScript:goCustInfoData("+contractNum+");^_self";
						sb.append(contractNum).append(tmp);
						map.put("contractNum",sb.toString());
					}
				}
			}


		return resultList;
	}

	/**
	 * 본사 -고객관리 선불CDR관리  목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getPrepaidCdrMngExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("통화구분","계약번호","착신번호","발신시간","사용시간(초)","과금액","국제사업자","잔액","국가코드","소속대리점");
		param.setStrValue("CALL_GUBUN_NM","CONTRACT_NUM","CALLED_NUM","START_TIME","USED_TIME","CHARGE","TELCO_PX","REMAINS","KOR_NAME","AGENT_NM" );
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000);
		param.setIntLen(0, 0, 0, 0, 0, 1, 0, 1, 0,0);
		param.setSheetName("선불CDR관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectOnlyList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		TestHandler handler  = new TestHandler();	
			
		File f = makeBigDataExcel("com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper.getPrepaidCdrMngExcel", pRequestParamMap, param, handler);
		String fileName = "선불CDR관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
		//=======파일다운로드사유 로그 START==========================================================
        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
            
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getHeader("REMOTE_ADDR");
           
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getRemoteAddr();
            
            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
            
            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
        }
        //=======파일다운로드사유 로그 END==========================================================
        
		try {
			doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
		} catch (UnsupportedEncodingException e) {

//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
	
	}

	/**
	 * 본사 -고객관리 선불 일사용내역관리  목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getPrepaidCdrDailyMngList(Map<String, Object> pRequestParamMap) {


			//List<?> resultList = new ArrayList<PpsCdrPpDaily2014Vo>();
					
			List<EgovMap> resultList = (List<EgovMap>)hdofcCustMngMapper.getPrepaidCdrDailyMngList(pRequestParamMap);
			for(EgovMap map : resultList){
				if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
					String contractNum =  map.get("contractNum").toString().trim();
					if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
					{
						StringBuffer sb = new StringBuffer();
						String tmp = "^javaScript:goCustInfoData("+contractNum+");^_self";
						sb.append(contractNum).append(tmp);
						map.put("contractNum",sb.toString());
					}
				}
			}


		return resultList;
	}

	/**
	 * 본사 -고객관리 선불 일사용내역관리  엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getPrepaidCdrDailyMngExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("통화구분","계약번호","사용일자","시작시간","종료시간","누적사용량(K)","과금액","기존잔액","데이터잔량(K)","데이터만료일","소속대리점");
		param.setStrValue("CALL_GUBUN_NM","CONTRACT_NUM","ACCESS_DT","START_TIME","END_TIME","PKTKBYTE","CHARGE","REMAINS","DATA_REMAINS","DATA_EXPIRE","AGENT_NM" );
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 1, 1, 1, 1,0,0);
		param.setSheetName("선불일사용내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
			
		TestHandler handler  = new TestHandler();
			
		File f = makeBigDataExcel("com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper.getPrepaidCdrDailyMngExcel", pRequestParamMap, param, handler);
		String fileName = "일사용내역관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
		//=======파일다운로드사유 로그 START==========================================================
        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
            
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getHeader("REMOTE_ADDR");
           
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getRemoteAddr();
            
            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
            
            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
        }
        //=======파일다운로드사유 로그 END==========================================================
        
		try {
			doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
		} catch (UnsupportedEncodingException e) {

//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}

	}

	/**
	 * 선불 고객정보 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	
	public PpsCustomerVo getPpsCustmerInfo(Map<String, Object> pRequestParamMap){

		PpsCustomerVo ppsCustomerVo = new PpsCustomerVo();

		ppsCustomerVo = hdofcCustMngMapper.getPpsCustmerInfo(pRequestParamMap);
		
		return ppsCustomerVo;
	}



	/**
	 * KT현행화 고객정보 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public PpsKtJuoCusVo getPpsKtJuoCusInfo(Map<String, Object> pRequestParamMap){



		PpsKtJuoCusVo ppsktJuoCusVo =new PpsKtJuoCusVo();
		ppsktJuoCusVo = hdofcCustMngMapper.getPpsKtJuoCusInfo(pRequestParamMap);

		return ppsktJuoCusVo;
	}


	/**
	 * 선불 KT현행화 계약정보 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */

	public PpsKtJuoSubVo getPpsKtJuoSubInfo(Map<String, Object> pRequestParamMap){

		PpsKtJuoSubVo ppsKtJuoSubVo = new PpsKtJuoSubVo();
		ppsKtJuoSubVo = hdofcCustMngMapper.getPpsKtJuoSubInfo(pRequestParamMap);
		
		return ppsKtJuoSubVo;
	}

	/**
	 * 선불 KT현행화 청구정보
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public PpsKtJuoBanVo getPpsKtJuoBanInfo(Map<String, Object> pRequestParamMap){

		PpsKtJuoBanVo ppsKtJuoBanVo=new PpsKtJuoBanVo();

		ppsKtJuoBanVo = hdofcCustMngMapper.getPpsKtJuoBanInfo(pRequestParamMap);

		return ppsKtJuoBanVo;

	}

	/**
	 * 요금제 정보조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public MspRateMstVo getPpsCodeServiceInfo(Map<String, Object> pRequestParamMap){

		MspRateMstVo mspRateMstVo= new MspRateMstVo();

		mspRateMstVo = hdofcCustMngMapper.getPpsCodeServiceInfo(pRequestParamMap);

		return mspRateMstVo;
	}



	/**
	 * 총충전데이터(총충전횟수,총충전금액)조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public PpsRcgVo getPpsRcgSumData(Map<String, Object> pRequestParamMap){
		PpsRcgVo ppsRcgVo = new PpsRcgVo();
		ppsRcgVo = hdofcCustMngMapper.getPpsRcgSumData(pRequestParamMap);

		return ppsRcgVo;

	}

	/**
	 * 가입된 부가서비스 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getPpsKtJuoFeatureInfoList(Map<String, Object> pRequestParamMap) {


		List<?> resultList = new ArrayList<PpsKtJuoFeatureVo>();
//		List<?> resultList = new ArrayList<Map<String, Object>>();

		resultList = hdofcCustMngMapper.getPpsKtJuoFeatureInfoList(pRequestParamMap);

		return resultList;

	}

	/**
	 * 고객관리 상세정보 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Crypto(decryptName="DBMSDec", fields = {"userSsn","adSsn","customerSsn","bankAcctHolderId"})
	public Map<String, Object > getHdofcCustMngInfo(Map<String, Object> pRequestParamMap) {
		Map<String, Object> resultMap  =new HashMap<String, Object>();

		logger.info("======  PpsHdofcCustMgmtController.selectHdofcCustMngInfoJson-- 고객관리 상세정보 조회 1");

		//고객정보
		PpsCustomerVo ppsCustomerVo = getPpsCustmerInfo(pRequestParamMap) ;
		if(ppsCustomerVo == null)
		{
			ppsCustomerVo = new PpsCustomerVo();
		}

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cmsAccountMsk","ACCOUNT");
		maskFields.put("cmsUserNameMsk","CUST_NAME");
		maskFields.put("cmsUserSsnMsk","SSN");
		
		maskFields.put("adNameMsk","CUST_NAME");
		maskFields.put("adSsnMsk","SSN");
		maskFields.put("adAddressMsk","ADDR");
		maskFields.put("smsPhoneMsk","MOBILE_PHO");

		maskingService.setMask(ppsCustomerVo, maskFields, pRequestParamMap);
		
		logger.debug("ppsCustomerVo : "+ppsCustomerVo.toString());
		
		
		// KT현행화 계약정보
		PpsKtJuoSubVo ppsKtJuoSubVo = getPpsKtJuoSubInfo(pRequestParamMap);
		if(ppsKtJuoSubVo == null)
		{
			ppsKtJuoSubVo = new PpsKtJuoSubVo();
		}

		HashMap<String, String> maskFields2 = new HashMap<String, String>();
		maskFields2.put("subscriberNoMsk","MOBILE_PHO");
		maskFields2.put("userSsnMsk","SSN");
		maskFields2.put("subAdrSecondaryLnMsk","ADDR");
		maskFields2.put("subLinkNameMsk","CUST_NAME");
		maskFields2.put("iccIdMsk","USIM");
		maskFields2.put("intmSrlNoMsk","DEV_NO");
		
		maskingService.setMask(ppsKtJuoSubVo, maskFields2, pRequestParamMap);		
		
		logger.debug("ppsKtJuoSubVo : "+ppsKtJuoSubVo.toString());

		pRequestParamMap.put("customerId", ppsKtJuoSubVo.getCustomerId());
		
		
		// KT현행화고객정보
		PpsKtJuoCusVo ppsKtJuoCusVo = getPpsKtJuoCusInfo(pRequestParamMap);
		if(ppsKtJuoCusVo==null|| StringUtils.isBlank(ppsKtJuoCusVo.getCustomerSsn())){
			ppsKtJuoCusVo = new PpsKtJuoCusVo();
		}		

		HashMap<String, String> maskFields3 = new HashMap<String, String>();
		maskFields3.put("customerSsnMsk","SSN");
		maskFields3.put("driverLicnsNoMsk","SSN");
		maskFields3.put("customerLinkNameMsk","CUST_NAME");
		
		maskingService.setMask(ppsKtJuoCusVo, maskFields3, pRequestParamMap);
		
		logger.debug("ppsKtJuoCusVo : "+ppsKtJuoCusVo.toString());


		pRequestParamMap.put("customerBan", ppsKtJuoSubVo.getCustomerBan());

		// KT현행화 청구정보
		PpsKtJuoBanVo ppsKtJuoBanVo = getPpsKtJuoBanInfo(pRequestParamMap);
		if(ppsKtJuoBanVo==null){
			ppsKtJuoBanVo = new PpsKtJuoBanVo();
		}

		HashMap<String, String> maskFields4 = new HashMap<String, String>();
		maskFields4.put("banLinkNameMsk","CUST_NAME");
		maskFields4.put("banAdrSecondaryLnMsk","ADDR");
		maskFields4.put("bankAcctHolderIdMsk","SSN");
		
		maskingService.setMask(ppsKtJuoBanVo, maskFields4, pRequestParamMap);
		
		logger.debug("ppsKtJuoBanVo : "+ppsKtJuoBanVo.toString());

		pRequestParamMap.put("soc", ppsCustomerVo.getSoc());

		//요금제 정보
		MspRateMstVo mspRateMstVo= getPpsCodeServiceInfo(pRequestParamMap);
		if(mspRateMstVo==null || StringUtils.isBlank(mspRateMstVo.getRateCd())){
			mspRateMstVo = new MspRateMstVo();
		}

		logger.debug("McpRateVo : "+mspRateMstVo.toString());

		//총충전정보
		PpsRcgVo ppsRcgVo = getPpsRcgSumData(pRequestParamMap);
		if(ppsRcgVo== null){
			ppsRcgVo = new PpsRcgVo();
		}

		logger.debug("ppsRcgVo : "+ppsRcgVo.toString());


		pRequestParamMap.put("dataServiceCode", ppsCustomerVo.getDataServiceCode());


		
		//계약번호
		resultMap.put("contractNum", ppsCustomerVo.getContractNum());

		//고객명
		resultMap.put("customerLinkName", ppsKtJuoCusVo.getCustomerLinkName());
		resultMap.put("customerLinkNameMsk", ppsKtJuoCusVo.getCustomerLinkNameMsk());

		//CTN
		resultMap.put("subscriberNo",ppsKtJuoSubVo.getSubscriberNo());
		resultMap.put("subscriberNoMsk", ppsKtJuoSubVo.getSubscriberNoMsk());

		//상태
		resultMap.put("subStatusNm", ppsKtJuoSubVo.getSubStatusNm());
		resultMap.put("subStatus", ppsKtJuoSubVo.getSubStatus());


		//상태날짜
		resultMap.put("subStatusDate", ppsKtJuoSubVo.getSubStatusDate());

		//고객번호
		resultMap.put("customerId",ppsKtJuoSubVo.getCustomerId());

		//고객유형
		resultMap.put("customerTypeNm", ppsKtJuoCusVo.getCustomerTypeNm());
		resultMap.put("customerType", ppsKtJuoCusVo.getCustomerType());
		resultMap.put("mnpIndCdNm", ppsKtJuoSubVo.getMnpIndCdNm());
		resultMap.put("mnpIndCd", ppsKtJuoSubVo.getMnpIndCd());

		//청구주소우편번호
		if(!StringUtils.isBlank(ppsKtJuoBanVo.getBanAdrZip()))
		{
			resultMap.put("banAdrZip1", ppsKtJuoBanVo.getBanAdrZip().substring(0,3));
			resultMap.put("banAdrZip2", ppsKtJuoBanVo.getBanAdrZip().substring(3));
		}else{
			resultMap.put("banAdrZip1", "");
			resultMap.put("banAdrZip2", "");
		}

		//청구주소1
		resultMap.put("banAdrPrimaryLn",ppsKtJuoBanVo.getBanAdrPrimaryLn() );

		//청구주소2
		resultMap.put("banAdrSecondaryLn", ppsKtJuoBanVo.getBanAdrSecondaryLn());
		resultMap.put("banAdrSecondaryLnMsk", ppsKtJuoBanVo.getBanAdrSecondaryLnMsk());

		//주민번호
		if(!StringUtils.isBlank(ppsKtJuoCusVo.getCustomerSsn()))
		{
			resultMap.put("customerSsn", ppsKtJuoCusVo.getCustomerSsn());
			resultMap.put("customerSsnMsk", ppsKtJuoCusVo.getCustomerSsnMsk());
		}else{
			resultMap.put("customerSsn", "");
			resultMap.put("customerSsnMsk", "");
		}

		//사업자번호
		if(!StringUtils.isBlank(ppsKtJuoCusVo.getTaxId()))
		{
			resultMap.put("taxId", ppsKtJuoCusVo.getTaxId());
		}else{
			resultMap.put("taxId", "");
		}



		//법인번호
		if(!StringUtils.isBlank(ppsKtJuoCusVo.getDrivrLicnsNo()))
		{
			resultMap.put("driverLicnsNo", ppsKtJuoCusVo.getDrivrLicnsNo());
			resultMap.put("driverLicnsNoMsk", ppsKtJuoCusVo.getDriverLicnsNoMsk());
		}else{
			resultMap.put("driverLicnsNo", "");
			resultMap.put("driverLicnsNoMsk", "");
		}

		//요금제명
		resultMap.put("serviceName", mspRateMstVo.getRateNm());

		//일당요금
		resultMap.put("serviceBasic", mspRateMstVo.getMonthlyFee());



		//기본잔액
		resultMap.put("basicRemains", ppsCustomerVo.getBasicRemainsStr());
		resultMap.put("point", ppsCustomerVo.getPoint());

		//유효기간
		resultMap.put("basicExpire", ppsCustomerVo.getBasicExpire());

		//마지막충전일
		if(ppsCustomerVo.getLastBasicChgDt() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getLastBasicChgDt()))
		{
			resultMap.put("lastBasicChgDt",ppsCustomerVo.getLastBasicChgDt() );
		}
		else
		{
			resultMap.put("lastBasicChgDt","0000-00-00");
		}

		//데이터가입
		
		//Kim Woong
		if(ppsCustomerVo.getDataRegNm() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getDataRegNm()))
		{
			resultMap.put("dataRegNm", ppsCustomerVo.getDataRegNm());
		}
		else
		{
			resultMap.put("dataRegNm", "미가입");
		}
		
		if(ppsCustomerVo.getDataModeNm() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getDataModeNm()))
		{
			resultMap.put("dataModeNm",ppsCustomerVo.getDataModeNm());
		}
		else
		{
			resultMap.put("dataModeNm", "수동");
		}

		//데이터상품/충전일
		//resultMap.put("serviceDataAmount", mcpRateVo.getDataSize());
		
		if(ppsCustomerVo.getLastDataChgDt() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getLastDataChgDt()))
		{
			resultMap.put("lastDataChgDt", ppsCustomerVo.getLastDataChgDt());
		}
		else
		{
			resultMap.put("lastDataChgDt","0000-00-00");
		}
		
		

		//데이터잔량/유효기간
		resultMap.put("dataQuotaRemains", ppsCustomerVo.getDataQuotaRemains());
		if(ppsCustomerVo.getDataExpire() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getDataExpire()))
		{
			resultMap.put("dataExpire", ppsCustomerVo.getDataExpire());
		}
		else
		{
			resultMap.put("dataExpire", "없음");
		}

		//잔액최종갱신일
		
		if(ppsCustomerVo.getLastKtSynDt() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getLastKtSynDt()))
		{
			resultMap.put("lastKtSynDt", ppsCustomerVo.getLastKtSynDt());
		}
		else
		{
			resultMap.put("lastKtSynDt", "0000-00-00");
		}

		//총충전횟수/총금액
		resultMap.put("rcgCnt", ppsCustomerVo.getTotalBasicCnt());
		resultMap.put("sumAmount",ppsCustomerVo.getTotalBasicChg());
		
		// 요금정보
		resultMap.put("overVoice","0");
		resultMap.put("overSms", "0");
		resultMap.put("overData", "0");
		
		
		

		//당월사용량
		if(ppsCustomerVo.getUsLcVoice() > 0)
		{
			resultMap.put("usLcVoice", ppsCustomerVo.getUsLcVoice());
		}
		else
		{
			resultMap.put("usLcVoice", "0");
		}
		if(ppsCustomerVo.getUsLcSms() > 0)
		{
			resultMap.put("usLcSms", ppsCustomerVo.getUsLcSms());
		}
		else
		{
			resultMap.put("usLcSms", "0");
		}

		if(ppsCustomerVo.getUsLcData() > 0)
		{
		  
			BigDecimal a = new BigDecimal(Double.toString(ppsCustomerVo.getUsLcData()));
			BigDecimal b = new BigDecimal("1024");
		    BigDecimal mData = a.divide(b, 0, BigDecimal.ROUND_HALF_UP);
		    resultMap.put("usLcData", mData);
		}
		else
		{
			resultMap.put("usLcData", 0);
		}

		//실사용자명
		resultMap.put("subLinkName", ppsKtJuoSubVo.getSubLinkName());
		resultMap.put("subLinkNameMsk", ppsKtJuoSubVo.getSubLinkNameMsk());

		//실사용자 주민번호
		if(!StringUtils.isBlank(ppsKtJuoSubVo.getUserSsn())){
//			resultMap.put("userSsn", ppsKtJuoSubVo.getUserSsn().substring(0,6));
			resultMap.put("userSsn", ppsKtJuoSubVo.getUserSsn());
			resultMap.put("userSsnMsk", ppsKtJuoSubVo.getUserSsnMsk());
		}

		//실사용자 주소
		StringBuilder juoAddrStr = new StringBuilder();
		StringBuilder juoAddrStrMsk = new StringBuilder();
		
		if(ppsKtJuoSubVo.getSubAdrZip() != null &&
				!StringUtils.isBlank(ppsKtJuoSubVo.getSubAdrZip()))
		{
			juoAddrStr.append("(");
			juoAddrStr.append(ppsKtJuoSubVo.getSubAdrZip());
			juoAddrStr.append(") ");

			juoAddrStrMsk.append("(");
			juoAddrStrMsk.append(ppsKtJuoSubVo.getSubAdrZip());
			juoAddrStrMsk.append(") ");
		}
		if(ppsKtJuoSubVo.getSubAdrPrimaryLn() != null &&
				!StringUtils.isBlank(ppsKtJuoSubVo.getSubAdrPrimaryLn()))
		{
			juoAddrStr.append(ppsKtJuoSubVo.getSubAdrPrimaryLn());
			juoAddrStr.append(" ");

			juoAddrStrMsk.append(ppsKtJuoSubVo.getSubAdrPrimaryLn());
			juoAddrStrMsk.append(" ");
		}
		if(ppsKtJuoSubVo.getSubAdrSecondaryLn() != null &&
				!StringUtils.isBlank(ppsKtJuoSubVo.getSubAdrSecondaryLn()))
		{
			juoAddrStr.append(ppsKtJuoSubVo.getSubAdrSecondaryLn());
			juoAddrStrMsk.append(ppsKtJuoSubVo.getSubAdrSecondaryLnMsk());
		}
		
		resultMap.put("subLinkAddr", juoAddrStr);
		resultMap.put("subLinkAddrMsk", juoAddrStrMsk);

		//온라인예약번호
		resultMap.put("srlIfId", ppsKtJuoSubVo.getSrlIfId());

		//단말번호
		if(ppsKtJuoSubVo.getModelName() != null &&
				!StringUtils.isBlank(ppsKtJuoSubVo.getModelName()))
		{
			resultMap.put("modelName", ppsKtJuoSubVo.getModelName());
		}
		else
		{
			resultMap.put("modelName", "모델명");
		}
		
		if(ppsKtJuoSubVo.getImei() != null &&
				!StringUtils.isBlank(ppsKtJuoSubVo.getImei()))
		{
			resultMap.put("imei", ppsKtJuoSubVo.getImei());
		}
		else
		{
			resultMap.put("imei", "imei 번호");
		}
		
		if(ppsKtJuoSubVo.getIntmSrlNo() != null &&
				!StringUtils.isBlank(ppsKtJuoSubVo.getIntmSrlNo()))
		{
			resultMap.put("intmSrlNo", ppsKtJuoSubVo.getIntmSrlNo());
			resultMap.put("intmSrlNoMsk", ppsKtJuoSubVo.getIntmSrlNoMsk());
		}
		else
		{
			resultMap.put("intmSrlNo", "단말기일련번호");
			resultMap.put("intmSrlNoMsk", "단말기일련번호");
		}
		
		if(ppsKtJuoSubVo.getIccId() != null &&
				!StringUtils.isBlank(ppsKtJuoSubVo.getIccId()))
		{
			resultMap.put("iccId", ppsKtJuoSubVo.getIccId());
			resultMap.put("iccIdMsk", ppsKtJuoSubVo.getIccIdMsk());
		}
		else
		{
			resultMap.put("iccId", "USIM 카드 일련번호");
			resultMap.put("iccIdMsk", "USIM 카드 일련번호");
		}
		
		

		//개통대리점
		resultMap.put("agentNm", ppsCustomerVo.getAgentNm());
		resultMap.put("agentId", ppsCustomerVo.getAgentId());
		
		//판매점
		resultMap.put("agentSaleId", ppsCustomerVo.getAgentSaleId());
		resultMap.put("agentSaleNm", ppsCustomerVo.getAgentSaleNm());
		

		//웹등록여부
		if(ppsCustomerVo.getWwwRegFlagNm() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getWwwRegFlagNm()))
		{
			resultMap.put("wwwRegFlagNm", ppsCustomerVo.getWwwRegFlagNm());
		}
		else
		{
			resultMap.put("wwwRegFlagNm", "미등록");
		}

		//웹등록날짜
		if(ppsCustomerVo.getWwwRegDt() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getWwwRegDt()))
		{
			resultMap.put("wwwRegDt", ppsCustomerVo.getWwwRegDt());
		}
		else
		{
			resultMap.put("wwwRegDt", "없음");
		}

		//렌탈상태
		
		if(ppsCustomerVo.getRentalStatusNm() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getRentalStatusNm()))
		{
			resultMap.put("rentalStatusNm", ppsCustomerVo.getRentalStatusNm());
		}
		else
		{
			resultMap.put("rentalStatusNm", "렌탈불가");
		}

		//렌탈날짜
		
		if(ppsCustomerVo.getRentalDt() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getRentalDt()))
		{
			resultMap.put("rentalDt", ppsCustomerVo.getRentalDt());
		}
		else
		{
			resultMap.put("rentalDt", "0000-00-00");
		}

		//서류1
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImage1())){
			resultMap.put("paperImage1", ppsCustomerVo.getPaperImage1());
		}else{
			resultMap.put("paperImage1", "");
		}
		//서류2
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImage2())){
			resultMap.put("paperImage2", ppsCustomerVo.getPaperImage2());
		}else{
			resultMap.put("paperImage2", "");
		}
		//서류3
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImage3())){
			resultMap.put("paperImage3", ppsCustomerVo.getPaperImage3());
		}else{
			resultMap.put("paperImage3", "");
		}
		//서류4
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImage4())){
			resultMap.put("paperImage4", ppsCustomerVo.getPaperImage4());
		}else{
			resultMap.put("paperImage4", "");
		}
		//서류5
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImage5())){
			resultMap.put("paperImage5", ppsCustomerVo.getPaperImage5());
		}else{
			resultMap.put("paperImage5", "");
		}
		
		//서류1 O X
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImg1())){
			resultMap.put("paperImg1", ppsCustomerVo.getPaperImg1());
		}else{
			resultMap.put("paperImg1", "");
		}
		//서류2 O X
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImg2())){
			resultMap.put("paperImg2", ppsCustomerVo.getPaperImg2());
		}else{
			resultMap.put("paperImg2", "");
		}
		//서류3 O X
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImg3())){
			resultMap.put("paperImg3", ppsCustomerVo.getPaperImg3());
		}else{
			resultMap.put("paperImg3", "");
		}
		//서류4 O X
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImg4())){
			resultMap.put("paperImg4", ppsCustomerVo.getPaperImg4());
		}else{
			resultMap.put("paperImg4", "");
		}
		//서류5 O X
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImg5())){
			resultMap.put("paperImg5", ppsCustomerVo.getPaperImg5());
		}else{
			resultMap.put("paperImg5", "");
		}
		
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImg())){
			resultMap.put("paperImg", ppsCustomerVo.getPaperImg());
		}else{
			resultMap.put("paperImg", "");
		}
		
		
		
		
		// 서류 등록/미등록
		if(!StringUtils.isBlank(ppsCustomerVo.getPaperImage1()) || 
				!StringUtils.isBlank(ppsCustomerVo.getPaperImage2()) ||
				!StringUtils.isBlank(ppsCustomerVo.getPaperImage3()) ||
				!StringUtils.isBlank(ppsCustomerVo.getPaperImage4()) ||
				!StringUtils.isBlank(ppsCustomerVo.getPaperImage5()) )
		{
			resultMap.put("paperImage", "O");
		}
		else
		{
			resultMap.put("paperImage", "X");
		}

		//국적
		resultMap.put("adNation", ppsCustomerVo.getAdNation());
		
		
		
		if(ppsCustomerVo.getAdNationNm() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getAdNationNm()))
		{
			resultMap.put("adNationNm", ppsCustomerVo.getAdNationNm());
		}
		else
		{
			resultMap.put("adNationNm", "국적 미등록");
		}
		if(ppsCustomerVo.getLangCodeNm() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getLangCodeNm()))
		{
			resultMap.put("langCodeNm", ppsCustomerVo.getLangCodeNm());
		}
		else
		{
			resultMap.put("langCodeNm", "언어 미등록");
		}
		if(ppsCustomerVo.getLangCode() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getLangCode()))
		{
			resultMap.put("langCode", ppsCustomerVo.getLangCode());
		}
		else
		{
			resultMap.put("langCode", "");
		}

		//충전/문자/발송번호
		if(ppsCustomerVo.getRechargeFlag() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getRechargeFlag()))
		{
			resultMap.put("rechargeFlag",  ppsCustomerVo.getRechargeFlag());
			
			if(ppsCustomerVo.getRechargeFlag().equals("Y")){
				if(ppsCustomerVo.getStayExpirFlag().equals("Y")){
					resultMap.put("rechargeStr",  "<font color='blue'>충전가능</font>");
				}else{
					resultMap.put("rechargeStr",  "<font color='red'>체류기간만료</font>");
				}
			}else{
				resultMap.put("rechargeStr",  "<font color='red'>충전불가</font>");
			}
		}
		else
		{
			resultMap.put("rechargeFlag", "N");
			
			resultMap.put("rechargeStr",  "<font color='red'>충전불가</font>");
		}
		
		if(ppsCustomerVo.getWarFlag() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getWarFlag()))
		{
			resultMap.put("warFlagStr",  "<font color='red'><<주의고객>></font>");
		}
		else
		{
			resultMap.put("warFlagStr",  "");
		}
		
		if(ppsCustomerVo.getRechargeFlagNm() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getRechargeFlagNm()))
		{
			resultMap.put("rechargeFlagNm", ppsCustomerVo.getRechargeFlagNm());
		}
		else
		{
			resultMap.put("rechargeFlagNm", "충전불가");
		}
		if(ppsCustomerVo.getSmsFlagNm() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getSmsFlagNm()))
		{
			resultMap.put("smsFlagNm", ppsCustomerVo.getSmsFlagNm());
		}
		else
		{
			resultMap.put("smsFlagNm", "문자미발송");
		}
		if(ppsCustomerVo.getSmsFlag() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getSmsFlag()))
		{
			resultMap.put("smsFlag", ppsCustomerVo.getSmsFlag());
		}
		else
		{
			resultMap.put("smsFlag", "N");
		}
		
		if(ppsCustomerVo.getSmsPhone() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getSmsPhone()))
		{
			resultMap.put("smsPhone", ppsCustomerVo.getSmsPhone());
			resultMap.put("smsPhoneMsk", ppsCustomerVo.getSmsPhoneMsk());
		}
		else
		{
			resultMap.put("smsPhone", "");
			resultMap.put("smsPhoneMsk", "");
		}
		
		if(ppsCustomerVo.getVizaFlag() != null &&
				!StringUtils.isBlank(ppsCustomerVo.getVizaFlag()))
		{
			resultMap.put("vizaFlag", ppsCustomerVo.getVizaFlag());
		}
		else
		{
			resultMap.put("vizaFlag", "N");
		}
		
		//고객식별구분코드
		if(!StringUtils.isBlank(ppsKtJuoCusVo.getCustIdntNoIndCd()))
		{
			resultMap.put("custIdntNoIndCd", ppsKtJuoCusVo.getCustIdntNoIndCd());
		}else{
			resultMap.put("custIdntNoIndCd", "");
		}
		
		//고객식별구분코드명
		if(!StringUtils.isBlank(ppsKtJuoCusVo.getCustIdntNoIndCdNm()))
		{
			resultMap.put("custIdntNoIndCdNm", ppsKtJuoCusVo.getCustIdntNoIndCdNm());
		}else{
			resultMap.put("custIdntNoIndCdNm", "");
		}
		
		//체류기간
		if(!StringUtils.isBlank(ppsCustomerVo.getStayExpirDt()))
		{
			resultMap.put("stayExpirDt", ppsCustomerVo.getStayExpirDt());
		}else{
			resultMap.put("stayExpirDt", "");
		}
		
		//PPS35충전가능금액
		resultMap.put("topupRcgAmt", ppsCustomerVo.getTopupRcgAmt());		
		
		resultMap.put("retCode","000");
		resultMap.put("retMsg", "");


		logger.debug(" service resultMap ["+resultMap.toString()+"]" );

		return resultMap;
	}


	/**
	    * 가상계좌정보 조회
	    * @param pRequestParamMap
	    * @return
	    * 
	    */
		public PpsVacVo getPpsVacData(Map<String, Object> pRequestParamMap) {

			PpsVacVo ppsVacVo = new PpsVacVo();
			ppsVacVo = hdofcCustMngMapper.getPpsVacData(pRequestParamMap);
			logger.debug("==============처리 후 Service \n==============");
			logger.debug("ppsVacVo===>"+ppsVacVo+"\n");
			logger.debug("===========================");




			return ppsVacVo;

		}

		/**
		 * 실시간 자동이체 tab목록조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsRcgRealCmsTabList(Map<String, Object> pRequestParamMap) {
			List<?> resultList = new ArrayList<PpsRcgRealCmsVo>();

			resultList = hdofcCustMngMapper.getPpsRcgRealCmsTabList(pRequestParamMap);

			return resultList;

		}

		/**
		 *  충전tab목록조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsRcgTabList(Map<String, Object> pRequestParamMap) {
			List<?> resultList = new ArrayList<PpsRcgVo>();

			resultList = hdofcCustMngMapper.getPpsRcgTabList(pRequestParamMap);

			return resultList;

		}

		/**
		 * 통화내역tab목록조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsCdrPpTabList(Map<String, Object> pRequestParamMap) {
			List<?> resultList = new ArrayList<PpsCdrPpVo>();

			StringBuilder tableNm = new StringBuilder();
			StringBuilder idxNm = new StringBuilder();
			if(pRequestParamMap != null && pRequestParamMap.get("tableName") != null)
			{
				tableNm.append("PPS_CDR_PP_");
				tableNm.append(pRequestParamMap.get("tableName").toString());

				idxNm.append(tableNm).append("_IDX1");
				pRequestParamMap.put("tableName", tableNm);
				pRequestParamMap.put("idxName", idxNm);
			}

			resultList = hdofcCustMngMapper.getPpsCdrPpTabList(pRequestParamMap);

			return resultList;

		}

		/**
		 * 일사용내역tab목록조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsCdrPktTabList(Map<String, Object> pRequestParamMap) {
			List<?> resultList = new ArrayList<PpsCdrPpVo>();

			StringBuilder tableNm = new StringBuilder();
			StringBuilder idxNm = new StringBuilder();
			if(pRequestParamMap != null && pRequestParamMap.get("tableName") != null)
			{
				tableNm.append("PPS_CDR_PP_DAILY_");
				tableNm.append(pRequestParamMap.get("tableName").toString());

				idxNm.append(tableNm).append("_IDX1");
				pRequestParamMap.put("tableName", tableNm);
				pRequestParamMap.put("idxName", idxNm);
			}

			logger.debug("pRequestParamMap===>"+pRequestParamMap+"\n");
			resultList = hdofcCustMngMapper.getPpsCdrPktTabList(pRequestParamMap);

			return resultList;

		}


		/**
		 * 문자사용내역tab목록조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsSmsTabList(Map<String, Object> pRequestParamMap) {
			List<?> resultList = new ArrayList<PpsSmsVo>();

			resultList = hdofcCustMngMapper.getPpsSmsTabList(pRequestParamMap);
			
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("calledNumber","MOBILE_PHO");

			maskingService.setMask(resultList, maskFields, pRequestParamMap);
			
			return resultList;

		}

		/**
		 * 상담내역tab목록조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsCustomerDiaryTabList(Map<String, Object> pRequestParamMap) {
			//List<?> resultList = new ArrayList<PpsCustomerDiaryVo>();

			List<EgovMap> resultList = (List<EgovMap>)hdofcCustMngMapper.getPpsCustomerDiaryTabList(pRequestParamMap);
			
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("reqUserName","CUST_NAME");
			maskFields.put("regAdminNm","CUST_NAME");
			maskFields.put("resAdminNm","CUST_NAME");

			maskingService.setMask(resultList, maskFields, pRequestParamMap);			
			
			for(EgovMap map : resultList){
				String diaryId =  map.get("diaryId").toString().trim();
				if(!StringUtils.isBlank(diaryId))
				{
					StringBuffer sb = new StringBuffer();
					String tmp = "^javaScript:getDiaryDetailFromDiaryId("+diaryId+");^_self";
					sb.append(diaryId).append(tmp);
					map.put("diaryId",sb.toString());
				}
			}

			return resultList;

		}

		/**
		 * 상담내역tab목록조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public Map<String, Object> getPpsCustomerDiaryWriteUpdate(Map<String, Object> pRequestParamMap) {


//			procPpsDiaryWriteUpdate
			Map<String, Object> resultMap = new HashMap<String, Object>();

			logger.debug(pRequestParamMap.toString());

			resultMap.put("opCode",pRequestParamMap.get("opCode") );
			resultMap.put("diaryId",pRequestParamMap.get("diaryId") );
			resultMap.put("contractNum",pRequestParamMap.get("contractNum") );
			resultMap.put("reqUserName",pRequestParamMap.get("reqUserName") );
			resultMap.put("reqUserGubun",pRequestParamMap.get("reqUserGubun") );
			resultMap.put("reqType",pRequestParamMap.get("reqType") );
			resultMap.put("reqBody", pRequestParamMap.get("reqBody"));
			resultMap.put("resStatus", pRequestParamMap.get("resStatus"));
			resultMap.put("resBody", pRequestParamMap.get("resBody"));
			resultMap.put("resStartDate", pRequestParamMap.get("resStartDate"));
			resultMap.put("resEndDate", pRequestParamMap.get("resEndDate"));
			resultMap.put("regDate", pRequestParamMap.get("regDate"));
			//regAdmin은 contoller단에서
			if(pRequestParamMap.get("bonsaId") != null && !StringUtils.isBlank(pRequestParamMap.get("bonsaId").toString()))
			{
				if("ADD".equals(pRequestParamMap.get("opCode")))
				{
					resultMap.put("regAdmin", pRequestParamMap.get("bonsaId"));
				}
				else if("EDT".equals(pRequestParamMap.get("opCode")))
				{
					resultMap.put("resAdmin", pRequestParamMap.get("bonsaId"));
				}
				else if("DEL".equals(pRequestParamMap.get("opCode")))
				{
					resultMap.put("resAdmin", pRequestParamMap.get("bonsaId"));
				}
					
			}
			
			resultMap.put("agentId", pRequestParamMap.get("agentId"));
			resultMap.put("bonsaId", pRequestParamMap.get("bonsaId"));
			resultMap.put("remark", pRequestParamMap.get("remark"));
			resultMap.put("status", pRequestParamMap.get("status"));
			resultMap.put("authFlag", pRequestParamMap.get("authFlag"));

			resultMap.put("retCode","" );
			resultMap.put("retMsg","" );

			hdofcCustMngMapper.procPpsDiaryWriteUpdate(resultMap);

			return resultMap;

		}


		/**
		 * 상담내역 상세조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public PpsCustomerDiaryVo getPpsCustomerDiaryInfo(Map<String, Object> pRequestParamMap) {
			PpsCustomerDiaryVo ppsCustomerDiaryVo = new PpsCustomerDiaryVo();
			ppsCustomerDiaryVo = hdofcCustMngMapper.getPpsCustomerDiaryInfo(pRequestParamMap);

			return ppsCustomerDiaryVo;
		}

		/**
		 * 선불충전처리
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> procPpsRecharge(Map<String, Object> pRequestParamMap) {
			Map<String,Object> resultMap = new HashMap<String,Object>();

			resultMap.put("opCode",pRequestParamMap.get("opCode") );
			resultMap.put("reqType",pRequestParamMap.get("reqType") );
			resultMap.put("recharge",pRequestParamMap.get("recharge") );
			resultMap.put("rechargeInfo",pRequestParamMap.get("rechargeInfo") );
			resultMap.put("subscriberNo",pRequestParamMap.get("subscriberNo") );
			resultMap.put("constractNum",pRequestParamMap.get("constractNum") );
			resultMap.put("rechargeIp", pRequestParamMap.get("rechargeIp"));
			resultMap.put("dataVaId", pRequestParamMap.get("dataVaId"));
			resultMap.put("adminId", pRequestParamMap.get("adminId"));


			resultMap.put("seq","" );
			resultMap.put("oSeq", "");
			resultMap.put("retCode","" );
			resultMap.put("retMsg","" );

			logger.debug("==============충전처리 전 Service \n==============");
			logger.debug("resultMap===>"+resultMap+"\n");
			logger.debug("===========================");

			hdofcCustMngMapper.procPpsRecharge(resultMap);
			logger.debug("==============충전처리 후 Service \n==============");
			logger.debug("resultMap===>"+resultMap+"\n");
			logger.debug("===========================");
			
			PpsKtInResVo ppsKtInResVo = new PpsKtInResVo();
			ppsKtInResVo = hdofcCommonService.ppsHdofcSleep(resultMap);
			
			resultMap.put("oResCode", ppsKtInResVo.getoResCode());
			logger.debug(ppsKtInResVo.getoResCode());
			if(ppsKtInResVo.getoResCode().equals("0000")){
				resultMap.put("oResCodeNm", "충전되었습니다.");
			}else{
				resultMap.put("oResCodeNm", ppsKtInResVo.getoResCodeNm());
			}

			logger.debug(resultMap);
			return resultMap;
		}

		/**
		 * 선불 pin정보조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public Map<String, Object> getPpsPinInfoData(Map<String, Object> pRequestParamMap) {


			Map<String,Object> resultMap =  hdofcCustMngMapper.getPpsPinInfoData(pRequestParamMap);
			String retMsg = "잔액 조회중 오류가 발생 하였습니다";
			String retCode = "-300";
			String pinPrice ="0";
			if(resultMap==null || resultMap.get("nowPrice")==null){
				resultMap = new HashMap<String,Object>();
				retMsg ="존재하지 않는 카드번호입니다.";
				retCode = "-200";
				pinPrice ="0";

			}else if(String.valueOf(resultMap.get("nowPrice")).equals("0")){
				retMsg= "잔액이 소진된 카드입니다.";
				retCode="-100";
				pinPrice = String.valueOf(resultMap.get("nowPrice"));

			}else if(!String.valueOf(resultMap.get("NOW_PRICE")).equals("")){
				retMsg = "잔액이 확인 되었습니다.";
				retCode = "0000";
				pinPrice = String.valueOf(resultMap.get("nowPrice"));

			}

			resultMap.put("retMsg", retMsg);
			resultMap.put("retCode", retCode);
			resultMap.put("pinPrice", pinPrice);

			logger.debug("  service getPpsPinInfoData   resultMap===>"+resultMap+"\n");

			return resultMap;
		}

		/**
		 * 선불 가상계좌 처리
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> procPpsVac(Map<String, Object> pRequestParamMap) {
			Map<String,Object> resultMap = new HashMap<String,Object>();

			
			resultMap.put("opCode",pRequestParamMap.get("opCode") );
			resultMap.put("opMode",pRequestParamMap.get("opMode") );
			resultMap.put("userType",pRequestParamMap.get("userType") );

			resultMap.put("reqBankCode",pRequestParamMap.get("reqBankCode") );
			resultMap.put("reqBankName",pRequestParamMap.get("reqBankName") );
			resultMap.put("userNumber",pRequestParamMap.get("agentId") );
			resultMap.put("userNumber",pRequestParamMap.get("userNumber") );
			resultMap.put("adminId",pRequestParamMap.get("adminId") );
			
			resultMap.put("retCode","" );
			resultMap.put("retMsg","" );

			logger.debug("==============처리 전 Service \n==============");
			logger.debug("resultMap===>"+resultMap+"\n");
			logger.debug("===========================");

			hdofcCustMngMapper.procPpsVac(resultMap);
			logger.debug("==============처리 후 Service \n==============");
			logger.debug("resultMap===>"+resultMap+"\n");
			logger.debug("===========================");


			return resultMap;
		}

		/**
		 * 선불 문자 발송처리
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> procPpsSms(Map<String, Object> pRequestParamMap) {
			
			Map<String,Object> resultMap = new HashMap<String,Object>();

			
			resultMap.put("event",pRequestParamMap.get("event") );
			resultMap.put("contractNum",pRequestParamMap.get("contractNum") );
			
			resultMap.put("lang","" );
			resultMap.put("msg",pRequestParamMap.get("msg") );
			resultMap.put("sendDate","" );
			resultMap.put("callBack",pRequestParamMap.get("callBack") );
			resultMap.put("var1","" );
			
			if(pRequestParamMap.get("event").equals("AGENT_VAC_INFO")){
				resultMap.put("var2",pRequestParamMap.get("loginAgentId") );
			}else{
				resultMap.put("var2","" );
			}
			
			resultMap.put("var3","" );
			resultMap.put("var4","" );
			resultMap.put("var5","" );

			resultMap.put("oRetCd","" );
			resultMap.put("oRetMsg","" );
			
			logger.debug("==============처리 전 Service ==============");
			logger.debug("resultMap===>"+resultMap+"\n");
			logger.debug("===========================");
			
			hdofcCustMngMapper.getPpsRemainsSms(resultMap);
			logger.debug("==============처리 후 Service ==============");
			logger.debug("resultMap===>"+resultMap+"\n");
			logger.debug("===========================");


			return resultMap;
		}

		/**
		 * 실시간이체 설정
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> settingPpsCustomerRealCms(Map<String, Object> pRequestParamMap) {


//			procPpsDiaryWriteUpdate
			Map<String, Object> resultMap = new HashMap<String, Object>();

			logger.debug(pRequestParamMap.toString());

			resultMap.put("contractNum",pRequestParamMap.get("contractNum") );
			resultMap.put("subscriberNo",pRequestParamMap.get("subscriberNo") );
			resultMap.put("cmsBankCode",pRequestParamMap.get("cmsBankCode") );
			resultMap.put("cmsAccount",pRequestParamMap.get("cmsAccount") );

			resultMap.put("cmsUserName","" );
			resultMap.put("cmsUserSsn","" );
			resultMap.put("cmsAgreeFlag", pRequestParamMap.get("cmsAgreeFlag"));
			resultMap.put("cmsCharge", pRequestParamMap.get("cmsCharge"));
			resultMap.put("cmsTryRemains", pRequestParamMap.get("cmsTryRemains"));

			resultMap.put("cmsChargeType", pRequestParamMap.get("cmsChargeType"));
			resultMap.put("cmsChargeDate", pRequestParamMap.get("cmsChargeDate"));

			resultMap.put("cmsChargeMonth", pRequestParamMap.get("cmsChargeMonth"));
			resultMap.put("nowCharge", pRequestParamMap.get("nowCharge"));
			resultMap.put("adminId", pRequestParamMap.get("adminId"));

			resultMap.put("retCode","" );
			resultMap.put("retMsg","" );

			hdofcCustMngMapper.procPpsRcgRealCmsSetting(resultMap);

			return resultMap;

		}

		/**
		 * 고객 잔액갱신
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> getPpsCusRemains(Map<String, Object> pRequestParamMap) {

			String seq = hdofcCommonService.getPpsKtSeq();
			pRequestParamMap.put("seq", seq);
			hdofcCustMngMapper.getPpsCusRemains(pRequestParamMap);
			PpsKtInResVo ppsKtInResVo = new PpsKtInResVo();
			ppsKtInResVo = hdofcCommonService.ppsHdofcSleep(pRequestParamMap);
			Map<String, Object> resultMap  =new HashMap<String, Object>();

			resultMap.put("oResCode", ppsKtInResVo.getoResCode());
			if(ppsKtInResVo.getoResCode().equals("0000")){
				Map<String, Object> resultMapTmp  =new HashMap<String, Object>();
				resultMapTmp.put("eventCd", "BS_REMAINS_VIEW");
				resultMapTmp.put("val1", pRequestParamMap.get("contractNum"));
				resultMapTmp.put("val2", pRequestParamMap.get("seq"));
				
				List<EgovMap> resultList = (List<EgovMap>)hdofcCommonService.getPpsCodeDescInfoList(resultMapTmp);
				for(EgovMap map : resultList){
					if(map.get("descInfo")!=null && !map.get("descInfo").equals("") ){
						String descInfo =  map.get("descInfo").toString().trim();
						resultMap.put("oResCodeNm", descInfo);
					}else{
						resultMap.put("oResCodeNm", "현재 잔액은 "+ppsKtInResVo.getoRemains()+"원 입니다.");
					}
				}
				
				/*
				if(topupCnt > 0){
					DecimalFormat df = new DecimalFormat("0");
					resultMap.put("oResCodeNm", "현재 잔액은 "+ppsKtInResVo.getoRemains()+"원 입니다. <br/>"
								  + "남은 음성통화량은 " + df.format(ppsKtInResVo.getoRefillTime()/60) + "분 입니다. <br/>"
								  + "남은 문자량은 " + ppsKtInResVo.getoRefillMsg() + "건 입니다. <br/>"
								  + "남은 데이터량은 " + ppsKtInResVo.getoFreeData() + "M 입니다.");
				}else{
					resultMap.put("oResCodeNm", "현재 잔액은 "+ppsKtInResVo.getoRemains()+"원 입니다.");
				}
				*/
				
				resultMap.put("currentRemains", ppsKtInResVo.getoRemainsStr());
			}else{
				resultMap.put("oResCodeNm", ppsKtInResVo.getoResCodeNm());
				resultMap.put("currentRemains", "0");
			}

			return resultMap;
		}

		/**
		 * 고객 선불잔액 문자발송
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> getPpsRemainsSms(Map<String, Object> pRequestParamMap) {

			hdofcCustMngMapper.getPpsRemainsSms(pRequestParamMap);
			return pRequestParamMap;
		}

		/**
		 * 고객 충전가능/불가능 변경
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> ppsCusRcgFlagChg(Map<String, Object> pRequestParamMap) {

			hdofcCustMngMapper.getPpsCusRcgFlagChg(pRequestParamMap);
			
			PpsCustomerVo ppsCustomerVo = getPpsCustmerInfo(pRequestParamMap);
			if(ppsCustomerVo == null)
			{
				ppsCustomerVo = new PpsCustomerVo();
			}

			if(ppsCustomerVo.getRechargeFlag() != null &&
					!StringUtils.isBlank(ppsCustomerVo.getRechargeFlag()))
			{
				pRequestParamMap.put("rechargeFlag",  ppsCustomerVo.getRechargeFlag());
				
				if(ppsCustomerVo.getRechargeFlag().equals("Y")){
					if(ppsCustomerVo.getStayExpirFlag().equals("Y")){
						pRequestParamMap.put("rechargeStr",  "<font color='blue'>충전가능</font>");
					}else{
						pRequestParamMap.put("rechargeStr",  "<font color='red'>체류기간만료</font>");
					}
				}else{
					pRequestParamMap.put("rechargeStr",  "<font color='red'>충전불가</font>");
				}
			}
			else
			{
				pRequestParamMap.put("rechargeFlag", "N");
				
				pRequestParamMap.put("rechargeStr",  "<font color='red'>충전불가</font>");
			}
			
			if(ppsCustomerVo.getWarFlag() != null &&
					!StringUtils.isBlank(ppsCustomerVo.getWarFlag()))
			{
				pRequestParamMap.put("warFlagStr",  "<font color='red'><<주의고객>></font>");
			}
			else
			{
				pRequestParamMap.put("warFlagStr",  "");
			}
			
			// KT현행화 계약정보
			PpsKtJuoSubVo ppsKtJuoSubVo = getPpsKtJuoSubInfo(pRequestParamMap);
			if(ppsKtJuoSubVo == null)
			{
				ppsKtJuoSubVo = new PpsKtJuoSubVo();
			}

			HashMap<String, String> maskFields2 = new HashMap<String, String>();
			maskFields2.put("subscriberNoMsk","MOBILE_PHO");
			maskFields2.put("userSsnMsk","SSN");
			maskFields2.put("subAdrSecondaryLnMsk","ADDR");
			maskFields2.put("subLinkNameMsk","CUST_NAME");
			maskFields2.put("iccIdMsk","USIM");
			maskFields2.put("intmSrlNoMsk","DEV_NO");
			
			maskingService.setMask(ppsKtJuoSubVo, maskFields2, pRequestParamMap);	
			
			//CTN
			pRequestParamMap.put("subscriberNo",ppsKtJuoSubVo.getSubscriberNo());
			pRequestParamMap.put("subscriberNoMsk",ppsKtJuoSubVo.getSubscriberNoMsk());
			
			//상태
			pRequestParamMap.put("subStatusNm", ppsKtJuoSubVo.getSubStatusNm());
			
			//상태날짜
			pRequestParamMap.put("subStatusDate", ppsKtJuoSubVo.getSubStatusDate());
			
			return pRequestParamMap;
		}

		/**
		 * 고객 문자전송여부 변경
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> ppsCusSmsFlagChg(Map<String, Object> pRequestParamMap) {

			hdofcCustMngMapper.getPpsCusSmsFlagChg(pRequestParamMap);
			
			return pRequestParamMap;
		}
		
		/**
		 * 고객 비자접수여부 변경
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> ppsCusVizaFlagChg(Map<String, Object> pRequestParamMap) {

			hdofcCustMngMapper.getPpsCusVizaFlagChg(pRequestParamMap);
			
			return pRequestParamMap;
		}

		/**
		 * 고객 동보전송 문자번호 변경
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> ppsCusSmsPhoneUp(Map<String, Object> pRequestParamMap) {

			hdofcCustMngMapper.getPpsCusSmsPhoneUp(pRequestParamMap);
			
			return pRequestParamMap;
		}


		/**
		 * 고객관리 상세 국가리스트조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsCustomerNationInfoData(Map<String, Object> pRequestParamMap) {

			List<?> resultList = new ArrayList<PpsCustomerNationListVo>();

			resultList = hdofcCustMngMapper.getPpsCustomerNationInfoData(pRequestParamMap);


			return resultList;
		}

		/**
		 * 고개관리 상세 언어리스트조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsCustomerLanguageInfoData(Map<String, Object> pRequestParamMap) {

			List<?> resultList = new ArrayList<PpsCustomerLanguageListVo>();

			resultList = hdofcCustMngMapper.getPpsCustomerLanguageInfoData(pRequestParamMap);


			return resultList;
		}

		/**
		 * 고개관리 대리점 이름 리스트
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsCustomerOrgnInfoData(Map<String, Object> pRequestParamMap) {

			List<?> resultList = new ArrayList<PpsCustomerLanguageListVo>();

			resultList = hdofcCustMngMapper.getPpsCustomerOrgnInfoData(pRequestParamMap);


			return resultList;
		}

		/**
		 * 고객관리 상세 은행코드리스트 
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsVacCodeListData(Map<String, Object> pRequestParamMap) {
			
			List<?> resultList = new ArrayList<PpsCustomerNationListVo>();
			
			resultList = hdofcCustMngMapper.getPpsVacCodeListData(pRequestParamMap);
			
			
			return resultList;
		}
		
		/**
		 * 고객관리 상세 요금제코드리스트 
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsFeatureCodeListData(Map<String, Object> pRequestParamMap) {
			
			List<?> resultList = new ArrayList<PpsCustomerNationListVo>();
			
			resultList = hdofcCustMngMapper.getPpsFeatureCodeListData(pRequestParamMap);
			
			
			return resultList;
		}
		
		/**
		 * 고객관리 상세 부가요금제코드리스트 
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsFeatureAddtionCodeListData(Map<String, Object> pRequestParamMap) {
			
			List<?> resultList = new ArrayList<PpsCustomerNationListVo>();
			
			resultList = hdofcCustMngMapper.getPpsFeatureAddtionCodeListData(pRequestParamMap);
			
			
			return resultList;
		}
		
		
	    
	    /**
		 * 고객 국적/SMS 변경
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> ppsCusLangUpdatePop(Map<String, Object> pRequestParamMap) {

			hdofcCustMngMapper.getPpsCusLangUpdatePop(pRequestParamMap);
			
			return pRequestParamMap;
		}
		
		
		/**
		 *  대리점 개통 상세 가상계좌번호.은행명.은행코드 
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsAgencyOpenVacInfo(Map<String, Object> pRequestParamMap) {
			
			List<?> resultList = new ArrayList<PpsCustomerNationListVo>();
			
			resultList = hdofcCustMngMapper.getPpsAgencyVacInfo(pRequestParamMap);
			
			
			return resultList;
		}
		
		/**
		 *  대리점 일괄변경 
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> getPpsAllAgentChg(Map<String, Object> pRequestParamMap) {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("WHERE A.CONTRACT_NUM = B.CONTRACT_NUM ");
			selectQuery.append("AND A.CONTRACT_NUM = C.CONTRACT_NUM(+) ");
			selectQuery.append("AND A.CONTRACT_NUM = D.CONTRACT_NUM(+) ");
			selectQuery.append("AND A.SOC = E.RATE_CD ");
			selectQuery.append("AND B.CUSTOMER_ID = F.CUSTOMER_ID ");
			selectQuery.append("AND ENTER_DATE > TO_DATE('1900-01-01','YYYY-MM-DD') ");
			selectQuery.append("AND B.SUB_STATUS IN ('A','S') ");
			selectQuery.append("AND E.PAY_CL_CD = 'PP' ");			

			if(!pRequestParamMap.get("searchNm").equals("")){
				if(pRequestParamMap.get("searchType").equals("subscriberNo")){
					String strQuery = "AND B.CONTRACT_NUM = (select F_GET_LIKE_CTN2_CONTRACT_NUM('"+pRequestParamMap.get("searchNm")+"') from dual) ";
					selectQuery.append(strQuery);
				}else if(pRequestParamMap.get("searchType").equals("contractNum")){
					String strQuery = "AND A.CONTRACT_NUM = '"+pRequestParamMap.get("searchNm")+"' ";
					selectQuery.append(strQuery);
				}else if(pRequestParamMap.get("searchType").equals("customerId")){
					String strQuery = "AND B.CUSTOMER_ID = '"+pRequestParamMap.get("searchNm")+"' ";
					selectQuery.append(strQuery);
				}else if(pRequestParamMap.get("searchType").equals("customerBan")){
					String strQuery = "AND B.BAN = '"+pRequestParamMap.get("searchNm")+"' ";
					selectQuery.append(strQuery);
				}else if(pRequestParamMap.get("searchType").equals("subLinkName")){
					String strQuery = "AND B.SUB_LINK_NAME LIKE '%"+pRequestParamMap.get("searchNm")+"%' ";
					selectQuery.append(strQuery);
				}else if(pRequestParamMap.get("searchType").equals("userSsn")){
					String strQuery = "AND USER_SSN = CRYPTO_AES256.ENC_AES('"+pRequestParamMap.get("searchNm")+"') ";
					selectQuery.append(strQuery);
				}else if(pRequestParamMap.get("searchType").equals("vacId")){
					String strQuery = "AND VIR_ACCOUNT_ID = '"+pRequestParamMap.get("searchNm")+"' ";
					selectQuery.append(strQuery);
				}else{
					String strQuery = "AND" + pRequestParamMap.get("searchType")+ "='" + pRequestParamMap.get("searchNm")+"' ";
					selectQuery.append(strQuery);
				}
			}else{
				String strQuery = "";
				if(!pRequestParamMap.get("startDt").equals("")){
					strQuery = "AND ENTER_DATE >= TO_DATE('"+pRequestParamMap.get("startDt")+"', 'YYYY-MM-DD') ";
					selectQuery.append(strQuery);
				}
				if(!pRequestParamMap.get("endDt").equals("")){
					strQuery = "AND ENTER_DATE < TO_DATE('"+pRequestParamMap.get("endDt")+"', 'YYYY-MM-DD')+1 ";
					selectQuery.append(strQuery);
				}
			}
			
			
			if(!pRequestParamMap.get("searchAgentId").equals("")){
				String strQuery = "AND AGENT_ID = '"+pRequestParamMap.get("searchAgentId")+"' ";
				selectQuery.append(strQuery);
			}
			if(!pRequestParamMap.get("searchStartRemains").equals("")){
				String strQuery = "AND BASIC_REMAINS >= '"+pRequestParamMap.get("searchStartRemains")+"' ";
				selectQuery.append(strQuery);
			}
			if(!pRequestParamMap.get("searchEndRemains").equals("")){
				String strQuery = "AND BASIC_REMAINS <= '"+pRequestParamMap.get("searchEndRemains")+"' ";
				selectQuery.append(strQuery);
			}
			if(!pRequestParamMap.get("searchServiceType").equals("")){
				String strQuery = "AND A.SERVICE_TYPE = '"+pRequestParamMap.get("searchServiceType")+"' ";
				selectQuery.append(strQuery);
			}
			if(!pRequestParamMap.get("searchFeatures").equals("")){
				String strQuery = "AND A.SOC = '"+pRequestParamMap.get("searchFeatures")+"' ";
				selectQuery.append(strQuery);
			}
			if(!pRequestParamMap.get("searchSubStatus").equals("")){
				String strQuery = "AND B.SUB_STATUS = '"+pRequestParamMap.get("searchSubStatus")+"' ";
				selectQuery.append(strQuery);
			}
			if(!pRequestParamMap.get("searchDpRcgType").equals("")){
				if(pRequestParamMap.get("searchDpRcgType").equals("X")){
					selectQuery.append("AND A.AGNT_DP_RCG_FLAG = 'Y' AND AGNT_DP_RCG_CNT IS NOT NULL AND AGNT_DP_RCG_NOW_CNT IS NOT NULL AND AGNT_DP_RCG_CNT = AGNT_DP_RCG_NOW_CNT ");
				}else if(pRequestParamMap.get("searchDpRcgType").equals("Y")){
					String strQuery = "AND A.AGNT_DP_RCG_FLAG = '"+pRequestParamMap.get("searchDpRcgType")+"' ";
					selectQuery.append(strQuery);
				}else{
					String strQuery = "AND A.AGNT_DP_RCG_FLAG = '"+pRequestParamMap.get("searchDpRcgType")+"' OR A.AGNT_DP_RCG_FLAG IS NULL ";
					selectQuery.append(strQuery);
				}
			}
			if(!pRequestParamMap.get("searchRealCms").equals("")){
				if(pRequestParamMap.get("searchRealCms").equals("Y")){
					selectQuery.append("AND CMS_CHARGE_TYPE IN ('1','2') ");
				}else if(pRequestParamMap.get("searchRealCms").equals("N")){
					selectQuery.append("AND CMS_CHARGE_TYPE IN ('N','0') ");
				}
			}
			if(!pRequestParamMap.get("searchAgentSale").equals("")){
				String strQuery = "AND (A.AGENT_SALE_ID LIKE '%"+pRequestParamMap.get("searchAgentSale")+"%' OR AGENT_SALE_ID IN (SELECT ORGN_ID FROM ORG_ORGN_INFO_MST WHERE ORGN_LVL_CD = '20' AND ORGN_NM LIKE '%"+pRequestParamMap.get("searchAgentSale")+"%')) ";
				selectQuery.append(strQuery);
			}
			if(!pRequestParamMap.get("searchVac").equals("")){
				if(pRequestParamMap.get("searchVac").equals("Y")){
					selectQuery.append("AND VIR_ACCOUNT_ID IS NOT NULL ");
				}else if(pRequestParamMap.get("searchVac").equals("N")){
					selectQuery.append("AND VIR_ACCOUNT_ID IS NULL ");
				}
			}
			if(!pRequestParamMap.get("searchPaperImg").equals("")){
				if(pRequestParamMap.get("searchPaperImg").equals("O")){
					selectQuery.append("AND D.CONTRACT_NUM IS NOT NULL  ");
				}else if(pRequestParamMap.get("searchPaperImg").equals("X")){
					selectQuery.append("AND D.CONTRACT_NUM IS NULL ");
				}
			}
			if(!pRequestParamMap.get("minorYn").equals("") && pRequestParamMap.get("minorYn").equals("Y")){
				selectQuery.append("AND C.CSTMR_TYPE = 'NM' ");
			}
			if(!pRequestParamMap.get("appBlckAgrmYn").equals("") && pRequestParamMap.get("appBlckAgrmYn").equals("Y")){
				selectQuery.append("AND C.APP_BLCK_AGRM_YN = 'Y' ");
			}
			if(!pRequestParamMap.get("appInstYn").equals("") && pRequestParamMap.get("appInstYn").equals("Y")){
				selectQuery.append("AND C.APP_INST_YN = 'Y' ");
			}
			
			if(!pRequestParamMap.get("searchMnpIndCd").equals("") && pRequestParamMap.get("searchMnpIndCd").equals("N")){
				selectQuery.append("AND B.MNP_IND_CD IS NULL ");
			}
			
			if(!pRequestParamMap.get("searchMnpIndCd").equals("") && pRequestParamMap.get("searchMnpIndCd").equals("M")){
				selectQuery.append("AND B.MNP_IND_CD = '1' ");
			}
			
			if(!pRequestParamMap.get("searchVizaFlag").equals("")){
				if(pRequestParamMap.get("searchVizaFlag").equals("Y")){
					selectQuery.append("AND A.VIZA_FLAG = 'Y' ");
				}else if(pRequestParamMap.get("searchVizaFlag").equals("N")){
					selectQuery.append("AND (A.VIZA_FLAG IS NULL OR A.VIZA_FLAG = 'N' ");
				}
			}
			
			if(!pRequestParamMap.get("searchRechargeFlag").equals("")){
				if(pRequestParamMap.get("searchRechargeFlag").equals("Y")){
					selectQuery.append("AND A.RECHARGE_FLAG = 'Y' ");
				}else if(pRequestParamMap.get("searchRechargeFlag").equals("N")){
					selectQuery.append("AND (A.RECHARGE_FLAG IS NULL OR A.RECHARGE_FLAG = 'N' ");
				}
			}
			
			logger.debug("selectQuery="+selectQuery);
			logger.debug("||agentId="+pRequestParamMap.get("agentId"));
			logger.debug("||agentSaleId="+pRequestParamMap.get("agentSaleId"));
			logger.debug("||changeReaSon="+pRequestParamMap.get("changeReason"));	
			logger.debug("||adminId="+pRequestParamMap.get("adminId"));
			logger.debug("||ip="+pRequestParamMap.get("ip"));	
			pRequestParamMap.put("selectQuery", String.valueOf(selectQuery));
			hdofcCustMngMapper.getPpsAllAgentChg(pRequestParamMap);
			
			return pRequestParamMap;
		}
		
		
		
		/**
		 * 고객관리 상세 은행코드리스트 
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Crypto(decryptName="DBMSDec", fields = {"cmsAccount"})
		public PpsCustomerVo getPpsHdofcUserCmsInfo(Map<String, Object> pRequestParamMap) {
			
			
			PpsCustomerVo ppsCustomerVo = new PpsCustomerVo();

			ppsCustomerVo = hdofcCustMngMapper.getPpsHdofcUserCmsInfo(pRequestParamMap);

			return ppsCustomerVo;
			
			
		}
		
		
		/**
		 * 통화내역tab목록조회 엑셀출력
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public void getPpsCdrPpTabListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
			
			ExcelParam param = new ExcelParam();
			param.setStrHead("통화구분","계약번호","착신번호","발신시간","사용시간","과금액","통화종류","국제사업자","잔액","국가코드");
			param.setStrValue("CALL_GUBUN_NAME","CONTRACT_NUM","CALLED_NUM","START_TIME","USED_TIME","CHARGE","CALL_TYPE","TELCO_PX","REMAINS","KOR_NAME");
			param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
			param.setIntLen(0, 0, 0, 0, 0, 1, 0, 0, 1,0);
			param.setSheetName("통화내역");
			param.setExcelPath(path);
			param.setFileName("test");
			
			
			List<?> lCmnMskGrp =  maskingMapper.selectOnlyList(pRequestParamMap);
			
			param.setCmnMaskGrpList(lCmnMskGrp);
				
			Decryptor dec = factory.getDecryptorByName("DBMSDec");
			
			TestHandler handler  = new TestHandler();
			handler.setDecryptor(dec);
				
				
			File f = makeBigDataExcel("com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper.getPpsCdrPpTabListExcel", pRequestParamMap, param, handler);
			String fileName = "사용자통화내역정보_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
			
			//=======파일다운로드사유 로그 START==========================================================
	        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
	            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
	            
	            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
	                ipAddr = request.getHeader("REMOTE_ADDR");
	           
	            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
	                ipAddr = request.getRemoteAddr();
	            
	            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
	            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
	            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
	            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
	            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
	            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
	            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
	            
	            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
	        }
	        //=======파일다운로드사유 로그 END==========================================================
	        
			try {
				doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
			} catch (UnsupportedEncodingException e) {

//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
//				20210706 소스코드점검 수정
//				System.out.println("Connection Exception occurred");
				logger.error("Connection Exception occurred");
			}

		}
		
				
		
		/**
		 * 일사용내역tab목록조회엑셀출력
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public void getPpsCdrPktTabListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

			ExcelParam param = new ExcelParam();
			param.setStrHead("통화구분","계약번호","사용일자","시작시간","종료시간","누적사용량(K)","과금액","기존잔액","데이터잔량(K)","데이터만료일","소속대리점");
			param.setStrValue("CALL_GUBUN_NAME","CONTRACT_NUM","ACCESS_DT","START_TIME","END_TIME","PKTKBYTE","CHARGE","REMAINS","DATA_REMAINS","DATA_EXPIRE","AGENT_ID_NM");
			param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000);
			param.setIntLen(0, 0, 0, 0, 0, 1, 1, 1, 1,0,0);
			param.setSheetName("일사용내역");
			param.setExcelPath(path);
			param.setFileName("test");
			
			List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
			
			param.setCmnMaskGrpList(lCmnMskGrp);
				
			Decryptor dec = factory.getDecryptorByName("DBMSDec");
			
			TestHandler handler  = new TestHandler();
			handler.setDecryptor(dec);
				
				
			File f = makeBigDataExcel("com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper.getPpsCdrPktTabListExcel", pRequestParamMap, param, handler);
			String fileName = "사용자일사용내역정보_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
			
			//=======파일다운로드사유 로그 START==========================================================
	        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
	            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
	            
	            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
	                ipAddr = request.getHeader("REMOTE_ADDR");
	           
	            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
	                ipAddr = request.getRemoteAddr();
	            
	            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
	            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
	            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
	            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
	            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
	            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
	            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
	            
	            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
	        }
	        //=======파일다운로드사유 로그 END==========================================================
	        
			try {
				doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
			} catch (UnsupportedEncodingException e) {

//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
//				20210706 소스코드점검 수정
//				System.out.println("Connection Exception occurred");
				logger.error("Connection Exception occurred");
			}	

		}
		
		/**
		 * 상담내역tab목록상세
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public PpsCustomerDiaryVo getPpsCustomerDiaryTabDetail(Map<String, Object> pRequestParamMap) {

			PpsCustomerDiaryVo ppsCustomerDiaryVo = new PpsCustomerDiaryVo();
			ppsCustomerDiaryVo = hdofcCustMngMapper.getPpsCustomerDiaryTabDetail(pRequestParamMap);
			logger.debug("==============처리 후 Service \n==============");
			logger.debug("ppsCustomerDiaryVo===>"+ppsCustomerDiaryVo+"\n");
			logger.debug("===========================");

			return ppsCustomerDiaryVo;

		}
		
		/**
		 * 개통리스트 대리점변경
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> getPpsAgentInfoChg(Map<String, Object> pRequestParamMap) {
			hdofcCustMngMapper.getPpsAgentInfoChg(pRequestParamMap);
			
			logger.debug("==============처리 후 Service \n==============");
			logger.debug("resultMap===>"+pRequestParamMap.toString()+"\n");
			logger.debug("===========================");

			return pRequestParamMap;

		}		
		
		
		/**
		 * 은행코드리스트 실시간이체용 
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsRcgCodeListData(Map<String, Object> pRequestParamMap) {
			
			List<?> resultList = new ArrayList<PpsCustomerNationListVo>();
			
			resultList = hdofcCustMngMapper.getPpsRcgCodeListData(pRequestParamMap);
			
			
			return resultList;
		}
		
		/**
		 * 선불고객 수동등록
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> getPpsCustomerProc(Map<String, Object> pRequestParamMap) {
			Map<String, Object> resultMap = new HashMap<String, Object>();

			logger.debug(pRequestParamMap.toString());
			
			resultMap.put("opCode",pRequestParamMap.get("opCode"));
			resultMap.put("contractNum",pRequestParamMap.get("contractNum"));
			resultMap.put("subscriberNo", pRequestParamMap.get("subscriberNo"));
			resultMap.put("userName",pRequestParamMap.get("userName"));
			resultMap.put("soc",pRequestParamMap.get("soc"));
			resultMap.put("nation",pRequestParamMap.get("nation"));
			resultMap.put("langCode",pRequestParamMap.get("langCode"));
			resultMap.put("agentId", pRequestParamMap.get("agentId"));
			resultMap.put("saleAgentId", pRequestParamMap.get("agentSaleId"));
			resultMap.put("adminId", pRequestParamMap.get("adminId"));
			resultMap.put("remark",pRequestParamMap.get("remark"));
			resultMap.put("oRetCd","" );
			resultMap.put("oRetMsg","" );
			
			hdofcCustMngMapper.getPpsCustomerProc(resultMap);
			
			
			logger.debug("service resultMap"+resultMap.toString());
			
			return resultMap;
		}
		
		public String getCurrentTimes()
		{
			String currDateTime = "";
			
			Date currtentDate = new Date(System.currentTimeMillis());
			SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
			currDateTime = "_"+sdfFileName.format(currtentDate);
			
			
			return currDateTime;
		}
		
		
		/**
		 * 예치금 충전 설정
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> settingPpsCustomerDpRcg(Map<String, Object> pRequestParamMap) {


//			procPpsDiaryWriteUpdate
			Map<String, Object> resultMap = new HashMap<String, Object>();

			logger.debug(pRequestParamMap.toString());

			resultMap.put("contractNum",pRequestParamMap.get("contractNum") );
			resultMap.put("agntDpRcgFlag",pRequestParamMap.get("agntDpRcgFlag") );
			resultMap.put("agntDpRcgRemains",pRequestParamMap.get("agntDpRcgRemains") );

			resultMap.put("agntDpRcgCharge",pRequestParamMap.get("agntDpRcgCharge") );
			resultMap.put("agntDpRcgCnt",pRequestParamMap.get("agntDpRcgCnt") );
			
			resultMap.put("adminId",pRequestParamMap.get("adminId") );
			
			resultMap.put("retCode","" );
			resultMap.put("retMsg","" );

			try {
				hdofcCustMngMapper.procPpsRcgDepositSetting(resultMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
//				20210706 소스코드점검 수정
//				System.out.println("Connection Exception occurred");
				logger.error("Connection Exception occurred");
			}

			return resultMap;

		}


		/**
		 * 예치금 충전 정보 
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public PpsCustomerVo getPpsHdofcUserDpRcgInfo(Map<String, Object> pRequestParamMap) {
			
			
			PpsCustomerVo ppsCustomerVo = new PpsCustomerVo();

			ppsCustomerVo = hdofcCustMngMapper.getPpsHdofcUserDpRcgInfo(pRequestParamMap);

			return ppsCustomerVo;
			
			
		}
		
		/**
		 * 실시간이체 PayInfo
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsRealPayInfoList(Map<String, Object> pRequestParamMap) {

			List<EgovMap> resultList = new ArrayList<EgovMap>();
			resultList = (List<EgovMap>)hdofcCustMngMapper.getPpsRealPayinfoList(pRequestParamMap);

			return resultList;
		}

		/**
		 * 실시간이체등록내역
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsRealPayinfoMenuList(Map<String, Object> pRequestParamMap) {
			List<EgovMap> resultList = new ArrayList<EgovMap>();
			resultList = (List<EgovMap>)hdofcCustMngMapper.getPpsCustCmsList(pRequestParamMap);
			
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("cmsUserName",	"CUST_NAME");
			maskFields.put("recIdNm",		"CUST_NAME");
			
			maskingService.setMask(resultList, maskFields, pRequestParamMap);
			for(EgovMap map : resultList){
				if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
					String contractNum =  map.get("contractNum").toString().trim();
					if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
					{
						StringBuffer sb = new StringBuffer();
						String tmp = "^javaScript:goCustInfoData("+contractNum+");^_self";
						sb.append(contractNum).append(tmp);
						map.put("contractNum",sb.toString());
					}
				}
			}
			
			return resultList;
		}
		
		/**
		 * 실시간이체등록내역 엑셀출력
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public void getPpsCustCmsListExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
			ExcelParam param = new ExcelParam();
			param.setStrHead("계약번호","구분","은행명","예금주명","회원증빙여부","회원증빙등록일","처리자");
			param.setStrValue("CONTRACT_NUM", "CMS_CHARGE_TYPE_NM","CMS_BANK_CODE_NM","CMS_USER_NAME", "PAYINFO_FLAG","REC_DT","REC_ID_NM");
			param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000, 5000);
			param.setIntLen(0, 0, 0, 0, 0, 0, 0);
			param.setSheetName("실시간이체등록내역");
			param.setExcelPath(path);
			param.setFileName("test");
				
			List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
			
			param.setCmnMaskGrpList(lCmnMskGrp);
			
			Decryptor dec = factory.getDecryptorByName("DBMSDec");
			
			TestHandler handler  = new TestHandler();
			handler.setDecryptor(dec);
						
			File f = makeBigDataExcel("com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper.getPpsCustCmsListExcel", pRequestParamMap, param, handler);
			
			
			String fileName = "실시간이체등록내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";

			//=======파일다운로드사유 로그 START==========================================================
	        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
	            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
	            
	            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
	                ipAddr = request.getHeader("REMOTE_ADDR");
	           
	            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
	                ipAddr = request.getRemoteAddr();
	            
	            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
	            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
	            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
	            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
	            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
	            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
	            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수

	            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
	        }
	        //=======파일다운로드사유 로그 END==========================================================
			
			try {
				doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
			} catch (UnsupportedEncodingException e) {

//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
//				20210706 소스코드점검 수정
//				System.out.println("Connection Exception occurred");
				logger.error("Connection Exception occurred");
			}

		}
		
		/**
		 * PPS35관리 PPS35요금제코드리스트 
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		public List<?> getPpsPPS35SocListData(Map<String, Object> pRequestParamMap) {
			
			List<?> resultList = new ArrayList<PpsCustomerNationListVo>();
			
			resultList = hdofcCustMngMapper.getPpsPPS35SocListData(pRequestParamMap);
			
			
			return resultList;
		}
		
		
		/**
		 * 본사-고객관리 PPS35 목록조회
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		
		public List<?> getPps35InfoMgmtList(Map<String, Object> pRequestParamMap) {

			
			List<EgovMap> resultList = new ArrayList<EgovMap>();

			resultList = (List<EgovMap>)hdofcCustMngMapper.getPps35InfoMgmtList(pRequestParamMap);
			
			List<EgovMap> result = encService.decryptCustMngtList(resultList);
			
			
			for( EgovMap map : result  ){
				  
			
				if(map.get("contractNum")!=null && !map.get("contractNum").equals("") ){
					String contractNum =  map.get("contractNum").toString().trim();
					
					if(!StringUtils.isBlank(contractNum) && !contractNum.equals("0"))
					{
						StringBuffer sb = new StringBuffer();
						String tmp = "^javaScript:goCustInfoData("+contractNum+");^_self";
						sb.append(contractNum).append(tmp);
						map.put("contractNumStr",sb.toString());
					}
				}
				
			}


			return result;
		}

		/**
		 * 본사-고객관리 개통정보목록조회 엑셀출력
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		
		public void getPps35InfoMgmtListExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


			ExcelParam param = new ExcelParam();
			param.setStrHead("계약번호","고객명","요금제","상태", "잔액","개통일","정지일","잔액소진일","만료일","총충전액","대리점","사용량제공일","국가","잔액소진경과일","사용일수");
			param.setStrValue("CONTRACT_NUM", "SUB_LINK_NAME","SERVICE_NM","SUB_STATUS_NM", "BASIC_REMAINS","ENTER_DATE","STATUS_STOP_DT","BASIC_EMPT_DT","BASIC_EXPIRE","TOTAL_BASIC_CHG","AGENT_NM","PPS35_REUSE_DATE","AD_NATION_NM","BASIC_EMPT_CNT","USE_TERM");
			param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
			param.setIntLen(0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
			param.setSheetName("PPS35관리");
			param.setExcelPath(path);
			param.setFileName("test");
				
			List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
			
			param.setCmnMaskGrpList(lCmnMskGrp);
			
			Decryptor dec = factory.getDecryptorByName("DBMSDec");
			
			TestHandler handler  = new TestHandler();
			handler.setDecryptor(dec);
						
			File f = makeBigDataExcel("com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCustMgmtMapper.getPps35InfoMgmtListExcel", pRequestParamMap, param, handler);
			
			
			String fileName = "PPS35관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";

			//=======파일다운로드사유 로그 START==========================================================
	        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
	            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
	            
	            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
	                ipAddr = request.getHeader("REMOTE_ADDR");
	           
	            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
	                ipAddr = request.getRemoteAddr();
	            
	            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
	            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
	            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
	            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
	            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
	            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
	            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수

	            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
	        }
	        //=======파일다운로드사유 로그 END==========================================================
			
			try {
				doDownload(response, f, URLEncoder.encode(fileName, "UTF-8") );
			} catch (UnsupportedEncodingException e) {

//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
//				20210706 소스코드점검 수정
//				System.out.println("Connection Exception occurred");
				logger.error("Connection Exception occurred");
			}

		}
		
		/**
		 * 고객 체류기간변경
		 * @param pRequestParamMap
		 * @return
		 * 
		 */
		@Transactional(rollbackFor=Exception.class)
		public Map<String, Object> getppsCusStayExpirUpdatePop(Map<String, Object> pRequestParamMap) {

			hdofcCustMngMapper.getppsCusStayExpirUpdatePop(pRequestParamMap);
			
			PpsCustomerVo ppsCustomerVo = getPpsCustmerInfo(pRequestParamMap);
			if(ppsCustomerVo == null)
			{
				ppsCustomerVo = new PpsCustomerVo();
			}

			if(ppsCustomerVo.getRechargeFlag() != null &&
					!StringUtils.isBlank(ppsCustomerVo.getRechargeFlag()))
			{
				pRequestParamMap.put("rechargeFlag",  ppsCustomerVo.getRechargeFlag());
				
				if(ppsCustomerVo.getRechargeFlag().equals("Y")){
					if(ppsCustomerVo.getStayExpirFlag().equals("Y")){
						pRequestParamMap.put("rechargeStr",  "<font color='blue'>충전가능</font>");
					}else{
						pRequestParamMap.put("rechargeStr",  "<font color='red'>체류기간만료</font>");
					}
				}else{
					pRequestParamMap.put("rechargeStr",  "<font color='red'>충전불가</font>");
				}
			}
			else
			{
				pRequestParamMap.put("rechargeFlag", "N");
				
				pRequestParamMap.put("rechargeStr",  "<font color='red'>충전불가</font>");
			}
			
			if(ppsCustomerVo.getWarFlag() != null &&
					!StringUtils.isBlank(ppsCustomerVo.getWarFlag()))
			{
				pRequestParamMap.put("warFlagStr",  "<font color='red'><<주의고객>></font>");
			}
			else
			{
				pRequestParamMap.put("warFlagStr",  "");
			}
			
			// KT현행화 계약정보
			PpsKtJuoSubVo ppsKtJuoSubVo = getPpsKtJuoSubInfo(pRequestParamMap);
			if(ppsKtJuoSubVo == null)
			{
				ppsKtJuoSubVo = new PpsKtJuoSubVo();
			}
			
			//CTN
			pRequestParamMap.put("subscriberNo",ppsKtJuoSubVo.getSubscriberNo());
			
			//상태
			pRequestParamMap.put("subStatusNm", ppsKtJuoSubVo.getSubStatusNm());
			
			//상태날짜
			pRequestParamMap.put("subStatusDate", ppsKtJuoSubVo.getSubStatusDate());
			
			return pRequestParamMap;
		}
		
		
		
		
}
