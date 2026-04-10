package com.ktis.msp.pps.agentstmmgmt.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.pps.agentstmmgmt.mapper.PpsAgencyStmMgmtMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @param <PpsAgencyStmMgmtMapper>
 * @Class Name : PpsAgencyStmMgmtService
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
public class PpsAgencyStmMgmtService extends ExcelAwareService  {
//	@Autowired
//	private PpsHdofcCommonService  hdofcCommonService;
	
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
	private PpsAgencyStmMgmtMapper stmMgmtMapper;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;
	
	
	/**
	 * 대리점 정산내역 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getAgentStmHistoryMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getAgentStmHistoryMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 대리점 정산내역 Excel
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getAgentStmHistoryMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("정산월","대리점","기본(카드)","기본(현금)","유심(카드)","유심(현금)","GRADE(카드)","GRADE(현금)","우량(카드)","우량(현금)","명변(카드)","명변(현금)","재충전(카드)","재충전(현금)","자동이체(카드)","자동이체(현금)","무료충전환수액","환수(카드)","환수(현금)","조정(카드)","조정(현금)","수수료조정(카드)","수수료조정(현금)","합계(카드)","합계(현금)","메모","등록관리자","등록일자");
		param.setStrValue("BILL_MONTH","AGENT_NM","BASIC_CARD_SD","BASIC_CASH_SD","USIM_CARD_SD","USIM_CASH_SD","GRADE_CARD_SD","GRADE_CASH_SD","UL_CARD_SD","UL_CASH_SD","MB_CARD_SD","MB_CASH_SD","RCG_CARD_SD","RCG_CASH_SD","CMS_CARD_SD","CMS_CASH_SD","REFUND_FREE_RCG_AMT","REFUND_CARD_SD","REFUND_CASH_SD","MOD_CARD_SD","MOD_CASH_SD","MOD_AGENT_CARD_SD","MOD_AGENT_CASH_SD","CARD_SD","CASH_SD","REMARK","REG_ADMIN_NM","REG_DT");
		param.setIntWidth(5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		param.setSheetName("대리점정산");
		param.setExcelPath(path);
		param.setFileName("test");
		
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
			
		File f = makeBigDataExcel("com.ktis.msp.pps.agentstmmgmt.mapper.PpsAgencyStmMgmtMapper.getAgentStmHistoryMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "대리별정산_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 대리점정산관리 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getAgentStmSelfMgmtList(Map<String, Object> pRequestParamMap){

		List<EgovMap> resultList = (List<EgovMap>) stmMgmtMapper.getAgentStmSelfMgmtList(pRequestParamMap);
		
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
	
	
	

}
