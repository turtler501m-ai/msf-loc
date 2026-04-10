package com.ktmmobile.mcp.event.service;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.appform.service.AppformSvc;
import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.event.dao.PromotionDao;
import com.ktmmobile.mcp.event.dto.PromotionDto;

@Service
public class PromotionSvcImpl implements PromotionSvc {

    @Autowired
    private PromotionDao promotionDao;

    @Autowired
    private AppformSvc appformSvc;

    @Autowired
    private FormDtlSvc formDtlSvc;

    @Override
    public int getDoubleCheckCtn(PromotionDto promotionDto) {
        return promotionDao.getDoubleCheckCtn(promotionDto);
    }

    @Override
    public int promotionReg(PromotionDto promotionDto) {
        return promotionDao.promotionReg(promotionDto);
    }

    @Override
    public String getDocumentContent(String cdGroupId1, String cdGroupId2) {
        FormDtlDTO formDtlDTO = new FormDtlDTO();
        formDtlDTO.setCdGroupId1(cdGroupId1);
        formDtlDTO.setCdGroupId2(cdGroupId2);

        // docVer 가져오기
        FormDtlDTO formDtlRtn = appformSvc.getFormDesc(formDtlDTO);
        String docVer = "";
        if (formDtlRtn != null) {
            docVer = formDtlRtn.getDocVer();
        }

        // 내용 가져오기
        FormDtlDTO oFormDtlDTO = new FormDtlDTO();
        oFormDtlDTO.setCdGroupId1(cdGroupId1);
        oFormDtlDTO.setCdGroupId2(cdGroupId2);
        oFormDtlDTO.setDocVer(docVer);
        FormDtlDTO rtnObj = formDtlSvc.FormDtlView(oFormDtlDTO);

        if (rtnObj != null) {
            return StringEscapeUtils.unescapeHtml(rtnObj.getDocContent());
        }

        return "";
    }
}
