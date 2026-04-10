package com.ktis.msp.m2m.smsmgmt.mapper;

import java.util.List;

import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.m2m.smsmgmt.vo.SmsSendReqVO;
import com.ktis.msp.m2m.smsmgmt.vo.SmsSendResVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("M2mSmsMgmtMapper")
public interface M2mSmsMgmtMapper {

	/**
	 * SMS발송조회
	 * @param vo
	 * @return
	 */
	List<SmsSendResVO> getSmsSendList(SmsSendReqVO vo);

	/**
	 * 수신자번호 검색
	 * @param vo
	 * @return
	 */
	String getMobileNo(SmsSendResVO vo);

	/**
	 * 템플릿 combo
	 * @return
	 */
	List<?> getTemplateCombo(CmnCdMgmtVo vo);

	/**
	 * 검색구분 combo
	 * @return
	 */
	List<?> getSearchCodeCombo(CmnCdMgmtVo vo);
	
	/**
	 * SMS발송조회(과거)
	 * @param vo
	 * @return
	 */
	List<SmsSendResVO> getSmsSendListOld(SmsSendReqVO vo);
}
