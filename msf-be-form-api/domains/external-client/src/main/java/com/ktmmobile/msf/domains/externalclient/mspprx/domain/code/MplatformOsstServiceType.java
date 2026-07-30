package com.ktmmobile.msf.domains.externalclient.mspprx.domain.code;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum MplatformOsstServiceType {

    USIM_SELF_CHG("UC0", "OsstUsimChgPrcService", "osst:usimChgPrc", "OsstUsimChgInVO"),
    DATA_SHARING_PRE_CHECK("PC0", "OsstSvcPrcService", "osst:osstPrePrc", "OsstPrePrcInVO"),
    DATA_SHARING_FRMPAP_PRE_CHECK("FPC0", "OsstFrmpapSvcPrcSOService", "osst:osstFrmpapPrePrc", "OsstPrePrcInVO"),
    OWNER_CHANGE_PRE_CHECK("FMC0", "OsstFrmpapMcnChgPrcService", "osst:osstFrmpapMcnPrechk", "OsstMcnPrechkInVO"),
    LEGACY_OWNER_CHANGE_PRE_CHECK("MC0", "OsstMcnChgPrcService", "osst:osstMcnPrechk", "OsstMcnPrechkInVO"),
    OWNER_CHANGE_PROCESS("FMP0", "OsstFrmpapMcnChgPrcService", "osst:osstFrmpapMcnPrechk", "OsstMcnPrechkInVO"),
    LEGACY_OWNER_CHANGE_PROCESS("MP0", "OsstMcnMgmtSO", "osst:osstMcnChg", "OsstMcnChgInVO"),
    NEW_CHANGE_MNP_PROCESS("FPC0", "OsstFrmpapSvcPrcSOService", "osst:osstFrmpapNpPrePrc", "OsstPrePrcInVO"),
    NEW_CHANGE_NAC_PROCESS("FPC0", "OsstFrmpapSvcPrcSOService", "osst:osstFrmpapPrePrc", "OsstPrePrcInVO"),
    NEW_CHANGE_HCN_PROCESS("FHC0", "OsstFrmpapIcgMainPrcsService", "osst:icgFrmpapPreChk", "icgPreChkVO"),
    LEGACY_NEW_CHANGE_MNP_PROCESS("PC0", "OsstNpSvcPrcService", "osst:osstNpPrePrc", "OsstPrePrcInVO"),
    LEGACY_NEW_CHANGE_NAC_PROCESS("PC0", "OsstSvcPrcService", "osst:osstPrePrc", "OsstPrePrcInVO"),
    LEGACY_NEW_CHANGE_HCN_PROCESS("HC0", "OsstIcgMainPrcsService", "osst:icgPreChk", "icgPreChkVO"),
    CHOICE_NUMBER_SEARCH("NU1", "OsstSvcNoService", "osst:inqrOsstSvcNoInfo", "InqrOsstSvcNoInfoInVO"),
    CHOICE_NUMBER_RESERVE("NU2", "OsstSvcNoService", "osst:resvOsstTlphNo", "ResvOsstTlphNoInVO"),
    CHOICE_NUMBER_CANCEL("NU2", "OsstSvcNoService", "osst:resvOsstTlphNo", "ResvOsstTlphNoInVO"),
    NUMBER_PORTABLE_REQ("NP1", "OsstNpSvcPrcService", "osst:osstNpBfacAgree", "OsstNpPrePrcInVO"),
    NUMBER_PORTABLE_RESULT("NP3", "OsstNpSvcPrcService", "osst:osstNpBfacAgreeRpyRetv", "OsstNpBfacAgreRpyRetvInVO"),
    FRMPAP_ID_LIST_SEARCH("FS0", "OsstFrmpapService", "osst:osstFrmpapListRetv", "OsstFrmpapListRetvVO"),
    FRMPAP_ID_STATUS_SEARCH("FS1", "OsstFrmpapService", "osst:osstFrmpapRetv", "OsstFrmpapRetvInVO"),
    FRMPAP_ID_STATUS_CHANGE("FS2", "OsstFrmpapService", "osst:osstFrmpapStatChg", "OsstFrmpapStatChgInVO"),
    FRMPAP_IF_SEND("OF", "OsstFrmPapIfSendService", "osst:osstFrmPapIfSend", "OsstFrmPapIfSendInVO"),
    FRMPAP_ONLINE_REG("FS5", "OsstOnlineFrmpapService", "osst:osstOnlineFrmpapReg", "OsstOnlineFrmpapRegInVO"),
    FACE_AUTH_URL_REQUEST("FS8", "OsstCustFathMgmtService", "osst:custFathUrlRqt", "CustFathUrlRqtInVO"),
    FACE_AUTH_SKIP_REQUEST("FT1", "OsstCustFathMgmtService", "osst:inqrOsstSvcNoInfo", "CustFathTxnSkipReqInVO"),
    DATA_SHARING_OPEN("OP0", "OsstSvcPrcService", "osst:osstOpenPrc", "OsstOpenPrcInVO"),
    SELECT_STATUS("ST1", "OsstSvcPrcService", "osst:osstPrcSch", "OsstPrcSchInVO");

    private final String eventCd;
    private final String serviceName;
    private final String serviceInfo;
    private final String serviceVo;

    MplatformOsstServiceType(String eventCd, String serviceName, String serviceInfo, String serviceVo) {
        this.eventCd = eventCd;
        this.serviceName = serviceName;
        this.serviceInfo = serviceInfo;
        this.serviceVo = serviceVo;
    }

    public static MplatformOsstServiceType findByEventCd(String eventCd) {
        return Arrays.stream(values())
            .filter(type -> type.getEventCd().equals(eventCd))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("정의되지 않은 이벤트 코드입니다: " + eventCd));
    }

    public static MplatformOsstServiceType findByEventCd(MplatformOsstServiceType eventCd) {
        return Arrays.stream(values())
            .filter(type -> type.equals(eventCd))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("정의되지 않은 이벤트 코드입니다: " + eventCd));
    }
}
