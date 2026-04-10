package com.ktis.msp.rcp.old.controller;

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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class RcpExcleView extends AbstractJExcelView {
	
	protected Logger log = LogManager.getLogger(getClass());
	
	private final String[] title = {"번호","고객명","주민번호","휴대폰번호","서비스구분","구분","요금제","신청일자","진행상태","처리상태"};
	private final Integer[] width = {5,20,20,20,20,40,25,20,20,20};
 
	private final static String name = "template_request_list";

	@Override
	protected void buildExcelDocument(Map<String, Object> param, WritableWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws RowsExceededException, WriteException {
		String fileName = null;
		try {
			fileName = createFileName();
		} catch (Exception e) {
			log.error(e);
		}
		setFileNameToResponse(request, response, fileName);        

		List<EgovMap> list = (List<EgovMap>) param.get("list");
		int sheetNum = 0;
		WritableSheet sheet = null;
		try {
			sheet = this.createSheet(workbook, name+"_"+sheetNum, sheetNum++);
		} catch (Exception e) {
			log.error(e);
		}
		WritableCellFormat format1 = null;
		try {
			format1 = this.getCellFormat();
		} catch (Exception e) {
			log.error(e);
		}

		int row = 0;
		if( list != null){
			for(int i=0; i < list.size(); i++){
				EgovMap data = list.get(i);  
				++row;
				if(row > 65535 * sheetNum){
					try {
						sheet = this.createSheet(workbook, name+"_"+sheetNum, sheetNum++);
					} catch (Exception e) {
						log.error(e);
					}
					row = 1;
				}
				int sell = 0;
				sheet.addCell(new jxl.write.Label(sell++,  row, String.valueOf(row), format1));
				sheet.addCell(new jxl.write.Label(sell++,  row, (String)data.get("cstmrName"), format1));
				if(!StringUtils.equals("FN", (String) data.get("cstmrType"))){
					sheet.addCell(new jxl.write.Label(sell++,  row, (String)data.get("cstmrNativeRrn"), format1));
				}
				
				sheet.addCell(new jxl.write.Label(sell++,  row, (String)data.get("cstmrMobile"), format1));
				sheet.addCell(new jxl.write.Label(sell++,  row, (String)data.get("serviceType"), format1));
				
				if(StringUtils.equals("1100011741", (String)data.get("cntpntShopId")) ){
					sheet.addCell(new jxl.write.Label(sell++,  row, "온라인", format1));
				}else if(StringUtils.equals("1100011744", (String)data.get("cntpntShopId")) ){
					sheet.addCell(new jxl.write.Label(sell++,  row, "사내특판", format1));
				}else if(StringUtils.equals("1100011745", (String)data.get("cntpntShopId")) ){
					sheet.addCell(new jxl.write.Label(sell++,  row, "프리피아", format1));
				}else{
					sheet.addCell(new jxl.write.Label(sell++,  row, (String)data.get("cntpntShopId"), format1));
				}
				sheet.addCell(new jxl.write.Label(sell++,  row, StringUtils.defaultIfBlank((String)data.get("rateName"), (String)data.get("rateCode")), format1));
				sheet.addCell(new jxl.write.Label(sell++,  row, (String)data.get("rdate"), format1));
				sheet.addCell(new jxl.write.Label(sell++,  row, (String)data.get("requestStateCode"), format1));
				sheet.addCell(new jxl.write.Label(sell++,  row, (String)data.get("pstate"), format1));
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
			log.error(e);
		}		

		for(int i=0; i < title.length; i++){
			sheet.setColumnView(i, width[i]);
			sheet.addCell(new jxl.write.Label(i, 0, title[i], format0));	
		}
		return sheet;
	}

	private String createFileName() throws UnsupportedEncodingException {
		return new StringBuilder(URLEncoder.encode(this.name, "UTF-8")).append("-").append(".xls").toString();
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
