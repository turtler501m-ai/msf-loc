package com.ktis.msp.batch.job.org.orgmgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cls.ClsAcntConfig;
import com.ktis.msp.batch.job.org.orgmgmt.mapper.OrgnAgenCnptDataMapper;
import com.ktis.msp.batch.job.rsk.statmgmt.mapper.StatMgmtMapper;
import com.ktis.msp.batch.job.rsk.statmgmt.vo.StatMgmtVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler3;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class OrgnAgenCnptDataService extends BaseService {
	
	@Autowired
	private OrgnAgenCnptDataMapper orgnAgenCnptMapper;
	
	
	/**
	 * @throws MvnoServiceException 
	 * M유통 대리점 조직연동 프로시저호출
	 * @param vo
	 * @throws 
	 */
//	@Transactional(rollbackFor=Exception.class)
	public void callOrgnAgenData(Map<String, Object> param) throws MvnoServiceException{
		
		try{
				
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				LOGGER.info("☆☆☆☆☆☆☆☆callOrgnAgenData 프로시저시작☆☆☆☆☆☆☆☆ : ");
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				orgnAgenCnptMapper.callOrgnAgenData(param);
				
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * @throws MvnoServiceException 
	 * M유통 판매점 조직연동 프로시저호출
	 * @param vo
	 * @throws 
	 */
//	@Transactional(rollbackFor=Exception.class)
	public void callOrgnCnptData(Map<String, Object> param) throws MvnoServiceException{
		
		try{
				
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				LOGGER.info("☆☆☆☆☆☆☆☆callOrgnCnptData 프로시저시작☆☆☆☆☆☆☆☆ : ");
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				orgnAgenCnptMapper.callOrgnCnptData(param);
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws MvnoServiceException 
	 * M유통 판매점 조직연동 프로시저호출
	 * @param vo
	 * @throws 
	 */
//	@Transactional(rollbackFor=Exception.class)
	public void deleteMdsOrgnData(Map<String, Object> param) throws MvnoServiceException{
		
		try{
				
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				LOGGER.info("☆☆☆☆☆☆☆☆deleteMdsOrgnData 서비스 시작☆☆☆☆☆☆☆☆ : ");
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				orgnAgenCnptMapper.deleteOrgAgenData(param);
				orgnAgenCnptMapper.deleteOrgCpntData(param);
				orgnAgenCnptMapper.deleteOrgRelData(param);
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
}
