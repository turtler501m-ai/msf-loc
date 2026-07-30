package com.ktmmobile.msf.domains.form.form.servicechange.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormCommonRequest;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestMstVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.CombineSelfRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.InsuranceProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NumberChgeProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UnpauseProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UsimChangeUC0Request;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceChangeFieldMapper {

    @Mapping(target = "trnsNm", source = "userName")
    @Mapping(target = "trnsMobileNo", source = "subscriberNo")
    @Mapping(target = "trCstmrNativeRrn", source = "unUserSSn")
    @Mapping(target = "ncn", source = "contractNum")
    @Mapping(target = "trnsCstmrTypeCd", source = "cstmrType")
    MsfRequestNameChgVo toMsfRequestNameChgVo(McpUserCntrMngDto mcpUserCntrMngDto);

    McpCustRequestMstVo toMcpCustRequestMstVo(InsuranceProcessRequest request);

    MplatFormCommonRequest toMplatFormCommonRequest(ChargePlanRequest request);

    @Mapping(target = "requestKey", source = "requestKey")
    @Mapping(target = "svcTgtCd", source = "insurance.svcTgtCd")
    @Mapping(target = "clauseInsuranceYn", source = "insurance.clauseInsuranceYn")
    @Mapping(target = "catCd", source = "insurance.catCd")
    @Mapping(target = "insrProdCd", source = "insurance.insrProdCd")
    @Mapping(target = "ncn", source = "ncn")
    @Mapping(target = "ctn", source = "ctn")
    @Mapping(target = "custNm", source = "cstmrNm")
    @Mapping(target = "mobileNo", source = "ctn")
    // @Mapping(target = "cstmrNativeRrn", source = "insurance")
    @Mapping(target = "cstmrTypeCd", source = "cstmrTypeCd")
    // @Mapping(target = "etcMobile", source = "")
    @Mapping(target = "parentScanId", source = "parentScanId")
    InsuranceProcessRequest toInsuranceProcessRequest(ServiceChangeCompleteReqDto req);

    @Mapping(target = "custNo", source = "custId")
    @Mapping(target = "tlphNo", source = "ctn")
    @Mapping(target = "svcContId", source = "ncn")
    @Mapping(target = "simPurchaseMethod", source = "simInfo.simPurchaseMethod")
    @Mapping(target = "iccId", source = "simInfo.reqUsimSn")
    @Mapping(target = "cntpntCd", source = "cpntId")
    // @Mapping(target = "slsPrsnId", source = "cntpntShopCd")
    @Mapping(target = "simTypeCd", source = "simInfo.simTypeCd")
    @Mapping(target = "parentScanId", source = "parentScanId")
    @Mapping(target = "cstmrTypeCd", source = "cstmrTypeCd")
    UsimChangeUC0Request toUsimChangeUC0Request(ServiceChangeCompleteReqDto req);

    // 일시정지해지
    @Mapping(target = "parentScanId", source = "parentScanId")
    @Mapping(target = "strPwdInsert", source = "unpause.unLockPw")
    @Mapping(target = "strPwdNumInsert", source = "unpause.unLockPw")
    UnpauseProcessRequest toUnpauseProcessRequest(ServiceChangeCompleteReqDto req);

    // 번호변경
    @Mapping(target = "parentScanId", source = "parentScanId")
    @Mapping(target = "resvHkCtn", source = "numberChange.wishNo")
    @Mapping(target = "resvHkSCtn", source = "numberChange.wishNoc")
    @Mapping(target = "resvHkMarketGubun", source = "numberChange.wishMarket")
    NumberChgeProcessRequest toNumberChgeProcessRequest(ServiceChangeCompleteReqDto req);

    CombineSelfRequest toCombineSelfRequest(ServiceChangeCompleteReqDto req);
}
