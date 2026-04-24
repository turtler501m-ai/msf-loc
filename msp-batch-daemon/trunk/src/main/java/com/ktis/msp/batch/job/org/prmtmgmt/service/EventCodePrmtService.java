package com.ktis.msp.batch.job.org.prmtmgmt.service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.prmtmgmt.mapper.EventCodePrmtMapper;
import com.ktis.msp.batch.job.org.prmtmgmt.vo.EventCodePrmtVO;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.GiftVocVO;
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
public class EventCodePrmtService extends BaseService {

    @Autowired
    private EventCodePrmtMapper eventCodePrmtMapper;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private FileDownService fileDownService;

    @Autowired
    private MaskingService maskingService;

    private static final String XML_ENCODING = "UTF-8";

    /**
     * 이벤트코드 이력 관리 엑셀 다운로드
     * @param batchCommonVO
     * @throws MvnoServiceException
     */
    public void getEventCodePrmtListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
        String [] strHead = {
                "이벤트제목","이벤트코드","계약번호","고객명","휴대폰번호",
                "최초 요금제 코드","최초 요금제명","현재 요금제 코드","현재 요금제명","대리점",
                "업무구분","구매유형","신청일자","개통일자","해지일자",
                "정지일자","유심접점","성별","생년월일","나이",
                "상태","이동전통신사","추천인정보"
        };
        String [] strValue = {
                "evntnm","evntcd","contractnum","sublinkname","subscriberno",
                "fstratecd","fstratenm","lstratecd","lstratenm","openagntnm",
                "opertypenm","reqbuytypenm","reqinday","lstcomactvdate","candt",
                "stopdt","usimorgnm","cstmrgender","birthdt","custage",
                "substatusnm","movecompanynm","recommendinfo"
        };
        int[] intWidth = {
                15, 20, 15, 20, 15,
                25, 25, 25, 25, 25,
                15, 20, 25, 15, 15,
                15, 25, 15, 15, 15,
                15, 20, 25
        };
        int[] intLen = {
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0
        };
        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------

            String execParam = batchCommonVO.getExecParam();
            EventCodePrmtVO searchVO = (EventCodePrmtVO) JacksonUtil.makeVOFromJson(execParam, EventCodePrmtVO.class);

            String serverInfo = propertiesService.getString("excelPath");
            String fileName = serverInfo + "이벤트코드 이력 관리_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
            String sheetName = "이벤트코드 이력 관리";

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
            eventCodePrmtMapper.getEventCodePrmtListExcel(searchVO, new ExcelDataListResultHandler(sw, styles, strHead, strValue, intLen, maskingService.getMaskFields(), paramMap));

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
            throw new MvnoServiceException("이벤트코드 이력 관리 엑셀저장 에러", e);
        }
    }
}
