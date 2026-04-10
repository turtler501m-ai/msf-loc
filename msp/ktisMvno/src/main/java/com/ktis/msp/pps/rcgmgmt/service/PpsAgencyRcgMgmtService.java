package com.ktis.msp.pps.rcgmgmt.service;

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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.pps.hdofccustmgmt.service.PpsHdofcCommonService;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsKtInResVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsRcgVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsVacVo;
import com.ktis.msp.pps.rcgmgmt.mapper.PpsAgencyRcgMgmtMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;
import com.ktds.crypto.annotation.Crypto;
import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;




import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @param <PpsAgencyRcgMgmtMapper>
 * @Class Name : PpsHdofcRcgMgmtService
 * @Description : 선불대리점 충전관리  service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */

@Service
public class PpsAgencyRcgMgmtService extends ExcelAwareService  {
	@Autowired
	private PpsHdofcCommonService  hdofcCommonService;
	
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
	private PpsAgencyRcgMgmtMapper ppsAgencyRcgMgmtMapper;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;
	
	
	
	
	/**
	 * 선불 대리점 충전냉역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getAgencyRcgInfoMgmtList(Map<String, Object> pRequestParamMap){
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		List<EgovMap> resultList = (List<EgovMap>)ppsAgencyRcgMgmtMapper.getAgencyRcgInfoMgmtList(pRequestParamMap);
		
		for(EgovMap map : resultList){
			
			logger.debug("map ["+map.toString()+"]");

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
		
		logger.debug("service  resultList ["+resultList.toString()+"]"); 
	
		return resultList;
	}
	
	
	/**
	 * 선불 대리점  충전내역엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	//@Crypto(decryptName="DBMSDec", fields = {"userSsn", "adSsn","cmsUserSsn","customerSsn"})
	public void getAgencyRcgInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		ExcelParam param = new ExcelParam();
		param.setStrHead("CTN", "충전요청", "충전구분", "충전정보", "결제방식", "충전금액", "실입금액", "충전결과", "결과메세지", "충전일자", "충전후잔액", "충전후만료일", "충전관리자", "취소여부", "취소일자");
		param.setStrValue("SUBSCRIBER_NO","REQ_SRC","CHG_TYPE_NAME","RECHARGE_INFO","RECHARGE_METHOD","AMOUNT","IN_AMOUNT","KT_RES_CODE_NM","REMARK","RECHARGE_DATE","BASIC_REMAINS","BASIC_EXPIRE","ADMIN_ID","CANCEL_FLAG","CANCEL_DT");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000,5000, 5000, 5000, 5000,5000,5000,5000,5000,5000,5000);
		param.setIntLen(0, 0,0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0);
		param.setSheetName("충전내역");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
			
			
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
			
		File f = makeBigDataExcel("com.ktis.msp.pps.rcgmgmt.mapper.PpsAgencyRcgMgmtMapper.getAgencyRcgInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "대리점충전내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
	 * 선불 대리점 정보 조회 (가상계좌. 잔액)
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@Crypto(decryptName="DBMSDec", fields = {"acntnum"})
	public List<?> getAgencyRcgAgentInfoMgmt(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsVacVo>();
		
		resultList = ppsAgencyRcgMgmtMapper.getAgencyRcgAgentInfoMgmt(pRequestParamMap);

	
		return resultList;
	}
	
	
	/**
	 * 선불 대리점 예치금 내역 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getAgencyRcgDepositHstList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsRcgVo>();
		
		resultList = ppsAgencyRcgMgmtMapper.getAgencyRcgDepositHstList(pRequestParamMap);

	
		return resultList;
	}
	
	/**
	 * 선불 대리점 고객번호조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	//@Crypto(decryptName="DBMSDec", fields = {"subscriberNo"})
	//@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> agencySearchCtn(Map<String, Object> pRequestParamMap){
		
		
		int count = 0;
		
		if(pRequestParamMap.get("contractNum") != null && !"".equals(pRequestParamMap.get("contractNum"))){
			count = 1;	
		}else{
			count = ppsAgencyRcgMgmtMapper.agencySearchCtn(pRequestParamMap);
		}
		logger.debug("count="+count);
		
		if(count == 1){
			pRequestParamMap.put("reqType", "AG_REMAINS_VIEW");
			String seq = hdofcCommonService.getPpsKtSeq();
			pRequestParamMap.put("seq", seq);
			ppsAgencyRcgMgmtMapper.getAgenyCusRemains(pRequestParamMap);
			PpsKtInResVo ppsKtInResVo = new PpsKtInResVo();
			ppsKtInResVo = hdofcCommonService.ppsHdofcSleep(pRequestParamMap);
			pRequestParamMap.put("oResCode", ppsKtInResVo.getoResCode());
			logger.debug(ppsKtInResVo.getoResCode());
			if(ppsKtInResVo.getoResCode().equals("0000")){
				pRequestParamMap.put("oResCodeNm", "음성잔액 : " + ppsKtInResVo.getoRemains()+"원");
				pRequestParamMap.put("oRemains",ppsKtInResVo.getoRemains());
			}else{
				pRequestParamMap.put("oResCodeNm", ppsKtInResVo.getoResCodeNm());
			}

			logger.debug(pRequestParamMap);
		}else{
			pRequestParamMap.put("oResCodeNm", "가입된 고객이 아닙니다.");
		}
	
		return pRequestParamMap;
	}
	
	/**
	 * 선불 대리점 고객번호조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	//@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> agencyRecharge(Map<String, Object> pRequestParamMap){
		
		ppsAgencyRcgMgmtMapper.agencyRecharge(pRequestParamMap);
		PpsKtInResVo ppsKtInResVo = new PpsKtInResVo();
		ppsKtInResVo = hdofcCommonService.ppsHdofcSleep(pRequestParamMap);
		pRequestParamMap.put("oResCode", ppsKtInResVo.getoResCode());
		logger.debug(ppsKtInResVo.getoResCode());
		if(ppsKtInResVo.getoResCode().equals("0000")){
			pRequestParamMap.put("oResCodeNm", "충전되었습니다.");
		}else{
			pRequestParamMap.put("oResCodeNm", ppsKtInResVo.getoResCodeNm());
		}

		logger.debug(pRequestParamMap);
	
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
