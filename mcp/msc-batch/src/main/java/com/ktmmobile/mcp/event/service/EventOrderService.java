package com.ktmmobile.mcp.event.service;

import com.ktmmobile.mcp.event.dao.EventOrderDao;
import com.ktmmobile.mcp.event.dto.EventOrderDtlDto;
import com.ktmmobile.mcp.event.dto.EventOrderDto;
import com.ktmmobile.mcp.renew.dto.RenewReserveDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventOrderService {
    final EventOrderDao eventOrderDao;

    public EventOrderService(EventOrderDao eventOrderDao) {
        this.eventOrderDao = eventOrderDao;
    }

    @Transactional
    public boolean processEventOrderReserve(RenewReserveDto renewReserve) {
        int mstSeq = Integer.parseInt(renewReserve.getTrgKey());
        if (mstSeq == 0) {
            throw new NullPointerException("mstSeq is null");
        }

        List<EventOrderDtlDto> eventOrderDtlList = eventOrderDao.getEventOrderDtlListBySeq(mstSeq);
        eventOrderDao.updateEventBoardOdrgList(eventOrderDtlList);
        eventOrderDao.updateEventOrderComplete(mstSeq);

        return true;
    }
}
