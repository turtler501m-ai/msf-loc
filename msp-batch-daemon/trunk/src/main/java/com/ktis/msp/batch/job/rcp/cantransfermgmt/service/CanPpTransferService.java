package com.ktis.msp.batch.job.rcp.cantransfermgmt.service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper.CanPpTransferMapper;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.vo.CanTransferVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CanPpTransferService extends BaseService {

	@Autowired
	private CanPpTransferMapper transferMapper;

	final static int TMNT_CUST_DT      = 180;
	final static int TMNT_CUST_DEL_DT  = 1825;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 해지후 180일 지난 선불 고객 데이터 이관
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int procTmntPpCustTransfer() throws MvnoServiceException {
		int procCnt = 0;

		LOGGER.info("## 해지후 180일 지난 선불 고객 데이터 이관 START");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("stdrCnt", TMNT_CUST_DT);

		// 해지 이관대상 조회
		List<CanTransferVO> list = transferMapper.getTmntPpCntrList(paramMap);

		//PPS_RCG_REMOTE_REQ_LOG 테이블은 CONTRACT_NUM이 없는 경우도 있어서 REQ_SEQ와 IN_REQ_DATE로 대상 찾아옴
		List<CanTransferVO> logList = transferMapper.getRcgRmoteReqLogList(paramMap);
		
		try{
			for(CanTransferVO vo : list){
				LOGGER.debug("계약번호=" + vo.getContractNum());

				vo.setTmntCustDt(TMNT_CUST_DT);

				// 선불 테이블 이관 (주민번호 존재하는 테이블)
				transferMapper.insertCanPpsCustomer(vo.getContractNum());
				transferMapper.insertCanPpsCustomerHist(vo.getContractNum());
				transferMapper.insertCanPpsAgentStmOpen(vo.getContractNum());
				transferMapper.insertCanPpsRcgRealCms(vo.getContractNum());
				transferMapper.insertCanPpsRcgRealCmsSetup(vo.getContractNum());
				transferMapper.insertCanPpsRealPayinfoCmsLog(vo.getContractNum());
				transferMapper.insertCanPpsVirAccountKibnet(vo.getContractNum());
				transferMapper.insertCanPpsRcgArs(vo.getContractNum());				

				// 선불 테이블 데이터 치환 (주민번호 존재하는 테이블)
				transferMapper.updatePpsCustomer(vo.getContractNum());
				transferMapper.updatePpsCustomerHist(vo.getContractNum());
				transferMapper.updatePpsAgentStmOpen(vo.getContractNum());
				transferMapper.updatePpsRcgRealCms(vo.getContractNum());
				transferMapper.updatePpsRcgRealCmsSetup(vo.getContractNum());
				transferMapper.updatePpsRealPayinfoCmsLog(vo.getContractNum());
				transferMapper.updatePpsVirAccountKibnet(vo.getContractNum());
				transferMapper.updatePpsRcgArs(vo.getContractNum());				
				
				procCnt++;
			}
			
			//PPS_RCG_REMOTE_REQ_LOG 테이블 이관
			for(CanTransferVO logVo : logList){
				LOGGER.debug("PPS_RCG_REMOTE_REQ_LOG 이관 : reqSeq=" + logVo.getReqSeq() + ", inReqDt=" + logVo.getInReqDt());
				
				transferMapper.insertCanPpsRcgRemoteReqLog(logVo);
				transferMapper.updatePpsRcgRemoteReqLog(logVo);
			}
			
		}catch(Exception e){
			throw new MvnoServiceException("ERCP1006", e);
		}

		LOGGER.info("## 해지후 180일 지난 선불 고객 데이터 이관 End");

		return procCnt;
	}

	public static boolean fileIsLive(String isLivefile){
		File file = new File(isLivefile);
		if(file.exists()) {
			return true;
		} else {
			return false;
		}
	}
	
}
