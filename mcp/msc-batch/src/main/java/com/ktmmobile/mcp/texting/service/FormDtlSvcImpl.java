package com.ktmmobile.mcp.texting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.texting.dao.FormDtlDao;
import com.ktmmobile.mcp.texting.dto.FormDtlDTO;

@Service
public class FormDtlSvcImpl implements FormDtlSvc {

    @Autowired
    FormDtlDao formDtlDAO;

    @Override
    public FormDtlDTO FormDtlView(FormDtlDTO formDtlDTO){
        return formDtlDAO.FormDtlView(formDtlDTO);
    }

    @Override
    public List<FormDtlDTO> FormDtlLists(FormDtlDTO formDtlDTO) {
        return formDtlDAO.getFormList(formDtlDTO);
    }

}
