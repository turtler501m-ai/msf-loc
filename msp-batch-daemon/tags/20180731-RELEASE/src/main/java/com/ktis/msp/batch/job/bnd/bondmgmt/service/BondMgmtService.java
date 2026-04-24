package com.ktis.msp.batch.job.bnd.bondmgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.bnd.bondmgmt.mapper.BondMgmtMapper;
import com.ktis.msp.batch.job.bnd.bondmgmt.mapper.BondScheduleMapper;
import com.ktis.msp.batch.job.bnd.bondmgmt.vo.BondMgmtVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler2;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.common.masking.MaskingService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class BondMgmtService extends BaseService {
	
	@Autowired
	private BondScheduleMapper bondScheduleMapper;
	
	@Autowired
	private BondMgmtMapper bondMgmtMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
    private FileDownService fileDownService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	@Transactional(rollbackFor=Exception.class)
	public void callCreateBondMst(Map<String, Object> param) throws MvnoServiceException {
		try {
			
			LOGGER.info("--- 단말채권정보 시작 ---");
			
			// 유동화정보생성 프로시저 호출
			bondScheduleMapper.callBondAssetData(param);
			
		} catch(Exception e) {
			throw new MvnoServiceException("EBND1003", e);
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void callCreateBondInstMst(Map<String, Object> param) throws MvnoServiceException {
		try {
			
			LOGGER.info("--- 채권정보 시작 ---");
			
			// 채권정보생성 프로시저 호출
			bondScheduleMapper.callInstBondData(param);
			
		} catch(Exception e) {
			throw new MvnoServiceException("EBND1003", e);
		}
	}
	
//	/**
//	 * @Description : 채권판매대상 엑셀 다운로드
//	 * @Param  : 
//	 * @Return : AjaxReturnVO
//	 * @Author : 정하영
//	 * @Create Date : 2016. 8. 23.
//	 */
//	public void saveExcelSaleBondObject(BatchCommonVO batchCommonVO) throws MvnoServiceException {
//		
//		String[] strHead = {"보증보험번호", "계약번호", "고객명", "할부개월", "할부원금", "월납입액", "잔여할부개월수", "잔여채권", "납입금액", "미납여부", "할부시작일자", "할부종료일자", "보증보험상태", "보험개시일자", "보험종료일자"};
//		String[] strValue = {"grntInsrMngmNo", "contractNum", "subLinkName", "ttlInstMnthCnt", "mntInstAmt", "mntInstAmt", "rmnInstCnt", "yetPymnAmnt", "payAmt", "unpaidYn", "payStrtDt", "payEndDt", "grntInsrStatChngRsnCd", "instStrtDate", "insrTrmnDate"};
//		
//		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
//		int[] intLen = {0,0,0,1,1,1,1,1,1,0,0,0,0,0,0};
//		
//				
//		try {
//			String execParam = batchCommonVO.getExecParam();
//			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
//			
//			String serverInfo = propertiesService.getString("excelPath");
//			String fileName = serverInfo + "채권판매대상조회_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
//			
//			String sheetName = "채권판매대상조회";
//			
//			List<?> voLists = bondMgmtMapper.selectBondSaleObjectListEx(searchVO);
//			
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("userId", searchVO.getUserId());
//			voLists = getMaskingData(voLists, paramMap);
//			
//			fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
//			
//			
//		} catch(Exception e){
//			throw new MvnoServiceException("EBND1005", e);
//		}
//		
//	}
	
	public void saveExcelSaleBondObject(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		String[] strHead = {"보증보험번호", "계약번호", "고객명", "할부개월", "할부원금", "월납입액", "잔여할부개월수", "잔여채권", "납입금액", "미납여부", "할부시작일자", "할부종료일자", "보증보험상태", "보험개시일자", "보험종료일자"};
		String[] strValue = {"grntInsrMngmNo", "contractNum", "subLinkName", "ttlInstMnthCnt", "ttlInstAmnt", "mntInstAmt", "rmnInstCnt", "yetPymnAmnt", "payAmt", "unpaidYn", "payStrtDt", "payEndDt", "grntInsrStatChngRsnCd", "instStrtDate", "insrTrmnDate"};

		//엑셀 컬럼 사이즈
		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		int[] intLen = {0,0,0,1,1,1,1,1,1,0,0,0,0,0,0};
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "채권판매대상조회_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "채권판매대상조회";
			
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
			bondMgmtMapper.selectBondSaleObjectListEx(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, getMaskFields(), paramMap));
			
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
	 * 채권정보(단말유동화>채권관리>고객채권관리)
	 * @param batchCommonVO
	 * @throws MvnoServiceException
	 */
	public void saveExcelBondMstList(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		
		String[] strHead = {"계약번호","보증보험관리번호","고객명","전화번호","계약상태","개통일","해지일","할부원금","할부개월수","잔여청구금액","잔여할부금액","잔여할부개월수","미납회차","미납여부","채권만료월","보험청구월","보증보험시작일자","보증보험종료일자"};
		String[] strValue = {"contractNum","grntInsrMngmNo","subLinkName","subscriberNo","subStatusNm","openDt","tmntDt","instAmt","instMnthCnt","remainBillAmt","remainAmt","remainCnt","unpaidCnt","unpaidYn","bondTmntYm","insrBillYm","instStrtDate","insrTrmnDate"};
		
		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		int[] intLen = {0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0};
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "고객채권관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "고객채권관리";
			
			List<?> voLists = bondMgmtMapper.selectBondMstListEx(searchVO);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);
			
			fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
		} catch(Exception e){
			throw new MvnoServiceException("EBND1005", e);
		}
	}
	
	public void saveExcelGrntInsr(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		String[] strHead = {"보증보험관리번호", "계약번호", "전화번호", "개통일자", "계약상태", "고객명", "주소", "제품ID", "제품코드", "제품명", "출고가", "할부원금", "할부개월수", "보험가입금액", "보험개시일자", "보험종료일자", "보험료", "보험청구여부", "보험청구금액"};
		String[] strValue = {"grntInsrMngmNo", "contractNum", "subscriberNo", "openDt", "subStatus", "subLinkName", "subAdrPrimaryLn", "prdtId", "prdtCode", "prdtNm", "prdtAmt", "instAmt", "instCnt", "grntInsrAmt", "grntInsrStrtDt", "grntInsrEndDt", "grntInsrFee", "grntInsrInvYn", "grntInsrInvAmt"};
		
		int[] intWidth = {20,20,20,20,20,20,70,20,20,20,20,20,20,20,20,20,20,20,20};
		int[] intLen = {0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,0,1};
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "보증보험관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "보증보험관리";
			
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
			bondMgmtMapper.selectGrntInsrInfoListEx(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, getMaskFields(), paramMap));
			
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
//	 * 보증보험(단말유동화>채권관리>보증보험관리)
//	 * @param batchCommonVO
//	 * @throws MvnoServiceException
//	 */
//	public void saveExcelGrntInsr(BatchCommonVO batchCommonVO, String a) throws MvnoServiceException {
//		
//		//----------------------------------------------------------------
//		// 목록 db select
//		//----------------------------------------------------------------
//		
//		String[] strHead = {"보증보험관리번호", "계약번호", "전화번호", "개통일자", "계약상태", "고객명", "주소", "제품ID", "제품코드", "제품명", "출고가", "할부원금", "할부개월수", "보험가입금액", "보험개시일자", "보험종료일자", "보험료", "보험청구여부", "보험청구금액"};
//		String[] strValue = {"grntInsrMngmNo", "contractNum", "subscriberNo", "openDt", "subStatus", "subLinkName", "subAdrPrimaryLn", "prdtId", "prdtCode", "prdtNm", "prdtAmt", "instAmt", "instCnt", "grntInsrAmt", "grntInsrStrtDt", "grntInsrEndDt", "grntInsrFee", "grntInsrInvYn", "grntInsrInvAmt"};
//		
//		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
//		int[] intLen = {0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,0,1};
//		
//		try {
//			
//			String execParam = batchCommonVO.getExecParam();
//			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
//			
//			String serverInfo = propertiesService.getString("excelPath");
//			String fileName = serverInfo + "보증보험관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
//			String sheetName = "보증보험관리";
//			
//			List<?> voLists = bondMgmtMapper.selectGrntInsrInfoListEx(searchVO);
//			
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("userId", searchVO.getUserId());
//			voLists = getMaskingData(voLists, paramMap);
//			
//			fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
//		} catch(Exception e){
//			throw new MvnoServiceException("EBND1005", e);
//		}
//	}
	
	/**
	 * 할부채권스케줄(단말유동화>채권관리>채권스케줄)
	 * @param batchCommonVO
	 * @throws MvnoServiceException
	 */
	public void saveExcelBondDtl(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "할부스케줄_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "할부스케줄";
			
			String[] strHead = new String[39];   // {"계약번호", "개통월","청구월"};
			strHead[0] = "계약번호";
			strHead[1] = "기준월";
			strHead[2] = "청구월";
			
			String[] strValue = new String[39];  //{"contractNum", "billYm", "billYm"};
			strValue[0] = "contractNum";
			strValue[1] = "billYm";
			strValue[2] = "billYm";

			int[] intWidth = new int[39]; 		//{20,20,20};
			intWidth[0] = 20;
			intWidth[1] = 20;
			intWidth[2] = 20;

			int[] intLen = new int[39]; 		//{0,0,1};
			intLen[0] = 0;
			intLen[1] = 0;
			intLen[2] = 1;
			
			Date dateResult = null;
			SimpleDateFormat simpleDate = null; 
			
			simpleDate = new SimpleDateFormat("yyyyMM", Locale.getDefault());
			dateResult = simpleDate.parse(searchVO.getBillYm());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월", Locale.getDefault());
			
			Calendar c = Calendar.getInstance();
			c.setTime(dateResult);

			int j = 1;
			
			for(int i=3;i<39;i++) {
//				strHead[i] = new String("M+"+ j + ""); // + j + "";
				c.add(Calendar.MONTH, 1);
				String str = df.format(c.getTime());
				strHead[i] = str;
//				strHead[i] = "M+"+ j + ""; // + j + "";
				strValue[i] = "mon" + j + "";
				intWidth[i] = 20;
				intLen[i] = 1;
				j++;
			}
			
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			LOGGER.debug("--- 대용량 엑셀저장시작 ---");
			
			StringBuilder sbFileName = new StringBuilder();
			StringBuilder teplateFileName = new StringBuilder();
			
			// Step 1. Create a template file. Setup sheets and workbook-level objects such as
			// cell styles, number formats, etc.
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(sheetName);
			
			Map<String, XSSFCellStyle> styles = fileDownService.createStyles2(wb);
			String sheetRef = sheet.getPackagePart().getPartName().getName();

			Date toDay = new Date();
			
			String strFileName = fileName;
			String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
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

			//Step 2. Generate XML file.
			File tempDir = new File(serverInfo);
			File tmp = File.createTempFile("sheet", ".xml", tempDir);
			Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), XML_ENCODING);
			
			
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("billYm",searchVO.getBillYm());
			
			ExcelWriter sw = new ExcelWriter(fw);
			sw.beginWorkSheet();
			// 칼럼 사이즈 변경
			List<SfExcelAttribute> customCellSizeList = new ArrayList<SfExcelAttribute>();
			for (int i = 1; i < intWidth.length+1; i++) {
				customCellSizeList.add(new SfExcelAttribute(i,i,intWidth[i-1],1));
			}
			
			sw.customCell(customCellSizeList);
			sw.beginSheetBond();
			bondMgmtMapper.getBondScheBig(param, new ExcelDataListResultHandler2(sw, styles, strHead, strValue, intLen));
			LOGGER.debug("sw.endSheet()");
			sw.endSheetBond();
			// 셀 머지 (mergeCells의 parent node 는 worksheet 임
			String[] mergeCell = {"A1:A2","B1:B2"};
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
			
			stopWatch.stop();
			LOGGER.debug("--- 대용량 엑셀저장끝 --- 걸린시간 : [" + stopWatch.getTotalTimeSeconds() + "]");
			
			// 엑셀파일 저장한 내역 DB에 남기기
			fileDownService.saveExcelHistory(sbFileName.toString(), searchVO.getUserId());
		} catch(Exception e){
			throw new MvnoServiceException("EBND1012", e);
		}
	}
	
	public void saveExcelBondSoldAsset(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		
		String[] strHead = {"보증보험관리번호","계약번호","고객명","고객구분","생년월일","사업자번호","개통대리점명","고객주소","전화번호","할부시작일자","할부종료일자","상환일","결제방식","채권원금실행액","채권이자실행액","기타실행액","총할부금액","채권원금잔액","채권이자잔액","기타잔액","할부금액잔액","기준월할부잔액","월할부납입액","할부개월","잔여할부개월수"};
		String[] strValue = {"grntInsrMngmNo","contractNum","subLinkName","customerType","userSsn","taxId","orgnNm","subAdrPrimaryLn","subscriberNo","payStrtDt","payEndDt","redeemDt","blBillingMethod","ttlInstAmnt","instIntAmt","instEtcAmt","instTotalAmt","bondSaleAmt","bondSaleIntAmt","bondSaleEtcAmt","bondSaleTotalAmt","rmnInstAmt","monBill","ttlInstMnthCnt","rmnInstCnt"};
		
		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		int[] intLen = {0,0,0,0,0,0,2,2,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1};
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "판매채권자산명세서_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "판매채권자산명세서";
			
			List<?> voLists = bondMgmtMapper.selectSoldAssetList2Ex(searchVO);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);
			
			fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			
		} catch(Exception e){
			throw new MvnoServiceException("EBND1006", e);
		}
	}
	
	public void saveExcelScheduledCf(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		
		String[] strHead = {"청구월","청구금액"};
		String[] strValue = {"saleYm","expBondAmt"};
		
		int[] intWidth = {20,20};
		int[] intLen = {0,1};
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "ScheduledCf_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "Scheduled CF";
			
			List<?> voLists = bondMgmtMapper.selectScheduledCf(searchVO);
			
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);*/
			
			fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			
		} catch(Exception e){
			throw new MvnoServiceException("EBND1007", e);
		}
	}
	
	public void saveExcelCanBond(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		String[] strHead = {"판매회차","회수월","계약번호","전화번호","회수금액","회수처리자","회수이유"};
		String[] strValue = {"saleNo","rtnYm","contractNum","subscriberNo","rtnAmt","rtnId","rtnRsn"};
		
		int[] intWidth = {20,20,20,20,20,20,50};
		int[] intLen = {0,0,0,0,1,0,0};
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "회수채권_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "회수채권";
			
			List<?> voLists = bondMgmtMapper.selectSaleBckInfoList(searchVO);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);
			
			fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			
		} catch(Exception e){
			throw new MvnoServiceException("EBND1008", e);
		}
	}
	
	public void saveExcelSoldStatic(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		String[] strHead = {"판매회차","청구월","월초채권잔액","월초잔존건수","정상청구","연체청구","정상입금","연체입금","조기상환(완납)","조기상환(부분납)","수수료/기타연체가산금","보증보험청구건수","보증보험청구금액","보증보험수납금액","보증보험차액","보상환급이자","하자담보(채권회수)","하자담보(조정금액)","월말채권잔액","월말건수","0개월","1개월","2개월","3개월","4개월","5개월","6개월","7개월","8개월","9개월","10개월","11개월","12개월이상"};
		String[] strValue = {"saleNo","billYm","mnthFstAmt","bondCnt","billAmnt","dlyBillAmt","pymnAmnt","unpayAmnt","nrFullPrfpayAmnt","nrMidPrfpayAmnt","arrsFeeAmnt","insrBillCnt","insrBillAmnt","insrPymnAmnt","insrMtgAmt","insrBckIntAmt","nrRtnAmnt","adjsAmnt","yetPymnAmnt","bondCnt","yetPymnAmnt00","yetPymnAmnt01","yetPymnAmnt02","yetPymnAmnt03","yetPymnAmnt04","yetPymnAmnt05","yetPymnAmnt06","yetPymnAmnt07","yetPymnAmnt08","yetPymnAmnt09","yetPymnAmnt10","yetPymnAmnt11","yetPymnAmnt12"};
		
		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		int[] intLen = {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "판매채권_STATICDATA_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "STATICDATA";
			
			List<?> voLists = bondMgmtMapper.selectSoldBondStaticInfoList(searchVO);
			
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);*/
			
			fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			
		} catch(Exception e){
			throw new MvnoServiceException("EBND1009", e);
		}
	}
	
	/*public void saveExcelSoldAsset(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		String[] strHead = {"보증보험관리번호","계약번호","고객명","고객구분","생년월일","사업자번호","개통대리점명","고객주소","전화번호","할부시작일자","할부종료일자","상환일","결제방식","채권원금실행액","채권이자실행액","기타실행액","총할부금액","채권원금잔액","채권이자잔액","기타잔액","할부금액잔액","기준월할부잔액","월할부납입액","할부개월","잔여할부개월수"};
		String[] strValue = {"grntInsrMngmNo","contractNum","subLinkName","customerType","userSsn","taxId","orgnNm","subAdrPrimaryLn","subscriberNo","payStrtDt","payEndDt","redeemDt","blBillingMethod","ttlInstAmnt","instIntAmt","instEtcAmt","instTotalAmt","bondSaleAmt","bondSaleIntAmt","bondSaleEtcAmt","bondSaleTotalAmt","rmnInstAmt","monBill","ttlInstMnthCnt","rmnInstCnt"};
		
		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1};
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "판매채권자산명세서_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "판매채권자산명세서";
			
			List<?> voLists = bondMgmtMapper.selectSoldAssetListEx(searchVO);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);
			
			fileDownService.saveExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			
		} catch(Exception e){
			throw new MvnoServiceException("EBND1010");
		}
	}*/
	
	/**
	 * @Description : 판매채권자산명세서 엑셀 다운로드
	 * @Param  : 
	 * @Return : AjaxReturnVO
	 * @Author : 정하영
	 * @Create Date : 2015. 8. 23.
	 */
	public void saveExcelSoldAsset(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String[] strHead = {"보증보험관리번호", "계약번호", "고객명", "고객구분", "생년월일", "사업자번호", "개통대리점", "주소", "전화번호", "할부시작일자", "할부종료일자", "상환일", "결제방식", "(최초)채권원금실행액", "(최초)채권이자실행액", "(최초)기타실행액", "(최초)총할부금액",
			            "(매각시)채권원금실행액", "(매각시)채권이자실행액", "(매각시)기타실행액", "(매각시)총할부금액", "(기준월)할부잔액", "월할부납입액", "할부개월", "잔여할부개월수"};
		String[] strValue = {"grntInsrMngmNo", "contractNum", "subLinkName", "customerType", "userSsn", "taxId", "orgnNm", "subAdrPrimaryLn", "subscriberNo" , "payStrtDt", "payEndDt", "redeemDt", "blBillingMethod", "ttlInstAmnt", "instIntAmt", "instEtcAmt", "instTotalAmt",
			  			 "bondSaleAmt", "bondSaleIntAmt", "bondSaleEtcAmt", "bondSaleTotalAmt", "yetPymnAmnt", "monBill", "ttlInstMnthCnt", "rmnInstCnt"};
		
		
		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		int[] intLen = {0,0,0,0,0,0,2,2,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1};
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "판매채권자산명세서_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "판매채권자산명세서";
			
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			LOGGER.debug("--- 대용량 엑셀저장시작 ---");
			
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
			
			bondMgmtMapper.selectSoldAssetListEx(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, getMaskFields(), paramMap));
			
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
			LOGGER.debug("--- 대용량 엑셀저장끝 --- 걸린시간 : [" + stopWatch.getTotalTimeSeconds() + "]");
			
			// 엑셀파일 저장한 내역 DB에 남기기
			fileDownService.saveExcelHistory(sbFileName.toString(), searchVO.getUserId());
			
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("EBND1010", e);
		}
		
	}
	
	
