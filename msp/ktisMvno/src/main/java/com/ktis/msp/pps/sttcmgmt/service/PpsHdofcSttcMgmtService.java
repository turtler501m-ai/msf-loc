package com.ktis.msp.pps.sttcmgmt.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.pps.sttcmgmt.mapper.PpsHdofcSttcMgmtMapper;
import com.ktis.msp.pps.sttcmgmt.mapper.PpsSttcHandler;
import com.ktis.msp.pps.sttcmgmt.vo.PpsStatsCallVo;
import com.ktis.msp.pps.sttcmgmt.vo.PpsStatsKjCallVo;
import com.ktis.msp.pps.sttcmgmt.vo.PpsVStatsCardVo;
import com.ktis.msp.pps.sttcmgmt.vo.PpsVStatsMinusDepositVo;
import com.ktis.msp.pps.sttcmgmt.vo.PpsVStatsOpenVo;
import com.ktis.msp.pps.sttcmgmt.vo.PpsVStatsPlusDepositVo;
import com.ktis.msp.pps.sttcmgmt.vo.PpsVStatsRechargeVo;

/**
 * @param <PpsHdofcSttcMgmtMapper>
 * @Class Name : PpsHdofcSttcMgmtService
 * @Description : 선불통계관리  service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */
@Service
public class PpsHdofcSttcMgmtService extends ExcelAwareService {
	
	@Autowired
	private PpsHdofcSttcMgmtMapper sttcMgmtMapper;
	
	@Autowired
    private FileDownService  fileDownService;
	
	
	
