package com.ktis.msp.batch.job.cms.cmsnMgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cms.cmsnMgmt.mapper.CmsnMgmtMapper;
import com.ktis.msp.batch.job.cms.cmsnMgmt.vo.CmsnMgmtVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpListVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.excel.ExcelDataListResultHandler;
import com.ktis.msp.batch.manager.common.excel.ExcelWriter;
import com.ktis.msp.batch.manager.common.excel.SfExcelAttribute;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.common.masking.MaskingService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class CmsnMgmtService extends BaseService {
	
	@Autowired
	private CmsnMgmtMapper cmsnMgmtMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
    private FileDownService fileDownService;
	
	private static final String XML_ENCODING = "UTF-8";
	
	/**
	 * @Description : Grade관리수수료 엑셀 다운로드
	 * @Param  : 
	 * @Return : 
	 * @Author : 정하영
	 * @Create Date : 2017. 03. 08.
	 */
	public void getGrdCmsnPrvdListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		String [] strHead = {"대리점ID", "대리점", "수수료명", "수수료정책", "계약번호", "대리점유형", "대리점유형상세", "채널상세", "정책시작일자", "정책종료일자", "계약상태", "지급건수", "수수료율(%)", "지급여부", "수납금액", "수수료"};
		String [] strValue = {"agncyCd", "agncyNm", "cmsnNm", "cmsnPlcyNm", "srvcCntrNum", "typeDtlNm1", "typeDtlNm2", "typeDtlNm3", "applStrtDt", "applEndDt", "sbscrbStat", "mgmtCnt", "cmsnRateAmt", "prvdYn", "pymnAmt", "lastCmsn"};
		//엑셀 컬럼 사이즈
		int[] intWidth = {5000,10000,5000,10000,5000,5000,5000,5000,5000,5000,5000,5000,5000,3000,3000,3000};
		int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1};
		
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			CmsnMgmtVO searchVO = (CmsnMgmtVO) JacksonUtil.makeVOFromJson(execParam, CmsnMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "Grd관리수수료_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "Grd관리수수료";
			
			List<?> voLists = cmsnMgmtMapper.getGrdCmsnPrvdListExcel(searchVO);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);
			
			String fileNm = null;
			
			fileNm = fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			
			File file = new File(fileNm);
			
			Map<String, Object> pRequestParamMap = new HashMap<String, Object>();
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(searchVO.getDwnldRsn())){
			
				pRequestParamMap.put("FILE_NM"   , file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" , file.getPath());				//파일경로
				pRequestParamMap.put("FILE_SIZE" , (int) file.length());		//파일크기
				pRequestParamMap.put("IP_INFO"   , searchVO.getIpAddr());		//IP정보
				pRequestParamMap.put("DATA_CNT"  , 0);							//자료건수
				pRequestParamMap.put("DWNLD_RSN", searchVO.getDwnldRsn());		//다운로드사유
				pRequestParamMap.put("EXCL_DNLD_ID", searchVO.getExclDnldId());
				fileDownService.insertCmnFileDnldMgmtMstU(pRequestParamMap);
			}
		} catch(Exception e){
			throw new MvnoServiceException("EBND1005", e);
		}
	}
	
	/**
	 * @Description : 대리점수수료상세내역 엑셀 다운로드
	 * @Param  : 
	 * @Return : 
	 * @Author : 정하영
	 * @Create Date : 2017. 03. 08.
	 */
	public void getCmsnPrvdListExcel(BatchCommonVO batchCommonVO, String strTmp) throws MvnoServiceException {
		
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		
		String[] strHead = {"대리점명","수수료명","수수료정책명","계약번호","수수료","조정금액","환수금액","지급수수료","조정사유"};
		String[] strValue = {"saleStoreNm","cmsnNm","cmsnPlcyNm","srvcCntrNum","cmsn","adjAmt","clwbckAmt","lastCmsn","rsn"};

		//엑셀 컬럼 사이즈
		int[] intWidth = {5000, 7000, 7000, 5000, 5000, 4500, 4500, 4500, 8000};
		int[] intLen = {0, 0, 0, 0, 1, 1, 1, 1, 0};
		
		try {
			
			String execParam = batchCommonVO.getExecParam();
			CmsnMgmtVO searchVO = (CmsnMgmtVO) JacksonUtil.makeVOFromJson(execParam, CmsnMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "대리점수수료상세내역_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "대리점수수료상세내역";
			
			List<?> voLists = cmsnMgmtMapper.getCmsnPrvdListExcel(searchVO);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", searchVO.getUserId());
			voLists = getMaskingData(voLists, paramMap);
			
			String fileNm = null;
			
			fileNm = fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			
			File file = new File(fileNm);
			
			Map<String, Object> pRequestParamMap = new HashMap<String, Object>();
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(searchVO.getDwnldRsn())){
			
				pRequestParamMap.put("FILE_NM"   , file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" , file.getPath());				//파일경로
				pRequestParamMap.put("FILE_SIZE" , (int) file.length());		//파일크기
				pRequestParamMap.put("IP_INFO"   , searchVO.getIpAddr());		//IP정보
				pRequestParamMap.put("DATA_CNT"  , 0);							//자료건수
				pRequestParamMap.put("DWNLD_RSN", searchVO.getDwnldRsn());		//다운로드사유
				pRequestParamMap.put("EXCL_DNLD_ID", searchVO.getExclDnldId());
				fileDownService.insertCmnFileDnldMgmtMstU(pRequestParamMap);
			}
			
			
			
			
		} catch(Exception e){
			throw new MvnoServiceException("EBND1005", e);
		}
	}
	
	
//	/**
//	 * @Description : 판매_ARPU수수료내역 엑셀 다운로드
//	 * @Param  : 
//	 * @Return : 
//	 * @Author : 정하영
//	 * @Create Date : 2017. 03. 15.
//	 */
//	public void getCalcCrebasisListExcel(BatchCommonVO batchCommonVO, String a) throws MvnoServiceException {
//		
//		//----------------------------------------------------------------
//		// 목록 db select
//		//----------------------------------------------------------------
//		String [] strHead = {"기준월", "판매대리점", "계약번호", "전화번호", "가입상태", "가입유형", "개통일자", "해지일자", "기변일자", "이동세대구분", "모델ID", "모델명", "모델일련번호", "신청서접수일시", "판매정책명", "개통요금제명", "개통요금제그룹", "현재요금제명", "현재요금제그룹"
//				           , "약정개월수", "판매수수료","ARPU수수료","최초개통점", "해지일자(현재)", "해지사유(현재)"};
//		String [] strValue = {"stdrMon", "saleStoreNm", "contractNum", "telnum", "sbscrbStatNm", "sbscrbTypeNm", "openDt", "tmntDt", "hndstChngDt", "g3IndNm", "hndstId", "prdtCode","hndstSrlNum", "reqRcptDate", "salePlcyNm", "fChrgPlanNm", "fRateGrpCd", "cChrgPlanNm", "cRateGrpCd"
//				           , "agrmNomCnt", "saleAmnt", "arpuAmnt", "openAgntNm", "canDt", "bckTrgt"};
//		//엑셀 컬럼 사이즈
//		int[] intWidth = {3000,10000,3000,3000,3000,3000,3000,3000,3000,3000,3000,5000,5000,5000,10000,5000,3000,5000,3000,3000,3000,3000,10000,5000,10000};
//		int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//				        1,1,1,0,0,0};
//		
//		try {
//			
//			String execParam = batchCommonVO.getExecParam();
//			CmsnMgmtVO searchVO = (CmsnMgmtVO) JacksonUtil.makeVOFromJson(execParam, CmsnMgmtVO.class);
//			
//			String serverInfo = propertiesService.getString("excelPath");
//			String fileName = serverInfo + "판매_ARPU수수료내역_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
//			String sheetName = "판매_ARPU수수료내역";
//			
//			List<?> voLists = cmsnMgmtMapper.getCalcCrebasisListExcel(searchVO);
//			
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("userId", searchVO.getUserId());
//			voLists = getMaskingData(voLists, paramMap);
//			
//			String fileNm = null;
//			
//			fileNm = fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists, strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
//			
//			File file = new File(fileNm);
//			
//			Map<String, Object> pRequestParamMap = new HashMap<String, Object>();
//			
//			//=======파일다운로드사유 로그 START==========================================================
//			if(KtisUtil.isNotEmpty(searchVO.getDwnldRsn())){
//			
//				pRequestParamMap.put("FILE_NM"   , file.getName());				//파일명
//				pRequestParamMap.put("FILE_ROUT" , file.getPath());				//파일경로
//				pRequestParamMap.put("FILE_SIZE" , (int) file.length());		//파일크기
//				pRequestParamMap.put("IP_INFO"   , searchVO.getIpAddr());		//IP정보
//				pRequestParamMap.put("DATA_CNT"  , 0);							//자료건수
//				pRequestParamMap.put("DWNLD_RSN", searchVO.getDwnldRsn());		//다운로드사유
//				pRequestParamMap.put("EXCL_DNLD_ID", searchVO.getExclDnldId());
//				fileDownService.insertCmnFileDnldMgmtMstU(pRequestParamMap);
//			}
//			
//			
//		} catch(Exception e){
//			throw new MvnoServiceException("EBND1005", e);
//		}
//	}
	
	
	
	// 주소,이름,전화번호를 마스킹처리한다.
	public List<?> getMaskingData(List<?> result, Map<String, Object> paramMap) {
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
}
