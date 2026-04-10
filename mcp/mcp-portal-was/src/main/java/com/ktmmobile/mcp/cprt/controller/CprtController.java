package com.ktmmobile.mcp.cprt.controller;

import com.ktmmobile.mcp.cprt.dto.AlliCardContDto;
import com.ktmmobile.mcp.cprt.dto.AlliCardContDtoXML;
import com.ktmmobile.mcp.cprt.service.CprtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CprtController {
	
	private static final Logger logger = LoggerFactory.getLogger(CprtController.class);
	
	@Autowired
	private CprtService cprtService;
	
    /**
	 * 전제 제휴 카테고리 xml 정보 조회
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/cprt/cprtCardXmlViewAjax.do", "/m/cprt/cprtCardXmlViewAjax.do"})
	@ResponseBody
	public List<AlliCardContDto> cprtCardXmlViewAjax(HttpServletRequest request, HttpServletResponse response) {
		return cprtService.cprtCardGdncListXml();
	}

    @RequestMapping(value = "/cprt/getCprtCardGdncDtlXmlListAjax.do")
    @ResponseBody
    public List<AlliCardContDtoXML> getCprtCardGdncDtlXmlList(AlliCardContDto AlliCardContDto) {
        return cprtService.getCprtCardGdncDtlXmlList(AlliCardContDto);
    }

    @RequestMapping(value = "/cprt/getCprtCardLinkXmlAjax.do")
    @ResponseBody
    public AlliCardContDtoXML getCprtCardLinkXml(AlliCardContDto AlliCardContDto) {
        List<AlliCardContDtoXML> cprtCardLinkXmlList = cprtService.getCprtCardLinkXmlList(AlliCardContDto);
        if (cprtCardLinkXmlList == null || cprtCardLinkXmlList.isEmpty()) {
            return new AlliCardContDtoXML();
        }
        return cprtCardLinkXmlList.get(0);
    }
}
