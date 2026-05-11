package com.ktmmobile.msf.domains.eformsign.application.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.eformsign.application.dto.EformApiTokenResponse;
import com.ktmmobile.msf.domains.eformsign.application.dto.VerifyFormPwRequest;
import com.ktmmobile.msf.domains.eformsign.application.dto.VerifyFormPwResponse;
import com.ktmmobile.msf.domains.eformsign.application.port.in.EformReader;
import com.ktmmobile.msf.domains.eformsign.application.port.in.EformWriter;
import com.ktmmobile.msf.domains.eformsign.application.port.out.EformClient;
import com.ktmmobile.msf.domains.eformsign.application.port.out.EformRepository;
import com.ktmmobile.msf.domains.eformsign.domain.code.CstmrType;
import com.ktmmobile.msf.domains.eformsign.domain.code.FormType;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EformService implements EformReader, EformWriter {

    private final EformClient eformClient;
    private final EformRepository eformRepository;
    // private final NewChangeService newChangeService;

    /*@Override
    public List<FormResponse> formSubmit(EFormRequest request) {


        return formClient.formSubmit(request);
    }*/

    @Override
    public EformApiTokenResponse getEformApiToken() {
        return eformClient.getEformApiToken();
    }

    @Override
    public VerifyFormPwResponse verifyFormPw(VerifyFormPwRequest request) {

        CstmrType cstmrType = CstmrType.valueOfCode(request.cstmrTypeCd());
        FormType formType = FormType.valueOfCode(request.formTypeCd());

        Boolean isSuccess = eformRepository.verifyFormPw(
            request,
            cstmrType.getAuthField(),
            formType.getRequestResource()
        );

        return null;
    }
}
