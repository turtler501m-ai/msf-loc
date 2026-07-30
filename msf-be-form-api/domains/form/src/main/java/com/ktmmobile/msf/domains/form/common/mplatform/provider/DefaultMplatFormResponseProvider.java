package com.ktmmobile.msf.domains.form.common.mplatform.provider;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormServerAdapter;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlNoSelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;

@Component
@RequiredArgsConstructor
public class DefaultMplatFormResponseProvider implements MplatFormResponseProvider {

    private final MsfMplatFormServerAdapter mplatFormServerAdapter;
    private final MplatFormMockResponseProvider mockResponseProvider;

    @Value("${LOCAL_TEST:false}")
    private boolean localTest;

    @Override
    public boolean callService(HashMap<String, String> param, CommonXmlVO vo, int mockNo)
        throws SelfServiceException, SocketTimeoutException {
        return callService(param, vo, 30000, mockNo);
    }

    @Override
    public boolean callService(HashMap<String, String> param, CommonXmlVO vo, int timeout, int mockNo)
        throws SelfServiceException, SocketTimeoutException {
        if (localTest) {
            return mockResponseProvider.getVo(mockNo, vo);
        }
        return mplatFormServerAdapter.callService(param, vo, timeout);
    }

    @Override
    public boolean callServiceNe(HashMap<String, String> param, CommonXmlNoSelfServiceException vo, int timeout, int mockNo)
        throws SelfServiceException, SocketTimeoutException {
        if (localTest) {
            return mockResponseProvider.getVoNe(mockNo, vo);
        }
        return mplatFormServerAdapter.callServiceNe(param, vo, timeout);
    }

    @Override
    public boolean callServiceEtc(HashMap<String, String> param, CommonXmlVO vo, int timeout, String mockEventCd)
        throws SelfServiceException, SocketTimeoutException {
        if (localTest) {
            return mockResponseProvider.getVoEtc(mockEventCd, vo);
        }
        return mplatFormServerAdapter.callService(param, vo, timeout);
    }
}
