package com.ktis.msp.batch.job.voc.vocMgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ktis.msp.batch.job.voc.vocMgmt.vo.GiftPayStatVO;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.GiftVocVO;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.crypto.base.KeyInfo;
import com.ktds.crypto.decryptor.aes.AES256Decryptor;
import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.openmgmt.vo.OpenMgmtListVO;
import com.ktis.msp.batch.job.voc.vocMgmt.mapper.VocListMgmtMapper;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.VocListMgmtVO;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.VocListNotEgovMapVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.util.StringUtil;
import com.ktis.msp.batch.manager.common.masking.MaskingService;

import egovframework.rte.fdl.property.EgovPropertyService;
import org.springframework.util.StopWatch;

@Service
public class VocListMgmtService extends BaseSchedule {
	
	@Autowired
	private VocListMgmtMapper vocListMgmtMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

	private static final String XML_ENCODING = "UTF-8";
	
	/**
	 * 상담목록 엑셀 다운로드
	 * @param batchCommonVO
	 * @throws MvnoServiceException
	 */
	public void selectVocListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		
		String [] strHead = {	"접수ID","고객CTN","고객명","서비스구분","상품구분","고객ID","계약ID","고객여부","고객관계","요청고객명",
								"요청CTN","접수부서ID","접수부서","접수자ID","접수자","접수일시","대분류","중분류","소분류","상담상태",
								"처리부서ID","처리부서","처리자ID","처리자","처리일시","완료부서ID","완료부서","완료자ID","완료자","완료일시",
								"소요시간(분)","잔여시간(분)","요청내용","응대내용","처리내용","나이(만)","사용중인요금제","가입대리점"};
		
		String [] strValue = {	"rcpId","telNum","custNm","pppo","prodTypeNm","customerId","contractNum","custYnNm","custRelNm","reqCustNm",
								"reqTelNum","reqOrgnId","reqOrgnNm","reqrId","reqrNm","reqDttm","cnslMstNm","cnslMidNm","cnslDtlNm","cnslStatNm",
								"procOrgnId","procOrgnNm","procrId","procrNm","procDttm","cmplOrgnId","cmplOrgnNm","cmplrId","cmplrNm","cmplDttm",
								"procMm","remainMm","reqCntt","resCntt","procCntt","custAge","lstRateNm","orgnNm"};
		
		int[] intWidth = {5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,
						5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,
						5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,
						5000,5000,10000,10000,10000,3000,5000,5000};
		int[] intLen = {0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,
						1,0,0,0,0,0,0,0};
		
		try {
			String execParam = batchCommonVO.getExecParam();
			VocListMgmtVO searchVO = (VocListMgmtVO) JacksonUtil.makeVOFromJson(execParam, VocListMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "상담목록_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "상담목록";

			List<VocListNotEgovMapVO> voLists = vocListMgmtMapper.selectVocListExcel(searchVO);
			LOGGER.info("\tBATCH EXCEL DOWN : 복호화 시작");
			com.ktds.crypto.decryptor.aes.AES256Decryptor svc = new AES256Decryptor();
			KeyInfo keyinfo = new KeyInfo();
			keyinfo.setName("DBMS-AES256-Key");
			keyinfo.setDefaultEncoding("UTF-8");
			keyinfo.setKeyString("S3QxMTQ2NTQ1MDExNElTMzc3NjQ5NDkwMTBNb2JpbGU=");
			keyinfo.setEnableSalt(true);
			keyinfo.setSaltString("AAAAAAAAAAAAAAAAAAAAAA==");

			svc.setKeyInfo(keyinfo);

			for(int i = 0; i<voLists.size(); i++){
				if(voLists.get(i).getSsn() == null || "".equals(voLists.get(i).getSsn())){
					voLists.get(i).setSsn("-");	
				}else{
					String dscryptingSsn = svc.decrypting(voLists.get(i).getSsn());
					voLists.get(i).setSsn(dscryptingSsn);
				}
			}

			for(VocListNotEgovMapVO vocListNotEgovMapVO : voLists){	
				if("JP".equals(vocListNotEgovMapVO.getCstmrType()) || "PP".equals(vocListNotEgovMapVO.getCstmrType())
						|| "".equals(vocListNotEgovMapVO.getCstmrType()) || vocListNotEgovMapVO.getCstmrType() == null){
					vocListNotEgovMapVO.setCustAge("-");
				}else{
					vocListNotEgovMapVO.setCustAge(String.valueOf(StringUtil.getAge(vocListNotEgovMapVO.getSsn())));
				}
				
				// 개인정보제거
				vocListNotEgovMapVO.setSsn("");
			}

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);
			
			String fileNm = null;

			LOGGER.info("\tBATCH EXCEL DOWN : 파일 생성 시작(" + voLists.size() + ")건");

			if(voLists.size() <= 10000) {
				fileNm = fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists.iterator(), strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			} else {
				fileNm = fileDownService.csvDownProcStream(fileName, voLists.iterator(), strHead, strValue, searchVO.getUserId(), batchCommonVO.getReqDttm());
			}
			File file = new File(fileNm);
			Map<String, Object> pRequestParamMap = new HashMap<String, Object>();
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(searchVO.getDwnldRsn())){
			
				pRequestParamMap.put("FILE_NM"   , file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" , file.getPath());				//파일경로
				pRequestParamMap.put("DUTY_NM"   , "VOC");						//업무명
				pRequestParamMap.put("IP_INFO"   , searchVO.getIpAddr());		//IP정보
				pRequestParamMap.put("FILE_SIZE" , (int) file.length());		//파일크기
				pRequestParamMap.put("menuId"	 , searchVO.getMenuId());		//메뉴ID
				pRequestParamMap.put("DATA_CNT"  , 0);							//자료건수
				pRequestParamMap.put("SESSION_USER_ID", searchVO.getUserId());	//사용자ID
				pRequestParamMap.put("DWNLD_RSN", searchVO.getDwnldRsn());		//다운로드사유
				pRequestParamMap.put("EXCL_DNLD_ID", Integer.parseInt(searchVO.getExclDnldId()));
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			LOGGER.info("BATCH EXCEL DOWN END");
			
		} catch(Exception e){
			LOGGER.error(StringUtil.getPrintStackTrace(e));
			throw new MvnoServiceException("EVOC1001", e);
		}
	}

