package com.ktis.msp.batch.job.rcp.rcpmgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.RcpMgmtMapper;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpListVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class RcpMgmtService extends BaseService {
	
	@Autowired
	private RcpMgmtMapper rcpMapper;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	/**
	 * 기변대상생성
	 * @param param
	 * @return
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setDvcChgTrgtMst(Map<String, Object> param) throws MvnoServiceException{
		
		try {
				
			rcpMapper.callDvcChgTrgtProc(param);
				
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1042", e);
		}
		
	}
	
	
	
	@Autowired
	private RcpMgmtMapper rcpMgmtMapper;
	
//	@Autowired
//	private MaskingService maskingService;
//
//	@Autowired
//	private MaskingMapper maskingMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	
	public void selectRcpListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		// 2018-02-05, 기기변경고개혜택(benefitName) 항목 추가
		//----------------------------------------------------------------
		String [] strHead = {"상품구분","서비스구분","예약번호","고객명","생년월일","성별","구매유형","특별판매코드","특별판매코드명","이동전 통신사",
				 "제품명","제품색상","휴대폰번호","대리점","요금제","업무구분","고객주소","배송주소","배송우편번호","배송휴대폰번호",
				 "유선연락처","신청일자","진행상태","신청서상태","모집경로","유입경로","추천인구분","추천인정보","녹취여부","할인유형",
				 "렌탈서비스동의","단말배상금동의","단말배상금(부분파손)동의","렌탈기본료","렌탈할인금액","단말배상금액","프로모션명","프로모션혜택","기변고객혜택","수정자",
				 "수정일자"};	//41
		String [] strValue = {"prodTypeNm","serviceName","resNo","cstmrName","birthDt","cstmrGenderStr","reqBuyType","slsNo","slsNm","moveCompany",
				  "prdtNm","prdtColrNm","cstmrMobile","agentName","socName","operName","cstmrAddr","dlvryAddr","dlvryPost","dlvryMobile",
				  "cstmrTelNo","reqInDay","requestStateName","pstateName","onOffName","openMarketReferer","recommendFlagNm","recommendInfo","recYn","sprtTpNm",
				  "clauseRentalService","clauseRentalModelCp","clauseRentalModelCpPr","rentalBaseAmt","rentalBaseDcAmt","rentalModelCpAmt","promotionNm","benefitCmmt","benefitName","rvisnNm",
				  "rvisnDttm"};	//41
		//엑셀 컬럼 사이즈
		int[] intWidth = {15, 15, 15, 15, 15,  5, 15, 15, 30, 15, 
						  30, 15, 20, 30, 30, 15, 50, 50, 15, 15, 
						  15, 15, 15, 15 ,15 ,30, 30, 30, 15, 15, 
						  15, 15, 15, 15, 15, 15, 30, 50, 30, 15, 
						  15};
		int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 
						0};
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			RcpListVO searchVO = (RcpListVO) JacksonUtil.makeVOFromJson(execParam, RcpListVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "신청관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "신청관리";
			
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
			

			// 서비스계약번호로 계약번호 추출
			if("2".equals(searchVO.getpSearchGbn())){
				String pContractNum = rcpMgmtMapper.getpContractNum(searchVO.getpSearchName());	

				if(pContractNum != null && !"".equals(pContractNum)){
					searchVO.setpSearchName(pContractNum);
				}
			}
			
			// db
			rcpMgmtMapper.selectRcpListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, getMaskFields(), paramMap));
			
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
			throw new MvnoServiceException("신청관리 엑셀저장 에러", e);
		}
	}
	
//	/**
//	 * 신청관리 엑셀 다운로드
//	 * @param batchCommonVO
//	 * @throws MvnoServiceException
//	 */
//	public void selectRcpListExcel(BatchCommonVO batchCommonVO, String a) throws MvnoServiceException {
//		//----------------------------------------------------------------
//		// 목록 db select
//		//----------------------------------------------------------------
//		
//		String [] strHead = {"상품구분", "서비스구분", "예약번호", "고객명", "생년월일", "성별", "구매유형", "이동전 통신사", "제품명", "제품색상",  "휴대폰번호", "대리점", "요금제", "업무구분", "고객주소", "배송주소", "배송우편번호" ,"배송휴대폰번호"
//				, "유선연락처", "신청일자", "진행상태", "신청서상태" , "모집경로", "유입경로" , "추천인구분", "추천인정보", "녹취여부", "할인유형", "렌탈서비스동의", "단말배상금동의", "단말배상금(부분파손)동의", "렌탈기본료", "렌탈할인금액", "단말배상금액"};
//
//		String [] strValue = {"prodTypeNm", "serviceName","resNo","cstmrName","birthDt", "cstmrGenderStr", "reqBuyType", "moveCompany", "prdtNm","prdtColrNm","cstmrMobile","agentName","socName","operName", "cstmrAddr", "dlvryAddr", "dlvryPost" ,"dlvryMobile"
//				, "cstmrTelNo","reqInDay","requestStateName", "pstateName" , "onOffName", "openMarketReferer", "recommendFlagNm", "recommendInfo" ,"recYn", "sprtTpNm", "clauseRentalService", "clauseRentalModelCp", "clauseRentalModelCpPr", "rentalBaseAmt", "rentalBaseDcAmt", "rentalModelCpAmt"};
//
//		//엑셀 컬럼 사이즈
//		int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 15000, 5000, 5000, 8000, 15000, 5000, 15000, 15000, 5000, 5000, 5000, 5000, 5000, 5000 , 5000 , 5000, 5000, 5000, 5000, 5000, 8000, 8000, 8000, 5000, 5000, 5000};
//
//		int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0, 0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1};
//
//		try {
//			String execParam = batchCommonVO.getExecParam();
//			RcpListVO searchVO = (RcpListVO) JacksonUtil.makeVOFromJson(execParam, RcpListVO.class);
//			
//			String serverInfo = propertiesService.getString("excelPath");
//			String fileName = serverInfo + "신청관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
//			String sheetName = "신청관리";
//			
//			LOGGER.info("\tBATCH EXCEL DOWN : 조회 시작");
//			List<RcpListVO> voLists = rcpMgmtMapper.selectRcpListExcel(searchVO);
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
//			for(RcpListVO item : voLists) {
//				maskingService.setMask(item, maskFields, lCmnMskGrp);
//			}
//			
//			
//			for(RcpListVO rcpListVO : voLists){
//				String[] ssn = StringUtil.getJuminNumber(rcpListVO.getSsn());
//								
//				rcpListVO.setCstmrGenderStr(StringUtils.substring(ssn[1], 0, 1));
//				rcpListVO.setBirthDt(StringUtils.substring(ssn[0], 0, 6));
//				
//				// 개인정보제거
//				rcpListVO.setCstmrNativeRrn("");
//				rcpListVO.setCstmrForeignerRrn("");
//				rcpListVO.setSsn("");
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
//				pRequestParamMap.put("DATA_CNT"  , voLists.size());				//자료건수
//				pRequestParamMap.put("SESSION_USER_ID", searchVO.getUserId());	//사용자ID
//				pRequestParamMap.put("DWNLD_RSN", searchVO.getDwnldRsn());		//다운로드사유
//				pRequestParamMap.put("EXCL_DNLD_ID", Integer.parseInt(searchVO.getExclDnldId()));
//				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
//			}
//			//=======파일다운로드사유 로그 END==========================================================
//			LOGGER.info("BATCH EXCEL DOWN END");
//			
//		} catch(Exception e){
//			throw new MvnoServiceException("신청관리 엑셀저장 에러", e);
//		}
//	}

	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrName",			"CUST_NAME");
		maskFields.put("cstmrForeignerRrn",	"SSN");
		maskFields.put("cstmrMobile",		"MOBILE_PHO");
		maskFields.put("cstmrForeignerPn",	"PASSPORT");
		maskFields.put("cstmrJuridicalRrn",	"CORPORATE");
		maskFields.put("cstmrMail",			"EMAIL");
		maskFields.put("cstmrAddr",			"ADDR");
		maskFields.put("cstmrTel",			"TEL_NO");
		maskFields.put("dlvryName",			"CUST_NAME");
		maskFields.put("dlvryTel",			"TEL_NO");
		maskFields.put("dlvryMobile",		"MOBILE_PHO");
		maskFields.put("dlvryAddr",			"ADDR");
		maskFields.put("reqAccountNumber",	"ACCOUNT");
		maskFields.put("reqAccountName",	"CUST_NAME");
		maskFields.put("reqAccountRrn",		"SSN");
		maskFields.put("reqCardNo",			"CREDIT_CAR");
		maskFields.put("reqCardMm",			"CARD_EXP");
		maskFields.put("reqCardYy",			"CARD_EXP");
		maskFields.put("reqCardName",		"CUST_NAME");
		maskFields.put("reqCardRrn",		"SSN");
		maskFields.put("reqGuide",			"MOBILE_PHO");
		maskFields.put("moveMobile",		"MOBILE_PHO");
		maskFields.put("minorAgentName",	"CUST_NAME");
		maskFields.put("minorAgentRrn",		"SSN");
		maskFields.put("minorAgentTel",		"TEL_NO");
		maskFields.put("reqUsimSn",			"USIM");
		maskFields.put("reqPhoneSn",		"DEV_NO");
		maskFields.put("cstmrNativeRrn",	"SSN");
		maskFields.put("faxnum",			"TEL_NO");
		maskFields.put("selfIssuNum",		"PASSPORT");	// 본인인증번호
		maskFields.put("minorSelfIssuNum",	"PASSPORT");	// 본인인증번호
		
		return maskFields;
	}
	
	
	/**
	 * 개통고객 LMS 전송
	 * @param param
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setDelayOpenInfoLms() throws MvnoServiceException{
		int nRslt = 0;
		
		//SMS 발송 대상 조회
		RcpListVO rcpVO = new RcpListVO();
		List<RcpListVO> aryList = rcpMapper.getDelayOpenList(rcpVO);
		
		//SMS 발송 처리
		String templateId = "34";
		SmsCommonVO smsVO = smsCommonMapper.getTemplateText(templateId);
		smsVO.setSendMessage(smsVO.getTemplateText());
		smsVO.setTemplateId(templateId);
		
		SimpleDateFormat  formatter  = new SimpleDateFormat("yyyyMMdd");
		String todate =  formatter.format(new Date());
		
		smsVO.setRequestTime(todate+"090000");
		
		for( RcpListVO rcpListVO : aryList  ){
			try{
				int iLmsSendCnt = 0;
				
				smsVO.setMobileNo(rcpListVO.getCstmrMobile());
				
				// SMS 발송 등록
				iLmsSendCnt = smsCommonMapper.insertTemplateMsgQueue(smsVO);
				
				smsVO.setSendDivision("MSP");
				smsVO.setReqId(BatchConstants.BATCH_USER_ID);
				
				// SMS 발송내역 등록
				smsCommonMapper.insertSmsSendMst(smsVO);
				
				if(iLmsSendCnt > 0){
					nRslt++;
				}
			}catch (Exception e){
				e.printStackTrace();
				throw new MvnoServiceException(e.getMessage());
			}
		}
		
		return nRslt;
	}
}
