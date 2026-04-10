package com.ktis.msp.rcp.openMgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.masking.vo.MaskingVO;
import com.ktis.msp.org.common.vo.FileInfoVO;
import com.ktis.msp.rcp.custMgmt.vo.FileInfoCuVO;
import com.ktis.msp.rcp.openMgmt.mapper.OpenMgmtMapper;
import com.ktis.msp.rcp.openMgmt.vo.OpenMgmtListExVO;
import com.ktis.msp.rcp.openMgmt.vo.OpenMgmtListVO;
import com.ktis.msp.rcp.openMgmt.vo.OpenMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.mapper.PointRecoveryMapper;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;
import com.ktis.msp.rcp.rcpMgmt.vo.PointTxnVO;

@Service
public class OpenMgmtService extends BaseService {
	@Autowired
	private RcpEncService encSvc;

	@Autowired
	private OpenMgmtMapper openMgmtMapper;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private MaskingMapper maskingMapper;

	/** 포인트사용금액 서비스 */
	@Autowired
	private PointRecoveryMapper pointRecoveryMapper;
	
	
	/**
	 * @Description : 개통관리 목록 조회
	 * @Param  : OpenMgmtListVO
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2017. 03. 28.
	 */
	public List<OpenMgmtListVO> getOpenMgmtListNew(OpenMgmtListVO searchVO, Map<String, Object> paramMap){
		logger.debug("searchVO = " + searchVO);

		logger.info("#################### 개통관리 목록 조회 시작 #####################");
		List<OpenMgmtListVO> list = openMgmtMapper.getOpenMgmtListNew(searchVO);
		logger.info("#################### 개통관리 목록 조회 종료 #####################");
		
		List<OpenMgmtListVO> result = (List<OpenMgmtListVO>) encSvc.decryptDBMSCustMngtListNotEgovMap(list);	// 복호화 필수
		
		HashMap<String, String> maskFields = getMaskFields();		

		logger.info("#################### 마스킹 시작 #####################");
		for(OpenMgmtListVO item : result) {
			maskingService.setMask(item, maskFields, paramMap);
		}
		logger.info("#################### 마스킹 종료 #####################");
		for( OpenMgmtListVO openMgmtVO : list  ){
			
			if ("JP".equals(openMgmtVO.getCstmrType())){ //법인이면 나이(만) 를 표시하지 않게 변경
				openMgmtVO.setAge("법인");
			} else {
				// 주민번호
				openMgmtVO.setAge(Integer.toString(Util.getAge(openMgmtVO.getSsn())));	
			}
			
			String[] cstmrNativeRrn = Util.getJuminNumber(openMgmtVO.getSsn());
			openMgmtVO.setBirth(cstmrNativeRrn[0]);

		}
		
		return result;
	}
	/**
	 * @Description : 20180809추가 --> 해지고객인경우 스캔프로그램 실행 X
	 * @Param  : OpenMgmtListVO
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2018. 08. 09.
	 */
	public List<?> ValidationScan(Map<String, Object> paramMap){
			
		logger.info("=================================================================");
		logger.info("ValidationScan START.");
		logger.info("=================================================================");
		return  openMgmtMapper.validationCanScan(paramMap);
	}	
	
