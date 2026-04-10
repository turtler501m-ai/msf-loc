package com.ktmmobile.mcp.renew.service;

import com.ktmmobile.mcp.event.service.EventOrderService;
import com.ktmmobile.mcp.rate.guide.service.RateGuideService;
import com.ktmmobile.mcp.renew.dao.RenewReserveDao;
import com.ktmmobile.mcp.renew.dto.RenewReserveDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.common.constant.Constants.RENEW_RESERVE_CD_EVENT_ORDER;
import static com.ktmmobile.mcp.common.constant.Constants.RENEW_RESERVE_CD_RATE_ADSVC_GDNC;

@Service
public class RenewReserveService {
    private static final Logger logger = LoggerFactory.getLogger(RenewReserveService.class);
    private final RenewReserveDao renewReserveDao;
    private final EventOrderService eventOrderService;
    private final RateGuideService rateGuideService;

    public RenewReserveService(RenewReserveDao renewReserveDao, EventOrderService eventOrderService, RateGuideService rateGuideService) {
        this.renewReserveDao = renewReserveDao;
        this.eventOrderService = eventOrderService;
        this.rateGuideService = rateGuideService;
    }

    public Map<String, Object> processRenewReserveList(String reserveDate) {
        Map<String, Object> result = new HashMap<>();
        int successCnt = 0;
        int failCnt = 0;

        List<RenewReserveDto> renewReserveList = renewReserveDao.getRenewReserveList(reserveDate);
        for (RenewReserveDto renewReserve : renewReserveList) {
            renewReserveDao.updateRenewReservePending(renewReserve.getRenewSeq());
        }

        for (RenewReserveDto renewReserve : renewReserveList) {
            boolean isSuccess = false;
            try {
                isSuccess = this.processRenewReserveByRenewCd(renewReserve);
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error(Arrays.toString(e.getStackTrace()));
            } finally {
                if (isSuccess) {
                    successCnt++;
                    renewReserveDao.updateRenewReserveSuccess(renewReserve);
                } else {
                    failCnt++;
                    renewReserveDao.updateRenewReserveFail(renewReserve);
                }
            }
        }

        result.put("successCnt", successCnt);
        result.put("failCnt", failCnt);
        result.put("totalCnt", renewReserveList.size());

        return result;
    }

    private boolean processRenewReserveByRenewCd(RenewReserveDto renewReserve) {
        switch (renewReserve.getRenewCd()) {
            case RENEW_RESERVE_CD_RATE_ADSVC_GDNC:
                return rateGuideService.createAndUploadAllXmlFiles();
            case RENEW_RESERVE_CD_EVENT_ORDER:
                return eventOrderService.processEventOrderReserve(renewReserve);
            default:
                throw new InvalidParameterException("Invalid RenewCd : " + renewReserve.getRenewCd());
        }
    }
}
