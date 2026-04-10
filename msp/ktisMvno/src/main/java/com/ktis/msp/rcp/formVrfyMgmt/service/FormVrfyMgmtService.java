package com.ktis.msp.rcp.formVrfyMgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.formVrfyMgmt.mapper.FormVrfyMgmtMapper;
import com.ktis.msp.rcp.formVrfyMgmt.vo.FormVrfyMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;


@Service
public class FormVrfyMgmtService extends BaseService {

	@Autowired
	private RcpEncService encSvc;
	
	@Autowired
	private FormVrfyMgmtMapper formVrfyMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
		
	public List<FormVrfyMgmtVO> getFormVrfyList(FormVrfyMgmtVO searchVO, Map<String, Object> paramMap){

		List<FormVrfyMgmtVO> list = formVrfyMgmtMapper.getFormVrfyList(searchVO);

		List<FormVrfyMgmtVO> result = (List<FormVrfyMgmtVO>) encSvc.decryptDBMSCustMngtListNotEgovMap(list);
		
		for(FormVrfyMgmtVO item : result) {
			maskingService.setMask(item, maskingService.getMaskFields(), paramMap);
		}
		
		for( FormVrfyMgmtVO formVrfyMgmtVO : list  ){
			
			if ("JP".equals(formVrfyMgmtVO.getCstmrType())){
				formVrfyMgmtVO.setAge("법인");
			} else {
				formVrfyMgmtVO.setAge(Integer.toString(Util.getAge(formVrfyMgmtVO.getSsn())));	
			}
			
			String[] cstmrNativeRrn = Util.getJuminNumber(formVrfyMgmtVO.getSsn());
			formVrfyMgmtVO.setBirth(cstmrNativeRrn[0]);
			
		}

		return result;
	}
	
	public List<FormVrfyMgmtVO> getFormVrfyListByExcel(FormVrfyMgmtVO searchVO, Map<String, Object> paramMap){

		List<FormVrfyMgmtVO> list = formVrfyMgmtMapper.getFormVrfyListByExcel(searchVO);

		List<FormVrfyMgmtVO> result = (List<FormVrfyMgmtVO>) encSvc.decryptDBMSCustMngtListNotEgovMap(list);
		
		for(FormVrfyMgmtVO item : result) {
			maskingService.setMask(item, maskingService.getMaskFields(), paramMap);
		}
		
		for( FormVrfyMgmtVO formVrfyMgmtVO : list  ){
			
			if ("JP".equals(formVrfyMgmtVO.getCstmrType())){
				formVrfyMgmtVO.setAge("법인");
			} else {
				formVrfyMgmtVO.setAge(Integer.toString(Util.getAge(formVrfyMgmtVO.getSsn())));	
			}
			
			String[] cstmrNativeRrn = Util.getJuminNumber(formVrfyMgmtVO.getSsn());
			formVrfyMgmtVO.setBirth(cstmrNativeRrn[0]);

		}

		return result;

	}
	
	public List<FormVrfyMgmtVO> getFormVrfyAgntList(FormVrfyMgmtVO searchVO, Map<String, Object> paramMap){
		
		List<FormVrfyMgmtVO> list = formVrfyMgmtMapper.getFormVrfyAgntList(searchVO);

		List<FormVrfyMgmtVO> result = (List<FormVrfyMgmtVO>) encSvc.decryptDBMSCustMngtListNotEgovMap(list);
		
		for(FormVrfyMgmtVO item : result) {
			maskingService.setMask(item, maskingService.getMaskFields(), paramMap);
		}
		
		for( FormVrfyMgmtVO formVrfyMgmtVO : list  ){
			
			if ("JP".equals(formVrfyMgmtVO.getCstmrType())){
				formVrfyMgmtVO.setAge("법인");
			} else {
				formVrfyMgmtVO.setAge(Integer.toString(Util.getAge(formVrfyMgmtVO.getSsn())));	
			}
			
			String[] cstmrNativeRrn = Util.getJuminNumber(formVrfyMgmtVO.getSsn());
			formVrfyMgmtVO.setBirth(cstmrNativeRrn[0]);
						
		}

		return result;
	}
	
