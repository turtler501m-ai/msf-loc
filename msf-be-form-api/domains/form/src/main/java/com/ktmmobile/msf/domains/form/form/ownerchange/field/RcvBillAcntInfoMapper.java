package com.ktmmobile.msf.domains.form.form.ownerchange.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0InfoRequest;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RcvBillAcntInfoMapper {

    @Mapping(target = "rqsshtPprfrmCd", source = "cstmrBillSendTypeCd")         // 청구서양식코드 (필수)
    // @Mapping(target = "rqsshtTlphNo", source = "")           // 청구서 발송 전화번호 (C)
    // @Mapping(target = "rqsshtEmlAdrsNm", source = "")        // 청구서이메일주소명 (C)
    // @Mapping(target = "billZipNo", source = "")              // 청구우편번호 (필수)
    // @Mapping(target = "billFndtCntplcSbst", source = "")     // 청구기본연락처내용 (필수)
    // @Mapping(target = "billMntCntplcSbst", source = "")      // 청구상세연락처내용 (필수)
    @Mapping(target = "blpymMthdCd", source = "reqPayTypeCd")            // 납부방법코드 (필수)
    // @Mapping(target = "duedatDateIndCd", source = "")        // 납기일자구분코드 (필수)
    @Mapping(target = "crdtCardExprDate", source = "reqCardYy")       // 신용카드만기일자 (C)
    @Mapping(target = "crdtCardKindCd", source = "reqCardCompanyCd")         // 신용카드종류코드 (C)
    @Mapping(target = "bankCd", source = "reqBankCd")                 // 은행코드 (C)
    // @Mapping(target = "blpymMthdIdntNo", source = "")        // 납부방법식별번호 (C)
    @Mapping(target = "blpymCustNm", source = "reqAccountNm")            // 납부고객명
        // @Mapping(target = "blpymCustIdntNo", source = "")        // 납부고객식별번호
        // @Mapping(target = "blpymMthdIdntNoHideYn", source = "")  // 납부방법식별번호숨김여부
        // @Mapping(target = "bankSkipYn", source = "")             // 은행건너뛰기여부
        // @Mapping(target = "agreIndCd", source = "")              // 동의자료코드 (C)
        // @Mapping(target = "myslAthnTypeCd", source = "")         // 본인인증타입코드 (C)
        // @Mapping(target = "billAtchExclYn", source = "")         // 청구첨부제외여부
        // @Mapping(target = "rqsshtTlphNoHideYn", source = "")     // 청구서전화번호숨김여부
        // @Mapping(target = "rqsshtDsptYn", source = "")           // 청구서발송여부
        // @Mapping(target = "enclBillTrmnYn", source = "")         // 동봉청구해지여부
    MplatFormFMC0InfoRequest.RcvBillAcntInfo toRcvBillAcntInfo(MsfRequestNameChgVo source);
}
