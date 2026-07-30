package com.ktmmobile.msf.domains.form.extra.receipt.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.PagedDataResponse;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageCondition;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageResponse;
import com.ktmmobile.msf.domains.form.extra.receipt.application.port.in.ReceiptPageReader;
import com.ktmmobile.msf.domains.form.extra.receipt.application.port.out.ReceiptPageRepository;
import com.ktmmobile.msf.domains.form.extra.receipt.domain.entity.ReceiptPage;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReceiptPageService implements ReceiptPageReader {

    private final ReceiptPageRepository formRequestRepository;
    private final CommonCodeReader commonCodeReader;

    @Override
    public PagedDataResponse<ReceiptPageResponse> getList(ReceiptPageCondition condition) {
        CommonCodesRequest request = CommonCodesRequest.of(List.of("FORM_TYPE_CD",
            "REQ_BUY_TYPE_CD",
            "OPER_TYPE_CD",
            "CL01",
            "IDENTITY_CERT_TYPE_CD",
            "CSTMR_TYPE_CD"), true, false);
        CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(request);
        // 작성자 기준
        List<String> formTypelist = null;
        if (!condition.formTypeOne().equals("0")) {
            formTypelist = List.of(condition.formTypeOne());
        } else {
            formTypelist = List.of("1", "2", "3", "4");
        }
        log.debug("formType:{}, cretId:{}", formTypelist.toString(), AuthenticationUtils.getUser().getUserId());
        ReceiptPageCondition reCondition = condition.toBuilder()
            .formTypeCd(formTypelist)
            .authCretId(AuthenticationUtils.getUser().getUserId())
            .build();
        Page<ReceiptPage> page = formRequestRepository.selectList(reCondition);
        return PagedDataResponse.of(page,
            entity -> ReceiptPageResponse.of(entity, commonCodeGroups));
    }
}
