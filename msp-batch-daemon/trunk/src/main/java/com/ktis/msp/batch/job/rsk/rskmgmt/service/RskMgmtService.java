package com.ktis.msp.batch.job.rsk.rskmgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rsk.rskmgmt.mapper.RskMgmtMapper;
import com.ktis.msp.batch.job.rsk.rskmgmt.vo.RskMgmtVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.common.masking.MaskingService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class RskMgmtService extends BaseService {
	
	@Autowired
	private RskMgmtMapper rskMgmtMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private MaskingService maskingService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	public void selectNgCustListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		String [] strHead = {"계약번호","서비스구분","고객명","생년월일","성별","CTN","납부계정번호","계약상태","가입경로","가입유형"
							,"신청일자","개통일자","해지일자","해지사유","개통대리점","판매정책","할부기간","약정기간","최초요금제","최종요금제"
							,"최초요금제변경일","최초단말","최초단말일련번호","현재단말","현재단말일련번호","최초기변일","최종기변일","최초정지일","최초명변일","가입기간"
							,"정지일수"};
		String [] strValue = {"contractNum","serviceTypeNm","custNm","birthDt","gender","subscriberNo","ban","subStatusNm","onOffTypeNm","reqBuyTypeNm"
							,"reqDt","openDt","tmntDt","tmntRsnNm","agncyNm","plcySaleNm","instMnthCnt","agrmTrm","fstRateNm","lstRateNm"
							,"chgRateDt","fstPrdtCode","fstIntmSrlNo","lstPrdtCode","lstIntmSrlNo","fstDvcChgDt","lstDvcChgDt","fstSusdt","fstMcnDt","usgDtCnt"
							,"stopDtCnt"};
		//엑셀 컬럼 사이즈
		int[] intWidth = {15,15,15,15,10,15,15,10,10,15
						,15,15,15,20,30,35,10,10,25,25
						,20,20,20,20,20,15,15,15,15,10
						,10};
		int[] intLen = {0,0,0,0,0,0,0,0,0,0
						,0,0,0,0,0,0,1,1,0,0
						,0,0,0,0,0,0,0,0,0,1
						,1};

		try {
			
			String execParam = batchCommonVO.getExecParam();
			RskMgmtVO searchVO = (RskMgmtVO) JacksonUtil.makeVOFromJson(execParam, RskMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo  + "부실가입자조회_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";;//파일명
			String sheetName = "부실가입자조회";//시트명
			
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			LOGGER.info("--- 대용량 엑셀저장시작 ---");
			
			StringBuilder sbFileName = new StringBuilder();
			StringBuilder teplateFileName = new StringBuilder();
			
			// Step 1. Create a template file. Setup sheets and workbook-level objects such as
			// cell styles, number formats, etc.
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(sheetName);
			
			Map<String, CellStyle> styles = fileDownService.createStyles(wb);
			String sheetRef = sheet.getPackagePart().getPartName().getName();
			
			String strFileName = fileName;
			sbFileName.append(strFileName);
			sbFileName.append(batchCommonVO.getReqDttm());
			sbFileName.append(".xlsx");
			
			//save the template
			// template 파일을 임시로 만들고 나서 다 끝나면 삭제하도록 한다.
			teplateFileName.append(strFileName);
			teplateFileName.append("template_");
			teplateFileName.append(batchCommonVO.getReqDttm());
			teplateFileName.append(".xlsx");
			FileOutputStream os = new FileOutputStream(teplateFileName.toString());
			wb.write(os);
			os.close();
			
			//Step 2. Generate XML file.
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
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			
			// db
			rskMgmtMapper.selectNgCustListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
			LOGGER.debug("sw.endSheet()");
			sw.endSheetBond();
			
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
			
			stopWatch.stop();
			LOGGER.info("--- 대용량 엑셀저장끝 --- 걸린시간 : [" + stopWatch.getTotalTimeSeconds() + "]");
			
			//=======파일다운로드사유 로그 START==========================================================
			File logFile = new File(sbFileName.toString());
			HashMap<String, Object> pRequestParamMap =  new HashMap<String, Object>();
			
			if(KtisUtil.isNotEmpty(searchVO.getExclDnldId())){
			
				pRequestParamMap.put("FILE_NM"         , logFile.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT"       , logFile.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"         , "RCP");                          //업무명
				pRequestParamMap.put("IP_INFO"         , searchVO.getIpAddr());           //IP정보
				pRequestParamMap.put("FILE_SIZE"       , (int) logFile.length());         //파일크기
				pRequestParamMap.put("menuId"          , searchVO.getMenuId());           //메뉴ID
				pRequestParamMap.put("DATA_CNT"        , 0);                              //자료건수
				pRequestParamMap.put("SESSION_USER_ID" , searchVO.getUserId()); 	      //사용자ID
				pRequestParamMap.put("EXCL_DNLD_ID"    , searchVO.getExclDnldId());
				pRequestParamMap.put("DWNLD_RSN"       , searchVO.getDwnldRsn());
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			
			fileDownService.saveExcelHistory(sbFileName.toString(), searchVO.getUserId());
		}catch(Exception e){
			LOGGER.error(StringUtil.getPrintStackTrace(e));
			throw new MvnoServiceException("부실가입자조회 엑셀저장 에러", e);
		}
	}
	
	

	public void selectSaleSttcListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		String [] strHead = {
				"부가서비스코드"		,"부가서비스명"			,"부가서비스가입일자"			,"부가서비스해지일자"		,"계약번호"
				,"개통일자"				,"현재요금제"			,"최초요금제"					,"할인유형"				,"판매정책명"
				,"단말모델"				,"대리점"
				};
		String [] strValue = {
				"addProd"				,"addProdNm"			,"strtDt"					,"endDt"				,"contractNum"
				,"openDt"					,"lstRateNm"				,"fstRateNm"				,"dcTypeNm"		,"salePlcyNm"
				,"fstModelNm"			,"openAgntNm"
				};
		//엑셀 컬럼 사이즈
		int[] intWidth = {
				15,20,20,20,15
				,15,30,30,15,30
				,15,20
				};
		int[] intLen = {
				0,0,0,0,0
				,0,0,0,0,0
				,0,0
				};
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			RskMgmtVO searchVO = (RskMgmtVO) JacksonUtil.makeVOFromJson(execParam, RskMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo  + "부가서비스가입자조회_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";;//파일명
			String sheetName = "부가서비스가입자조회";//시트명
			
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			LOGGER.info("--- 대용량 엑셀저장시작 ---");
			
			StringBuilder sbFileName = new StringBuilder();
			StringBuilder teplateFileName = new StringBuilder();
			
			// Step 1. Create a template file. Setup sheets and workbook-level objects such as
			// cell styles, number formats, etc.
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(sheetName);
			
			Map<String, CellStyle> styles = fileDownService.createStyles(wb);
			String sheetRef = sheet.getPackagePart().getPartName().getName();
			
			String strFileName = fileName;
			sbFileName.append(strFileName);
			sbFileName.append(batchCommonVO.getReqDttm());
			sbFileName.append(".xlsx");
			
			//save the template
			// template 파일을 임시로 만들고 나서 다 끝나면 삭제하도록 한다.
			teplateFileName.append(strFileName);
			teplateFileName.append("template_");
			teplateFileName.append(batchCommonVO.getReqDttm());
			teplateFileName.append(".xlsx");
			FileOutputStream os = new FileOutputStream(teplateFileName.toString());
			wb.write(os);
			os.close();
			
			//Step 2. Generate XML file.
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
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			
			// db
			rskMgmtMapper.selectSaleSttcListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
			LOGGER.debug("sw.endSheet()");
			sw.endSheetBond();
			
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
			
			stopWatch.stop();
			LOGGER.info("--- 대용량 엑셀저장끝 --- 걸린시간 : [" + stopWatch.getTotalTimeSeconds() + "]");
			
			//=======파일다운로드사유 로그 START==========================================================
			File logFile = new File(sbFileName.toString());
			HashMap<String, Object> pRequestParamMap =  new HashMap<String, Object>();
			
			if(KtisUtil.isNotEmpty(searchVO.getExclDnldId())){
				
				pRequestParamMap.put("FILE_NM"         , logFile.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT"       , logFile.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"         , "RCP");                          //업무명
				pRequestParamMap.put("IP_INFO"         , searchVO.getIpAddr());           //IP정보
				pRequestParamMap.put("FILE_SIZE"       , (int) logFile.length());         //파일크기
				pRequestParamMap.put("menuId"          , searchVO.getMenuId());           //메뉴ID
				pRequestParamMap.put("DATA_CNT"        , 0);                              //자료건수
				pRequestParamMap.put("SESSION_USER_ID" , searchVO.getUserId()); 	      //사용자ID
				pRequestParamMap.put("EXCL_DNLD_ID"    , searchVO.getExclDnldId());
				pRequestParamMap.put("DWNLD_RSN"       , searchVO.getDwnldRsn());
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			
			fileDownService.saveExcelHistory(sbFileName.toString(), searchVO.getUserId());
		}catch(Exception e){
			LOGGER.error(StringUtil.getPrintStackTrace(e));
			throw new MvnoServiceException("부가서비스가입자조회 엑셀저장 에러", e);
		}
	}
	
}
