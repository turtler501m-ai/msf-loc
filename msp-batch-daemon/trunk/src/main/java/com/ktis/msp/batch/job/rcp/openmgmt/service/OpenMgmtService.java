package com.ktis.msp.batch.job.rcp.openmgmt.service;

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
import com.ktis.msp.batch.job.rcp.openmgmt.mapper.OpenMgmtMapper;
import com.ktis.msp.batch.job.rcp.openmgmt.vo.OpenMgmtListVO;
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
public class OpenMgmtService extends BaseService {

//	@Autowired
//	private RcpEncService encSvc;
	
	@Autowired
	private OpenMgmtMapper openMgmtMapper;
		
	@Autowired
	private AuthChkService authChkService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private MaskingService maskingService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	public void selectOpenMgmtListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		String [] strHead = {
						"상품구분",					"계약번호",					"예약번호",						"고객명",						"생년월일",
						"성별",						"나이(만)",					"구매유형",						"업무구분",						"이동전 통신사",
						"휴대폰번호",				"최초요금제코드",			"최초요금제",					"현재요금제코드",				"현재요금제",
						"판매정책",					"약정개월수",				"할부개월수",					"단말모델명",					"단말일련번호",
						"최초개통단말",				"최초단말일련번호",			"상태",							"정지사유",						"해지사유",			
						"모집경로",					"유입경로",					"추천인구분",					"추천인정보",					"녹취여부",			
						"대리점",					"판매점",					"신청일자",						"개통일자",						"해지일자",			
						"정지일자",					"법정대리인",				"관계",							"대리인연락처",					"유해차단APP명",	
						"가입비납부방법",			"가입비",					"USIM납부방법",					"USIM비",						"대리점ID",			
						"할부원금",					"할인유형",					"보증보험관리번호",				"보증보험가입상태",				"심플할인ID",		
						"심플약정기간",				"심플약정시작일자",			"심플약정종료일자",				"심플약정상태",					"렌탈기본료",		
						"렌탈료할인",				"렌탈단말배상금",			"기변횟수",						"기변유형",						"기변일자",			
						"기변모델ID",				"기변모델명",				"기변단말일련번호",				"기변대리점",					"기변단말출고가",	
						"기변단말할부원금",			"기변단말할부개월",			"기변약정개월",					"기변판매정책",					"OTA등록일자",		
						"캐치콜가입일자",			"캐치콜해지일자",			"프로모션명",					"프로모션시작일",				"프로모션종료일",	
						"가입비면제",				"유심비면제",				"추가데이터제공",				"추가기본료할인",				"실판매점",			
						"생활안심보험가입여부",		"생활안심보험명",			"선택형단체보험동의여부",		"선택형단체보험가입여부",		"선택형단체보험명",	
						"KT해지사유",				"광고/정보전송동의",		"해지후이동사업자정보",			"휴대폰안심보험",				"유심접점",			
						"최초유심접점",				"주소",						"광고사",						"국가",							"VISA 코드",		
						"최초유심일련번호",			"현재유심일련번호",  		"본인인증방식",					"메모사항",						"개통시간",        
						"이동전 통신사(구)",		"eSIM여부",					"최초eSIM여부",					"현재USIM모델명", 				"현재USIM NFC기능", 
						"최초USIM모델명",			"최초USIM NFC기능",			"평생할인 프로모션명",			"제휴처",						"서비스 계약번호",
						"단말 IMEI",				"접점 비고",				"KT해지사유 유형",							"바로배송유심여부"
						//"보험가입여부" //(20181012 -> 보험가입여부 추가)
						}; 
		String [] strValue = {
						"prodtypenm",				"contractnum",				"resno",						"cstmrname",					"birth",
						"gender",					"age",						"reqbuytypenm",					"opertypenm",					"portincpy",
						"subscriberno",				"fstratecd",				"fstratenm",					"lstratecd", 					"lstratenm",
						"saleplcynm",				"enggmnthcnt",				"instmnthcnt",					"lstmodelnm",					"lstmodelsrlnum",
						"fstmodelnm",				"fstmodelsrlnum",			"substatusnm",					"stoprsn",						"canrsn",
						"onofftypenm",				"openmarketreferer",		"recommendflagnm",				"recommendinfo",				"recyn",
						"agentnm",					"shopnm",					"reqinday",						"opendt",						"tmntdt",
						"stopdt",					"minoragentname",			"minoragentrelation",			"minoragenttel",				"appnm",
						"joinpaymthdnm",			"joinprice",				"usimpaymthdnm",				"usimprice",					"openagntcd",
						"instorginamnt",			"sprttpnm",					"grntinsrmngmno",				"grntinsrstatnm",				"simid",
						"simenggmnthcnt",			"simstartdt",				"simenddt",						"simstatnm",					"rentalbaseamt",
						"rentalbasedcamt",			"rentalmodelcpamt",			"dvcchgcnt",					"dvcopertypenm",				"dvcchgdt",
						"dvcmodelid",				"dvcmodelnm",				"dvcintmsrlno",					"dvcagntnm",					"dvchndstamnt",
						"dvcinstorginamnt",			"dvcinstmnthcnt",			"dvcenggmnthcnt",				"dvcsaleplcynm",				"otadate",
						"catchcall",				"catchcallcan",				"promotionnm",					"prmtstrtdt",					"prmtenddt",
						"benefit03",				"benefit04",				"benefit01",					"benefit02",					"realshopnm",
						"insryn1",					"insrnm1",					"clauseinsuranceflag",			"insryn2",						"insrnm2",
						"substatusrsnnm",			"mrktagryn",				"cmpnnm",						"insrprodnm",					"usimorgnm",
						"fstusimorgnm",				"banadrprimaryln",			"ktmreferer",					"cstmrforeignernation",			"visacdnm",
						"fstusimnum",				"lstusimnum",				"authtype", 					"memo",							"opentime",
						"movecompanynm",			"esimyn",					"fstesimyn", 					"lstusimmodnm",					"lstnfcyn",
						"fstusimmodnm" ,			"fstnfcyn", 		   		"disprmtnm",					"jehuprodtype",					"svccntrno",
						"imei",						"rfrnsbst",					"cantype",						"dduyn"
						//"insryn" // (20181012 -> 보험가입여부 추가)
						};
		int[] intWidth = {
						15, 15, 15, 10, 10, 10, 10, 10, 10, 15,
						12, 25, 25, 25, 25, 12, 12, 15, 20, 18,
						20, 20, 10, 15, 15, 15,	15, 12, 15, 12, 
						20, 20, 12, 12, 12, 12,	12, 10, 16, 16, 
						16, 12, 16, 12, 12, 12,	15, 18, 18, 15, 
						15, 20, 20, 16, 12, 12,	16, 12, 12, 12, 
						16, 16, 18, 20, 16, 18,	18, 16, 16, 16, 
						16, 16, 20, 16, 16, 12,	12, 16, 16, 16, 
						25, 20, 25, 25, 20, 20,	10, 25, 20, 20, 
						20, 50, 50, 20, 20, 20, 20, 20, 50, 20, 
						18, 10, 10, 10, 10, 10, 10, 50, 25, 17,
						17, 50, 17, 25
						};
		int[] intLen = {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 
						1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 
						0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 0
						};
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			
			String execParam = batchCommonVO.getExecParam();
			OpenMgmtListVO searchVO = (OpenMgmtListVO) JacksonUtil.makeVOFromJson(execParam, OpenMgmtListVO.class);
			
