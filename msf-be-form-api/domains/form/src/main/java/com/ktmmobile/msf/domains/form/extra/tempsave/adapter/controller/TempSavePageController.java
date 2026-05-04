package com.ktmmobile.msf.domains.form.extra.tempsave.adapter.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageListResponse;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.port.in.TempSavePageReader;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.port.in.TempSavePageWriter;

@RestController
@RequiredArgsConstructor
public class TempSavePageController {

    private final TempSavePageReader tempSavePageReader;
    private final TempSavePageWriter tempSavePageWriter;

    @PostMapping("/api/tempsave/list")
    public CommonResponse<List<TempSavePageListResponse>> getTempSaveList(
        @RequestBody TempSavePageCondition condition
    ) {
        return ResponseUtils.ok(tempSavePageReader.getTempSaveList(condition));
    }

}
