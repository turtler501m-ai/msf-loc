package com.ktmmobile.msf.domains.form.extra.tempsave.application.service;

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
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageResponse;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.port.in.TempSavePageReader;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.port.out.TempSavePageRepository;
import com.ktmmobile.msf.domains.form.extra.tempsave.domain.entity.TempSavePage;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TempSavePageService implements TempSavePageReader {

    private final TempSavePageRepository tempSavePageRepository;
    private final CommonCodeReader commonCodeReader;

    @Override
    public PagedDataResponse<TempSavePageResponse> getList(TempSavePageCondition condition) {
        CommonCodesRequest request = CommonCodesRequest.of(List.of("FORM_TYPE_CD",
            "REQ_BUY_TYPE_CD",
            "OPER_TYPE_CD",
            "CL01",
            "IDENTITY_CERT_TYPE_CD",
            "CSTMR_TYPE_CD"), true, false);
        CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(request);
        // 작성자 기준
        List<String> formTypelist = List.of("1");  // 신규/변경 신청서
        log.debug("formType:{}, cretId:{}", formTypelist.toString(), AuthenticationUtils.getUser().getUserId());
        TempSavePageCondition reCondition = condition.toBuilder()
            .formTypeCd(formTypelist)
            .authShopCd(AuthenticationUtils.getShopCode())              // molo -- test 용
            //.authCretId(AuthenticationUtils.getUser().getUserId()) // molo -- 이 조건을 사용해야 함.
            .build();
        Page<TempSavePage> page = tempSavePageRepository.selectList(reCondition);
        return PagedDataResponse.of(page,
            entity -> TempSavePageResponse.of(entity, commonCodeGroups));
    }
}
