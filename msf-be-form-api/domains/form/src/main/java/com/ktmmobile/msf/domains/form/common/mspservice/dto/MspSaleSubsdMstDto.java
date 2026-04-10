package com.ktmmobile.msf.domains.form.common.mspservice.dto;

import static com.ktmmobile.msf.domains.form.form.common.constant.PhoneConstant.PHONE_FOR_MSP;
import static com.ktmmobile.msf.domains.form.form.common.constant.PhoneConstant.USIM_FOR_MSP;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ktmmobile.msf.domains.form.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.ParseHtmlTagUtil;
import com.ktmmobile.msf.domains.form.form.common.constant.PhoneConstant;


/**
 * @Class Name : MspSaleSubsdMst
 * @Description : MSP DB의 MSP_SALE_SUBSD_MST 테이블과 대응 되는 DTO 이다
 *
 *  정책코드,요금제코드,제품ID,중고여부 ,약정기간,가입유형,조직ID,지원금유형에 따른
 *  아래  금액정보를 가지고 있고  할부원금,부가가치세등을 계산한다.
 *
  ===>  단말금액(VAT포함)
        공시지원금(VAT포함)
        할부원금(VAT포함)
        할부수수료(VAT포함)
        대리점보조금MAX(VAT포함)
        대리점보조금(VAT포함)

     ★  단말할인,요금할인(공시지원금 지원유무 요금할인일경우 공시지원금을 지원하지 않는다.)
     ★  약정할인 해당 약정에 대한 지원비를 요금제에서 차감해준다.


 *
 * @author : ant
 * @Create Date : 2016. 1. 22.
 */
