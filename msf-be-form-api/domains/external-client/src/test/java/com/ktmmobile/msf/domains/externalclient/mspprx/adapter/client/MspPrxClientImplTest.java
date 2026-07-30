package com.ktmmobile.msf.domains.externalclient.mspprx.adapter.client;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.util.MultiValueMap;

import com.ktmmobile.msf.domains.externalclient.mspprx.adapter.client.httpclient.MspPrxHttpClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxJsonRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceId;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.in.ServiceAlterTraceRecorder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("MSP PRX Client 구현체")
class MspPrxClientImplTest {

    private final MspPrxHttpClient httpClient = mock(MspPrxHttpClient.class);
    private final ServiceAlterTraceRecorder serviceAlterTraceRecorder = mock(ServiceAlterTraceRecorder.class);
    private final MspPrxClientImpl client = new MspPrxClientImpl(
        httpClient,
        new MspPrxSoapResponseParser(),
        serviceAlterTraceRecorder
    );

    @Test
    @DisplayName("serviceCall.do에는 getURL form 파라미터로 요청한다")
    @SuppressWarnings("unchecked")
    void callServiceWithGetUrlFormData() {
        when(httpClient.serviceCall(any())).thenReturn(successResponseXml("moscPerInfoResponse"));

        client.callService(MspPrxFormRequest.builder()
            .parameter("appEventCd", "X25")
            .parameter("custNm", "홍길동")
            .build());

        ArgumentCaptor<MultiValueMap<String, String>> formCaptor = ArgumentCaptor.forClass(MultiValueMap.class);
        verify(httpClient).serviceCall(formCaptor.capture());

        MultiValueMap<String, String> form = formCaptor.getValue();
        assertThat(form).containsOnlyKeys("getURL");
        assertThat(URLDecoder.decode(form.getFirst("getURL"), StandardCharsets.UTF_8))
            .isEqualTo("appEventCd=X25&custNm=홍길동");

        ArgumentCaptor<ServiceAlterTraceRequest> traceCaptor = ArgumentCaptor.forClass(ServiceAlterTraceRequest.class);
        verify(serviceAlterTraceRecorder).recordTrace(traceCaptor.capture());
        assertThat(traceCaptor.getValue().getEventCd()).isEqualTo("X25");
        assertThat(traceCaptor.getValue().getPrcsMdlDivCd()).startsWith("PRX");
        assertThat(traceCaptor.getValue().getTrtmRsltSbst()).isNull();
        assertThat(traceCaptor.getValue().getParameter()).isEqualTo("appEventCd[X25]custNm[홍길동]");
    }

    @Test
    @DisplayName("osstServiceCall.do에는 getURL form 파라미터로 요청한다")
    @SuppressWarnings("unchecked")
    void callOsstServiceWithGetUrlFormData() {
        when(httpClient.osstServiceCall(any())).thenReturn(successResponseXml("osstNpPrePrcResponse"));

        client.callOsstService(MspPrxFormRequest.builder()
            .parameter("appEventCd", "EP0")
            .parameter("ctn", "01012345678")
            .build());

        ArgumentCaptor<MultiValueMap<String, String>> formCaptor = ArgumentCaptor.forClass(MultiValueMap.class);
        verify(httpClient).osstServiceCall(formCaptor.capture());

        MultiValueMap<String, String> form = formCaptor.getValue();
        assertThat(form).containsOnlyKeys("getURL");
        assertThat(URLDecoder.decode(form.getFirst("getURL"), StandardCharsets.UTF_8))
            .isEqualTo("appEventCd=EP0&ctn=01012345678");
    }

