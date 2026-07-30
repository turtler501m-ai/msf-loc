package com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out;

import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxJsonRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;

public interface MspPrxClient {

    MspPrxSoapResponse callService(MspPrxFormRequest request);

    MspPrxSoapResponse callServiceJson(MspPrxJsonRequest request);

    MspPrxSoapResponse callOsstService(MspPrxFormRequest request);

    MspPrxSoapResponse callSimpleOpenService(MspPrxFormRequest request);

    MspPrxSoapResponse callXmlOsstService(MspPrxFormRequest request);

    MspPrxSoapResponse callXmlSelfService(MspPrxFormRequest request);
}
