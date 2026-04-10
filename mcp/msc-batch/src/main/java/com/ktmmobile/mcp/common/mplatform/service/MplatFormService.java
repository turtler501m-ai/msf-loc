package com.ktmmobile.mcp.common.mplatform.service;

import com.ktmmobile.mcp.common.exception.McpMplatFormException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.dto.MPhoneNoListXmlVO;
import com.ktmmobile.mcp.common.mplatform.dto.MSimpleOsstXmlVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.HashMap;

@Service
public class MplatFormService {

    private static final Logger logger = LoggerFactory.getLogger(MplatFormService.class);

    @Autowired
    private MplatFormOsstServerAdapter mplatFormOsstServerAdapter;

    public MSimpleOsstXmlVO sendOsstService(String resNo, String eventCd) throws SelfServiceException, SocketTimeoutException, McpMplatFormException {

        MSimpleOsstXmlVO simpleOsstXmlVO =  new MSimpleOsstXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);
        mplatFormOsstServerAdapter.callService(param, simpleOsstXmlVO,100000);
        return simpleOsstXmlVO;
    }


    public MPhoneNoListXmlVO sendOsstSvcNoService(String resNo, String wantTelNo, String eventCd) throws SelfServiceException, SocketTimeoutException, McpMplatFormException {

        MPhoneNoListXmlVO mPhoneNoListXmlVO =  new MPhoneNoListXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);
        param.put("tlphNo", wantTelNo);
        mplatFormOsstServerAdapter.callService(param, mPhoneNoListXmlVO,100000);
        return mPhoneNoListXmlVO;
    }

    public MSimpleOsstXmlVO sendOsstResvTlphService(String resNo, String eventCd, String gubun) throws SelfServiceException, SocketTimeoutException, McpMplatFormException {

        MSimpleOsstXmlVO simpleOsstXmlVO =  new MSimpleOsstXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);
        param.put("gubun", gubun);
        mplatFormOsstServerAdapter.callService(param, simpleOsstXmlVO,100000);
        return simpleOsstXmlVO;
    }
}
