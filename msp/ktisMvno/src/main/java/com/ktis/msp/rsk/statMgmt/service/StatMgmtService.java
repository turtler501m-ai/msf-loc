package com.ktis.msp.rsk.statMgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelDataListResultHandler3;
import com.ktis.msp.base.excel.ExcelWriter;
import com.ktis.msp.base.excel.SfExcelAttribute;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.masking.vo.MaskingVO;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.MsgQueueReqVO;
import com.ktis.msp.rsk.statMgmt.mapper.StatMgmtMapper;
import com.ktis.msp.rsk.statMgmt.vo.StatMgmt3gSubVO;
import com.ktis.msp.rsk.statMgmt.vo.StatMgmtUagAmntVO;
import com.ktis.msp.rsk.statMgmt.vo.StatMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class StatMgmtService extends BaseService {
	
	@Autowired
	private StatMgmtMapper statMgmtMapper;

	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private EgovPropertyService propertiesService;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	private static final String XML_ENCODING = "UTF-8";
	
	public List<?> getBillItemMgmtList(StatMgmtVO vo){
		if(vo.getStdrDt() == null || vo.getStdrDt().equals("")){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		return statMgmtMapper.getBillItemMgmtList(vo);
	}
	
	public List<?> getBillItemMgmtListExcel(StatMgmtVO vo){
		if(vo.getStdrDt() == null || vo.getStdrDt().equals("")){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		return statMgmtMapper.getBillItemMgmtListExcel(vo);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void insertBillItmInfo(StatMgmtVO vo){
		if(vo.getBillItemCd() == null || vo.getBillItemCd().equals("")){
			throw new MvnoRunException(-1, "청구항목코드를 입력하세요");
		}
		
		if(vo.getItemCdNm() == null || vo.getItemCdNm().equals("")){
			throw new MvnoRunException(-1, "청구항목명을 입력하세요");
		}
		
		if(vo.getApplStrtDt() == null || vo.getApplStrtDt().equals("")){
			throw new MvnoRunException(-1, "시작일자를 입력하세요");
		}
		
		if(vo.getApplEndDt() == null || vo.getApplEndDt().equals("")){
			throw new MvnoRunException(-1, "종료일자를 입력하세요");
		}
		
		if(!vo.getApplEndDt().equals("99991231")){
			throw new MvnoRunException(-1, "종료일자는 '9999-12-31'을 입력하세요");
		}
		
		// 청구항목유무
		int chk = statMgmtMapper.getBillItmChk(vo);
		logger.debug("chk=" + chk);
		if((Integer) statMgmtMapper.getBillItmChk(vo) > 0){
			throw new MvnoRunException(-1, "동일한 청구항목코드가 존재합니다");
		}
		
		StatMgmtVO oldVo = new StatMgmtVO();
		oldVo.setBillItemCd(vo.getBillItemCd());
		oldVo.setApplStrtDt(vo.getApplStrtDt());
		oldVo.setApplEndDt(vo.getApplEndDt());
		
		statMgmtMapper.updateBillItmInfo(oldVo);
		
		statMgmtMapper.insertBillItmInfo(vo);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updateBillItmInfo(StatMgmtVO vo){
		if(vo.getBillItemCd() == null || vo.getBillItemCd().equals("")){
			throw new MvnoRunException(-1, "청구항목코드를 입력하세요");
		}
		
		if(vo.getApplEndDt() == null || vo.getApplEndDt().equals("")){
			throw new MvnoRunException(-1, "종료일자를 입력하세요");
		}
		
		vo.setApplEndDt("");
		
		statMgmtMapper.updateBillItmInfo(vo);
	}
	
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("custNm","CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("ssn","SSN");
		maskFields.put("reqPhoneSn","DEV_NO");
		maskFields.put("phoneSn","DEV_NO");
		
		return maskFields;
	}
	
	public List<?> getInvPymDataList(StatMgmtVO vo){
		// 필수값 체크
		if(vo.getTrgtYm() == null || vo.getTrgtYm().equals("")){
			throw new MvnoRunException(-1, "기준월이 존재하지 않습니다");
		}
		
		if(vo.getStrtYm() == null || vo.getStrtYm().equals("")){
			throw new MvnoRunException(-1, "시작월이 존재하지 않습니다");
		}
		
		if(vo.getEndYm() == null || vo.getEndYm().equals("")){
			throw new MvnoRunException(-1, "종료월이 존재하지 않습니다");
		}
		
		if(Integer.parseInt(vo.getStrtYm()) > Integer.parseInt(vo.getEndYm())){
			throw new MvnoRunException(-1, "시작월이 종료월보다 큽니다");
		}
		
		// 5년이상 자료조회 불가
		if(statMgmtMapper.getInvPymPrdCheck(vo) == 0){
			throw new MvnoRunException(-1, "종료월 기준 5년 이전 자료는 조회가 불가능합니다");
		}
		
		return statMgmtMapper.getInvPymDataList(vo);
	}
	
	/**
	 * 청구수납자료생성(프로시져 호출)
	 * @param vo
	 * @throws 
	 */
	@Async
	public void setInvPymDataList(StatMgmtVO vo) {
		// 필수값 체크
		if(vo.getTrgtYm() == null || vo.getTrgtYm().equals("")){
			throw new MvnoRunException(-1, "기준월이 존재하지 않습니다");
		}
		
		if(vo.getStrtYm() == null || vo.getStrtYm().equals("")){
			throw new MvnoRunException(-1, "시작월이 존재하지 않습니다");
		}
		
		if(vo.getEndYm() == null || vo.getEndYm().equals("")){
			throw new MvnoRunException(-1, "종료월이 존재하지 않습니다");
		}
		
		if(Integer.parseInt(vo.getStrtYm()) > Integer.parseInt(vo.getEndYm())){
			throw new MvnoRunException(-1, "시작월이 종료월보다 큽니다");
		}
		// 5년이상 자료조회 불가
		if(statMgmtMapper.getInvPymPrdCheck(vo) == 0){
			throw new MvnoRunException(-1, "종료월 기준 5년 이전 자료는 조회가 불가능합니다");
		}
				
		// 실행중인 자료요청건 존재여부 확인
		if(statMgmtMapper.getInvPymDataCheck(vo) > 0){
			throw new MvnoRunException(-1, "자료생성중입니다");
		}
		
		statMgmtMapper.callInvPymData(vo);
		
		// 파일생성
		while(true){
			if(statMgmtMapper.getInvPymDataCheck(vo) == 0){
				saveInvPymDataExcel(vo);
				break;
			}
			
			// 5분에 한번씩 실행
			sleep(60 * 5);
		}
	}
	
	public void saveInvPymDataExcel(StatMgmtVO vo){
		String serverInfo = propertiesService.getString("excelPath");
		String strFileName = serverInfo + "청구수납자료_";
		String strSheetName = "청구수납자료";
		
		String[] strHead = new String[132];
		strHead[0] = "계약번호";
		strHead[1] = "개통일자";
		strHead[2] = "계약상태";
		strHead[3] = "선후불";
		strHead[4] = "약정개월수";
		strHead[5] = "가입요금제";
		strHead[6] = "청구월";
		strHead[7] = "청구금액";
		strHead[8] = "수납금액";
		strHead[9] = "미납금액";
		strHead[10] = "미납율";
		strHead[11] = "미납여부";
		
		int yy = Integer.parseInt(vo.getStrtYm().substring(0, 4));
		int mm = Integer.parseInt(vo.getStrtYm().substring(4, 6));
		
		logger.debug("yy=" + yy);
		logger.debug("mm=" + mm);
		
		String vYY = "";
		String vMM = "";
		
		for(int i=0; i<120; i++){
			
			if(i % 2 == 0){
				if(i == 0){
					vYY = String.valueOf(yy);
					vMM = String.valueOf(mm);
				}else{
					mm++;
					
					if(mm > 12) {
						mm = 1;
						yy ++;
					}
				}
				
				vYY = String.valueOf(yy);
				vMM = mm < 10 ? "0" + mm : String.valueOf(mm);  
//				vMM = (String) (mm < 10 ? "0" + mm : mm);
				
				strHead[i+12] = vYY + "-" + vMM;
				
			}else{
				strHead[i+12] = "";
			}
			
			logger.debug("yymm=" + strHead[i+12]);
		}
		
		String[] strValue = new String[132];
		strValue[0] = "contractNum";
		strValue[1] = "openDt";
		strValue[2] = "subStatusNm";
		strValue[3] = "pppo";
		strValue[4] = "enggMnthCnt";
		strValue[5] = "rateNm";
		strValue[6] = "billYm";
		strValue[7] = "invAmnt";
		strValue[8] = "pymAmnt";
		strValue[9] = "unpdAmnt";
		strValue[10] = "unpdRatio";
		strValue[11] = "unpdYn";

		for(int i=1; i<=120; i++){
			if(i < 10) strValue[i+11] = "col00" + i;
			else if(i<100) strValue[i+11] = "col0" + i;
			else strValue[i+11] = "col" + i;
		}
		
		int[] intWidth = new int[132];
		for(int i=0; i<132; i++){
			intWidth[i] = 20;
		}
		
		int[] intLen = new int[132];
		for(int i=0; i<132; i++){
			if(i == 7 || i == 8 || i == 9 || i >= 12) intLen[i] = 1;
			else intLen[i] = 0;
		}
		
		StringBuilder sbFileName = new StringBuilder();
		StringBuilder teplateFileName = new StringBuilder();
		
		try{
			// Step1.
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(strSheetName);
			
			Map<String, XSSFCellStyle> styles = createStyles(wb);
			String sheetRef = sheet.getPackagePart().getPartName().getName();

			Date toDay = new Date();
			
//			String strFileName = fileName;
			String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
			sbFileName.append(strFileName);
			sbFileName.append(strToDay);
			sbFileName.append(".xlsx");

			//save the template
			// template 파일을 임시로 만들고 나서 다 끝나면 삭제하도록 한다.
			teplateFileName.append(strFileName);
			teplateFileName.append("template_");
			teplateFileName.append(strToDay);
			teplateFileName.append(".xlsx");
			
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(teplateFileName.toString());
				wb.write(os);
			} catch(Exception e) {
				throw new MvnoErrorException(e);
			} finally {
				if(os != null){
					os.close();						
				}		
			}
			
			// Step2.
			File tmp = File.createTempFile("sheet", ".xml");
			Writer fw = null;
			try {
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
				statMgmtMapper.getInvPymDataListExcel(vo, new ExcelDataListResultHandler3(sw, styles, strHead, strValue, intLen));
				sw.endSheetBond();
				
				// 셀 머지 (mergeCells의 parent node 는 worksheet 임
//				A = "계약번호";
//				B = "개통일자";
//				C = "계약상태";
//				D = "선후불";
//				E = "약정개월수";
//				F = "가입요금제";
//				G = "청구월";
//				H = "청구금액";
//				I = "수납금액";
//				J = "미납금액";
//				K = "미납율";
//				L = "미납여부";
				String[] mergeCell = {"A1:A2","B1:B2","C1:C2","D1:D2","E1:E2","F1:F2","G1:G2","K1:K2"
							, "M1:N1",   "O1:P1",   "Q1:R1", "S1:T1",   "U1:V1",   "W1:X1",   "Y1:Z1" // 7
							, "AA1:AB1", "AC1:AD1", "AE1:AF1", "AG1:AH1", "AI1:AJ1", "AK1:AL1", "AM1:AN1", "AO1:AP1", "AQ1:AR1", "AS1:AT1", "AU1:AV1", "AW1:AX1", "AY1:AZ1" // 13
							, "BA1:BB1", "BC1:BD1", "BE1:BF1", "BG1:BH1", "BI1:BJ1", "BK1:BL1", "BM1:BN1", "BO1:BP1", "BQ1:BR1", "BS1:BT1", "BU1:BV1", "BW1:BX1", "BY1:BZ1" // 13
							, "CA1:CB1", "CC1:CD1", "CE1:CF1", "CG1:CH1", "CI1:CJ1", "CK1:CL1", "CM1:CN1", "CO1:CP1", "CQ1:CR1", "CS1:CT1", "CU1:CV1", "CW1:CX1", "CY1:CZ1" // 13
							, "DA1:DB1", "DC1:DD1", "DE1:DF1", "DG1:DH1", "DI1:DJ1", "DK1:DL1", "DM1:DN1", "DO1:DP1", "DQ1:DR1", "DS1:DT1", "DU1:DV1", "DW1:DX1", "DY1:DZ1" // 13
							, "EA1:EB1" // 1
					};
				sw.mergeCell(mergeCell);
				sw.endWorkSheet();
				
			} catch(Exception e) {
				throw new MvnoErrorException(e);
			} finally {
				if(fw != null){
					fw.close();	
				}							
			}			
			

			
			//Step 3. Substitute the template entry with the generated data
			FileOutputStream out = null; 
			File file = null;
			try {
				out = new FileOutputStream(sbFileName.toString());
				file = new File(teplateFileName.toString());
				fileDownService.substitute(file, tmp, sheetRef.substring(1), out);
			} catch(Exception e) {
				throw new MvnoErrorException(e);
			} finally {
				if(out != null){
					out.close();
				}	

				// 임시로 만든 template 파일을 삭제한다
				file.delete();
			}

			
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		
		// 엑셀파일 저장한 내역 DB에 남기기
		fileDownService.saveExcelHistory(sbFileName.toString(), vo.getSessionUserId());
		
		// SMS발송
		MsgQueueReqVO msgVO = new MsgQueueReqVO();
		msgVO.setMsgType("1");
		msgVO.setDstaddr(statMgmtMapper.getMblphnNum(vo.getSessionUserId()));
		msgVO.setCallback("18995000");
		msgVO.setStat("0");
		msgVO.setText("청구수납자료생성이 완료되었습니다");
		msgVO.setExpiretime("7200");
		msgVO.setOptId("INVPYMDATA");
		smsMgmtMapper.insertMsgQueue(msgVO);
	}
	
	public Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb){
		
		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
		
		// Cell 스타일 생성 
		XSSFCellStyle cellStyleHead = wb.createCellStyle(); 
		XSSFCellStyle cellStyleMid = wb.createCellStyle(); 
		XSSFCellStyle cellStyleInt = wb.createCellStyle(); 
		XSSFCellStyle cellStyleData = wb.createCellStyle(); 

		// 줄 바꿈 
		cellStyleHead.setWrapText(true); 
		  
		XSSFFont fontHead = wb.createFont();
		fontHead.setBoldweight((short) 1);
		fontHead.setFontName("맑은 고딕");
		fontHead.setColor(HSSFColor.BLACK.index);
		fontHead.setFontHeightInPoints((short) 12);
		
		XSSFFont fontMid = wb.createFont();
		fontMid.setBoldweight((short) 1);
		fontMid.setFontName("맑은 고딕");
		fontMid.setColor(HSSFColor.BLACK.index);
		fontMid.setFontHeightInPoints((short) 10);
		
		//제목
		cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyleHead.setAlignment((short) 2);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setFillPattern((short) 1);
		cellStyleHead.setBorderRight((short) 1);
//		cellStyleHead.setBorderLeft((short) 1);
		cellStyleHead.setBorderTop((short) 1);
		cellStyleHead.setBorderBottom((short) 1);
		cellStyleHead.setFont(fontHead);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styles.put("header", cellStyleHead);
		
		//내용
		cellStyleMid.setAlignment((short) 2);
		cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleMid.setFillPattern((short) 1);
		cellStyleMid.setBorderRight((short) 1);
		cellStyleMid.setBorderLeft((short) 1);
		cellStyleMid.setBorderTop((short) 1);
		cellStyleMid.setBorderBottom((short) 1);
		cellStyleMid.setFont(fontMid);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styles.put("mid", cellStyleMid);
		
		//숫자용
		XSSFDataFormat format = wb.createDataFormat();
		
		cellStyleInt.setAlignment((short) 3);
		cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleInt.setFillPattern((short) 1);
		cellStyleInt.setBorderRight((short) 1);
		cellStyleInt.setBorderLeft((short) 1);
		cellStyleInt.setBorderTop((short) 1);
		cellStyleInt.setBorderBottom((short) 1);
		cellStyleInt.setFont(fontMid);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleInt.setDataFormat(format.getFormat("#,##0"));
		styles.put("number", cellStyleInt);
		
		//수식용
		cellStyleData.setWrapText(true); 
		cellStyleData.setAlignment((short) 3);
		cellStyleData.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		cellStyleData.setFillPattern((short) 1);
		cellStyleData.setBorderRight((short) 1);
		cellStyleData.setBorderLeft((short) 1);
		cellStyleData.setBorderTop((short) 1);
		cellStyleData.setBorderBottom((short) 1);
		cellStyleData.setFont(fontMid);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleData.setDataFormat((short) XSSFCell.CELL_TYPE_FORMULA);
		
		styles.put("data", cellStyleData);
		
		return styles;
	}
	
	public void sleep(int time){
		// time 은 초 단위임
		try {
			Thread.sleep(1000 * time);
		} catch (InterruptedException e) { 
			throw new MvnoErrorException(e);
		}
	}
	
	public List<StatMgmtUagAmntVO> getUsgAmntStatOrgList(StatMgmtVO vo, Map<String, Object> paramMap){
		
		if(vo.getUsgYm() == null || "".equals(vo.getUsgYm())){
			throw new MvnoRunException(-1, "사용월이 존재하지 않습니다");
		}

		if(vo.getOrgnId() == null || "".equals(vo.getOrgnId())){
			throw new MvnoRunException(-1, "조직이 존재하지 않습니다.");
		}
		
		if(!"".equals(vo.getSearchType()) && "".equals(vo.getSearchVal())){
			throw new MvnoRunException(-1, "검색어를 입력하세요");
		}
		
		HashMap<String, String> maskFields = getMaskFields();
		
		List<MaskingVO> lCmnMskGrp = maskingMapper.selectListNotEgovMap(paramMap);
		
		List<StatMgmtUagAmntVO> list = statMgmtMapper.getUsgAmntStatOrgList(vo);
		
		for(StatMgmtUagAmntVO item : list) {
			maskingService.setMask(item, maskFields, lCmnMskGrp);
		}
		
		return list;
	}
	
	public List<StatMgmtUagAmntVO> getUsgAmntStatOrgListExcel(StatMgmtVO vo, Map<String, Object> paramMap){
		
		if(vo.getUsgYm() == null || "".equals(vo.getUsgYm())){
			throw new MvnoRunException(-1, "사용월이 존재하지 않습니다");
		}

		if(vo.getOrgnId() == null || "".equals(vo.getOrgnId())){
			throw new MvnoRunException(-1, "조직이 존재하지 않습니다.");
		}
		
		if(!"".equals(vo.getSearchType()) && "".equals(vo.getSearchVal())){
			throw new MvnoRunException(-1, "검색어를 입력하세요");
		}
		
		HashMap<String, String> maskFields = getMaskFields();
		
		List<MaskingVO> lCmnMskGrp = maskingMapper.selectListNotEgovMap(paramMap);
		
		List<StatMgmtUagAmntVO> list = statMgmtMapper.getUsgAmntStatOrgListExcel(vo);
		
		for(StatMgmtUagAmntVO item : list) {
			maskingService.setMask(item, maskFields, lCmnMskGrp);
		}
		
		return list;
	}
	
	/**
	 * 청구항목별자료 조회 2018.03.23 권오승
	 * */
	public List<?> getInvItemDataList(StatMgmtVO vo){
		// 필수값 체크
		if(vo.getStdrDt() == null || vo.getStdrDt().equals("")){
			throw new MvnoRunException(-1, "기준월이 존재하지 않습니다");
		}
		return statMgmtMapper.getInvItemDataList(vo);
	}
	
	/**
	 * 
	 * 청구항목별자료 엑셀조회
	 * @param vo
	 * @return
	 */
	public List<StatMgmtVO> getInvItemDataListExcel(StatMgmtVO vo){
		
		if(vo.getStdrDt() == null || "".equals(vo.getStdrDt())){
			throw new MvnoRunException(-1, "기준월 존재하지 않습니다");
		}
		List<StatMgmtVO> list = statMgmtMapper.getInvItemDataListExcel(vo);
		
		return list;
	}
	
	/**
	 * 3G 유지가입자 현황 조회
	 * */
	public List<?> getDaily3gSubList(StatMgmt3gSubVO vo){
		// 필수값 체크
		if(vo.getStdrDt() == null || vo.getStdrDt().equals("")){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		return statMgmtMapper.getDaily3gSubList(vo);
	}
}
	
