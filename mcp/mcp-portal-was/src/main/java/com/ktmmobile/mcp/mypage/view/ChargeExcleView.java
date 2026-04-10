package com.ktmmobile.mcp.mypage.view;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.mcp.common.mplatform.vo.MpEtcVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpMonthPayMentDto;
import com.ktmmobile.mcp.common.util.DateTimeUtil;

import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@SuppressWarnings("deprecation")
public class ChargeExcleView extends org.springframework.web.servlet.view.document.AbstractJExcelView {

    private static final Logger logger = LoggerFactory.getLogger(ChargeExcleView.class);
    

    private final String[] title = {"구분","금액"};
    private final int [] width = {10,10,10,10};

    private final static String NAME = "요금상세내역";

    @Override
    protected void buildExcelDocument(Map<String, Object> param, WritableWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws RowsExceededException, WriteException {
        String fileName = null;
        try {
            fileName = createFileName();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        setFileNameToResponse(request, response, fileName);

        @SuppressWarnings("unchecked")
        List<MpEtcVO> list = (List<MpEtcVO>) param.get("list");
        MpMonthPayMentDto monthPay = (MpMonthPayMentDto) param.get("total");
        String userName = (String) param.get("userName");
        String userCtn = (String) param.get("userCtn");

        int sheetNum = 0;
        WritableSheet sheet = null;
        try {
            sheet = this.createSheet(workbook, NAME+sheetNum, sheetNum++,userName,userCtn);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        WritableCellFormat format1 = null;
        try {
            format1 = this.getCellFormat();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        int row = 2; // index 2row 이후에 데이터 노출
        if( list != null){
            for(int i=0; i < list.size(); i++){
            	MpEtcVO data = list.get(i);
                ++row;
                if(row > 65535 * sheetNum){
                    try {
                        sheet = this.createSheet(workbook, NAME+sheetNum, sheetNum++,userName,userCtn);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    row = 2;
                }

                sheet.addCell(new jxl.write.Label(0,  row, data.getSplitDescription(), format1));
                sheet.addCell(new jxl.write.Label(2,  row, data.getActvAmt(), format1));
                sheet.mergeCells(0, row, 1, row); // 컬럼,로우,컬럼,로우
                sheet.mergeCells(2, row, 3, row); // 컬럼,로우,컬럼,로우
            }
        }
        if(monthPay != null){
            ++row;
            sheet.addCell(new jxl.write.Label(0,  row, "총 청구금액", format1));
            sheet.addCell(new jxl.write.Label(2,  row, monthPay.getTotalDueAmt(), format1));
            sheet.mergeCells(0, row, 1, row); // 컬럼,로우,컬럼,로우
            sheet.mergeCells(2, row, 3, row); // 컬럼,로우,컬럼,로우
        }

    }

    private void setFileNameToResponse(HttpServletRequest request, HttpServletResponse response, String fileName) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.indexOf("MSIE 5.5") >= 0) {
            response.setContentType("doesn/matter");
            response.setHeader("Content-Disposition","filename=\""+fileName+"\"");
        } else {
            response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
        }
    }

    private WritableSheet createSheet(WritableWorkbook workbook, String name, int sheetNum,String userName,String userCtn) throws RowsExceededException, WriteException {
        WritableSheet sheet = workbook.createSheet(name, sheetNum);

        // 상단 회선 번호 및 이름 추가
        WritableCellFormat format0 = null;
        try {
        	format0 = this.getCellFormat();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        sheet.setRowView(0, 400);
        sheet.setColumnView(0, 10);
        sheet.addCell(new jxl.write.Label(0, 0, "고객명", format0)); // 열||,행==,값
        sheet.setColumnView(1, 10);
        sheet.addCell(new jxl.write.Label(1, 0, userName, format0));
        sheet.setColumnView(2, 15);
        sheet.addCell(new jxl.write.Label(2, 0, "휴대폰번호", format0));
        sheet.setColumnView(3, 20);
        sheet.addCell(new jxl.write.Label(3, 0, userCtn, format0));
        // 상단 회선 번호 및 이름 추가 끝

        // 구분/금액
        WritableCellFormat format1 = null;
        try {
        	format1 = this.getTitleFormat();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        sheet.setColumnView(3, 10);
        sheet.addCell(new jxl.write.Label(0, 2, "구분", format1));
        sheet.setColumnView(3, 10);
        sheet.addCell(new jxl.write.Label(2, 2, "금액", format1));
        sheet.mergeCells(0, 2, 1, 2); // 컬럼,로우,컬럼,로우
        sheet.mergeCells(2, 2, 3, 2); // 컬럼,로우,컬럼,로우
        return sheet;
    }

    private String createFileName() throws UnsupportedEncodingException {
        return new StringBuilder(URLEncoder.encode(NAME, "UTF-8")).append("-").append(DateTimeUtil.getTimeStampString()).append(".xls").toString();
    }

    private WritableCellFormat getTitleFormat() throws WriteException {
        WritableCellFormat format= new WritableCellFormat();
        format.setBackground(jxl.format.Colour.AQUA );
        format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN );
        format.setAlignment(jxl.format.Alignment.CENTRE);

        return format;
    }

    private WritableCellFormat getCellFormat() throws WriteException {
        WritableCellFormat format= new WritableCellFormat();
        format.setBackground(jxl.format.Colour.WHITE );
        format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN );
        format.setAlignment(jxl.format.Alignment.CENTRE);

        return format;
    }

}
