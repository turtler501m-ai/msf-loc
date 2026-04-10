package com.ktis.msp.rcp.courtMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.payinfo.vo.PayInfoDtlVO;
import com.ktis.msp.rcp.courtMgmt.vo.RegstCrCstmrVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("RegstCrCstmrMapper")
public interface RegstCrCstmrMapper {

	int chkCrCstmrRrn(Map<String, Object> param);
	
	void regstCrCstmr(RegstCrCstmrVO regstCrCstmrVo);
	
	void insertLcInfo(RegstCrCstmrVO regstCrCstmrVo);
	
	String getLcMstSeq(RegstCrCstmrVO regstCrCstmrVo);	
	
	List<String> getLcDtlSeq(RegstCrCstmrVO regstCrCstmrVo);	
	
	int getCrSeq(RegstCrCstmrVO regstCrCstmrVo);
	
	void modifyCrCstmr(RegstCrCstmrVO regstCrCstmrVo);
	
    List<?> getCrCstmr(Map<String, Object> param);
    
    public int chkBondNum(RegstCrCstmrVO regstCrCstmrVo);
    
    public int chkBondNumModi(RegstCrCstmrVO regstCrCstmrVo);
    
	void insertCrBond(RegstCrCstmrVO regstCrCstmrVo);
	
	void insertCrBondByLc(RegstCrCstmrVO regstCrCstmrVo);

	void modifyCrBond(RegstCrCstmrVO regstCrCstmrVo);

	void deleteCrBond(RegstCrCstmrVO regstCrCstmrVo);
	
	List<?> getCrBondList(Map<String, Object> param);
	
	void insertCrRciv(RegstCrCstmrVO regstCrCstmrVo);
	
	void insertCrRcivByLc(RegstCrCstmrVO regstCrCstmrVo);

	void modifyCrRciv(RegstCrCstmrVO regstCrCstmrVo);

	void deleteCrRciv(RegstCrCstmrVO regstCrCstmrVo);
	
	List<?> getCrRcivList(Map<String, Object> param);
	
    String getFileNmSeq();
    
    List<String> getEvidenceFileExtList();

    String getEvidenceFileSizeTargetChk(String strExt);

    List<String> getEvidenceFileResizeExtList(String strEtc3);
    
    public int updateRcivFile(RegstCrCstmrVO regstCrCstmrVo);
    
    public String getFileNmDtl(String fileId);
    
	public int deleteFile(String fileId);

	public void pVacOnceVacId(RegstCrCstmrVO regstCrCstmrVo);

	public String chkDclrDate(RegstCrCstmrVO regstCrCstmrVo);

	public void updateDclrDate(RegstCrCstmrVO regstCrCstmrVo);
	
	public void insertCrInout(RegstCrCstmrVO regstCrCstmrVo);
	
	public void modifyCrInout(RegstCrCstmrVO regstCrCstmrVo);
	
	public void deleteCrInout(RegstCrCstmrVO regstCrCstmrVo);
	
	List<?> getCrInoutList(Map<String, Object> param);
	
	List<?> getCrInoutTotal(Map<String, Object> param);
}