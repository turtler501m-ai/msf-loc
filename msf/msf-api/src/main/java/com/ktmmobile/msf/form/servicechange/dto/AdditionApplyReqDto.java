package com.ktmmobile.msf.form.servicechange.dto;

/**
 * 부가서비스 신청/해지 요청 DTO
 *
 * POST /api/v1/addition/cancel (해지)
 * POST /api/v1/addition/reg   (신청)
 *
 * AdditionReqDto의 공통 필드(ncn/ctn/custId)에 더해
 * 신청/해지에 필요한 SOC, 부가정보, 이력번호, 변경 플래그를 추가.
 *
 * ASIS에서는 MyPageSearchDto(세션) + @RequestParam(soc, ftrNewParam 등)으로
 * 분산 수신하던 것을 단일 요청 바디로 통합.
 */
public class AdditionApplyReqDto {

    /** 서비스 계약번호 9자리 ([-] 제외) */
    private String ncn;

    /** 전화번호 11자리 */
    private String ctn;

    /** 고객번호 */
    private String custId;

    /**
     * SOC 코드 (부가서비스 상품 코드)
     * 해지 시: 해지할 SOC
     * 신청 시: 신청할 SOC
     */
    private String soc;

    /**
     * 부가정보 (선택)
     * 부가서비스 신청 시 추가 옵션 파라미터가 필요한 경우 사용.
     * 예: 로밍 하루종일ON 날짜 선택 등
     * M플랫폼 Y25의 ftrNewParam 파라미터에 전달.
     */
    private String ftrNewParam;

    /**
     * 상품이력번호 (선택, 해지 전용)
     * 동일 SOC를 복수 가입한 경우 특정 이력 건만 해지할 때 사용.
     * 예: 로밍 부가서비스를 날짜별로 여러 건 가입한 경우.
     * 있으면 X38 moscRegSvcCanChgSeq, 없으면 X38 moscRegSvcCanChg 호출.
     */
    private String prodHstSeq;

    /**
     * 선해지 후 신청 플래그 (신청 전용)
     * "Y": 동일 SOC를 먼저 해지한 뒤 재신청 (변경 시나리오)
     *      주로 로밍 부가서비스 변경 시 사용 (기존 가입 해지 → 새 옵션으로 재가입)
     * 그 외: 바로 신청 (해지 없이)
     */
    private String flag;

    public String getNcn() { return ncn; }
    public void setNcn(String ncn) { this.ncn = ncn; }

    public String getCtn() { return ctn; }
    public void setCtn(String ctn) { this.ctn = ctn; }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }

    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }

    public String getFtrNewParam() { return ftrNewParam; }
    public void setFtrNewParam(String ftrNewParam) { this.ftrNewParam = ftrNewParam; }

    public String getProdHstSeq() { return prodHstSeq; }
    public void setProdHstSeq(String prodHstSeq) { this.prodHstSeq = prodHstSeq; }

    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }
}
