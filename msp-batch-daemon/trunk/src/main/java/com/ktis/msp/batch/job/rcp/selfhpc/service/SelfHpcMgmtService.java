package com.ktis.msp.batch.job.rcp.selfhpc.service;

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
import com.ktis.msp.batch.job.rcp.selfhpc.mapper.SelfHpcMgmtMapper;
import com.ktis.msp.batch.job.rcp.selfhpc.vo.SelfHpcVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.common.masking.MaskingService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class SelfHpcMgmtService extends BaseService {
	
	@Autowired
	private SelfHpcMgmtMapper selfHpcMgmtMapper;
	
	@Autowired
	private FileDownService fileDownService;

	@Autowired
	private MaskingService maskingService;	

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	
	public void getRcpSelfHpcListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {

		// 엑셀 첫줄
		String [] strHead = {"계약번호", "개통일자", "고객명", "고객전화번호", "대리점명",
							 "NICE인증 휴대폰번호", "NICE인증 통신사", "NICE 본인인증 일시", "나이", "개통요금제",
							 "성별", "주소지", "유심접점", "최초유심접점", "해피콜진행상태",
							 "1차해피콜결과", "1차해피콜비고", "1차해피콜처리자", "1차해피콜처리시간", "2차해피콜결과",
							 "2차해피콜비고", "2차해피콜처리자", "2차해피콜처리시간", "3차해피콜결과", "3차해피콜비고",
							 "3차해피콜처리자", "3차해피콜처리시간", "해피콜결과", "A'Cen 요청상태", "A'Cen 결과"}; // 30

		String [] strValue = {"contractNum", "lstComActvDate", "subLinkName", "subscriberNo", "openAgntNm",
							  "mobileNo","mobileCo", "authTime", "age", "fstRateNm",
							  "gender", "addr", "usimOrgNm", "fstUsimOrgNm", "hpcStatNm",
							  "fstHpcRstNm", "fstHpcRmk", "fstHpcUsrNm", "fstHpcDttm", "sndHpcRstNm",
							  "sndHpcRmk", "sndHpcUsrNm", "sndHpcDttm", "thdHpcRstNm", "thdHpcRmk",
							  "thdHpcUsrNm", "thdHpcDttm", "hpcRstNm", "acenReqStatusNm", "acenRstNm"}; //30

		int[] intWidth = {15, 15, 15, 20, 25
						, 25, 25, 25, 15, 25
						, 15, 50, 30, 30, 20
						, 20, 50, 20, 20, 20
						, 50, 20, 20, 20, 50
						, 20, 20, 20, 20, 20};

		int[] intLen = {0, 0, 0, 0, 0,
						0, 0, 0, 0, 0,
						0, 0, 0, 0, 0,
						0, 0, 0, 0, 0,
						0, 0, 0, 0, 0,
						0, 0, 0, 0, 0};
		

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			SelfHpcVO searchVO = (SelfHpcVO) JacksonUtil.makeVOFromJson(execParam, SelfHpcVO.class);
			//변수명다른거(strtdt등.)  배치쪽 vo에 추가하던지 M전산쪽 넘기는 파라미터를 바꾸던지 strtdt추가하던지
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "010셀프개통대상_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "010셀프개통대상";
			
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
			selfHpcMgmtMapper.getRcpSelfHpcListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
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
			throw new MvnoServiceException("010셀프개통대상 엑셀저장 에러", e);
		}
		
		
		
		
	}
	
}
