package com.ktmmobile.msf.domains.cache.terms.domain.entity;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TermsDetailContent {
    String docVer;
    String docContent;
    String useYn;
    LocalDateTime eventStartDt;
    LocalDateTime eventEndDt;
    LocalDateTime eventStartDtSec;
    LocalDateTime eventEndDtSec;
    String startHour;
    String endHour;
    String startHourSec;
    String endHourSec;
    String docContentSec;
    String docType;
    String expnsnStrVal;
}
