package com.ktmmobile.msf.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.msf.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.common.mplatform.vo.MpUsimPukVO;
import com.ktmmobile.msf.form.servicechange.dao.CustRequestDao;
import com.ktmmobile.msf.form.servicechange.dto.CustRequestDto;

@Service("custRequestService")
public class MsfCustRequestServiceImpl implements MsfCustRequestService {

    private static final Logger logger = LoggerFactory.getLogger(MsfCustRequestServiceImpl.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private CustRequestDao custRequestDao;
    
    @Autowired
    private MsfMplatFormService mPlatFormService;
    
	@Override
	public long getCustRequestSeq() {
		return custRequestDao.getCustRequestSeq();
	}
	
    @Override
	public boolean insertCustRequestMst(CustRequestDto custReuqestDto) {		
		return custRequestDao.insertCustRequestMst(custReuqestDto);
	}
    
    @Override
    public boolean insertCustRequestCallList(CustRequestDto custReuqestDto) {
    	return custRequestDao.insertCustRequestCallList(custReuqestDto);
    }
    
    @Override
    public boolean insertCustRequestJoinForm(CustRequestDto custReuqestDto) {
    	return custRequestDao.insertCustRequestJoinForm(custReuqestDto);
    }
    
    @Override
    public MpUsimPukVO getUsimPukByMP(String ncn, String ctn, String custId) {

    	MpUsimPukVO vo = null;

 		try {
 			//puk번호 열람은 prx 서버를 통해야 한다.
 			vo = mPlatFormService.getUsimPuk(ncn, ctn, custId);
 		} catch(SocketTimeoutException e) {
			logger.info("Y07Error");
		} catch(Exception e) {
 			logger.info("Y07Error");
 		}
 		return vo;
 	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getInsrInfo(String contractNum) {
		//---- API 호출 S ----//
		Map<String, String> insrInfo = null;
        RestTemplate restTemplate = new RestTemplate();
        insrInfo = restTemplate.postForObject(apiInterfaceServer + "/mypage/getInsrInfo", contractNum, Map.class);
        
        return insrInfo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getInsrInfoByCd(String insrCd) {
		//---- API 호출 S ----//
		Map<String, String> insrInfo = null;
        RestTemplate restTemplate = new RestTemplate();
        insrInfo = restTemplate.postForObject(apiInterfaceServer + "/mypage/getInsrInfoByCd", insrCd, Map.class);
        
        return insrInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getReqInsrData(long custReqKey) {
		//---- API 호출 S ----//
		Map<String, String> insrInfo = null;
        RestTemplate restTemplate = new RestTemplate();
        insrInfo = restTemplate.postForObject(apiInterfaceServer + "/mypage/getReqInsrData", custReqKey, Map.class);
        
        return insrInfo;
	}

	@Override
	public String getOrgScanId(String contractNum) {
		//---- API 호출 S ----//
		String orgScanId = null;
        RestTemplate restTemplate = new RestTemplate();
        orgScanId = restTemplate.postForObject(apiInterfaceServer + "/mypage/getOrgScanId", contractNum, String.class);
        
        return orgScanId;
	}

	@Override
	public boolean insertCustRequestInsr(CustRequestDto custReuqestDto) {
    	return custRequestDao.insertCustRequestInsr(custReuqestDto);
	}
}