	// 가입자 정보 조회
	@Transactional(rollbackFor=Exception.class)
	public List<OpenMgmtVO> getOpenMgmtListDtl(Map<String, Object> pRequestParamMap){
		
		try {
			List<OpenMgmtVO> list = openMgmtMapper.getOpenMgmtListDtl(pRequestParamMap);
			List<OpenMgmtVO> result = (List<OpenMgmtVO>) encSvc.decryptDBMSCustMngtListNotEgovMap(list);	// 복호화 필수

			HashMap<String, String> maskFields = getMaskFields();
			for(OpenMgmtVO item : result) {
				maskingService.setMask(item, maskFields, pRequestParamMap);
			}
			
			if(result.size() > 0) {
				String insrNm = result.get(0).getInsrNm();
				
				//2022.02.27 포인트사용금액 조회
				String contractNum = result.get(0).getContractNum();
				if(contractNum != null && !"".equals(contractNum)) {
					PointTxnVO usePointAmt = pointRecoveryMapper.selectUsePointAmtOpenMgmtListDtl(contractNum);
					if(usePointAmt != null && usePointAmt.getPoint() != null) {
						result.get(0).setUsePointAmt(String.valueOf(usePointAmt.getPoint()));
					}
				}
				
            if(insrNm != null && !"".equals(insrNm)) {
               String[] aryInsrNm = insrNm.split("@");
               
               if(aryInsrNm.length < 2) {
                  
                  if("N".equals(aryInsrNm[0].substring(0, 1))) {
                     result.get(0).setInsrNm1(aryInsrNm[0].substring(1, aryInsrNm[0].length()));   //생활안심보험
                  }else {
                     result.get(0).setInsrNm2(aryInsrNm[0].substring(1, aryInsrNm[0].length()));   //선택형단체보험
                  }
                  
               } else {
                  result.get(0).setInsrNm1(aryInsrNm[0].substring(1, aryInsrNm[0].length()));   //생활안심보험
                  result.get(0).setInsrNm2(aryInsrNm[1].substring(1, aryInsrNm[1].length()));   //선택형단체보험
               }
            }
			}
			
			return result;
			
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}
	

	// 계약 이력
	public List<OpenMgmtVO> getSubInfoHist(Map<String, Object> pRequestParamMap){
		try {
			List<OpenMgmtVO> result = openMgmtMapper.getSubInfoHist(pRequestParamMap);
			
			HashMap<String, String> maskFields = new HashMap<String, String>();
//			maskFields.put("subscriberNo","MOBILE_PHO");
//			maskFields.put("userSsn","SSN");
//			maskFields.put("subAdrPrimaryLn","ADDR");
//			maskFields.put("subAdrSecondaryLn","ADDR");
			maskFields.put("subLinkName","CUST_NAME");
//			maskFields.put("ncn","DEV_NO");
//			maskFields.put("iccId","USIM");
//			maskFields.put("intmSrlNo","DEV_NO");
//			
			for(OpenMgmtVO item : result) {
				maskingService.setMask(item, maskFields, pRequestParamMap);
			}
			
			return result;
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}
	
	// 단말 이력
	public List<OpenMgmtVO> getModelHist(Map<String, Object> pRequestParamMap){
		try {
			List<OpenMgmtVO> result = openMgmtMapper.getModelHist(pRequestParamMap);
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("userSsn","SSN");
			maskFields.put("subAdrPrimaryLn","ADDR");
			maskFields.put("subAdrSecondaryLn","ADDR");
			maskFields.put("subLinkName","CUST_NAME");
			maskFields.put("iccId","USIM");
			maskFields.put("ncn","DEV_NO");
			maskFields.put("intmSrlNo","DEV_NO");
			
			for(OpenMgmtVO item : result) {
				maskingService.setMask(item, maskFields, pRequestParamMap);
			}
			
			return result;
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}
	
	// 상품 이력
	public List<OpenMgmtVO> getFeatureHist(Map<String, Object> pRequestParamMap){

		try {
			
			List<OpenMgmtVO> result = openMgmtMapper.getFeatureHist(pRequestParamMap);
			
			return result;

		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}
	
	// 납부방법
	public List<OpenMgmtVO> getBanHist(Map<String, Object> pRequestParamMap){
		try {
			
			List<OpenMgmtVO> list = openMgmtMapper.getBanHist(pRequestParamMap);
			List<OpenMgmtVO> result = (List<OpenMgmtVO>) encSvc.decryptDBMSCustMngtBankAcctHolderIdNotEgovMap(list);
			
			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("bankAcctHolderId","SSN");
			maskFields.put("banAdrPrimaryLn","ADDR");
			maskFields.put("addr","ADDR");
			maskFields.put("banLinkName","CUST_NAME");
			
			for(OpenMgmtVO item : result) {
				maskingService.setMask(item, maskFields, pRequestParamMap);
			}
			
			return result;

		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}
	
	// 청구수납 이력
	public List<OpenMgmtVO> getInvPymList(Map<String, Object> pRequestParamMap) {
		try {
			List<OpenMgmtVO> result = openMgmtMapper.getInvPymList(pRequestParamMap);
			
			return result;
		} catch (Exception e) {
			logger.error(e);
		}
		
		return null;
	}

	// 법정대리인
	public List<OpenMgmtVO> getAgentInfo(Map<String, Object> pRequestParamMap){
		try {
			List<OpenMgmtVO> list = openMgmtMapper.getAgentInfo(pRequestParamMap);
			List<OpenMgmtVO> result = (List<OpenMgmtVO>) encSvc.decryptDBMSCustMngtBankAcctHolderIdNotEgovMap(list);

			HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("minorAgentName","CUST_NAME");
			maskFields.put("agentTelNum","MOBILE_PHO");
			maskFields.put("instCnfmNm","CUST_NAME");
			
			for(OpenMgmtVO item : result) {
				maskingService.setMask(item, maskFields, pRequestParamMap);
			}
			
			return result;

		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}
	
	// 법정대리인(앱설치확인)
	@Transactional(rollbackFor=Exception.class)
	public void updateAppInstYnCnfm(OpenMgmtVO searchVO) {
		openMgmtMapper.updateAppInstYnCnfm(searchVO);
	}
	
	// 할인유형이력
	public List<OpenMgmtVO> getAddInfoList(Map<String, Object> pRequestParamMap){
		if(pRequestParamMap.get("CONTRACT_NUM") == null || "".equals(pRequestParamMap.get("CONTRACT_NUM"))){
			throw new MvnoRunException(-1, "계약번호 누락");
		}
		
		return openMgmtMapper.getAddInfoList(pRequestParamMap);
	}
	
	// 기기변경이력
	public List<OpenMgmtVO> getDvcChgList(Map<String, Object> pRequestParamMap){
		if(pRequestParamMap.get("CONTRACT_NUM") == null || "".equals((String) pRequestParamMap.get("CONTRACT_NUM"))){
			throw new MvnoRunException(-1, "계약번호 누락");
		}
		
		
		List<OpenMgmtVO> list = openMgmtMapper.getDvcChgList(pRequestParamMap);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		for(OpenMgmtVO item : list) {
			maskingService.setMask(item, maskFields, pRequestParamMap);
		}
		
		return list;
		
	}
	
	// 신청정보조회
	public OpenMgmtVO getApplFormInfo(OpenMgmtVO searchVO, Map<String, Object> paramMap){
		List<OpenMgmtVO> list = openMgmtMapper.getApplFormInfo(searchVO);
		List<OpenMgmtVO> result = (List<OpenMgmtVO>) encSvc.decryptDBMSCustMngtListNotEgovMap(list);	// 복호화 필수
		
		HashMap<String, String> maskFields = getMaskFields();
		
		for(OpenMgmtVO item : result) {
			maskingService.setMask(item, maskFields, paramMap);
		}
		
		OpenMgmtVO vo = list.get(0);
		
		/* 주민번호 */
		String[] cstmrNativeRrn = Util.getJuminNumber(vo.getSsn());
		vo.setCstmrNativeRrn1(cstmrNativeRrn[0]);
		vo.setCstmrNativeRrn2(cstmrNativeRrn[1]);
		
		/* 카드번호 */
		String[] reqCardNo = Util.getCardNumber(vo.getReqCardNo());
		vo.setReqCardNo1(reqCardNo[0]);
		vo.setReqCardNo2(reqCardNo[1]);
		vo.setReqCardNo3(reqCardNo[2]);
		vo.setReqCardNo4(reqCardNo[3]);
		
		/* 카드명의자주민번호 */
		String[] reqCardRrn = Util.getJuminNumber(vo.getReqCardRrn());
		vo.setReqCardRrn1(reqCardRrn[0]);
		vo.setReqCardRrn2(reqCardRrn[1]);
		
		/* 은행계좌번호 */
		String reqAccountNumber = vo.getReqAccountNumber();
		vo.setReqAccountNumber(reqAccountNumber);
		
		/* 은행계좌명의자주민번호 */
		String[] reqAccountRrn = Util.getJuminNumber(vo.getReqAccountRrn());
		vo.setReqAccountRrn1(reqAccountRrn[0]);
		vo.setReqAccountRrn2(reqAccountRrn[1]);
		
		/* 대리인주민번호 */
		String[] minorAgentRrn = Util.getJuminNumber(vo.getMinorAgentRrn());
		vo.setMinorAgentRrn1(minorAgentRrn[0]);
		vo.setMinorAgentRrn2(minorAgentRrn[1]);

		return vo;
	}
	
	
	// 녹취파일조회
	public List<?> getFile1(FileInfoCuVO fileInfoVO){
		
		
		List<?> fileInfoVOs = new ArrayList<FileInfoVO>();
		
		fileInfoVOs = openMgmtMapper.getFile1(fileInfoVO);
		
		return fileInfoVOs;
	}    	
	
	// 녹취파일업로드
	@Transactional(rollbackFor=Exception.class)
	public int insertFileByCust(FileInfoCuVO fileInfoVO){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("insertFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = openMgmtMapper.fileInsertByCust(fileInfoVO);
		
		return resultCnt;
	}
	
		
	/**
	 * <PRE>
	 *
	 * </PRE>
	 * @param 
	 * @param 
	 * @return 
	 * @exception 
	 */
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrName","CUST_NAME");
		maskFields.put("cstmrMobile","MOBILE_PHO");
		maskFields.put("cstmrNativeRrn","SSN");
		maskFields.put("cstmrForeignerRrn","SSN");
		maskFields.put("cstmrForeignerPn","PASSPORT");
		maskFields.put("cstmrJuridicalRrn","CORPORATE");
		maskFields.put("cstmrMail","EMAIL");
		maskFields.put("cstmrTel","MOBILE_PHO");
		maskFields.put("cstmrMobile","MOBILE_PHO");
		maskFields.put("faxnum","MOBILE_PHO");
		maskFields.put("dlvryTel","MOBILE_PHO");
		maskFields.put("reqAccountNumber","ACCOUNT");
		maskFields.put("reqCardNo","CREDIT_CAR");
		maskFields.put("reqCardMm","CARD_EXP");
		maskFields.put("reqCardYy","CARD_EXP");
		maskFields.put("reqAccountName","CUST_NAME");
		maskFields.put("reqAccountRrn","SSN");
		maskFields.put("reqCardName","CUST_NAME");
		maskFields.put("reqCardRrn","SSN");
		maskFields.put("reqGuide","MOBILE_PHO");
		maskFields.put("moveMobile","MOBILE_PHO");
		maskFields.put("minorAgentName","CUST_NAME");
		maskFields.put("minorAgentRrn","SSN");
		maskFields.put("minorAgentTel","MOBILE_PHO");
		maskFields.put("jrdclAgentName","CUST_NAME");
		maskFields.put("jrdclAgentRrn","SSN");
		maskFields.put("jrdclAgentTel","MOBILE_PHO");
		maskFields.put("reqUsimSn","USIM");
		maskFields.put("reqPhoneSn","DEV_NO");
		maskFields.put("iccId","USIM");
		maskFields.put("intmSrlNo","DEV_NO");
		maskFields.put("cstmrAddr","ADDR");
		maskFields.put("dlvryAddr","ADDR");
		maskFields.put("realUseCustNm","CUST_NAME");
		maskFields.put("cstmrAddrDtl","PASSWORD");
		maskFields.put("customerAdr","ADDR");
		maskFields.put("dlvryName","CUST_NAME");
		maskFields.put("dlvryMobile","MOBILE_PHO");
		maskFields.put("dlvryAddrDtl","PASSWORD");
		maskFields.put("customerLinkName","CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("ssn","SSN");
		maskFields.put("ssn1","SSN");
		maskFields.put("ssn2","SSN");
		maskFields.put("ssn3","SSN");
		// 개통관리 엑셀다운로드시 최초단말일련번호 마스킹적용
		maskFields.put("fstModelSrlNum","DEV_NO");
		maskFields.put("lstModelSrlNum","DEV_NO");
		maskFields.put("imei","IMEI");

		maskFields.put("shopUsrNm","CUST_NAME");
		maskFields.put("shopUsrId","SYSTEM_ID");
		
		maskFields.put("custNmVoc","CUST_NAME");
		maskFields.put("ctnVoc","MOBILE_PHO");
		
		return maskFields;
	}
}