	public List<FormVrfyMgmtVO> getFormVrfyAgntListByExcel(FormVrfyMgmtVO searchVO, Map<String, Object> paramMap){

		List<FormVrfyMgmtVO> list = formVrfyMgmtMapper.getFormVrfyAgntListByExcel(searchVO);

		List<FormVrfyMgmtVO> result = (List<FormVrfyMgmtVO>) encSvc.decryptDBMSCustMngtListNotEgovMap(list);
		
		for(FormVrfyMgmtVO item : result) {
			maskingService.setMask(item, maskingService.getMaskFields(), paramMap);
		}
		
		for( FormVrfyMgmtVO formVrfyMgmtVO : list  ){
			
			if ("JP".equals(formVrfyMgmtVO.getCstmrType())){
				formVrfyMgmtVO.setAge("법인");
			} else {
				formVrfyMgmtVO.setAge(Integer.toString(Util.getAge(formVrfyMgmtVO.getSsn())));	
			}
			
			String[] cstmrNativeRrn = Util.getJuminNumber(formVrfyMgmtVO.getSsn());
			formVrfyMgmtVO.setBirth(cstmrNativeRrn[0]);

		}

		return result;

	}
	
	public List<FormVrfyMgmtVO> getFormVrfyRstList(FormVrfyMgmtVO formVrfyMgmtVO){

		List<FormVrfyMgmtVO> getFormVrfyRstList = new ArrayList<FormVrfyMgmtVO>();

		getFormVrfyRstList = formVrfyMgmtMapper.getFormVrfyRstList(formVrfyMgmtVO);

		return getFormVrfyRstList;

	}
	
	public String getFormVrfyRstAsinYn(FormVrfyMgmtVO formVrfyMgmtVO){

		return formVrfyMgmtMapper.getFormVrfyRstAsinYn(formVrfyMgmtVO);

	}
		
	/*public void regFormVrfyInfo(FormVrfyMgmtVO formVrfyMgmtVO){
		
		if (formVrfyMgmtVO.getClausePriAdFlag() == null || "".equals(formVrfyMgmtVO.getClausePriAdFlag()) ){
			throw new MvnoRunException(-1, "광고수신동의여부가 존재하지 않습니다.");
		}
		
		if (formVrfyMgmtVO.getInsrFormFlag() == null || "".equals(formVrfyMgmtVO.getInsrFormFlag()) ){
			throw new MvnoRunException(-1, "선택형상해보험서식지등록여부가 존재하지 않습니다.");
		}
		
		if (formVrfyMgmtVO.getInsrCfmtFlag() == null || "".equals(formVrfyMgmtVO.getInsrCfmtFlag()) ){
			throw new MvnoRunException(-1, "선택형상해보험적격여부가 존재하지 않습니다.");
		}
		
		if (formVrfyMgmtVO.getFstRsltCd() == null || "".equals(formVrfyMgmtVO.getFstRsltCd()) ){
			throw new MvnoRunException(-1, "1차검증결과가 존재하지 않습니다.");
		}
		
		if(formVrfyMgmtVO.getFstRsltCd().equals("FNOK")){
			if (formVrfyMgmtVO.getFstRsnCd() == null || "".equals(formVrfyMgmtVO.getFstRsnCd()) ){
				throw new MvnoRunException(-1, "1차검증 검증의견이 존재하지 않습니다.");
			}
		}
		
		formVrfyMgmtMapper.regFormVrfyInfo(formVrfyMgmtVO);
	}*/
	
