package com.ktmmobile.msf.domains.form.form.newchange.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MPhoneNoListXmlVO;
import com.ktmmobile.msf.domains.form.form.newchange.service.MarketInfo;

/**
 * 희망번호 조회 Response
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchNumberResponse {

    private List<MPhoneNoListXmlVO> mPhoneNoList; //
    private List<MarketInfo> marketList;// 상세 정보 리스트

    private int tryCount;

    /*public void setMPhoneNoList(List<MarketInfo> marketList) {
    }*/
}
