package com.ktmmobile.msf.domains.form.common.mplatform;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformOsstServiceType;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformServiceType;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormXmlSelfcareRequest;

@Service
@RequiredArgsConstructor
public class MsfMcpOsstPrxService {

    private final MspPrxClient mspPrxClient;
    private final MsfOsstHistoryService msfOsstHistoryService;

    public MspPrxSoapResponse callSimpleOpenService(Map<String, String> params, int timeout) throws SocketTimeoutException {
        try {
            MspPrxSoapResponse response = callWithTimeout(
                () -> mspPrxClient.callSimpleOpenService(MspPrxFormRequest.builder()
                    .parameters(params)
                    .build()),
                timeout
            );
            msfOsstHistoryService.saveResponse(params, response);
            return response;
        } catch (SocketTimeoutException e) {
            msfOsstHistoryService.saveFailure(params, e);
            throw e;
        } catch (RuntimeException e) {
            msfOsstHistoryService.saveFailure(params, e);
            throw e;
        }
    }

    public MspPrxSoapResponse callXmlSelfService(List<Object> list, MplatformServiceType serviceType, MplatFormXmlSelfcareRequest request) {
        ServiceAlterTraceRequest trace = ServiceAlterTraceRequest.builder().eventCd(serviceType.getEventCd()).build();
        try {
            Map<String, String> map = createMapData(list, serviceType, request);
            MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.createXmlSelfRequest(map, trace);
            MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callXmlSelfService(mspPrxFormRequest);
            // msfOsstHistoryService.saveResponse(Map.of("mvnoOrdNo", validMvnoOrdNo, "appEventCd", appEventCd), mspPrxSoapResponse);
            return mspPrxSoapResponse;
        } catch (Exception e) {
            // msfOsstHistoryService.saveFailure(Map.of("mvnoOrdNo", validMvnoOrdNo, "appEventCd", appEventCd), e);
            throw e;
        }
    }

    public MspPrxSoapResponse callOsstService(Map<String, String> params, int timeout) throws SocketTimeoutException {
        try {
            MspPrxSoapResponse response = callWithTimeout(
                () -> mspPrxClient.callOsstService(MspPrxFormRequest.builder()
                    .parameters(params)
                    .build()),
                timeout
            );
            msfOsstHistoryService.saveResponse(params, response);
            return response;
        } catch (SocketTimeoutException e) {
            msfOsstHistoryService.saveFailure(params, e);
            throw e;
        } catch (RuntimeException e) {
            msfOsstHistoryService.saveFailure(params, e);
            throw e;
        }
    }

    public MspPrxSoapResponse callXmlOsstService(List<Object> list, String appEventCd, String mvnoOrdNo) {
        Map<String, String> map = StringUtils.hasText(mvnoOrdNo)
            ? Map.of("mvnoOrdNo", mvnoOrdNo, "appEventCd", appEventCd)
            : Map.of("appEventCd", appEventCd);
        ServiceAlterTraceRequest trace = ServiceAlterTraceRequest.builder().eventCd(appEventCd).build();
        try {
            MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.createXmlRequest(list, appEventCd, trace);
            MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callXmlOsstService(mspPrxFormRequest);
            msfOsstHistoryService.saveResponse(map, mspPrxSoapResponse);
            return mspPrxSoapResponse;
        } catch (Exception e) {
            msfOsstHistoryService.saveFailure(map, e);
            throw e;
        }
    }

    public MspPrxSoapResponse callXmlOsstService(List<Object> list, String appEventCd, String appAgncCd, String mvnoOrdNo) {
        Map<String, String> map = StringUtils.hasText(mvnoOrdNo)
            ? Map.of("mvnoOrdNo", mvnoOrdNo, "appEventCd", appEventCd)
            : Map.of("appEventCd", appEventCd);
        ServiceAlterTraceRequest trace = ServiceAlterTraceRequest.builder().eventCd(appEventCd).build();
        try {
            MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.createXmlRequest(list,
                MplatformOsstServiceType.findByEventCd(appEventCd),
                appAgncCd,
                trace);
            MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callXmlOsstService(mspPrxFormRequest);
            msfOsstHistoryService.saveResponse(map, mspPrxSoapResponse);
            return mspPrxSoapResponse;
        } catch (Exception e) {
            msfOsstHistoryService.saveFailure(map, e);
            throw e;
        }
    }

