package com.ktis.msp.batch.job.voc.vocMgmt.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.crypto.base.KeyInfo;
import com.ktds.crypto.decryptor.aes.AES256Decryptor;
import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.openmgmt.vo.OpenMgmtListVO;
import com.ktis.msp.batch.job.voc.vocMgmt.mapper.VocListMgmtMapper;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.VocListMgmtVO;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.VocListNotEgovMapVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.common.filedown.FileDownService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.util.StringUtil;

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
		
		String [] strHead = {	"접수ID","고객CTN","고객명","서비스구분","상품구분","고객ID","계약ID","고객여부","고객관계","요청고객명",
								"요청CTN","접수부서ID","접수부서","접수자ID","접수자","접수일시","대분류","중분류","소분류","상담상태",
								"처리부서ID","처리부서","처리자ID","처리자","처리일시","완료부서ID","완료부서","완료자ID","완료자","완료일시",
								"소요시간(분)","잔여시간(분)","요청내용","응대내용","처리내용","나이(만)","사용중인요금제","가입대리점"};
		
		String [] strValue = {	"rcpId","telNum","custNm","pppo","prodTypeNm","customerId","contractNum","custYnNm","custRelNm","reqCustNm",
								"reqTelNum","reqOrgnId","reqOrgnNm","reqrId","reqrNm","reqDttm","cnslMstNm","cnslMidNm","cnslDtlNm","cnslStatNm",
								"procOrgnId","procOrgnNm","procrId","procrNm","procDttm","cmplOrgnId","cmplOrgnNm","cmplrId","cmplrNm","cmplDttm",
								"procMm","remainMm","reqCntt","resCntt","procCntt","custAge","lstRateNm","orgnNm"};
		
		int[] intWidth = {5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,
						5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,
						5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,
						5000,5000,10000,10000,10000,3000,5000,5000};
		int[] intLen = {0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,
						0,0,0,0,0,0,0,0,0,0,
						1,0,0,0,0,0,0,0};
		
		try {
			String execParam = batchCommonVO.getExecParam();
			VocListMgmtVO searchVO = (VocListMgmtVO) JacksonUtil.makeVOFromJson(execParam, VocListMgmtVO.class);
			
			String serverInfo = propertiesService.getString("excelPath");
			String fileName = serverInfo + "상담목록_" + searchVO.getUserId() + "_" + batchCommonVO.getBatchReqId() + "_";
			String sheetName = "상담목록";

			List<VocListNotEgovMapVO> voLists = vocListMgmtMapper.selectVocListExcel(searchVO);
			LOGGER.info("\tBATCH EXCEL DOWN : 복호화 시작");
			com.ktds.crypto.decryptor.aes.AES256Decryptor svc = new AES256Decryptor();
			KeyInfo keyinfo = new KeyInfo();
			keyinfo.setName("DBMS-AES256-Key");
			keyinfo.setDefaultEncoding("UTF-8");
			keyinfo.setKeyString("S3QxMTQ2NTQ1MDExNElTMzc3NjQ5NDkwMTBNb2JpbGU=");
			keyinfo.setEnableSalt(true);
			keyinfo.setSaltString("AAAAAAAAAAAAAAAAAAAAAA==");

			svc.setKeyInfo(keyinfo);

			for(int i = 0; i<voLists.size(); i++){
				if(voLists.get(i).getSsn() == null || "".equals(voLists.get(i).getSsn())){
					voLists.get(i).setSsn("-");	
				}else{
					String dscryptingSsn = svc.decrypting(voLists.get(i).getSsn());
					voLists.get(i).setSsn(dscryptingSsn);
				}
			}

			for(VocListNotEgovMapVO vocListNotEgovMapVO : voLists){	
				if("JP".equals(vocListNotEgovMapVO.getCstmrType()) || "PP".equals(vocListNotEgovMapVO.getCstmrType())
						|| "".equals(vocListNotEgovMapVO.getCstmrType()) || vocListNotEgovMapVO.getCstmrType() == null){
					vocListNotEgovMapVO.setCustAge("-");
				}else{
					vocListNotEgovMapVO.setCustAge(String.valueOf(StringUtil.getAge(vocListNotEgovMapVO.getSsn())));
				}
				
				// 개인정보제거
				vocListNotEgovMapVO.setSsn("");
			}
			
			String fileNm = null;

			LOGGER.info("\tBATCH EXCEL DOWN : 파일 생성 시작(" + voLists.size() + ")건");

			if(voLists.size() <= 10000) {
				fileNm = fileDownService.saveBigExcel(searchVO.getUserId(), fileName, sheetName, voLists.iterator(), strHead, intWidth, strValue, intLen, batchCommonVO.getReqDttm());
			} else {
				fileNm = fileDownService.csvDownProcStream(fileName, voLists.iterator(), strHead, strValue, searchVO.getUserId(), batchCommonVO.getReqDttm());
			}
			File file = new File(fileNm);
			Map<String, Object> pRequestParamMap = new HashMap<String, Object>();
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(searchVO.getDwnldRsn())){
			
				pRequestParamMap.put("FILE_NM"   , file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" , file.getPath());				//파일경로
				pRequestParamMap.put("DUTY_NM"   , "VOC");						//업무명
				pRequestParamMap.put("IP_INFO"   , searchVO.getIpAddr());		//IP정보
				pRequestParamMap.put("FILE_SIZE" , (int) file.length());		//파일크기
				pRequestParamMap.put("menuId"	 , searchVO.getMenuId());		//메뉴ID
				pRequestParamMap.put("DATA_CNT"  , 0);							//자료건수
				pRequestParamMap.put("SESSION_USER_ID", searchVO.getUserId());	//사용자ID
				pRequestParamMap.put("DWNLD_RSN", searchVO.getDwnldRsn());		//다운로드사유
				pRequestParamMap.put("EXCL_DNLD_ID", Integer.parseInt(searchVO.getExclDnldId()));
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			LOGGER.info("BATCH EXCEL DOWN END");
			
		} catch(Exception e){
			LOGGER.error(StringUtil.getPrintStackTrace(e));
			throw new MvnoServiceException("EVOC1001", e);
		}
	}
}