	/**
	 *  선불 개통현황통계 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getSttcOpenMgmtList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsVStatsOpenVo>();
		
		resultList = sttcMgmtMapper.getSttcOpenMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 선불 개통현황(통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getSttcOpenMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("기간", "개통대리점", "요금제", "총개통건수", "정상건수", "정지건수", "해지건수");
		param.setStrValue("STATUS_DATE","AGENT_NM","SOC_NM","TOTAL_OPEN_CNT","OPEN_CNT","STOP_CNT","CANCEL_CNT");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 1, 1, 1, 1);
		param.setSheetName("개통현황(통계)");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		
		
		PpsSttcHandler handler  = new PpsSttcHandler();
		
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.sttcmgmt.mapper.PpsHdofcSttcMgmtMapper.getSttcOpenMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "선불개통현황(통계)_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 선불 충전현황(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getSttcRechargeMgmtList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsVStatsRechargeVo>();
		
		resultList = sttcMgmtMapper.getSttcRechargeMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 선불 충전현황(통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getSttcRechargeMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("기간", "충전구분별", "결제방식별", "개통대리점", "충전대리점", "충전건수", "충전금액", "실결제금액", "음성충전", "무료충전");
		param.setStrValue("RECHARGE_DATE","CHG_TYPE_NM","RECHARGE_METHOD","AGENT_NM","RECHARGE_AGENT_NM","RECHARGE_CNT","AMOUNT","IN_AMOUNT","VOICE_RECHARGE","FREE_RECHARGE");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 1, 1, 1, 1,1);
		param.setSheetName("충전현황(통계)");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		
		
		PpsSttcHandler handler  = new PpsSttcHandler();
		
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.sttcmgmt.mapper.PpsHdofcSttcMgmtMapper.getSttcRechargeMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "선불충전현황(통계)_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 선불 예치금입금현황(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getSttcDepositInMgmtList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsVStatsPlusDepositVo>();
		
		resultList = sttcMgmtMapper.getSttcDepositInMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 선불 예치금입금현황(통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getSttcDepositInMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("기간", "대리점별", "입금은행별", "입금유형", "입금액");
		param.setStrValue("DEPOSIT_DATE","AGENT_NM","VAC_BANK_NAME","DEPOSIT_TYPE_NM","RECHARGE");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 1);
		param.setSheetName("예치금입금현황(통계)");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		
		
		PpsSttcHandler handler  = new PpsSttcHandler();
		
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.sttcmgmt.mapper.PpsHdofcSttcMgmtMapper.getSttcDepositInMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "선불예치금입금통계_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 선불 예치금출금현황(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getSttcDepositOutMgmtList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsVStatsMinusDepositVo>();
		
		resultList = sttcMgmtMapper.getSttcDepositOutMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 선불 예치금출금현황(통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getSttcDepositOutMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("기간", "대리점별",  "출금유형", "출금액");
		param.setStrValue("DEPOSIT_DATE","AGENT_NM","DEPOSIT_TYPE_NM","RECHARGE");
		param.setIntWidth(3000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 1);
		param.setSheetName("예치금출금현황(통계)");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		
		
		PpsSttcHandler handler  = new PpsSttcHandler();
		
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.sttcmgmt.mapper.PpsHdofcSttcMgmtMapper.getSttcDepositOutMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "선불예치금출금통계_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 선불 사용현황(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getSttcUseMgmtList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsStatsCallVo>();
		
		resultList = sttcMgmtMapper.getSttcUseMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 선불 사용현황(통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getSttcUseMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("기간", "일차감건수", "일차감액", "국내음성(분)", "국내통화료", "국제음성(분)", "국제통화금액", "국내데이타(M)", "국내데이타료", "국내문자건수", "국내문자료", "국제문자건수", "국제문자료", "기타건수", "기타금액");
		param.setStrValue("CALL_DATE","DAY_MINUS_CNT","DAY_MINUS_AMOUNT","IN_CALL_DURATION","IN_CALL_AMOUNT","OUT_CALL_DURATION","OUT_CALL_AMOUNT","IN_DATA_PKT_DURATION","IN_DATA_PKT_AMOUNT","IN_SMS_CNT","IN_SMS_AMOUNT","OUT_SMS_CNT","OUT_SMS_AMOUNT","ETC_CNT","ETC_AMOUNT");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 3000, 3000, 5000, 5000, 5000 ,5000, 5000,5000);
		param.setIntLen(0, 1, 1, 1, 1, 1, 1, 1, 1,1,1,1,1,1,1);
		param.setSheetName("사용현황(통계)");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		
		
		PpsSttcHandler handler  = new PpsSttcHandler();
		
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.sttcmgmt.mapper.PpsHdofcSttcMgmtMapper.getSttcUseMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "선불사용현황(통계)_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 선불 국제현황(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getSttcIntrnMgmtList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsStatsKjCallVo>();
		
		resultList = sttcMgmtMapper.getSttcIntrnMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 선불 국제현황(통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getSttcIntrnMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("기간", "TELCO사업자별", "국가별", "통화건수", "통화분수", "통화과금액", "문자건수", "문자과금액");
		param.setStrValue("CALL_DATE","TELCO_PX","PREFIX_NM","CALL_CNT","CALL_TIME","CALL_AMOUNT","SMS_CNT","SMS_AMOUNT");
		param.setIntWidth(3000, 5000, 5000, 3000, 5000, 5000, 5000, 3000, 3000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 1, 1, 1, 1, 1, 1,1,1,1);
		param.setSheetName("국제현황(통계)");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		
		
		PpsSttcHandler handler  = new PpsSttcHandler();
		
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.sttcmgmt.mapper.PpsHdofcSttcMgmtMapper.getSttcIntrnMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "선불국제현황(통계)_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 선불 선불 카드현황(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getSttcCardMgmtList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsVStatsCardVo>();
		
		resultList = sttcMgmtMapper.getSttcCardMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 선불 카드현황(통계)) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getSttcCardMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("기간", "On/Off구분", "음성/데이타", "생성PIN수", "출고수", "미출고수", "개통수", "개통금액", "미개통수", "충전수", "충전금액", "미충전수(개통후미충전)");
		param.setStrValue("CR_DATE","ONOFF_GUBUN_NM","PIN_GUBUN_NM","CREATE_PIN_CNT","PIN_OUT_CNT","PIN_NON_OUT_CNT","PIN_OPEN_CNT","OPEN_CHARGE","NON_OPEN_CNT","RCG_CNT","RCG_CHARGE","RCG_NON_CNT");
		param.setIntWidth(3000, 5000, 5000, 3000, 5000, 5000, 5000, 3000, 3000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 1, 1, 1, 1, 1, 1,1,1,1);
		param.setSheetName("카드현황(통계)");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		
		
		PpsSttcHandler handler  = new PpsSttcHandler();
		
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.sttcmgmt.mapper.PpsHdofcSttcMgmtMapper.getSttcCardMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "선불카드현황(통계)_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	
	public String getCurrentTimes()
	{
		String currDateTime = "";
		
		Date currtentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
		currDateTime = "_"+sdfFileName.format(currtentDate);
		
		
		return currDateTime;
	}
	
	
	/**
	 * 선불-arpu분석통계 목록조회
	 * @param pRequestParamMap
	 * @return
	 */
	public List<?> getSttcArpuMgmtList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsVStatsCardVo>();
		