			if (!authChkService.chkUsrGrpAuth(searchVO.getUserId(), "A_SHOPNM_R")){
				List<String> lHead = new ArrayList<String>();
				List<String> lValue = new ArrayList<String>();
				List<Integer> lWidth = new ArrayList<Integer>();
				List<Integer> lLen = new ArrayList<Integer>();
				
				int num = -1;
				for(int i=0;i<strHead.length;i++) {
					if(strHead[i].equals("실판매점")) {
						num = i;
					}
					
					lHead.add(strHead[i]);
					lValue.add(strValue[i]);
					lWidth.add(intWidth[i]);
					lLen.add(intLen[i]);
				}
				
				if (num > -1) {
					lHead.remove(num);
					lValue.remove(num);
					lWidth.remove(num);
					lLen.remove(num);
					
					strHead = new String[lHead.size()];
					strValue = new String[lValue.size()];
					intWidth = new int[lWidth.size()];
					intLen = new int[lLen.size()];
					
					strHead = lHead.toArray(strHead);
					strValue = lValue.toArray(strValue);
					
					for(int i=0;i<lWidth.size();i++) {
						intWidth[i] = lWidth.get(i);
						intLen[i] = lLen.get(i);
					}
				}
			}
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "개통관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "개통관리";
			
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
			openMgmtMapper.selectOpenMgmtListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
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
