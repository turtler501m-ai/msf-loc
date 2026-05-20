package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(OrderDto.class);

    private String searchStart;
    private String searchEnd;
    private String requestKey;
    private String resNo;
    private String sysRdate;
    private String operType;
    private String instCmsn;
    private String orgnId;
    private String prodId;
    private String reqModelColor;
    private String prodNm;
    private String listShowText;
    private String rateNm;
    private String modelPrice;
    private String modelPriceVat;
    private String modelInstallment;
    private String modelDiscount1;
    private String modelDiscount2;
    private String modelDiscount3;
    private String maxDiscount3;
    private String modelMonthly;
    private String enggMnthCnt;
    private String joinPrice;
    private String usimPrice;
    private String sprtTp;
    private String settlAmt;
    private String requestStateCode;
    private String pstate;
    private String tbCd;
    private String dlvryNo;
    private String reqPayType;
    private String reqBuyType;
    private String reqBank;
    private String reqAccountNumber;
    private String reqCardCompany;
    private String reqCardNo;
    private String dlvryPost;
    private String dlvryAddr;
    private String dlvryAddrDtl;
    private String cstmrName;
    private String cstmrMail;
    private String cstmrMobileFn;
    private String cstmrMobileMn;
    private String cstmrMobileRn;
    private String baseAmt;
    private String dcAmt;
    private String addDcAmt;
    private String prdtSctnCd;
    private String imgPath;
    private String proId;
    private String cretId;
    private String sntyColorCd;
    private String prodType;
    private String selfProdNm;
    private String socCode;              // 요금제코드
    private String modelSalePolicyCode;  // 정책코드
    private String cntpntShopId;         // 채널점아이디_판매점코드
    private int apiParam1 = 0;
    private int apiParam2 = 0;
    private String atribValNmOne;
    private String atribValNmTwo;
    private String tmpStep;
    private String selfYn;
    private String onOffType;
    private String phoneCtgLabel;
    private String phoneSaleYn;          // 폰 판매가능 여부
    private String percelUrl;
    private String tempType;
    private int hndsetSalePrice;         // 자급제 가격
    private int usePoint;                // 자급제 포인트
    private int cardDcAmt;               // 자급제 카드할인가
    private String usimKindsCd;          // 유심종류(RCP2035) 06

    // parseInt 포함 커스텀 getter 유지
    public int getModelPriceInt() {
        int result = 0;
        try {
            result = Integer.parseInt(modelPrice);
        } catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }

    public int getModelDiscount2Int() {
        int result = 0;
        try {
            result = Integer.parseInt(modelDiscount2);
        } catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }

    public int getModelMonthlyInt() {
        int result = 0;
        try {
            result = Integer.parseInt(modelMonthly);
        } catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }

    public int getBaseAmtInt() {
        int result = 0;
        try {
            result = Integer.parseInt(baseAmt);
        } catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }

    public int getDcAmtInt() {
        int result = 0;
        try {
            result = Integer.parseInt(dcAmt);
        } catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }

    public int getAddDcAmtInt() {
        int result = 0;
        try {
            result = Integer.parseInt(addDcAmt);
        } catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }

    public int getMaxDiscount3Int() {
        int result = 0;
        try {
            result = Integer.parseInt(maxDiscount3);
        } catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }

    public int getModelPriceVatInt() {
        int result = 0;
        try {
            result = Integer.parseInt(modelPriceVat);
        } catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }

    public String getRequestStateCode() {
        /*
         * 서식지 진행상태가 배송중 일때.. (10,03,04)
         * 그리고 택배사 정보 , 택배 송장 번호가 없을때..
         * 배송 대기로 설정
         */
        /*
        if ( ("10".equals(requestStateCode) || "03".equals(requestStateCode) || "04".equals(requestStateCode))
               && ( StringUtils.isBlank(tbCd) || StringUtils.isBlank(dlvryNo) ) ) {
            return "09";//배송 대기로 설정...
        } else {
            return requestStateCode;
        }
        */
        return requestStateCode;
    }

    public String getTbCd() {
        if (StringUtils.isBlank(dlvryNo) || StringUtils.isBlank(tbCd)) return "";
        return tbCd;
    }

    public String getDlvryNo() {
        if (StringUtils.isBlank(dlvryNo) || StringUtils.isBlank(tbCd)) return "";
        return dlvryNo;
    }

}
