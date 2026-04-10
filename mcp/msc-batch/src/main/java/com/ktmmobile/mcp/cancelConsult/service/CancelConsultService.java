package com.ktmmobile.mcp.cancelConsult.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.cancelConsult.dao.CancelConsultDao;

@Service
public class CancelConsultService {
    private static final Logger logger = LoggerFactory.getLogger(CancelConsultService.class);

    @Autowired
    private CancelConsultDao cancelConsultDao;


    public int selectCancelConsultCount() {
        return cancelConsultDao.selectCancelConsultCount();
    }

    public int deleteCancelConsult() {
        return cancelConsultDao.deleteCancelConsult();
    }

}
