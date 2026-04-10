package com.ktis.msp.org.shopmgmt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.orgmgmt.service.OrgMgmtService;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;
import com.ktis.msp.org.shopmgmt.mapper.ShopMgmtMapper;
import com.ktis.msp.org.shopmgmt.vo.ShopMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;
/**
 * @Class Name : ShopMgmtService
 * @Description : 판매점관리
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.03 이춘수 최초생성
 * @
 * @author : 
 * @Create Date : 
 */
@Service
public class ShopMgmtService extends BaseService {
	
	@Autowired
	private ShopMgmtMapper shopMgmtMapper;
		
	@Autowired
	private MaskingService maskingService;
	
	public ShopMgmtService() {
		setLogPrefix("[ShopMgmtService]");
	}

	public int isExistBizRegNum(ShopMgmtVO shopMgmtVo) {
		return shopMgmtMapper.isExistBizRegNum(shopMgmtVo);
	}
	
	public List<?> getMappingShopList(ShopMgmtVO shopMgmtVO, Map<String, Object> pRequestParamMap) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("getMappingShopList START."));
		logger.info(generateLogMsg("ShopMgmtVO == " + shopMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> list = new ArrayList<ShopMgmtVO>();
				
		list = shopMgmtMapper.getMappingShopList(shopMgmtVO);
		
		//masking
		HashMap<String, Object> maskFields = new HashMap<String, Object>();
		maskFields.put("rprsenNm", "CUST_NAME");
		maskFields.put("telnum", "TEL_NO");

		maskingService.setMask(list, maskFields, pRequestParamMap);
		
		return list;
	}
	
	private ShopMgmtVO maskingCheck(ShopMgmtVO shopMgmtVO) {
		//마스킹 대상 UPDATE
		String strCorpRegNum = null;//법인등록번호 뒷자리 5개
		String strRprsenNm = null;//대표자명
		String strRprsenRrnum = null;//대표자주민번호
		String strTelnum = null;//대표전화번호 중간 2자리
//    		String strTelnum2 = null;//대표자전화번호 마지막 1자리
		String strEmail = null;//이메일
		String strAddr1 = null;//주소
		String strAddr2 = null;//상세주소
		
		strCorpRegNum = shopMgmtVO.getCorpRegNum1()+shopMgmtVO.getCorpRegNum2();
		strRprsenNm = shopMgmtVO.getRprsenNm();
		strRprsenRrnum = shopMgmtVO.getRprsenRrnum1()+shopMgmtVO.getRprsenRrnum2();
		strTelnum = shopMgmtVO.getTelnum1()+shopMgmtVO.getTelnum2()+shopMgmtVO.getTelnum3();
		strEmail = shopMgmtVO.getEmail();
		strAddr1 = shopMgmtVO.getAddr1();
		strAddr2 = shopMgmtVO.getAddr2();
		
		int intCorpRegNum = 0;
		int intRprsenNm = 0;
		int intRprsenRrnum = 0;
		int intTelnum = 0;
		int intEmail = 0;
		int intAddr1 = 0;
		int intAddr2 = 0;

		if(!StringUtils.isEmpty(strCorpRegNum))
		{
			intCorpRegNum = strCorpRegNum.indexOf("*");
		}
		
		if(!StringUtils.isEmpty(strRprsenNm))
		{
			intRprsenNm = strRprsenNm.indexOf("*");
		}
		
		if(!StringUtils.isEmpty(strRprsenRrnum))
		{
			intRprsenRrnum = strRprsenRrnum.indexOf("*");
		}
		
		if(!StringUtils.isEmpty(strTelnum))
		{
			intTelnum = strTelnum.indexOf("*");
		}
		
		if(!StringUtils.isEmpty(strEmail))
		{
			intEmail = strEmail.indexOf("*");
		}
		
		if(!StringUtils.isEmpty(strAddr1))
		{
			intAddr1 = strAddr1.indexOf("*");
		}
		
		if(!StringUtils.isEmpty(strAddr2))
		{
			intAddr2 = strAddr2.indexOf("*");
		}

		
		if(intCorpRegNum == -1)
		{
			shopMgmtVO.setCorpRegNum(shopMgmtVO.getCorpRegNum1()+shopMgmtVO.getCorpRegNum2());
		}
		else
		{
			shopMgmtVO.setCorpRegNum(null);
		}

		if(intRprsenNm == -1)
		{
			shopMgmtVO.setRprsenNm(shopMgmtVO.getRprsenNm());
		}
		else
		{
			shopMgmtVO.setRprsenNm(null);
		}		
		
		if(intRprsenRrnum == -1)
		{
			shopMgmtVO.setRprsenRrnum(shopMgmtVO.getRprsenRrnum1()+shopMgmtVO.getRprsenRrnum2());
		}
		else
		{
			shopMgmtVO.setRprsenRrnum(null);
		}		
		
		if(intTelnum == -1)
		{
			shopMgmtVO.setTelnum(shopMgmtVO.getTelnum1()+shopMgmtVO.getTelnum2()+shopMgmtVO.getTelnum3());
		}
		else
		{
			shopMgmtVO.setTelnum(null);
		}
		
		if(intEmail == -1)
		{
			shopMgmtVO.setEmail(shopMgmtVO.getEmail());
		}
		else
		{
			shopMgmtVO.setEmail(null);
		}
		
		if(intAddr1 == -1)
		{
			shopMgmtVO.setAddr1(shopMgmtVO.getAddr1());
		}
		else
		{
			shopMgmtVO.setAddr1(null);
		}
		
		if(intAddr2 == -1)
		{
			shopMgmtVO.setAddr2(shopMgmtVO.getAddr2());
		}
		else
		{
			shopMgmtVO.setAddr2(null);
		}
		
		return shopMgmtVO;
	}
}
