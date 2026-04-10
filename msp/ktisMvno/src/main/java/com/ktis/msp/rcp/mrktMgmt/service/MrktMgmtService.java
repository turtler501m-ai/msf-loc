package com.ktis.msp.rcp.mrktMgmt.service;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.ktis.msp.rcp.rcpMgmt.service.MplatformService;
import com.ktis.msp.rcp.rcpMgmt.vo.MoscContCustInfoAgreeChgInVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.mrktMgmt.mapper.MrktMgmtMapper;
import com.ktis.msp.rcp.mrktMgmt.vo.MrktMgmtVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Service
public class MrktMgmtService extends BaseService  {

	@Autowired
	private MrktMgmtMapper mrktMgmtMapper;

	@Resource(name = "maskingService")
	private MaskingService maskingService;

	@Resource(name = "mplatformService")
	private MplatformService mplatformService;

	public List<?> getMarketingSmsSendList(MrktMgmtVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> selectList = new ArrayList<MrktMgmtVO>();
		selectList = mrktMgmtMapper.getMarketingSmsSendList(searchVO);

		maskingService.setMask(selectList, maskingService.getMaskFields(), pRequestParamMap);

		return selectList;
	}

	public List<?> getMarketingSmsSendListOld(MrktMgmtVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> selectList = new ArrayList<MrktMgmtVO>();
		selectList = mrktMgmtMapper.getMarketingSmsSendListOld(searchVO);

		maskingService.setMask(selectList, maskingService.getMaskFields(), pRequestParamMap);

		return selectList;
	}

	public List<?> getMarketingHistoryList(MrktMgmtVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> selectList = new ArrayList<MrktMgmtVO>();
		selectList = mrktMgmtMapper.getMarketingHistoryList(searchVO);

		maskingService.setMask(selectList, maskingService.getMaskFields(), pRequestParamMap);

		return selectList;
	}

	public void regMarketingHistoryList(MrktMgmtVO searchVO, String usrId) throws EgovBizException{
		List<MrktMgmtVO> itemList = searchVO.getItems();

		for (MrktMgmtVO vo : itemList) {
			vo.setSessionUserId(usrId);
			this.validateMrktAgrInfo(vo);
			this.moscContCustInfoAgreeChg(vo);

			MrktMgmtVO oldMrktMgmtVO = getOldMrktAgrInfo(vo);
			if (oldMrktMgmtVO != null) {
				if (oldMrktMgmtVO.getAgrYn().equals(searchVO.getAgrYn())) {
					continue;
				}
				this.setRevisionTimeByComparingNewAndOldAgrees(vo, oldMrktMgmtVO);
			}

			mrktMgmtMapper.expireMrktAgrMgmt(vo);
			mrktMgmtMapper.regMspMrktAgrMgmtByInfo(vo);
			mrktMgmtMapper.updMspRequestByInfo(vo);
		}
	}

	public void modMarketingHistory(MrktMgmtVO searchVO, String usrId) throws EgovBizException{
		if (!"Y".equals(searchVO.getAgrYn())) searchVO.setAgrYn("N");
		if (!"Y".equals(searchVO.getPersonalInfoCollectAgree())) searchVO.setPersonalInfoCollectAgree("N");
		if (!"Y".equals(searchVO.getOthersTrnsAgree())) searchVO.setOthersTrnsAgree("N");
		if (!"Y".equals(searchVO.getOthersTrnsKtAgree())) searchVO.setOthersTrnsKtAgree("N");
		if (!"Y".equals(searchVO.getOthersAdReceiveAgree())) searchVO.setOthersAdReceiveAgree("N");

		searchVO.setSessionUserId(usrId);
		this.validateMrktAgrInfo(searchVO);
		this.moscContCustInfoAgreeChg(searchVO);

		MrktMgmtVO oldMrktMgmtVO = getOldMrktAgrInfo(searchVO);
		if (oldMrktMgmtVO == null) {
			throw new EgovBizException("잘못된 접근입니다.");
		}

		//변경사항이 없을경우 저장되지 않도록 수정(2024.03.07)
		if (searchVO.getAgrYn().equals(oldMrktMgmtVO.getAgrYn())
				&& searchVO.getPersonalInfoCollectAgree().equals(oldMrktMgmtVO.getPersonalInfoCollectAgree())
				&& searchVO.getOthersTrnsAgree().equals(oldMrktMgmtVO.getOthersTrnsAgree())
				&& searchVO.getOthersTrnsKtAgree().equals(oldMrktMgmtVO.getOthersTrnsKtAgree())
				&& searchVO.getOthersAdReceiveAgree().equals(oldMrktMgmtVO.getOthersAdReceiveAgree())) {
			return;
		}

		this.setRevisionTimeByComparingNewAndOldAgrees(searchVO, oldMrktMgmtVO);


		mrktMgmtMapper.expireMrktAgrMgmt(searchVO);
		mrktMgmtMapper.regMspMrktAgrMgmtByInfo(searchVO);
		mrktMgmtMapper.updMspRequestByInfo(searchVO);
	}

