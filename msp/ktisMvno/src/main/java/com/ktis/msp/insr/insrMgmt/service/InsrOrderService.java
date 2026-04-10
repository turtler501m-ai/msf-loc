package com.ktis.msp.insr.insrMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.insr.insrMgmt.mapper.InsrOrderMapper;
import com.ktis.msp.insr.insrMgmt.vo.InsrOrderVO;

@Service
public class InsrOrderService extends BaseService {
	
	@Autowired
	private InsrOrderMapper insrOrderMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	public List<InsrOrderVO> getInsrOrderList(InsrOrderVO insrOrderVO, Map<String, Object> paramMap){
		
		if ( (insrOrderVO.getSearchCd() == null || "".equals(insrOrderVO.getSearchCd()))
			&& ((insrOrderVO.getSearchFromDt() == null || "".equals(insrOrderVO.getSearchFromDt()))
			|| (insrOrderVO.getSearchToDt() == null || "".equals(insrOrderVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "가입일자 정보가 존재 하지 않습니다.");
		}
		
		List<InsrOrderVO> rsltAryListVo = insrOrderMapper.getInsrOrderList(insrOrderVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
	
	public List<InsrOrderVO> getInsrOrderListByExcel(InsrOrderVO insrOrderVO, Map<String, Object> paramMap){
		
		if ( (insrOrderVO.getSearchCd() == null || "".equals(insrOrderVO.getSearchCd()))
			&& ((insrOrderVO.getSearchFromDt() == null || "".equals(insrOrderVO.getSearchFromDt()))
			|| (insrOrderVO.getSearchToDt() == null || "".equals(insrOrderVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "가입일자 정보가 존재 하지 않습니다.");
		}
		
		List<InsrOrderVO> rsltAryListVo = insrOrderMapper.getInsrOrderListByExcel(insrOrderVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
	
	public void regIntmInfoByUsimCust(InsrOrderVO insrOrderVO){
		
		if (insrOrderVO.getContractNum() == null || "".equals(insrOrderVO.getContractNum()) ){
			throw new MvnoRunException(-1, "계약번호가 존재 하지 않습니다.");
		}
		
		if (insrOrderVO.getInsrProdCd() == null || "".equals(insrOrderVO.getInsrProdCd()) ){
			throw new MvnoRunException(-1, "보험상품코드가 존재 하지 않습니다.");
		}
		
		insrOrderMapper.regIntmInfoByUsimCust(insrOrderVO);
		
		if( "FOK".equals(insrOrderVO.getVrfyRsltCd()) || "SOK".equals(insrOrderVO.getVrfyRsltCd()) ) {
			insrOrderVO.setIfTrgtCd(insrOrderVO.getImgChkYn());
			insrOrderMapper.regAdmsn(insrOrderVO);
		}
		
	}
	
	public void regVrfyRslt(InsrOrderVO insrOrderVO){
		
		if (insrOrderVO.getContractNum() == null || "".equals(insrOrderVO.getContractNum()) ){
			throw new MvnoRunException(-1, "계약번호가 존재 하지 않습니다.");
		}
		
		if (insrOrderVO.getInsrProdCd() == null || "".equals(insrOrderVO.getInsrProdCd()) ){
			throw new MvnoRunException(-1, "보험상품코드가 존재 하지 않습니다.");
		}
		
		if("REG".equals(insrOrderVO.getVrfyRsltCd())) {
			insrOrderVO.setVrfyRsltCd(insrOrderVO.getFstVrfyCd());
		} else {
			insrOrderVO.setVrfyRsltCd(insrOrderVO.getSndVrfyCd());
		}
		
		insrOrderMapper.regVrfyRslt(insrOrderVO);
		
		if("FOK".equals(insrOrderVO.getVrfyRsltCd()) || "SOK".equals(insrOrderVO.getVrfyRsltCd())) {
			if("02".equals(insrOrderVO.getInsrTypeCd())) {
				if("Y".equals(insrOrderVO.getImgChkYn())) {
					insrOrderVO.setIfTrgtCd("Y");
					insrOrderMapper.regAdmsn(insrOrderVO);
				}
			} else {
				insrOrderVO.setIfTrgtCd("Y");
				insrOrderMapper.regAdmsn(insrOrderVO);
			}
		} else if ("SNOK".equals(insrOrderVO.getVrfyRsltCd())) {
			insrOrderVO.setIfTrgtCd("F");
			insrOrderMapper.regAdmsn(insrOrderVO);
			insrOrderMapper.regIntmInsrCanInfo(insrOrderVO);
		}
	}
	
	public List<InsrOrderVO> getInsrReadyList(InsrOrderVO insrOrderVO, Map<String, Object> paramMap){
		
		if ( (insrOrderVO.getSearchCd() == null || "".equals(insrOrderVO.getSearchCd()))
			&& ((insrOrderVO.getSearchFromDt() == null || "".equals(insrOrderVO.getSearchFromDt()))
			|| (insrOrderVO.getSearchToDt() == null || "".equals(insrOrderVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "검색일자 정보가 존재 하지 않습니다.");
		}
		
		List<InsrOrderVO> rsltAryListVo = insrOrderMapper.getInsrReadyList(insrOrderVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
	
	public List<InsrOrderVO> getInsrReadyListByExcel(InsrOrderVO insrOrderVO, Map<String, Object> paramMap){
		
		if ( (insrOrderVO.getSearchCd() == null || "".equals(insrOrderVO.getSearchCd()))
			&& ((insrOrderVO.getSearchFromDt() == null || "".equals(insrOrderVO.getSearchFromDt()))
			|| (insrOrderVO.getSearchToDt() == null || "".equals(insrOrderVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "검색일자 정보가 존재 하지 않습니다.");
		}
		
		List<InsrOrderVO> rsltAryListVo = insrOrderMapper.getInsrReadyListByExcel(insrOrderVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
	
	private HashMap<String, String> getMaskFields() {
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		
		maskFields.put("subLinkName", "CUST_NAME");
		maskFields.put("subscriberNo", "MOBILE_PHO");
		maskFields.put("intmSrlNo", "DEV_NO");
		maskFields.put("smsPhone", "MOBILE_PHO");
		maskFields.put("fstVrfyId", "CUST_NAME");
		maskFields.put("sndVrfyId", "CUST_NAME");
		
		return maskFields;
	}
	
}
