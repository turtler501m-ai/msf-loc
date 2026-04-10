package com.ktmmobile.mcp.texting.service;

import java.util.List;

import com.ktmmobile.mcp.texting.dto.FormDtlDTO;


public interface FormDtlSvc {

    public FormDtlDTO FormDtlView(FormDtlDTO formDtlDTO);

    List<FormDtlDTO> FormDtlLists(FormDtlDTO formDtlDTO);

}
