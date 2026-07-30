package com.ktmmobile.msf.domains.externalclient.mspprx.adapter.client;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.exception.MspPrxClientException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("MSP PRX SOAP 응답 파서")
class MspPrxSoapResponseParserTest {

    private final MspPrxSoapResponseParser parser = new MspPrxSoapResponseParser();

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("성공 응답의 operation, header, outDto payload를 파싱한다")
    void parseSuccessResponse() {
        String xml = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <ns2:inqrOsstSvcNoInfoResponse xmlns:ns2="http://osst.so.itl.mvno.kt.com/">
                        <return>
                            <bizHeader>
                                <appEventCd>NU1</appEventCd>
                            </bizHeader>
                            <commHeader>
                                <globalNo>TEST-GLOBAL</globalNo>
                                <responseType>N</responseType>
                                <responseCode></responseCode>
                                <responseBasic></responseBasic>
                            </commHeader>
                            <outDto>
                                <lastPageYn>N</lastPageYn>
                                <svcNoList>
                                    <tlphNo>01011112222</tlphNo>
                                </svcNoList>
                                <svcNoList>
                                    <tlphNo>01033334444</tlphNo>
                                </svcNoList>
                            </outDto>
                            <outDto>
                                <lastPageYn>N</lastPageYn>
                                <svcNoList>
                                    <tlphNo>01011112222</tlphNo>
                                </svcNoList>
                                <svcNoList>
                                    <tlphNo>01033334444</tlphNo>
                                </svcNoList>
                            </outDto>
                        </return>
                    </ns2:inqrOsstSvcNoInfoResponse>
                </soap:Body>
            </soap:Envelope>
            """;

        MspPrxSoapResponse response = parser.parse(xml);

        assertThat(response.success()).isTrue();
        assertThat(response.operationName()).isEqualTo("inqrOsstSvcNoInfoResponse");
        assertThat(response.globalNo()).isEqualTo("TEST-GLOBAL");
        assertThat(response.bizHeader()).containsEntry("appEventCd", "NU1");
        assertThat(response.payloadText("outDto", "lastPageYn")).contains("N");
        assertThat(response.payloadList("outDto").orElseThrow()).hasSize(2);

        List<Map<String, Object>> svcNoList = (List<Map<String, Object>>) (List<?>) response.payloadList("outDto", "svcNoList")
            .orElseThrow();
        assertThat(svcNoList).hasSize(4);
        assertThat(svcNoList).extracting(item -> item.get("tlphNo"))
            .containsExactly("01011112222", "01033334444", "01011112222", "01033334444");
        assertThat(response.rawXml()).isEqualTo(xml);
    }

    @Test
    @DisplayName("return 직하 payload 필드를 파싱한다")
    void parsePayloadFieldsDirectlyUnderReturn() {
        String xml = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <ns2:osstNpPrePrcResponse xmlns:ns2="http://osst.so.itl.mvno.kt.com/">
                        <return>
                            <bizHeader>
                                <appEventCd>PC0</appEventCd>
                            </bizHeader>
                            <commHeader>
                                <globalNo>PC0-GLOBAL</globalNo>
                                <responseType>N</responseType>
                                <responseCode></responseCode>
                                <responseBasic></responseBasic>
                            </commHeader>
                            <osstOrdNo>20240522000001</osstOrdNo>
                            <rsltCd>S</rsltCd>
                            <rsltMsg>성공</rsltMsg>
                        </return>
                    </ns2:osstNpPrePrcResponse>
                </soap:Body>
            </soap:Envelope>
            """;

        MspPrxSoapResponse response = parser.parse(xml);

        assertThat(response.payloadText("osstOrdNo")).contains("20240522000001");
        assertThat(response.payloadText("rsltCd")).contains("S");
        assertThat(response.payloadText("rsltMsg")).contains("성공");
    }

