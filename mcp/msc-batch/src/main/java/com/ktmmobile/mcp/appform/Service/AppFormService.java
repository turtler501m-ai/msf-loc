package com.ktmmobile.mcp.appform.Service;

import com.ktmmobile.mcp.appform.dao.AppFormDao;
import com.ktmmobile.mcp.appform.dto.AppFormDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class AppFormService {

    @Autowired
    private AppFormDao appFormDao;

    public List<AppFormDto> selectDeleteMaterials() {

        return appFormDao.selectDeleteMaterials();
    }

    public int deleteMcpRequest(String requestKey) {

        return this.appFormDao.deleteMcpRequest(requestKey);
    }

    public int deleteMcpRequestCstmr(String requestKey) {

        return this.appFormDao.deleteMcpRequestCstmr(requestKey);
    }

    public int deleteMcpRequestSaleinfo(String requestKey) {

        return this.appFormDao.deleteMcpRequestSaleinfo(requestKey);
    }

    public int deleteMcpRequestDlvry(String requestKey) {

        return this.appFormDao.deleteMcpRequestDlvry(requestKey);
    }

    public int deleteMcpRequestReq(String requestKey) {

        return this.appFormDao.deleteMcpRequestReq(requestKey);
    }

    public int deleteMcpRequestMove(String requestKey) {

        return this.appFormDao.deleteMcpRequestMove(requestKey);
    }

    public int deleteMcpRequestPayment(String requestKey) {

        return this.appFormDao.deleteMcpRequestPayment(requestKey);
    }

    public int deleteMcpRequestAgent(String requestKey) {

        return this.appFormDao.deleteMcpRequestAgent(requestKey);
    }

    public int deleteMcpRequestState(String requestKey) {

        return this.appFormDao.deleteMcpRequestState(requestKey);
    }

    public int deleteMcpRequestChange(String requestKey) {

        return this.appFormDao.deleteMcpRequestChange(requestKey);
    }

    public int deleteMcpRequestDvcChg(String requestKey) {

        return this.appFormDao.deleteMcpRequestDvcChg(requestKey);
    }

    public int deleteMcpRequestAddition(String requestKey) {

        return this.appFormDao.deleteMcpRequestAddition(requestKey);
    }
    public int deleteMcpRequestOsst(String resNo) {
        return this.appFormDao.deleteMcpRequestOsst(resNo);
    }

    public int deleteMcpRequestKtInter(String resNo) {
        return this.appFormDao.deleteMcpRequestKtInter(resNo);
    }
    public int deleteMcpRequestDtl(String requestKey) {
        return this.appFormDao.deleteMcpRequestDtl(requestKey);
    }
}
