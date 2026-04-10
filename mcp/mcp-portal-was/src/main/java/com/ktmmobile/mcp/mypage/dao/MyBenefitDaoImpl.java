/**
 *
 */
package com.ktmmobile.mcp.mypage.dao;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.mspservice.dto.MspNoticSupportMstDto;
import com.ktmmobile.mcp.mypage.controller.MyGiftController;
import com.ktmmobile.mcp.mypage.dto.BenefitSearchDto;
import com.ktmmobile.mcp.mypage.dto.MyGiftDto;
import com.ktmmobile.mcp.point.dto.CustPointDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;

/**
 * @author ANT_FX700_02
 *
 */
@Repository
public class MyBenefitDaoImpl implements MyBenefitDao{

	private static Logger logger = LoggerFactory.getLogger(MyBenefitDaoImpl.class);
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /**
	 * @Description : 월별 포인트 이용현황 조회
	 * @param 
	 * @return 
	 * @Author : ant
	 * @Create Date :
	 */
	public List<CustPointTxnDto> selectMothlyPointList(String cntrNo){
		 return sqlSessionTemplate.selectList("MyBenefitMapper.getPointSumeryList", cntrNo);
	}

	/**
	 * @Description : 고객 포인트 조회
	 * @param 
	 * @return 
	 * @Author : ant
	 * @Create Date :
	 */
	
	public CustPointDto selectCustPoint(String cntrNo) {
		return sqlSessionTemplate.selectOne("MyBenefitMapper.selectCustPoint", cntrNo);
	}
	
	
	public List<CustPointTxnDto> getSelectPointDetailList(BenefitSearchDto vo, int skipResult, int maxResult) {
		return sqlSessionTemplate.selectList("MyBenefitMapper.getSelectPointDetailList", vo, new RowBounds(skipResult, maxResult));
	}
	
	public int getSelectPointDetailListCnt(BenefitSearchDto vo) {
		return sqlSessionTemplate.selectOne("MyBenefitMapper.getSelectPointDetailListCnt", vo);
		
	}

	@Override
	public List<MyGiftDto> getGiftCustList(MyGiftDto myGiftDto, int skipResult, int maxResult) {
		myGiftDto.setApiParam1(skipResult);
		myGiftDto.setApiParam2(maxResult);

        RestTemplate restTemplate = new RestTemplate();
        MyGiftDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/giftCustList", myGiftDto, MyGiftDto[].class);
		List<MyGiftDto> list = Arrays.asList(resultList);
		return list;
	}

	@Override
	public int getCustListCount(MyGiftDto myGiftDto) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/mypage/giftCustListCount", myGiftDto, int.class);
	}
    
   
}
