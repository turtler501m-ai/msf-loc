package com.ktmmobile.msf.system.common.dto.db;

import java.io.Serializable;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestDto.java
 * 날짜     : 2016. 1. 15. 오후 2:28:01
 * 작성자   : papier
 * 설명     : 번호이동정보 테이블(MCP_REQUEST_MOVE)
 * </pre>
 */
public class McpRequestSaleinfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 가입신청_키 */
    private long requestKey;

    /** 지원금유형 (단말할인:KD , 요금할인:PM) */
    private String sprtTp;
    /** 추가지원금(MAX) */
    private long maxDiscount3;
    /** 할인금액 */
    private long dcAmt;
    /** 추가할인금액 */
    private long addDcAmt;
    /** 모델ID */
    private String modelId;
    /** 단말할부개월수 */
    private String modelMonthly;
    /** 단말할부원금 */
    private long modelInstallment;
    /** 단말출고가_부가세 */
    private String modelSalePolicyCode;
    /** 단말출고가_부가세 */
    private long modelPriceVat;
    /** 중고여부 */
    private String recycleYn;
    /** 제조사장려금 */
    private long modelDiscount1;
    /** 공시지원금 */
    private long modelDiscount2;
    /** 약정개월수 */
    private long enggMnthCnt;
    /** 요금제코드 */
    private String socCode;

    /** soc 명 */
    private String socNm;

    /** soc 가격 SOC_PRICE*/
    private String socPrice;


    /** 유심납부유형(B: 정기, I:즉납) */
    private String usimPriceType;
    /** 가입비납부유형(B:정기, I:즉납) */
    private String joinPriceType;
    private long joinPrice;
    /** 유심판매가 */
    private long usimPrice;
    /** 단말출고가 */
    private long modelPrice;
    /** 부가서비스 */
    private String addtionService;
    /** 부가서비스합계 */
    private String addtionServiceSum;
    /** 대리점보조금 */
    private long modelDiscount3;
    /** USIM비 납부방법(0 : 면제, 1:일시납, 2: 분납(3개월) */
    private String usimPayMthdCd;
    /** 가입비 납부방법(0 : 면제, 1:일시납, 2: 분납(3개월) */
    private String joinPayMthdCd;
    /** 실제단말할부원금(VAT포함) */
    private long realMdlInstamt;
    /** 결제수단코드  01 신용카드 , 02 실시간계좌이체 */
    private String  settlWayCd ;
    /** 결제금액 */
    private int  settlAmt =0 ;
    /** 결제 승인번호*/
    private String  settlApvNo ;
    /** 결제 거래번호 */
    private String  settlTraNo ;
    /** 개인 통관 고유 부호-외산 휴대폰 */
    private String  ownPersonalCode ;
    //대표모델 아이디
    private String rprsPrdtId;

    /** 렌탈 기본료 금액 */
    private int  rentalBaseAmt =0 ;
    /** 렌탈 기본료 할인 금액 */
    private int  rentalBaseDcAmt =0 ;
    /** 단말기 배상 금액 */
    private int  rentalModelCpAmt =0 ;



    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public String getSprtTp() {
        return sprtTp;
    }
    public void setSprtTp(String sprtTp) {
        this.sprtTp = sprtTp;
    }
    public long getMaxDiscount3() {
        return maxDiscount3;
    }
    public void setMaxDiscount3(long maxDiscount3) {
        this.maxDiscount3 = maxDiscount3;
    }
    public long getDcAmt() {
        return dcAmt;
    }
    public void setDcAmt(long dcAmt) {
        this.dcAmt = dcAmt;
    }
    public long getAddDcAmt() {
        return addDcAmt;
    }
    public void setAddDcAmt(long addDcAmt) {
        this.addDcAmt = addDcAmt;
    }
    public String getModelId() {
        return modelId;
    }
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    public String getModelMonthly() {
        return modelMonthly;
    }
    public void setModelMonthly(String modelMonthly) {
        this.modelMonthly = modelMonthly;
    }

    public String getModelMonthlyTxt() {
        if (modelMonthly == null || modelMonthly.equals("")) {
            return "" ;
        } else {
            return modelMonthly + "개월";
        }
    }

    public long getModelInstallment() {
        return modelInstallment;
    }
    public void setModelInstallment(long modelInstallment) {
        this.modelInstallment = modelInstallment;
    }
    public String getModelSalePolicyCode() {
        return modelSalePolicyCode;
    }
    public void setModelSalePolicyCode(String modelSalePolicyCode) {
        this.modelSalePolicyCode = modelSalePolicyCode;
    }
    public long getModelPriceVat() {
        return modelPriceVat;
    }
    public void setModelPriceVat(long modelPriceVat) {
        this.modelPriceVat = modelPriceVat;
    }
    public String getRecycleYn() {
        return recycleYn;
    }
    public void setRecycleYn(String recycleYn) {
        this.recycleYn = recycleYn;
    }
    public long getModelDiscount1() {
        return modelDiscount1;
    }
    public void setModelDiscount1(long modelDiscount1) {
        this.modelDiscount1 = modelDiscount1;
    }
    public long getModelDiscount2() {
        return modelDiscount2;
    }
    public void setModelDiscount2(long modelDiscount2) {
        this.modelDiscount2 = modelDiscount2;
    }
    public long getEnggMnthCnt() {
        return enggMnthCnt;
    }

    public String getEnggMnthCntTxt() {
        if ( enggMnthCnt == 0) {
            return "무약정";
        } else {
            return enggMnthCnt +"개월";
        }

    }

    public void setEnggMnthCnt(long enggMnthCnt) {
        this.enggMnthCnt = enggMnthCnt;
    }
    public String getSocCode() {
        return socCode;
    }
    public void setSocCode(String socCode) {
        this.socCode = socCode;
    }
    public String getUsimPriceType() {
        return usimPriceType;
    }
    public void setUsimPriceType(String usimPriceType) {
        this.usimPriceType = usimPriceType;
    }
    public String getJoinPriceType() {
        return joinPriceType;
    }
    public void setJoinPriceType(String joinPriceType) {
        this.joinPriceType = joinPriceType;
    }
    public long getJoinPrice() {
        return joinPrice;
    }
    public void setJoinPrice(long joinPrice) {
        this.joinPrice = joinPrice;
    }
    public long getUsimPrice() {
        return usimPrice;
    }
    public void setUsimPrice(long usimPrice) {
        this.usimPrice = usimPrice;
    }
    public long getModelPrice() {
        return modelPrice;
    }
    public void setModelPrice(long modelPrice) {
        this.modelPrice = modelPrice;
    }
    public String getAddtionService() {
        return addtionService;
    }
    public void setAddtionService(String addtionService) {
        this.addtionService = addtionService;
    }
    public String getAddtionServiceSum() {
        return addtionServiceSum;
    }
    public void setAddtionServiceSum(String addtionServiceSum) {
        this.addtionServiceSum = addtionServiceSum;
    }
    public long getModelDiscount3() {
        return modelDiscount3;
    }
    public void setModelDiscount3(long modelDiscount3) {
        this.modelDiscount3 = modelDiscount3;
    }
    public String getUsimPayMthdCd() {
        return usimPayMthdCd;
    }
    public void setUsimPayMthdCd(String usimPayMthdCd) {
        this.usimPayMthdCd = usimPayMthdCd;
    }
    public String getJoinPayMthdCd() {
        return joinPayMthdCd;
    }
    public void setJoinPayMthdCd(String joinPayMthdCd) {
        this.joinPayMthdCd = joinPayMthdCd;
    }
    public long getRealMdlInstamt() {
        return realMdlInstamt;
    }
    public void setRealMdlInstamt(long realMdlInstamt) {
        this.realMdlInstamt = realMdlInstamt;
    }
    public String getSettlWayCd() {
        return settlWayCd;
    }

    public String getSettlWayNm() {
        if (settlWayCd == null){
            return "";
        } else if ("01".equals(settlWayCd)) {
            return "신용카드";
        } else if ("02".equals(settlWayCd)) {
            return "실시간계좌이체";
        } else {
            return "";
        }

    }

    public void setSettlWayCd(String settlWayCd) {
        this.settlWayCd = settlWayCd;
    }
    public int getSettlAmt() {
        return settlAmt;
    }
    public void setSettlAmt(int settlAmt) {
        this.settlAmt = settlAmt;
    }
    public String getSettlApvNo() {
        return settlApvNo;
    }
    public void setSettlApvNo(String settlApvNo) {
        this.settlApvNo = settlApvNo;
    }
    public String getSettlTraNo() {
        return settlTraNo;
    }
    public void setSettlTraNo(String settlTraNo) {
        this.settlTraNo = settlTraNo;
    }
    public String getOwnPersonalCode() {
        return ownPersonalCode;
    }
    public void setOwnPersonalCode(String ownPersonalCode) {
        this.ownPersonalCode = ownPersonalCode;
    }
    public String getRprsPrdtId() {
        return rprsPrdtId;
    }
    public void setRprsPrdtId(String rprsPrdtId) {
        this.rprsPrdtId = rprsPrdtId;
    }
    public int getRentalBaseAmt() {
        return rentalBaseAmt;
    }
    public void setRentalBaseAmt(int rentalBaseAmt) {
        this.rentalBaseAmt = rentalBaseAmt;
    }
    public int getRentalBaseDcAmt() {
        return rentalBaseDcAmt;
    }
    public void setRentalBaseDcAmt(int rentalBaseDcAmt) {
        this.rentalBaseDcAmt = rentalBaseDcAmt;
    }
    public int getRentalModelCpAmt() {
        return rentalModelCpAmt;
    }
    public void setRentalModelCpAmt(int rentalModelCpAmt) {
        this.rentalModelCpAmt = rentalModelCpAmt;
    }
    public String getSocNm() {
        return socNm;
    }
    public void setSocNm(String socNm) {
        this.socNm = socNm;
    }
    public String getSocPrice() {
        return socPrice;
    }
    public void setSocPrice(String socPrice) {
        this.socPrice = socPrice;
    }




}
