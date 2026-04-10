package com.ktis.msp.cmn.filedown.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.opc.internal.ZipHelper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelWriter;
import com.ktis.msp.base.excel.SfExcelAttribute;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.mapper.FileDownMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;

import au.com.bytecode.opencsv.CSVWriter;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;



/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: FileDownService.java
 * 3. Package	: com.ktis.msp.cmn.filedown.service
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:54:28
 * </PRE>
 */
@Service
public class FileDownService {

	@Autowired  
	protected MessageSource messageSource; 

	@Autowired
	private FileDownMapper fileDownMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	protected Log log = LogFactory.getLog(this.getClass());


	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	private static final String XML_ENCODING = "UTF-8";

	/**
	 * <PRE>
	 * 1. MethodName: insertCmnFileDnldMgmtMst
	 * 2. ClassName	: FileDownService
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:54:31
	 * </PRE>
	 * 		@return int
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertCmnFileDnldMgmtMst(Map<String, Object> pRequestParamMap)  {
		
		return fileDownMapper.insertCmnFileDnldMgmtMst(pRequestParamMap);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int insertCmnExclDnldHst(Map<String, Object> pRequestParamMap)  {
		
		return fileDownMapper.insertCmnExclDnldHst(pRequestParamMap);
	}
	
	public boolean getFileMgmtById(HttpServletRequest pRequest, 
									HttpServletResponse pResponse, 
									Map<String, Object> pRequestParamMap)  
	{
		
		try
		{

			//---------------------------------
			// DB에서 file 정보 가져오기
			//---------------------------------
			List<?> resultList =  fileDownMapper.getFileMgmtById(pRequestParamMap);
			
			if ( resultList.size() == 0 )
			{
				return false;
			}
			
			EgovMap lRow = (EgovMap) resultList.get(0);
			
			//---------------------------------
			// 
			//---------------------------------
			String lBaseDir = propertyService.getString("fileUploadBaseDirectory");
		
			//=====================
			pResponse.setContentType("application/octet-stream;charset=utf-8"); 
			pResponse.setCharacterEncoding("UTF-8"); 

			//--------------------------------
			// 파일 이름
			//--------------------------------
			String lFileName = StringUtils.defaultString((String)lRow.get("fileNm"),"no_file_name");
			lFileName =  URLDecoder.decode(lFileName, "UTF-8")   ;


			//--------------------------------
			//PC 파일 이름
			//--------------------------------
			String lFileNamePc = StringUtils.defaultString((String)lRow.get("fileNmPc"),"no_file_name");
			lFileNamePc =  URLDecoder.decode(lFileNamePc, "UTF-8")   ;


			//--------------------------------
			// 서버의 경로(모율별)
			//--------------------------------
			String lFilePath = StringUtils.defaultString((String)lRow.get("fileRout"),"no_path"); 
			if ( lFilePath.contains(".."))
			{
				return false;
			}
			
			int    lFileSize = 0; 


			//--------------------------------
			// 파일명이 없으면 return
			//--------------------------------
			if( lFileName == null ) 
			{
				return false;
			}
			
			String fileNameFullPath = lBaseDir + lFilePath  + "/" +  lFileName;
			File file = new File(fileNameFullPath);
			String lFileNamePcEncode = URLEncoder.encode(lFileNamePc, "UTF-8");

			//--------------------------------
			// 파일이 존재하면 down 시작
			//--------------------------------
			if (file.exists()){		
//	        	out.clear();
//	        	out = pageContext.pushBody();
				
				pResponse.setHeader("Content-Disposition","attachment;filename="+lFileNamePcEncode);
				lFileSize = (int) file.length();
				byte b[] = new byte[lFileSize]; 
				//byte b[] = new byte[4062];
				
				BufferedInputStream fin   = null;
				BufferedOutputStream outs = null;
				try {
					fin   = new BufferedInputStream(new FileInputStream(file));
					outs = new BufferedOutputStream(pResponse.getOutputStream());
					
					int read = 0;
					while ((read = fin.read(b)) != -1){
					  	outs.write(b,0,read);
					}
					
				} catch(Exception e){
					throw new MvnoErrorException(e);
				} finally {
					if(outs != null){
						outs.close();
					}
					
					if(fin != null){
						fin.close();
					}
				}
				

				
				if (StringUtils.defaultString((String)pRequestParamMap.get("writeFileUpDownLog"),"N").equals("Y") )
				{
					pRequestParamMap.put("FILE_NM"   ,lFileName);
					
					String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
					
					if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
						ipAddr = pRequest.getHeader("REMOTE_ADDR");
				   
					if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
						ipAddr = pRequest.getRemoteAddr();
					

					
					pRequestParamMap.put("IP_INFO"   ,ipAddr);
					pRequestParamMap.put("DUTY_NM"   ,"");
					pRequestParamMap.put("FILE_ROUT" ,fileNameFullPath);
					pRequestParamMap.put("FILE_SIZE" ,lFileSize);
					
					pRequestParamMap.put("DATA_CNT", 0);
					
					insertCmnFileDnldMgmtMst(pRequestParamMap);
				}	            
			}else{

				pResponse.setContentType("text/html;charset=utf-8");
				pResponse.getWriter(). println(lFileName + " : not exists.");
			}
		} catch (Exception e)
		{
			throw new MvnoErrorException(e);
		}
		return true;
	}
	
	
	
	/**
	 * @Description : 엑셀다운로드 기능
	 * @Param  : fileName
	 * @Return : String
	 * @Author : 김용문
	 * @Create Date : 2016. 11. 15.
	 */
	@SuppressWarnings("unchecked")
	public String excelDownProc(String fileName
			, String sheetName
			, Iterator<?> voLists
			, String[] strHead
			, int[] intWidth
			, String[] strValue
			, HttpServletRequest request
			, HttpServletResponse response
			, int[] intLen){
		
		HSSFWorkbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전 
//	        Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상 
		
		// Sheet 생성 
		Sheet sheet1 = xlsWb.createSheet(sheetName); 
		 
		for (int i = 0; i < intWidth.length; i++) {
			sheet1.setColumnWidth(i, intWidth[i]);
		}
		  
		// Cell 스타일 생성 
		CellStyle cellStyleHead = xlsWb.createCellStyle(); 
		CellStyle cellStyleMid = xlsWb.createCellStyle(); 
		CellStyle cellStyleInt = xlsWb.createCellStyle(); 
		  
		// 줄 바꿈 
		cellStyleHead.setWrapText(true); 
		  
		HSSFFont fontHead = xlsWb.createFont();
		fontHead.setBoldweight((short) 1);
		fontHead.setFontName("맑은 고딕");
		fontHead.setColor(HSSFColor.BLACK.index);
		fontHead.setFontHeightInPoints((short) 12);
		
		HSSFFont fontMid = xlsWb.createFont();
		fontMid.setBoldweight((short) 1);
		fontMid.setFontName("맑은 고딕");
		fontMid.setColor(HSSFColor.BLACK.index);
		fontMid.setFontHeightInPoints((short) 10);
		
		//제목
//	        cellStyle.setFillForegroundColor(HSSFColor.LIME.index); 
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment((short) 2);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setBorderRight((short) 1);
//	        cellStyleHead.setBorderLeft((short) 1);
		cellStyleHead.setBorderTop((short) 1);
		cellStyleHead.setBorderBottom((short) 1);
		cellStyleHead.setFont(fontHead);
		
		//내용
		cellStyleMid.setAlignment((short) 2);
		cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleMid.setFillPattern((short) 1);
		cellStyleMid.setBorderRight((short) 1);
		cellStyleMid.setBorderLeft((short) 1);
		cellStyleMid.setBorderTop((short) 1);
		cellStyleMid.setBorderBottom((short) 1);
		cellStyleMid.setFont(fontMid);
		
		//숫자용
		HSSFDataFormat format = xlsWb.createDataFormat();
		
		cellStyleInt.setAlignment((short) 3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern((short) 1);
		cellStyleInt.setBorderRight((short) 1);
		cellStyleInt.setBorderLeft((short) 1);
		cellStyleInt.setBorderTop((short) 1);
		cellStyleInt.setBorderBottom((short) 1);
		cellStyleInt.setFont(fontMid);
		cellStyleInt.setDataFormat(format.getFormat("#,##0"));
		
		Row row = null; 
		Cell cell = null; 

		// 첫 번째 줄 제목 
		row = sheet1.createRow(0); 
		
		for (int i = 0; i < strHead.length; i++) {
			cell = row.createCell(i); 
			cell.setCellValue(strHead[i]);
			cell.setCellStyle(cellStyleHead);
		}
		
		
		// 데이터 적재
		try{
			int iCR = 0;
			while(voLists.hasNext()) {
				Object obj = voLists.next();
				Field[] fields = obj.getClass().getDeclaredFields();
				
				row = sheet1.createRow(iCR + 1);
				for (int j = 0; j < strValue.length; j++) {
					cell = row.createCell(j);
					String strVal = "";
					for(int i=0; i<=fields.length-1;i++){
						fields[i].setAccessible(true);
						if(intLen[j] == 1){
							if(strValue[j].equals(fields[i].getName())) {
								strVal = fields[i].get(obj) != null && !"".equals(String.valueOf(fields[i].get(obj))) ? String.valueOf(fields[i].get(obj)) : "0";
								
								double d = Double.parseDouble(strVal);
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);
								cell.setCellValue(d);
								cell.setCellStyle(cellStyleInt);
							}
						} else {
							if(strValue[j].equals(fields[i].getName())) {
								strVal = fields[i].get(obj) != null && !"".equals((String) fields[i].get(obj)) ? fields[i].get(obj).toString() : "";
								
								cell.setCellValue(strVal);
								cell.setCellStyle(cellStyleMid);
							}
						}
					}
				}
				iCR++;
			}
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}

		StringBuilder sbFileName = new StringBuilder();
		
		Date toDay = new Date();
		
		String strFileName = fileName;
		String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
		
		sbFileName.append(strFileName);
		
		//2015.12.09 엑셀다운로드 파일명에 ID 추가-곽대리요청
        sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID"));
        sbFileName.append("_");
		sbFileName.append(strToDay);
		sbFileName.append(".xls");
		
		String encodingFileName = "";

		int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
		
		try {
			encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			encodingFileName = sbFileName.toString();
		}

		response.setHeader("Cache-Control", "");
		response.setHeader("Pragma", "");

		response.setContentType("Content-type:application/octet-stream;");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		/** 20230518 PMD 조치 */
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		File tempFile = new File(sbFileName.toString());
		
		try {
			bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			
			xlsWb.write(bos);
			bos.flush();
			bos.close();
				  
			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());
				
			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			bos.flush();
					
		} catch (IOException e) {
			throw new MvnoErrorException(e);
		} finally {
			if (bis != null){
				try {
					bis.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		
		return sbFileName.toString();
	}
	
	/**
	 * @Description : 엑셀다운로드 기능
	 * @Param  : fileName
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@SuppressWarnings("unchecked")
	public String excelDownProc(String fileName
			, String sheetName
			, List<?> voLists
			, String[] strHead
			, int[] intWidth
			, String[] strValue
			, HttpServletRequest request
			, HttpServletResponse response
			, int[] intLen){
		
		HSSFWorkbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전 
//	        Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상 
		
		// Sheet 생성 
		Sheet sheet1 = xlsWb.createSheet(sheetName); 
		 
		for (int i = 0; i < intWidth.length; i++) {
			sheet1.setColumnWidth(i, intWidth[i]);
		}
		  
		// Cell 스타일 생성 
		CellStyle cellStyleHead = xlsWb.createCellStyle(); 
		CellStyle cellStyleMid = xlsWb.createCellStyle(); 
		CellStyle cellStyleInt = xlsWb.createCellStyle(); 
		  
		// 줄 바꿈 
		cellStyleHead.setWrapText(true); 
		  
		HSSFFont fontHead = xlsWb.createFont();
		fontHead.setBoldweight((short) 1);
		fontHead.setFontName("맑은 고딕");
		fontHead.setColor(HSSFColor.BLACK.index);
		fontHead.setFontHeightInPoints((short) 12);
		
		HSSFFont fontMid = xlsWb.createFont();
		fontMid.setBoldweight((short) 1);
		fontMid.setFontName("맑은 고딕");
		fontMid.setColor(HSSFColor.BLACK.index);
		fontMid.setFontHeightInPoints((short) 10);
		
		//제목
//	        cellStyle.setFillForegroundColor(HSSFColor.LIME.index); 
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment((short) 2);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setBorderRight((short) 1);
//	        cellStyleHead.setBorderLeft((short) 1);
		cellStyleHead.setBorderTop((short) 1);
		cellStyleHead.setBorderBottom((short) 1);
		cellStyleHead.setFont(fontHead);
		
		//내용
		cellStyleMid.setAlignment((short) 2);
		cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleMid.setFillPattern((short) 1);
		cellStyleMid.setBorderRight((short) 1);
		cellStyleMid.setBorderLeft((short) 1);
		cellStyleMid.setBorderTop((short) 1);
		cellStyleMid.setBorderBottom((short) 1);
		cellStyleMid.setFont(fontMid);
		
		//숫자용
		HSSFDataFormat format = xlsWb.createDataFormat();
		
		cellStyleInt.setAlignment((short) 3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern((short) 1);
		cellStyleInt.setBorderRight((short) 1);
		cellStyleInt.setBorderLeft((short) 1);
		cellStyleInt.setBorderTop((short) 1);
		cellStyleInt.setBorderBottom((short) 1);
		cellStyleInt.setFont(fontMid);
		cellStyleInt.setDataFormat(format.getFormat("#,##0"));
		
		Row row = null; 
		Cell cell = null; 

		// 첫 번째 줄 제목 
		row = sheet1.createRow(0); 
		
		for (int i = 0; i < strHead.length; i++) {
			cell = row.createCell(i); 
			cell.setCellValue(strHead[i]);
			cell.setCellStyle(cellStyleHead);
		}
		
		
		// 데이터 적재
		try{
			for (int i = 0; i < voLists.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) voLists.get(i);
				row = sheet1.createRow(i + 1);
				
				for (int j = 0; j < strValue.length; j++) {
					cell = row.createCell(j);
					String strVal = "";
					if(intLen[j] == 1){
						strVal = map.get(strValue[j]) != null && !"".equals(String.valueOf(map.get(strValue[j]))) ? String.valueOf(map.get(strValue[j])) : "0";
						
						double d = Double.parseDouble(strVal);
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(d);
						cell.setCellStyle(cellStyleInt);
					}else{
						strVal = map.get(strValue[j]) != null && !"".equals(String.valueOf(map.get(strValue[j]))) ? map.get(strValue[j]).toString() : "";
						cell.setCellValue(strVal);
						cell.setCellStyle(cellStyleMid);
					}
				}
			}
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}

		StringBuilder sbFileName = new StringBuilder();
		
		Date toDay = new Date();
		
		String strFileName = fileName;
		String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
		
		sbFileName.append(strFileName);
		
		//2015.12.09 엑셀다운로드 파일명에 ID 추가-곽대리요청
        sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID"));
        sbFileName.append("_");
		sbFileName.append(strToDay);
		sbFileName.append(".xls");
		
		String encodingFileName = "";

		int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
		
		try {
			encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			encodingFileName = sbFileName.toString();
		}

		response.setHeader("Cache-Control", "");
		response.setHeader("Pragma", "");

		response.setContentType("Content-type:application/octet-stream;");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		/** 20230518 PMD 조치 */
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		File tempFile = new File(sbFileName.toString());
		
		try {
			bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			
			xlsWb.write(bos);
			bos.flush();
			bos.close();
				  
			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());
				
			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			bos.flush();
					
		} catch (IOException e) {
			throw new MvnoErrorException(e);
		} finally {
			if (bis != null){
				try {
					bis.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		
		return sbFileName.toString();
	}

	/**
	 * @Description : 엑셀 Save ( zip 용 ) 기능
	 * @Param  : fileName
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2020. 9. 29.
	 */
	@SuppressWarnings("unchecked")
	public String excelSaveProc(String fileName
			, String sheetName
			, List<?> voLists
			, String[] strHead
			, int[] intWidth
			, String[] strValue
			, HttpServletRequest request
			, HttpServletResponse response
			, int[] intLen){
		
		HSSFWorkbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전 
//	        Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상 
		
		// Sheet 생성 
		Sheet sheet1 = xlsWb.createSheet(sheetName); 
		 
		for (int i = 0; i < intWidth.length; i++) {
			sheet1.setColumnWidth(i, intWidth[i]);
		}
		  
		// Cell 스타일 생성 
		CellStyle cellStyleHead = xlsWb.createCellStyle(); 
		CellStyle cellStyleMid = xlsWb.createCellStyle(); 
		CellStyle cellStyleInt = xlsWb.createCellStyle(); 
		  
		// 줄 바꿈 
		cellStyleHead.setWrapText(true); 
		  
		HSSFFont fontHead = xlsWb.createFont();
		fontHead.setBoldweight((short) 1);
		fontHead.setFontName("맑은 고딕");
		fontHead.setColor(HSSFColor.BLACK.index);
		fontHead.setFontHeightInPoints((short) 12);
		
		HSSFFont fontMid = xlsWb.createFont();
		fontMid.setBoldweight((short) 1);
		fontMid.setFontName("맑은 고딕");
		fontMid.setColor(HSSFColor.BLACK.index);
		fontMid.setFontHeightInPoints((short) 10);
		
		//제목
//	        cellStyle.setFillForegroundColor(HSSFColor.LIME.index); 
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment((short) 2);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setBorderRight((short) 1);
//	        cellStyleHead.setBorderLeft((short) 1);
		cellStyleHead.setBorderTop((short) 1);
		cellStyleHead.setBorderBottom((short) 1);
		cellStyleHead.setFont(fontHead);
		
		//내용
		cellStyleMid.setAlignment((short) 2);
		cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleMid.setFillPattern((short) 1);
		cellStyleMid.setBorderRight((short) 1);
		cellStyleMid.setBorderLeft((short) 1);
		cellStyleMid.setBorderTop((short) 1);
		cellStyleMid.setBorderBottom((short) 1);
		cellStyleMid.setFont(fontMid);
		
		//숫자용
		HSSFDataFormat format = xlsWb.createDataFormat();
		
		cellStyleInt.setAlignment((short) 3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern((short) 1);
		cellStyleInt.setBorderRight((short) 1);
		cellStyleInt.setBorderLeft((short) 1);
		cellStyleInt.setBorderTop((short) 1);
		cellStyleInt.setBorderBottom((short) 1);
		cellStyleInt.setFont(fontMid);
		cellStyleInt.setDataFormat(format.getFormat("#,##0"));
		
		Row row = null; 
		Cell cell = null; 

		// 첫 번째 줄 제목 
		row = sheet1.createRow(0); 
		
		for (int i = 0; i < strHead.length; i++) {
			cell = row.createCell(i); 
			cell.setCellValue(strHead[i]);
			cell.setCellStyle(cellStyleHead);
		}
		
		
		// 데이터 적재
		try{
			for (int i = 0; i < voLists.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) voLists.get(i);
				row = sheet1.createRow(i + 1);
				
				for (int j = 0; j < strValue.length; j++) {
					cell = row.createCell(j);
					String strVal = "";
					if(intLen[j] == 1){
						strVal = map.get(strValue[j]) != null && !"".equals(String.valueOf(map.get(strValue[j]))) ? String.valueOf(map.get(strValue[j])) : "0";
						
						double d = Double.parseDouble(strVal);
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(d);
						cell.setCellStyle(cellStyleInt);
					}else{
						strVal = map.get(strValue[j]) != null && !"".equals((String) map.get(strValue[j])) ? map.get(strValue[j]).toString() : "";
						cell.setCellValue(strVal);
						cell.setCellStyle(cellStyleMid);
					}
				}
			}
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}

		StringBuilder sbFileName = new StringBuilder();
		
		Date toDay = new Date();
		
		String strFileName = fileName;
		String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
		
		sbFileName.append(strFileName);
		
		//2015.12.09 엑셀다운로드 파일명에 ID 추가-곽대리요청
        sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID"));
        sbFileName.append("_");
		sbFileName.append(strToDay);
		sbFileName.append(".xls");
		
		String encodingFileName = "";

		int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
		
		try {
			encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			encodingFileName = sbFileName.toString();
		}
		

		/*
		response.setHeader("Cache-Control", "");
		response.setHeader("Pragma", "");

		response.setContentType("Content-type:application/octet-stream;");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		*/
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		File tempFile = new File(sbFileName.toString());
		
		try {
			bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			
			xlsWb.write(bos);
			bos.flush();
			bos.close();
			
			/*
			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());
				
			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			bos.flush();
			*/	
		} catch (IOException e) {
			throw new MvnoErrorException(e);
		} finally {
			if (bis != null){
				try {
					bis.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}			
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		
		return sbFileName.toString();
	}
	
	/**
	 * @Description : CSV다운로드 기능
	 * @Param
	 * @Return
	 * @Author : KYM
	 * @Create Date : 2016. 11. 15
	 */
	@SuppressWarnings("unchecked")
	public String csvDownProcStream(String fileName
			                        , Iterator<?> voLists
			                        , String[] strHead
			                        , String[] strValue
			                        , HttpServletRequest request
			            			, HttpServletResponse response) {
		StringBuilder sbFileName = new StringBuilder();
		
		CSVWriter cw = null;
		try {
	        Date toDay = new Date();
	        String strFileName = fileName;
	        String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
	        sbFileName.append(strFileName);
	        //2015.12.09 엑셀다운로드 파일명에 ID 추가-곽대리요청
	        sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID")).append("_");
	        sbFileName.append(strToDay);
	        sbFileName.append(".csv");
	        
			/**
	         * csv 파일을 쓰기위한 설정
	         * 설명
	         * D:\\test.csv : csv 파일저장할 위치+파일명
	         * EUC-KR : 한글깨짐설정을 방지하기위한 인코딩설정(UTF-8로 지정해줄경우 한글깨짐)
	         * ',' : 배열을 나눌 문자열
	         * '"' : 값을 감싸주기위한 문자
	         **/
	        cw = new CSVWriter(new OutputStreamWriter(new FileOutputStream(sbFileName.toString()), "EUC-KR"),',', '"');
	        cw.writeNext(strHead);
	        
	        while(voLists.hasNext()) {
	        	Object obj = voLists.next();
	        	Field[] fields = obj.getClass().getDeclaredFields();
				String[] arrBody = new String[fields.length];
				
				for (int j = 0; j < strValue.length; j++) {
					for(int i=0; i<=fields.length-1;i++){
						// private 도 접근 가능하도록 바꿔준다.
						fields[i].setAccessible(true);
						if(strValue[j].equals(fields[i].getName())) {
							arrBody[j] = fields[i].get(obj) != null ? String.valueOf(fields[i].get(obj)) : "";
						}
					}
				}
				cw.writeNext(arrBody);
	        }
	        
	        String encodingFileName = "";
			int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
	        try {
	          encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
	        } catch (UnsupportedEncodingException uee) {
	          encodingFileName = sbFileName.toString();
	        }
	        response.setHeader("Cache-Control", "");
	        response.setHeader("Pragma", "");
	        response.setContentType("Content-type:application/octet-stream;");
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
	        response.setHeader("Content-Transfer-Encoding", "binary");
		} catch(Exception e) {
			throw new MvnoErrorException(e);
		} finally {
			if(cw != null){
		        try {
					cw.close();
				} catch (IOException e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		
		return sbFileName.toString();
	}
	
	/**
	 * @Description : CSV다운로드 기능
	 * @Param
	 * @Return
	 * @Author : KYM
	 * @Create Date : 2016. 09. 23
	 */
	@SuppressWarnings("unchecked")
	public String csvDownProcStream(String fileName
			                        , List<?> voLists
			                        , String[] strHead
			                        , String[] strValue
			                        , HttpServletRequest request
			                        , HttpServletResponse response) {
		StringBuilder sbFileName = new StringBuilder();

		CSVWriter cw = null;
		try {
	        Date toDay = new Date();
	        String strFileName = fileName;
	        String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
	        sbFileName.append(strFileName);
	        //2015.12.09 엑셀다운로드 파일명에 ID 추가-곽대리요청
	        sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID")).append("_");
	        sbFileName.append(strToDay);
	        sbFileName.append(".csv");
	        
			/**
	         * csv 파일을 쓰기위한 설정
	         * 설명
	         * D:\\test.csv : csv 파일저장할 위치+파일명
	         * EUC-KR : 한글깨짐설정을 방지하기위한 인코딩설정(UTF-8로 지정해줄경우 한글깨짐)
	         * ',' : 배열을 나눌 문자열
	         * '"' : 값을 감싸주기위한 문자
	         **/
	        cw = new CSVWriter(new OutputStreamWriter(new FileOutputStream(sbFileName.toString()), "EUC-KR"),',', '"');
	        cw.writeNext(strHead);
        
	        for (int i = 0; i < voLists.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) voLists.get(i);
				String[] arrBody = new String[map.size()];
				
				for (int j = 0; j < strValue.length; j++) {
					arrBody[j] = map.get(strValue[j]) != null ? String.valueOf(map.get(strValue[j])) : "";
				}
				cw.writeNext(arrBody);
	        }
	        
	        String encodingFileName = "";
			int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
	        try {
	          encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
	        } catch (UnsupportedEncodingException uee) {
	          encodingFileName = sbFileName.toString();
	        }
	        response.setHeader("Cache-Control", "");
	        response.setHeader("Pragma", "");
	        response.setContentType("Content-type:application/octet-stream;");
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
	        response.setHeader("Content-Transfer-Encoding", "binary");
		} catch(Exception e) {
			throw new MvnoErrorException(e);
		} finally {
			if(cw != null){
		        try {
					cw.close();
				} catch (IOException e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		
		return sbFileName.toString();
	}
	
	/**
     * @Description : 엑셀다운로드 기능 - 100건씩 엑셀에 담고 메모리 초기화하는 방식으로 변경
     * @Param  : fileName
     * @Return : String
     * @Author : 한상욱
     * @Create Date : 2016. 2. 18.
     */
    @SuppressWarnings("unchecked")
    public String excelDownProcStream(String fileName
			, String sheetName
			, List<?> voLists
			, String[] strHead
			, int[] intWidth
			, String[] strValue
			, HttpServletRequest request
			, HttpServletResponse response
			, int[] intLen){
    	
       // streaming workbook 생성 - Excel 2007 이상 
        Workbook wb = new SXSSFWorkbook(100); // 100 row마다 파일로 flush

        // Sheet 생성 
        Sheet sheet1 = wb.createSheet(sheetName); 
         
        for (int i = 0; i < intWidth.length; i++) {
        	sheet1.setColumnWidth(i, intWidth[i]);
		}
          
        // Cell 스타일 생성 
        CellStyle cellStyleHead = wb.createCellStyle(); 
        CellStyle cellStyleMid = wb.createCellStyle(); 
        CellStyle cellStyleInt = wb.createCellStyle(); 

        // 줄 바꿈 
        cellStyleHead.setWrapText(true); 
          
        Font fontHead = wb.createFont();
        fontHead.setBoldweight((short) 1);
        fontHead.setFontName("맑은 고딕");
        fontHead.setColor(HSSFColor.BLACK.index);
        fontHead.setFontHeightInPoints((short) 12);
        
        Font fontMid = wb.createFont();
        fontMid.setBoldweight((short) 1);
        fontMid.setFontName("맑은 고딕");
        fontMid.setColor(HSSFColor.BLACK.index);
        fontMid.setFontHeightInPoints((short) 10);
        
        //제목
//        cellStyle.setFillForegroundColor(HSSFColor.LIME.index); 
        cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyleHead.setAlignment((short) 2);
        cellStyleHead.setFillPattern((short) 1);
        cellStyleHead.setFillPattern((short) 1);
        cellStyleHead.setBorderRight((short) 1);
//        cellStyleHead.setBorderLeft((short) 1);
        cellStyleHead.setBorderTop((short) 1);
        cellStyleHead.setBorderBottom((short) 1);
        cellStyleHead.setFont(fontHead);
        
        //내용
        cellStyleMid.setAlignment((short) 2);
        cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        cellStyleMid.setFillPattern((short) 1);
        cellStyleMid.setBorderRight((short) 1);
        cellStyleMid.setBorderLeft((short) 1);
        cellStyleMid.setBorderTop((short) 1);
        cellStyleMid.setBorderBottom((short) 1);
        cellStyleMid.setFont(fontMid);

        //숫자용
        XSSFDataFormat format = (XSSFDataFormat) wb.createDataFormat();

        cellStyleInt.setAlignment((short) 3);
        cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        cellStyleInt.setFillPattern((short) 1);
        cellStyleInt.setBorderRight((short) 1);
        cellStyleInt.setBorderLeft((short) 1);
        cellStyleInt.setBorderTop((short) 1);
        cellStyleInt.setBorderBottom((short) 1);
        cellStyleInt.setFont(fontMid);
        cellStyleInt.setDataFormat(format.getFormat("#,##0"));

        Row row = null; 
        Cell cell = null; 

        // 첫 번째 줄 제목 
        row = sheet1.createRow(0); 

        for (int i = 0; i < strHead.length; i++) {
        	cell = row.createCell(i); 
        	cell.setCellValue(strHead[i]);
        	cell.setCellStyle(cellStyleHead);
		}
        
     // 데이터 적재
 		try{
 			for (int i = 0; i < voLists.size(); i++) {
 				Map<String, Object> map = (Map<String, Object>) voLists.get(i);
 				row = sheet1.createRow(i + 1);
 				
 				for (int j = 0; j < strValue.length; j++) {
 					cell = row.createCell(j);
 					String strVal = "";
 					if(intLen[j] == 1){
 						strVal = map.get(strValue[j]) != null && !"".equals(String.valueOf(map.get(strValue[j]))) ? String.valueOf(map.get(strValue[j])) : "0";
 						
 						double d = Double.parseDouble(strVal);
 						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
 						cell.setCellValue(d);
 						cell.setCellStyle(cellStyleInt);
 					}else{
 						strVal = map.get(strValue[j]) != null && !"".equals((String) map.get(strValue[j])) ? map.get(strValue[j]).toString() : "";
 						cell.setCellValue(strVal);
 						cell.setCellStyle(cellStyleMid);
 					}
 				}
 			}
 		}catch(Exception e){
 			throw new MvnoErrorException(e);
 		}

		StringBuilder sbFileName = new StringBuilder();
		
        Date toDay = new Date();
        
        String strFileName = fileName;
        String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
        
        sbFileName.append(strFileName);
        //2015.12.09 엑셀다운로드 파일명에 ID 추가-곽대리요청
        sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID"));
        sbFileName.append("_");
        sbFileName.append(strToDay);
        sbFileName.append(".xlsx");

        String encodingFileName = "";

		int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
		
        try {
          encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
        } catch (UnsupportedEncodingException uee) {
          encodingFileName = sbFileName.toString();
        }

        response.setHeader("Cache-Control", "");
        response.setHeader("Pragma", "");

        response.setContentType("Content-type:application/octet-stream;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        
        File tempFile = new File(sbFileName.toString());
        
        try {
			bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			
			wb.write(bos);
			bos.flush();
			bos.close();
			/** 20230518 PMD 조치 */
			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());
		        
			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
		          bos.write(buf, 0, read);
			}
			bos.flush();
			        
			} catch (IOException e) {
				throw new MvnoErrorException(e);
		    } finally {
		        if (bis != null) {
		          try {
		            bis.close();
		          } catch (Exception e) {
		        	  throw new MvnoErrorException(e);
		          }
		        }
		        if (bos != null) {
		          try {
		            bos.close();
		            ((SXSSFWorkbook)wb).dispose();
		          } catch (Exception e) {
		        	  throw new MvnoErrorException(e);
		          }
		        }
		    }
        return sbFileName.toString();
	}
    
	// 엑셀저장
	public String saveExcel(String userId
			, String fileName
			, String sheetName
			, List<?> voLists
			, String[] strHead
			, int[] intWidth
			, String[] strValue
			, int[] intLen) {
		
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		log.debug("--- 엑셀저장시작 ---");
		
		
		StringBuilder sbFileName = new StringBuilder();
		StringBuilder teplateFileName = new StringBuilder();

		FileOutputStream os =null;
		Writer fw = null;
		FileOutputStream out = null;
		try {
			// Step 1. Create a template file. Setup sheets and workbook-level objects such as
			// cell styles, number formats, etc.
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(sheetName);
			
			Map<String, CellStyle> styles = createStyles(wb);
			String sheetRef = sheet.getPackagePart().getPartName().getName();

			Date toDay = new Date();
			
			String strFileName = fileName;
			String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
			sbFileName.append(strFileName);
			sbFileName.append(strToDay);
			sbFileName.append(".xlsx");

			//save the template
			// template 파일을 임시로 만들고 나서 다 끝나면 삭제하도록 한다.
//			teplateFileName.append(strFileName);
			teplateFileName.append(strToDay);
			teplateFileName.append("template");
			teplateFileName.append(".xlsx");
			log.debug("sbFileName.toString() = " + sbFileName.toString());
			log.debug("teplateFileName.toString() = " + teplateFileName.toString());
			os = new FileOutputStream(teplateFileName.toString());
//		        FileOutputStream os = new FileOutputStream("template.xlsx");
			wb.write(os);
			os.close();

			//Step 2. Generate XML file.
			File tmp = File.createTempFile("sheet", ".xml");
			fw = new OutputStreamWriter(new FileOutputStream(tmp), XML_ENCODING);
			generate(fw, styles, voLists, strHead, intWidth, strValue, intLen);
			fw.close();

			
			//Step 3. Substitute the template entry with the generated data
			out = new FileOutputStream(sbFileName.toString());
			File fTemp = new File(teplateFileName.toString());
//			substitute(new File(teplateFileName.toString()), tmp, sheetRef.substring(1), out);
			substitute(fTemp, tmp, sheetRef.substring(1), out);
			out.close();
			
			// 임시로 만든 template 파일을 삭제한다
			fTemp.delete();
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		} finally {
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					throw new MvnoErrorException(e);
				}
			}
			
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					throw new MvnoErrorException(e);
				}
			}
			
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		
		stopWatch.stop();
		log.debug("--- 엑셀저장끝 --- 걸린시간 : [" + stopWatch.getTotalTimeSeconds() + "]");
		
		// 엑셀파일 저장한 내역 DB에 남기기
		saveExcelHistory(sbFileName.toString(), userId);
		return sbFileName.toString();
		
	}
	
	/**
	 * Create a library of cell styles.
	 */
	
	public Map<String, CellStyle> createStyles(XSSFWorkbook wb){
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
//	        XSSFDataFormat fmt = wb.createDataFormat();
		
		// Cell 스타일 생성 
		CellStyle cellStyleHead = wb.createCellStyle(); 
		CellStyle cellStyleMid = wb.createCellStyle(); 
		CellStyle cellStyleInt = wb.createCellStyle(); 
		  
		// 줄 바꿈 
		cellStyleHead.setWrapText(true); 
		  
		XSSFFont fontHead = wb.createFont();
		fontHead.setBoldweight((short) 1);
		fontHead.setFontName("맑은 고딕");
		fontHead.setColor(HSSFColor.BLACK.index);
		fontHead.setFontHeightInPoints((short) 12);
		
		XSSFFont fontMid = wb.createFont();
		fontMid.setBoldweight((short) 1);
		fontMid.setFontName("맑은 고딕");
		fontMid.setColor(HSSFColor.BLACK.index);
		fontMid.setFontHeightInPoints((short) 10);
		
		//제목
//	        cellStyle.setFillForegroundColor(XSSFColor.LIME.index); 
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment((short) 2);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setBorderRight((short) 1);
//	        cellStyleHead.setBorderLeft((short) 1);
		cellStyleHead.setBorderTop((short) 1);
		cellStyleHead.setBorderBottom((short) 1);
		cellStyleHead.setFont(fontHead);
		styles.put("header", cellStyleHead);
		
		//내용
		cellStyleMid.setAlignment((short) 2);
		cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleMid.setFillPattern((short) 1);
		cellStyleMid.setBorderRight((short) 1);
		cellStyleMid.setBorderLeft((short) 1);
		cellStyleMid.setBorderTop((short) 1);
		cellStyleMid.setBorderBottom((short) 1);
		cellStyleMid.setFont(fontMid);
		styles.put("mid", cellStyleMid);
		
		//숫자용
		XSSFDataFormat format = wb.createDataFormat();
		
		cellStyleInt.setAlignment((short) 3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern((short) 1);
		cellStyleInt.setBorderRight((short) 1);
		cellStyleInt.setBorderLeft((short) 1);
		cellStyleInt.setBorderTop((short) 1);
		cellStyleInt.setBorderBottom((short) 1);
		cellStyleInt.setFont(fontMid);
		cellStyleInt.setDataFormat(format.getFormat("#,##0"));
		styles.put("number", cellStyleInt);

		return styles;
	}

	public void generate(Writer out, Map<String, CellStyle> styles
			, List<?> voLists
			, String[] strHead
			, int[] intWidth
			, String[] strValue
			, int[] intLen) {

		//SpreadsheetWriter sw = new SpreadsheetWriter(out);
		ExcelWriter sw = new ExcelWriter(out);
		
		try {
			sw.beginWorkSheet();
			
			// 칼럼 사이즈 변경
			List<SfExcelAttribute> customCellSizeList = new ArrayList<SfExcelAttribute>();
			for (int i = 1; i < intWidth.length+1; i++) {
				customCellSizeList.add(new SfExcelAttribute(i,i,intWidth[i-1],1));
			}
			
			sw.customCell(customCellSizeList);
			
			sw.beginSheetBond();

			//insert header row
			sw.insertRow(0);
			int styleIndex = styles.get("header").getIndex();
			
			for (int i = 0; i < strHead.length; i++) {
				sw.createCell(i, strHead[i], styleIndex);
			}

			sw.endRow();

			//write data rows
			for (int rownum = 1; rownum <= voLists.size(); rownum++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) voLists.get(rownum-1);
				sw.insertRow(rownum);
				
				for (int j = 0; j < strValue.length; j++) {
					String strVal = "";
					if(intLen[j] == 1){
						strVal = map.get(strValue[j]) != null && !"".equals(String.valueOf(map.get(strValue[j]))) ? String.valueOf(map.get(strValue[j])) : "0";
						sw.createCell(j, Double.parseDouble(strVal), styles.get("number").getIndex());
					}else{
						strVal = map.get(strValue[j]) != null && !"".equals(String.valueOf(map.get(strValue[j]))) ? String.valueOf(map.get(strValue[j])) : "";
						sw.createCell(j, strVal, styles.get("mid").getIndex());
					}
				}
				sw.endRow();
			}
			sw.endSheetBond();
			sw.endWorkSheet();
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
	}

	/**
	 *
	 * @param zipfile the template file
	 * @param tmpfile the XML file with the sheet data
	 * @param entry the name of the sheet entry to substitute, e.g. xl/worksheets/sheet1.xml
	 * @param out the stream to write the result to
	 */
	public void substitute(File zipfile, File tmpfile, String entry, OutputStream out) throws IOException {
		ZipFile zip = ZipHelper.openZipFile(zipfile);

		try {
			ZipOutputStream zos = new ZipOutputStream(out);

			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
			while (en.hasMoreElements()) {
				ZipEntry ze = en.nextElement();
				if(!ze.getName().equals(entry)){
					zos.putNextEntry(new ZipEntry(ze.getName()));
					/** 20230518 PMD 조치 */
					InputStream is = null;
					try {
						is = zip.getInputStream(ze);
						copyStream(is, zos);						
					}catch(Exception e) {
						throw new MvnoErrorException(e);
					}finally {
						is.close();
					}
				}
			}
			zos.putNextEntry(new ZipEntry(entry));
			
			InputStream is = null;
			try {
				is = new FileInputStream(tmpfile);
				copyStream(is, zos);
			} catch(Exception e) {
				throw new MvnoErrorException(e);
			} finally {
				if(is != null){
					is.close();
				}
			}

			zos.close();
		} finally {
			zip.close();
		}
	}

	public void copyStream(InputStream in, OutputStream out) throws IOException {
		byte[] chunk = new byte[1024];
		int count;
		while ((count = in.read(chunk)) >=0 ) {
		  out.write(chunk,0,count);
		}
	}

	// 엑셀저장 내용을 DB에 저장한다.
	public void saveExcelHistory(String sbFileName, String userId) {
		// 엑셀파일 저장한 내역 DB에 남기기
		Map<String, Object> param = new HashMap<String, Object>();
		File excelFile = new File(sbFileName);
		param.put("fileNm", excelFile.getName());
		param.put("filePath", excelFile.getPath());
		param.put("loginId", userId);
		fileDownMapper.insertExcelDown(param);
	}
	
	// 엑셀리스트 가져오기
	public List<?> selectExcelList(String userId, String startDt, String endDt, int pageIndex, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loginId", userId);
		param.put("searchStartDt", startDt);
		param.put("searchEndDt", endDt);
		param.put("pageIndex", pageIndex);
		param.put("pageSize", pageSize);
		
		List<EgovMap> result = (List<EgovMap>) fileDownMapper.selectExcelList(param);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userId);
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	// 엑셀파일명 가져오기
	public List<?> selectExcelFilePath(int excelDownSeqNum) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("excelDownSeqNum", excelDownSeqNum);
		return fileDownMapper.selectExcelFilePath(param);
	}
	// exclDnldId로 엑셀파일명 가져오기
	public List<?> selectExcelFilePathByExclDnldId(int exclDnldId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("exclDnldId", exclDnldId);
		return fileDownMapper.selectExcelFilePathByExclDnldId(param);
	}
	
	public int getSqCmnExclDnldHst() {
		return fileDownMapper.getSqCmnExclDnldHst();
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int insertCmnExclDnldHstVO(BatchJobVO vo) {
		return fileDownMapper.insertCmnExclDnldHstVO(vo);
	}
	
	/**
	 * @Description : 하나의 엑셀에 파일에 여러건의 sheet 생성
	 * @Param
	 * @Return
	 * @Author : 강무성
	 * @Create Date : 2017. 05. 11
	 */
	public String excelDownProcAry(String fileName
			, ArrayList<HashMap<String, Object>> excelInfo 
			, HttpServletRequest request
			, HttpServletResponse response){
		
		HSSFWorkbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전  
		
		String sheetName;
		Iterator<?> voLists;
		String[] strHead;
		String[] strValue;
		int[] intWidth;
		int[] intLen;
		
		for(int idx = 0 ; idx < excelInfo.size(); idx++){
			
			HashMap<String, Object> excelMap = excelInfo.get(idx);
			
			sheetName = (String)excelMap.get("sheetName");
			voLists   = (Iterator<?>)excelMap.get("voLists");
			strHead   = (String[])excelMap.get("strHead");
			strValue  = (String[])excelMap.get("strValue");
			intWidth  = (int[])excelMap.get("intWidth");
			intLen    = (int[])excelMap.get("intLen");
			
			// Sheet 생성 
			Sheet sheet1 = xlsWb.createSheet(sheetName); 
			 
			for (int i = 0; i < intWidth.length; i++) {
				sheet1.setColumnWidth(i, intWidth[i]);
			}
			  
			// Cell 스타일 생성 
			CellStyle cellStyleHead = xlsWb.createCellStyle(); 
			CellStyle cellStyleMid = xlsWb.createCellStyle(); 
			CellStyle cellStyleInt = xlsWb.createCellStyle(); 
			  
			// 줄 바꿈 
			cellStyleHead.setWrapText(true); 
			  
			HSSFFont fontHead = xlsWb.createFont();
			fontHead.setBoldweight((short) 1);
			fontHead.setFontName("맑은 고딕");
			fontHead.setColor(HSSFColor.BLACK.index);
			fontHead.setFontHeightInPoints((short) 12);
			
			HSSFFont fontMid = xlsWb.createFont();
			fontMid.setBoldweight((short) 1);
			fontMid.setFontName("맑은 고딕");
			fontMid.setColor(HSSFColor.BLACK.index);
			fontMid.setFontHeightInPoints((short) 10);
			
			//제목
			cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyleHead.setAlignment((short) 2);
			cellStyleHead.setFillPattern((short) 1);
			cellStyleHead.setFillPattern((short) 1);
			cellStyleHead.setBorderRight((short) 1);
			cellStyleHead.setBorderTop((short) 1);
			cellStyleHead.setBorderBottom((short) 1);
			cellStyleHead.setFont(fontHead);
			
			//내용
			cellStyleMid.setAlignment((short) 2);
			cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
			cellStyleMid.setFillPattern((short) 1);
			cellStyleMid.setBorderRight((short) 1);
			cellStyleMid.setBorderLeft((short) 1);
			cellStyleMid.setBorderTop((short) 1);
			cellStyleMid.setBorderBottom((short) 1);
			cellStyleMid.setFont(fontMid);
			
			//숫자용
			HSSFDataFormat format = xlsWb.createDataFormat();
			
			cellStyleInt.setAlignment((short) 3);
			cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
			cellStyleInt.setFillPattern((short) 1);
			cellStyleInt.setBorderRight((short) 1);
			cellStyleInt.setBorderLeft((short) 1);
			cellStyleInt.setBorderTop((short) 1);
			cellStyleInt.setBorderBottom((short) 1);
			cellStyleInt.setFont(fontMid);
			cellStyleInt.setDataFormat(format.getFormat("#,##0"));
			
			Row row = null; 
			Cell cell = null; 
	
			// 첫 번째 줄 제목 
			row = sheet1.createRow(0); 
			
			for (int i = 0; i < strHead.length; i++) {
				cell = row.createCell(i); 
				cell.setCellValue(strHead[i]);
				cell.setCellStyle(cellStyleHead);
			}
			
			
			// 데이터 적재
			try{
				int iCR = 0;
				while(voLists.hasNext()) {
					Object obj = voLists.next();
					Field[] fields = obj.getClass().getDeclaredFields();
					
					row = sheet1.createRow(iCR + 1);
					for (int j = 0; j < strValue.length; j++) {
						cell = row.createCell(j);
						String strVal = "";
						for(int i=0; i<=fields.length-1;i++){
							fields[i].setAccessible(true);
							if(intLen[j] == 1){
								if(strValue[j].equals(fields[i].getName())) {
									strVal = fields[i].get(obj) != null && !"".equals(String.valueOf(fields[i].get(obj))) ? String.valueOf(fields[i].get(obj)) : "0";
									
									double d = Double.parseDouble(strVal);
									cell.setCellType(Cell.CELL_TYPE_NUMERIC);
									cell.setCellValue(d);
									cell.setCellStyle(cellStyleInt);
								}
							} else {
								if(strValue[j].equals(fields[i].getName())) {
									strVal = fields[i].get(obj) != null && !"".equals((String) fields[i].get(obj)) ? fields[i].get(obj).toString() : "";
									
									cell.setCellValue(strVal);
									cell.setCellStyle(cellStyleMid);
								}
							}
						}
					}
					iCR++;
				}
			}catch(Exception e){
				throw new MvnoErrorException(e);
			}
		}//End For idx
	
		StringBuilder sbFileName = new StringBuilder();
		
		Date toDay = new Date();
		
		String strFileName = fileName;
		String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
		
		sbFileName.append(strFileName);
		
		//2015.12.09 엑셀다운로드 파일명에 ID 추가-곽대리요청
        sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID"));
        sbFileName.append("_");
		sbFileName.append(strToDay);
		sbFileName.append(".xls");
		
		String encodingFileName = "";

		int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
		
		try {
			encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			encodingFileName = sbFileName.toString();
		}

		response.setHeader("Cache-Control", "");
		response.setHeader("Pragma", "");

		response.setContentType("Content-type:application/octet-stream;");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		/** 20230518 PMD 조치 */
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		File tempFile = new File(sbFileName.toString());
		
		try {
			bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			
			xlsWb.write(bos);
			bos.flush();
			bos.close();
			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());
				
			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			bos.flush();
					
		} catch (IOException e) {
			throw new MvnoErrorException(e);
		} finally {
			if (bis != null){
				try {
					bis.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		
		return sbFileName.toString();
	}
	
	
	/**
	 * @Description : 엑셀 파일 생성 시 셀 병합(제목만) 기능 추가
	 * @Param  : fileName
	 * @Return : String
	 * @Author : 강무성
	 * @Create Date : 2017. 07. 07.
	 */
	public String excelCellMergedDownProc(String fileName
			, String sheetName
			, Iterator<?> voLists
			, ArrayList<String[]> aryHead
			, ArrayList<int[]> aryMerged
			, int[] intWidth
			, String[] strValue
			, HttpServletRequest request
			, HttpServletResponse response
			, int[] intLen){
		
		HSSFWorkbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전 
		
		// Sheet 생성 
		Sheet sheet1 = xlsWb.createSheet(sheetName); 
		
		for (int i = 0; i < intWidth.length; i++) {
			sheet1.setColumnWidth(i, intWidth[i]);
		}
		
		// Cell 스타일 생성 
		CellStyle cellStyleHead = xlsWb.createCellStyle(); 
		CellStyle cellStyleMid = xlsWb.createCellStyle(); 
		CellStyle cellStyleInt = xlsWb.createCellStyle(); 
		  
		// 줄 바꿈 
		cellStyleHead.setWrapText(true); 
		  
		HSSFFont fontHead = xlsWb.createFont();
		fontHead.setBoldweight((short) 1);
		fontHead.setFontName("맑은 고딕");
		fontHead.setColor(HSSFColor.BLACK.index);
		fontHead.setFontHeightInPoints((short) 12);
		
		HSSFFont fontMid = xlsWb.createFont();
		fontMid.setBoldweight((short) 1);
		fontMid.setFontName("맑은 고딕");
		fontMid.setColor(HSSFColor.BLACK.index);
		fontMid.setFontHeightInPoints((short) 10);
		
		//제목
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment((short) 2);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setBorderRight((short) 1);
		cellStyleHead.setBorderTop((short) 1);
		cellStyleHead.setBorderBottom((short) 1);
		cellStyleHead.setFont(fontHead);
		
		//내용
		cellStyleMid.setAlignment((short) 2);
		cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleMid.setFillPattern((short) 1);
		cellStyleMid.setBorderRight((short) 1);
		cellStyleMid.setBorderLeft((short) 1);
		cellStyleMid.setBorderTop((short) 1);
		cellStyleMid.setBorderBottom((short) 1);
		cellStyleMid.setFont(fontMid);
		
		//숫자용
		HSSFDataFormat format = xlsWb.createDataFormat();
		
		cellStyleInt.setAlignment((short) 3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern((short) 1);
		cellStyleInt.setBorderRight((short) 1);
		cellStyleInt.setBorderLeft((short) 1);
		cellStyleInt.setBorderTop((short) 1);
		cellStyleInt.setBorderBottom((short) 1);
		cellStyleInt.setFont(fontMid);
		cellStyleInt.setDataFormat(format.getFormat("#,##0"));
		
		Row row = null; 
		Cell cell = null; 
		
		// 제목 생성(다건 처리) 
		for(int nIdxH = 0; nIdxH < aryHead.size(); nIdxH++){
			String[] strHead = aryHead.get(nIdxH);
			
			row = sheet1.createRow(nIdxH); 
			
			for (int i = 0; i < strHead.length; i++) {
				cell = row.createCell(i); 
				cell.setCellValue(strHead[i]);
				cell.setCellStyle(cellStyleHead);
			}
		}
		
		// 제목 셀 병합
		for(int nIdxM = 0; nIdxM < aryMerged.size(); nIdxM++){
			int[] nMerged = aryMerged.get(nIdxM); //시작행, 마지막행, 시작열, 마지막열
			
			sheet1.addMergedRegion(new CellRangeAddress(nMerged[0],nMerged[1],nMerged[2],nMerged[3]));
		}
		
		// 데이터 적재
		try{
			int iCR = aryHead.size(); //제목 줄 다음 행 부터 데이터 생성
			while(voLists.hasNext()) {
				Object obj = voLists.next();
				Field[] fields = obj.getClass().getDeclaredFields();
				
				row = sheet1.createRow(iCR);
				for (int j = 0; j < strValue.length; j++) {
					cell = row.createCell(j);
					String strVal = "";
					for(int i=0; i<=fields.length-1;i++){
						fields[i].setAccessible(true);
						if(intLen[j] == 1){
							if(strValue[j].equals(fields[i].getName())) {
								strVal = fields[i].get(obj) != null && !"".equals(String.valueOf(fields[i].get(obj))) ? String.valueOf(fields[i].get(obj)) : "0";
								
								double d = Double.parseDouble(strVal);
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);
								cell.setCellValue(d);
								cell.setCellStyle(cellStyleInt);
							}
						} else {
							if(strValue[j].equals(fields[i].getName())) {
								strVal = fields[i].get(obj) != null && !"".equals((String) fields[i].get(obj)) ? fields[i].get(obj).toString() : "";
								
								cell.setCellValue(strVal);
								cell.setCellStyle(cellStyleMid);
							}
						}
					}
				}
				iCR++;
			}
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		
		StringBuilder sbFileName = new StringBuilder();
		
		Date toDay = new Date();
		
		String strFileName = fileName;
		String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
		
		sbFileName.append(strFileName);
		
		sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID"));
		sbFileName.append("_");
		sbFileName.append(strToDay);
		sbFileName.append(".xls");
		
		String encodingFileName = "";
		
		int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
		
		try {
			encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			encodingFileName = sbFileName.toString();
		}
		
		response.setHeader("Cache-Control", "");
		response.setHeader("Pragma", "");
		
		response.setContentType("Content-type:application/octet-stream;");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		File tempFile = new File(sbFileName.toString());
		
		try {
			bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			
			xlsWb.write(bos);
			bos.flush();
			bos.close();
			/** 20230518 PMD 조치 */
			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());
			
			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			bos.flush();
			
		} catch (IOException e) {
			throw new MvnoErrorException(e);
		} finally {
			if (bis != null){
				try {
					bis.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		
		return sbFileName.toString();
	}
	
}
