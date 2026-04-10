package com.ktmmobile.mcp.texting.dao;

import java.util.List;

import com.ktmmobile.mcp.texting.dto.FormDtlDTO;


public interface FormDtlDao {

    public FormDtlDTO FormDtlView(FormDtlDTO formDtlDTO);

    public List<FormDtlDTO> getFormList(FormDtlDTO formDtlDTO) ;

}
