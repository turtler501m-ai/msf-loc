package com.ktis.msp.cmn.cdmgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.cdmgmt.mapper.CmnCdMgmtMapper;
import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;

/**
 * @Class Name : CmnCdMgmtService
 * @Description : 공통코드 서비스
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Service
public class CmnCdMgmtService extends BaseService {
	
    /** 공통코드 Mapper 선언 */
    @Autowired
    private CmnCdMgmtMapper cmnCdMgmtMapper;
    
    /** LOGGER Service Main Name */
    public CmnCdMgmtService() {
        setLogPrefix("[CmnCdMgmtService] ");
    }
    

    /**
     * @Description : 공통코드를 조회 한다.
     * @Param  : CrdGrdMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    public List<HashMap<String, String>> getCmnCdList(HashMap<String, String> map){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("getCmnCdList START."));
        logger.info(generateLogMsg("================================================================="));
        
        return cmnCdMgmtMapper.getCmnCdList(map);
    }
    
    public List<CmnCdMgmtVo> getCmnCdList2(HashMap<String, String> map){
    	return cmnCdMgmtMapper.getCmnCdList2(map);
    }
    
    
    /**
	 * @Description : 공통코드Combo조회
	 * 
	 * @Param  : grpId	- 공통코드ID
	 * @Param  : etc1	- 기타1
	 * @Param  : etc2	- 기타2
	 * @Param  : etc3	- 기타3
	 * @Param  : etc4	- 기타4
	 * @Param  : etc5	- 기타5
	 * @Param  : etc6	- 기타6
	 * 
	 * @Return : void
	 */
	public List<?> getCommCombo(CmnCdMgmtVo vo) throws MvnoServiceException {
		
		logger.debug(generateLogMsg("============================================================"));
		logger.debug(generateLogMsg("| 공통코드Combo - START									|"));
		logger.debug(generateLogMsg("============================================================"));
		
		
		/* param setting */
		CmnCdMgmtVo paramVo = new CmnCdMgmtVo();
		
		paramVo.setGrpId(vo.getGrpId());		/** 그룹ID */
		paramVo.setEtc1(vo.getEtc1());			/** 기타1 */
		paramVo.setEtc2(vo.getEtc2());			/** 기타2 */
		paramVo.setEtc3(vo.getEtc3());			/** 기타3 */
		paramVo.setEtc4(vo.getEtc4());			/** 기타4 */
		paramVo.setEtc5(vo.getEtc5());			/** 기타5 */
		paramVo.setEtc6(vo.getEtc6());			/** 기타6 */
		
		paramVo.setOrderBy(vo.getOrderBy());	/** 정렬 */
		
		/* 공통코드Combo조회 */
		logger.debug("===== 공통코드Combo paramVo : " + paramVo);
		
		List<?> resultList = cmnCdMgmtMapper.getCommCombo(paramVo);
		
		
		logger.debug(generateLogMsg("============================================================"));
		logger.debug(generateLogMsg("| 공통코드Combo - END										|"));
		logger.debug(generateLogMsg("============================================================"));
		
		return resultList;
	}
	
	public List<?> getMcpCommCombo(CmnCdMgmtVo vo) throws MvnoServiceException {
		return cmnCdMgmtMapper.getMcpCommCombo(vo);
	}

	public Map<String, String> getCmnGrpCdMst(HashMap<String,String> param) {
		return cmnCdMgmtMapper.getCmnGrpCdMst(param);
	}

	public Map<String, String> getMcpCommCdDtl(CmnCdMgmtVo vo) {
		return cmnCdMgmtMapper.getMcpCommCdDtl(vo);
	}

}
