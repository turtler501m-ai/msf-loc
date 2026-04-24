package com.ktis.msp.batch.job.rsk.unpaybondmgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rsk.unpaybondmgmt.mapper.UnpayBondMgmtMapper;
import com.ktis.msp.batch.job.rsk.unpaybondmgmt.vo.UnpayBondMgmtVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.common.masking.MaskingService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class UnpayBondMgmtService extends BaseService {
	@Autowired
	private UnpayBondMgmtMapper unpayBondMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	public void getUnpayBondDtlListEx(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String execParam = batchCommonVO.getExecParam();
		
		UnpayBondMgmtVO unpayBondMgmtVO = (UnpayBondMgmtVO) JacksonUtil.makeVOFromJson(execParam, UnpayBondMgmtVO.class);
		
		if(KtisUtil.isEmpty(unpayBondMgmtVO.getStdrYm())) {
			throw new MvnoServiceException("ERSK1008");
		} else if (KtisUtil.isEmpty(unpayBondMgmtVO.getStrtYm())) {
			throw new MvnoServiceException("ERSK1009");
		} else if (KtisUtil.isEmpty(unpayBondMgmtVO.getEndYm())) {
			throw new MvnoServiceException("ERSK1009");
		}
		
		LOGGER.debug("시작 = {}", KtisUtil.toStr(new Date(), "yyyyMMddHHmmss"));
		
		String[] strHead = {"기준월","사용월","청구월","서비스계약번호","계약번호","계약상태","개통대리점","청구항목",	//7
				"청구금액","청구금액부가세","할인금액","할인부가세","클레임금액","클레임부가세","위약금(공시지원금1)","위약금(공시지원금1)부가세",	//8
				"위약금(공시지원금2)","위약금(공시지원금2)부가세","위약금(추가지원금1)","위약금(추가지원금1)부가세","위약금(추가지원금2)","위약금(추가지원금2)부가세",	//6
				"절사금액","절사부가세","기타금액","기타 부가세","조정금액","조정금액부가세","조정금액합계",	//7
				"총청구금액","수납금액","수납금액부가세","수납금액합계","미납금액","미납금액부가세","미납금액합계",	//7
				"납기일자","수납일자","미납개월수","납부방법"};	//4
		
		String[] strValue = {"stdrYmEx","usgYmEx","billYmEx","svcCntrNo","contractNum","statusNm","openAgntNm","saleItmNm","invAmnt","invVat"
		         ,"adjAAmnt","adjAVat","adjBAmnt","adjBVat","adjCAmnt","adjCVat","adjDAmnt","adjDVat","adjEAmnt","adjEVat","adjFAmnt","adjFVat","adjGAmnt"
		         ,"adjGVat","adjHAmnt","adjHVat","adjAmnt","adjVat","totAdjAmnt","totInvAmnt","pymAmnt","pymVat","totPymAmnt","unpdAmnt","unpdVat","totUnpdAmnt"
		         ,"duedatDateEx","blpymDateEx","unpdMnthCnt","pymnMthdNm"};
		
		
		int[] intWidth = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		int[] intLen = {0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0};
		
		try {
			LOGGER.debug("--- 청구수미납정보 엑셀저장시작 ---");
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "청구수미납상세_" + unpayBondMgmtVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "청구수미납상세";
			
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
			
			LOGGER.debug("XML 파일생성 시작===> sw.beginWorkSheet()");
			sw.beginWorkSheet();
			
			// 칼럼 사이즈 변경
			List<SfExcelAttribute> customCellSizeList = new ArrayList<SfExcelAttribute>();
			for (int i = 1; i < intWidth.length+1; i++) {
				customCellSizeList.add(new SfExcelAttribute(i,i,intWidth[i-1],1));
			}
			
			sw.customCell(customCellSizeList);
			sw.beginSheetBond();
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", unpayBondMgmtVO.getUserId());

			unpayBondMgmtMapper.getUnpayBondDtlListEx(unpayBondMgmtVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));	

			LOGGER.debug("XML 파일생성 중간 확인 111111111111111111111");
			
			sw.endSheetBond();
			
			LOGGER.debug("XML 파일생성 종료===> sw.endWorkSheet()");
			sw.endWorkSheet();
			
			fw.close();
			
			//Step 3. Substitute the template entry with the generated data
			FileOutputStream out = new FileOutputStream(sbFileName.toString());
			File templateFile = new File(teplateFileName.toString());
			fileDownService.substitute(templateFile, tmp, sheetRef.substring(1), out);
			
			File file = new File(sbFileName.toString());
			Map<String, Object> pRequestParamMap = new HashMap<String, Object>();
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(unpayBondMgmtVO.getDwnldRsn())){
			
				pRequestParamMap.put("FILE_NM"   , file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" , file.getPath());				//파일경로
				pRequestParamMap.put("DUTY_NM"   , "MSP");						//업무명
				pRequestParamMap.put("IP_INFO"   , unpayBondMgmtVO.getIpAddr());		//IP정보
				pRequestParamMap.put("FILE_SIZE" , (int) file.length());		//파일크기
				pRequestParamMap.put("menuId"	 , unpayBondMgmtVO.getMenuId());		//메뉴ID
				pRequestParamMap.put("DATA_CNT"  , 0);							//자료건수
				pRequestParamMap.put("SESSION_USER_ID", unpayBondMgmtVO.getUserId());	//사용자ID
				pRequestParamMap.put("DWNLD_RSN", unpayBondMgmtVO.getDwnldRsn());		//다운로드사유
				pRequestParamMap.put("EXCL_DNLD_ID", Integer.parseInt(unpayBondMgmtVO.getExclDnldId()));
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			out.close();
			
			// 임시로 만든 template 파일을 삭제한다
			templateFile.delete();
//			file.delete();
			tmp.delete();
			
			// 엑셀파일 저장한 내역 DB에 남기기
			fileDownService.saveExcelHistory(sbFileName.toString(), unpayBondMgmtVO.getUserId());
			
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("EBND1010", e);
		}
		
		LOGGER.debug("종료 = {}", KtisUtil.toStr(new Date(), "yyyyMMddHHmmss"));
	}
	
	public List<?> setMaskingData(List<?> list, Map<String, Object> param){
		
		maskingService.setMask(list, maskingService.getMaskFields(), param);
		
		return list;
	}
	
}
