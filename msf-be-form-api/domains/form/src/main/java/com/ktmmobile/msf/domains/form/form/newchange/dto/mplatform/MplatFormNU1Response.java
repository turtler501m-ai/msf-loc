package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.serializer.PrxDecryptValueDeserializer;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@EqualsAndHashCode(callSuper = true)
@Data
public class MplatFormNU1Response extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    public static class OutDto {

        @JacksonXmlProperty(localName = "svcNoList")
        private List<SvcNoList> svcNoListAll;
        @JacksonXmlProperty(localName = "lastPageYn")
        private String lastPageYn; //마지막 페이지 여부

        @Data
        public static class SvcNoList {

            private String tlphNoStatCd; //전화번호 상태코드
            private String asgnAgncId; //할당 대리점 ID
            private String tlphNoOwnCmncCmpnCd; //전화번호소유 통신회사 코드
            private String openSvcIndCd; //개통서비스구분코드
            private String encdTlphNo; //암호화전화번호

            @JsonDeserialize(using = PrxDecryptValueDeserializer.class)
            private String tlphNo; //전화번호

            private String fvrtnoAqcsPsblYn; //선호번호획득가능여부
            private String rsrvCustNo; //고객번호
            private String statMntnEndPrrnDate; //상태유지 종료 예정일
            private String tlphNoChrcCd; //전화번호특성코드
            private String tlphNoStatChngDt; //전화번호 상태변경일
            private String tlphNoUseCd; //번호사용용도코드
            private String tlphNoUseMntCd; //번호사용상세사유코드
        }

    }

}
