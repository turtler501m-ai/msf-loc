package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestDvcChgVo {

    private Long requestKey;

    private LocalDateTime regstDttm;

    private LocalDateTime rvisnDttm;

    private String rvisnId;

    private String dvcChgType;

    private String dvcChgRsnCd;

    private String dvcChgRsnDtlCd;

    // MyBatis 매핑 전용 가짜(Dummy) Setter
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }
    public void setCretDt(LocalDateTime cretDt) { this.regstDttm = cretDt; }
    public void setAmdDt(LocalDateTime amdDt) { this.rvisnDttm = amdDt; }
    public void setAmdId(String amdId) { this.rvisnId = amdId; }
    public void setDvcChgTypeCd(String dvcChgTypeCd) { this.dvcChgType = dvcChgTypeCd; }
    public void setDvcChgRsnCd(String dvcChgRsnCd) { this.dvcChgRsnCd = dvcChgRsnCd; }
    public void setDvcChgRsnDtlCd(String dvcChgRsnDtlCd) { this.dvcChgRsnDtlCd = dvcChgRsnDtlCd; }
}
