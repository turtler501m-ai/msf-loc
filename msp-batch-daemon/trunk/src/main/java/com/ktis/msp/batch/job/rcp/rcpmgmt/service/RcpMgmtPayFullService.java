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
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.RcpMgmtPayFullMapper;
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
public class RcpMgmtPayFullService extends BaseService {
	
	@Autowired
	private RcpMgmtPayFullMapper rcpMgmtPayFullMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	public void selectRcpPayFullListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		String [] strHead = {
				"주문번호","예약번호","고객명","메모","업무구분","단말모델명","단말일련번호","단말색상","가입요금제코드","가입요금제"
				 ,"약정개월수","할부개월수","출고가","공시지원금","추가지원금","할부원금","유심종류","유심모델","유심일련번호","유심접점"
				 ,"배송우편번호","배송주소","전화번호","휴대폰번호","배송요청사항","진행상태","신청상태","택배사","송장번호","결제상태"
				 ,"결제취소실패사유","결제취소자","결제취소일","수정자","수정일자","신청일자"
		};	// 14
		String [] strValue = {
				"requestKey","resNo","cstmrName","requestMemo","operName","prdtCode","reqPhoneSn","reqModelColor","socCode","socName"
				,"agrmTrm","instNom","hndstAmt","modelDiscount2","modelDiscount3","modelInstallment","usimKindsNm","usimName","reqUsimSn","usimOrgNm"
				,"dlvryPost","fullDlvryAddr","dlvryTel","dlvryMobile","dlvryMemo","requestStateName","pstate","tbCd","dlvryNo","payRstNm"
				,"cnclFailRsn","cnclNm","cnclDt","amdNm","amdDt","reqInDt"
		};	//14
		//엑셀 컬럼 사이즈\
		int[] intWidth = {
				15, 15, 10, 50, 10, 20, 18, 5, 25, 25
				,12, 12, 16, 16, 16, 16, 15, 15, 15, 15
				,15, 50, 15, 15, 50, 15, 15, 15, 15, 15
				,50, 15, 15, 15, 20, 20
				};
		int[] intLen = {
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0
				,0, 0, 0, 0, 0, 0, 0, 0, 0, 0
				,0, 0, 0, 0, 0, 0, 0, 0, 0, 0
				,0, 0, 0, 0, 0, 0
				};
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			String execParam = batchCommonVO.getExecParam();
			RcpListVO searchVO = (RcpListVO) JacksonUtil.makeVOFromJson(execParam, RcpListVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "신청관리(일시납)_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "신청관리(일시납)";
			
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
				String pContractNum = rcpMgmtPayFullMapper.getpContractNum(searchVO.getSearchVal());	

				if(pContractNum != null && !"".equals(pContractNum)){
					searchVO.setSearchVal(pContractNum);
				}
			}
			
			// db
			rcpMgmtPayFullMapper.selectRcpPayFullListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
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
			throw new MvnoServiceException("신청관리(일시납) 엑셀저장 에러", e);
		}
	}
	
}
