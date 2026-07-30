package com.ktmmobile.msf.domains.form.common.mplatform.provider;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlNoSelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;

public interface MplatFormResponseProvider {

    boolean callService(HashMap<String, String> param, CommonXmlVO vo, int mockNo)
        throws SelfServiceException, SocketTimeoutException;

    boolean callService(HashMap<String, String> param, CommonXmlVO vo, int timeout, int mockNo)
        throws SelfServiceException, SocketTimeoutException;

    boolean callServiceNe(HashMap<String, String> param, CommonXmlNoSelfServiceException vo, int timeout, int mockNo)
        throws SelfServiceException, SocketTimeoutException;

    boolean callServiceEtc(HashMap<String, String> param, CommonXmlVO vo, int timeout, String mockEventCd)
        throws SelfServiceException, SocketTimeoutException;
}
