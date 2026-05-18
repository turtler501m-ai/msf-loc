package com.ktmmobile.msf.domains.form.main.application.port.in;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.PagedDataResponse;
import com.ktmmobile.msf.domains.form.main.application.dto.FormMainCountResponse;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeHitsRequest;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeResponse;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaHitsRequest;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaResponse;

public interface FormMainReader {

    FormMainCountResponse getCountFormRequest();

    PagedDataResponse<NoticeResponse> getListNotice(NoticeCondition condition);

    Boolean addNoticeHits(NoticeHitsRequest request);

    PagedDataResponse<QnaResponse> getListQna(QnaCondition condition);

    Boolean addQnaHits(QnaHitsRequest request);
}
