package com.ktis.msp.org.rqstmgmt.service;

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

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.rqstmgmt.mapper.RqstMgmtMapper;
import com.ktis.msp.org.rqstmgmt.vo.RqstMgmtVo;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : RqstMgmtService
 * @Description : 청구 Service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Service
public class RqstMgmtService extends BaseService {

	@Autowired
	private RqstMgmtMapper rqstMgmtMapper;
	
    @Autowired
    protected EgovPropertyService propertyService;
	
	@Autowired
	private RcpEncService encSvc;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
    
	public RqstMgmtService() {
		setLogPrefix("[RqstMgmtService] ");
	}
	
	/**
	 * @Description : 청구 리스트를 조회 한다.
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> selectRqstMgmtList(RqstMgmtVo rqstMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + rqstMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> rqstMgmtVoList = new ArrayList<RqstMgmtVo>();
		
		// 계약번호로 서비스계약번호 추출
		if(rqstMgmtVo.getContractNum() != null && !"".equals(rqstMgmtVo.getContractNum())){
			String pSvcCntrNo = rqstMgmtMapper.getpSvcCntrNo(rqstMgmtVo.getContractNum());	
			
			if(pSvcCntrNo != null && !"".equals(pSvcCntrNo)){
				//v2018.11 PMD 적용 소스 수정
			   //pSvcCntrNo = "'" + pSvcCntrNo + "'";
//	            StringBuilder strBld = new StringBuilder("");
//	            strBld.append("'");
//	            strBld.append(pSvcCntrNo);
//	            strBld.append("'");
//	            pSvcCntrNo = strBld.toString();
//            
//				rqstMgmtVo.setContractNum(pSvcCntrNo);
				
				//소스 코드 취약점 관련 수정 ($변수 사용제한)
				rqstMgmtVo.setSvcCntrNoYn("Y");
			}else{
//				rqstMgmtVo.setContractNum("'" + rqstMgmtVo.getContractNum() + "'");
				rqstMgmtVo.setSvcCntrNoYn("N");
			}
		}
		
		rqstMgmtVoList = rqstMgmtMapper.selectRqstMgmtList(rqstMgmtVo);
		
		return rqstMgmtVoList;
	}
	
	/**
	 * @Description : 청구 리스트를 조회 한다. 엑셀용
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> selectRqstMgmtListEx(RqstMgmtVo rqstMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + rqstMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> rqstMgmtVoList = new ArrayList<RqstMgmtVo>();
		
		// 계약번호로 서비스계약번호 추출
		if(rqstMgmtVo.getContractNum() != null && !"".equals(rqstMgmtVo.getContractNum())){
			String pSvcCntrNo = rqstMgmtMapper.getpSvcCntrNo(rqstMgmtVo.getContractNum());	
			
			if(pSvcCntrNo != null && !"".equals(pSvcCntrNo)){
				//v2018.11 PMD 적용 소스 수정
			   //pSvcCntrNo = "'" + pSvcCntrNo + "'";
//				StringBuilder strBld = new StringBuilder("");
//				strBld.append("'");
//				strBld.append(pSvcCntrNo);
//				strBld.append("'");
//				pSvcCntrNo = strBld.toString();
//				
//				rqstMgmtVo.setContractNum(pSvcCntrNo);
				
				//소스 코드 취약점 관련 수정 ($변수 사용제한)
				rqstMgmtVo.setSvcCntrNoYn("Y");
			}else{
//				rqstMgmtVo.setContractNum("'" + rqstMgmtVo.getContractNum() + "'");
				rqstMgmtVo.setSvcCntrNoYn("N");
			}
		}		
		
		rqstMgmtVoList = rqstMgmtMapper.selectRqstMgmtListEx(rqstMgmtVo);
		
		return rqstMgmtVoList;
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
        
        /** 20230518 PMD 조치 */
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
	 * @Description : 번호이동 리스트를 조회 한다.
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 */
	public List<?> getMnpList(RqstMgmtVo rqstMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용량 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + rqstMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> mnpList = new ArrayList<RqstMgmtVo>();
		
		mnpList = rqstMgmtMapper.getMnpList(rqstMgmtVo);

		return mnpList;
		
	}
	
	
	/**
	 * @Description : 번호이동 리스트를 조회 한다. 엑셀
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 */
	public List<?> getMnpListEx(RqstMgmtVo rqstMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("번호이동 엑셀 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + rqstMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> mnpList = new ArrayList<RqstMgmtVo>();
		
		mnpList = rqstMgmtMapper.getMnpListEx(rqstMgmtVo);

		return mnpList;
		
	}
	
	/**
	 * @Description : 청구상세(TAX&VAT용) 리스트를 조회 한다.
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> selectRqstMgmtDetailList(RqstMgmtVo rqstMgmtVo, Map<String, Object> paramMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구상세 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + rqstMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<EgovMap> list = (List<EgovMap>) rqstMgmtMapper.selectRqstMgmtDetailList(rqstMgmtVo);
		
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		return list;
	}
	
	/**
	 * @Description : 청구상세(TAX&VAT용) 리스트를 조회 한다. 엑셀용
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> selectRqstMgmtDetailListEx(RqstMgmtVo rqstMgmtVo, Map<String, Object> paramMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구상세 엑셀다운로드 START."));
		logger.info(generateLogMsg("================================================================="));
		
		List<EgovMap> list = (List<EgovMap>) rqstMgmtMapper.selectRqstMgmtDetailListEx(rqstMgmtVo);
		
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		return list;
	}
	

}
