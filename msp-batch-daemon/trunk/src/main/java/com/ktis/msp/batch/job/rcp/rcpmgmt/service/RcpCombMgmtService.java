package com.ktis.msp.batch.job.rcp.rcpmgmt.service;

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
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.RcpCombMgmtMapper;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpCombMgmtListVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.auth.AuthChkService;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.common.masking.MaskingService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class RcpCombMgmtService extends BaseService {

//	@Autowired
//	private RcpEncService encSvc;
	
	@Autowired
	private RcpCombMgmtMapper rcpCombMgmtMapper;
		
	@Autowired
	private AuthChkService authChkService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private MaskingService maskingService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	public void selectRcpCombMgmtListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String [] strHead = {
						"결합유형",				"결합A 계약번호",				"결합A 고객명",					"결합A 성별",				"결합A 생년월일",				 		//5
						"결합A CTN", 				"결합A 개통일자",              	"결합A 상품명",					"결합A 상품코드",			"결합A 부가서비스명",						//5 	
						"결합A 부가서비스코드", 		"결합A 거주지",					"결합A 대리점",					"결합A 이동전통신사",			"신청경로",								//5
						"결합B 계약번호",			"결합B 고객명",					"결합B 성별",					"결합B 생년월일",			"결합B CTN",							//5
						"결합B 개통일",				"결합B 상품구분", 				"결합B 상품명",					"결합B 상품코드",			"결합B 부가서비스명",						//5
						"결합B 부가서비스코드", 		"결합B 거주지",	 				"결합B 대리점",					"결합B 이동전통신사",												//4
						"신청일",					"결합대상",					"승인여부",					"비고"														//4
						}; 
		String [] strValue = {
						"combTypeNm",			"mSvcCntrNo",				"cstmrName",				"mSexNm",			 	"mCustBirth",						//5
						"tel1",					"mOpenDt" , 				"mRateNm",					"mRateCd",			    "mRateAdsvcNm",						//5
						"mRateAdsvcCd",  		"mAddr",					"mOpenAgntNm",				"moveCompanyNm",		"requestMethod",					//5
						"combSvcCntrNo",		"cstmrName2",				"combSexNm",				"combBirth",	        "tel2",								//5
						"combOpenDt",			"combDtlTypeNm",			"combSocNm",				"combSocCd",			"combRateAdsvcNm",					//5
						"combRateAdsvcCd", 		"combAddr",					"combOpenAgntNm",			"combMoveCompanyNm",										//4
						"sysDt",				"combTgtTypeNm",			"rsltNm",					"rsltMemo"													//4
						};
		int[] intWidth = {
						20, 20, 20, 20, 20,	
						20, 20, 20, 20, 25, 
						25, 25, 20, 20, 15,
						20, 20, 20, 20, 20, 
						20, 20, 20, 20, 25, 
						25, 25, 20, 20,
						15, 15, 15, 30
						};
		int[] intLen = {
						0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 
						0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 
						0, 0, 0, 0, 0, 
						0, 0, 0, 0,
						0, 0, 0, 0
						};
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			
			String execParam = batchCommonVO.getExecParam();
			RcpCombMgmtListVO searchVO = (RcpCombMgmtListVO) JacksonUtil.makeVOFromJson(execParam, RcpCombMgmtListVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "신청관리(결합서비스)_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "신청관리(결합서비스)";
			
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
			rcpCombMgmtMapper.selectRcpCombListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
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
			LOGGER.error(StringUtil.getPrintStackTrace(e));
			throw new MvnoServiceException("개통관리 엑셀저장 에러", e);
		}
	}
	
	
