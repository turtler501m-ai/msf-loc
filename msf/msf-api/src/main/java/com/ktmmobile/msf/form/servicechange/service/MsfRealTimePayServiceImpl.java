package com.ktmmobile.msf.form.servicechange.service;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.net.SocketTimeoutException;


import com.ktmmobile.msf.system.common.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.system.common.dto.MoscPymnReqDto;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.system.common.mplatform.vo.MpFarPaymentInfoVO;
import com.ktmmobile.msf.system.common.mplatform.vo.PaymentInfoVO;

import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;

@Service
public class MsfRealTimePayServiceImpl implements MsfRealTimePayService{

    private static final Logger logger = LoggerFactory.getLogger(MsfRealTimePayServiceImpl.class);


    @Autowired
    MsfMplatFormService mplatFormService;

    /**
     * x92 당월요금+미납요금 조회(X92)
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     */

    @Override
    public PaymentInfoVO moscCurrMthNpayInfo(String ncn, String ctn, String custId) {
        PaymentInfoVO farPaymentInfoVO = new PaymentInfoVO();

        try {
            farPaymentInfoVO = mplatFormService.moscCurrMthNpayInfo(ncn, ctn, custId);
        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        } catch (SocketTimeoutException e){
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        }

        return farPaymentInfoVO;
    }

    /**
     *  x22 납부/미납요금 조회 연동 규격
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     */

    @Override
    public MpFarPaymentInfoVO farPaymentInfoList(String ncn, String ctn, String custId,String startDate,String endDate) throws SocketTimeoutException {

        MpFarPaymentInfoVO farPaymentInfoVO = new MpFarPaymentInfoVO();
        farPaymentInfoVO = mplatFormService.farPaymentInfo(ncn, ctn, custId,startDate,endDate);
        return farPaymentInfoVO;
    }

    /**
     * x67 미납요금 월별 조회(X67)
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     */

    @Override
    public PaymentInfoVO farPaymentInfo(String ncn, String ctn, String custId) {

        PaymentInfoVO farPaymentInfoVO = new PaymentInfoVO();

        try {
            farPaymentInfoVO = mplatFormService.moscPymnMonthInfo(ncn, ctn, custId);
        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        } catch (SocketTimeoutException e){
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        }

        return farPaymentInfoVO;
    }

    /**
     * x68 미납요금 즉시수납처리
     * @author bsj
     * @Date : 2021.12.30
     * @param moscPymnReqDto
     * @return
     */

    @Override
    public String moscPymnTreat(MoscPymnReqDto moscPymnReqDto) {
        PaymentInfoVO resultUrlDto= new PaymentInfoVO();
        String url = "";

        try {
            resultUrlDto = mplatFormService.moscPymnTreat(moscPymnReqDto);

            //간편결제일때 url이 호출되는건지?
            //일반신용카드 결제와 응답하는게 다른지 확인
            //간편결재일때
            if("P".equals(moscPymnReqDto.getBlMethod())) {
                if(resultUrlDto.getUrl() == null) {
                      throw new McpCommonException(COMMON_EXCEPTION);
                } else {
                    url = resultUrlDto.getUrl();
                }
            }

            if(!resultUrlDto.isSuccess()) {
                  throw new McpCommonException(COMMON_EXCEPTION);
            }

        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        } catch (SocketTimeoutException e){
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        }

        return url;
    }

    /**
     * x93  당월요금+미납요금 조회(X93)
     * @author bsj
     * @Date : 2021.12.30
     * @param moscPymnReqDto
     * @return
     *
     */

    @Override
    public String moscCurrMthNpayTreat(MoscPymnReqDto moscPymnReqDto) {
        PaymentInfoVO resultUrlDto= new PaymentInfoVO();
        String url = "";

        try {
            //x93 납부
            resultUrlDto = mplatFormService.moscCurrMthNpayTreat(moscPymnReqDto);

            //간편결제일때 url이 호출되는건지?
            //일반신용카드 결제와 응답하는게 다른지 확인
            //간편결재일때
            if("P".equals(moscPymnReqDto.getBlMethod())) {
                if(resultUrlDto.getUrl() == null) {
                      throw new McpCommonException(COMMON_EXCEPTION);
                } else {
                    url = resultUrlDto.getUrl();
                }
            }

            if(!resultUrlDto.isSuccess()) {
                  throw new McpCommonException(COMMON_EXCEPTION);
            }

        } catch (SelfServiceException e) {
            throw e ;
        } catch (SocketTimeoutException e){
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        }

        return url;
    }
}
