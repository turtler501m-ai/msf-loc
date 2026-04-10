package com.ktis.msp.org.orgmgmt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.org.orgmgmt.mapper.OrgPopupMapper;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;

@Service
public class OrgPopupService extends BaseService {
	
	@Autowired
	private OrgPopupMapper orgPopupMapper;
	
   /** Constructor */
    public OrgPopupService() {
        setLogPrefix("[OrgPopupService] ");
    }
	
	@Crypto(decryptName="DBMSDec", fields = {"rprsenRrnum"})
	public List<?> getHeadAgencyOrgnList(OrgMgmtVO orgMgmtVO) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("getHeadAgencyOrgnList START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> listOrgMgmts = new ArrayList<OrgMgmtVO>();
		
		listOrgMgmts = orgPopupMapper.getHeadAgencyOrgnList(orgMgmtVO);
		
		return listOrgMgmts;
	}
	
	@Crypto(decryptName="DBMSDec", fields = {"rprsenRrnum"})
	public List<?> getShopOrgnList(OrgMgmtVO orgMgmtVO) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("getShopOrgnList START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> listOrgMgmts = new ArrayList<OrgMgmtVO>();
		
		listOrgMgmts = orgPopupMapper.getShopOrgnList(orgMgmtVO);
		
		return listOrgMgmts;
	}
	
	@Crypto(decryptName="DBMSDec", fields = {"rprsenRrnum"})
	public List<?> getShopOrgnListNew(OrgMgmtVO orgMgmtVO) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("getShopOrgnListNew START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> listOrgMgmts = new ArrayList<OrgMgmtVO>();
		
		listOrgMgmts = orgPopupMapper.getShopOrgnListNew(orgMgmtVO);
		
		return listOrgMgmts;
	}
	
    @Crypto(decryptName="DBMSDec", fields = {"rprsenRrnum"})
    public List<?> getSalesOrgnList(OrgMgmtVO orgMgmtVO) {
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("getSalesOrgnList START."));
        logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> listOrgMgmts = new ArrayList<OrgMgmtVO>();
        
        listOrgMgmts = orgPopupMapper.getSalesOrgnList(orgMgmtVO);
        
        return listOrgMgmts;
    }
    
    /* 대리점/판매점 조직리스트 조회 2022.06.07 추가 */
    @Crypto(decryptName="DBMSDec", fields = {"rprsenRrnum"})
	public List<?> getSalesOrgnNewList(OrgMgmtVO orgMgmtVO) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("getSalesOrgnNewList START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> listOrgMgmts = new ArrayList<OrgMgmtVO>();
		
		listOrgMgmts = orgPopupMapper.getSalesOrgnNewList(orgMgmtVO);
		
		return listOrgMgmts;
	}
    
    /** 유심접점조회 */
	public List<?> getUsimOrgList(OrgMgmtVO orgMgmtVO){	

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("getSalesOrgnList START."));
        logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
        logger.info(generateLogMsg("================================================================="));

        List<?> usimOrgList = new ArrayList<OrgMgmtVO>();
        
        usimOrgList = orgPopupMapper.getUsimOrgList(orgMgmtVO);
        
        return usimOrgList;
	}


	/** K-NOTE 접점 조회 */
	public List<?> getKnoteOrgList(OrgMgmtVO orgMgmtVO){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("getKnoteOrgList START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		List<?> knoteOrgList = new ArrayList<OrgMgmtVO>();

		knoteOrgList = orgPopupMapper.getKnoteOrgList(orgMgmtVO);

		return knoteOrgList;
	}
}
