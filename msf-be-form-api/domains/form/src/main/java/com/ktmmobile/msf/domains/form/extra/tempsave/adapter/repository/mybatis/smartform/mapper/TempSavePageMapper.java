package com.ktmmobile.msf.domains.form.extra.tempsave.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.domain.vo.TempSaveVo;

@Mapper
public interface TempSavePageMapper {

    int countTempSaveList(TempSavePageCondition condition);

    List<TempSaveVo> selectTempSaveList(TempSavePageCondition condition);
}
