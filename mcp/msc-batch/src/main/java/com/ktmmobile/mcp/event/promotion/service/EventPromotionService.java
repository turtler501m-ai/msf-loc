package com.ktmmobile.mcp.event.promotion.service;

import com.ktmmobile.mcp.event.promotion.dao.EventPromotionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EventPromotionService {
    private static final Logger logger = LoggerFactory.getLogger(EventPromotionService.class);

    private final EventPromotionDao eventPromotionDao;

    public EventPromotionService(EventPromotionDao eventPromotionDao) {
        this.eventPromotionDao = eventPromotionDao;
    }

    public Map<String, Object> deleteExpiredJoinHist() {
        Map<String, Object> result = new HashMap<>();
        int failCnt = 0;

        int successCnt = eventPromotionDao.deleteExpiredJoinList();
        eventPromotionDao.expireEventPromotion();

        logger.error("deleteExpiredJoinHist >> count : {}", successCnt);

        result.put("successCnt", successCnt);
        result.put("failCnt", failCnt);
        result.put("totalCnt", successCnt + failCnt);

        return result;
    }
}