	public void updFormVrfyInfo(FormVrfyMgmtVO formVrfyMgmtVO){
		
		if (formVrfyMgmtVO.getClausePriAdFlag() == null || "".equals(formVrfyMgmtVO.getClausePriAdFlag()) ){
			throw new MvnoRunException(-1, "광고수신동의여부가 존재하지 않습니다.");
		}
		
		if (formVrfyMgmtVO.getInsrFormFlag() == null || "".equals(formVrfyMgmtVO.getInsrFormFlag()) ){
			throw new MvnoRunException(-1, "선택형상해보험서식지등록여부가 존재하지 않습니다.");
		}
		
		if (formVrfyMgmtVO.getInsrCfmtFlag() == null || "".equals(formVrfyMgmtVO.getInsrCfmtFlag()) ){
			throw new MvnoRunException(-1, "선택형상해보험적격여부가 존재하지 않습니다.");
		}
		
		if (formVrfyMgmtVO.getFstRsltCd() == null || "".equals(formVrfyMgmtVO.getFstRsltCd()) ){
			throw new MvnoRunException(-1, "1차검증결과가 존재하지 않습니다.");
		}
		
		if(formVrfyMgmtVO.getFstRsltCd().equals("FNOK")){
			if (formVrfyMgmtVO.getFstRemark() == null || "".equals(formVrfyMgmtVO.getFstRemark()) ){
				throw new MvnoRunException(-1, "1차검증 검증의견이 존재하지 않습니다.");
			}
		}
		
		if(formVrfyMgmtVO.getFstRsltCd().equals("FOK")){
			if (!(formVrfyMgmtVO.getSndRemark() == null || "".equals(formVrfyMgmtVO.getSndRemark()))){
				throw new MvnoRunException(-1, "1차검증결과가 적격인 경우 2차검증결과는 입력할 수 없습니다.");
			}
		}
		
		if(formVrfyMgmtVO.getSndRsltCd().equals("SNOK")){
			if (formVrfyMgmtVO.getSndRemark() == null || "".equals(formVrfyMgmtVO.getSndRemark()) ){
				throw new MvnoRunException(-1, "2차검증 검증의견이 존재하지 않습니다.");
			}
		}
		
		if (formVrfyMgmtVO.getSndRsltCd() == null || "".equals(formVrfyMgmtVO.getSndRsltCd())) {
			formVrfyMgmtVO.setVrfyStatCd(formVrfyMgmtVO.getFstRsltCd());
		} else {
			formVrfyMgmtVO.setVrfyStatCd(formVrfyMgmtVO.getSndRsltCd());
		}
		
		formVrfyMgmtMapper.updFormVrfyInfo(formVrfyMgmtVO);
	}
	
	public void updFormVrfyAgentInfo(FormVrfyMgmtVO formVrfyMgmtVO){
		
		if(formVrfyMgmtVO.getFstActCd() == null || "".equals(formVrfyMgmtVO.getFstActRemark())){
			throw new MvnoRunException(-1, "1차조치결과가 존재하지 않습니다.");
		}
		
		if(!(formVrfyMgmtVO.getSndRsltCd() == null || "".equals(formVrfyMgmtVO.getSndRsltCd()))){
			if(formVrfyMgmtVO.getSndActCd() == null || "".equals(formVrfyMgmtVO.getSndActRemark())){
				throw new MvnoRunException(-1, "2차조치결과가 존재하지 않습니다.");
			}
		}
		
		formVrfyMgmtMapper.updFormVrfyAgntInfo(formVrfyMgmtVO);
	}
		
	public List<?> getFormVrfyUsrList(Map<String, Object> param){
        
		List<?> list = formVrfyMgmtMapper.getFormVrfyUsrList(param);
        
        HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_NM","CUST_NAME");
		maskFields.put("USR_ID_MSK","SYSTEM_ID");
		
		maskingService.setMask(list, maskFields, param);
		
		return list;
	}
	
	public List<FormVrfyMgmtVO> getFormVrfyAsinList(FormVrfyMgmtVO searchVO, Map<String, Object> paramMap){

		List<FormVrfyMgmtVO> list = formVrfyMgmtMapper.getFormVrfyAsinList(searchVO);

		List<FormVrfyMgmtVO> result = (List<FormVrfyMgmtVO>) encSvc.decryptDBMSCustMngtListNotEgovMap(list);
		
		for(FormVrfyMgmtVO item : result) {
	        
	        HashMap<String, String> maskFields = new HashMap<String, String>();
			maskFields.put("fstUsrNm","CUST_NAME"); //1차검증자
			maskFields.put("sndUsrNm","CUST_NAME"); //2차검증자
			maskFields.put("cstmrName","CUST_NAME");
			maskFields.put("subscriberNo","MOBILE_PHO");
			maskFields.put("fstModelSrlNum","DEV_NO");
			maskFields.put("shopUsrId",			"SYSTEM_ID");
			maskFields.put("shopUsrNm",			"CUST_NAME");
			
			maskingService.setMask(item, maskFields, paramMap);
		}
		
		for( FormVrfyMgmtVO formVrfyMgmtVO : list  ){
			
			if ("JP".equals(formVrfyMgmtVO.getCstmrType())){
				formVrfyMgmtVO.setAge("법인");
			} else {
				formVrfyMgmtVO.setAge(Integer.toString(Util.getAge(formVrfyMgmtVO.getSsn())));	
			}
			
			String[] cstmrNativeRrn = Util.getJuminNumber(formVrfyMgmtVO.getSsn());
			formVrfyMgmtVO.setBirth(cstmrNativeRrn[0]);

		}

		return result;
	}
	
