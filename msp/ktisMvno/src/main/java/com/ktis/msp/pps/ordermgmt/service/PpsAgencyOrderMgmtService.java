package com.ktis.msp.pps.ordermgmt.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.pps.ordermgmt.mapper.PpsAgencyOrderMgmtMapper;
import com.ktis.msp.pps.orgmgmt.vo.PpsOnlineOrderVo;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;
import com.ktis.msp.base.mvc.ExcelAwareService;

import egovframework.rte.psl.dataaccess.util.EgovMap;
/**
 * @param <PpsAgencyOrderMgmtMapper>
 * @Class Name : PpsAgencyOrderMgmtService
 * @Description : 선불 대리점 온라인주문  service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 **/

@Service
public class PpsAgencyOrderMgmtService extends ExcelAwareService  {

	@Autowired
	private PpsAgencyOrderMgmtMapper ppsAgencyOrderMgmtMapper;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
	private CryptoFactory factory;
	
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
	 * 대리점 주문관리 내역
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getOrderInfoList(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) ppsAgencyOrderMgmtMapper.getOrderInfoList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 재고관리 내역
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getOrderGoodsList(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) ppsAgencyOrderMgmtMapper.getOrderGoodsList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 대리점 주문관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public void getOrderInfoListExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		String excelTitle = "";
		if(request.getParameter("menuId").equals("PPS2300001")){
			excelTitle = "주문관리";
		}else if(request.getParameter("menuId").equals("PPS2300002")){
			excelTitle = "인수관리";
		}
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("주문번호","주문일자","상태","대리점명","품명","상품코드","상품명","선택1","선택2","선택3","주문수량","주문단가","배정수량","배정금액","총청구액(VAT포함)","배송방법","배정일자","파일등록여부","결제여부","결제방법","결제일자","인수확인일자");
		param.setStrValue("ORDER_NO","ORDER_DT","STATUS_NM","AGENT_NM","TYPE_NM","CD","CD_NM","OP1_NM","OP2_NM","OP3_NM","REQ_ORDER_CNT","REQ_SALE_AMT","SEND_CNT","SEND_AMT","SEND_TOT_AMT","DLV_METHOD_NM","SEND_DT","SEND_FILE_FLAG","PAY_FLAG","PAY_METHOD_NM","PAY_DT","END_DT");
		param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0);
		param.setSheetName(excelTitle);
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
					
		File f = makeBigDataExcel("com.ktis.msp.pps.ordermgmt.mapper.PpsAgencyOrderMgmtMapper.getOrderInfoListExcel", pRequestParamMap, param, handler);
		
		
		String fileName = excelTitle + "_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";

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
	 * 대리점정보조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getOrderAgentInfo(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) ppsAgencyOrderMgmtMapper.getOrderAgentInfo(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 주문관리, 출고관리 등록/수정/삭제
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsOrderInfoProc(Map<String, Object> pRequestParamMap) {
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));		
		ppsAgencyOrderMgmtMapper.ppsOrderInfoProc(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 *  send_file을 가져온다.
	 * @param pRequestParamMap
	 * @return
	 */
	public List<?> getOrderSendFile(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) ppsAgencyOrderMgmtMapper.getOrderSendFile(pRequestParamMap);
		
		return resultList;
		
	}
	
	/**
	 * 본사 재고관리 코드  Select용
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getOrderGoodsCd(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) ppsAgencyOrderMgmtMapper.getOrderGoodsCd(pRequestParamMap);
		
		return resultList;
	}

}
