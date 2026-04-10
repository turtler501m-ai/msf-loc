package com.ktis.msp.pps.usimmgmt.service;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.base.CryptoFactory;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.cardmgmt.mapper.PpsCardHandler;
import com.ktis.msp.pps.usimmgmt.mapper.PpsUsimMgmtMapper;
import com.ktis.msp.pps.usimmgmt.vo.PpsUsimVo;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @param <PpsUsimMgmtMapper>
 * @Class Name : PpsUsimMgmtService
 * @Description : 선불
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.16  최초생성
 * @
 * @author : 
 * @Create Date : 2016. 3. 16.
 */

@Service
public class PpsUsimMgmtService extends ExcelAwareService {

	
	
	@Autowired
	protected EgovPropertyService propertyService;

	@Autowired
	private PpsUsimMgmtMapper usimMgmtMapper;
	/*
	@Autowired
	private CryptoFactory factory;
	*/
	@Autowired
    private FileDownService  fileDownService;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private MaskingMapper maskingMapper;

	/**
	 * 유심정보 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getUsimInfoMgmtList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<PpsUsimVo>();
		
		List<EgovMap> resultList = (List<EgovMap>)usimMgmtMapper.getUsimInfoMgmtList(pRequestParamMap);
		for( EgovMap map : resultList){
			String mngCodeStr = map.get("sn").toString();
			String mngCodeStr1 = map.get("usimModel").toString();
			
			if(!StringUtils.isBlank(map.get("sn").toString()))
		    	
		    {
		    	StringBuffer sb = new StringBuffer();
		    	String tmp = "^javaScript:goUsimInfoData(\"" + mngCodeStr + "\",\"" + mngCodeStr1 + "\");^_self";
		    	sb.append(mngCodeStr).append(tmp);
		    	
		    	map.put("sn", sb.toString());
		    }
			
			if(map.get("contractNum") != null && !"".equals(map.get("contractNum")))
			{	
				String contractNum = map.get("contractNum").toString();
				
				StringBuffer sb = new StringBuffer();
		    	String tmp = "^javaScript:goCustDetail(\"" + contractNum + "\");^_self";
				sb.append(contractNum).append(tmp);
		    	
		    	map.put("contractNum", sb.toString());
		    }
			
		}
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("crAdminNm","CUST_NAME");
		maskFields.put("outAdminNm","CUST_NAME");
		maskFields.put("backAdminNm","CUST_NAME");				
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		return resultList;
	}

	/**
	 * 유심정보 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public void getUsimInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		
		param.setStrHead("유심번호", "유심모델", "유심S/N","상태", "개통일자", "계약번호", "입고번호", "입고일자", "입고자", "출고번호","출고번호", "출고자", "대리점", "반품일","반품처리자");
		param.setStrValue("TELCO_CD","USIM_MODEL","USIM_SN","STATUS_NM","OPEN_DATE","CONTRACT_NUM","CR_SEQ","CR_DATE","CR_ADMIN_NM","OUT_SEQ","OUT_DATE","OUT_ADMIN_NM","OUT_AGENT_NM","BACK_DATE","BACK_ADMIN_NM");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000, 5000,5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0,0);
		param.setSheetName("USIM관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);		
		param.setCmnMaskGrpList(lCmnMskGrp);
		//Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		PpsCardHandler handler  = new PpsCardHandler();
		//handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.usimmgmt.mapper.PpsUsimMgmtMapper.getUsimInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "USIM관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 유심 정보조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public PpsUsimVo getPpsUsimInfoData(Map<String, Object> pRequestParamMap) {

		PpsUsimVo ppsPinInfoVo = new PpsUsimVo();

		ppsPinInfoVo = usimMgmtMapper.getPpsUsimInfoData(pRequestParamMap);
		 String retCode ="-300";
		 String retMsg = "데이터를 조회하는 동안 오류가 발생하였습니다.";
		if(ppsPinInfoVo== null){
			retCode ="-200";
			retMsg = "해당 데이터가 없습니다.";
		}else if(ppsPinInfoVo!=null && !StringUtils.isBlank(ppsPinInfoVo.getTelcoCd()) ){

			retCode ="000";
			retMsg = "";
		}

		 ppsPinInfoVo.setRetCode(retCode);
		 ppsPinInfoVo.setRetMsg(retMsg);


		return ppsPinInfoVo;
	}
	
	/**
	 * 유심입고 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getUsimCreateMgmtList(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = (List<EgovMap>)usimMgmtMapper.getUsimCreateMgmtList(pRequestParamMap);
		
		return resultList;
	}

	/**
	 * 유심입고 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public void getUsimCreateMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("입고번호", "유심모델", "유심S/N(S)", "유심SN(E)", "입고수량", "입고일자", "입고처리자", "메모", "출고취소일자", "취소처리자", "취소여부");
		param.setStrValue("CR_SEQ","USIM_MODEL","START_SN","END_SN","USIM_CNT","CR_DATE","CR_ADMIN_NM","REMARK","CANCEL_DATE","CANCEL_ADMIN_NM","CANCEL_FLAG");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 7000, 5000, 7000, 7000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("USIM입고");
		param.setExcelPath(path);
		param.setFileName("test");
		
		//Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		PpsCardHandler handler  = new PpsCardHandler();
		//handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.usimmgmt.mapper.PpsUsimMgmtMapper.getUsimCreateMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "USIM입고_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 유심출고 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getUsimOutMgmtList(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = (List<EgovMap>)usimMgmtMapper.getUsimOutMgmtList(pRequestParamMap);

		return resultList;
	}

	/**
	 * 유심출고 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public void getUsimOutMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("출고번호", "유심모델", "유심S/N(S)", "유심SN(E)", "출고수량", "출고일자", "출고처리자", "출고대리점", "메모", "출고취소일자", "취소처리자", "취소여부");
		param.setStrValue("OUT_SEQ","USIM_MODEL","START_SN","END_SN","USIM_CNT","OUT_DATE","OUT_ADMIN_NM","OUT_AGENT_NM","REMARK","CANCEL_DATE","CANCEL_ADMIN_NM","CANCEL_FLAG");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 7000, 5000, 5000, 7000, 7000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("USIM출고");
		param.setExcelPath(path);
		param.setFileName("test");
		
		//Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		PpsCardHandler handler  = new PpsCardHandler();
		//handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.usimmgmt.mapper.PpsUsimMgmtMapper.getUsimOutMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "USIM관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 유심반품 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getUsimBackMgmtList(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = (List<EgovMap>)usimMgmtMapper.getUsimBackMgmtList(pRequestParamMap);

		return resultList;
	}

	/**
	 * 유심반품 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public void getUsimBackMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("반품번호", "유심모델", "유심S/N(S)", "유심SN(E)", "반품수량", "반품일자", "반품처리자", "반품대리점", "메모", "반품취소일자", "취소처리자", "취소여부");
		param.setStrValue("BACK_SEQ","USIM_MODEL","START_SN","END_SN","USIM_CNT","BACK_DATE","BACK_ADMIN_NM","BACK_AGENT_NM","REMARK","CANCEL_DATE","CANCEL_ADMIN_NM","CANCEL_FLAG");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 7000, 5000, 5000, 7000, 7000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("USIM반품");
		param.setExcelPath(path);
		param.setFileName("test");
			
		//Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		PpsCardHandler handler  = new PpsCardHandler();
		//handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.usimmgmt.mapper.PpsUsimMgmtMapper.getUsimBackMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "USIM관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 유심 입고정보조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public PpsUsimVo getPpsUsimCreateData(Map<String, Object> pRequestParamMap) {

		PpsUsimVo ppsPinInfoVo = new PpsUsimVo();

		ppsPinInfoVo = usimMgmtMapper.getPpsUsimCreateData(pRequestParamMap);
		 String retCode ="-300";
		 String retMsg = "데이터를 조회하는 동안 오류가 발생하였습니다.";
		if(ppsPinInfoVo== null){
			retCode ="-200";
			retMsg = "해당 데이터가 없습니다.";
		}else if(ppsPinInfoVo!=null && !StringUtils.isBlank(ppsPinInfoVo.getTelcoCd()) ){

			retCode ="000";
			retMsg = "";
		}

		 ppsPinInfoVo.setRetCode(retCode);
		 ppsPinInfoVo.setRetMsg(retMsg);


		return ppsPinInfoVo;
	}

	
	/**
	 * 유심 번호 콤보박스 옵션목록 가져오기 
	 * @param pRequestParamMap
	 * @return
	 */
	public List<?> getSelectPpsUsimModeDataList(Map<String, Object> pRequestParamMap){
		
		List<?> resultList = new ArrayList<Map<String, Object>>();
		   
		resultList = usimMgmtMapper.getSelectPpsUsimModeDataList(pRequestParamMap);
		
		
		return resultList;
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
	 * 유심 입고.
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsUsimCreateProc(Map<String, Object> pRequestParamMap) {
		

		pRequestParamMap.put("oRetCd","" );
		pRequestParamMap.put("oRetMsg","" );
		
		
		
		//String startSnStr = "";
		StringBuffer startSnStr = new StringBuffer();
		String endSnStr = "";
		String telcoStr ="";
		String usimModelStr = "";
		
		
		
		if(pRequestParamMap != null && pRequestParamMap.get("telcoCd") != null)
		{
			telcoStr = pRequestParamMap.get("telcoCd").toString();
			
		}
		if(pRequestParamMap != null && pRequestParamMap.get("usimModel") != null)
		{
			usimModelStr = pRequestParamMap.get("usimModel").toString();
			
		}
		
		
		if(pRequestParamMap != null && pRequestParamMap.get("startSn") != null)
		{
			startSnStr.append(pRequestParamMap.get("startSn").toString());
			//startSnStr = pRequestParamMap.get("startSn").toString();
		}
		
		if(pRequestParamMap != null && pRequestParamMap.get("endSn") != null)
		{
			endSnStr = pRequestParamMap.get("endSn").toString();
		}
		
		if(!"CR_CAN".equals(pRequestParamMap.get("opCode")) &&  !"CR_REMARK".equals(pRequestParamMap.get("opCode")))
		{
			int startint = 0;
			int endint = 0;
			
			try {
				
				if(startSnStr.toString() != null && startSnStr.toString().length() > 0)
					startint = Integer.parseInt(startSnStr.toString());
				if(endSnStr != null && endSnStr.length() > 0)
					endint = Integer.parseInt(endSnStr);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
//				20210706 소스코드점검 수정
//				System.out.println("Connection Exception occurred");
				logger.error("Connection Exception occurred");
			}
			
			StringBuilder snSb = new StringBuilder();
			
			if((startSnStr.toString() != null && startSnStr.toString().length() > 0) && (endSnStr != null && endSnStr.length() > 0))
			{
				while(endint - startint >= 0)
				{
					snSb.append(telcoStr);
					snSb.append(usimModelStr);
					
					startSnStr.setLength(0);
					startSnStr.append(String.valueOf(startint));
					//startSnStr = String.valueOf(startint);
					
					while (startSnStr.toString().length() < 7) {
						startSnStr.insert(0, "0");
						//startSnStr = "0"+startSnStr;
					}
					
					snSb.append(startSnStr.toString());
					
					if(endint - startint > 0)
						snSb.append("|");
					
					startint++;
					
				}
				
				pRequestParamMap.put("iccId", snSb.toString());
			}
		}
		
		
		logger.debug("==============처리 전 Service ==============");
		logger.debug("resultMap===>"+pRequestParamMap+"\n");
		logger.debug("===========================");
		
		usimMgmtMapper.ppsUsimCreateProc(pRequestParamMap);
		logger.debug("==============처리 후 Service ==============");
		logger.debug("resultMap===>"+pRequestParamMap+"\n");
		logger.debug("===========================");


		return pRequestParamMap;
	}
	
	/**
	 * 유심 출고.
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsUsimOutProc(Map<String, Object> pRequestParamMap) {
		

		
		
		pRequestParamMap.put("oCnt","" );
		pRequestParamMap.put("oRetCd","" );
		pRequestParamMap.put("oRetMsg","" );
		
		
		//String startSnStr = "";
		//String endSnStr = "";
		StringBuffer startSnStr = new StringBuffer();
		StringBuffer endSnStr = new StringBuffer();
		
		if(pRequestParamMap != null && pRequestParamMap.get("startSn") != null)
		{
			startSnStr.append(pRequestParamMap.get("startSn").toString());
			//startSnStr = pRequestParamMap.get("startSn").toString();
		}
		
		if(pRequestParamMap != null && pRequestParamMap.get("endSn") != null)
		{
			endSnStr.append(pRequestParamMap.get("endSn").toString());
			//endSnStr = pRequestParamMap.get("endSn").toString();
		}
		
		if(!"OT_CAN".equals(pRequestParamMap.get("opCode")))
		{
			if(startSnStr.toString() != null && startSnStr.toString().length() > 0)
			{
				while (startSnStr.toString().length() < 7) {
					startSnStr.insert(0, "0");
					//startSnStr = "0"+startSnStr;
				}
				pRequestParamMap.put("startSn", startSnStr.toString());
			}
			
			if(endSnStr.toString() != null && endSnStr.toString().length() > 0)
			{
				while (endSnStr.toString().length() < 7) {
					endSnStr.insert(0, "0");
					//endSnStr = "0"+endSnStr;
				}
				
				pRequestParamMap.put("endSn", endSnStr.toString());
			}
		}
		
		logger.debug("==============처리 전 Service ==============");
		logger.debug("resultMap===>"+pRequestParamMap+"\n");
		logger.debug("===========================");
		
		usimMgmtMapper.ppsUsimOutProc(pRequestParamMap);
		logger.debug("==============처리 후 Service ==============");
		logger.debug("resultMap===>"+pRequestParamMap+"\n");
		logger.debug("===========================");


		return pRequestParamMap;
	}
	
	/**
	 * 유심 반품.
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsUsimBackProc(Map<String, Object> pRequestParamMap) {
		

		pRequestParamMap.put("oCnt","" );
		pRequestParamMap.put("oRetCd","" );
		pRequestParamMap.put("oRetMsg","" );
		
		//String startSnStr = "";
		//String endSnStr = "";
		StringBuffer startSnStr = new StringBuffer();
		StringBuffer endSnStr = new StringBuffer();
		
		if(pRequestParamMap != null && pRequestParamMap.get("startSn") != null)
		{
			startSnStr.append(pRequestParamMap.get("startSn").toString());
			//startSnStr = pRequestParamMap.get("startSn").toString();
		}
		
		if(pRequestParamMap != null && pRequestParamMap.get("endSn") != null)
		{
			endSnStr.append(pRequestParamMap.get("endSn").toString());
			//endSnStr = pRequestParamMap.get("endSn").toString();
		}
		
		if(!"BK_CAN".equals(pRequestParamMap.get("opCode")))
		{
			if(startSnStr.toString() != null && startSnStr.toString().length() > 0)
			{
				while (startSnStr.toString().length() < 7) {
					startSnStr.insert(0, "0");
					//startSnStr = "0"+startSnStr;
				}
				pRequestParamMap.put("startSn", startSnStr.toString());
			}
			
			if(endSnStr.toString() != null && endSnStr.toString().length() > 0)
			{
				while (endSnStr.toString().length() < 7) {
					endSnStr.insert(0, "0");
					//endSnStr = "0"+endSnStr;
				}
				
				pRequestParamMap.put("endSn", endSnStr.toString());
			}
		}
		
		logger.debug("==============처리 전 Service ==============");
		logger.debug("resultMap===>"+pRequestParamMap+"\n");
		logger.debug("===========================");
		
		usimMgmtMapper.ppsUsimBackProc(pRequestParamMap);
		logger.debug("==============처리 후 Service ==============");
		logger.debug("resultMap===>"+pRequestParamMap+"\n");
		logger.debug("===========================");


		return pRequestParamMap;
	}
	
	
	
	
	/**
	 * 유심모델 관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getUsimModelInfoMgmtList(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = (List<EgovMap>)usimMgmtMapper.getUsimModelInfoMgmtList(pRequestParamMap);

		return resultList;
	}

	/**
	 * 유심모델관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public void getUsimModelInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		
		param.setStrHead("유심번호", "유심명", "메모", "등록자", "등록일자");
		param.setStrValue("USIM_MODEL","USIM_MODEL_NAME","REMARK","RECORD_ADMIN_NM","RECORD_DATE");
		param.setIntWidth(3000, 5000, 7000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0);
		param.setSheetName("USIM 모델관리");
		param.setExcelPath(path);
		param.setFileName("test");
			
		//Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		PpsCardHandler handler  = new PpsCardHandler();
		//handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.usimmgmt.mapper.PpsUsimMgmtMapper.getUsimModelInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "USIM관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 유심모델관리 정보조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public PpsUsimVo getPpsUsimModelInfoData(Map<String, Object> pRequestParamMap) {

		PpsUsimVo ppsPinInfoVo = new PpsUsimVo();

		ppsPinInfoVo = usimMgmtMapper.getPpsUsimInfoData(pRequestParamMap);
		 String retCode ="-300";
		 String retMsg = "데이터를 조회하는 동안 오류가 발생하였습니다.";
		if(ppsPinInfoVo== null){
			retCode ="-200";
			retMsg = "해당 데이터가 없습니다.";
		}else if(ppsPinInfoVo!=null && !StringUtils.isBlank(ppsPinInfoVo.getTelcoCd()) ){

			retCode ="000";
			retMsg = "";
		}

		 ppsPinInfoVo.setRetCode(retCode);
		 ppsPinInfoVo.setRetMsg(retMsg);


		return ppsPinInfoVo;
	}
	
	
	/**
	 *  유심입고 excel파일 읽기
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public String getPpsUsimExcelFileRead(String sFileNm ) throws MvnoRunException {
	     String filePath    =  sFileNm;
	    HSSFWorkbook workBook  = null;
	    HSSFSheet sheet    =  null;
	    HSSFRow row     =  null;
	    HSSFCell cell    =  null;
	    String sCellValue ="";
		FileInputStream fInput = null;
		//int nUpdateCnt = 0;
		
		StringBuilder iccIds = new StringBuilder();
		
		try{ 

			fInput = new FileInputStream(new File(filePath));
		   // excel  =  new POIFSFileSystem(new FileInputStream(filePath));
		    workBook  =  new HSSFWorkbook(fInput);
		    workBook.getNumberOfSheets();
			// 1 .sheet
	          sheet     =  workBook.getSheetAt(0);
	          int rows    =  sheet.getPhysicalNumberOfRows();
	          
	          for(int r=0;r<rows;r++){
	        	  
	        	  //row전체가 비어있는경우 건너띄기 위해
	        	   if(sheet.getRow(r) == null && rows -1 != r){
		        	   rows++;
		        	   continue;
		           }
	        	   
		           if(r== 0){
			            continue;
			       }
		           
		           if( r > 1 )
		           {
		        	   iccIds.append("|");
		           }

	        	   row     =  sheet.getRow(r);
		           
		           //EgovMap em = new EgovMap();
		           for(int c=0;c<4;c++){
			            cell   =  row.getCell(c);		            
			            if(cell== null){
			            	continue;
			            }
			            
			            switch(cell.getCellType()){
                            case HSSFCell.CELL_TYPE_FORMULA :
				            	 sCellValue = ""+cell.getStringCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_NUMERIC :
				            	 sCellValue = ""+cell.getNumericCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_STRING :
				            	 sCellValue = ""+cell.getStringCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_BLANK :
				            	 sCellValue = ""+cell.getStringCellValue();

                            break;
                            case HSSFCell.CELL_TYPE_BOOLEAN :
				            	 sCellValue = ""+cell.getStringCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_ERROR :
			            	     sCellValue = ""+cell.getErrorCellValue();
                            break;				            
				             default:
				            	 logger.debug("default");
			            }
			            
			            
			            if(c == 0){
			            	//em.put("telcoCd", sCellValue);
			            	iccIds.append(sCellValue);
			            }

		           }//cell
		           
			       if(r > 10000  )   { throw new MvnoRunException(-1,"10000건이상은  처리 불가 합니다 ");}
	          }//row
	         
			}
		    catch(Exception e){
		    	iccIds = new StringBuilder();

              throw new MvnoRunException(-1,"");
		    }
		finally {
			if (fInput != null) {
				try { fInput.close(); } catch (IOException e) {  throw new MvnoRunException(-1,e.getMessage()); }
			}
		}
		
		return iccIds.toString();

	}
	
	/**
	 *  유심 Excel파일 grid 입고
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	//@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> usimCreateGridProc(Map<String, Object> pRequestParamMap, JSONArray iJsonArray) throws MvnoServiceException, InterruptedException  {
		
	    
		pRequestParamMap.put("oRetCd","" );
		pRequestParamMap.put("oRetMsg","" );
		
		logger.debug("==============처리 전 Service ==============");
		logger.debug("resultMap===>"+pRequestParamMap+"\n");
		logger.debug("===========================");
		
		usimMgmtMapper.ppsUsimCreateProc(pRequestParamMap);
		logger.debug("==============처리 후 Service ==============");
		logger.debug("resultMap===>"+pRequestParamMap+"\n");
		logger.debug("===========================");
		
		return pRequestParamMap;
	
	}
	
	/**
	 * 문자전송내역 샘플 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public String getSmsDumpMgmtExcelSample(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) {
		
		String[] strHead = {"통신사코드", "USIM 모델", "Serial Number"};
		int[] intWidth = {5000, 5000, 5000, 5000};
		
		HSSFWorkbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전 
//        Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상 
		
        // Sheet 생성 
        Sheet sheet1 = xlsWb.createSheet("샘플"); 
        
        
        
        for (int i = 0; i < intWidth.length; i++) {
        	sheet1.setColumnWidth(i, intWidth[i]);
		}
          
        // Cell 스타일 생성 
        CellStyle cellStyleHead = xlsWb.createCellStyle(); 
        CellStyle cellStyleMid = xlsWb.createCellStyle(); 
        CellStyle cellStyleInt = xlsWb.createCellStyle(); 
          
        // 줄 바꿈 
        cellStyleHead.setWrapText(true); 
          
        HSSFFont fontHead = xlsWb.createFont();
        fontHead.setBoldweight((short) 1);
        fontHead.setFontName("맑은 고딕");
        fontHead.setColor(HSSFColor.BLACK.index);
        fontHead.setFontHeightInPoints((short) 12);
        
        HSSFFont fontMid = xlsWb.createFont();
        fontMid.setBoldweight((short) 1);
        fontMid.setFontName("맑은 고딕");
        fontMid.setColor(HSSFColor.BLACK.index);
        fontMid.setFontHeightInPoints((short) 10);
        
        //제목
//        cellStyle.setFillForegroundColor(HSSFColor.LIME.index); 
        cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyleHead.setAlignment((short) 2);
        cellStyleHead.setFillPattern((short) 1);
        cellStyleHead.setFillPattern((short) 1);
        cellStyleHead.setBorderRight((short) 1);
//        cellStyleHead.setBorderLeft((short) 1);
        cellStyleHead.setBorderTop((short) 1);
        cellStyleHead.setBorderBottom((short) 1);
        cellStyleHead.setFont(fontHead);
        
        //내용
        cellStyleMid.setAlignment((short) 2);
        cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        cellStyleMid.setFillPattern((short) 1);
        cellStyleMid.setBorderRight((short) 1);
        cellStyleMid.setBorderLeft((short) 1);
        cellStyleMid.setBorderTop((short) 1);
        cellStyleMid.setBorderBottom((short) 1);
        cellStyleMid.setFont(fontMid);
        
        //숫자용
        HSSFDataFormat format = xlsWb.createDataFormat();
        
        cellStyleInt.setAlignment((short) 3);
        cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        cellStyleInt.setFillPattern((short) 1);
        cellStyleInt.setBorderRight((short) 1);
        cellStyleInt.setBorderLeft((short) 1);
        cellStyleInt.setBorderTop((short) 1);
        cellStyleInt.setBorderBottom((short) 1);
        cellStyleInt.setFont(fontMid);
        cellStyleInt.setDataFormat(format.getFormat("#,##0"));
        
        Row row = null; 
        Cell cell = null; 

        // 첫 번째 줄 제목 
        row = sheet1.createRow(0); 
        
        for (int i = 0; i < strHead.length; i++) {
        	cell = row.createCell(i); 
        	cell.setCellValue(strHead[i]);
        	cell.setCellStyle(cellStyleHead);
		}
        
        row = sheet1.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("898230");
		cell.setCellStyle(cellStyleMid);
		cell = row.createCell(1);
        cell.setCellValue("00000");
		cell.setCellStyle(cellStyleMid);
		cell = row.createCell(2);
        cell.setCellValue("0000001");
		cell.setCellStyle(cellStyleMid);
		
		row = sheet1.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("898230");
		cell.setCellStyle(cellStyleMid);
		cell = row.createCell(1);
        cell.setCellValue("00000");
		cell.setCellStyle(cellStyleMid);
		cell = row.createCell(2);
        cell.setCellValue("0000002");
		cell.setCellStyle(cellStyleMid);

		StringBuilder sbFileName = new StringBuilder();
		
        Date toDay = new Date();
        
        String serverInfo = propertyService.getString("excelPath");
        
        String strFileName = serverInfo + "smsExcelSample";
        String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
        
        sbFileName.append(strFileName);
        sbFileName.append(strToDay);
        sbFileName.append(".xls");
        
        String encodingFileName = "";

		int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
		
        try {
        	encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
        } catch (UnsupportedEncodingException uee) {
        	encodingFileName = sbFileName.toString();
        }

        response.setHeader("Cache-Control", "");
        response.setHeader("Pragma", "");

        response.setContentType("Content-type:application/octet-stream;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        
        File tempFile = new File(sbFileName.toString());
        
        try {
        	bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			
			xlsWb.write(bos);
			bos.flush();
			bos.close();
			      
			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());
		        
			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			bos.flush();
			        
        } catch (IOException e) {
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
        } finally {
        	if (bis != null){
        		try {
        			bis.close();
        		} catch (Exception e) {
        			logger.info(e.getMessage());
        		}
        	}
        	
        	if (bos != null) {
        		try {
        			bos.close();
        		} catch (Exception e) {
        			logger.info(e.getMessage());
        		}
        	}
        }
        
        return sbFileName.toString();
	}
	
	/**
	 * 유심 출고.
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsUsimModelInputProc(Map<String, Object> pRequestParamMap) {
		
		pRequestParamMap.put("oRetCd","" );
		pRequestParamMap.put("oRetMsg","" );
		usimMgmtMapper.ppsUsimModelInputProc(pRequestParamMap);
		return pRequestParamMap;
	}
	
	/**
	 * 유심관리 proc 호출용
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getPpsUsimInfoForProcData(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = (List<EgovMap>)usimMgmtMapper.getPpsUsimInfoForProcData(pRequestParamMap);

		return resultList;
	}
	

}
