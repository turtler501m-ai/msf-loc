package com.ktmmobile.msf.form.servicechange.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.form.servicechange.dao.CancelConsultDao;
import com.ktmmobile.msf.form.servicechange.dto.CancelConsultDto;


@Service
public class CancelConsultSvcImpl implements CancelConsultSvc {

    @Autowired
    private CancelConsultDao cancelConsultDao;

    @Override
    public int countCancelConsult(CancelConsultDto cancelConsultDto) {
        return cancelConsultDao.countCancelConsult(cancelConsultDto);
    }

    @Override
    @Transactional
    public String cancelConsultRequest(CancelConsultDto cancelConsultDto) {
        String result = "";

        cancelConsultDao.insertNmcpCustReqMst(cancelConsultDto);
        cancelConsultDao.insertCancelConsult(cancelConsultDto);

        result = "SUCCESS";

        return result;
    }

    @Override
    public List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto) {
        return cancelConsultDao.selectCancelConsultList(cancelConsultDto);
    }
}
