package com.ktmmobile.mcp.payinfo.dao;

import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.payinfo.dto.EvidenceDto;
import com.ktmmobile.mcp.payinfo.dto.PayInfoDto;
import com.ktmmobile.mcp.payinfo.dto.PayInfoFormDto;

public interface PayInfoDao {
	
	/**
	 * 
	 * @return
	 */
    public String getSeq();

    /**
     * 
     * @return
     */
    public List<PayInfoFormDto> getPositionList();

    /**
     * 
     * @param dto
     * @return
     */
    public int insetPayInfo(PayInfoDto dto);

    /**
     * <pre>
     * 설명     :  CMN_KFTC_EVIDENCE@DL_MSP TABLE 에..
     *            CONTRACT_NUM 으로 존재하면. UPDATE 처리 없으면.. INSERT
     * @param PayInfoDto :
     * @return
     * </pre>
     */
    public int insertOrUpdatePayInfo(EvidenceDto dto);

    /**
     * 
     * @param dto
     * @return
     */
    public int updatePayInto(PayInfoDto dto);

    /**
     * 
     * @param dto
     * @return
     */
    public EvidenceDto selectMspJuoSubinfo(PayInfoDto dto);

    /**
     * 
     * @param dto
     * @return
     */
    public int insetEvidence(EvidenceDto dto);

    /**
     * 
     * @param contractNum
     * @param reqBank
     * @param reqAccountNumber
     * @param ext
     * @param result
     * @return
     */
    public Map callMspPayinfoImg(String contractNum, String reqBank , String reqAccountNumber , String ext , String result);
    
    /**
     * 
     * @param dto
     * @return
     */
    public PayInfoDto selectPayInfo(PayInfoDto dto); 
}
