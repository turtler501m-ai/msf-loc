package com.ktis.msp.org.orgmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("orgPopupMapper")
public interface OrgPopupMapper {
	
	List<?> getHeadAgencyOrgnList(OrgMgmtVO orgMgmtVO);
	
	List<?> getShopOrgnList(OrgMgmtVO orgMgmtVO);
	
	List<?> getShopOrgnListNew(OrgMgmtVO orgMgmtVO);
	
	List<?> getSalesOrgnList(OrgMgmtVO orgMgmtVO);
	
	/* 대리점/판매점 조직리스트 조회 2022.06.07 추가 */
	List<?> getSalesOrgnNewList(OrgMgmtVO orgMgmtVO);
	
	/** 유심접점조회 */
	List<?> getUsimOrgList(OrgMgmtVO orgMgmtVO);

	/** K-Note 접점 조회 */
	List<?> getKnoteOrgList(OrgMgmtVO orgMgmtVO);
}
