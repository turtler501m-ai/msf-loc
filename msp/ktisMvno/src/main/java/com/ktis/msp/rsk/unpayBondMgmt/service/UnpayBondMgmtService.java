package com.ktis.msp.rsk.unpayBondMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rsk.unpayBondMgmt.mapper.UnpayBondMgmtMapper;
import com.ktis.msp.rsk.unpayBondMgmt.vo.UnpayBondMgmtVO;

@Service
public class UnpayBondMgmtService extends BaseService {
	@Autowired
	private UnpayBondMgmtMapper unpayBondMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	/**
	 * 미납채권관리 조회
	 * @param unpayBondMgmtVO
	 * @return
	 */
	public List<?> getUnpayBondList(UnpayBondMgmtVO unpayBondMgmtVO) {
		if (KtisUtil.isEmpty(unpayBondMgmtVO.getStdrYm())) {
			throw new MvnoRunException(-1, "기준월을 확인해주세요.");
		}
		
		if (KtisUtil.isEmpty(unpayBondMgmtVO.getStrtYm())) {
			throw new MvnoRunException(-1, "시작월을 확인해주세요.");
		}
		
		if (KtisUtil.isEmpty(unpayBondMgmtVO.getEndYm())) {
			throw new MvnoRunException(-1, "종료월을 확인해주세요.");
		}
		
		return unpayBondMgmtMapper.getUnpayBondList(unpayBondMgmtVO);
	}
	
	/**
	 * 조정금액 상세 조회
	 * @param unpayBondMgmtVO
	 * @return
	 */
	public List<?> getAdjAmntDtl(UnpayBondMgmtVO unpayBondMgmtVO) {
		if (KtisUtil.isEmpty(unpayBondMgmtVO.getStdrYm())) {
			throw new MvnoRunException(-1, "기준월을 확인해주세요.");
		}
		
		if (KtisUtil.isEmpty(unpayBondMgmtVO.getBillYm())) {
			throw new MvnoRunException(-1, "청구월을 확인해주세요.");
		}
		
		if (KtisUtil.isEmpty(unpayBondMgmtVO.getBillItemCd())) {
			throw new MvnoRunException(-1, "청구항목을 확인해주세요.");
		}
		
		return unpayBondMgmtMapper.getAdjAmntDtl(unpayBondMgmtVO);
	}
	
	/**
	 * 미납채권관리 엑셀다운로드
	 * @param unpayBondMgmtVO
	 * @return
	 */
	public List<?> getUnpayBondListEx(UnpayBondMgmtVO unpayBondMgmtVO) {
		if (KtisUtil.isEmpty(unpayBondMgmtVO.getStdrYm())) {
			throw new MvnoRunException(-1, "기준월을 확인해주세요.");
		}
		
		if (KtisUtil.isEmpty(unpayBondMgmtVO.getStrtYm())) {
			throw new MvnoRunException(-1, "시작월을 확인해주세요.");
		}
		
		if (KtisUtil.isEmpty(unpayBondMgmtVO.getEndYm())) {
			throw new MvnoRunException(-1, "종료월을 확인해주세요.");
		}
		
		return unpayBondMgmtMapper.getUnpayBondListEx(unpayBondMgmtVO);
	}
	
	/**
	 * 미납채권상세 조회
	 * @param unpayBondMgmtVO
	 * @return
	 */
	public List<?> getUnpayBondDtlList(UnpayBondMgmtVO unpayBondMgmtVO, Map<String,Object> paramMap) {
		if (KtisUtil.isEmpty(unpayBondMgmtVO.getStdrYm())) {
			throw new MvnoRunException(-1, "기준월을 확인해주세요.");
		}
		
		if (KtisUtil.isEmpty(unpayBondMgmtVO.getStrtYm()) || KtisUtil.isEmpty(unpayBondMgmtVO.getEndYm())){
			throw new MvnoRunException(-1, "청구월을 확인해주세요.");
		}
		
		if (!KtisUtil.isEmpty(unpayBondMgmtVO.getSearchType()) && KtisUtil.isEmpty(unpayBondMgmtVO.getSearchVal())) {
			throw new MvnoRunException(-1, "검색어를 입력하세요.");
		}
		
		logger.debug("unpdYn=" + unpayBondMgmtVO.getUnpdYn());
		
		HashMap<String,String> maskFields = getMaskFields();
		
		List<?> list = unpayBondMgmtMapper.getUnpayBondDtlList(unpayBondMgmtVO);
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	/**
	 * 조정사유 조회
	 * @param unpayBondMgmtVO
	 * @return
	 */
	public List<?> getAdjRsnList(UnpayBondMgmtVO unpayBondMgmtVO) {
		
		if(KtisUtil.isEmpty(unpayBondMgmtVO.getBillYm())) {
			throw new MvnoRunException(-1, "청구월을 확인해주세요.");
		} else if (KtisUtil.isEmpty(unpayBondMgmtVO.getSvcCntrNo())) {
			throw new MvnoRunException(-1, "서비스계약번호를 확인해주세요.");
		}
		
		return unpayBondMgmtMapper.getAdjRsnList(unpayBondMgmtVO);
	}
	
	/**
	 * 마스크필드
	 * @return
	 */
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		
		maskFields.put("subLinkName", "CUST_NAME");
		
		return maskFields;
	}
	
	public List<?> setMaskingData(List<?> list, Map<String, Object> param){
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, param);
		
		return list;
	}
	
}
