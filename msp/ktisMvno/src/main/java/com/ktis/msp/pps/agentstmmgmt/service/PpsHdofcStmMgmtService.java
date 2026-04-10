package com.ktis.msp.pps.agentstmmgmt.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @param <PpsHdofcStmMgmtMapper>
 * @Class Name : PpsHdofcStmMgmtService
 * @Description : 정산 수수료 관리  service
 * @
 * @ 수정일		수정자		수정내용
 * @ ----------	------	-----------------------------
 * @ 2017.05.01	김웅		최초생성
 * @
 * @author : 김웅
 * @Create Date : 2017. 05. 01.
 */

@Service

public class PpsHdofcStmMgmtService extends ExcelAwareService {
//	@Autowired
//	private PpsHdofcCommonService  hdofcCommonService;
	
	@Autowired
	private PpsHdofcStmMgmtMapper stmMgmtMapper;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;
	
//	@Autowired
//	private CustEncService  encService;
	
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
	private MaskingService maskingService;
	
	
	/**
	 * 기본 수수로 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmInfoMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmInfoMgmtList(pRequestParamMap);

		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		return resultList;
	}
	
	
	/**
	 * 기본 수수로 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getStmInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","계약번호","고객번호","개통번호","고객명","요금제","모집경로","신규/이동","신분증","개통일","상태(1일)","서식지수","3G/LTE","단말결합여부(15일)","대리점명","판매점명","중복개통상태","기본수수료상태","기본수수료(카드)","기본수수료(현금)","유심수수료상태","유심수수료(카드)","유심수수료(현금)","등록자","등록일","환수여부","환수사유","환수일","메모");
		param.setStrValue("BILL_MONTH","CONTRACT_NUM","CUSTOMER_ID","SUBSCRIBER_NO","SUB_LINK_NAME","SOC_NM","ONOFF","MNP_GUBUN_NM","CUST_IDNT_NO_IND_CD_NM","ENTER_DATE","SUB_STATUS_1_NM","DOC_CNT_1","DATA_TYPE_1","MODEL_FLAG_15","AGENT_1_NM","AGENT_SALE_1_NM","OVERLAP_STATUS_NM","BASIC_STATUS_NM","BASIC_CARD_SD","BASIC_CASH_SD","USIM_STATUS_NM","USIM_CARD_SD","USIM_CASH_SD","REG_ADMIN_NM","REG_DT","REFUND_FLAG","REFUND_RSN_NM","REFUND_DT","REMARK");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("개통기본수수료");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "개통기본수수료관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * Grade 수수료 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmGradeMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmGradeMgmtList(pRequestParamMap);
		
		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		return resultList;
	}
	
	/**
	 * Grade 수수료 목록 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmGradeMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","계약번호","전화번호","고객명","개통일","신분증","요금제","고객상태(1일)","가정산","가정산 카드","가정산 현금","대리점명","판매점명","고객상태(15일)","중복개통상태","실정산","정산 카드","정산 현금","메모","등록처리자","등록일");
		param.setStrValue("BILL_MONTH","CONTRACT_NUM","SUBSCRIBER_NO","SUB_LINK_NAME","ENTER_DATE","CUST_IDNT_NO_IND_CD_NM","SOC_NM","SUB_STATUS_NM_1","STATUS_NM_1","CARD_SD_1","CASH_SD_1","AGENT_ID_NM_1","AGENT_SALE_ID_NM_1","SUB_STATUS_NM_15","OVERLAP_STATUS_NM","STATUS_NM_15","CARD_SD_15","CASH_SD_15","REMARK","REG_ADMIN_NM","REG_DT");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("Grade수수료");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmGradeMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "Grade수수료관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 명변 수수료 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmMbMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmMbMgmtList(pRequestParamMap);

		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		return resultList;
	}
	
	/**
	 * 명의변경 수수료 목록 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmMbMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","계약번호","전화번호","대리점명","판매점명","고객상태","명변구분","이전고객구분","현재고객구분","명변일자","무료충전금액","유료충전금액","총충전금액","수수료상태","카드수당","현금수당","등록관리자","등록일자","메모");
		param.setStrValue("BILL_MONTH","CONTRACT_NUM","SUBSCRIBER_NO","AGENT_NM","AGENT_SALE_NM","SUB_STATUS_NM","GUBUN_NM","CUST_IDNT_NO_IND_CD_OLD_NM","CUST_IDNT_NO_IND_CD_NM","MB_DT","RCG_FREE_AMT","RCG_PAY_AMT","RCG_AMT","STATUS_NM","CARD_SD","CASH_SD","REG_ADMIN","REG_DT","REMARK");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("명의변경수수료");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmMbMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "명의변경수수료관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 우량 고객 수수료 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmUlMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmUlMgmtList(pRequestParamMap);
		
		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		return resultList;
	}
	
	/**
	 * 우량 고객 수수료 목록 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmUlMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","계약번호","전화번호","고객명","대리점명","판매점명","개통일자","상태(15일)","요금제","고객구분","단말결합여부","중복개통상태","수수료상태","사용금액","사용횟수","카드수당","현금수당","등록관리자","등록일자","메모");
		param.setStrValue("BILL_MONTH","CONTRACT_NUM","SUBSCRIBER_NO","SUB_LINK_NAME","AGENT_15_NM","AGENT_SALE_15_NM","ENTER_DATE","SUB_STATUS_15_NM","SOC_NM","CUST_IDNT_NO_IND_CD_NM","MODEL_FLAG_15","OVERLAP_STATUS_NM","STATUS_NM","USE_AMT","USE_CNT","CARD_SD","CASH_SD","REG_ADMIN_NM","REG_DT","REMARK");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("우량고객수수료");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmUlMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "우량고객수수료관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 환수 등록 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmRefundExcelMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmRefundExcelMgmtList(pRequestParamMap);
		
		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 환수 등록  목록 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmRefundExcelListMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","계약번호","환수구분","대리점명","판매점명","수수료상태","무료충전금액","현금환수","카드환수","등록일자","등록관리자","메모");
		param.setStrValue("BILL_MONTH","CONTRACT_NUM","GUBUN_NM","AGENT_1_NM","AGENT_SALE_1_NM","STATUS_NM","FREE_RCG_AMT","CASH_SD_1","CARD_SD_1","REG_DT","REG_ADMIN_NM","REMARK");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("환수등록목록");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmRefundExcelMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "환수등록목록관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 자동이체 수수료 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmCmsMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmCmsMgmtList(pRequestParamMap);
		
		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 자동이체 수수료 목록 Excel
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmCmsMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","계약번호","대리점명","판매점명","요금제","정책설정금액","자동이체금액","자동이체일자","수수료상태","카드수당","현금수당","등록관리자","등록일자","메모");
		param.setStrValue("BILL_MONTH","CONTRACT_NUM","AGENT_NM","AGENT_SALE_NM","SOC_NM","CMS_AMT_SETUP","CMS_AMT","CMS_DT","STATUS_NM","CARD_SD","CASH_SD","REG_ADMIN_NM","REG_DT","REMARK");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("자동이체수수료");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmCmsMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "자동이체수수료관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 해지 환수 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmRefundMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmRefundMgmtList(pRequestParamMap);
		
		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		return resultList;
	}
	
	/**
	 * 해지 환수 조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmRefundMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","계약번호","전화번호","요금제","대리점명","판매점명","개통일","해지일","해지사유","사용일","무료충전금액","수수료상태","현금환수","카드환수","등록일자","등록관리자","메모");
		param.setStrValue("BILL_MONTH","CONTRACT_NUM","SUBSCRIBER_NO","SOC_NM","AGENT_1_NM","AGENT_SALE_1_NM","ENTER_DATE","CANCEL_DATE","MVNO_CANCEL_RSN_NM","USE_CNT","FREE_RCG_AMT","STATUS_NM","CASH_SD_1","CARD_SD_1","REG_DT","REG_ADMIN_NM","REMARK");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("해지환수관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmRefundMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "해지환수관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 미사용 환수 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmNoUseMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmNoUseMgmtList(pRequestParamMap);
		
		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		return resultList;
	}
	
	/**
	 * 미사용 환수 조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmNoUseMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","계약번호","전화번호","대리점명","판매점명","고객상태","요금제","개통일자","무료충전","유료충전","총충전금액","유료충전기준금액","기본료","음성","문자","데이터","기타","총사용액","사용액기준금액","수수료상태","무료충전환수액","현금","카드","등록일","등록관리자","메모");
		param.setStrValue("BILL_MONTH","CONTRACT_NUM","SUBSCRIBER_NO","AGENT_1_NM","AGENT_SALE_1_NM","SUB_STATUS_NM","SOC_NM","ENTER_DATE","FREE_RCG","PAY_RCG","RCG_AMT","RCG_SETUP_AMT","USE_BASIC","USE_VOICE","USE_SMS","USE_DATA","USE_ETC","USE_AMT","USE_SETUP_AMT","STATUS_NM","FREE_RCG_AMT","CASH_SD_1","CARD_SD_1","REG_DT","REG_ADMIN_NM","REMARK");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("미사용환수");
		param.setExcelPath(path);
		param.setFileName("test");
		

		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmNoUseMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "미사용환수관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 비 정상 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmReOpenMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmReOpenMgmtList(pRequestParamMap);
		
		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 비 정상 관리 Excel
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmReOpenMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","해지계약번호","해지일자","신규계약번호","개통일자","대리점명","판매점명","재가입기간","정책시작일수","정책마지막일수","수수료상태","등록관리자","등록일자","메모");
		param.setStrValue("BILL_MONTH","CANCEL_CONTRACT_NUM","CANCEL_DATE","OPEN_CONTRACT_NUM","ENTER_DATE","AGENT_1_NM","AGENT_SALE_1_NM","OPEN_CNT","START_CNT_SETUP","END_CNT_SETUP","STATUS_NM","REG_ADMIN_NM","REG_DT","REMARK");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("비정상관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmReOpenMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "비정상관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 재충전 수수료 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmRcgMgmtList(Map<String, Object> pRequestParamMap){
		
		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmRcgMgmtList(pRequestParamMap);
		
		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 재 충전 수수료 Excel
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmRcgMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","계약번호","대리점명","판매점명","요금제","선불카드충전","선불카드수수료율","가상계좌충전","가상계좌수수료율","자동이체금액","자동이체수수료","신용카드금액","신용카드수수료","편의점충전","편의점수수료율","예치금충전","예치금수수료율","재충전(카드)","재충전(현금)","수수료상태","등록관리자","등록일자","메모");
		param.setStrValue("BILL_MONTH","CONTRACT_NUM","AGENT_NM","AGENT_SALE_NM","SOC_NM","PPS_AMT","PPS_RATE","VAC_AMT","VAC_RATE","CMS_AMT","CMS_RATE","CCD_AMT","CCD_RATE","POS_AMT","POS_RATE","DEP_AMT","DEP_RATE","CARD_SD","CASH_SD","STATUS_NM","REG_ADMIN_NM","REG_DT","REMARK");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("재충전수수료");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmRcgMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "재충전수수료_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 조정수수료 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmModMgmtList(Map<String, Object> pRequestParamMap){
		
		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmModMgmtList(pRequestParamMap);
		
		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 조정수수료 Excel
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmModMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","계약번호","대리점명","판매점명","카드조정","현금조정","수수료상태","등록관리자","등록일자","메모");
		param.setStrValue("BILL_MONTH","CONTRACT_NUM","AGENT_NM","AGENT_SALE_NM","CARD_SD","CASH_SD","STATUS_NM","REG_ADMIN_NM","REG_DT","REMARK");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("조정수수료");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmModMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "조정수수료_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 대리점별 정산내역 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmHistoryMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmHistoryMgmtList(pRequestParamMap);
		
		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 대리점별 정산내역 Excel
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmHistoryMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","대리점명","기본(카드)","기본(현금)","유심(카드)","유심(현금)","GRADE(카드)","GRADE(현금)","우량(카드)","우량(현금)","명변(카드)","명변(현금)","재충전(카드)","재충전(현금)","자동이체(카드)","자동이체(현금)","무료충전환수액","환수(카드)","환수(현금)","조정(카드)","조정(현금)","수수료조정(카드)","수수료조정(현금)","합계(카드)","합계(현금)","메모","등록관리자","등록일자");
		param.setStrValue("BILL_MONTH","AGENT_NM","BASIC_CARD_SD","BASIC_CASH_SD","USIM_CARD_SD","USIM_CASH_SD","GRADE_CARD_SD","GRADE_CASH_SD","UL_CARD_SD","UL_CASH_SD","MB_CARD_SD","MB_CASH_SD","RCG_CARD_SD","RCG_CASH_SD","CMS_CARD_SD","CMS_CASH_SD","REFUND_FREE_RCG_AMT","REFUND_CARD_SD","REFUND_CASH_SD","MOD_CARD_SD","MOD_CASH_SD","MOD_AGENT_CARD_SD","MOD_AGENT_CASH_SD","CARD_SD","CASH_SD","REMARK","REG_ADMIN_NM","REG_DT");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("대리점별정산");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmHistoryMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "대리점별정산_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 통합수수료정산 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmMgmtList(pRequestParamMap);
		
		logger.debug("service  resultList ["+resultList.toString()+"]");
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap); 
		
		return resultList;
	}
	
	/**
	 * 통합수수료정산 Excel
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getStmMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","기본(카드)","기본(현금)","유심(카드)","유심(현금)","GRADE(카드)","GRADE(현금)","우량(카드)","우량(현금)","명변(카드)","명변(현금)","재충전(카드)","재충전(현금)","자동이체(카드)","자동이체(현금)","무료충전환수액","환수(카드)","환수(현금)","조정(카드)","조정(현금)","수수료조정(카드)","수수료조정(현금)","합계(카드)","합계(현금)","메모","마감여부","마감관리자","마감일자","등록관리자","등록일자");
		param.setStrValue("BILL_MONTH","BASIC_CARD_SD","BASIC_CASH_SD","USIM_CARD_SD","USIM_CASH_SD","GRADE_CARD_SD","GRADE_CASH_SD","UL_CARD_SD","UL_CASH_SD","MB_CARD_SD","MB_CASH_SD","RCG_CARD_SD","RCG_CASH_SD","CMS_CARD_SD","CMS_CASH_SD","REFUND_FREE_RCG_AMT","REFUND_CARD_SD","REFUND_CASH_SD","MOD_CARD_SD","MOD_CASH_SD","MOD_AGENT_CARD_SD","MOD_AGENT_CASH_SD","CARD_SD","CASH_SD","REMARK","END_FLAG","END_ADMIN_NM","END_DT","REG_ADMIN_NM","REG_DT");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("통합수수료정산");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "통합수수료정산_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 환수 등록 excel 파일 읽기
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsRefundFileRead(Map<String, Object> pRequestParamMap, String sFileNm, String fileName ) throws MvnoRunException {
		
		String filePath			= sFileNm;
	    HSSFWorkbook workBook	= null;
	    HSSFSheet sheet			= null;
	    HSSFRow row				= null;
	    HSSFCell cell			= null;
	    String sCellValue		= "";
		FileInputStream fInput	= null;
		int nUpdateCnt 			= 0;
		
		StringBuilder sb1 = new StringBuilder(); // 계약번호
		StringBuilder sb2 = new StringBuilder(); // 메모
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try
		{ 

			fInput = new FileInputStream(new File(filePath));
		    workBook =  new HSSFWorkbook(fInput);
		    workBook.getNumberOfSheets();
			// 1 .sheet
	    	sheet = workBook.getSheetAt(0);
	    	int rows = sheet.getPhysicalNumberOfRows();
	    	
	    	pRequestParamMap.put("opCode", "F");
	          
    		// insert lgs Ord Prdt
	    	for(int r = 0 ; r < rows ; r++ )
	    	{
    			//row전체가 비어있는경우 건너띄기 위해
    			if(sheet.getRow(r) == null && rows -1 != r){
					rows++;
					continue;
    			}
	        	   
    			if( r == 0){
					continue;
    			}

    			row = sheet.getRow(r);
		           
    			for(int c=0 ; c < 2 ;c++){
					cell   =  row.getCell(c);		            
					if(cell== null){
						sCellValue = "";
					}else{
			            
						switch(cell.getCellType()){
							case HSSFCell.CELL_TYPE_FORMULA :
								sCellValue = ""+cell.getStringCellValue();
								 break;
							case HSSFCell.CELL_TYPE_NUMERIC :
								//소주점 있다면 지정필요
								sCellValue = ""+String.format("%.0f", cell.getNumericCellValue());
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
							 	break;
						}
					}
					
					/*
					 * 계약번호/대리점ID/판매점ID/카드수수료/현금수수료/얘치금수수료
					 */
					
						
			            
