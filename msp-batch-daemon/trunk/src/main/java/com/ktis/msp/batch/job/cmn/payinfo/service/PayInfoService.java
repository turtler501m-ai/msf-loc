package com.ktis.msp.batch.job.cmn.payinfo.service;

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
import com.ktis.msp.batch.job.cmn.payinfo.mapper.PayInfoMapper;
import com.ktis.msp.batch.job.cmn.payinfo.vo.PayInfoDtlVO;
import com.ktis.msp.batch.job.cmn.payinfo.vo.PayInfoVO;
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
public class PayInfoService extends BaseService {

//	@Autowired
//	private RcpEncService encSvc;
	
	@Autowired
	private PayInfoMapper payInfo;
		
	@Autowired
	private AuthChkService authChkService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private MaskingService maskingService;
	
	private static final String XML_ENCODING = "UTF-8";
	/**
	 * @Class Name : PayInfoService
	 * @Description : PayInfo service
	 * @
	 * @ 생성일             생성자   생성내용
	 * @ ------------- -------- -----------------------------
	 * @ 2018.03.08   권오승   변경동의서관리 대용량 데이터 엑셀저장
	 * @
	 * @author : 권오승
	 * @Create Date :  2018.03.08
	 */
	
	/**
	 * @Description : 변경동의서관리 자료생성(대용량데이터 엑셀다운로드)
	 * @Param  : BatchCommonVO
	 * @Author : 권오승
	 * @Create Date : 2018. 03. 08.
	 */
	public void selectPayInfoListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		String [] strHead = {"등록구분","등록일자","계약번호","개통구분","상태","고객명","전화번호","생년월일","개통일자","납부방법", // 10
						"대리점명","주소","검증상태","검증일시","검증내용" , "파일등록여부" //6
						}; 
		String [] strValue = {"regynnm","regstdttm","contractnum","onofftypenm","substatusnm","custname","telno","userssn","lstcomactvdate","blbillingmethodnm",
						"openagntnm","addr","approvalcdnm","approvaldt","approvalmsg" , "regyn"
						};
		int[] intWidth = {25, 25, 30, 20, 20, 20, 30, 25, 25, 20,
						30, 40, 30, 25, 30 , 30
						};	
		int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0 , 0
						};
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			
			String execParam = batchCommonVO.getExecParam();
			PayInfoVO searchVO = (PayInfoVO) JacksonUtil.makeVOFromJson(execParam, PayInfoVO.class);
			
			if (!authChkService.chkUsrGrpAuth(searchVO.getUserId(), "A_SHOPNM_R")){
				List<String> lHead = new ArrayList<String>();
				List<String> lValue = new ArrayList<String>();
				List<Integer> lWidth = new ArrayList<Integer>();
				List<Integer> lLen = new ArrayList<Integer>();
				
				int num = -1;
				for(int i=0;i<strHead.length;i++) {
					if(strHead[i].equals("파일등록여부")) {
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
			String fileName = serverInfo + "변경동의서관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "변경동의서관리";
			
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
			payInfo.selectPayInfoListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
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
				
				pRequestParamMap.put("FILE_NM"         , logFile.getName());              		//파일명
				pRequestParamMap.put("FILE_ROUT"       , logFile.getPath());              		//파일경로
				pRequestParamMap.put("DUTY_NM"         , "RCP");                          			//업무명
				pRequestParamMap.put("IP_INFO"         , searchVO.getIpAddr());           	//IP정보
				pRequestParamMap.put("FILE_SIZE"       , (int) logFile.length());         		//파일크기
				pRequestParamMap.put("menuId"          , searchVO.getMenuId());          	//메뉴ID
				pRequestParamMap.put("DATA_CNT"        , 0);                              			//자료건수
				pRequestParamMap.put("SESSION_USER_ID" , searchVO.getUserId()); 	    //사용자ID
				pRequestParamMap.put("EXCL_DNLD_ID"    , searchVO.getExclDnldId());	//엑셀다운로드 ID
				pRequestParamMap.put("DWNLD_RSN"    , searchVO.getDwnldRsn());		//엑셀다운로드사유
				
				//엑셀다운로드로그 insert
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			
			// 엑셀파일 저장한 내역 DB에 남기기
			fileDownService.saveExcelHistory(sbFileName.toString(), searchVO.getUserId());
		} catch(Exception e){
			LOGGER.error(StringUtil.getPrintStackTrace(e));
			throw new MvnoServiceException("변경동의서관리관리 엑셀저장 에러", e);
		}
	}
	
	/**
	 * @Description : KT연동동의서관리 엑셀다운로드(대용량처리)
	 * @Param  : BatchCommonVO
	 * @Author : 권오승
	 * @Create Date : 2018. 03. 08.
	 */
	public void selectPayInfoKtListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String [] strHead = {"등록구분","등록일자","계약번호","개통구분","상태","고객명","전화번호","생년월일","개통일자","납부방법", // 10
						"대리점명","은행명","계좌번호","주소","파일명" , "시스템오류코드", "검증상태", "검증일시", "검증내용", "파일유형명" //10
						}; 
		String [] strValue = {"fileregynnm","rgstdt","contractnum","onofftypenm","substatusnm","sublinkname","subscriberno","userssn","lstcomactvdate","blbillingmethodnm",
						"openagntnm","bankcdnm","bankbnkacnno","addr","realfilename" , "errorcdnm" , "approvalcdnm" , "approvaldt" , "approvalmsg" , "evicdnm"
						};
		int[] intWidth = {20, 20, 20, 20, 20, 20, 30, 20, 20, 20,
						30, 20, 25, 35, 25 , 25 , 20 , 20 , 30 , 10
						};	
		int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0 , 0 ,0, 0, 0 , 0
						};
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			
			String execParam = batchCommonVO.getExecParam();
			PayInfoDtlVO searchVO = (PayInfoDtlVO) JacksonUtil.makeVOFromJson(execParam, PayInfoDtlVO.class);
			
			if (!authChkService.chkUsrGrpAuth(searchVO.getUserId(), "A_SHOPNM_R")){
				List<String> lHead = new ArrayList<String>();
				List<String> lValue = new ArrayList<String>();
				List<Integer> lWidth = new ArrayList<Integer>();
				List<Integer> lLen = new ArrayList<Integer>();
				
				int num = -1;
				for(int i=0;i<strHead.length;i++) {
					if(strHead[i].equals("파일유형명")) {
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
			String fileName = serverInfo + "KT연동동의서관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "KT연동동의서관리";
			
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
			payInfo.selectPayInfoKtListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
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
				
				pRequestParamMap.put("FILE_NM"         , logFile.getName());              		//파일명
				pRequestParamMap.put("FILE_ROUT"       , logFile.getPath());              		//파일경로
				pRequestParamMap.put("DUTY_NM"         , "RCP");                          			//업무명
				pRequestParamMap.put("IP_INFO"         , searchVO.getIpAddr());           	//IP정보
				pRequestParamMap.put("FILE_SIZE"       , (int) logFile.length());         		//파일크기
				pRequestParamMap.put("menuId"          , searchVO.getMenuId());          	//메뉴ID
				pRequestParamMap.put("DATA_CNT"        , 0);                              			//자료건수
				pRequestParamMap.put("SESSION_USER_ID" , searchVO.getUserId()); 	    //사용자ID
				pRequestParamMap.put("EXCL_DNLD_ID"    , searchVO.getExclDnldId());	//엑셀다운로드 ID
				pRequestParamMap.put("DWNLD_RSN"    , searchVO.getDwnldRsn());		//엑셀다운로드사유
				
				//엑셀다운로드로그 insert
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			
			// 엑셀파일 저장한 내역 DB에 남기기
			fileDownService.saveExcelHistory(sbFileName.toString(), searchVO.getUserId());
		} catch(Exception e){
			LOGGER.error(StringUtil.getPrintStackTrace(e));
			throw new MvnoServiceException("KT연동동의서관리 엑셀저장 에러", e);
		}
	}
}
