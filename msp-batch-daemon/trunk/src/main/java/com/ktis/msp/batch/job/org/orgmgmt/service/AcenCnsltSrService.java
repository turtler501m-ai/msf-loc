package com.ktis.msp.batch.job.org.orgmgmt.service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.orgmgmt.mapper.AcenCnsltSrMapper;
import com.ktis.msp.batch.job.org.orgmgmt.vo.AcenCnsltSrVO;
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
import java.util.*;

@Service
public class AcenCnsltSrService extends BaseService {

    @Autowired
    private AcenCnsltSrMapper acenCnsltSrMapper;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private FileDownService fileDownService;

    @Autowired
    private MaskingService maskingService;

    private static final String XML_ENCODING = "UTF-8";

    public void selectAcenCnsltConSrListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {

        // 엑셀 첫줄
        String[] strHead = {
          "통화일자", "통화유형", "상담유형(대)", "상담유형(중)", "상담유형(소)", "상담유형(세)", "통화시작", "통화종료", "총통화(초)", "후처리(초)",
          "통화번호", "CTN", "등록번호", "고객번호", "상담사", "통화자", "고객명", "통화결과", "만나이", "생년월일",
          "성별", "망구분", "개통대리점", "현재상태", "개통일", "해지일", "정지일", "업무구분", "구매유형", "최초유심접점",
          "유심접점", "현재 요금제", "마케팅동의여부", "ESIM여부", "모집경로", "상담내용"}; // 36

        String [] strValue = {
          "talkDt", "talkType", "cnsltTypeFirNm", "cnsltTypeSecNm", "cnsltTypeThiNm", "cnsltTypeDtlNm", "talkStrtDttm", "talkEndDttm", "talkTotalTime", "acwTotalTime",
          "talkTelNo", "ctn", "contractNum", "customerNo", "cnsltNm", "talkNm", "cstmrNm", "talkResultCode", "age", "birthDt",
          "gender", "dataType", "openAgnt", "subStatusNm", "lstComActvDate", "cancelDate", "stopDate", "operType", "reqBuyType", "fstUsimOrg",
          "usimOrg", "rateNm", "mrktAgreeYn", "usimKindsCd","onOffType" ,"cnsltContent"}; // 36

        int[] intWidth = {
          15, 15, 40, 40, 40, 40, 20, 20, 15, 15,
          15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
          15, 15, 30, 15, 15, 15, 15, 15, 15, 20,
          20, 50, 15, 15, 20, 80}; // 36

        int[] intLen = {
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0}; // 36

        try {

            String execParam = batchCommonVO.getExecParam();
            AcenCnsltSrVO searchVO = (AcenCnsltSrVO) JacksonUtil.makeVOFromJson(execParam, AcenCnsltSrVO.class);

            String serverInfo = propertiesService.getString("excelPath");
            String fileName = serverInfo + "A'Cen상담이력(SR)목록(상담내용포함)_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
            String sheetName = "A'Cen상담이력(SR)목록(상담내용포함)";

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

            // db
            acenCnsltSrMapper.selectAcenCnsltConSrListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));

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
            throw new MvnoServiceException("A’Cen 상담이력 SR 목록 엑셀저장 에러", e);
        }
    }

    public void selectAcenCnsltSrListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {

        // 엑셀 첫줄
        String[] strHead = {
          "통화일자", "통화유형", "상담유형(대)", "상담유형(중)", "상담유형(소)", "상담유형(세)", "통화시작", "통화종료", "총통화(초)", "후처리(초)",
          "통화번호", "CTN", "등록번호", "고객번호", "상담사", "통화자", "고객명", "통화결과", "만나이", "생년월일",
          "성별", "망구분", "개통대리점", "현재상태", "개통일", "해지일", "정지일", "업무구분", "구매유형", "최초유심접점",
          "유심접점", "현재 요금제", "마케팅동의여부", "ESIM여부", "모집경로"}; // 35

        String [] strValue = {
          "talkDt", "talkType", "cnsltTypeFirNm", "cnsltTypeSecNm", "cnsltTypeThiNm", "cnsltTypeDtlNm", "talkStrtDttm", "talkEndDttm", "talkTotalTime", "acwTotalTime",
          "talkTelNo", "ctn", "contractNum", "customerNo", "cnsltNm", "talkNm", "cstmrNm", "talkResultCode", "age", "birthDt",
          "gender", "dataType", "openAgnt", "subStatusNm", "lstComActvDate", "cancelDate", "stopDate", "operType", "reqBuyType", "fstUsimOrg",
          "usimOrg", "rateNm", "mrktAgreeYn", "usimKindsCd", "onOffType"}; // 35

        int[] intWidth = {
          15, 15, 40, 40, 40, 40, 20, 20, 15, 15,
          15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
          15, 15, 30, 15, 15, 15, 15, 15, 15, 20,
          20, 50, 15, 15, 20}; // 35

        int[] intLen = {
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0}; // 35

        try {

            String execParam = batchCommonVO.getExecParam();
            AcenCnsltSrVO searchVO = (AcenCnsltSrVO) JacksonUtil.makeVOFromJson(execParam, AcenCnsltSrVO.class);

            String serverInfo = propertiesService.getString("excelPath");
            String fileName = serverInfo + "A'Cen상담이력(SR)목록(상담내용미포함)_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
            String sheetName = "A'Cen상담이력(SR)목록(상담내용미포함)";

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

            // db
            acenCnsltSrMapper.selectAcenCnsltSrListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));

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
            throw new MvnoServiceException("A’Cen 상담이력 SR 목록 엑셀저장 에러", e);
        }
    }

    public void selectAcenCnsltSrStatsListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {

        // 엑셀 첫줄
        String[] strHead = {"연도-월", "상담유형(대)", "상담유형(중)", "상담유형(소)", "상담유형(상세)", "상담유형 건수"}; // 6

        String [] strValue = {"talkYm", "cnsltTypeFirNm", "cnsltTypeSecNm", "cnsltTypeThiNm", "cnsltTypeDtlNm", "totalCnt"}; // 6

        int[] intWidth = {20, 30, 30, 30, 50, 40}; // 6

        int[] intLen = {0, 0, 0, 0, 0, 0}; // 6

        try {

            String execParam = batchCommonVO.getExecParam();
            AcenCnsltSrVO searchVO = (AcenCnsltSrVO) JacksonUtil.makeVOFromJson(execParam, AcenCnsltSrVO.class);

            String serverInfo = propertiesService.getString("excelPath");
            String fileName = serverInfo + "A'Cen상담이력(SR)월통계_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
            String sheetName = "A'Cen상담이력(SR)월통계";

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

            // db
            acenCnsltSrMapper.selectAcenCnsltSrStatsListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));

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
            throw new MvnoServiceException("A’Cen 상담이력 SR 월 통계 목록 엑셀저장 에러", e);
        }
    }
}
