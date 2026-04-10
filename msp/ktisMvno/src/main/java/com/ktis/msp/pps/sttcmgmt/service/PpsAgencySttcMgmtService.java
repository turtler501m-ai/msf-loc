package com.ktis.msp.pps.sttcmgmt.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
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
import com.ktis.msp.pps.sttcmgmt.mapper.PpsAgencySttcMgmtMapper;
import com.ktis.msp.pps.sttcmgmt.mapper.PpsSttcHandler;
import com.ktis.msp.pps.sttcmgmt.vo.PpsVStatsOpenVo;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @param <PpsHdofcSttcMgmtMapper>
 * @Class Name : PpsAgencySttcMgmtService
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
public class PpsAgencySttcMgmtService extends ExcelAwareService {
	
	@Autowired
	private PpsAgencySttcMgmtMapper sttcMgmtMapper;
	
	@Autowired
    private FileDownService  fileDownService;
	
	
	public String getCurrentTimes()
	{
		String currDateTime = "";
		
		Date currtentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
		currDateTime = "_"+sdfFileName.format(currtentDate);
		
		
		return currDateTime;
	}
	
	/**
	 * 선불 대리점 개통현황(통계) 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getAgencySttcOpenMgmtList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsVStatsOpenVo>();
		
		resultList = sttcMgmtMapper.getAgencySttcOpenMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 선불 대리점 개통현황(통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getAgencySttcOpenMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("기간", "개통대리점", "요금제", "총개통건수", "정상건수", "정지건수", "해지건수");
		param.setStrValue("STATUS_DATE","AGENT_NM","SOC_NM","TOTAL_OPEN_CNT","OPEN_CNT","STOP_CNT","CANCEL_CNT");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 1, 1, 1, 1);
		param.setSheetName("개통현황");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		
		
		PpsSttcHandler handler  = new PpsSttcHandler();
		
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.sttcmgmt.mapper.PpsAgencySttcMgmtMapper.getAgencySttcOpenMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "선불개통현황_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 가입자 이용통계 조회
	 * @param pRequestParamMap
	 * @return
	 */
	public List<?> getAgencySttcSubscribersMgmtList(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = new ArrayList<EgovMap>();
		
		resultList = (List<EgovMap>)sttcMgmtMapper.getAgencySttcSubscribersMgmtList(pRequestParamMap);
		DecimalFormat df = new DecimalFormat("#######.##");

		for( EgovMap map : resultList  ){
			if(map.get("basRemains")!=null && !map.get("basRemains").equals("0") ){
				
				map.put("strBasicRemains", df.format(map.get("basRemains")).toString());
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
		
		
		
		return resultList;
	}
	/**
	 * 가입자이용통계 엑셀출력
	 * @param response
	 * @param request
	 * @param pRequestParamMap
	 * @param path
	 */
	public void getAgencySttcSubscribersMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","이름", "요금제명", "상태", "개통일","정지일", "해지일", "사용기간", "총충전금액", "최근충전일", "음성사용액", "데이터사용액", "총사용액", "판매점");
		param.setStrValue("CONTRACT_NUM","SUB_LINK_NAME","SERVICE_NM","SUB_STATUS_NM","ENTER_DATE","STATUS_STOP_DT","STATUS_CANCEL_DT","USE_TERM","TOT_RCG_CHARGE","LAST_BASIC_CHG_DT","TOT_VOC_CHARGE","TOT_PKT_CHARGE","USE_CHARGE","AGENT_SALE_NM");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0,0,0,0,0,1, 1, 0, 1,1,1,0);
		param.setSheetName("가입자이용현황");
		param.setExcelPath(path);
		param.setFileName("test");
			
		PpsSttcHandler handler  = new PpsSttcHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.sttcmgmt.mapper.PpsAgencySttcMgmtMapper.getAgencySttcSubscribersMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "선불가입자이용현황_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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