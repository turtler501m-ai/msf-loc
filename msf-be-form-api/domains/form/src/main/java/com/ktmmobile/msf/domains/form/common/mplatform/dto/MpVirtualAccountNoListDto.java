package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;
import com.ktmmobile.msf.domains.form.common.util.XmlParse;
import org.jdom.Element;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MpVirtualAccountNoListDto extends CommonXmlVO {
    private final List<MpVirtualAccountNoDto> mpVirtualAccountNoList = new ArrayList<>();

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        List<Element> moscVirtlBnkacnNoList = XmlParse.getChildElementList(this.body, "moscVirtlBnkacnNoListInfoOutDTO");
        for (Element item : moscVirtlBnkacnNoList) {
            MpVirtualAccountNoDto mpVirtualAccountNoDto = new MpVirtualAccountNoDto();
            mpVirtualAccountNoDto.setVirtlBnkacnNo(XmlParse.getChildValue(item, "virtlBnkacnNo"));
            mpVirtualAccountNoDto.setBankCd(XmlParse.getChildValue(item, "bankCd"));
            mpVirtualAccountNoDto.setBankNm(XmlParse.getChildValue(item, "bankNm"));
            mpVirtualAccountNoDto.setEfctStDt(XmlParse.getChildValue(item, "efctStDt"));
            mpVirtualAccountNoDto.setEfctFnsDt(XmlParse.getChildValue(item, "efctFnsDt"));
            mpVirtualAccountNoDto.setRepVirtlBnkacnYn(XmlParse.getChildValue(item, "repVirtlBnkacnYn"));
            mpVirtualAccountNoList.add(mpVirtualAccountNoDto);
        }
    }

    public List<MpVirtualAccountNoDto> getMpVirtualAccountNoList() {
        return mpVirtualAccountNoList;
    }
}
