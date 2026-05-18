package com.ktmmobile.msf.domains.form.main.application.service;

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
import com.ktmmobile.msf.domains.form.main.application.dto.FormMainCountResponse;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeHitsRequest;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeResponse;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaHitsRequest;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaResponse;
import com.ktmmobile.msf.domains.form.main.application.port.in.FormMainReader;
import com.ktmmobile.msf.domains.form.main.application.port.out.FormMainRepository;
import com.ktmmobile.msf.domains.form.main.domain.entity.Board;
import com.ktmmobile.msf.domains.form.main.domain.entity.Qna;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FormMainService implements FormMainReader {

    private final FormMainRepository formMainRepository;
    private final CommonCodeReader commonCodeReader;

    @Override
    public FormMainCountResponse getCountFormRequest() {
        return FormMainCountResponse.of(formMainRepository.getCountFormStatus(), formMainRepository.getCountFormService());
    }

    @Override
    public PagedDataResponse<NoticeResponse> getListNotice(NoticeCondition condition) {
        CommonCodesRequest request = CommonCodesRequest.of(List.of("SBST_CTG_CD1"), true, false);
        CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(request);

        Page<Board> page = formMainRepository.getListNotice(condition);
        return PagedDataResponse.of(page, entity -> NoticeResponse.of(entity, commonCodeGroups));
    }


    @Override
    @Transactional
    public Boolean addNoticeHits(NoticeHitsRequest request) {
        if (request.ids() == null || request.ids().isEmpty()) {
            return true;
        }
        return formMainRepository.addNoticeHits(request) > 0;
    }

    @Override public PagedDataResponse<QnaResponse> getListQna(QnaCondition condition) {
        CommonCodesRequest request = CommonCodesRequest.of(List.of("QNA_CTG_CD", "ANS_STTUS_CD"), true, false);
        CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(request);

        Page<Qna> page = formMainRepository.getListQna(condition.withLoginId(AuthenticationUtils.getUser().getUserId()));
        return PagedDataResponse.of(page, entity -> QnaResponse.of(entity, commonCodeGroups));
    }

    @Override
    @Transactional
    public Boolean addQnaHits(QnaHitsRequest request) {
        if (request.ids() == null || request.ids().isEmpty()) {
            return true;
        }
        return formMainRepository.addQnaHits(request) > 0;
    }
}
