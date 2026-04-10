package com.ktis.msp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ktis.msp.base.exception.MvnoErrorException;

public class ExcelFilesUploadUtil {
	
	protected static Logger logger = LogManager.getLogger(ExcelFilesUploadUtil.class);
	
	public static ArrayList<Object> getRegDataFromExcel(String voName, String sFileNm, String[] arrCell) {
		
		return getRegDataFromExcel(voName, sFileNm, arrCell, 1);
	}
	
		
	
	public static ArrayList<Object> getRegDataFromExcel(String voName, String sFileNm, String[] arrCell, int readRowNum) {
		
		String filePath = sFileNm;
		//xls 파일 읽기***************************************************
		
		ArrayList<Object> arrList = new ArrayList<Object>();
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(filePath));
			
			Workbook workBook =null;
			
			if(filePath.endsWith(".xls")){
				workBook = new HSSFWorkbook(fis);
			}else if(filePath.endsWith(".xlsx")){
				 workBook = new XSSFWorkbook(fis);
			}else{
				throw new IllegalAccessError("xls / xlsx 확장자만 읽을 수 있습니다.");
			}
			
			Sheet sheet = null;
			Row row = null;
			Cell cell = null;
			String sCellValue = "";
			int blankCnt = 0;
			
			Class<?> outVo = Class.forName(voName);
			
			// 1 .sheet
			for (int k=0 ; k < 1 ; k++) {
				sheet = workBook.getSheetAt(k);
				
				int rows = sheet.getPhysicalNumberOfRows();
				
				for(int r=readRowNum ; r < rows ; r++) {
					
					row = sheet.getRow(r);
					
					int cells = arrCell.length;
					
					blankCnt = 0;
					
					Object objVo = outVo.newInstance();
					
					for(int c = 0 ; c < cells ; c++) {
						
						cell = row.getCell(c);
						logger.debug("row:[" + r + "] == cells:" + c);
						
						sCellValue = "";
						
						if(cell != null) {
							
							switch(cell.getCellType()) {
							
							case HSSFCell.CELL_TYPE_FORMULA :
								sCellValue = "" + cell.getStringCellValue();
								logger.debug(" FORMULA value c[" + c + "]- value[" + sCellValue + "]");
							break;
							
							case HSSFCell.CELL_TYPE_NUMERIC :
								Double numValue = cell.getNumericCellValue();
								
								sCellValue = "" + numValue.intValue();
								logger.debug(" CELL_TYPE_NUMERICc[" + c + "]- value[" + sCellValue + "]");
							break;
							
							case HSSFCell.CELL_TYPE_STRING :
								sCellValue = "" + cell.getStringCellValue();
								logger.debug(" CELL_TYPE_STRINGc[" + c + "]- value[" + sCellValue + "]");
							break;
							
							case HSSFCell.CELL_TYPE_BLANK :
								sCellValue = "" + cell.getStringCellValue();
								blankCnt++;
								logger.debug(" CELL_TYPE_BLANKc[" + c + "]- value[" + sCellValue + "]");
							break;
							
							case HSSFCell.CELL_TYPE_BOOLEAN :
								sCellValue = "" + cell.getStringCellValue();
								logger.debug(" CELL_TYPE_BOOLEAN c[" + c + "]- value[" + sCellValue + "]");
							break;
							
							case HSSFCell.CELL_TYPE_ERROR :
								logger.debug(" CELL_TYPE_ERROR c[" + c + "]- value[" + sCellValue + "]");
								sCellValue = "" + cell.getErrorCellValue();
							break;
							
							default:
								logger.debug("default");
							}
							
						} else {
							blankCnt++;
						}
						
						String methodNm = "set" + arrCell[c].substring(0,1).toUpperCase() + arrCell[c].substring(1);
						
						Method method = objVo.getClass().getDeclaredMethod(methodNm, new Class[]{String.class});
						
						method.invoke(objVo, sCellValue.trim());
						
					} //cell
					
					if(blankCnt == cells) {
						break;
					}
					
					arrList.add(objVo);
					
				} //row
				
			} // sheet

//			20200512 소스코드점검 수정
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		} catch (IllegalAccessException e) {
//			e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		} catch (InvocationTargetException e) {
//			e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		} catch (InstantiationException e) {
//			e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		} catch (SecurityException e) {
//			e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		} catch(Exception e) {
			throw new MvnoErrorException(e);
		} finally {
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
//					e.printStackTrace();
					//System.out.println("Connection Exception occurred");
					//20210722 pmd소스코드수정
					logger.error("Connection Exception occurred");
				}
			}
		}
		
		return arrList;
	}

}
