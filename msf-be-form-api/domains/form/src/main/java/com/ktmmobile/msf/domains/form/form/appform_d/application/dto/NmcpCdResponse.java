package com.ktmmobile.msf.domains.form.form.appform_d.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NmcpCdResponse(
    String cdGroupId,
    String dtlCd,
    String dtlCdNm,
    String useYn

    //List<NmcpCdResponse> nmcpCdResponses;
) {

  /*  public static NmcpCdResponse of(NmcpCd entity) {
        return NmcpCdFieldMapper.INSTANCE.toResponse(entity);
//        return new NmcpCdResponse(entity.getCdGroupId1(), entity.getCdGroupId2(), entity.getSelectCdGroupId1());
    }*/
}
