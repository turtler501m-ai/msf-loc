package com.ktmmobile.msf.domains.form.extra.tempsave.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.domain.entity.TempSavePage;

@Mapper
public interface TempSavePageMapper {

    int count(TempSavePageCondition condition);

    List<TempSavePage> selectList(TempSavePageCondition condition);
}
