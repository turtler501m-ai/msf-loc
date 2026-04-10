package com.ktmmobile.mcp.appform.service;

import com.ktmmobile.mcp.appform.dao.FormDtlDao;
import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.common.commCode.dao.CommCodeDAO;
import com.ktmmobile.mcp.common.commCode.dto.CommCodeInstDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormDtlSvcImpl implements FormDtlSvc {

	@Autowired
	CommCodeDAO commCodeDAO;

	@Autowired
	FormDtlDao formDtlDAO;

	@Override
	public List<CommCodeInstDTO> FormDtlCommonCodeList(String id) {
		// TODO Auto-generated method stub
		List<CommCodeInstDTO> list = commCodeDAO.getSndCodeList(id);
		return list;
	}

	@Override
	public CommCodeInstDTO FormDtlCommonCode(String id) {
		// TODO Auto-generated method stub
		CommCodeInstDTO dto = commCodeDAO.getFstCodeTble(id);
		return dto;
	}

	@Override
	public int FormDtlListCNT(FormDtlDTO formDtlDTO) {
		// TODO Auto-generated method stub
		return formDtlDAO.FormDtlListCNT(formDtlDTO);
	}


	@Override
	public List<FormDtlDTO> FormDtlList(FormDtlDTO formDtlDTO,int skipResult, int maxResult) {
		// TODO Auto-generated method stub
		List<FormDtlDTO> list = formDtlDAO.FormDtlList(formDtlDTO,skipResult,maxResult);
		return list;
	}

	@Override
	public boolean FormDtlInsert(FormDtlDTO formDtlDTO){
		// TODO Auto-generated method stub
		return formDtlDAO.FormDtlInsert(formDtlDTO);
	}

	@Override
	public FormDtlDTO FormDtlView(FormDtlDTO formDtlDTO){
		// TODO Auto-generated method stub
		return formDtlDAO.FormDtlView(formDtlDTO);
	}

	@Override
	public boolean FormDtlUpdate(FormDtlDTO formDtlDTO){
		// TODO Auto-generated method stub
		return formDtlDAO.FormDtlUpdate(formDtlDTO);
	}

	public String FormDtlGetVersionAjax(String cdGroupId1, String cdGroupId2){
		// TODO Auto-generated method stub
		return formDtlDAO.FormDtlGetVersionAjax(cdGroupId1,cdGroupId2);
	}

	@Override
	public int etcNoticeListCnt(FormDtlDTO formDtlDTO) {
		// TODO Auto-generated method stub
		return formDtlDAO.etcNoticeListCnt(formDtlDTO);
	}

	@Override
	public  List<FormDtlDTO> etcNoticeList(FormDtlDTO formDtlDTO,int skipResult, int maxResult) {
		return formDtlDAO.etcNoticeList(formDtlDTO,skipResult,maxResult);
	}

	@Override
	public List<FormDtlDTO> getCdGroupIdList(FormDtlDTO formDtlDTO){
		return formDtlDAO.getCdGroupIdList(formDtlDTO);
	}
	@Override
	public List<FormDtlDTO> getEtcCdGroupIdList(FormDtlDTO formDtlDTO){
		return formDtlDAO.getEtcCdGroupIdList(formDtlDTO);
	}

	@Override
	public List<FormDtlDTO> FormDtlLists(FormDtlDTO formDtlDTO) {
		return formDtlDAO.getFormList(formDtlDTO);
	}

    @Override
    public FormDtlDTO FormDtlViewByDate(FormDtlDTO formDtlDTO) {
		return formDtlDAO.FormDtlViewByDate(formDtlDTO);
    }
}
