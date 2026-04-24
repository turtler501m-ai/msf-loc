package com.ktis.msp.batch.manager.common.filedown;

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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.opc.internal.ZipHelper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.util.StringUtil;

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

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	private static final String XML_ENCODING = "UTF-8";

	private static final short SHORT1 = 1;
	private static final short SHORT2 = 2;
	private static final short SHORT3 = 3;
	private static final short SHORT10 = 10;
	private static final short SHORT12 = 12;

	/**
	 * <PRE>
	 * 1. MethodName: insertCmnFileDnldMgmtMst
	 * 2. ClassName	: FileDownService
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:54:31
	 * </PRE>
	 * 
	 * @return int
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertCmnFileDnldMgmtMst(Map<String, Object> pRequestParamMap) {

		return fileDownMapper.insertCmnFileDnldMgmtMst(pRequestParamMap);
	}
	
	public boolean getFileMgmtById(HttpServletRequest pRequest,
			HttpServletResponse pResponse, Map<String, Object> pRequestParamMap) {

		try {

			// ---------------------------------
			// DB에서 file 정보 가져오기
			// ---------------------------------
			List<?> resultList = fileDownMapper
					.getFileMgmtById(pRequestParamMap);

			if (resultList.size() == 0) {
				return false;
			}

			EgovMap lRow = (EgovMap) resultList.get(0);

			// ---------------------------------
			//
			// ---------------------------------
			String lBaseDir = propertyService
					.getString("fileUploadBaseDirectory");

			// =====================
			pResponse.setContentType("application/octet-stream;charset=utf-8");
			pResponse.setCharacterEncoding("UTF-8");

			// --------------------------------
			// 파일 이름
			// --------------------------------
			String lFileName = StringUtils.defaultString(
					(String) lRow.get("fileNm"), "no_file_name");
			lFileName = URLDecoder.decode(lFileName, "UTF-8");

			// --------------------------------
			// PC 파일 이름
			// --------------------------------
			String lFileNamePc = StringUtils.defaultString(
					(String) lRow.get("fileNmPc"), "no_file_name");
			lFileNamePc = URLDecoder.decode(lFileNamePc, "UTF-8");

			// --------------------------------
			// 서버의 경로(모율별)
			// --------------------------------
			String lFilePath = StringUtils.defaultString(
					(String) lRow.get("fileRout"), "no_path");
			if (lFilePath.contains("..")) {
				return false;
			}

			int lFileSize = 0;

			// --------------------------------
			// 파일명이 없으면 return
			// --------------------------------
			if (lFileName == null) {
				return false;
			}

			String fileNameFullPath = lBaseDir + lFilePath + "/" + lFileName;
			File file = new File(fileNameFullPath);
			String lFileNamePcEncode = URLEncoder.encode(lFileNamePc, "UTF-8");

			// --------------------------------
			// 파일이 존재하면 down 시작
			// --------------------------------
			if (file.exists()) {
				// out.clear();
				// out = pageContext.pushBody();

				pResponse.setHeader("Content-Disposition",
						"attachment;filename=" + lFileNamePcEncode);
				lFileSize = (int) file.length();
				byte b[] = new byte[lFileSize];
				// byte b[] = new byte[4062];

				BufferedInputStream fin = new BufferedInputStream(
						new FileInputStream(file));
				BufferedOutputStream outs = new BufferedOutputStream(
						pResponse.getOutputStream());

				int read = 0;
				while ((read = fin.read(b)) != -1) {
					outs.write(b, 0, read);
				}

				outs.close();
				fin.close();

				if (StringUtils.defaultString(
						(String) pRequestParamMap.get("writeFileUpDownLog"),
						"N").equals("Y")) {
					pRequestParamMap.put("FILE_NM", lFileName);

					String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

					if (ipAddr == null || ipAddr.length() == 0
							|| ipAddr.equalsIgnoreCase("unknown"))
						ipAddr = pRequest.getHeader("REMOTE_ADDR");

					if (ipAddr == null || ipAddr.length() == 0
							|| ipAddr.equalsIgnoreCase("unknown"))
						ipAddr = pRequest.getRemoteAddr();

					pRequestParamMap.put("IP_INFO", ipAddr);
					pRequestParamMap.put("DUTY_NM", "");
					pRequestParamMap.put("FILE_ROUT", fileNameFullPath);
					pRequestParamMap.put("FILE_SIZE", lFileSize);

					pRequestParamMap.put("DATA_CNT", 0);

					insertCmnFileDnldMgmtMst(pRequestParamMap);
				}
			} else {

				pResponse.setContentType("text/html;charset=utf-8");
				pResponse.getWriter().println(lFileName + " : not exists.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @Description : 엑셀다운로드 기능
	 * @Param : fileName
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@SuppressWarnings("unchecked")
	public String excelDownProc(String fileName, String sheetName,
			List<?> voLists, String[] strHead, int[] intWidth,
			String[] strValue, HttpServletRequest request,
			HttpServletResponse response, int[] intLen) {

		HSSFWorkbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전
		// Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상

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
		fontHead.setBoldweight(SHORT1);
		fontHead.setFontName("맑은 고딕");
		fontHead.setColor(HSSFColor.BLACK.index);
		fontHead.setFontHeightInPoints(SHORT12);

		HSSFFont fontMid = xlsWb.createFont();
		fontMid.setBoldweight(SHORT1);
		fontMid.setFontName("맑은 고딕");
		fontMid.setColor(HSSFColor.BLACK.index);
		fontMid.setFontHeightInPoints(SHORT10);

		// 제목
		// cellStyle.setFillForegroundColor(HSSFColor.LIME.index);
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment(SHORT2);
		cellStyleHead.setFillPattern(SHORT1);
		cellStyleHead.setFillPattern(SHORT1);
		cellStyleHead.setBorderRight(SHORT1);
		// cellStyleHead.setBorderLeft(SHORT1);
		cellStyleHead.setBorderTop(SHORT1);
		cellStyleHead.setBorderBottom(SHORT1);
		cellStyleHead.setFont(fontHead);

		// 내용
		cellStyleMid.setAlignment(SHORT2);
		cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleMid.setFillPattern(SHORT1);
		cellStyleMid.setBorderRight(SHORT1);
		cellStyleMid.setBorderLeft(SHORT1);
		cellStyleMid.setBorderTop(SHORT1);
		cellStyleMid.setBorderBottom(SHORT1);
		cellStyleMid.setFont(fontMid);

		// 숫자용
		HSSFDataFormat format = xlsWb.createDataFormat();

		cellStyleInt.setAlignment(SHORT3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern(SHORT1);
		cellStyleInt.setBorderRight(SHORT1);
		cellStyleInt.setBorderLeft(SHORT1);
		cellStyleInt.setBorderTop(SHORT1);
		cellStyleInt.setBorderBottom(SHORT1);
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
		try {
			for (int i = 0; i < voLists.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) voLists.get(i);
				row = sheet1.createRow(i + 1);

				for (int j = 0; j < strValue.length; j++) {
					cell = row.createCell(j);
					String strVal = "";
					if (intLen[j] == 1) {
						strVal = map.get(strValue[j]) != null
								&& !"".equals(String.valueOf(map
										.get(strValue[j]))) ? String
								.valueOf(map.get(strValue[j])) : "0";

						double d = Double.parseDouble(strVal);
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(d);
						cell.setCellStyle(cellStyleInt);
					} else {
						strVal = map.get(strValue[j]) != null
								&& !"".equals((String) map.get(strValue[j])) ? map
								.get(strValue[j]).toString() : "";
						cell.setCellValue(strVal);
						cell.setCellStyle(cellStyleMid);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringBuilder sbFileName = new StringBuilder();

		Date toDay = new Date();

		String strFileName = fileName;
		String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);

		sbFileName.append(strFileName);

		// 2015.12.09 엑셀다운로드 파일명에 ID 추가-곽대리요청
		sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID"));
		sbFileName.append("_");
		sbFileName.append(strToDay);
		sbFileName.append(".xls");

		String encodingFileName = "";

		int serverInfoLen = Integer.parseInt(propertyService
				.getString("excelPathLen"));

		try {
			encodingFileName = URLEncoder.encode(sbFileName.toString()
					.substring(serverInfoLen), "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			encodingFileName = sbFileName.toString();
		}

		response.setHeader("Cache-Control", "");
		response.setHeader("Pragma", "");

		response.setContentType("Content-type:application/octet-stream;");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ encodingFileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		File tempFile = new File(sbFileName.toString());

		try {
			bos = new BufferedOutputStream(new FileOutputStream(tempFile));

			xlsWb.write(bos);
			bos.flush();
			// bos.close();

			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			bos.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return sbFileName.toString();
	}

	/**
	 * @Description : 엑셀다운로드 기능 - 100건씩 엑셀에 담고 메모리 초기화하는 방식으로 변경
	 * @Param : fileName
	 * @Return : String
	 * @Author : 한상욱
	 * @Create Date : 2016. 2. 18.
	 */
	@SuppressWarnings("unchecked")
	public String excelDownProcStream(String fileName, String sheetName,
			List<?> voLists, String[] strHead, int[] intWidth,
			String[] strValue, HttpServletRequest request,
			HttpServletResponse response, int[] intLen) {

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
		fontHead.setBoldweight(SHORT1);
		fontHead.setFontName("맑은 고딕");
		fontHead.setColor(HSSFColor.BLACK.index);
		fontHead.setFontHeightInPoints(SHORT12);

		Font fontMid = wb.createFont();
		fontMid.setBoldweight(SHORT1);
		fontMid.setFontName("맑은 고딕");
		fontMid.setColor(HSSFColor.BLACK.index);
		fontMid.setFontHeightInPoints(SHORT10);

		// 제목
		// cellStyle.setFillForegroundColor(HSSFColor.LIME.index);
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment(SHORT2);
		cellStyleHead.setFillPattern(SHORT1);
		cellStyleHead.setFillPattern(SHORT1);
		cellStyleHead.setBorderRight(SHORT1);
		// cellStyleHead.setBorderLeft(SHORT1);
		cellStyleHead.setBorderTop(SHORT1);
		cellStyleHead.setBorderBottom(SHORT1);
		cellStyleHead.setFont(fontHead);

		// 내용
		cellStyleMid.setAlignment(SHORT2);
		cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleMid.setFillPattern(SHORT1);
		cellStyleMid.setBorderRight(SHORT1);
		cellStyleMid.setBorderLeft(SHORT1);
		cellStyleMid.setBorderTop(SHORT1);
		cellStyleMid.setBorderBottom(SHORT1);
		cellStyleMid.setFont(fontMid);

		// 숫자용
		XSSFDataFormat format = (XSSFDataFormat) wb.createDataFormat();

		cellStyleInt.setAlignment(SHORT3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern(SHORT1);
		cellStyleInt.setBorderRight(SHORT1);
		cellStyleInt.setBorderLeft(SHORT1);
		cellStyleInt.setBorderTop(SHORT1);
		cellStyleInt.setBorderBottom(SHORT1);
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
		try {
			for (int i = 0; i < voLists.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) voLists.get(i);
				row = sheet1.createRow(i + 1);

				for (int j = 0; j < strValue.length; j++) {
					cell = row.createCell(j);
					String strVal = "";
					if (intLen[j] == 1) {
						strVal = map.get(strValue[j]) != null
								&& !"".equals(String.valueOf(map
										.get(strValue[j]))) ? String
								.valueOf(map.get(strValue[j])) : "0";

						double d = Double.parseDouble(strVal);
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(d);
						cell.setCellStyle(cellStyleInt);
					} else {
						strVal = map.get(strValue[j]) != null
								&& !"".equals((String) map.get(strValue[j])) ? map
								.get(strValue[j]).toString() : "";
						cell.setCellValue(strVal);
						cell.setCellStyle(cellStyleMid);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringBuilder sbFileName = new StringBuilder();

		Date toDay = new Date();

		String strFileName = fileName;
		String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);

		sbFileName.append(strFileName);
		// 2015.12.09 엑셀다운로드 파일명에 ID 추가-곽대리요청
		sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID"));
		sbFileName.append("_");
		sbFileName.append(strToDay);
		sbFileName.append(".xlsx");

		String encodingFileName = "";

		int serverInfoLen = Integer.parseInt(propertyService
				.getString("excelPathLen"));

		try {
			encodingFileName = URLEncoder.encode(sbFileName.toString()
					.substring(serverInfoLen), "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			encodingFileName = sbFileName.toString();
		}

		response.setHeader("Cache-Control", "");
		response.setHeader("Pragma", "");

		response.setContentType("Content-type:application/octet-stream;");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ encodingFileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		File tempFile = new File(sbFileName.toString());

		try {
			bos = new BufferedOutputStream(new FileOutputStream(tempFile));

			wb.write(bos);
			bos.flush();
			// bos.close();

			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			bos.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
					((SXSSFWorkbook) wb).dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return sbFileName.toString();
	}

	// 엑셀저장
	public String saveExcel(String userId, String fileName, String sheetName,
			List<?> voLists, String[] strHead, int[] intWidth,
			String[] strValue, int[] intLen) {

		Date toDay = new Date();
		String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);

		return this.saveExcel(userId, fileName, sheetName, voLists, strHead,
				intWidth, strValue, intLen, strToDay);
	}

	// 엑셀저장
	public String saveExcel(String userId, String fileName, String sheetName,
			List<?> voLists, String[] strHead, int[] intWidth,
			String[] strValue, int[] intLen, String strToDay) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		LOGGER.debug("--- 엑셀저장시작 ---");

		StringBuilder sbFileName = new StringBuilder();
		StringBuilder teplateFileName = new StringBuilder();

		try {
			// Step 1. Create a template file. Setup sheets and workbook-level
			// objects such as
			// cell styles, number formats, etc.
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(sheetName);

			Map<String, CellStyle> styles = createStyles(wb);
			String sheetRef = sheet.getPackagePart().getPartName().getName();

			String strFileName = fileName;
			sbFileName.append(strFileName);
			sbFileName.append(strToDay);
			sbFileName.append(".xlsx");

			// save the template
			// template 파일을 임시로 만들고 나서 다 끝나면 삭제하도록 한다.
			teplateFileName.append(strFileName);
			teplateFileName.append(strToDay);
			teplateFileName.append("template");
			teplateFileName.append(".xlsx");
			LOGGER.debug("sbFileName.toString() = {}", sbFileName.toString());
			LOGGER.debug("teplateFileName.toString() = {}", teplateFileName.toString());
			FileOutputStream os = new FileOutputStream(teplateFileName.toString());
			// FileOutputStream os = new FileOutputStream("template.xlsx");
			wb.write(os);
			os.close();

			// Step 2. Generate XML file.
			File tmp = File.createTempFile("sheet", ".xml");
			Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), XML_ENCODING);
			generate(fw, styles, voLists, strHead, intWidth, strValue, intLen);
			fw.close();

			// Step 3. Substitute the template entry with the generated data
			FileOutputStream out = new FileOutputStream(sbFileName.toString());
			File fTemp = new File(teplateFileName.toString());
			substitute(fTemp, tmp, sheetRef.substring(1), out);
			out.close();
			
			// 임시로 만든 template 파일을 삭제한다
			fTemp.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

		stopWatch.stop();
		LOGGER.debug("--- 엑셀저장끝 --- 걸린시간 : [{}]", stopWatch.getTotalTimeSeconds());

		// 엑셀파일 저장한 내역 DB에 남기기
		saveExcelHistory(sbFileName.toString(), userId);
		return sbFileName.toString();

	}
	
	
	// 엑셀저장 (POI에서 지원하는 OutputStream 방식의 workbook객체 SXSSFWorkbook 사용)
	@SuppressWarnings("unchecked")
	public String saveBigExcel(String userId, String fileName, String sheetName,
			List<?> voLists, String[] strHead, int[] intWidth,
			String[] strValue, int[] intLen, String strToDay) throws MvnoServiceException {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		LOGGER.debug("--- 엑셀저장시작 ---");
		
		StringBuilder sbFileName = new StringBuilder();
//		StringBuilder teplateFileName = new StringBuilder();
		
		// out of memory 방지위해 fetchSize만큼 쪼개서 출력
		int fetchSize = 100;
		try {
			
			// Step 1. Create a template file. Setup sheets and workbook-level
			// objects such as
			// cell styles, number formats, etc.
			SXSSFWorkbook wb = new SXSSFWorkbook(fetchSize);
			SXSSFSheet sheet = (SXSSFSheet) wb.createSheet(sheetName);
			
			Map<String, CellStyle> styles = createStyles(wb);
			
			// 
			Row row = null;
			Cell cell = null;
			
			// 첫 번째 줄 제목
			row = sheet.createRow(0);
			
			int[] setColWid = new int[strHead.length];
			
			for (int i = 0; i < strHead.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(strHead[i]);
				cell.setCellStyle(styles.get("header"));
				setColWid[i] = (int) (strHead[i].length()*3*256);
				//LOGGER.debug("~~~~~~~~~~>>>>>>>>>>>>>>>>>>> header:{}, width:{}",strHead[i], sheet.getColumnWidth(i));
			}
			
			// 데이터 적재
			for (int i = 0; i < voLists.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) voLists.get(i);
				row = sheet.createRow(i + 1);
				
				for (int j = 0; j < strValue.length; j++) {
					cell = row.createCell(j);
					String strVal = "";
					if (intLen[j] == 1) {
						strVal = map.get(strValue[j]) != null && !"".equals(String.valueOf(map.get(strValue[j]))) ? String.valueOf(map.get(strValue[j])) : "0";
						double d = Double.parseDouble(strVal);
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(d);
						cell.setCellStyle(styles.get("number"));
					} else if (intLen[j] == 2) {
						strVal = map.get(strValue[j]) != null && !"".equals((String) map.get(strValue[j])) ? map.get(strValue[j]).toString() : "";
						cell.setCellValue(strVal);
						cell.setCellStyle(styles.get("left"));
					} else {
						strVal = map.get(strValue[j]) != null && !"".equals((String) map.get(strValue[j])) ? map.get(strValue[j]).toString() : "";
						cell.setCellValue(strVal);
						cell.setCellStyle(styles.get("mid"));
					}
					
				}
			}
			
			for (int i = 0; i < strHead.length; i++) {
				sheet.autoSizeColumn(i);
				if(sheet.getColumnWidth(i) < setColWid[i]) {
					sheet.setColumnWidth(i, setColWid[i]);
				}
			}
			
			
			String strFileName = fileName;
			sbFileName.append(strFileName);
			sbFileName.append(strToDay);
			sbFileName.append(".xlsx");
			
			FileOutputStream out = new FileOutputStream(sbFileName.toString());
			wb.write(out);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new MvnoServiceException(e.getMessage(), e);
		}

		stopWatch.stop();
		LOGGER.debug("--- 엑셀저장끝 --- 걸린시간 : [{}]", stopWatch.getTotalTimeSeconds());

		// 엑셀파일 저장한 내역 DB에 남기기
		saveExcelHistory(sbFileName.toString(), userId);
		return sbFileName.toString();

	}
	
	
	
	/**
	 * Create a library of cell styles.
	 */

	public Map<String, CellStyle> createStyles(XSSFWorkbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		// XSSFDataFormat fmt = wb.createDataFormat();

		// Cell 스타일 생성
		CellStyle cellStyleHead = wb.createCellStyle();
		CellStyle cellStyleMid = wb.createCellStyle();
		CellStyle cellStyleInt = wb.createCellStyle();
		CellStyle cellStyleLeft = wb.createCellStyle();

		// 줄 바꿈
		cellStyleHead.setWrapText(true);

		XSSFFont fontHead = wb.createFont();
		fontHead.setBoldweight(SHORT1);
		fontHead.setFontName("맑은 고딕");
		fontHead.setColor(HSSFColor.BLACK.index);
		fontHead.setFontHeightInPoints(SHORT12);

		XSSFFont fontMid = wb.createFont();
		fontMid.setBoldweight(SHORT1);
		fontMid.setFontName("맑은 고딕");
		fontMid.setColor(HSSFColor.BLACK.index);
		fontMid.setFontHeightInPoints(SHORT10);
		
		XSSFFont fontLeft = wb.createFont();
		fontLeft.setBoldweight(SHORT1);
		fontLeft.setFontName("맑은 고딕");
		fontLeft.setColor(HSSFColor.BLACK.index);
		fontLeft.setFontHeightInPoints(SHORT10);

		// 제목
		// cellStyle.setFillForegroundColor(XSSFColor.LIME.index);
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment(SHORT2);
		cellStyleHead.setFillPattern(SHORT1);
		cellStyleHead.setFillPattern(SHORT1);
		cellStyleHead.setBorderRight(SHORT1);
		// cellStyleHead.setBorderLeft(SHORT1);
		cellStyleHead.setBorderTop(SHORT1);
		cellStyleHead.setBorderBottom(SHORT1);
		cellStyleHead.setFont(fontHead);
		styles.put("header", cellStyleHead);

		// 내용
		cellStyleMid.setAlignment(SHORT2);
		cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleMid.setFillPattern(SHORT1);
		cellStyleMid.setBorderRight(SHORT1);
		cellStyleMid.setBorderLeft(SHORT1);
		cellStyleMid.setBorderTop(SHORT1);
		cellStyleMid.setBorderBottom(SHORT1);
		cellStyleMid.setFont(fontMid);
		styles.put("mid", cellStyleMid);
		
		// 내용2
		cellStyleLeft.setAlignment(SHORT1);
		cellStyleLeft.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleLeft.setFillPattern(SHORT1);
		cellStyleLeft.setBorderRight(SHORT1);
		cellStyleLeft.setBorderLeft(SHORT1);
		cellStyleLeft.setBorderTop(SHORT1);
		cellStyleLeft.setBorderBottom(SHORT1);
		cellStyleLeft.setFont(fontMid);
		styles.put("left", cellStyleLeft);

		// 숫자용
		XSSFDataFormat format = wb.createDataFormat();

		cellStyleInt.setAlignment(SHORT3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern(SHORT1);
		cellStyleInt.setBorderRight(SHORT1);
		cellStyleInt.setBorderLeft(SHORT1);
		cellStyleInt.setBorderTop(SHORT1);
		cellStyleInt.setBorderBottom(SHORT1);
		cellStyleInt.setFont(fontMid);
		cellStyleInt.setDataFormat(format.getFormat("#,##0"));
		styles.put("number", cellStyleInt);

		return styles;
	}
	
	public Map<String, CellStyle> createStyles(SXSSFWorkbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		// XSSFDataFormat fmt = wb.createDataFormat();

		// Cell 스타일 생성
		CellStyle cellStyleHead = wb.createCellStyle();
		CellStyle cellStyleMid = wb.createCellStyle();
		CellStyle cellStyleInt = wb.createCellStyle();
		CellStyle cellStyleLeft = wb.createCellStyle();

		// 줄 바꿈
		cellStyleHead.setWrapText(true);

		Font fontHead = wb.createFont();
		fontHead.setBoldweight(SHORT1);
		fontHead.setFontName("맑은 고딕");
		fontHead.setColor(HSSFColor.BLACK.index);
		fontHead.setFontHeightInPoints(SHORT12);

		Font fontMid = wb.createFont();
		fontMid.setBoldweight(SHORT1);
		fontMid.setFontName("맑은 고딕");
		fontMid.setColor(HSSFColor.BLACK.index);
		fontMid.setFontHeightInPoints(SHORT10);
		
		Font fontLeft = wb.createFont();
		fontLeft.setBoldweight(SHORT1);
		fontLeft.setFontName("맑은 고딕");
		fontLeft.setColor(HSSFColor.BLACK.index);
		fontLeft.setFontHeightInPoints(SHORT10);

		// 제목
		// cellStyle.setFillForegroundColor(XSSFColor.LIME.index);
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment(SHORT2);
		cellStyleHead.setFillPattern(SHORT1);
		cellStyleHead.setFillPattern(SHORT1);
		cellStyleHead.setBorderRight(SHORT1);
		// cellStyleHead.setBorderLeft(SHORT1);
		cellStyleHead.setBorderTop(SHORT1);
		cellStyleHead.setBorderBottom(SHORT1);
		cellStyleHead.setFont(fontHead);
		styles.put("header", cellStyleHead);

		// 내용
		cellStyleMid.setAlignment(SHORT2);
		cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleMid.setFillPattern(SHORT1);
		cellStyleMid.setBorderRight(SHORT1);
		cellStyleMid.setBorderLeft(SHORT1);
		cellStyleMid.setBorderTop(SHORT1);
		cellStyleMid.setBorderBottom(SHORT1);
		cellStyleMid.setFont(fontMid);
		styles.put("mid", cellStyleMid);
		
		// 내용2
		cellStyleLeft.setAlignment(SHORT1);
		cellStyleLeft.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleLeft.setFillPattern(SHORT1);
		cellStyleLeft.setBorderRight(SHORT1);
		cellStyleLeft.setBorderLeft(SHORT1);
		cellStyleLeft.setBorderTop(SHORT1);
		cellStyleLeft.setBorderBottom(SHORT1);
		cellStyleLeft.setFont(fontMid);
		styles.put("left", cellStyleLeft);

		// 숫자용
		DataFormat format = wb.createDataFormat();

		cellStyleInt.setAlignment(SHORT3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern(SHORT1);
		cellStyleInt.setBorderRight(SHORT1);
		cellStyleInt.setBorderLeft(SHORT1);
		cellStyleInt.setBorderTop(SHORT1);
		cellStyleInt.setBorderBottom(SHORT1);
		cellStyleInt.setFont(fontMid);
		cellStyleInt.setDataFormat(format.getFormat("#,##0"));
		styles.put("number", cellStyleInt);

		return styles;
	}
	
	// XSSFCellStyle 스타일을 만든다.
	public Map<String, XSSFCellStyle> createStyles2(XSSFWorkbook wb){
		
		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
		
		// Cell 스타일 생성 
		XSSFCellStyle cellStyleHead = wb.createCellStyle(); 
		XSSFCellStyle cellStyleMid = wb.createCellStyle(); 
		XSSFCellStyle cellStyleInt = wb.createCellStyle(); 
		XSSFCellStyle cellStyleData = wb.createCellStyle(); 

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
//		        cellStyle.setFillForegroundColor(XSSFColor.LIME.index); 
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment((short) 2);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setBorderRight((short) 1);
//		        cellStyleHead.setBorderLeft((short) 1);
		cellStyleHead.setBorderTop((short) 1);
		cellStyleHead.setBorderBottom((short) 1);
		cellStyleHead.setFont(fontHead);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
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
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
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
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleInt.setDataFormat(format.getFormat("#,##0"));
		styles.put("number", cellStyleInt);


		//수식용
		cellStyleData.setWrapText(true); 
		cellStyleData.setAlignment((short) 3);
		cellStyleData.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleData.setFillPattern((short) 1);
		cellStyleData.setBorderRight((short) 1);
		cellStyleData.setBorderLeft((short) 1);
		cellStyleData.setBorderTop((short) 1);
		cellStyleData.setBorderBottom((short) 1);
		cellStyleData.setFont(fontMid);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleData.setDataFormat((short) XSSFCell.CELL_TYPE_FORMULA);
		
		/*
		logger.debug("HSSFCell.CELL_TYPE_FORMULA = " + HSSFCell.CELL_TYPE_FORMULA);
		logger.debug("cellStyleData.getDataFormat() = " + cellStyleData.getDataFormat() );
		logger.debug("cellStyleData.getDataFormatString() = " + cellStyleData.getDataFormatString() );
		*/
		
		styles.put("data", cellStyleData);
		
		return styles;
	}
	
	// XSSFCellStyle 스타일을 만든다.
	public Map<String, CellStyle> createStyles2(SXSSFWorkbook wb){
		
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		
		// Cell 스타일 생성 
		CellStyle cellStyleHead = wb.createCellStyle(); 
		CellStyle cellStyleMid = wb.createCellStyle(); 
		CellStyle cellStyleInt = wb.createCellStyle(); 
		CellStyle cellStyleData = wb.createCellStyle(); 

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
//			        cellStyle.setFillForegroundColor(XSSFColor.LIME.index); 
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment((short) 2);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setBorderRight((short) 1);
//			        cellStyleHead.setBorderLeft((short) 1);
		cellStyleHead.setBorderTop((short) 1);
		cellStyleHead.setBorderBottom((short) 1);
		cellStyleHead.setFont(fontHead);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
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
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styles.put("mid", cellStyleMid);
		
		//숫자용
		DataFormat format = wb.createDataFormat();
		
		cellStyleInt.setAlignment((short) 3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern((short) 1);
		cellStyleInt.setBorderRight((short) 1);
		cellStyleInt.setBorderLeft((short) 1);
		cellStyleInt.setBorderTop((short) 1);
		cellStyleInt.setBorderBottom((short) 1);
		cellStyleInt.setFont(fontMid);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleInt.setDataFormat(format.getFormat("#,##0"));
		styles.put("number", cellStyleInt);


		//수식용
		cellStyleData.setWrapText(true); 
		cellStyleData.setAlignment((short) 3);
		cellStyleData.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleData.setFillPattern((short) 1);
		cellStyleData.setBorderRight((short) 1);
		cellStyleData.setBorderLeft((short) 1);
		cellStyleData.setBorderTop((short) 1);
		cellStyleData.setBorderBottom((short) 1);
		cellStyleData.setFont(fontMid);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleData.setDataFormat((short) XSSFCell.CELL_TYPE_FORMULA);
		
		/*
		logger.debug("HSSFCell.CELL_TYPE_FORMULA = " + HSSFCell.CELL_TYPE_FORMULA);
		logger.debug("cellStyleData.getDataFormat() = " + cellStyleData.getDataFormat() );
		logger.debug("cellStyleData.getDataFormatString() = " + cellStyleData.getDataFormatString() );
		*/
		
		styles.put("data", cellStyleData);
		
		return styles;
	}
	
	
	public void generate(Writer out, Map<String, CellStyle> styles,
			List<?> voLists, String[] strHead, int[] intWidth,
			String[] strValue, int[] intLen) {

		// SpreadsheetWriter sw = new SpreadsheetWriter(out);
		ExcelWriter sw = new ExcelWriter(out);

		try {
			sw.beginWorkSheet();

			// 칼럼 사이즈 변경
			List<SfExcelAttribute> customCellSizeList = new ArrayList<SfExcelAttribute>();
			for (int i = 1; i < intWidth.length + 1; i++) {
				customCellSizeList.add(new SfExcelAttribute(i, i,
						intWidth[i - 1], 1));
			}

			sw.customCell(customCellSizeList);

			sw.beginSheetBond();

			// insert header row
			sw.insertRow(0);
			int styleIndex = styles.get("header").getIndex();

			for (int i = 0; i < strHead.length; i++) {
				sw.createCell(i, strHead[i], styleIndex);
			}

			sw.endRow();

			// write data rows
			for (int rownum = 1; rownum <= voLists.size(); rownum++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) voLists
						.get(rownum - 1);
				sw.insertRow(rownum);

				for (int j = 0; j < strValue.length; j++) {
					String strVal = "";
					if (intLen[j] == 1) {
						strVal = map.get(strValue[j]) != null
								&& !"".equals(String.valueOf(map
										.get(strValue[j]))) ? String
								.valueOf(map.get(strValue[j])) : "0";
						sw.createCell(j, Double.parseDouble(strVal), styles
								.get("number").getIndex());
					} else if (intLen[j] == 2) {
						strVal = map.get(strValue[j]) != null
								&& !"".equals(String.valueOf(map
										.get(strValue[j]))) ? String
								.valueOf(map.get(strValue[j])) : "";
						sw.createCell(j, strVal, styles.get("left").getIndex());
					} else {
						strVal = map.get(strValue[j]) != null
								&& !"".equals(String.valueOf(map
										.get(strValue[j]))) ? String
								.valueOf(map.get(strValue[j])) : "";
						sw.createCell(j, strVal, styles.get("mid").getIndex());
					}
				}
				sw.endRow();
			}
			sw.endSheetBond();
			sw.endWorkSheet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param zipfile
	 *            the template file
	 * @param tmpfile
	 *            the XML file with the sheet data
	 * @param entry
	 *            the name of the sheet entry to substitute, e.g.
	 *            xl/worksheets/sheet1.xml
	 * @param out
	 *            the stream to write the result to
	 */
	public void substitute(File zipfile, File tmpfile, String entry,
			OutputStream out) throws IOException {
		ZipFile zip = ZipHelper.openZipFile(zipfile);

		try {
			ZipOutputStream zos = new ZipOutputStream(out);

			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
			while (en.hasMoreElements()) {
				ZipEntry ze = en.nextElement();
				if (!ze.getName().equals(entry)) {
					zos.putNextEntry(new ZipEntry(ze.getName()));
					InputStream is = zip.getInputStream(ze);
					copyStream(is, zos);
					is.close();
				}
			}
			zos.putNextEntry(new ZipEntry(entry));
			InputStream is = new FileInputStream(tmpfile);
			copyStream(is, zos);
			is.close();

			zos.close();
		} finally {
			zip.close();
		}
	}

	public void copyStream(InputStream in, OutputStream out) throws IOException {
		byte[] chunk = new byte[1024];
		int count;
		while ((count = in.read(chunk)) >= 0) {
			out.write(chunk, 0, count);
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
	public List<?> selectExcelList(String userId, String startDt, String endDt) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loginId", userId);
		param.put("searchStartDt", startDt);
		param.put("searchEndDt", endDt);
		return fileDownMapper.selectExcelList(param);
	}

	// 엑셀파일명 가져오기
	public List<?> selectExcelFilePath(int excelDownSeqNum) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("excelDownSeqNum", excelDownSeqNum);
		return fileDownMapper.selectExcelFilePath(param);
	}
	
	public String saveBigExcel(String userId
								, String fileName
								, String sheetName
								, Iterator<?> voLists
								, String[] strHead
								, int[] intWidth
								, String[] strValue
								, int[] intLen
								, String strToDay) throws MvnoServiceException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		LOGGER.debug("--- 엑셀저장시작 ---");
		
		StringBuilder sbFileName = new StringBuilder();
		
		int fetchSize = 100;
		try {
			
			SXSSFWorkbook wb = new SXSSFWorkbook(fetchSize);
			SXSSFSheet sheet = (SXSSFSheet) wb.createSheet(sheetName);
			
			Map<String, CellStyle> styles = createStyles(wb);
			
			Row row = null;
			Cell cell = null;
			
			row = sheet.createRow(0);
			
			int[] setColWid = new int[strHead.length];
			
			for (int i = 0; i < strHead.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(strHead[i]);
				cell.setCellStyle(styles.get("header"));
				setColWid[i] = (int) (strHead[i].length()*3*256);
			}
			
			int iCR = 0;
			while(voLists.hasNext()) {
				Object obj = voLists.next();
				Field[] fields = obj.getClass().getDeclaredFields();
				
				row = sheet.createRow(iCR + 1);
				
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
								cell.setCellStyle(styles.get("number"));
							}
						} else {
							if(strValue[j].equals(fields[i].getName())) {
								strVal = fields[i].get(obj) != null && !"".equals((String) fields[i].get(obj)) ? fields[i].get(obj).toString() : "";
								
								cell.setCellValue(strVal);
								cell.setCellStyle(styles.get("mid"));
							}
						}
					}
				}
				iCR++;
			}
			
			for (int i = 0; i < strHead.length; i++) {
				sheet.autoSizeColumn(i);
				if(sheet.getColumnWidth(i) < setColWid[i]) {
					sheet.setColumnWidth(i, setColWid[i]);
				}
			}
			
			String strFileName = fileName;
			sbFileName.append(strFileName);
			sbFileName.append(strToDay);
			sbFileName.append(".xlsx");
			
			FileOutputStream out = new FileOutputStream(sbFileName.toString());
			wb.write(out);
			out.close();
			
		} catch (Exception e) {
//			e.printStackTrace();
			LOGGER.error("----- saveBigExcel Exception -----");
			LOGGER.error(StringUtil.getPrintStackTrace(e));
			LOGGER.error("----- saveBigExcel Exception -----");
			throw new MvnoServiceException(e.getMessage(), e);
		}
		
		stopWatch.stop();
		LOGGER.debug("--- 엑셀저장끝 --- 걸린시간 : [{}]", stopWatch.getTotalTimeSeconds());
		
		saveExcelHistory(sbFileName.toString(), userId);
		return sbFileName.toString();
	}


	// CSV다운로드 기능
	@SuppressWarnings("unchecked")
	public String csvDownProcStream(String fileName
			                        , Iterator<?> voLists
			                        , String[] strHead
			                        , String[] strValue
			                        , String userId
			                        , String strToDay
			            			//, HttpServletResponse response
			            			) throws MvnoServiceException {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		LOGGER.debug("--- 엑셀저장시작 ---");
		
		StringBuilder sbFileName = new StringBuilder();
		try {
	        String strFileName = fileName;
	        sbFileName.append(strFileName);
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
	        CSVWriter cw = new CSVWriter(new OutputStreamWriter(new FileOutputStream(sbFileName.toString()), "EUC-KR"),',', '"');
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
	        cw.close();
	        
		} catch(Exception e) {
//			e.printStackTrace();
			LOGGER.error("----- csvDownProcStream Exception -----");
			LOGGER.error(StringUtil.getPrintStackTrace(e));
			LOGGER.error("----- csvDownProcStream Exception -----");
			throw new MvnoServiceException(e.getMessage(), e);
		}

		stopWatch.stop();
		LOGGER.debug("--- 엑셀저장끝 --- 걸린시간 : [{}]", stopWatch.getTotalTimeSeconds());
		
		saveExcelHistory(sbFileName.toString(), userId);
		
		return sbFileName.toString();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int insertCmnFileDnldMgmtMstU(Map<String, Object> pRequestParamMap) {

		return fileDownMapper.insertCmnFileDnldMgmtMstU(pRequestParamMap);
	}
	
}
