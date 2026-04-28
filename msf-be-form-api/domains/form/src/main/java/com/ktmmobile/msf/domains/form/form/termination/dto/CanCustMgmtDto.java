package com.ktmmobile.msf.domains.form.form.termination.dto;

import java.util.List;

public class CanCustMgmtDto {

    public static class ListReqDto {
        private String procCd;       // 처리상태 (RC/RQ/CP/BK, null=전체)
        private String formTypeCd;   // 신청서구분 (1=신규/변경, 2=서비스변경, 3=명의변경, 4=서비스해지, null=전체)
        private String searchGbn;    // 검색구분 (CONTRACT_NUM/CANCEL_MOBILE_NO/CSTMR_NM)
        private String searchName;   // 검색어
        private String startDt;      // 접수일자 시작 (YYYYMMDD)
        private String endDt;        // 접수일자 종료 (YYYYMMDD)
        private PageReqDto page;     // 페이징 (pageNum/rowSize)

        public String getProcCd() { return procCd; }
        public void setProcCd(String procCd) { this.procCd = procCd; }
        public String getFormTypeCd() { return formTypeCd; }
        public void setFormTypeCd(String formTypeCd) { this.formTypeCd = formTypeCd; }
        public String getSearchGbn() { return searchGbn; }
        public void setSearchGbn(String searchGbn) { this.searchGbn = searchGbn; }
        public String getSearchName() { return searchName; }
        public void setSearchName(String searchName) { this.searchName = searchName; }
        public String getStartDt() { return startDt; }
        public void setStartDt(String startDt) { this.startDt = startDt; }
        public String getEndDt() { return endDt; }
        public void setEndDt(String endDt) { this.endDt = endDt; }
        public PageReqDto getPage() { return page; }
        public void setPage(PageReqDto page) { this.page = page; }
    }

    public static class PageReqDto {
        private Integer pageNum;
        private Integer rowSize;

        public Integer getPageNum() { return pageNum; }
        public void setPageNum(Integer pageNum) { this.pageNum = pageNum; }
        public Integer getRowSize() { return rowSize; }
        public void setRowSize(Integer rowSize) { this.rowSize = rowSize; }
    }

    public static class ListResDto {
        private List<DetailDto> data;
        private MetaDto meta;

        public List<DetailDto> getData() { return data; }
        public void setData(List<DetailDto> data) { this.data = data; }
        public MetaDto getMeta() { return meta; }
        public void setMeta(MetaDto meta) { this.meta = meta; }
    }

    public static class MetaDto {
        private PageMetaDto page;

        public PageMetaDto getPage() { return page; }
        public void setPage(PageMetaDto page) { this.page = page; }
    }

    public static class PageMetaDto {
        private int pageNum;
        private int rowSize;
        private int totalCount;

        public int getPageNum() { return pageNum; }
        public void setPageNum(int pageNum) { this.pageNum = pageNum; }
        public int getRowSize() { return rowSize; }
        public void setRowSize(int rowSize) { this.rowSize = rowSize; }
        public int getTotalCount() { return totalCount; }
        public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
    }

    public static class DetailDto {
        private Long requestKey;
        private String formTypeCd;
        private String operTypeCd;
        private String procCd;
        private String cstmrNm;
        private String cstmrBirth;
        private String cstmrTypeCd;
        private String identityCertTypeCd;
        private String agentCd;
        private String agentNm;
        private String shopCd;
        private String shopNm;
        private String managerNm;
        private String regstId;
        private String contractNum;
        private String cancelMobileNo;
        private String receiveMobileNo;
        private String cretDt;
        private String procDt;
        private String amdDt;
        private String amdId;
        private String memo;
        private Long payAmt;
        private Long pnltAmt;
        private Long lastSumAmt;
        private String resCd;
        private String resMsg;
        private String resNo;

