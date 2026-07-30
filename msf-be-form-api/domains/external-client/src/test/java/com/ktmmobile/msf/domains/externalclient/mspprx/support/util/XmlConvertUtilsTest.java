package com.ktmmobile.msf.domains.externalclient.mspprx.support.util;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.annotation.JsonDeserialize;

import com.ktmmobile.msf.commons.crypto.support.util.KisaSeedUtils;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.serializer.PrxDecryptValueDeserializer;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("XML 변환 유틸리티")
class XmlConvertUtilsTest {

    @Test
    @DisplayName("Java 객체를 XML 문자열로 변환한다")
    void convertObjectToXml() {
        String xml = XmlConvertUtils.convertObjectToXml(new SampleXmlRequest("FMC0", "홍길동"));

        assertThat(xml)
            .contains("<sampleXmlRequest>")
            .contains("<appEventCd>FMC0</appEventCd>")
            .contains("<custNm>홍길동</custNm>")
            .doesNotContain("<?xml");
    }

    @Test
    @DisplayName("변환 대상 객체가 null이면 빈 문자열을 반환한다")
    void convertNullObjectToEmptyString() {
        assertThat(XmlConvertUtils.convertObjectToXml(null)).isEmpty();
    }

    @Test
    @DisplayName("Jackson 3 ValueDeserializer를 적용해 return XML을 객체로 변환한다")
    void parseReturnXmlWithJackson3ValueDeserializer() throws Exception {
        KisaSeedUtils.initialize("0123456789abcdef", "MDEyMzQ1Njc4OWFiY2RlZg==");
        String encryptedPhoneNumber = KisaSeedUtils.encrypt("01012345678");
        String xml = """
            <Envelope>
                <Body>
                    <return>
                        <phoneNumber>%s</phoneNumber>
                    </return>
                </Body>
            </Envelope>
            """.formatted(encryptedPhoneNumber);

        SampleXmlResponse response = XmlConvertUtils.xmlReturnParser(xml, SampleXmlResponse.class);

        assertThat(response.phoneNumber).isEqualTo("01012345678");
    }

    @XmlRootElement(name = "sampleXmlRequest")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class SampleXmlRequest {

        private String appEventCd;
        private String custNm;

        SampleXmlRequest() {
        }

        SampleXmlRequest(String appEventCd, String custNm) {
            this.appEventCd = appEventCd;
            this.custNm = custNm;
        }
    }

    private static class SampleXmlResponse {

        @JsonDeserialize(using = PrxDecryptValueDeserializer.class)
        private String phoneNumber;
    }
}
