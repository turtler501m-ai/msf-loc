package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * Y39 아이핀 CI 조회 응답 VO. ASIS MpSvcContIpinVO 와 동일.
 * - ipinCi: IPIN 연계정보 (CI)
 */
public class MpOsstIpinCiVO extends CommonXmlVO {

    private String ipinCi;

    public String getIpinCi() { return ipinCi; }

    @Override
    protected void parse() throws Exception {
        if (body == null) return;
        ipinCi = XmlParseUtil.getChildValue(body, "ipinCi");
    }
}