	public void regFormVrfyAsinInfo(FormVrfyMgmtVO formVrfyMgmtVO){
		formVrfyMgmtMapper.regFormVrfyAsinInfo(formVrfyMgmtVO);
	}
	
	public void updFormVrfyAsinInfoY(FormVrfyMgmtVO formVrfyMgmtVO){
		formVrfyMgmtMapper.updFormVrfyAsinInfoY(formVrfyMgmtVO);
	}
	
	public void updFormVrfyAsinInfoN(FormVrfyMgmtVO formVrfyMgmtVO){
		formVrfyMgmtMapper.updFormVrfyAsinInfoN(formVrfyMgmtVO);
	}
	
	public FormVrfyMgmtVO getFormVrfyInfo(FormVrfyMgmtVO formVrfyMgmtVO, Map<String, Object> paramMap) {
		
		FormVrfyMgmtVO data = formVrfyMgmtMapper.getFormVrfyInfo(formVrfyMgmtVO);
		FormVrfyMgmtVO result = encSvc.decryptDBMSFormVrfyMgmtVO(data);
		
		if ("JP".equals(result.getCstmrType())){
			result.setAge("법인");
		} else {
			result.setAge(Integer.toString(Util.getAge(result.getSsn())));	
		}
		
		String[] cstmrNativeRrn = Util.getJuminNumber(result.getSsn());
		result.setBirth(cstmrNativeRrn[0]);
		
		/* 할부수수료 계산 */
		int modelInstallment = 0;
		int instMnthCnt = 0;
		double rateCommission = 0;
		
		//기변인 경우 기변정보 세팅
		if (result.getDvcChgYn().equals("Y")) {
			result.setInstMnthCnt(result.getDvcInstMnthCnt().toString());
			result.setModelInstallment(result.getDvcInstOrginAmnt());
			result.setAgentNm(result.getDvcAgntNm());
			result.setFstRateNm(result.getLstRateNm());
			result.setOperTypeNm(result.getDvcOperTypeNm());
			result.setFstModelNm(result.getDvcModelNm());
			result.setEnggMnthCnt(result.getDvcEnggMnthCnt().toString());
			
		}
		
		if (result.getInstMnthCnt() != null && !result.getInstMnthCnt().isEmpty()) {
			modelInstallment = result.getModelInstallment();
			instMnthCnt = Integer.parseInt(result.getInstMnthCnt());
			
			if (modelInstallment > 0 && instMnthCnt > 0) {
				switch(instMnthCnt) {
				case 12 :
					rateCommission = 0.0322;
					break;
				case 24 :
					rateCommission = 0.0626;
					break;
				case 30 :
					rateCommission = 0.0780;
					break;
				case 36 :
					rateCommission = 0.0936;
					break;
				default :
					break;
				}
				
				long monthlyInstallment = Math.round(modelInstallment/instMnthCnt);
				long monthlyFee = Math.round(modelInstallment/instMnthCnt*rateCommission);
				long monthylTotal = monthlyInstallment+monthlyFee;
				long totalFee = monthlyFee*instMnthCnt;
				
				result.setMonthlyInstallment(monthlyInstallment); 
				result.setMonthlyFee(monthlyFee);
				result.setMonthylTotal(monthylTotal);
				result.setTotalFee(totalFee);
				
			}
		}
		
		String vrfyStatCd = result.getVrfyStatCd();
		
		if (vrfyStatCd.equals("NONE")) {
			result.setFstModifyYn("Y");
			result.setSndModifyYn("N");
		} else if (vrfyStatCd.equals("EXP")) {
			result.setSndModifyYn("N");
		} else if (vrfyStatCd.equals("FOK")) {
			result.setSndModifyYn("N");
		} else if (vrfyStatCd.equals("FNOK")) {
			if (result.getSndUsrId() == null) {
				result.setFstModifyYn("Y");
				result.setSndModifyYn("N");
			} else {
				result.setFstModifyYn("N");
				
				if (!result.getSndUsrId().equals(paramMap.get("SESSION_USER_ID")) && result.getAdminYn().equals("N")) {
					result.setSndModifyYn("N");
				}
			}
		} else if (vrfyStatCd.equals("SOK")) {
			result.setFstModifyYn("N");
			
			if (!result.getSndUsrId().equals(paramMap.get("SESSION_USER_ID")) && result.getAdminYn().equals("N")) {
				result.setSndModifyYn("N");
			}
		} else if (vrfyStatCd.equals("SNOK")) {
			result.setFstModifyYn("N");
			
			if (!result.getSndUsrId().equals(paramMap.get("SESSION_USER_ID")) && result.getAdminYn().equals("N")) {
				result.setSndModifyYn("N");
			} else {
				result.setSndModifyYn("Y");
			}
		}
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	public FormVrfyMgmtVO getFormVrfyAgntInfo(FormVrfyMgmtVO formVrfyMgmtVO, Map<String, Object> paramMap) {
		
		FormVrfyMgmtVO data = formVrfyMgmtMapper.getFormVrfyAgntInfo(formVrfyMgmtVO);
		FormVrfyMgmtVO result = encSvc.decryptDBMSFormVrfyMgmtVO(data);
		
		if ("JP".equals(result.getCstmrType())){
			result.setAge("법인");
		} else {
			result.setAge(Integer.toString(Util.getAge(result.getSsn())));	
		}
		
		String[] cstmrNativeRrn = Util.getJuminNumber(result.getSsn());
		result.setBirth(cstmrNativeRrn[0]);
		
		/* 할부수수료 계산 */
		int modelInstallment = 0;
		int instMnthCnt = 0;
		double rateCommission = 0;
		
		//기변인 경우 기변정보 세팅
		if (result.getDvcChgYn().equals("Y")) {
			result.setInstMnthCnt(result.getDvcInstMnthCnt().toString());
			result.setModelInstallment(result.getDvcInstOrginAmnt());
			result.setAgentNm(result.getDvcAgntNm());
			result.setFstRateNm(result.getLstRateNm());
			result.setOperTypeNm(result.getDvcOperTypeNm());
			result.setFstModelNm(result.getDvcModelNm());
			result.setEnggMnthCnt(result.getDvcEnggMnthCnt().toString());
		}
		
		if (result.getInstMnthCnt() != null && !result.getInstMnthCnt().isEmpty()) {
			modelInstallment = result.getModelInstallment();
			instMnthCnt = Integer.parseInt(result.getInstMnthCnt());
			
			if (modelInstallment > 0 && instMnthCnt > 0) {
				switch(instMnthCnt) {
				case 12 :
					rateCommission = 0.0322;
					break;
				case 24 :
					rateCommission = 0.0626;
					break;
				case 30 :
					rateCommission = 0.0780;
					break;
				case 36 :
					rateCommission = 0.0936;
					break;
				default :
					break;
				}
				
				long monthlyInstallment = Math.round(modelInstallment/instMnthCnt);
				long monthlyFee = Math.round(modelInstallment/instMnthCnt*rateCommission);
				long monthylTotal = monthlyInstallment+monthlyFee;
				long totalFee = monthlyFee*instMnthCnt;
				
				result.setMonthlyInstallment(monthlyInstallment); 
				result.setMonthlyFee(monthlyFee);
				result.setMonthylTotal(monthylTotal);
				result.setTotalFee(totalFee);
				
			}
		}
		
		String vrfyStatCd = result.getVrfyStatCd();
				
		if (vrfyStatCd.equals("NONE") || vrfyStatCd.equals("EXP") || vrfyStatCd.equals("FOK") || vrfyStatCd.equals("SOK")) {
			result.setFstModifyYn("N");
			result.setSndModifyYn("N");
		} else if (vrfyStatCd.equals("FNOK")) {
			result.setFstModifyYn("Y");
			result.setSndModifyYn("N");
		} else if (vrfyStatCd.equals("SNOK")) {
			result.setFstModifyYn("N");
			result.setSndModifyYn("Y");
		}
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
}