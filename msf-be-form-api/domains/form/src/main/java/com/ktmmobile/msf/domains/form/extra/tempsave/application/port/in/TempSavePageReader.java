package com.ktmmobile.msf.domains.form.extra.tempsave.application.port.in;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.PagedDataResponse;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageResponse;

public interface TempSavePageReader {

    PagedDataResponse<TempSavePageResponse> getList(TempSavePageCondition condition);
}
