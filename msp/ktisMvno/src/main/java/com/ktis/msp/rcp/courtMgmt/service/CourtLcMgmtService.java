package com.ktis.msp.rcp.courtMgmt.service;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;
import com.ktis.msp.rcp.courtMgmt.mapper.CourtLcMgmtMapper;
import com.ktis.msp.rcp.courtMgmt.vo.CourtLcMgmtVO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourtLcMgmtService extends BaseService {

    @Autowired
    private RcpEncService encSvc;

    @Autowired
    private CourtLcMgmtMapper courtLcMgmtMapper;

    /** propertiesService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;


    /** 마스킹 처리 서비스 */
    @Autowired
    private MaskingService maskingService;

    /** Constructor */
    public CourtLcMgmtService() {
        setLogPrefix("[CourtLcMgmtService] ");
    }

    @Crypto(encryptName="DBMSEnc", fields = {"searchRrn"})
    public List<?> getCourtLcMgmtList(Map<String, Object> paramMap){

        // 필수조건 체크
        if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
            if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
                throw new MvnoRunException(-1, "검색값 누락");
            }
        } else {
            if(paramMap.get("strtDt") == null || "".equals(paramMap.get("strtDt"))){
                throw new MvnoRunException(-1, "시작일자 누락");
            }
            if(paramMap.get("endDt") == null || "".equals(paramMap.get("endDt"))){
                throw new MvnoRunException(-1, "종료일자 누락");
            }
        }

        List<EgovMap> list = (List<EgovMap>) courtLcMgmtMapper.getCourtLcMgmtList(paramMap);
        List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(result, maskFields, paramMap);

        return result;
    }

    @Crypto(encryptName="DBMSEnc", fields = {"searchRrn"})
    public List<?> getCourtLcMgmtListByExcel(Map<String, Object> paramMap){

        // 필수조건 체크
        if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
            if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
                throw new MvnoRunException(-1, "검색값 누락");
            }
        } else {
            if(paramMap.get("strtDt") == null || "".equals(paramMap.get("strtDt"))){
                throw new MvnoRunException(-1, "시작일자 누락");
            }
            if(paramMap.get("endDt") == null || "".equals(paramMap.get("endDt"))){
                throw new MvnoRunException(-1, "종료일자 누락");
            }
        }

        List<EgovMap> list = (List<EgovMap>) courtLcMgmtMapper.getCourtLcMgmtListByExcel(paramMap);
        List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(result, maskFields, paramMap);

        return result;
    }

    public List<?> getCourtLcMgmtDtlList(Map<String, Object> paramMap){

        List<EgovMap> list = (List<EgovMap>) courtLcMgmtMapper.getCourtLcMgmtDtlList(paramMap);
        List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);

        HashMap<String, String> maskFields = getMaskFields();

        maskingService.setMask(result, maskFields, paramMap);

        return result;
    }
   
    @Crypto(encryptName="DBMSEnc", fields = {"cstmrRrn"})
    public int checkLcMst(CourtLcMgmtVO vo) throws EgovBizException {
    	int chkLcMst = courtLcMgmtMapper.checkLcMst(vo) - courtLcMgmtMapper.checkLcSeq(vo);
    	
        return chkLcMst;
    }

    @Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="DBMSEnc", fields = {"cstmrRrn"})
    public String insertLcList(CourtLcMgmtVO vo) throws EgovBizException {
        String seqVal = "";
        
        if(vo.getLcMstSeq() == null || "".equals(vo.getLcMstSeq())) {
        	seqVal = courtLcMgmtMapper.getLcMstSeqNvl();
        	vo.setLcMstSeq(seqVal);

            courtLcMgmtMapper.insertLcMst(vo);
        }

        courtLcMgmtMapper.insertLcDtl(vo);

        return vo.getLcMstSeq();
    }

    @Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="DBMSEnc", fields = {"cstmrRrn"})
    public void updateLcDtl(CourtLcMgmtVO vo) throws EgovBizException {
        courtLcMgmtMapper.updateLcDtl(vo);

        return;
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteLcDtl(CourtLcMgmtVO vo) throws MvnoServiceException {
        courtLcMgmtMapper.deleteLcDtl(vo);
        
        return;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void issueLcMst(CourtLcMgmtVO vo) throws EgovBizException {
        int maxLcNumNvl = 0;
        
        maxLcNumNvl = courtLcMgmtMapper.cntLcYear();
        
        vo.setMaxLcNumNvl(maxLcNumNvl + 1);
        
        courtLcMgmtMapper.issueLcMst(vo);

        return;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void discardLcMst(CourtLcMgmtVO vo) throws MvnoServiceException {
        courtLcMgmtMapper.discardLcMst(vo);
 
        return;
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
        maskFields.put("issueId",			"CUST_NAME");
        maskFields.put("regstId",			"CUST_NAME");
        maskFields.put("rvisnId",			"CUST_NAME");

        return maskFields;
    }
}
