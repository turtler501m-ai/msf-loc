package com.ktis.msp.pps.warmgmt.service;



import java.io.File;
import java.io.FileInputStream;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;
import com.ktis.msp.pps.warmgmt.mapper.PpsWarMgmtMapper;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @param <PpsWarMgmtMapper>
 * @Class Name : PpsWarMgmtService
 * @Description : 선불
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.16  최초생성
 * @
 * @author : 
 * @Create Date : 2016. 3. 16.
 */

@Service
public class PpsWarMgmtService extends ExcelAwareService {

	
	
	@Autowired
	protected EgovPropertyService propertyService;

	@Autowired
	private PpsWarMgmtMapper warMgmtMapper;
	
//	@Autowired
//	private CryptoFactory factory;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;
	
	/**
	 * 다량문자모니터링 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getWarInfoMgmtList(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>)warMgmtMapper.getWarInfoMgmtList(pRequestParamMap);

		for(EgovMap map : resultList){
			
		    if(map.get("contractNum") != null && !"".equals(map.get("contractNum")))
			{	
				String contractNum = map.get("contractNum").toString();
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustDetail("+contractNum+");^_self";
				sb.append(contractNum).append(tmp);
		    	
		    	map.put("contractNumStr", sb.toString());
		    }
		    
		}
		
		return resultList;
	}
	
	/**
	 * 다량문자모니터링 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getWarInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","고객명","요금제","상태","개통일","상태변경일","이용일수","통화건수","통화분수","SMS","MMS","일평균문자","일최대문자","문자합계","대리점명","판매점명","등록여부");
		param.setStrValue("CONTRACT_NUM","SUB_LINK_NAME","SERVICE_NM","SUB_STATUS_NM","LST_COM_ACTV_DATE","SUB_STATUS_DATE","USE_TERM","VOICE_CNT","VOICE_DUR","SMS_CNT","MMS_CNT","SMS_CNT_DAY","TOT_SMS_MAX","TOT_SMS","AGENT_NM","AGENT_SALE_NM","WAR_FLAG");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0);
		param.setSheetName("다량문자모니터링");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.warmgmt.mapper.PpsWarMgmtMapper.getWarInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "다량문자모니터링_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
		
	}
	
	/**
	 * 주의고객 등록 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	//@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsWarReg(Map<String, Object> pRequestParamMap){
		warMgmtMapper.ppsWarReg(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 주의고객관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getWarRegInfoMgmtList(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>)warMgmtMapper.getWarRegInfoMgmtList(pRequestParamMap);

		for(EgovMap map : resultList){
			
		    if(map.get("contractNum") != null && !"".equals(map.get("contractNum")))
			{	
				String contractNum = map.get("contractNum").toString();
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goCustDetail("+contractNum+");^_self";
				sb.append(contractNum).append(tmp);
		    	
		    	map.put("contractNumStr", sb.toString());
		    }
		    
		}
		
		return resultList;
	}
	
	/**
	 * 주의고객관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getWarRegInfoMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		//List<?> resultList = new ArrayList<PpsRcgVo>();
		
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("계약번호","고객명","요금제","상태","개통일","상태변경일","이용일수","대리점명","판매점명","처리근거","등록사유","등록일자","등록관리자","메모");
		param.setStrValue("CONTRACT_NUM","SUB_LINK_NAME","SERVICE_NM","SUB_STATUS_NM","LST_COM_ACTV_DATE","SUB_STATUS_DATE","USE_TERM","AGENT_NM","AGENT_SALE_NM","WAR_CD_1_NM","WAR_CD_2_NM","ENTER_DATE","ADMIN_NM","DESC_INFO");
		param.setIntWidth(3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("주의고객관리");
		param.setExcelPath(path);
		param.setFileName("test");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.warmgmt.mapper.PpsWarMgmtMapper.getWarRegInfoMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "주의고객관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
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
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
		
	}
	
	/**
	 *  sms전송 excel파일 읽기
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public  String  getPpsWarFileRead(String fileName, String sFileNm) throws MvnoRunException {
		String filePath    =  sFileNm;
	    HSSFWorkbook workBook  = null;
	    HSSFSheet sheet    =  null;
	    HSSFRow row     =  null;
	    HSSFCell cell    =  null;
	    String sCellValue ="";
		FileInputStream fInput = null;
		int nUpdateCnt = 0;
		
		StringBuffer sb = new StringBuffer();
		String contractNumArr = "";
		
		try{ 
			File file = new File(filePath);
			fInput = new FileInputStream(file);
		   // excel  =  new POIFSFileSystem(new FileInputStream(filePath));
		    workBook  =  new HSSFWorkbook(fInput);
		    workBook.getNumberOfSheets();
			// 1 .sheet
	          sheet     =  workBook.getSheetAt(0);
	          int rows    =  sheet.getPhysicalNumberOfRows();
	          
    		// insert lgs Ord Prdt 
	          for(int r=0;r<rows;r++){
	        	  
	        	   //row전체가 비어있는경우 건너띄기 위해
	        	   if(sheet.getRow(r) == null && rows -1 != r){
		        	   rows++;
		        	   continue;
		           }
	        	   
		           if(r== 0){
			            continue;
			       }

	        	   row     =  sheet.getRow(r);
		           
//		           EgovMap em = new EgovMap();
		           for(int c=0;c<1;c++){
			            cell   =  row.getCell(c);		            
			            if(cell== null){
			            	continue;
			            }
			            
			            switch(cell.getCellType()){
                            case HSSFCell.CELL_TYPE_FORMULA :
				            	 sCellValue = ""+cell.getStringCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_NUMERIC :
				            	 sCellValue = ""+cell.getNumericCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_STRING :
				            	 sCellValue = ""+cell.getStringCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_BLANK :
				            	 sCellValue = ""+cell.getStringCellValue();

                            break;
                            case HSSFCell.CELL_TYPE_BOOLEAN :
				            	 sCellValue = ""+cell.getStringCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_ERROR :
			            	     sCellValue = ""+cell.getErrorCellValue();
                            break;				            
				             default:
				            	 logger.debug("default");
			            }
						/*
						   1. 계약번호
						 */
			            
			            if(c == 0){
			            	sb.append(sCellValue).append("|");
			            }

		           }//cell
		           
		           nUpdateCnt++;
			      
			       if(nUpdateCnt > 1000  )   { throw new MvnoRunException(-1,"1000건이상은  처리 불가 합니다 ");}
	          }//row
	          
	          if(nUpdateCnt > 0){
	        	  contractNumArr = sb.toString().substring(0, sb.toString().length()-1);
	          }
	          
	          if(!fileName.equals("war_sample.xls")){
	        	  file.delete();
	          }
	         
			}
		    catch(Exception e){
	            
              throw new MvnoRunException(-1,"");
		    }
		finally {
			if (fInput != null) {
				try { fInput.close(); } catch (IOException e) {  throw new MvnoRunException(-1,e.getMessage()); }
			}
		}
		
		return contractNumArr;

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
