package com.ktmmobile.msf.domains.shared.form.common.script.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptRequest;
import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptVariable;

@Mapper
@AutoAuditing
public interface TranscriptionScriptMapper {

    List<String> getTranscriptionScript(TranscriptionScriptRequest request);

    TranscriptionScriptVariable getNewChangeVariable(Long requestKey);

}
