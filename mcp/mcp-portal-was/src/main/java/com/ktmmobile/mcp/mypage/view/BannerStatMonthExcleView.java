package com.ktmmobile.mcp.mypage.view;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.mcp.common.dto.BannerStatDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarMonBillingInfoVO.M_MonthPayMentVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarMonDetailInfoVO.ItemEtcVO;
import com.ktmmobile.mcp.common.util.DateTimeUtil;

@SuppressWarnings("deprecation")
public class BannerStatMonthExcleView extends org.springframework.web.servlet.view.document.AbstractJExcelView {

    private static final Logger logger = LoggerFactory.getLogger(BannerStatMonthExcleView.class);

    private final String[] title = {"번호","판매월","배너명","배너코드","배너등록일","신청완료","판매량(개통완료)"};
    private final int [] width = {10,20,40,40,40,20,20};

    private final static String NAME = "배너별판매량통계월별";

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
        List<BannerStatDto> list = (List<BannerStatDto>) param.get("list");
        BannerStatDto total = (BannerStatDto) param.get("total");

        int sheetNum = 0;
        WritableSheet sheet = null;
        try {
            sheet = this.createSheet(workbook, NAME+sheetNum, sheetNum++);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        WritableCellFormat format1 = null;
        try {
            format1 = this.getCellFormat();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        int row = 0;
        ++row;
    	sheet.addCell(new jxl.write.Label(0,  row, "종합", format1));
    	sheet.addCell(new jxl.write.Label(1,  row, total.getReqInDay(), format1));
    	sheet.addCell(new jxl.write.Label(2,  row, "-", format1));
        sheet.addCell(new jxl.write.Label(3,  row, "-", format1));
        sheet.addCell(new jxl.write.Label(4,  row, "-", format1));
        sheet.addCell(new jxl.write.Label(5,  row, String.valueOf(total.getRequestStateCodeSum1()), format1));
        sheet.addCell(new jxl.write.Label(6,  row, String.valueOf(total.getRequestStateCodeSum2()), format1));
        if( list != null){
            for(int i=0; i < list.size(); i++){
            	BannerStatDto data = list.get(i);
                ++row;
                if(row > 65535 * sheetNum){
                    try {
                        sheet = this.createSheet(workbook, NAME+sheetNum, sheetNum++);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    row = 1;
                }

                sheet.addCell(new jxl.write.Label(0,  row, String.valueOf(data.getRnum()), format1));
                sheet.addCell(new jxl.write.Label(1,  row, data.getReqInDay(), format1));
                sheet.addCell(new jxl.write.Label(2,  row, data.getBannNm(), format1));
                sheet.addCell(new jxl.write.Label(3,  row, String.valueOf(data.getBannSeq()), format1));
                sheet.addCell(new jxl.write.Label(4,  row, data.getCretDt(), format1));
                sheet.addCell(new jxl.write.Label(5,  row, String.valueOf(data.getRequestStateCodeSum1()), format1));
                sheet.addCell(new jxl.write.Label(6,  row, String.valueOf(data.getRequestStateCodeSum2()), format1));

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

    private WritableSheet createSheet(WritableWorkbook workbook, String name, int sheetNum) throws RowsExceededException, WriteException {
        WritableSheet sheet = workbook.createSheet(name, sheetNum);
        WritableCellFormat format0 = null;
        try {
            format0 = this.getTitleFormat();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        for(int i=0; i < title.length; i++){
            sheet.setColumnView(i, width[i]);
            sheet.addCell(new jxl.write.Label(i, 0, title[i], format0));
        }
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
