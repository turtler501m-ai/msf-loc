package com.ktmmobile.msf.domains.form.extra.tempsave.application.port.out;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.domain.vo.TempSaveVo;


public interface TempSavePageRepository {

    Page<TempSaveVo> selectTempSaveList(TempSavePageCondition condition);
}