					if(c == 0){
						sb1.append(sCellValue);
						sb1.append("|");
					}else if(c == 1){
						sb2.append(sCellValue);
						sb2.append("|");
					}

    			}//cell
		           
    			if(nUpdateCnt == 499 || r == rows-1){
    				
    				sb1 = new StringBuilder(sb1.toString().substring(0, sb1.toString().length()-1)); // 계약번호
    				sb2 = new StringBuilder(sb2.toString().substring(0, sb2.toString().length()-1)); // 대리점 ID
    				
    				// procedure 호출
    				resultMap = procPpsStmRefundExcel(pRequestParamMap
    						,sb1.toString()
    						,sb2.toString()
    						);
    				
    				// 실패시 리턴 내역삭제는 프로시져에서 실행
    				if(!resultMap.get("retCode").equals("0000")){
    					//샘플파일이 아니면 파일삭제
    					if(!fileName.equals("agentStmRefundExcelSample.xls") && !fileName.equals("agentStmRefundExcelSample.xlsx")){
    						File file = null;
    						file = new File(filePath);
    						file.delete();
    					}
    					
    					return resultMap;
    				}
    				//두번째 호출부터는 opCode를 C로 보낸다.
    				pRequestParamMap.put("opCode", "C");
    				
    				nUpdateCnt = 0;
    				sb1 = new StringBuilder(); // 계약번호
    				sb2 = new StringBuilder(); // 메모
    			}
    			
    			nUpdateCnt++;
	    	}//row
	         
		}
		catch(Exception e){
			throw new MvnoRunException(-1,"");
		}
		finally {
			if (fInput != null) {
				try { fInput.close(); } catch (IOException e) {  throw new MvnoRunException(-1,e.getMessage()); }
			}
		}
		
		return resultMap;
	}
	
	public Map<String, Object> procPpsStmRefundExcel( Map<String, Object> pRequestParamMap,
			String contracts, String remark){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("opCode", pRequestParamMap.get("opCode"));
		resultMap.put("billMonth", "");
		resultMap.put("gubun", pRequestParamMap.get("refundGubun"));
		resultMap.put("contractNum", contracts);
		resultMap.put("remark", remark);
		resultMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));

		resultMap.put("retCode", "");
		resultMap.put("retMsg", "");
		stmMgmtMapper.procPpsStmRefundExcel(resultMap);
		
		return resultMap;
	}
	
	
	/**
	 * 각 수수료 상태 변경
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmStatusChg(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmStatusChg(pRequestParamMap);
		
		logger.debug("==============처리 후 Service \n==============");
		logger.debug("resultMap===>"+pRequestParamMap.toString()+"\n");
		logger.debug("===========================");

		return pRequestParamMap;
	}
	
	/**
	 * 대리점별 정산내역 수정
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsAgentStmAgentMod(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsAgentStmAgentMod(pRequestParamMap);
		
		logger.debug("==============처리 후 Service \n==============");
		logger.debug("resultMap===>"+pRequestParamMap.toString()+"\n");
		logger.debug("===========================");

		return pRequestParamMap;
	}
	
	/**
	 * 조정 수수료 등록 excel 파일 읽기
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsAgentStmModFileRead(Map<String, Object> pRequestParamMap, String sFileNm, String fileName ) throws MvnoRunException {
		
		String filePath			= sFileNm;
	    HSSFWorkbook workBook	= null;
	    HSSFSheet sheet			= null;
	    HSSFRow row				= null;
	    HSSFCell cell			= null;
	    String sCellValue		= "";
		FileInputStream fInput	= null;
		int nUpdateCnt 			= 0;
		
		StringBuilder sb1 = new StringBuilder(); // 계약번호
		StringBuilder sb2 = new StringBuilder(); // 대리점 ID
		StringBuilder sb3 = new StringBuilder(); // 판매점 ID
		StringBuilder sb4 = new StringBuilder(); // 카드수수료
		StringBuilder sb5 = new StringBuilder(); // 현금수수료
		StringBuilder sb6 = new StringBuilder(); // 예치금수수료
		StringBuilder sb7 = new StringBuilder(); // 메모
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try
		{ 

			fInput = new FileInputStream(new File(filePath));
		    workBook =  new HSSFWorkbook(fInput);
		    workBook.getNumberOfSheets();
			// 1 .sheet
	    	sheet = workBook.getSheetAt(0);
	    	int rows = sheet.getPhysicalNumberOfRows();
	    	
	    	pRequestParamMap.put("opCode", "F");
	          
    		// insert lgs Ord Prdt
	    	for(int r = 0 ; r < rows ; r++ )
	    	{
    			//row전체가 비어있는경우 건너띄기 위해
    			if(sheet.getRow(r) == null && rows -1 != r){
					rows++;
					continue;
    			}
	        	   
    			if( r == 0){
					continue;
    			}

    			row = sheet.getRow(r);
		           
    			for(int c=0 ; c < 7 ;c++){
					cell   =  row.getCell(c);		            
					if(cell== null){
						sCellValue = "";
					}else{
			            
						switch(cell.getCellType()){
							case HSSFCell.CELL_TYPE_FORMULA :
								sCellValue = ""+cell.getStringCellValue();
								 break;
							case HSSFCell.CELL_TYPE_NUMERIC :
								if(c>=3 && c<=5){
									sCellValue = ""+String.format("%.2f", cell.getNumericCellValue());
								}else{
									sCellValue = ""+String.format("%.0f", cell.getNumericCellValue());
								}
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
							 	break;
						}
					}
					
					/*
					 * 계약번호/대리점ID/판매점ID/카드수수료/현금수수료/얘치금수수료
					 */
					
						
			            
					if(c == 0){
						sb1.append(sCellValue);
						sb1.append("|");
					}else if(c == 1){
						sb2.append(sCellValue);
						sb2.append("|");
					}else if(c == 2){
						sb3.append(sCellValue);
						sb3.append("|");
					}else if(c == 3){
						if(sCellValue == null || "".equals(sCellValue)){
							sb4.append("0");
						}else{
							sb4.append(sCellValue);
						}
						sb4.append("|");
					}else if(c == 4){
						if(sCellValue == null || "".equals(sCellValue)){
							sb5.append("0");
						}else{
							sb5.append(sCellValue);
						}
						sb5.append("|");
					}else if(c == 5){
						if(sCellValue == null || "".equals(sCellValue)){
							sb6.append("0");
						}else{
							sb6.append(sCellValue);
						}
						sb6.append("|");
						
					}else if(c == 6){
						sb7.append(sCellValue);
						sb7.append("|");
					}

    			}//cell
		           
    			if(nUpdateCnt == 499 || r == rows-1){
    				sb1 = new StringBuilder(sb1.toString().substring(0, sb1.toString().length()-1)); // 계약번호
    				sb2 = new StringBuilder(sb2.toString().substring(0, sb2.toString().length()-1)); // 대리점 ID
    				sb3 = new StringBuilder(sb3.toString().substring(0, sb3.toString().length()-1)); // 판매점 ID
    				sb4 = new StringBuilder(sb4.toString().substring(0, sb4.toString().length()-1)); // 카드수수료
    				sb5 = new StringBuilder(sb5.toString().substring(0, sb5.toString().length()-1)); // 현금수수료
    				sb6 = new StringBuilder(sb6.toString().substring(0, sb6.toString().length()-1)); // 예치금수수료
    				sb7 = new StringBuilder(sb7.toString().substring(0, sb7.toString().length()-1)); // 메모
    				
    				// procedure 호출
    				resultMap = procPpsAgentStmModExcel(pRequestParamMap
    						,sb1.toString()
    						,sb2.toString()
    						,sb3.toString()
    						,sb4.toString()
    						,sb5.toString()
    						,sb6.toString()
    						,sb7.toString()
    						);
    				
    				// 실패시 리턴 내역삭제는 프로시져에서 실행
    				if(!resultMap.get("retCode").equals("0000")){
    					//샘플파일이 아니면 파일삭제
    					if(!fileName.equals("agentStmModExcelSample.xls") && !fileName.equals("agentStmModExcelSample.xlsx")){
    						File file = null;
    						file = new File(filePath);
    						file.delete();
    					}
    					
    					return resultMap;
    				}
    				//두번째 호출부터는 opCode를 C로 보낸다.
    				pRequestParamMap.put("opCode", "C");
    				
    				nUpdateCnt = 0;
    				sb1 = new StringBuilder(); // 계약번호
    				sb2 = new StringBuilder(); // 대리점 ID
    				sb3 = new StringBuilder(); // 판매점 ID
    				sb4 = new StringBuilder(); // 카드수수료
    				sb5 = new StringBuilder(); // 현금수수료
    				sb6 = new StringBuilder(); // 예치금수수료
    				sb7 = new StringBuilder(); // 메모
    			}
    			
    			nUpdateCnt++;
	    	}//row
	         
		}
		catch(Exception e){
			throw new MvnoRunException(-1,"");
		}
		finally {
			if (fInput != null) {
				try { fInput.close(); } catch (IOException e) {  throw new MvnoRunException(-1,e.getMessage()); }
			}
		}
		
		//샘플파일이 아니면 파일삭제
		if(!fileName.equals("agentStmModExcelSample.xls") && !fileName.equals("agentStmModExcelSample.xlsx")){
			File file = null;
			file = new File(filePath);
			file.delete();
		}
		
		return resultMap;
	}
	
	public Map<String, Object> procPpsAgentStmModExcel( Map<String, Object> pRequestParamMap,
			String contracts, String agentIds, String saleIds, String cardSd, String cashSd, String depSd, String remark){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("opCode", pRequestParamMap.get("opCode"));
		resultMap.put("billMonth", "");
		resultMap.put("contractNum", contracts);
		resultMap.put("agentId", agentIds);
		resultMap.put("agentSaleId", saleIds);
		resultMap.put("cardSd", cardSd);
		resultMap.put("cashSd", cashSd);
		resultMap.put("depSd", depSd);
		resultMap.put("remark", remark);
		resultMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));

		resultMap.put("retCode", "");
		resultMap.put("retMsg", "");
		stmMgmtMapper.procPpsAgentStmMod(resultMap);
		
		return resultMap;
	}
	
	/**
	 * 정책그룹 리스트
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getPpsAgentStmGroupData(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getPpsAgentStmGroupData(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 정책관리 리스트
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmSetupMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmSetupMgmtList(pRequestParamMap);

		return resultList;
	}
	
	/**
	 * 정책 값 변경
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmSetupChg(Map<String, Object> pRequestParamMap) {

		if(pRequestParamMap.get("opCode").equals("DTL_MOD") || pRequestParamMap.get("opCode").equals("DTL_REG")){
			if(pRequestParamMap.get("codeAType").equals("SELECT") && pRequestParamMap.get("codeAFlag").equals("Y")){
				pRequestParamMap.put("codeA", pRequestParamMap.get("codeASelect"));
			}else if(pRequestParamMap.get("codeAType").equals("INPUT") && pRequestParamMap.get("codeAFlag").equals("Y")){
				pRequestParamMap.put("codeA", pRequestParamMap.get("codeAInput"));
			}else{
				pRequestParamMap.put("codeAFlag", "N");
			}
			
			if(pRequestParamMap.get("codeBType").equals("SELECT") && pRequestParamMap.get("codeBFlag").equals("Y")){
				pRequestParamMap.put("codeB", pRequestParamMap.get("codeBSelect"));
			}else if(pRequestParamMap.get("codeBType").equals("INPUT") && pRequestParamMap.get("codeBFlag").equals("Y")){
				pRequestParamMap.put("codeB", pRequestParamMap.get("codeBInput"));
			}else{
				pRequestParamMap.put("codeBFlag", "N");
			}
			
			if(pRequestParamMap.get("codeCType").equals("SELECT") && pRequestParamMap.get("codeCFlag").equals("Y")){
				pRequestParamMap.put("codeC", pRequestParamMap.get("codeCSelect"));
			}else if(pRequestParamMap.get("codeCType").equals("INPUT") && pRequestParamMap.get("codeCFlag").equals("Y")){
				pRequestParamMap.put("codeC", pRequestParamMap.get("codeCInput"));
			}else{
				pRequestParamMap.put("codeCFlag", "N");
			}
			
			if(pRequestParamMap.get("codeDType").equals("SELECT") && pRequestParamMap.get("codeDFlag").equals("Y")){
				pRequestParamMap.put("codeD", pRequestParamMap.get("codeDSelect"));
			}else if(pRequestParamMap.get("codeDType").equals("INPUT") && pRequestParamMap.get("codeDFlag").equals("Y")){
				pRequestParamMap.put("codeD", pRequestParamMap.get("codeDInput"));
			}else{
				pRequestParamMap.put("codeDFlag", "N");
			}
			
			if(pRequestParamMap.get("codeEType").equals("SELECT") && pRequestParamMap.get("codeEFlag").equals("Y")){
				pRequestParamMap.put("codeE", pRequestParamMap.get("codeESelect"));
			}else if(pRequestParamMap.get("codeEType").equals("INPUT") && pRequestParamMap.get("codeEFlag").equals("Y")){
				pRequestParamMap.put("codeE", pRequestParamMap.get("codeEInput"));
			}else{
				pRequestParamMap.put("codeEFlag", "N");
			}
		}
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmSetupChg(pRequestParamMap);
		
		logger.debug("==============처리 후 Service \n==============");
		logger.debug("resultMap===>"+pRequestParamMap.toString()+"\n");
		logger.debug("===========================");

		return pRequestParamMap;
	}
	
	/**
	 * 정책 세부내역 리스트
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmSetupDtlMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmSetupDtlMgmtList(pRequestParamMap);

		return resultList;
	}
	
	/**
	 * 중복 개통 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmOverlapList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmOverlapList(pRequestParamMap);

		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		return resultList;
	}
	
	
	/**
	 * 중복 개통 조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getStmOverlapListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","대리점명","고객번호","계약번호","개통번호","고객명","개통일","신분증","요금제","모집경로","상태(1일)","3G/LTE","서식지수","기본수수료상태","중복개통상태","메모");
		param.setStrValue("BILL_MONTH","AGENT_ID_1_NM","CUSTOMER_ID","CONTRACT_NUM","SUBSCRIBER_NO","SUB_LINK_NAME","ENTER_DATE","CUST_IDNT_NO_IND_CD_NM","SOC_NM","ONOFF","SUB_STATUS_1_NM","DATA_TYPE_1","DOC_CNT_1","BASIC_STATUS_NM","OVERLAP_STATUS_NM","REMARK");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("중복개통내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmOverlapListExcel", pRequestParamMap, param, handler);
		String fileName = "중복개통내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 정책코드
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmCodeMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmCodeMgmtList(pRequestParamMap);

		return resultList;
	}
	
	/**
	 * 현재 정산 진행상태 체크
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getStmCntMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmCntMgmtList(pRequestParamMap);

		return resultList;
	}
	
	/**
	 * 수동 기본수수료 정산
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmBasic(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmBasic(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 수동 grade수수료 정산
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmGrade(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmGrade(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 수동 명변수수료 정산
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmMb(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmMb(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 수동 재충전수수료 정산
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmRcg(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmRcg(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 수동 비정상해지 정산
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmRefund(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmRefund(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 수동 기준사용미달 정산
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmNouse(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmNouse(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 수동 해지후재가입 정산
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmReopen(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmReopen(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 수동 우량수수료 정산
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmUl(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmUl(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 수동 자동이체수수료 정산
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmCms(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmCms(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 수동 대리점별 정산
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStmAgent(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStmAgent(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 수동 통합 정산
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> procPpsStm(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
		pRequestParamMap.put("retCode", "");
		pRequestParamMap.put("retMsg", "");
		
		stmMgmtMapper.procPpsStm(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 대리점정산관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getStmSelfMgmtList(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getStmSelfMgmtList(pRequestParamMap);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 대리점정산관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getStmSelfMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","대리점명","카드(정산액)","현금(정산액)","개통(실적)","해지(실적)","실정산(실적)","건수(위탁수수료)","카드(위탁수수료)","현금(위탁수수료)","건수(유지수수료)","카드(유지수수료)","현금(유지수수료)","건수(프로모션수수료)","카드(프로모션수수료)","현금(프로모션수수료)","건수(환수)","카드(환수)","현금(환수)","카드(전월미정산)","현금(전월미정산)","카드(기타정산)","현금(기타정산)","카드(지급수수료)","현금(지급수수료)","등록일자","등록관리자");
		param.setStrValue("BILL_MONTH","AGENT_NM","OP_PPS","OP_OPEN_AMT","OP_OPEN_CNT","OP_CAN_CNT","OP_REAL_CNT","PSD_CNT","PSD_PPS","PSD_AMT","MNG_CNT","MNG_PPS","MNG_AMT","PRM_CNT","PRM_PPS","PRM_AMT","RF_CNT","RF_PPS","RF_AMT","LAST_PPS","LAST_AMT","ETC_PPS","ETC_AMT","TOT_PPS","TOT_AMT","REG_DT","REG_ADMIN_NM");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("대리점정산관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsHdofcStmMgmtMapper.getStmSelfMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "대리점정산관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 대리점정산관리 등록 excel 파일 읽기
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsAgentStmSelfFileRead(Map<String, Object> pRequestParamMap, String sFileNm, String fileName ) throws MvnoRunException {
		
		String filePath			= sFileNm;
	    HSSFWorkbook workBook	= null;
	    HSSFSheet sheet			= null;
	    HSSFRow row				= null;
	    HSSFCell cell			= null;
	    String sCellValue		= "";
		FileInputStream fInput	= null;
		int nUpdateCnt 			= 0;
		int nSbCnt				= 21;
		
		StringBuilder sb = new StringBuilder();
		String[] stArr = new String[nSbCnt];
		
		try
		{ 

			fInput = new FileInputStream(new File(filePath));
		    workBook =  new HSSFWorkbook(fInput);
		    workBook.getNumberOfSheets();
			// 1 .sheet
	    	sheet = workBook.getSheetAt(0);
	    	int rows = sheet.getPhysicalNumberOfRows();
	    	
	    	pRequestParamMap.put("opCode", "REG1");
	    	
    		// insert lgs Ord Prdt
	    	for(int r = 0 ; r < rows ; r++ )
	    	{
	    		
    			//row전체가 비어있는경우 건너띄기 위해
    			if(sheet.getRow(r) == null && rows -1 != r){
					rows++;
					continue;
    			}
	        	/*
    			if( r == 0){
					continue;
    			}
    			*/

    			row = sheet.getRow(r);
    			
    			for(int c=0 ; c < nSbCnt ;c++){
    				
    				if("".equals(stArr[c]) || stArr[c] == null){
    					sb = new StringBuilder();
    				}else{
    					sb = new StringBuilder(stArr[c]);
    				}
    				
					cell = row.getCell(c);
					if(cell== null){
						sCellValue = "";
					}else{
						switch(cell.getCellType()){
							case HSSFCell.CELL_TYPE_FORMULA :
								sCellValue = ""+cell.getStringCellValue();
								 break;
							case HSSFCell.CELL_TYPE_NUMERIC :
								sCellValue = ""+String.format("%.0f", cell.getNumericCellValue());
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
							 	break;
						}
					}
					
					sb.append(sCellValue);
					sb.append("|");
					
					stArr[c] = sb.toString();
					
    			}//cell
    			
    			
    			
    			if(nUpdateCnt == 499 || r == rows-1){
    				String[] arr = new String[]{"agentId", "opPps", "opOpenAmt", "opOpenCnt", "opCanCnt", "psdCnt", "psdPps", "psdAmt", "mngCnt", "mngPps", "mngAmt", "prmCnt", "prmPps", "prmAmt", "rfCnt", "rfPps", "rfAmt", "lastPps", "lastAmt", "etcPps", "etcAmt"};
    				pRequestParamMap.put("opCode", pRequestParamMap.get("opCode"));
    				pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));
    				for(int c=0 ; c < nSbCnt ;c++){
    					pRequestParamMap.put(arr[c], stArr[c].substring(0, stArr[c].length() - 1));
    				}
    				
    				// procedure 호출
    				procPpsAgentStmSelfExcel(pRequestParamMap);
    				
    				// 실패시 리턴 내역삭제는 프로시져에서 실행
    				if(!pRequestParamMap.get("retCode").equals("0000")){
    					//샘플파일이 아니면 파일삭제
    					if(!fileName.equals("agentStmSelfExcelSample.xls") && !fileName.equals("agentStmSelfExcelSample.xlsx")){
    						File file = null;
    						file = new File(filePath);
    						file.delete();
    					}
    					
    					return pRequestParamMap;
    				}
    				
    				//두번째 호출부터는 opCode를 REG2로 보낸다.
    				pRequestParamMap.put("opCode", "REG2");
    				
    				nUpdateCnt = 0;
    				stArr = new String[nSbCnt];
    				for(int c=0 ; c < nSbCnt ;c++){
    					pRequestParamMap.put(arr[c], "");
    				}
    			}
    			
    			nUpdateCnt++;
	    	}//row
	    	
	    	
	         
		}
		catch(Exception e){
			logger.debug(e);
			throw new MvnoRunException(-1,"");
		}
		finally {
			if (fInput != null) {
				try { fInput.close(); } catch (IOException e) {  throw new MvnoRunException(-1,e.getMessage()); }
			}
		}
		
		//샘플파일이 아니면 파일삭제
		if(!fileName.equals("agentStmSelfExcelSample.xls") && !fileName.equals("agentStmSelfExcelSample.xlsx")){
			File file = null;
			file = new File(filePath);
			file.delete();
		}
		
		return pRequestParamMap;
	}
	
	public Map<String, Object> procPpsAgentStmSelfExcel( Map<String, Object> pRequestParamMap){
		
		stmMgmtMapper.procPpsAgentStmSelf(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	public String getCurrentTimes()
	{
		String currDateTime = "";
		
		Date currtentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
		currDateTime = "_"+sdfFileName.format(currtentDate);
		
		
		return currDateTime;
	}
	
}
