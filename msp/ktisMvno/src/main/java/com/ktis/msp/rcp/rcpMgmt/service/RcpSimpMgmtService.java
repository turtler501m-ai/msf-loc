package com.ktis.msp.rcp.rcpMgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.rcp.rcpMgmt.mapper.OsstCallMapper;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpSimpMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.OsstReqVO;
import com.ktis.msp.rcp.rcpMgmt.vo.OsstResVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpRateVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpSimpVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpTlphNoVO;

@Service
public class RcpSimpMgmtService extends BaseService {
	
	@Autowired
	private RcpSimpMgmtMapper rcpSimpMapper;
	
	@Autowired
	private OsstCallMapper osstCallMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	private final static String TCP_RSLT_CHK_CD = "PC2";
//	private final static String TCP_RSLT_SUCCESS_CD = "0000";
	
	/**
	 * 개통간소화 신청가능 부가서비스
	 * @param rcpRateVO
	 * @return
	 */
	public List<RcpRateVO> getRcpSimpRateList(RcpRateVO rcpRateVO){
		
		return rcpSimpMapper.getRcpSimpRateList(rcpRateVO);
	}
	
	/**
	 * 부가서비스배타관계
	 * @return
	 */
	public List<RcpRateVO> getExclusiveRateList(){
		
		return rcpSimpMapper.getExclusiveRateList();
	}
	
	/**
	 * 희망번호조회
	 * @param rcpTlphNoVO
	 * @return
	 */
	public RcpTlphNoVO getRcpSimpRsvTlphNoByInfo(RcpTlphNoVO rcpTlphNoVO){
		
		if (rcpTlphNoVO.getResNo() == null || "".equals(rcpTlphNoVO.getResNo())) {
			throw new MvnoRunException(-1, "예약번호가 존재 하지 않습니다.");
		}
		
		return rcpSimpMapper.getRcpSimpRsvTlphNoByInfo(rcpTlphNoVO);
	}
	
	/**
	 * 번호이동 미납금액 조회
	 * @param osstReqVO
	 * @return
	 */
	public OsstResVO getRcpSimpPayClaByInfo(OsstReqVO osstReqVO){
		
		if (osstReqVO.getResNo() == null || "".equals(osstReqVO.getResNo())) {
			throw new MvnoRunException(-1, "예약번호가 존재 하지 않습니다.");
		}
		
		osstReqVO.setAppEventCd(TCP_RSLT_CHK_CD);
		
//		OsstResVO rsltVo = osstCallMapper.getTcpResult(osstReqVO);
//		
//		if(rsltVo == null){
//			throw new MvnoRunException(-1, "MPLATFORM 연동 진행중이니 잠시 후 이용바랍니다.");
//		}else{
//			if(!TCP_RSLT_SUCCESS_CD.equals(rsltVo.getRsltCd())){
//				throw new MvnoRunException(-1, rsltVo.getPrdcChkNotiMsg());
//			}
//		}
		
		return osstCallMapper.getTcpResult(osstReqVO);
	}
	
	/**
	 * 납부주장 등록
	 * @param simpVO
	 */
	public void updMcpMovePayClaByInfo(RcpSimpVO simpVO){
		
		if (simpVO.getRequestKey() == null || "".equals(simpVO.getRequestKey())) {
			throw new MvnoRunException(-1, "REQUEST_KEY가 존재 하지 않습니다.");
		}
		
		if (simpVO.getOsstPayDay() == null || "".equals(simpVO.getOsstPayDay())) {
			throw new MvnoRunException(-1, "납부주장일자가 존재 하지 않습니다.");
		}
		
		if (simpVO.getOsstPayType() == null || "".equals(simpVO.getOsstPayType())) {
			throw new MvnoRunException(-1, "납부방법코드가 존재 하지 않습니다.");
		}
		
		int nRsltUpd = rcpSimpMapper.updMcpMovePayClaByInfo(simpVO);
		
		if(nRsltUpd < 1){
			throw new MvnoRunException(-1, "일치하는 정보가 존재 하지 않습니다.");
		}
	}
	
	/**
	 * 연동결과조회
	 * @param osstReqVO
	 * @return
	 */
	public OsstResVO getOsstRsltMsg(OsstReqVO osstReqVO){
		
		if (osstReqVO.getResNo() == null || "".equals(osstReqVO.getResNo())) {
			throw new MvnoRunException(-1, "예약번호가 존재 하지 않습니다.");
		}
		
		return rcpSimpMapper.getOsstRsltMsg(osstReqVO);
	}
	
	/**
	 * SMS템플릿조회
	 * @param osstReqVO
	 * @return
	 */
	public List<?> getRcpSmsTemplateList(RcpDetailVO searchVO){
		return rcpSimpMapper.getRcpSmsTemplateList(searchVO);
	}
	
	
	/**
	 * SMS발송
	 * @param vo
	 */
	public void setSmsSend(RcpSimpVO vo){
		
		if(vo.getDstaddr() == null || "".equals(vo.getDstaddr())){
			throw new MvnoRunException(-1, "수신자 전화번호가 없습니다.");
		}
		
		if(vo.getText() == null || "".equals(vo.getText())){
			throw new MvnoRunException(-1, "SMS내용이 없습니다.");
		}
		
		KtMsgQueueReqVO msgVO = new KtMsgQueueReqVO();
		msgVO.setMsgType(vo.getMsgType());
		msgVO.setRcptData(vo.getDstaddr());
		msgVO.setCallbackNum(vo.getCallback());
		msgVO.setSubject(vo.getSubject());
		msgVO.setMessage(vo.getText());
		msgVO.setReserved01("MSP");
		msgVO.setReserved02("MSP1000016");
		msgVO.setReserved03(vo.getSessionUserId());
		
		smsMgmtMapper.insertKtMsgQueue(msgVO);
	}
	
	/**
	 * SMS발송이력조회
	 * @param osstReqVO
	 * @return
	 */
	public List<?> getRcpSmsSendList(RcpSimpVO searchVO){
		return rcpSimpMapper.getRcpSmsSendList(searchVO);
	}
	/**
	 * SMS발송이력조회 구
	 * @param osstReqVO
	 * @return
	 */
	public List<?> getRcpSmsSendListOld(RcpSimpVO searchVO){
		return rcpSimpMapper.getRcpSmsSendListOld(searchVO);
	}	
}
