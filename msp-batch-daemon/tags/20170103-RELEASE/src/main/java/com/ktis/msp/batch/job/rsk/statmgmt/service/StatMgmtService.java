package com.ktis.msp.batch.job.rsk.statmgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cls.ClsAcntConfig;
import com.ktis.msp.batch.job.rsk.statmgmt.mapper.StatMgmtMapper;
import com.ktis.msp.batch.job.rsk.statmgmt.vo.StatMgmtVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler3;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class StatMgmtService extends BaseService {
	
	@Autowired
	private StatMgmtMapper statMgmtMapper;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private EgovPropertyService propertiesService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	/**
	 * @throws MvnoServiceException 
	 * 청구수납자료생성(프로시져 호출)
	 * @param vo
	 * @throws 
	 */
	public void setInvPymDataList(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String execParam = batchCommonVO.getExecParam();
		StatMgmtVO vo = (StatMgmtVO) JacksonUtil.makeVOFromJson(execParam, StatMgmtVO.class);
		
		// 필수값 체크
		if(vo.getTrgtYm() == null || vo.getTrgtYm().equals("")) {
			throw new MvnoServiceException("ERSK1001");
		}
		
		if(vo.getStrtYm() == null || vo.getStrtYm().equals("")) {
			throw new MvnoServiceException("ERSK1002");
		}
		
		if(vo.getEndYm() == null || vo.getEndYm().equals("")) {
			throw new MvnoServiceException("ERSK1003");
		}
		
		if(Integer.parseInt(vo.getStrtYm()) > Integer.parseInt(vo.getEndYm())) {
			throw new MvnoServiceException("ERSK1004");
		}
		// 5년이상 자료조회 불가
		if(statMgmtMapper.getInvPymPrdCheck(vo) == 0) {
			throw new MvnoServiceException("ERSK1005");
		}
				
		// 실행중인 자료요청건 존재여부 확인
		if(statMgmtMapper.getInvPymDataCheck(vo) > 0) {
			throw new MvnoServiceException("ERSK1006");
		}
		
		statMgmtMapper.callInvPymData(vo);
		
		// 파일생성
		while(true){
			if(statMgmtMapper.getInvPymDataCheck(vo) == 0){
				saveInvPymDataExcel(vo, batchCommonVO);
				break;
			}
			
			// 5분에 한번씩 실행
			sleep(60 * 5);
		}
	}
	
	public void saveInvPymDataExcel(StatMgmtVO vo, BatchCommonVO batchCommonVO) {
		String serverInfo = propertiesService.getString("excelPath");
		String strFileName = serverInfo + "청구수납자료_" + vo.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
		String strSheetName = "청구수납자료";
		
		String[] strHead = new String[132];
		strHead[0] = "계약번호";
		strHead[1] = "개통일자";
		strHead[2] = "계약상태";
		strHead[3] = "선후불";
		strHead[4] = "약정개월수";
		strHead[5] = "가입요금제";
		strHead[6] = "청구월";
		strHead[7] = "청구금액";
		strHead[8] = "수납금액";
		strHead[9] = "미납금액";
		strHead[10] = "미납율";
		strHead[11] = "미납여부";
		
		int yy = Integer.parseInt(vo.getStrtYm().substring(0, 4));
		int mm = Integer.parseInt(vo.getStrtYm().substring(4, 6));
		
		LOGGER.debug("yy={}", yy);
		LOGGER.debug("mm={}", mm);
		
		String vYY = "";
		String vMM = "";
		
		for(int i=0; i<120; i++){
			
			if(i % 2 == 0){
				if(i == 0){
					vYY = String.valueOf(yy);
					vMM = String.valueOf(mm);
				}else{
					mm++;
					
					if(mm > 12) {
						mm = 1;
						yy ++;
					}
				}
				
				vYY = String.valueOf(yy);
//				vMM = mm < 10 ? "0" + String.valueOf(mm) : String.valueOf(mm);  
				vMM = mm < 10 ? "0" + mm : String.valueOf(mm);  
				
				strHead[i+12] = vYY + "-" + vMM;
				
			}else{
				strHead[i+12] = "";
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("yymm={}", strHead[i+12]);
			}
		}
		
		String[] strValue = new String[132];
		strValue[0] = "contractNum";
		strValue[1] = "openDt";
		strValue[2] = "subStatusNm";
		strValue[3] = "pppo";
		strValue[4] = "enggMnthCnt";
		strValue[5] = "rateNm";
		strValue[6] = "billYm";
		strValue[7] = "invAmnt";
		strValue[8] = "pymAmnt";
		strValue[9] = "unpdAmnt";
		strValue[10] = "unpdRatio";
		strValue[11] = "unpdYn";

		for(int i=1; i<=120; i++){
			if(i < 10) strValue[i+11] = "col00" + i;
			else if(i<100) strValue[i+11] = "col0" + i;
			else strValue[i+11] = "col" + i;
		}
		
		int[] intWidth = new int[132];
		for(int i=0; i<132; i++){
			intWidth[i] = 20;
		}
		
		int[] intLen = new int[132];
		for(int i=0; i<132; i++){
			if(i == 7 || i == 8 || i == 9 || i >= 12) intLen[i] = 1;
			else intLen[i] = 0;
		}
		
		StringBuilder sbFileName = new StringBuilder();
		StringBuilder teplateFileName = new StringBuilder();
		
		try{
			// Step1.
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(strSheetName);
			
			Map<String, XSSFCellStyle> styles = createStyles(wb);
			String sheetRef = sheet.getPackagePart().getPartName().getName();

//			Date toDay = new Date();
			
//			String strFileName = fileName;
//			String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
			String strToDay = batchCommonVO.getReqDttm();
			sbFileName.append(strFileName);
			sbFileName.append(strToDay);
			sbFileName.append(".xlsx");

			//save the template
			// template 파일을 임시로 만들고 나서 다 끝나면 삭제하도록 한다.
			teplateFileName.append(strFileName);
			teplateFileName.append("template_");
			teplateFileName.append(strToDay);
			teplateFileName.append(".xlsx");
			FileOutputStream os = new FileOutputStream(teplateFileName.toString());
			wb.write(os);
			os.close();
			
			// Step2.
			File tempDir = new File(serverInfo);
			File tmp = File.createTempFile("sheet", ".xml", tempDir);
			Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), XML_ENCODING);
			
			
			ExcelWriter sw = new ExcelWriter(fw);
			sw.beginWorkSheet();
			// 칼럼 사이즈 변경
			List<SfExcelAttribute> customCellSizeList = new ArrayList<SfExcelAttribute>();
			for (int i = 1; i < intWidth.length+1; i++) {
				customCellSizeList.add(new SfExcelAttribute(i,i,intWidth[i-1],1));
			}
			
			sw.customCell(customCellSizeList);
			sw.beginSheetBond();
			statMgmtMapper.getInvPymDataListExcel(vo, new ExcelDataListResultHandler3(sw, styles, strHead, strValue, intLen));
			sw.endSheetBond();
			
			// 셀 머지 (mergeCells의 parent node 는 worksheet 임
//			A = "계약번호";
//			B = "개통일자";
//			C = "계약상태";
//			D = "선후불";
//			E = "약정개월수";
//			F = "가입요금제";
//			G = "청구월";
//			H = "청구금액";
//			I = "수납금액";
//			J = "미납금액";
//			K = "미납율";
//			L = "미납여부";
			String[] mergeCell = {"A1:A2","B1:B2","C1:C2","D1:D2","E1:E2","F1:F2","G1:G2","K1:K2"
						, "M1:N1",   "O1:P1",   "Q1:R1", "S1:T1",   "U1:V1",   "W1:X1",   "Y1:Z1" // 7
						, "AA1:AB1", "AC1:AD1", "AE1:AF1", "AG1:AH1", "AI1:AJ1", "AK1:AL1", "AM1:AN1", "AO1:AP1", "AQ1:AR1", "AS1:AT1", "AU1:AV1", "AW1:AX1", "AY1:AZ1" // 13
						, "BA1:BB1", "BC1:BD1", "BE1:BF1", "BG1:BH1", "BI1:BJ1", "BK1:BL1", "BM1:BN1", "BO1:BP1", "BQ1:BR1", "BS1:BT1", "BU1:BV1", "BW1:BX1", "BY1:BZ1" // 13
						, "CA1:CB1", "CC1:CD1", "CE1:CF1", "CG1:CH1", "CI1:CJ1", "CK1:CL1", "CM1:CN1", "CO1:CP1", "CQ1:CR1", "CS1:CT1", "CU1:CV1", "CW1:CX1", "CY1:CZ1" // 13
						, "DA1:DB1", "DC1:DD1", "DE1:DF1", "DG1:DH1", "DI1:DJ1", "DK1:DL1", "DM1:DN1", "DO1:DP1", "DQ1:DR1", "DS1:DT1", "DU1:DV1", "DW1:DX1", "DY1:DZ1" // 13
						, "EA1:EB1" // 1
				};
			
			sw.mergeCell(mergeCell);
			sw.endWorkSheet();
			
			fw.close();
			
			//Step 3. Substitute the template entry with the generated data
			FileOutputStream out = new FileOutputStream(sbFileName.toString());
			File file = new File(teplateFileName.toString());
			fileDownService.substitute(file, tmp, sheetRef.substring(1), out);
			out.close();
			
			// 임시로 만든 template 파일을 삭제한다
			file.delete();
			tmp.delete();
			
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		// 엑셀파일 저장한 내역 DB에 남기기
		fileDownService.saveExcelHistory(sbFileName.toString(), vo.getUserId());
		
		// SMS발송
		statMgmtMapper.insertMsgQueue(vo.getUserId());
		
	}
	
	public Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb) {
		
		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
		
		// Cell 스타일 생성 
		XSSFCellStyle cellStyleHead = wb.createCellStyle(); 
		XSSFCellStyle cellStyleMid = wb.createCellStyle(); 
		XSSFCellStyle cellStyleInt = wb.createCellStyle(); 
		XSSFCellStyle cellStyleData = wb.createCellStyle(); 

		// 줄 바꿈 
		cellStyleHead.setWrapText(true); 
		
		short short1 = 1; 
		short short2 = 2; 
		short short3 = 3; 
		short short10 = 10; 
		short short12 = 12; 
		  
		XSSFFont fontHead = wb.createFont();
		fontHead.setBoldweight(short1);
		fontHead.setFontName("맑은 고딕");
		fontHead.setColor(HSSFColor.BLACK.index);
		fontHead.setFontHeightInPoints(short12);
		
		XSSFFont fontMid = wb.createFont();
		fontMid.setBoldweight(short1);
		fontMid.setFontName("맑은 고딕");
		fontMid.setColor(HSSFColor.BLACK.index);
		fontMid.setFontHeightInPoints(short10);
		
		//제목
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment(short2);
		cellStyleHead.setFillPattern(short1);
		cellStyleHead.setFillPattern(short1);
		cellStyleHead.setBorderRight(short1);
//		cellStyleHead.setBorderLeft(short1);
		cellStyleHead.setBorderTop(short1);
		cellStyleHead.setBorderBottom(short1);
		cellStyleHead.setFont(fontHead);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styles.put("header", cellStyleHead);
		
		//내용
		cellStyleMid.setAlignment(short2);
		cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleMid.setFillPattern(short1);
		cellStyleMid.setBorderRight(short1);
		cellStyleMid.setBorderLeft(short1);
		cellStyleMid.setBorderTop(short1);
		cellStyleMid.setBorderBottom(short1);
		cellStyleMid.setFont(fontMid);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styles.put("mid", cellStyleMid);
		
		//숫자용
		XSSFDataFormat format = wb.createDataFormat();
		
		cellStyleInt.setAlignment(short3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern(short1);
		cellStyleInt.setBorderRight(short1);
		cellStyleInt.setBorderLeft(short1);
		cellStyleInt.setBorderTop(short1);
		cellStyleInt.setBorderBottom(short1);
		cellStyleInt.setFont(fontMid);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleInt.setDataFormat(format.getFormat("#,##0"));
		styles.put("number", cellStyleInt);
		
		//수식용
		cellStyleData.setWrapText(true); 
		cellStyleData.setAlignment(short3);
		cellStyleData.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleData.setFillPattern(short1);
		cellStyleData.setBorderRight(short1);
		cellStyleData.setBorderLeft(short1);
		cellStyleData.setBorderTop(short1);
		cellStyleData.setBorderBottom(short1);
		cellStyleData.setFont(fontMid);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleData.setDataFormat(short2);
		
		styles.put("data", cellStyleData);
		
		return styles;
	}
	
	public void sleep(int time){
		// time 은 초 단위임
		try {
			Thread.sleep(1000 * time);
		} catch (InterruptedException e) { 
			
		}
	}
	
	/**
	 * 사용량집계 생성
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setUsgAmntSttc(Map<String, Object> param) throws MvnoServiceException{
		
		if(!param.containsKey("WORK_YM") || "".equals(param.get("WORK_YM"))) {
			throw new MvnoServiceException("ECLS1030");
		}
		
		try {
			param.put("USER_ID", ClsAcntConfig.USER_ID);
			
			LOGGER.debug("param={}", param);
			
			statMgmtMapper.insertUsgAmntSttc(param);
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1041", e);
		}
	}
	
	/**
	 * 청구수납금액 집계 생성
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setInvAmntSttc(Map<String, Object> param) throws MvnoServiceException {
		
		if(!param.containsKey("WORK_YM") || "".equals(param.get("WORK_YM"))) {
			throw new MvnoServiceException("ECLS1030");
		}
		
		try {
			param.put("USER_ID", ClsAcntConfig.USER_ID);
			
			LOGGER.debug("param={}", param);
			
			statMgmtMapper.insertInvAmntSttc(param);
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1033", e);
		}
	}
	
	/**
	 * 일마감집계
	 */
//	@Transactional(rollbackFor=Exception.class)
	public void setDailyCloseSttc(Map<String, Object> param) throws MvnoServiceException {
		try {
			
			statMgmtMapper.callInserDailyCloseSttc(param);
			
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1023", e);
		}
		
	}
	
	/**
	 * 영업실적집계
	 */
//	@Transactional(rollbackFor=Exception.class)
	public void setSaleRsltSttc(Map<String, Object> param) throws MvnoServiceException{
		
		try {
			
			statMgmtMapper.callInserSaleRsltSttc(param);
			
		} catch(Exception e){
			throw new MvnoServiceException("ECLS1038", e);
		}
		
	}
	
	/**
	 * 계약별요금제사용일수집계
	 */
//	@Transactional(rollbackFor=Exception.class)
	public void setCntrUsgDtSttc(Map<String, Object> param) throws MvnoServiceException{
		
		try {
			LOGGER.debug("WORK_DTM={}", param.get("WORK_DTM"));
			
			statMgmtMapper.callInserCntrUsgDtSttc(param);
			
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1003", e);
		}
	}
	
	/**
	 * 회계자료생성
	 */
//	@Transactional(rollbackFor=Exception.class)
	public void callAcntDataSttc() throws MvnoServiceException {
		try {
			statMgmtMapper.callAcntDataSttc();
			
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1028", e);
		}
	}
	
	

}