//	/**
//	 * 개통관리(신규) 엑셀 다운로드
//	 * @param batchCommonVO
//	 * @throws MvnoServiceException
//	 */
//	public void selectOpenMgmtListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
//		//----------------------------------------------------------------
//		// 목록 db select
//		//----------------------------------------------------------------
//		
//		//엑셀 첫줄
//		// 2016-12-16, 기변정보추가
//		String [] strHead = {"상품구분","계약번호","예약번호","고객명","생년월일","성별","나이(만)","구매유형","업무구분","이동전 통신사", // 10
//						"휴대폰번호","최초요금제","현재요금제","판매정책","약정개월수","할부개월수","단말모델명","단말일련번호","최초개통단말","최초단말일련번호", // 10
//						"상태","해지사유","모집경로","유입경로","추천인구분","추천인정보","녹취여부","대리점","판매점","신청일자", // 10
//						"개통일자","해지일자","정지일자","법정대리인","관계","대리인연락처","유해차단APP명","가입비납부방법","가입비","USIM납부방법", // 10
//						"USIM비","대리점ID","할부원금","할인유형","보증보험관리번호","보증보험가입상태","심플할인ID","심플약정기간","심플약정시작일자","심플약정종료일자", // 10
//						"심플약정상태","렌탈기본료","렌탈료할인","렌탈단말배상금","기변횟수","기변유형","기변일자","기변모델ID","기변모델명","기변단말일련번호",	// 10
//						"기변대리점","기변단말출고가","기변단말할부원금","기변단말할부개월","기변약정개월","기변판매정책","OTA등록일자","캐치콜가입일자"	// 8
//						}; 
//		String [] strValue = {"prodTypeNm","contractNum","resNo","cstmrName","birth","gender","age","reqBuyTypeNm","operTypeNm","moveCompanyNm",
//						"subscriberNo","fstRateNm","lstRateNm","salePlcyNm","enggMnthCnt","instMnthCnt","lstModelNm","lstModelSrlNum","fstModelNm","fstModelSrlNum",
//						"subStatusNm","canRsn","onOffTypeNm","openMarketReferer","recommendFlagNm","recommendInfo","recYn","agentNm","shopNm","reqInDay",
//						"openDt","tmntDt","stopDt","minorAgentName","minorAgentRelation","minorAgentTel","appNm","joinPayMthdNm","joinPrice","usimPayMthdNm",
//						"usimPrice","openAgntCd","instOrginAmnt","sprtTpNm","grntInsrMngmNo","grntInsrStatNm","simId","simEnggMnthCnt","simStartDt","simEndDt",
//						"simStatNm","rentalBaseAmt","rentalBaseDcAmt","rentalModelCpAmt","dvcChgCnt","dvcOperTypeNm","dvcChgDt","dvcModelId","dvcModelNm","dvcIntmSrlNo",
//						"dvcAgntNm","dvcHndstAmnt","dvcInstOrginAmnt","dvcInstMnthCnt","dvcEnggMnthCnt","dvcSalePlcyNm","otaDate","catchcall"		// 8
//						};
//		int[] intWidth = {3000, 3000, 3000, 5000, 3000, 3000, 3000, 5000, 5000, 5000,
//						5000, 8000, 8000, 12000, 5000, 5000, 5000, 5000, 5000, 6000,
//						5000, 5000, 3000, 3000, 5000, 5000, 3000, 8000, 8000, 5000,
//						5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 
//						5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 
//						5000, 5000, 5000, 8000, 3000, 3000, 3000, 5000, 5000, 8000, 
//						5000, 6000, 6000, 6000, 6000, 12000, 6000, 5000
//						};	
//		int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//						0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//						0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//						0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
//						1, 0, 1, 0, 0, 0, 0, 1, 0, 0,
//						0, 1, 1, 1, 1, 0, 0, 0, 0, 0,
//						0, 1, 1, 1, 1, 0, 0, 0
//						};
//		try {
//			String execParam = batchCommonVO.getExecParam();
//			OpenMgmtListVO searchVO = (OpenMgmtListVO) JacksonUtil.makeVOFromJson(execParam, OpenMgmtListVO.class);
//			
//			String serverInfo = propertiesService.getString("excelPath");
//			String fileName = serverInfo + "개통관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
//			String sheetName = "개통관리";
//			
//			LOGGER.info("\tBATCH EXCEL DOWN : 조회 시작");
//			List<OpenMgmtListVO> voLists = openMgmtMapper.selectOpenMgmtListExcel(searchVO);
//			LOGGER.info("\tBATCH EXCEL DOWN : 복호화 시작");
//			com.ktds.crypto.decryptor.aes.AES256Decryptor svc = new AES256Decryptor();
//			KeyInfo keyinfo = new KeyInfo();
//			keyinfo.setName("DBMS-AES256-Key");
//			keyinfo.setDefaultEncoding("UTF-8");
//			keyinfo.setKeyString("S3QxMTQ2NTQ1MDExNElTMzc3NjQ5NDkwMTBNb2JpbGU=");
//			keyinfo.setEnableSalt(true);
//			keyinfo.setSaltString("AAAAAAAAAAAAAAAAAAAAAA==");
//			
//			svc.setKeyInfo(keyinfo);
//			
//			for(int i = 0; i<voLists.size(); i++){
//				String dscryptingSsn = svc.decrypting(voLists.get(i).getSsn());
//				voLists.get(i).setSsn(dscryptingSsn);
//			}
//			
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("SESSION_USER_ID", searchVO.getUserId());
//			
//			LOGGER.info("\tBATCH EXCEL DOWN : 마스킹 시작");
//			List<MaskingVO> lCmnMskGrp = maskingMapper.selectListNotEgovMap(paramMap);
//			
//			HashMap<String, String> maskFields = getMaskFields();
//			
//			for(OpenMgmtListVO item : voLists) {
//				maskingService.setMask(item, maskFields, lCmnMskGrp);
//			}
//			
//			for(OpenMgmtListVO openMgmtVO : voLists){
//				String[] cstmrNativeRrn = StringUtil.getJuminNumber(openMgmtVO.getSsn());
//				
//				if("JP".equals(openMgmtVO.getCstmrType()) || "PP".equals(openMgmtVO.getCstmrType())){
//					openMgmtVO.setBirth("");
//					openMgmtVO.setAge("");
//					openMgmtVO.setGender("");
//				}else{
//					openMgmtVO.setBirth(cstmrNativeRrn[0]);
//					openMgmtVO.setAge(String.valueOf(StringUtil.getAge(openMgmtVO.getSsn())));
//					openMgmtVO.setGender(cstmrNativeRrn[1].substring(0,1));
//				}
//				
//				// 개인정보제거
//				openMgmtVO.setSsn("");
//			}
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
//			//=======파일다운로드사유 로그 END==========================================================
//			LOGGER.info("BATCH EXCEL DOWN END");
//		} catch(Exception e){
//			LOGGER.error(StringUtil.getPrintStackTrace(e));
//			throw new MvnoServiceException("개통관리 엑셀저장 에러", e);
//		}
//	}

}
