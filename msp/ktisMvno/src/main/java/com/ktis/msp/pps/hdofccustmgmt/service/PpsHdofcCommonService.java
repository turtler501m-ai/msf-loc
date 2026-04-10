package com.ktis.msp.pps.hdofccustmgmt.service;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.pps.hdofccustmgmt.mapper.PpsHdofcCommonMapper;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsKtInResVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsRcgRealCmsVo;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class PpsHdofcCommonService  extends BaseService {
	
	@Autowired
	  protected EgovPropertyService propertyService;
	
	@Autowired
	private PpsHdofcCommonMapper  hdofcCommonMapper;
	
	/**
	 * 고객 잔액갱신
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	//@Transactional(rollbackFor=Exception.class)
	public PpsKtInResVo ppsHdofcSleep(Map<String, Object> pRequestParamMap) {
		PpsKtInResVo ppsKtInResVo = new PpsKtInResVo();
		int interval = 20;
		int sleep = 2000;
		
		if(pRequestParamMap.get("oRetCd").toString().equals("0000")){
						
			for(int i=0 ; i<interval ; i++){
				//int count = hdofcCommonMapper.getPpsKtInResCnt(pRequestParamMap);
				Date toDay = new Date();
		        String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
		        
		        pRequestParamMap.put("cntDate", strToDay);
				
				hdofcCommonMapper.getPpsKtInResCntProc(pRequestParamMap);
				
				logger.debug("count="+pRequestParamMap.get("oCnt"));
				if(Integer.valueOf(pRequestParamMap.get("oCnt").toString()) > 0){
					
					ppsKtInResVo = hdofcCommonMapper.getPpsKtInResInfo(pRequestParamMap);
					
					hdofcCommonMapper.deletePpsKtInRes(pRequestParamMap);	
					logger.debug("oResCode="+ppsKtInResVo.getoResCode());	
					return ppsKtInResVo;
					
				}else{
					try {
						Thread.sleep(sleep);
					} catch (InterruptedException e) {
//						20200512 소스코드점검 수정
//				    	e.printStackTrace();
//						20210706 소스코드점검 수정
//						System.out.println("Connection Exception occurred");
						logger.error("Connection Exception occurred");
					}
				}
			}	// end for
			
			/************ TIME OUT ************/
			ppsKtInResVo.setoResCode("-999"); 
			ppsKtInResVo.setoResCodeNm("서버응답 시간초과[Time Out]");
			ppsKtInResVo.setoRemains(0);
			ppsKtInResVo.setoDataVaRemains("0");

			return ppsKtInResVo; 
			
		}else{
			ppsKtInResVo.setoResCode(pRequestParamMap.get("oRetCd").toString()); 
			ppsKtInResVo.setoResCodeNm(pRequestParamMap.get("oRetMsg").toString());
			ppsKtInResVo.setoRemains(0);
			ppsKtInResVo.setoDataVaRemains("0");
			
			return ppsKtInResVo;
		}	// end if				
	}
	
	/**
	 * kt충전을 위한 seq를 받는다.
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public String getPpsKtSeq() {	

		String seq = hdofcCommonMapper.getPpsKtSeq();
		return seq;
	}
	
	/**
	 * 대리점명 조회해서 대리점콤보박스 옵션목록 가져오기 
	 * @param pRequestParamMap
	 * @return
	 */
	public List<?> getSelectPpsAgentInfoDataList(Map<String, Object> pRequestParamMap){
		
		List<?> resultList = new ArrayList<Map<String, Object>>();
		   
		   resultList = hdofcCommonMapper.getSelectPpsAgentInfoDataList(pRequestParamMap);
		
		
		return resultList;
	}
	
	/**
	 * CMS은행 리스트 
	 * @param pRequestParamMap
	 * @return
	 */
	public List<?> getCmsBankList(Map<String, Object> pRequestParamMap){
		
		List<?> resultList = new ArrayList<PpsRcgRealCmsVo>();
	    resultList = hdofcCommonMapper.getCmsBankList(pRequestParamMap);
	    logger.debug(resultList.get(0));
		return resultList;
	}
	
	/**
	 * 판매점 select 리스트
	 * @param pRequestParamMap
	 * @return
	 */
	public List<?> ppsAgentSaleSelect(Map<String, Object> pRequestParamMap){
		
		List<?>resultList = hdofcCommonMapper.ppsAgentSaleSelect(pRequestParamMap);
	    logger.debug(resultList.get(0));
		return resultList;
	}	
	
	
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
          
        // 줄 바꿈 
        cellStyleHead.setWrapText(true); 
          
        HSSFFont fontHead = xlsWb.createFont();
         short s1 =1;
         short s2 =2;
         short s3 =3;
         short s10=10;
         short s12=12;
                    
        fontHead.setBoldweight(s1);
        fontHead.setFontName("맑은 고딕");
        fontHead.setColor(HSSFColor.BLACK.index);
        
        fontHead.setFontHeightInPoints(s12);
        
        HSSFFont fontMid = xlsWb.createFont();
        fontMid.setBoldweight(s1);
        fontMid.setFontName("맑은 고딕");
        fontMid.setColor(HSSFColor.BLACK.index);
        
        fontMid.setFontHeightInPoints(s10);
        
        //제목
