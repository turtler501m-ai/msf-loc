package com.ktis.msp.batch.job.rcp.custmgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.ktis.msp.batch.job.rcp.custmgmt.mapper.CustGiftMngMapper;
import com.ktis.msp.batch.job.rcp.custmgmt.vo.CustGiftVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.common.masking.MaskingService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class CustGiftMngService extends BaseService {

	@Autowired
	private CustGiftMngMapper custGiftMngMapper;
	
	@Autowired
	private FileDownService fileDownService;

	@Autowired
	private MaskingService maskingService;	

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	private static final String XML_ENCODING = "UTF-8";
	

	public void getCustGiftMngExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		// 엑셀 첫줄
		String [] strHead = {"프로모션ID","프로모션명","계약번호","프로모션유형", "노출여부" ,"정산기준일","주관부서"
							,"고객명","CTN","받는분고객명","받는분 전화번호1","받는분 전화번호2"
							,"우편번호","주소","상세주소","사은품명","수량"
							,"합계금액","제한수량","제한금액","신청일","개통일"
							,"사은품신청일" ,"구매유형","최초개통단말","직영상품명","고객최초요금제명"
							,"현재요금제명" ,"대리점","최초유심접점","현요금제변경일시","현상태"};	//32
		String [] strValue = {"prmtId","prmtNm","contractNum","prmtTypeNm","showYnNm","billDt","orgnNm"
							,"subLinkName", "subscriberNo","maskMngNm","tel1","tel2"
							,"post","addr","maskAddrDtl","prdtNm","quantity"
							,"sumAmount","choiceLimit","amountLimit","reqInDay","lstComActvDate"
							,"giftRegstDate" ,"reqBuyTypeNm","fstModelNm","drctMngmPrdcNm","fstRateNm"
							,"lstRateNm" ,"openAgntNm","fstUsimOrgNm","lstRateDt","subStatusNm"};//32
		// 엑셀 컬럼  사이즈
		int[] intWidth = {19, 35, 13, 17, 13, 13, 13
						, 13, 13, 14, 19, 19
						, 19, 30, 30, 40, 13
						, 20, 12, 14, 21, 15
						, 15, 15, 25, 25, 25
						, 25, 19, 19, 19, 12}; //32 
		int[] intLen = {0, 0, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 1
						, 1, 0, 1, 0, 0
						, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0}; //32
		
		FileOutputStream os = null;
		Writer fw = null;
		FileOutputStream out = null;
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			CustGiftVO searchVO = (CustGiftVO) JacksonUtil.makeVOFromJson(execParam, CustGiftVO.class);
			//변수명다른거(strtdt등.)  배치쪽 vo에 추가하던지 M전산쪽 넘기는 파라미터를 바꾸던지 strtdt추가하던지
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "고객사은품관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "고객사은품관리";
			
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
			os = new FileOutputStream(teplateFileName.toString());
			wb.write(os);
			os.close();
			
			//Step 2. Generate XML file.
			File tempDir = new File(serverInfo);
			File tmp = File.createTempFile("sheet", ".xml", tempDir);
			fw = new OutputStreamWriter(new FileOutputStream(tmp), XML_ENCODING);
			
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
			custGiftMngMapper.getCustGiftMngExcelList(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
			LOGGER.debug("sw.endSheet()");
			sw.endSheetBond();
			
			sw.endWorkSheet();
			
			fw.close();
			
			//Step 3. Substitute the template entry with the generated data
			out = new FileOutputStream(sbFileName.toString());
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
			throw new MvnoServiceException("사은품관리_고객사은품관리 엑셀저장 에러", e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (fw != null) {
					fw.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				throw new MvnoServiceException("사은품관리_고객사은품제품 엑셀저장 IOException", e);
			}
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
}