    @Test
    @DisplayName("simpleOpenServiceCall.do에는 일반 form 파라미터로 요청한다")
    @SuppressWarnings("unchecked")
    void callSimpleOpenServiceWithFormData() {
        when(httpClient.simpleOpenServiceCall(any())).thenReturn(successResponseXml("osstNpBfacAgreeResponse"));

        client.callSimpleOpenService(MspPrxFormRequest.builder()
            .parameter("appEventCd", "NP1")
            .parameter("custNm", "홍길동")
            .build());

        ArgumentCaptor<MultiValueMap<String, String>> formCaptor = ArgumentCaptor.forClass(MultiValueMap.class);
        verify(httpClient).simpleOpenServiceCall(formCaptor.capture());

        assertThat(formCaptor.getValue())
            .containsEntry("appEventCd", List.of("NP1"))
            .containsEntry("custNm", List.of("홍길동"))
            .doesNotContainKey("getURL");
    }

    @Test
    @DisplayName("xmlOsstServiceCall.do에는 일반 form 파라미터로 요청한다")
    @SuppressWarnings("unchecked")
    void callXmlOsstServiceWithFormData() {
        when(httpClient.xmlOsstServiceCall(any())).thenReturn(successResponseXml("osstNpBfacAgreeResponse"));

        client.callXmlOsstService(MspPrxFormRequest.builder()
            .parameter("appEventCd", "NP1")
            .parameter("custNm", "홍길동")
            .build());

        ArgumentCaptor<MultiValueMap<String, String>> formCaptor = ArgumentCaptor.forClass(MultiValueMap.class);
        verify(httpClient).xmlOsstServiceCall(formCaptor.capture());

        assertThat(formCaptor.getValue())
            .containsEntry("appEventCd", List.of("NP1"))
            .containsEntry("custNm", List.of("홍길동"))
            .doesNotContainKey("getURL");
    }

    @Test
    @DisplayName("xmlSelfServiceCall.do에는 일반 form 파라미터로 요청한다")
    @SuppressWarnings("unchecked")
    void callXmlSelfServiceWithFormData() {
        when(httpClient.xmlSelfServiceCall(any())).thenReturn(successResponseXml("moscRegSvcChgSelfResponse"));

        client.callXmlSelfService(MspPrxFormRequest.builder()
            .parameter("appEventCd", "X19")
            .parameter("custNm", "홍길동")
            .build());

        ArgumentCaptor<MultiValueMap<String, String>> formCaptor = ArgumentCaptor.forClass(MultiValueMap.class);
        verify(httpClient).xmlSelfServiceCall(formCaptor.capture());

        assertThat(formCaptor.getValue())
            .containsEntry("appEventCd", List.of("X19"))
            .containsEntry("custNm", List.of("홍길동"))
            .doesNotContainKey("getURL");
    }

    @Test
    @DisplayName("serviceCallJson.do에 JSON body를 전달하고 SOAP 응답을 파싱한다")
    void callServiceJson() {
        String responseXml = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <ns2:moscCprtProdOptRetvResponse xmlns:ns2="http://selfcare.so.itl.mvno.kt.com/">
                        <return>
                            <commHeader>
                                <globalNo>JSON-GLOBAL</globalNo>
                                <responseType>N</responseType>
                                <responseCode></responseCode>
                                <responseBasic></responseBasic>
                            </commHeader>
                            <outDto>
                                <prodId>PROD001</prodId>
                            </outDto>
                        </return>
                    </ns2:moscCprtProdOptRetvResponse>
                </soap:Body>
            </soap:Envelope>
            """;
        when(httpClient.serviceCallJson(Map.of(
            "appEventCd", "Y52",
            "custId", "1000000001",
            "prodId", "PROD001"
        ))).thenReturn(responseXml);

        MspPrxSoapResponse response = client.callServiceJson(MspPrxJsonRequest.builder()
            .property("appEventCd", "Y52")
            .property("custId", "1000000001")
            .property("prodId", "PROD001")
            .build());

        assertThat(response.success()).isTrue();
        assertThat(response.operationName()).isEqualTo("moscCprtProdOptRetvResponse");
        assertThat(response.globalNo()).isEqualTo("JSON-GLOBAL");
        assertThat(response.payloadText("outDto", "prodId")).contains("PROD001");
        assertThat(response.rawXml()).isEqualTo(responseXml);
    }

    @Test
    @DisplayName("serviceCallJson.do에 배열형 JSON 필드를 그대로 전달한다")
    @SuppressWarnings("unchecked")
    void callServiceJsonWithArrayProperty() {
        String responseXml = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <ns2:moscPrdcTrtmPreChkResponse xmlns:ns2="http://selfcare.so.itl.mvno.kt.com/">
                        <return>
                            <commHeader>
                                <responseType>N</responseType>
                            </commHeader>
                        </return>
                    </ns2:moscPrdcTrtmPreChkResponse>
                </soap:Body>
            </soap:Envelope>
            """;
        when(httpClient.serviceCallJson(anyMap())).thenReturn(responseXml);

        List<Map<String, Object>> prdcList = List.of(
            Map.of("prdcCd", "PROD001", "prdcSeqNo", "1")
        );

        client.callServiceJson(MspPrxJsonRequest.builder()
            .property("appEventCd", "Y24")
            .property("prdcList", prdcList)
            .build());

        ArgumentCaptor<Map<String, Object>> bodyCaptor = ArgumentCaptor.forClass(Map.class);
        verify(httpClient).serviceCallJson(bodyCaptor.capture());

        assertThat(bodyCaptor.getValue())
            .containsEntry("appEventCd", "Y24")
            .containsEntry("prdcList", prdcList);
    }

