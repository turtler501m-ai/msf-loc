package com.ktis.msp.pps.rcgmgmt.service;


import java.io.File;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.hdofccustmgmt.service.PpsHdofcCommonService;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsAgentVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsKtInResVo;
import com.ktis.msp.pps.rcgmgmt.mapper.PpsHdofcRcgMgmtMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @param <PpsHdofcRcgMgmtMapper>
 * @Class Name : PpsHdofcRcgMgmtService
 * @Description : 선불충전관리  service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */

@Service

public class PpsHdofcRcgMgmtService extends ExcelAwareService {
	@Autowired
	private PpsHdofcCommonService  hdofcCommonService;
	
	@Autowired
	private PpsHdofcRcgMgmtMapper rcgMgmtMapper;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	/**
	 * 충전내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getRcgInfoMgmtList(Map<String, Object> pRequestParamMap){
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		List<EgovMap> resultList = (List<EgovMap>)rcgMgmtMapper.getRcgInfoMgmtList(pRequestParamMap);

		for(EgovMap map : resultList){
			
			logger.debug("map ["+map.toString()+"]");
			
			
		    if(map.get("cancelBtn") != null && !"".equals(map.get("cancelBtn")))
		    {	
		    	String cancelBtn = map.get("cancelBtn").toString();
		    	StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goRcgCancel("+map.get("seq")+");^_self";
		    	sb.append(cancelBtn).append(tmp);
		    	map.put("cancelBtn", sb.toString());
		    }
			
		    if(map.get("contractNum") != null && !"".equals(map.get("cancelBtn")))
			{	
				String contractNum = map.get("contractNum").toString();
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustDetail("+contractNum+");^_self";
				sb.append(contractNum).append(tmp);
		    	
		    	map.put("contractNum", sb.toString());
		    }
		    
		    if(map.get("ktResCode") != null && !"".equals(map.get("ktResCode"))){
		    	String remark = "";
		    	String ktResCode = map.get("ktResCode").toString();
		    	StringBuffer sb = new StringBuffer();
		    	if(ktResCode.equals("4002")){
		    		remark = "미가입고객";
		    	}else{
		    		if(map.get("remark") != null && !"".equals(map.get("remark"))){
		    			remark = map.get("remark").toString();
		    		}
		    	}
		    	sb.append(remark);	
		    	map.put("remark", sb.toString());
		    }
		    
		}
		
		HashMap<String, String> maskFields = new HashMap<String, String>();		
		maskFields.put("remark","SYSTEM_ID");
		maskFields.put("adminNm","CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);

		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		return resultList;
	}
	
	/**
	 * 충전내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getRcgInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호", "충전요청", "충전구분", "충전정보", "결제방식", "충전금액", "실입금액", "충전결과","결과메세지", "개통일자", "충전일자", "충전후잔액", "충전후만료일", "개통대리점", "판매점", "충전대리점", "충전관리자","요금제", "취소여부", "취소일자","카드구분");
		param.setStrValue("CONTRACTNUM","REQSRC","CHGTYPENAME","RECHARGEINFO","RECHARGEMETHOD","AMOUNT","INAMOUNT","KTRESCODENM","REMARK","LSTCOMACTVDATE", "RECHARGEDATE","BASICREMAINS","BASICEXPIRE","OPENAGENTNM","OPENAGENTSALENM","RECHARGEAGENTNAME","ADMINNM","SOC_NM","CANCELFLAG","CANCELDT","FREE_FLAG_NM");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000);
		param.setIntLen(0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1,0,0,0,0,0,0,0,0,0);
		param.setSheetName("충전내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.rcgmgmt.mapper.PpsHdofcRcgMgmtMapper.getRcgInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "충전내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 실시간자동출금내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@Crypto(decryptName="DBMSDec", fields = {"bankAccount"})
	public List<?> getRcgRealCmsMgmtList(Map<String, Object> pRequestParamMap){
		//List<?> resultList = new ArrayList<PpsRcgRealCmsVo>();
		
		List<EgovMap> resultList = (List<EgovMap>)rcgMgmtMapper.getRcgRealCmsMgmtList(pRequestParamMap);

		for(EgovMap map : resultList){
			
			//logger.debug("map ["+map.toString()+"]");
			
			String contractNum = map.get("contractNum").toString();
		    if(!StringUtils.isBlank(contractNum)&&!contractNum.equals("0"))
		    	
		    {
		    	StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustDetail("+contractNum+");^_self";
		    	sb.append(contractNum).append(tmp);
		    	
		    	map.put("contractNum", sb.toString());
		    }

		}

		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		


	
		return resultList;
	}
	
	
	/**
	 * 실시간자동출금내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@Crypto(decryptName="DBMSDec", fields = {"bankAccount"})
	public void getRcgRealCmsMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		//List<?> resultList = new ArrayList<PpsRcgRealCmsVo>();
		
		//resultList = rcgMgmtMapper.getRcgRealCmsMgmtListExcel(pRequestParamMap);

		
		//String strFilename = serverInfo  + "선불실시간자동충금내역_"; //파일명
		
		
		
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","요청일자","구분","출금여부","은행코드","예금주명","출금요청금액","실제출금액","출금결과코드","출금수수료","충전결과");
		param.setStrValue("CONTRACTNUM","REQDATE","CMSTYPE","SUCCFLAGNM","BANKCODENAME","BANKUSERNAME","REQAMOUNT","RESAMOUNT","RESCODENAME","CHARGEFEE","RECHARGERESULT");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0);
		param.setSheetName("실시간자동출금내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.rcgmgmt.mapper.PpsHdofcRcgMgmtMapper.getRcgRealCmsMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "실시간자동출금내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 가상계좌입금내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getRcgVacRechargeMgmtList(Map<String, Object> pRequestParamMap){
		//List<?> resultList = new ArrayList<PpsRcgVacVo>();
		List<EgovMap> resultList = (List<EgovMap>)rcgMgmtMapper.getRcgVacRechargeMgmtList(pRequestParamMap);
		for(EgovMap map : resultList){
			
			//logger.debug("map ["+map.toString()+"]");

			if(map.get("contractNum") != null )
			{	
				String contractNum = map.get("contractNum").toString();
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustDetail("+contractNum+");^_self";
				sb.append(contractNum).append(tmp);
		    	
		    	map.put("userNumber", sb.toString());
		    }
			
			if(map.get("agentName")!= null && map.get("userType") != null)
			{   String agentName = map.get("agentName").toString();
				String userType = map.get("userType").toString();
				String vacSeq = map.get("vacSeq").toString();
				StringBuffer sb = new StringBuffer();
				if(!userType.equals("P")){
					String tmp = "^javaScript:goAgentDepositList("+vacSeq+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
					sb.append(agentName).append(tmp);
					map.put("userNumber", sb.toString());
				}
		    	
		    	
		    }

		}

		logger.debug("service  resultList ["+resultList.toString()+"]"); 
	
		return resultList;
	}
	
	/**
	 * 가상계좌입금내역 목록조회엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getRcgVacRechargeMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		//List<?> resultList = new ArrayList<PpsRcgVacVo>();
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("가상계좌번호", "은행명", "이용자구분", "이용자번호", "입금일자", "입금액", "송금자명", "송금은행", "충전결과");
		param.setStrValue("VACCNO","BANKCDNM","USERTYPENM","USERNUMBER","TXDATE","PAYTOTAMT","REQUESTER","PAYBANKNM","ENDCODE");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 1, 0, 0, 0);
		param.setSheetName("가상계좌입금내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
						
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.rcgmgmt.mapper.PpsHdofcRcgMgmtMapper.getRcgVacRechargeMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "가상계좌입금내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 가상계좌 관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getRcgVacInfoMgmtList(Map<String, Object> pRequestParamMap){
		//List<?> resultList = new ArrayList<PpsVacVo>();
		//resultList = rcgMgmtMapper.getRcgVacInfoMgmtList(pRequestParamMap);
		List<EgovMap> resultList = (List<EgovMap>)rcgMgmtMapper.getRcgVacInfoMgmtList(pRequestParamMap);

		for(EgovMap map : resultList){
			
			//logger.debug("map ["+map.toString()+"]");

			
			if(map.get("contractNum") != null )
			{	
				String contractNum = map.get("contractNum").toString();
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustDetail("+contractNum+");^_self";
				sb.append(contractNum).append(tmp);
		    	
		    	map.put("contractNum", sb.toString());
		    }

			if(map.get("agentName")!= null && map.get("userType") != null && map.get("vacId")!= null && !"".equals(map.get("userType")))
			{   String agentName = map.get("agentName").toString();
				String userType = map.get("userType").toString();
				StringBuffer sb = new StringBuffer();
				if(!userType.equals("P")){
					String tmp = "^javaScript:goAgentDeposit("+map.get("vacId")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
					sb.append(agentName).append(tmp);
					map.put("agentName", sb.toString());
				}
		    	
		    	
		    }
			
			if(map.get("resetBtn")!= null && map.get("vacId")!= null && !"".equals(map.get("vacId")))
			{   String resetBtn = map.get("resetBtn").toString();
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goVacCancel("+map.get("vacId")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
				sb.append(resetBtn).append(tmp);
		    	
		    	map.put("resetBtn", sb.toString());
		    }

		}

		logger.debug("service  resultList ["+resultList.toString()+"]"); 
			
		return resultList;
	}
	
	/**
	 * 가상계좌 관리 목록조회엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getRcgVacInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("은행명", "가상계좌", "이용자구분", "계약번호", "대리점명", "상태", "부여일자", "회수일자", "최종입금일자", "수납횟수");
		param.setStrValue("VACBANKNAME","VACID","USERTYPENM","CONTRACTNUM","AGENTNAME","STATUSNM","OPENDATE","CLOSEDATE","LASTPAYMENTDATE","PAYCOUNT");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0,0,1);
		param.setSheetName("가상계좌관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
						
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.rcgmgmt.mapper.PpsHdofcRcgMgmtMapper.getRcgVacInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "가상계좌관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * POS충전내역 목록조회 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getRcgPosRechargeMgmtList(Map<String, Object> pRequestParamMap){
		//List<?> resultList = new ArrayList<PpsRcgPosVo>();
		List<EgovMap> resultList = (List<EgovMap>)rcgMgmtMapper.getRcgPosRechargeMgmtList(pRequestParamMap);

		for(EgovMap map : resultList){
			
			//logger.debug("map ["+map.toString()+"]");

			
			if(map.get("contractNum") != null )
			{	
				String contractNum = map.get("contractNum").toString();
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustDetail("+contractNum+");^_self";
				sb.append(contractNum).append(tmp);
		    	
		    	map.put("contractNum", sb.toString());
		    }
		}

		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		return resultList;
	}
	
	/**
	 * POS충전내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getRcgPosRechargeMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","CTN","요청구분","판매점명","승인번호","응답코드" ,"충전금액","등록일자","취소여부","취소일자");
		param.setStrValue("CONTRACTNUM","SUBSCRIBERNO","REQTYPENM","STORECODENM","AUTHCODE","RETCODENM","AMOUNT","RECORDDATE","CANCELFLAG","CANCELDATE");
		param.setIntWidth(3000, 5000, 5000, 9000, 10000, 5000, 7000, 9000,5000,5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 1, 0,0,0);
		param.setSheetName("POS충전내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
						
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.rcgmgmt.mapper.PpsHdofcRcgMgmtMapper.getRcgPosRechargeMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "POS충전내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 일괄충전 고객조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getRcgBatchSearchMgmtList(Map<String, Object> pRequestParamMap){
		//List<?> resultList = new ArrayList<PpsCustomerVo>();
		
		List<EgovMap> resultList = (List<EgovMap>)rcgMgmtMapper.getRcgBatchSearchMgmtList(pRequestParamMap);

		for(EgovMap map : resultList){
			
			//logger.debug("map ["+map.toString()+"]");

			
			if(map.get("contractNum") != null )
			{	
				String contractNum = map.get("contractNum").toString();
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustDetail("+contractNum+");^_self";
				sb.append(contractNum).append(tmp);
		    	
		    	map.put("contractNumLink", sb.toString());
		    }
		}

		//logger.debug("service  resultList ["+resultList.toString()+"]"); 
		
		return resultList;
	}
	
	
	/**
	 * 일괄충전 목록엑셀저장
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getRcgBatchSearchMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호", "휴대폰번호", "요금제", "최근충전일자", "잔액소진일자", "상태", "개통일자", "정지일자", "만료일자", "개통대리점", "잔액", "충전횟수", "총충전금액");
		param.setStrValue("CONTRACTNUM","SUBSCRIBERNO","SERVICENAME","LASTBASICCHGDT","BASICEMPTDT","SUBSTATUSNAME","LSTCOMACTVDATE","STATUSSTOPDT","BASICEXPIRE","AGENTNAME","BASICREMAINS","RECHARGECNT","RECHARGESUM");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0,0,1,1,1);
		param.setSheetName("일괄충전리스트");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
										
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.rcgmgmt.mapper.PpsHdofcRcgMgmtMapper.getRcgBatchSearchMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "일괄충전리스트_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 일괄충전
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsRcgBatch(Map<String, Object> pRequestParamMap){
		StringBuilder contractNumArr = new StringBuilder();
		String contractNum = "";
		int paramCnt = Integer.parseInt(pRequestParamMap.get("paramCnt").toString());	
		for ( int i=0; i<paramCnt; i++ ){
			contractNum = pRequestParamMap.get("data["+i+"][contractNum]").toString();
			contractNumArr.append(contractNum);
			contractNumArr.append("|");
	    }
		pRequestParamMap.put("contractNumArr", contractNumArr.toString());
		pRequestParamMap.put("reqType", "BS_BATCH_VOICE");
		rcgMgmtMapper.ppsRcgBatch(pRequestParamMap);
		return pRequestParamMap;
	}
	
	/**
	 * 추전취소 처리 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	//@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsRcgCancel(Map<String, Object> pRequestParamMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		rcgMgmtMapper.ppsRcgCancel(pRequestParamMap);
		
		PpsKtInResVo ppsKtInResVo = new PpsKtInResVo();
		ppsKtInResVo = hdofcCommonService.ppsHdofcSleep(pRequestParamMap);
		
		resultMap.put("oResCode", ppsKtInResVo.getoResCode());
		logger.debug(ppsKtInResVo.getoResCode());
		if(ppsKtInResVo.getoResCode().equals("0000")){
			resultMap.put("oResCodeNm", "취소되었습니다.");
		}else{
			resultMap.put("oResCodeNm", ppsKtInResVo.getoResCodeNm());
		}

		logger.debug(resultMap);
		return resultMap;
	}	
	
	
	/**
	 * 가상계좌관리 - 가상계좌회수
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> goPpsRcgVacReset(Map<String, Object> pRequestParamMap){
		
		rcgMgmtMapper.ppsRcgVacReset(pRequestParamMap);
		return pRequestParamMap;
	}
	   
    /**
	 * 대리점정보 얻어옴
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getAgentInfo(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsAgentVo>();
		
		resultList = rcgMgmtMapper.getAgentInfo(pRequestParamMap);
		return resultList;
	}
	
	/**
	 * 대리점예치금조정
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> goPpsDepositModify(Map<String, Object> pRequestParamMap){
		
		pRequestParamMap.put("opCode", "ADD");
		pRequestParamMap.put("depositType", "B");
		rcgMgmtMapper.goPpsDepositModify(pRequestParamMap);
		return pRequestParamMap;
	}
	
	/**
	 * 일괄충전내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getRcgBatchInfoMgmtList(Map<String, Object> pRequestParamMap){
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		List<EgovMap> resultList = (List<EgovMap>)rcgMgmtMapper.getRcgBatchInfoMgmtList(pRequestParamMap);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("adminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);

		return resultList;
	}
	
	/**
	 * 일괄충전내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getRcgBatchInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("번호","제목","요청수","충전금액","마지막처리일자","대기수","성공수" ,"실패수","요청관리자","요청일자");
		param.setStrValue("BATCHSEQ","TITLE","REQCNT","AMOUNT","LASTBATCHDT","DELAYCNT","SUCCCNT","FAILCNT","ADMINNM","RECDT");
		param.setIntWidth(5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(1, 0, 1, 1, 0, 1, 1, 1, 0, 0);
		param.setSheetName("일괄충전내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.rcgmgmt.mapper.PpsHdofcRcgMgmtMapper.getRcgBatchInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "일괄충전내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * ATM충전내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getRcgFtpAtmRcgInfoMgmtList(Map<String, Object> pRequestParamMap){
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		List<EgovMap> resultList = (List<EgovMap>)rcgMgmtMapper.getRcgFtpAtmRcgInfoMgmtList(pRequestParamMap);

		for(EgovMap map : resultList){
			
		    if(map.get("contractNum") != null && !"".equals(map.get("cancelBtn")))
			{	
				String contractNum = map.get("contractNum").toString();
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustDetail("+contractNum+");^_self";
				sb.append(contractNum).append(tmp);
		    	
		    	map.put("contractNum", sb.toString());
		    }
		    
		}

		return resultList;
	}
	
	/**
	 * 충전내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getRcgFtpAtmRcgInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("거래일시", "계약번호", "CTN", "결제수단", "충전금액", "상태", "응답코드", "응답코드명", "이전잔액", "잔액", "이전만료일", "만료일", "일련번호", "거래번호", "기기번호", "설치점명", "내부처리결과", "등록일");
		param.setStrValue("TR_TIME", "CONTRACT_NUM", "SUBSCRIBER_NO", "PAY_CODE_NM", "REFILL_CHARGE", "STATUS_NM", "R_CODE", "R_CODE_NM", "PRE_BALANCE", "POST_BALANCE", "PRE_BAL_DATE", "POST_BAL_DATE", "PIN_NO", "TR_NO", "NH_CODE", "CA_NAME", "PROC_RET_MSG", "RECORD_DATE");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0,0,0,0,0,0,0);
		param.setSheetName("ATM충전내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.rcgmgmt.mapper.PpsHdofcRcgMgmtMapper.getRcgFtpAtmRcgInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "ATM충전내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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

	public String getCurrentTimes()
	{
		String currDateTime = "";
		
		Date currtentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
		currDateTime = "_"+sdfFileName.format(currtentDate);
		
		
		return currDateTime;
	}
	
	
	
	
    

}
