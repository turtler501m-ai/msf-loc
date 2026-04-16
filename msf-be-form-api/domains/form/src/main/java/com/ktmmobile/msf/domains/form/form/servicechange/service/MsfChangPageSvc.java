package com.ktmmobile.msf.domains.form.form.servicechange.service;

import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;

public interface MsfChangPageSvc {

    /** 휴대폰 회선에 따른 단말기할인 정보 조회 */
    MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo);
}

