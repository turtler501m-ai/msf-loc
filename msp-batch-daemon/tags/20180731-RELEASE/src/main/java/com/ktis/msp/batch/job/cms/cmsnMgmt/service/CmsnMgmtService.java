package com.ktis.msp.batch.job.cms.cmsnMgmt.service;

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
import com.ktis.msp.batch.job.cms.cmsnMgmt.mapper.CmsnMgmtMapper;
import com.ktis.msp.batch.job.cms.cmsnMgmt.vo.CmsnMgmtVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpListVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.common.masking.MaskingService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class CmsnMgmtService extends BaseService {
	
	@Autowired
	private CmsnMgmtMapper cmsnMgmtMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
    private FileDownService fileDownService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	/**
	 * @Description : 수수료일정산 엑셀 다운로드
	 * @Param  : 
	 * @Return : 
	 * @Author : 정하영
	 * @Create Date : 2017. 03. 08.
	 */
	public void getCmsnDailyMgmtListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String [] strHead = {"계약번호","고객명","전화번호","계약상태","신청일자","개통일시","해지일자","신청판매정책","개통판매정책","제품코드","중고여부","요금제","약정기간","개통유형","개통대리점","공시지원금","판매수수료","ARPU수수료","대리점지원금", "할인유형"};
		String [] strValue = {"contractNum","custNm","svcTelNum","subStatusNm","reqDt","openDttm","tmntDt","reqSalePlcyNm","salePlcyNm","prdtCode","oldYnNm","rateNm","agrmTrm","operTypeNm","agntNm","subsdAmt","saleCmsnAmt","arpuCmsnAmt","agncySubsd", "sprtTpNm"};
		
		//엑셀 컬럼 사이즈
		int[] intWidth = {5000,5000,5000,5000,5000,5000,5000,10000,10000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000};
		int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1,0};
		
				
		try {
			String execParam = batchCommonVO.getExecParam();
			CmsnMgmtVO searchVO = (CmsnMgmtVO) JacksonUtil.makeVOFromJson(execParam, CmsnMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "수수료일정산_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			
			String sheetName = "수수료일정산";
			
			List<?> voLists = cmsnMgmtMapper.getCmsnDailyMgmtListExcel(searchVO);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);
			
			String fileNm = null;
			
			fileNm = fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			
			File file = new File(fileNm);
			
			Map<String, Object> pRequestParamMap = new HashMap<String, Object>();
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(searchVO.getDwnldRsn())){
			
				pRequestParamMap.put("FILE_NM"   , file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" , file.getPath());				//파일경로
				pRequestParamMap.put("FILE_SIZE" , (int) file.length());		//파일크기
				pRequestParamMap.put("IP_INFO"   , searchVO.getIpAddr());		//IP정보
				pRequestParamMap.put("DATA_CNT"  , 0);							//자료건수
				pRequestParamMap.put("DWNLD_RSN", searchVO.getDwnldRsn());		//다운로드사유
				pRequestParamMap.put("EXCL_DNLD_ID", searchVO.getExclDnldId());
				fileDownService.insertCmnFileDnldMgmtMstU(pRequestParamMap);
			}
			
		} catch(Exception e){
			throw new MvnoServiceException("EBND1005", e);
		}
		
	}
	
	/**
	 * @Description : Grade관리수수료 엑셀 다운로드
	 * @Param  : 
	 * @Return : 
	 * @Author : 정하영
	 * @Create Date : 2017. 03. 08.
	 */
	public void getGrdCmsnPrvdListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		String [] strHead = {"대리점ID", "대리점", "수수료명", "수수료정책", "계약번호", "대리점유형", "대리점유형상세", "채널상세", "정책시작일자", "정책종료일자", "계약상태", "지급건수", "수수료율(%)", "지급여부", "수납금액", "수수료"};
		String [] strValue = {"agncyCd", "agncyNm", "cmsnNm", "cmsnPlcyNm", "srvcCntrNum", "typeDtlNm1", "typeDtlNm2", "typeDtlNm3", "applStrtDt", "applEndDt", "sbscrbStat", "mgmtCnt", "cmsnRateAmt", "prvdYn", "pymnAmt", "lastCmsn"};
		//엑셀 컬럼 사이즈
		int[] intWidth = {5000,10000,5000,10000,5000,5000,5000,5000,5000,5000,5000,5000,5000,3000,3000,3000};
		int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1};
		
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			CmsnMgmtVO searchVO = (CmsnMgmtVO) JacksonUtil.makeVOFromJson(execParam, CmsnMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "Grd관리수수료_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "Grd관리수수료";
			
			List<?> voLists = cmsnMgmtMapper.getGrdCmsnPrvdListExcel(searchVO);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);
			
			String fileNm = null;
			
			fileNm = fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			
			File file = new File(fileNm);
			
			Map<String, Object> pRequestParamMap = new HashMap<String, Object>();
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(searchVO.getDwnldRsn())){
			
				pRequestParamMap.put("FILE_NM"   , file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" , file.getPath());				//파일경로
				pRequestParamMap.put("FILE_SIZE" , (int) file.length());		//파일크기
				pRequestParamMap.put("IP_INFO"   , searchVO.getIpAddr());		//IP정보
				pRequestParamMap.put("DATA_CNT"  , 0);							//자료건수
				pRequestParamMap.put("DWNLD_RSN", searchVO.getDwnldRsn());		//다운로드사유
				pRequestParamMap.put("EXCL_DNLD_ID", searchVO.getExclDnldId());
				fileDownService.insertCmnFileDnldMgmtMstU(pRequestParamMap);
			}
		} catch(Exception e){
			throw new MvnoServiceException("EBND1005", e);
		}
	}
	
	/**
	 * @Description : 대리점수수료상세내역 엑셀 다운로드
	 * @Param  : 
	 * @Return : 
	 * @Author : 김용문
	 * @Create Date : 2017. 04. 19.
	 */
	public void getCmsnPrvdListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		String[] strHead = {"대리점명","수수료명","수수료정책명","계약번호","수수료","조정금액","환수금액","지급수수료","조정사유"};
		String[] strValue = {"saleStoreNm","cmsnNm","cmsnPlcyNm","srvcCntrNum","cmsn","adjAmt","clwbckAmt","lastCmsn","rsn"};

		//엑셀 컬럼 사이즈
		int[] intWidth = {20, 20, 20, 20, 20, 20, 20, 20, 50};
		int[] intLen = {0, 0, 0, 0, 1, 1, 1, 1, 0};
		
		try {
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			CmsnMgmtVO searchVO = (CmsnMgmtVO) JacksonUtil.makeVOFromJson(execParam, CmsnMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "대리점수수료상세내역_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "대리점수수료상세내역";
			
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
			cmsnMgmtMapper.getCmsnPrvdListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, getMaskFields(), paramMap));
			
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
			e.printStackTrace();
			throw new MvnoServiceException("EBND1005", e);
		}
	}
	
	/**
	 * @Description : 대리점수수료상세내역 엑셀 다운로드
	 * @Param  : 
	 * @Return : 
	 * @Author : 정하영
	 * @Create Date : 2017. 03. 08.
	 */
	public void getCmsnPrvdListExcel(BatchCommonVO batchCommonVO, String strTmp) throws MvnoServiceException {
		
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		
		String[] strHead = {"대리점명","수수료명","수수료정책명","계약번호","수수료","조정금액","환수금액","지급수수료","조정사유"};
		String[] strValue = {"saleStoreNm","cmsnNm","cmsnPlcyNm","srvcCntrNum","cmsn","adjAmt","clwbckAmt","lastCmsn","rsn"};

		//엑셀 컬럼 사이즈
		int[] intWidth = {5000, 7000, 7000, 5000, 5000, 4500, 4500, 4500, 8000};
		int[] intLen = {0, 0, 0, 0, 1, 1, 1, 1, 0};
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			CmsnMgmtVO searchVO = (CmsnMgmtVO) JacksonUtil.makeVOFromJson(execParam, CmsnMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "대리점수수료상세내역_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "대리점수수료상세내역";
			
			List<?> voLists = cmsnMgmtMapper.getCmsnPrvdListExcel(searchVO);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);
			
			String fileNm = null;
			
			fileNm = fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			
			File file = new File(fileNm);
			
			Map<String, Object> pRequestParamMap = new HashMap<String, Object>();
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(searchVO.getDwnldRsn())){
			
				pRequestParamMap.put("FILE_NM"   , file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" , file.getPath());				//파일경로
				pRequestParamMap.put("FILE_SIZE" , (int) file.length());		//파일크기
				pRequestParamMap.put("IP_INFO"   , searchVO.getIpAddr());		//IP정보
				pRequestParamMap.put("DATA_CNT"  , 0);							//자료건수
				pRequestParamMap.put("DWNLD_RSN", searchVO.getDwnldRsn());		//다운로드사유
				pRequestParamMap.put("EXCL_DNLD_ID", searchVO.getExclDnldId());
				fileDownService.insertCmnFileDnldMgmtMstU(pRequestParamMap);
			}
			
			
			
			
		} catch(Exception e){
			throw new MvnoServiceException("EBND1005", e);
		}
	}
	
	
	public void getCalcCrebasisListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		String [] strHead = {"기준월", "판매대리점", "계약번호", "전화번호", "가입상태", "가입유형", "개통일자", "해지일자", "기변일자", "이동세대구분", "모델ID", "모델명", "모델일련번호", "신청서접수일시", "판매정책명", "개통요금제명", "개통요금제그룹", "현재요금제명", "현재요금제그룹"
				           , "약정개월수", "판매수수료","ARPU수수료","최초개통점", "해지일자(현재)", "해지사유(현재)"};
		String [] strValue = {"stdrMon", "saleStoreNm", "contractNum", "telnum", "sbscrbStatNm", "sbscrbTypeNm", "openDt", "tmntDt", "hndstChngDt", "g3IndNm", "hndstId", "prdtCode","hndstSrlNum", "reqRcptDate", "salePlcyNm", "fChrgPlanNm", "fRateGrpCd", "cChrgPlanNm", "cRateGrpCd"
				           , "agrmNomCnt", "saleAmnt", "arpuAmnt", "openAgntNm", "canDt", "bckTrgt"};
		//엑셀 컬럼 사이즈
		int[] intWidth = {15,30,15,15,15,15,15,15,15,15,15,15,30,30,30,30,15,50,15,15,15,15,30,15,100};
		int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0};
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			CmsnMgmtVO searchVO = (CmsnMgmtVO) JacksonUtil.makeVOFromJson(execParam, CmsnMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "판매_ARPU수수료내역_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "판매_ARPU수수료내역";
			
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
			cmsnMgmtMapper.getCalcCrebasisListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, getMaskFields(), paramMap));
			
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
			throw new MvnoServiceException("EBND1005", e);
		}
	}
	
	