//        cellStyle.setFillForegroundColor(HSSFColor.LIME.index); 
        
        cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyleHead.setAlignment(s2);
        cellStyleHead.setFillPattern(s1);
        cellStyleHead.setFillPattern(s1);
        cellStyleHead.setBorderRight(s1);
//        cellStyleHead.setBorderLeft((short) 1);
        cellStyleHead.setBorderTop(s1);
        cellStyleHead.setBorderBottom(s1);
        cellStyleHead.setFont(fontHead);
        
        //내용
        cellStyleMid.setAlignment(s2);
        cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        cellStyleMid.setFillPattern(s1);
        cellStyleMid.setBorderRight(s1);
        cellStyleMid.setBorderLeft(s1);
        cellStyleMid.setBorderTop(s1);
        cellStyleMid.setBorderBottom(s1);
        cellStyleMid.setFont(fontMid);
        
        //숫자용
        HSSFDataFormat format = xlsWb.createDataFormat();
        
        cellStyleInt.setAlignment(s3);
        cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        cellStyleInt.setFillPattern(s1);
        cellStyleInt.setBorderRight(s1);
        cellStyleInt.setBorderLeft(s1);
        cellStyleInt.setBorderTop(s1);
        cellStyleInt.setBorderBottom(s1);
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
				//logger.debug("["+i+"] ["+j+"]"+ map.get(strValue[j]).toString());
				
				if(intLen[j] == 1)
				{
					double d = Double.parseDouble(map.get(strValue[j]).toString());
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cell.setCellValue(d);
					cell.setCellStyle(cellStyleInt);
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
				// TODO Auto-generated catch block
//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
//				20210706 소스코드점검 수정
//				System.out.println("Connection Exception occurred");
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
	 * 선불 공통코드 select박스 리스트 
	 * @param pRequestParamMap
	 * @return
	 */
		public List<?> getppsKtCommonCdList(Map<String, Object> pRequestParamMap){
		
		List<?>resultList = hdofcCommonMapper.getppsKtCommonCdList(pRequestParamMap);
	   
		return resultList;
	}	
		
		
	/**
	 *  선불  콤보코드 select 리스트 
	 * @param pRequestParamMap
	 * @return
	 */
	public 	List<?> getPpsCodeComboList (Map<String, Object> pRequestParamMap){
		List<?>resultList = hdofcCommonMapper.getPpsCodeComboList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 *  선불 설명 리스트 
	 * @param pRequestParamMap
	 * @return
	 */
	public 	List<?> getPpsCodeDescInfoList (Map<String, Object> pRequestParamMap){
		List<?>resultList = hdofcCommonMapper.getPpsCodeDescInfoList(pRequestParamMap);
		
		return resultList;
	}
		
		

}
