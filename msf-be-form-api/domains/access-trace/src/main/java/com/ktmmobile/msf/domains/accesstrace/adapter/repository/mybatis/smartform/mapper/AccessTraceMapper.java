package com.ktmmobile.msf.domains.accesstrace.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;

@AutoAuditing
@Mapper
public interface AccessTraceMapper {

    @AutoAuditing(false)
    int insertAccessTrace(
        @Param("userId") String userId,
        @Param("accessIp") String accessIp,
        @Param("prcsMdlDivCd") String prcsMdlDivCd,
        @Param("prcsSbst") String prcsSbst,
        @Param("trtmRsltSbst") String trtmRsltSbst,
        @Param("accessUrlAdr") String accessUrlAdr,
        @Param("parameter") String parameter,
        @Param("expnsnStrVal1") String expnsnStrVal1,
        @Param("loginDivCd") String loginDivCd,
        @Param("cprtCd") String cprtCd
    );
}