//	/**
//	 * @Description : 판매_ARPU수수료내역 엑셀 다운로드
//	 * @Param  : 
//	 * @Return : 
//	 * @Author : 정하영
//	 * @Create Date : 2017. 03. 15.
//	 */
//	public void getCalcCrebasisListExcel(BatchCommonVO batchCommonVO, String a) throws MvnoServiceException {
//		
//		//----------------------------------------------------------------
//		// 목록 db select
//		//----------------------------------------------------------------
//		String [] strHead = {"기준월", "판매대리점", "계약번호", "전화번호", "가입상태", "가입유형", "개통일자", "해지일자", "기변일자", "이동세대구분", "모델ID", "모델명", "모델일련번호", "신청서접수일시", "판매정책명", "개통요금제명", "개통요금제그룹", "현재요금제명", "현재요금제그룹"
//				           , "약정개월수", "판매수수료","ARPU수수료","최초개통점", "해지일자(현재)", "해지사유(현재)"};
//		String [] strValue = {"stdrMon", "saleStoreNm", "contractNum", "telnum", "sbscrbStatNm", "sbscrbTypeNm", "openDt", "tmntDt", "hndstChngDt", "g3IndNm", "hndstId", "prdtCode","hndstSrlNum", "reqRcptDate", "salePlcyNm", "fChrgPlanNm", "fRateGrpCd", "cChrgPlanNm", "cRateGrpCd"
//				           , "agrmNomCnt", "saleAmnt", "arpuAmnt", "openAgntNm", "canDt", "bckTrgt"};
//		//엑셀 컬럼 사이즈
//		int[] intWidth = {3000,10000,3000,3000,3000,3000,3000,3000,3000,3000,3000,5000,5000,5000,10000,5000,3000,5000,3000,3000,3000,3000,10000,5000,10000};
//		int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//				        1,1,1,0,0,0};
//		
//		try {
//			
//			String execParam = batchCommonVO.getExecParam();
//			CmsnMgmtVO searchVO = (CmsnMgmtVO) JacksonUtil.makeVOFromJson(execParam, CmsnMgmtVO.class);
//			
//			String serverInfo = propertiesService.getString("excelPath");
//			String fileName = serverInfo + "판매_ARPU수수료내역_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
//			String sheetName = "판매_ARPU수수료내역";
//			
//			List<?> voLists = cmsnMgmtMapper.getCalcCrebasisListExcel(searchVO);
//			
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("userId", searchVO.getUserId());
//			voLists = getMaskingData(voLists, paramMap);
//			
//			String fileNm = null;
//			
//			fileNm = fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
//			
//			File file = new File(fileNm);
//			
//			Map<String, Object> pRequestParamMap = new HashMap<String, Object>();
//			
//			//=======파일다운로드사유 로그 START==========================================================
//			if(KtisUtil.isNotEmpty(searchVO.getDwnldRsn())){
//			
//				pRequestParamMap.put("FILE_NM"   , file.getName());				//파일명
//				pRequestParamMap.put("FILE_ROUT" , file.getPath());				//파일경로
//				pRequestParamMap.put("FILE_SIZE" , (int) file.length());		//파일크기
//				pRequestParamMap.put("IP_INFO"   , searchVO.getIpAddr());		//IP정보
//				pRequestParamMap.put("DATA_CNT"  , 0);							//자료건수
//				pRequestParamMap.put("DWNLD_RSN", searchVO.getDwnldRsn());		//다운로드사유
//				pRequestParamMap.put("EXCL_DNLD_ID", searchVO.getExclDnldId());
//				fileDownService.insertCmnFileDnldMgmtMstU(pRequestParamMap);
//			}
//			
//			
//		} catch(Exception e){
//			throw new MvnoServiceException("EBND1005", e);
//		}
//	}
	
	
	
	// 주소,이름,전화번호를 마스킹처리한다.
	public List<?> getMaskingData(List<?> result, Map<String, Object> paramMap) {
		
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	// 마스킹처리를 위한 필드
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subLinkName","CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("subAdrPrimaryLn","ADDR");
		
		return maskFields;
	}	
}
