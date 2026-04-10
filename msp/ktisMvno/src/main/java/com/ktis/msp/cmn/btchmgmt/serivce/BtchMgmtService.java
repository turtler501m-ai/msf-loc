package com.ktis.msp.cmn.btchmgmt.serivce;

import java.util.List;
import java.util.Map;

import com.ktis.msp.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.btchmgmt.mapper.BtchMgmtMapper;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.btchmgmt.vo.JuiceBtchLogMgmtVO;

@Service
public class BtchMgmtService extends BaseService {
	
	@Autowired
	BtchMgmtMapper btchMgmtMapper;
	
	/**
	 * JUICE BATCH ID 조회
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getJuiceBtchIdList(){
		
		return btchMgmtMapper.getJuiceBtchIdList();
	}
	
	/**
	 * JUICE BATCH Log 조회
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getCmnJuiceBtchLogList(JuiceBtchLogMgmtVO vo){
		
		return btchMgmtMapper.getCmnJuiceBtchLogList(vo);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void insertBatchRequest(BatchJobVO vo) {
		
		if(vo.getBatchJobId() == null || "".equals(vo.getBatchJobId())){
			throw new MvnoRunException(-1, "배치작업ID 누락");
		}
	
		int excelBatch = 0;
		excelBatch = btchMgmtMapper.getExecBatchCnt(vo);
		
		//admin, DEV 권한이 있는경우 제외
		if(vo.getExclDnldYn() > 0 && excelBatch > 0) {
			throw new MvnoRunException(-1, "요청중인 엑셀 다운로드가 있습니다.");
		}
		
		btchMgmtMapper.insertBatchRequest(vo);
	}
	
	/**
	 * 배치정보조회
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getBatchInfo(BatchJobVO vo){
		
		return btchMgmtMapper.getBatchInfo(vo);
	}
	
	/**
	 * 배치정보저장
	 * @param VO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void savBatchInfo(BatchJobVO vo) {

		if((vo.getSaveType()).equals("U") && (vo.getBatchJobId() == null || "".equals(vo.getBatchJobId()))){
			throw new MvnoRunException(-1, "배치작업ID 누락");
		}
		
		if(vo.getBatchJobNm() == null || "".equals(vo.getBatchJobNm())){
			throw new MvnoRunException(-1, "배치작업명 누락");
		}
		
		if(vo.getDutySctn() == null || "".equals(vo.getDutySctn())){
			throw new MvnoRunException(-1, "업무구분 누락");
		}
		
		if(vo.getExecService() == null || "".equals(vo.getExecService())){
			throw new MvnoRunException(-1, "실행서비스 누락");
		}
		
		if(vo.getSessionUserId() == null || "".equals(vo.getSessionUserId())){
			throw new MvnoRunException(-1, "사용자ID 누락");
		}

		if(StringUtil.isEmpty(vo.getServerType())){
			throw new MvnoRunException(-1, "배치서버 누락");
		}


		// 등록
		if((vo.getSaveType()).equals("I")) {
			
			String dupFlag = btchMgmtMapper.getExecDupCheck(vo);
			
			if("Y".equals(dupFlag)) {
				throw new MvnoRunException(-1, "이미 등록된 실행서비스입니다.");
			}
			
			btchMgmtMapper.insertBatchInfo(vo);
			
		// 수정
		} else if((vo.getSaveType()).equals("U")) {
			
			btchMgmtMapper.updateBatchInfo(vo);
			
		}
		
	}
	
	/**
	 * 배치실행이력조회
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getBatchExecHst(BatchJobVO vo){
		
		return btchMgmtMapper.getBatchExecHst(vo);
	}

}
