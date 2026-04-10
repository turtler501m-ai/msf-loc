package com.ktmmobile.mcp.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.common.dto.MspCommDatPrvTxnDto;
import com.ktmmobile.mcp.common.dto.MspRateMstDto;
import com.ktmmobile.mcp.common.dto.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.dto.SearchDto;

@Mapper
public interface CommonMapper {

	/**
	 * 요금제 정보 조회
	 * @param rateCd
	 * @return
	 * @throws Exception
	 */
	MspRateMstDto selectMspRateMst(String rateCd);
	
	/**
	 * 
	 * @param rateCd
	 * @return
	 * @throws Exception
	 */
	MspSmsTemplateMstDto selectMspSmsTemplateMst(int rateCd);
	
	/**
	 * 
	 * @param mspCommDatPrvTxnDto
	 * @return
	 * @throws Exception
	 */
	int insertMspCommDatPrvTxn(MspCommDatPrvTxnDto mspCommDatPrvTxnDto);

	List<SearchDto> selectPhoneIndexing();

}
