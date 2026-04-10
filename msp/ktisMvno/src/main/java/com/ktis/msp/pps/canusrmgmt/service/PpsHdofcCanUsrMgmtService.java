package com.ktis.msp.pps.canusrmgmt.service;



import java.io.File;
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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.canusrmgmt.mapper.PpsHdofcCanUsrMgmtMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @param <PpsHdofcCanUsrMgmtMapper>
 * @Class Name : PpsHdofcCanUsrMgmtService
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
public class PpsHdofcCanUsrMgmtService extends ExcelAwareService {

	
	
	 @Autowired
	  protected EgovPropertyService propertyService;

	@Autowired
	private PpsHdofcCanUsrMgmtMapper canUsrMgmtMapper;
	
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
    private FileDownService  fileDownService;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
	private MaskingService maskingService;

	/**
	 * 해지조회 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getCanUsrMgmtList(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = (List<EgovMap>)canUsrMgmtMapper.getCanUsrMgmtList(pRequestParamMap);
		for( EgovMap map : resultList){
			String contracNum = map.get("contractNum").toString();
			if(!StringUtils.isBlank(map.get("contractNum").toString())&& !contracNum.equals("0") )
			{
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustInfoData("+contracNum+");^_self";
		    	  sb.append(contracNum).append(tmp);
		    	  map.put("contractNumStr",sb.toString());
			}
		}
		
		return resultList;
	}
	
	/**
	 * 해지조회내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getCanUsrQueueMgmtList(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = (List<EgovMap>)canUsrMgmtMapper.getCanUsrQueueMgmtList(pRequestParamMap);
		for( EgovMap map : resultList){
			String contracNum = map.get("contractNum").toString();
			if(!StringUtils.isBlank(map.get("contractNum").toString())&& !contracNum.equals("0") )
			{
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustInfoData("+contracNum+");^_self";
		    	  sb.append(contracNum).append(tmp);
		    	  map.put("contractNumStr",sb.toString());
			}
		}
		
		return resultList;
	}
	
	/**
	 * 해지대상자조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public void getCanUsrMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","CTN","고객명","요금제","최근충전일","잔액소진일","상태","개통일","정지일","해지일","잔액","만료일","엠만료일","충전가능여부","대리점","판매점","서류");
		param.setStrValue("CONTRACT_NUM","SUBSCRIBER_NO","SUB_LINK_NAME","SERVICE_NM","LAST_BASIC_CHG_DT","BASIC_EMPT_DT_STR","SUB_STATUS_NM","LST_COM_ACTV_DATE","STATUS_STOP_DT","STATUS_CANCEL_DT","BASIC_REMAINS","BASIC_EXPIRE","MVNO_BASIC_EXPIRE","RECHARGE_FLAG","AGENT_NM","AGENT_SALE_NM","PAPER_IMAGE");
		param.setIntWidth(3000, 5000, 5000, 5000,5000,5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,5000);
		param.setIntLen(0, 0, 0, 0,0,0, 0, 0, 0, 0, 1, 0, 0, 0, 0,0,1);
		param.setSheetName("해지대상자조회");
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);	
			
		File f = makeBigDataExcel("com.ktis.msp.pps.canusrmgmt.mapper.PpsHdofcCanUsrMgmtMapper.getCanUsrMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "해지대상자조회_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 직권해지처리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public void getCanUsrQueueMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","CTN","고객명","요금제","요청구분","처리결과","최근충전일","잔액소진일","상태","개통일","정지일","해지일","잔액","만료일","엠만료일","대리점","판매점","결과메세지","요청일자","요청자","처리일자","처리자","사유");
		param.setStrValue("CONTRACT_NUM","SUBSCRIBER_NO","SUB_LINK_NAME","SERVICE_NM","REQ_GUBUN_NM","STATUS_NM","LAST_BASIC_CHG_DT","BASIC_EMPT_DT","SUB_STATUS_NM","OPEN_DATE","STATUS_STOP_DT","STATUS_CANCEL_DT","BASIC_REMAINS","BASIC_EXPIRE","MVNO_BASIC_EXPIRE","AGENT_NM","AGENT_SALE_NM","RET_MSG","REQ_DT","REQ_NM","CONFIRM_DT","CONFIRM_NM","REMARK");
		param.setIntWidth(3000, 5000, 5000, 5000,5000,5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0, 0, 0, 0,0,0, 0, 0, 0, 0, 0, 0, 1, 0, 0,0,0,0,0,0,0,0,0);
		param.setSheetName("직권해지처리");
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);	
			
		File f = makeBigDataExcel("com.ktis.msp.pps.canusrmgmt.mapper.PpsHdofcCanUsrMgmtMapper.getCanUsrQueueMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "직권해지처리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 해지결과 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	public List<?> getCanUsrStatsMgmtList(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = (List<EgovMap>)canUsrMgmtMapper.getCanUsrStatsMgmtList(pRequestParamMap);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("confirmNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 해지결과 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public void getCanUsrStatsMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		ExcelParam param = new ExcelParam();
		param.setStrHead("그룹번호","요청건수","해지요청","취소","전송성공","전송실패","해지성공","해지실패","요청관리자","요청일자","메모");
		param.setStrValue("GROUP_SEQ","CONFIRM_CNT","ING_CNT","CAN_CNT","S1_CNT","F1_CNT","S2_CNT","F2_CNT","CONFIRM_NM","CONFIRM_DT","REMARK");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0);
		param.setSheetName("해지결과");
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);	
			
		File f = makeBigDataExcel("com.ktis.msp.pps.canusrmgmt.mapper.PpsHdofcCanUsrMgmtMapper.getCanUsrStatsMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "해지결과_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 *  해지요청
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsCanUsrQueue(Map<String, Object> pRequestParamMap) throws InterruptedException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(pRequestParamMap.get("opCode").equals("DEL")){
			StringBuilder sb = new StringBuilder();
			String seq = "";
			
			for(int i=0; i < Integer.parseInt(pRequestParamMap.get("del_cnt").toString()); i++){
				seq = pRequestParamMap.get("data["+i+"][seq]").toString();
				sb.append(seq);
				
				if(i+1 != Integer.parseInt(pRequestParamMap.get("del_cnt").toString())){
					sb.append("|");
				}
			}
			
			pRequestParamMap.put("param", sb.toString());
			
		}else if(pRequestParamMap.get("opCode").equals("CONFIRM")){
			StringBuilder sb = new StringBuilder();
			String seq = "";
			
			for(int i=0; i < Integer.parseInt(pRequestParamMap.get("confirm_cnt").toString()); i++){
				seq = pRequestParamMap.get("data["+i+"][seq]").toString();
				sb.append(seq);
				
				if(i+1 != Integer.parseInt(pRequestParamMap.get("confirm_cnt").toString())){
					sb.append("|");
				}
			}
			
			pRequestParamMap.put("param", sb.toString());
			
		}
		
		canUsrMgmtMapper.getPpsCanUsrQueue(pRequestParamMap);
		resultMap.put("oRetCd", pRequestParamMap.get("oRetCode"));
		resultMap.put("oRetMsg", pRequestParamMap.get("oRetMsg"));
		
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

}
