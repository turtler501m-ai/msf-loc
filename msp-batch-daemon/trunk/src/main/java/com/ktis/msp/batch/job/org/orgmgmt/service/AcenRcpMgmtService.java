package com.ktis.msp.batch.job.org.orgmgmt.service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.orgmgmt.mapper.AcenRcpMgmtMapper;
import com.ktis.msp.batch.job.org.orgmgmt.vo.AcenRcpMgmtVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.RcpMgmtMapper;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.common.masking.MaskingService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.util.StringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AcenRcpMgmtService extends BaseService {

  @Autowired
  private AcenRcpMgmtMapper acenRcpMgmtMapper;

  @Resource(name = "propertiesService")
  protected EgovPropertyService propertiesService;

  @Autowired
  private FileDownService fileDownService;

  @Autowired
  private MaskingService maskingService;

  @Autowired
  private RcpMgmtMapper rcpMgmtMapper;

  private static final String XML_ENCODING = "UTF-8";

  public void selectAcenRcpMgmtListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {

    // 엑셀 첫줄
    String [] strHead = {
       "신청일자",			"예약번호",		"고객명",			"생년월일",		"휴대폰번호",
       "대리점",				"모집경로",		"요금제",			"업무구분",		"진행상태",
       "신청서상태",			"유심접점",		"배송방법",		"시나리오",		"성공여부",
       "시나리오(상세)",		"요청여부",		"실패사유",		"처리메모",		"수정자",
       "수정일자" ,			"재실행 여부",	"eSIM 여부"
    };

    String [] strValue = {
       "reqInDay",				"resNo",				"cstmrNm",			"birthDt",					"cstmrMobile",
       "agentNm",				"onOffNm",			"socNm",				"operNm",				"requestStateNm",
       "pstateNm",				"usimOrgNm",		"dlvryTypeNm",		"acenEvntGrpNm",		"acenStatus",
       "acenEvntTypeNm",		"reqStatusNm",		"acenFailRmk",		"requestMemo",		"rvisnNm",
       "rvisnDttm",				"rtyYn",				"usimKindsCd"
    };

    int[] intWidth = {
      20, 15, 15, 15, 20, 
      30, 15, 40, 15, 15,
      15, 20, 15, 20, 10, 
      30, 10, 50, 50, 15,
      20, 15, 15
    };

    int[] intLen = {
      0, 0, 0, 0, 0,
      0, 0, 0, 0, 0,
      0, 0, 0, 0, 0,
      0, 0, 0, 0, 0,
      0, 0, 0
    };

    try {

      String execParam = batchCommonVO.getExecParam();
      AcenRcpMgmtVO searchVO = (AcenRcpMgmtVO) JacksonUtil.makeVOFromJson(execParam, AcenRcpMgmtVO.class);

      String serverInfo = propertiesService.getString("excelPath");
      String fileName = serverInfo + "A’Cen자동개통통계_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
      String sheetName = "A’Cen자동개통통계";

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

      // save the template
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
        String pContractNum = rcpMgmtMapper.getpContractNum(searchVO.getpSearchName());

        if(pContractNum != null && !"".equals(pContractNum)){
          searchVO.setpSearchName(pContractNum);
        }
      }

      // db
      acenRcpMgmtMapper.selectAcenRcpMgmtListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));

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
        pRequestParamMap.put("DUTY_NM"         , "ORG");                          //업무명
        pRequestParamMap.put("IP_INFO"         , searchVO.getIpAddr());           //IP정보
        pRequestParamMap.put("FILE_SIZE"       , (int) logFile.length());         //파일크기
        pRequestParamMap.put("menuId"          , searchVO.getMenuId());           //메뉴ID
        pRequestParamMap.put("DATA_CNT"        , 0);                              //자료건수
        pRequestParamMap.put("SESSION_USER_ID" , searchVO.getUserId()); 	        //사용자ID
        pRequestParamMap.put("EXCL_DNLD_ID"    , searchVO.getExclDnldId());
        pRequestParamMap.put("DWNLD_RSN"       , searchVO.getDwnldRsn());

        fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
      }
      //=======파일다운로드사유 로그 END==========================================================

      // 엑셀파일 저장한 내역 DB에 남기기
      fileDownService.saveExcelHistory(sbFileName.toString(), searchVO.getUserId());

    } catch(Exception e){
      LOGGER.error(StringUtil.getPrintStackTrace(e));
      throw new MvnoServiceException("A’Cen자동개통통계 엑셀저장 에러", e);
    }
  }

  public void selectAcenRcpMgmtFailListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {

    // 엑셀 첫줄
    String [] strHead = {
      "신청일자",				"예약번호",		"고객명",			"생년월일",		"휴대폰번호",
      "대리점",				"모집경로",		"요금제",			"업무구분",		"진행상태",
      "신청서상태",			"유심접점",		"배송방법",		"시나리오",		"성공여부",
      "시나리오(상세)",		"요청여부",		"실패사유",		"실패일자",		"처리메모",
      "수정자",				"수정일자",		"재실행 여부",	"eSIM 여부"
    };

    String [] strValue = {
      "reqInDay",					"resNo",				"cstmrNm",			"birthDt",				"cstmrMobile",
      "agentNm",				"onOffNm",			"socNm",				"operNm",			"requestStateNm",
      "pstateNm",				"usimOrgNm",		"dlvryTypeNm",		"acenEvntGrpNm",	"acenStatus",
      "acenEvntTypeNm",		"reqStatusNm",		"acenFailRmk",		"acenFailDttm",		"requestMemo",
      "rvisnNm",					"rvisnDttm",			"rtyYn",				"usimKindsCd"
    };

    int[] intWidth = {
      20, 15, 15, 15, 20, 
      30, 15, 40, 15, 15,
      15, 20, 15, 20, 10, 
      30, 10, 50, 20, 50,
      15, 20, 15, 15
    };

    int[] intLen = {
      0, 0, 0, 0, 0,
      0, 0, 0, 0, 0,
      0, 0, 0, 0, 0,
      0, 0, 0, 0, 0,
      0, 0, 0, 0
    };

    try {

      String execParam = batchCommonVO.getExecParam();
      AcenRcpMgmtVO searchVO = (AcenRcpMgmtVO) JacksonUtil.makeVOFromJson(execParam, AcenRcpMgmtVO.class);

      String serverInfo = propertiesService.getString("excelPath");
      String fileName = serverInfo + "A’Cen자동개통통계_실패상세_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
      String sheetName = "A’Cen자동개통통계_실패상세";

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

      // save the template
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
        String pContractNum = rcpMgmtMapper.getpContractNum(searchVO.getpSearchName());

        if(pContractNum != null && !"".equals(pContractNum)){
          searchVO.setpSearchName(pContractNum);
        }
      }

      // db
      acenRcpMgmtMapper.selectAcenRcpMgmtFailListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));

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
        pRequestParamMap.put("DUTY_NM"         , "ORG");                          //업무명
        pRequestParamMap.put("IP_INFO"         , searchVO.getIpAddr());           //IP정보
        pRequestParamMap.put("FILE_SIZE"       , (int) logFile.length());         //파일크기
        pRequestParamMap.put("menuId"          , searchVO.getMenuId());           //메뉴ID
        pRequestParamMap.put("DATA_CNT"        , 0);                              //자료건수
        pRequestParamMap.put("SESSION_USER_ID" , searchVO.getUserId()); 	        //사용자ID
        pRequestParamMap.put("EXCL_DNLD_ID"    , searchVO.getExclDnldId());
        pRequestParamMap.put("DWNLD_RSN"       , searchVO.getDwnldRsn());

        fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
      }
      //=======파일다운로드사유 로그 END==========================================================

      // 엑셀파일 저장한 내역 DB에 남기기
      fileDownService.saveExcelHistory(sbFileName.toString(), searchVO.getUserId());

    } catch(Exception e){
      LOGGER.error(StringUtil.getPrintStackTrace(e));
      throw new MvnoServiceException("A’Cen자동개통 통계 실패상세 엑셀저장 에러", e);
    }
  }


}
