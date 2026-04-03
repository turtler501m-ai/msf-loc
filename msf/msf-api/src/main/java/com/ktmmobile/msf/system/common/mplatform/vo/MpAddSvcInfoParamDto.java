package com.ktmmobile.msf.system.common.mplatform.vo;

import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_SOC_FREE_TAX_LIST;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.XmlParse;

public class MpAddSvcInfoParamDto extends CommonXmlVO{
	 private List<MpSocVO> list;
	    private int freeCnt = 0;
	    private int notfreeCnt = 0;
	    private int discountCnt = 0;

	    /*총 할인 금액 */
	    private int discountRate = 0;
	    /*총 유료 금액 */
	    private int totalRateVatVal =0;

	    @Override
	    public void parse()  {

	        List<Element> itemList = XmlParse.getChildElementList(this.body, "svcList");
	        list = new ArrayList<MpSocVO>();
	        for(Element item : itemList){
	            MpSocVO vo = new MpSocVO();

	            vo.setSocDescription(XmlParse.getChildValue(item, "socDescription"));
	            vo.setSocRateValue(XmlParse.getChildValue(item, "socRateValue").replace(",", "").replace("WON","").replace(" ", ""));
	            vo.setEffectiveDate(XmlParse.getChildValue(item, "effectiveDate"));
	            vo.setSoc(XmlParse.getChildValue(item, "soc"));
	            vo.setProdHstSeq(XmlParse.getChildValue(item, "prodHstSeq"));
	            vo.setParamSbst(XmlParse.getChildValue(item, "paramSbst"));
	            
	            vo.parseParamSbst();

	            if(XmlParse.getChildValue(item, "socRateValue").equals("Free")){
	                freeCnt++;
	                vo.setSocRateVat(0);
	            }else{
	                int vatVal = Integer.parseInt( XmlParse.getChildValue(item, "socRateValue").replace(",", "").replace("WON","").replace(" ", "") );



	                //과세 비과세 확인
	                NmcpCdDtlDto socFreeTax = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_SOC_FREE_TAX_LIST,vo.getSoc());
	                if (socFreeTax !=null) {
	                    //비과세
	                    BigDecimal baseAmt    = new BigDecimal(vatVal + "");  //기본요금

	                    int baseAmtInt = baseAmt.intValue();
	                    int setSocRateVat = baseAmtInt;
	                    vo.setSocRateVatValue(String.valueOf(setSocRateVat));
	                    vo.setSocRateVat(setSocRateVat);

	                    //0보단 작은며 할인 금액 으로 처리
	                    if (0 > setSocRateVat ) {
	                        discountRate += setSocRateVat;
	                        discountCnt++;
	                    } else {
	                        totalRateVatVal += setSocRateVat ;
	                        notfreeCnt++;
	                    }

	                } else {

	                    BigDecimal addRate    = new BigDecimal("0.1"); //부가가치세율
	                    BigDecimal baseAmt    = new BigDecimal(vatVal + "");  //기본요금

	                    //부가가치세 = 기본요금 x 0.1
	                    /*
	                     * 부가가치세율 0.1
	                     * 10910 + 1091 = 12001
	                     * (기본요금 x 0.1) + 기본요금 원단위 버림
	                     */
	                    int setSocRateVat = baseAmt.multiply(addRate).add(baseAmt).setScale(-1, RoundingMode.DOWN).intValue();

	                    //과세
	                    vo.setSocRateVatValue(String.valueOf(setSocRateVat));
	                    vo.setSocRateVat(setSocRateVat);

	                    //0보단 작은며 할인 금액 으로 처리
	                    if (0 > setSocRateVat ) {
	                        discountRate += setSocRateVat;
	                        discountCnt++;
	                    } else {
	                        totalRateVatVal += setSocRateVat ;
	                        notfreeCnt++;
	                    }


	                    //vo.setSocRateVatValue(String.valueOf((int)Math.floor( vatVal+(vatVal*0.1))));
	                }

	            }

	            list.add(vo);
	        }

	    }

	    public List<MpSocVO> getList() {
	        return list;
	    }

	    public void setList(List<MpSocVO> list) {
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

	    public int getDiscountRate() {
	        return discountRate;
	    }

	    public void setDiscountRate(int discountRate) {
	        this.discountRate = discountRate;
	    }

	    public int getDiscountCnt() {
	        return discountCnt;
	    }

	    public void setDiscountCnt(int discountCnt) {
	        this.discountCnt = discountCnt;
	    }

	    public int getTotalRateVatVal() {
	        return totalRateVatVal;
	    }

	    public void setTotalRateVatVal(int totalRateVatVal) {
	        this.totalRateVatVal = totalRateVatVal;
	    }



}