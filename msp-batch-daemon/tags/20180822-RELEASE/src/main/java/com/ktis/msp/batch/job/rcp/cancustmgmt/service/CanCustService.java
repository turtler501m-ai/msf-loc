package com.ktis.msp.batch.job.rcp.cancustmgmt.service;

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
import com.ktis.msp.batch.job.rcp.cancustmgmt.mapper.CanCustMapper;
import com.ktis.msp.batch.job.rcp.cancustmgmt.vo.CanCustVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class CanCustService extends BaseService {

	@Autowired
	private CanCustMapper canCustMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	public void selectCanCustListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//엑셀 첫줄
		String [] strHead = {"해지일자","계약번호","구매유형","최초요금제명","판매정책","최초개통단말","상태","해지사유","모집경로","대리점명","개통일자","할인유형"};
		String [] strValue = {"canDt","contractNum","reqBuyTypeNm","fstRateNm","salePlcyNm","fstModelNm","subStatusNm","canRsn","onOffTypeNm","agentNm","openDt","sprtTpNm"};
		
		//엑셀 컬럼 사이즈
		int[] intWidth = {15,15,15,30,30,30,15,80,15,30,15,15};
		int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			CanCustVO searchVO = (CanCustVO) JacksonUtil.makeVOFromJson(execParam, CanCustVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "해지자정보_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "해지자정보";
			
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
			canCustMapper.selectCanCustListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, getMaskFields(), paramMap));
			
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
				pRequestParamMap.put("DWNLD_RSN"    , searchVO.getDwnldRsn());
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			
			// 엑셀파일 저장한 내역 DB에 남기기
			fileDownService.saveExcelHistory(sbFileName.toString(), searchVO.getUserId());
		} catch(Exception e){
			throw new MvnoServiceException("해지자정보 엑셀저장 에러", e);
		}
	}
	
//	/**
//	 * 해지자정보 엑셀 다운로드
//	 * @param batchCommonVO
//	 * @throws MvnoServiceException
//	 */
//	public void selectCanCustListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
//		//----------------------------------------------------------------
//		// 목록 db select
//		//----------------------------------------------------------------
//		
//		//엑셀 첫줄
//		String [] strHead = {"해지일자","계약번호","구매유형","최초요금제명","판매정책","최초개통단말","상태","해지사유","모집경로","대리점명","개통일자","할인유형"};
//		
//		String [] strValue = {"canDt","contractNum","reqBuyTypeNm","fstRateNm","salePlcyNm","fstModelNm","subStatusNm","canRsn","onOffTypeNm","agentNm","openDt","sprtTpNm"};
//		
//		//엑셀 컬럼 사이즈
//		int[] intWidth = {5000,5000,5000,8000,8000,8000,4000,8000,5000,8000,5000,5000};
//		
//		int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//
//		try {
//			String execParam = batchCommonVO.getExecParam();
//			CanCustVO searchVO = (CanCustVO) JacksonUtil.makeVOFromJson(execParam, CanCustVO.class);
//			
//			String serverInfo = propertiesService.getString("excelPath");
//			String fileName = serverInfo + "해지자정보_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
//			String sheetName = "해지자정보";
//
//			LOGGER.info("\tBATCH EXCEL DOWN : 조회 시작");
//			List<CanCustVO> voLists = canCustMapper.selectCanCustListExcel(searchVO);
//			
//			String fileNm = null;
//
//			LOGGER.info("\tBATCH EXCEL DOWN : 파일 생성 시작(" + voLists.size() + ")건");
//			if(voLists.size() <= 10000) {
//				fileNm = fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists.iterator(), strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
//			} else {
//				fileNm = fileDownService.csvDownProcStream(fileName, voLists.iterator(), strHead, strValue, searchVO.getUserId(), batchCommonVO.getReqDttm());
//			}
//			
//			File file = new File(fileNm);
//			Map<String, Object> pRequestParamMap = new HashMap<String, Object>();
//			
//			//=======파일다운로드사유 로그 START==========================================================
//			if(KtisUtil.isNotEmpty(searchVO.getDwnldRsn())){
//			
//				pRequestParamMap.put("FILE_NM"   , file.getName());				//파일명
//				pRequestParamMap.put("FILE_ROUT" , file.getPath());				//파일경로
//				pRequestParamMap.put("DUTY_NM"   , "RCP");						//업무명
//				pRequestParamMap.put("IP_INFO"   , searchVO.getIpAddr());		//IP정보
//				pRequestParamMap.put("FILE_SIZE" , (int) file.length());		//파일크기
//				pRequestParamMap.put("menuId"	 , searchVO.getMenuId());		//메뉴ID
//				pRequestParamMap.put("DATA_CNT"  , 0);							//자료건수
//				pRequestParamMap.put("SESSION_USER_ID", searchVO.getUserId());	//사용자ID
//				pRequestParamMap.put("DWNLD_RSN", searchVO.getDwnldRsn());		//다운로드사유
//				pRequestParamMap.put("EXCL_DNLD_ID", Integer.parseInt(searchVO.getExclDnldId()));
//				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
//			}
//			//=======파일다운로드사유 로그 END=========================================================
//
//			LOGGER.info("BATCH EXCEL DOWN END");
//		} catch(Exception e){
//			throw new MvnoServiceException("해지자정보 엑셀저장 에러", e);
//		}
//	}

	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		return maskFields;
	}
}
