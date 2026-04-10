package com.ktis.msp.rcp.formVrfyMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.formVrfyMgmt.vo.FormVrfyMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("FormVrfyMgmtMapper")
public interface FormVrfyMgmtMapper {

	/**
	 * 서식지검증대상목록조회
	 * @param param
	 * @return
	 */
    public List<FormVrfyMgmtVO> getFormVrfyList(FormVrfyMgmtVO formVrfyMgmtVO);
    
    public List<FormVrfyMgmtVO> getFormVrfyListByExcel(FormVrfyMgmtVO formVrfyMgmtVO);
	
    public List<FormVrfyMgmtVO> getFormVrfyAgntList(FormVrfyMgmtVO formVrfyMgmtVO);
    
    public List<FormVrfyMgmtVO> getFormVrfyAgntListByExcel(FormVrfyMgmtVO formVrfyMgmtVO);
    
    public List<FormVrfyMgmtVO> getFormVrfyRstList(FormVrfyMgmtVO formVrfyMgmtVO);
    
    public FormVrfyMgmtVO getFormVrfyInfo(FormVrfyMgmtVO formVrfyMgmtVO);
    
    public FormVrfyMgmtVO getFormVrfyAgntInfo(FormVrfyMgmtVO formVrfyMgmtVO);
    
    public String getFormVrfyRstAsinYn(FormVrfyMgmtVO formVrfyMgmtVO);
        
    int updFormVrfyInfo(FormVrfyMgmtVO formVrfyMgmtVO);
    
    List<?> getFormVrfyUsrList(Map<String, Object> pReqParamMap);
    
    public List<FormVrfyMgmtVO> getFormVrfyAsinList(FormVrfyMgmtVO formVrfyMgmtVO);
    
    int updFormVrfyAgntInfo(FormVrfyMgmtVO formVrfyMgmtVO);
    
    int regFormVrfyAsinInfo(FormVrfyMgmtVO formVrfyMgmtVO);
    
    int updFormVrfyAsinInfoY(FormVrfyMgmtVO formVrfyMgmtVO);
    
    int updFormVrfyAsinInfoN(FormVrfyMgmtVO formVrfyMgmtVO);
}
