package com.ktmmobile.mcp.mypage.view;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.mcp.common.mplatform.vo.MpFarPaymentInfoVO.ItemPay;
import com.ktmmobile.mcp.common.util.DateTimeUtil;

import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@SuppressWarnings("deprecation")
public class ChargeMonthListExcelView extends org.springframework.web.servlet.view.document.AbstractJExcelView {

    private static final Logger logger = LoggerFactory.getLogger(ChargeMonthListExcelView.class);


    private final String[] title = {"납부일자","납부방법","납부금액"};
    private final int [] width = {10,10,10,10};

    private final static String NAME = "납부내역";

    @Override
    protected void buildExcelDocument(Map<String, Object> param, WritableWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws RowsExceededException, WriteException {
        String fileName = null;
        try {
            fileName = createFileName();
        } catch(UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        setFileNameToResponse(request, response, fileName);

        @SuppressWarnings("unchecked")
        List<ItemPay> list = (List<ItemPay>) param.get("list");
        String userName = (String) param.get("userName");
        String userCtn = (String) param.get("userCtn");

        int sheetNum = 0;
        WritableSheet sheet = null;
        try {
            sheet = this.createSheet(workbook, NAME+sheetNum, sheetNum++,userName,userCtn);
        } catch(RowsExceededException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        WritableCellFormat format1 = null;
        try {
            format1 = this.getCellFormat();
        } catch(WriteException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        WritableCellFormat formatDate = null;
        try {
            formatDate = this.getDateFormat();
        } catch(WriteException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        WritableCellFormat formatInt = null;
        try {
            formatInt = this.getIntFormat();
        } catch(WriteException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        int row = 2; // index 2row 이후에 데이터 노출
        if( list != null){
            for(int i=0; i < list.size(); i++){
                ItemPay data = list.get(i);
                ++row;
                if(row > 65535 * sheetNum){
                    try {
                        sheet = this.createSheet(workbook, NAME, sheetNum++,userName,userCtn);
                    } catch (RowsExceededException e) {
                        logger.error(e.getMessage());
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    row = 2;
                }

                sheet.setRowView(row, 400);
                Date now = Calendar.getInstance().getTime();
                try {
                    now = DateTimeUtil.check(data.getPayMentDate());
                } catch(ParseException e) {
                    logger.error(e.getMessage());
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }

                DateTime datetime = new DateTime(0,row,now,formatDate);
                sheet.addCell(datetime);
                sheet.addCell(new jxl.write.Label(2,  row, data.getPayMentMethod(), format1));  //payMentMethod
                //sheet.addCell(new jxl.write.Label(4,  row, data.getPayMentMoney(), format1));   //payMentMoney

                int intPay = Integer.parseInt(data.getPayMentMoney());
                jxl.write.Number number4 = new jxl.write.Number(4, row, intPay, formatInt);
                sheet.addCell(number4);
                sheet.mergeCells(0, row, 1, row); // 컬럼,로우,컬럼,로우
                sheet.mergeCells(2, row, 3, row); // 컬럼,로우,컬럼,로우
                sheet.mergeCells(4, row, 5, row); // 컬럼,로우,컬럼,로우
            }
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
        } catch(WriteException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        // 구분/금액
        WritableCellFormat format1 = null;
        try {
            format1 = this.getTitleFormat();
        } catch(WriteException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        sheet.setRowView(0, 400);
        sheet.setColumnView(0, 20);
        sheet.addCell(new jxl.write.Label(0, 0, "고객명", format1)); // 열||,행==,값
        sheet.setColumnView(2, 20);
        sheet.addCell(new jxl.write.Label(2, 0, userName, format0));
        sheet.setColumnView(3, 15);
        sheet.addCell(new jxl.write.Label(3, 0, "휴대폰번호", format1));
        sheet.setColumnView(5, 20);
        sheet.addCell(new jxl.write.Label(5, 0, userCtn, format0));
        sheet.mergeCells(0, 0, 1, 0); // 컬럼,로우,컬럼,로우
        sheet.mergeCells(3, 0, 4, 0); // 컬럼,로우,컬럼,로우
        // 상단 회선 번호 및 이름 추가 끝


        // private final String[] title = {"납부일자","납부방법","납부금액"};
        sheet.setRowView(2, 400);
        sheet.setColumnView(3, 15);
        sheet.addCell(new jxl.write.Label(0, 2, "납부일자", format1));
        sheet.setColumnView(3, 15);
        sheet.addCell(new jxl.write.Label(2, 2, "납부방법", format1));
        sheet.setColumnView(3, 15);
        sheet.addCell(new jxl.write.Label(4, 2, "납부금액", format1));
        sheet.mergeCells(0, 2, 1, 2); // 컬럼,로우,컬럼,로우 sheet.mergeCells(시작열,시작행,종료열,종료행);
        sheet.mergeCells(2, 2, 3, 2); // 컬럼,로우,컬럼,로우
        sheet.mergeCells(4, 2, 5, 2); // 컬럼,로우,컬럼,로우
        return sheet;
    }

    private String createFileName() throws UnsupportedEncodingException {
        return new StringBuilder(URLEncoder.encode(NAME, "UTF-8")).append("-").append(DateTimeUtil.getTimeStampString()).append(".xls").toString();
    }

    private WritableCellFormat getTitleFormat() throws WriteException {
        WritableCellFormat format= new WritableCellFormat();
        format.setBackground(jxl.format.Colour.GRAY_25 );
        format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN );
        format.setVerticalAlignment(VerticalAlignment.CENTRE);
        format.setAlignment(jxl.format.Alignment.CENTRE);

        return format;
    }

    private WritableCellFormat getCellFormat() throws WriteException {
        WritableCellFormat format= new WritableCellFormat();
        format.setBackground(jxl.format.Colour.WHITE );
        format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN );
        format.setVerticalAlignment(VerticalAlignment.CENTRE);
        format.setAlignment(jxl.format.Alignment.CENTRE);

        return format;
    }


    private WritableCellFormat getDateFormat() throws WriteException {

        //Date now = Calendar.getInstance().getTime();
        DateFormat userdatafmt = new DateFormat("yyyy-MM-DD");
        WritableCellFormat format = new WritableCellFormat (userdatafmt);
        //DateTime dateCell = new DateTime(0, 6, now, dateFormat);
        //sheet.addCell(dateCell);


        //WritableCellFormat format= new WritableCellFormat();
        format.setBackground(jxl.format.Colour.WHITE );
        format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN );
        format.setVerticalAlignment(VerticalAlignment.CENTRE);
        format.setAlignment(jxl.format.Alignment.CENTRE);

        return format;
    }

    private WritableCellFormat getIntFormat() throws WriteException {
        NumberFormat fivedps = new NumberFormat("###,###");
        WritableCellFormat format = new WritableCellFormat(fivedps);
        format.setBackground(jxl.format.Colour.WHITE );
        format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN );
        format.setVerticalAlignment(VerticalAlignment.CENTRE);
        format.setAlignment(jxl.format.Alignment.CENTRE);

        return format;
    }

}
