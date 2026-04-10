package com.ktis.msp.pps.ordermgmt.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.annotation.Crypto;
import com.ktds.crypto.base.CryptoFactory;
import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.ordermgmt.mapper.PpsHdofcOrderMgmtMapper;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @param <PpsHdofcOrderMgmtMapper>
 * @Class Name : PpsHdofcOrderMgmtService
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

public class PpsHdofcOrderMgmtService extends ExcelAwareService {
	
	@Autowired
	private PpsHdofcOrderMgmtMapper orderMgmtMapper;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
    private FileDownService  fileDownService;
	
	@Autowired
	private CryptoFactory factory;
	
	@Autowired
    protected EgovPropertyService propertyService;
	
	@Autowired
	private MaskingService maskingService;
	
	public String getCurrentTimes()
	{
		String currDateTime = "";
		
		Date currtentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
		currDateTime = "_"+sdfFileName.format(currtentDate);
		
		
		return currDateTime;
	}
	
	/**
	 * 재고관리 내역
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getOrderGoodsList(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) orderMgmtMapper.getOrderGoodsList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 본사 재고관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public void getOrderGoodsListExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("품명","상품코드","상품명","선택1","선택2","선택3","전체입고","출고","폐기","재고수량","판매단가");
		param.setStrValue("TYPE_NM", "CD","CD_NM","OP1_NM", "OP2_NM","OP3_NM","TOT_CNT","OUT_CNT","CLOSE_CNT","IN_CNT","SALE_AMT");
		param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000,5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1);
		param.setSheetName("재고관리");
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
					
		File f = makeBigDataExcel("com.ktis.msp.pps.ordermgmt.mapper.PpsHdofcOrderMgmtMapper.getOrderGoodsListExcel", pRequestParamMap, param, handler);
		
		
		String fileName = "재고관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";

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
	 * 본사 재고관리 타입  Select용
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getOrderGoodsType(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) orderMgmtMapper.getOrderGoodsType(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 본사 재고관리 코드  Select용
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getOrderGoodsCd(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) orderMgmtMapper.getOrderGoodsCd(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 *  선불  상품코드 리스트 
	 * @param pRequestParamMap
	 * @return
	 */
	public 	List<?> getPpsOrderCodeList (Map<String, Object> pRequestParamMap){
		List<?>resultList = orderMgmtMapper.getPpsOrderCodeList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 재고,입출고관리 등록/수정/삭제
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsOrderGoodsProc(Map<String, Object> pRequestParamMap) {
		
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));		
		orderMgmtMapper.ppsOrderGoodsProc(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 입출고내역 내역
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getOrderInoutList(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) orderMgmtMapper.getOrderInoutList(pRequestParamMap);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regAdminNm",	"CUST_NAME");
		maskFields.put("cancelAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 본사 입출고관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public void getOrderInoutListExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {


		ExcelParam param = new ExcelParam();
		param.setStrHead("입출고","품목","상품코드","상품명","선택1","선택2","선택3","판매단가","수량","재고수량","등록자","등록일","주문번호","인수구분","취소여부","취소일","취소자","관리자메모","취소사유");
		param.setStrValue("INOUT_GUBUN_NM", "TYPE_NM","CD","CD_NM", "OP1_NM","OP2_NM","OP3_NM","SALE_AMT","INOUT_CNT","REMAINS_CNT","REG_ADMIN_NM","REG_DT","ORDER_NO","STATUS_NM","CANCEL_FLAG","CANCEL_DT","CANCEL_ADMIN_NM","REMARK","CANCEL_RSN");
		param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		param.setSheetName("입출고관리");
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
					
		File f = makeBigDataExcel("com.ktis.msp.pps.ordermgmt.mapper.PpsHdofcOrderMgmtMapper.getOrderInoutListExcel", pRequestParamMap, param, handler);
		
		
		String fileName = "입출고관리_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";

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
	 * 주문관리 내역
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getOrderInfoList(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) orderMgmtMapper.getOrderInfoList(pRequestParamMap);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("payAdminNm",	"CUST_NAME");
		
		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 본사 주문관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	
	public void getOrderInfoListExcel(HttpServletResponse response,HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {

		String excelTitle = "";
		if(request.getParameter("menuId").equals("PPS1920003")){
			excelTitle = "주문관리";
		}else if(request.getParameter("menuId").equals("PPS1920004")){
			excelTitle = "출고관리";
		}else if(request.getParameter("menuId").equals("PPS1920005")){
			excelTitle = "인수관리";
		}
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("주문번호","주문일자","상태","대리점명","품명","상품코드","상품명","선택1","선택2","선택3","주문수량","주문단가","배정수량","배정금액","총청구액(VAT포함)","배송방법","배정일자","파일등록여부","결제여부","결제방법","결제일자","결제관리자","인수확인일자");
		param.setStrValue("ORDER_NO","ORDER_DT","STATUS_NM","AGENT_NM","TYPE_NM","CD","CD_NM","OP1_NM","OP2_NM","OP3_NM","REQ_ORDER_CNT","REQ_SALE_AMT","SEND_CNT","SEND_AMT","SEND_TOT_AMT","DLV_METHOD_NM","SEND_DT","SEND_FILE_FLAG","PAY_FLAG","PAY_METHOD_NM","PAY_DT","PAY_ADMIN_NM","END_DT");
		param.setIntWidth(3000, 5000, 5000,5000, 5000, 5000,5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000);
		param.setIntLen(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0);
		param.setSheetName(excelTitle);
		param.setExcelPath(path);
		param.setFileName("test");
			
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		Decryptor dec = factory.getDecryptorByName("DBMSDec");
		
		TestHandler handler  = new TestHandler();
		handler.setDecryptor(dec);
					
		File f = makeBigDataExcel("com.ktis.msp.pps.ordermgmt.mapper.PpsHdofcOrderMgmtMapper.getOrderInfoListExcel", pRequestParamMap, param, handler);
		
		
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
	 * 주문관리, 출고관리 등록/수정/삭제
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsOrderInfoProc(Map<String, Object> pRequestParamMap) {
		
		if(pRequestParamMap.get("file_upload1_s_0")!=null && !pRequestParamMap.get("file_upload1_s_0").equals("")){
			String sendFileUrl = pRequestParamMap.get("file_upload1_s_0").toString();
			if(!StringUtils.isBlank(sendFileUrl)){
				pRequestParamMap.put("sendFile",sendFileUrl);
			}
		}
		
		if(pRequestParamMap.get("file_upload1_r_0")!=null && !pRequestParamMap.get("file_upload1_r_0").equals("")){
			String sendOrgFile = pRequestParamMap.get("file_upload1_r_0").toString();
			if(!StringUtils.isBlank(sendOrgFile)){
				pRequestParamMap.put("sendOrgFile",sendOrgFile);
			}
		}
		
		pRequestParamMap.put("adminId", pRequestParamMap.get("SESSION_USER_ID"));		
		orderMgmtMapper.ppsOrderInfoProc(pRequestParamMap);
		
		return pRequestParamMap;
	}
	
	/**
	 * 대리점정보조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getOrderAgentInfo(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) orderMgmtMapper.getOrderAgentInfo(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 첨부파일등록
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object>  fileUpload(
			HttpServletRequest pRequest, 
			String pFieldName,
			String pPath,
			Map<String, Object> pRequestParamMap
	) throws Throwable 
	{		
	
		String lSaveFileName = "";
//		String filename = "";
		Integer filesize = 0;
	  	InputStream filecontent = null;
		File f= null;
		OutputStream fout= null;
		String locPath =pPath;
		try
	    {
			
			ServletFileUpload sServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = sServletFileUpload.parseRequest(pRequest);
		    
	  	    //--------------------------------
	        // upload base directory를 구함
	  	    // 존재하지 않으면 exception 발생
	        //--------------------------------
	  	    String lBaseDir = propertyService.getString("ppsPath");
			if ( !new File(lBaseDir ).exists())
			{
//				throw new Exception(messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
			}

	  	   
	        
			//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	        // 모듈별 경로에 앞뒤로 "/" 가 붙거나 붙지않은 경우 check하여 "/path" 형태로 변경함
	  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	        if ( locPath.contains(".."))  {
	        		return null;
	        }
	        
	        locPath = locPath.replaceAll("^/", "");
	        locPath = locPath.replaceAll("/$", "");
	  	    String sFilePath      = "/" + locPath;
	  	    
	  	    StringBuffer tmplBaseDir = new StringBuffer();
	  	    
	  	    tmplBaseDir.append(lBaseDir);
	  	  	tmplBaseDir.append(sFilePath);
	  	
	  	    lBaseDir = tmplBaseDir.toString();
	  	     
	  	     
	  	    for (FileItem item : items) {
				
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		        // 파일 타입이 아니면 skip
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				if (item.isFormField()) 
					continue;
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		        // 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
		  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				long limitSize = 209715200; //200메가로 제한
				
				if ( item.getSize() == 0 ||  item.getSize()  >  limitSize )
					continue;

				try	{
			  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			        // 모듈별 directory가 존재하지 않으면 생성
			  	    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					Date currtentDate = new Date(System.currentTimeMillis());
					
					SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MMdd",Locale.getDefault());
					SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
					StringBuffer dateFolder = new StringBuffer(lBaseDir);
					String dateFileName = sdfFileName.format(currtentDate);
					String agentId = String.valueOf(pRequestParamMap.get("agentId"));
					String adminId= String.valueOf(pRequestParamMap.get("adminId"));
					lBaseDir = dateFolder.append(sdf.format(currtentDate)).toString();
					
					File sDir = new File(lBaseDir);
					if ( !sDir.exists())
					{
						
						//logger.debug("file can write... : " , lDir.canWrite());
						sDir.mkdirs();
					}
					
					
					//----------------------------------------------------------------
			    	// upload 이름 구함
			    	//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file")){
						//----------------------------------------------------------------
				    	// 파일 이름 구함
				    	//----------------------------------------------------------------
						
						String ext = FilenameUtils.getExtension(item.getName());
						StringBuffer sb  = new StringBuffer();
						String sfileNamePc = sb.append(agentId).append("_").append(dateFileName).append(".").append(ext).toString();	

						//String sUrl =lBaseDir +"/";
						
 						lSaveFileName = sfileNamePc;
						
				    	String sTransferTargetFileName = lBaseDir + "/" + lSaveFileName;
				    	pRequestParamMap.put("imgFile",lSaveFileName);
						pRequestParamMap.put("imgPath",sTransferTargetFileName);
						pRequestParamMap.put("name", sTransferTargetFileName);
						pRequestParamMap.put("baseDir", lBaseDir);
						//----------------------------------------------------------------
				    	// 파일 write
				    	//----------------------------------------------------------------
				    	 filecontent = item.getInputStream();
						 f=new File(sTransferTargetFileName);
						fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;
						while((len=filecontent.read(buf))>0) {
							fout.write(buf,0,len);
							filesize+=len;
						}
//						fout.close();
					}
				}catch (Exception e) {
//					20200512 소스코드점검 수정
//			    	e.printStackTrace();
//					20210706 소스코드점검 수정
//					System.out.println("Connection Exception occurred");
					logger.error("Connection Exception occurred");
				    throw e;
				}
				finally {
					if (filecontent != null) {
						try { filecontent.close(); } catch (IOException e) {  throw new MvnoRunException(-1,e.getMessage());}
					}
					if (fout != null) {
						try { fout.close(); } catch (IOException e) {  throw new MvnoRunException(-1,e.getMessage());}
					}
				}
			}
			
		} catch (Exception e)
		{
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
			 throw new MvnoRunException(-1,e.getMessage());
		}  	    	
		
		
		pRequestParamMap.put("state", true);
		pRequestParamMap.put("size", "" + 111);
		pRequestParamMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		pRequestParamMap.put("msg", "");
			
		return pRequestParamMap;
	}
	
	/**
	 *  send_file을 가져온다.
	 * @param pRequestParamMap
	 * @return
	 */
	public List<?> getOrderSendFile(Map<String, Object> pRequestParamMap){
		List<EgovMap> resultList = (List<EgovMap>) orderMgmtMapper.getOrderSendFile(pRequestParamMap);
		
		return resultList;
		
	}
	
	/**
	 *  선불  리프레쉬 리스트 
	 * @param pRequestParamMap
	 * @return
	 */
	public 	List<?> getPpsOrderRefreshList (Map<String, Object> pRequestParamMap){
		List<?>resultList = orderMgmtMapper.getPpsOrderRefreshList(pRequestParamMap);
		
		return resultList;
	}
	
	
	
}
