package com.ktis.msp.batch.job.lgs.lgsbatchmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.batch.job.lgs.agpomgmt.mapper.AgncyPoOutMapper;
import com.ktis.msp.batch.job.lgs.agpomgmt.vo.AgncyPoOutVO;
import com.ktis.msp.batch.job.lgs.lgsbatchmgmt.mapper.LgsNstepIfMapper;
import com.ktis.msp.batch.job.lgs.lgsbatchmgmt.vo.LgsNstepIfVo;
import com.ktis.msp.batch.job.lgs.lgscommon.LgsComCode;
import com.ktis.msp.batch.job.lgs.lgscommon.LgsOpenEnum;
import com.ktis.msp.batch.job.lgs.lgscommon.LgsSrEnum;
import com.ktis.msp.batch.job.lgs.lgsmgmt.mapper.LgsMgmtMapper;
import com.ktis.msp.batch.job.lgs.lgsmgmt.vo.LgsMgmtVO;
import com.ktis.msp.batch.job.lgs.lgsmgmt.vo.LgsPrdtSrlMstVo;


@Service
public class LgsNstepIfService  extends BaseService {
	
	@Autowired
	private LgsNstepIfMapper lgsNstepIfMapper;
	
	@Autowired
	private AgncyPoOutMapper agncyPoOutMapper;
	
