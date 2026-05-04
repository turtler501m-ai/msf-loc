package com.ktmmobile.msf.domains.form.extra.tempsave.application.port.in;

import java.util.List;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.PagedDataResponse;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageListResponse;

public interface TempSavePageReader {

    PagedDataResponse<TempSavePageListResponse> getTempSaveList(TempSavePageCondition condition);
}
