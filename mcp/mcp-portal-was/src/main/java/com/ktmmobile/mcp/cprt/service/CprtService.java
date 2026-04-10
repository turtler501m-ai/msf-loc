package com.ktmmobile.mcp.cprt.service;

import com.ktmmobile.mcp.cprt.dto.AlliCardContDto;
import com.ktmmobile.mcp.cprt.dto.AlliCardContDtoXML;

import java.util.List;

public interface CprtService {

	public List<AlliCardContDto> cprtCardGdncListXml();

    List<AlliCardContDtoXML> getCprtCardGdncDtlXmlList(AlliCardContDto alliCardContDto);

    List<AlliCardContDtoXML> getCprtCardLinkXmlList(AlliCardContDto alliCardContDto);
}
