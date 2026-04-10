package com.ktis.msp.org.common.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.org.common.mapper.OrgCommonMapper;
import com.ktis.msp.org.common.vo.FileInfoVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : OrgCommonService
 * @Description : 기준 공통 서비스
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */

@Service
public class OrgCommonService extends BaseService {
	
	@Autowired
	private OrgCommonMapper orgCommonMapper;
	
    @Autowired
    protected EgovPropertyService propertyService;
    
	/** Constructor */
	public OrgCommonService() {
		setLogPrefix("[OrgCommonService] ");
	}
	
    /**
     * @Description : 해당월의 시작일을 가지고 온다록
     * @Param  : AcntMgmtVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String getFirstDay(){
		return orgCommonMapper.getFirstDay(0);
	}
	
    /**
     * @Description : 해당월의 마지막일을 가지고 온다
     * @Param  : AcntMgmtVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String getLastDay(){
		return orgCommonMapper.getLastDay(0);
	}
	
    /**
     * @Description : 해당월을 가지고 온다
     * @Param  : AcntMgmtVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String getToMonth(){
		return orgCommonMapper.getToMonth(0);
	}
	
    /**
     * @Description : 해당일을 가지고 온다
     * @Param  : AcntMgmtVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String getToDay(){
		return orgCommonMapper.getToDay(0);
	}
	
    /**
     * @Description : 해당시간을 가지고 온다
     * @Param  : AcntMgmtVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String getToTime(){
		return orgCommonMapper.getToTime(0);
	}
	
    /**
     * @Description : 전월을 가지고 온다 
     * @Param  : AcntMgmtVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String getLastMonth(){
		return orgCommonMapper.getLastMonth(0);
	}
	
    /**
     * @Description : 전월 01일을 가지고 온다 
     * @Param  : AcntMgmtVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String getLastMonthDay(){
		return orgCommonMapper.getLastMonthDay(0);
	}
	
	/**
     * @Description : 전월 마지막일을 가지고 온다 
     * @Param  : AcntMgmtVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String getLastMonthMaxDay(){
		return orgCommonMapper.getLastMonthMaxDay(0);
	}
	
    /**
     * @Description : 해당일에 +된 일자를 가져온다.
     * @Param  : addition
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String getWantDay(int addition ){
		return orgCommonMapper.getWantDay(addition);
	}
	
    /**
     * @Description : 해당일에 +된 일자를 가져온다.
     * @Param  : addition
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String getWantDayTime(int addition ){
		return orgCommonMapper.getWantDayTime(addition);
	}
	
    /**
     * @Description : 해당일을 가지고 온다
     * @Param  : AcntMgmtVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String getToDayTime(){
		return orgCommonMapper.getToDayTime(0);
	}

    /**
     * @Description : 특정월 마지막일을 가지고 온다
     * @Param  : wantDate
     * @Return : String
     * @Author : hsy
     * @Create Date : 2023. 4. 24.
     */
    public String getCertainMonthMaxDay(String wantDate){
        return orgCommonMapper.getCertainMonthMaxDay(wantDate);
    }

