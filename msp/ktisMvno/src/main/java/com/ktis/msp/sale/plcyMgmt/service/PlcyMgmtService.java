package com.ktis.msp.sale.plcyMgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.vo.MsgQueueVO;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.MsgQueueReqVO;
import com.ktis.msp.sale.plcyMgmt.common.PolicyConstants;
import com.ktis.msp.sale.plcyMgmt.mapper.PlcyMgmtMapper;
import com.ktis.msp.sale.plcyMgmt.vo.PlcyMgmtVO;
import com.ktis.msp.sale.plcyMgmt.vo.PolicyLogVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class PlcyMgmtService extends BaseService {
	
	@Autowired
	private PlcyMgmtMapper plcyMgmtMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private LoginService loginService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	/**
	 * 정책조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getPlcyMgmtList(PlcyMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		List<EgovMap> result = (List<EgovMap>) plcyMgmtMapper.getPlcyMgmtList(searchVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	/**
	 * 정책엑셀다운로드
	 * @param searchVO
	 * @return
	 */
	public List<?> getPlcyMgmtListExcel(PlcyMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return plcyMgmtMapper.getPlcyMgmtListExcel(searchVO);
	}
	
	/**
	 * 대리점대상조회
	 * @return
	 */
	public List<?> getAgncyTrgtList(PlcyMgmtVO searchVO){
		return plcyMgmtMapper.getAgncyTrgtList(searchVO);
	}
	
	/**
	 * 요금제대상조회
	 * @return
	 */
	public List<?> getRateTrgtList(PlcyMgmtVO searchVO){
		return plcyMgmtMapper.getRateTrgtList(searchVO);
	}
	
	/**
	 * 제품대상조회
	 * @return
	 */
	public List<?> getPrdtTrgtList(PlcyMgmtVO searchVO){
		return plcyMgmtMapper.getPrdtTrgtList(searchVO);
	}
	
	/**
	 * 요금제ARPU대상조회
	 * @return
	 */
	public List<?> getRateArpuTrgtList(PlcyMgmtVO searchVO){
		return plcyMgmtMapper.getRateArpuTrgtList(searchVO);
	}
	
	/**
	 * 정책보조금정보조회
	 * @param map
	 * @return
	 */
	public Map<String, Object> getPlcySubsdAmtList(Map<String, Object> map){
		return plcyMgmtMapper.getPlcySubsdAmtList(map);
	}
	
	/**
	 * 정책생성
	 * @param searchVO
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String setSalePlcyMst(PlcyMgmtVO searchVO){
		
		checkPlcyEventLog(searchVO.getSalePlcyCd());
		
		// 정책진행중 여부 확인
		if(plcyMgmtMapper.getSalePlcyApplChk(searchVO) > 0){
			throw new MvnoRunException(-1, "확정되어 판매된 정책은 수정할 수 없습니다");
		}
		
		if(searchVO.getSalePlcyNm() == null || searchVO.getSalePlcyNm().equals("")){
			throw new MvnoRunException(-1, "정책명이 존재하지 않습니다");
		}
		
		if(searchVO.getSaleStrtDt() == null || searchVO.getSaleStrtDt().equals("")){
			throw new MvnoRunException(-1, "정책시작일자가 존재하지 않습니다");
		}
		
		if(searchVO.getSaleStrtTm() == null || searchVO.getSaleStrtTm().equals("")){
			throw new MvnoRunException(-1, "정책시작시간이 존재하지 않습니다");
		}
		
		if(searchVO.getSaleEndDt() == null || searchVO.getSaleEndDt().equals("")){
			throw new MvnoRunException(-1, "정책종료일자가 존재하지 않습니다");
		}
		
		if(searchVO.getSaleEndTm() == null || searchVO.getSaleEndTm().equals("")){
			throw new MvnoRunException(-1, "정책종료시간이 존재하지 않습니다");
		}
		
		if(searchVO.getPlcySctnCd().equals("01") && (searchVO.getInstRate() == null || searchVO.getInstRate().equals(""))){
			throw new MvnoRunException(-1, "할부수수료율이 존재하지 않습니다");
		}
		
		if(searchVO.getPlcySctnCd().equals("01") && (searchVO.getSprtTp() == null || searchVO.getSprtTp().equals(""))){
			throw new MvnoRunException(-1, "지원금유형이 존재하지 않습니다");
		}
		
		try{
			
			// 정책코드 조회
			if(searchVO.getFlag() == null || searchVO.getFlag().equals("I")) {
				searchVO.setSalePlcyCd(plcyMgmtMapper.getSalePlcyCd(searchVO));
			}
			
			// merge into
			plcyMgmtMapper.insertSalePlcyMst(searchVO);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			
			throw new MvnoRunException(-1, "정책생성 중 오류가 발생하였습니다");
		}
		
		return searchVO.getSalePlcyCd();
	}
	
	/**
	 * 정책생성
	 * @param searchVO
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	@Async
	public void setSalePlcyInfo(PlcyMgmtVO searchVO, Map<String, Object> paramMap) {
		
		String plcyEventCd = PolicyConstants.PLCY_EVENT_1003;
		if(searchVO.getFlag() == null || searchVO.getFlag().equals("I")) {
			plcyEventCd = PolicyConstants.PLCY_EVENT_1001;
		}
		// 정책 이벤트 로그 insert
		PolicyLogVO policyLogVO = insertSaleEventLog(searchVO.getSalePlcyCd(), searchVO.getSessionUserId(), plcyEventCd);
		
		try{
			
			// 정책제품
			this.setSalePrdtMst(searchVO, paramMap);
			updateSaleEventLog(policyLogVO, "정책생성 - 판매정책단말 완료");
			
			// 정책요금제
			this.setSaleRateMst(searchVO, paramMap);
			updateSaleEventLog(policyLogVO, "정책생성 - 판매정책요금제 완료");
			
			// 정책약정기간
			this.setSaleAgrmMst(searchVO, paramMap);
			updateSaleEventLog(policyLogVO, "정책생성 - 판매정책약정기간 완료");
			
			// 정책할부개월수
			this.setSaleInstMst(searchVO, paramMap);
			updateSaleEventLog(policyLogVO, "정책생성 - 판매정책할부개월수 완료");
			
			// 정책업무유형
			this.setSaleOperMst(searchVO, paramMap);
			updateSaleEventLog(policyLogVO, "정책생성 - 판매정책업무유형 완료");
			
			// 정책대리점
			this.setSaleOrgnMst(searchVO, paramMap);
			updateSaleEventLog(policyLogVO, "정책생성 - 판매정책대리점 완료");
			
			// 정책arpu
			this.setSaleArpuMst(searchVO, paramMap);
			updateSaleEventLog(policyLogVO, "정책생성 - 판매정책ARPU 완료");
			
			searchVO.setActionType(PolicyConstants.PLCY_ACTION_M);
			searchVO.setPlcyEventCd(plcyEventCd);
			// 정책보조금
			plcyMgmtMapper.callPlcyDetail(searchVO);
			if(searchVO.getResultCd().equals("OK")) {
				updateEndSaleEventLog(policyLogVO, "정책생성 - 판매정책지원금 완료");
			} else {
				throw new MvnoRunException(-1, searchVO.getResultCd());
			}
			
			/*PlcyMgmtVO vo = new PlcyMgmtVO();
			vo.setSalePlcyCd(searchVO.getSalePlcyCd());
			vo.setInstRate(searchVO.getInstRate());
			vo.setUserId(searchVO.getUserId());
			vo.setSprtTp(searchVO.getSprtTp());
			
			this.setSaleSubsdMst(vo);
			
			this.setMsgQueue(searchVO.getUserId(), searchVO.getSalePlcyCd());*/
		} catch(MvnoRunException e) {
			logger.info(e.getMessage());
			updateEndSaleEventLog(policyLogVO, e.getMessage());
			throw e;
		} catch(Exception e) {
			logger.info(e.getMessage());
			updateEndSaleEventLog(policyLogVO, "정책생성 중 오류가 발생하였습니다");
			throw new MvnoRunException(-1, "정책생성 중 오류가 발생하였습니다");
		}	
		
	}
	
	/**
	 * 정책제품생성
	 * @param flag
	 * @param salePlcyCd
	 * @param prdtIds
	 */
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void setSalePrdtMst(PlcyMgmtVO vo, Map<String, Object> paramMap) {
		if(vo.getFlag() != null && vo.getFlag().equals("U")) {
			plcyMgmtMapper.deleteSalePrdtMst(vo.getSalePlcyCd());
		}
		
		String[] prdtIds = paramMap.get("prdtId").toString().split("&");
		
		for(int i=0; i<prdtIds.length; i++) {
			
			String[] prdtInfo = prdtIds[i].split(":");
			
			vo.setPrdtId(prdtInfo[0]);
			vo.setOldYn(prdtInfo[1]);
			vo.setNewCmsnAmt(Integer.parseInt(prdtInfo[2]));
			vo.setMnpCmsnAmt(Integer.parseInt(prdtInfo[3]));
			vo.setHcnCmsnAmt(Integer.parseInt(prdtInfo[4]));
			vo.setHdnCmsnAmt(Integer.parseInt(prdtInfo[5]));
			
			plcyMgmtMapper.insertSalePrdtMst(vo);
			
		}
	}
	
	/**
	 * 정책요금제생성
	 * @param flag
	 * @param salePlcyCd
	 * @param rateCds
	 */
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void setSaleRateMst(PlcyMgmtVO vo, Map<String, Object> paramMap){
		if(vo.getFlag() != null && vo.getFlag().equals("U")) {
			plcyMgmtMapper.deleteSaleRateMst(vo.getSalePlcyCd());
		}
		
		String[] rateCds = paramMap.get("rateCd").toString().split("&");
		
		for(int i=0; i<rateCds.length; i++) {
			vo.setRateCd(rateCds[i]);
			
			plcyMgmtMapper.insertSaleRateMst(vo);
		}
	}
	
	/**
	 * 정책약정기간생성
	 * @param flag
	 * @param salePlcyCd
	 * @param agrmTrms
	 */
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void setSaleAgrmMst(PlcyMgmtVO vo, Map<String, Object> paramMap){
		if(vo.getFlag() != null && vo.getFlag().equals("U")) {
			plcyMgmtMapper.deleteSaleAgrmMst(vo.getSalePlcyCd());
		}
		
		String[] agrmTrms = paramMap.get("agrmTrm").toString().split("&");
		
		for(int i=0; i<agrmTrms.length; i++) {
			vo.setAgrmTrm(agrmTrms[i]);
			
			plcyMgmtMapper.insertSaleAgrmMst(vo);
		}
	}
	
	/**
	 * 정책업무유형생성
	 * @param flag
	 * @param salePlcyCd
	 * @param operTypes
	 */
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void setSaleOperMst(PlcyMgmtVO vo, Map<String, Object> paramMap){
		if(vo.getFlag() != null && vo.getFlag().equals("U")) {
			plcyMgmtMapper.deleteSaleOperMst(vo.getSalePlcyCd());
		}
		
		String[] operTypes = paramMap.get("operType").toString().split("&");
		
		for(int i=0; i<operTypes.length; i++) {
			vo.setOperType(operTypes[i]);
			
			plcyMgmtMapper.insertSaleOperMst(vo);
		}
	}
	
	
	/**
	 * 정책대리점생성
	 * @param flag
	 * @param salePlcyCd
	 * @param orgnIds
	 */
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void setSaleOrgnMst(PlcyMgmtVO vo, Map<String, Object> paramMap) {
		if(vo.getFlag() != null && vo.getFlag().equals("U")) {
			plcyMgmtMapper.deleteSaleOrgnMst(vo.getSalePlcyCd());
		}
		
		String[] orgnIds = paramMap.get("orgnId").toString().split("&");
		
		for(int i=0; i<orgnIds.length; i++) {
			vo.setOrgnId(orgnIds[i]);
			plcyMgmtMapper.insertSaleOrgnMst(vo);
		}
	}
	
	/**
	 * 정책ARPU생성
	 * @param flag
	 * @param salePlcyCd
	 * @param arpuCmsns
	 */
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void setSaleArpuMst(PlcyMgmtVO vo, Map<String, Object> paramMap){
		
		
		if(paramMap.get("arpuId").toString().length() == 0) {
			return;
		}
			
		if(vo.getFlag() != null && vo.getFlag().equals("U")) {
			plcyMgmtMapper.deleteSaleArpuMst(vo.getSalePlcyCd());
		}
//		if(!paramMap.containsKey("arpuId")){
//			return;
//		}
		
		String[] arpuIds = paramMap.get("arpuId").toString().split("&");

		for(int i=0; i<arpuIds.length; i++) {
			String[] arpuInfo = arpuIds[i].split(":");
			
			vo.setRateGrpCd(arpuInfo[0]);
			vo.setRateCd(arpuInfo[1]);
			vo.setArpuCmsnAmt(arpuInfo[2].equals("") ? 0 :Integer.parseInt(arpuInfo[2]));
			vo.setPrdtId(arpuInfo[3]);
			vo.setOldYn(arpuInfo[4]);
			vo.setRateType(arpuInfo[5]);
			vo.setDataType(arpuInfo[6]);
			
			if(vo.getPrdtId() == null || vo.getPrdtId().equals("") || vo.getOldYn() == null || vo.getOldYn().equals("")) {
				throw new MvnoRunException(-1, "ARPU 수수료 대상 제품정보가 존재하지 않습니다");
			}
			
			if(!vo.getRateCd().equals("")) {
				// 요금제코드로 수수료 세팅
				plcyMgmtMapper.insertSaleArpuMst(vo);
			} else {
				// 요금제그룹으로 수수료 세팅된 경우
				List<?> list = plcyMgmtMapper.getRateGrpRateCdList(vo);
				for(int j=0; j<list.size(); j++) {
					EgovMap map = (EgovMap) list.get(j);
					vo.setRateCd((String) map.get("rateCd"));
					
					plcyMgmtMapper.insertSaleArpuMst(vo);
				}
			}
		}

	}
	
	/**
	 * 정책보조금생성
	 * @param flag
	 * @param salePlcyCd
	 * @param subsdList
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setSaleSubsdMst(PlcyMgmtVO vo) {
		if(vo.getFlag() != null && vo.getFlag().equals("U")) {
			plcyMgmtMapper.deleteSaleSubsdMst(vo.getSalePlcyCd());
		}
		
		logger.debug("vo=" + vo);
		logger.debug("vo[addRateYn]=" + vo.getAddRateYn());
		
		plcyMgmtMapper.insertSaleSubsdMst(vo);
	}
	
	/**
	 * 정책보조금조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getPlcySubsdList(PlcyMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		//return plcyMgmtMapper.getPlcySubsdList(searchVO);
		return plcyMgmtMapper.getPlcySubsdListPart(searchVO);
	}
	
	/**
	 * 정책대리점조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getSaleOrgnList(PlcyMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return plcyMgmtMapper.getSaleOrgnList(searchVO);
	}
	
	
	/**
	 * 정책요금제조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getSaleRateList(PlcyMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return plcyMgmtMapper.getSaleRateList(searchVO);
	}
	
	/**
	 * 정책개통유형조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getSaleOperList(PlcyMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return plcyMgmtMapper.getSaleOperList(searchVO);
	}
	
	
	/**
	 * 정책약정기간조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getSaleAgrmList(PlcyMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return plcyMgmtMapper.getSaleAgrmList(searchVO);
	}
	
	/**
	 * 정책제품조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getSalePrdtList(PlcyMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return plcyMgmtMapper.getSalePrdtList(searchVO);
	}
	
	/**
	 * 정책arpu수수료조회 
	 * @param searchVO
	 * @return
	 */
	public List<?> getSaleArpuList(PlcyMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return plcyMgmtMapper.getSaleArpuList(searchVO);
	}
	
	/**
	 * 확정
	 * @param searchVO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setPlcyConfirm(PlcyMgmtVO searchVO){
		if(searchVO.getSalePlcyCd() == null || searchVO.getSalePlcyCd().equals("")){
			throw new MvnoRunException(-1, "판매정책코드가 존재하지 않습니다");
		}
		
		checkPlcyEventLog(searchVO.getSalePlcyCd());
		
		// 정책 이벤트 로그 insert
		PolicyLogVO policyLogVO = insertSaleEventLog(searchVO.getSalePlcyCd(), searchVO.getUserId(), PolicyConstants.PLCY_EVENT_1004);
		
		try {
			plcyMgmtMapper.setPlcyConfirm(searchVO);
			
			updateEndSaleEventLog(policyLogVO, "판매정책 확정 완료");
			
		} catch (Exception e) {
			updateEndSaleEventLog(policyLogVO, "판매정책 확정 실패!");
			throw new MvnoRunException(-1, "판매정책 확정 실패!");
		}
		
	}
	
	/**
	 * 확정취소
	 * @param searchVO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setPlcyCancel(PlcyMgmtVO searchVO){
		if(searchVO.getSalePlcyCd() == null || searchVO.getSalePlcyCd().equals("")){
			throw new MvnoRunException(-1, "판매정책코드가 존재하지 않습니다");
		}
		
		// 해당 정책으로 판매된 건이 있는지 조회
		if(plcyMgmtMapper.getSalePlcyApplChk(searchVO) > 0){
			throw new MvnoRunException(-1, "확정되어 판매된 정책은 수정할 수 없습니다");
		}
		
		checkPlcyEventLog(searchVO.getSalePlcyCd());
		
		// 정책 이벤트 로그 insert
		PolicyLogVO policyLogVO = insertSaleEventLog(searchVO.getSalePlcyCd(), searchVO.getUserId(), PolicyConstants.PLCY_EVENT_1005);
		
		try {
			// 확정상태 변경
			plcyMgmtMapper.setPlcyCancel(searchVO);
			
			updateEndSaleEventLog(policyLogVO, "판매정책 확정취소 완료");
			
		} catch (Exception e) {
			updateEndSaleEventLog(policyLogVO, "판매정책 확정취소 실패!");
			throw new MvnoRunException(-1, "판매정책 확정취소 실패!");
		}
		
	}
	
	/**
	 * 대리점보조금목록조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getAgncySubsdList(PlcyMgmtVO searchVO){
		logger.debug("searchVO=" + searchVO);
		
		// 본사조직이 아니면 대리점ID 입력 체크
		if((searchVO.getOrgnId() == null || searchVO.getOrgnId().equals("")) && !searchVO.getSessionUserOrgnTypeCd().equals("10")){
		
			throw new MvnoRunException(-1, "조직ID가 존재하지 않습니다");
		}
		
		if(searchVO.getStdrDt() == null || searchVO.getStdrDt().equals("")){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		return plcyMgmtMapper.getAgncySubsdList(searchVO);
	}
	
	/**
	 * 대리점보조금엑셀다운로드
	 * @param searchVO
	 * @return
	 */
	public List<?> getAgncySubsdListExcel(PlcyMgmtVO searchVO){
		logger.debug("searchVO=" + searchVO);
		
		// 본사조직이 아니면 대리점ID 입력 체크
		if((searchVO.getOrgnId() == null || searchVO.getOrgnId().equals("")) && !searchVO.getSessionUserOrgnTypeCd().equals("10")){
			throw new MvnoRunException(-1, "조직ID가 존재하지 않습니다");
		}
		
		if(searchVO.getStdrDt() == null || searchVO.getStdrDt().equals("")){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}

		return plcyMgmtMapper.getAgncySubsdListExcel(searchVO);
	}
	
	/**
	 * 대리점보조금수정
	 * @param searchVO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateAgncySubsdAmt(PlcyMgmtVO searchVO) {
		logger.debug("searchVO=" + searchVO);
		
		if(searchVO.getOrgnId() == null || searchVO.getOrgnId().equals("")){
			throw new MvnoRunException(-1, "조직ID가 존재하지 않습니다");
		}
		
		if(searchVO.getSalePlcyCd() == null || searchVO.getSalePlcyCd().equals("")){
			throw new MvnoRunException(-1, "판매정책코드가 존재하지 않습니다");
		}
		
		plcyMgmtMapper.updateAgncySubsdAmt(searchVO);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Async
	public void deleteSalePlcyMst(String salePlcyCd, String userId){
		if(salePlcyCd == null || salePlcyCd.equals("")){
			throw new MvnoRunException(-1, "판매정책코드가 존재하지 않습니다");
		}
		
		// 정책 이벤트 로그 insert
		PolicyLogVO policyLogVO = insertSaleEventLog(salePlcyCd, userId, PolicyConstants.PLCY_EVENT_1006);
		PlcyMgmtVO vo = new PlcyMgmtVO();
		
		try {
			plcyMgmtMapper.deleteSalePlcyMst(salePlcyCd);
			
			plcyMgmtMapper.deleteSaleRateMst(salePlcyCd);
			
			plcyMgmtMapper.deleteSalePrdtMst(salePlcyCd);
			
			plcyMgmtMapper.deleteSaleAgrmMst(salePlcyCd);
			
			plcyMgmtMapper.deleteSaleOperMst(salePlcyCd);
			
			plcyMgmtMapper.deleteSaleArpuMst(salePlcyCd);
			
			vo.setSalePlcyCd(salePlcyCd);
			vo.setUserId(userId);
			vo.setActionType(PolicyConstants.PLCY_ACTION_D);
			vo.setPlcyEventCd(PolicyConstants.PLCY_EVENT_1006);
			
			plcyMgmtMapper.callPlcyDetail(vo);
			if(!vo.getResultCd().equals("OK")) {
				throw new MvnoRunException(-1, vo.getResultCd());
			}
			
			plcyMgmtMapper.deleteSaleOrgnMst(salePlcyCd);
			
			updateEndSaleEventLog(policyLogVO, "판매정책 삭제 완료");
			
			this.setMsgQueue(vo.getUserId(), vo.getSalePlcyCd(), PolicyConstants.PLCY_ACTION_D);
			
		} catch (Exception e) {
			updateEndSaleEventLog(policyLogVO, "판매정책 삭제 실패.");
			throw new MvnoRunException(-1, "판매정책 삭제 실패.");
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Async
	public void initSalePlcyMst(String salePlcyCd, String userId){
		if(salePlcyCd == null || salePlcyCd.equals("")){
			throw new MvnoRunException(-1, "판매정책코드가 존재하지 않습니다");
		}
		
		// 정책 이벤트 로그 insert
		PolicyLogVO policyLogVO = insertSaleEventLog(salePlcyCd, userId, PolicyConstants.PLCY_EVENT_1002);
		
		PlcyMgmtVO vo = new PlcyMgmtVO();
		
		try {
			vo.setSalePlcyCd(salePlcyCd);
			vo.setUserId(userId);
			vo.setActionType(PolicyConstants.PLCY_ACTION_I);
			vo.setPlcyEventCd(PolicyConstants.PLCY_EVENT_1002);
			
			plcyMgmtMapper.callPlcyDetail(vo);
			if(!vo.getResultCd().equals("OK")) {
				throw new MvnoRunException(-1, vo.getResultCd());
			}
			
			updateEndSaleEventLog(policyLogVO, "판매정책 상세내역 초기화 완료");
			
			this.setMsgQueue(vo.getUserId(), vo.getSalePlcyCd(), PolicyConstants.PLCY_ACTION_I);
			
		} catch (Exception e) {
			updateEndSaleEventLog(policyLogVO, "판매정책 상세내역 초기화 실패.");
			throw new MvnoRunException(-1, "판매정책 상세내역 초기화 실패.");
		}
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updatePlcyMgmtClose(PlcyMgmtVO searchVO){
		if(searchVO.getSalePlcyCd() == null || searchVO.getSalePlcyCd().equals("")){
			throw new MvnoRunException(-1, "판매정책코드가 존재하지 않습니다");
		}
		
		checkPlcyEventLog(searchVO.getSalePlcyCd());
		
		// 정책 이벤트 로그 insert
		PolicyLogVO policyLogVO = insertSaleEventLog(searchVO.getSalePlcyCd(), searchVO.getSessionUserId(), PolicyConstants.PLCY_EVENT_1007);
		
		try {
			plcyMgmtMapper.updatePlcyMgmtClose(searchVO);
			
			updateEndSaleEventLog(policyLogVO, "판매정책 종료 완료");
			
		} catch (Exception e) {
			updateEndSaleEventLog(policyLogVO, "판매정책 종료 실패!");
			throw new MvnoRunException(-1, "판매정책 종료 실패!");
		}
		
	}
	
	public List<?> getPlcyAgncyAddList(PlcyMgmtVO searchVO) {
		if(searchVO.getSalePlcyCd() == null || searchVO.getSalePlcyCd().equals("")) {
			throw new MvnoRunException(-1, "판매정책코드가 존재하지 않습니다");
		}
		
		return plcyMgmtMapper.getPlcyAgncyAddList(searchVO);
	}
	
	/**
	 * @Description : 선불대리점 추가
	 * @Param  : PlcyMgmtVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 29.
	 */
	public List<?> getPlcyAgncyAddList2(PlcyMgmtVO searchVO) {
		if(searchVO.getSalePlcyCd() == null || searchVO.getSalePlcyCd().equals("")) {
			throw new MvnoRunException(-1, "판매정책코드가 존재하지 않습니다");
		}
		
		return plcyMgmtMapper.getPlcyAgncyAddList2(searchVO);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void setPlcyAgncyAdd(List<?> voList, LoginInfo loginInfo, String agencyType) {
		
		if(voList.size() > 0) {
			checkPlcyEventLog(((PlcyMgmtVO)voList.get(0)).getSalePlcyCd());
		} else {
			throw new MvnoRunException(-1, "추가할 대리점이 없습니다.");
		}
		
		String plcyEventCd = PolicyConstants.PLCY_EVENT_1008;
		if("addPreAgency".equals(agencyType)) {
			plcyEventCd = PolicyConstants.PLCY_EVENT_1009;
		}
		
		// 정책 이벤트 로그 insert
		PolicyLogVO policyLogVO = insertSaleEventLog(((PlcyMgmtVO)voList.get(0)).getSalePlcyCd(), loginInfo.getUserId(), plcyEventCd);
		
		try {
			for(int i=0; i<voList.size(); i++) {
				PlcyMgmtVO vo = (PlcyMgmtVO) voList.get(i);
				loginInfo.putSessionToVo(vo);
				
				if(vo.getSalePlcyCd() == null || vo.getSalePlcyCd().equals("")) {
					throw new MvnoRunException(-1, "판매정책코드가 존재하지 않습니다");
				}
				
				if(vo.getOrgnId() == null || vo.getOrgnId().equals("")) {
					throw new MvnoRunException(-1, "조직ID가 존재하지 않습니다");
				}
				vo.setUserId(loginInfo.getUserId());
				
				plcyMgmtMapper.insertSaleOrgnMst(vo);
				
				this.setSaleSubsdMst(vo);
			}
			updateEndSaleEventLog(policyLogVO, "대리점추가 저장 완료!");
			
		} catch(Exception e) {
			logger.info(e.getMessage());
			updateEndSaleEventLog(policyLogVO, "대리점추가 저장 실패!");
			throw new MvnoRunException(-1, "대리점추가 저장 중 오류가 발생하였습니다");
		}	
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Async
	public void setPlcyCopy(Map<String, Object> paramMap) throws EgovBizException {
		
		if(paramMap.get("salePlcyCd") == null || paramMap.get("salePlcyCd").equals("")) {
			throw new MvnoRunException(-1, "기존 판매정책코드가 존재하지 않습니다");
		}
		
		PlcyMgmtVO vo = new PlcyMgmtVO();
		PolicyLogVO policyLogVO = new PolicyLogVO();
		
		//vo.setPlcyTypeCd((String) paramMap.get("plcyTypeCd"));
		vo.setPlcyTypeCd((String) paramMap.get("newPlcyTypeCd"));
		
		// 신규정책코드조회
		paramMap.put("newSalePlcyCd", (String) plcyMgmtMapper.getSalePlcyCd(vo));
		
		logger.debug("paramMap=" + paramMap);
		
		try {
			// 기준정보생성
			copyPolicyMst(paramMap);
			
			// 정책 이벤트 로그 insert
			policyLogVO = insertSaleEventLog((String)paramMap.get("newSalePlcyCd"), (String)paramMap.get("SESSION_USER_ID"), PolicyConstants.PLCY_EVENT_1011);
			
			copyPolicyInfo(paramMap);
			
			// 정책보조금
			vo.setSalePlcyCd((String) paramMap.get("newSalePlcyCd"));
			vo.setUserId((String) paramMap.get("SESSION_USER_ID"));
			vo.setActionType(PolicyConstants.PLCY_ACTION_C);
			vo.setPlcyEventCd(PolicyConstants.PLCY_EVENT_1011);
			
			plcyMgmtMapper.callPlcyDetail(vo);
			if(vo.getResultCd().equals("OK")) {
				updateEndSaleEventLog(policyLogVO, "정책복사 - 판매정책지원금 완료");
			} else {
				throw new MvnoRunException(-1, vo.getResultCd());
			}
		} catch(MvnoRunException e) {
			logger.info(e.getMessage());
			updateEndSaleEventLog(policyLogVO, e.getMessage());
			throw e;
		} catch(Exception e) {
			logger.info(e.getMessage());
			updateEndSaleEventLog(policyLogVO, "정책복사 중 오류가 발생하였습니다");
			throw new MvnoRunException(-1, "정책복사 중 오류가 발생하였습니다");
		}
		
	}
	
	public void setMsgQueue(String userId, String salePlcyCd, String actionType) throws EgovBizException {
		
		try {
			String sendMsg = "";
			if(actionType.equals(PolicyConstants.PLCY_ACTION_I)) {
				sendMsg = "판매정책 상세내역 초기화가";
			} else if(actionType.equals(PolicyConstants.PLCY_ACTION_M)) {
				sendMsg = "정책생성이";
			} else if(actionType.equals(PolicyConstants.PLCY_ACTION_D)) {
				sendMsg = "정책삭제가";
			} else if(actionType.equals(PolicyConstants.PLCY_ACTION_C)) {
				sendMsg = "정책복사가";
			}
			
			KtMsgQueueReqVO vo = new KtMsgQueueReqVO();
			vo.setMsgType("1");
			vo.setMessage("[" + salePlcyCd + "] " + sendMsg + " 완료되었습니다.");
			vo.setCallbackNum(propertyService.getString("sms.callcenter"));
			vo.setRcptData(plcyMgmtMapper.getMblphnNum(userId));
			vo.setReserved01("MSP");
			vo.setReserved02("MSP2002012");
			vo.setReserved03(userId);
				
			loginService.insertKtMsgQueue(vo);
		} catch(Exception e) {
			throw new MvnoRunException(-1, "MSG_QUEUE 등록 실패!!!");
		}
		
	}

	// 정책생성후 문자전송
	public void setMsgQueue(String userId, String salePlcyCd) throws EgovBizException {
		
		try {
			MsgQueueReqVO msgVo = new MsgQueueReqVO();
			msgVo.setMsgType("1");
			msgVo.setDstaddr(plcyMgmtMapper.getMblphnNum(userId));
			msgVo.setCallback(propertyService.getString("sms.callcenter"));
			msgVo.setStat("0");
			msgVo.setText("[" + salePlcyCd + "] 정책생성이 완료되었습니다.");
			msgVo.setExpiretime("7200");
			msgVo.setOptId("MAKE_POLICY");
			
			smsMgmtMapper.insertMsgQueue(msgVo);
		} catch(Exception e) {
			throw new MvnoRunException(-1, "MSG_QUEUE 등록 실패!!!");
		}
		
	}
	
	/**
	 * 정책할부개월수생성
	 * @param flag
	 * @param salePlcyCd
	 * @param instTrms
	 */
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void setSaleInstMst(PlcyMgmtVO vo, Map<String, Object> paramMap){
		if(vo.getFlag() != null && vo.getFlag().equals("U")) {
			plcyMgmtMapper.deleteInstNom(vo.getSalePlcyCd());
		}
		
		String[] instTrms = paramMap.get("instTrm").toString().split("&");
		
		for(int i=0; i<instTrms.length; i++) {
			vo.setInstTrm(instTrms[i]);
			
			plcyMgmtMapper.insertInstNom(vo);
		}
	}
	
	/**
	 * 정책할부개월수조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getInstList(PlcyMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return plcyMgmtMapper.getInstList(searchVO);
	}
	
	/**
	 * 요금제 추가
	 * @param searchVO
	 * @return
	 */
	public List<?> getPlcyRateAddList(PlcyMgmtVO searchVO) {
		if(searchVO.getSalePlcyCd() == null || searchVO.getSalePlcyCd().equals("")) {
			throw new MvnoRunException(-1, "판매정책코드가 존재하지 않습니다");
		}
		
		return plcyMgmtMapper.getPlcyRateAddList(searchVO);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void setPlcyRateAdd(List<?> voList, LoginInfo loginInfo) {
		
		if(voList.size() > 0) {
			checkPlcyEventLog(((PlcyMgmtVO) voList.get(0)).getSalePlcyCd());
		} else {
			throw new MvnoRunException(-1, "추가할 요금제가 없습니다.");
		}
		
		// 정책 이벤트 로그 insert
		PolicyLogVO policyLogVO = insertSaleEventLog(((PlcyMgmtVO) voList.get(0)).getSalePlcyCd(), loginInfo.getUserId(), PolicyConstants.PLCY_EVENT_1010);
		
		try {
			for(int i=0; i<voList.size(); i++) {
				PlcyMgmtVO vo = (PlcyMgmtVO) voList.get(i);
				loginInfo.putSessionToVo(vo);
				
				if(vo.getSalePlcyCd() == null || vo.getSalePlcyCd().equals("")) {
					throw new MvnoRunException(-1, "판매정책코드가 존재하지 않습니다");
				}
				
				if(vo.getRateCd() == null || vo.getRateCd().equals("")) {
					throw new MvnoRunException(-1, "요금제코드가 존재하지 않습니다");
				}
				
				vo.setUserId(loginInfo.getUserId());
				vo.setAddRateYn("Y");
				
				plcyMgmtMapper.insertSaleRateMst(vo);
				
				this.setSaleSubsdMst(vo);
			}
			
			updateEndSaleEventLog(policyLogVO, "요금제추가 저장 완료!");
		} catch(Exception e) {
			logger.info(e.getMessage());
			updateEndSaleEventLog(policyLogVO, "요금제추가 저장 실패!");
			throw new MvnoRunException(-1, "요금제추가 저장 중 오류가 발생하였습니다");
		}
		
	}
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void copyPolicyMst(Map<String, Object> paramMap) throws EgovBizException {
		// 기준정보생성
		plcyMgmtMapper.insertSalePlcyMstCopy(paramMap);
	}
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void copyPolicyInfo(Map<String, Object> paramMap) throws EgovBizException {
		
		plcyMgmtMapper.insertSaleOrgnMstCopy(paramMap);
		
		plcyMgmtMapper.insertSaleOperMstCopy(paramMap);
		
		plcyMgmtMapper.insertSaleAgrmMstCopy(paramMap);
		
		plcyMgmtMapper.insertSaleRateMstCopy(paramMap);
		
		plcyMgmtMapper.insertSalePrdtMstCopy(paramMap);
		
		plcyMgmtMapper.insertSaleArpuMstCopy(paramMap);
		
		// 할부기간생성
		plcyMgmtMapper.insertInstNomCopy(paramMap);
		
	}
	
	public void checkPlcyEventLog(String salePlcyCd) {
		PolicyLogVO policyLogVO = new PolicyLogVO();
		policyLogVO.setSalePlcyCd(salePlcyCd);
		
		policyLogVO = plcyMgmtMapper.checkSaleEventLog(policyLogVO);
		
		if(policyLogVO != null && policyLogVO.getEndDttm() == null) {
			throw new MvnoRunException(-1, "정책[" + policyLogVO.getSalePlcyCd() + "]은 현재 " + policyLogVO.getPlcyEventNm() + " 진행중입니다.");
		}
	}
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public PolicyLogVO insertSaleEventLog(String salePlcyCd, String userId, String plcyEventCd) {
		PolicyLogVO policyLogVO = new PolicyLogVO();
		policyLogVO.setSalePlcyCd(salePlcyCd);
		policyLogVO.setSessionUserId(userId);
		policyLogVO.setPlcyEventCd(plcyEventCd);
		
		plcyMgmtMapper.insertSaleEventLog(policyLogVO);
		
		return policyLogVO;
	}
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void updateSaleEventLog(PolicyLogVO policyLogVO, String remrk) {
		policyLogVO.setRemrk(remrk);
		
		plcyMgmtMapper.updateSaleEventLog(policyLogVO);
	}
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void updateEndSaleEventLog(PolicyLogVO policyLogVO, String remrk) {
		policyLogVO.setRemrk(remrk);
		
		plcyMgmtMapper.updateEndSaleEventLog(policyLogVO);
	}
	
	public void checkPolicyConfirm(String salePlcyCd) {
		String confirmYn = plcyMgmtMapper.checkPolicyConfirm(salePlcyCd);
		if(confirmYn.equals("Y")) {
			throw new MvnoRunException(-1, "확정된 정책입니다. 확정취소 후 가능합니다.");
		}
	}
	

	/**
	 * @Description : 할부 개월 수 팝업 SELECT
	 * @Param  : PlcyMgmtVO
	 * @Return : List<?>
	 * @Author : 
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> selectInstNomPuList(PlcyMgmtVO plcyMgmtVO){
		
		List<?> salePlcyInstVoList = new ArrayList<PlcyMgmtVO>();
		
		salePlcyInstVoList = plcyMgmtMapper.selectInstNomPuList(plcyMgmtVO);
		
		return salePlcyInstVoList;
	}

	/**
	 * @Description : 할부 개월 수 삭제(할부기간 팝업)
	 * @Param  : PlcyMgmtVO
	 * @Return : int
	 * @Author : 
	 * @Create Date : 2014. 8. 14.
	 */
    @Transactional(rollbackFor=Exception.class)
	public int deleteInstNom(PlcyMgmtVO plcyMgmtVO){
		
		int returnCnt = 0;
		
		returnCnt = plcyMgmtMapper.deleteInstNomPu(plcyMgmtVO);
		
		return returnCnt;
	}	
    
	/**
	 * @Description : 할부 개월 수 등록(할부기간 팝업)
	 * @Param  : PlcyMgmtVO
	 * @Return : int
	 * @Author : 
	 * @Create Date : 2014. 8. 14.
	 */
    @Transactional(rollbackFor=Exception.class)
	public int insertInstNom(PlcyMgmtVO plcyMgmtVO){
		
		int returnCnt = 0;
		
		returnCnt = plcyMgmtMapper.insertInstNomPu(plcyMgmtVO);
		
		return returnCnt;
	}	
}

