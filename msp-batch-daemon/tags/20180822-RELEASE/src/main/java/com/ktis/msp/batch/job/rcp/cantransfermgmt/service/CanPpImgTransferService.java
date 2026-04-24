package com.ktis.msp.batch.job.rcp.cantransfermgmt.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper.CanPpImgTransferMapper;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper.CanTransferMapper;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.vo.CanTransferVO;

@Service
public class CanPpImgTransferService extends BaseService {

	@Autowired
	private CanPpImgTransferMapper transferPpImgMapper;

	@Autowired
	private CanTransferMapper transferMapper;
	// 해지고객 선불 이미지 정보이관
	final static int TMNT_CUST_DT      = 180;

	
		
	/**
	 * 해지후 180일 지난 고객 이관
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int procTmntPpImgTransfer() throws MvnoServiceException {
		int procCnt = 0;
		
		List<CanTransferVO> list = transferPpImgMapper.getTmntPpImgList(TMNT_CUST_DT);
		
		try{
			for(CanTransferVO vo : list){
				
				LOGGER.debug("계약번호=" + vo.getContractNum());
				LOGGER.debug("서비스계약번호=" + vo.getSvcCntrNo());
				// 미납체크
				CanTransferVO unpayVO = transferMapper.getUnpayAmtCheck(vo);
				
				LOGGER.debug("청구월=" + unpayVO.getBillYm());
				LOGGER.debug("미납금액=" + unpayVO.getUnpayAmnt());
				LOGGER.debug("할부원금=" + unpayVO.getInstOrignAmnt());
				LOGGER.debug("단말납부금=" + unpayVO.getEquistPymnAmnt());
				
				if(unpayVO.getUnpayAmnt() == null) unpayVO.setUnpayAmnt(0);
				if(unpayVO.getInstOrignAmnt() == null) unpayVO.setInstOrignAmnt(0);
				if(unpayVO.getEquistPymnAmnt() == null) unpayVO.setEquistPymnAmnt(0);
				
				//미납금액이 없고 할부원금이 단말납부금액 이상인 경우 선불 이미지 이관
				if(unpayVO.getUnpayAmnt() <= 0 && (unpayVO.getEquistPymnAmnt() >= unpayVO.getInstOrignAmnt())){
					
					//계약번호 조회
					List<CanTransferVO> contList = transferPpImgMapper.getContractNum(vo.getContractNum());
					
					for(CanTransferVO contVO : contList){
						LOGGER.debug("requestKey=" + contVO.getContractNum());
						
						//선불 해지고객 이미지 정보 이관
						transferPpImgMapper.insertCanPpImg(vo.getContractNum());
					}
				}
			}
		}catch(Exception e){
			throw new MvnoServiceException("ERCP1006", e);
		}
		
		return procCnt;
	}	
}