    @Test
    @DisplayName("outDto 하위 중첩 payload 객체를 파싱한다")
    void parseNestedPayloadObjects() {
        String xml = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <ns2:moscPerInfoResponse xmlns:ns2="http://selfcare.so.itl.mvno.kt.com/">
                        <return>
                            <bizHeader>
                                <appEventCd>A01</appEventCd>
                            </bizHeader>
                            <commHeader>
                                <globalNo>A01-GLOBAL</globalNo>
                                <responseType>N</responseType>
                                <responseCode></responseCode>
                                <responseBasic></responseBasic>
                            </commHeader>
                            <outDto>
                                <outApplyDto>
                                    <svcCntrNo>1234567890</svcCntrNo>
                                    <ctn>01012345678</ctn>
                                </outApplyDto>
                                <outChgDto>
                                    <chgCd>C001</chgCd>
                                </outChgDto>
                            </outDto>
                        </return>
                    </ns2:moscPerInfoResponse>
                </soap:Body>
            </soap:Envelope>
            """;

        MspPrxSoapResponse response = parser.parse(xml);

        assertThat(response.payloadText("outDto", "outApplyDto", "svcCntrNo")).contains("1234567890");
        assertThat(response.payloadText("outDto", "outApplyDto", "ctn")).contains("01012345678");
        assertThat(response.payloadText("outDto", "outChgDto", "chgCd")).contains("C001");
        assertThat(response.payloadObject("outDto", "outApplyDto").orElseThrow())
            .containsEntry("svcCntrNo", "1234567890")
            .containsEntry("ctn", "01012345678");
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("동일 이름의 반복 payload 노드를 List로 파싱한다")
    void parseRepeatedPayloadNodesAsList() {
        String xml = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <ns2:inqrOsstSvcNoInfoResponse xmlns:ns2="http://osst.so.itl.mvno.kt.com/">
                        <return>
                            <commHeader>
                                <globalNo>NU1-GLOBAL</globalNo>
                                <responseType>N</responseType>
                                <responseCode></responseCode>
                                <responseBasic></responseBasic>
                            </commHeader>
                            <outDto>
                                <svcNoList>
                                    <tlphNo>01011112222</tlphNo>
                                    <tlphNoStatCd>AA</tlphNoStatCd>
                                </svcNoList>
                                <svcNoList>
                                    <tlphNo>01033334444</tlphNo>
                                    <tlphNoStatCd>BB</tlphNoStatCd>
                                </svcNoList>
                            </outDto>
                        </return>
                    </ns2:inqrOsstSvcNoInfoResponse>
                </soap:Body>
            </soap:Envelope>
            """;

        MspPrxSoapResponse response = parser.parse(xml);

        Object svcNoListValue = response.payloadValue("outDto", "svcNoList").orElseThrow();
        assertThat(svcNoListValue).isInstanceOf(List.class);

        List<Map<String, Object>> svcNoList = (List<Map<String, Object>>) svcNoListValue;
        assertThat(svcNoList).extracting(item -> item.get("tlphNo"))
            .containsExactly("01011112222", "01033334444");
        assertThat(svcNoList).extracting(item -> item.get("tlphNoStatCd"))
            .containsExactly("AA", "BB");
    }

    @Test
    @DisplayName("responseType이 N이 아니어도 예외 없이 응답 값을 반환한다")
    void parseResponseEvenWhenResponseTypeIsNotNormal() {
        String xml = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <ns2:osstNpPrePrcResponse xmlns:ns2="http://osst.so.itl.mvno.kt.com/">
                        <return>
                            <commHeader>
                                <globalNo>TEST-GLOBAL</globalNo>
                                <responseType>E</responseType>
                                <responseCode>ITL_SST_E1018</responseCode>
                                <responseBasic>개통 불가 고객입니다</responseBasic>
                            </commHeader>
                        </return>
                    </ns2:osstNpPrePrcResponse>
                </soap:Body>
            </soap:Envelope>
            """;

        MspPrxSoapResponse response = parser.parse(xml);

        assertThat(response.success()).isFalse();
        assertThat(response.responseType()).isEqualTo("E");
        assertThat(response.responseCode()).isEqualTo("ITL_SST_E1018");
        assertThat(response.responseBasic()).isEqualTo("개통 불가 고객입니다");
        assertThat(response.globalNo()).isEqualTo("TEST-GLOBAL");
    }

    @ParameterizedTest(name = "[{index}] 빈 응답: {0}")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\n\t"})
    @DisplayName("응답 XML이 비어 있으면 예외가 발생한다")
    void throwExceptionWhenResponseXmlIsBlank(String xml) {
        assertThatThrownBy(() -> parser.parse(xml))
            .isInstanceOf(MspPrxClientException.class)
            .hasMessage("PRX 응답 XML이 비어 있습니다.");
    }

    @Test
    @DisplayName("응답 XML이 올바른 XML이 아니면 예외가 발생한다")
    void throwExceptionWhenResponseXmlIsMalformed() {
        String xml = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <ns2:osstNpPrePrcResponse>
            """;

        assertThatThrownBy(() -> parser.parse(xml))
            .isInstanceOf(MspPrxClientException.class)
            .hasMessage("PRX 응답 XML 파싱에 실패했습니다.");
    }

    @Test
    @DisplayName("SOAP Body가 없으면 예외가 발생한다")
    void throwExceptionWhenResponseXmlHasNoSoapBody() {
        String xml = """
            <empty />
            """;

        assertThatThrownBy(() -> parser.parse(xml))
            .isInstanceOf(MspPrxClientException.class)
            .hasMessage("PRX 응답에 SOAP Body가 없습니다.");
    }

    @Test
    @DisplayName("SOAP Body 안에 응답 operation 노드가 없으면 예외가 발생한다")
    void throwExceptionWhenSoapBodyHasNoOperationNode() {
        String xml = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    응답 노드 없음
                </soap:Body>
            </soap:Envelope>
            """;

        assertThatThrownBy(() -> parser.parse(xml))
            .isInstanceOf(MspPrxClientException.class)
            .hasMessage("PRX SOAP Body에 응답 노드가 없습니다.");
    }

    @Test
    @DisplayName("응답 operation 안에 return 노드가 없으면 예외가 발생한다")
    void throwExceptionWhenResponseHasNoReturnNode() {
        String xml = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <ns2:osstNpPrePrcResponse xmlns:ns2="http://osst.so.itl.mvno.kt.com/">
                        <notReturn />
                    </ns2:osstNpPrePrcResponse>
                </soap:Body>
            </soap:Envelope>
            """;

        assertThatThrownBy(() -> parser.parse(xml))
            .isInstanceOf(MspPrxClientException.class)
            .hasMessage("PRX 응답에 return 노드가 없습니다.");
    }
}
