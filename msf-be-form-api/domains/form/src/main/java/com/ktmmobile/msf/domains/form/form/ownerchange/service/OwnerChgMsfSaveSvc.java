package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.datasource.smartform.SmartFormDataSourceConfig;
import com.ktmmobile.msf.domains.form.form.common.repository.McpRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.repository.MsfRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.newchange.dao.AppformDao;
import com.ktmmobile.msf.domains.form.form.ownerchange.field.OwnerChangeFieldMapper;
import com.ktmmobile.msf.domains.form.form.termination.repository.CancelPageRepositoryImpl;

@Service
@RequiredArgsConstructor
public class OwnerChgMsfSaveSvc {

    private final CancelPageRepositoryImpl cancelPageRepository;
    private final MsfRequestRepositoryImpl msfRequestRepository;
    private final McpRequestRepositoryImpl mcpRequestRepository;
    private final OwnerChangeFieldMapper ownerChangeFieldMapper;
    private final AppformDao appformDao;

    @Transactional(transactionManager = SmartFormDataSourceConfig.SMARTFORM_TX_MANAGER)
    public void save(MsfRequestNameChgVo request) {

        long requestKey = cancelPageRepository.nextRequestKey();

        //예약번호
        request.setMcnResNo(mcpRequestRepository.selectGenerateResNo());

        //
        // /*** msf 데이터 저장  ***/
        //
        // 명의변경신청정보 저장
        request.setRequestKey(requestKey);
        msfRequestRepository.insertMsfRequestNameChg(request);
        // 명의변경양도인정보 저장
        msfRequestRepository.insertMsfRequestNameTrns(request);

        // 가입신청정보 저장
        MsfRequestCstmrVo msfRequestCstmrVo = ownerChangeFieldMapper.toMsfRequestCstmrVo(request);
        msfRequestRepository.insertMsfRequestCstmr(msfRequestCstmrVo);
        // 가입신청대리인정보 저장(미성년자인 경우)
        MsfRequestAgentVo msfRequestAgentVo = ownerChangeFieldMapper.toMsfRequestAgentVo(request);
        msfRequestRepository.insertMsfRequestAgent(msfRequestAgentVo);
        // 가입신청청구신청정보 저장
        MsfRequestBillReqVo msfRequestBillReqVo = ownerChangeFieldMapper.toMsfRequestBillReqVo(request);
        msfRequestRepository.insertMsfRequestBillReq(msfRequestBillReqVo);

    }
}
