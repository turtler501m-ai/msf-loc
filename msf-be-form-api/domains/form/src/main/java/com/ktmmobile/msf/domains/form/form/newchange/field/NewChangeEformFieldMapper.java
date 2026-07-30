package com.ktmmobile.msf.domains.form.form.newchange.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MsfRequestEformRecord;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeEformInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;

//@AutoAuditing
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewChangeEformFieldMapper {

    NewChangeEformFieldMapper INSTANCE = Mappers.getMapper(NewChangeEformFieldMapper.class);

    //신청서 SELECT
    @Mapping(target = ".", source = "msfRequestVo")
    @Mapping(target = ".", source = "msfRequestCstmrVo")
    @Mapping(target = ".", source = "msfRequestAgentVo")
    @Mapping(target = ".", source = "msfRequestSaleVo")
    @Mapping(target = ".", source = "msfRequestBillReqVo")
    @Mapping(target = ".", source = "msfRequestMoveVo")
    @Mapping(target = ".", source = "msfRequestDvcChgVo")
    //@Mapping(target = "additionList", source = "msfRequestAdditionVo")
    @Mapping(target = "requestKey", source = "msfRequestVo.requestKey")
    @Mapping(target = "agentCd", source = "customAgentCd")
    @Mapping(target = "agentCd2", source = "customAgentCd")
    @Mapping(target = "reqPhoneSn", source = "customReqPhoneSn")
    @Mapping(target = "modelMonthlyPricdCd", source = "customModelMonthlyPricdCd")
    @Mapping(target = "reqModelName", source = "msfRequestVo.reqModelNm")
    @Mapping(target = "cstmrTypeCd", source = "customCstmrTypeCd")
    @Mapping(target = "addDcAmt", source = "customAddDcAmt")
    @Mapping(target = "etcDcAmt", constant = "0")
    @Mapping(target = "penaltySupportAmt", constant = "0")
    @Mapping(target = "joinFeeSupportAmt", constant = "0")
    @Mapping(target = "etcSupportAmt", constant = "0")
    @Mapping(target = "custPayAmt", source = "customCustPayAmt")
    @Mapping(target = "realMdlInstamt", source = "customRealMdlInstamt")
    @Mapping(target = "modelInstallment", source = "customModelInstallment")
    @Mapping(target = "phoneMonthPayAmt", source = "customPhoneMonthPayAmt")
    @Mapping(target = "phoneTotSubsidyAmt", source = "customPhoneTotSubsidyAmt")
    @Mapping(target = "avgInstFee", source = "customAvgInstFee")
    @Mapping(target = "deviceDiscountAmt", source = "customDeviceDiscountAmt")
    @Mapping(target = "planDiscountAmt", source = "customPlanDiscountAmt")
    @Mapping(target = "planDiscountAmt3", source = "customPlanDiscountAmt3")
    @Mapping(target = "monthFeeVat", source = "msfRequestSaleVo.socBaseChrgAmt")
    @Mapping(target = "socCode", source = "customSocCode")
    @Mapping(target = "socCodeNm", source = "customSocCodeNm")
    @Mapping(target = "enggSocCodeNm", source = "customEnggSocCodeNm")
    @Mapping(target = "dcAmt", source = "customDcAmt")
    @Mapping(target = "monthFeeDiscountAmt", source = "customDcAmt")
    @Mapping(target = "telecomMonthPay", source = "customTelecomMonthPay")
    @Mapping(target = "baseMonthPay", source = "customBaseMonthPay")
    @Mapping(target = "cstmrEmailAdr", source = "msfRequestCstmrVo.cstmrEmailAdr")
    @Mapping(target = "cstmrNm", source = "customCstmrName")
    @Mapping(target = "cstmrNativeRrn", source = "customCstmrNativeRrn")
    @Mapping(target = "gender", source = "customGender")
    @Mapping(target = "cstmrForeignerRrn", source = "customCstmrForeignerRrn")
    @Mapping(target = "cstmrForeignerNation", source = "customCstmrForeignerNation")
    @Mapping(target = "cstmrForeignerPn", source = "customCstmrForeignerPn")
    @Mapping(target = "cstmrForeignerSdate", source = "customCstmrForeignerSdate")
    @Mapping(target = "cstmrForeignerEdate", source = "customCstmrForeignerEdate")
    @Mapping(target = "cstmrReceiveTelNo", source = "customCstmrReceiveTelNo")
    @Mapping(target = "cstmrAddr", source = "customCstmrAddr")
    @Mapping(target = "cstmrMail", source = "msfRequestCstmrVo.cstmrEmailAdr")
    @Mapping(target = "cstmrBillSendCode", source = "customCstmrBillSendCode")
    @Mapping(target = "reqPayType", source = "customReqPayType")
    @Mapping(target = "reqPayTypeCd", source = "customReqPayTypeCd")
    @Mapping(target = "combineId", source = "msfRequestBillReqVo.prntsBillNo")
    @Mapping(target = "othersPaymentNm", source = "customOthersPaymentNm")
    @Mapping(target = "othersPaymentRrn", source = "customOthersPaymentRrn")
    @Mapping(target = "othersPaymentRelation", source = "msfRequestBillReqVo.othersPaymentRelTypeCd")
    @Mapping(target = "autoPayAcctCardNo", source = "customAutoPayAcctCardNo")
    @Mapping(target = "autoPayCardExp", source = "customAutoPayCardExp")
    @Mapping(target = "rantal", source = "customReqAdditionPrice")
    @Mapping(target = "usimPayMthdCd", source = "msfRequestSaleVo.joinPayMthdCd")
    @Mapping(target = "wishNoLinkSvc", source = "customWishNoLinkSvc")
    @Mapping(target = "usimKindsCd", source = "customUsimKindsCd")
    @Mapping(target = "reqUsimName", source = "msfRequestVo.reqUsimNm")
    @Mapping(target = "imei", source = "customImei")
    @Mapping(target = "reqUsimSn", source = "customReqUsimSn")
    @Mapping(target = "usimPriceType", source = "customUsimPriceTypeCd")
    @Mapping(target = "usimPrice", source = "customUsimPrice")
    @Mapping(target = "reqWireTypeCd", source = "msfRequestBillReqVo.reqWireTypeCd")
    @Mapping(target = "moveMobileNo", source = "customMoveMobileNo")
    @Mapping(target = "moveAllotmentStat", source = "msfRequestMoveVo.moveAllotmentSttusCd")
    @Mapping(target = "moveThismonthPayType", source = "customMoveThismonthPayType")
    @Mapping(target = "moveRefundAgreeFlag", source = "msfRequestMoveVo.moveRefundAgreeYn")
    @Mapping(target = "appFormReqDt", source = "customReqInDt")
    @Mapping(target = "gdnFormReqDt", source = "customReqInDt")
    @Mapping(target = "enggReqDt", source = "customReqInDt")
    @Mapping(target = "androidReqDt", source = "customReqInDt")
    @Mapping(target = "iosRegDt", source = "customReqInDt")
    @Mapping(target = "iosReqDt", source = "customReqInDt")
    @Mapping(target = "clausePriOfferYnJini", source = "customJehuPartnerJiniYn")
    @Mapping(target = "clausePriOfferYnStory", source = "customJehuPartnerStoryYn")
    @Mapping(target = "clausePriOfferYnWho", source = "customJehuPartnerWhoYn")
    @Mapping(target = "clausePriOfferYnMl", source = "customJehuPartnerMlYn")
    @Mapping(target = "clausePriOfferYnWc", source = "customJehuPartnerWcYn")
    @Mapping(target = "clausePriOfferYnMi", source = "customJehuPartnerMiYn")
    @Mapping(target = "clausePriOfferYnWv", source = "customJehuPartnerWvYn")
    @Mapping(target = "clausePriOfferYnCu", source = "customJehuPartnerCuYn")
    @Mapping(target = "clausePriOfferYnMz", source = "customJehuPartnerMzYn")
    @Mapping(target = "clausePriOfferYnAlpha", source = "customJehuPartnerAlphaYn")
    @Mapping(target = "clausePriOfferYnLotte", source = "customJehuPartnerLotteYn")
    @Mapping(target = "clausePriOfferYnKbank", source = "customJehuPartnerKbankYn")
    @Mapping(target = "clausePriOfferYnStory2", source = "customJehuPartnerStory2Yn")
    @Mapping(target = "clausePriOfferYnMiWho", source = "customJehuPartnerMiWhoYn")
    @Mapping(target = "clause5GCoverageYn", source = "msfRequestVo.clause5gCoverageYn")
    @Mapping(target = "collectAllAgreeYn", source = "msfRequestVo.othersTrnsAllAgreeYn")
    @Mapping(target = "clauseConfidenceYn2", source = "msfRequestVo.clauseConfidenceYn")
    @Mapping(target = "clausePriOfferYn2", source = "msfRequestVo.clausePriOfferYn")
    @Mapping(target = "minorAgentRrn", source = "customMinorAgentRrn")
    @Mapping(target = "minorAgentGender", source = "customMinorAgentGender")
    @Mapping(target = "minorAgentTelNo", source = "customMinorAgentTelNo")
    @Mapping(target = "minorDelegator", source = "customMinorDelegator")
    @Mapping(target = "minorAgent", source = "customMinorAgent")
    @Mapping(target = "minorAgentRrn2", source = "customMinorAgentRrn2")
    @Mapping(target = "minorAgentGender2", source = "customMinorAgentGender2")
    @Mapping(target = "minorAgentTelNo2", source = "customMinorAgentTelNo2")
    @Mapping(target = "appBlckAgrmYn", source = "msfRequestVo.nwBlckAgrmYn")
    @Mapping(target = "blckAppDivCd", source = "msfRequestVo.appBlckAgrmYn")
    @Mapping(target = "discountProg", source = "customDiscountProg")
    @Mapping(target = "monthFeeDcVatAmt", source = "customMonthFeeDcVatAmt")
    @Mapping(target = "modelEnggMnthCnt", source = "customModelEnggMnthCnt")
    @Mapping(target = "modelEnggMnthCntDesc", source = "customModelEnggMnthCnt")
    @Mapping(target = "enggMnthCnt", source = "customEnggMnthCnt")
    @Mapping(target = "enggMnthCntDesc", source = "customEnggMnthCnt")
    @Mapping(target = "modelSprt2", source = "customModelSprt")
    @Mapping(target = "androidInsrProdCd", source = "customInsrProdCd")
    @Mapping(target = "iosInsrProdCd", source = "customInsrProdCd")
    @Mapping(target = "androidCstmrNativeBirth", source = "customAndroidCstmrNativeBirth")
    @Mapping(target = "iosCstmrNativeBirth", source = "customIosCstmrNativeBirth")
    @Mapping(target = "authInfo", source = "customAuthInfo")
    @Mapping(target = "agentCd3", source = "customAgentCd")
    @Mapping(target = "modelPrice3", source = "msfRequestSaleVo.modelPrice")
    @Mapping(target = "modelSprt3", source = "customModelSprt3")
    @Mapping(target = "custPayAmt3", source = "customCustPayAmt")
    @Mapping(target = "etcDcAmt3", constant = "0")
    @Mapping(target = "modelInstallment3", source = "customModelInstallment")
    @Mapping(target = "modelMonthly3", source = "msfRequestSaleVo.modelMonthly")
    @Mapping(target = "modelInstFeeRate3", constant = "5.9")
    @Mapping(target = "modelInstFee3", source = "customModelInstFee3")
    @Mapping(target = "phoneMonthPayAmt3", source = "customPhoneMonthPayAmt")
    @Mapping(target = "socCode3", source = "customSocCode")
    @Mapping(target = "monthFeeVat3", source = "msfRequestSaleVo.socBaseChrgAmt")
    @Mapping(target = "enggMnthCnt3", source = "customEnggMnthCnt3")
    @Mapping(target = "planDiscountMnthAmt3", source = "msfRequestSaleVo.disPrmtAmt")
    @Mapping(target = "planDiscountMnthRate3", source = "customPlanDiscountMnthRate3")
    @Mapping(target = "telecomMonthPay3", source = "customTelecomMonthPay")
    @Mapping(target = "baseMonthPay3", source = "customBaseMonthPay")
    @Mapping(target = "penaltyCanRate3", source = "customPenaltyCanRate3")
    @Mapping(target = "penaltyChgRate3", source = "customPenaltyChgRate3")
    //@Mapping(target = "penaltyCanMnth3", constant = "0")//todo
    @Mapping(target = "penalty12AmtMnth3", source = "customPenalty12AmtMnth3")
    @Mapping(target = "penalty18AmtMnth3", source = "customPenalty18AmtMnth3")
    @Mapping(target = "penaltyAmt3", constant = "0")//todo
    @Mapping(target = "saleManagerNm3", source = "msfRequestVo.managerNm")
    @Mapping(target = "mobileNo3", source = "msfRequestVo.openNo")
    @Mapping(target = "cstmrName3", source = "customCstmrName")
    @Mapping(target = "enggSignYn", source = "customEnggSignYn")
    @Mapping(target = "saleManagerNm", source = "customSaleManagerNm")
    NewChangeEformInfoResponse toNewChangeEformInfoResponse(MsfRequestEformRecord record);

    //신청서 저장 (INSERT / UPDATE)
    //NewChangeInfoRequest ~> MSF_REQUEST
    MsfRequestVo toMsfRequestVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_CSTMR
    MsfRequestCstmrVo toMsfRequestCstmrVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_AGENT
    MsfRequestAgentVo toMsfRequestAgentVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_SALE
    MsfRequestSaleVo toMsfRequestSaleVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_BILL_REQ
    MsfRequestBillReqVo toMsfRequestBillReqVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_MOVE_TEMP
    MsfRequestMoveVo toMsfRequestMoveVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_DVC_CHG_TEMP
    MsfRequestDvcChgVo toMsfRequestDvcChgVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_ADDITION_TEMP
    //MsfRequestAdditionVo toMsfRequestAdditionVo(NewChangeInfoRequest request);
    //List<MsfRequestAdditionVo> toMsfRequestAdditionVo(List<NewChangeAdditionRequest> additionList);

    //MsfRequestOsstVo toMsfRequestOsstVo(NewChangeInfoRequest request); //MSF_REQUEST
}
