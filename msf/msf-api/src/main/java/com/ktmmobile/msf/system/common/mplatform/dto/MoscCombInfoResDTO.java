package com.ktmmobile.msf.system.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.msf.system.common.util.XmlParse;

/**
 * MVNO 결합상태조회 x77
 * @author bsj
 *
 */
public class MoscCombInfoResDTO extends com.ktmmobile.msf.system.common.mplatform.vo.CommonXmlNoSelfServiceException{

    @Deprecated
    private static final Logger logger = LoggerFactory.getLogger(MoscCombInfoResDTO.class);

    private List<MoscMvnoComInfo> moscSrchCombInfoList;
    private MoscMvnoComInfo moscMvnoComInfo;

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {

        Element element = XmlParse.getChildElement(this.body, "moscMvnoComInfo");
        List<Element> elementList = XmlParse.getChildElementList(this.body, "moscSrchCombInfoList");

        if(element !=null) {

            moscMvnoComInfo = new MoscMvnoComInfo();
            moscMvnoComInfo.setCombSvcNo(XmlParse.getChildValue(element, "combSvcNo"));
            moscMvnoComInfo.setCombYn(XmlParse.getChildValue(element, "combYn"));
            moscMvnoComInfo.setSvcDivCd(XmlParse.getChildValue(element, "svcDivCd"));
            moscMvnoComInfo.setSvcNo(XmlParse.getChildValue(element, "svcNo"));
            moscMvnoComInfo.setIndvInfoAgreeMsgSbst(XmlParse.getChildValue(element, "indvInfoAgreeMsgSbst"));
        }

        if(elementList !=null && !elementList.isEmpty()) {
            moscSrchCombInfoList = new ArrayList<MoscMvnoComInfo>();
            for(Element item : elementList){
                MoscMvnoComInfo vo = new MoscMvnoComInfo();
                vo.setCombSvcNo(XmlParse.getChildValue(item, "combSvcNo"));
                vo.setSvcNo(XmlParse.getChildValue(item, "svcNo"));
                vo.setCombYn(XmlParse.getChildValue(item, "combYn"));
                vo.setSvcDivCd(XmlParse.getChildValue(item, "svcDivCd"));
                vo.setSvcContOpnDt(XmlParse.getChildValue(item, "svcContOpnDt"));
                vo.setCorrNm(XmlParse.getChildValue(item, "corrNm"));
                moscSrchCombInfoList.add(vo);
            }
        }
    }


    public List<MoscMvnoComInfo> getMoscSrchCombInfoList() {
        return moscSrchCombInfoList;
    }

    public void setMoscSrchCombInfoList(List<MoscMvnoComInfo> moscSrchCombInfoList) {
        this.moscSrchCombInfoList = moscSrchCombInfoList;
    }

    public MoscMvnoComInfo getMoscMvnoComInfo() {
        return moscMvnoComInfo;
    }

    public void setMoscMvnoComInfo(MoscMvnoComInfo moscMvnoComInfo) {
        this.moscMvnoComInfo = moscMvnoComInfo;
    }


}
