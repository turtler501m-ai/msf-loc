package com.ktmmobile.msf.domains.form.form.servicechange.service;

// TOBESKIP: 사용하지 않는 기능에서 쓰던 import는 참고용으로 주석 보존한다.
// import java.net.SocketTimeoutException;
import java.util.Map;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
// import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpUsimPukVO;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.CustRequestDao;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.CustRequestDto;

@Service("custRequestService")
public class MsfCustRequestServiceImpl implements MsfCustRequestService {

    // TOBESKIP: 사용하지 않는 기능에서 쓰던 logger는 참고용으로 주석 보존한다.
    // private static final Logger logger = LoggerFactory.getLogger(MsfCustRequestServiceImpl.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private CustRequestDao custRequestDao;

    // TOBESKIP: 사용하지 않는 기능에서 쓰던 M플랫폼 연동 서비스는 참고용으로 주석 보존한다.
    // @Autowired
    // private MsfMplatFormService mPlatFormService;
    
	@Override
	public long getCustRequestSeq() {
		return custRequestDao.getCustRequestSeq();
	}
	
    @Override
	public boolean insertCustRequestMst(CustRequestDto custReuqestDto) {		
		return custRequestDao.insertCustRequestMst(custReuqestDto);
	}
    
    // TOBESKIP: 통화내역 열람, 가입신청서 출력, USIM PUK 구현은 삭제하지 않고 주석으로 보존한다.
    /*
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
    */

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
