package com.ktmmobile.msf.domains.form.form.termination.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;
import com.ktmmobile.msf.domains.form.form.termination.repository.CancelConsultRepositoryImpl;


@Service
public class MsfCancelConsultSvcImpl implements MsfCancelConsultSvc {

    private static final Logger logger = LoggerFactory.getLogger(MsfCancelConsultSvcImpl.class);

    @Autowired
    private CancelConsultRepositoryImpl cancelConsultRepository;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public int countCancelConsult(CancelConsultDto cancelConsultDto) {
        return cancelConsultRepository.countCancelConsult(cancelConsultDto);
    }

    @Override
    @Transactional
    public String cancelConsultRequest(CancelConsultDto cancelConsultDto) {
        String result = "";

        cancelConsultRepository.insertNmcpCustReqMst(cancelConsultDto);
        cancelConsultRepository.insertCancelConsult(cancelConsultDto);

        result = "SUCCESS";

        return result;
    }

    @Override
    public List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto) {
        return cancelConsultRepository.selectCancelConsultList(cancelConsultDto);
    }

}
