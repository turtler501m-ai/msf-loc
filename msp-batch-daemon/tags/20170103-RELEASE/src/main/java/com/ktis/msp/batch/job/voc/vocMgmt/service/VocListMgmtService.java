package com.ktis.msp.batch.job.voc.vocMgmt.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.voc.vocMgmt.mapper.VocListMgmtMapper;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.VocListMgmtVO;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.VocListNotEgovMapVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class VocListMgmtService extends BaseSchedule {
	
	@Autowired
	private VocListMgmtMapper vocListMgmtMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService fileDownService;
	
	/**
	 * 상담목록 엑셀 다운로드
	 * @param batchCommonVO
	 * @throws MvnoServiceException
	 */
	public void selectVocListExcel(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		//----------------------------------------------------------------
		// 목록 db select
		//----------------------------------------------------------------
		
		String [] strHead = {	"접수ID","고객CTN","고객명","서비스구분","상품구분","고객ID","계약ID","고객여부","고객관계",
								"요청고객명","요청CTN","접수부서ID","접수부서","접수자ID","접수자","접수일시",
								"대분류","중분류","소분류","상담상태","처리부서ID","처리부서","처리자ID","처리자","처리일시",
								"완료부서ID","완료부서","완료자ID","완료자","완료일시","소요시간(분)","요청내용","응대내용","처리내용"};
		
		String [] strValue = {	"rcpId","telNum","custNm","pppo","prodTypeNm","customerId","contractNum","custYnNm","custRelNm",
								"reqCustNm","reqTelNum","reqOrgnId","reqOrgnNm","reqrId","reqrNm","reqDttm",
								"cnslMstNm","cnslMidNm","cnslDtlNm","cnslStatNm","procOrgnId","procOrgnNm","procrId","procrNm","procDttm",
								"cmplOrgnId","cmplOrgnNm","cmplrId","cmplrNm","cmplDttm","procMm","reqCntt","resCntt","procCntt"};
		
		int[] intWidth = {5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,10000,10000,10000};
		int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0};
		
		try {
			String execParam = batchCommonVO.getExecParam();
			VocListMgmtVO searchVO = (VocListMgmtVO) JacksonUtil.makeVOFromJson(execParam, VocListMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "상담목록_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "상담목록";
			
			List<VocListNotEgovMapVO> voLists = vocListMgmtMapper.selectVocListExcel(searchVO);
			
			fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists.iterator(), strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
		} catch(Exception e){
			throw new MvnoServiceException("EVOC1001", e);
		}
	}
}
