package com.ktmmobile.msf.domains.form.form.common.vo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestAdditionVo {

    private Long requestKey;

    private Long additionKey;

    private String additionName;

    private Integer rantal;

    private String procYn;

    // MyBatis 매핑 전용 가짜(Dummy) Setter
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }
    public void setAdditionKey(Long additionKey) { this.additionKey = additionKey; }
    public void setAdditionNm(String additionNm) { this.additionName = additionNm; }
    public void setRantal(Integer rantal) { this.rantal = rantal; }
    public void setProcYn(String procYn) { this.procYn = procYn; }
}
