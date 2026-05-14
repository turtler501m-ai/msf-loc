package com.ktmmobile.msf.domains.form.extra.receipt.application.dto;

import java.time.LocalDateTime;

import com.ktmmobile.msf.domains.cache.commoncode.application.dto.SimpleCommonCode;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.form.extra.receipt.domain.entity.ReceiptPage;

public record ReceiptPageResponse(
    long requestKey,
    LocalDateTime cretDt,
    SimpleCommonCode formTypeCd,
    SimpleCommonCode reqBuyTypeCd,
    SimpleCommonCode operTypeCd,
    SimpleCommonCode procCd,
    String cstmrNm,
    String cstmrNativeBirth,
    SimpleCommonCode cstmrTypeCd,
    SimpleCommonCode identityCertTypeCd,
    String agentCd,
    String agentNm,
    String shopCd,
    String shopNm,
    String cretId
) {

    public static ReceiptPageResponse of(
        ReceiptPage entity,
        CommonCodeGroups codeGroups
    ) {
        return new ReceiptPageResponse(
            entity.getRequestKey(),
            entity.getCretDt(),
            codeGroups.getSimple("FORM_TYPE_CD", entity.getFormTypeCd()),
            codeGroups.getSimple("REQ_BUY_TYPE_CD", entity.getReqBuyTypeCd()),
            codeGroups.getSimple("OPER_TYPE_CD", entity.getOperTypeCd()),
            codeGroups.getSimple("CL01", entity.getProcCd()),
            entity.getCstmrNm(),
            entity.getCstmrNativeBirth(),
            codeGroups.getSimple("CSTMR_TYPE_CD", entity.getCstmrTypeCd()),
            codeGroups.getSimple("IDENTITY_CERT_TYPE_CD", entity.getIdentityCertTypeCd()),
            entity.getAgentCd(),
            entity.getAgentNm(),
            entity.getShopCd(),
            entity.getShopNm(),
            entity.getCretId()

        );
    }

}
