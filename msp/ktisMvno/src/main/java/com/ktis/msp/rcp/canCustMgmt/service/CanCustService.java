package com.ktis.msp.rcp.canCustMgmt.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.canCustMgmt.mapper.CanCustMapper;
import com.ktis.msp.rcp.canCustMgmt.vo.CanBatchVo;
import com.ktis.msp.rcp.canCustMgmt.vo.CanCustVO;
import com.ktis.msp.util.StringUtil;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class Name : CanCustService
 * @Description : CanCust Service
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.08.10  장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2016. 8. 10.
 */
@Service
public class CanCustService extends BaseService {
    
	/** CanCustMapper mapper */
    @Autowired
    private CanCustMapper canCustMapper;
    
    /** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
    
    /** prefix log */
    public CanCustService() {
        setLogPrefix("[CanCustService] ");
    }
    
    /**
     * @Description : 해지 정보를 가져온다.
     * @Param  : CanCustVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    public List<?> selectCanCustList(CanCustVO canCustVO, Map<String, Object> paramMap){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg(" 해지 고객  리스트 조회 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> selectCanCustList = new ArrayList<CanCustVO>();
        selectCanCustList = canCustMapper.selectCanCustList(canCustVO);
		
		maskingService.setMask(selectCanCustList, maskingService.getMaskFields(), paramMap);
		
		return selectCanCustList;
    }
    
    /**
     * @Description : 해지복구 정보를 가져온다.
     * @Param  : CanCustVO
     * @Return : List<?>
     * @Author : 장준화
     * @Create Date : 2019. 3. 26.
     */
    public List<?> selectRclCustList(CanCustVO canCustVO, Map<String, Object> paramMap){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg(" 해지복구 고객  리스트 조회 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> selectRclCustList = new ArrayList<CanCustVO>();
        selectRclCustList = canCustMapper.selectRclCustList(canCustVO);
		
		maskingService.setMask(selectRclCustList, maskingService.getMaskFields(), paramMap);
		
		return selectRclCustList;
    }

    /**
     * @Description 일괄직권해지 목록 조회
     */
    public List<EgovMap> getCanBatchList(CanBatchVo searchVO, Map<String, Object> paramMap) {



        List<EgovMap> list = canCustMapper.getCanBatchList(searchVO);

        HashMap<String, String> maskFields = getMaskFields();
        maskingService.setMask(list, maskFields, paramMap);

        return list;
    }

    /**
     * @Description : 일괄직권해지 목록 엑셀 다운로드
     */
    public List<EgovMap> getCanBatchListExcelDown(CanBatchVo searchVO, Map<String, Object> paramMap) {

        /* 유효성 검사 */
        if(KtisUtil.isEmpty(searchVO.getSearchStartDt()) || KtisUtil.isEmpty(searchVO.getSearchEndDt())) {
            throw new MvnoRunException(-1, "조회일자가 존재하지 않습니다.");
        }

        List<EgovMap> list = canCustMapper.getCanBatchListExcelDown(searchVO);

        HashMap<String, String> maskFields = getMaskFields();
        maskingService.setMask(list, maskFields, paramMap);

        return list;
    }

    /**
     * @Description 일괄직권해지 배치 대상 등록
     */
    public void insertCanBatch(List<Object> uploadList, CanBatchVo searchVO){

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String userId = searchVO.getSessionUserId();

        for (int i = 0; i < uploadList.size(); i++) {
            CanBatchVo canBatchVo = (CanBatchVo) uploadList.get(i);
            canBatchVo.setSessionUserId(userId);
            String subStatus = canCustMapper.getJuoSubStatus(canBatchVo.getContractNum());

            if(subStatus != null && !subStatus.isEmpty()) {
                if ("A".equals(subStatus)) {
                    canCustMapper.insertCanBatchSkip(canBatchVo);
                } else {
                    canCustMapper.insertCanBatch(canBatchVo);
                }
            }
        }
    }

    /**
     * @Description 일괄직권해지 미처리 대상 count
     */
    public String getCanBatchTargetCount() {
        return canCustMapper.getCanBatchTargetCount();
    }

    /**
     * @Description 일괄직권해지 요청 count
     */
    public String getCanBatchRequestCount() {
        return canCustMapper.getCanBatchRequestCount();
    }

    public HashMap<String, String> getMaskFields() {
        HashMap<String, String> maskFields = new HashMap<String, String>();

        maskFields.put("subscriberNo","MOBILE_PHO"); //개통전화번호
        maskFields.put("reqNm","CUST_NAME"); //처리요청자

        return maskFields;
    }
    
    /**
     * @Description : 해지상담신청 정보를 가져온다.
     */
    public List<?> selectCanCslList(CanCustVO canCustVO, Map<String, Object> paramMap){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg(" 해지상담신청 고객  리스트 조회 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> selectCanCustList = canCustMapper.selectCanCslList(canCustVO);
		maskingService.setMask(selectCanCustList, maskingService.getMaskFields(), paramMap);
		
		return selectCanCustList;
    }
    
    /**
     * @Description : 해지상담신청 목록 엑셀 다운로드
     */
    public List<?> selectCanCslListByExcel(CanCustVO canCustVO, Map<String, Object> paramMap) {

        List<?> list = canCustMapper.selectCanCslListByExcel(canCustVO);
        maskingService.setMask(list, maskingService.getMaskFields(), paramMap);

        return list;
    }
    
    /**
     * @Description : 해지상담신청 상태값 조회
     */
    @Transactional(rollbackFor=Exception.class)
    public String getCustStatus(CanCustVO vo) {
    	return canCustMapper.getCustStatus(vo.getCustReqSeq());
    }
   
    
    /**
     * @Description : 해지상담신청 상태 처리 및 합본 처리
     */
	@Transactional(rollbackFor=Exception.class)
	public int updateCanCsl(CanCustVO vo, String usrId) throws EgovBizException {
		
		logger.debug("CanCustVO 처리건수 : " + vo.toString());
		int resultCnt = 0;
		String maxFileNum = "";
		
		// CP 처리 상태일때만 합본 한다.
		if("CP".equals(vo.getProcCd())) {
			// 합본 처리
			// 1. emv_scan_file MAX FILE_NUMBER 값을 찾아온다.
			maxFileNum = canCustMapper.getCanCslFileNum(vo.getOldScanId());
			logger.debug("maxFileNum : " + maxFileNum);
			
			// 2. scan_id로 emv_scan_file 정보를 가져와 insert 합본 완료
			vo.setFileNum(maxFileNum);
			resultCnt = canCustMapper.insertCanCslEmvFile(vo);
	
			logger.debug("resultCnt 처리건수 : " + resultCnt);
			
			int newFileCnt  = Integer.parseInt(maxFileNum) + resultCnt;
			vo.setMaxFileCnt(String.valueOf(newFileCnt));
			canCustMapper.updateCanCslEmvFileMst(vo);
		}
		
		// 상태 변경
		// NMCP_CUST_REQUEST_CALL_LIST 테이블에 메모와 상태를 업데이트 친다.
		resultCnt = canCustMapper.updateCanCslProcCd(vo);
		
		logger.debug("최종 처리 건수 : " + resultCnt);
		return resultCnt;
	}
}
