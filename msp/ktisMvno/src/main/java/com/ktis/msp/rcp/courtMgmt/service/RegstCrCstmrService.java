package com.ktis.msp.rcp.courtMgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.payinfo.vo.PayInfoDtlVO;
import com.ktis.msp.rcp.courtMgmt.mapper.RegstCrCstmrMapper;
import com.ktis.msp.rcp.courtMgmt.vo.RegstCrCstmrVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class RegstCrCstmrService extends BaseService {

    @Autowired
    private RcpEncService encSvc;

    @Autowired
    private RegstCrCstmrMapper regstCrCstmrMapper;

    /** propertiesService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;


    /** 마스킹 처리 서비스 */
    @Autowired
    private MaskingService maskingService;

    /** Constructor */
    public RegstCrCstmrService() {
        setLogPrefix("[RegstCrCstmrService] ");
    }

    @Crypto(encryptName="DBMSEnc", fields = {"searchRrn"})
    public int chkCrCstmrRrn(Map<String, Object> paramMap){
    	int chkCstmrRrn = 0;
    	
    	chkCstmrRrn = regstCrCstmrMapper.chkCrCstmrRrn(paramMap);

        return chkCstmrRrn;
    }
    
    @Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="DBMSEnc", fields = {"cstmrRrn"})
    public int regstCrCstmr(RegstCrCstmrVO vo) throws EgovBizException {
    	int crSeq = 0;
    	
       	regstCrCstmrMapper.regstCrCstmr(vo);
       	
       	crSeq = regstCrCstmrMapper.getCrSeq(vo);

        return crSeq;
    }
    
    @Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="DBMSEnc", fields = {"cstmrRrn"})
    public void modifyCrCstmr(RegstCrCstmrVO vo) throws EgovBizException {
    	
    	regstCrCstmrMapper.modifyCrCstmr(vo);

        return;
    }
    
    @Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="DBMSEnc", fields = {"cstmrRrn"})
    public String insertLcInfo(RegstCrCstmrVO vo) throws EgovBizException {
    	String lcMstSeq = "";
    	List<String> lcDtlSeq = new ArrayList<String>();
    	
    	lcMstSeq = regstCrCstmrMapper.getLcMstSeq(vo);
    	
    	if(!"".equals(lcMstSeq) && lcMstSeq != null) {
        	vo.setLcMstSeq(lcMstSeq);     	
        	regstCrCstmrMapper.insertCrRcivByLc(vo);
        	
        	lcDtlSeq = regstCrCstmrMapper.getLcDtlSeq(vo);
    		
        	for(int i = 0; i < lcDtlSeq.size(); i++) {
        		vo.setLcDtlSeq(lcDtlSeq.get(i));
        		regstCrCstmrMapper.insertCrBondByLc(vo);
        	}
    	}
    	
    	return lcMstSeq;
    }
    
    public List<?> getCrCstmr(Map<String, Object> paramMap){
    	
        List<EgovMap> list = (List<EgovMap>) regstCrCstmrMapper.getCrCstmr(paramMap);
        List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(result, maskFields, paramMap);

        return result;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public int insertCrBond(RegstCrCstmrVO vo) throws EgovBizException {
    	int chkBondNum = regstCrCstmrMapper.chkBondNum(vo);
    	
    	if(chkBondNum > 0) {
    		return chkBondNum;
    	}
    	
       	regstCrCstmrMapper.insertCrBond(vo);

        return chkBondNum;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public int modifyCrBond(RegstCrCstmrVO vo) throws EgovBizException {
    	int chkResult = regstCrCstmrMapper.chkBondNum(vo) - regstCrCstmrMapper.chkBondNumModi(vo);
    	
    	if(chkResult > 0) {
    		return chkResult;
    	}
    	
    	regstCrCstmrMapper.modifyCrBond(vo);

        return chkResult;
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteCrBond(RegstCrCstmrVO vo) throws MvnoServiceException {
    	regstCrCstmrMapper.deleteCrBond(vo);
        
        return;
    }
    
    public List<?> getCrBondList(Map<String, Object> paramMap){
    	
        List<EgovMap> list = (List<EgovMap>) regstCrCstmrMapper.getCrBondList(paramMap);
        List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(result, maskFields, paramMap);

        return result;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void insertCrRciv(RegstCrCstmrVO vo) throws EgovBizException {    	
       	regstCrCstmrMapper.insertCrRciv(vo);

        return;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void modifyCrRciv(RegstCrCstmrVO vo) throws EgovBizException {
    	regstCrCstmrMapper.modifyCrRciv(vo);

        return;
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteCrRciv(RegstCrCstmrVO vo) throws MvnoServiceException {
    	regstCrCstmrMapper.deleteCrRciv(vo);
        
        return;
    }
    
    public List<?> getCrRcivList(Map<String, Object> paramMap){
    	
        List<EgovMap> list = (List<EgovMap>) regstCrCstmrMapper.getCrRcivList(paramMap);
        List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(result, maskFields, paramMap);

        return result;
    }
    
    public String getFileNmSeq(){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일 이름 시퀀스 조회 서비스 START."));
        logger.info(generateLogMsg("================================================================="));
        String strSeq = regstCrCstmrMapper.getFileNmSeq();
        logger.info(generateLogMsg("파일 시퀀스 : " + strSeq));
        logger.info(generateLogMsg("================================================================="));
        return strSeq;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public List<String> getEvidenceFileExtList() {
    	List<String> comboList = new ArrayList<String>();
    	
    	comboList = regstCrCstmrMapper.getEvidenceFileExtList();
    	
    	return comboList;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public String getEvidenceFileSizeTargetChk(String strExt) {
    	
    	String strEvidenceFileSizeTargetChk = "";
    	
    	strEvidenceFileSizeTargetChk = regstCrCstmrMapper.getEvidenceFileSizeTargetChk(strExt);
    	
    	return strEvidenceFileSizeTargetChk;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public List<String> getEvidenceFileResizeExtList(String strEtc3) {
    	
    	List<String> comboList = new ArrayList<String>();
    	
    	comboList = regstCrCstmrMapper.getEvidenceFileResizeExtList(strEtc3);
    	
    	return comboList;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public int updateRcivFile(RegstCrCstmrVO regstCrCstmrVo){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("updateRcivFile - 접수이력 파일관리 update START."));
        logger.info(generateLogMsg("================================================================="));

        int resultCnt = 0;

        try{
            resultCnt = regstCrCstmrMapper.updateRcivFile(regstCrCstmrVo);
            logger.info(generateLogMsg("등록건수 = " + resultCnt));

        }catch (Exception e){
            logger.error(e);
        }
        return resultCnt;
    }
    
    public String getFileNmDtl(String fileId){
        String returnFileId = regstCrCstmrMapper.getFileNmDtl(fileId);

        return returnFileId;
    } 
    
	public int deleteFile(String fileId){
		int returnFileId = regstCrCstmrMapper.deleteFile(fileId);
		
		return returnFileId;
	} 

	public void manageVac(RegstCrCstmrVO vo) {	
		regstCrCstmrMapper.pVacOnceVacId(vo);
		
		if(!"0000".equals(vo.getRetCd())) {
			throw new MvnoRunException(-1, "[" + vo.getRetCd() + "] " +  vo.getRetMsg());
		}
		
		return;
	}
	
    @Transactional(rollbackFor=Exception.class)
    public String updateDclrDate(RegstCrCstmrVO vo) throws EgovBizException {
    	String dclrDate = "";
    	
    	dclrDate = regstCrCstmrMapper.chkDclrDate(vo);
    	
    	if(dclrDate != null && !"".equals(dclrDate)) {
    		return dclrDate;
    	}

    	regstCrCstmrMapper.updateDclrDate(vo);

        return vo.getDclrDate();
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void insertCrInout(RegstCrCstmrVO vo) throws EgovBizException {    	
    	regstCrCstmrMapper.insertCrInout(vo);
    	
    	return;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void modifyCrInout(RegstCrCstmrVO vo) throws EgovBizException {    	
    	regstCrCstmrMapper.modifyCrInout(vo);
    	
    	return;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void deleteCrInout(RegstCrCstmrVO vo) throws MvnoServiceException {
    	regstCrCstmrMapper.deleteCrInout(vo);
    	
    	return;
    }
    
    public List<?> getCrInoutList(Map<String, Object> paramMap){
    	
    	List<EgovMap> list = (List<EgovMap>) regstCrCstmrMapper.getCrInoutList(paramMap);
    	List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
    	
    	HashMap<String, String> maskFields = getMaskFields();
    	
    	maskingService.setMask(result, maskFields, paramMap);
    	
    	return result;
    }
    
    public List<?> getCrInoutTotal(Map<String, Object> paramMap){
    	
        List<EgovMap> list = (List<EgovMap>) regstCrCstmrMapper.getCrInoutTotal(paramMap);
        List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(result, maskFields, paramMap);

        return result;
    }

    private HashMap<String, String> getMaskFields() {
        HashMap<String, String> maskFields = new HashMap<String, String>();
        maskFields.put("cstmrName",			"CUST_NAME");
        maskFields.put("cstmrForeignerRrn",	"SSN");
        maskFields.put("cstmrMobile",		"MOBILE_PHO");
        maskFields.put("cstmrForeignerPn",	"PASSPORT");
        maskFields.put("cstmrJuridicalRrn",	"CORPORATE");
        maskFields.put("cstmrMail",			"EMAIL");
        maskFields.put("cstmrAddr",			"ADDR");
        maskFields.put("cstmrTel",			"TEL_NO");
        maskFields.put("dlvryName",			"CUST_NAME");
        maskFields.put("dlvryTel",			"TEL_NO");
        maskFields.put("dlvryMobile",		"MOBILE_PHO");
        maskFields.put("dlvryAddr",			"ADDR");
        maskFields.put("reqAccountNumber",	"ACCOUNT");
        maskFields.put("reqAccountName",	"CUST_NAME");
        maskFields.put("reqAccountRrn",		"SSN");
        maskFields.put("reqCardNo",			"CREDIT_CAR");
        maskFields.put("reqCardMm",			"CARD_EXP");
        maskFields.put("reqCardYy",			"CARD_EXP");
        maskFields.put("reqCardName",		"CUST_NAME");
        maskFields.put("reqCardRrn",		"SSN");
        maskFields.put("reqGuide",			"MOBILE_PHO");
        maskFields.put("moveMobile",		"MOBILE_PHO");
        maskFields.put("minorAgentName",	"CUST_NAME");
        maskFields.put("minorAgentRrn",		"SSN");
        maskFields.put("minorAgentTel",		"TEL_NO");
        maskFields.put("reqUsimSn",			"USIM");
        maskFields.put("reqPhoneSn",		"DEV_NO");
        maskFields.put("cstmrNativeRrn",	"SSN");
        maskFields.put("faxnum",			"TEL_NO");
        maskFields.put("selfIssuNum",		"PASSPORT");	// 본인인증번호
        maskFields.put("minorSelfIssuNum",	"PASSPORT");	// 본인인증번호
        
        maskFields.put("cstmrName2",		"CUST_NAME");
        maskFields.put("cstmrNameMask",		"CUST_NAME");
        maskFields.put("cstmrAddrDtl",		"PASSWORD");
        maskFields.put("dlvryAddrDtl",		"PASSWORD");
        maskFields.put("cstmrTelMn",		"PASSWORD");
        maskFields.put("cstmrMobileMn",		"PASSWORD");
        maskFields.put("dlvryTelMn",		"PASSWORD");
        maskFields.put("dlvryMobileMn",		"PASSWORD");
        maskFields.put("moveMobileMn",		"PASSWORD");
        maskFields.put("reqUsimSnMask",		"USIM");
        maskFields.put("reqPhoneSnMask",	"DEV_NO");
        maskFields.put("userId",			"SYSTEM_ID");
        maskFields.put("cstmrRrn",			"SSN");
        maskFields.put("mobileNo",			"MOBILE_PHO");
        maskFields.put("searchRrn",			"SSN");
        maskFields.put("issueId",			"CUST_NAME");
        maskFields.put("regstId",			"CUST_NAME");
        maskFields.put("rvisnId",			"CUST_NAME");
        maskFields.put("acctId",			"ACCOUNT");
        maskFields.put("vacId",				"ACCOUNT");
        
        return maskFields;
    }
}
