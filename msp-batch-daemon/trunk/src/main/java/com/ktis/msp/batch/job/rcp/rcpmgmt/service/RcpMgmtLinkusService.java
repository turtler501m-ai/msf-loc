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
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.RcpMgmtLinkusMapper;
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.RcpMgmtMapper;
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
public class RcpMgmtLinkusService extends BaseService {
	
	@Autowired
	private RcpMgmtMapper rcpMgmtMapper;
	
	@Autowired
	private RcpMgmtLinkusMapper rcpMgmtLinkusMapper;
	
//	@Autowired
//	private MaskingService maskingService;
//
//	@Autowired
//	private MaskingMapper maskingMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private MaskingService maskingService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	
	public void getRcpMgmtListLinkusExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		//엑셀 첫줄
		String [] strHead = {"구분", "예약번호", "확정시간", "고객명", "메모", "생년월일", "성별", "구매유형", "제품명", "제품ID", 
							 "제품코드","제품색상", "직영상품명", "휴대폰번호", "대리점", "요금제", "업무구분", "고객주소", "배송우편번호", "배송주소", 
							 "배송휴대폰번호","유심종류","유심일련번호" ,"배송요청사항", "유선연락처", "신청일자", "진행상태", "모집경로", "유입경로", "수정자", "수정일자"};	// 31

		String [] strValue = {"serviceName","resNo","setDttm","cstmrName2","requestMemo","birthDt","gender","reqBuyNm","modelName","prdtId", 
							  "prdtCode","reqModelColor","drctMngmPrdcNm","cstmrMobile","agentName","socName","operName","cstmrAddr","dlvryPost","fullDlvryAddr",
							  "dlvryMobile","usimKindsNm","reqUsimSn","dlvryMemo","cstmrTel","reqInDt","requestStateName","onOffNm","openMarketReferer","rvisnNm","rvisnDttm"};	//31

		//엑셀 컬럼 사이즈
		int[] intWidth = {10,  15,  15,  35, 25, 15,  15,  15,  18, 18,  
				  18,  18,  15,  15,  20, 25, 15,  45, 15, 45, 
				  20, 20, 25, 40, 20, 15,  15,  15,  15, 15, 20};	//31	

		int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0 ,0 ,0, 0, 0, 0, 0, 0, 0};		
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			RcpListVO searchVO = (RcpListVO) JacksonUtil.makeVOFromJson(execParam, RcpListVO.class);//변수명다른거(strtdt등.)  배치쪽 vo에 추가하던지 M전산쪽 넘기는 파라미터를 바꾸던지 strtdt추가하던지
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "신청정보링커스_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "신청정보링커스";
			
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
			if("2".equals(searchVO.getSearchCd())){
				String pContractNum = rcpMgmtMapper.getpContractNum(searchVO.getSearchVal());	
				LOGGER.info("--- getpContractNum pContractNum CHK ---" + pContractNum);
				if(pContractNum != null && !"".equals(pContractNum)){
					searchVO.setpSearchName(pContractNum);
				}
			}
			
			// db
			rcpMgmtLinkusMapper.getRcpMgmtListLinkusExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
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
			throw new MvnoServiceException("신청정보_링커스 엑셀저장 에러", e);
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
	
}
