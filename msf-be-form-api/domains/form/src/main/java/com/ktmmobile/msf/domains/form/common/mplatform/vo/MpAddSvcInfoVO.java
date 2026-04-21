package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.GROUP_CODE_SOC_FREE_TAX_LIST;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.XmlParse;

public class MpAddSvcInfoVO extends CommonXmlVO{
//	private final static String ITEM = "addSvcInfo";
    private List<ItemVO> list;
    private int freeCnt = 0;
    private int notfreeCnt = 0;

    @Override
    public void parse()  {
//		Element item = XmlParse.getChildElement(this.body, "");
//
//		List<Element> descList = XmlParse.getChildElementList(item, "SOC_DESCRIPTION");
//		List<Element> rateList = XmlParse.getChildElementList(item, "SOC_RATE_VALUE");
//		List<Element> effectList = XmlParse.getChildElementList(item, "EFFECTIVE_DATE");
//
//		int descSize = descList.size();
//		int rateSize = rateList.size();
//		int effectSize = effectList.size();

        List<Element> itemList = XmlParse.getChildElementList(this.body, "outDto");
        list = new ArrayList<ItemVO>();
        for(Element item : itemList){
            ItemVO vo = new ItemVO();

            vo.setSocDescription(XmlParse.getChildValue(item, "socDescription"));
            vo.setSocRateValue(XmlParse.getChildValue(item, "socRateValue").replace(",", "").replace("WON","").replace(" ", ""));
            vo.setEffectiveDate(XmlParse.getChildValue(item, "effectiveDate"));
            vo.setSoc(XmlParse.getChildValue(item, "soc"));

            if(XmlParse.getChildValue(item, "socRateValue").equals("Free")){
                freeCnt++;
            }else{
                int vatVal = Integer.parseInt( XmlParse.getChildValue(item, "socRateValue").replace(",", "").replace("WON","").replace(" ", "") );
                notfreeCnt++;

                //과세 비과세 확인
                NmcpCdDtlDto socFreeTax = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_SOC_FREE_TAX_LIST,vo.getSoc());
                if (socFreeTax !=null) {
                    //비과세
                    vo.setSocRateVatValue(String.valueOf(vatVal));
                } else {
                    //과세
                    vo.setSocRateVatValue(String.valueOf(Math.round( vatVal+(vatVal*0.1))));
                }


            }

            list.add(vo);
        }

//		list = new ArrayList<ItemVO>();
//		for( int inx=0; inx < descSize && inx < rateSize && inx < effectSize ;  inx++){
//			ItemVO vo = new ItemVO();
//			vo.setSocDescription(descList.get(inx).getValue());
//			vo.setSocRateValue(rateList.get(inx).getValue());
//			vo.setEffectiveDate(effectList.get(inx).getValue());
//
//			if( StringUtil.equalsIgnoreCase(vo.getSocRateValue().trim(), "free") ){
//				freeCnt++;
//			}else{
//				notfreeCnt++;
//			}
//
//			if(StringUtil.isNotEmpty(vo.getEffectiveDate()) && vo.getEffectiveDate().length() == 8){
//				String date = vo.getEffectiveDate();
//				String dateArray[] = StringUtil.getDateSplit(date);
//				date = dateArray[0] + "." + dateArray[1] + "." + dateArray[2];
//				vo.setEffectiveDate(date);
//			}
//
//
//			list.add(vo);
//		}
    }

    public List<ItemVO> getList() {
        return list;
    }

    public void setList(List<ItemVO> list) {
        this.list = list;
    }

    public int getFreeCnt() {
        return freeCnt;
    }

    public void setFreeCnt(int freeCnt) {
        this.freeCnt = freeCnt;
    }

    public int getNotfreeCnt() {
        return notfreeCnt;
    }

    public void setNotfreeCnt(int notfreeCnt) {
        this.notfreeCnt = notfreeCnt;
    }

    public class ItemVO{
        private String socDescription;//부가서비스명
        private String socRateValue;//이용요금
        private String effectiveDate;//신청일자
        private String effectiveEndDate;//종료일
        private String socRateVatValue;//VAT포함이용요금
        private String soc;//부가서비스코드
        private String memo;//기타정보
        public String getSocDescription() {
            return socDescription;
        }
        public void setSocDescription(String socDescription) {
            this.socDescription = socDescription;
        }
        public String getSocRateValue() {
            return socRateValue;
        }
        public void setSocRateValue(String socRateValue) {
            this.socRateValue = socRateValue;
        }
        public String getEffectiveDate() {
            return effectiveDate;
        }
        public void setEffectiveDate(String effectiveDate) {
            this.effectiveDate = effectiveDate;
        }
        public String getEffectiveEndDate() {
            return effectiveEndDate;
        }
        public void setEffectiveEndDate(String effectiveEndDate) {
            this.effectiveEndDate = effectiveEndDate;
        }
        public String getSocRateVatValue() {
            return socRateVatValue;
        }
        public void setSocRateVatValue(String socRateVatValue) {
            this.socRateVatValue = socRateVatValue;
        }
        public String getSoc() {
            return soc;
        }
        public void setSoc(String soc) {
            this.soc = soc;
        }
        public String getMemo() {
            return memo;
        }
        public void setMemo(String memo) {
            this.memo = memo;
        }

    }
}