package com.ktmmobile.msf.domains.form.form.termination.repository;

import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommReadMapper;
import com.ktmmobile.msf.domains.form.form.termination.repository.msp.MspCancelPageMapper;
import com.ktmmobile.msf.domains.form.form.termination.repository.smartform.CancelPageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CancelPageRepositoryImpl {

    private final CancelPageMapper cancelPageMapper;
    private final MspCancelPageMapper mspCancelPageMapper;
    private final FormCommReadMapper formCommReadMapper;

    public Long nextRequestKey() {
        return formCommReadMapper.generateRequestKey();
    }

    public boolean existsInProgressApplicationByMobileNo(String mobileNo) {
        if (mobileNo == null || mobileNo.isBlank()) {
            return false;
        }
        return cancelPageMapper.countInProgressApplicationByMobileNo(mobileNo) > 0;
    }

    public int selectPrePayment(String contractNum) {
        Integer count = mspCancelPageMapper.selectPrePayment(contractNum);
        return count == null ? 0 : count;
    }
}
