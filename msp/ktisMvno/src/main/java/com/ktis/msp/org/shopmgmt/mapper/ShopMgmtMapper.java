package com.ktis.msp.org.shopmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.shopmgmt.vo.ShopMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : ShopMgmtMapper
 * @Description : 판매점관리
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.03 이춘수 최초생성
 * @
 * @author : 
 * @Create Date : 
 */

@Mapper("shopMgmtMapper")
public interface ShopMgmtMapper {
	
	int isExistBizRegNum(ShopMgmtVO shopMgmtVO);
	
	List<?> getMappingShopList(ShopMgmtVO shopMgmtVO);
	
}
