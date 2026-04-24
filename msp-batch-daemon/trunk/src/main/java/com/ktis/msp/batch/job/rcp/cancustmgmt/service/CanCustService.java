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
import com.ktis.msp.batch.manager.common.masking.MaskingService;
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
	
	@Autowired
	private MaskingService maskingService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	public void selectCanCustListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {	
		String [] strHead = {
						"해지일자","계약번호","구매유형","최초요금제코드","최초요금제명","판매정책", "최초개통단말","상태","해지사유","대리점명","개통일자","할인유형",// 11
						"업무구분","이동전 통신사","판매점","모집경로","유입경로", "나이(만)","성별","데이터유형","약정개월수","할부개월수","최종단말모델",// 11
						"최종요금제코드","최종요금제명","할부원금","보증보험가입상태","심플할인ID","심플약정기간","심플약정시작일자","심플약정종료일자","심플약정상태","기변횟수","기변유형", // 10
						"기변일자","기변모델ID","기변모델명","기변단말일련번호","기변대리점","기변단말출고가","기변단말할부원금","기변단말할부개월","기변약정개월","기변판매정책", // 10
						"KT해지사유","해지후이동사업자정보","유심접점","최초유심접점","eSIM여부","최초eSIM여부","KT해지사유 유형"
						}; 
		String [] strValue = {
						"tmntdt","contractnum","reqbuytypenm","fstratecd","fstratenm","saleplcynm","fstmodelnm", "substatusnm","canrsn", "agentnm","opendt","sprttpnm", //11
						"opertypenm","movecompanynm","shopnm","onofftypenm","openmarketreferer","age", "gender", "datatype","enggmnthcnt","instmnthcnt","lstmodelnm", //10
						"lstratecd","lstratenm","instorginamnt" ,"grntinsrstatnm" ,"simid","simenggmnthcnt","simstartdt","simenddt","simstatnm","dvcchgcnt","dvcopertypenm",
						"dvcchgdt","dvcmodelid","dvcmodelnm","dvcintmsrlno","dvcagntnm","dvchndstamnt","dvcinstorginamnt","dvcinstmnthcnt","dvcenggmnthcnt","dvcsaleplcynm",
						"substatusrsnnm","cmpnnm","usimorgnm","fstusimorgnm","esimyn","fstesimyn","cantype"};
		int[] intWidth = {12, 15, 10, 25, 25 ,12, 20, 10, 15, 20, 12, 15,
						12, 15, 20, 15 ,15, 10, 10, 12, 12, 12, 20,
						30, 30, 12, 18, 15, 15, 20, 20, 16, 12, 12,
						12, 16, 16, 18, 20, 16, 18, 18, 16, 16,
						20, 25, 20, 20, 10, 15, 17
						};	
		int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0
						};
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
			canCustMapper.selectCanCustListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
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
	

//	 * 해지복구자 정보 엑셀 다운로드
//	 * @param batchCommonVO
//	 * @throws MvnoServiceException
	 
	public void selectRclCustList(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		String [] strHead = {
						"계약번호","서비스계약번호","상태","해지복구일시","해지복구사유","해지복구해지일시","해지복구해지사유","해지일시","해지사유","데이터유형", //10
						"최초개통단말","구매유형","최초요금제코드","최초요금제명","대리점명","개통일자","업무구분","이동전통신사","해지후이동사업자","판매점", //10
						"모집경로","유입경로","나이(만)","성별","현재단말모델명","현재요금제코드","현재요금제명","유심접점","최초유심접점","eSIM여부", //10
						"최초eSIM여부" //1
						}; 
		String [] strValue = {
						"contractNum","svcCntrNo","subStatusNm","rclDttm","rclRsn","rclCanDttm","rclCanRsn","canDttm","canRsn","dataType", //10
						"fstModel","reqBuyTypeNm","fstRateCd","fstRateNm","openAgntNm","lstComActvDate","operTypeNm","bfCommCmpnNm","npCommCmpnNm","shopNm", //10
						"onOffTypeNm","openMarketReferer","age","gender","modelName","lstRateCd","lstRateNm","usimOrgNm","fstUsimOrgNm","esimYn", //10
						"fstEsimYn" //1
						};
		int[] intWidth = {20, 20, 10, 20, 25, 20, 25, 20, 25, 15,
						20, 20, 20, 40, 20, 15, 20, 20, 25, 30,
						20, 20, 15, 10, 20, 20, 40, 20, 20, 20,
						20
						};	
		int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0
						};
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			CanCustVO searchVO = (CanCustVO) JacksonUtil.makeVOFromJson(execParam, CanCustVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "해지복구자정보_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "해지복구자정보";
			
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
			canCustMapper.selectRclCustList(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
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
			throw new MvnoServiceException("해지복구자정보 엑셀저장 에러", e);
		}
	}
}