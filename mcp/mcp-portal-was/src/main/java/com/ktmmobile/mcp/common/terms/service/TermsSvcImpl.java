package com.ktmmobile.mcp.common.terms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.common.commCode.dao.CommCodeDAO;
import com.ktmmobile.mcp.common.terms.dao.TermsDao;

@Service
public class TermsSvcImpl implements TermsSvc {

	@Autowired
	CommCodeDAO commCodeDAO;

	@Autowired
	TermsDao termsDAO;

	@Override
	public FormDtlDTO getTermsDtl(FormDtlDTO formDtlDTO) {
		return termsDAO.getTermsDtl(formDtlDTO);
	}

}
