package com.ktmmobile.mcp.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.event.dao.AgreeTermsEventDao;
import com.ktmmobile.mcp.event.dto.AgreeTermsEventDto;

@Service
public class AgreeTermsEventServiceImpl implements AgreeTermsEventService {


    @Autowired
    private AgreeTermsEventDao agreeTermsEventDao;

    @Override
    public int agreeJoinInsert(AgreeTermsEventDto agreeTermsEventDto) {
        return agreeTermsEventDao.agreeJoinInsert(agreeTermsEventDto);
    }


}