		resultList = sttcMgmtMapper.getSttcArpuMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 선불 ARPU분석통계(통계)) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getSttcArpuMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("요금제","가입자수","ARPU","남자(%)","여자(%)","기타(%)","남자ARPU","여자ARPU","기타ARPU","사용일수(평균)","무료충전액(평균)","유료충전액(평균)","기본료(평균)","국내음성(평균)","국제음성(평균)","국내문자(평균)","국제문자(평균)","국내데이터(평균)","국제데이터(평균)","기타(평균)","대리점","국적");
		param.setStrValue("SOC_NM","TOT_CNT","APRU_CHARGE","TOT_M_CNT","TOT_F_CNT","TOT_ETC_CNT","AVG_M_USE_CHARGE","AVG_F_USE_CHARGE","AVG_ETC_USE_CHARGE","AVG_USE_TERM","AVG_RCG_FREE_CHARGE","AVG_RCG_PAY_CHARGE","AVG_DAY_CHARGE","AVG_VOC_N_CHARGE","AVG_VOC_I_CHARGE","AVG_SMS_N_CHARGE","AVG_SMS_I_CHARGE","AVG_PKT_N_CHARGE","AVG_PKT_I_CHARGE","AVG_ETC_CHARGE","AGENT_NM","AD_NATION_NM");
		param.setIntWidth(6000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 1, 1, 1, 1, 1, 1, 1, 1,1,1,1,1, 1, 1, 1, 1, 1, 1, 1,0,0);
		param.setSheetName("ARPU분석통계");
		param.setExcelPath(path);
		param.setFileName("test");
			
		PpsSttcHandler handler  = new PpsSttcHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.sttcmgmt.mapper.PpsHdofcSttcMgmtMapper.getSttcArpuMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "ARPU분석통계_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 선불 사용현황2(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getSttcUse2MgmtList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsStatsCallVo>();
		
		resultList = sttcMgmtMapper.getSttcUse2MgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 선불 사용현황2(통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getSttcUse2MgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("기간","요금제", "일차감건수", "일차감액", "국내음성(분)", "국내통화료", "국제음성(분)", "국제통화금액", "국내데이타(M)", "국내데이타료", "국내문자건수", "국내문자료", "국제문자건수", "국제문자료", "기타건수", "기타금액");
		param.setStrValue("CALL_DATE","SOC_TYPE_NM","DAY_MINUS_CNT","DAY_MINUS_AMOUNT","IN_CALL_DURATION","IN_CALL_AMOUNT","OUT_CALL_DURATION","OUT_CALL_AMOUNT","IN_DATA_PKT_DURATION","IN_DATA_PKT_AMOUNT","IN_SMS_CNT","IN_SMS_AMOUNT","OUT_SMS_CNT","OUT_SMS_AMOUNT","ETC_CNT","ETC_AMOUNT");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 3000, 3000, 5000, 5000, 5000 ,5000, 5000,5000);
		param.setIntLen(0, 0, 1, 1, 1, 1, 1, 1, 1, 1,1,1,1,1,1,1);
		param.setSheetName("사용현황2(통계)");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		
		
		PpsSttcHandler handler  = new PpsSttcHandler();
		
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.sttcmgmt.mapper.PpsHdofcSttcMgmtMapper.getSttcUse2MgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "선불사용현황2(통계)_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	
	 

}
