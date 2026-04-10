package com.ktis.msp.pps.datamgmt.service;


import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.pps.datamgmt.mapper.PpsAgencyDataMgmtMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @param <PpsAgencyDataMgmtMapper>
 * @Class Name : PpsHdofcDataMgmtService
 * @Description : 데이타관리  service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */

@Service

public class PpsAgencyDataMgmtService extends ExcelAwareService {
	
	@Autowired
	private PpsAgencyDataMgmtMapper dataMgmtMapper;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
    private FileDownService  fileDownService;
	
	/**
	 * 실시간선불정산대상 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getAgencyDataInfoMgmtList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		List<EgovMap> resultList = (List<EgovMap>)dataMgmtMapper.getAgencyDataInfoMgmtList(pRequestParamMap);
		DecimalFormat df = new DecimalFormat("#######.##");
		
		for(EgovMap map : resultList){
			
			//logger.debug("map ["+map.toString()+"]");
		    
		    if(map.get("basicRemains")!=null && !map.get("basicRemains").equals("0") ){
				
				map.put("strBasicRemains", df.format(map.get("basicRemains")).toString());
			}else{
				map.put("strBasicRemains", "0");
			}
			
			if(map.get("useDur")!=null && !map.get("useDur").equals("0") ){
				
				map.put("strUseDur", df.format(map.get("useDur")).toString());
			}else{
				map.put("strUseDur", "0");
			}
			
			if(map.get("useCharge")!=null && !map.get("useCharge").equals("0") ){
				
				map.put("strUseCharge", df.format(map.get("useCharge")).toString());
			}else{
				map.put("strUseCharge", "0");
			}
			
			if(map.get("usePkt")!=null && !map.get("usePkt").equals("0") ){
				
				map.put("strUsePkt", df.format(map.get("usePkt")).toString());
			}else{
				map.put("strUsePkt", "0");
			}
			
			if(map.get("vocNDur")!=null && !map.get("vocNDur").equals("0") ){
				
				map.put("strVocNDur", df.format(map.get("vocNDur")).toString());
			}else{
				map.put("strVocNDur", "0");
				
			}
			
			if(map.get("vocNCharge")!=null && !map.get("vocNCharge").equals("0") ){
				
				map.put("strVocNCharge", df.format(map.get("vocNCharge")).toString());
			}else{
				map.put("strVocNCharge", "0");
				
			}
			
			if(map.get("vocIDur")!=null && !map.get("vocIDur").equals("0") ){
				
				map.put("strVocIDur", df.format(map.get("vocIDur")).toString());
			}else{
				map.put("strVocIDur", "0");
				
			}
			
			if(map.get("vocICharge")!=null && !map.get("vocICharge").equals("0") ){
				
				map.put("strVocICharge", df.format(map.get("vocICharge")).toString());
			}else{
				map.put("strVocICharge", "0");
				
			}
			
			if(map.get("vidNDur")!=null && !map.get("vidNDur").equals("0") ){
				
				map.put("strVidNDur", df.format(map.get("vidNDur")).toString());
			}else{
				map.put("strVidNDur", "0");
				
			}
			
			if(map.get("vidNCharge")!=null && !map.get("vidNCharge").equals("0") ){
				
				map.put("strVidNCharge", df.format(map.get("vidNCharge")).toString());
			}else{
				map.put("strVidNCharge", "0");
				
			}
			
			if(map.get("vidIDur")!=null && !map.get("vidIDur").equals("0") ){
				
				map.put("strVidIDur", df.format(map.get("vidIDur")).toString());
			}else{
				map.put("strVidIDur", "0");
				
			}
			
			if(map.get("vidICharge")!=null && !map.get("vidICharge").equals("0") ){
				
				map.put("strVidICharge", df.format(map.get("vidICharge")).toString());
			}else{
				map.put("strVidICharge", "0");
				
			}
			
			if(map.get("smsNCharge")!=null && !map.get("smsNCharge").equals("0") ){
				
				map.put("strSmsNCharge", df.format(map.get("smsNCharge")).toString());
			}else{
				map.put("strSmsNCharge", "0");
				
			}
			
			if(map.get("smsICharge")!=null && !map.get("smsICharge").equals("0") ){
				
				map.put("strSmsICharge", df.format(map.get("smsICharge")).toString());
			}else{
				map.put("strSmsICharge", "0");
				
			}
			
			if(map.get("pktNDur")!=null && !map.get("pktNDur").equals("0") ){
				
				map.put("strPktNDur", df.format(map.get("pktNDur")).toString());
			}else{
				map.put("strPktNDur", "0");
				
			}
			
			if(map.get("pktNCharge")!=null && !map.get("pktNCharge").equals("0") ){
				
				map.put("strPktNCharge", df.format(map.get("pktNCharge")).toString());
			}else{
				map.put("strPktNCharge", "0");
				
			}
			
			if(map.get("pktIDur")!=null && !map.get("pktIDur").equals("0") ){
				
				map.put("strPktIDur", df.format(map.get("pktIDur")).toString());
			}else{
				map.put("strPktIDur", "0");
				
			}
			
			if(map.get("pktICharge")!=null && !map.get("pktICharge").equals("0") ){
				
				map.put("strPktICharge", df.format(map.get("pktICharge")).toString());
			}else{
				map.put("strPktICharge", "0");
				
			}
			
			if(map.get("dayCharge")!=null && !map.get("dayCharge").equals("0") ){
				
				map.put("strDayCharge", df.format(map.get("dayCharge")).toString());
			}else{
				map.put("strDayCharge", "0");
				
			}
			
			if(map.get("etcDur")!=null && !map.get("etcDur").equals("0") ){
				
				map.put("strEtcDur", df.format(map.get("etcDur")).toString());
			}else{
				map.put("strEtcDur", "0");
				
			}
			
			if(map.get("etcPkt")!=null && !map.get("etcPkt").equals("0") ){
				
				map.put("strEtcPkt", df.format(map.get("etcPkt")).toString());
			}else{
				map.put("strEtcPkt", "0");
				
			}
			
			if(map.get("etcCharge")!=null && !map.get("etcCharge").equals("0") ){
				
				map.put("strEtcCharge", df.format(map.get("etcCharge")).toString());
			}else{
				map.put("strEtcCharge", "0");
				
			}
			
			if(map.get("totPktCharge")!=null && !map.get("totPktCharge").equals("0") ){
				
				map.put("strTotPktCharge", df.format(map.get("totPktCharge")).toString());
			}else{
				map.put("strTotPktCharge", "0");
				
			}
			
			if(map.get("totVocCharge")!=null && !map.get("totVocCharge").equals("0") ){
				
				map.put("strTotVocCharge", df.format(map.get("totVocCharge")).toString());
			}else{
				map.put("strTotVocCharge", "0");
				
			}
			
			if(map.get("totVocDur")!=null && !map.get("totVocDur").equals("0") ){
				
				map.put("strTotVocDur", df.format(map.get("totVocDur")).toString());
			}else{
				map.put("strTotVocDur", "0");
				
			}
			
			if(map.get("totVidCharge")!=null && !map.get("totVidCharge").equals("0") ){
				
				map.put("strTotVidCharge", df.format(map.get("totVidCharge")).toString());
			}else{
				map.put("strTotVidCharge", "0");
				
			}
			
			if(map.get("totVidDur")!=null && !map.get("totVidDur").equals("0") ){
				
				map.put("strTotVidDur", df.format(map.get("totVidDur")).toString());
			}else{
				map.put("strTotVidDur", "0");
				
			}
			
			if(map.get("totSmsCharge")!=null && !map.get("totSmsCharge").equals("0") ){
				
				map.put("strTotSmsCharge", df.format(map.get("totSmsCharge")).toString());
			}else{
				map.put("strTotSmsCharge", "0");
				
			}
		    
		}
		
		//logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		return resultList;
	}
	
	/**
	 * 실시간선불관리대상 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getAgencyDataInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		
		
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호", "CTN", "계약자", "국적", "요금제", "상태", "개통일", "정지일", "해지일","만료일", "모델명", "비자여부", "사용기간", "총시도수", "총시도액","조정금액","무료충전액","유료충전액","무료취소액","유료취소액","총충전액", "최근충전일","사용횟수","사용분","사용액","일차감","데이타량(M)","데이타사용액","잔액","개통대리점","판매점","일차감건수","일차감액","국내음성(분)","국내통화료","국제음성(분)","국제통화료","국내데이터(M)","국내데이타료","국내문자건수","국내문자료","국제문자건수","국제문자료","기타건수","기타금액");
		param.setStrValue("CONTRACT_NUM", "SUBSCRIBER_NO","SUB_LINK_NAME","AD_NATION_NM","SERVICE_NM","SUB_STATUS_NM","ENTER_DATE","STATUS_STOP_DT","STATUS_CANCEL_DT","BASIC_EXPIRE","MODEL_NAME","VIZA_FLAG","USE_TERM","TRY_RCG_CNT","TRY_RCG_CHARGE","RCG_EDIT_CHARGE","RCG_FREE_CHARGE","RCG_PAY_CHARGE","RCG_FREE_CANCEL_CHARGE","RCG_PAY_CANCEL_CHARGE","TOT_RCG_CHARGE","LAST_BASIC_CHG_DT","USE_CNT","USE_DUR","USE_CHARGE","DAY_CHARGE","USE_PKT","TOT_PKT_CHARGE","BASIC_REMAINS","AGENT_NM","AGENT_SALE_NM","DAY_CNT","DAY_CHARGE","VOC_N_DUR","VOC_N_CHARGE","VOC_I_DUR","VOC_I_CHARGE","PKT_N_DUR","PKT_N_CHARGE","SMS_N_CNT","SMS_N_CHARGE","SMS_I_CNT","SMS_I_CHARGE","ETC_CNT","ETC_CHARGE");
		param.setIntWidth(3000, 3000, 5000,5000,5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,5000,5000,5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,5000,5000,5000,5000);
		param.setIntLen(0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1,0,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1);
		param.setSheetName("실시간선불정산대상");
	 	
		
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
				
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
		
		File f = makeBigDataExcel("com.ktis.msp.pps.datamgmt.mapper.PpsAgencyDataMgmtMapper.getAgencyDataInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "";
		try {
			
				fileName = "실시간선불관리대상_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
				
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
		        
				doDownload(response, f, URLEncoder.encode(fileName, "UTF-8"));
		 	
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
	
	}
	
	 /**
	 * 선불관리대상 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public void getDataInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		//List<EgovMap> resultList = (List<EgovMap>)dataMgmtMapper.getDataInfoMgmtListExcel(pRequestParamMap);
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호", "계약자", "요금제", "상태", "개통일", "정지일", "해지일","만료일", "모델명", "사용기간", "총충전액", "총충전수", "최근충전일", "실충전액", "사용횟수", "사용분", "사용액", "일차감", "데이타량(M)", "데이타사용액","사용액합계","잔액", "개통대리점", "판매점");
		param.setStrValue("CONTRACTNUM", "CUSTOMERLINKNAME", "SERVICENM", "SUBSTATUSNM", "LSTCOMACTVDATE", "STATUSSTOPDT", "STATUSCANCELDT","BASICEXPIRE", "MODELNAME", "USETURM", "TOTRCG", "RCGCNT", "LASTBASICCHGDT", "REALRCG", "CALLCNT", "CALLDUR", "CALLCHARGE", "DAYCHARGE", "PKTDUR", "PKTCHARGE","TOTALCHARGE","BASICREMAINS","AGENTNAME","AGENTSALENAME");
		param.setIntWidth(3000, 5000,5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,5000,5000,5000);
		param.setIntLen(0, 0, 0, 0,0, 0, 0, 0, 0, 1, 1, 1, 0,1,1,1,1,1,1,1,1,1,0,0);
		if("basicPage".equals(pRequestParamMap.get("dataPageType").toString())){
			param.setSheetName("선불정산대상");
	 	}else if("usimPage".equals(pRequestParamMap.get("dataPageType").toString())){
	 		param.setSheetName("유심임대선불관리");
	 	}
		
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
				
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
		
		File f = makeBigDataExcel("com.ktis.msp.pps.datamgmt.mapper.PpsAgencyDataMgmtMapper.getDataInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "";
		try {
			if("basicPage".equals(pRequestParamMap.get("dataPageType").toString())){
				fileName = "선불관리대상_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		 	}else if("usimPage".equals(pRequestParamMap.get("dataPageType").toString())){
		 		fileName = "유심임대선불관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		 	}
			
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
			
			doDownload(response, f, URLEncoder.encode(fileName, "UTF-8"));
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}

		//logger.debug("service  resultList ["+resultList.toString()+"]"); 
		//resultList = rcgMgmtMapper.getRcgInfoMgmtListExcel(pRequestParamMap);
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
