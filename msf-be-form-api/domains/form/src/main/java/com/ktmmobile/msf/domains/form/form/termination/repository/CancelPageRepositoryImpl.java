package com.ktmmobile.msf.domains.form.form.termination.repository;

import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.repository.msp.MspCancelPageMapper;
import com.ktmmobile.msf.domains.form.form.termination.repository.smartform.CancelPageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CancelPageRepositoryImpl {

    private final CancelPageMapper cancelPageMapper;
    private final MspCancelPageMapper mspCancelPageMapper;

    public Long nextRequestKey() {
        return cancelPageMapper.nextRequestKey();
    }

    public int insertRequestCancel(TerminationApplyReqDto dto) {
        return cancelPageMapper.insertRequestCancel(dto);
    }

    public int selectPrePayment(String contractNum) {
        Integer count = mspCancelPageMapper.selectPrePayment(contractNum);
        return count == null ? 0 : count;
    }
}