	@Autowired
	private LgsMgmtMapper lgsMgmtMapper;
	
	
	public LgsNstepIfService() {
		setLogPrefix("[Nstep-Interface] ");
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	public List<?> getLinkNonProcData() {
		return lgsNstepIfMapper.getLinkNonProcData();
	}
	
	/*****************************************
	 * @Description : 연동 데이터 상세 조회  *
	 * @Author : IB                          *
	 * @Create Date : 2015. 10. 8.            *
	 *****************************************/
	@Transactional(rollbackFor=Exception.class)
	public LgsNstepIfVo getCheckedLinkDataProc(LgsNstepIfVo dataVo) {
		
		/** 연동정보상세조회 **/
		LgsNstepIfVo procVo = lgsNstepIfMapper.getLinkDataDtlInfo(dataVo);
		
		return procVo;
	}
	
	
	/***************************************************
	 * @Description : 연동 데이터 유효성 검증 처리     *
	 * @Author : IB                                    *
	 * @Create Date : 2015. 10. 8.                      *
	 ***************************************************/
	@Transactional(rollbackFor=Exception.class)
	public LgsNstepIfVo verifyLinkedDataProc(LgsNstepIfVo dataVo) {
		
		
		/********************************************************************************
		 * 2015.08.03일 기준 : 이전데이터는 상세사유가 없다
		 * 2015.11.20일 기준 : NSTEP 처리시간 연동됨
		 ********************************************************************************/
		
		
		/** 연동데이터 기본 검증 체크 **/
		
		// [SKIP] 반영제외전문
		if( LgsComCode.YN_N.equals(dataVo.getProcYn()) ) {
			LOGGER.debug(LgsOpenEnum.PRC001.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC001.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC001.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [SKIP] 미등록고객
		if( LgsComCode.YN_N.equals(dataVo.getCusYn()) ) {
			LOGGER.debug(LgsOpenEnum.PRC002.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC002.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC002.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [SKIP] 선불전문
		if( LgsComCode.YN_N.equals(dataVo.getPoYn()) ) {
			LOGGER.debug(LgsOpenEnum.PRC003.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC003.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC003.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [SKIP] 미등록모델
		if( LgsComCode.YN_N.equals(dataVo.getMdlYn()) ) {
			LOGGER.debug(LgsOpenEnum.PRC004.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC004.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC004.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [SKIP] USIM전문
		if( !LgsComCode.INTM_TYPE_HAND.equals(dataVo.getPrdtTypeCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC005.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC005.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC005.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [SKIP] 미등록기기
		if( LgsOpenEnum.INTM_MYCP_X.equals(dataVo.getMycpYn()) ) {
			LOGGER.debug(LgsOpenEnum.PRC006.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC006.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC006.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		/** 처리 불가 전문 상세 체크 **/
		if( LgsOpenEnum.EVNT_DEL.equals(dataVo.getAplEvntCd()) ) {
			
			// 개통
			if( LgsOpenEnum.EVNT_NAC.equals(dataVo.getEvntCd()) ) {
				this.getCheckedOpenProc(dataVo);
				return new LgsNstepIfVo();
			}
			
			// 기변
			if( LgsOpenEnum.EVNT_C07.equals(dataVo.getEvntCd()) ) {
				this.getCheckedChngProc(dataVo);
				return new LgsNstepIfVo();
			}
			
			// 해지
			if( LgsOpenEnum.EVNT_CAN.equals(dataVo.getEvntCd()) ) {
				this.getCheckedCanProc(dataVo);
				return new LgsNstepIfVo();
			}
			
			// 해지복구
			if( LgsOpenEnum.EVNT_RCL.equals(dataVo.getEvntCd()) ) {
				this.getCheckedRstProc(dataVo);
				return new LgsNstepIfVo();
			}
			
			// 정합
			if( LgsOpenEnum.EVNT_UPD.equals(dataVo.getEvntCd())  ) {
				
				if( LgsOpenEnum.EVNT_RSN_NW.equals(dataVo.getEvntRsnCd()) ) {
					this.getCheckedOpenProc(dataVo);
					return new LgsNstepIfVo();
				}
				else if( LgsOpenEnum.EVNT_RSN_NR.equals(dataVo.getEvntRsnCd())
						 || LgsOpenEnum.EVNT_RSN_AS.equals(dataVo.getEvntRsnCd()) ) {
					this.getCheckedChngProc(dataVo);
					return new LgsNstepIfVo();
				}
				else if( LgsOpenEnum.EVNT_RSN_CN.equals(dataVo.getEvntRsnCd())
						 || ( dataVo.getEvntRsnCd() == null || "".equals(dataVo.getEvntRsnCd()) ) ) {
					this.getCheckedCanProc(dataVo);
					return new LgsNstepIfVo();
				}
				else if( LgsOpenEnum.EVNT_RSN_CR.equals(dataVo.getEvntRsnCd()) ) {
					this.getCheckedRstProc(dataVo);
					return new LgsNstepIfVo();
				}
				else {
					LOGGER.debug(LgsOpenEnum.PRC001.getDesc());
					dataVo.setProcCd(LgsOpenEnum.PRC001.getCode());
					dataVo.setProcStatCd(LgsOpenEnum.PRC001.getStat());
					lgsNstepIfMapper.updLinkData(dataVo);
					lgsNstepIfMapper.insChckDataInfo(dataVo);
					return new LgsNstepIfVo();
				}
				
			}
			
			// 부분정합
			if( LgsOpenEnum.EVNT_UPS.equals(dataVo.getEvntCd())  ) {
				
				if( LgsOpenEnum.EVNT_RSN_NW.equals(dataVo.getEvntRsnCd()) ) {
					this.getCheckedOpenProc(dataVo);
					return new LgsNstepIfVo();
				}
				else if( LgsOpenEnum.EVNT_RSN_NR.equals(dataVo.getEvntRsnCd())
						 || LgsOpenEnum.EVNT_RSN_AS.equals(dataVo.getEvntRsnCd()) ) {
					this.getCheckedChngProc(dataVo);
					return new LgsNstepIfVo();
				}
				else if( LgsOpenEnum.EVNT_RSN_CN.equals(dataVo.getEvntRsnCd())
						 || ( dataVo.getEvntRsnCd() == null || "".equals(dataVo.getEvntRsnCd()) ) ) {
					this.getCheckedCanProc(dataVo);
					return new LgsNstepIfVo();
				}
				else if( LgsOpenEnum.EVNT_RSN_CR.equals(dataVo.getEvntRsnCd()) ) {
					this.getCheckedRstProc(dataVo);
					return new LgsNstepIfVo();
				}
				else {
					LOGGER.debug(LgsOpenEnum.PRC001.getDesc());
					dataVo.setProcCd(LgsOpenEnum.PRC001.getCode());
					dataVo.setProcStatCd(LgsOpenEnum.PRC001.getStat());
					lgsNstepIfMapper.updLinkData(dataVo);
					lgsNstepIfMapper.insChckDataInfo(dataVo);
					return new LgsNstepIfVo();
				}
				
			}
			
		}
		else {
			return dataVo;
		}
		
		// [EXEC] 처리예외전문
		LOGGER.debug(LgsOpenEnum.PRC000.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC000.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC000.getStat());
		lgsNstepIfMapper.updLinkData(dataVo);
		lgsNstepIfMapper.insChckDataInfo(dataVo);
		return new LgsNstepIfVo();
		
	}
	
	
	/*****************************************
	 * @Description : 개통 데이터 상세 체크  *
	 * @Author : IB                          *
	 * @Create Date : 2015. 10. 8.           *
	 *****************************************/
	@Transactional(rollbackFor=Exception.class)
	public LgsNstepIfVo getCheckedOpenProc(LgsNstepIfVo dataVo) {
		
		// 조직체크 : 재고단말일경우
		if( LgsOpenEnum.INV_STAT_REG.equals(dataVo.getInvStatCd()) ) {
			
			// [SKIP] 미등록조직
			if( LgsOpenEnum.ORG_STAT_NON.equals(dataVo.getCntpntStatCd()) ) {
				LOGGER.debug(LgsOpenEnum.PRC801.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC801.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC801.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
			
			// [SKIP] 비판매조직
			if( LgsOpenEnum.ORG_STAT_NSL.equals(dataVo.getCntpntStatCd()) ) {
				LOGGER.debug(LgsOpenEnum.PRC802.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC802.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC802.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
			
			// [SKIP] 비가동조직
			if( LgsOpenEnum.ORG_STAT_NLV.equals(dataVo.getCntpntStatCd()) ) {
				LOGGER.debug(LgsOpenEnum.PRC803.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC803.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC803.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
			
		}
		
		// 조직체크 : 입고예정단말일경우
		if( LgsOpenEnum.INV_STAT_RSV.equals(dataVo.getInvStatCd()) ) {
			
			// [SKIP] 미등록조직
			if( LgsOpenEnum.ORG_STAT_NON.equals(dataVo.getRsvCntpntCd()) ) {
				LOGGER.debug(LgsOpenEnum.PRC801.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC801.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC801.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
			
			// [SKIP] 비판매조직
			if( LgsOpenEnum.ORG_STAT_NSL.equals(dataVo.getRsvCntpntCd()) ) {
				LOGGER.debug(LgsOpenEnum.PRC802.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC802.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC802.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
				
			}
			
			// [SKIP] 비가동조직
			if( LgsOpenEnum.ORG_STAT_NLV.equals(dataVo.getRsvCntpntCd()) ) {
				LOGGER.debug(LgsOpenEnum.PRC803.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC803.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC803.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
				
			}
			
		}
		
		// [CHCK] 개통:재고조직상이
		if( LgsOpenEnum.INV_STAT_DEL.equals(dataVo.getInvStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC101.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC101.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC101.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 개통:이력존재
		if( LgsComCode.YN_Y.equals(dataVo.getHstYn()) ) {
			LOGGER.debug(LgsOpenEnum.PRC102.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC102.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC102.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 개통:사유코드상이
		if( !LgsOpenEnum.EVNT_RSN_NW.equals(dataVo.getEvntCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC103.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC103.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC103.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [EXEC] 처리예외전문
		LOGGER.debug(LgsOpenEnum.PRC000.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC000.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC000.getStat());
		lgsNstepIfMapper.updLinkData(dataVo);
		lgsNstepIfMapper.insChckDataInfo(dataVo);
		return new LgsNstepIfVo();
	}
	
	
	/*********************************************
	 * 개통처리(NAC)
	 * @Author : IB
	 * @Create Date : 2015. 8. 10.
	 *********************************************/
	@Transactional(rollbackFor=Exception.class)
	public void intmOpenProc(LgsNstepIfVo dataVo) {
		
		
		/** CASE : 자사단말 **/
		if( LgsComCode.YN_Y.equals(dataVo.getMycpYn()) ) {
			
			
			/** 기기정보세팅 **/
			dataVo.setInoutId(lgsNstepIfMapper.getInvtSrl(LgsComCode.SEQ_IO));		/** 입출고시퀀스생성 */
			
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
			
			intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());	/** 물류처리상태코드 : 판매 */
			intmVo.setLastInoutId(dataVo.getInoutId());		/** 최종입출고ID */
			intmVo.setSvcCtNum(dataVo.getContractNum());	/** 가입계약번호 */
			intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
			intmVo.setSlsDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 판매일자 **/
			intmVo.setCanDt("00000000");					/** 해지일자 **/
			intmVo.setImei(dataVo.getImei());				/** IMEI **/
			intmVo.setWifiMacId(dataVo.getWifiMacId());		/** WIFI_MAC_ID **/
			intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.insertPrdtSnapshot(intmVo);
			lgsMgmtMapper.updateLgsPrdtSrlMst(intmVo);
			
			
			/** 입출고생성 **/
			/* 입출고SRL등록 */
			AgncyPoOutVO procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 모델일련번호 */
			procVo.setOutUnitPric(dataVo.getUnitPric());	/** 출고단가 **/
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdtSrl(procVo);
			
			
			/* 입출고PRDT등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setRegQnty(1);							/** 수량 */
			procVo.setOutAmt(dataVo.getUnitPric());			/** 금액 */
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdt(procVo);
			
			
			/* 입출고MST등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());		/** 입출고ID */
			procVo.setInoutDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 입출고일자 */
			procVo.setInoutTypeCd(LgsSrEnum.SNR_SALE_01.getSnrType());		/** 입출고유형코드 : 대리점판매 */
			procVo.setInoutTypeDtlCd(LgsSrEnum.SNR_SALE_01.getSnrCode());	/** 입출고유형상세코드 : 판매 */
			procVo.setOutOrgnId(dataVo.getCntpntCd());	/** 출고조직ID */
			procVo.setUserId(dataVo.getUserId());		/** 사용자ID */
			
			agncyPoOutMapper.regInOutMst2(procVo);
			
			
			/* 입출고REL등록 */
			procVo = new AgncyPoOutVO();
			procVo.setInoutId(dataVo.getInoutId());
			procVo.setJobCd(lgsMgmtMapper.getInvtSrl(LgsComCode.SEQ_SL));
			procVo.setJobIndCd(LgsSrEnum.SNR_SALE);
			procVo.setUserId(dataVo.getUserId());
			
			agncyPoOutMapper.regInOutRel(procVo);
			
			
			/** 재고수량변경 **/
			LgsMgmtVO mgmtVo = new LgsMgmtVO();
			
			mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 출고조직ID */
			mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
			mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
			mgmtVo.setGoodStrgInvtrQnty(-1);		/** 양품재고수량 */
			mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
			mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
			mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
			mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
			
			if(lgsMgmtMapper.updateInvtrDayMst(mgmtVo) <= 0){
				lgsMgmtMapper.insertInvtrDayMst(mgmtVo);
			}
			
			
		}
		/** CASE : 타사단말 **/
		else {
			
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
			
			intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());		/** 물류처리상태코드 : 판매 */
			intmVo.setSvcCtNum(dataVo.getContractNum());	/** 가입계약번호 */
			intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
			intmVo.setSlsDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 판매일자 **/
			intmVo.setCanDt("00000000");					/** 해지일자 **/
			intmVo.setImei(dataVo.getImei());				/** IMEI **/
			intmVo.setWifiMacId(dataVo.getWifiMacId());		/** WIFI_MAC_ID **/
			intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.updateLgsOtcpSrlMst(intmVo);
			
			
			/** 재고수량변경 **/
			LgsMgmtVO mgmtVo = new LgsMgmtVO();
			
			mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 입고조직ID */
			mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
			mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
			mgmtVo.setGoodStrgInvtrQnty(-1);		/** 양품재고수량 */
			mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
			mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
			mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
			mgmtVo.setSaleQty(1);					/** 판매수량 */
			mgmtVo.setCanQty(0);					/** 해지수량 */
			mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
			
			lgsMgmtMapper.mgrOtcpInvMst(mgmtVo);
			
		}
		
		
		/** 개통이력생성 **/
		LOGGER.debug(LgsOpenEnum.PRC910.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC910.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC910.getStat());
		lgsNstepIfMapper.insOpenHstInfo(dataVo);
		
		
		/** 상세 조회 데이터 이력 생성 **/
		LOGGER.debug(LgsOpenEnum.PRC999.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC999.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC999.getStat());
		lgsNstepIfMapper.updLinkData(dataVo);
		lgsNstepIfMapper.insChckDataInfo(dataVo);
		
	}
	
	
	/*********************************************
	 * 인수전개통처리(NAC)
	 * @Author : IB
	 * @Create Date : 2015. 10. 01.
	 *********************************************/
	@Transactional(rollbackFor=Exception.class)
	public void intmOpenNonTakeProc(LgsNstepIfVo dataVo) {
		
		
		/** CASE : 자사단말 **/
		if( LgsComCode.YN_Y.equals(dataVo.getMycpYn()) ) {
			
			
			/** 기기정보세팅 **/
			dataVo.setInoutId(lgsNstepIfMapper.getInvtSrl(LgsComCode.SEQ_IO));		/** 입출고시퀀스생성 */
			
			
			/** 단말 출고가 조회**/
			LgsMgmtVO mgmtVo = new LgsMgmtVO();
			mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 제품ID */
			mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
			
			LgsMgmtVO amtVo = lgsMgmtMapper.getIntmAmtInfo(mgmtVo);
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
			
			intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			intmVo.setOrgnId(dataVo.getRsvCntpntCd());		/** 입고예정조직 **/
			intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_02.getSnrCode());	/** 물류처리상태코드 : 인수전판매 */
			intmVo.setLastInoutId(dataVo.getInoutId());		/** 최종입출고ID */
			intmVo.setSvcCtNum(dataVo.getContractNum());	/** 가입계약번호 */
			intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
			intmVo.setOutDt(lgsMgmtMapper.getDbSysDate(0));	/** 출고일자 */
			intmVo.setOutUnitPrice(amtVo.getOutUnitPric());	/** 출고단가 */
			intmVo.setSlsDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 판매일자 **/
			intmVo.setCanDt("00000000");					/** 해지일자 **/
			intmVo.setImei(dataVo.getImei());				/** IMEI **/
			intmVo.setWifiMacId(dataVo.getWifiMacId());		/** WIFI_MAC_ID **/
			intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.insertPrdtSnapshot(intmVo);
			lgsMgmtMapper.updateLgsPrdtSrlMst(intmVo);
			
			
			/** 입출고생성 **/
			/* 입출고SRL등록 */
			AgncyPoOutVO procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 모델일련번호 */
			procVo.setOutUnitPric(dataVo.getUnitPric());	/** 출고단가 **/
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdtSrl(procVo);
			
			
			/* 입출고PRDT등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setRegQnty(1);							/** 수량 */
			procVo.setOutAmt(dataVo.getUnitPric());			/** 금액 */
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdt(procVo);
			
			
			/* 입출고MST등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());		/** 입출고ID */
			procVo.setInoutDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 입출고일자 */
			procVo.setInoutTypeCd(LgsSrEnum.SNR_SALE_02.getSnrType());		/** 입출고유형코드 : 대리점판매 */
			procVo.setInoutTypeDtlCd(LgsSrEnum.SNR_SALE_02.getSnrCode());	/** 입출고유형상세코드 : 인수전판매 */
			procVo.setInOrgnId(dataVo.getRsvCntpntCd());	/** 입고조직ID */
			procVo.setOutOrgnId(dataVo.getRsvCntpntCd());	/** 출고조직ID */
			procVo.setUserId(dataVo.getUserId());		/** 사용자ID */
			
			agncyPoOutMapper.regInOutMst2(procVo);
			
			
			/* 입출고REL등록 */
			procVo = new AgncyPoOutVO();
			procVo.setInoutId(dataVo.getInoutId());
			procVo.setJobCd(lgsMgmtMapper.getInvtSrl(LgsComCode.SEQ_SL));
			procVo.setJobIndCd(LgsSrEnum.SNR_SALE);
			procVo.setUserId(dataVo.getUserId());
			
			agncyPoOutMapper.regInOutRel(procVo);
			
		}
		/** CASE : 타사단말 **/
		else {
			
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
			
			intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			intmVo.setOrgnId(dataVo.getRsvCntpntCd());		/** 입고예정조직 **/
			intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_02.getSnrCode());		/** 물류처리상태코드 : 인수전판매 */
			intmVo.setSvcCtNum(dataVo.getContractNum());	/** 가입계약번호 */
			intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
			intmVo.setSlsDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 판매일자 **/
			intmVo.setCanDt("00000000");					/** 해지일자 **/
			intmVo.setImei(dataVo.getImei());				/** IMEI **/
			intmVo.setWifiMacId(dataVo.getWifiMacId());		/** WIFI_MAC_ID **/
			intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.updateLgsOtcpSrlMst(intmVo);
			
			
		}
		
		
		/** 개통이력생성 **/
		LOGGER.debug(LgsOpenEnum.PRC910.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC910.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC910.getStat());
		lgsNstepIfMapper.insOpenHstInfo(dataVo);
		
		
		/** 상세 조회 데이터 이력 생성 **/
		LOGGER.debug(LgsOpenEnum.PRC999.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC999.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC999.getStat());
		lgsNstepIfMapper.updLinkData(dataVo);
		lgsNstepIfMapper.insChckDataInfo(dataVo);
		
	}
	
	
	/*****************************************
	 * @Description : 기변 데이터 상세 체크  *
	 * @Author : IB                          *
	 * @Create Date : 2015. 10. 8.           *
	 *****************************************/
	@Transactional(rollbackFor=Exception.class)
	public LgsNstepIfVo getCheckedChngProc(LgsNstepIfVo dataVo) {
		
		// [SKIP] 미등록조직
		if( LgsOpenEnum.ORG_STAT_NON.equals(dataVo.getCntpntStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC801.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC801.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC801.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [SKIP] 비판매조직
		if( LgsOpenEnum.ORG_STAT_NSL.equals(dataVo.getCntpntStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC802.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC802.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC802.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
			
		}
		
		// [SKIP] 비가동조직
		if( LgsOpenEnum.ORG_STAT_NLV.equals(dataVo.getCntpntStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC803.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC803.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC803.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
			
		}
		
		// [CHCK] 기변:이력미존재
		if( LgsOpenEnum.EVNT_RSN_AS.equals(dataVo.getEvntRsnCd())
			&& LgsComCode.YN_N.equals(dataVo.getHstYn()) ) {
			LOGGER.debug(LgsOpenEnum.PRC201.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC201.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC201.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 기변:해지이력존재
		if( LgsOpenEnum.EVNT_CAN.equals(dataVo.getPrvEvntCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC202.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC202.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC202.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 기변:사유코드상이
		if( !LgsOpenEnum.EVNT_RSN_NR.equals(dataVo.getEvntRsnCd())
			&& !LgsOpenEnum.EVNT_RSN_AS.equals(dataVo.getEvntRsnCd())
			&& !LgsOpenEnum.EVNT_RSN_VP.equals(dataVo.getEvntRsnCd())
			&& !LgsOpenEnum.EVNT_RSN_VC.equals(dataVo.getEvntRsnCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC203.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC203.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC203.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// 우수기변일 경우
		if( LgsOpenEnum.EVNT_RSN_VP.equals(dataVo.getEvntRsnCd()) ) {
			
			// [CHCK] 기변:단말미존재
			if( !LgsOpenEnum.INV_STAT_REG.equals(dataVo.getNorgInvStatCd()) ) {
				LOGGER.debug(LgsOpenEnum.PRC204.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC204.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC204.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
			
			// [CHCK] 기변:판매단말미존재
			// 'WAT' -- 처리가능단말 - 이력없이 유심단독.
			if( !LgsOpenEnum.INV_STAT_SLS.equals(dataVo.getNorgPrvInvStatCd()) ) {
				if(!LgsOpenEnum.INV_STAT_WAT.equals(dataVo.getNorgPrvInvStatCd()) || !LgsComCode.YN_N.equals(dataVo.getOpenMdlYn())) {
					LOGGER.debug(LgsOpenEnum.PRC205.getDesc());
					dataVo.setProcCd(LgsOpenEnum.PRC205.getCode());
					dataVo.setProcStatCd(LgsOpenEnum.PRC205.getStat());
					lgsNstepIfMapper.updLinkData(dataVo);
					lgsNstepIfMapper.insChckDataInfo(dataVo);
					return new LgsNstepIfVo();
				}
			}
		} 
		
		// 우수기변취소일 경우
		else if ( LgsOpenEnum.EVNT_RSN_VC.equals(dataVo.getEvntRsnCd()) ) {
			
			// [CHCK] 기변:재고상태상이
			if( !LgsOpenEnum.INV_STAT_SLS.equals(dataVo.getNorgInvStatCd())
				&& !LgsOpenEnum.INV_STAT_SLS.equals(dataVo.getNorgPrvInvStatCd()) ) {
				LOGGER.debug(LgsOpenEnum.PRC210.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC210.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC210.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
			
			// [CHCK] 기변:우수기변이력미존재
			if( !LgsOpenEnum.EVNT_C07.equals(dataVo.getPrvEvntCd())
				&& !LgsOpenEnum.EVNT_RSN_VP.equals(dataVo.getPrvEvntRsnCd())
				&& !LgsOpenEnum.EVNT_RSN_AS.equals(dataVo.getPrvEvntRsnCd()) ) {
				LOGGER.debug(LgsOpenEnum.PRC211.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC211.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC211.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
		} 
		
		else {
			
			// [CHCK] 기변:단말미존재
			if( !LgsOpenEnum.INV_STAT_REG.equals(dataVo.getInvStatCd()) ) {
				LOGGER.debug(LgsOpenEnum.PRC204.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC204.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC204.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
			
			// [CHCK] 기변:판매단말미존재
			if( !LgsOpenEnum.INV_STAT_SLS.equals(dataVo.getPrvInvStatCd()) ) {
				if(!LgsOpenEnum.INV_STAT_WAT.equals(dataVo.getNorgPrvInvStatCd()) || !LgsComCode.YN_N.equals(dataVo.getOpenMdlYn())) {
					LOGGER.debug(LgsOpenEnum.PRC205.getDesc());
					dataVo.setProcCd(LgsOpenEnum.PRC205.getCode());
					dataVo.setProcStatCd(LgsOpenEnum.PRC205.getStat());
					lgsNstepIfMapper.updLinkData(dataVo);
					lgsNstepIfMapper.insChckDataInfo(dataVo);
					return new LgsNstepIfVo();
				}
			}
		}
		
		
		// [CHCK] 기변:불가(자사->타사)
		/**
		 * 적용사유 : 기변시 자타사가 동일한 수수료율 적용
		 * 적용일자 : 2015.08.12.
		 * By IB 
		 */
//		if( !dataVo.getMycpYn().equals(dataVo.getPrvMycpYn()) ) {
//			LOGGER.debug(LgsOpenEnum.PRC206.getDesc());
//			dataVo.setProcCd(LgsOpenEnum.PRC206.getCode());
//			dataVo.setProcYn(LgsOpenEnum.PRC206.getStat());
//			lgsNstepIfMapper.updLinkData(dataVo);
//			lgsNstepIfMapper.insChckDataInfo(dataVo);
//			return new LgsNstepIfVo();
//		}
		
		// 상세체크 : AS기변일경우
		if( LgsOpenEnum.EVNT_RSN_AS.equals(dataVo.getEvntRsnCd()) ) {
			
			// [CHCK] 기변:기변기간만료
			if( LgsComCode.YN_N.equals(dataVo.getExchYn()) ) {
				LOGGER.debug(LgsOpenEnum.PRC206.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC206.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC206.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
			
			// [CHCK] 기변:대표모델상이
			if( !dataVo.getRprsPrdtId().equals(dataVo.getPrvRprsPrdtId()) ) {
				LOGGER.debug(LgsOpenEnum.PRC207.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC207.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC207.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
			
			// [CHCK] 기변:중고여부상이
			if( !dataVo.getOldYn().equals(dataVo.getPrvOldYn()) ) {
				LOGGER.debug(LgsOpenEnum.PRC208.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC208.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC208.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
			
			// [CHCK] 기변:판매조직상이
			if( !dataVo.getCntpntCd().equals(dataVo.getPrvCntpntCd()) ) {
				LOGGER.debug(LgsOpenEnum.PRC209.getDesc());
				dataVo.setProcCd(LgsOpenEnum.PRC209.getCode());
				dataVo.setProcStatCd(LgsOpenEnum.PRC209.getStat());
				lgsNstepIfMapper.updLinkData(dataVo);
				lgsNstepIfMapper.insChckDataInfo(dataVo);
				return new LgsNstepIfVo();
			}
			
		}
		
		// [EXEC] 처리예외전문
		LOGGER.debug(LgsOpenEnum.PRC000.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC000.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC000.getStat());
		lgsNstepIfMapper.updLinkData(dataVo);
		lgsNstepIfMapper.insChckDataInfo(dataVo);
		return new LgsNstepIfVo();
	}
	
	
	/*********************************************
	 * 기변처리(C07)
	 * @Author : IB
	 * @Create Date : 2015. 8. 12.
	 *********************************************/
	@Transactional(rollbackFor=Exception.class)
	public void intmChngProc(LgsNstepIfVo dataVo) {
		
		
		/** CASE : 자사단말 **/
		if( LgsComCode.YN_Y.equals(dataVo.getMycpYn()) ) {
			
			
			/** 기기정보세팅 **/
			dataVo.setInoutId(lgsNstepIfMapper.getInvtSrl(LgsComCode.SEQ_IO));		/** 입출고시퀀스생성 */
			
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
			
			intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());	/** 물류처리상태코드 : 판매 */
			intmVo.setLastInoutId(dataVo.getInoutId());		/** 최종입출고ID */
			intmVo.setSvcCtNum(dataVo.getContractNum());	/** 가입계약번호 */
			intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
			intmVo.setSlsDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 판매일자 **/
			intmVo.setCanDt("00000000");					/** 해지일자 **/
			intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.insertPrdtSnapshot(intmVo);
			lgsMgmtMapper.updateLgsPrdtSrlMst(intmVo);
			
			
			/** 입출고생성 **/
			/* 입출고SRL등록 */
			AgncyPoOutVO procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 모델일련번호 */
			procVo.setOutUnitPric(dataVo.getUnitPric());	/** 출고단가 **/
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdtSrl(procVo);
			
			
			/* 입출고PRDT등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setRegQnty(1);							/** 수량 */
			procVo.setOutAmt(dataVo.getUnitPric());			/** 금액 */
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdt(procVo);
			
			
			/* 입출고MST등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());		/** 입출고ID */
			procVo.setInoutDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 입출고일자 */
			procVo.setInoutTypeCd(LgsSrEnum.SNR_SALE_01.getSnrType());	/** 입출고유형코드 : 대리점판매 */
			procVo.setInoutTypeDtlCd(LgsSrEnum.SNR_SALE_01.getSnrCode());	/** 입출고유형상세코드 : 판매 */
			procVo.setOutOrgnId(dataVo.getCntpntCd());	/** 출고조직ID */
			procVo.setUserId(dataVo.getUserId());		/** 사용자ID */
			
			agncyPoOutMapper.regInOutMst2(procVo);
			
			
			/* 입출고REL등록 */
			procVo = new AgncyPoOutVO();
			procVo.setInoutId(dataVo.getInoutId());
			procVo.setJobCd(lgsMgmtMapper.getInvtSrl(LgsComCode.SEQ_SL));
			procVo.setJobIndCd(LgsSrEnum.SNR_SALE);
			procVo.setUserId(dataVo.getUserId());
			
			agncyPoOutMapper.regInOutRel(procVo);
			
			
			/** 재고수량변경 **/
			LgsMgmtVO mgmtVo = new LgsMgmtVO();
			
			mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 입고조직ID */
			mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
			mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
			mgmtVo.setGoodStrgInvtrQnty(-1);		/** 양품재고수량 */
			mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
			mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
			mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
			mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
			
			if(lgsMgmtMapper.updateInvtrDayMst(mgmtVo) <= 0) {
				lgsMgmtMapper.insertInvtrDayMst(mgmtVo);
			}
			
			
			/** 일반기변 **/
			if( LgsOpenEnum.EVNT_RSN_NR.equals(dataVo.getEvntRsnCd())
				|| LgsOpenEnum.EVNT_RSN_LS.equals(dataVo.getEvntRsnCd()) ) {
				
				
				/** 기존단말 : 자사일경우 **/
				if( LgsComCode.YN_Y.equals(dataVo.getPrvMycpYn()) ) {
					
					
					/** 원부변경 **/
					LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
					
					prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
					prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
					prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());		/** 물류처리상태코드 : 판매 */
					prevIntmVo.setSvcCtNum("000000000");					/** 가입계약번호 */
					prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
					prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
					prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
					prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
					prevIntmVo.setCanDt("99991231");					/** 해지일자 **/
					prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
					
					lgsMgmtMapper.updateLgsPrdtSrlMst(prevIntmVo);
					
				}
				/** 기존단말 : 타사일경우 **/
				else {
					
					
					/** 원부변경 **/
					LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
					
					prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
					prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
					prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());		/** 물류처리상태코드 : 판매 */
					prevIntmVo.setSvcCtNum("000000000");					/** 가입계약번호 */
					prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
					prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
					prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
					prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
					prevIntmVo.setCanDt("99991231");					/** 해지일자 **/
					prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
					
					lgsMgmtMapper.updateLgsOtcpSrlMst(prevIntmVo);
					
				}
				
			}
			/** AS기변 **/
			else if( LgsOpenEnum.EVNT_RSN_AS.equals(dataVo.getEvntRsnCd()) ) {
				
				
				/** 기존단말 : 자사일경우 **/
				if( LgsComCode.YN_Y.equals(dataVo.getPrvMycpYn()) ) {
					
					
					/** 기기정보세팅 **/
					dataVo.setPrvInoutId(lgsNstepIfMapper.getInvtSrl(LgsComCode.SEQ_IO));		/** 입출고시퀀스생성 */
					
					
					/** 원부변경 **/
					LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
					
					prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
					prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
					prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_04.getSnrCode());		/** 물류처리상태코드 : 판매해지 */
					prevIntmVo.setLastInoutId(dataVo.getPrvInoutId());		/** 최종입출고ID */
					prevIntmVo.setSvcCtNum("000000000");						/** 가입계약번호 */
					prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());		/** 이벤트코드 */
					prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
					prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
					prevIntmVo.setIfQueNum(dataVo.getIfQueNum());			/** 인터페이스번호 **/
					prevIntmVo.setCanDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 해지일자 **/
					prevIntmVo.setUserId(dataVo.getUserId());				/** 사용자ID */
					
					lgsMgmtMapper.updateLgsPrdtSrlMst(prevIntmVo);
					
					
					/** 입출고생성 **/
					/* 입출고SRL등록 */
					procVo = new AgncyPoOutVO();
					
					procVo.setInoutId(dataVo.getPrvInoutId());			/** 입출고ID */
					procVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
					procVo.setOldYn(dataVo.getPrvOldYn());				/** 중고여부 */
					procVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 모델일련번호 */
					procVo.setOutUnitPric(dataVo.getUnitPric());		/** 출고단가 **/
					procVo.setUserId(dataVo.getUserId());				/** 사용자ID */
					
					agncyPoOutMapper.regInOutPrdtSrl(procVo);
					
					
					/* 입출고PRDT등록 */
					procVo = new AgncyPoOutVO();
					
					procVo.setInoutId(dataVo.getPrvInoutId());	/** 입출고ID */
					procVo.setPrdtId(dataVo.getPrvPrdtId());	/** 기기ID */
					procVo.setOldYn(dataVo.getPrvOldYn());		/** 중고여부 */
					procVo.setRegQnty(1);						/** 수량 */
					procVo.setOutAmt(dataVo.getUnitPric());		/** 금액 */
					procVo.setUserId(dataVo.getUserId());		/** 사용자ID */
					
					agncyPoOutMapper.regInOutPrdt(procVo);
					
					
					/* 입출고MST등록 */
					procVo = new AgncyPoOutVO();
					
					procVo.setInoutId(dataVo.getPrvInoutId());		/** 입출고ID */
					procVo.setInoutDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 입출고일자 */
					procVo.setInoutTypeCd(LgsSrEnum.SNR_SALE_04.getSnrType());		/** 입출고유형코드 : 입고 */
					procVo.setInoutTypeDtlCd(LgsSrEnum.SNR_SALE_04.getSnrCode());	/** 입출고유형상세코드 : 판매해지 */
					procVo.setInOrgnId(dataVo.getPrvCntpntCd());	/** 입고조직ID */
					procVo.setOutOrgnId(dataVo.getPrvCntpntCd());	/** 출고조직ID */
					procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
					
					agncyPoOutMapper.regInOutMst2(procVo);
					
					
					/* 입출고REL등록 */
					procVo = new AgncyPoOutVO();
					procVo.setInoutId(dataVo.getInoutId());
					procVo.setJobCd(lgsMgmtMapper.getInvtSrl(LgsComCode.SEQ_SL));
					procVo.setJobIndCd(LgsSrEnum.SNR_SALE);
					procVo.setUserId(dataVo.getUserId());
					
					agncyPoOutMapper.regInOutRel(procVo);
					
					
					/** 재고수량변경 **/
					mgmtVo = new LgsMgmtVO();
					
					mgmtVo.setOrgnId(dataVo.getPrvCntpntCd());	/** 입고조직ID */
					mgmtVo.setPrdtId(dataVo.getPrvPrdtId());	/** 기기ID */
					mgmtVo.setOldYn(dataVo.getPrvOldYn());		/** 중고여부 */
					mgmtVo.setGoodStrgInvtrQnty(1);				/** 양품재고수량 */
					mgmtVo.setNgStrgInvtrQnty(0);				/** 불량재고수량 */
					mgmtVo.setInDueQnty(0);						/** 입고예정수량 */
					mgmtVo.setOutRsvQnty(0);					/** 출고예정수량 */
					mgmtVo.setUserId(dataVo.getUserId());		/** 사용자ID */
					
					if(lgsMgmtMapper.updateInvtrDayMst(mgmtVo) <= 0){
						lgsMgmtMapper.insertInvtrDayMst(mgmtVo);
					}
					
				}
				/** 기존단말 : 타사일경우 **/
				else {
					
					/** 원부변경 **/
					LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
					
					prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
					prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
					prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_04.getSnrCode());		/** 물류처리상태코드 : 판매해지 */
					prevIntmVo.setSvcCtNum("000000000");					/** 가입계약번호 */
					prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
					prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
					prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
					prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
					prevIntmVo.setCanDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 해지일자 **/
					prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
					
					lgsMgmtMapper.updateLgsOtcpSrlMst(prevIntmVo);
					
					
					/** 재고수량변경 **/
					mgmtVo = new LgsMgmtVO();
					
					mgmtVo.setOrgnId(dataVo.getPrvCntpntCd());	/** 입고조직ID */
					mgmtVo.setPrdtId(dataVo.getPrvPrdtId());	/** 기기ID */
					mgmtVo.setOldYn(dataVo.getPrvOldYn());		/** 중고여부 */
					mgmtVo.setGoodStrgInvtrQnty(1);				/** 양품재고수량 */
					mgmtVo.setNgStrgInvtrQnty(0);				/** 불량재고수량 */
					mgmtVo.setInDueQnty(0);						/** 입고예정수량 */
					mgmtVo.setOutRsvQnty(0);					/** 출고예정수량 */
					mgmtVo.setSaleQty(0);						/** 판매수량 */
					mgmtVo.setCanQty(1);						/** 해지수량 */
					mgmtVo.setUserId(dataVo.getUserId());		/** 사용자ID */
					
					lgsMgmtMapper.mgrOtcpInvMst(mgmtVo);
					
				}
				
			}
			
		}
		/** CASE : 타사단말 **/
		else {
			
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
			
			intmVo.setOutOrgnId(dataVo.getCntpntCd());		/** 출고조직ID */
			intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());		/** 물류처리상태코드 : 판매 */
			intmVo.setSvcCtNum(dataVo.getContractNum());	/** 가입계약번호 */
			intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			intmVo.setSlsDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 판매일자 **/
			intmVo.setCanDt("00000000");					/** 해지일자 **/
			intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
			intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.updateLgsOtcpSrlMst(intmVo);
			
			
			/** 재고수량변경 **/
			LgsMgmtVO mgmtVo = new LgsMgmtVO();
			
			mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 조직ID */
			mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
			mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
			mgmtVo.setGoodStrgInvtrQnty(-1);		/** 양품재고수량 */
			mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
			mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
			mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
			mgmtVo.setSaleQty(1);					/** 판매수량 */
			mgmtVo.setCanQty(0);					/** 해지수량 */
			mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
			
			lgsMgmtMapper.mgrOtcpInvMst(mgmtVo);
			
			
			/** 일반기변 **/
			if( LgsOpenEnum.EVNT_RSN_NR.equals(dataVo.getEvntRsnCd())
				|| LgsOpenEnum.EVNT_RSN_LS.equals(dataVo.getEvntRsnCd()) ) {
				
				
				/** 기존단말 : 자사일경우 **/
				if( LgsComCode.YN_Y.equals(dataVo.getPrvMycpYn()) ) {
					
					
					/** 원부변경 **/
					LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
					
					prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
					prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
					prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());		/** 물류처리상태코드 : 판매 */
					prevIntmVo.setSvcCtNum("000000000");					/** 가입계약번호 */
					prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
					prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
					prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
					prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
					prevIntmVo.setCanDt("99991231");					/** 해지일자 **/
					prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
					
					lgsMgmtMapper.updateLgsPrdtSrlMst(prevIntmVo);
					
				}
				/** 기존단말 : 타사일경우 **/
				else {
					
					
					/** 원부변경 **/
					LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
					
					prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
					prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
					prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());		/** 물류처리상태코드 : 판매 */
					prevIntmVo.setSvcCtNum("000000000");					/** 가입계약번호 */
					prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
					prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
					prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
					prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
					prevIntmVo.setCanDt("99991231");					/** 해지일자 **/
					prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
					
					lgsMgmtMapper.updateLgsOtcpSrlMst(prevIntmVo);
					
				}
				
			}
			/** AS기변 **/
			else if( LgsOpenEnum.EVNT_RSN_AS.equals(dataVo.getEvntRsnCd()) ) {
				
				
				/** 기존단말 : 자사일경우 **/
				if( LgsComCode.YN_Y.equals(dataVo.getPrvMycpYn()) ) {
					
					
					/** 기기정보세팅 **/
					dataVo.setPrvInoutId(lgsNstepIfMapper.getInvtSrl(LgsComCode.SEQ_IO));		/** 입출고시퀀스생성 */
					
					
					/** 원부변경 **/
					LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
					
					prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
					prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
					prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_04.getSnrCode());		/** 물류처리상태코드 : 판매해지 */
					prevIntmVo.setLastInoutId(dataVo.getPrvInoutId());		/** 최종입출고ID */
					prevIntmVo.setSvcCtNum("000000000");					/** 가입계약번호 */
					prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());		/** 이벤트코드 */
					prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
					prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
					prevIntmVo.setIfQueNum(dataVo.getIfQueNum());			/** 인터페이스번호 **/
					prevIntmVo.setCanDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 해지일자 **/
					prevIntmVo.setUserId(dataVo.getUserId());				/** 사용자ID */
					
					lgsMgmtMapper.updateLgsPrdtSrlMst(prevIntmVo);
					
					
					/** 입출고생성 **/
					/* 입출고SRL등록 */
					AgncyPoOutVO procVo = new AgncyPoOutVO();
					
					procVo.setInoutId(dataVo.getPrvInoutId());			/** 입출고ID */
					procVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
					procVo.setOldYn(dataVo.getPrvOldYn());				/** 중고여부 */
					procVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 모델일련번호 */
					procVo.setOutUnitPric(dataVo.getUnitPric());		/** 출고단가 **/
					procVo.setUserId(dataVo.getUserId());				/** 사용자ID */
					
					agncyPoOutMapper.regInOutPrdtSrl(procVo);
					
					
					/* 입출고PRDT등록 */
					procVo = new AgncyPoOutVO();
					
					procVo.setInoutId(dataVo.getPrvInoutId());	/** 입출고ID */
					procVo.setPrdtId(dataVo.getPrvPrdtId());	/** 기기ID */
					procVo.setOldYn(dataVo.getPrvOldYn());		/** 중고여부 */
					procVo.setRegQnty(1);						/** 수량 */
					procVo.setOutAmt(dataVo.getUnitPric());		/** 금액 */
					procVo.setUserId(dataVo.getUserId());		/** 사용자ID */
					
					agncyPoOutMapper.regInOutPrdt(procVo);
					
					
					/* 입출고MST등록 */
					procVo = new AgncyPoOutVO();
					
					procVo.setInoutId(dataVo.getPrvInoutId());		/** 입출고ID */
					procVo.setInoutDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 입출고일자 */
					procVo.setInoutTypeCd(LgsSrEnum.SNR_SALE_04.getSnrType());		/** 입출고유형코드 : 입고 */
					procVo.setInoutTypeDtlCd(LgsSrEnum.SNR_SALE_04.getSnrCode());	/** 입출고유형상세코드 : 판매해지 */
					procVo.setInOrgnId(dataVo.getPrvCntpntCd());	/** 입고조직ID */
					procVo.setOutOrgnId(dataVo.getPrvCntpntCd());	/** 출고조직ID */
					procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
					
					agncyPoOutMapper.regInOutMst2(procVo);
					
					
					/* 입출고REL등록 */
					procVo = new AgncyPoOutVO();
					procVo.setInoutId(dataVo.getInoutId());
					procVo.setJobCd(lgsMgmtMapper.getInvtSrl(LgsComCode.SEQ_SL));
					procVo.setJobIndCd(LgsSrEnum.SNR_SALE);
					procVo.setUserId(dataVo.getUserId());
					
					agncyPoOutMapper.regInOutRel(procVo);
					
					
					/** 재고수량변경 **/
					mgmtVo = new LgsMgmtVO();
					
					mgmtVo.setOrgnId(dataVo.getPrvCntpntCd());	/** 입고조직ID */
					mgmtVo.setPrdtId(dataVo.getPrvPrdtId());	/** 기기ID */
					mgmtVo.setOldYn(dataVo.getPrvOldYn());		/** 중고여부 */
					mgmtVo.setGoodStrgInvtrQnty(1);				/** 양품재고수량 */
					mgmtVo.setNgStrgInvtrQnty(0);				/** 불량재고수량 */
					mgmtVo.setInDueQnty(0);						/** 입고예정수량 */
					mgmtVo.setOutRsvQnty(0);					/** 출고예정수량 */
					mgmtVo.setUserId(dataVo.getUserId());		/** 사용자ID */
					
					if(lgsMgmtMapper.updateInvtrDayMst(mgmtVo) <= 0){
						lgsMgmtMapper.insertInvtrDayMst(mgmtVo);
					}
					
				}
				/** 기존단말 : 타사일경우 **/
				else if( LgsComCode.YN_N.equals(dataVo.getPrvMycpYn()) ) {
					
					
					/** 원부변경 **/
					LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
					
					prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
					prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
					prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_04.getSnrCode());		/** 물류처리상태코드 : 판매해지 */
					prevIntmVo.setSvcCtNum("000000000");					/** 가입계약번호 */
					prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
					prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
					prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
					prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
					prevIntmVo.setCanDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 해지일자 **/
					prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
					
					lgsMgmtMapper.updateLgsOtcpSrlMst(prevIntmVo);
					
					
					/** 재고수량변경 **/
					mgmtVo = new LgsMgmtVO();
					
					mgmtVo.setOrgnId(dataVo.getPrvCntpntCd());	/** 입고조직ID */
					mgmtVo.setPrdtId(dataVo.getPrvPrdtId());	/** 기기ID */
					mgmtVo.setOldYn(dataVo.getPrvOldYn());		/** 중고여부 */
					mgmtVo.setGoodStrgInvtrQnty(1);				/** 양품재고수량 */
					mgmtVo.setNgStrgInvtrQnty(0);				/** 불량재고수량 */
					mgmtVo.setInDueQnty(0);						/** 입고예정수량 */
					mgmtVo.setOutRsvQnty(0);					/** 출고예정수량 */
					mgmtVo.setSaleQty(0);						/** 판매수량 */
					mgmtVo.setCanQty(1);						/** 해지수량 */
					mgmtVo.setUserId(dataVo.getUserId());		/** 사용자ID */
					
					lgsMgmtMapper.mgrOtcpInvMst(mgmtVo);
					
				}
				
			}
			
		}
		
		/** 개통이력변경 **/
		lgsNstepIfMapper.updOpenHstInfo(dataVo);
		
		
		/** 개통이력생성 **/
		LOGGER.debug(LgsOpenEnum.PRC920.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC920.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC920.getStat());
		lgsNstepIfMapper.insOpenHstInfo(dataVo);
		
		
		/** 상세 조회 데이터 이력 생성 **/
		LOGGER.debug(LgsOpenEnum.PRC999.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC999.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC999.getStat());
		lgsNstepIfMapper.updLinkData(dataVo);
		lgsNstepIfMapper.insChckDataInfo(dataVo);
		
	}
	
	
	/*****************************************
	 * @Description : 해지 데이터 상세 체크  *
	 * @Author : IB                          *
	 * @Create Date : 2015. 10. 8.           *
	 *****************************************/
	@Transactional(rollbackFor=Exception.class)
	public LgsNstepIfVo getCheckedCanProc(LgsNstepIfVo dataVo) {
		
		// [SKIP] 미등록조직
		if( LgsOpenEnum.ORG_STAT_NON.equals(dataVo.getCntpntStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC801.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC801.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC801.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [SKIP] 비판매조직
		if( LgsOpenEnum.ORG_STAT_NSL.equals(dataVo.getCntpntStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC802.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC802.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC802.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
			
		}
		
		// [SKIP] 비가동조직
		if( LgsOpenEnum.ORG_STAT_NLV.equals(dataVo.getCntpntStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC803.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC803.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC803.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
			
		}
		
		// [CHCK] 해지:사유코드상이
		if( !LgsOpenEnum.EVNT_RSN_CN.equals(dataVo.getEvntRsnCd())
			|| dataVo.getEvntRsnCd() != null
			|| !"".equals(dataVo.getEvntRsnCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC301.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC301.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC301.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
		}
		
		// [CHCK] 해지:이력미존재
		if( LgsComCode.YN_N.equals(dataVo.getHstYn()) ) {
			LOGGER.debug(LgsOpenEnum.PRC302.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC302.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC302.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 해지:해지이력존재
		if( LgsOpenEnum.EVNT_CAN.equals(dataVo.getPrvEvntCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC303.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC303.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC303.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 해지:재고상태상이
		if( !LgsOpenEnum.INV_STAT_SLS.equals(dataVo.getInvStatCd())
			&& !LgsOpenEnum.INV_STAT_SLS.equals(dataVo.getPrvInvStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC304.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC304.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC304.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [EXEC] 처리예외전문
		LOGGER.debug(LgsOpenEnum.PRC000.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC000.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC000.getStat());
		lgsNstepIfMapper.updLinkData(dataVo);
		lgsNstepIfMapper.insChckDataInfo(dataVo);
		return new LgsNstepIfVo();
	}
	
	
	/*********************************************
	 * 해지처리(CAN)
	 * @Author : IB
	 * @Create Date : 2015. 8. 14.
	 *********************************************/
	@Transactional(rollbackFor=Exception.class)
	public void intmCanProc(LgsNstepIfVo dataVo) {
		
		
		/** CASE : 자사단말 **/
		if( LgsComCode.YN_Y.equals(dataVo.getMycpYn()) ) {
			
			
			/** 판매철회일경우 **/
			if( LgsComCode.YN_Y.equals(dataVo.getRtnYn()) ) {
				
				
				/** 기기정보세팅 **/
				dataVo.setInoutId(lgsNstepIfMapper.getInvtSrl(LgsComCode.SEQ_IO));		/** 입출고시퀀스생성 */
				dataVo.setEvntRsnCd(LgsOpenEnum.EVNT_RSN_CC);							/** 해지처리 : 판매철회 */
				
				
				/** 원부변경 **/
				LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
				
				intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
				intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
				intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_04.getSnrCode());		/** 물류처리상태코드 : 판매해지 */
				intmVo.setLastInoutId(dataVo.getInoutId());		/** 최종입출고ID */
				intmVo.setSvcCtNum("000000000");				/** 가입계약번호 */
				intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
				intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
				intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
				intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
				intmVo.setCanDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 해지일자 **/
				intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
				
				lgsMgmtMapper.insertPrdtSnapshot(intmVo);
				lgsMgmtMapper.updateLgsPrdtSrlMst(intmVo);
				
				
				/** 입출고생성 **/
				/* 입출고SRL등록 */
				AgncyPoOutVO procVo = new AgncyPoOutVO();
				
				procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
				procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
				procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
				procVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 모델일련번호 */
				procVo.setOutUnitPric(dataVo.getUnitPric());	/** 출고단가 **/
				procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
				
				agncyPoOutMapper.regInOutPrdtSrl(procVo);
				
				
				/* 입출고PRDT등록 */
				procVo = new AgncyPoOutVO();
				
				procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
				procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
				procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
				procVo.setRegQnty(1);							/** 수량 */
				procVo.setOutAmt(dataVo.getUnitPric());			/** 금액 */
				procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
				
				agncyPoOutMapper.regInOutPrdt(procVo);
				
				
				/* 입출고MST등록 */
				procVo = new AgncyPoOutVO();
				
				procVo.setInoutId(dataVo.getInoutId());		/** 입출고ID */
				procVo.setInoutDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 입출고일자 */
				procVo.setInoutTypeCd(LgsSrEnum.SNR_SALE_04.getSnrType());		/** 입출고유형코드 : 입고 */
				procVo.setInoutTypeDtlCd(LgsSrEnum.SNR_SALE_04.getSnrCode());	/** 입출고유형상세코드 : 판매해지 */
				procVo.setInOrgnId(dataVo.getCntpntCd());	/** 입고조직ID */
				procVo.setOutOrgnId(dataVo.getCntpntCd());	/** 출고조직ID */
				procVo.setUserId(dataVo.getUserId());		/** 사용자ID */
				
				agncyPoOutMapper.regInOutMst2(procVo);
				
				
				/* 입출고REL등록 */
				procVo = new AgncyPoOutVO();
				procVo.setInoutId(dataVo.getInoutId());
				procVo.setJobCd(lgsMgmtMapper.getInvtSrl(LgsComCode.SEQ_SL));
				procVo.setJobIndCd(LgsSrEnum.SNR_SALE);
				procVo.setUserId(dataVo.getUserId());
				
				agncyPoOutMapper.regInOutRel(procVo);
				
				
				/** 재고수량변경 **/
				LgsMgmtVO mgmtVo = new LgsMgmtVO();
				
				mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 입고조직ID */
				mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
				mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
				mgmtVo.setGoodStrgInvtrQnty(1);			/** 양품재고수량 */
				mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
				mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
				mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
				mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
				
				if(lgsMgmtMapper.updateInvtrDayMst(mgmtVo) <= 0){
					lgsMgmtMapper.insertInvtrDayMst(mgmtVo);
				}
				
			}
			/** 일반해지일 경우 **/
			else {
				
				/** 기기정보세팅 **/
				dataVo.setEvntRsnCd(LgsOpenEnum.EVNT_RSN_CN);	/** 해지처리 : 일반해지 */
				
				
				/** 원부변경 **/
				LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
				
				intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
				intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
				intmVo.setSvcCtNum("000000000");				/** 가입계약번호 */
				intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
				intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
				intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
				intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
				intmVo.setCanDt("99991231");					/** 해지일자 **/
				intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
				
				lgsMgmtMapper.insertPrdtSnapshot(intmVo);
				lgsMgmtMapper.updateLgsPrdtSrlMst(intmVo);
				
			}
			
		}
		/** CASE : 타사단말 **/
		else if( LgsComCode.YN_N.equals(dataVo.getMycpYn()) ) {
			
			
			/** 판매철회일경우 **/
			if( LgsComCode.YN_Y.equals(dataVo.getRtnYn()) ) {
				
				/** 기기정보세팅 **/
				dataVo.setEvntRsnCd(LgsOpenEnum.EVNT_RSN_CC);	/** 해지처리 : 판매철회 */
				
				
				/** 원부변경 **/
				LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
				
				prevIntmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
				prevIntmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
				prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_04.getSnrCode());	/** 물류처리상태코드 : 판매해지 */
				prevIntmVo.setSvcCtNum("000000000");				/** 가입계약번호 */
				prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
				prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
				prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
				prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
				prevIntmVo.setCanDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 해지일자 **/
				prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
				
				lgsMgmtMapper.updateLgsOtcpSrlMst(prevIntmVo);
				
				
				/** 재고수량변경 **/
				LgsMgmtVO mgmtVo = new LgsMgmtVO();
				
				mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 입고조직ID */
				mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
				mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
				mgmtVo.setGoodStrgInvtrQnty(1);			/** 양품재고수량 */
				mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
				mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
				mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
				mgmtVo.setSaleQty(0);					/** 판매수량 */
				mgmtVo.setCanQty(1);					/** 해지수량 */
				mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
				
				lgsMgmtMapper.mgrOtcpInvMst(mgmtVo);
				
			}
			/** 일반해지일 경우 **/
			else {
				
				/** 기기정보세팅 **/
				dataVo.setEvntRsnCd(LgsOpenEnum.EVNT_RSN_CN);	/** 해지처리 : 일반해지 */
				
				
				/** 원부변경 **/
				LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
				
				prevIntmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
				prevIntmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
				prevIntmVo.setSvcCtNum("000000000");				/** 가입계약번호 */
				prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
				prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
				prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
				prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
				prevIntmVo.setCanDt("99991231");					/** 해지일자 **/
				prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
				
				lgsMgmtMapper.updateLgsOtcpSrlMst(prevIntmVo);
				
			}
			
		}
		
		/** 개통이력변경 **/
		lgsNstepIfMapper.updOpenHstInfo(dataVo);
		
		
		/** 개통이력생성 **/
		LgsNstepIfVo procVo = dataVo;
		dataVo.setPrvRprsPrdtId("");
		dataVo.setPrvPrdtId("");
		dataVo.setPrvPrdtSrlNum("");
		LOGGER.debug(LgsOpenEnum.PRC930.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC930.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC930.getStat());
		lgsNstepIfMapper.insOpenHstInfo(dataVo);
		
		
		/** 상세 조회 데이터 이력 생성 **/
		LOGGER.debug(LgsOpenEnum.PRC999.getDesc());
		procVo.setProcCd(LgsOpenEnum.PRC999.getCode());
		procVo.setProcStatCd(LgsOpenEnum.PRC999.getStat());
		lgsNstepIfMapper.updLinkData(procVo);
		lgsNstepIfMapper.insChckDataInfo(procVo);
		
	}
	
	
	/*********************************************
	 * @Description : 해지복구 데이터 상세 체크  *
	 * @Author : IB                              *
	 * @Create Date : 2015. 10. 8.               *
	 *********************************************/
	@Transactional(rollbackFor=Exception.class)
	public LgsNstepIfVo getCheckedRstProc(LgsNstepIfVo dataVo) {
		
		// [SKIP] 미등록조직
		if( LgsOpenEnum.ORG_STAT_NON.equals(dataVo.getCntpntStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC801.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC801.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC801.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [SKIP] 비판매조직
		if( LgsOpenEnum.ORG_STAT_NSL.equals(dataVo.getCntpntStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC802.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC802.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC802.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [SKIP] 비가동조직
		if( LgsOpenEnum.ORG_STAT_NLV.equals(dataVo.getCntpntStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC803.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC803.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC803.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 해지복구:사유코드상이
		if( !LgsOpenEnum.EVNT_RSN_CR.equals(dataVo.getEvntRsnCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC401.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC401.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC401.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 해지복구:이력미존재
		if( LgsComCode.YN_N.equals(dataVo.getHstYn()) ) {
			LOGGER.debug(LgsOpenEnum.PRC402.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC402.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC402.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 해지복구:해지이력미존재
		if( !LgsOpenEnum.EVNT_CAN.equals(dataVo.getPrvEvntCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC403.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC403.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC403.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 해지복구:복구일자만료
		if( LgsComCode.YN_N.equals(dataVo.getRstYn()) ) {
			LOGGER.debug(LgsOpenEnum.PRC404.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC404.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC404.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 해지복구:재고조직상이
		if( !dataVo.getCntpntCd().equals(dataVo.getPrvCntpntCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC405.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC405.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC405.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [CHCK] 해지복구:재고상태상이
		if( !LgsOpenEnum.INV_STAT_REG.equals(dataVo.getInvStatCd())
			&& !LgsOpenEnum.INV_STAT_REG.equals(dataVo.getPrvInvStatCd()) ) {
			LOGGER.debug(LgsOpenEnum.PRC406.getDesc());
			dataVo.setProcCd(LgsOpenEnum.PRC406.getCode());
			dataVo.setProcStatCd(LgsOpenEnum.PRC406.getStat());
			lgsNstepIfMapper.updLinkData(dataVo);
			lgsNstepIfMapper.insChckDataInfo(dataVo);
			return new LgsNstepIfVo();
		}
		
		// [EXEC] 처리예외전문
		LOGGER.debug(LgsOpenEnum.PRC000.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC000.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC000.getStat());
		lgsNstepIfMapper.updLinkData(dataVo);
		lgsNstepIfMapper.insChckDataInfo(dataVo);
		return new LgsNstepIfVo();
	}
	
	
	/*********************************************
	 * 해지복구처리(RCL)
	 * @Author : IB
	 * @Create Date : 2015. 8. 14.
	 *********************************************/
	@Transactional(rollbackFor=Exception.class)
	public void intmRstProc(LgsNstepIfVo dataVo) {
		
		/** CASE : 자사단말 **/
		if( LgsComCode.YN_Y.equals(dataVo.getMycpYn()) ) {
			
			/** 기기정보세팅 **/
			dataVo.setInoutId(lgsNstepIfMapper.getInvtSrl(LgsComCode.SEQ_IO));		/** 입출고시퀀스생성 */
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
			
			intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			intmVo.setSvcCtNum(dataVo.getContractNum());	/** 가입계약번호 */
			intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_03.getSnrCode());		/** 물류처리상태코드 : 해지복구 */
			intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
			intmVo.setCanDt("00000000");					/** 해지일자 **/
			intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.insertPrdtSnapshot(intmVo);
			lgsMgmtMapper.updateLgsPrdtSrlMst(intmVo);
			
			
			/** 입출고생성 **/
			/* 입출고SRL등록 */
			AgncyPoOutVO procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 모델일련번호 */
			procVo.setOutUnitPric(dataVo.getUnitPric());	/** 출고단가 **/
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdtSrl(procVo);
			
			
			/* 입출고PRDT등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setRegQnty(1);							/** 수량 */
			procVo.setOutAmt(dataVo.getUnitPric());			/** 금액 */
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdt(procVo);
			
			
			/* 입출고MST등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());		/** 입출고ID */
			procVo.setInoutDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 입출고일자 */
			procVo.setInoutTypeCd(LgsSrEnum.SNR_SALE_03.getSnrType());		/** 입출고유형코드 : 판매 */
			procVo.setInoutTypeDtlCd(LgsSrEnum.SNR_SALE_03.getSnrCode());	/** 입출고유형상세코드 : 해지복구 */
			procVo.setInOrgnId(dataVo.getCntpntCd());	/** 입고조직ID */
			procVo.setOutOrgnId(dataVo.getCntpntCd());	/** 출고조직ID */
			procVo.setUserId(dataVo.getUserId());		/** 사용자ID */
			
			agncyPoOutMapper.regInOutMst2(procVo);
			
			
			/* 입출고REL등록 */
			procVo = new AgncyPoOutVO();
			procVo.setInoutId(dataVo.getInoutId());
			procVo.setJobCd(lgsMgmtMapper.getInvtSrl(LgsComCode.SEQ_SL));
			procVo.setJobIndCd(LgsSrEnum.SNR_SALE);
			procVo.setUserId(dataVo.getUserId());
			
			agncyPoOutMapper.regInOutRel(procVo);
			
			
			if( LgsComCode.YN_Y.equals(dataVo.getRtnYn()) ) {
				
				/** 재고수량변경 **/
				LgsMgmtVO mgmtVo = new LgsMgmtVO();
				
				mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 입고조직ID */
				mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
				mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
				mgmtVo.setGoodStrgInvtrQnty(-1);		/** 양품재고수량 */
				mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
				mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
				mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
				mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
				
				if(lgsMgmtMapper.updateInvtrDayMst(mgmtVo) <= 0){
					lgsMgmtMapper.insertInvtrDayMst(mgmtVo);
				}
			}
			
		}
		/** CASE : 타사단말 **/
		else {
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
			
			prevIntmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			prevIntmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			prevIntmVo.setSvcCtNum(dataVo.getContractNum());	/** 가입계약번호 */
			prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_03.getSnrCode());		/** 물류처리상태코드 : 해지복구 */
			prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
			prevIntmVo.setCanDt("00000000");					/** 해지일자 **/
			prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.updateLgsOtcpSrlMst(prevIntmVo);
			
			
			if( LgsComCode.YN_Y.equals(dataVo.getRtnYn()) ) {
				
				/** 재고수량변경 **/
				LgsMgmtVO mgmtVo = new LgsMgmtVO();
				
				mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 입고조직ID */
				mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
				mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
				mgmtVo.setGoodStrgInvtrQnty(-1);		/** 양품재고수량 */
				mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
				mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
				mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
				mgmtVo.setSaleQty(0);					/** 판매수량 */
				mgmtVo.setCanQty(-1);					/** 해지수량 */
				mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
				
				lgsMgmtMapper.mgrOtcpInvMst(mgmtVo);
			}
			
		}
		
		/** 개통이력변경 **/
		lgsNstepIfMapper.updOpenHstInfo(dataVo);
		
		
		/** 개통이력생성 **/
		LgsNstepIfVo procVo = dataVo;
		dataVo.setPrvRprsPrdtId("");
		dataVo.setPrvPrdtId("");
		dataVo.setPrvPrdtSrlNum("");
		LOGGER.debug(LgsOpenEnum.PRC940.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC940.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC940.getStat());
		lgsNstepIfMapper.insOpenHstInfo(dataVo);
		
		
		/** 상세 조회 데이터 이력 생성 **/
		LOGGER.debug(LgsOpenEnum.PRC999.getDesc());
		procVo.setProcCd(LgsOpenEnum.PRC999.getCode());
		procVo.setProcStatCd(LgsOpenEnum.PRC999.getStat());
		lgsNstepIfMapper.updLinkData(procVo);
		lgsNstepIfMapper.insChckDataInfo(procVo);
		
	}
	
	
	
	/*********************************************
	 * 우수기변처리(C07 / 01)
	 * @Author : TREXSHIN
	 * @Create Date : 2016. 10. 04.
	 *********************************************/
	@Transactional(rollbackFor=Exception.class)
	public void intmChngVpProc(LgsNstepIfVo dataVo) {
		
		
		/** CASE : 자사단말 **/
		if( LgsComCode.YN_Y.equals(dataVo.getMycpYn()) ) {
			
			
			/** 기기정보세팅 **/
			dataVo.setInoutId(lgsNstepIfMapper.getInvtSrl(LgsComCode.SEQ_IO));		/** 입출고시퀀스생성 */
			
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
			
			intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());	/** 물류처리상태코드 : 판매 */
			intmVo.setLastInoutId(dataVo.getInoutId());		/** 최종입출고ID */
			intmVo.setSvcCtNum(dataVo.getContractNum());	/** 가입계약번호 */
			intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
			intmVo.setSlsDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 판매일자 **/
			intmVo.setCanDt("00000000");					/** 해지일자 **/
			intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.insertPrdtSnapshot(intmVo);
			lgsMgmtMapper.updateLgsPrdtSrlMst(intmVo);
			
			
			/** 입출고생성 **/
			/* 입출고SRL등록 */
			AgncyPoOutVO procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 모델일련번호 */
			procVo.setOutUnitPric(dataVo.getUnitPric());	/** 출고단가 **/
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdtSrl(procVo);
			
			
			/* 입출고PRDT등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setRegQnty(1);							/** 수량 */
			procVo.setOutAmt(dataVo.getUnitPric());			/** 금액 */
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdt(procVo);
			
			
			/* 입출고MST등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());		/** 입출고ID */
			procVo.setInoutDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 입출고일자 */
			procVo.setInoutTypeCd(LgsSrEnum.SNR_SALE_01.getSnrType());	/** 입출고유형코드 : 대리점판매 */
			procVo.setInoutTypeDtlCd(LgsSrEnum.SNR_SALE_01.getSnrCode());	/** 입출고유형상세코드 : 판매 */
			procVo.setOutOrgnId(dataVo.getCntpntCd());	/** 출고조직ID */
			procVo.setUserId(dataVo.getUserId());		/** 사용자ID */
			
			agncyPoOutMapper.regInOutMst2(procVo);
			
			
			/* 입출고REL등록 */
			procVo = new AgncyPoOutVO();
			procVo.setInoutId(dataVo.getInoutId());
			procVo.setJobCd(lgsMgmtMapper.getInvtSrl(LgsComCode.SEQ_SL));
			procVo.setJobIndCd(LgsSrEnum.SNR_SALE);
			procVo.setUserId(dataVo.getUserId());
			
			agncyPoOutMapper.regInOutRel(procVo);
			
			
			/** 재고수량변경 **/
			LgsMgmtVO mgmtVo = new LgsMgmtVO();
			
			mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 입고조직ID */
			mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
			mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
			mgmtVo.setGoodStrgInvtrQnty(-1);		/** 양품재고수량 */
			mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
			mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
			mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
			mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
			
			if(lgsMgmtMapper.updateInvtrDayMst(mgmtVo) <= 0) {
				lgsMgmtMapper.insertInvtrDayMst(mgmtVo);
			}
			
			
			/** 기존단말 : 자사일경우 **/
			if( LgsComCode.YN_Y.equals(dataVo.getPrvMycpYn()) ) {
				
				
				/** 원부변경 **/
				LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
				
				prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
				prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
				prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());		/** 물류처리상태코드 : 판매 */
				prevIntmVo.setSvcCtNum("000000000");					/** 가입계약번호 */
				prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
				prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
				prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
				prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
				prevIntmVo.setCanDt("99991231");					/** 해지일자 **/
				prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
				
				lgsMgmtMapper.updateLgsPrdtSrlMst(prevIntmVo);
				
			}
			/** 기존단말 : 타사일경우 **/
			else {
				
				
				/** 원부변경 **/
				LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
				
				prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
				prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
				prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());		/** 물류처리상태코드 : 판매 */
				prevIntmVo.setSvcCtNum("000000000");					/** 가입계약번호 */
				prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
				prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
				prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
				prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
				prevIntmVo.setCanDt("99991231");					/** 해지일자 **/
				prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
				
				lgsMgmtMapper.updateLgsOtcpSrlMst(prevIntmVo);
				
			}
			
		}
		/** CASE : 타사단말 **/
		else {
			
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
			
			intmVo.setOutOrgnId(dataVo.getCntpntCd());		/** 출고조직ID */
			intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());		/** 물류처리상태코드 : 판매 */
			intmVo.setSvcCtNum(dataVo.getContractNum());	/** 가입계약번호 */
			intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			intmVo.setSlsDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 판매일자 **/
			intmVo.setCanDt("00000000");					/** 해지일자 **/
			intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
			intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.updateLgsOtcpSrlMst(intmVo);
			
			
			/** 재고수량변경 **/
			LgsMgmtVO mgmtVo = new LgsMgmtVO();
			
			mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 조직ID */
			mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
			mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
			mgmtVo.setGoodStrgInvtrQnty(-1);		/** 양품재고수량 */
			mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
			mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
			mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
			mgmtVo.setSaleQty(1);					/** 판매수량 */
			mgmtVo.setCanQty(0);					/** 해지수량 */
			mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
			
			lgsMgmtMapper.mgrOtcpInvMst(mgmtVo);
			
			
			/** 기존단말 : 자사일경우 **/
			if( LgsComCode.YN_Y.equals(dataVo.getPrvMycpYn()) ) {
				
				
				/** 원부변경 **/
				LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
				
				prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
				prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
				prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());		/** 물류처리상태코드 : 판매 */
				prevIntmVo.setSvcCtNum("000000000");					/** 가입계약번호 */
				prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
				prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
				prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
				prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
				prevIntmVo.setCanDt("99991231");					/** 해지일자 **/
				prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
				
				lgsMgmtMapper.updateLgsPrdtSrlMst(prevIntmVo);
				
			}
			/** 기존단말 : 타사일경우 **/
			else {
				
				
				/** 원부변경 **/
				LgsPrdtSrlMstVo prevIntmVo = new LgsPrdtSrlMstVo();
				
				prevIntmVo.setPrdtId(dataVo.getPrvPrdtId());			/** 기기ID */
				prevIntmVo.setPrdtSrlNum(dataVo.getPrvPrdtSrlNum());	/** 기기일련번호 */
				prevIntmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_01.getSnrCode());		/** 물류처리상태코드 : 판매 */
				prevIntmVo.setSvcCtNum("000000000");					/** 가입계약번호 */
				prevIntmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
				prevIntmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
				prevIntmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
				prevIntmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
				prevIntmVo.setCanDt("99991231");					/** 해지일자 **/
				prevIntmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
				
				lgsMgmtMapper.updateLgsOtcpSrlMst(prevIntmVo);
				
			}
			
		}
		
		/** 개통이력변경 **/
		lgsNstepIfMapper.updOpenHstInfo(dataVo);
		
		
		/** 개통이력생성 **/
		LOGGER.debug(LgsOpenEnum.PRC950.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC950.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC950.getStat());
		lgsNstepIfMapper.insOpenHstInfo(dataVo);
		
		
		/** 상세 조회 데이터 이력 생성 **/
		LOGGER.debug(LgsOpenEnum.PRC999.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC999.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC999.getStat());
		lgsNstepIfMapper.updLinkData(dataVo);
		lgsNstepIfMapper.insChckDataInfo(dataVo);
		
	}
	
	
	/*********************************************
	 * 우수기변취소처리(C07 / 07)
	 * @Author : TREXSHIN
	 * @Create Date : 2016. 10. 04.
	 *********************************************/
	@Transactional(rollbackFor=Exception.class)
	public void intmChngVpCanProc(LgsNstepIfVo dataVo) {
		
		
		/** CASE : 자사단말 **/
		if( LgsComCode.YN_Y.equals(dataVo.getMycpYn()) ) {
			
			
			/** 기기정보세팅 **/
			dataVo.setInoutId(lgsNstepIfMapper.getInvtSrl(LgsComCode.SEQ_IO));		/** 입출고시퀀스생성 */
			
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
			
			intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_04.getSnrCode());	/** 물류처리상태코드 : 판매해지 */
			intmVo.setLastInoutId(dataVo.getInoutId());		/** 최종입출고ID */
			intmVo.setSvcCtNum("000000000");				/** 가입계약번호 */
			intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 **/
			intmVo.setCanDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 해지일자 **/
			intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.insertPrdtSnapshot(intmVo);
			lgsMgmtMapper.updateLgsPrdtSrlMst(intmVo);
			
			
			/** 입출고생성 **/
			/* 입출고SRL등록 */
			AgncyPoOutVO procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 모델일련번호 */
			procVo.setOutUnitPric(dataVo.getUnitPric());	/** 출고단가 **/
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdtSrl(procVo);
			
			
			/* 입출고PRDT등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());			/** 입출고ID */
			procVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			procVo.setOldYn(dataVo.getOldYn());				/** 중고여부 */
			procVo.setRegQnty(1);							/** 수량 */
			procVo.setOutAmt(dataVo.getUnitPric());			/** 금액 */
			procVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			agncyPoOutMapper.regInOutPrdt(procVo);
			
			
			/* 입출고MST등록 */
			procVo = new AgncyPoOutVO();
			
			procVo.setInoutId(dataVo.getInoutId());		/** 입출고ID */
			procVo.setInoutDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 입출고일자 */
			procVo.setInoutTypeCd(LgsSrEnum.SNR_SALE_04.getSnrType());	/** 입출고유형코드 : 입고 */
			procVo.setInoutTypeDtlCd(LgsSrEnum.SNR_SALE_04.getSnrCode());	/** 입출고유형상세코드 : 판매해지 */
			procVo.setInOrgnId(dataVo.getCntpntCd());	/** 입고조직ID */
			procVo.setOutOrgnId(dataVo.getCntpntCd());	/** 출고조직ID */
			procVo.setUserId(dataVo.getUserId());		/** 사용자ID */
			
			agncyPoOutMapper.regInOutMst2(procVo);
			
			
			/* 입출고REL등록 */
			procVo = new AgncyPoOutVO();
			procVo.setInoutId(dataVo.getInoutId());
			procVo.setJobCd(lgsMgmtMapper.getInvtSrl(LgsComCode.SEQ_SL));
			procVo.setJobIndCd(LgsSrEnum.SNR_SALE);
			procVo.setUserId(dataVo.getUserId());
			
			agncyPoOutMapper.regInOutRel(procVo);
			
			
			/** 재고수량변경 **/
			LgsMgmtVO mgmtVo = new LgsMgmtVO();
			
			mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 입고조직ID */
			mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
			mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
			mgmtVo.setGoodStrgInvtrQnty(1);			/** 양품재고수량 */
			mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
			mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
			mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
			mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
			
			if(lgsMgmtMapper.updateInvtrDayMst(mgmtVo) <= 0) {
				lgsMgmtMapper.insertInvtrDayMst(mgmtVo);
			}
			
			
		}
		/** CASE : 타사단말 **/
		else {
			
			
			/** 원부변경 **/
			LgsPrdtSrlMstVo intmVo = new LgsPrdtSrlMstVo();
			
			intmVo.setPrdtId(dataVo.getPrdtId());			/** 기기ID */
			intmVo.setPrdtSrlNum(dataVo.getPrdtSrlNum());	/** 기기일련번호 */
			intmVo.setLogisProcStatCd(LgsSrEnum.SNR_SALE_04.getSnrCode());		/** 물류처리상태코드 : 판매해지 */
			intmVo.setSvcCtNum(dataVo.getContractNum());	/** 가입계약번호 */
			intmVo.setSvcProcStatCd(dataVo.getAplEvntCd());	/** 이벤트코드 */
			intmVo.setSvcOpenDt(dataVo.getApplStrtDttm().substring(0, 8));	/** 서비스계약일자 */
			intmVo.setSvcOpenTm(dataVo.getApplStrtDttm().substring(8, 14));	/** 서비스계약일시 */
			intmVo.setIfQueNum(dataVo.getIfQueNum());		/** 인터페이스번호 */
			intmVo.setCanDt(dataVo.getApplStrtDttm().substring(0, 8));		/** 해지일자 **/
			intmVo.setUserId(dataVo.getUserId());			/** 사용자ID */
			
			lgsMgmtMapper.updateLgsOtcpSrlMst(intmVo);
			
			
			/** 재고수량변경 **/
			LgsMgmtVO mgmtVo = new LgsMgmtVO();
			
			mgmtVo.setOrgnId(dataVo.getCntpntCd());	/** 조직ID */
			mgmtVo.setPrdtId(dataVo.getPrdtId());	/** 기기ID */
			mgmtVo.setOldYn(dataVo.getOldYn());		/** 중고여부 */
			mgmtVo.setGoodStrgInvtrQnty(1);			/** 양품재고수량 */
			mgmtVo.setNgStrgInvtrQnty(0);			/** 불량재고수량 */
			mgmtVo.setInDueQnty(0);					/** 입고예정수량 */
			mgmtVo.setOutRsvQnty(0);				/** 출고예정수량 */
			mgmtVo.setSaleQty(0);					/** 판매수량 */
			mgmtVo.setCanQty(1);					/** 해지수량 */
			mgmtVo.setUserId(dataVo.getUserId());	/** 사용자ID */
			
			lgsMgmtMapper.mgrOtcpInvMst(mgmtVo);
			
			
		}
		
		/** 개통이력변경 **/
		lgsNstepIfMapper.updOpenHstInfo(dataVo);
		
		
		/** 개통이력생성 **/
		LgsNstepIfVo procVo = dataVo;
		dataVo.setPrvRprsPrdtId("");
		dataVo.setPrvPrdtId("");
		dataVo.setPrvPrdtSrlNum("");
		LOGGER.debug(LgsOpenEnum.PRC960.getDesc());
		dataVo.setProcCd(LgsOpenEnum.PRC960.getCode());
		dataVo.setProcStatCd(LgsOpenEnum.PRC960.getStat());
		lgsNstepIfMapper.insOpenHstInfo(dataVo);
		
		
		/** 상세 조회 데이터 이력 생성 **/
		LOGGER.debug(LgsOpenEnum.PRC999.getDesc());
		procVo.setProcCd(LgsOpenEnum.PRC999.getCode());
		procVo.setProcStatCd(LgsOpenEnum.PRC999.getStat());
		lgsNstepIfMapper.updLinkData(procVo);
		lgsNstepIfMapper.insChckDataInfo(procVo);
		
	}
	
}