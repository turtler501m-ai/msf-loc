package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * X20 부가서비스 목록 조회 응답 VO. ASIS MpAddSvcInfoDto 동일 구조.
 * outDto 하위 outDto 목록을 MpSocVO 리스트로 파싱.
 */
public class MpAddSvcInfoDto extends CommonXmlVO {

    private List<MpSocVO> list;

    @Override
    protected void parse() {
        list = new ArrayList<>();
        if (body == null) return;

        List<Element> items = XmlParseUtil.getChildElementList(body, "outDto");
        for (Element item : items) {
            MpSocVO vo = new MpSocVO();
            vo.setSoc(XmlParseUtil.getChildValue(item, "soc"));
            vo.setSocDescription(XmlParseUtil.getChildValue(item, "socDescription"));
            String rateValue = XmlParseUtil.getChildValue(item, "socRateValue")
                .replace(",", "").replace("WON", "").replace(" ", "");
            vo.setSocRateValue(rateValue);
            vo.setEffectiveDate(XmlParseUtil.getChildValue(item, "effectiveDate"));
            vo.setProdHstSeq(XmlParseUtil.getChildValue(item, "prodHstSeq"));
            vo.setParamSbst(XmlParseUtil.getChildValue(item, "paramSbst"));
            list.add(vo);
        }
    }

    public List<MpSocVO> getList() { return list; }
    public void setList(List<MpSocVO> list) { this.list = list; }
}
