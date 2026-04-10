package com.ktis.msp.rcp.chgRcptMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.chgRcptMgmt.mapper.ChgRcptMgmtMapper;
import com.ktis.msp.rcp.chgRcptMgmt.vo.ChgRcptMgmtVO;

@Service
public class ChgRcptMgmtService extends BaseService {

	@Autowired
	private ChgRcptMgmtMapper chgRcptMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	public List<?> getChgRcptList(ChgRcptMgmtVO vo){
		if(vo.getStrtDt() == null || "".equals(vo.getStrtDt())){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다");
		}
		
		if(vo.getEndDt() == null || "".equals(vo.getEndDt())){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다");
		}
		
		List<?> list = chgRcptMapper.getChgRcptList(vo);
		
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> pReqParamMap = new HashMap<String, Object>();
		pReqParamMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(list, maskFields, pReqParamMap);
		
		return list; 
	}
	
	@Transactional(rollbackFor=Exception.class)
	public List<?> getContractInfo(ChgRcptMgmtVO vo) {
		/*if(KtisUtil.isEmpty(vo.getSearchSubscriberNo())) {
			throw new MvnoRunException(-1, "검색할 전화번호를 입력해 주십시오.");
		}*/
		
		if(KtisUtil.isEmpty(vo.getSearchGb())) {
			throw new MvnoRunException(-1, "검색구분을 선택하세요.");
		}
		if(KtisUtil.isEmpty(vo.getSearchGbVal())) {
			throw new MvnoRunException(-1, "검색어를 입력하세요.");
		}
		
		// 조회이력생성
		chgRcptMapper.insertChgRcptSearchHst(vo);
		
		// 계약정보조회
		return chgRcptMapper.getContractInfo(vo);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void insertChgRcptMst(ChgRcptMgmtVO vo) {
		if(KtisUtil.isEmpty(vo.getContractNum())){
			throw new MvnoRunException(-1, "전화번호 검색을 먼저 해주십시오.");
		}
		
		if(KtisUtil.isEmpty(vo.getTskTp()) && KtisUtil.isEmpty(vo.getTskNm())) {
			throw new MvnoRunException(-1, "업무유형을 입력해 주십시오.");
		}
		
		if(KtisUtil.isEmpty(vo.getRmk())){
			throw new MvnoRunException(-1, "처리내용을 입력해 주십시오.");
		}
		
		// 2018-09-13 해지상태인 경우 업무유형 체크
		if("C".equals(vo.getSubStatus()) && !"CAN".equals(vo.getTskTp())) {
			throw new MvnoRunException(-1, "해지상태인 경우 해지업무만 등록가능합니다.");
		}
		
		// 변경신청 일련번호 조회
		vo.setSeqNum(chgRcptMapper.getSeqNum());
		
		// 단체보험 변경
		if("INS".equals(vo.getTskTp())) {
			if(vo.getInsrCd() == null || "".equals(vo.getInsrCd())) {
				throw new MvnoRunException(-1, "단체보험을 선택하세요.");
			}
			
			if(vo.getClauseInsuranceFlag() == null || !"Y".equals(vo.getClauseInsuranceFlag())) {
				throw new MvnoRunException(-1, "단체보험가입동의를 체크하세요.");
			}
			
			if(vo.getNewScanId() == null || "".equals(vo.getNewScanId())) {
				throw new MvnoRunException(-1, "서식지정보(SCAN_ID)가 존재하지 않습니다.");
			}
			
			// 2018-12-19, 단체보험변경인 경우만 서식지 유무 체크로 수정
			if(chgRcptMapper.getScanFileCount(vo) == 0) {
				throw new MvnoRunException(-1, "서식지파일이 존재하지 않습니다.");
			}
			
			if(chgRcptMapper.getInsrChangeHst(vo) > 0) {
				throw new MvnoRunException(-1, "단체보험 변경은 월중 1회 가능합니다.");
			}
		}
		
		// 단체보험해지
		if("INC".equals(vo.getTskTp())) {
			chgRcptMapper.cancelInsrMember(vo);
		}
		
		// 단말보험
		if("CARE".equals(vo.getTskTp())) {
			if(vo.getInsrProdCd() == null || "".equals(vo.getInsrProdCd())) {
				throw new MvnoRunException(-1, "단말보험을 선택하세요.");
			}
			
			if(vo.getClauseInsrProdFlag() == null || !"Y".equals(vo.getClauseInsrProdFlag())) {
				throw new MvnoRunException(-1, "단말보험가입동의를 체크하세요.");
			}
			
			if(vo.getNewScanId() == null || "".equals(vo.getNewScanId())) {
				throw new MvnoRunException(-1, "서식지정보(SCAN_ID)가 존재하지 않습니다.");
			}
		}
		
		// 변경신청등록
		chgRcptMapper.insertChgRcptMst(vo);
		
		/**
		 * [SRM18111483882] 서식지가 없는 ATM 접점 개통건에 대한 서식지합본 기능 개선
		 * ATM 개통건인지 여부 체크하여, scanId 가 없는 경우 msp_juo_sub_info 에 scanId 생성하여 합본 가능하게 처리
		 */
		ChgRcptMgmtVO chkVO = chgRcptMapper.getAtmOpenContract(vo);
		logger.debug("chkVO=" + chkVO);
		
		if(chkVO != null																	// ATM 개통
				&& "10".equals(vo.getSessionUserOrgnTypeCd())								// 본사 사용자
				&& (chkVO.getContractNum() != null && !"".equals(chkVO.getContractNum()))	// 계약번호 존재 
				&& (chkVO.getScanId() == null || "".equals(chkVO.getScanId()))				// SCAN_ID 미존재 
				) {
			String scanId = this.generationUUID();
			chkVO.setScanId(scanId);
			
			chgRcptMapper.updateSubInfoScanId(chkVO);
		}
		
		// 서식지가 있는 경우 자동 합본처리
		if(vo.getNewScanId() != null && !"".equals(vo.getNewScanId())) {
			
			ChgRcptMgmtVO rsltVo = chgRcptMapper.getSubInfoScanId(vo);
			
			if(rsltVo.getScanId() == null || "".equals(rsltVo.getScanId())) {
				throw new MvnoRunException(-1, "서식지정보(SCAN_ID)가 존재하지 않습니다.");
			}
			
			vo.setScanId(rsltVo.getScanId());
			
			if("N".equals(rsltVo.getScanMstYn())) {
				chgRcptMapper.insertEmvScanMst(vo);
			}
			
			chgRcptMapper.insertEmvScanFile(vo);
		}
	}
	
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("custNm","CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("procrNm","CUST_NAME"); //처리자
				
		return maskFields;
	}

	private String generationUUID(){
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-","").trim();
		
		logger.error("uuid=" + uuid);
		
		return uuid;
	}

	public List<?> getInsrPsblYn(ChgRcptMgmtVO vo) {
		
		return chgRcptMapper.getInsrPsblYn(vo);
	}
}
