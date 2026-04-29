package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestMoveVo {

    Long requestKey;
    String moveCompanyCd;
    String moveMobileFnNo;
    String moveMobileMnNo;
    String moveMobileRnNo;
    String moveAuthTypeCd;
    String moveAuthNo;
    String moveThismonthPayTypeCd;
    String moveAllotmentSttusCd;
    String moveRefundAgreeYn;
    String reqGuideYn;
    String reqGuideFnNo;
    String reqGuideRnNo;
    String reqGuideMnNo;
    String osstPayDate;
    String osstPayTypeCd;
    Long movePenalty;

}
