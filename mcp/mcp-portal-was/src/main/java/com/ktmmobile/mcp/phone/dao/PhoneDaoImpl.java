package com.ktmmobile.mcp.phone.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.phone.dto.PhoneSntyBasDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
//
//import com.ktmmobile.mcp.order.dto.OrderDto;
//import com.ktmmobile.mcp.phone.dto.AnnouncementDto;
//import com.ktmmobile.mcp.phone.dto.CommonSearchDto;
//import com.ktmmobile.mcp.phone.dto.NmcpEventBoardBasDto;
//import com.ktmmobile.mcp.phone.dto.PhoneMspDto;
//import com.ktmmobile.mcp.phone.dto.PhoneProdAttrDto;
//import com.ktmmobile.mcp.phone.dto.PhoneProdBasDto;
//import com.ktmmobile.mcp.phone.dto.PhoneProdImgDetailDto;
//import com.ktmmobile.mcp.phone.dto.PhoneSntyBasDto;

import com.ktmmobile.mcp.phone.dto.CommonSearchDto;
import com.ktmmobile.mcp.phone.dto.PhoneMspDto;
import com.ktmmobile.mcp.phone.dto.PhoneProdBasDto;


/**
 * @Class Name : PhoneDaoImpl
 * @Description : 핸드폰 상품 Dao 구현클래스
 *
 * @author : ant
 * @Create Date : 2015. 12. 31.
 */
@Repository
public class PhoneDaoImpl implements PhoneDao {

    @Value("${api.interface.server}")
	private String apiInterfaceServer;

	@Override
	public List<PhoneMspDto> listPHoneMspDto(CommonSearchDto param) {
		RestTemplate restTemplate = new RestTemplate();
		PhoneMspDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/api/storeUsim/modelList", param, PhoneMspDto[].class);
		List<PhoneMspDto> list = Arrays.asList(resultList);
		return list;
	}

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.phone.dao.PhoneDao#findPhoneProdOne(com.ktmmobile.mcp.phone.dto.PhoneProdBasDto)
     */
    @Override
    public PhoneProdBasDto findPhoneProd(PhoneProdBasDto phoneProdBasDto) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/phone/nmcpProdBas", phoneProdBasDto, PhoneProdBasDto.class);

    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.phone.dao.PhoneDao#listPhoneProdBasForFrontOneQuery(com.ktmmobile.mcp.phone.dto.CommonSearchDto)
     */
    @Override
    public List<PhoneProdBasDto> listPhoneProdBasForFrontOneQuery(
            CommonSearchDto commonSearchDto) {
    	RestTemplate restTemplate = new RestTemplate();
    	PhoneProdBasDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/phone/phoneProdBasForFrontOneQuery", commonSearchDto, PhoneProdBasDto[].class);
		List<PhoneProdBasDto> list = Arrays.asList(resultList);
		return list;

    }

    /* (non-Javadoc
     * @see com.ktmmobile.mcp.phone.dao.PhoneDao#findLte3GphoneCount(com.ktmmobile.mcp.phone.dto.CommonSearchDto)
     */
    @SuppressWarnings("unchecked")
	@Override
    public Map<String, Integer> findLte3GphoneCount(
            CommonSearchDto commonSearchDto) {
    	RestTemplate restTemplate = new RestTemplate();
    	return (Map<String, Integer>)restTemplate.postForObject(apiInterfaceServer + "/phone/lte3GphoneCount", commonSearchDto, Map.class);

    }

	@Override
	public List<PhoneSntyBasDto> selectProdBasSnty(PhoneSntyBasDto phoneProdBasDto) {
		RestTemplate restTemplate = new RestTemplate();
		PhoneSntyBasDto[] result = restTemplate.postForObject(apiInterfaceServer + "/phone/selectPhonePordSnty", phoneProdBasDto, PhoneSntyBasDto[].class);
		List<PhoneSntyBasDto> resultList = Arrays.asList(result);
		return resultList;
	}

}