//	public void saveExcelSaleBond(BatchCommonVO batchCommonVO) throws MvnoServiceException {
//		//----------------------------------------------------------------
//		// 목록 db select
//		//----------------------------------------------------------------
//		String[] strHead = {"계약번호", "전화번호", "고객명", "고객유형", "주민번호", "사업자등록번호", "주소", "대리점", "할부원금", "할부기간", "잔여할부개월수", "할부시작일자", "할부종료일자", "채권판매액"};
//		String[] strValue = {"contractNum", "subscriberNo", "subLinkName", "customerType", "userSsn", "taxId", "subAdrPrimaryLn", "openAgntCd", "ttlInstAmnt", "ttlInstMnthCnt", "rmnInstCnt", "payStrtDt", "payEndDt", "yetPymnAmnt"};
//		
//		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20};
//		int[] intLen = {0,0,0,0,0,0,2,2,1,1,1,0,0,1};
//		
//		try {
//			
//			String execParam = batchCommonVO.getExecParam();
//			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
//			
//			String serverInfo = propertiesService.getString("excelPath");
//			String fileName = serverInfo + "채권판매관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
//			String sheetName = "채권판매관리";
//			
//			List<?> voLists = bondMgmtMapper.selectBondSaleCntrListEx(searchVO);
//			
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("userId", searchVO.getUserId());
//			voLists = getMaskingData(voLists, paramMap);
//			
//			fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
//			
//		} catch(Exception e){
//			throw new MvnoServiceException("EBND1011", e);
//		}
//	}
	public void saveExcelSaleBond(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		String[] strHead = {"계약번호", "전화번호", "고객명", "고객유형", "주민번호", "사업자등록번호", "주소", "대리점", "할부원금", "할부기간", "잔여할부개월수", "할부시작일자", "할부종료일자", "채권판매액"};
		String[] strValue = {"contractNum", "subscriberNo", "subLinkName", "customerType", "userSsn", "taxId", "subAdrPrimaryLn", "openAgntCd", "ttlInstAmnt", "ttlInstMnthCnt", "rmnInstCnt", "payStrtDt", "payEndDt", "yetPymnAmnt"};
		
		//엑셀 컬럼 사이즈
		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		int[] intLen = {0,0,0,0,0,0,2,2,1,1,1,0,0,1};
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "채권판매관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "채권판매관리";
			
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
			bondMgmtMapper.selectBondSaleCntrListEx(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, getMaskFields(), paramMap));
			
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
	 * @Description : 공시용자산명세서 엑셀 다운로드
	 * @Param  : 
	 * @Return : AjaxReturnVO
	 * @Author : 정하영
	 * @Create Date : 2015. 8. 23.
	 */
	public void saveExcelSoldAsset2(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String[] strHead = {"계약번호", "보증보험관리번호", "고객명", "생년월일", "할부시작일자", "할부종료일자", "할부개월", "잔여개월", "할부금액", "채권잔액", "주소"};
		String[] strValue = {"contractNum", "grntInsrMngmNo", "subLinkName", "userSsn", "payStrtDt", "payEndDt", "ttlInstMnthCnt", "rmnInstCnt", "ttlInstAmnt", "yetPymnAmnt", "subAdrPrimaryLn"};
		
		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20};
		int[] intLen = {0,0,0,0,0,0,1,1,1,1,2}; 
		
		
		try {
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "공시용자산명세서_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "공시용자산명세서";
			
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			LOGGER.debug("--- 대용량 엑셀저장시작 ---");
			
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
			
			bondMgmtMapper.selectSoldAssetList2Ex(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, getMaskFields(), paramMap));
			
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
			LOGGER.debug("--- 대용량 엑셀저장끝 --- 걸린시간 : [" + stopWatch.getTotalTimeSeconds() + "]");
			
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
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e){
			e.printStackTrace();
			throw new MvnoServiceException("EBND1011", e);
		}
		
	}


	/**
	 * @Description : 공시용자산명세서 금감원 제출용 엑셀 다운로드
	 * @Param  : 
	 * @Return : AjaxReturnVO
	 * @Author : 박준성
	 * @Create Date : 2017. 09. 08.
	 */
	public void saveExcelSoldAsset3(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String[] strHead = {"계약번호", "보증보험관리번호", "고객명", "생년월일", "할부시작일자", "할부종료일자", "할부개월", "잔여개월", "할부금액", "채권잔액", "주소"};
		String[] strValue = {"contractNum", "grntInsrMngmNo", "subLinkName", "userSsn", "payStrtDt", "payEndDt", "ttlInstMnthCnt", "rmnInstCnt", "ttlInstAmnt", "yetPymnAmnt", "subAdrPrimaryLn"};
		
		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,50};
		int[] intLen = {0,0,0,0,0,0,1,1,1,1,2}; 
		
		
		try {
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "공시용자산명세서_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "공시용자산명세서";
			
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			LOGGER.debug("--- 대용량 엑셀저장시작 ---");
			
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
			
			bondMgmtMapper.selectSoldAssetList2Ex(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, getMaskFields2(), paramMap));
			
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
			LOGGER.debug("--- 대용량 엑셀저장끝 --- 걸린시간 : [" + stopWatch.getTotalTimeSeconds() + "]");
			
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
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e){
			e.printStackTrace();
			throw new MvnoServiceException("EBND1011", e);
		}
		
	}
	
	/**
	 * @Description : 매각채권수납내역서 리스트 엑셀 다운로드
	 * @Param  : 
	 * @Return : AjaxReturnVO
	 * @Author : 정하영
	 * @Create Date : 2017. 11. 20.
	 */
	public void saveExcelSoldReceiptByMonth(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String[] strHead = {"보증보험관리번호", "월초채권잔액", "할부회차", "청구금액", "청구상환", "연체상환", "미납금액", "미납금액(누적)", "미납회차", 
				            "조기상환(완납)", "조기상환(부분납)", "보증보험수납", "감액", "연체이자청구", "연체이자수납","채권감소금액", "수납금액", "월말 채권잔액"};
		String[] strValue = {"grntInsrMngmNo", "prvYetPymnAmnt", "instCnt", "billAmnt", "pymnAmnt", "unpayAmnt", "toUnpayArrsAmnt", "unpayArrsAmnt", "unpayArrsTmscnt", 
				             "fullPrfpayAmnt", "midPrfpayAmnt", "grntInsrAmnt", "adjsAmnt", "arrsFeeInvAmnt", "arrsFeeAmnt", "dscAmnt", "realPymnAmnt", "yetPymnAmnt"};
		
		int[] intWidth = {30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30};
		int[] intLen = {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}; 
		
		
		try {
			String execParam = batchCommonVO.getExecParam();
			BondMgmtVO searchVO = (BondMgmtVO) JacksonUtil.makeVOFromJson(execParam, BondMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "매각채권수납내역서_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "매각채권수납내역서";
			
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			LOGGER.debug("--- 대용량 엑셀저장시작 ---");
			
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
			
			bondMgmtMapper.selectSoldReceiptByMonth(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, getMaskFields(), paramMap));
			
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
			LOGGER.debug("--- 대용량 엑셀저장끝 --- 걸린시간 : [" + stopWatch.getTotalTimeSeconds() + "]");
			
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
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e){
			e.printStackTrace();
			throw new MvnoServiceException("EBND1011", e);
		}
		
	}
	
	
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
	
	// 금감원 제출용 마스킹처리를 위한 필드
	public HashMap<String, String> getMaskFields2() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subLinkName","CUST_NAME2");
		maskFields.put("userSsn","BIRTH");
		maskFields.put("subAdrPrimaryLn","ADDR2");
		
		return maskFields;
	}		
	
}
