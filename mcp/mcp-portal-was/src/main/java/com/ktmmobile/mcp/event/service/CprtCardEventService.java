package com.ktmmobile.mcp.event.service;

import java.text.ParseException;
import java.util.List;

import com.ktmmobile.mcp.cprt.dto.AliasMapWrapper;
import com.ktmmobile.mcp.cprt.dto.AlliCardContDto;
import com.ktmmobile.mcp.cprt.dto.AlliCardContDtoXML;
import com.ktmmobile.mcp.cprt.dto.AlliCardDtlContDtoXML;

public interface CprtCardEventService {

	/**
	 * 설명 : 카테고리 조회 
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @return
	 */ 
	public AliasMapWrapper getAliasMapWrapper();
	
	/**
	 * 설명 : 제휴카드 안내 목록 조회 
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @return
	 * @throws ParseException
	 */ 
	public List<AlliCardContDto> getProdXmlList() throws ParseException;
	
	/**
	 * 설명 : 카테고리에 해당하는 제휴카드 안내 목록 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 * @throws ParseException
	 */ 
	public List<AlliCardContDto> getProdXmlList(AlliCardContDto alliCardContDto) throws ParseException;
	
	/**
	 * 설명 : 기본 제휴카드정보조회 
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 */ 
	public AlliCardDtlContDtoXML getCprtCardGdncBasXml(AlliCardContDto alliCardContDto);
	
	/**
	 * 설명 : 제휴카드 혜택 상세 xml조회 
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 */ 
	public List<AlliCardContDtoXML> getCprtCardGdncDtlXmlList(AlliCardContDto alliCardContDto);
	
	/**
	 * 설명 : 제휴카드 혜택 상세 xml조회 
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 */ 
	public List<AlliCardContDtoXML> getCprtCardBnfitGdncDtlXmlList(AlliCardContDto alliCardContDto);
	
	/**
	 * 설명 : 제휴카드 링크상세 xml조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 */ 
	public List<AlliCardContDtoXML> getCprtCardLinkXmlList(AlliCardContDto alliCardContDto);
	
	/**
	 * 설명 : 제휴카드 할인금액조회 
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param alliCardContDto
	 * @return
	 */ 
	public List<AlliCardContDtoXML> getCprtCardDcAmtXmlList(AlliCardContDto alliCardContDto);
	
}
