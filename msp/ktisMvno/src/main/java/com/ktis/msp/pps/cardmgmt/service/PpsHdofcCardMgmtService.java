package com.ktis.msp.pps.cardmgmt.service;



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
import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.cardmgmt.mapper.PpsCardHandler;
import com.ktis.msp.pps.cardmgmt.mapper.PpsHdofcCardMgmtMapper;
import com.ktis.msp.pps.cardmgmt.vo.PpsPinSummaryOpenVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsAgentVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsPinInfoVo;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @param <PpsHdofcCardMgmtMapper>
 * @Class Name : PpsHdofcCardMgmtService
 * @Description : 선불
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */

@Service
public class PpsHdofcCardMgmtService extends ExcelAwareService {

	
	
	 @Autowired
	  protected EgovPropertyService propertyService;

	@Autowired
	private PpsHdofcCardMgmtMapper cardMgmtMapper;
	
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
    private FileDownService  fileDownService;
	
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private MaskingMapper maskingMapper;

	/**
	 * 핀정보 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	@Crypto(decryptName="DBMSDec", fields = {"pinNumber"})
	public List<?> getPinInfoMgmtList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<PpsPinInfoVo>();
		
		
		
		List<EgovMap> resultList = (List<EgovMap>)cardMgmtMapper.getPinInfoMgmtList(pRequestParamMap);
		for( EgovMap map : resultList){
			String mngCodeStr = map.get("mngCode").toString();
			if(!StringUtils.isBlank(map.get("mngCode").toString()))
		    	
		    {
		    	StringBuffer sb = new StringBuffer();
		    	String tmp = "^javaScript:goPinInfoData("+mngCodeStr+");^_self";
		    	  sb.append(mngCodeStr).append(tmp);
		    	
		    	map.put("mngCode", sb.toString());
		    }
			String contracNum = map.get("contractNum").toString();
			if(!StringUtils.isBlank(map.get("contractNum").toString())&& !contracNum.equals("0") )
			{
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustInfoData("+contracNum+");^_self";
		    	  sb.append(contracNum).append(tmp);
			}
			
			
			
			
			
			
			
			
		}
		
		

		return resultList;
	}

	/**
	 * 핀정보 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public void getPinInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("관리번호", "PIN구분", "ON/OFF구분", "핀상태", "생성일자", "출고일자", "개통일자", "충전일자", "액면가격", "유통가격", "만료일자", "계약번호","카드구분");
		param.setStrValue("MNG_CODE","PIN_GUBUN_NM","ONOFF_GUBUN_NM","STATUS_NM","CR_DATE","OUT_DATE","OPEN_DATE","RCG_DATE","START_PRICE","OUT_PRICE","EXPIRE_DATE","CONTRACT_NUM","FREE_FLAG_NM");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0,0);
		param.setSheetName("PIN관리");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		PpsCardHandler handler  = new PpsCardHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.cardmgmt.mapper.PpsHdofcCardMgmtMapper.getPinInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "PIN관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 핀생성 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getPinCreateMgmtList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<PpsPinSummaryCreateVo>();

		List<EgovMap> resultList = (List<EgovMap>)cardMgmtMapper.getPinCreateMgmtList(pRequestParamMap);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("crAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		for( EgovMap map : resultList){
			String mngCodeStr = map.get("mngCodeStr").toString();
		    if(!StringUtils.isBlank(map.get("mngCodeStr").toString()))
		    	
		    {
		    	StringBuffer sb = new StringBuffer();
		    	String tmp = "^javaScript:goPinInfoExcel("+map.get("crSeq")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
		    	  sb.append(mngCodeStr).append(tmp);
		    	
		    	map.put("mngCodeStr", sb.toString());
		    }
		    
		    String cancelFlagNm = map.get("cancelFlagNm").toString();
		    if(!cancelFlagNm.equals("-") && !cancelFlagNm.equals("취소완료")){
				//String lineNum = map.get("rownum").toString();
				cancelFlagNm = "취소 ^javascript:goPinCreateCancel("+map.get("crSeq")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
				map.put("cancelFlagNm", cancelFlagNm);
				
		    }
	    
		}

		return resultList;
	}

	/**
	 * 핀생성 목록조회엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public void getPinCreateMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("PIN관리번호", "PIN구분", "ON/OFF구분", "PIN길이", "PIN개수", "등록관리자", "등록일자", "PIN금액", "취소여부", "취소일자", "비고");
		param.setStrValue("MNG_CODE_STR","PIN_GUBUN_NM","ONOFF_GUBUN_NM","PIN_LENGTH","CR_COUNT","CR_ADMIN_NM","CR_DATE","START_PRICE","CANCEL_FLAG_NM", "CANCEL_DATE","REMARK");
		param.setIntWidth(10000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 1, 1, 0, 0,1, 0, 0, 0);
		param.setSheetName("PIN생성관리");
		param.setExcelPath(path);
		param.setFileName("test");

		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.cardmgmt.mapper.PpsHdofcCardMgmtMapper.getPinCreateMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "PIN생성관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 핀 개통 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getPinOpenMgmtList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<PpsPinSummaryOpenVo>();

		List<EgovMap> resultList = (List<EgovMap>)cardMgmtMapper.getPinOpenMgmtList(pRequestParamMap);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("openAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		   for(EgovMap map : resultList){
			   
			    String agentType = map.get("openAgentType").toString();
			    if(agentType.equals("D")){
			    	 
			    	map.put("openAgentId" , map.get("openDealerId"));
			    	map.put("openAgentNm", map.get("openDealerNm"));
			    	 
			    }
			     
			    String cancelFlagNm = map.get("cancelFlagNm").toString();
				   
			    if(!cancelFlagNm.equals("-") && !cancelFlagNm.equals("취소완료")){
						//String lineNum = map.get("rownum").toString();
						cancelFlagNm = "취소 ^javascript:goPinOpenCancel("+map.get("openSeq")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
						map.put("cancelFlagNm", cancelFlagNm);
						
				}
		     	String updNmStr = map.get("updNmStr").toString();
				if(!updNmStr.equals("-")){
					//String lineNum = map.get("rownum").toString();
					updNmStr = "수정 ^javascript:goPinOpenUpdate("+map.get("openSeq")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
					map.put("updNmStr", updNmStr);
					
				}
				
				String mngCodeStr = map.get("mngCodeStr").toString();
			    if(!StringUtils.isBlank(map.get("mngCodeStr").toString()))
			    	
			    {
			    	StringBuffer sb = new StringBuffer();
			    	String tmp = "^javaScript:goPinInfoExcel("+map.get("openSeq")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
			    	  sb.append(mngCodeStr).append(tmp);
			    	
			    	map.put("mngCodeStr", sb.toString());
			    }
			     
			     
			     
			     
			   
		   }

		 logger.debug("service  resultList ["+resultList.toString()+"]");  	
		return resultList;
	}

	/**
	 * 핀개통 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public void getPinOpenMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("관리번호", "출고일자", "개통개수", "개통일자", "액면가액합계", "출고가격합계", "개통대리점구분", "대리점",  "수납금액", "수납여부", "수납일자", "개통처리관리자");
		param.setStrValue("MNG_CODE_STR","OUT_DATE","OPEN_COUNT","OPEN_DATE","PIN_CHARGE_SUM","AGENT_CHARGE_SUM","OPEN_AGENT_TYPE_NM","OPEN_AGENT_NM","PAY_AMT","PAY_FLAG","PAY_DATE","OPEN_ADMIN_NM");
		param.setIntWidth(10000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0);
		param.setSheetName("PIN개통관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);			
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.cardmgmt.mapper.PpsHdofcCardMgmtMapper.getPinOpenMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "PIN개통관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 핀 출고 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getPinReleaseMgmtList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<PpsPinSummaryOutVo>();

		List<EgovMap> resultList = (List<EgovMap>)cardMgmtMapper.getPinReleaseMgmtList(pRequestParamMap);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("outAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		for(EgovMap map : resultList){
			
					//logger.debug("map ["+map.toString()+"]");
			
			String agentType = map.get("outAgentType").toString();
			if(agentType.equals("D"))
			{
				 map.put("outAgentId" , map.get("outDealerId"));
		    	 map.put("outAgentNm", map.get("outDealerNm"));
			}
			
			String cancelFlagNm = map.get("cancelFlagNm").toString();
			
			if((map.get("cancelFlagNm") != null && !"".equals(map.get("cancelFlagNm"))) && (!map.get("cancelFlagNm").toString().equals("취소완료") )){
				//String lineNum = map.get("rownum").toString();
				
				
				StringBuffer sb = new StringBuffer();
				String tmp = "^javascript:goPinOutCancel("+map.get("outSeq")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
				sb.append(cancelFlagNm).append(tmp);
				      
					map.put("cancelFlagNm", sb.toString());
				
			}
			
			String mngCodeStr = map.get("mngCodeStr").toString();
		    if(!StringUtils.isBlank(map.get("mngCodeStr").toString()))
		    	
		    {
		    	StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goPinInfoExcel("+map.get("outSeq")+","+pRequestParamMap.get("pageSize")+","+pRequestParamMap.get("pageIndex")+");^_self";
		    	  sb.append(mngCodeStr).append(tmp);
		    	
		    	map.put("mngCodeStr", sb.toString());
		    }
			
		}

		logger.debug("service  resultList ["+resultList.toString()+"]"); 
		return resultList;
	}

	/**
	 * 핀 출고 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public void getPinReleaseMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("구분", "대리점/카드대리점", "대리점명/카드대리점명", "관리번호", "관리자", "출고수량", "개통수량", "출고일자", "비고","카드구분");
		param.setStrValue("OUT_AGENT_TYPE_NM","OUT_AGENT_ID","OUT_AGENT_NM","MNG_CODE_STR","OUT_ADMIN_NM","OUT_COUNT","OPEN_COUNT","OUT_DATE","REMARK","FREE_FLAG_NM");
		param.setIntWidth(3000, 5000, 5000, 10000, 5000, 5000, 5000, 5000, 5000,5000);
		param.setIntLen(0, 0, 0, 0, 0, 1, 1, 0, 0,0);
		param.setSheetName("PIN출고관리");
		param.setExcelPath(path);
		param.setFileName("test");

		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.cardmgmt.mapper.PpsHdofcCardMgmtMapper.getPinReleaseMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "선불PIN출고관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 핀 정보조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	@Crypto(decryptName="DBMSDec", fields = {"pinNumber"})
	public PpsPinInfoVo getPpsPinInfoData(Map<String, Object> pRequestParamMap) {

		PpsPinInfoVo ppsPinInfoVo = new PpsPinInfoVo();

		ppsPinInfoVo = cardMgmtMapper.getPpsPinInfoData(pRequestParamMap);
		 String retCode ="-300";
		 String retMsg = "데이터를 조회하는 동안 오류가 발생하였습니다.";
		if(ppsPinInfoVo== null){
			retCode ="-200";
			retMsg = "해당 데이터가 없습니다.";
		}else if(ppsPinInfoVo!=null && !StringUtils.isBlank(ppsPinInfoVo.getMngCode()) ){

			retCode ="000";
			retMsg = "";
		}

		 ppsPinInfoVo.setRetCode(retCode);
		 ppsPinInfoVo.setRetMsg(retMsg);


		return ppsPinInfoVo;
	}

	/**
	 * 핀생성 등록 처리
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsPinInfoCreate(Map<String, Object> pRequestParamMap) {


		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("opCode", pRequestParamMap.get("opCode"));
		resultMap.put("crSeq", pRequestParamMap.get("crSeq"));
		resultMap.put("pinGubun", pRequestParamMap.get("pinGubun"));

		resultMap.put("onoffGubun", pRequestParamMap.get("onoffGubun"));
		resultMap.put("pinLength", pRequestParamMap.get("pinLength"));

		resultMap.put("crCount", pRequestParamMap.get("crCount"));
		//resultMap.put("crAdminId", pRequestParamMap.get("crAdminId"));
		resultMap.put("crAdminId",pRequestParamMap.get("adminId"));
		resultMap.put("startPrice", pRequestParamMap.get("startPrice"));
		resultMap.put("remark", pRequestParamMap.get("remark"));


		resultMap.put("oRetCode","0000" );
		resultMap.put("oRetMsg","" );


		cardMgmtMapper.getPpsPinInfoCreate(resultMap);

		logger.debug(resultMap.get("oRetCode").toString());
		if(StringUtils.isBlank(resultMap.get("oRetCode").toString())){
			resultMap.put("oRetCode", "0000");
		}
		pRequestParamMap.put("retCode",  resultMap.get("oRetCode"));
		pRequestParamMap.put("retMsg",  resultMap.get("oRetMsg"));
		return pRequestParamMap;
	}

	/**
	 *  핀 출고 처리
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsPinOutProc(Map<String, Object> pRequestParamMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String opCode =pRequestParamMap.get("opCodeStr").toString();
		if(!StringUtils.isBlank(opCode)){
			resultMap.put("opCode", pRequestParamMap.get("opCodeStr"));


					String startCode = "000000000"+pRequestParamMap.get("startMngCode");
					String endCode = "000000000"+pRequestParamMap.get("endMngCode");
					StringBuilder startMngCode = new StringBuilder();
					startMngCode.append(pRequestParamMap.get("manageCodeHeader"));
				    startMngCode.append(startCode.substring(startCode.length()-9));
				    resultMap.put("startMngCode", startMngCode.toString());

				    StringBuilder endMngCode = new StringBuilder();
					endMngCode.append(pRequestParamMap.get("manageCodeHeader"));
				    endMngCode.append(endCode.substring(endCode.length()-9));
				    resultMap.put("endMngCode", endMngCode.toString());

					resultMap.put("outDate", pRequestParamMap.get("outDate"));
					resultMap.put("outAgentType", pRequestParamMap.get("outAgentType"));
					resultMap.put("agentId", pRequestParamMap.get("agentId"));
					resultMap.put("adminId", pRequestParamMap.get("adminId"));
					resultMap.put("payAmt", pRequestParamMap.get("payAmt"));
					resultMap.put("payFlag", pRequestParamMap.get("payFlag"));
					resultMap.put("payDate", pRequestParamMap.get("payDate"));
					resultMap.put("remark", pRequestParamMap.get("remark"));
					resultMap.put("reqOutCount", pRequestParamMap.get("reqOutCount"));
					resultMap.put("outSeq", pRequestParamMap.get("outSeq") );
					resultMap.put("freeFlag", pRequestParamMap.get("freeFlag") );

					resultMap.put("oRetOutCount","" );
					resultMap.put("oRetTotalCharge","" );
					resultMap.put("oRetAgentCharge","" );
					resultMap.put("oRetCode","" );
					resultMap.put("oRetMsg","" );


					cardMgmtMapper.getPpsPinOutProc(resultMap);







		}







		return resultMap;
	}

	/**
	 * 핀 개통처리
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsPinOpenProc(Map<String, Object> pRequestParamMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(pRequestParamMap.get("opCode").toString().equals("OPEN") || pRequestParamMap.get("opCode").toString().equals("CHECK")){
			String startMngCode = pRequestParamMap.get("startMngCode").toString();
			StringBuilder sb = new StringBuilder();
			String endMngCode = pRequestParamMap.get("endMngCode").toString();
			String manageCodeHeader = pRequestParamMap.get("manageCodeHeader").toString();

    	    sb.append("000000000");
    	    sb.append(startMngCode);
    	  	              
	        startMngCode = manageCodeHeader+sb.toString().substring(sb.toString().length()-9, sb.toString().length());
	        resultMap.put("startMngCode", startMngCode);
	        StringBuilder sb2 = new StringBuilder();

	        sb2.append("000000000");
    	    sb2.append(endMngCode);
	        endMngCode = manageCodeHeader+sb2.toString().substring(sb2.toString().length()-9, sb2.toString().length());
	        resultMap.put("endMngCode", endMngCode);
		}else{
			resultMap.put("startMngCode", pRequestParamMap.get("startMngCode"));
			resultMap.put("endMngCode", pRequestParamMap.get("endMngCode"));
		}
		
		
		resultMap.put("opCode", pRequestParamMap.get("opCode"));
		resultMap.put("openSeq", pRequestParamMap.get("openSeq"));
		resultMap.put("openDate", pRequestParamMap.get("openDate"));
		resultMap.put("openAgentType", pRequestParamMap.get("openAgentType"));
		resultMap.put("agentId", pRequestParamMap.get("agentId"));
		resultMap.put("adminId", pRequestParamMap.get("adminId"));
		resultMap.put("payAmt", pRequestParamMap.get("payAmt"));
		resultMap.put("payFlag", pRequestParamMap.get("payFlag"));
		resultMap.put("payDate", pRequestParamMap.get("payDate"));
		resultMap.put("remark", pRequestParamMap.get("remark"));
		resultMap.put("reqOpenCount", pRequestParamMap.get("reqOpenCount"));
		resultMap.put("freeFlag", pRequestParamMap.get("freeFlag") );

		resultMap.put("oRetOpenCount","" );
		resultMap.put("oRetTotalCharge","" );
		resultMap.put("oRetAgentCharge","" );
		resultMap.put("oRetCode","" );
		resultMap.put("oRetMsg","" );


		cardMgmtMapper.getPpsPinOpenProc(resultMap);



		return resultMap;
	}

	/**
	 * 생성 출고 개통시 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public void getPinInfListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("관리번호", "PIN번호", "PIN구분", "ON/OFF구분", "핀상태", "생성일자", "출고일자", "개통일자", "충전일자", "액면가격", "유통가격", "만료일자", "계약번호");
		param.setStrValue("MNG_CODE","PIN_NUMBER","PIN_GUBUN_NM","ONOFF_GUBUN_NM","STATUS_NM","CR_DATE","OUT_DATE","OPEN_DATE","RCG_DATE","START_PRICE","OUT_PRICE","EXPIRE_DATE","CONTRACT_NUM");
		param.setIntWidth(8000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0);
		param.setSheetName("PIN개별정보");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		PpsCardHandler handler  = new PpsCardHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.cardmgmt.mapper.PpsHdofcCardMgmtMapper.getPinInfListExcel", pRequestParamMap, param, handler);
		String fileName = "선불PIN개별정보_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 핀관리코드 해더목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getMngCodeHeaderList(Map<String, Object> pRequestParamMap) {
		List<?> resultList = new ArrayList<Map<String, Object>>();

		if("CREATE".equals(pRequestParamMap.get("tblNm").toString())) {
			resultList = cardMgmtMapper.getMngCodeHeaderList();
		}

		return resultList;
	}

	/**
	 * 핀개통정보 조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public PpsPinSummaryOpenVo getPpsPinOpenSummaryInfo(Map<String, Object> pRequestParamMap) {

		PpsPinSummaryOpenVo pinSummaryOpenVo = new PpsPinSummaryOpenVo();


		pinSummaryOpenVo = cardMgmtMapper.getPpsPinOpenSummaryInfo(pRequestParamMap);



		 String retCode ="-300";
		 String retMsg = "데이터를 조회하는 동안 오류가 발생하였습니다.";
		if(pinSummaryOpenVo== null){
			retCode ="-200";
			retMsg = "해당 데이터가 없습니다.";
		}else if(pinSummaryOpenVo!=null && !StringUtils.isBlank(pinSummaryOpenVo.getStartMngCode()) ){

			retCode ="0000";
			retMsg = "";
		}

		pinSummaryOpenVo.setRetCode(retCode);
		pinSummaryOpenVo.setRetMsg(retMsg);


		return pinSummaryOpenVo;
	}
	
	/**
	 * 핀생성통계 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getPinCreateStatsMgmtList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<PpsPinSummaryCreateVo>();

		List<EgovMap> resultList = (List<EgovMap>)cardMgmtMapper.getPinCreateStatsMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 핀생성통계 목록조회엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public void getPinCreateStatsMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("생성관리자", "생성월", "생성수량", "생성금액", "생성취소수량", "생성취소금액");
		param.setStrValue("CR_ADMIN_NM","CR_DATE","CR_CNT","CR_AMT","CR_CAN_CNT","CR_CAN_AMT");
		param.setIntWidth(5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 1, 1, 1, 1);
		param.setSheetName("PIN생성통계");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		PpsCardHandler handler  = new PpsCardHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.cardmgmt.mapper.PpsHdofcCardMgmtMapper.getPinCreateStatsMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "PIN생성통계_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 핀개통통계 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getPinOpenStatsMgmtList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<PpsPinSummaryCreateVo>();

		List<EgovMap> resultList = (List<EgovMap>)cardMgmtMapper.getPinOpenStatsMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 핀개통통계 목록조회엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public void getPinOpenStatsMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("개통관리자", "개통월", "개통수량", "개통금액", "판매금액", "개통취소수량", "개통취소금액");
		param.setStrValue("OP_ADMIN_NM","OP_DATE","OP_CNT","OP_AMT","SL_AMT","OP_CAN_CNT","OP_CAN_AMT");
		param.setIntWidth(5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 1, 1, 1, 1, 1);
		param.setSheetName("PIN개통통계");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		PpsCardHandler handler  = new PpsCardHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.cardmgmt.mapper.PpsHdofcCardMgmtMapper.getPinOpenStatsMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "PIN개통통계_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 핀현황통계 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getPinInfoStatsMgmtList(Map<String, Object> pRequestParamMap) {
		//List<?> resultList = new ArrayList<PpsPinSummaryCreateVo>();

		List<EgovMap> resultList = (List<EgovMap>)cardMgmtMapper.getPinInfoStatsMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 핀현황통계 목록조회엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public void getPinInfoStatsMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("요금제","가입자수","생성개수","미출고","출고","개통","충전","정지","폐기","기타","유통수량","생성금액","미출고금액","출고금액","개통금액","충전금액","정지금액","폐기금액","기타","유통중인카드");
		param.setStrValue("START_PRICE","CR_DATE","TOT_CNT","CR_CNT","OT_CNT","OP_CNT","RE_CNT","ST_CNT","CL_CNT","ETC_CNT","SL_CNT","TOT_AMT","CR_AMT","OT_AMT","OP_AMT","RE_AMT","ST_AMT","CL_AMT","ETC_AMT","SL_AMT");
		param.setIntWidth(5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
		param.setSheetName("PIN현황통계");
		param.setExcelPath(path);
		param.setFileName("test");
			
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		PpsCardHandler handler  = new PpsCardHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.cardmgmt.mapper.PpsHdofcCardMgmtMapper.getPinInfoStatsMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "PIN현황통계_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 대리점 정보 조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public PpsAgentVo getAgentInfo(Map<String, Object> pRequestParamMap) {
		
		
		PpsAgentVo ppsAgentVo = new PpsAgentVo();
		
		ppsAgentVo= cardMgmtMapper.getAgentInfo(pRequestParamMap);
		
		return ppsAgentVo;
		
		
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
