package com.ktmmobile.msf.domains.shared.form.common.faceauth.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfFathResultPush;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfFathSelfUrl;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfRequestOsst;

@AutoAuditing
@Mapper
public interface FaceAuthMapper {

    Integer insertMsfRequestOsst(MsfRequestOsst msfRequestOsst);

    MsfFathResultPush selectMsfFathResultPush(String resNo);

    Integer insertMsfFathResultPush(MsfFathResultPush msfFathResltPush);

    Integer insertMsfFathSelfUrl(MsfFathSelfUrl msfFathSelfUrl);
}
