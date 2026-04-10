package com.ktis.msp.base.mvc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.dao.ExcelResultHandler;
import com.ktis.msp.base.dao.MvnoExcelResultHandler;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.excel.ExcelWriter;
import com.ktis.msp.base.exception.MvnoErrorException;

public class ExcelAwareService extends BaseService {
	
	@Autowired
	SqlSession sqlSession;
	
	//v2018.11 PMD 적용 소스 수정
/*	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	private PpsHdofcRcgMgmtMapper rcgMgmtMapper;*/
	
	protected String doDownload(HttpServletResponse response, File file, String fileName) {
    	
        return doDownload(response, file, fileName, true);
    }
	
	protected String doDownload(HttpServletResponse response, File file, String fileName, boolean deleteFlag) {
    	
    	String encodingFileName = fileName;
		try {
			encodingFileName = new String(fileName.getBytes("EUC-KR"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
//			20200512 소스코드점검 수정
//	    	e1.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		}  
           
        response.setHeader("Cache-Control", "");
        response.setHeader("Pragma", "");

        response.setContentType("Content-type:application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        
    	BufferedInputStream 	bis = null;
        BufferedOutputStream 	bos = null;
        
        try {
        	      
    		bis = new BufferedInputStream(new FileInputStream(file));
    		bos = new BufferedOutputStream(response.getOutputStream());
    	        
    		byte[] buf = new byte[2048];
    		int read = 0;
    		while ((read = bis.read(buf)) != -1) {
    			bos.write(buf, 0, read);
    		}
    		bos.flush();    
    	} catch (IOException e) {
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
			//System.out.println("Connection Exception occurred");
    		//20210722 pmd소스코드수정
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
            
            if(deleteFlag) file.delete();
    	}
        return fileName;
    }
	
	public File makeBigDataExcel(String sqlId, Object sqlParam , ExcelParam excelParam, ExcelResultHandler handler) throws IOException {
    	
    	XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(excelParam.getSheetName());
        
        Map<String, XSSFCellStyle> wbStyle = createCellStyles(wb);
        String sheetRef = sheet.getPackagePart().getPartName().getName();
        
        //local path
        File dirTarget 		= new File(excelParam.getExcelPath());
        File dirTemp 		= new File(excelParam.getExcelPath());
        
        File fTarget 	= null;
        File fTemp 		= null;
        File fXml 		= null;
        
        
        if(!dirTarget.isDirectory()) dirTarget.mkdir();
        
        if(!dirTemp.isDirectory()) 	dirTemp.mkdir();
        
        FileOutputStream fosTemp = null;
        
        try {
        	String tempFileName = System.currentTimeMillis() + "";
        	
        	fTarget = File.createTempFile(excelParam.getFileName(), ".xlsx", dirTarget);
        	fTemp = File.createTempFile("Excel", ".tmp", dirTemp);
        	
        	fosTemp = new FileOutputStream(fTemp);
        	wb.write(fosTemp);
        	
        	fXml = File.createTempFile(tempFileName, ".xml", dirTemp);        
		} catch (IOException e1) {
			
//			20200512 소스코드점검 수정
//	    	e1.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		} finally {
			if(fosTemp != null)
                fosTemp.close();
        }
        
        Writer writer = null;
        
		try {
			writer = new OutputStreamWriter(new FileOutputStream(fXml), "UTF-8");
			ExcelWriter writerExcel = new ExcelWriter(writer);
			
			writerExcel.beginSheet();
			
			
			handler.init(writerExcel, wbStyle, excelParam);
			
			sqlSession.select(sqlId, sqlParam, handler);
			
	        writerExcel.endSheet();
		} finally{
            if(writer != null) writer.close();
        }
		
		FileOutputStream out = null;
        
        try{
            out = new FileOutputStream(fTarget);
            Map sheetMap = new HashMap();
            sheetMap.put(sheetRef.substring(1), fXml);
            substitute(fTemp, sheetMap, out);
        } finally{
            if (out != null) {
                out.close();
            }
            if (fTemp != null) {
                fTemp.delete();
            }
            if (fXml != null) {
                fXml.delete();
            }
        }
        
        return fTarget;
        //return doExcelDownload(response, makeFileName(fileName), workBook, Integer.parseInt(propertyService.getString("excelPathLen")));
	}
	
	private void substitute(File zipfile, Map sheetMap, OutputStream out)
		throws IOException {
		
		/** 20230518 PMD 조치 */
		ZipFile zip = null;
		ZipOutputStream zos = new ZipOutputStream(out);
		try {
			zip = new ZipFile(zipfile);
			Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
			while (en.hasMoreElements()) {
				ZipEntry ze = en.nextElement();
				if (!sheetMap.containsKey(ze.getName())) {
					zos.putNextEntry(new ZipEntry(ze.getName()));
					InputStream is = null;
					try {
						is = zip.getInputStream(ze);
						copyStream(is, zos);
					}catch(Exception e) {
					    throw new MvnoErrorException(e);
					}finally {
						if(is != null){
							is.close();
						}						
					}
				}
			}
		}catch(Exception e) {
		    throw new MvnoErrorException(e);			
		}finally {
			if(zip != null){
				zip.close();
			}
		}

		Iterator it = sheetMap.keySet().iterator();
		while (it.hasNext()) {
			String entry = (String) it.next();
			//System.out.println(entry);
			zos.putNextEntry(new ZipEntry(entry));
			
			InputStream is = null;
			try {
				is = new FileInputStream((File) sheetMap.get(entry));
				copyStream(is, zos);
			} catch(Exception e) {
//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
				//System.out.println("Connection Exception occurred");
				//20210722 pmd소스코드수정
				logger.error("Connection Exception occurred");
			} finally {
				if(is != null){
					is.close();
				}
			}
		}
		zos.close();
	}
		
	private void copyStream(InputStream in, OutputStream out) throws IOException {
		byte[] chunk = new byte[1024];
		int count;
		while ((count = in.read(chunk)) >=0 ) {
			out.write(chunk,0,count);
		}
	}
	
	protected String makeFileName(String strFileName) {
    	StringBuilder sbFileName = new StringBuilder();
		Date toDay = new Date();
        String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
        sbFileName.append(strFileName);
        sbFileName.append(strToDay);
        sbFileName.append(".xls");
        
        return sbFileName.toString();
    }
	
	private Map<String, XSSFCellStyle> createCellStyles(XSSFWorkbook workBook){
		
		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
	       
		XSSFFont headFont = workBook.createFont();
		headFont.setBold(true);
		headFont.setFontName("맑은 고딕");
		headFont.setColor(XSSFFont.COLOR_NORMAL);
		headFont.setFontHeight(10);
	    
		XSSFFont bodyFont = workBook.createFont();
		bodyFont.setBold(false);
		bodyFont.setFontName("맑은 고딕");
		bodyFont.setColor(XSSFFont.COLOR_NORMAL);
		bodyFont.setFontHeight(10);
		
		//v2018.11 PMD 적용 소스 수정
		//XSSFColor gray = new XSSFColor(Color.LIGHT_GRAY);
		
	    XSSFCellStyle headStyle = workBook.createCellStyle();
	    headStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	    headStyle.setWrapText(true);
        headStyle.setFont(headFont);
        
        styles.put("head", headStyle);
	    
        XSSFCellStyle bodyStyle = workBook.createCellStyle();
        bodyStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        bodyStyle.setFont(bodyFont);
	  
	    styles.put("body", bodyStyle);
	    
	    XSSFCellStyle bodyNumericStyle = workBook.createCellStyle();
	    bodyNumericStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	    bodyNumericStyle.setDataFormat(workBook.createDataFormat().getFormat("#,##0"));
	    bodyNumericStyle.setFont(bodyFont);
	    
	    styles.put("bodyNumeric", bodyNumericStyle);
	    
	    return styles;
	}
		
}
