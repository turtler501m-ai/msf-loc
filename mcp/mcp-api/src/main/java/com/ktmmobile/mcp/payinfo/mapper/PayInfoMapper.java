package com.ktmmobile.mcp.payinfo.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.payinfo.dto.EvidenceDto;
import com.ktmmobile.mcp.payinfo.dto.PayInfoDto;

@Mapper
public interface PayInfoMapper {
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	String selectSeq();
	
	/**
	 * 
	 * @param payInfoDto
	 * @return
	 * @throws Exception
	 */
	EvidenceDto selectMspJuoSubinfo(PayInfoDto payInfoDto);
	
	/**
	 * 
	 * @param evidenceDto
	 * @return
	 * @throws Exception
	 */
	int selectCheckEvidenceCount(EvidenceDto evidenceDto);
	
	/**
	 * 
	 * @param evidenceDto
	 * @return
	 * @throws Exception
	 */
	int updateEvidence(EvidenceDto evidenceDto);
	
	/**
	 * 
	 * @param evidenceDto
	 * @return
	 * @throws Exception
	 */
	int insertEvidence(EvidenceDto evidenceDto);
	
	/**
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map callMspPayinfoImg(Map<String, String> map);
}
