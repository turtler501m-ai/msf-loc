package com.ktis.msp.rcp.custTrnsMgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.rcp.custTrnsMgmt.mapper.CustTrnsMgmtMapper;
import com.ktis.msp.rcp.custTrnsMgmt.vo.CustTrnsVO;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class CustTrnsMgmtService extends BaseService {

	@Autowired
	private CustTrnsMgmtMapper mapper;
	
	@Autowired
	private RcpEncService encSvc;
	
	public List<EgovMap> getCustTrnsList(CustTrnsVO searchVO){
		List<EgovMap> list = encSvc.decryptDBMSCustMngtList(mapper.getCustTrnsAgrmList(searchVO));	// 복호화 필수
		
		for(EgovMap map : list ){
			String[] ssn = Util.getJuminNumber((String) map.get("ssn"));
			map.put("ssn1",		ssn[0]);
			map.put("gender",	ssn[1].substring(0,1));
		}
		
		return list;
	}

    @Transactional(rollbackFor=Exception.class)
	public void setCustTrnsAgrm(CustTrnsVO searchVO) {
    	if(searchVO.getContractNum() == null || "".equals(searchVO.getContractNum())){
    		throw new MvnoRunException(-1, "계약번호가 존재하지 않습니다.");
    	}
    	
    	if(searchVO.getTrnsAgrmYn() == null || !"N".equals(searchVO.getTrnsAgrmYn())){
    		throw new MvnoRunException(-1, "미동의고객이 아닙니다.");
    	}
    	
    	if(searchVO.getProcMthdCd() == null || "".equals(searchVO.getProcMthdCd())){
    		throw new MvnoRunException(-1, "미동의시 처리방법을 선택하세요.");
    	}
    	
    	if(searchVO.getTrnsMemo() == null || "".equals(searchVO.getTrnsMemo())){
    		throw new MvnoRunException(-1, "미동의사유를 간략하게 입력하세요.");
    	}
    	
    	searchVO.setUserId(searchVO.getSessionUserId());
    	
    	try {
    		if(mapper.insertSoTrnsAgrmMst(searchVO) > 0 && mapper.updateMspRequestSoCd(searchVO) == 0){
    			mapper.updatePpsCustomerSoCd(searchVO);
    		}
    	}catch(Exception e){
    		throw new MvnoRunException(-1, "저장에 실패하였습니다.");
    	}
	}
    
	public List<EgovMap> getCustTrnsListEx(CustTrnsVO searchVO){
		return mapper.getCustTrnsAgrmListEx(searchVO);
	}
    
}