public class MspSaleSubsdMstDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Deprecated
    private static Logger logger = LoggerFactory.getLogger(MspSaleSubsdMstDto.class);

    /** 정잭정보마스터(할부이자율정보를 포함하고있다) */
    private MspSalePlcyMstDto mspSalePlcyMstDto;

    /** 요금제 정보 */
    private MspRateMstDto mspRateMstDto;

    /**판매정책코드 */
    private String salePlcyCd;

    /** 요금제코드 */
    private String rateCd;

    /** 상품id */
    private String prdtId;

    /** 중고여부  */
    private String oldYn = "N";

    /**약정기간 */
    private String agrmTrm = "0";

    /** 약정기간 label */
    private String agrmLabel;

    /** 업무유형(가입유형) NAC:신규가입 , MNP:번호이동,  HCN:기기변경*/
    private String operType;

    /** 조직코드 */
    private String orgnId;

    /**단말금액(VAT)포함  */
    private int hndstAmt;

    /**공시지원금(VAT포함)  */
    private int subsdAmt;

    /** 할부원금 (단말기 가격 - 공시지원금) (VAT포함) */
    private int instAmt;

    /** 할부수수료(VAT포함)*/
    private int instCmsn;

    /** 총 할부수수료(VAT포함)*/
    private int totalInstCmsn;

    /** 대리점보조금[추가지원금]MAX(VAT포함) */
    private int agncySubsdMax;

    /** 대리점보조금[추가지원금](VAT포함) */
    private int agncySubsdAmt;

    /** 등록자 */
    private String regstId;

    /** 등록일*/
    private Date regstDttm;

    /**수정자 */
    private String rvisnId;

    /** 수정일 */
    private Date rvisnDttm;

    /**지원금유형 단말할인:KD ,요금할인:PM) */
    private String sprtTp;

    /**지원금유형 label */
    private String sprtTpLabel;

    /** 기본요금 */
    private int baseAmt;

    /** 할인금액(약정할인선택시 할인금액) */
    private int dcAmt;

    /** 추가할인금액(요금할인선택시 할인금액  단말할인과는 반대된다(단말할인시 공시지원금 대리금 지원금 지원)) */
    private int addDcAmt;

    /** 프로모션 할인 금액(공통코드 에서 요금제 코드로 맵핑 하여 프로모션 할인 요금) */
    private int promotionDcAmt;

    /** 요금제 혜택 텍스트(공통코드 에서 요금제 코드로 맵핑 ) */
    private String rateBenefitTxt;

    /** 할부 이자율 */
    private BigDecimal instRate;

    /** 할부 이자율 기본(정책정보에 할부이자율이 없을경우 MSP 공통코드에서 기본 이자율을 적용한다) */
    private BigDecimal defaultInstRate;

    /** 부가가치세 */
    private int vat;

    /** 월단말요금 */
    private int instMnthAmt;

    /** 월납부 단말요금(할부수수료포함) */
    private int payMnthAmt;

    /** 월납부 통신료금 */
    private int payMnthChargeAmt;

    /** 단말실구매가 */
    private int hndstPayAmt;

    /** 단말기 할부 (24,36) */
    private int modelMonthly;

    /** Y:무약정요금은은 표기 하지 않는다. */
    private String noArgmYn;

    /** 선불 후불여부 */
    private String payClCd;

    /** 요금제명 */
    private String rateNm;

    /** 망내통화 */
    private String nwInCallCnt;

    /** 망외통화 */
    private String nwOutCallCnt;

    /** SMS */
    private String freeSmsCnt;

    /** 데이터 */
    private String freeDataCnt;

    /** 요금제 혜택  */
    private String cmnt;

    /** 무료통화 */
    private String freeCallCnt;

    /** data구분  */
    private String dataType;

    /** 핸드폰 리스트 최저가 조회시 성능 개선을 위한 최소한의 column 을 호출하기위한 처리 서비스   */
    private String forFrontFastYn;

    /** usim 합계가격  baseAmt + dcAmt + addDcAmt */
    private int totalPrice;

    /** usim 합계가격 baseAmt + dcAmt + addDcAmt + vat */
    private int totalVatPrice;

    /** 확장문자열값1 */
    private String expnsnStrVal1;

    /** 확장문자열값2 */
    private String expnsnStrVal2;
    /** 확장문자열값3 */
    private String expnsnStrVal3;

    /** 정책구분코드(01:단말,02:유심) */
    private String plcySctnCd;

    /** 온라인오프라인구분 */
    private String onOffType;

    /** 관리자 정렬순서 PAY_SORT */
    private String paySort;

    /** XML 시퀀스*/
    private int rateAdsvcGdncSeq;
    /** XML - 납부금액 */
    private int xmlPayMnthAmt;
    /** XML - 기본료 */
    private String mmBasAmtVatDesc;
    /** XML - 프로모션 할인된 값 */
    private String promotionAmtVatDesc;
    /** XML - 요금제 설명 */
    private String rateAdsvcBasDesc;
    /** XML - 요금제 명 */
    private String rateAdsvcNm;
    /** XML - 데이터 설명 */
    private String rateAdsvcData;
    /** XML - 음성 설명 */
    private String rateAdsvcCall;
    /** XML - 문자 설명 */
    private String rateAdsvcSms;
    /** XML - 프로모션 데이터 설명 */
    private String rateAdsvcPromData;
    /** XML - 프로모션 음성 설명 */
    private String rateAdsvcPromCall;
    /** XML - 프로모션 문자 설명 */
    private String rateAdsvcPromSms;
    /** XML - 혜택 안내 */
    private String rateAdsvcBnfit;
    /** XML - 제휴 혜택 안내 */
    private String rateAdsvcAllianceBnfit;
    /** XML - 휴대폰 구매시 노출문구*/
    private String phoneBuyPhrase;
    /** 설계페이지 여부 */
    private String designYn;
    /** 최대 데이터제공량(데이터(노출문구))*/
    private String maxDataDelivery;


    public String getForFrontFastYn() {
        return forFrontFastYn;
    }

    public void setForFrontFastYn(String forFrontFastYn) {
        this.forFrontFastYn = forFrontFastYn;
    }



    public String getOnOffType() {
        return onOffType;
    }

    public void setOnOffType(String onOffType) {
        this.onOffType = onOffType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getSprtTpLabel() {

        if ("0".equals(agrmTrm)) {
            sprtTpLabel = "무약정";
        } else if (PhoneConstant.PHONE_DISCOUNT_FOR_MSP.equals(sprtTp)) {
            sprtTpLabel = "단말할인";
        } else if (PhoneConstant.CHARGE_DISCOUNT_FOR_MSP.equals(sprtTp)) {
            sprtTpLabel = "스폰서할인";
        }
          return sprtTpLabel;
      }

    //--------------------- 요금관련 정책 시작-------------------------------------------------------------//

    /**
     * @Description :  단말지원금
         공시지원금 + 대리점 보조금 = 단말지원금
     * @return
     */
     public int getHndstDcAmt() {
         if (PhoneConstant.CHARGE_DISCOUNT_FOR_MSP.equals(sprtTp)) {	//요금할인을 선댁할경우 단말기 지원금 없음
             return 0 ;
         }

         //(공시지원금 + 대리점 보조금)
         return subsdAmt +  agncySubsdAmt;
     }

    /**
    * @Description :  단말기 할부원금을 가져온다.
        단말기 출고가 - (공시지원금 + 대리점 보조금) = 단말기 할부원금
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 26.
    */
    public int getInstAmt() {
        if (PhoneConstant.CHARGE_DISCOUNT_FOR_MSP.equals(sprtTp)) {	//요금할인을 선댁할경우 단말기 출고가를 리턴한다.
            return getHndstAmt();
        }

        //단말기 할부원금  =  단말기 출고가 - (공시지원금 + 대리점 보조금)
        instAmt = hndstAmt - (subsdAmt +  agncySubsdAmt);
        if (instAmt  < 0 ) {
            instAmt = 0;
        }
        return instAmt;
    }

    /**
    * @Description :* 할부수수료금액을 가져온다.
     *  단말기 할부원금  x (할부수수료/100)
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 26.
    *
       SELECT  round(1094500*0.059/12*POWER((1+0.059/12), 24)/(POWER((1+0.059/12), 24)-1),0) - round((1094500/24),0)
       FROM DUAL

       trunc(692600*0.059/12*POWER((1+0.059/12), 24)/(POWER((1+0.059/12), 24)-1),0) - trunc((692600/24),0)

         할부수수료
       1094500 = 할부원금
       24 = 할부개월수
       round(1094500*0.059/12*POWER((1+0.059/12), 24)/(POWER((1+0.059/12), 24)-1),0)=월납입금액
       round((1094500/24),0) = 할부원금/24
    */
    public int getInstCmsn() {
        BigDecimal bgYearRate= new BigDecimal("0.059"); //고정...
        BigDecimal bgInstAmt =  new BigDecimal(getInstAmt()+"");    //단말기 할부원금
        BigDecimal bgModelMonthly =  new BigDecimal(getModelMonthly()+"");  //단말기 할부 기간
//        BigDecimal bgMonth = new BigDecimal("12");
//        BigDecimal bgTemp=  new BigDecimal("1") ;
        BigDecimal bgMonth = BigDecimal.valueOf(12);
        BigDecimal bgTemp=  BigDecimal.ONE;
        BigDecimal bgPow;
        BigDecimal bgTemp2= bgYearRate.divide(bgMonth,38,BigDecimal.ROUND_HALF_UP).add(bgTemp) ; //(1+0.059/12)
        bgPow = bgTemp2.pow(getModelMonthly()).setScale(38,BigDecimal.ROUND_HALF_UP);  ////POWER((1+0.059/12), 24)
        BigDecimal bgPow2 = bgPow.subtract(bgTemp); //( P-1 )

        //round(    1094500 * 0.059 / 12 * P / ( P-1 ),0)
        BigDecimal bgRound1 = bgInstAmt.multiply(bgYearRate).divide(bgMonth,38,BigDecimal.ROUND_HALF_UP).multiply(bgPow)
                .divide(bgPow2,38,BigDecimal.ROUND_HALF_UP).setScale(0,BigDecimal.ROUND_DOWN);
        //round((1094500/24),0)
        BigDecimal bgRound2 = bgInstAmt.divide(bgModelMonthly,0,BigDecimal.ROUND_DOWN);
        instCmsn = bgRound1.subtract(bgRound2).intValue();


        /*BigDecimal instAmt =  new BigDecimal(getInstAmt()+"");	//단말기 할부원금
        BigDecimal instRate = new BigDecimal(getInstRate()+""); //할부 수수료
        BigDecimal percentRate = new BigDecimal("100");
        //할부수수료 = 단말기 할부원금  x (할부수수료/100)
        instCmsn = instAmt.multiply(instRate.divide(percentRate)).intValue();

        if (instCmsn < 0) {
            instCmsn = 0;
        }*/
        return instCmsn;
    }

    /**
    * @Description : 부가가치세를 가져온다.
     * (기본요금 - 기본할인금액 )x 0.1 = 부가가치세
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 26.
    */
    public int getVat() {
        BigDecimal baseAmt    = new BigDecimal(getBaseAmt() + "");	//기본요금
        BigDecimal dcAmt      = new BigDecimal(getDcAmt()+"");		//무약정이어도 할인을 할수있다.
        BigDecimal addDcAmt   = new BigDecimal(getAddDcAmt()+"");	//요금할인
        BigDecimal addRate    = new BigDecimal("0.1");				//부가가치세율

        //
        if (PhoneConstant.CHARGE_DISCOUNT_FOR_MSP.equals(sprtTp) || PhoneConstant.SIMPLE_USIM_DISCOUNT_FOR_MSP.equals(sprtTp) ) {	//요금할인선택시 요금할인 추가적으로 빼준 금액에서 부가세 계산
            //부가가치세 = (기본요금 - 기본할인금액 - 요금할인 ) x 0.1
            vat = baseAmt.subtract(dcAmt).subtract(addDcAmt).multiply(addRate).setScale(0, RoundingMode.DOWN).intValue();
        } else {
            //부가가치세 = (기본요금 - 기본할인금액 ) x 0.1
            vat = baseAmt.subtract(dcAmt).multiply(addRate).setScale(0, RoundingMode.DOWN).intValue();
        }


        if (vat < 0) {
            vat = 0;
        }
        return vat;
    }

    /**
    * @Description :  월납부 단말요금 을 가져온다.
     * 월단말요금 + 할부수수료 = 월납부 단말요금(실제 월 납부 단말요금)<=== 이전 변경 처리
     * (할부원금+총할부수수료)/할부개월수
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 26.
    */
    public int getPayMnthAmt() {
        if (1 != getModelMonthly()) {
            //(할부원금+총할부수수료)/할부개월수
            BigDecimal bgTotal = new BigDecimal(getInstAmt() + getTotalInstCmsn() + ""); //(할부원금+총할부수수료)
            BigDecimal bgModelMonthly = new BigDecimal(getModelMonthly() + "");  //단말기 할부 기간

            payMnthAmt = bgTotal.divide(bgModelMonthly, 0, BigDecimal.ROUND_HALF_UP).intValue();
        } else {
            payMnthAmt = 0;
        }
        return payMnthAmt;
    }


    /** @Description :
    * 월단말요금(할부 수수료를 제외한 금액)
     * 단말기 실구매가  /  약정개월
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 26.
    */
    public int getInstMnthAmt() {
        //월단말금액	= 단말기 실구매가    / 약정개월수
        instMnthAmt = getInstAmt()  / getModelMonthly();
        return instMnthAmt;
    }


    /**
     *@Description :
    * 할부수수료를 포함한 최종 단말기 실제 구매가격
    * 월납부요금 x 할부개월 = 단말기 실구매가
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 26.
    */
    public int getHndstPayAmt() {
        //단말기 실구매가	= 월 납부 단말요금 x 할부기간
        hndstPayAmt = getPayMnthAmt() * getModelMonthly();
        return hndstPayAmt;
    }

    /**
    * @Description :
    * 단말기 할부 기간 0 일경우 1을 리턴한다.
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 26.
    */
    public int getModelMonthly() {
        if (modelMonthly == 0) {
            modelMonthly = 1;
        }
        return modelMonthly;
    }

    /**
    * @Description :
    *  월납부 통신요금  = 기본요금 - 기본할인 - 요금할인 + 부가세
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 27.
    */
    public int getPayMnthChargeAmt() {
        //월납부통신요금	   = 기본요금  - 기본할인   + 부가세
        payMnthChargeAmt = getBaseAmt() - getDcAmt() - getPromotionDcAmt() + getVat();

        if (PhoneConstant.CHARGE_DISCOUNT_FOR_MSP.equals(sprtTp) || PhoneConstant.SIMPLE_USIM_DISCOUNT_FOR_MSP.equals(sprtTp)) {	//요금할인선택시 추가할인금액을 빼준다.
            payMnthChargeAmt = payMnthChargeAmt - getAddDcAmt();
        }
        return payMnthChargeAmt > 0 ? payMnthChargeAmt:0 ;
    }
    //--------------------- 요금관련 정책 끝 -------------------------------------------------------------//


    public MspSalePlcyMstDto getMspSalePlcyMstDto() {
        return mspSalePlcyMstDto;
    }

    public void setMspSalePlcyMstDto(MspSalePlcyMstDto mspSalePlcyMstDto) {
        this.mspSalePlcyMstDto = mspSalePlcyMstDto;
    }

    public String getSalePlcyCd() {
        return salePlcyCd;
    }

    public void setSalePlcyCd(String salePlcyCd) {
        this.salePlcyCd = salePlcyCd;
    }

    public String getRateCd() {
        return rateCd;
    }

    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }

    public String getPrdtId() {
        return prdtId;
    }

    public void setPrdtId(String prdtId) {
        this.prdtId = prdtId;
    }

    public String getOldYn() {
        return oldYn;
    }

    public void setOldYn(String oldYn) {
        this.oldYn = oldYn;
    }

    public String getAgrmTrm() {
        return agrmTrm;
    }

    public void setAgrmTrm(String agrmTrm) {
        this.agrmTrm = agrmTrm;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }

    public int getHndstAmt() {
        return hndstAmt;
    }

    /*
     * 단말금액 VAT제외 리턴
     */
    public int getHndstVatExAmt() {
        BigDecimal bigHndstAmt =  new BigDecimal(hndstAmt+"");
        BigDecimal divideVal =  new BigDecimal("1.1");

        return bigHndstAmt.divide(divideVal,0,RoundingMode.HALF_UP).intValue();

    }

    /*
     * 단말금액 VAT 리턴
     */
    public int getHndstVatAmt() {
        return hndstAmt - getHndstVatExAmt();
    }


    public void setHndstAmt(int hndstAmt) {
        this.hndstAmt = hndstAmt;
    }

    public int getSubsdAmt() {
        return subsdAmt;
    }

    public void setSubsdAmt(int subsdAmt) {
        this.subsdAmt = subsdAmt;
    }

    public void setInstAmt(int instAmt) {
        this.instAmt = instAmt;
    }

    public void setInstCmsn(int instCmsn) {
        this.instCmsn = instCmsn;
    }

    public int getAgncySubsdMax() {
        return agncySubsdMax;
    }

    public void setAgncySubsdMax(int agncySubsdMax) {
        this.agncySubsdMax = agncySubsdMax;
    }

    public int getAgncySubsdAmt() {
        return agncySubsdAmt;
    }

    public void setAgncySubsdAmt(int agncySubsdAmt) {
        this.agncySubsdAmt = agncySubsdAmt;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

    public Date getRegstDttm() {
        return regstDttm;
    }

    public void setRegstDttm(Date regstDttm) {
        this.regstDttm = regstDttm;
    }

    public String getRvisnId() {
        return rvisnId;
    }

    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }

    public Date getRvisnDttm() {
        return rvisnDttm;
    }

    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    public String getSprtTp() {
        if (sprtTp == null) {
            return "";
        }
        return sprtTp;
    }

    public void setSprtTp(String sprtTp) {
        this.sprtTp = sprtTp;
    }

    public int getBaseAmt() {
        return baseAmt;
    }

    public int getBaseVatAmt() {
        int rtnObj = 0 ;
        if (baseAmt > 0) {
            BigDecimal valueAmt = new BigDecimal(baseAmt + "");	//기본요금
            BigDecimal addRate    = new BigDecimal("1.1");				//부가가치세율

            rtnObj = valueAmt.multiply(addRate).setScale(0, RoundingMode.DOWN).intValue();
        }
        return rtnObj;
    }


    public void setBaseAmt(int baseAmt) {
        this.baseAmt = baseAmt;
    }

    public int getDcAmt() {
        return dcAmt;
    }

    public int getDcVatAmt() {
        int rtnObj = 0 ;
        if (dcAmt > 0) {
            BigDecimal valueAmt    = new BigDecimal(dcAmt + "");	//기본요금
            BigDecimal addRate    = new BigDecimal("1.1");				//부가가치세율
            rtnObj = valueAmt.multiply(addRate).setScale(0, RoundingMode.HALF_UP).intValue();
        }
        return rtnObj;
    }

    public void setDcAmt(int dcAmt) {
        this.dcAmt = dcAmt;
    }

    public int getAddDcAmt() {
        return addDcAmt;
    }

    public int getAddDcVatAmt() {
        int rtnObj = 0 ;
        if (addDcAmt > 0) {
            BigDecimal valueAmt    = new BigDecimal(addDcAmt + "");	//기본요금
            BigDecimal addRate    = new BigDecimal("1.1");				//부가가치세율
            rtnObj = valueAmt.multiply(addRate).setScale(0, RoundingMode.HALF_UP).intValue();
        }
        return rtnObj;
    }

    public void setAddDcAmt(int addDcAmt) {
        this.addDcAmt = addDcAmt;
    }


    //요금제 할인 금액
    public int getTotalVatPriceDC() {
        return getDcVatAmt()+getAddDcVatAmt();
    }

    /** 프로모션 할인 금액(공통코드 에서 요금제 코드로 맵핑 하여 프로모션 할인 요금) */
    public int getPromotionDcAmt() {
        return  this.promotionDcAmt;

    }


    public void setPromotionDcAmt() {
        //USIM
        if (PHONE_FOR_MSP.equals(plcySctnCd)) {
            String promotionDcAmt= NmcpServiceUtils.getCodeNm("PhonePromotionDcAmtList",getRateCd());

            if (promotionDcAmt == null || promotionDcAmt.equals("")) {
                this.promotionDcAmt = 0;
            } else {
                this.promotionDcAmt= Integer.parseInt(promotionDcAmt);
            }
        } else  if (USIM_FOR_MSP.equals(plcySctnCd) ) {
            String promotionDcAmt= NmcpServiceUtils.getCodeNm("UsimPromotionDcAmtList",getRateCd());

            if (promotionDcAmt == null || promotionDcAmt.equals("")) {
                this.promotionDcAmt = 0;
            } else {
                this.promotionDcAmt= Integer.parseInt(promotionDcAmt);
            }
        } else {
            this.promotionDcAmt = 0;
        }
    }



    public void setPromotionDcAmt(int PromotionDcAmt) {
        //USIM
        this.promotionDcAmt = PromotionDcAmt;
    }

    public String getRateBenefitTxt() {
        return rateBenefitTxt;


    }


    public void setRateBenefitTxt() {
        if (PHONE_FOR_MSP.equals(plcySctnCd)) {


            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("PhoneRateBenefitList",getRateCd());

            if (nmcpCdDtlDto != null ) {
                this.rateBenefitTxt = ParseHtmlTagUtil.convertHtmlchars(nmcpCdDtlDto.getExpnsnStrVal1());
            } else {
                this.rateBenefitTxt = "";
            }
        } else  if (USIM_FOR_MSP.equals(plcySctnCd) ) {
            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("UsimRateBenefitList",getRateCd());

            if (nmcpCdDtlDto != null ) {
                this.rateBenefitTxt = ParseHtmlTagUtil.convertHtmlchars(nmcpCdDtlDto.getExpnsnStrVal1());
            } else {
                this.rateBenefitTxt = "";
            }
        } else {
            this.rateBenefitTxt = "";
        }
    }

    public BigDecimal getInstRate() {
        if(instRate == null){
            instRate = BigDecimal.ZERO;
        }
        return instRate;
    }

    public void setInstRate(BigDecimal instRate) {
        this.instRate = instRate;
    }

    public BigDecimal getDefaultInstRate() {
        return defaultInstRate;
    }

    public void setDefaultInstRate(BigDecimal defaultInstRate) {
        this.defaultInstRate = defaultInstRate;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public void setHndstPayAmt(int hndstPayAmt) {
        this.hndstPayAmt = hndstPayAmt;
    }

    public void setInstMnthAmt(int instMnthAmt) {
        this.instMnthAmt = instMnthAmt;
    }

    public void setPayMnthAmt(int payMnthAmt) {
        this.payMnthAmt = payMnthAmt;
    }

    public void setModelMonthly(int modelMonthly) {
        this.modelMonthly = modelMonthly;
    }

    public MspRateMstDto getMspRateMstDto() {
        return mspRateMstDto;
    }

    public void setMspRateMstDto(MspRateMstDto mspRateMstDto) {
        this.mspRateMstDto = mspRateMstDto;
    }

    public void setPayMnthChargeAmt(int payMnthChargeAmt) {
        this.payMnthChargeAmt = payMnthChargeAmt;
    }

    public String getAgrmLabel() {
        if ("0".equals(agrmTrm)) {
            agrmLabel = " [무약정]";
        } else {
            agrmLabel = "";
        }
        return agrmLabel;
    }

    public String getAgrmLabel2() {
        if ("0".equals(agrmTrm)) {
            return "/ [무약정]";
        } else {
            return  "";
        }
    }


    public String getNoArgmYn() {
        return noArgmYn;
    }

    public void setNoArgmYn(String noArgmYn) {
        this.noArgmYn = noArgmYn;
    }

    public String getPayClCd() {
        return payClCd;
    }

    public void setPayClCd(String payClCd) {
        this.payClCd = payClCd;
    }

    public String getRateNm() {
        return rateNm;
    }

    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }

    public String getFreeCallCnt() {
        return freeCallCnt;
    }

    public void setFreeCallCnt(String freeCallCnt) {
        this.freeCallCnt = freeCallCnt;
    }

    public String getNwInCallCnt() {
        return nwInCallCnt;
    }

    public void setNwInCallCnt(String nwInCallCnt) {
        this.nwInCallCnt = nwInCallCnt;
    }

    public String getNwOutCallCnt() {
        return nwOutCallCnt;
    }

    public void setNwOutCallCnt(String nwOutCallCnt) {
        this.nwOutCallCnt = nwOutCallCnt;
    }

    public String getFreeSmsCnt() {
        return freeSmsCnt;
    }

    public void setFreeSmsCnt(String freeSmsCnt) {
        this.freeSmsCnt = freeSmsCnt;
    }

    public String getFreeDataCnt() {
        return freeDataCnt;
    }

    public void setFreeDataCnt(String freeDataCnt) {
        this.freeDataCnt = freeDataCnt;
    }

    public String getCmnt() {
        return cmnt;
    }

    public void setCmnt(String cmnt) {
        this.cmnt = cmnt;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    /**
     * @return the totalPrice
     */
    public int getTotalPrice() {
        totalPrice = baseAmt - dcAmt - addDcAmt;
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the totalVatPrice
     */
    public int getTotalVatPrice() {
        totalVatPrice = baseAmt - dcAmt - addDcAmt - getPromotionDcAmt() + (int)((baseAmt - dcAmt - addDcAmt) * 0.1);
        return totalVatPrice > 0 ? totalVatPrice:0 ;
    }

    /**
     * @param totalVatPrice the totalVatPrice to set
     */
    public void setTotalVatPrice(int totalVatPrice) {
        this.totalVatPrice = totalVatPrice;
    }

    public String getPlcySctnCd() {
        return plcySctnCd;
    }

    public void setPlcySctnCd(String plcySctnCd) {
        this.plcySctnCd = plcySctnCd;
        //setPromotionDcAmt();//프로모션 가격 설정
        setRateBenefitTxt();
    }

    /**
     * @Description :
     *  총 할부수수료 :
     *   (할부원금 × 0.059 ÷ 12 × (1 + 0.059 ÷ 12)^할부개월수 ÷((1 + 0.059 ÷ 12)^할부개월수 -1)) x 할부개월수 – 할부원금
     *    빨간 글씨의 계산결과는 소수점 첫째자리 버림
     *    SELECT  trunc(692600*0.059/12*POWER((1+0.059/12), 24)/(POWER((1+0.059/12), 24)-1),0)  *24 - 692600
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 27.
     */
    public int getTotalInstCmsn() {

        if (1 != getModelMonthly()){

            BigDecimal bgYearRate= new BigDecimal("0.059"); //고정...
            BigDecimal bgInstAmt =  new BigDecimal(getInstAmt()+"");    //단말기 할부원금
            BigDecimal bgModelMonthly =  new BigDecimal(getModelMonthly()+"");  //단말기 할부 기간
//            BigDecimal bgMonth = new BigDecimal("12");
//            BigDecimal bgTemp=  new BigDecimal("1") ;
            BigDecimal bgMonth = BigDecimal.valueOf(12);
            BigDecimal bgTemp=  BigDecimal.ONE;
            BigDecimal bgPow;
            BigDecimal bgTemp2= bgYearRate.divide(bgMonth,38,BigDecimal.ROUND_HALF_UP).add(bgTemp) ; //(1+0.059/12)
            bgPow = bgTemp2.pow(getModelMonthly()).setScale(38,BigDecimal.ROUND_HALF_UP);  ////POWER((1+0.059/12), 24)
            BigDecimal bgPow2 = bgPow.subtract(bgTemp); //( P-1 )

            //round(    1094500 * 0.059 / 12 * P / ( P-1 ),0)
            BigDecimal bgRound1 = bgInstAmt.multiply(bgYearRate).divide(bgMonth,38,BigDecimal.ROUND_HALF_UP).multiply(bgPow)
                    .divide(bgPow2,0,BigDecimal.ROUND_DOWN);

            //x 할부개월수 – 할부원금
            totalInstCmsn = bgRound1.multiply(bgModelMonthly).subtract(bgInstAmt).intValue();
            //round((1094500/24),0)
           // BigDecimal bgRound2 = bgInstAmt.divide(bgModelMonthly,0,BigDecimal.ROUND_HALF_UP);
            //totalInstCmsn = bgRound1.subtract(bgRound2).intValue();
        } else {
            totalInstCmsn = 0;
        }
        return totalInstCmsn;
    }

    public int getTotalNoConstCmsn() {

        if (1 != getModelMonthly()){

            BigDecimal bgYearRate= new BigDecimal("0.059"); //고정...
            BigDecimal bgInstAmt =  new BigDecimal(getHndstAmt()+"");    //단말기 할부원금
            BigDecimal bgModelMonthly =  new BigDecimal(getModelMonthly()+"");  //단말기 할부 기간
//            BigDecimal bgMonth = new BigDecimal("12");
//            BigDecimal bgTemp=  new BigDecimal("1") ;
            BigDecimal bgMonth = BigDecimal.valueOf(12);
            BigDecimal bgTemp=  BigDecimal.ONE;
            BigDecimal bgPow;
            BigDecimal bgTemp2= bgYearRate.divide(bgMonth,38,BigDecimal.ROUND_HALF_UP).add(bgTemp) ; //(1+0.059/12)
            bgPow = bgTemp2.pow(getModelMonthly()).setScale(38,BigDecimal.ROUND_HALF_UP);  ////POWER((1+0.059/12), 24)
            BigDecimal bgPow2 = bgPow.subtract(bgTemp); //( P-1 )

            //round(    1094500 * 0.059 / 12 * P / ( P-1 ),0)
            BigDecimal bgRound1 = bgInstAmt.multiply(bgYearRate).divide(bgMonth,38,BigDecimal.ROUND_HALF_UP).multiply(bgPow)
                    .divide(bgPow2,0,BigDecimal.ROUND_DOWN);

            //x 할부개월수 – 할부원금
            totalInstCmsn = bgRound1.multiply(bgModelMonthly).subtract(bgInstAmt).intValue();
            //round((1094500/24),0)
            // BigDecimal bgRound2 = bgInstAmt.divide(bgModelMonthly,0,BigDecimal.ROUND_HALF_UP);
            //totalInstCmsn = bgRound1.subtract(bgRound2).intValue();
        } else {
            totalInstCmsn = 0;
        }
        return totalInstCmsn;
    }
    public void setTotalInstCmsn(int totalInstCmsn) {
        this.totalInstCmsn = totalInstCmsn;
    }



    public String getExpnsnStrVal1() {
        return expnsnStrVal1;
    }

    public void setExpnsnStrVal1(String expnsnStrVal1) {
        this.expnsnStrVal1 = expnsnStrVal1;
    }

    public String getExpnsnStrVal2() {
        return expnsnStrVal2;
    }

    public void setExpnsnStrVal2(String expnsnStrVal2) {
        this.expnsnStrVal2 = expnsnStrVal2;
    }

    public String getExpnsnStrVal3() {
        return expnsnStrVal3;
    }

    public void setExpnsnStrVal3(String expnsnStrVal3) {
        this.expnsnStrVal3 = expnsnStrVal3;
    }


    public String getFreeCallCntByMobile() {
        StringBuffer sbRtn = new StringBuffer();
        if (StringUtils.isEmpty(freeCallCnt) || freeCallCnt.equals("0")) {
            if (StringUtils.isEmpty(nwOutCallCnt) ||  StringUtils.isEmpty(nwInCallCnt) ) {
                return "0분";
            } else {
                if (!StringUtils.isEmpty(nwOutCallCnt) ) {
                    sbRtn.append("망외 ");
                    sbRtn.append(nwOutCallCnt);
                    if (nwOutCallCnt.indexOf("기본제공") == -1){
                        sbRtn.append("분");
                    }
                }
                if (sbRtn.length() > 0) {
                    sbRtn.append(",");
                }
                if (!StringUtils.isEmpty(nwInCallCnt) ) {
                    sbRtn.append("망내 ");
                    sbRtn.append(nwInCallCnt);

                    if (StringUtils.isNumeric(nwInCallCnt) ) {
                        sbRtn.append("분");
                    }
                }
                return sbRtn.toString();
            }

        } else if (StringUtils.isNumeric(freeCallCnt)) {
            sbRtn.append(freeCallCnt);
            sbRtn.append("분");
        } else {
            sbRtn.append(freeCallCnt);
        }

        return sbRtn.toString();
    }

    public String getFreeSmsCntByMobile() {
        StringBuffer sbRtn = new StringBuffer();
        if (StringUtils.isEmpty(freeSmsCnt) || freeSmsCnt.equals("0")) {
            return "0건";
        } else if (StringUtils.isNumeric(freeSmsCnt)) {
            sbRtn.append(freeSmsCnt);
            sbRtn.append("건");
        } else {
            sbRtn.append(freeSmsCnt);
        }
        return sbRtn.toString();
    }

    public String getFreeDataCntByMobile() {
        String convertFreeDataCnt = freeDataCnt;
        BigDecimal castData;
        try {
            if (StringUtils.isEmpty(convertFreeDataCnt)) {
                convertFreeDataCnt = "0";
            }
            castData = new BigDecimal(convertFreeDataCnt);
            if (castData.intValue() < 1) {
                return "0MB";
            }
            if (castData.intValue() >= 1024) {
                return castData.divide(new BigDecimal("1024"),1,BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "GB";
            }
            return castData.intValue() + "MB";

        } catch (NumberFormatException nfe) {
            return freeDataCnt;
        }
    }

    public String getPaySort() {
        return paySort;
    }

    public void setPaySort(String paySort) {
        this.paySort = paySort;
    }


    public int getPaySortInt() {
        try {
            return Integer.parseInt(paySort);
        } catch (NumberFormatException e) {
            return 9999;
        }

    }

    public int getRateAdsvcGdncSeq() {
        return rateAdsvcGdncSeq;
    }

    public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
        this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
    }

    public int getXmlPayMnthAmt() {
        return xmlPayMnthAmt;
    }

    public void setXmlPayMnthAmt(int xmlPayMnthAmt) {
        this.xmlPayMnthAmt = xmlPayMnthAmt;
    }

    public String getMmBasAmtVatDesc() {
        return mmBasAmtVatDesc;
    }

    public void setMmBasAmtVatDesc(String mmBasAmtVatDesc) {
        this.mmBasAmtVatDesc = mmBasAmtVatDesc;
    }

    public String getPromotionAmtVatDesc() {
        return promotionAmtVatDesc;
    }

    public void setPromotionAmtVatDesc(String promotionAmtVatDesc) {
        this.promotionAmtVatDesc = promotionAmtVatDesc;
    }

    public String getRateAdsvcBasDesc() {
        return rateAdsvcBasDesc;
    }

    public void setRateAdsvcBasDesc(String rateAdsvcBasDesc) {
        this.rateAdsvcBasDesc = rateAdsvcBasDesc;
    }

    public String getRateAdsvcNm() {
        return rateAdsvcNm;
    }

    public void setRateAdsvcNm(String rateAdsvcNm) {
        this.rateAdsvcNm = rateAdsvcNm;
    }

    public String getRateAdsvcData() {
        return rateAdsvcData;
    }

    public void setRateAdsvcData(String rateAdsvcData) {
        this.rateAdsvcData = rateAdsvcData;
    }

    public String getRateAdsvcCall() {
        return rateAdsvcCall;
    }

    public void setRateAdsvcCall(String rateAdsvcCall) {
        this.rateAdsvcCall = rateAdsvcCall;
    }

    public String getRateAdsvcSms() {
        return rateAdsvcSms;
    }

    public void setRateAdsvcSms(String rateAdsvcSms) {
        this.rateAdsvcSms = rateAdsvcSms;
    }

    public String getRateAdsvcPromData() {
        return rateAdsvcPromData;
    }

    public void setRateAdsvcPromData(String rateAdsvcPromData) {
        this.rateAdsvcPromData = rateAdsvcPromData;
    }

    public String getRateAdsvcPromCall() {
        return rateAdsvcPromCall;
    }

    public void setRateAdsvcPromCall(String rateAdsvcPromCall) {
        this.rateAdsvcPromCall = rateAdsvcPromCall;
    }

    public String getRateAdsvcPromSms() {
        return rateAdsvcPromSms;
    }

    public void setRateAdsvcPromSms(String rateAdsvcPromSms) {
        this.rateAdsvcPromSms = rateAdsvcPromSms;
    }

    public String getRateAdsvcBnfit() {
        return rateAdsvcBnfit;
    }

    public void setRateAdsvcBnfit(String rateAdsvcBnfit) {
        this.rateAdsvcBnfit = rateAdsvcBnfit;
    }

    public String getRateAdsvcAllianceBnfit() {
        return rateAdsvcAllianceBnfit;
    }

    public void setRateAdsvcAllianceBnfit(String rateAdsvcAllianceBnfit) {
        this.rateAdsvcAllianceBnfit = rateAdsvcAllianceBnfit;
    }

    public String getDesignYn() {
        return designYn;
    }

    public void setDesignYn(String designYn) {
        this.designYn = designYn;
    }

    /*
    *   기본요금(세금포함) - 기본할인(세금포함) - 추가할인(세금포함) 금액
    * */
    public int getTotalPayAmout() {
        //total payment amount
        return this.getBaseVatAmt() - this.getDcVatAmt() - this.getAddDcVatAmt() - this.getPromotionDcAmt();
    }

    public int getPayMnthUnContAmt(String baseAgrm) {
        if ("0".equals(baseAgrm)) {
            BigDecimal bgTotal    = new BigDecimal(getHndstAmt() + getTotalNoConstCmsn() + ""); //(할부원금+총할부수수료)
            BigDecimal bgModelMonthly =  new BigDecimal(getModelMonthly()+"");  //단말기 할부 기간

            payMnthAmt = bgTotal.divide(bgModelMonthly,0,BigDecimal.ROUND_HALF_UP).intValue();
        } else {
            payMnthAmt = getPayMnthAmt();
        }
        return payMnthAmt;
    }

    public String getPhoneBuyPhrase() {
        return phoneBuyPhrase;
    }

    public void setPhoneBuyPhrase(String phoneBuyPhrase) {
        this.phoneBuyPhrase = phoneBuyPhrase;
    }

    public String getMaxDataDelivery() {
        return maxDataDelivery;
    }

    public void setMaxDataDelivery(String maxDataDelivery) {
        this.maxDataDelivery = maxDataDelivery;
    }
}