    @Test
    @DisplayName("serviceAlterTrace가 있으면 PRX 응답 정보를 보강해서 저장한다")
    void recordServiceAlterTraceWithResponse() {
        String responseXml = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <ns2:moscRegSvcChgSelfResponse xmlns:ns2="http://selfcare.so.itl.mvno.kt.com/">
                        <return>
                            <commHeader>
                                <globalNo>GLOBAL-001</globalNo>
                                <responseType>N</responseType>
                                <responseCode></responseCode>
                                <responseBasic>정상 처리</responseBasic>
                            </commHeader>
                        </return>
                    </ns2:moscRegSvcChgSelfResponse>
                </soap:Body>
            </soap:Envelope>
            """;
        when(httpClient.serviceCall(any())).thenReturn(responseXml);
        when(serviceAlterTraceRecorder.recordTrace(any()))
            .thenReturn(new ServiceAlterTraceId("1000000001", "20260727", 1));

        MspPrxSoapResponse response = client.callService(MspPrxFormRequest.builder()
            .parameter("appEventCd", "X19")
            .serviceAlterTrace(ServiceAlterTraceRequest.builder()
                .ncn("1000000001")
                .eventCd("X19")
                .trtmRsltSbst("요금제변경")
                .build())
            .build());

        assertThat(response.serviceAlterTraceId())
            .isEqualTo(new ServiceAlterTraceId("1000000001", "20260727", 1));

        ArgumentCaptor<ServiceAlterTraceRequest> traceCaptor = ArgumentCaptor.forClass(ServiceAlterTraceRequest.class);
        verify(serviceAlterTraceRecorder).recordTrace(traceCaptor.capture());

        ServiceAlterTraceRequest traceRequest = traceCaptor.getValue();
        assertThat(traceRequest.getNcn()).isEqualTo("1000000001");
        assertThat(traceRequest.getEventCd()).isEqualTo("X19");
        assertThat(traceRequest.getPrcsMdlDivCd()).startsWith("PRX");
        assertThat(traceRequest.getTrtmRsltSbst()).isEqualTo("요금제변경");
        assertThat(traceRequest.getGlobalNo()).isEqualTo("GLOBAL-001");
        assertThat(traceRequest.getRsltCd()).isEqualTo("0000");
        assertThat(traceRequest.getPrcsSbst()).isEqualTo("정상 처리");
    }

    @Test
    @DisplayName("serviceAlterTrace 기본값은 PRX 요청 파라미터에서 업무 이력 값을 보강한다")
    void resolveServiceAlterTraceDefaultsFromParameters() {
        when(httpClient.serviceCall(any())).thenReturn(successResponseXml("moscRegSvcChgSelfResponse"));

        client.callService(MspPrxFormRequest.builder()
            .parameter("appEventCd", "X19")
            .parameter("prcsMdlInd", "GC260617153100")
            .parameter("svcCntrNo", "527143026")
            .parameter("ctn", "01030012343")
            .parameter("toSocCode", "PL214M328")
            .parameter("fromSocCode", "PL203D130")
            .parameter("toSocAmt", "-2727")
            .parameter("fromSocAmt", "0")
            .build());

        ArgumentCaptor<ServiceAlterTraceRequest> traceCaptor = ArgumentCaptor.forClass(ServiceAlterTraceRequest.class);
        verify(serviceAlterTraceRecorder).recordTrace(traceCaptor.capture());

        ServiceAlterTraceRequest traceRequest = traceCaptor.getValue();
        assertThat(traceRequest.getNcn()).isEqualTo("527143026");
        assertThat(traceRequest.getContractNum()).isEqualTo("527143026");
        assertThat(traceRequest.getSubscriberNo()).isEqualTo("01030012343");
        assertThat(traceRequest.getPrcsMdlDivCd()).isEqualTo("GC260617153100");
        assertThat(traceRequest.getASocCode()).isEqualTo("PL203D130");
        assertThat(traceRequest.getTSocCode()).isEqualTo("PL214M328");
        assertThat(traceRequest.getASocAmt()).isZero();
        assertThat(traceRequest.getTSocAmt()).isEqualTo(-2727);
        assertThat(traceRequest.getParameter())
            .contains("svcCntrNo[527143026]")
            .contains("ctn[01030012343]")
            .contains("appEventCd[X19]");
    }

    @Test
    @DisplayName("PRX 호출 실패 시 serviceAlterTrace 실패 이력을 저장하고 예외를 다시 던진다")
    void recordServiceAlterTraceWhenFailed() {
        when(httpClient.serviceCall(any())).thenReturn("");

        ServiceAlterTraceRequest traceRequest = ServiceAlterTraceRequest.builder()
            .ncn("1000000001")
            .eventCd("X19")
            .trtmRsltSbst("요금제변경")
            .build();

        assertThatThrownBy(() -> client.callService(MspPrxFormRequest.builder()
            .parameter("appEventCd", "X19")
            .serviceAlterTrace(traceRequest)
            .build()))
            .isInstanceOf(RuntimeException.class);

        ArgumentCaptor<ServiceAlterTraceRequest> traceCaptor = ArgumentCaptor.forClass(ServiceAlterTraceRequest.class);
        verify(serviceAlterTraceRecorder).recordTrace(traceCaptor.capture());

        assertThat(traceCaptor.getValue().getRsltCd()).isEqualTo("ERROR");
        assertThat(traceCaptor.getValue().getPrcsSbst()).isEqualTo("PRX 응답 XML이 비어 있습니다.");
    }

    @Test
    @DisplayName("serviceAlterTrace 저장 실패는 PRX 호출 결과에 영향을 주지 않는다")
    void ignoreServiceAlterTraceRecordFailure() {
        when(httpClient.serviceCall(any())).thenReturn(successResponseXml("moscRegSvcChgSelfResponse"));
        doThrow(new IllegalStateException("db error"))
            .when(serviceAlterTraceRecorder)
            .recordTrace(any());

        MspPrxSoapResponse response = client.callService(MspPrxFormRequest.builder()
            .parameter("appEventCd", "X19")
            .serviceAlterTrace(ServiceAlterTraceRequest.builder()
                .ncn("1000000001")
                .eventCd("X19")
                .build())
            .build());

        assertThat(response.success()).isTrue();
    }

    private String successResponseXml(String operationName) {
        return """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <ns2:%s xmlns:ns2="http://selfcare.so.itl.mvno.kt.com/">
                        <return>
                            <commHeader>
                                <responseType>N</responseType>
                            </commHeader>
                        </return>
                    </ns2:%s>
                </soap:Body>
            </soap:Envelope>
            """.formatted(operationName, operationName);
    }
}
