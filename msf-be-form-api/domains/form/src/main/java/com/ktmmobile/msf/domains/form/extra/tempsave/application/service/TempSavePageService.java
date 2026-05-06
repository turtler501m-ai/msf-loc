package com.ktmmobile.msf.domains.form.extra.tempsave.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.PagedDataResponse;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageListResponse;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.fieldmapper.TempSavePageFieldMapper;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.port.in.TempSavePageReader;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.port.in.TempSavePageWriter;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.port.out.TempSavePageRepository;
import com.ktmmobile.msf.domains.form.extra.tempsave.domain.vo.TempSaveVo;

@Service
@RequiredArgsConstructor
@Slf4j
public class TempSavePageService implements TempSavePageReader, TempSavePageWriter {

    private final TempSavePageRepository tempSavePageRepository;
    private final TempSavePageFieldMapper tempSavePageFieldMapper;

    @Override public PagedDataResponse<TempSavePageListResponse> getTempSaveList(TempSavePageCondition condition) {
        log.debug("condition:{}", condition);
        Page<TempSaveVo> page = tempSavePageRepository.selectTempSaveList(condition);
        return PagedDataResponse.of(page, tempSavePageFieldMapper::toTempSavePageListResponse);
    }
}
