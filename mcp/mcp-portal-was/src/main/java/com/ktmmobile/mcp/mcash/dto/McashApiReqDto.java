package com.ktmmobile.mcp.mcash.dto;

public class McashApiReqDto extends McashApiDto {

    /* request field */
    private String reqApiNo;        // 요청 전문 번호
    private String potalId;         // 포탈ID
    private String svcContId;       // 서비스계약ID
    private String wdYn;            // 탈회여부
    private String wdDate;          // 탈회일자
    private String bfPotalId;       // 이전 포탈ID
    private String bfSvcContId;     // 이전 서비스계약ID

    public McashApiReqDto(McashUserDto mcashUserDto) {
        super(mcashUserDto);
    }

    public String getReqApiNo() {
        return reqApiNo;
    }

    public void setReqApiNo(String reqApiNo) {
        this.reqApiNo = reqApiNo;
    }

    public String getPotalId() {
        return potalId;
    }

    public void setPotalId(String potalId) {
        this.potalId = potalId;
    }

    public String getSvcContId() {
        return svcContId;
    }

    public void setSvcContId(String svcContId) {
        this.svcContId = svcContId;
    }

    public String getWdYn() {
        return wdYn;
    }

    public void setWdYn(String wdYn) {
        this.wdYn = wdYn;
    }

    public String getWdDate() {
        return wdDate;
    }

    public void setWdDate(String wdDate) {
        this.wdDate = wdDate;
    }

    public String getBfPotalId() {
        return bfPotalId;
    }

    public void setBfPotalId(String bfPotalId) {
        this.bfPotalId = bfPotalId;
    }

    public String getBfSvcContId() {
        return bfSvcContId;
    }

    public void setBfSvcContId(String bfSvcContId) {
        this.bfSvcContId = bfSvcContId;
    }

    public McashUserDto toMcashUserDto() {
        McashUserDto mcashUserDto = new McashUserDto();

        mcashUserDto.setUserid(this.potalId);
        mcashUserDto.setSvcCntrNo(this.svcContId);
        mcashUserDto.setEvntType(super.getEvntType());
        mcashUserDto.setEvntTypeDtl(super.getEvntTypeDtl());

        return mcashUserDto;
    }
}




