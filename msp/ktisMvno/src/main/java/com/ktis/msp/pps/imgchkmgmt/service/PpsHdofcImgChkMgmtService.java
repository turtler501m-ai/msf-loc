package com.ktis.msp.pps.imgchkmgmt.service;


import com.ktds.crypto.annotation.Crypto;
import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.hdofccustmgmt.service.CustEncService;
import com.ktis.msp.pps.hdofccustmgmt.service.PpsHdofcCommonService;
import com.ktis.msp.pps.imgchkmgmt.mapper.PpsHdofcImgChkMgmtMapper;
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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @param <PpsHdofcImgChkMgmtMapper>
 * @Class Name : PpsImgChkMgmtService
 * @Description : 검수관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */


@Service
public class PpsHdofcImgChkMgmtService  extends ExcelAwareService {

	@Autowired
	private PpsHdofcImgChkMgmtMapper  ppsImgChkMgmtMapper;

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
	 * 본사-검수관리 검수관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Crypto(decryptName="DBMSDec", fields = {"userSsn"})
	public List<?> getCustImgChkMngList(Map<String, Object> pRequestParamMap) {

		
		List<EgovMap> resultList = new ArrayList<EgovMap>();

		if("".equals(pRequestParamMap.get("pageCode")) || pRequestParamMap.get("pageCode") == null){
			resultList = (List<EgovMap>)ppsImgChkMgmtMapper.getCustImgChkMngList(pRequestParamMap);
			
			for( EgovMap map : resultList  ){
				  
				
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
		}else if(pRequestParamMap.get("pageCode").equals("ALL")){
			pRequestParamMap.put("getColumn", "3");
			pRequestParamMap.put("rownum", 1);
			resultList = (List<EgovMap>)ppsImgChkMgmtMapper.getCustImgChkGetRow(pRequestParamMap);
			
		}else if(pRequestParamMap.get("pageCode").equals("ONE")){
			pRequestParamMap.put("getColumn", "3");
			resultList = (List<EgovMap>)ppsImgChkMgmtMapper.getCustImgChkGetRow(pRequestParamMap);
		}else if(pRequestParamMap.get("pageCode").equals("NEXT")){
			pRequestParamMap.put("getColumn", "2");
			int rownum = ppsImgChkMgmtMapper.getCustImgChkGetRownum(pRequestParamMap);
			pRequestParamMap.put("rownum", rownum + 1);
			pRequestParamMap.put("seq", "");
			pRequestParamMap.put("getColumn", "3");
			resultList = (List<EgovMap>)ppsImgChkMgmtMapper.getCustImgChkGetRow(pRequestParamMap);
		}else if(pRequestParamMap.get("pageCode").equals("BEFORE")){
			pRequestParamMap.put("getColumn", "2");
			int rownum = ppsImgChkMgmtMapper.getCustImgChkGetRownum(pRequestParamMap);
			if(rownum -1 == 0){
				pRequestParamMap.put("rownum", -1);
			}else{
				pRequestParamMap.put("rownum", rownum - 1);
			}
			pRequestParamMap.put("seq", "");
			pRequestParamMap.put("getColumn", "3");
			resultList = (List<EgovMap>)ppsImgChkMgmtMapper.getCustImgChkGetRow(pRequestParamMap);
		}

		return resultList;
	}
	
	/**
	 * 본사-검수관리 검수관리목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public void getCustImgChkMngExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("검수등록번호","계약번호","개통번호","업무구분","개통일","개통대리점","검수등록일","검수현황","개통신청서","신분증","기타","개통서류","신분증","서명","기타","검수메모","등록자","처리자","처리일");
		param.setStrValue("EVENT_SEQ","CONTRACT_NUM","SUBSCRIBER_NO","MNP_IND_CD_NM","LST_COM_ACTV_DATE","AGENT_NM","REG_DT","CHK_STATUS_NM","IMG_OPEN_CNT","IMG_ID_CNT","IMG_ETC_CNT","D_STR","I_STR","S_STR","E_STR","MEMO1_FLAG","REG_ADMIN_NM","CHK_ADMIN_NM","CHK_DATE");
		param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0);
		param.setSheetName("검수관리");
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
					
		File f = makeBigDataExcel("com.ktis.msp.pps.imgchkmgmt.mapper.PpsHdofcImgChkMgmtMapper.getCustImgChkMngExcel", pRequestParamMap, param, handler);
		
		
		String fileName = "검수관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";

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
	 * 검수관리 검수등록
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsCustImgChkRegProc(Map<String, Object> pRequestParamMap) {

		ppsImgChkMgmtMapper.getPpsCustImgChkRegProc(pRequestParamMap);

		return pRequestParamMap;
	}
	
	/**
	 * 검수관리 검수수정
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsCustImgChkChgProc(Map<String, Object> pRequestParamMap) {

		pRequestParamMap.put("opCode", "ADMIN");
		ppsImgChkMgmtMapper.getPpsCustImgChkChgProc(pRequestParamMap);

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
	
	/**
	 * 본사-검수관리 검수이력관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	//@Crypto(decryptName="DBMSDec", fields = {"userSsn"})
	public List<?> getCustImgChkGrpMngList(Map<String, Object> pRequestParamMap) {
		
		List<EgovMap> resultList = new ArrayList<EgovMap>();

		resultList = (List<EgovMap>)ppsImgChkMgmtMapper.getCustImgChkGrpMngList(pRequestParamMap);
		return resultList;
	}
	
	/**
	 * 본사-검수관리 검수이력관리목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public void getCustImgChkGrpMngExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("검수등록번호","등록건수","검수요청","보완요청","재검수요청","검수불가","검수불필요","검수완료","기타","개통일","대리점","등록일","등록관리자");
		param.setStrValue("EVENT_SEQ","TOT_CNT","STATUS10","STATUS20","STATUS21","STATUS90","STATUS91","STATUS00","STATUS99","EVENT_DT","AGENT_NM","REG_DT","REG_ADMIN_NM");
		param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0,1,1,1,1,1,1,1,1,0,0,0,0);
		param.setSheetName("검수이력관리");
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
					
		File f = makeBigDataExcel("com.ktis.msp.pps.imgchkmgmt.mapper.PpsHdofcImgChkMgmtMapper.getCustImgChkGrpMngExcel", pRequestParamMap, param, handler);
		
		
		String fileName = "검수이력관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";

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
	 * 본사-검수관리 재검수관리목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public void getCustImgChkReMngExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("검수등록번호","계약번호","개통번호","업무구분","개통일","개통대리점","검수등록일","검수현황","개통신청서","신분증","기타","개통서류","신분증","서명","기타","검수메모","등록자","처리자","처리일");
		param.setStrValue("EVENT_SEQ","CONTRACT_NUM","SUBSCRIBER_NO","MNP_IND_CD_NM","LST_COM_ACTV_DATE","AGENT_NM","REG_DT","CHK_STATUS_NM","IMG_OPEN_CNT","IMG_ID_CNT","IMG_ETC_CNT","D_STR","I_STR","S_STR","E_STR","MEMO1_FLAG","REG_ADMIN_NM","CHK_ADMIN_NM","CHK_DATE");
		param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,5000,5000,5000);
		param.setIntLen(0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0);
		param.setSheetName("재검수관리");
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
					
		File f = makeBigDataExcel("com.ktis.msp.pps.imgchkmgmt.mapper.PpsHdofcImgChkMgmtMapper.getCustImgChkMngExcel", pRequestParamMap, param, handler);
		
		
		String fileName = "재검수관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";

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
	 * 본사-검수관리 검수관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	//@Crypto(decryptName="DBMSDec", fields = {"userSsn"})
	public List<?> getPpsAgentCmsReqMngList(Map<String, Object> pRequestParamMap) {

		
		List<EgovMap> resultList = new ArrayList<EgovMap>();

		resultList = (List<EgovMap>)ppsImgChkMgmtMapper.getPpsAgentCmsReqMngList(pRequestParamMap);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("modAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		for( EgovMap map : resultList  ){
			  
			
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

		return resultList;
	}
	
	/**
	 * 본사-검수관리 검수관리목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public void getPpsAgentCmsReqMngListExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","대리점","상태","요청구분","잔액부족기준액","잔액부족출금액","정기출금일자","정기출금액","은행","등록일","수정일","수정관리자");
		param.setStrValue("CONTRACT_NUM","AGENT_NM","STATUS_NM","CMS_CHARGE_TYPE_NM","CMS_TRY_REMAINS","CMS_CHARGE","CMS_CHARGE_DATE","CMS_CHARGE_MONTH","CMS_BANK_CODE_NM","REG_DT","MOD_DT","MOD_ADMIN_NM");
		param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0,0,0,0,1,1,0,1,0,0,0,0);
		param.setSheetName("자동이체등록");
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
					
		File f = makeBigDataExcel("com.ktis.msp.pps.imgchkmgmt.mapper.PpsHdofcImgChkMgmtMapper.getPpsAgentCmsReqMngListExcel", pRequestParamMap, param, handler);
		
		
		String fileName = "자동이체등록_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";

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
	 * 본사-검수관리 검수관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Crypto(decryptName="DBMSDec", fields = {"cmsAccount"})
	public List<?> getPpsAgentCmsReqRow(Map<String, Object> pRequestParamMap) {

		
		List<EgovMap> resultList = new ArrayList<EgovMap>();

		resultList = (List<EgovMap>)ppsImgChkMgmtMapper.getPpsAgentCmsReqRow(pRequestParamMap);

		return resultList;
	}
	
	/**
	 * 본사-검수관리 예금주명, 주민번호조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	public List<?> getPpsAgentCmsReqCustInfo(Map<String, Object> pRequestParamMap) {

		
		List<EgovMap> resultList = new ArrayList<EgovMap>();

		resultList = (List<EgovMap>)ppsImgChkMgmtMapper.getPpsAgentCmsReqCustInfo(pRequestParamMap);

		return resultList;
	}
	
	/**
	 * 자동이체드옥 수정
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsAgentCmsReqProc(Map<String, Object> pRequestParamMap) {

		ppsImgChkMgmtMapper.getPpsAgentCmsReqProc(pRequestParamMap);

		return pRequestParamMap;
	}
		
}
