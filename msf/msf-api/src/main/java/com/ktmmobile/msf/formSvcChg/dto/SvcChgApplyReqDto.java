package com.ktmmobile.msf.formSvcChg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

import java.util.List;

/**
 * 서비스변경 통합 신청 요청 DTO.
 * POST /api/v1/service-change/apply
 * 모든 선택 항목(무선차단, 정보료, 요금제변경, USIM 등)을 하나의 요청으로 처리.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SvcChgApplyReqDto extends SvcChgInfoDto {

    /** 선택한 서비스변경 업무 코드 목록 (WIRELESS_BLOCK, RATE_CHANGE, ...) */
    private List<String> selectedOptions;

    /** 무선데이터차단 희망 상태: 'use'(이용) | 'block'(차단 서비스 이용) */
    private String wirelessBlock;

    /** 정보료 상한금액: '0', '3000', '10000', '20000', '30000' */
    private String infoLimit;

    /** 요금제 변경 SOC 코드 */
    private String ratePlanSoc;

    /** 요금제 변경 스케줄: 'immediate'(즉시 X19) | 'reserve'(예약 X88) */
    private String rateChangeSchedule;

    /** USIM 번호 (UC0 변경 대상 ICCID) */
    private String usimChange;

    /** SIM 보유 유형 (USIM_CHANGE 시): OWN, BUY, ESIM */
    private String usimSimType;

    /** 번호변경 희망 번호 (NUM_CHANGE) */
    private String numChange;

    /** 분실복구/일시정지해제 비밀번호 (LOST_RESTORE) */
    private String pausePassword;

    /** 부가서비스 변경 항목 목록 (ADDITION 선택 시) */
    private List<AdditionApplyItem> additions;

    /** 고객 구분 유형 코드: NA(내국인), FN(외국인), NM(내국인미성년자), JP(법인), PP(개인사업자) */
    private String custType;

    /** 메모 */
    private String memo;

    /** 매니저 코드 */
    private String managerCd;

    /** 대리점 코드 */
    private String agentCd;

    // ── getters/setters ──────────────────────────────────────

    public List<String> getSelectedOptions() { return selectedOptions; }
    public void setSelectedOptions(List<String> selectedOptions) { this.selectedOptions = selectedOptions; }

    public String getWirelessBlock() { return wirelessBlock; }
    public void setWirelessBlock(String wirelessBlock) { this.wirelessBlock = wirelessBlock; }

    public String getInfoLimit() { return infoLimit; }
    public void setInfoLimit(String infoLimit) { this.infoLimit = infoLimit; }

    public String getRatePlanSoc() { return ratePlanSoc; }
    public void setRatePlanSoc(String ratePlanSoc) { this.ratePlanSoc = ratePlanSoc; }

    public String getRateChangeSchedule() { return rateChangeSchedule; }
    public void setRateChangeSchedule(String rateChangeSchedule) { this.rateChangeSchedule = rateChangeSchedule; }

    public String getUsimChange() { return usimChange; }
    public void setUsimChange(String usimChange) { this.usimChange = usimChange; }

    public String getUsimSimType() { return usimSimType; }
    public void setUsimSimType(String usimSimType) { this.usimSimType = usimSimType; }

    public String getNumChange() { return numChange; }
    public void setNumChange(String numChange) { this.numChange = numChange; }

    public String getPausePassword() { return pausePassword; }
    public void setPausePassword(String pausePassword) { this.pausePassword = pausePassword; }

    public List<AdditionApplyItem> getAdditions() { return additions; }
    public void setAdditions(List<AdditionApplyItem> additions) { this.additions = additions; }

    public String getCustType() { return custType; }
    public void setCustType(String custType) { this.custType = custType; }

    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }

    public String getManagerCd() { return managerCd; }
    public void setManagerCd(String managerCd) { this.managerCd = managerCd; }

    public String getAgentCd() { return agentCd; }
    public void setAgentCd(String agentCd) { this.agentCd = agentCd; }

    // ── 내부 클래스: 부가서비스 항목 ──────────────────────────

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AdditionApplyItem {
        /** SOC 코드 */
        private String soc;
        /** 처리 유형: 'reg'(신청/가입) | 'cancel'(해지) */
        private String action;
        /** SOC 설명 (화면 표시 및 DB 저장용) */
        private String socDescription;
        /** 부가 파라미터 (불법TM차단번호, KISA동의 등) */
        private String ftrNewParam;

        public String getSoc() { return soc; }
        public void setSoc(String soc) { this.soc = soc; }

        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }

        public String getSocDescription() { return socDescription; }
        public void setSocDescription(String socDescription) { this.socDescription = socDescription; }

        public String getFtrNewParam() { return ftrNewParam; }
        public void setFtrNewParam(String ftrNewParam) { this.ftrNewParam = ftrNewParam; }
    }
}
