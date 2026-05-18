package com.ktmmobile.msf.domains.form.main.application.dto;

import java.time.LocalDateTime;

import com.ktmmobile.msf.domains.cache.commoncode.application.dto.SimpleCommonCode;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.form.main.domain.entity.Qna;

public record QnaResponse(
    Long id,
    SimpleCommonCode category,
    SimpleCommonCode subCategory,
    String title,
    String writerId,
    String writerName,
    LocalDateTime writeDate,
    String contents,
    String showPublic,
    QnaAnswerResponse answer
) {
    public record QnaAnswerResponse(
        String title,
        String contents,
        SimpleCommonCode status,
        String answerId,
        String answerName,
        String answerOrginizationId,
        String answerOrginizationName,
        LocalDateTime answerDate,
        String sendedSmsYn,
        Integer hitCount
    ) {
        public static QnaAnswerResponse of(Qna qna, CommonCodeGroups codeGroups) {
            return new QnaAnswerResponse(
                qna.getAnsTitle(),
                qna.getAnsContentSbst(),
                codeGroups.getSimple("ANS_STTUS_CD", qna.getAnsSttusCd()),
                qna.getAnsId(),
                qna.getAnsNm(),
                qna.getAnsOrgId(),
                qna.getAnsOrgNm(),
                qna.getAnsDt(),
                qna.getSmsSendYn(),
                qna.getBoardHitCnt()
            );
        }
    }

    public static QnaResponse of(Qna qna, CommonCodeGroups codeGroups) {
        return new QnaResponse(
            qna.getQnaSeq(),
            codeGroups.getSimple("QNA_CTG_CD", qna.getQnaCtgCd()),
            codeGroups.getSimple("QNA_CTG_CD", qna.getQnaSubCtgCd()),
            qna.getQnaTitle(),
            qna.getQnaWriterId(),
            qna.getQnaNm(),
            qna.getWriteDt(),
            qna.getQnaContentSbst(),
            qna.getCretViewYn(),
            QnaAnswerResponse.of(qna, codeGroups)
        );
    }
}
