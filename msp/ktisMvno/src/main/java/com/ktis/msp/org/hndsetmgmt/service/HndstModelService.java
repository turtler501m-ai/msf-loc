package com.ktis.msp.org.hndsetmgmt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.org.hndsetmgmt.mapper.HndstModelMapper;
import com.ktis.msp.org.hndsetmgmt.vo.HndstModelVo;

/**
 * @Class Name : HndstModelService
 * @Description : 제품 Service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Service
public class HndstModelService extends BaseService {

	@Autowired
	private HndstModelMapper hndstModelMapper;
	
	public HndstModelService() {
		setLogPrefix("[HndstModelService] ");
	}
	
	/**
	 * @Description : 제품 리스트를 조회 한다.
	 * @Param  : hndstModelVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> selectHndstModelList(HndstModelVo hndstModelVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [hndstModelVo] = " + hndstModelVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> hndstModelVoList = new ArrayList<HndstModelVo>();
		
//		if(!StringUtils.isEmpty(hndstModelVo.getPrdtId()))
//		{
//		    hndstModelVo.setPrdtId(hndstModelVo.getPrdtId().toLowerCase());
//		}
		
		hndstModelVoList = hndstModelMapper.selectHndstModelList(hndstModelVo);
		
		return hndstModelVoList;
	}
	
	
    /**
     * @Description : 제품 정보를 생성한다.
     * @Param  : hndstModelVo
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	@Transactional(rollbackFor=Exception.class)
    public int insertHndstModel(HndstModelVo hndstModelVo){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품 등록 서비스 START."));
		logger.info(generateLogMsg("hndstModelVo == " + hndstModelVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = hndstModelMapper.insertHndstModel(hndstModelVo);
		
		logger.info(generateLogMsg("등록 건수 = ") + resultCnt);
		
        return resultCnt;
    }

    /**
     * @Description : 제품 정보 수정
     * @Param  : HndstModelVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 29.
     */
	@Transactional(rollbackFor=Exception.class)
    public int updateHndstModel(HndstModelVo hndstModelVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("제품 수정 서비스 START."));
        logger.info(generateLogMsg("hndstModelVo == " + hndstModelVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = hndstModelMapper.updateHndstModel(hndstModelVo);
        
        logger.info(generateLogMsg("수정 건수 = ") + resultCnt);
        
        return resultCnt;
    }
    
    /**
     * @Description : 제품명 중복체크
     * @Param  : HndstModelVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 29.
     */
    public int isExistHndstModel(HndstModelVo hndstModelVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("제품명 중복체크 START."));
        logger.info(generateLogMsg("hndstModelVo == " + hndstModelVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = hndstModelMapper.isExistHndstModel(hndstModelVo);
        
        return resultCnt;
    }
    
    /**
     * @Description : KT제품명 중복체크
     * @Param  : HndstModelVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 29.
     */
    public int isExistHndstModel2(HndstModelVo hndstModelVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("KT 제품명 중복체크 START."));
        logger.info(generateLogMsg("hndstModelVo == " + hndstModelVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = hndstModelMapper.isExistHndstModel2(hndstModelVo);
        
        return resultCnt;
    }
    
    public List<?> listPrdtColorCd(String mnfctCd){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("제품 색상코드 START."));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> resultList = new ArrayList<HndstModelVo>();

        resultList = hndstModelMapper.listPrdtColorCd(mnfctCd);
        
        return resultList;
    }
}
