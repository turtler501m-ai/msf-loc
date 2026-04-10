/**
 *
 */
package com.ktmmobile.mcp.mypage.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.mypage.dao.MyBenefitDao;
import com.ktmmobile.mcp.mypage.dto.BenefitSearchDto;
import com.ktmmobile.mcp.mypage.dto.MyGiftDto;
import com.ktmmobile.mcp.point.dto.CustPointDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;
import com.ktmmobile.mcp.point.service.PointService;

/**
 * @author ANT_FX700_02
 *
 */
@Service
public class MyBenefitServiceImpl implements MyBenefitService{

	@Value("${api.interface.server}")
    private String apiInterfaceServer;
	
	@Autowired
	MyBenefitDao myBenefitDao;
	
	@Autowired
	PointService pointService;
	
	public List<CustPointTxnDto> selectMothlyPointList(String cntrNo) {
		return myBenefitDao.selectMothlyPointList(cntrNo);
    }
	
	public CustPointDto selectCustPoint(String cntrNo) {
		return myBenefitDao.selectCustPoint(cntrNo);
	}
	
	public List<CustPointTxnDto> getSelectPointDetailList(BenefitSearchDto vo, int skipResult, int maxResult){
		return myBenefitDao.getSelectPointDetailList(vo,skipResult,maxResult);
	}
	
	public int getSelectPointDetailListCnt(BenefitSearchDto vo){
		return myBenefitDao.getSelectPointDetailListCnt(vo);
	}
	
	@Transactional
	public String insertCouponGift(CustPointTxnDto custPointTxnDto){
		pointService.editPoint(custPointTxnDto);
		return "";
	}

	@Override
	public List<MyGiftDto> getGiftCustList(MyGiftDto myGiftDto, int skipResult, int maxResult) {
		// TODO Auto-generated method stub
		return myBenefitDao.getGiftCustList(myGiftDto, skipResult, maxResult);
	}

	@Override
	public int getCustListCount(MyGiftDto myGiftDto) {
		// TODO Auto-generated method stub
		return myBenefitDao.getCustListCount(myGiftDto);
	}

}
