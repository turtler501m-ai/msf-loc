package com.ktmmobile.mcp.alliance.service;

import com.ktmmobile.mcp.alliance.dao.AllianceCardDao;
import com.ktmmobile.mcp.alliance.dto.AlliCardDtlContDtoXML;
import org.springframework.stereotype.Service;


@Service
public class AllianceCardService {
    private final AllianceCardDao allianceCardDao;
    public AllianceCardService(AllianceCardDao allianceCardDao) {
        this.allianceCardDao = allianceCardDao;
    }

    public AlliCardDtlContDtoXML getPlanBannerEnabledAllianceCardXml() {
        return allianceCardDao.getPlanBannerEnabledAllianceCardXml();
    }
}
