package com.ktmmobile.msf.common.service;

import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.common.dto.PhoneSntyBasDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.common.dao.PhoneDao;
import com.ktmmobile.msf.common.dto.CommonSearchDto;
import com.ktmmobile.msf.common.dto.PhoneProdBasDto;


/**
 * @Class Name : PhoneServiceImpl
 * @Description : 핸드폰상품서비스 구현체
 *
 * @author : ant
 * @Create Date : 2016. 1. 4.
 */
@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    PhoneDao phoneDao;

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.phone.service.PhoneService#findPhoneProdBasDtoByProdId(com.ktmmobile.mcp.phone.dto.PhoneProdBasDto)
     */
    @Override
    public PhoneProdBasDto findPhoneProdBasDtoByProdId(
            PhoneProdBasDto phoneProdBasDto) {
        return phoneDao.findPhoneProd(phoneProdBasDto);
    }

    @Override
    public List<PhoneProdBasDto> listPhoneProdBasForFrontOneQuery(
            CommonSearchDto commonSearchDto) {

        return phoneDao.listPhoneProdBasForFrontOneQuery(commonSearchDto);
    }

    @Override
    public Map<String, Integer> findLte3GphoneCount(
            CommonSearchDto commonSearchDto) {
        return phoneDao.findLte3GphoneCount(commonSearchDto);
    }

    @Override
    public List<PhoneSntyBasDto> selectProdBasSnty(PhoneSntyBasDto phoneProdBasDto) {
        return this.phoneDao.selectProdBasSnty(phoneProdBasDto);
    }

}