        public Long getRequestKey() { return requestKey; }
        public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }
        public String getFormTypeCd() { return formTypeCd; }
        public void setFormTypeCd(String formTypeCd) { this.formTypeCd = formTypeCd; }
        public String getOperTypeCd() { return operTypeCd; }
        public void setOperTypeCd(String operTypeCd) { this.operTypeCd = operTypeCd; }
        public String getProcCd() { return procCd; }
        public void setProcCd(String procCd) { this.procCd = procCd; }
        public String getCstmrNm() { return cstmrNm; }
        public void setCstmrNm(String cstmrNm) { this.cstmrNm = cstmrNm; }
        public String getCstmrBirth() { return cstmrBirth; }
        public void setCstmrBirth(String cstmrBirth) { this.cstmrBirth = cstmrBirth; }
        public String getCstmrTypeCd() { return cstmrTypeCd; }
        public void setCstmrTypeCd(String cstmrTypeCd) { this.cstmrTypeCd = cstmrTypeCd; }
        public String getIdentityCertTypeCd() { return identityCertTypeCd; }
        public void setIdentityCertTypeCd(String identityCertTypeCd) { this.identityCertTypeCd = identityCertTypeCd; }
        public String getAgentCd() { return agentCd; }
        public void setAgentCd(String agentCd) { this.agentCd = agentCd; }
        public String getAgentNm() { return agentNm; }
        public void setAgentNm(String agentNm) { this.agentNm = agentNm; }
        public String getShopCd() { return shopCd; }
        public void setShopCd(String shopCd) { this.shopCd = shopCd; }
        public String getShopNm() { return shopNm; }
        public void setShopNm(String shopNm) { this.shopNm = shopNm; }
        public String getManagerNm() { return managerNm; }
        public void setManagerNm(String managerNm) { this.managerNm = managerNm; }
        public String getRegstId() { return regstId; }
        public void setRegstId(String regstId) { this.regstId = regstId; }
        public String getContractNum() { return contractNum; }
        public void setContractNum(String contractNum) { this.contractNum = contractNum; }
        public String getCancelMobileNo() { return cancelMobileNo; }
        public void setCancelMobileNo(String cancelMobileNo) { this.cancelMobileNo = cancelMobileNo; }
        public String getReceiveMobileNo() { return receiveMobileNo; }
        public void setReceiveMobileNo(String receiveMobileNo) { this.receiveMobileNo = receiveMobileNo; }
        public String getProcDt() { return procDt; }
        public void setProcDt(String procDt) { this.procDt = procDt; }
        public String getCretDt() { return cretDt; }
        public void setCretDt(String cretDt) { this.cretDt = cretDt; }
        public String getAmdDt() { return amdDt; }
        public void setAmdDt(String amdDt) { this.amdDt = amdDt; }
        public String getAmdId() { return amdId; }
        public void setAmdId(String amdId) { this.amdId = amdId; }
        public String getMemo() { return memo; }
        public void setMemo(String memo) { this.memo = memo; }
        public Long getPayAmt() { return payAmt; }
        public void setPayAmt(Long payAmt) { this.payAmt = payAmt; }
        public Long getPnltAmt() { return pnltAmt; }
        public void setPnltAmt(Long pnltAmt) { this.pnltAmt = pnltAmt; }
        public Long getLastSumAmt() { return lastSumAmt; }
        public void setLastSumAmt(Long lastSumAmt) { this.lastSumAmt = lastSumAmt; }
        public String getResCd() { return resCd; }
        public void setResCd(String resCd) { this.resCd = resCd; }
        public String getResMsg() { return resMsg; }
        public void setResMsg(String resMsg) { this.resMsg = resMsg; }
        public String getResNo() { return resNo; }
        public void setResNo(String resNo) { this.resNo = resNo; }
    }

    public static class ProcessReqDto {
        private Long requestKey;
        private String procCd;
        private String itgOderWhyCd;
        private String aftmnIncInCd;
        private String apyRelTypeCd;
        private String custTchMediCd;
        private String smsRcvYn;
        private String memo;
        private String amdId;
        private String resCd;
        private String resMsg;
        private String resNo;

        public Long getRequestKey() { return requestKey; }
        public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }
        public String getProcCd() { return procCd; }
        public void setProcCd(String procCd) { this.procCd = procCd; }
        public String getItgOderWhyCd() { return itgOderWhyCd; }
        public void setItgOderWhyCd(String itgOderWhyCd) { this.itgOderWhyCd = itgOderWhyCd; }
        public String getAftmnIncInCd() { return aftmnIncInCd; }
        public void setAftmnIncInCd(String aftmnIncInCd) { this.aftmnIncInCd = aftmnIncInCd; }
        public String getApyRelTypeCd() { return apyRelTypeCd; }
        public void setApyRelTypeCd(String apyRelTypeCd) { this.apyRelTypeCd = apyRelTypeCd; }
        public String getCustTchMediCd() { return custTchMediCd; }
        public void setCustTchMediCd(String custTchMediCd) { this.custTchMediCd = custTchMediCd; }
        public String getSmsRcvYn() { return smsRcvYn; }
        public void setSmsRcvYn(String smsRcvYn) { this.smsRcvYn = smsRcvYn; }
        public String getMemo() { return memo; }
        public void setMemo(String memo) { this.memo = memo; }
        public String getAmdId() { return amdId; }
        public void setAmdId(String amdId) { this.amdId = amdId; }
        public String getResCd() { return resCd; }
        public void setResCd(String resCd) { this.resCd = resCd; }
        public String getResMsg() { return resMsg; }
        public void setResMsg(String resMsg) { this.resMsg = resMsg; }
        public String getResNo() { return resNo; }
        public void setResNo(String resNo) { this.resNo = resNo; }
    }

    public static class ProcessResVO {
        private boolean success;
        private String message;
        private String osstOrdNo;
        private String procCd;

        public static ProcessResVO ok(String osstOrdNo) {
            ProcessResVO vo = new ProcessResVO();
            vo.success = true;
            vo.osstOrdNo = osstOrdNo;
            vo.procCd = "CP";
            vo.message = "";
            return vo;
        }

        public static ProcessResVO fail(String message) {
            ProcessResVO vo = new ProcessResVO();
            vo.success = false;
            vo.message = message;
            return vo;
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getOsstOrdNo() { return osstOrdNo; }
        public void setOsstOrdNo(String osstOrdNo) { this.osstOrdNo = osstOrdNo; }
        public String getProcCd() { return procCd; }
        public void setProcCd(String procCd) { this.procCd = procCd; }
    }
}
