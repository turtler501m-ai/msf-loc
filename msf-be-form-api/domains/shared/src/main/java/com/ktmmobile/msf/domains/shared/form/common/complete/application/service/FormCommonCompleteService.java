package com.ktmmobile.msf.domains.shared.form.common.complete.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.domains.shared.form.common.complete.application.dto.CompletedFormCondition;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.dto.CompletedFormResponse;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.port.in.FormCommonCompleteReader;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.port.out.FormCommonCompleteRepository;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.code.RequestFormCstmrType;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.code.RequestFormType;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity.CompletedRequestForm;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FormCommonCompleteService implements FormCommonCompleteReader {

    private final FormCommonCompleteRepository formCommonCompleteRepository;

    @Override public CompletedRequestForm getCompletedForm(CompletedFormCondition condition) {
        return formCommonCompleteRepository.getCompletedRequestForm(condition);
    }

    @Override public CompletedFormResponse getCompletedFormResponse(CompletedFormCondition condition) {
        CompletedRequestForm form = getCompletedForm(condition);

        CompletedFormResponse response = CompletedFormResponse.of(form.getFormType(),
            form.getRequestKey(),
            form.getBaseScanId(),
            form.getScanId(),
            form.getCstmrNm());
        CompletedFormResponse.CompletedFormMobileInfo mobile = generateFirstMobileInfo(form);
        return response.withMobiles(List.of(CompletedFormResponse.CompletedFormMobileInfo.of(null, mobile.mobile())));
    }

    @Override public CompletedFormResponse.CompletedFormMobileInfo generateFirstMobileInfo(CompletedRequestForm form) {
        // 내국인 미성년자(19세 미만), 외국인 미성년자(19세 미만): 법정대리인번호
        if (RequestFormCstmrType.NATIVEMINOR.equals(form.getCstmrTypeCd()) || RequestFormCstmrType.FOREIGNERMINOR.equals(form.getCstmrTypeCd())) {
            if (StringUtils.hasText(form.getMinorAgentTelNo())) {
                return CompletedFormResponse.CompletedFormMobileInfo.of(
                    form.getMinorAgentNm(),
                    form.getMinorAgentTelNo()
                );
            }
            return CompletedFormResponse.CompletedFormMobileInfo.of(null, null);
        }

        // 서비스해지: 해지후 연락처번호
        if (RequestFormType.TERMINATION.equals(form.getFormType())) {
            if (StringUtils.hasText(form.getCstmrReceiveTelNo()) && form.getCstmrReceiveTelNo().startsWith("010")) {
                return CompletedFormResponse.CompletedFormMobileInfo.of(
                    form.getCstmrNm(),
                    form.getCstmrReceiveTelNo()
                );
            }
            return CompletedFormResponse.CompletedFormMobileInfo.of(null, null);
        }

        // 신규/변경-신규개통: 신규개통번호
        // 신규/변경-번호이동: 번호이동번호
        // 신규/변경-기기변경: 기기변경번호
        // 서비스변경: 서비스변경번호
        // 명의변경: 명의변경번호
        if (StringUtils.hasText(form.getMobileNo())) {
            return CompletedFormResponse.CompletedFormMobileInfo.of(
                form.getCstmrNm(),
                form.getMobileNo()
            );
        }

        return CompletedFormResponse.CompletedFormMobileInfo.of(null, null);
    }

    @Override public CompletedFormResponse.CompletedFormMobileInfo generateSecondMobileInfo(CompletedRequestForm form) {
        // 신규/변경: 연락처번호
        if (RequestFormType.NEWCHANGE.equals(form.getFormType())) {
            if (StringUtils.hasText(form.getCstmrMobileNo()) && form.getCstmrMobileNo().startsWith("010")) {
                return CompletedFormResponse.CompletedFormMobileInfo.of(
                    form.getCstmrNm(),
                    form.getCstmrMobileNo()
                );
            }
            return CompletedFormResponse.CompletedFormMobileInfo.of(null, null);
        }

        // 서비스해지
        if (RequestFormType.TERMINATION.equals(form.getFormType())) {
            return CompletedFormResponse.CompletedFormMobileInfo.of(null, null);
        }

        // 서비스변경: 연락처번호
        // 명의변경: 양수인연락처번호
        if (StringUtils.hasText(form.getCstmrReceiveTelNo()) && form.getCstmrReceiveTelNo().startsWith("010")) {
            return CompletedFormResponse.CompletedFormMobileInfo.of(
                form.getCstmrNm(),
                form.getCstmrReceiveTelNo()
            );
        }

        return CompletedFormResponse.CompletedFormMobileInfo.of(null, null);
    }
}
