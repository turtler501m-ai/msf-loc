package com.ktmmobile.msf.domains.form.main.adapter.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.domains.form.main.adapter.repository.mybatis.smartform.mapper.FormMainMapper;
import com.ktmmobile.msf.domains.form.main.application.dto.ChartCountResponse;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeHitsRequest;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaHitsRequest;
import com.ktmmobile.msf.domains.form.main.application.port.out.FormMainRepository;
import com.ktmmobile.msf.domains.form.main.domain.entity.ChartCount;
import com.ktmmobile.msf.domains.form.main.domain.entity.Board;
import com.ktmmobile.msf.domains.form.main.domain.entity.Qna;

@RequiredArgsConstructor
@Repository
public class FormMainRepositoryImpl implements FormMainRepository {

    private final FormMainMapper formMainMapper;

    @Override public List<ChartCountResponse> getCountFormStatus() {
        List<ChartCount> list = formMainMapper.selectCountFormStatus();
        if (list == null) {
            return List.of();
        }
        return list.stream().map((entity) -> ChartCountResponse.of(entity.code(), entity.name(), entity.count())).toList();
    }

    @Override public List<ChartCountResponse> getCountFormService() {
        List<ChartCount> list = formMainMapper.selectCountFormService();
        if (list == null) {
            return List.of();
        }
        return list.stream().map((entity) -> ChartCountResponse.of(entity.code(), entity.name(), entity.count())).toList();
    }

    @Override public Page<Board> getListNotice(NoticeCondition condition) {
        Integer totalCount = formMainMapper.selectCountNotice(condition);
        List<Board> list = formMainMapper.selectListNotice(condition);
        return Page.of(list, condition.page(), totalCount);
    }

    @Override public Integer addNoticeHits(NoticeHitsRequest request) {
        return formMainMapper.updateAddNoticeHits(request);
    }

    @Override public Page<Qna> getListQna(QnaCondition condition) {
        Integer totalCount = formMainMapper.selectCountQna(condition);
        List<Qna> list = formMainMapper.selectListQna(condition);
        return Page.of(list, condition.page(), totalCount);
    }

    @Override public Integer addQnaHits(QnaHitsRequest request) {
        return formMainMapper.updateAddQnaHits(request);
    }
}
