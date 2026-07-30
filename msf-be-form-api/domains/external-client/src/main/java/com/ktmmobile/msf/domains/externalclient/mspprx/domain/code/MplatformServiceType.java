package com.ktmmobile.msf.domains.externalclient.mspprx.domain.code;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum MplatformServiceType {

    X20(
        "X20",  // 이벤트 코드
        "MoscRegSvcService", // serviceName
        "sel:moscRegSvcInfo", // serviceInfo
        "MoscRegSvcInfoInVO", // serviceVo
        "Y", // selfCareInYn : selfCareInYn 태그 필요한 경우
        "Y", // 암호화 여부
        "sel" // prefix xmlns prefix 설정 (sel: 셀프케어, del: 딜리버리, juic: 쥬스, cat: 캐치콜)
    ),
    X38("X38", "MoscRegSvcService", "sel:moscRegSvcCanChg"
        , "MoscRegSvcCanChgInVO", "Y", "N", "sel"),
    Y02("Y02", "MoscFarPriceService", "sel:moscGetFarPricePlanInfo"
        , "MoscGetFarPricePlanInfoInVO", "Y", "Y", "sel"),
    Y07("Y07", "MoscIntmMgmtService", "sel:moscInqrUsimPuk"
        , "MoscInqrUsimPukInVO", "Y", "Y", "sel"),
    Y12("Y12", "MoscIntmInfoRetvService", "sel:moscRetvIntmMdlSpecInfo"
        , "MoscRetvIntmMdlSpecInVO", "N", "Y", "sel"),
    Y13("Y13", "MoscIntmInfoRetvService", "sel:moscRetvIntmOrrgInfo"
        , "MvnoIntmInfoDualRecInVO", "N", "Y", "sel"),
    Y14("Y14", "MoscOmdIntmMgmtService", "sel:moscBfacChkOmdIntm"
        , "MoscBfacChkOmdIntmInVO", "N", "Y", "sel"),
    Y15("Y15", "MoscOmdIntmMgmtService", "sel:moscTrtOmdIntm"
        , "MoscTrtOmdIntmInVO", "N", "Y", "sel"),
    Y25("Y25", "MoscPrdcTrtmService", "sel:moscPrdcTrtm"
        , "MoscPrdcTrtmInVO", "Y", "N", "sel");


    private final String eventCd;
    private final String serviceName;
    private final String serviceInfo;
    private final String serviceVo;
    private final String selfCareInYn; // <selfcareIn></selfcareIn> 영역 prx에서 Y일 때만 생성, 연동 규격서에 포함될 경우 Y 필수
    private final String encryptYn;
    private final String prefix;

    MplatformServiceType(
        String eventCd, String serviceName, String serviceInfo, String serviceVo,
        String selfCareInYn, String encryptYn, String prefix
    ) {
        this.eventCd = eventCd;
        this.serviceName = serviceName;
        this.serviceInfo = serviceInfo;
        this.serviceVo = serviceVo;
        this.selfCareInYn = selfCareInYn;
        this.encryptYn = encryptYn;
        this.prefix = prefix;
    }

    public static MplatformServiceType findByEventCd(String eventCd) {
        return Arrays.stream(values())
            .filter(type -> type.getEventCd().equals(eventCd))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("정의되지 않은 이벤트 코드입니다: " + eventCd));
    }

    public static MplatformServiceType findByEventCd(MplatformServiceType eventCd) {
        return Arrays.stream(values())
            .filter(type -> type.equals(eventCd))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("정의되지 않은 이벤트 코드입니다: " + eventCd));
    }
}
