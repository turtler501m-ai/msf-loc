package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * NU1 희망번호 조회 응답 VO. ASIS MPhoneNoListXmlVO 와 동일 구조.
 * 가능한 전화번호 목록(List<String>) 반환.
 */
public class MpPhoneNoListVO extends CommonXmlVO {

    private List<String> phoneNoList = new ArrayList<>();

    public List<String> getPhoneNoList() {
        return phoneNoList;
    }

    @Override
    protected void parse() throws Exception {
        phoneNoList = new ArrayList<>();
        if (body == null) return;

        // outDto 하위 outDto 리스트 순회
        NodeList nodes = body.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (!(nodes.item(i) instanceof Element)) continue;
            Element item = (Element) nodes.item(i);
            String tlphNo = XmlParseUtil.getChildValue(item, "tlphNo");
            if (tlphNo != null && !tlphNo.trim().isEmpty()) {
                phoneNoList.add(tlphNo.trim());
            }
        }
    }
}
