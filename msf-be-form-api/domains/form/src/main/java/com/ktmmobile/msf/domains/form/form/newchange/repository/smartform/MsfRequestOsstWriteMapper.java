package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestOsstVo;

@AutoAuditing
@Mapper
public interface MsfRequestOsstWriteMapper {

    //MSF_REQUEST_OSST
    void insertMsfRequestOsst(MsfRequestOsstVo msfRequestOsstVo);
}
