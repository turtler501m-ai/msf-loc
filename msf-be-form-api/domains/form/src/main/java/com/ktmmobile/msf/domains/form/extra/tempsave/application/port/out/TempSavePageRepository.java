package com.ktmmobile.msf.domains.form.extra.tempsave.application.port.out;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.domain.entity.TempSavePage;

public interface TempSavePageRepository {

    Page<TempSavePage> selectList(TempSavePageCondition condition);
}
