package com.ktmmobile.msf.domains.form.form.servicechange.service;

import com.ktmmobile.msf.payinfo.dto.PayInfoDto;

// TOBESKIP: TOBE 미구현 — 결제정보 이미지 생성 기능. 인터페이스는 컴파일 호환을 위해 보존한다.
public interface MsfPayInfoService {

    // TOBESKIP: TOBE 미구현 — 결제정보 이미지 생성
    public void makePayInfoImage(PayInfoDto dto);

    // TOBESKIP: TOBE 미구현 — 결제정보 이미지 재생성
    public String remakePayInfoImage(PayInfoDto dto);

    // TOBESKIP: TOBE 미구현 — payInfo 저장
    public void insetPayInfo(PayInfoDto dto);

    // TOBESKIP: TOBE 미구현 — payInfo 상태 저장 (MsfPaywayServiceImpl.farChgWayChg에서 호출)
    public PayInfoDto savePayInfoHist(PayInfoDto payInfoDto);

}