    //신규,변경 개통 전 사전체크는 ServiceName 동일, Operation Name 상이로 분리함.
    public MspPrxSoapResponse callXmlOsstServiceNewChange(List<Object> list, MplatformOsstServiceType appEventCd, String mvnoOrdNo) {
        Map<String, String> map = StringUtils.hasText(mvnoOrdNo)
            ? Map.of("mvnoOrdNo", mvnoOrdNo, "appEventCd", appEventCd.getEventCd())
            : Map.of("appEventCd", appEventCd.getEventCd());
        ServiceAlterTraceRequest trace = ServiceAlterTraceRequest.builder().eventCd(appEventCd.getEventCd()).build();
        try {
            MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.createXmlRequest(list,
                MplatformOsstServiceType.findByEventCd(appEventCd),
                trace);
            MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callXmlOsstService(mspPrxFormRequest);
            msfOsstHistoryService.saveResponse(map, mspPrxSoapResponse);
            return mspPrxSoapResponse;
        } catch (Exception e) {
            msfOsstHistoryService.saveFailure(map, e);
            throw e;
        }
    }

    public MspPrxSoapResponse callXmlOsstService(List<Object> list, String appEventCd, Map<String, String> historyParams) {
        ServiceAlterTraceRequest trace = ServiceAlterTraceRequest.builder().eventCd(appEventCd).build();
        try {
            MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.createXmlRequest(list, MplatformOsstServiceType.findByEventCd(appEventCd), trace);
            MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callXmlOsstService(mspPrxFormRequest);
            msfOsstHistoryService.saveResponse(historyParams, mspPrxSoapResponse);
            return mspPrxSoapResponse;
        } catch (Exception e) {
            msfOsstHistoryService.saveFailure(historyParams, e);
            throw e;
        }
    }

    public MspPrxSoapResponse callXmlOsstServiceWithParams(Map<String, String> params, String appEventCd, String mvnoOrdNo) {
        String validMvnoOrdNo = StringUtils.hasText(mvnoOrdNo) ? mvnoOrdNo : "";
        Map<String, String> historyKey = Map.of("mvnoOrdNo", validMvnoOrdNo, "appEventCd", appEventCd);
        ServiceAlterTraceRequest trace = ServiceAlterTraceRequest.builder().eventCd(appEventCd).build();
        try {
            MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.builder()
                .parameters(params)
                .serviceAlterTrace(trace)
                .build();
            MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callXmlOsstService(mspPrxFormRequest);
            msfOsstHistoryService.saveResponse(historyKey, mspPrxSoapResponse);
            return mspPrxSoapResponse;
        } catch (Exception e) {
            msfOsstHistoryService.saveFailure(historyKey, e);
            throw e;
        }
    }

    private MspPrxSoapResponse callWithTimeout(Supplier<MspPrxSoapResponse> supplier, int timeout)
        throws SocketTimeoutException {
        if (timeout <= 0) {
            return supplier.get();
        }

        CompletableFuture<MspPrxSoapResponse> future = CompletableFuture.supplyAsync(supplier);
        try {
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            SocketTimeoutException timeoutException = new SocketTimeoutException("PRX call timeout: " + timeout + "ms");
            timeoutException.initCause(e);
            throw timeoutException;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            SocketTimeoutException timeoutException = new SocketTimeoutException("PRX call interrupted");
            timeoutException.initCause(e);
            throw timeoutException;
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            if (cause instanceof Error error) {
                throw error;
            }
            throw new IllegalStateException(cause);
        }

    }

    public Map<String, String> createMapData(List<Object> list, MplatformServiceType serviceType, MplatFormXmlSelfcareRequest request) {

        StringBuilder xml = new StringBuilder();

        if (!CollectionUtils.isEmpty(list)) {
            for (Object obj: list) {
                xml.append(XmlConvertUtils.convertObjectToXml(obj));
            }
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("xml", xml.toString());
        paramMap.put("appEventCd", serviceType.getEventCd());
        paramMap.put("serviceName", serviceType.getServiceName());
        paramMap.put("serviceInfo", serviceType.getServiceInfo());
        paramMap.put("serviceVo", serviceType.getServiceVo());
        paramMap.put("selfCareInYn", serviceType.getSelfCareInYn());
        paramMap.put("encryptYn", serviceType.getEncryptYn());
        paramMap.put("prefix", serviceType.getPrefix());
        paramMap.put("appAgncCd", request.getAppAgncCd());
        paramMap.put("appNstepUserId", request.getAppAgncCd());
        paramMap.put("appEntrPrsnId", request.getAppAgncCd());
        paramMap.put("custId", request.getCustId());
        paramMap.put("ctn", request.getCtn());
        paramMap.put("ncn", request.getNcn());

        return paramMap;
    }
}