	public void validateMrktAgrInfo(MrktMgmtVO vo) throws EgovBizException {
		if (vo.getContractNum() == null || "".equals(vo.getContractNum())){
			throw new EgovBizException("계약번호가 존재하지 않습니다.");
		}

		if (vo.getPersonalInfoCollectAgree() == null || "".equals(vo.getPersonalInfoCollectAgree())){
			throw new EgovBizException("고객 혜택 제공을 위한 개인정보 수집 및 관련 동의 여부가 존재하지 않습니다.");
		}

		if (vo.getAgrYn() == null || "".equals(vo.getAgrYn())){
			throw new EgovBizException("개인정보 처리 위탁 및 혜택 제공 동의 여부가 존재하지 않습니다.");
		}

		if (vo.getOthersTrnsAgree() == null || "".equals(vo.getOthersTrnsAgree())){
			throw new EgovBizException("혜택 제공을 위한 제 3자 제공 동의 : M모바일 여부가 존재하지 않습니다.");
		}

		if (vo.getOthersTrnsKtAgree() == null || "".equals(vo.getOthersTrnsKtAgree())){
			throw new EgovBizException("혜택 제공을 위한 제 3자 제공 동의 : KT 여부가 존재하지 않습니다.");
		}

		if (vo.getOthersAdReceiveAgree() == null || "".equals(vo.getOthersAdReceiveAgree())){
			throw new EgovBizException("제 3자 제공관련 광고 수신 동의 여부가 존재하지 않습니다.");
		}

        if (!"Y".equals(vo.getPersonalInfoCollectAgree())) {
            if ("Y".equals(vo.getAgrYn())) {
                throw new EgovBizException("{개인정보 처리 위탁 및 혜택 제공 동의}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의}에 동의하여야 합니다.");
            }
        }

		if((!"Y".equals(vo.getPersonalInfoCollectAgree()) || !"Y".equals(vo.getAgrYn()))){
			if("Y".equals(vo.getOthersTrnsAgree())){
				throw new EgovBizException("{혜택 제공을 위한 제 3자 제공 동의 : M모바일}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
			}
			if("Y".equals(vo.getOthersTrnsKtAgree())){
				throw new EgovBizException("{혜택 제공을 위한 제 3자 제공 동의 : KT}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
			}
			if("Y".equals(vo.getOthersAdReceiveAgree())){
				throw new EgovBizException("{제 3자 제공관련 광고 수신 동의}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
			}
		}
	}

	private MrktMgmtVO getOldMrktAgrInfo(MrktMgmtVO vo) {
		MrktMgmtVO oldMrktMgmtVO = null;
		List<MrktMgmtVO> mrktMgmtVOList = (List<MrktMgmtVO>) mrktMgmtMapper.getMspMrktAgrMgmtByInfo(vo);
		if (mrktMgmtVOList.size() > 0) {
			oldMrktMgmtVO = mrktMgmtVOList.get(0);
		}
		return oldMrktMgmtVO;
	}

	/**
	 * 마케팅동의 여부, 개인정보수집이용동의 여부, 제3자제공동의 여부가 각각 이전과 같으면 이전날짜로 setting 하고 다르면 오늘날짜로 세팅
	 * @param newMrktMgmtVO
	 * @param oldMrktMgmtVO
	 */
	private void setRevisionTimeByComparingNewAndOldAgrees(MrktMgmtVO newMrktMgmtVO, MrktMgmtVO oldMrktMgmtVO) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String todayDate = dateFormat.format(Calendar.getInstance().getTime());
		newMrktMgmtVO.setMrktRvisnDttm(this.getRvisnDttmByComparingAgree(newMrktMgmtVO.getAgrYn(), oldMrktMgmtVO.getAgrYn(), todayDate, oldMrktMgmtVO.getMrktRvisnDttm()));
		newMrktMgmtVO.setPersonalInfoRvisnDttm(this.getRvisnDttmByComparingAgree(newMrktMgmtVO.getPersonalInfoCollectAgree(), oldMrktMgmtVO.getPersonalInfoCollectAgree(), todayDate, oldMrktMgmtVO.getPersonalInfoRvisnDttm()));
		newMrktMgmtVO.setOthersTrnsRvisnDttm(this.getRvisnDttmByComparingAgree(newMrktMgmtVO.getOthersTrnsAgree(), oldMrktMgmtVO.getOthersTrnsAgree(), todayDate, oldMrktMgmtVO.getOthersTrnsRvisnDttm()));
		newMrktMgmtVO.setOthersTrnsKtRvisnDttm(this.getRvisnDttmByComparingAgree(newMrktMgmtVO.getOthersTrnsAgree(), oldMrktMgmtVO.getOthersTrnsAgree(), todayDate, oldMrktMgmtVO.getOthersTrnsRvisnDttm()));
		newMrktMgmtVO.setOthersAdReceiveRvisnDttm(this.getRvisnDttmByComparingAgree(newMrktMgmtVO.getOthersTrnsAgree(), oldMrktMgmtVO.getOthersTrnsAgree(), todayDate, oldMrktMgmtVO.getOthersTrnsRvisnDttm()));
	}

	private String getRvisnDttmByComparingAgree(String newAgreeYn, String oldAgreeYn, String newRvisnDttm, String oldRvisnDttm) {
		if (newAgreeYn.equals(oldAgreeYn)) {
			if (oldRvisnDttm == null ||  "".equals(oldRvisnDttm)){
				return newRvisnDttm;
			} else {
				return oldRvisnDttm;
			}
		} else {
			return newRvisnDttm;
		}
	}

	private void moscContCustInfoAgreeChg(MrktMgmtVO mrktMgmtVO) throws EgovBizException {
		try {
			MrktMgmtVO contractInfo = mrktMgmtMapper.getContractInfo(mrktMgmtVO);
            MoscContCustInfoAgreeChgInVO vo = this.getMoscMoscContCustInfoAgreeChgInVO(contractInfo, mrktMgmtVO);
			Map<String, Object> result = mplatformService.moscContCustInfoAgreeChg(vo);
			if (!"N".equals(result.get("responseType"))) {
				throw new EgovBizException("{정보/광고 수신 동의} 등록/수정에 실패하였습니다. 계약번호[" + mrktMgmtVO.getContractNum() + "]");
			}
		} catch (Exception e) {
			throw new EgovBizException("{정보/광고 수신 동의} 등록/수정에 실패하였습니다. 계약번호[" + mrktMgmtVO.getContractNum() + "]");
		}
	}

    private MoscContCustInfoAgreeChgInVO getMoscMoscContCustInfoAgreeChgInVO(MrktMgmtVO contractInfo, MrktMgmtVO mrktMgmtVO) {
        MoscContCustInfoAgreeChgInVO vo = new MoscContCustInfoAgreeChgInVO();
        vo.setNcn(contractInfo.getSvcCntrNo());
        vo.setCtn(contractInfo.getSubscriberNo());
        vo.setCustId(contractInfo.getCustomerId());
        vo.setOthcmpInfoAdvrRcvAgreYn(mrktMgmtVO.getPersonalInfoCollectAgree());
        vo.setOthcmpInfoAdvrCnsgAgreYn(mrktMgmtVO.getAgrYn());
        vo.setFnncDealAgreeYn(mrktMgmtVO.getOthersTrnsAgree());
        vo.setGrpAgntBindSvcSbscAgreYn(mrktMgmtVO.getOthersTrnsKtAgree());
        vo.setCardInsrPrdcAgreYn(mrktMgmtVO.getOthersAdReceiveAgree());
        return vo;
    }
}
