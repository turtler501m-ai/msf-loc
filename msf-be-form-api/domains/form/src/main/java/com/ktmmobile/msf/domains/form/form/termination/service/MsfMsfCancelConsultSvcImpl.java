package com.ktmmobile.msf.domains.form.form.termination.service;

import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.domains.form.form.termination.dao.CancelConsultDao;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class MsfMsfCancelConsultSvcImpl implements MsfCancelConsultSvc {

    private static final Logger logger = LoggerFactory.getLogger(MsfMsfCancelConsultSvcImpl.class);

    @Autowired
    private CancelConsultDao cancelConsultDao;

    @Autowired
    private MsfMplatFormService msfMplatFormService;

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

    @Override
    public RemainChargeResVO getRemainCharge(RemainChargeReqDto reqDto) {
        RemainChargeResVO resVO = new RemainChargeResVO();
        try {
            MpFarRealtimePayInfoVO mpVO = msfMplatFormService.farRealtimePayInfo(
                    reqDto.getNcn(), reqDto.getCtn(), reqDto.getCustId());

            if (mpVO == null) {
                resVO.setSuccess(false);
                resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
                return resVO;
            }

            resVO.setSuccess(true);
            resVO.setSearchDay(mpVO.getSearchDay());
            resVO.setSearchTime(mpVO.getSearchTime());
            resVO.setSumAmt(mpVO.getSumAmt());

            List<RemainChargeResVO.FareItem> items = new ArrayList<>();
            if (mpVO.getList() != null) {
                for (MpFarRealtimePayInfoVO.RealFareVO realFare : mpVO.getList()) {
                    if ("원단위절사금액".equals(realFare.getGubun())) {
                        continue;
                    }
                    RemainChargeResVO.FareItem item = new RemainChargeResVO.FareItem();
                    item.setGubun(realFare.getGubun());
                    item.setPayment(realFare.getPayment());
                    items.add(item);
                }
            }
            resVO.setItems(items);
        } catch (com.ktmmobile.msf.domains.form.common.exception.SelfServiceException e) {
            logger.info("X18 ERROR: ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn(), e);
            resVO.setSuccess(false);
            resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
        } catch (java.net.SocketTimeoutException e) {
            logger.info("X18 ERROR (Timeout): ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn(), e);
            resVO.setSuccess(false);
            resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
        } catch (Exception e) {
            logger.error("X18 잔여요금 조회 오류: ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn(), e);
            resVO.setSuccess(false);
            resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
        }
        return resVO;
    }
}
