package com.ktmmobile.msf.domains.form.main.application.port.out;

import java.util.List;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.domains.form.main.application.dto.ChartCountResponse;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeHitsRequest;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaHitsRequest;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaRequest;
import com.ktmmobile.msf.domains.form.main.domain.entity.Board;
import com.ktmmobile.msf.domains.form.main.domain.entity.Qna;

public interface FormMainRepository {

    List<ChartCountResponse> getCountFormStatus();

    List<ChartCountResponse> getCountFormService();

    Page<Board> getListNotice(NoticeCondition condition);

    Integer addNoticeHits(NoticeHitsRequest request);

    Page<Qna> getListQna(QnaCondition condition);

    Integer addQnaHits(QnaHitsRequest request);

    Integer registQna(QnaRequest request);
}
