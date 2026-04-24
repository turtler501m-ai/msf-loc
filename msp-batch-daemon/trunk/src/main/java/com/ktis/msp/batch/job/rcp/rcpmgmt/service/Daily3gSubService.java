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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.Daily3gSubMapper;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.Daily3gSubVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.common.masking.MaskingService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class Daily3gSubService extends BaseService {

	@Autowired
	private Daily3gSubMapper daily3gSubMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;	
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private MaskingService maskingService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	/**
	 * 3G 유지 가입자 등록
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertDaily3gSub() throws MvnoServiceException {
		int cnt = 0;
		int insertCnt = 0;;
		int deleteCnt = 0;
		// 1.3G요금제
		cnt += daily3gSubMapper.insert3gSubContNum1st();
		LOGGER.info("#### 3G요금제 >> {}", cnt);
		
		// 2.3G단말망
		cnt += daily3gSubMapper.insert3gSubContNum2nd();
		LOGGER.info("#### 3G단말망 >> {}", cnt);
		
		// 3.3G유심망
		cnt += daily3gSubMapper.insert3gSubContNum3rd();
		LOGGER.info("#### 3G유심망 >> {}", cnt);
		
		// 4.Volte 미지원 4G단말
		cnt += daily3gSubMapper.insert3gSubContNum4th();
		LOGGER.info("#### Volte 미지원 4G단말 >> {}", cnt);
		
		// 5.유심단독 개통후 단말정보 없는 고객
		cnt += daily3gSubMapper.insert3gSubContNum5th();
		LOGGER.info("#### 유심단독 개통후 단말정보 없는 고객 >> {}", cnt);
		
		LOGGER.info("#### 3G 유지 가입자 insert START");
		insertCnt = daily3gSubMapper.insertDaily3gSub();
		LOGGER.info("#### 3G 유지 가입자 insert END >> {}", insertCnt);

		LOGGER.info("#### 3G 유지 가입자 계약번호 delete START");
		deleteCnt = daily3gSubMapper.delete3gSubContNum();
		LOGGER.info("#### 3G 유지 가입자 계약번호 delete END >> {}", deleteCnt);

		return cnt;
	}
	

	/**
	 * 3G 유지 가입자 현황 엑셀다운로드
	 * @return
	 * @throws MvnoServiceException
	 */
	public void getDaily3gSubListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		String [] strHead = {"기준일자", "계약번호", "구매유형", "개통일자", "생년월일", "성별", "상태", "개통대리점코드", "개통대리점", "최종요금제코드" // 10
						, "최종요금제명", "최종요금제망", "최종단말모델코드", "최종단말명", "최종단말망", "단말VOLTE지원여부", "단말NFC지원여부", "최종유심모델", "최종유심명", "최종유심망" // 10
						, "유심NFC지원여부", "할인유형", "약정시작일자", "약정종료일자", "정지일자", "정지사유", "명세서유형" // 7
						}; 
		String [] strValue = {"stdrDt", "contractNum", "reqBuyType", "lstComActvDate", "birthDt", "gender", "subStatus", "openAgntCd", "openAgntNm", "lstRateCd" //10
				          , "lstRateNm", "lstRateTp", "lstModelId", "lstModelNm", "lstModelTp", "modelVolteYn", "modelNfcYn", "lstUsimId", "lstUsimNm", "lstUsimTp" // 10
				          , "usimNfcYn", "blDcType", "argmStrtDt", "argmEndDt", "stopDt", "stopRsnNm", "billSendNm" // 7
						};
		int[] intWidth = {15, 15, 15, 15, 15, 15, 15, 20, 30, 20
						, 40, 15, 20, 20, 15, 20, 20, 15, 20, 15
						, 20, 15, 15, 15, 15, 30, 20
						};
		int[] intLen = {  0, 0, 0, 0, 0, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0, 0, 0
						};
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			
			String execParam = batchCommonVO.getExecParam();
			Daily3gSubVO searchVO = (Daily3gSubVO) JacksonUtil.makeVOFromJson(execParam, Daily3gSubVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "3G유지가입자현황_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "3G유지가입자현황";
			
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
			daily3gSubMapper.getDaily3gSubListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
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
			throw new MvnoServiceException("3G유지가입자현황 엑셀저장 에러", e);
		}
	}
}
