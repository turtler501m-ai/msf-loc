package com.ktmmobile.mcp.retention.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.retention.dao.RetentionDao;
import com.ktmmobile.mcp.retention.dto.RetentionDto;


@Service
public class RetentionServiceImpl implements RetentionService {

    @Autowired
    private RetentionDao retentionDao;

    @Autowired
    SmsSvc smsSvc;

    @Value("${sms.callcenter}")
    private String callCenter;

    @Override
    public String selectRetentionUserId(RetentionDto retentionDto) {
        // 로직 필요하면...

        // 필요없으면
        return retentionDao.selectRetentionUserId(retentionDto);
    }

    @Override
    public int insertRetentionUserId(RetentionDto retentionDto) {


        return retentionDao.insertRetentionUserId(retentionDto);
    }

    @Override
    public int updateRetentionNotice(RetentionDto retentionDto) {

        return retentionDao.updateRetentionNotice(retentionDto);
    }


    @Override
    public List<RetentionDto> selectApyNotiTxtList(RetentionDto retentionDto) {
        return retentionDao.selectApyNotiTxtList(retentionDto);
    }

     /*sms 발송*/

    public boolean sendRetentionSms(RetentionDto retentionDto) {
        boolean result = true;

        String userId = retentionDto.getUserId();
        String message =retentionDto.getMessage();

        try {
            //smsSvc.sendLms("[kt M모바일] 약정만료 알림", retentionDto.getMobileNo(), message);
            smsSvc.sendLms("[kt M모바일] 약정만료 알림", retentionDto.getMobileNo(), message,callCenter,"sendRetention","SYSTEM");
        } catch(DataAccessException e) {
            return false;
        } catch(Exception e) {
            return false;
        }

        retentionDto.setUserId(userId);
        String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        retentionDto.setStartDate(today);

        return result;
    }
}
