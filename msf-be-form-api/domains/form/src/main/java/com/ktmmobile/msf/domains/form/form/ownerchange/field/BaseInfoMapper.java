package com.ktmmobile.msf.domains.form.form.ownerchange.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0InfoRequest;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMP0InfoRequest;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BaseInfoMapper {

    // @Mapping(target = "mvnoOrdNo", source = "mvnoOrdNo")                 // MVNO 오더 번호 (필수)
    @Mapping(target = "slsCmpnCd", source = "shopCd")                 // 판매회사코드 (필수)
    // @Mapping(target = "custNo", source = "custNo")                       // 고객번호
    // @Mapping(target = "svcContId", source = "svcContId")                 // 계약번호 (필수)
    @Mapping(target = "tlphNo", source = "trnsMobileNo")                       // 전화번호 (필수)

    @Mapping(target = "mcnStatRsnCd", source = "mcnStatRsnCd")           // 명변 사유코드 (필수)
    @Mapping(target = "usimSuccYn", source = "usimSuccYn")               // USIM 승계 여부 (eSim계약 명의변경시 승계 Y 고정) (필수)
    @Mapping(target = "iccId", source = "iccId")                         // USIM 일련번호 (eSim계약 명의변경시 NULL로 연동) C

    @Mapping(target = "realUseCustNm", source = "cstmrJuridicalUserNm")         // 실사용고객명 C
    @Mapping(target = "realUseCustBrthDate", source = "cstmrJuridicalBirth") // 실사용자 생년월일 C
    MplatFormFMC0InfoRequest.BaseInfo toFMC0BaseInfo(MsfRequestNameChgVo source);

    @Mapping(target = "osstOrdNo", source = "mcnResNo") //	OSST 오더 번호 (YYYYMMDD + seq 6자리) 필수
    // @Mapping(target = "custNo", source = "custId") //	고객번호 (양도인 고객ID) 필수
    @Mapping(target = "tlphNo", source = "trnsMobileNo") //	전화번호 (양도인 전화번호) 필수
    // @Mapping(target = "svcContId", source = "ncn") //	서비스계약번호 (양도인) 필수
    // @Mapping(target = "rcvCustNo", source = "rcvCustNo") //	양수인고객번호 필수
    // @Mapping(target = "rcvBillAcntNo", source = "") //	양수인청구계정번호 필수
    // @Mapping(target = "mcnStatRsnCd", source = "") //	명변 사유코드 필수
    // @Mapping(target = "iccId", source = "") //	유심 ICCID (eSim은 Null)
    // @Mapping(target = "usimPymnMthdCd", source = "") //	USIM 수납방법 코드 (R:즉납, B:후청구, N:비구매 - eSim계약 비구매 고정)
    // @Mapping(target = "docConfirmYn", source = "") //	증빙서류 확인 여부 필수
    // @Mapping(target = "followupYn", source = "") //	사후점검 이행 여부 필수
    @Mapping(target = "slsCmpnCd", source = "shopCd") //	판매회사코드 필수
    @Mapping(target = "sbscPrtlstRcvEmlAdrsNm", source = "cstmrEmailAdr") //	가입내역서수신이메일주소명
    @Mapping(target = "cntpntCd", source = "cpntId") //	접점코드 필수
    @Mapping(target = "cntplcNo", source = "cstmrReceiveTelNmNo") //	연락처번호 (양도인미납을 위한 연락처) 필수
        // @Mapping(target = "chgRqsshtEmlAdrsNm", source = "") //	청구서이메일주소명
    MplatFormFMP0InfoRequest.BaseInfo toFMP0BaseInfo(MsfRequestNameChgVo source);
}
