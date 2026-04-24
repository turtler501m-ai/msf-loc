package com.ktis.msp.batch.job.rcp.statsmgmt.service;

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
import com.ktis.msp.batch.job.rcp.statsmgmt.mapper.ModelOpenMapper;
import com.ktis.msp.batch.job.rcp.statsmgmt.vo.ModelOpenListVO;
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
public class ModelOpenService extends BaseService {

//	@Autowired
//	private RcpEncService encSvc;
	
	@Autowired
	private ModelOpenMapper modelOpenMapper;
		
	@Autowired
	private AuthChkService authChkService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private MaskingService maskingService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	public void selectModelOpenListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		String [] strHead = {"상품구분","계약번호","예약번호","고객명","생년월일","성별","나이(만)","구매유형","업무구분","이동전 통신사", // 10
						"휴대폰번호","최초요금제코드","최초요금제","현재요금제코드","현재요금제","판매정책","약정개월수","할부개월수","단말모델명","단말일련번호","최초개통단말","최초단말일련번호", // 10
						"상태","해지사유","모집경로","유입경로","추천인구분","추천인정보","녹취여부","대리점","판매점","신청일자", // 10
						"개통일자","해지일자","정지일자","법정대리인","관계","대리인연락처","유해차단APP명","가입비납부방법","가입비","USIM납부방법", // 10
						"USIM비","대리점ID","할부원금","할인유형","보증보험관리번호","보증보험가입상태","심플할인ID","심플약정기간","심플약정시작일자","심플약정종료일자", // 10
						"심플약정상태","렌탈기본료","렌탈료할인","렌탈단말배상금","기변횟수","기변유형","기변일자","기변모델ID","기변모델명","기변단말일련번호",	// 10
						"기변대리점","기변단말출고가","기변단말할부원금","기변단말할부개월","기변약정개월","기변판매정책","OTA등록일자","캐치콜가입일자","캐치콜해지일자",	// 9
						"프로모션명","프로모션시작일","프로모션종료일","가입비면제","유심비면제","추가데이터제공","추가기본료할인","실판매점", //"보험가입여부" // 8 -> 9 (20181012 -> 보험가입여부 추가)
						"생활안심보험가입여부","생활안심보험명","선택형단체보험동의여부","선택형단체보험가입여부","선택형단체보험명","KT해지사유","광고/정보전송동의","해지후이동사업자정보","휴대폰안심보험","유심접점", // 10
						"주소","광고사","최종요금제변경일","이전모델명"
						}; 
		String [] strValue = {"prodtypenm","contractnum","resno","cstmrname","birth","gender","age","reqbuytypenm","opertypenm","movecompanynm",
						"subscriberno","fstratecd","fstratenm","lstratecd", "lstratenm","saleplcynm","enggmnthcnt","instmnthcnt","lstmodelnm","lstmodelsrlnum","fstmodelnm","fstmodelsrlnum",
						"substatusnm","canrsn","onofftypenm","openmarketreferer","recommendflagnm","recommendinfo","recyn","agentnm","shopnm","reqinday",
						"opendt","tmntdt","stopdt","minoragentname","minoragentrelation","minoragenttel","appnm","joinpaymthdnm","joinprice","usimpaymthdnm",
						"usimprice","openagntcd","instorginamnt","sprttpnm","grntinsrmngmno","grntinsrstatnm","simid","simenggmnthcnt","simstartdt","simenddt",
						"simstatnm","rentalbaseamt","rentalbasedcamt","rentalmodelcpamt","dvcchgcnt","dvcopertypenm","dvcchgdt","dvcmodelid","dvcmodelnm","dvcintmsrlno",
						"dvcagntnm","dvchndstamnt","dvcinstorginamnt","dvcinstmnthcnt","dvcenggmnthcnt","dvcsaleplcynm","otadate","catchcall","catchcallcan",	// 9
						"promotionnm","prmtstrtdt","prmtenddt","benefit03","benefit04","benefit01","benefit02","realshopnm", //"insryn" //  8 -> 9 (20181012 -> 보험가입여부 추가)
						"insryn1","insrnm1","clauseinsuranceflag","insryn2","insrnm2", "substatusrsnnm","mrktagryn","cmpnnm","insrprodnm","usimorgnm", // 10
						"banadrprimaryln","ktmreferer","lstChgRateDt","beforeModel"
						};
		int[] intWidth = {15, 15, 15, 10, 10, 10, 10, 10, 10, 15,
						12, 25, 25, 25, 25, 12, 12, 15, 20, 18, 20, 20,
						10, 15, 15, 15, 12, 15, 12, 20, 20, 12,
						12, 12, 12, 12, 10, 16, 16, 16, 12, 16, 
						12, 12, 12, 15, 18, 18, 15, 15, 20, 20, 
						16, 12, 12, 16, 12, 12, 12, 16, 16, 18, 
						20, 16, 18, 18, 16, 16, 16, 16, 16, 20,
						16, 16, 12, 12, 16, 16, 16, 25, 20, 25,
						25, 20, 20, 10, 25, 20, 20, 50, 50, 25, 50
						};
		int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
						1, 0, 1, 0, 0, 0, 0, 1, 0, 0,
						0, 1, 1, 1, 1, 0, 0, 0, 0, 0,
						0, 1, 1, 1, 1, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
						};
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			
			String execParam = batchCommonVO.getExecParam();
			ModelOpenListVO searchVO = (ModelOpenListVO) JacksonUtil.makeVOFromJson(execParam, ModelOpenListVO.class);
			
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
			String fileName = serverInfo + "특정폰사용자현황_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "특정폰사용자현황";
			
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
			modelOpenMapper.selectModelOpenListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));
			
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
			throw new MvnoServiceException("특정폰사용자현황 엑셀저장 에러", e);
		}
	}
	
}