	// 주소,이름,전화번호를 마스킹처리한다.
	public List<VocListNotEgovMapVO> getMaskingData(List<VocListNotEgovMapVO> result, Map<String, Object> paramMap) {
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		return result;
	}

	/**
	 * 사은품 VOC 엑셀 다운로드
	 * @param batchCommonVO
	 * @throws MvnoServiceException
	 */
	public void getGiftVocListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		String [] strHead = {
				"No","고객명","계약번호","전화번호","개통일자",
				"신청일자","최초 요금제","현재 요금제","상태","추천인정보",
				"모집경로","유입경로","민원인","프로모션명","상품권종류",
				"금액","접수상태","인입CTN","구분","비고",
				"담당부서","VOC구분","신청일","회원ID","추천인ID",
				"추천인계약번호","대외기관 언급여부","VOC내용","기타 특이사항","지급/미지급사유",
				"지급품목","지급금액","지급예정월","답변상태","처리(답변)일자",
				"등록일자","등록자ID","등록자","수정일자","수정자ID",
				"수정자"
		};
		String [] strValue = {
				"rnum","sublinkname","contractnum","subscriberno","lstcomactvdate",
				"reqinday","fstratenm","lstratenm","substatus","recommendinfo",
				"onofftype","openmarketreferer","custnm","promnm","gifttype",
				"giftamt","vocstat","vocctn","divisn","rmk",
				"vocmngorgn","voctype","vocreqdt","userid","recommid",
				"recommcontractnum","extnlorgnrefyn","vocdesc","ansrmk","anspayrsn",
				"anspayitem","anspayamt","anspayplanmon","ansstat","ansprocdttm",
				"regstdttm","regstid","regstnm","rvisndttm","rvisnid",
				"rvisnnm"
		};
		int[] intWidth = {
				10, 15, 15, 15, 15,
				20, 30, 30, 15, 20,
				15, 20, 15, 25, 20,
				15, 15, 15, 20, 25,
				20, 15, 15, 15, 15,
				15, 25, 30, 25, 25,
				20, 15, 15, 15, 25,
				25, 15, 15, 25, 15, 
				15
		};
		int[] intLen = {
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0
		};
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------

			String execParam = batchCommonVO.getExecParam();
			GiftVocVO searchVO = (GiftVocVO) JacksonUtil.makeVOFromJson(execParam, GiftVocVO.class);

			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "사은품VOC_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "사은품VOC";

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
			vocListMgmtMapper.getGiftVocListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));

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
				pRequestParamMap.put("DUTY_NM"         , "VOC");                          //업무명
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
			LOGGER.error(StringUtil.getPrintStackTrace(e));
			throw new MvnoServiceException("사은품 VOC 엑셀저장 에러", e);
		}
	}

	/**
	 * 사은품 지급 리스트 엑셀 다운로드
	 * @param batchCommonVO
	 * @throws MvnoServiceException
	 */
	public void getGiftPayStatExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		String [] strHead = {
				"지급월","계약번호","고객명","CTN","제세공과금 회신 여부",
				"프로모션명","사은품종류","금액","등록일자","등록자ID",
				"등록자"
		};
		String [] strValue = {
				"paymon","contractnum","custnm","ctn","taxrplyyn",
				"promnm","gifttype","giftamt","regstdttm","regstid",
				"regstnm"
		};
		int[] intWidth = {
				15, 15, 15, 15, 25,
				25, 15, 15, 25, 15,
				15
		};
		int[] intLen = {
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 
				0
		};
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------

			String execParam = batchCommonVO.getExecParam();
			GiftPayStatVO searchVO = (GiftPayStatVO) JacksonUtil.makeVOFromJson(execParam, GiftPayStatVO.class);

			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "사은품 지급 리스트_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "사은품 지급 리스트";

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
			vocListMgmtMapper.getGiftPayStatExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));

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
				pRequestParamMap.put("DUTY_NM"         , "VOC");                          //업무명
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
			LOGGER.error(StringUtil.getPrintStackTrace(e));
			throw new MvnoServiceException("사은품 VOC 엑셀저장 에러", e);
		}
	}
}
