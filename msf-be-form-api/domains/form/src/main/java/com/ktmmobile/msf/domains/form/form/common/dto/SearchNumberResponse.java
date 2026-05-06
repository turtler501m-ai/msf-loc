package com.ktmmobile.msf.domains.form.form.common.dto;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MPhoneNoListXmlVO;
import com.ktmmobile.msf.domains.form.form.common.service.MarketInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchNumberResponse {
    private List<MPhoneNoListXmlVO> mPhoneNoList; //
    private List<MarketInfo> marketList;// 상세 정보 리스트

    /*public void setMPhoneNoList(List<MarketInfo> marketList) {
    }*/
}