    /**
     * @Description : 엑셀다운로드 기능
     * @Param  : fileName
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @SuppressWarnings("unchecked")
	public String excelDownProc(String fileName, String sheetName, List<?> voLists, String[] strHead, int[] intWidth, String[] strValue, HttpServletRequest request, HttpServletResponse response, int[] intLen ) { 
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("엑셀다운로드 START."));
        logger.info(generateLogMsg("================================================================="));
        
        // Workbook 생성 
        HSSFWorkbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전 
//        Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상 
  
        // Sheet 생성 
        Sheet sheet1 = xlsWb.createSheet(sheetName); 
         
        for (int i = 0; i < intWidth.length; i++) {
        	sheet1.setColumnWidth(i, intWidth[i]);
		}
          
        // Cell 스타일 생성 
        CellStyle cellStyleHead = xlsWb.createCellStyle(); 
        CellStyle cellStyleMid = xlsWb.createCellStyle(); 
        CellStyle cellStyleInt = xlsWb.createCellStyle();
        CellStyle cellStyleWrap = xlsWb.createCellStyle(); 
          
        // 줄 바꿈 
        cellStyleHead.setWrapText(true); 
          
        HSSFFont fontHead = xlsWb.createFont();
        fontHead.setBoldweight((short) 1);
        fontHead.setFontName("맑은 고딕");
        fontHead.setColor(HSSFColor.BLACK.index);
        fontHead.setFontHeightInPoints((short) 12);
        
        HSSFFont fontMid = xlsWb.createFont();
        fontMid.setBoldweight((short) 1);
        fontMid.setFontName("맑은 고딕");
        fontMid.setColor(HSSFColor.BLACK.index);
        fontMid.setFontHeightInPoints((short) 10);
        
        //제목
//        cellStyle.setFillForegroundColor(HSSFColor.LIME.index); 
        cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyleHead.setAlignment((short) 2);
        cellStyleHead.setFillPattern((short) 1);
        cellStyleHead.setFillPattern((short) 1);
        cellStyleHead.setBorderRight((short) 1);
//        cellStyleHead.setBorderLeft((short) 1);
        cellStyleHead.setBorderTop((short) 1);
        cellStyleHead.setBorderBottom((short) 1);
        cellStyleHead.setFont(fontHead);
        
        //내용
        cellStyleMid.setAlignment((short) 2);
        cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        cellStyleMid.setFillPattern((short) 1);
        cellStyleMid.setBorderRight((short) 1);
        cellStyleMid.setBorderLeft((short) 1);
        cellStyleMid.setBorderTop((short) 1);
        cellStyleMid.setBorderBottom((short) 1);
        cellStyleMid.setFont(fontMid);
        
        /**
         * @Description 셀데이터 줄바꿈 가능토록 추가
         * @Create Date : 2014. 11. 13.
         * @author IB
         */
        //내용줄바꿈
        cellStyleWrap.setWrapText(true);
        cellStyleWrap.setAlignment((short) 2);
        cellStyleWrap.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        cellStyleWrap.setFillPattern((short) 1);
        cellStyleWrap.setBorderRight((short) 1);
        cellStyleWrap.setBorderLeft((short) 1);
        cellStyleWrap.setBorderTop((short) 1);
        cellStyleWrap.setBorderBottom((short) 1);
        cellStyleWrap.setFont(fontMid);
        
        //숫자용
        HSSFDataFormat format = xlsWb.createDataFormat();
        
        cellStyleInt.setAlignment((short) 3);
        cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        cellStyleInt.setFillPattern((short) 1);
        cellStyleInt.setBorderRight((short) 1);
        cellStyleInt.setBorderLeft((short) 1);
        cellStyleInt.setBorderTop((short) 1);
        cellStyleInt.setBorderBottom((short) 1);
        cellStyleInt.setFont(fontMid);
        cellStyleInt.setDataFormat(format.getFormat("#,##0"));
        
        Row row = null; 
        Cell cell = null; 

        // 첫 번째 줄 제목 
        row = sheet1.createRow(0); 
        
        for (int i = 0; i < strHead.length; i++) {
        	cell = row.createCell(i); 
        	cell.setCellValue(strHead[i]);
        	cell.setCellStyle(cellStyleHead);
		}
        
        
        // 데이터 적재
		for (int i = 0; i < voLists.size(); i++) {
			
			Map<String, Object> map = (Map<String, Object>) voLists.get(i);

			row = sheet1.createRow(i + 1);
			
			for (int j = 0; j < strValue.length; j++) {

				cell = row.createCell(j);
				
				if(intLen[j] == 1)
				{
					double d = Double.parseDouble(map.get(strValue[j]).toString());
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cell.setCellValue(d);
					cell.setCellStyle(cellStyleInt);
				}
				/**
				 * @Description 셀데이터 줄바꿈 가능토록 추가
				 * @Create Date : 2014. 11. 13.
				 * @author IB
				 */
				else if(intLen[j] == 2)
				{
					cell.setCellValue( map.get(strValue[j]).toString());
		        	cell.setCellStyle(cellStyleWrap);
				}
				else
				{
					cell.setCellValue( map.get(strValue[j]).toString());
		        	cell.setCellStyle(cellStyleMid);
				}
			}
		}

