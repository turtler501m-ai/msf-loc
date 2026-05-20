package com.ktmmobile.msf.domains.form.form.termination.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class CanCustMgmtDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ListReqDto {
        private String procCd;       // 처리상태 (RC/RQ/CP/BK, null=전체)
        private String formTypeCd;   // 신청서구분 (1=신규/변경, 2=서비스변경, 3=명의변경, 4=서비스해지, null=전체)
        private String searchGbn;    // 검색구분 (CONTRACT_NUM/CANCEL_MOBILE_NO/CSTMR_NM)
        private String searchName;   // 검색어
        private String startDt;      // 접수일자 시작 (YYYYMMDD)
        private String endDt;        // 접수일자 종료 (YYYYMMDD)
        private PageReqDto page;     // 페이징 (pageNum/rowSize)
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PageReqDto {
        private Integer pageNum;
        private Integer rowSize;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ListResDto {
        private List<DetailDto> data;
        private MetaDto meta;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MetaDto {
        private PageMetaDto page;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PageMetaDto {
        private int pageNum;
        private int rowSize;
        private int totalCount;
    }

    @Getter
    @Setter
    @NoArgsConstructor
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
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProcessReqDto {
        private Long requestKey;
        private String itgOderWhyCd;
        private String aftmnIncInCd;
        private String apyRelTypeCd;
        private String custTchMediCd;
        private String smsRcvYn;
        private String memo;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProcessStatusDto {
        private Long requestKey;
        private String formTypeCd;
        private String procCd;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProcessUpdateDto {
        private Long requestKey;
        private String procCd;
        private String memo;
        private String resCd;
        private String resMsg;
        private String resNo;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProcessResVO {
        private String osstOrdNo;
        private String procCd;

        public static ProcessResVO complete(String osstOrdNo) {
            ProcessResVO vo = new ProcessResVO();
            vo.osstOrdNo = osstOrdNo;
            vo.procCd = "CP";
            return vo;
        }

        public static ProcessResVO revert() {
            ProcessResVO vo = new ProcessResVO();
            vo.procCd = "RC";
            return vo;
        }

        public static ProcessResVO reject() {
            ProcessResVO vo = new ProcessResVO();
            vo.procCd = "BK";
            return vo;
        }
    }
}
