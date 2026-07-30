package com.ktmmobile.msf.domains.form.main.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeHitsRequest;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaHitsRequest;
import com.ktmmobile.msf.domains.form.main.domain.entity.Board;
import com.ktmmobile.msf.domains.form.main.domain.entity.ChartCount;
import com.ktmmobile.msf.domains.form.main.domain.entity.Qna;

@AutoAuditing
@Mapper
public interface FormMainMapper {

    List<ChartCount> selectCountFormStatus(String userId);

    List<ChartCount> selectCountFormService(String userId);

    Integer selectCountNotice(NoticeCondition condition);

    List<Board> selectListNotice(NoticeCondition condition);

    Integer updateAddNoticeHits(NoticeHitsRequest request);

    Integer selectCountQna(QnaCondition condition);

    List<Qna> selectListQna(QnaCondition condition);

    Integer updateAddQnaHits(QnaHitsRequest request);

    Integer insertQna(Qna qna);
}