		StringBuilder sbFileName = new StringBuilder();
		
        Date toDay = new Date();
        
        String strFileName = fileName;
        String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
        
        sbFileName.append(strFileName);
        //2015.12.09 엑셀다운로드 파일명에 ID 추가-곽대리요청
        //v2018.11 PMD 적용 소스 수정
        //sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID")+"_");
        sbFileName.append(request.getSession().getAttribute("SESSION_USER_ID"));
        sbFileName.append("_");
        sbFileName.append(strToDay);
        sbFileName.append(".xls");

        
        String encodingFileName = "";

		int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
		
        try {
          encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
        } catch (UnsupportedEncodingException uee) {
          encodingFileName = sbFileName.toString();
        }

        response.setHeader("Cache-Control", "");
        response.setHeader("Pragma", "");

        response.setContentType("Content-type:application/octet-stream;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        
        File tempFile = new File(sbFileName.toString());
        
        try {
			bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			
			xlsWb.write(bos);
			bos.flush();
			bos.close();
			      
			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());
		        
			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
		          bos.write(buf, 0, read);
			}
			bos.flush();
			        
			} catch (IOException e) {
				//20210714 소스코드점검수정
				logger.error("Connection Exception occurred");
				
		    } finally {
		        if (bis != null) {
		          try {
		            bis.close();
		          } catch (Exception e) {
		            logger.error(e);
		          }
		        }
		        if (bos != null) {
		          try {
		            bos.close();
		          } catch (Exception e) {
		        	  logger.error(e);
		          }

		        }
		    }
        return sbFileName.toString();
	}
    
    /**
     * @Description : 엑셀다운로드 기능 2
     * @Param  : fileName
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	public String excelDownProc2(String fileName, String sheetName, String[] strHead, int[] intWidth, String[] strValue, HttpServletRequest request, HttpServletResponse response, int[] intLen ) { 
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("엑셀다운로드 START."));
        logger.info(generateLogMsg("================================================================="));
        
        // Workbook 생성 
        HSSFWorkbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전 
//        Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상 
  
        // Sheet 생성 
        Sheet sheet1 = xlsWb.createSheet(sheetName); 
         
        for (int i = 0; i < intWidth.length; i++) {
        	sheet1.setColumnWidth(i, intWidth[i]);
		}
          
        // Cell 스타일 생성 
        CellStyle cellStyleHead = xlsWb.createCellStyle(); 
//        CellStyle cellStyleMid = xlsWb.createCellStyle(); 
//        CellStyle cellStyleInt = xlsWb.createCellStyle(); 
          
        // 줄 바꿈 
        cellStyleHead.setWrapText(true); 
          
        HSSFFont fontHead = xlsWb.createFont();
        fontHead.setBoldweight((short) 1);
        fontHead.setFontName("맑은 고딕");
        fontHead.setColor(HSSFColor.BLACK.index);
        fontHead.setFontHeightInPoints((short) 10);
        
        //제목
//        cellStyle.setFillForegroundColor(HSSFColor.LIME.index); 
        cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyleHead.setAlignment((short) 2);
        cellStyleHead.setFillPattern((short) 1);
        cellStyleHead.setFillPattern((short) 1);
        cellStyleHead.setBorderRight((short) 1);
//        cellStyleHead.setBorderLeft((short) 1);
        cellStyleHead.setBorderTop((short) 1);
        cellStyleHead.setBorderBottom((short) 1);
        cellStyleHead.setFont(fontHead);
        
        Row row = null; 
        Cell cell = null; 
        
        // 첫 번째 줄 제목 
        row = sheet1.createRow(0); 
        
        for (int i = 0; i < strHead.length; i++) {
        	cell = row.createCell(i); 
        	cell.setCellValue(strHead[i]);
        	cell.setCellStyle(cellStyleHead);
		}
        

		StringBuilder sbFileName = new StringBuilder();
		
        
        String strFileName = fileName;
        
        sbFileName.append(strFileName);
        sbFileName.append(".xls");

        
        String encodingFileName = "";

		int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
		
        try {
          encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
        } catch (UnsupportedEncodingException uee) {
          encodingFileName = sbFileName.toString();
        }

        response.setHeader("Cache-Control", "");
        response.setHeader("Pragma", "");

        response.setContentType("Content-type:application/octet-stream;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        
        File tempFile = new File(sbFileName.toString());
        
        try {
			bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			
			xlsWb.write(bos);
			bos.flush();
			bos.close();
			      
			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());
		        
			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
		          bos.write(buf, 0, read);
			}
			bos.flush();
			        
			} catch (IOException e) {
				//20210714 소스코드점검수정
				logger.error("Connection Exception occurred");
		    } finally {
		        if (bis != null) {
		          try {
		            bis.close();
		          } catch (Exception e) {
		            logger.error(e);
		          }
		        }
		        if (bos != null) {
		          try {
		            bos.close();
		          } catch (Exception e) {
		        	  logger.error(e);
		          }

		        }
		    }
        return sbFileName.toString();
	}

    /**
     * @Description : 파일 정보를 등록한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int insertFile(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("insertFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.insertFile(fileInfoVO);
		
        return resultCnt;
    }
    
    /**
     * @Description : 파일 정보를 변경한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFile(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.updateFile(fileInfoVO);
		
        return resultCnt;
    }   
    
    /**
     * @Description : 파일 정보를 변경한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFile1(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.updateFile1(fileInfoVO);
		
        return resultCnt;
    } 
    
    /**
     * @Description : 파일 정보를 변경한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFile2(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.updateFile2(fileInfoVO);
		
        return resultCnt;
    } 
    
    /**
     * @Description : 파일 정보를 변경한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFile3(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateFile3 START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.updateFile3(fileInfoVO);
		
        return resultCnt;
    } 
    
    /**
     * @Description : 파일 정보를 변경한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFile4(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateFile4 START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.updateFile4(fileInfoVO);
		
        return resultCnt;
    } 
    
    /**
     * @Description : 파일 정보를 변경한다.
     * @Param  : FileInfoVO5
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFile5(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.updateFile5(fileInfoVO);
		
        return resultCnt;
    } 
    
    /**
     * @Description : 파일 정보를 변경한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFile6(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateFile6 START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.updateFile6(fileInfoVO);
		
        return resultCnt;
    } 
    

    /**
     * @Description : 파일 정보를 변경한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFile7(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateFile6 START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.updateFile7(fileInfoVO);
		
        return resultCnt;
    } 
    
    /**
     * @Description : 파일 정보를 변경한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFile8(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateFile6 START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.updateFile8(fileInfoVO);
		
        return resultCnt;
    } 
    
	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> getFile1(FileInfoVO fileInfoVO){
		
		
		List<?> fileInfoVOs = new ArrayList<FileInfoVO>();
		
		fileInfoVOs = orgCommonMapper.getFile1(fileInfoVO);
		
		return fileInfoVOs;
	}    
	
	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> getFile2(FileInfoVO fileInfoVO){
		
		
		List<?> fileInfoVOs = new ArrayList<FileInfoVO>();
		
		fileInfoVOs = orgCommonMapper.getFile2(fileInfoVO);
		
		return fileInfoVOs;
	}    
	
	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> getFile3(FileInfoVO fileInfoVO){
		
		
		List<?> fileInfoVOs = new ArrayList<FileInfoVO>();
		
		fileInfoVOs = orgCommonMapper.getFile3(fileInfoVO);
		
		return fileInfoVOs;
	}    
	
	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public String getFileNm(String fileId){
		
		
		String returnFileId = orgCommonMapper.getFileNm(fileId);
		
		return returnFileId;
	}  
	
	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public String getFileNm2(String orgnId){
		
		
		String returnFileId = orgCommonMapper.getFileNm2(orgnId);
		
		return returnFileId;
	}  
	
	/**
	 * @Description : 첨부 파일 삭제
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public int deleteFile(String fileId){
		
		int returnFileId = orgCommonMapper.deleteFile(fileId);
		
		return returnFileId;
	}  	
	
	/**
	* @Description : 파일 갯수 
	* @Param  : FileInfoVO
	* @Return : int
	* @Author : 고은정
	* @Create Date : 2014. 10. 17.
	 */
	public int getFileCnt(FileInfoVO fileInfoVO){
        
        int returnCnt = orgCommonMapper.getFileCnt(fileInfoVO);
        
        return returnCnt;
    }	
	

	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> getFile4(FileInfoVO fileInfoVO){
		
		
		List<?> fileInfoVOs = new ArrayList<FileInfoVO>();
		
		fileInfoVOs = orgCommonMapper.getFile4(fileInfoVO);
		
		return fileInfoVOs;
	}

	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public int getFile5(FileInfoVO fileInfoVO){
		
		int cnt = orgCommonMapper.getFile5(fileInfoVO);
		
		return cnt;
	}
	
	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public int getFile6(FileInfoVO fileInfoVO){
		
		int cnt = orgCommonMapper.getFile6(fileInfoVO);
		
		return cnt;
	} 
	
	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> getFile7(FileInfoVO fileInfoVO){
		
		
		List<?> fileInfoVOs = new ArrayList<FileInfoVO>();
		
		fileInfoVOs = orgCommonMapper.getFile7(fileInfoVO);
		
		return fileInfoVOs;
	}	
	
	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public String getFileNm8(String orgnId){
		
		
		String returnFileId = orgCommonMapper.getFileNm8(orgnId);
		
		return returnFileId;
	}  
	
	/**
	 * @Description : 녹취 파일 시퀸스를 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public String getRecSeq(String orgnId){
		
		
		String returnReqSeq = orgCommonMapper.getRecSeq(orgnId);
		
		return returnReqSeq;
	}

	/**
	 * @Description : 첨부 파일 명을 찾아온다.
	 * @Param  : FileInfoVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> getRequestKey(FileInfoVO fileInfoVO){
		
		
		List<?> fileInfoVOs = new ArrayList<FileInfoVO>();
		
		fileInfoVOs = orgCommonMapper.getRequestKey(fileInfoVO);
		
		return fileInfoVOs;
	} 

    /**
     * @Description : 파일 정보를 변경한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFile23(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.updateFile23(fileInfoVO);
		
        return resultCnt;
    } 
    
    /**
     * @Description : 파일 정보를 변경한다.
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateFile24(FileInfoVO fileInfoVO){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = orgCommonMapper.updateFile24(fileInfoVO);
		
        return resultCnt;
    } 
    
    /**
     * @Description : 녹취여부 Y인값 찾기
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int getRequestKeyCnt(String orgnId){
    	
		int resultCnt = orgCommonMapper.getRequestKeyCnt(orgnId);
		
        return resultCnt;
    } 
    
    /**
     * @Description : 녹취여부 N값으로 업데이트
     * @Param  : FileInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int getRequestKeyUpdate(String orgnId){
    	
		int resultCnt = orgCommonMapper.getRequestKeyUpdate(orgnId);
		
        return resultCnt;
    }
	
}
