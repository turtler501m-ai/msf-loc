package com.ktmmobile.msf.formSvcChg.mapper;

import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseCategoryDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseOptionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 서비스변경 업무 코드 Mapper. SvcChgBaseMapper.xml 참조.
 * MSF_CD_GROUP_BAS, MSF_CD_DTL 테이블 조회.
 */
@Mapper
public interface SvcChgBaseMapper {

    /** 서비스변경 업무 카테고리 목록 (MSF_CD_GROUP_BAS) */
    List<SvcChgBaseCategoryDto> selectSvcChgCategories();

    /** 카테고리별 옵션 목록 (MSF_CD_DTL) */
    List<SvcChgBaseOptionDto> selectSvcChgOptions(@Param("groupCd") String groupCd);
}
