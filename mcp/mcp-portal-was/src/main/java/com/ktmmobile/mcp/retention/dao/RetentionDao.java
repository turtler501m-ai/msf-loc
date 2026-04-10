package com.ktmmobile.mcp.retention.dao;

import java.util.List;

import com.ktmmobile.mcp.retention.dto.RetentionDto;

public interface RetentionDao {

    String selectRetentionUserId(RetentionDto retentionDto);

    int insertRetentionUserId(RetentionDto retentionDto);

    int updateRetentionNotice(RetentionDto retentionDto);

    /**
     * <pre>
     * 설명     : 전화번호로 약정 만료 신청 정보 조회
     * @return
     * </pre>
     */
    List<RetentionDto> selectApyNotiTxtList(RetentionDto retentionDto);

}
