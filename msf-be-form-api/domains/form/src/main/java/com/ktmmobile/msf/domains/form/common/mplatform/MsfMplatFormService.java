package com.ktmmobile.msf.domains.form.common.mplatform;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.dto.MoscCombChkReqDto;
import com.ktmmobile.msf.domains.form.common.dto.MoscCombReqDto;
import com.ktmmobile.msf.domains.form.common.dto.MoscPymnReqDto;
import com.ktmmobile.msf.domains.form.common.dto.MoscRemindSmsDto;
import com.ktmmobile.msf.domains.form.common.dto.NowDlvryReqDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.CommCdInfoRes;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.CoupInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.InqrCoupInfoRes;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.InqrUsedCoupListRes;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombChkRes;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombChkResDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombDtlResDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombInfoResDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCrdtCardAthnInDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscDataSharingResDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscFarPriceChgDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscFarPriceResDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscInqrUsimUsePsblOutDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscMvnoComInfo;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscOtpSvcInfoRes;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscSimplePaySmsRes;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscSubMstCombChgRes;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscWireUseTimeInfoRes;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpVirtualAccountNoListDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.NowDlvryResDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.RegSvcChgRes;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.TrtmCoupUseRes;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlNoSelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscBfacChkOmdIntmVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscBillReSendChgOutVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscBillReSendInfoOutVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscBillSendInfoOutVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscCombStatMgmtInfoOutVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscRetvIntmMdlSpecInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscRetvIntmOrrgInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscTrtOmdIntmVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpAddSvcInfoParamDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpBilEmailBillReqInfo;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpBilEmailChgVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpBilPrintInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpCommonXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpCustInfoAgreeVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarChgWayChgVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarMonBillingInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarMonBillingInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarMonDetailInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarMonDetailInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarPaymentInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarPriceInfoDetailItemVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscBillChgInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscRegSvcCanChgInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscSdsInfoVo;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscSdsSvcPreChkVo;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscSpnsrItgInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpNumChgeChgVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpNumChgeListVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpPcsLostChgVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpPcsLostCnlChgVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpPcsLostInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpRemindSmsVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSuspenChgVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSuspenCnlChgInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSuspenCnlPosInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSuspenPosHisVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSuspenPosInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSvcContIpinVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpTelTotalUseTimeDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpTelTotalUseTimeMobileDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpTelTotalUseTimeMobileVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpTelTotalUseTimeVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpUsimPukVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpVoidTypeVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.PaymentInfoVO;
import com.ktmmobile.msf.domains.form.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.service.FCommonSvc;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.ObjectUtils;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.newchange.dto.EsimDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.JuoSubInfoDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMypageSvc;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;

@Service
public class MsfMplatFormService {

    private static final Logger logger = LoggerFactory.getLogger(MsfMplatFormService.class);

    @Lazy
    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private MsfMypageSvc msfMypageSvc;

    @Autowired
    private MsfMplatFormServerAdapter mplatFormServerAdapter;

    @Value("${SERVER_NAME}")
    private String serverLocation;

    /**
     * 1.가입정보조회 연동 규격   woo
     *
     * @param  ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param  ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpPerMyktfInfoVO perMyktfInfo(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        MpPerMyktfInfoVO vo = new MpPerMyktfInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X01");
        if ("LOCAL".equals(serverLocation)) {
            getVo(1, vo);
            //mplatFormServerAdapter.callService(param, vo, 3000);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 2. 청구지주소변경
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public void perAddrChg(
        String ncn, String ctn, String custId,
        String addrZip, String adrPrimaryLn, String adrSecondaryLn, String bilCtnDisplay
    ) throws SelfServiceException, SocketTimeoutException {
        MpVoidTypeVO vo = new MpVoidTypeVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X02");
        param.put("addrZip", StringUtil.NVL(addrZip, ""));
        param.put("adrPrimaryLn", StringUtil.NVL(adrPrimaryLn, ""));
        param.put("adrSecondaryLn", StringUtil.NVL(adrSecondaryLn, ""));
        param.put("bilCtnDisplay", StringUtil.NVL(bilCtnDisplay, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(2, vo);
            //            mplatFormServerAdapter.callService(param, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
    }

    /**
     * 3. e-mail청구서 조회
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpBilEmailBillReqInfo bilEmailBillReqInfo(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        MpBilEmailBillReqInfo vo = new MpBilEmailBillReqInfo();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X03");
        if ("LOCAL".equals(serverLocation)) {
            getVo(3, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 4. e-mail청구서 신청/변경/해지
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String status				: 기능구분코드(1:신청, 9:변경, 0:해지)
     * @param String email				: 발송이메일
     * @param String securMailYn	: 보안메일 여부(Y ,N)
     * @param String ecRcvAgreYn	: 이벤트 수신동의 여부(Y, N)
     * @param String sendGubun				: 청구서 발송 여부(1:발송, 2:발송제외, 3:해지할떄)
     * @return
     * @throws
     */
    public MpBilEmailChgVO bilEmailChg(
        String ncn, String ctn, String custId,
        String status, String email, String securMailYn,
        String ecRcvAgreYn, String sendGubun
    ) throws SelfServiceException, SocketTimeoutException {
        MpBilEmailChgVO vo = new MpBilEmailChgVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X04");
        param.put("status", StringUtil.NVL(status, ""));
        param.put("email", StringUtil.NVL(email, ""));
        param.put("securMailYn", StringUtil.NVL(securMailYn, ""));
        param.put("ecRcvAgreYn", StringUtil.NVL(ecRcvAgreYn, ""));
        param.put("sendGubun", StringUtil.NVL(sendGubun, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(4, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 9. e-mail청구서재발송처리
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String year				: 재발송 년도
     * @param String month				: 재발송 월
     * @param String securMailYn	: 보안메일 여부(Y ,N)
     * @param String causeCode	: 재발송 사유 코드 (1:단순재발행 ,2:이메일명세서 주소변경 ,3:미수신 ,4:기타)
     * @param String email				: 발송이메일
     * @return
     * @throws
     */
    public void bilResendChgChge(
        String ncn, String ctn, String custId,
        String year, String month, String securMailYn,
        String causeCode, String email
    ) throws SelfServiceException, SocketTimeoutException {
        MpVoidTypeVO vo = new MpVoidTypeVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X09");
        param.put("year", StringUtil.NVL(year, ""));
        param.put("month", StringUtil.NVL(month, ""));
        param.put("securMailYn", StringUtil.NVL(securMailYn, ""));
        param.put("causeCode", StringUtil.NVL(causeCode, ""));
        param.put("email", StringUtil.NVL(email, ""));
        mplatFormServerAdapter.callService(param, vo);
    }

    /**
     * 10. 종이청구서조회
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpBilPrintInfoVO bilPrintInfo(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        MpBilPrintInfoVO vo = new MpBilPrintInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X10");
        getVo(10, vo);

        if ("LOCAL".equals(serverLocation)) {
            getVo(10, vo);
            //            mplatFormServerAdapter.callService(param, vo, 3000);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 11. 종이명세서 재발행
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public void bilReprintInfo(String ncn, String ctn, String custId, String billDate) throws SelfServiceException, SocketTimeoutException {
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X11");
        param.put("billDate", StringUtil.NVL(billDate, ""));
        mplatFormServerAdapter.callService(param, null);
    }

    /**
     * tobe 추가 테스트가 필요한 사항
     * 12. 총 통화 시간 조회
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpTelTotalUseTimeVO telTotalUseTime(String ncn, String ctn, String custId, String useMonth)
        throws SelfServiceException, SocketTimeoutException {
        MpTelTotalUseTimeVO vo = new MpTelTotalUseTimeVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X12");
        if (useMonth != null) {
            param.put("useMonth", useMonth);
        }
        if ("LOCAL".equals(serverLocation)) {
            getVo(12, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }


    /**
     * tobe 추가 테스트가 필요한 사항`
     * 12. 총 통화 시간 조회
     *
     * @param useMonth
     * @param String   ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String   ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpTelTotalUseTimeDto telTotalUseTimeDto(String ncn, String ctn, String custId, String useMonth)
        throws SelfServiceException, SocketTimeoutException {
        MpTelTotalUseTimeDto vo = new MpTelTotalUseTimeDto();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X12");
        if (useMonth != null) {
            param.put("useMonth", useMonth);
        }
        if ("LOCAL".equals(serverLocation)) {
            getVo(12, vo);
            //mplatFormServerAdapter.callService(param, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 15. 요금 조회 연동
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpFarMonBillingInfoVO farMonBillingInfo(String ncn, String ctn, String custId, String productionDate)
        throws SelfServiceException, SocketTimeoutException {
        MpFarMonBillingInfoVO vo = new MpFarMonBillingInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X15");
        param.put("productionDate", StringUtil.NVL(productionDate, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(15, vo);
            //mplatFormServerAdapter.callService(param, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    public MpFarMonBillingInfoDto farMonBillingInfoDto(String ncn, String ctn, String custId, String productionDate)
        throws SelfServiceException, SocketTimeoutException {
        MpFarMonBillingInfoDto vo = new MpFarMonBillingInfoDto();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X15");
        param.put("productionDate", StringUtil.NVL(productionDate, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(15, vo);
            //            mplatFormServerAdapter.callService(param, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 16. 요금 상세 조회 연동
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String billSeqNo : 청구번호
     * @param String billDueDateList : 조회년월
     * @param String billMonth : 청구월
     * @param String billStartDate : 청구시작일
     * @param String billEndDate : 청구종료일
     * @return
     * @throws
     */
    public MpFarMonDetailInfoVO farMonDetailInfo(
        String ncn, String ctn, String custId,
        String billSeqNo, String billDueDateList, String billMonth,
        String billStartDate, String billEndDate
    ) throws SocketTimeoutException {
        MpFarMonDetailInfoVO vo = new MpFarMonDetailInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X16");
        param.put("billSeqNo", StringUtil.NVL(billSeqNo, ""));
        param.put("billDueDateList", StringUtil.NVL(billDueDateList, ""));
        param.put("billMonth", StringUtil.NVL(billMonth, ""));
        param.put("billStartDate", StringUtil.NVL(billStartDate, ""));
        param.put("billEndDate", StringUtil.NVL(billEndDate, ""));

        if ("LOCAL".equals(serverLocation)) {
            getVo(16, vo);
            //mplatFormServerAdapter.callService(param, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }

        return vo;
    }


    public MpFarMonDetailInfoDto farMonDetailInfoDto(
        String ncn, String ctn, String custId,
        String billSeqNo, String billDueDateList, String billMonth,
        String billStartDate, String billEndDate
    ) throws SocketTimeoutException {
        MpFarMonDetailInfoDto vo = new MpFarMonDetailInfoDto();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X16");
        param.put("billSeqNo", StringUtil.NVL(billSeqNo, ""));
        param.put("billDueDateList", StringUtil.NVL(billDueDateList, ""));
        param.put("billMonth", StringUtil.NVL(billMonth, ""));
        param.put("billStartDate", StringUtil.NVL(billStartDate, ""));
        param.put("billEndDate", StringUtil.NVL(billEndDate, ""));

        if ("LOCAL".equals(serverLocation)) {
            getVo(16, vo);
            //            mplatFormServerAdapter.callService(param, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }

        return vo;
    }


    /**
     * 17. 요금 항목별 조회 연동
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String billSeqNo : 청구번호
     * @param String billMonth : 청구월
     * @param String messageLine : 청구항목코드
     * @return
     * @throws
     */
    public MpFarPriceInfoDetailItemVO farPriceInfoDetailItem(
        String ncn, String ctn, String custId,
        String billSeqNo, String billMonth, String messageLine
    ) throws SocketTimeoutException {
        MpFarPriceInfoDetailItemVO vo = new MpFarPriceInfoDetailItemVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X17");
        param.put("billSeqNo", StringUtil.NVL(billSeqNo, ""));
        param.put("billMonth", StringUtil.NVL(billMonth, ""));
        param.put("messageLine", StringUtil.NVL(messageLine, ""));

        if ("LOCAL".equals(serverLocation)) {
            if (param.get("messageLine").equals("DCNOR")) {//할인전부가세
                //                getVo(171, vo);
                mplatFormServerAdapter.callService(param, vo);
            } else if (param.get("messageLine").equals("PY000")) {//소액결제
                //                getVo(172, vo);
                mplatFormServerAdapter.callService(param, vo);
            } else if (param.get("messageLine").equals("MA000")) {
                //                getVo(173, vo);
                mplatFormServerAdapter.callService(param, vo);
            } else {
                //                getVo(17, vo);
                mplatFormServerAdapter.callService(param, vo);
            }
            //

        } else {
            mplatFormServerAdapter.callService(param, vo);
        }

        return vo;
    }


    /**
     * 18. 실시간 요금조회
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpFarRealtimePayInfoVO farRealtimePayInfo(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        MpFarRealtimePayInfoVO vo = new MpFarRealtimePayInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X18");
        logger.debug("X18 farRealtimePayInfo callService start. ncn={}, ctn={}, custId={}, serverLocation={}",
            ncn, ctn, custId, serverLocation);

        //if ("LOCAL".equals(serverLocation) || (serverLocation != null && serverLocation.startsWith("LOCAL"))) {
        //    getVo(18, vo);
            //mplatFormServerAdapter.callService(param, vo);
        //} else {
            mplatFormServerAdapter.callService(param, vo);
        //}
        int listSize = vo.getList() == null ? 0 : vo.getList().size();
        logger.debug("X18 farRealtimePayInfo callService done. success={}, searchDay={}, searchTime={}, sumAmt={}, listSize={}",
            vo.isSuccess(), vo.getSearchDay(), vo.getSearchTime(), vo.getSumAmt(), listSize);

        return vo;
    }

    /**
     * tobe : sr
     * x19
     * 19. 요금 상품 변경
     * 이력 저장 처리 추가
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String soc : SOC 코드
     * @param String nowPriceSocCode : 로그 저장용 현재 socCode
     * @param String prcsMdlInd : 로그 저장용 구분자
     * @return
     * @throws
     */
    public RegSvcChgRes farPricePlanChgNeTrace(MyPageSearchDto searchVO, String soc) {
        RegSvcChgRes vo = new RegSvcChgRes();


        try {
            HashMap<String, String> param = getParamMap(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), "X19");
            param.put("soc", StringUtil.NVL(soc, ""));
            if ("LOCAL".equals(serverLocation)) {
                getVoNe(19, vo);
                //adapter.callServiceNe(param, vo,30000);
            } else {
                mplatFormServerAdapter.callServiceNe(param, vo, 30000);
            }


        } catch (SocketTimeoutException e) {
            vo.setResultCode("9999");
            vo.setSvcMsg(SOCKET_TIMEOUT_EXCEPTION);
        } catch (Exception e) {
            vo.setResultCode("9998");
            vo.setSvcMsg(e.getMessage());
        }

        return vo;
    }

    /**
     * 20. 부가서비스 조회 연동 규격
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return java.util.HashMap`
     * - ResultCode : SUCCESS
     * - SOC_DESCRIPTION : 부가서비스명
     * - SOC_RATE_VALUE : 이용요금
     * - EFFECTIVE_DATE : 신청일자
     * @exception
     */
    //    public MpAddSvcInfoVO getAddSvcInfo(String ncn, String ctn, String custId) throws SelfServiceException,SocketTimeoutException{
    //        MpAddSvcInfoVO vo = new MpAddSvcInfoVO();
    //        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X20");
    //        if("LOCAL".equals(serverLocation)) {
    //            getVo(20, vo);
    //        } else {
    //            mplatFormServerAdapter.callService(param, vo);
    //        }
    //        return vo;
    //    }

    /**
     * x20 부가서비스 조회 연동 규격
     *
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     * @throws SelfServiceException
     * @throws SocketTimeoutException
     */

    public MpAddSvcInfoDto getAddSvcInfoDto(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        MpAddSvcInfoDto vo = new MpAddSvcInfoDto();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X20");

        if ("LOCAL".equals(serverLocation)) {
            getVo(20, vo);
            //mplatFormServerAdapter.callService(param, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }

        return vo;
    }

    /**
     * 21. 부가서비스신청
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String soc : SOC 코드
     * @param String ftrNewParam : 부가정보가 있는경우 부가정보
     * @return
     * @throws
     */
    public MpRegSvcChgVO regSvcChg(
        String ncn, String ctn, String custId,
        String soc, String ftrNewParam
    ) throws SocketTimeoutException {
        MpRegSvcChgVO vo = new MpRegSvcChgVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X21");
        param.put("soc", StringUtil.NVL(soc, ""));
        param.put("ftrNewParam", StringUtil.NVL(ftrNewParam, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(21, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * Y25 - 상품변경처리(multi)
     *
     * [TOBE 추가 2026-04-03] ASIS X21(부가서비스신청) 대체 코드.
     *
     * X21과의 차이:
     *   X21 — 단건 부가서비스 신청 (단순 SOC 1개 처리)
     *   Y25 — 상품변경처리(multi): 복수 SOC 처리 지원, 선/후처리 조합 가능
     *          IF 설계서 기준 TOBE 표준 코드 (X21 대체 확정)
     *
     * LOCAL 환경: getVo(25, vo) 목업 응답 반환 (실제 M플랫폼 호출 없음)
     * 운영 환경: mplatFormServerAdapter.callService() → 30초 타임아웃
     *
     * @param ncn         서비스 계약번호 9자리 [-]제외
     * @param ctn         전화번호 11자리 (10자리면 앞에 0 추가)
     * @param custId      고객번호
     * @param soc         SOC 코드 (신청할 부가서비스)
     * @param ftrNewParam 부가정보 (추가 옵션이 있는 경우, 없으면 공백)
     * @return MpRegSvcChgVO (success/resultCode/message)
     * @throws SocketTimeoutException 30초 타임아웃 초과 시
     */
    public MpRegSvcChgVO regSvcChgY25(
        String ncn, String ctn, String custId,
        String soc, String ftrNewParam
    ) throws SocketTimeoutException {
        MpRegSvcChgVO vo = new MpRegSvcChgVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "Y25");
        param.put("soc", StringUtil.NVL(soc, ""));
        param.put("ftrNewParam", StringUtil.NVL(ftrNewParam, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(25, vo); // LOCAL 목업 — case 25 응답 사용
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * 21. 부가서비스신청
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String soc : SOC 코드
     * @param String ftrNewParam : 부가정보가 있는경우 부가정보
     * @return
     * @throws
     */
    public RegSvcChgRes regSvcChgNe(String ncn, String ctn, String custId, String soc, String ftrNewParam) throws SocketTimeoutException {
        RegSvcChgRes vo = new RegSvcChgRes();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X21");
        param.put("soc", StringUtil.NVL(soc, ""));
        param.put("ftrNewParam", StringUtil.NVL(ftrNewParam, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVoNe(21, vo);
        } else {
            mplatFormServerAdapter.callServiceNe(param, vo, 30000);
        }
        return vo;
    }


    /**
     * 요금제 변경후에 -> 부가서비스 가입할때
     * <p>
     * tobe sr머지
     * 21. 부가서비스신청
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String soc : SOC 코드
     * @param String ftrNewParam : 부가정보가 있는경우 부가정보
     * @return
     * @throws
     */
    public RegSvcChgRes regSvcChgNeTrace(MyPageSearchDto searchVO, String soc, String ftrNewParam) {
        RegSvcChgRes vo = new RegSvcChgRes();

        try {
            HashMap<String, String> param = getParamMap(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), "X21");
            param.put("soc", StringUtil.NVL(soc, ""));
            param.put("ftrNewParam", StringUtil.NVL(ftrNewParam, ""));


            if ("LOCAL".equals(serverLocation)) {
                getVoNe(21, vo);
            } else {
                mplatFormServerAdapter.callServiceNe(param, vo, 30000);
            }


        } catch (SocketTimeoutException e) {
            vo.setResultCode("9999");
            vo.setSvcMsg(SOCKET_TIMEOUT_EXCEPTION);
        } catch (Exception e) {
            vo.setResultCode("9998");
            vo.setSvcMsg(e.getMessage());
        }

        return vo;
    }

    /**
     * 21. 부가서비스신청
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String soc : SOC 코드
     * @param String ftrNewParam : 부가정보가 있는경우 부가정보
     * @return
     * @exception
     */
    //    public RegSvcChgRes regSvcChgNe(String ncn, String ctn, String custId,
    //            String soc, String ftrNewParam) throws SocketTimeoutException {
    //        RegSvcChgRes vo = new RegSvcChgRes();
    //        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X21");
    //        param.put("soc", StringUtil.NVL(soc, ""));
    //        param.put("ftrNewParam", StringUtil.NVL(ftrNewParam,""));
    //        if("LOCAL".equals(serverLocation)) {
    //            getVoNe(21, vo);
    //        } else {
    //            mplatFormServerAdapter.callServiceNe(param, vo,30000);
    //        }
    //
    //        return vo;
    //    }


    /**
     * 22. 납부/미납요금 조회 연동 규격
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpFarPaymentInfoVO farPaymentInfo(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        MpFarPaymentInfoVO vo = new MpFarPaymentInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X22");
        if ("LOCAL".equals(serverLocation)) {
            getVo(22, vo);
            //            mplatFormServerAdapter.callService(param, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 22. 납부/미납요금 조회 연동 규격
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpFarPaymentInfoVO farPaymentInfo(String ncn, String ctn, String custId, String startDate, String endDate)
        throws SelfServiceException, SocketTimeoutException {
        MpFarPaymentInfoVO vo = new MpFarPaymentInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X22");
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        if ("LOCAL".equals(serverLocation)) {
            getVo(22, vo);
            //            mplatFormServerAdapter.callService(param, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * tobe 추가사항
     * x67 미납요금 월별 조회(X67)
     *
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     * @throws SelfServiceException
     * @throws SocketTimeoutException
     */
    public PaymentInfoVO moscPymnMonthInfo(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {

        PaymentInfoVO vo = new PaymentInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X67");
        if ("LOCAL".equals(serverLocation)) {
            getVo(67, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }

        return vo;

    }

    /**
     * x92 당월요금+미납요금 조회(X92)
     *
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     * @throws SelfServiceException
     * @throws SocketTimeoutException
     */
    public PaymentInfoVO moscCurrMthNpayInfo(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {

        PaymentInfoVO vo = new PaymentInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X92");
        if ("LOCAL".equals(serverLocation)) {
            getVo(92, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }

        return vo;
    }

    /**
     * x93 당월요금+미납요금 조회(X93)
     *
     * @param moscPymnReqDto
     * @return
     * @throws SocketTimeoutException
     */

    public PaymentInfoVO moscCurrMthNpayTreat(MoscPymnReqDto moscPymnReqDto) throws SocketTimeoutException {
        PaymentInfoVO vo = new PaymentInfoVO();

        HashMap<String, String> param = getParamMap(moscPymnReqDto.getNcn(), moscPymnReqDto.getCtn(), moscPymnReqDto.getCustId(), "X93");
        param.put("payMentMoney", moscPymnReqDto.getPayMentMoney()); //수납금액
        param.put("blMethod", moscPymnReqDto.getBlMethod()); //수납방법

        if ("D".equals(moscPymnReqDto.getBlMethod())) {//수납방법 D:실시간계좌이체
            param.put("bankAcctNo", moscPymnReqDto.getBankAcctNo()); //계좌번호
            param.put("blBankCode", moscPymnReqDto.getBlBankCode()); //은행코드
            param.put("agrDivCd", moscPymnReqDto.getAgrDivCd()); //동의유형
            param.put("myslfAthnTypeItgCd", moscPymnReqDto.getMyslfAthnTypeItgCd()); //본인인증유형
        } else if ("C".contentEquals(moscPymnReqDto.getBlMethod())) { //신용카드 C
            param.put("cardNo", moscPymnReqDto.getCardNo()); //카드번호
            param.put("cardExpirDate", moscPymnReqDto.getCardExpirDate()); //카드유효기간 YYYMM
            param.put("cardPwd", moscPymnReqDto.getCardPwd());//카드비밀번호 앞 두자리
            param.put("cardInstMnthCnt", moscPymnReqDto.getCardInstMnthCnt());//5만원 미만일 경우 일시불만 가능, 할부시 최대 36개월 ex 01,02
        } else if ("P".equals(moscPymnReqDto.getBlMethod())) { //간편결재
            param.put("rmnyChId", moscPymnReqDto.getRmnyChId()); //카카오페이:KA, 페이코:PY, 네이버페이:NP
        }

        logger.info("[WOO][WOO][WOO]==>" + ObjectUtils.convertObjectToString(param));

        if ("LOCAL".equals(serverLocation)) {
            getVo(93, vo);
            //mplatFormServerAdapter.callService(param, vo, 30000);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;

    }


    /**
     * 23. 현재 납부 방법 연동
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpFarChangewayInfoVO farChangewayInfo(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        MpFarChangewayInfoVO vo = new MpFarChangewayInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X23");
        if ("LOCAL".equals(serverLocation)) {
            getVo(23, vo);
            //            mplatFormServerAdapter.callService(param, vo, 3000);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * tobe 추가 테스트가 필요한 경우
     * 25. 납부방법 변경
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String nextBlMethod   	다음납부방법(공통)	S	C	1	M	C: 카드, D: 자동이체, R: 지로
     * @param String nextBlBankCode 	다음납부방법 은행코드 (계좌이체 시)	S	C	10	C	0020000 산업은행 0030000 기업은행 0040000 국민은행..(개통코드표참조)
     * @param String nextCycleDueDay	자동이체납부일, 납기일자 코드 (공통)	S	C	2	M	21 매월21일/25 매월25일/27 매월27일/99 매월말일  (각 사업자별 카드, 계좌이체, 지로별 납기일자 셋팅 필요)
     * @param String nextBankAcctNo		다음납부방법 계좌번호  (계좌이체 시)	S	C	40	C	번호
     * @param String nextCardExpirDate 	카드유효기간 년월 (카드)	S	C	6	O	예)201712
     * @param String nextCreditCardNo	다음납부방법 신용카드번호 (카드)	S	C	40	C	번호
     * @param String nextcardCode  		다음납부방법 신용카드종류 (카드)	S	C	10	C	BM BC M.COM카드,BK KTF맴버스 BC카드,LK KTF멤버스LG카드,NH NH카드…(개통코드표참조)
     * @param String blpymTmsIndCd 		신용카드 회차 정보(카드)	S	C	2	C	‘01’ or ’02’ 1 or 2 회차 (각사업자별 가능한 회차 지정)
     * @param String adrZip        		우편번호 (지로)	S	C	6	C	우편번호
     * @param String adrPrimaryLn   	기본주소 (지로)	S	V	200	C	메인주소
     * @param String adrSecondaryLn		상세주소 (지로)	S	V	200	C	상세주소
     * @return
     * @throws
     */
    public MpFarChgWayChgVO farChgWayChg(
        String ncn, String ctn, String custId,
        Map<String, String> map
    ) throws SocketTimeoutException {
        MpFarChgWayChgVO vo = new MpFarChgWayChgVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X25");
        param.put("nextBlMethod", StringUtil.NVL(map.get("nextBlMethod"), ""));
        param.put("nextBlBankCode", StringUtil.NVL(map.get("nextBlBankCode"), ""));
        param.put("nextCycleDueDay", StringUtil.NVL(map.get("nextCycleDueDay"), ""));
        param.put("nextBankAcctNo", StringUtil.NVL(map.get("nextBankAcctNo"), ""));
        param.put("nextCardExpirDate", StringUtil.NVL(map.get("nextCardExpirDate"), ""));
        param.put("nextCreditCardNo", StringUtil.NVL(map.get("nextCreditCardNo"), ""));
        param.put("nextCardCode", StringUtil.NVL(map.get("nextCardCode"), ""));
        param.put("blpymTmsIndCd", StringUtil.NVL(map.get("blpymTmsIndCd"), ""));
        param.put("adrZip", StringUtil.NVL(map.get("adrZip"), ""));
        param.put("adrPrimaryLn", StringUtil.NVL(map.get("adrPrimaryLn"), ""));
        param.put("adrSecondaryLn", StringUtil.NVL(map.get("adrSecondaryLn"), ""));

        /*
         * agreIndCd 동의자료코드 (계좌이체)
         * 1. 납부방법이 자동치에이면 필수
         * 01: 서면 , 02: 공인인증 , 03:일반 인증 , 04: 녹취 , 05:ARS
         *
         * myslAthnTypeCd 본인인증타입 코드 (계좌이체)
         * 1. 동의자료 코드가 일반인증이면 필수
         * 01:SMS , 02:iPin , 03:신용카드 ,04 : 범용공인 인증
         */
        param.put("agreIndCd", StringUtil.NVL(map.get("agreIndCd"), ""));
        param.put("myslAthnTypeCd", StringUtil.NVL(map.get("myslAthnTypeCd"), ""));

        if ("LOCAL".equals(serverLocation)) {
            getVo(25, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * 26. 일시정지이력조회(X26)
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String termGubun : 조회기간 1:최근 1년, 'A' : 전체사용 기간
     * @return
     * @throws
     */
    public MpSuspenPosHisVO suspenPosHis(String ncn, String ctn, String custId, String termGubun)
        throws SelfServiceException, SocketTimeoutException {
        MpSuspenPosHisVO vo = new MpSuspenPosHisVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X26");

        param.put("termGubun", StringUtil.NVL(termGubun, ""));

        if ("LOCAL".equals(serverLocation)) {
            getVo(26, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 27. 일시정지가능여부 조회
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String stopRsnCd : 일시정지사유코드  CR01(고객요청), CS01(고객요청(M2M, MVNO)) 만 가능
     * @return
     * @throws
     */
    public MpSuspenPosInfoVO suspenPosInfo(String ncn, String ctn, String custId, String stopRsnCd)
        throws SelfServiceException, SocketTimeoutException {
        MpSuspenPosInfoVO vo = new MpSuspenPosInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X27");

        param.put("stopRsnCd", StringUtil.NVL(stopRsnCd, ""));

        if ("LOCAL".equals(serverLocation)) {
            getVo(27, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 28. 일시정지해제가능여부조회(X28)
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpSuspenCnlPosInfoInVO suspenCnlPosInfo(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        MpSuspenCnlPosInfoInVO vo = new MpSuspenCnlPosInfoInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X28");

        if ("LOCAL".equals(serverLocation)) {
            getVo(28, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }


    /**
     * 29. 일시정지 신청
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String reasonCode : 발착신 상태코드(발착신 상태코드(99(정지없음)/01(발신정지)/02(착신정지)/03(착발신정지))
     * @param String userMemo : 사용자 메모
     * @param String cpDateYn : 일시정지 기간여부(Y/N)
     * @param String cpEndDt : 일시정지 만료일자(yyyymmdd)
     * @param String cpStartDt : 일시정지 시작일자(yyyymmdd)
     * @param String cpPwdInsert : 일시정지패스워드 (4~8자리 숫자)
     * @return
     * @throws
     */
    public MpSuspenChgVO suspenChg(
        String ncn, String ctn, String custId,
        String reasonCode, String userMemo, String cpDateYn, String cpEndDt, String cpStartDt, String cpPwdInsert
    ) throws SocketTimeoutException {
        MpSuspenChgVO vo = new MpSuspenChgVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X29");
        param.put("reasonCode", StringUtil.NVL(reasonCode, ""));
        param.put("stopRsnCd", "");
        param.put("userMemo", StringUtil.NVL(userMemo, ""));
        param.put("cpDateYn", StringUtil.NVL(cpDateYn, ""));
        param.put("cpEndDt", StringUtil.NVL(cpEndDt, ""));
        param.put("cpStartDt", StringUtil.NVL(cpStartDt, ""));
        param.put("cpPwdInsert", StringUtil.NVL(cpPwdInsert, ""));
        if ("LOCAL".equals(serverLocation)) {
            //            getVo(29, vo);
            mplatFormServerAdapter.callService(param, vo, 30000);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * 30. 일시정지해제신청(X30)
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String pwdType : 비밀번호 타입 - PP: 일시정지, CP: 개인정보 암호
     * @param String strPwdNumInsert : 일시정지 비밀번호 - 4~8자리 숫자
     * @return
     * @throws
     */
    public MpSuspenCnlChgInVO suspenCnlChgIn(String ncn, String ctn, String custId, String pwdType, String cpPwdInsert)
        throws SocketTimeoutException {
        MpSuspenCnlChgInVO vo = new MpSuspenCnlChgInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X30");
        param.put("pwdType", StringUtil.NVL(pwdType, ""));
        param.put("strPwdNumInsert", StringUtil.NVL(cpPwdInsert, ""));

        if ("LOCAL".equals(serverLocation)) {
            getVo(30, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * 31. 번호목록조회
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String chkctn : 번호 채번 조회할 마지막 4자리 번호
     * @return
     * @throws
     */
    public MpNumChgeListVO numChgeList(String ncn, String ctn, String custId, String chkCtn) throws SocketTimeoutException {
        MpNumChgeListVO vo = new MpNumChgeListVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X31");
        param.put("chkCtn", StringUtil.NVL(chkCtn, ""));

        if ("LOCAL".equals(serverLocation)) {
            getVo(31, vo);
            //mplatFormServerAdapter.callService(param, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }

        return vo;
    }

    /**
     * 32. 번호변경
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String resvHkCtn : 채번한 번호	S	C	11	M	채번한 번호(010 + 4자리 + 4자리)
     * @param String resvHkSCtn : 채번한 번호 암호화	S	V	30	M	예)111AbCD
     * @param String resvHkMarketGubun : 채번한 번호 KT/KTF 구분	S	V	3	M	Market 구분 값 (kt/ktf)
     * @return
     * @throws
     */
    public MpNumChgeChgVO numChgeChg(
        String ncn, String ctn, String custId,
        String resvHkCtn, String resvHkSCtn, String resvHkMarketGubun
    ) throws SocketTimeoutException {
        MpNumChgeChgVO vo = new MpNumChgeChgVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X32");
        param.put("resvHkCtn", StringUtil.NVL(resvHkCtn, ""));
        param.put("resvHkSCtn", StringUtil.NVL(resvHkSCtn, ""));
        param.put("resvHkMarketGubun", StringUtil.NVL(resvHkMarketGubun, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(32, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * 33. 분실신고가능여부 조회 연동
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpPcsLostInfoVO pcsLostInfo(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MpPcsLostInfoVO vo = new MpPcsLostInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X33");
        if ("LOCAL".equals(serverLocation)) {
            getVo(33, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }

        return vo;
    }

    /**
     * 34. 분실신고 신청
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String loseType : 분실유형 코드(H:핸드폰, U:USIM부실, A:모두분실)
     * @param String guideYn : 고객요청사항(Y:발신정지(착신가능), N:발착신정지, C:정지 없음)
     * @param String cntcTlphNo : 연락받을번호
     * @param String loseCont : 분실내용
     * @param String loseLocation : 분실지역(N:국내분실, Y:해외분실)
     * @param String strPwdInsert : 일시정지패스워드 (4~8자리 숫자)
     * @return
     * @throws
     */
    public MpPcsLostChgVO pcsLostChg(
        String ncn, String ctn, String custId,
        String loseType, String guideYn, String cntcTlphNo, String loseCont, String loseLocation, String strPwdInsert
    ) throws SocketTimeoutException {
        MpPcsLostChgVO vo = new MpPcsLostChgVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X34");
        param.put("loseType", StringUtil.NVL(loseType, ""));
        param.put("guideYn", StringUtil.NVL(guideYn, ""));
        param.put("cntcTlphNo", StringUtil.NVL(cntcTlphNo, ""));
        param.put("loseCont", StringUtil.NVL(loseCont, ""));
        param.put("loseLocation", StringUtil.NVL(loseLocation, ""));
        param.put("strPwdInsert", StringUtil.NVL(strPwdInsert, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(34, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * 35. 분실신고 해제
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String strPwdNumInsert : 일시정지 패스워드 4~8자리 숫자
     * @param String pwdType : 패스워드 유형 (PP: 일시정지 암호, CP: 개인정보 암호)
     * @return
     * @throws
     */
    public MpPcsLostCnlChgVO pcsLostCnlChg(String ncn, String ctn, String custId, String strPwdNumInsert, String pwdType)
        throws SocketTimeoutException {


        MpPcsLostCnlChgVO vo = new MpPcsLostCnlChgVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X35");
        param.put("strPwdNumInsert", StringUtil.NVL(strPwdNumInsert, ""));
        param.put("pwdType", StringUtil.NVL(pwdType, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(34, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * 12. 총 통화 시간 조회 모바일용
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpTelTotalUseTimeMobileVO telTotalUseTimeMobile(String ncn, String ctn, String custId)
        throws SelfServiceException, SocketTimeoutException {
        MpTelTotalUseTimeMobileVO vo = new MpTelTotalUseTimeMobileVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X12");
        if ("LOCAL".equals(serverLocation)) {
            getVo(12, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }


    /**
     * 12. 총 통화 시간 조회 모바일용 (inner class  제외 처리)
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @return
     * @throws
     */
    public MpTelTotalUseTimeMobileDto telTotalUseTimeMobileDto(String ncn, String ctn, String custId)
        throws SelfServiceException, SocketTimeoutException {
        MpTelTotalUseTimeMobileDto vo = new MpTelTotalUseTimeMobileDto();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X12");
        if ("LOCAL".equals(serverLocation)) {
            //if("LOCAL".equals(serverLocation)) {	//molo test
            getVo(12, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 38. 부가서비스 해지
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String soc : SOC 코드
     * @return
     * @throws
     */
    public MpMoscRegSvcCanChgInVO moscRegSvcCanChg(String ncn, String ctn, String custId, String soc) throws SocketTimeoutException {
        MpMoscRegSvcCanChgInVO vo = new MpMoscRegSvcCanChgInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X38");
        param.put("soc", StringUtil.NVL(soc, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(38, vo);
            //mplatFormServerAdapter.callService(param, vo, 30000);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * 38. 부가서비스 해지(prodHstSeq 포함)
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String soc : SOC 코드
     * @return
     * @throws
     */
    public MpMoscRegSvcCanChgInVO moscRegSvcCanChgSeq(String ncn, String ctn, String custId, String soc, String prodHstSeq)
        throws SocketTimeoutException {
        MpMoscRegSvcCanChgInVO vo = new MpMoscRegSvcCanChgInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X38");
        param.put("soc", StringUtil.NVL(soc, ""));
        param.put("prodHstSeq", StringUtil.NVL(prodHstSeq, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(38, vo);
            //mplatFormServerAdapter.callService(param, vo, 30000);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * 요금제변경시에만 사용
     * tobe 요금제 변경시 sr머지
     * x38. 부가서비스 해지
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String soc : SOC 코드
     * @param String prcsMdlInd : 로그 저장용 구분자
     * @return
     * @throws
     */
    public RegSvcChgRes moscRegSvcCanChgNeTrace(MyPageSearchDto searchVO, String soc) {
        RegSvcChgRes vo = new RegSvcChgRes();

        boolean isCheck = true;

        //해지 신청 부가서비스 SOC 체크
        MspRateMstDto mspRateMstDto = fCommonSvc.getMspRateMst(soc);
        if (mspRateMstDto != null) {
            String onlineCanYn = StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "");
            if (!onlineCanYn.equals("Y")) {
                vo.setResultCode("E");
                vo.setResultCode("8888");
                vo.setSvcMsg(ExceptionMsgConstant.NO_ONLINE_CAN_CHANGE_ADD);
                isCheck = false;
            }
        } else {
            vo.setResultCode("E");
            vo.setResultCode("8887");
            vo.setSvcMsg(ExceptionMsgConstant.NO_EXSIST_RATE);
            isCheck = false;
        }

        try {
            if (isCheck) {
                HashMap<String, String> param = getParamMap(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), "X38");
                param.put("soc", StringUtil.NVL(soc, ""));
                if ("LOCAL".equals(serverLocation)) {
                    //getVoNe(38, vo);
                    mplatFormServerAdapter.callServiceNe(param, vo, 30000);
                } else {
                    mplatFormServerAdapter.callServiceNe(param, vo, 30000);
                }


            }

        } catch (SocketTimeoutException e) {
            vo.setResultCode("9999");
            vo.setSvcMsg(SOCKET_TIMEOUT_EXCEPTION);

        } catch (Exception e) {
            vo.setResultCode("9998");
            vo.setSvcMsg(e.getMessage());
        }

        return vo;
    }

    /**
     * 49. 청구서조회
     *
     * @return
     * @throws
     */
    public MpMoscBilEmailInfoInVO kosMoscBillInfo(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MpMoscBilEmailInfoInVO vo = new MpMoscBilEmailInfoInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X49");
        if ("LOCAL".equals(serverLocation)) {
            getVo(49, vo);
            //mplatFormServerAdapter.callService(param, vo, 30000);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }


    /**
     * 50. 청구서변경
     *
     * @return
     * @throws
     */
    public MpMoscBillChgInVO kosMoscBillChg(
        String ncn,
        String ctn,
        String custId,
        String billTypeCd,
        String status,
        String billAdInfo,
        String securMailYn,
        String ecRcvAgreYn,
        String sendGubun
    ) throws SocketTimeoutException {
        MpMoscBillChgInVO vo = new MpMoscBillChgInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X50");
        param.put("billTypeCd", StringUtil.NVL(billTypeCd, ""));
        param.put("status", StringUtil.NVL(status, ""));
        param.put("billAdInfo", StringUtil.NVL(billAdInfo, ""));
        param.put("securMailYn", StringUtil.NVL(securMailYn, ""));
        param.put("ecRcvAgreYn", StringUtil.NVL(ecRcvAgreYn, ""));
        param.put("sendGubun", StringUtil.NVL(sendGubun, ""));

        if ("LOCAL".equals(serverLocation)) {
            getVo(50, vo);
            //            mplatFormServerAdapter.callService(param, vo, 30000);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * 54. 스폰서조회
     *
     * @return
     * @throws
     */
    public MpMoscSpnsrItgInfoInVO kosMoscSpnsrItgInfo(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MpMoscSpnsrItgInfoInVO vo = new MpMoscSpnsrItgInfoInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X54");
        if ("LOCAL".equals(serverLocation)) {
            getVo(54, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * 59. 심플할인 사전체크
     *
     * @return
     * @throws
     */
    public MpMoscSdsSvcPreChkVo moscSdsSvcPreChk(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MpMoscSdsSvcPreChkVo vo = new MpMoscSdsSvcPreChkVo();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X59");
        if ("LOCAL".equals(serverLocation)) {
            getVo(59, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;
    }

    /**
     * X60. 심플할인 가입
     *
     * @return
     * @throws
     */
    public MpCommonXmlVO moscSdsSvcPreChk(String ncn, String ctn, String custId, String engtPerd) throws SocketTimeoutException {
        MpCommonXmlVO vo = new MpCommonXmlVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X60");
        param.put("engtPerd", StringUtil.NVL(engtPerd, "24"));
        if ("LOCAL".equals(serverLocation)) {
            getVo(60, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }


    /**
     * X61. 심플할인 해지
     *
     * @return
     * @throws
     */
    public MpCommonXmlVO moscSdsSvcCanChg(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MpCommonXmlVO vo = new MpCommonXmlVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X61");
        if ("LOCAL".equals(serverLocation)) {
            getVo(61, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }


    /**
     * X62. 심플할인 정보조회(X62)
     *
     * @return
     * @throws
     */
    public MpMoscSdsInfoVo moscSdsInfo(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MpMoscSdsInfoVo vo = new MpMoscSdsInfoVo();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X62");
        if ("LOCAL".equals(serverLocation)) {
            getVo(62, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }


    /**
     * X74. 쿠폰 정보조회(X74)
     *
     * @return
     * @throws
     */
    public InqrCoupInfoRes inqrCoupInfo(CoupInfoDto coupInfo) throws SocketTimeoutException {
        InqrCoupInfoRes inqrCoupInfoRes = new InqrCoupInfoRes();
        HashMap<String, String> param = getParamMap(coupInfo.getNcn(), coupInfo.getCtn(), coupInfo.getCustId(), "X74");

        param.put("searchTypeCd", coupInfo.getSearchTypeCd());
        param.put("coupSerialNo", coupInfo.getCoupSerialNo());
        param.put("svcTypeCd", coupInfo.getSvcTypeCd());
        param.put("coupTypeCd", coupInfo.getCoupTypeCd());
        param.put("pageNo", coupInfo.getPageNo() + "");
        param.put("coupStatCd", coupInfo.getCoupStatCd());

        if ("LOCAL".equals(serverLocation)) {
            //mplatFormServerAdapter.callService(param, inqrCoupInfoRes, 30000);
            getVo(74, inqrCoupInfoRes);
        } else {
            mplatFormServerAdapter.callService(param, inqrCoupInfoRes, 30000);
        }
        return inqrCoupInfoRes;
    }

    /**
     * X75. 쿠폰사용(X75)
     *
     * @return
     * @throws
     */
    public TrtmCoupUseRes trtmCoupUse(CoupInfoDto coupInfo) throws SocketTimeoutException {
        TrtmCoupUseRes trtmCoupUseRes = new TrtmCoupUseRes();
        HashMap<String, String> param = getParamMap(coupInfo.getNcn(), coupInfo.getCtn(), coupInfo.getCustId(), "X75");

        if (StringUtils.isBlank(coupInfo.getUseTypeCd())) {
            param.put("useTypeCd", "CRU");  //등록사용(등록동시사용)-즉시사용
        } else {
            param.put("useTypeCd", coupInfo.getUseTypeCd());
        }

        param.put("coupSerialNo", coupInfo.getCoupSerialNo());

        if (StringUtils.isBlank(coupInfo.getSvcTypeCd())) {
            param.put("svcTypeCd", "ND");  // - ND: Not Defined (모든 서비스유형)
        } else {
            param.put("svcTypeCd", coupInfo.getSvcTypeCd());
        }

        if ("LOCAL".equals(serverLocation)) {
            //getVo(75, trtmCoupUseRes);
            mplatFormServerAdapter.callService(param, trtmCoupUseRes, 30000);
        } else {
            mplatFormServerAdapter.callService(param, trtmCoupUseRes, 30000);
        }
        return trtmCoupUseRes;
    }


    /**
     * X76. 사용완료 쿠폰 목록 조회(X76)
     *
     * @return
     * @throws
     */
    public InqrUsedCoupListRes inqrUsedCoupList(CoupInfoDto coupInfo) throws SocketTimeoutException {
        InqrUsedCoupListRes inqrUsedCoupListRes = new InqrUsedCoupListRes();
        HashMap<String, String> param = getParamMap(coupInfo.getNcn(), coupInfo.getCtn(), coupInfo.getCustId(), "X76");

        param.put("coupSerialNo", coupInfo.getCoupSerialNo());

        if (!StringUtils.isBlank(coupInfo.getSvcTypeCd())) {
            param.put("svcTypeCd", coupInfo.getSvcTypeCd());
        } else {
            param.put("svcTypeCd", "");
        }

        String today = DateTimeUtil.getFormatString("yyyyMMdd");
        if (StringUtils.isBlank(coupInfo.getUseDateFrom())) {
            try {
                param.put("useDateFrom", DateTimeUtil.addMonths(today, -2));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        } else {
            param.put("useDateFrom", coupInfo.getUseDateFrom());
        }

        if (StringUtils.isBlank(coupInfo.getUseDateTo())) {
            param.put("useDateTo", today);   //20201201
        } else {
            param.put("useDateTo", coupInfo.getUseDateTo());
        }

        param.put("pageNo", coupInfo.getPageNo() + "");

        if ("LOCAL".equals(serverLocation)) {
            mplatFormServerAdapter.callService(param, inqrUsedCoupListRes, 30000);
            //getVo(76, inqrUsedCoupListRes);
        } else {
            mplatFormServerAdapter.callService(param, inqrUsedCoupListRes, 30000);
        }
        return inqrUsedCoupListRes;
    }


    /**
     * tobe추가
     * MVNO 결합 상태 조회
     * x77
     * @param moscCombReqDto
     * @return
     * @throws SocketTimeoutException
     */
    public MoscCombInfoResDTO moscCombSvcInfo(MoscCombReqDto moscCombReqDto) throws SocketTimeoutException {

        MoscCombInfoResDTO moscCombInfoOutDTO = new MoscCombInfoResDTO();
        HashMap<String, String> param = getParamMap(moscCombReqDto.getNcn(), moscCombReqDto.getCtn(), moscCombReqDto.getCustId(), "X77");

        param.put("combSvcNoCd", moscCombReqDto.getCombSvcNoCd()); //M: MVNO회선, K:KT 회선
        param.put("combSrchId", moscCombReqDto.getCombSrchId()); //MVNO회선은 전화번호 KT 회선은 이름
        param.put("svcIdfyNo", moscCombReqDto.getSvcIdfyNo()); //생년월일 ex) 19821120 (KT회선일 경우 필수)
        param.put("sexCd", moscCombReqDto.getSexCd()); //1: 남성, 2: 여성 (KT 회선일 경우 필수)
        param.put("cmbStndSvcNo", moscCombReqDto.getCmbStndSvcNo()); //KT 회선일 경우 필수 모바일 회선: 전화번호  인터넷: ID

        if ("LOCAL".equals(serverLocation)) {
            //            getVoNe(77, moscCombInfoOutDTO);
            mplatFormServerAdapter.callServiceNe(param, moscCombInfoOutDTO, 30000);
        } else {
            mplatFormServerAdapter.callServiceNe(param, moscCombInfoOutDTO, 30000);
        }


        return moscCombInfoOutDTO;

    }

    /**
     * tobe추가
     * MVNO 결합 상태 조회
     * x77
     *
     * @param moscCombReqDto
     * @return
     * @throws SocketTimeoutException
     */
    public MoscCombInfoResDTO moscCombSvcInfoLog(MoscCombReqDto moscCombReqDto, String prcsMdlInd, String contractNum) {

        MoscCombInfoResDTO moscCombInfoOutDTO = new MoscCombInfoResDTO();

        if (prcsMdlInd != null && !StringUtils.isBlank(prcsMdlInd) && prcsMdlInd.length() > 14) {
            throw new McpMplatFormException(ExceptionMsgConstant.INVALID_REFERER_EXCEPTION);
        }
        //이력 저장
        McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();
        serviceAlterTraceSub.setNcn(moscCombReqDto.getNcn());
        serviceAlterTraceSub.setContractNum(contractNum);
        serviceAlterTraceSub.setSubscriberNo(moscCombReqDto.getCtn());
        if ("M".equals(moscCombReqDto.getCombSvcNoCd())) {
            serviceAlterTraceSub.setTrtmRsltSmst(moscCombReqDto.getCombSrchId());
        } else {
            serviceAlterTraceSub.setTrtmRsltSmst(moscCombReqDto.getCmbStndSvcNo());
        }
        serviceAlterTraceSub.setPrcsMdlInd("MP" + prcsMdlInd);
        serviceAlterTraceSub.setEventCode("X77");

        StringBuffer parameterBuffer = new StringBuffer();
        parameterBuffer.append("combSvcNoCd[");
        parameterBuffer.append(moscCombReqDto.getCombSvcNoCd());
        parameterBuffer.append("]combSrchId[");
        parameterBuffer.append(moscCombReqDto.getCombSrchId());
        parameterBuffer.append("]svcIdfyNo[");
        parameterBuffer.append(moscCombReqDto.getSvcIdfyNo());
        parameterBuffer.append("]sexCd[");
        parameterBuffer.append(moscCombReqDto.getSexCd());
        parameterBuffer.append("]cmbStndSvcNo[");
        parameterBuffer.append(moscCombReqDto.getCmbStndSvcNo());
        parameterBuffer.append("]sameCustKtRetvYn[");
        parameterBuffer.append(moscCombReqDto.getSameCustKtRetvYn());
        parameterBuffer.append("]");

        if (parameterBuffer.capacity() > 300) {
            String strParameter = parameterBuffer.toString();
            byte[] bytes = strParameter.getBytes();
            strParameter = new String(bytes, 0, 299);
            serviceAlterTraceSub.setParameter(strParameter);
        } else {
            serviceAlterTraceSub.setParameter(parameterBuffer.toString());
        }

        try {
            HashMap<String, String> param = getParamMap(moscCombReqDto.getNcn(), moscCombReqDto.getCtn(), moscCombReqDto.getCustId(), "X77");

            param.put("combSvcNoCd", moscCombReqDto.getCombSvcNoCd()); //M: MVNO회선, K:KT 회선
            param.put("combSrchId", moscCombReqDto.getCombSrchId()); //MVNO회선은 전화번호 KT 회선은 이름
            param.put("svcIdfyNo", moscCombReqDto.getSvcIdfyNo()); //생년월일 ex) 19821120 (KT회선일 경우 필수)
            param.put("sexCd", moscCombReqDto.getSexCd()); //1: 남성, 2: 여성 (KT 회선일 경우 필수)
            param.put("cmbStndSvcNo", moscCombReqDto.getCmbStndSvcNo()); //KT 회선일 경우 필수 모바일 회선: 전화번호  인터넷: ID
            if ("Y".equals(moscCombReqDto.getSameCustKtRetvYn())) {
                param.put("sameCustKtRetvYn", moscCombReqDto.getSameCustKtRetvYn()); //동일명의로 가입된 KT 회선 정보 조회 여부
            }
            if ("LOCAL".equals(serverLocation)) {
                getVoNe(77, moscCombInfoOutDTO);
                //            mplatFormServerAdapter.callServiceNe(param, moscCombInfoOutDTO, 30000);
            } else {
                mplatFormServerAdapter.callServiceNe(param, moscCombInfoOutDTO, 30000);
            }

        } catch (SocketTimeoutException e) {
            serviceAlterTraceSub.setSuccYn("N");
            serviceAlterTraceSub.setPrcsSbst(e.getMessage());
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);
            throw new McpMplatFormException(e.getMessage());
        } catch (Exception e) {
            serviceAlterTraceSub.setSuccYn("N");
            serviceAlterTraceSub.setPrcsSbst(e.getMessage());
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);
            throw new McpMplatFormException(e.getMessage());
        }

        serviceAlterTraceSub.setGlobalNo(moscCombInfoOutDTO.getGlobalNo());
        serviceAlterTraceSub.setRsltCd(moscCombInfoOutDTO.getResultCode());

        StringBuffer resltMsgBuffer = new StringBuffer();
        MoscMvnoComInfo parentObj = null;
        if (moscCombInfoOutDTO != null && moscCombInfoOutDTO.isSuccess()) {
            serviceAlterTraceSub.setSuccYn("Y");
            parentObj = moscCombInfoOutDTO.getMoscMvnoComInfo();

            if (parentObj != null) {
                resltMsgBuffer.append("moscMvnoComInfo:{");
                resltMsgBuffer.append("combSvcNo:");
                resltMsgBuffer.append(parentObj.getCombSvcNo());
                resltMsgBuffer.append(",combYn:");
                resltMsgBuffer.append(parentObj.getCombYn());
                resltMsgBuffer.append(",svcDivCd:");
                resltMsgBuffer.append(parentObj.getSvcDivCd());
                resltMsgBuffer.append(",svcNo:");
                resltMsgBuffer.append(parentObj.getSvcNo());
                resltMsgBuffer.append(",indvInfoAgreeMsgSbst:");
                resltMsgBuffer.append(parentObj.getIndvInfoAgreeMsgSbst());
            }

            if (moscCombInfoOutDTO.getMoscSrchCombInfoList() != null) {
                resltMsgBuffer.append(",moscSrchCombInfoList[");
                int index = 0;
                for (MoscMvnoComInfo item: moscCombInfoOutDTO.getMoscSrchCombInfoList()) {
                    if (index > 0) {
                        resltMsgBuffer.append(",");
                    }
                    resltMsgBuffer.append("{");
                    resltMsgBuffer.append("combSvcNo:");
                    resltMsgBuffer.append(item.getCombSvcNo());
                    resltMsgBuffer.append(",combYn:");
                    resltMsgBuffer.append(item.getCombYn());
                    resltMsgBuffer.append(",svcDivCd:");
                    resltMsgBuffer.append(item.getSvcDivCd());
                    resltMsgBuffer.append(",svcNo:");
                    resltMsgBuffer.append(item.getSvcNo());
                    resltMsgBuffer.append(",svcContOpnDt:");
                    resltMsgBuffer.append(item.getSvcContOpnDt());
                    resltMsgBuffer.append(",corrNm:");
                    resltMsgBuffer.append(item.getCorrNm());
                    resltMsgBuffer.append("}");
                    index++;
                }
                resltMsgBuffer.append("]");
            }
            resltMsgBuffer.append("}");

        } else {
            serviceAlterTraceSub.setSuccYn("N");
            resltMsgBuffer.append(moscCombInfoOutDTO.getSvcMsg());
        }


        try {
            if (resltMsgBuffer.capacity() > 1000) {
                String strResltMsg = resltMsgBuffer.toString();
                byte[] bytes = strResltMsg.getBytes();
                int tempIndex = 999;
                if (999 > strResltMsg.length()) {
                    tempIndex = strResltMsg.length();
                }
                strResltMsg = new String(bytes, 0, tempIndex);
                serviceAlterTraceSub.setPrcsSbst(strResltMsg);
            } else {
                serviceAlterTraceSub.setPrcsSbst(resltMsgBuffer.toString());
            }
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);
        } catch (DataAccessException e) {
            logger.error("DataAccessException=>" + e.getMessage());
        } catch (Exception e) {
            logger.error("Exception=>" + e.getMessage());
        }

        return moscCombInfoOutDTO;

    }


    /**
     * tobe추가
     * x78
     * mvno 결합 사전 체크
     *
     * @param moscCombChkReqDto
     * @return
     * @throws SocketTimeoutException
     */
    public MoscCombChkResDto moscCombChkInfo(MoscCombChkReqDto moscCombChkReqDto) throws SocketTimeoutException {

        MoscCombChkResDto moscCombChkResDto = new MoscCombChkResDto();
        HashMap<String, String> param = getParamMap(moscCombChkReqDto.getNcn(), moscCombChkReqDto.getCtn(), moscCombChkReqDto.getCustId(), "X78");

        param.put("jobGubun", moscCombChkReqDto.getJobGubun()); //clntUsrId
        param.put("svcNoCd", moscCombChkReqDto.getSvcNoCd()); //clntUsrId
        param.put("svcNoTypeCd", moscCombChkReqDto.getSvcNoTypeCd()); //clntUsrId
        param.put("cmbStndSvcNo", moscCombChkReqDto.getCmbStndSvcNo()); //clntUsrId
        param.put("custNm", moscCombChkReqDto.getCustNm()); //clntUsrId
        param.put("svcIdfyTypeCd", moscCombChkReqDto.getSvcIdfyTypeCd()); //clntUsrId
        param.put("svcIdfyNo", moscCombChkReqDto.getSvcIdfyNo()); //clntUsrId
        param.put("sexCd", moscCombChkReqDto.getSexCd()); //clntUsrId
        param.put("aplyMethCd", moscCombChkReqDto.getAplyMethCd()); //clntUsrId
        param.put("aplyRelatnCd", moscCombChkReqDto.getAplyRelatnCd()); //clntUsrId
        param.put("aplyNm", moscCombChkReqDto.getAplyNm()); //clntUsrId
        param.put("homeCombTerm", moscCombChkReqDto.getHomeCombTerm()); //clntUsrId

        if ("LOCAL".equals(serverLocation)) {
            //            getVo(78, moscCombChkResDto);
            mplatFormServerAdapter.callService(param, moscCombChkResDto, 30000);
        } else {
            mplatFormServerAdapter.callService(param, moscCombChkResDto, 30000);
        }

        return moscCombChkResDto;
    }


    /**
     * x78
     * mvno 결합 사전 체크
     * 로그 저장 처리
     *
     * @param MoscCombChkReqDto
     * @return
     * @throws SocketTimeoutException
     */
    public MoscCombChkRes moscCombChkInfoLog(MoscCombChkReqDto moscCombChkReqDto, String prcsMdlInd, String contractNum) {
        MoscCombChkRes moscCombChkRes = new MoscCombChkRes();

        if (prcsMdlInd != null && !StringUtils.isBlank(prcsMdlInd) && prcsMdlInd.length() > 14) {
            throw new McpMplatFormException(ExceptionMsgConstant.INVALID_REFERER_EXCEPTION);
        }

        //이력 저장
        McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();
        serviceAlterTraceSub.setNcn(moscCombChkReqDto.getNcn());
        serviceAlterTraceSub.setContractNum(contractNum);
        serviceAlterTraceSub.setSubscriberNo(moscCombChkReqDto.getCtn());
        serviceAlterTraceSub.setTrtmRsltSmst(moscCombChkReqDto.getCmbStndSvcNo());
        serviceAlterTraceSub.setPrcsMdlInd("MP" + prcsMdlInd);
        serviceAlterTraceSub.setEventCode("X78");


        StringBuffer parameterBuffer = new StringBuffer();
        parameterBuffer.append("cmbStndSvcNo[");
        parameterBuffer.append(moscCombChkReqDto.getCmbStndSvcNo());
        parameterBuffer.append("]custNm[");
        parameterBuffer.append(moscCombChkReqDto.getCustNm());
        parameterBuffer.append("]svcIdfyNo[");
        parameterBuffer.append(moscCombChkReqDto.getSvcIdfyNo());
        parameterBuffer.append("]jobGubun[");
        parameterBuffer.append(moscCombChkReqDto.getJobGubun());
        parameterBuffer.append("]sexCd[");
        parameterBuffer.append(moscCombChkReqDto.getSexCd());
        parameterBuffer.append("]");

        if (parameterBuffer.capacity() > 300) {
            String strParameter = parameterBuffer.toString();
            byte[] bytes = strParameter.getBytes();
            strParameter = new String(bytes, 0, 299);
            serviceAlterTraceSub.setParameter(strParameter);
        } else {
            serviceAlterTraceSub.setParameter(parameterBuffer.toString());
        }

        try {
            HashMap<String, String> param = getParamMap(moscCombChkReqDto.getNcn(), moscCombChkReqDto.getCtn(), moscCombChkReqDto.getCustId(), "X78");
            param.put("jobGubun", moscCombChkReqDto.getJobGubun());
            param.put("svcNoCd", moscCombChkReqDto.getSvcNoCd());
            param.put("svcNoTypeCd", moscCombChkReqDto.getSvcNoTypeCd());
            param.put("cmbStndSvcNo", moscCombChkReqDto.getCmbStndSvcNo());
            param.put("custNm", moscCombChkReqDto.getCustNm());
            param.put("svcIdfyTypeCd", moscCombChkReqDto.getSvcIdfyTypeCd());
            param.put("svcIdfyNo", moscCombChkReqDto.getSvcIdfyNo());
            param.put("sexCd", moscCombChkReqDto.getSexCd());
            param.put("aplyMethCd", moscCombChkReqDto.getAplyMethCd());
            param.put("aplyRelatnCd", moscCombChkReqDto.getAplyRelatnCd());
            param.put("aplyNm", moscCombChkReqDto.getAplyNm());
            param.put("homeCombTerm", moscCombChkReqDto.getHomeCombTerm());

            if ("LOCAL".equals(serverLocation)) {
                getVoNe(78, moscCombChkRes);
                //mplatFormServerAdapter.callServiceNe(param, moscCombChkRes, 30000);
            } else {
                mplatFormServerAdapter.callServiceNe(param, moscCombChkRes, 30000);
            }

        } catch (SocketTimeoutException e) {
            serviceAlterTraceSub.setSuccYn("N");
            serviceAlterTraceSub.setPrcsSbst(e.getMessage());
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);
            throw new McpMplatFormException(e.getMessage());
        } catch (Exception e) {
            serviceAlterTraceSub.setSuccYn("N");
            serviceAlterTraceSub.setPrcsSbst(e.getMessage());
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);
            throw new McpMplatFormException(e.getMessage());
        }

        serviceAlterTraceSub.setGlobalNo(moscCombChkRes.getGlobalNo());
        serviceAlterTraceSub.setRsltCd(moscCombChkRes.getResultCode());


        StringBuffer resltMsgBuffer = new StringBuffer();
        if (moscCombChkRes.isSuccess()) {
            serviceAlterTraceSub.setSuccYn("Y");
            resltMsgBuffer.append("resltMsg:");
            resltMsgBuffer.append(moscCombChkRes.getSvcMsg());
            resltMsgBuffer.append(",sbscYn:");
            resltMsgBuffer.append(moscCombChkRes.getSbscYn());
            resltMsgBuffer.append(",moscCombPreChkListOutDTO:[");
            /*
            <resltMsg>정상</resltMsg>
           <sbscYn>Y</sbscYn>
            <moscCombPreChkListOutDTO>
                <resltMsg>정상</resltMsg>
                <sbscYn>Y</sbscYn>
                <svcNo>01071109431</svcNo>
            </moscCombPreChkListOutDTO>
            <moscCombPreChkListOutDTO>
                <resltMsg>정상</resltMsg>
                <sbscYn>Y</sbscYn>
                <svcNo>01022230726</svcNo>
            </moscCombPreChkListOutDTO>
            */

            List<MoscCombChkRes.MoscCombPreChkListOut> moscCombPreChkList = moscCombChkRes.getMoscCombChkList();
            int index = 0;
            if (moscCombPreChkList != null && moscCombPreChkList.size() > 0) {
                for (MoscCombChkRes.MoscCombPreChkListOut item: moscCombPreChkList) {
                    if (index > 0) {
                        resltMsgBuffer.append(",");
                    }
                    resltMsgBuffer.append("{");
                    resltMsgBuffer.append("resltMsg:");
                    resltMsgBuffer.append(item.getResltMsg());
                    resltMsgBuffer.append(",sbscYn:");
                    resltMsgBuffer.append(item.getSbscYn());
                    resltMsgBuffer.append(",svcNo:");
                    resltMsgBuffer.append(item.getSvcNo());
                    resltMsgBuffer.append("}");

                    index++;
                }
            }
            resltMsgBuffer.append("]");
        } else {
            serviceAlterTraceSub.setSuccYn("N");
            resltMsgBuffer.append(moscCombChkRes.getSvcMsg());
        }

        try {
            if (resltMsgBuffer.capacity() > 1000) {
                String strResltMsg = resltMsgBuffer.toString();
                byte[] bytes = strResltMsg.getBytes();
                int tempIndex = 999;
                if (999 > strResltMsg.length()) {
                    tempIndex = strResltMsg.length();
                }
                strResltMsg = new String(bytes, 0, tempIndex);
                serviceAlterTraceSub.setPrcsSbst(strResltMsg);
            } else {
                serviceAlterTraceSub.setPrcsSbst(resltMsgBuffer.toString());
            }
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);
        } catch (DataAccessException e) {
            logger.error("DataAccessException=>" + e.getMessage());
        } catch (Exception e) {
            logger.error("Exception=>" + e.getMessage());
        }

        if (!moscCombChkRes.isSuccess()) {
            throw new SelfServiceException(moscCombChkRes.getResultCode(), moscCombChkRes.getSvcMsg());
        }
        return moscCombChkRes;
    }


    /**
     * mvno 결합 저장
     * x79
     *
     * @param moscCombChkReqDto
     * @return
     * @throws SocketTimeoutException
     */
    public MoscCombChkResDto moscCombSaveInfo(MoscCombChkReqDto moscCombChkReqDto) throws SocketTimeoutException {

        MoscCombChkResDto moscCombChkResDto = new MoscCombChkResDto();
        HashMap<String, String> param = getParamMap(moscCombChkReqDto.getNcn(), moscCombChkReqDto.getCtn(), moscCombChkReqDto.getCustId(), "X79");

        param.put("jobGubun", moscCombChkReqDto.getJobGubun()); //clntUsrId
        param.put("svcNoCd", moscCombChkReqDto.getSvcNoCd()); //clntUsrId
        param.put("svcNoTypeCd", moscCombChkReqDto.getSvcNoTypeCd()); //clntUsrId
        param.put("cmbStndSvcNo", moscCombChkReqDto.getCmbStndSvcNo()); //clntUsrId
        param.put("custNm", moscCombChkReqDto.getCustNm()); //clntUsrId
        param.put("svcIdfyTypeCd", moscCombChkReqDto.getSvcIdfyTypeCd()); //clntUsrId
        param.put("svcIdfyNo", moscCombChkReqDto.getSvcIdfyNo()); //clntUsrId
        param.put("sexCd", moscCombChkReqDto.getSexCd()); //clntUsrId
        param.put("aplyMethCd", moscCombChkReqDto.getAplyMethCd()); //clntUsrId
        param.put("aplyRelatnCd", moscCombChkReqDto.getAplyRelatnCd()); //clntUsrId
        param.put("aplyNm", moscCombChkReqDto.getAplyNm()); //clntUsrId
        param.put("homeCombTerm", moscCombChkReqDto.getHomeCombTerm()); //clntUsrId


        if ("LOCAL".equals(serverLocation)) {
            //            getVo(79, moscCombChkResDto);
            mplatFormServerAdapter.callService(param, moscCombChkResDto, 30000);
        } else {
            mplatFormServerAdapter.callService(param, moscCombChkResDto, 30000);
        }

        return moscCombChkResDto;
    }

    /**
     * x79
     *
     * @param moscCombChkReqDto
     * @return
     * @throws SocketTimeoutException
     *
     * moscCombPreChk
     */
    public MoscCombChkRes moscCombSaveInfoLog(MoscCombChkReqDto moscCombChkReqDto, String prcsMdlInd, String contractNum)
        throws SocketTimeoutException {

        if (prcsMdlInd != null && !StringUtils.isBlank(prcsMdlInd) && prcsMdlInd.length() > 14) {
            throw new McpMplatFormException(ExceptionMsgConstant.INVALID_REFERER_EXCEPTION);
        }

        MoscCombChkRes moscCombChkRes = new MoscCombChkRes();

        //이력 저장
        McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();
        serviceAlterTraceSub.setNcn(moscCombChkReqDto.getNcn());
        serviceAlterTraceSub.setContractNum(contractNum);
        serviceAlterTraceSub.setSubscriberNo(moscCombChkReqDto.getCtn());
        serviceAlterTraceSub.setTrtmRsltSmst(moscCombChkReqDto.getCmbStndSvcNo());
        serviceAlterTraceSub.setPrcsMdlInd("MP" + prcsMdlInd);
        serviceAlterTraceSub.setEventCode("X79");


        StringBuffer parameterBuffer = new StringBuffer();
        parameterBuffer.append("cmbStndSvcNo[");
        parameterBuffer.append(moscCombChkReqDto.getCmbStndSvcNo());
        parameterBuffer.append("]custNm[");
        parameterBuffer.append(moscCombChkReqDto.getCustNm());
        parameterBuffer.append("]svcIdfyNo[");
        parameterBuffer.append(moscCombChkReqDto.getSvcIdfyNo());
        parameterBuffer.append("]jobGubun[");
        parameterBuffer.append(moscCombChkReqDto.getJobGubun());
        parameterBuffer.append("]sexCd[");
        parameterBuffer.append(moscCombChkReqDto.getSexCd());
        parameterBuffer.append("]");

        if (parameterBuffer.capacity() > 300) {
            String strParameter = parameterBuffer.toString();
            byte[] bytes = strParameter.getBytes();
            strParameter = new String(bytes, 0, 299);
            serviceAlterTraceSub.setParameter(strParameter);
        } else {
            serviceAlterTraceSub.setParameter(parameterBuffer.toString());
        }

        try {
            HashMap<String, String> param = getParamMap(moscCombChkReqDto.getNcn(), moscCombChkReqDto.getCtn(), moscCombChkReqDto.getCustId(), "X79");

            param.put("jobGubun", moscCombChkReqDto.getJobGubun());
            param.put("svcNoCd", moscCombChkReqDto.getSvcNoCd());
            param.put("svcNoTypeCd", moscCombChkReqDto.getSvcNoTypeCd());
            param.put("cmbStndSvcNo", moscCombChkReqDto.getCmbStndSvcNo());
            param.put("custNm", moscCombChkReqDto.getCustNm());
            param.put("svcIdfyTypeCd", moscCombChkReqDto.getSvcIdfyTypeCd());
            param.put("svcIdfyNo", moscCombChkReqDto.getSvcIdfyNo());
            param.put("sexCd", moscCombChkReqDto.getSexCd());
            param.put("aplyMethCd", moscCombChkReqDto.getAplyMethCd());
            param.put("aplyRelatnCd", moscCombChkReqDto.getAplyRelatnCd());
            param.put("aplyNm", moscCombChkReqDto.getAplyNm());
            param.put("homeCombTerm", moscCombChkReqDto.getHomeCombTerm());

            if ("LOCAL".equals(serverLocation)) {
                //            getVo(79, moscCombChkResDto);
                getVoNe(79, moscCombChkRes);
                //                mplatFormServerAdapter.callServiceNe(param, moscCombChkRes, 30000);
            } else {
                mplatFormServerAdapter.callServiceNe(param, moscCombChkRes, 30000);
            }

        } catch (SocketTimeoutException e) {
            serviceAlterTraceSub.setSuccYn("N");
            serviceAlterTraceSub.setPrcsSbst(e.getMessage());
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);
            throw e;
        } catch (Exception e) {
            serviceAlterTraceSub.setSuccYn("N");
            serviceAlterTraceSub.setPrcsSbst(e.getMessage());
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);
            throw new McpMplatFormException(e.getMessage());
        }

        serviceAlterTraceSub.setGlobalNo(moscCombChkRes.getGlobalNo());
        serviceAlterTraceSub.setRsltCd(moscCombChkRes.getResultCode());
        StringBuffer resltMsgBuffer = new StringBuffer();

        if (moscCombChkRes.isSuccess()) {
            serviceAlterTraceSub.setSuccYn("Y");

            resltMsgBuffer.append("resltMsg:");
            resltMsgBuffer.append(moscCombChkRes.getSvcMsg());
            resltMsgBuffer.append(",sbscYn:");
            resltMsgBuffer.append(moscCombChkRes.getSbscYn());
            resltMsgBuffer.append(",moscCombPreChkListOutDTO:[");
            /*
            <resltMsg>정상</resltMsg>
           <sbscYn>Y</sbscYn>
            <moscCombPreChkListOutDTO>
                <resltMsg>정상</resltMsg>
                <sbscYn>Y</sbscYn>
                <svcNo>01071109431</svcNo>
            </moscCombPreChkListOutDTO>
            <moscCombPreChkListOutDTO>
                <resltMsg>정상</resltMsg>
                <sbscYn>Y</sbscYn>
                <svcNo>01022230726</svcNo>
            </moscCombPreChkListOutDTO>
            */

            List<MoscCombChkRes.MoscCombPreChkListOut> moscCombPreChkList = moscCombChkRes.getMoscCombChkList();
            int index = 0;
            if (moscCombPreChkList != null && moscCombPreChkList.size() > 0) {
                for (MoscCombChkRes.MoscCombPreChkListOut item: moscCombPreChkList) {
                    if (index > 0) {
                        resltMsgBuffer.append(",");
                    }
                    resltMsgBuffer.append("{");
                    resltMsgBuffer.append("resltMsg:");
                    resltMsgBuffer.append(item.getResltMsg());
                    resltMsgBuffer.append(",sbscYn:");
                    resltMsgBuffer.append(item.getSbscYn());
                    resltMsgBuffer.append(",svcNo:");
                    resltMsgBuffer.append(item.getSvcNo());
                    resltMsgBuffer.append("}");

                    index++;
                }
            }
            resltMsgBuffer.append("]");

        } else {
            resltMsgBuffer.append(moscCombChkRes.getSvcMsg());
            serviceAlterTraceSub.setSuccYn("N");
        }


        try {
            if (resltMsgBuffer.capacity() > 1000) {
                String strResltMsg = resltMsgBuffer.toString();
                byte[] bytes = strResltMsg.getBytes();
                int tempIndex = 999;
                if (999 > strResltMsg.length()) {
                    tempIndex = strResltMsg.length();
                }
                strResltMsg = new String(bytes, 0, tempIndex);
                serviceAlterTraceSub.setPrcsSbst(strResltMsg);
            } else {
                serviceAlterTraceSub.setPrcsSbst(resltMsgBuffer.toString());
            }
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);

        } catch (DataAccessException e) {
            logger.error("DataAccessException=>" + e.getMessage());
        } catch (Exception e) {
            logger.error("Exception=>" + e.getMessage());
        }

        if (!moscCombChkRes.isSuccess()) {
            throw new SelfServiceException(moscCombChkRes.getResultCode(), moscCombChkRes.getSvcMsg());
        }

        return moscCombChkRes;
    }

    /**
     * X80. OTP인증서비스(X80)* @return
     *
     * @throws
     */
    public MoscOtpSvcInfoRes moscOtpSvcInfo(String ncn, String ctn, String custId, String svcNo, String dataVal1) throws SocketTimeoutException {
        MoscOtpSvcInfoRes moscOtpSvcInfoRes = new MoscOtpSvcInfoRes();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X80");

        param.put("svcNo", svcNo);

         /*
          * chkInd
         01 : LTE 결합 부가서비스 OTP 인증
         (LTE 결합서비스 데이터 받기 PL208J939)
         */
        param.put("chkInd", "01");

        /*
         * 업무구분코드 01 번 입력시 부가서비스 코드 필수
         * 부가 서비스 확인 필요<==========
         */
        param.put("dataVal1", dataVal1);

        if ("LOCAL".equals(serverLocation)) {
            getVoNe(80, moscOtpSvcInfoRes);
            //      	 mplatFormServerAdapter.callServiceNe(param, moscOtpSvcInfoRes, 30000);
        } else {
            mplatFormServerAdapter.callServiceNe(param, moscOtpSvcInfoRes, 30000);
        }
        return moscOtpSvcInfoRes;
    }


    /**
     * 83.회선 사용기간조회
     *
     * @return
     * @throws
     */
    public MoscWireUseTimeInfoRes moscWireUseTimeInfo(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MoscWireUseTimeInfoRes vo = new MoscWireUseTimeInfoRes();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X83");

        if ("LOCAL".equals(serverLocation)) {
            //adapter.callService(param, vo, 30000);
            getVo(83, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * X85. USIM 유효성 체크(X85)
     *
     * @return
     * @throws
     */
    public MoscInqrUsimUsePsblOutDTO moscIntmMgmtSO(JuoSubInfoDto juoSubInfoDto) throws SocketTimeoutException {
        MoscInqrUsimUsePsblOutDTO moscInqrUsimUsePsblOutDTO = new MoscInqrUsimUsePsblOutDTO();

        HashMap<String, String> param = new HashMap<String, String>();
        String iccid = StringUtil.NVL(juoSubInfoDto.getIccId(), "");
        param.put("appEventCd", "X85");
        param.put("iccid", iccid);

        if ("LOCAL".equals(serverLocation) || "STG".equals(serverLocation)) {
            getVo(85, moscInqrUsimUsePsblOutDTO);
        } else {
            mplatFormServerAdapter.callService(param, moscInqrUsimUsePsblOutDTO, 30000);
        }
        return moscInqrUsimUsePsblOutDTO;
    }

    /**
     * 서브회선 마스터 결합 신청(Y44)
     *
     * @return
     * @throws
     */
    public MoscSubMstCombChgRes moscSubMstCombChg(String ncn, String ctn, String custId, String mstSvcContId)
        throws SelfServiceException, SocketTimeoutException {
        //이력 저장
        McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();
        serviceAlterTraceSub.setNcn(ncn);
        serviceAlterTraceSub.setContractNum(ncn);
        serviceAlterTraceSub.setSubscriberNo(ctn);
        serviceAlterTraceSub.setTrtmRsltSmst(mstSvcContId);
        serviceAlterTraceSub.setPrcsMdlInd("MPY44");
        serviceAlterTraceSub.setEventCode("Y44");

        MoscSubMstCombChgRes moscSubMstCombChgRes = new MoscSubMstCombChgRes();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "Y44");
        param.put("mstSvcContId", mstSvcContId);


        try {
            mplatFormServerAdapter.callServiceNe(param, moscSubMstCombChgRes, 30000);
        } catch (SocketTimeoutException e) {
            serviceAlterTraceSub.setSuccYn("N");
            serviceAlterTraceSub.setPrcsSbst(e.getMessage());
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);
            throw e;
        } catch (Exception e) {
            serviceAlterTraceSub.setSuccYn("N");
            serviceAlterTraceSub.setPrcsSbst(e.getMessage());
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);
            throw new McpMplatFormException(e.getMessage());
        }


        serviceAlterTraceSub.setGlobalNo(moscSubMstCombChgRes.getGlobalNo());
        serviceAlterTraceSub.setRsltCd(moscSubMstCombChgRes.getResultCode());
        StringBuffer resltMsgBuffer = new StringBuffer();

        if (moscSubMstCombChgRes.isSuccess()) {
            serviceAlterTraceSub.setSuccYn("Y");
            resltMsgBuffer.append(moscSubMstCombChgRes.extractValue("outDto"));
        } else {
            serviceAlterTraceSub.setSuccYn("N");
            resltMsgBuffer.append(moscSubMstCombChgRes.extractValue("outDto"));
        }

        try {
            if (resltMsgBuffer.capacity() > 1000) {
                String strResltMsg = resltMsgBuffer.toString();
                byte[] bytes = strResltMsg.getBytes();
                int tempIndex = 999;
                if (999 > strResltMsg.length()) {
                    tempIndex = strResltMsg.length();
                }
                strResltMsg = new String(bytes, 0, tempIndex);
                serviceAlterTraceSub.setPrcsSbst(strResltMsg);
            } else {
                serviceAlterTraceSub.setPrcsSbst(resltMsgBuffer.toString());
            }
            msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);

        } catch (DataAccessException e) {
            logger.error("DataAccessException=>" + e.getMessage());
        } catch (Exception e) {
            logger.error("Exception=>" + e.getMessage());
        }


        //서비스 실패
        if (!moscSubMstCombChgRes.isSuccess()) {
            /*
            <commHeader>
                    <globalNo>9122533020250312155813471</globalNo>
                    <encYn></encYn>
                    <responseType>E</responseType>
                    <responseCode>ITL_KSC_E004</responseCode>
                    <responseLogcd></responseLogcd>
                    <responseTitle></responseTitle>
                    <responseBasic>해당 전화번호의 고객이 존재하지 않습니다.</responseBasic>
                    <langCode></langCode>
                    <filler></filler>
                </commHeader>
             */
            //Msg 치환 처리
            //공통 코드 조회
            List<NmcpCdDtlDto> errorMsgReplaceList = Optional.ofNullable(NmcpServiceUtils.getCodeList("ErrorMsgReplaceList"))
                .orElse(Collections.emptyList());

            Map<String, NmcpCdDtlDto> errorMsgReplaceListMap = errorMsgReplaceList.stream()
                .collect(Collectors.toMap(
                    NmcpCdDtlDto::getDtlCd,
                    Function.identity(),
                    (existing, replacement) -> replacement // 기존 값 덮어쓰기
                ));

            // 오류 코드로 치환할 공통코드  있는지?
            NmcpCdDtlDto msgReplaceObj = errorMsgReplaceListMap.get(moscSubMstCombChgRes.getResultCode());  // null 체크
            if (msgReplaceObj != null) {
                String checkVariable = msgReplaceObj.getDtlCdNm();

                if ("ALL".equals(checkVariable)) {  //메세지 까지 확인 하자 않음
                    //메세지 치환
                    moscSubMstCombChgRes.setResultMsg(msgReplaceObj.getDtlCdDesc());
                    moscSubMstCombChgRes.setResultCd("9999");
                } else {
                    String svcMsg = Optional.ofNullable(moscSubMstCombChgRes.getSvcMsg()).orElse("");
                    if (svcMsg.indexOf(checkVariable) > -1) {
                        moscSubMstCombChgRes.setResultMsg(msgReplaceObj.getDtlCdDesc());
                        moscSubMstCombChgRes.setResultCd("9999");
                    }
                }
            }
        } else if (!"0000".equals(moscSubMstCombChgRes.getResultCd())) {
            /*
            <commHeader>
                <globalNo>9122533020250317153039870</globalNo>
                <encYn />
                <responseType>N</responseType>
                <responseCode />
                <responseLogcd />
                <responseTitle />
                <responseBasic />
                <langCode />
                <filler />
            </commHeader>
            <outDto>
                <resultCd>1000</resultCd>
                <resultMsg>[MVNO마스터결합전용 더미부가서비스(엠모바일)] 상품은 월중 2회 이상 가입이 불가능합니다.</resultMsg>
            </outDto>

            resultCd	결과코드	4	M
            "0000 : 성공(변경 처리 완료)
            9999 : 실패
            1000 : 서브회선 부가서비스 가입 오류 "
            */
            //Msg 치환 처리
            //공통 코드 조회
            List<NmcpCdDtlDto> errorMsgReplaceList = Optional.ofNullable(NmcpServiceUtils.getCodeList("ErrorMsgReplaceList"))
                .orElse(Collections.emptyList());

            Map<String, NmcpCdDtlDto> errorMsgReplaceListMap = errorMsgReplaceList.stream()
                .collect(Collectors.toMap(
                    NmcpCdDtlDto::getDtlCd,
                    Function.identity(),
                    (existing, replacement) -> replacement // 기존 값 덮어쓰기
                ));

            // 오류 코드로 치환할 공통코드  있는지?
            NmcpCdDtlDto msgReplaceObj = errorMsgReplaceListMap.get(moscSubMstCombChgRes.getResultCd());  // null 체크
            if (msgReplaceObj != null) {
                String checkVariable = msgReplaceObj.getDtlCdNm();

                if ("ALL".equals(checkVariable)) {  //메세지 까지 확인 하자 않음
                    //메세지 치환
                    moscSubMstCombChgRes.setResultMsg(msgReplaceObj.getDtlCdDesc());
                    moscSubMstCombChgRes.setResultCd("9999");
                } else {
                    String resultMsg = Optional.ofNullable(moscSubMstCombChgRes.getResultMsg()).orElse("");
                    if (resultMsg.indexOf(checkVariable) > -1) {
                        moscSubMstCombChgRes.setResultMsg(msgReplaceObj.getDtlCdDesc());
                        moscSubMstCombChgRes.setResultCd("9999");
                    }
                }
            }
        }


        return moscSubMstCombChgRes;
    }


    /**
     * 요금제변경
     * x84
     *
     * @param custId
     * @param ncn
     * @param ctn
     * @param clntIp
     * @param clntUsrId
     * @return
     * @throws SocketTimeoutException
     */
    public String moscFarPriceChg(String custId, String ncn, String ctn, String clntIp, String clntUsrId) throws SocketTimeoutException {

        return null;

    }

    /**
     * tobe추가
     * 데이터쉐어링 결합중인 대상 조회
     * x71
     *
     * @param custId
     * @param ncn
     * @param ctn
     * @param clntIp
     * @param clntUsrId
     * @return
     * @throws SocketTimeoutException
     */

    public MoscDataSharingResDto mosharingList(String custId, String ncn, String ctn) throws SocketTimeoutException {
        MoscDataSharingResDto moscDataSharingResDto = new MoscDataSharingResDto();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X71");
        if ("LOCAL".equals(serverLocation)) {
            getVoNe(71, moscDataSharingResDto);
        } else {
            mplatFormServerAdapter.callServiceNe(param, moscDataSharingResDto, 30000);
        }

        return moscDataSharingResDto;
    }

    /**
     * tobe 추가
     * 데이터 쉐어링 가입/해지
     * x70
     *
     * @param custId
     * @param ncn
     * @param ctn
     * @param clntIp
     * @param clntUsrId
     * @param crprCtn
     * @param opmdSvcNo
     * @param opmdWorkDivCd
     * @return
     * @throws SocketTimeoutException
     */

    public MpCommonXmlVO moscDataSharingSave(
        String custId, String ncn,
        String ctn, String opmdSvcNo
        , String opmdWorkDivCd
    ) throws SocketTimeoutException {
        MpCommonXmlVO vo = new MpCommonXmlVO();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X70");
        param.put("opmdSvcNo", opmdSvcNo); //데이터쉐어링 대상 전화번호
        param.put("opmdWorkDivCd", opmdWorkDivCd); //처리구분코드 A: 결합, C: 해지

        if ("LOCAL".equals(serverLocation)) {
            getVo(70, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }


        return vo;

    }

    /**
     * tobe 추가
     * 데이터쉐어링 사전 체크 및 가입 가능 대상 조회
     * x69
     *
     * @param custId
     * @param ncn
     * @param ctn
     * @param clntIp
     * @param clntUsrId
     * @param crprCtn
     * @return
     * @throws SocketTimeoutException
     */
    public MoscDataSharingResDto moscDataSharingChk(String custId, String ncn, String ctn, String crprCtn) throws SocketTimeoutException {

        MoscDataSharingResDto moscDataSharingResDto = new MoscDataSharingResDto();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X69");
        //param.put("crprCtn", crprCtn); //결합 할 법인 전화번호

        if ("LOCAL".equals(serverLocation)) {
            getVoNe(69, moscDataSharingResDto);
        } else {
            mplatFormServerAdapter.callServiceNe(param, moscDataSharingResDto, 30000);
        }

        return moscDataSharingResDto;

    }

    /**
     * tobe추가
     * 데이터 주고받기 결합상태 조회
     * x86
     *
     * @param custId
     * @param ncn
     * @param ctn
     * @param clntIp
     * @param clntUsrId
     * @param retvGubunCd
     * @return
     * @throws SocketTimeoutException
     */
    //    public MoscCombMgmtResDTO moscCombSvcMgmtInfo(String custId, String ncn, String ctn, String clntIp, String clntUsrId, String retvGubunCd) throws SocketTimeoutException {
    //		MoscCombMgmtResDTO moscCombMgmtResDTO = new MoscCombMgmtResDTO();
    //
    //	    HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X86");
    //	    param.put("clntIp", clntIp); //clientIp
    //	    param.put("clntUsrId", clntUsrId); //clntUsrId
    //	    param.put("retvGubunCd",retvGubunCd); //조회구분코드
    //
    //        mplatFormServerAdapter.callService(param, moscCombMgmtResDTO, 30000);
    //
    //      	return moscCombMgmtResDTO;
    //    }
    public MoscCombStatMgmtInfoOutVO moscCombStatMgmtInfo(String ncn, String ctn, String custId, String retvGubunCd) throws SocketTimeoutException {

        MoscCombStatMgmtInfoOutVO moscCombStatMgmtInfoOutVO = new MoscCombStatMgmtInfoOutVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X86");
        param.put("retvGubunCd", retvGubunCd);

        if ("LOCAL".equals(serverLocation)) {
            getVo(86, moscCombStatMgmtInfoOutVO);
        } else {
            mplatFormServerAdapter.callService(param, moscCombStatMgmtInfoOutVO, 30000);
        }
        return moscCombStatMgmtInfoOutVO;
    }

    /**
     * tobe추가
     * mvno 결합 서비스 계약 조회
     * x87
     *
     * @param custId
     * @param ncn
     * @param ctn
     * @param clntIp
     * @return
     * @throws SocketTimeoutException
     */

    public MoscCombDtlResDTO moscCombSvcInfoList(String custId, String ncn, String ctn) throws SocketTimeoutException {

        MoscCombDtlResDTO moscCombDtlOutDTO = new MoscCombDtlResDTO();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X87");

        if ("LOCAL".equals(serverLocation)) {
            getVoNe(87, moscCombDtlOutDTO);
            //mplatFormServerAdapter.callServiceNe(param, moscCombDtlOutDTO, 30000);
        } else {
            mplatFormServerAdapter.callServiceNe(param, moscCombDtlOutDTO, 30000);
        }

        return moscCombDtlOutDTO;

    }

    /**
     * tobe추가
     * x88
     * 요금제 상품 예약변경
     *
     * @param custId
     * @param ncn
     * @param ctn
     * @param clntIp
     * @param clntUsrId
     * @param soc
     * @param ftrNewParam
     * @return
     * @throws SocketTimeoutException
     */
    public MoscFarPriceChgDto doFarPricePlanRsrvChg(
        String custId, String ncn, String ctn,
        String soc, String ftrNewParam
    ) throws SocketTimeoutException {

        MoscFarPriceChgDto moscFarPriceChgDto = new MoscFarPriceChgDto();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X88");
        param.put("soc", soc); //soc 코드 요금제코드
        param.put("ftrNewParam", ftrNewParam); //부가파람정보(부가파람정보가 존재하는 경우 입력)

        if ("LOCAL".equals(serverLocation)) {
            getVo(88, moscFarPriceChgDto);
        } else {
            mplatFormServerAdapter.callService(param, moscFarPriceChgDto, 30000);
        }

        return moscFarPriceChgDto;

    }

    /**
     * tobe 추가
     * x89
     * 요금상품예약변경조회
     *
     * @param custId
     * @param ncn
     * @param ctn
     * @param clntIp
     * @param clntUsrId
     * @return
     * @throws SocketTimeoutException
     */
    public MoscFarPriceResDto doFarPricePlanRsrvSearch(String custId, String ncn, String ctn) throws SocketTimeoutException {

        MoscFarPriceResDto moscFarPriceResDto = new MoscFarPriceResDto();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X89");

        if ("LOCAL".equals(serverLocation)) {
            getVo(89, moscFarPriceResDto);
        } else {
            mplatFormServerAdapter.callService(param, moscFarPriceResDto, 30000);
        }

        return moscFarPriceResDto;

    }

    /**
     * tobe추가
     * 요금상품예약변경취소(X90)
     *
     * @param custId
     * @param ncn
     * @param ctn
     * @param clntIp
     * @param clntUsrId
     * @return
     * @throws SocketTimeoutException
     */
    public MpCommonXmlVO doFarPricePlanRsrvCancel(String custId, String ncn, String ctn) throws SocketTimeoutException {

        MpCommonXmlVO vo = new MpCommonXmlVO();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X90");

        if ("LOCAL".equals(serverLocation)) {
            getVo(90, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * 91. 신용카드 인증조회
     *
     * @param  crdtCardNo : 신용카드번호
     * @param  crdtCardTermDay : 신용카드유효기간  예)YYYYMM
     * @param  brthDate : 생년월일  예)YYYYMMDD
     * @param  custNm : 고객이름
     * @return
     * @throws
     */
    public MoscCrdtCardAthnInDto moscCrdtCardAthnInfo(
        String crdtCardNo, String crdtCardTermDay
        , String brthDate, String custNm
    ) throws SocketTimeoutException {
        MoscCrdtCardAthnInDto vo = new MoscCrdtCardAthnInDto();
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("appEventCd", "X91");
        param.put("crdtCardNo", crdtCardNo);
        param.put("crdtCardTermDay", crdtCardTermDay);
        param.put("brthDate", brthDate);
        param.put("custNm", custNm);

        if ("LOCAL".equals(serverLocation)) {
            getVoNe(91, vo);
        } else {
            mplatFormServerAdapter.callServiceNe(param, vo, 30000);
        }
        return vo;
    }

    /**
     * 2023-07-06 test
     * tobe추가
     * 부가서비스 조회 param 포함(X97)
     *
     * @param custId
     * @param ncn
     * @param ctn
     * @return
     * @throws SocketTimeoutException
     * @throws SelfServiceException
     */
    public MpAddSvcInfoParamDto getAddSvcInfoParamDto(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {

        MpAddSvcInfoParamDto vo = new MpAddSvcInfoParamDto();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X97");

        if ("LOCAL".equals(serverLocation)) {
            getVo(97, vo);//임시로 x20 테스트용 값 호출
            //mplatFormServerAdapter.callService(param, vo, 30000);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;


    }

    /**
     * 미납요금 즉시 납부처리
     * tobe 추가
     * x68
     *
     * @param moscPymnReqDto
     * @return
     * @throws SocketTimeoutException
     */
    public PaymentInfoVO moscPymnTreat(MoscPymnReqDto moscPymnReqDto) throws SocketTimeoutException {
        PaymentInfoVO vo = new PaymentInfoVO();

        HashMap<String, String> param = getParamMap(moscPymnReqDto.getNcn(), moscPymnReqDto.getCtn(), moscPymnReqDto.getCustId(), "X68");


        param.put("payMentMoney", moscPymnReqDto.getPayMentMoney()); //수납금액
        param.put("blMethod", moscPymnReqDto.getBlMethod()); //수납방법

        if ("D".equals(moscPymnReqDto.getBlMethod())) {//수납방법 D:실시간계좌이체
            param.put("bankAcctNo", moscPymnReqDto.getBankAcctNo()); //계좌번호
            param.put("blBankCode", moscPymnReqDto.getBlBankCode()); //은행코드
            param.put("agrDivCd", moscPymnReqDto.getAgrDivCd()); //동의유형
            param.put("myslfAthnTypeItgCd", moscPymnReqDto.getMyslfAthnTypeItgCd()); //본인인증유형
        } else if ("C".contentEquals(moscPymnReqDto.getBlMethod())) { //신용카드 C
            param.put("cardNo", moscPymnReqDto.getCardNo()); //카드번호
            param.put("cardExpirDate", moscPymnReqDto.getCardExpirDate()); //카드유효기간 YYYMM
            param.put("cardPwd", moscPymnReqDto.getCardPwd());//카드비밀번호 앞 두자리
            param.put("cardInstMnthCnt", moscPymnReqDto.getCardInstMnthCnt());//5만원 미만일 경우 일시불만 가능, 할부시 최대 36개월 ex 01,02
        } else if ("P".equals(moscPymnReqDto.getBlMethod())) { //간편결재
            param.put("rmnyChId", moscPymnReqDto.getRmnyChId()); //카카오페이:KA, 페이코:PY, 네이버페이:NP

        }

        //간편결제인 경우 간편결제 처리 가능한 URL이며
        //고객이 직접 이 URL로 접속하여 간편결제 요청한 수납채널에서 직접 수납처리하여야 KT요금이 수납처리됨.
        //그 외의 수납방법인 경우는 null"

        if ("LOCAL".equals(serverLocation)) {
            getVo(68, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }

        return vo;

    }

    public NowDlvryResDto inqrPsblDeliveryAddr(NowDlvryReqDto nowDlvryReqDto) throws SocketTimeoutException {
        NowDlvryResDto nowDlvryResDto = new NowDlvryResDto();
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("appEventCd", "D01");
        param.put("zipNo", nowDlvryReqDto.getZipNo());
        //        param.put("targetAddr1", nowDlvryReqDto.getTargetAddr1());
        param.put("targetAddr1", nowDlvryReqDto.getJibunAddr());
        param.put("targetAddr2", nowDlvryReqDto.getTargetAddr2());
        param.put("addrTypeCd", nowDlvryReqDto.getAddrTypeCd());
        param.put("bizOrgCd", nowDlvryReqDto.getBizOrgCd());
        param.put("targetAddrLat", nowDlvryReqDto.getEntY());
        param.put("targetAddrLng", nowDlvryReqDto.getEntX());
        param.put("nfcYn", nowDlvryReqDto.getNfcYn());

        if ("LOCAL".equals(serverLocation)) {

            getVoEtc("D01", nowDlvryResDto);
            //mplatFormServerAdapter.callService(param, nowDlvryResDto, 3000);
        } else {
            mplatFormServerAdapter.callService(param, nowDlvryResDto, 30000);
        }
        return nowDlvryResDto;
    }

    public NowDlvryResDto acceptDeliveryUsim(NowDlvryReqDto nowDlvryReqDto) throws SocketTimeoutException {
        NowDlvryResDto nowDlvryResDto = new NowDlvryResDto();
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("appEventCd", "D02");
        param.put("zipNo", nowDlvryReqDto.getZipNo());
        param.put("targetAddr1", nowDlvryReqDto.getTargetAddr1());
        param.put("targetAddr2", nowDlvryReqDto.getTargetAddr2());
        param.put("addrTypeCd", nowDlvryReqDto.getAddrTypeCd());
        param.put("bizOrgCd", nowDlvryReqDto.getBizOrgCd());
        param.put("orderRcvTlphNo", nowDlvryReqDto.getOrderRcvTlphNo());
        param.put("custInfoAgreeYn", nowDlvryReqDto.getCustInfoAgreeYn());
        param.put("rsvOrderYn", nowDlvryReqDto.getRsvOrderYn());
        param.put("rsvOrderDt", nowDlvryReqDto.getRsvOrderDt());
        param.put("orderReqMsg", nowDlvryReqDto.getOrderReqMsg());
        param.put("acceptTime", nowDlvryReqDto.getAcceptTime());
        param.put("usimAmt", nowDlvryReqDto.getUsimAmt());
        param.put("usimAmt", nowDlvryReqDto.getUsimAmt());
        param.put("targetAddrLat", nowDlvryReqDto.getEntY());
        param.put("targetAddrLng", nowDlvryReqDto.getEntX());
        param.put("nfcYn", nowDlvryReqDto.getNfcYn());

        if ("LOCAL".equals(serverLocation)) {
            getVoEtc("D02", nowDlvryResDto);
            //mplatFormServerAdapter.callService(param, nowDlvryResDto, 30000);
        } else {
            mplatFormServerAdapter.callService(param, nowDlvryResDto, 30000);
        }
        return nowDlvryResDto;
    }

    public NowDlvryResDto updateDeliveryUsim(NowDlvryReqDto nowDlvryReqDto) throws SocketTimeoutException {
        NowDlvryResDto nowDlvryResDto = new NowDlvryResDto();
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("appEventCd", "D03");
        param.put("jobGubun", nowDlvryReqDto.getJobGubun());
        param.put("ktOrderId", nowDlvryReqDto.getKtOrderId());
        param.put("orderRcvTlphNo", nowDlvryReqDto.getOrderRcvTlphNo());
        param.put("zipNo", nowDlvryReqDto.getZipNo());
        param.put("addrTypeCd", nowDlvryReqDto.getAddrTypeCd());
        param.put("targetAddr1", nowDlvryReqDto.getTargetAddr1());
        param.put("targetAddr2", nowDlvryReqDto.getTargetAddr2());
        param.put("custInfoAgreeYn", nowDlvryReqDto.getCustInfoAgreeYn());
        param.put("rsvOrderYn", nowDlvryReqDto.getRsvOrderYn());
        param.put("rsvOrderDt", nowDlvryReqDto.getRsvOrderDt());
        param.put("orderReqMsg", nowDlvryReqDto.getOrderReqMsg());
        param.put("acceptTime", nowDlvryReqDto.getAcceptTime());
        param.put("usimAmt", nowDlvryReqDto.getUsimAmt());

        if ("LOCAL".equals(serverLocation)) {

            getVoEtc("D03", nowDlvryResDto);
        } else {
            mplatFormServerAdapter.callService(param, nowDlvryResDto, 30000);

        }
        return nowDlvryResDto;
    }

    public NowDlvryResDto inqrDeliveryOrderInfo(NowDlvryReqDto nowDlvryReqDto) throws SocketTimeoutException {
        NowDlvryResDto nowDlvryResDto = new NowDlvryResDto();
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("appEventCd", "D04");
        param.put("ktOrderId", nowDlvryReqDto.getKtOrderId());
        if ("LOCAL".equals(serverLocation)) {

            getVoEtc("D04", nowDlvryResDto);
        } else {
            mplatFormServerAdapter.callService(param, nowDlvryResDto, 30000);

        }
        return nowDlvryResDto;
    }


    /**
     * 82.KAKAOPAY 가입 SMS 전송
     *
     * @return
     * @throws
     */
    public MoscSimplePaySmsRes moscSimplePaySms(String ncn, String ctn, String custId, String payBizCd) throws SocketTimeoutException {
        MoscSimplePaySmsRes vo = new MoscSimplePaySmsRes();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X82");
        param.put("payBizCd", StringUtil.NVL(payBizCd, ""));
        if ("LOCAL".equals(serverLocation)) {
            getVo(90, vo);
            //        	mplatFormServerAdapter.callService(param, vo, 30000);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }


        return vo;
    }

    /**
     * 청구서발송결과조회
     * X51
     *
     * @param String ncn, String ctn, String custId
     * @return MoscBillSendInfoOutVO
     * @throws SocketTimeoutException
     */
    public MoscBillSendInfoOutVO kosMoscBillSendInfo(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MoscBillSendInfoOutVO vo = new MoscBillSendInfoOutVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X51");
        if ("LOCAL".equals(serverLocation)) {
            getVo(51, vo);
            //mplatFormServerAdapter.callService(param, vo, 30000);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * 청구서재발송조회(X52)
     * X52
     *
     * @param String ncn, String ctn, String custId
     * @return kosMoscBillReSendInfo
     * @throws SocketTimeoutException
     */
    public MoscBillReSendInfoOutVO kosMoscBillReSendInfo(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MoscBillReSendInfoOutVO vo = new MoscBillReSendInfoOutVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X52");
        if ("LOCAL".equals(serverLocation)) {
            mplatFormServerAdapter.callService(param, vo, 30000);
            // getVo(52, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * 청구서재발송처리(X53)
     * X53
     *
     * @param String ncn, String ctn, String custId
     * @return kosMoscBillReSendInfo
     * @throws SocketTimeoutException
     */
    public MoscBillReSendChgOutVO kosMoscBillReSendChg(
        String ncn, String ctn, String custId,
        String year, String month, String billTypeCd, String billAdInfo
    ) throws SocketTimeoutException {
        MoscBillReSendChgOutVO vo = new MoscBillReSendChgOutVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X53");
        param.put("year", year); // 재발송 년도 예)2014
        param.put("month", month); // 재발송 월 예)12
        param.put("billTypeCd", billTypeCd); // 청구서발송유형 1.이메일, 2.MMS
        param.put("securMailYn", "Y"); // 보안메일 여부 Y/N
        param.put("causeCode", "1"); // 재발송 사유 코드  : 1:단순재발행, 2:명세서 주소변경,3:미수신, 4:기타 - 사유코드가 2인 경우 명세서 정보 변경됨
        param.put("billAdInfo", billAdInfo); // 발송주소 : 청구서발송유형이 1일 경우 이메일주소/ 2일 경우 전화번호

        if ("LOCAL".equals(serverLocation)) {
            getVo(53, vo);
            //            mplatFormServerAdapter.callService(param, vo, 30000);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * Y07. USIM PUK 번호 조회(2022.08.01.wooki)
     *
     * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
     * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
     * @param String custId : 고객번호
     * @return MpUsimPukVO
     * @throws
     */
    public MpUsimPukVO getUsimPuk(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        MpUsimPukVO vo = new MpUsimPukVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "Y07");

        if ("LOCAL".equals(serverLocation)) {
            getVoEtc("Y07", vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }


    /**
     * Y12. 단말기 스펙정보 조회(Y12)
     *
     * @param
     * @return
     * @throws
     */
    public MoscRetvIntmMdlSpecInfoVO moscRetvIntmMdlSpecInfo(String indCd, String intmMdlId) throws SocketTimeoutException {


        MoscRetvIntmMdlSpecInfoVO vo = new MoscRetvIntmMdlSpecInfoVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", "Y12");
        param.put("indCd", indCd);
        param.put("intmMdlId", intmMdlId);
        param.put("intmSpecTypeCd", "111|110");


        if ("LOCAL".equals(serverLocation)) {
            mplatFormServerAdapter.callServiceNe(param, vo, 30000);
            //        	getVoEtc("Y13", vo);
        } else {
            mplatFormServerAdapter.callServiceNe(param, vo, 30000);
        }
        return vo;
    }

    /**
     * Y13. 기기원부조회(Y13)
     *
     * @param
     * @return
     * @throws
     */
    public MoscRetvIntmOrrgInfoVO moscRetvIntmOrrgInfo(String indCd, String imei) throws SocketTimeoutException {


        MoscRetvIntmOrrgInfoVO vo = new MoscRetvIntmOrrgInfoVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", "Y13");
        param.put("indCd", indCd);
        param.put("intmUniqIdntNo", imei);

        if ("LOCAL".equals(serverLocation)) {
            mplatFormServerAdapter.callServiceNe(param, vo, 30000);
            //        	getVoEtc("Y13", vo);
        } else {
            mplatFormServerAdapter.callServiceNe(param, vo, 30000);
        }
        return vo;
    }

    /**
     * OMD 단말 처리 사전체크(Y14)
     *
     * @param
     * @return
     * @throws
     */
    public MoscBfacChkOmdIntmVO moscBfacChkOmdIntm(String wrkjobDivCd, String imei, String imei2) throws SocketTimeoutException {

        MoscBfacChkOmdIntmVO vo = new MoscBfacChkOmdIntmVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", "Y14");
        param.put("wrkjobDivCd", wrkjobDivCd);
        param.put("imei", imei);
        param.put("imei2", imei2);

        if ("LOCAL".equals(serverLocation)) {
            mplatFormServerAdapter.callServiceNe(param, vo, 30000);
            //        	getVoEtc("Y14", vo);
        } else {
            mplatFormServerAdapter.callServiceNe(param, vo, 30000);
        }
        return vo;
    }

    /**
     * OMD 단말 처리(Y15)
     *
     * @param
     * @return
     * @throws
     */
    public MoscTrtOmdIntmVO moscTrtOmdIntm(EsimDto esimDto) throws SocketTimeoutException {

        MoscTrtOmdIntmVO vo = new MoscTrtOmdIntmVO();
        HashMap<String, String> param = new HashMap<String, String>();

        String wrkjobDivCd = StringUtil.NVL(esimDto.getWrkjobDivCd(), "");
        String intmModelNm = StringUtil.NVL(esimDto.getModelNm(), "");
        String intmModelId = StringUtil.NVL(esimDto.getModelId(), "");
        String intmSeq = StringUtil.NVL(esimDto.getIntmSrlNo(), "");


        String wifiMacAdr = StringUtil.NVL(esimDto.getWifiMacAdr(), "");
        String intmEtcPurpDivCd = StringUtil.NVL(esimDto.getIntmEtcPurpDivCd(), "");
        String euiccId = StringUtil.NVL(esimDto.getEid(), "");
        String trtDivCd = StringUtil.NVL(esimDto.getTrtDivCd(), "");
        String imei1 = StringUtil.NVL(esimDto.getImei1(), "");
        String imei2 = StringUtil.NVL(esimDto.getImei2(), "");
        String cmpnCdIfVal = StringUtil.NVL(esimDto.getCmpnCdIfVal(), "");
        String birthday = StringUtil.NVL(esimDto.getBirthday(), "");
        String sexDiv = StringUtil.NVL(esimDto.getSexDiv(), "");
        String ctn = StringUtil.NVL(esimDto.getCtn(), "");

        param.put("appEventCd", "Y15");
        param.put("wrkjobDivCd", wrkjobDivCd);
        param.put("intmModelNm", intmModelNm);
        param.put("intmModelId", intmModelId);
        param.put("intmSeq", intmSeq);
        if (!"".equals(wifiMacAdr)) {
            param.put("wifiMacAdr", wifiMacAdr);
        }
        param.put("intmEtcPurpDivCd", intmEtcPurpDivCd);
        param.put("euiccId", euiccId);
        if (!"".equals(trtDivCd)) {
            param.put("trtDivCd", trtDivCd);
        }
        param.put("imei", imei1);
        param.put("imei2", imei2);

        if (!"".equals(cmpnCdIfVal)) {
            param.put("cmpnCdIfVal", cmpnCdIfVal);
        }

        if (!"".equals(birthday)) {
            param.put("birthday", birthday);
        }

        if (!"".equals(sexDiv)) {
            param.put("sexDiv", sexDiv);
        }

        if (!"".equals(ctn)) {
            param.put("ctn", ctn);
        }


        if ("LOCAL".equals(serverLocation)) {
            mplatFormServerAdapter.callServiceNe(param, vo, 30000);
            //        	getVoEtc("Y15", vo);
        } else {
            mplatFormServerAdapter.callServiceNe(param, vo, 30000);
        }
        return vo;
    }


    public CommCdInfoRes moscCommCdInfo(String cdKey) {


        CommCdInfoRes vo = new CommCdInfoRes();
        try {

            HashMap<String, String> param = new HashMap<String, String>();
            param.put("appEventCd", "Y29");
            param.put("cdKey", cdKey);

            mplatFormServerAdapter.callServiceNe(param, vo, 30000);


        } catch (SocketTimeoutException e) {
            vo.setResultCode("9999");
            vo.setSvcMsg(SOCKET_TIMEOUT_EXCEPTION);
        } catch (Exception e) {
            vo.setResultCode("9998");
            vo.setSvcMsg(e.getMessage());
        }

        return vo;
    }

    /**
     * 아이핀Ci 조회 (Y39)
     * @param osstOrdNo
     * @return
     * @throws SocketTimeoutException
     */
    public MpSvcContIpinVO MoscSvcContService(String osstOrdNo) throws SocketTimeoutException {

        MpSvcContIpinVO vo = new MpSvcContIpinVO();

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", "Y39");
        param.put("osstOrdNo", osstOrdNo);

        if ("LOCAL".equals(serverLocation)) {
            getVoEtc("Y39", vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 개인정보 활용동의 변경 (Y41)
     * @param ncn
     * @param ctn
     * @param custId
     * @param mktAgrYn
     * @return MpCustInfoAgreeVO
     * @throws SocketTimeoutException
     */
    public MpCustInfoAgreeVO moscContCustInfoAgreeChg(String ncn, String ctn, String custId, MpCustInfoAgreeVO mpCustInfoAgreeVO)
        throws SocketTimeoutException {

        MpCustInfoAgreeVO vo = new MpCustInfoAgreeVO();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "Y41");
        this.putAgreeInfoToParamMap(param, mpCustInfoAgreeVO);

        if ("LOCAL".equals(serverLocation)) {
            getVoEtc("Y41", vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    private void putAgreeInfoToParamMap(HashMap<String, String> param, MpCustInfoAgreeVO mpCustInfoAgreeVO) {
        List<String> agreeNames = List.of("othcmpInfoAdvrRcvAgreYn",
            "othcmpInfoAdvrCnsgAgreYn",
            "grpAgntBindSvcSbscAgreYn",
            "cardInsrPrdcAgreYn",
            "fnncDealAgreeYn",
            "cnsgInfoAdvrRcvAgreYn",
            "indvLoInfoPrvAgreeYn");

        Map<String, String> agreeMap = agreeNames.stream()
            .filter(agreeName -> (mpCustInfoAgreeVO.getAgreeYn(agreeName) != null) && !mpCustInfoAgreeVO.getAgreeYn(agreeName).isEmpty())
            .collect(Collectors.toMap(Function.identity(), mpCustInfoAgreeVO::getAgreeYn));

        param.putAll(agreeMap);
    }

    private HashMap<String, String> getParamMap(String ncn, String ctn, String custId, String eventCd) {
        HashMap<String, String> param = new HashMap<String, String>();

        String userId = this.sesUserId();
        try {
            param.put("ncn", ncn);
            param.put("ctn", ctn);
            param.put("custId", custId);
            param.put("userid", userId);
            param.put("appEventCd", eventCd);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return param;
    }

    private String sesUserId() {
        String retId = "";

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto != null) {
            retId = StringUtil.NVL(userSessionDto.getUserId(), "");
        }
        logger.debug("sesUserId:{}", retId);
        return retId;
    }


    /**
     * Y07. 제휴상품 리마인드 SMS 수신 상태 조회 및 변경(2024.11.07.su)
     *
     * @param MoscRemindSmsDto
     * @return MpRemindSmsVO
     * @throws
     */
    public MpRemindSmsVO getRemindSms(MoscRemindSmsDto moscRemindSmsDto) throws SelfServiceException, SocketTimeoutException {
        MpRemindSmsVO vo = new MpRemindSmsVO();
        HashMap<String, String> param = getParamMap(moscRemindSmsDto.getNcn(), moscRemindSmsDto.getCtn(), moscRemindSmsDto.getCustId(), "Y42");

        param.put("prodGubun", moscRemindSmsDto.getProdGubun()); //조회할 상품구분값
        param.put("wrkjobCd", moscRemindSmsDto.getWrkjobCd()); //업무구분
        param.put("smsRcvBlckYn", moscRemindSmsDto.getSmsRcvBlckYn()); //SMS 수신 차단 상태

        if ("LOCAL".equals(serverLocation)) {
            getVoEtc("Y42", vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    public MpVirtualAccountNoListDto moscVirtlBnkacnNoListInfo(String ncn, String ctn, String custId) throws SocketTimeoutException {
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "Y48");

        MpVirtualAccountNoListDto vo = new MpVirtualAccountNoListDto();
        if ("LOCAL".equals(serverLocation)) {
            getVoEtc("Y48", vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /*
     * 테스트를 위해 정상적인 리턴을 강제로 넘겨준다.
     */
    private boolean getVo(int param, CommonXmlVO vo) {
        boolean result = true;
        //////////////////////////////////
        StringBuffer responseXml = new StringBuffer();

        responseXml.append(
            "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscPerInfoResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\">");

        switch (param) {
            case 1://가입정보조회------
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>bluemoor9521@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return>");
                break;
            case 2://청구지주소변경
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>bluemoor9521@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return>");
                break;
            case 3://e-mail청구서조회------
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X03</appEventCd><appSendDateTime>20160112154248</appSendDateTime><appRecvDateTime>20160112154245</appRecvDateTime><appLgDateTime>20160112154245</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112154242512</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outApplyChgDto><currentState>e-Mail명세서 등록 상태</currentState><ecRcvAgreYn>Y</ecRcvAgreYn><email>bluemoor9521@naver.com</email><option>true</option><securMailYn>Y</securMailYn><sendGubun>Y</sendGubun><svcMsg></svcMsg></outApplyChgDto><outApplyDto><ecRcvAgreYn></ecRcvAgreYn><email></email><safetyEmailFlag>B</safetyEmailFlag><securMailYn></securMailYn><sendGubun></sendGubun><status>1</status></outApplyDto><outChgDto><ecRcvAgreYn>Y</ecRcvAgreYn><effectiveDate></effectiveDate><email></email><giroGubun>2</giroGubun><msgGubun></msgGubun><oriEcRcvAgreYn>Y</oriEcRcvAgreYn><oriSecurMailYn>Y</oriSecurMailYn><oriSendGubun>1</oriSendGubun><orieMail>bluemoor9521@naver.com</orieMail><securMailYn>Y</securMailYn><sendGubun></sendGubun><status>9</status></outChgDto><outOrgDto><option>true</option><orgEmail>bluemoor9521@naver.com</orgEmail></outOrgDto><outTermDto><email></email><status></status></outTermDto></outDto></return>");
                break;
            case 4://email청구서변경
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X04</appEventCd><appSendDateTime>20160114110952</appSendDateTime><appRecvDateTime>20160114110947</appRecvDateTime><appLgDateTime>20160114110947</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114110943883</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X04</appEventCd><appSendDateTime>20160114113711</appSendDateTime><appRecvDateTime>20160114113711</appRecvDateTime><appLgDateTime>20160114113711</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114113708462</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_COM_E0003</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>[inDto.status] 항목에 [2]값은 허용되지 않은 값입니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 10://종이청구서조회------
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd></appAgncCd><appEventCd>X10</appEventCd><appSendDateTime>20160112155236</appSendDateTime><appRecvDateTime>20160112155233</appRecvDateTime><appLgDateTime>20160112155233</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112155230089</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><arBalance>0</arBalance><ban>1111</ban><disableFlag>disabled</disableFlag><option>true</option><outItemBillDto><amt>0</amt><billDate>201512201601</billDate></outItemBillDto><outItemBillDto><amt>0</amt><billDate>1111</billDate></outItemBillDto><outItemBillDto><amt>0</amt><billDate>1111</billDate></outItemBillDto><outReqReDto><requestReason>NR</requestReason></outReqReDto><outReqReDto><requestReason>AC</requestReason></outReqReDto><outSndGuDto><sendGubun>F</sendGubun></outSndGuDto><outSndGuDto><sendGubun>R</sendGubun></outSndGuDto><zipCode></zipCode><zipCode1></zipCode1><pAddr></pAddr><sAddr></sAddr></outDto></return>");
                break;
            case 12://총통화시간조회------
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X12</appEventCd><appSendDateTime>20241218113150</appSendDateTime><appRecvDateTime>20241218113149</appRecvDateTime><appLgDateTime>20241218113149</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020241218113148852</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><totUseTimeCntDto><strFreeSmsCur>999999999</strFreeSmsCur><strFreeSmsRemain>999999971</strFreeSmsRemain><strFreeSmsRoll>0</strFreeSmsRoll><strFreeSmsTotal>999999999</strFreeSmsTotal><strFreeSmsUse>28</strFreeSmsUse><strSvcNameSms>통화 맘껏 15GB</strSvcNameSms></totUseTimeCntDto><totUseTimeCntTotDto><strFreeSmsCur>999999999</strFreeSmsCur><strFreeSmsRemain>999999971</strFreeSmsRemain><strFreeSmsRoll>0</strFreeSmsRoll><strFreeSmsTotal>999999999</strFreeSmsTotal><strFreeSmsUse>28</strFreeSmsUse><total>합계</total></totUseTimeCntTotDto><totalUseTimeDto><strBunGun>V</strBunGun><strCtnSecs>25301</strCtnSecs><strFreeMinCur>999999999</strFreeMinCur><strFreeMinRemain>999971733</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>999999999</strFreeMinTotal><strFreeMinUse>28266</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>음성</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>V</strBunGun><strCtnSecs>2965</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>HD보이스</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>V</strBunGun><strCtnSecs>207</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>음성(부가)</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>U</strBunGun><strCtnSecs>0</strCtnSecs><strFreeMinCur>1800</strFreeMinCur><strFreeMinRemain>1593</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>1800</strFreeMinTotal><strFreeMinUse>207</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>영상/부가</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>D</strBunGun><strCtnSecs>23</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>SMS</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>D</strBunGun><strCtnSecs>5</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>MMS</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>D</strBunGun><strCtnSecs>0</strCtnSecs><strFreeMinCur>999999999</strFreeMinCur><strFreeMinRemain>999999971</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>999999999</strFreeMinTotal><strFreeMinUse>28</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>SMS/MMS</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>P</strBunGun><strCtnSecs>9818022</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>데이터-LTE</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>P</strBunGun><strCtnSecs>9818022</strCtnSecs><strFreeMinCur>31457280</strFreeMinCur><strFreeMinRemain>21639258</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>31457280</strFreeMinTotal><strFreeMinUse>9818022</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>데이터-합계</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>D</strBunGun><strCtnSecs>24</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>국제로밍MMS</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>V</strBunGun><strCtnSecs>39</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>71</strSecsToAmt><strSecsToRate>39</strSecsToRate><strSvcName>국제음성</strSvcName></totalUseTimeDto><totalUseTimeDto><strBunGun>P</strBunGun><strCtnSecs>0</strCtnSecs><strFreeMinCur>0</strFreeMinCur><strFreeMinRemain>0</strFreeMinRemain><strFreeMinRoll></strFreeMinRoll><strFreeMinTotal>0</strFreeMinTotal><strFreeMinUse>0</strFreeMinUse><strSecsToAmt>0</strSecsToAmt><strSecsToRate>0</strSecsToRate><strSvcName>국제데이터</strSvcName></totalUseTimeDto><voiceCallDetailDto><strBunGun>V</strBunGun><strFreeMinCur>999999999</strFreeMinCur><strFreeMinRemain>999971733</strFreeMinRemain><strFreeMinRoll>0</strFreeMinRoll><strFreeMinTotal>999999999</strFreeMinTotal><strFreeMinUse>28266</strFreeMinUse><strSvcName>통화 맘껏 15GB</strSvcName></voiceCallDetailDto><voiceCallDetailTotDto><tottal>합계</tottal><iFreeMinCurSum>999999999</iFreeMinCurSum><iFreeMinRemainSum>999971733</iFreeMinRemainSum><iFreeMinRollSum>0</iFreeMinRollSum><iFreeMinTotalSum>999999999</iFreeMinTotalSum><iFreeMinUseSum>28266</iFreeMinUseSum></voiceCallDetailTotDto></outDto></return>");
                responseXml.append("<return>");
                responseXml.append("	<bizHeader>");
                responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                responseXml.append("		<appEventCd>X12</appEventCd>");
                responseXml.append("		<appSendDateTime>20251126152302</appSendDateTime>");
                responseXml.append("		<appRecvDateTime>20251126152301</appRecvDateTime>");
                responseXml.append("		<appLgDateTime>20251126152301</appLgDateTime>");
                responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("		<appOrderId></appOrderId>");
                responseXml.append("	</bizHeader>");
                responseXml.append("	<commHeader>");
                responseXml.append("		<globalNo>9122533020251126152301337</globalNo>");
                responseXml.append("		<encYn></encYn>");
                responseXml.append("		<responseType>N</responseType>");
                responseXml.append("		<responseCode></responseCode>");
                responseXml.append("		<responseLogcd></responseLogcd>");
                responseXml.append("		<responseTitle></responseTitle>");
                responseXml.append("		<responseBasic></responseBasic>");
                responseXml.append("		<langCode></langCode>");
                responseXml.append("		<filler></filler>");
                responseXml.append("	</commHeader>");
                responseXml.append("	<outDto>");
                responseXml.append("		<totUseTimeCntDto>");
                responseXml.append("			<strFreeSmsCur>999999999</strFreeSmsCur>");
                responseXml.append("			<strFreeSmsRemain>999999998</strFreeSmsRemain>");
                responseXml.append("			<strFreeSmsRoll>0</strFreeSmsRoll>");
                responseXml.append("			<strFreeSmsTotal>999999999</strFreeSmsTotal>");
                responseXml.append("			<strFreeSmsUse>1</strFreeSmsUse>");
                responseXml.append("			<strSvcNameSms>모두다 맘껏 안심 1.5GB+</strSvcNameSms>");
                responseXml.append("		</totUseTimeCntDto>");
                responseXml.append("		<totUseTimeCntTotDto>");
                responseXml.append("			<strFreeSmsCur>999999999</strFreeSmsCur>");
                responseXml.append("			<strFreeSmsRemain>999999998</strFreeSmsRemain>");
                responseXml.append("			<strFreeSmsRoll>0</strFreeSmsRoll>");
                responseXml.append("			<strFreeSmsTotal>999999999</strFreeSmsTotal>");
                responseXml.append("			<strFreeSmsUse>1</strFreeSmsUse>");
                responseXml.append("			<total>합계</total>");
                responseXml.append("		</totUseTimeCntTotDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>V</strBunGun>");
                responseXml.append("			<strCtnSecs>753</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>999999999</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>999999246</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>999999999</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>753</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>음성</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>U</strBunGun>");
                responseXml.append("			<strCtnSecs>0</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>1800</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>1800</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>1800</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>0</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>영상/부가</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>D</strBunGun>");
                responseXml.append("			<strCtnSecs>1</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>0</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>0</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>0</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>0</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>SMS</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>D</strBunGun>");
                responseXml.append("			<strCtnSecs>0</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>999999999</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>999999998</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>999999999</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>1</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>SMS/MMS</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>P</strBunGun>");
                responseXml.append("			<strCtnSecs>6403788</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>0</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>0</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>0</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>0</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>데이터-LTE</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>P</strBunGun>");
                responseXml.append("			<strCtnSecs>6403788</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>3145728</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>0</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>3145728</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>3145728</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>데이터-합계</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<totalUseTimeDto>");
                responseXml.append("			<strBunGun>P</strBunGun>");
                responseXml.append("			<strCtnSecs>0</strCtnSecs>");
                responseXml.append("			<strFreeMinCur>999999999</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>996741939</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll></strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>999999999</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>3258060</strFreeMinUse>");
                responseXml.append("			<strSecsToAmt>0</strSecsToAmt>");
                responseXml.append("			<strSecsToRate>0</strSecsToRate>");
                responseXml.append("			<strSvcName>속도제어(QoS)데이터-합계</strSvcName>");
                responseXml.append("		</totalUseTimeDto>");
                responseXml.append("		<voiceCallDetailDto>");
                responseXml.append("			<strBunGun>V</strBunGun>");
                responseXml.append("			<strFreeMinCur>999999999</strFreeMinCur>");
                responseXml.append("			<strFreeMinRemain>999999246</strFreeMinRemain>");
                responseXml.append("			<strFreeMinRoll>0</strFreeMinRoll>");
                responseXml.append("			<strFreeMinTotal>999999999</strFreeMinTotal>");
                responseXml.append("			<strFreeMinUse>753</strFreeMinUse>");
                responseXml.append("			<strSvcName>모두다 맘껏 안심 1.5GB+</strSvcName>");
                responseXml.append("		</voiceCallDetailDto>");
                responseXml.append("		<voiceCallDetailTotDto>");
                responseXml.append("			<tottal>합계</tottal>");
                responseXml.append("			<iFreeMinCurSum>999999999</iFreeMinCurSum>");
                responseXml.append("			<iFreeMinRemainSum>999999246</iFreeMinRemainSum>");
                responseXml.append("			<iFreeMinRollSum>0</iFreeMinRollSum>");
                responseXml.append("			<iFreeMinTotalSum>999999999</iFreeMinTotalSum>");
                responseXml.append("			<iFreeMinUseSum>753</iFreeMinUseSum>");
                responseXml.append("		</voiceCallDetailTotDto>");
                responseXml.append("	</outDto>");
                responseXml.append("</return>");

                break;
            case 15://요금조회------201512
                //			responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X15</appEventCd><appSendDateTime>20160119160359</appSendDateTime><appRecvDateTime>20160119160358</appRecvDateTime><appLgDateTime>20160119160358</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160119160357902</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><ban>976563580</ban><billEndDateList>20151231|20151130|20151031|20150930|20150831|20150731</billEndDateList><billSeqNumList>6|5|4|3|2|1</billSeqNumList><billStartDateList>20151201|20151101|20151001|20150901|20150801|20150715</billStartDateList><ctnNumTotalSum>0.0</ctnNumTotalSum><ctnNumproductionDate>201512|201511|201510|201509|201508|201507</ctnNumproductionDate><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20151231</billEndDate><billMonth>20160106</billMonth><billSeqNo>6</billSeqNo><billStartDate>20151201</billStartDate><pastDueAmt>28690</pastDueAmt><thisMonth>26540</thisMonth><totalDueAmt>55230</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20151130</billEndDate><billMonth>20151204</billMonth><billSeqNo>5</billSeqNo><billStartDate>20151101</billStartDate><pastDueAmt>0</pastDueAmt><thisMonth>28690</thisMonth><totalDueAmt>28690</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20151031</billEndDate><billMonth>20151104</billMonth><billSeqNo>4</billSeqNo><billStartDate>20151001</billStartDate><pastDueAmt>0</pastDueAmt><thisMonth>22880</thisMonth><totalDueAmt>22880</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20150930</billEndDate><billMonth>20151004</billMonth><billSeqNo>3</billSeqNo><billStartDate>20150901</billStartDate><pastDueAmt>0</pastDueAmt><thisMonth>25280</thisMonth><totalDueAmt>25280</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20150831</billEndDate><billMonth>20150904</billMonth><billSeqNo>2</billSeqNo><billStartDate>20150801</billStartDate><pastDueAmt>-10080</pastDueAmt><thisMonth>25280</thisMonth><totalDueAmt>15200</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20160106|20151204|20151104|20151004|20150904|20150804</billDueDateList><billEndDate>20150731</billEndDate><billMonth>20150804</billMonth><billSeqNo>1</billSeqNo><billStartDate>20150715</billStartDate><pastDueAmt>-30000</pastDueAmt><thisMonth>19970</thisMonth><totalDueAmt>-10030</totalDueAmt></payMentDTO></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X15</appEventCd><appSendDateTime>20220810010036</appSendDateTime><appRecvDateTime>20220810010035</appRecvDateTime><appLgDateTime>20220810010035</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220810010125312</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><ban>94028246820</ban><billEndDateList>20220630|20220531</billEndDateList><billSeqNumList></billSeqNumList><billStartDateList>20220601|20220506</billStartDateList><ctnNumTotalSum>0.0</ctnNumTotalSum><ctnNumproductionDate>202206|202205</ctnNumproductionDate><payMentDTO><billDueDateList>20220705|20220605</billDueDateList><billEndDate>20220630</billEndDate><billMonth>20220705</billMonth><billSeqNo>0</billSeqNo><billStartDate>20220601</billStartDate><pastDueAmt>0</pastDueAmt><thisMonth>138780</thisMonth><totalDueAmt>138780</totalDueAmt></payMentDTO><payMentDTO><billDueDateList>20220705|20220605</billDueDateList><billEndDate>20220531</billEndDate><billMonth>20220605</billMonth><billSeqNo>0</billSeqNo><billStartDate>20220506</billStartDate><pastDueAmt>0</pastDueAmt><thisMonth>126070</thisMonth><totalDueAmt>126070</totalDueAmt></payMentDTO></outDto></return>");
                break;
            case 16://요금조회상세------17, 20151201|20151101|20151001|20150901|20150801|20150701|, 201512, 20151201, 20151231
                //			responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X16</appEventCd><appSendDateTime>20160112160926</appSendDateTime><appRecvDateTime>20160112160925</appRecvDateTime><appLgDateTime>20160112160925</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112160919038</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><dateView>201512|201511|201510|201509|201508|201507</dateView><detListDto><actvAmt>18000</actvAmt><billSeqNo>17</billSeqNo><messageLine>MA000</messageLine><splitDescription>월정액</splitDescription></detListDto><detListDto><actvAmt>1800</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>할인전부가세(세금)</splitDescription></detListDto><detListDto><actvAmt>19800</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>당월 요금</splitDescription></detListDto><detListDto><actvAmt>0</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>미납요금</splitDescription></detListDto><detListDto><actvAmt>19800</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>납부하실 금액</splitDescription></detListDto><useDate>1201~1231</useDate></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X16</appEventCd><appSendDateTime>20220810010036</appSendDateTime><appRecvDateTime>20220810010036</appRecvDateTime><appLgDateTime>20220810010036</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220810010125881</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><dateView>202207|202206</dateView><detListDto><actvAmt>12000</actvAmt><billSeqNo></billSeqNo><messageLine>GMMA0000</messageLine><splitDescription>월정액   ▶ 초알뜰 1GB/60분</splitDescription></detListDto><detListDto><actvAmt>134890</actvAmt><billSeqNo></billSeqNo><messageLine>GMPY0000</messageLine><splitDescription>소액결제</splitDescription></detListDto><detListDto><actvAmt>-8</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>10원미만할인요금</splitDescription></detListDto><detListDto><actvAmt>1200</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>부가가치세</splitDescription></detListDto><detListDto><actvAmt>138780</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>당월요금계</splitDescription></detListDto><detListDto><actvAmt>138780</actvAmt><billSeqNo></billSeqNo><messageLine></messageLine><splitDescription>납부하실 금액</splitDescription></detListDto><detListDto><actvAmt>-9302</actvAmt><billSeqNo></billSeqNo><messageLine>DISCBYSVC</messageLine><splitDescription>할인요금</splitDescription></detListDto><useDate>0601~0630</useDate></outDto></return>");
                break;
            case 17://요금항목별조회------17, 201512, DCNOR
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X17</appEventCd><appSendDateTime>20160112161437</appSendDateTime><appRecvDateTime>20160112161436</appRecvDateTime><appLgDateTime>20160112161436</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112161432843</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><amt>0</amt><descr>요금할인액 부가세(세금)</descr></outDto></return>");
                break;
            case 171://요금항목별조회test1------13, 201603, DCNOR  //할인전부가세
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X17</appEventCd><appSendDateTime>20160302120743</appSendDateTime><appRecvDateTime>20160302120742</appRecvDateTime><appLgDateTime>20160302120742</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160302120741935</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><amt>900</amt><descr>요금할인액 부가세(세금)</descr></outDto><outDto><amt>9000</amt><descr>요금할인-알뜰폰</descr></outDto></return>");//
                break;
            case 172://요금항목별조회test2------13, 201603, PY000  //소액결제
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X17</appEventCd><appSendDateTime>20160302121054</appSendDateTime><appRecvDateTime>20160302121053</appRecvDateTime><appLgDateTime>20160302121053</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160302121053221</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><amt>1000</amt><descr>실물결제(다날)</descr></outDto><outDto><amt>2000</amt><descr>실물결제(test)</descr></outDto></return>");
                break;
            case 173://요금항목별조회test3------13, 201603, MA000  //월정액
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X17</appEventCd><appSendDateTime>20160302121232</appSendDateTime><appRecvDateTime>20160302121231</appRecvDateTime><appLgDateTime>20160302121231</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160302121231148</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><amt>35000</amt><descr>월정액</descr></outDto></return>");
                break;
            case 18://실시간요금조회------
                //			responseXml.append("response massage is null.");//비정상
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X18</appEventCd><appSendDateTime>20160112161906</appSendDateTime><appRecvDateTime>20160112161905</appRecvDateTime><appLgDateTime>20160112161905</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112161901518</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><amntDto><gubun>월정액</gubun><payMent>6387</payMent></amntDto><amntDto><gubun>부가세</gubun><payMent>638</payMent></amntDto><amntDto><gubun>원단위절사금액</gubun><payMent>-5</payMent></amntDto><amntDto><gubun>당월요금계</gubun><payMent>7025</payMent></amntDto><searchDay>20160112</searchDay><searchTime>0101~0112</searchTime></outDto></return>");
                break;
            case 19://요금상품변경
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20160114133336</appSendDateTime><appRecvDateTime>20160114133334</appRecvDateTime><appLgDateTime>20160114133334</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114133330197</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20160114133336</appSendDateTime><appRecvDateTime>20160114133334</appRecvDateTime><appLgDateTime>20160114133334</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114133330197</globalNo><encYn></encYn><responseType>S</responseType><responseCode>ITL_SYS_E9999</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>M-PLATFORM SYSTEM ERROR.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 20://가입중부가서비스조회------
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X20</appEventCd><appSendDateTime>20220331105448</appSendDateTime><appRecvDateTime>20220331105446</appRecvDateTime><appLgDateTime>20220331105446</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220331105042626</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outDto><effectiveDate>20211110144758</effectiveDate><prodHstSeq>300000783571478</prodHstSeq><soc>PL19AS353</soc><socDescription>M 요금할인 5000(VAT포함)</socDescription><socRateValue>-4,546 WON</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731236</prodHstSeq><soc>MPAYBLOCK</soc><socDescription>휴대폰결제 이용거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731240</prodHstSeq><soc>NOSPAM3</soc><socDescription>정보제공사업자번호차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210115101023</effectiveDate><prodHstSeq>300000645764018</prodHstSeq><soc>LTECERTID</soc><socDescription>LTE_인증상품</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731239</prodHstSeq><soc>WVMS</soc><socDescription>통합사서함</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210730111701</effectiveDate><prodHstSeq>300000736324810</prodHstSeq><soc>VLTEAUTSV</soc><socDescription>HD 보이스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20220331160000</effectiveDate><prodHstSeq>300000845621282</prodHstSeq><soc>PL2078760</soc><socDescription>로밍 하루종일ON</socDescription><socRateValue>10,000 WON</socRateValue></outDto><outDto><effectiveDate>20210728154003</effectiveDate><prodHstSeq>300000735456700</prodHstSeq><soc>SMARTNMON</soc><socDescription>스마트폰(종량)-일반</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731242</prodHstSeq><soc>SPMFILTER</soc><socDescription>스팸차단서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20211110144758</effectiveDate><prodHstSeq>300000783571477</prodHstSeq><soc>PL19AS352</soc><socDescription>M 요금할인 3000(VAT포함)</socDescription><socRateValue>-2,728 WON</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731241</prodHstSeq><soc>SMSB</soc><socDescription>SMS(문자서비스) 기본제공</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210730111701</effectiveDate><prodHstSeq>300000736324809</prodHstSeq><soc>PSVTAUTSV</soc><socDescription>HD 영상통화</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731237</prodHstSeq><soc>CLIPF</soc><socDescription>발신번호표시무료</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210401085835</effectiveDate><prodHstSeq>300000682894264</prodHstSeq><soc>LTEULTDC5</soc><socDescription>LTE안심QoS옵션 프로모션할인</socDescription><socRateValue>-5,000 WON</socRateValue></outDto></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X20</appEventCd><appSendDateTime>20220330185419</appSendDateTime><appRecvDateTime>20220330185417</appRecvDateTime><appLgDateTime>20220330185417</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220330185015020</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outDto><effectiveDate>20211110144758</effectiveDate><prodHstSeq>300000783571478</prodHstSeq><soc>PL19AS353</soc><socDescription>M 요금할인 5000(VAT포함)</socDescription><socRateValue>-4,546 WON</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731236</prodHstSeq><soc>MPAYBLOCK</soc><socDescription>휴대폰결제 이용거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731240</prodHstSeq><soc>NOSPAM3</soc><socDescription>정보제공사업자번호차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210115101023</effectiveDate><prodHstSeq>300000645764018</prodHstSeq><soc>LTECERTID</soc><socDescription>LTE_인증상품</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20220330220000</effectiveDate><prodHstSeq>300000845143353</prodHstSeq><soc>PL2078760</soc><socDescription>로밍 하루종일ON</socDescription><socRateValue>10,000 WON</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731239</prodHstSeq><soc>WVMS</soc><socDescription>통합사서함</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210730111701</effectiveDate><prodHstSeq>300000736324810</prodHstSeq><soc>VLTEAUTSV</soc><socDescription>HD 보이스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210728154003</effectiveDate><prodHstSeq>300000735456700</prodHstSeq><soc>SMARTNMON</soc><socDescription>스마트폰(종량)-일반</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731242</prodHstSeq><soc>SPMFILTER</soc><socDescription>스팸차단서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20211110144758</effectiveDate><prodHstSeq>300000783571477</prodHstSeq><soc>PL19AS352</soc><socDescription>M 요금할인 3000(VAT포함)</socDescription><socRateValue>-2,728 WON</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731241</prodHstSeq><soc>SMSB</soc><socDescription>SMS(문자서비스) 기본제공</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210730111701</effectiveDate><prodHstSeq>300000736324809</prodHstSeq><soc>PSVTAUTSV</soc><socDescription>HD 영상통화</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731237</prodHstSeq><soc>CLIPF</soc><socDescription>발신번호표시무료</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20210401085835</effectiveDate><prodHstSeq>300000682894264</prodHstSeq><soc>LTEULTDC5</soc><socDescription>LTE안심QoS옵션 프로모션할인</socDescription><socRateValue>-5,000 WON</socRateValue></outDto></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X20</appEventCd><appSendDateTime>20160406161559</appSendDateTime><appRecvDateTime>20160406161557</appRecvDateTime><appLgDateTime>20160406161557</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160406161556269</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outDto><effectiveDate>20160328134219</effectiveDate><soc>DTPLSU100</soc><socDescription>데이터플러스 m(결합) 100M</socDescription><socRateValue>5,000 WON</socRateValue></outDto><outDto><effectiveDate>20160328134233</effectiveDate><soc>DTPLSU500</soc><socDescription>데이터플러스 m(결합) 500M</socDescription><socRateValue>10,000 WON</socRateValue></outDto><outDto><effectiveDate>20160323030009</effectiveDate><soc>RCC1</soc><socDescription>통화가능알리미</socDescription><socRateValue>500 WON</socRateValue></outDto><outDto><effectiveDate>20160323094134</effectiveDate><soc>INTLIST</soc><socDescription>국제통화내역통보</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>NESPFMCD3</soc><socDescription>olleh WiFi싱글(무료)</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>VLTEAUTSV</soc><socDescription>HD 보이스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>PSVTAUTSV</soc><socDescription>HD 영상통화</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135921</effectiveDate><soc>WIFISGLM4</soc><socDescription>WiFi 싱글 할인M6</socDescription><socRateValue>-6,000 WON</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>WVMS</soc><socDescription>통합사서함</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328134206</effectiveDate><soc>DTPLSU02G</soc><socDescription>데이터플러스 m(결합) 2G</socDescription><socRateValue>20,000 WON</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>NOSPAM3</soc><socDescription>정보제공사업자번호차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135241</effectiveDate><soc>RCC1R</soc><socDescription>통화가능알리미 거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135427</effectiveDate><soc>SMS26N</soc><socDescription>신메시지매니저</socDescription><socRateValue>900 WON</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>SMSB</soc><socDescription>SMS(문자서비스) 기본제공</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135627</effectiveDate><soc>USEBILSMS</soc><socDescription>이용요금내역알리미</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160322224830</effectiveDate><soc>CYBDANGNT</soc><socDescription>정보보호알림이(일반)</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323040140</effectiveDate><soc>ITC</soc><socDescription>국제전화 발신제한</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323025931</effectiveDate><soc>LOC119</soc><socDescription>119 긴급구조 위치제공</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323134617</effectiveDate><soc>MMCISS</soc><socDescription>쇼미</socDescription><socRateValue>900 WON</socRateValue></outDto><outDto><effectiveDate>20160323134748</effectiveDate><soc>NOIPCRVE</soc><socDescription>음성로밍 완전 차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323134455</effectiveDate><soc>RCC2</soc><socDescription>통화요구알리미</socDescription><socRateValue>500 WON</socRateValue></outDto><outDto><effectiveDate>20160323040102</effectiveDate><soc>CNIRDO</soc><socDescription>익명호수신거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328134003</effectiveDate><soc>CNIRS</soc><socDescription>발신번호표시제한</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>MPAYBLOCK</soc><socDescription>휴대폰결제 이용거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>SPMFILTER</soc><socDescription>스팸차단서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160322224737</effectiveDate><soc>WFSMSNDSP</soc><socDescription>웹 및 국외발신 미표시 서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135909</effectiveDate><soc>WIFISGLM3</soc><socDescription>WiFi 싱글 M3</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160325200008</effectiveDate><soc>XRINGSMS</soc><socDescription>링투유알리미</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>CLIPF</soc><socDescription>발신번호표시무료</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328134153</effectiveDate><soc>DTPLSU01G</soc><socDescription>데이터플러스 m(결합) 1G</socDescription><socRateValue>15,000 WON</socRateValue></outDto><outDto><effectiveDate>20160323065520</effectiveDate><soc>NOIPCRDT</soc><socDescription>데이터로밍 완전 차단</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135150</effectiveDate><soc>PPINFO</soc><socDescription>요금납부알림서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>SMARTNMON</soc><socDescription>스마트폰(종량)-일반</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160328135933</effectiveDate><soc>WIRELESSC</soc><socDescription>무선데이터차단서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>AIPNESPOT</soc><socDescription>WiFi 인증서비스</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323065714</effectiveDate><soc>CATCHCALL</soc><socDescription>캐치콜서비스</socDescription><socRateValue>500 WON</socRateValue></outDto><outDto><effectiveDate>20160323025938</effectiveDate><soc>DPCBLC060</soc><socDescription>060발신차단서비스(무료)</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160208150750</effectiveDate><soc>LTECERTID</soc><socDescription>LTE_인증상품</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20160323135150</effectiveDate><soc>XRING</soc><socDescription>링투유</socDescription><socRateValue>900 WON</socRateValue></outDto></outDto></return>");
                //                responseXml.append("<return>");
                //                responseXml.append("	<bizHeader>");
                //                responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                //                responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                //                responseXml.append("		<appEventCd>X20</appEventCd>");
                //                responseXml.append("		<appSendDateTime>20220331105448</appSendDateTime>");
                //                responseXml.append("		<appRecvDateTime>20220331105446</appRecvDateTime>");
                //                responseXml.append("		<appLgDateTime>20220331105446</appLgDateTime>");
                //                responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
                //                responseXml.append("		<appOrderId></appOrderId>");
                //                responseXml.append("	</bizHeader>");
                //                responseXml.append("	<commHeader>");
                //                responseXml.append("		<globalNo>9122533020220331105042626</globalNo>");
                //                responseXml.append("		<encYn></encYn>");
                //                responseXml.append("		<responseType>N</responseType>");
                //                responseXml.append("		<responseCode></responseCode>");
                //                responseXml.append("		<responseLogcd></responseLogcd>");
                //                responseXml.append("		<responseTitle></responseTitle>");
                //                responseXml.append("		<responseBasic></responseBasic>");
                //                responseXml.append("		<langCode></langCode>");
                //                responseXml.append("		<filler></filler>");
                //                responseXml.append("	</commHeader>");
                //                responseXml.append("	<outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731236</prodHstSeq>");
                //                responseXml.append("			<soc>MPAYBLOCK</soc>");
                //                responseXml.append("			<socDescription>휴대폰결제 이용거부</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731240</prodHstSeq>");
                //                responseXml.append("			<soc>NOSPAM3</soc>");
                //                responseXml.append("			<socDescription>정보제공사업자번호차단</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20210115101023</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000645764018</prodHstSeq>");
                //                responseXml.append("			<soc>LTECERTID</soc>");
                //                responseXml.append("			<socDescription>LTE_인증상품</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731239</prodHstSeq>");
                //                responseXml.append("			<soc>WVMS</soc>");
                //                responseXml.append("			<socDescription>통합사서함</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20210730111701</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000736324810</prodHstSeq>");
                //                responseXml.append("			<soc>VLTEAUTSV</soc>");
                //                responseXml.append("			<socDescription>HD 보이스</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20220331160000</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000845621282</prodHstSeq>");
                //                responseXml.append("			<soc>PL2078760</soc>");
                //                responseXml.append("			<socDescription>로밍 하루종일ON</socDescription>");
                //                responseXml.append("			<socRateValue>10,000 WON</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20210728154003</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000735456700</prodHstSeq>");
                //                responseXml.append("			<soc>SMARTNMON</soc>");
                //                responseXml.append("			<socDescription>스마트폰(종량)-일반</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731242</prodHstSeq>");
                //                responseXml.append("			<soc>SPMFILTER</soc>");
                //                responseXml.append("			<socDescription>스팸차단서비스</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20211110144758</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000783571477</prodHstSeq>");
                //                responseXml.append("			<soc>PL19AS352</soc>");
                //                responseXml.append("			<socDescription>M 요금할인 3000(VAT포함)</socDescription>");
                //                responseXml.append("			<socRateValue>-2,728 WON</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731241</prodHstSeq>");
                //                responseXml.append("			<soc>SMSB</soc>");
                //                responseXml.append("			<socDescription>SMS(문자서비스) 기본제공</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20210730111701</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000736324809</prodHstSeq>");
                //                responseXml.append("			<soc>PSVTAUTSV</soc>");
                //                responseXml.append("			<socDescription>HD 영상통화</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("		<outDto>");
                //                responseXml.append("			<effectiveDate>20191031174605</effectiveDate>");
                //                responseXml.append("			<prodHstSeq>300000444731237</prodHstSeq>");
                //                responseXml.append("			<soc>CLIPF</soc>");
                //                responseXml.append("			<socDescription>발신번호표시무료</socDescription>");
                //                responseXml.append("			<socRateValue>Free</socRateValue>");
                //                responseXml.append("		</outDto>");
                //                responseXml.append("	</outDto>");
                //                responseXml.append("</return>");

                break;
            case 21://부가서비스신청
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>INL</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20240311145506</appSendDateTime><appRecvDateTime>20240311145501</appRecvDateTime><appLgDateTime>20240311145501</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9911100201502061201011234</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20220311145506</appSendDateTime><appRecvDateTime>20220311145501</appRecvDateTime><appLgDateTime>20220311145501</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220311145132712</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E021</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>[오토링]상품과 [링투유] 상품은 동시에 가입할 수 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 22://납부/미납요금조회------

                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X22</appEventCd><appSendDateTime>20220810010038</appSendDateTime><appRecvDateTime>20220810010037</appRecvDateTime><appLgDateTime>20220810010037</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220810010126507</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><noDate>현재 미납된 요금이 없습니다.</noDate><outItemPayDto><confirmDate>20220129</confirmDate><payMentDate>20220129</payMentDate><payMentMethod>간편결제</payMentMethod><payMentMoney>803</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220229</confirmDate><payMentDate>20220229</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220328</confirmDate><payMentDate>20220328</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220427</confirmDate><payMentDate>20220427</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220526</confirmDate><payMentDate>20220526</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220625</confirmDate><payMentDate>20220625</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220724</confirmDate><payMentDate>20220724</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220823</confirmDate><payMentDate>20220823</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20220922</confirmDate><payMentDate>20220922</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20221021</confirmDate><payMentDate>20221021</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20221120</confirmDate><payMentDate>20221120</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto><outItemPayDto><confirmDate>20221219</confirmDate><payMentDate>20221219</payMentDate><payMentMethod>온라인 카드수납</payMentMethod><payMentMoney>5999</payMentMoney></outItemPayDto></outDto></return>");

                //            responseXml.append("<return>");
                //            responseXml.append("    <bizHeader>");
                //            responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                //            responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                //            responseXml.append("        <appEventCd>X22</appEventCd>");
                //            responseXml.append("        <appSendDateTime>20220616131515</appSendDateTime>");
                //            responseXml.append("        <appRecvDateTime>20220616131515</appRecvDateTime>");
                //            responseXml.append("        <appLgDateTime>20220616131515</appLgDateTime>");
                //            responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                //            responseXml.append("        <appOrderId></appOrderId>");
                //            responseXml.append("    </bizHeader>");
                //            responseXml.append("    <commHeader>");
                //            responseXml.append("        <globalNo>9122533020220616131544017</globalNo>");
                //            responseXml.append("        <encYn></encYn>");
                //            responseXml.append("        <responseType>S</responseType>");
                //            responseXml.append("        <responseCode>ITL_SYS_E0001</responseCode>");
                //            responseXml.append("        <responseLogcd></responseLogcd>");
                //            responseXml.append("        <responseTitle></responseTitle>");
                //            responseXml.append("        <responseBasic>NSTEP ESB 연동 오류.</responseBasic>");
                //            responseXml.append("        <langCode></langCode>");
                //            responseXml.append("        <filler></filler>");
                //            responseXml.append("    </commHeader>");
                //            responseXml.append("</return>");


                //        	responseXml.append("<return>");
                //            responseXml.append("	<bizHeader>");
                //            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                //            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                //            responseXml.append("		<appEventCd>X22</appEventCd>");
                //            responseXml.append("		<appSendDateTime>20160112162057</appSendDateTime>");
                //            responseXml.append("		<appRecvDateTime>20160112162055</appRecvDateTime>");
                //            responseXml.append("		<appLgDateTime>20160112162055</appLgDateTime>");
                //            responseXml.append("		<appNstepUserId>91060728</appNstepUserId>");
                //            responseXml.append("		<appOrderId></appOrderId>");
                //            responseXml.append("	</bizHeader>");
                //            responseXml.append("	<commHeader>");
                //            responseXml.append("		<globalNo>9106072820160112162055330</globalNo>");
                //            responseXml.append("		<encYn></encYn>");
                //            responseXml.append("		<responseType>N</responseType>");
                //            //responseXml.append("		<responseType>E</responseType>");
                //            responseXml.append("		<responseCode></responseCode>");
                //            //responseXml.append("		<responseCode>ITL_SYS_E001</responseCode>");
                //            responseXml.append("		<responseLogcd></responseLogcd>");
                //            responseXml.append("		<responseTitle></responseTitle>");
                //            responseXml.append("		<responseBasic></responseBasic>");
                //            responseXml.append("		<langCode></langCode>");
                //            responseXml.append("		<filler></filler>");
                //            responseXml.append("	</commHeader>");
                //            responseXml.append("	<outDto>");
                //            responseXml.append("		<noDate>현재 미납된 요금이 없습니다.</noDate>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20151221</confirmDate>");
                //            responseXml.append("			<payMentDate>20151221</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>19800</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20151123</confirmDate>");
                //            responseXml.append("			<payMentDate>20151123</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>19800</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20151021</confirmDate>");
                //            responseXml.append("			<payMentDate>20151021</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>19800</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20150921</confirmDate>");
                //            responseXml.append("			<payMentDate>20150921</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>19800</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20150821</confirmDate>");
                //            responseXml.append("			<payMentDate>20150821</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>19800</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("		<outItemPayDto>");
                //            responseXml.append("			<confirmDate>20150721</confirmDate>");
                //            responseXml.append("			<payMentDate>20150721</payMentDate>");
                //            responseXml.append("			<payMentMethod>은행계좌자동이체</payMentMethod>");
                //            responseXml.append("			<payMentMoney>20080</payMentMoney>");
                //            responseXml.append("		</outItemPayDto>");
                //            responseXml.append("	</outDto>");
                //            responseXml.append("</return>");
                break;

            case 23://납부방법조회------
                //자동이체
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X23</appEventCd><appSendDateTime>20160112162141</appSendDateTime><appRecvDateTime>20160112162138</appRecvDateTime><appLgDateTime>20160112162138</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112162138010</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><bankAcctHolderName>장기용</bankAcctHolderName><billCycleDueDay>21</billCycleDueDay><blBankAcctNo>177620**********</blBankAcctNo><blBankName>수협중앙회</blBankName><payMethod>자동이체</payMethod></outDto></return>");
                //지로
                //                responseXml.append("<return> <bizHeader> <appEntrPrsnId>KIS</appEntrPrsnId> <appAgncCd>AA00364</appAgncCd> <appEventCd>X23</appEventCd> <appSendDateTime>20160112162141</appSendDateTime> <appRecvDateTime>20160112162138</appRecvDateTime> <appLgDateTime>20160112162138</appLgDateTime> <appNstepUserId>91060728</appNstepUserId> <appOrderId></appOrderId> </bizHeader> <commHeader> <globalNo>9106072820160112162138010</globalNo> <encYn></encYn> <responseType>N</responseType> <responseCode></responseCode> <responseLogcd></responseLogcd> <responseTitle></responseTitle> <responseBasic></responseBasic> <langCode></langCode> <filler></filler> </commHeader> <outDto> <bankAcctHolderName>장기용</bankAcctHolderName> <billCycleDueDay>21</billCycleDueDay> <blBankAcctNo>177620**********</blBankAcctNo> <blBankName>수협중앙회</blBankName> <payMethod>지로</payMethod> <blAddr>서울시 삼성동 595-1</blAddr> </outDto> </return>   ");
                //신용카드
                responseXml.append(
                    "<return> <bizHeader> <appEntrPrsnId>KIS</appEntrPrsnId> <appAgncCd>AA00364</appAgncCd> <appEventCd>X23</appEventCd> <appSendDateTime>20160112162141</appSendDateTime> <appRecvDateTime>20160112162138</appRecvDateTime> <appLgDateTime>20160112162138</appLgDateTime> <appNstepUserId>91060728</appNstepUserId> <appOrderId></appOrderId> </bizHeader> <commHeader> <globalNo>9106072820160112162138010</globalNo> <encYn></encYn> <responseType>N</responseType> <responseCode></responseCode> <responseLogcd></responseLogcd> <responseTitle></responseTitle> <responseBasic></responseBasic> <langCode></langCode> <filler></filler> </commHeader> <outDto> <bankAcctHolderName>장기용</bankAcctHolderName> <payMethod>신용카드</payMethod> <billCycleDueDay>21</billCycleDueDay> <prevCardNo>5361489001011656</prevCardNo> <billCycleDueDay>99</billCycleDueDay> <prevExpirDt>20180221</prevExpirDt> <payTmsCd>02</payTmsCd></outDto> </return> ");
                //카카오
                //                responseXml.append("<return>");
                //                responseXml.append("    <bizHeader>");
                //                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                //                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                //                responseXml.append("        <appEventCd>X23</appEventCd>");
                //                responseXml.append("        <appSendDateTime>20210721105427</appSendDateTime>");
                //                responseXml.append("        <appRecvDateTime>20210721105422</appRecvDateTime>");
                //                responseXml.append("        <appLgDateTime>20210721105422</appLgDateTime>");
                //                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                //                responseXml.append("        <appOrderId></appOrderId>");
                //                responseXml.append("    </bizHeader>");
                //                responseXml.append("    <commHeader>");
                //                responseXml.append("        <globalNo>9122533020210721105308250</globalNo>");
                //                responseXml.append("        <encYn></encYn>");
                //                responseXml.append("        <responseType>N</responseType>");
                //                responseXml.append("        <responseCode></responseCode>");
                //                responseXml.append("        <responseLogcd></responseLogcd>");
                //                responseXml.append("        <responseTitle></responseTitle>");
                //                responseXml.append("        <responseBasic></responseBasic>");
                //                responseXml.append("        <langCode></langCode>");
                //                responseXml.append("        <filler></filler>");
                //                responseXml.append("    </commHeader>");
                //                responseXml.append("    <outDto>");
                //                responseXml.append("        <billCycleDueDay>25</billCycleDueDay>");
                //                responseXml.append("        <payBizrCd>KKO</payBizrCd>");
                //                responseXml.append("        <payMethod>간편결제</payMethod>");
                //                responseXml.append("    </outDto>");
                //                responseXml.append("</return>");
                break;
            case 25://납부방법변경
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>bluemoor9521@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return>");
                break;
            case 26://일시정지이력조회(X26)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KTF</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X26</appEventCd><appSendDateTime>20150210134545</appSendDateTime><appRecvDateTime>20150210134542</appRecvDateTime><appLgDateTime>20150210134542</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201501191201011234</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><bkInfoDto><colSusDays>20150113</colSusDays><csaActivityRsnDesc>고객요청 - 발신정지</csaActivityRsnDesc><remainOgDays>0</remainOgDays><remainSusCnt>20150113</remainSusCnt></bkInfoDto><colSusDays>0</colSusDays><expectSpDate/><reckonFromDate>20150101</reckonFromDate><remainOgDays>30</remainOgDays><remainSusCnt>2</remainSusCnt><subStatus>A</subStatus><susCnt>0</susCnt><susDays>0</susDays></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X27</appEventCd><appSendDateTime>20160112162621</appSendDateTime><appRecvDateTime>20160112162619</appRecvDateTime><appLgDateTime>20160112162619</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112162619104</globalNo><encYn></encYn><responseType>S</responseType><responseCode>ITL_SYS_E9999</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>M-PLATFORM SYSTEM ERROR.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 27://일시정지가능여부조회------오류발생
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X27</appEventCd><appSendDateTime>20160211144217</appSendDateTime><appRecvDateTime>20160211144213</appRecvDateTime><appLgDateTime>20160211144213</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160211144209462</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><ctnStatus>A</ctnStatus><insurMsg></insurMsg><rsltInd>Y</rsltInd><rsltMsg></rsltMsg></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X27</appEventCd><appSendDateTime>20160112162621</appSendDateTime><appRecvDateTime>20160112162619</appRecvDateTime><appLgDateTime>20160112162619</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112162619104</globalNo><encYn></encYn><responseType>S</responseType><responseCode>ITL_SYS_E9999</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>M-PLATFORM SYSTEM ERROR.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 28://일시정지해제 가능여부 조회
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KTF</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X28</appEventCd><appSendDateTime>20150210134918</appSendDateTime><appRecvDateTime>20150210134918</appRecvDateTime><appLgDateTime>20150210134918</appLgDateTime><appNstepUserId>6833564</appNstepUserId>appOrderId/></bizHeader><commHeader><globalNo>9911100201501191201011234</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><ctnStatus>A</ctnStatus><rsltInd>Y</rsltInd><rsltMsg/><rsnDesc>-</rsnDesc><sndarvStatCd>01</sndarvStatCd><subStatusDate>20180831133251</subStatusDate><subStatusRsnCode/></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X27</appEventCd><appSendDateTime>20160112162621</appSendDateTime><appRecvDateTime>20160112162619</appRecvDateTime><appLgDateTime>20160112162619</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112162619104</globalNo><encYn></encYn><responseType>S</responseType><responseCode>ITL_SYS_E9999</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>M-PLATFORM SYSTEM ERROR.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 29://일시정지신청
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X29</appEventCd><appSendDateTime>20160216193710</appSendDateTime><appRecvDateTime>20160216193704</appRecvDateTime><appLgDateTime>20160216193704</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160216193659192</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><commHeader><globalNo>9106072820160115153506272</globalNo><encYn/><responseType>S</responseType><responseCode>MCG_SYS_E0500</responseCode><responseLogcd/><responseTitle>시스템 오류</responseTitle><responseBasic>[서버] 시스템 내부처리 오류</responseBasic><langCode/><filler/></commHeader><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X29</appEventCd><appSendDateTime>20160115153506</appSendDateTime><appRecvDateTime/><appLgDateTime/><appNstepUserId>91060728</appNstepUserId><appOrderId/></bizHeader></return>");
                break;
            case 30://일시정지해제신청(X30)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KTF</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X02</appEventCd><appSendDateTime>20150210135222</appSendDateTime><appRecvDateTime>20150210135150</appRecvDateTime><appLgDateTime>20150210135150</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId /></bizHeader><commHeader><globalNo>9911100201501191201011234</globalNo><encYn /><responseType>N</responseType><responseCode /><responseLogcd /><responseTitle /><responseBasic /><langCode /><filler /></commHeader></return>");
                //responseXml.append("<return><commHeader><globalNo>9106072820160114172107306</globalNo><encYn/><responseType>S</responseType><responseCode>MCG_SYS_E0500</responseCode><responseLogcd/><responseTitle>시스템 오류</responseTitle><responseBasic>[서버] 시스템 내부처리 오류</responseBasic><langCode/><filler/></commHeader><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X31</appEventCd><appSendDateTime>20160114172107</appSendDateTime><appRecvDateTime/><appLgDateTime/><appNstepUserId>91060728</appNstepUserId><appOrderId/></bizHeader></return>");
                break;
            case 31://번호목록조회
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X31</appEventCd><appSendDateTime>20160129173453</appSendDateTime><appRecvDateTime>20160129173451</appRecvDateTime><appLgDateTime>20160129173451</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160129173446675</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><ctn>01029026316</ctn><marketGubun>KTF</marketGubun><sctn>scvQDLall75RExOhy2lPqg==</sctn></outDto><outDto><ctn>01029406316</ctn><marketGubun>KTF</marketGubun><sctn>dIRo+cQGphVRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029436316</ctn><marketGubun>KTF</marketGubun><sctn>iwbQp4UTNpdRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029616316</ctn><marketGubun>KTF</marketGubun><sctn>QsuTEwz+sLxRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029646316</ctn><marketGubun>KTF</marketGubun><sctn>at6V4IHV0f9RExOhy2lPqg==</sctn></outDto><outDto><ctn>01029706316</ctn><marketGubun>KTF</marketGubun><sctn>bJMzy6Tx79BRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029726316</ctn><marketGubun>KTF</marketGubun><sctn>S3t+vepjZdhRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029756316</ctn><marketGubun>KTF</marketGubun><sctn>8RJlmRaqD3ZRExOhy2lPqg==</sctn></outDto><outDto><ctn>01029916316</ctn><marketGubun>KTF</marketGubun><sctn>4IZDoF+0LrlRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030176316</ctn><marketGubun>KTF</marketGubun><sctn>j5FTR6MDNj5RExOhy2lPqg==</sctn></outDto><outDto><ctn>01030196316</ctn><marketGubun>KTF</marketGubun><sctn>ZODkAmLfECFRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030256316</ctn><marketGubun>KTF</marketGubun><sctn>iSB7BvrfFapRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030266316</ctn><marketGubun>KTF</marketGubun><sctn>wKVaFNVsVFJRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030276316</ctn><marketGubun>KTF</marketGubun><sctn>4+Oc56z1c15RExOhy2lPqg==</sctn></outDto><outDto><ctn>01030286316</ctn><marketGubun>KTF</marketGubun><sctn>IBKM0NMjO2RRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030296316</ctn><marketGubun>KTF</marketGubun><sctn>ZmRV9timo/dRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030416316</ctn><marketGubun>KTF</marketGubun><sctn>BO8Rw/PXOMhRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030426316</ctn><marketGubun>KTF</marketGubun><sctn>sBq/nnbZ/hNRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030436316</ctn><marketGubun>KTF</marketGubun><sctn>5C5U6KLlxERRExOhy2lPqg==</sctn></outDto><outDto><ctn>01030466316</ctn><marketGubun>KTF</marketGubun><sctn>1XdXuZsnMBBRExOhy2lPqg==</sctn></outDto></return>");
                //responseXml.append("<return><commHeader><globalNo>9106072820160114172107306</globalNo><encYn/><responseType>S</responseType><responseCode>MCG_SYS_E0500</responseCode><responseLogcd/><responseTitle>시스템 오류</responseTitle><responseBasic>[서버] 시스템 내부처리 오류</responseBasic><langCode/><filler/></commHeader><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X31</appEventCd><appSendDateTime>20160114172107</appSendDateTime><appRecvDateTime/><appLgDateTime/><appNstepUserId>91060728</appNstepUserId><appOrderId/></bizHeader></return>");
                break;
            case 32://번호변경
                //			responseXml.append("<return><commHeader><globalNo>9106072820160114172107306</globalNo><encYn/><responseType>S</responseType><responseCode>MCG_SYS_E0500</responseCode><responseLogcd/><responseTitle>시스템 오류</responseTitle><responseBasic>[서버] 시스템 내부처리 오류</responseBasic><langCode/><filler/></commHeader><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X31</appEventCd><appSendDateTime>20160114172107</appSendDateTime><appRecvDateTime/><appLgDateTime/><appNstepUserId>91060728</appNstepUserId><appOrderId/></bizHeader></return>");
                //responseXml.append("<return><commHeader><globalNo>9106072820160114172107306</globalNo><encYn/><responseType>S</responseType><responseCode>MCG_SYS_E0500</responseCode><responseLogcd/><responseTitle>시스템 오류</responseTitle><responseBasic>[서버] 시스템 내부처리 오류</responseBasic><langCode/><filler/></commHeader><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X31</appEventCd><appSendDateTime>20160114172107</appSendDateTime><appRecvDateTime/><appLgDateTime/><appNstepUserId>91060728</appNstepUserId><appOrderId/></bizHeader></return>");

                //정상
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KTF</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X32</appEventCd><appSendDateTime>20150210135049</appSendDateTime><appRecvDateTime>20150210135016</appRecvDateTime><appLgDateTime>20150210135016</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201501191201011234</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader></return>");

                //비정상
                //			responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X32</appEventCd><appSendDateTime>20160201121354</appSendDateTime><appRecvDateTime>20160201121351</appRecvDateTime><appLgDateTime>20160201121351</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160201121347984</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_999_COME1002</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>번호변경 안내:선택하신 전화번호가 비정상적인 방법으로 변경되었습니다. 확인 후 작업하시기 바랍니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");

                break;
            case 33://분실신고가능여부조회------
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X33</appEventCd><appSendDateTime>20160112162508</appSendDateTime><appRecvDateTime>20160112162505</appRecvDateTime><appLgDateTime>20160112162505</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112162505018</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><asfdYn>N</asfdYn><coldeLinqStatus>N</coldeLinqStatus><rsltCd>Y</rsltCd><rsltMsg></rsltMsg><runMode>I</runMode><subStatusLastAct>NAC</subStatusLastAct></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X33</appEventCd><appSendDateTime>20160114173714</appSendDateTime><appRecvDateTime>20160114173712</appRecvDateTime><appLgDateTime>20160114173712</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114173707460</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><asfdYn>N</asfdYn><coldeLinqStatus>N</coldeLinqStatus><rsltCd>N</rsltCd><rsltMsg>분실신고 상태입니다. </rsltMsg><runMode>U</runMode><subStatusLastAct>SUS</subStatusLastAct></outDto></return>");
                break;
            case 34://분실신고신청
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X34</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;

            case 38://부가서비스해지
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X38</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;

            case 49://
                //MMS
                //responseXml.append("<return><bizHeader><appEntrPrsnId>INL</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X03</appEventCd><appSendDateTime>20150210104630</appSendDateTime><appRecvDateTime>20150210104620</appRecvDateTime><appLgDateTime>20150210104620</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201501191201011234</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><outMmsDto><billTypeCd>MB</billTypeCd><ctn>01011117263</ctn><slsCmpnCd>KIS</slsCmpnCd></outMmsDto></outDto></return>");
                //이메일
                //responseXml.append(" <return> <bizHeader> <appEntrPrsnId>INL</appEntrPrsnId> <appAgncCd>AA11070</appAgncCd> <appEventCd>X03</appEventCd> <appSendDateTime>20150210104630</appSendDateTime> <appRecvDateTime>20150210104620</appRecvDateTime> <appLgDateTime>20150210104620</appLgDateTime> <appNstepUserId>6833564</appNstepUserId> <appOrderId /> </bizHeader> <commHeader> <globalNo>9911100201501191201011234</globalNo> <encYn /> <responseType>N</responseType> <responseCode /> <responseLogcd /> <responseTitle /> <responseBasic /> <langCode /> <filler /> </commHeader> <outDto> <outEmailDto> <billTypeCd>CB</billTypeCd> <email>test@gmail.com</email> <sendGubun>Y</sendGubun> <securMailYn>Y</securMailYn> <ecRcvAgreYn>Y</ecRcvAgreYn> </outEmailDto> </outDto> </return> ");
                //우편명세서
                responseXml.append(
                    " <return> <bizHeader> <appEntrPrsnId>INL</appEntrPrsnId> <appAgncCd>AA11070</appAgncCd> <appEventCd>X03</appEventCd> <appSendDateTime>20150210104630</appSendDateTime> <appRecvDateTime>20150210104620</appRecvDateTime> <appLgDateTime>20150210104620</appLgDateTime> <appNstepUserId>6833564</appNstepUserId> <appOrderId /> </bizHeader> <commHeader> <globalNo>9911100201501191201011234</globalNo> <encYn /> <responseType>N</responseType> <responseCode /> <responseLogcd /> <responseTitle /> <responseBasic /> <langCode /> <filler /> </commHeader> <outDto> <outMailDto> <billTypeCd>LX</billTypeCd> <adrCustNm>홍길동</adrCustNm> <adrBasSbst>서울시 삼성동</adrBasSbst> <adrDtlSbst>KT선릉타워 12층</adrDtlSbst> <adrZipCd>100-100</adrZipCd> <rdAdrBasSbst>서울시 삼성동</rdAdrBasSbst> <rdAdrDtlSbst>KT선릉타워 12층</rdAdrDtlSbst> <rdAdrZipCd>100-100</rdAdrZipCd> </outMailDto> </outDto> </return> ");
                break;

            //PMD권장사항 수정 디폴트값지정

            case 50://청구서변경(X50)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X50</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X50</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>E</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>Selfcare 오류</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;

            case 51:// 사이버명세서 발송 이력 조회(X51)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>SPT8050</appAgncCd><appEventCd>X51</appEventCd><appSendDateTime>20230717093626</appSendDateTime><appRecvDateTime>20230717093625</appRecvDateTime><appLgDateTime>20230717093625</appLgDateTime><appNstepUserId>82023154</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>JHC1222233334444555500190009</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><outSendInfoListlDto><email>F504092A3304764F4CF9C7F972A1166E</email><sendDay>20230315</sendDay><state>MMS발송성공.자세한내용은고객센터로문의바랍니다.</state><thisMonth>202303</thisMonth></outSendInfoListlDto><outSendInfoListlDto><email>F504092A3304764F4CF9C7F972A1166E</email><sendDay>20230214</sendDay><state>MMS발송성공.자세한내용은고객센터로문의바랍니다.</state><thisMonth>202302</thisMonth></outSendInfoListlDto><outSendInfoListlDto><email>F504092A3304764F4CF9C7F972A1166E</email><sendDay>20230117</sendDay><state>MMS발송성공.자세한내용은고객센터로문의바랍니다.</state><thisMonth>202301</thisMonth></outSendInfoListlDto></outDto></return>");
                break;

            case 53: // 명세서 재발송 (X53)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>SPT8050</appAgncCd><appEventCd>X53</appEventCd><appSendDateTime>20230717093511</appSendDateTime><appRecvDateTime>20230717093505</appRecvDateTime><appLgDateTime>20230717093505</appLgDateTime><appNstepUserId>82023154</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>JHC1222233334444555500190006</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>SPT8050</appAgncCd><appEventCd>X53</appEventCd><appSendDateTime>20230717093511</appSendDateTime><appRecvDateTime>20230717093505</appRecvDateTime><appLgDateTime>20230717093505</appLgDateTime><appNstepUserId>82023154</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>JHC1222233334444555500190006</globalNo><encYn/><responseType>E</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic>Selfcare 오류</responseBasic><langCode/><filler/></commHeader></return>");
                break;

            case 54://스폰서조회(X54)
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X54</appEventCd><appSendDateTime>20180703142355</appSendDateTime><appRecvDateTime>20180703142354</appRecvDateTime><appLgDateTime>20180703142354</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820180703142350780</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outBasInfoDto><engtAplyStDate>20180102</engtAplyStDate><engtExpirPamDate>20200101</engtExpirPamDate><engtUseDayNum>183</engtUseDayNum><saleEngtNm>알뜰폰스폰서2[베이직코스]</saleEngtNm><saleEngtOptnCd>KD</saleEngtOptnCd><saleEngtTypeDivCd>KM1</saleEngtTypeDivCd></outBasInfoDto><outKDDto><apdSuprtAmt>8000</apdSuprtAmt><engtRmndDate>527</engtRmndDate><firstSuprtAmt>0</firstSuprtAmt><ktSuprtPenltAmt>0</ktSuprtPenltAmt><punoSuprtAmt>0</punoSuprtAmt><realDcAmt>43716</realDcAmt><rtrnAmtAndChageDcAmt>43716</rtrnAmtAndChageDcAmt><storSuprtPenltAmt>0</storSuprtPenltAmt><tgtKtSuprtPenltAmt>0</tgtKtSuprtPenltAmt><trmnForecBprmsAmt>0</trmnForecBprmsAmt></outKDDto></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X54</appEventCd><appSendDateTime>20180704170036</appSendDateTime><appRecvDateTime>20180704170030</appRecvDateTime><appLgDateTime>20180704170030</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820180704170025896</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outBasInfoDto><engtAplyStDate>20180115</engtAplyStDate><engtExpirPamDate>20200114</engtExpirPamDate><engtUseDayNum>171</engtUseDayNum><saleEngtNm>알뜰폰스폰서2 [요금할인(지원금)]</saleEngtNm><saleEngtOptnCd>PM</saleEngtOptnCd><saleEngtTypeDivCd>KM1</saleEngtTypeDivCd></outBasInfoDto><outPMDto><chageDcAmt>0</chageDcAmt><chageDcAmtSuprtMonth>9900</chageDcAmtSuprtMonth><chageDcAmtSuprtRtrnAmt>117079</chageDcAmtSuprtRtrnAmt><engtRmndDate>539</engtRmndDate><realDcAmt>117079</realDcAmt><rtrnAmtAndChageDcAmt>0</rtrnAmtAndChageDcAmt></outPMDto></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X54</appEventCd><appSendDateTime>20180705093218</appSendDateTime><appRecvDateTime>20180705093212</appRecvDateTime><appLgDateTime>20180705093212</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820180705093209193</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outBasInfoDto><engtAplyStDate>20180115</engtAplyStDate><engtExpirPamDate>20200114</engtExpirPamDate><engtUseDayNum>172</engtUseDayNum><saleEngtNm>알뜰폰스폰서2 [요금할인(지원금)]</saleEngtNm><saleEngtOptnCd>PM</saleEngtOptnCd><saleEngtTypeDivCd>KM1</saleEngtTypeDivCd></outBasInfoDto><outPMDto><chageDcAmt>0</chageDcAmt><chageDcAmtSuprtMonth>9900</chageDcAmtSuprtMonth><chageDcAmtSuprtRtrnAmt>117079</chageDcAmtSuprtRtrnAmt><engtRmndDate>538</engtRmndDate><realDcAmt>117079</realDcAmt><rtrnAmtAndChageDcAmt>0</rtrnAmtAndChageDcAmt></outPMDto></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X54</appEventCd><appSendDateTime>20180705093323</appSendDateTime><appRecvDateTime>20180705093317</appRecvDateTime><appLgDateTime>20180705093317</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820180705093314927</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outBasInfoDto><engtAplyStDate>20180207</engtAplyStDate><engtExpirPamDate>20200206</engtExpirPamDate><engtUseDayNum>149</engtUseDayNum><saleEngtNm>알뜰폰스폰서2 [요금할인(지원금)]</saleEngtNm><saleEngtOptnCd>PM</saleEngtOptnCd><saleEngtTypeDivCd>KM1</saleEngtTypeDivCd></outBasInfoDto><outPMDto><chageDcAmt>10000</chageDcAmt><chageDcAmtSuprtMonth>19000</chageDcAmtSuprtMonth><chageDcAmtSuprtRtrnAmt>79121</chageDcAmtSuprtRtrnAmt><engtRmndDate>561</engtRmndDate><realDcAmt>120763</realDcAmt><rtrnAmtAndChageDcAmt>34053</rtrnAmtAndChageDcAmt></outPMDto></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X54</appEventCd><appSendDateTime>20180705093412</appSendDateTime><appRecvDateTime>20180705093406</appRecvDateTime><appLgDateTime>20180705093406</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820180705093403832</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outBasInfoDto><engtAplyStDate>20180102</engtAplyStDate><engtExpirPamDate>20200101</engtExpirPamDate><engtUseDayNum>185</engtUseDayNum><saleEngtNm>알뜰폰스폰서2[베이직코스]</saleEngtNm><saleEngtOptnCd>KD</saleEngtOptnCd><saleEngtTypeDivCd>KM1</saleEngtTypeDivCd></outBasInfoDto><outKDDto><apdSuprtAmt>0</apdSuprtAmt><engtRmndDate>525</engtRmndDate><firstSuprtAmt>143000</firstSuprtAmt><ktSuprtPenltAmt>106760</ktSuprtPenltAmt><punoSuprtAmt>143000</punoSuprtAmt><realDcAmt>0</realDcAmt><rtrnAmtAndChageDcAmt>0</rtrnAmtAndChageDcAmt><storSuprtPenltAmt>0</storSuprtPenltAmt><tgtKtSuprtPenltAmt>143000</tgtKtSuprtPenltAmt><trmnForecBprmsAmt>106760</trmnForecBprmsAmt></outKDDto></outDto></return>");

                break;
            case 59://심플할인 사전체크(X59)
                //실패
                //responseXml.append("<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X59</appEventCd><appSendDateTime>20190503145537</appSendDateTime><appRecvDateTime>20190503145515</appRecvDateTime><appLgDateTime>20190503145515</appLgDateTime><appNstepUserId>8500056</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9105420120150508150101001</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><resltMsg>신청, 해지 불가</resltMsg><sbscYn>N</sbscYn></outDto></return>");

           /*sbscYn       사전체크 결과코드   1   M   "Y(신청가능) -> 신청만 가능함.
           E(중도해지) -> 해지만 가능함.
           N(신청불가) -> 신청, 해지 불가"
          resltMsg        결과 메시지      M   */

                //성공
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X59</appEventCd><appSendDateTime>20190503145537</appSendDateTime><appRecvDateTime>20190503145515</appRecvDateTime><appLgDateTime>20190503145515</appLgDateTime><appNstepUserId>8500056</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9105420120150508150101001</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><resltMsg>신청만 가능함.</resltMsg><sbscYn>Y</sbscYn></outDto></return>");
                break;
            case 60://심플할인 가입(X60)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X50</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 61://심플할인 해지(X61)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X50</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 62://심플할인 정보조회(X61)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>ENX</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X62</appEventCd><appSendDateTime>20190503104357</appSendDateTime><appRecvDateTime>20190503104327</appRecvDateTime><appLgDateTime>20190503104327</appLgDateTime><appNstepUserId>8500056</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9105420120150508150101001</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><chageDcAplyYn>Y</chageDcAplyYn><dcSuprtAmt>1000</dcSuprtAmt><engtAplyStDate>20170515</engtAplyStDate><engtExpirPamDate>20190514</engtExpirPamDate><engtPerdMonsNum>24</engtPerdMonsNum><ppPenlt>4611</ppPenlt></outDto></return>");
                break;
            case 68://즉시납부
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X68</appEventCd><appSendDateTime>20220222164543</appSendDateTime><appRecvDateTime>20220222164540</appRecvDateTime><appLgDateTime>20220222164540</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222164242447</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><url>https://sandbox-billgates-web.kakao.com/r/platform/pages/paynow/search/1633/6/7404c2d1-cd37-4d28-8234-133e504817bb</url></outDto></return>");
                break;
            case 74://쿠폰 정보조회(X74)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA11070</appAgncCd>");
                responseXml.append("        <appEventCd>X01</appEventCd>");
                responseXml.append("        <appSendDateTime>20180405140000</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20201216142110</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20201216142110</appLgDateTime>");
                responseXml.append("        <appNstepUserId>116833564</appNstepUserId>");
                responseXml.append("        <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9114053920180405150101001</globalNo>");
                responseXml.append("        <encYn/>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode/>");
                responseXml.append("        <responseLogcd/>");
                responseXml.append("        <responseTitle/>");
                responseXml.append("        <responseBasic/>");
                responseXml.append("        <langCode/>");
                responseXml.append("        <filler/>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <coupInfoList>");
                responseXml.append("            <coupAplyLimitCd>BIZ</coupAplyLimitCd>");
                responseXml.append("            <coupCategoryCd>01</coupCategoryCd>");
                responseXml.append("            <coupCreId>22020121500003124853</coupCreId>");
                responseXml.append("            <coupNm>쿠폰5: KIS/부가서비스형/KIS</coupNm>");
                responseXml.append("            <coupSerialNo>TQHYVNA4N15ROWR</coupSerialNo>");
                responseXml.append("            <coupStatCd>BPCO</coupStatCd>");
                responseXml.append("            <coupTypeCd>02</coupTypeCd>");
                responseXml.append("            <coupValu>5000</coupValu>");
                responseXml.append("            <dscnTypeCd>FXM</dscnTypeCd>");
                responseXml.append("            <rsvPsblYn>Y</rsvPsblYn>");
                responseXml.append("            <smsRcvCtn>010272192xx</smsRcvCtn>");
                responseXml.append("            <svcTypeCd>MMB</svcTypeCd>");
                responseXml.append("            <useEndDt>20210116235959</useEndDt>");
                responseXml.append("            <useStrtDt>20201216000000</useStrtDt>");
                responseXml.append("        </coupInfoList>");
                responseXml.append("        <coupInfoList>");
                responseXml.append("            <coupAplyLimitCd>BIZ</coupAplyLimitCd>");
                responseXml.append("            <coupCategoryCd>01</coupCategoryCd>");
                responseXml.append("            <coupCreId>22020121500003124853</coupCreId>");
                responseXml.append("            <coupNm>쿠폰5: KIS/부가서비스형/KIS</coupNm>");
                responseXml.append("            <coupSerialNo>TQHYVNA4N15ROWR</coupSerialNo>");
                responseXml.append("            <coupStatCd>BPCO</coupStatCd>");
                responseXml.append("            <coupTypeCd>02</coupTypeCd>");
                responseXml.append("            <coupValu>5000</coupValu>");
                responseXml.append("            <dscnTypeCd>FXM</dscnTypeCd>");
                responseXml.append("            <rsvPsblYn>Y</rsvPsblYn>");
                responseXml.append("            <smsRcvCtn>010272192xx</smsRcvCtn>");
                responseXml.append("            <svcTypeCd>MMB</svcTypeCd>");
                responseXml.append("            <useEndDt>20210116235959</useEndDt>");
                responseXml.append("            <useStrtDt>20201216000000</useStrtDt>");
                responseXml.append("        </coupInfoList>");
                responseXml.append("        <rtnCode>0000</rtnCode>");
                responseXml.append("        <rtnMsg>Success</rtnMsg>");
                responseXml.append("        <totalContentCnt>1</totalContentCnt>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 75://쿠폰사용(X75)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA11070</appAgncCd>");
                responseXml.append("        <appEventCd>X01</appEventCd>");
                responseXml.append("        <appSendDateTime>20180405140000</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20201216144116</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20201216144116</appLgDateTime>");
                responseXml.append("        <appNstepUserId>116833564</appNstepUserId>");
                responseXml.append("        <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9114053920180405150101001</globalNo>");
                responseXml.append("        <encYn/>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode/>");
                responseXml.append("        <responseLogcd/>");
                responseXml.append("        <responseTitle/>");
                responseXml.append("        <responseBasic/>");
                responseXml.append("        <langCode/>");
                responseXml.append("        <filler/>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <dscnTypeCd>FXM</dscnTypeCd>");
                responseXml.append("        <rtnCode>0000</rtnCode>");
                responseXml.append("        <rtnMsg>Success</rtnMsg>");
                responseXml.append("        <useEndDt>20210116235959</useEndDt>");
                responseXml.append("        <useStrtDt>20201216000000</useStrtDt>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 76://사용완료 쿠폰 목록 조회(X76)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA11070</appAgncCd>");
                responseXml.append("        <appEventCd>X01</appEventCd>");
                responseXml.append("        <appSendDateTime>20180405140000</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20201216154114</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20201216154114</appLgDateTime>");
                responseXml.append("        <appNstepUserId>116833564</appNstepUserId>");
                responseXml.append("        <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9114053920180405150101001</globalNo>");
                responseXml.append("        <encYn/>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode/>");
                responseXml.append("        <responseLogcd/>");
                responseXml.append("        <responseTitle/>");
                responseXml.append("        <responseBasic/>");
                responseXml.append("        <langCode/>");
                responseXml.append("        <filler/>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <rtnCode>0000</rtnCode>");
                responseXml.append("        <rtnMsg>Success</rtnMsg>");
                responseXml.append("        <totalContentCnt>1</totalContentCnt>");
                responseXml.append("        <usedCoupList>");
                responseXml.append("            <coupAplyLimitCd>BIZ</coupAplyLimitCd>");
                responseXml.append("            <coupNm>쿠폰5: KIS/부가서비스형/KIS</coupNm>");
                responseXml.append("            <coupSerialNo>YKHYMQA4TQ5RPUQ</coupSerialNo>");
                responseXml.append("            <coupStatCd>SYCO</coupStatCd>");
                responseXml.append("            <rgstStrtDt>20201216141750</rgstStrtDt>");
                responseXml.append("            <smsRcvCtn>010726036XX</smsRcvCtn>");
                responseXml.append("            <svcTypeCd>MMB</svcTypeCd>");
                responseXml.append("            <useReqDt>20201216141750</useReqDt>");
                responseXml.append("        </usedCoupList>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 83://회선 사용기간조회
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X83</appEventCd>");
                responseXml.append("        <appSendDateTime>20211103155502</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20211103155458</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20211103155458</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020211103155029878</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <longUseAdjDayNum>0</longUseAdjDayNum>");
                responseXml.append("        <realUseDayNum>2089</realUseDayNum>");
                responseXml.append("        <svcContSbscDt>20160208150750</svcContSbscDt>");
                responseXml.append("        <totStopDayNum>6</totStopDayNum>");
                responseXml.append("        <totUseDayNum>2095</totUseDayNum>");
                responseXml.append("    </outDto>");
                responseXml.append("  </return>");
                break;
            case 85://USIM 유효성 체크(X85)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X19</appEventCd>");
                responseXml.append("        <appSendDateTime>20210902150015</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20210902145952</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20210902145952</appLgDateTime>");
                responseXml.append("        <appNstepUserId>82023154</appNstepUserId>");
                responseXml.append("        <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>912788510902000000000001</globalNo>");
                responseXml.append("        <encYn/>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <psblYn>Y</psblYn>  ");
                responseXml.append("        <rsltMsg>rsltMsgrsltMsgrsltMsgrsltMsg</rsltMsg>");
                responseXml.append("    </outDto>     ");
                responseXml.append("</return>");
                break;
            case 88: //요금상품예약변경(X88)
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220406191431</appSendDateTime><appRecvDateTime>20220406191429</appRecvDateTime><appLgDateTime>20220406191429</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406191429656</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>Y</rsltCd><ruleId>100001842</ruleId><ruleMsgSbst>요금제 변경시 자동해지 되는 부가상품입니다. &#xD;- M 요금할인 5000(VAT포함)&#xD;(http--0.0.0.0-7006-4)  - M 요금할인 3000(VAT포함)</ruleMsgSbst></message><rsltYn>Y</rsltYn></outDto></return>");
                //  responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220406181627</appSendDateTime><appRecvDateTime>20220406181625</appRecvDateTime><appLgDateTime>20220406181625</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406181626460</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>N</rsltCd><ruleId>100000413</ruleId><ruleMsgSbst>고객님은요금제 예약한 고객이므로 예약 취소후 처리하십시요.</ruleMsgSbst></message><message><rsltCd>Y</rsltCd><ruleId>100001842</ruleId><ruleMsgSbst>요금제 변경 시 자동해지 되는 부가상품입니다. &#xD;- M 요금할인 5000(VAT포함)&#xD; - M 요금할인 3000(VAT포함)</ruleMsgSbst></message><rsltYn>N</rsltYn></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220406125323</appSendDateTime><appRecvDateTime>20220406125316</appRecvDateTime><appLgDateTime>20220406125316</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406124901628</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>N</rsltCd><ruleId>MSG_100999998_1</ruleId><ruleMsgSbst>현재 선택한 요금제에서는 가입할 수 없는 부가서비스[USIM 10GB 할인프로모션]입니다.</ruleMsgSbst></message><message><rsltCd>N</rsltCd><ruleId>MSG_100999998_1</ruleId><ruleMsgSbst>현재 선택한 요금제에서는 가입할 수 없는 부가서비스[LTE 데이터 추가제공 100GB(12개월)]입니다.</ruleMsgSbst></message><rsltYn>N</rsltYn></outDto></return>");
                // responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220310093349</appSendDateTime><appRecvDateTime>20220310093336</appRecvDateTime><appLgDateTime>20220310093336</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220310093010331</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>Y</rsltCd><ruleId>100001136</ruleId><ruleMsgSbst>고객 문의 후 희망하면, 무선데이터차단서비스를 해지해주시기 바랍니다.</ruleMsgSbst></message><rsltYn>Y</rsltYn></outDto></return>");
                responseXml.append("			<return>");
                responseXml.append("				<bizHeader>");
                responseXml.append("					<appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("					<appAgncCd>AA00364</appAgncCd>");
                responseXml.append("					<appEventCd>X88</appEventCd>");
                responseXml.append("					<appSendDateTime>20220530134033</appSendDateTime>");
                responseXml.append("					<appRecvDateTime>20220530134031</appRecvDateTime>");
                responseXml.append("					<appLgDateTime>20220530134031</appLgDateTime>");
                responseXml.append("					<appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("					<appOrderId></appOrderId>");
                responseXml.append("				</bizHeader>");
                responseXml.append("				<commHeader>");
                responseXml.append("					<globalNo>9122533020220530134054060</globalNo>");
                responseXml.append("					<encYn></encYn>");
                responseXml.append("					<responseType>N</responseType>");
                responseXml.append("					<responseCode></responseCode>");
                responseXml.append("					<responseLogcd></responseLogcd>");
                responseXml.append("					<responseTitle></responseTitle>");
                responseXml.append("					<responseBasic></responseBasic>");
                responseXml.append("					<langCode></langCode>");
                responseXml.append("					<filler></filler>");
                responseXml.append("				</commHeader>");
                responseXml.append("				<outDto>");
                responseXml.append("					<message>");
                responseXml.append("						<rsltCd>N</rsltCd>");
                responseXml.append("						<ruleId>MSG_100999998_1</ruleId>");
                responseXml.append("						<ruleMsgSbst>현재 선택한 요금제에서는 가입할 수 없는 부가서비스[LTE 데이터 추가제공 6GB]입니다.</ruleMsgSbst>");
                responseXml.append("					</message>");
                responseXml.append("					<rsltYn>N</rsltYn>");
                responseXml.append("				</outDto>");
                responseXml.append("			</return>");
                break;
            case 89: //요금상품예약변경조회(X89)
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X89</appEventCd><appSendDateTime>20220308175501</appSendDateTime><appRecvDateTime>20220308175459</appRecvDateTime><appLgDateTime>20220308175459</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220308175136057</globalNo><encYn></encYn><responseType>E</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><aplyDate>20220308170741</aplyDate><basicAmt>25000</basicAmt><efctStDate>20220401000001</efctStDate><prdcCd>PL208J932</prdcCd><prdcNm>모두다 맘껏 안심 2.5G+</prdcNm></outDto></return>");
                // responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X88</appEventCd><appSendDateTime>20220308171155</appSendDateTime><appRecvDateTime>20220308171142</appRecvDateTime><appLgDateTime>20220308171142</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220308170818766</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><message><rsltCd>N</rsltCd><ruleId>100000413</ruleId><ruleMsgSbst>고객님은요금제 예약한 고객이므로 예약 취소후 처리하십시요.</ruleMsgSbst></message><message><rsltCd>Y</rsltCd><ruleId>100001136</ruleId><ruleMsgSbst>고객 문의 후 희망하면, 무선데이터차단서비스를 해지해주시기 바랍니다.</ruleMsgSbst></message><rsltYn>N</rsltYn></outDto></return>");
                break;
            case 90: //요금상품예약변경취소(X90)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X89</appEventCd>");
                responseXml.append("        <appSendDateTime>20210902150015</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20210902145952</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20210902145952</appLgDateTime>");
                responseXml.append("        <appNstepUserId>82023154</appNstepUserId>");
                responseXml.append("        <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>912788510902000000000001</globalNo>");
                responseXml.append("        <encYn/>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("    </commHeader>");
                responseXml.append("</return>");
                break;

            case 78: //x78
                //           responseXml.append("    <return>    <bizHeader>     <appEntrPrsnId>KIS</appEntrPrsnId>     <appAgncCd>AA00364</appAgncCd>     <appEventCd>X78</appEventCd>     <appSendDateTime>20220127123920</appSendDateTime>     <appRecvDateTime>20220127123910</appRecvDateTime>     <appLgDateTime>20220127123910</appLgDateTime>     <appNstepUserId>91225330</appNstepUserId>     <appOrderId></appOrderId>    </bizHeader>    <commHeader>     <globalNo>9122533020220127123659194</globalNo>     <encYn></encYn>     <responseType>N</responseType>     <responseCode></responseCode>     <responseLogcd></responseLogcd>     <responseTitle></responseTitle>     <responseBasic></responseBasic>     <langCode></langCode>     <filler></filler>    </commHeader>    <outDto>     <moscCombPreChkListOutDTO>      <resltMsg>인터넷뭉치면올레 결합할인은 결합약정이 3년만 가입이 가능합니다.</resltMsg>      <sbscYn>N</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>MVNO모바일 회선은 인터넷 개통일자[20150925] 익월말 이후 결합은 불가합니다      </resltMsg>      <sbscYn>N</sbscYn>      <svcNo>01029672627</svcNo>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>     </moscCombPreChkListOutDTO>     <moscCombPreChkListOutDTO>      <resltMsg>정상</resltMsg>      <sbscYn>Y</sbscYn>      <svcNo>01029672627</svcNo>     </moscCombPreChkListOutDTO>     <resltMsg>약정만료일이 지난 결합의 약정기간 하향처리는 불가합니다.</resltMsg>     <sbscYn>N</sbscYn>    </outDto>   </return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X77</appEventCd><appSendDateTime>20221115134810</appSendDateTime><appRecvDateTime>20221115134804</appRecvDateTime><appLgDateTime>20221115134804</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020221115134909093</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>N</sbscYn><svcNo>01097785180</svcNo></moscCombPreChkListOutDTO><moscCombPreChkListOutDTO><resltMsg>인터넷뭉치면올레 결합할인은 결합약정이 3년만 가입이 가능합니다.</resltMsg><sbscYn>N</sbscYn><svcNo>z!64139196676</svcNo></moscCombPreChkListOutDTO><moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>Y</sbscYn><svcNo>01097785180</svcNo></moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>Y</sbscYn></outDto></return>");
                break;
            case 79: //x79
                //           responseXml.append("  <return>    <bizHeader>     <appEntrPrsnId>KIS</appEntrPrsnId>     <appAgncCd>AA00364</appAgncCd>     <appEventCd>X79</appEventCd>     <appSendDateTime>20220127162449</appSendDateTime>     <appRecvDateTime>20220127162424</appRecvDateTime>     <appLgDateTime>20220127162424</appLgDateTime>     <appNstepUserId>91225330</appNstepUserId>     <appOrderId></appOrderId>    </bizHeader>    <commHeader>     <globalNo>9122533020220127162212808</globalNo>     <encYn></encYn>     <responseType>N</responseType>     <responseCode></responseCode>     <responseLogcd></responseLogcd>     <responseTitle></responseTitle>     <responseBasic></responseBasic>     <langCode></langCode>     <filler></filler>    </commHeader>    <outDto>     <resltMsg>69141094682</resltMsg>     <sbscYn>Y</sbscYn>    </outDto>   </return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X77</appEventCd><appSendDateTime>20221115134810</appSendDateTime><appRecvDateTime>20221115134804</appRecvDateTime><appLgDateTime>20221115134804</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020221115134909093</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>N</sbscYn><svcNo>01097785180</svcNo></moscCombPreChkListOutDTO><moscCombPreChkListOutDTO><resltMsg>인터넷뭉치면올레 결합할인은 결합약정이 3년만 가입이 가능합니다.</resltMsg><sbscYn>N</sbscYn><svcNo>z!64139196676</svcNo></moscCombPreChkListOutDTO><moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>Y</sbscYn><svcNo>01097785180</svcNo></moscCombPreChkListOutDTO><resltMsg>정상</resltMsg><sbscYn>Y</sbscYn></outDto></return>");
                break;
            case 67: //x67
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X67</appEventCd><appSendDateTime>20220222130132</appSendDateTime><appRecvDateTime>20220222130127</appRecvDateTime><appLgDateTime>20220222130127</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222125829569</globalNo><encYn></encYn> <responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                //sponseXml.append("OrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222131514528</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader<outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                break;
            case 86: //x67
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X86</appEventCd><appSendDateTime>20220302151305</appSendDateTime><appRecvDateTime>20220302151303</appRecvDateTime><appLgDateTime>20220302151303</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220302150950662</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outWireDto><sbscBindNowCnt>0</sbscBindNowCnt><sbscBindRemdCnt>0</sbscBindRemdCnt><sbscPsblTotCnt>0</sbscPsblTotCnt></outWireDto></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X67</appEventCd><appSendDateTime>20220222130132</appSendDateTime><appRecvDateTime>20220222130127</appRecvDateTime><appLgDateTime>20220222130127</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222125829569</globalNo><encYn></encYn> <responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                //sponseXml.append("OrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222131514528</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader<outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                break;
            case 92: //x92
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X92</appEventCd><appSendDateTime>20220401160325</appSendDateTime> <appRecvDateTime>20220401160320</appRecvDateTime><appLgDateTime>20220401160320</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220401155914243</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><currMthNpayAmt>0</currMthNpayAmt><infoMsg></infoMsg><payTgtAmt>12110</payTgtAmt><totNpayAmt>12110</totNpayAmt></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X67</appEventCd><appSendDateTime>20220222130132</appSendDateTime><appRecvDateTime>20220222130127</appRecvDateTime><appLgDateTime>20220222130127</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222125829569</globalNo><encYn></encYn> <responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                //sponseXml.append("OrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220222131514528</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader<outDto><infoMsg></infoMsg><outNonPymnDto><invSum>12260</invSum><month>202201</month></outNonPymnDto><outNonPymnDto><invSum>10890</invSum><month>202112</month></outNonPymnDto><total>23150</total></outDto></return>");
                break;
            case 93: //x92
                responseXml.append("<return>");
                responseXml.append("	<bizHeader>");
                responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                responseXml.append("		<appEventCd>X93</appEventCd>");
                responseXml.append("		<appSendDateTime>20230814102338</appSendDateTime>");
                responseXml.append("		<appRecvDateTime>20230814102336</appRecvDateTime>");
                responseXml.append("		<appLgDateTime>20230814102336</appLgDateTime>");
                responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("		<appOrderId></appOrderId>");
                responseXml.append("	</bizHeader>");
                responseXml.append("	<commHeader>");
                responseXml.append("		<globalNo>9122533020230814102336881</globalNo>");
                responseXml.append("		<encYn></encYn>");
                responseXml.append("		<responseType>N</responseType>");
                //responseXml.append("		<responseType>E</responseType>");
                responseXml.append("		<responseCode>ITL_999_21</responseCode>");
                responseXml.append("		<responseLogcd></responseLogcd>");
                responseXml.append("		<responseTitle></responseTitle>");
                responseXml.append("		<responseBasic>비밀번호 오류횟수 초과-비밀번호 불일치 횟수 초과(3회)</responseBasic>");
                responseXml.append("		<langCode></langCode>");
                responseXml.append("		<filler></filler>");
                responseXml.append("	</commHeader>");
                responseXml.append("</return>");
                break;
            case 70: //x92
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X70</appEventCd><appSendDateTime>20220414162253</appSendDateTime><appRecvDateTime>20220414162249</appRecvDateTime><appLgDateTime>20220414162249</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220414162252889</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X70</appEventCd><appSendDateTime>20200512154018</appSendDateTime><appRecvDateTime>20200512154006</appRecvDateTime><appLgDateTime>20200512154006</appLgDateTime><appNstepUserId>116833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9114053920180405150101014</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader></return>");
                break;
            case 97://X97
                responseXml.append("<return>");
                responseXml.append("	<bizHeader>");
                responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                responseXml.append("		<appEventCd>X97</appEventCd>");
                responseXml.append("		<appSendDateTime>20230710170609</appSendDateTime>");
                responseXml.append("		<appRecvDateTime>20230710170606</appRecvDateTime>");
                responseXml.append("		<appLgDateTime>20230710170606</appLgDateTime>");
                responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("		<appOrderId></appOrderId>");
                responseXml.append("	</bizHeader>");
                responseXml.append("	<commHeader>");
                responseXml.append("		<globalNo>9122533020230710170748566</globalNo>");
                responseXml.append("		<encYn></encYn>");
                responseXml.append("		<responseType>N</responseType>");
                responseXml.append("		<responseCode></responseCode>");
                responseXml.append("		<responseLogcd></responseLogcd>");
                responseXml.append("		<responseTitle></responseTitle>");
                responseXml.append("		<responseBasic></responseBasic>");
                responseXml.append("		<langCode></langCode>");
                responseXml.append("		<filler></filler>");
                responseXml.append("	</commHeader>");
                responseXml.append("	<outDto>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342586</prodHstSeq>");
                responseXml.append("			<soc>SPMFILTER</soc>");
                responseXml.append("			<socDescription>스팸차단서비스</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102646</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027926291</prodHstSeq>");
                responseXml.append("			<soc>NESPFMCD3</soc>");
                responseXml.append("			<socDescription>WiFi싱글(무료)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230703172649</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001070592787</prodHstSeq>");
                responseXml.append("			<soc>RCSPRVSVC</soc>");
                responseXml.append("			<socDescription>채팅+</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230725000000</effectiveDate>");
                responseXml.append("			<paramSbst>STRT_DT=20230725000000|END_DT=20230725235959|PRDC_SRL_NO=1|");
                responseXml.append("			</paramSbst>");
                responseXml.append("			<prodHstSeq>300001073639407</prodHstSeq>");
                responseXml.append("			<soc>PL2079771</soc>");
                responseXml.append("			<socDescription>로밍 하루종일ON 플러스</socDescription>");
                responseXml.append("			<socRateValue>11,819 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230729000000</effectiveDate>");
                responseXml.append(
                    "			<paramSbst>STRT_DT=20230729000000|SHARE_SUB_CONTID1=626380439|SHARE_SUB_CONTID2=|SHARE_SUB_CONTID3=|SHARE_SUB_CONTID4=|PRDC_SRL_NO=1|");
                responseXml.append("			</paramSbst>");
                responseXml.append("			<prodHstSeq>300001073638402</prodHstSeq>");
                responseXml.append("			<soc>PL199N122</soc>");
                responseXml.append("			<socDescription>로밍 데이터 함께ON 아시아/미주 12GB(대표)</socDescription>");
                responseXml.append("			<socRateValue>60,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102655</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027926322</prodHstSeq>");
                responseXml.append("			<soc>PL19AS350</soc>");
                responseXml.append("			<socDescription>M 요금할인 1000(VAT포함)</socDescription>");
                responseXml.append("			<socRateValue>-910 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342580</prodHstSeq>");
                responseXml.append("			<soc>MPAYBLOCK</soc>");
                responseXml.append("			<socDescription>휴대폰결제 이용거부</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342581</prodHstSeq>");
                responseXml.append("			<soc>CLIPF</soc>");
                responseXml.append("			<socDescription>발신번호표시무료</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230418153151</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001030912105</prodHstSeq>");
                responseXml.append("			<soc>PL22CG717</soc>");
                responseXml.append("			<socDescription>LTE 데이터 추가제공 5GB(결합)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230628100335</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001067425311</prodHstSeq>");
                responseXml.append("			<soc>PL224R614</soc>");
                responseXml.append("			<socDescription>데이터 Free 쿠폰 30GB(1회)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342582</prodHstSeq>");
                responseXml.append("			<soc>LTECERTID</soc>");
                responseXml.append("			<socDescription>LTE_인증상품</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102646</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027926293</prodHstSeq>");
                responseXml.append("			<soc>LTEULDAF1</soc>");
                responseXml.append("			<socDescription>LTE데이터무제한V 1M(PM)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112711</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967341905</prodHstSeq>");
                responseXml.append("			<soc>VLTEAUTSV</soc>");
                responseXml.append("			<socDescription>HD 보이스</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230704171732</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001071204206</prodHstSeq>");
                responseXml.append("			<soc>PL214L310</soc>");
                responseXml.append("			<socDescription>휴대폰안심보험 안드로이드 플래티넘</socDescription>");
                responseXml.append("			<socRateValue>5,300 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342585</prodHstSeq>");
                responseXml.append("			<soc>SMSB</soc>");
                responseXml.append("			<socDescription>SMS(문자서비스) 기본제공</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102659</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027928132</prodHstSeq>");
                responseXml.append("			<soc>KISADDC07</soc>");
                responseXml.append("			<socDescription>M 요금할인 7,000</socDescription>");
                responseXml.append("			<socRateValue>-7,000 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102651</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027928111</prodHstSeq>");
                responseXml.append("			<soc>PL19AS355</soc>");
                responseXml.append("			<socDescription>M 요금할인 9000(VAT포함)</socDescription>");
                responseXml.append("			<socRateValue>-8,182 WON</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112711</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967341904</prodHstSeq>");
                responseXml.append("			<soc>PSVTAUTSV</soc>");
                responseXml.append("			<socDescription>HD 영상통화</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967342583</prodHstSeq>");
                responseXml.append("			<soc>WVMS</soc>");
                responseXml.append("			<socDescription>통합사서함</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112445</effectiveDate>");
                responseXml.append(
                    "			<paramSbst>BLCK_NO1=060|BLCK_TYPE1=3|BLCK_NO2=|BLCK_TYPE2=|BLCK_NO3=|BLCK_TYPE3=|BLCK_NO4=|BLCK_TYPE4=|BLCK_NO5=|BLCK_TYPE5=|BLCK_NO6=|BLCK_TYPE6=|BLCK_NO7=|BLCK_TYPE7=|BLCK_NO8=|BLCK_TYPE8=|BLCK_NO9=|BLCK_TYPE9=|BLCK_NO10=|BLCK_TYPE10=|");
                responseXml.append("			</paramSbst>");
                responseXml.append("			<prodHstSeq>300000967342584</prodHstSeq>");
                responseXml.append("			<soc>NOSPAM3</soc>");
                responseXml.append("			<socDescription>정보제공사업자번호차단</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20221221112711</effectiveDate>");
                responseXml.append("			<prodHstSeq>300000967341903</prodHstSeq>");
                responseXml.append("			<soc>SMARTNMON</soc>");
                responseXml.append("			<socDescription>스마트폰(종량)-일반</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("		<svcList>");
                responseXml.append("			<effectiveDate>20230412102646</effectiveDate>");
                responseXml.append("			<prodHstSeq>300001027926292</prodHstSeq>");
                responseXml.append("			<soc>PL217Q731</soc>");
                responseXml.append("			<socDescription>MVNO결합전용(블라이스)</socDescription>");
                responseXml.append("			<socRateValue>Free</socRateValue>");
                responseXml.append("		</svcList>");
                responseXml.append("        <svcList>");
                responseXml.append("            <effectiveDate>20230818000000</effectiveDate>");
                responseXml.append(
                    "            <paramSbst>STRT_DT=20230818000000|END_DT=20230818235959|SHARE_MAIN_CONTID=626506218|SHARE_MAIN_PROD_HST_SEQ=300001091066712|PRDC_SRL_NO=1|</paramSbst>");
                responseXml.append("            <prodHstSeq>300001091066715</prodHstSeq>");
                responseXml.append("            <soc>PL2079778</soc>");
                responseXml.append("            <socDescription>로밍 하루종일ON 투게더(서브)</socDescription>");
                responseXml.append("            <socRateValue>5,000 WON</socRateValue>");
                responseXml.append("        </svcList>");
                responseXml.append("	</outDto>");
                responseXml.append("</return>");
                break;
            default:
                logger.debug("Default MsfMplatFormService.java");
        }

        responseXml.append("</ns2:moscPerInfoResponse></soap:Body></soap:Envelope>");
        vo.setResponseXml(responseXml.toString());
        try {
            vo.toResponseParse();
        } catch (SelfServiceException e) {
            throw e;
        } catch (Exception e) {
            result = false;
        }
        //////////////////////////////////

        return result;
    }


    /*
     * 테스트를 위해 정상적인 리턴을 강제로 넘겨준다.
     */
    private boolean getVoNe(int param, CommonXmlNoSelfServiceException vo) {
        boolean result = true;
        //////////////////////////////////
        StringBuffer responseXml = new StringBuffer();

        responseXml.append(
            "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscPerInfoResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\">");

        switch (param) {
            case 19://요금상품변경
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20220406204440</appSendDateTime><appRecvDateTime>20220406204437</appRecvDateTime><appLgDateTime>20220406204437</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406204437223</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20220406201742</appSendDateTime><appRecvDateTime>20220406201741</appRecvDateTime><appLgDateTime>20220406201741</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406201326559</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E033</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>고객님께서는 일시정지 상태이므로 상품을 변경 하실 수 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20160114133336</appSendDateTime><appRecvDateTime>20160114133334</appRecvDateTime><appLgDateTime>20160114133334</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114133330197</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20160114133336</appSendDateTime><appRecvDateTime>20160114133334</appRecvDateTime><appLgDateTime>20160114133334</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114133330197</globalNo><encYn></encYn><responseType>S</responseType><responseCode>ITL_SYS_E9999</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>M-PLATFORM SYSTEM ERROR.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 21://부가서비스신청
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>bluemoor9521@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20220311145506</appSendDateTime><appRecvDateTime>20220311145501</appRecvDateTime><appLgDateTime>20220311145501</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220311145132712</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E021</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>[오토링]상품과 [링투유] 상품은 동시에 가입할 수 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 38://부가서비스해지
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X38</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 80://OTP인증서비스(X80)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("       <appEntrPrsnId>SKY</appEntrPrsnId>");
                responseXml.append("       <appAgncCd>SPT8050</appAgncCd>");
                responseXml.append("       <appEventCd>X80</appEventCd>");
                responseXml.append("       <appSendDateTime>20210203171537</appSendDateTime>");
                responseXml.append("       <appRecvDateTime>20210203171536</appRecvDateTime>");
                responseXml.append("       <appLgDateTime>20210203171536</appLgDateTime>");
                responseXml.append("       <appNstepUserId>82023154</appNstepUserId>");
                responseXml.append("       <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("       <globalNo>20210203104300120</globalNo>");
                responseXml.append("       <encYn/>");
                responseXml.append("       <responseType>N</responseType>");
                responseXml.append("       <responseCode/>");
                responseXml.append("       <responseLogcd/>");
                responseXml.append("       <responseTitle/>");
                responseXml.append("       <responseBasic/>");
                responseXml.append("       <langCode/>");
                responseXml.append("       <filler/>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("       <otpNo>7805</otpNo>");
                responseXml.append("       <resltCd>00</resltCd>");
                responseXml.append("       <resltMsgSbst>정상 처리 가능</resltMsgSbst>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 87: //x87
                responseXml.append("<return>");
                responseXml.append("	<bizHeader>");
                responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
                responseXml.append("		<appEventCd>X87</appEventCd>");
                responseXml.append("		<appSendDateTime>20220408142116</appSendDateTime>");
                responseXml.append("		<appRecvDateTime>20220408142115</appRecvDateTime>");
                responseXml.append("		<appLgDateTime>20220408142115</appLgDateTime>");
                responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("		<appOrderId></appOrderId>");
                responseXml.append("	</bizHeader>");
                responseXml.append("	<commHeader>");
                responseXml.append("		<globalNo>9122533020220408142115206</globalNo>");
                responseXml.append("		<encYn></encYn>");
                responseXml.append("		<responseType>N</responseType>");
                responseXml.append("		<responseCode></responseCode>");
                responseXml.append("		<responseLogcd></responseLogcd>");
                responseXml.append("		<responseTitle></responseTitle>");
                responseXml.append("		<responseBasic></responseBasic>");
                responseXml.append("		<langCode></langCode>");
                responseXml.append("		<filler></filler>");
                responseXml.append("	</commHeader>");
                responseXml.append("	<outDto>");
                responseXml.append("		<combDcTypeNm>알뜰폰 동일명의 결합</combDcTypeNm>");
                responseXml.append("		<combProdNm>(MVNO 결합) 모바일</combProdNm>");
                responseXml.append("		<combTypeNm>MVNO 결합</combTypeNm>");
                responseXml.append("		<engtPerdMonsNum>0년</engtPerdMonsNum>");
                responseXml.append("		<moscCombDtlListOutDTO>");
                responseXml.append("			<combEngtPerdMonsNum>3년</combEngtPerdMonsNum>");
                responseXml.append("			<prodNm>인터넷 베이직</prodNm>");
                responseXml.append("			<svcContDivNm>Internet</svcContDivNm>");
                responseXml.append("			<svcNo></svcNo>");
                responseXml.append("		</moscCombDtlListOutDTO>");

                responseXml.append("		<moscCombDtlListOutDTO>");
                responseXml.append("			<combEngtExpirDt>20280403</combEngtExpirDt>");
                responseXml.append("			<combEngtPerdMonsNum>3년</combEngtPerdMonsNum>");
                responseXml.append("			<combEngtStDt>20250404</combEngtStDt>");
                responseXml.append("			<prodNm>지니 TV 슬림</prodNm>");
                responseXml.append("			<svcContDivNm>IPTV</svcContDivNm>");
                responseXml.append("			<svcNo></svcNo>");
                responseXml.append("		</moscCombDtlListOutDTO>");

                responseXml.append("		<moscCombDtlListOutDTO>");
                responseXml.append("			<combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum>");
                responseXml.append("			<prodNm>LTE 표준</prodNm>");
                responseXml.append("			<svcContDivNm>Mobile(KIS)</svcContDivNm>");
                responseXml.append("			<svcNo>01027148794</svcNo>");
                responseXml.append("		</moscCombDtlListOutDTO>");
                responseXml.append("	</outDto>");
                responseXml.append("</return>");
                break;

            // responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220408142116</appSendDateTime><appRecvDateTime>20220408142115</appRecvDateTime><appLgDateTime>20220408142115</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220408142115206</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><combDcTypeNm>회선별 기여도 할인</combDcTypeNm><combProdNm>(홈) 인터넷+모바일</combProdNm><combTypeNm>홈</combTypeNm><engtPerdMonsNum>3년</engtPerdMonsNum><moscCombDtlListOutDTO><combEngtExpirDt>20241109</combEngtExpirDt><combEngtPerdMonsNum>3년</combEngtPerdMonsNum><prodNm>인터넷 베이직</prodNm><svcContDivNm>Internet</svcContDivNm><svcNo>z!64120575992</svcNo></moscCombDtlListOutDTO>	<moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum>	<prodNm>데이터 맘껏 15GB+/100분</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01025817234</svcNo></moscCombDtlListOutDTO><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>데이터ON 비디오</prodNm><svcContDivNm>Mobile</svcContDivNm><svcNo>01096014852</svcNo>	</moscCombDtlListOutDTO></outDto></return>");
            //                responseXml.append(" <return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220408142116</appSendDateTime><appRecvDateTime>20220408142115</appRecvDateTime><appLgDateTime>20220408142115</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220408142115206</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><combDcTypeNm>알뜰폰 동일명의 결합</combDcTypeNm><combProdNm>(MVNO 결합) 모바일</combProdNm><combTypeNm>MVNO 결합</combTypeNm><engtPerdMonsNum>0년</engtPerdMonsNum><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>실용 USIM 1.7</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01097785180</svcNo></moscCombDtlListOutDTO><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>LTE 표준</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01027148794</svcNo></moscCombDtlListOutDTO></outDto></return>");
            //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220406165031</appSendDateTime><appRecvDateTime>20220406165030</appRecvDateTime><appLgDateTime>20220406165030</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406165146658</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E114</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>[01043255080]회선은 결합되지 않은 회선입니다. </responseBasic><langCode></langCode><filler></filler></commHeader></return>");
            //데이터있음 responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220225103208</appSendDateTime><appRecvDateTime>20220225103206</appRecvDateTime><appLgDateTime>20220225103206</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220225102903049</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><combDcTypeNm>알뜰폰 결합</combDcTypeNm><combProdNm>(MVNO 결합) 모바일+모바일</combProdNm><combTypeNm>MVNO 결합</combTypeNm><engtPerdMonsNum>0년</engtPerdMonsNum><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>데이터 맘껏 안심 1GB+/100분</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01045859158</svcNo></moscCombDtlListOutDTO><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>LTE 표준</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01027148794</svcNo></moscCombDtlListOutDTO></outDto></return>");
            //불가능   responseXml.append("  <return>    <bizHeader>     <appEntrPrsnId>KIS</appEntrPrsnId>     <appAgncCd>AA00364</appAgncCd>     <appEventCd>X87</appEventCd>     <appSendDateTime>20220127162615</appSendDateTime>     <appRecvDateTime>20220127162611</appRecvDateTime>     <appLgDateTime>20220127162611</appLgDateTime>     <appNstepUserId>91225330</appNstepUserId>     <appOrderId></appOrderId>    </bizHeader>    <commHeader>     <globalNo>9122533020220127162359616</globalNo>     <encYn></encYn>     <responseType>E</responseType>     <responseCode>ITL_SFC_E114</responseCode>     <responseLogcd></responseLogcd>     <responseTitle></responseTitle>     <responseBasic>[01029672627]회선은 결합되지 않은 회선입니다. </responseBasic>     <langCode></langCode>     <filler></filler>    </commHeader>   </return>");

            case 77: //x77
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X77</appEventCd>");
                responseXml.append("        <appSendDateTime>20230818120443</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20230818120442</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20230818120442</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020230818120442749</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <moscMvnoComInfo>");
                responseXml.append("            <combSvcNo>01030004307</combSvcNo>");
                responseXml.append("            <combYn>N</combYn>");
                responseXml.append("            <prdcCd>PL225N754</prdcCd>");
                responseXml.append("            <prdcNm>M 프리미엄 11GB</prdcNm>");
                responseXml.append("            <svcDivCd>모바일</svcDivCd>");
                responseXml.append("            <svcNo>01030004307</svcNo>");
                responseXml.append("            <indvInfoAgreeMsgSbst></indvInfoAgreeMsgSbst>");
                responseXml.append("        </moscMvnoComInfo>");
                responseXml.append("        <moscSrchCombInfoList>");
                responseXml.append("            <combSvcNo>01095104513</combSvcNo>");
                responseXml.append("            <combYn>N</combYn>");
                responseXml.append("            <prdcCd>099S01</prdcCd>");
                responseXml.append("            <prdcNm>M 프리미엄 11GB</prdcNm>");
                responseXml.append("            <svcContOpnDt>20250301</svcContOpnDt>");
                responseXml.append("            <svcDivCd>모바일</svcDivCd>");
                responseXml.append("            <svcNo>01012341234</svcNo>");
                responseXml.append("            <corrNm>MVNOKIS</corrNm>");
                responseXml.append("        </moscSrchCombInfoList>");
                responseXml.append("        <moscSrchCombInfoList>");
                responseXml.append("            <combSvcNo>01095104513</combSvcNo>");
                responseXml.append("            <combYn>N</combYn>");
                responseXml.append("            <prdcCd>099S01</prdcCd>");
                responseXml.append("            <prdcNm>인터넷 슬림</prdcNm>");
                responseXml.append("            <svcContOpnDt>20250426</svcContOpnDt>");
                responseXml.append("            <svcDivCd>인터넷</svcDivCd>");
                responseXml.append("            <svcNo>z!62194901411</svcNo>");
                responseXml.append("            <corrNm>MVNOKIS</corrNm>");
                responseXml.append("        </moscSrchCombInfoList>");
                responseXml.append("        <moscSrchCombInfoList>");
                responseXml.append("            <combSvcNo>01095104513</combSvcNo>");
                responseXml.append("            <combYn>N</combYn>");
                responseXml.append("            <prdcCd>PL203D127</prdcCd>");
                responseXml.append("            <prdcNm>시니어 안심 2GB+</prdcNm>");
                responseXml.append("            <svcContOpnDt>20250430</svcContOpnDt>");
                responseXml.append("            <svcDivCd>인터넷</svcDivCd>");
                responseXml.append("            <svcNo>z!73205012522</svcNo>");
                responseXml.append("            <corrNm>MVNOKIS</corrNm>");
                responseXml.append("        </moscSrchCombInfoList>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");

                break;
            case 78: //x78
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X78</appEventCd>");
                responseXml.append("        <appSendDateTime>20230818120446</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20230818120443</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20230818120443</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020230818120443444</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("            <svcNo>01095104513</svcNo>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <resltMsg>정상</resltMsg>");
                responseXml.append("        <sbscYn>Y</sbscYn>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 79: //x79
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X79</appEventCd>");
                responseXml.append("        <appSendDateTime>20230818120446</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20230818120443</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20230818120443</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020230818120443444</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("            <svcNo>01095104513</svcNo>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <resltMsg>정상</resltMsg>");
                responseXml.append("        <sbscYn>Y</sbscYn>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 71: //x77
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X71</appEventCd><appSendDateTime>20220304092547</appSendDateTime><appRecvDateTime>20220304092546</appRecvDateTime><appLgDateTime>20220304092546</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220304092230512</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E105</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>데이터쉐어링 결합 중인 대상이 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X71</appEventCd><appSendDateTime>20200527133020</appSendDateTime><appRecvDateTime>20200527133019</appRecvDateTime><appLgDateTime>20200527133019</appLgDateTime><appNstepUserId>116833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9114053920180405150101014</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><outDataSharingDto><efctStDt>20200525</efctStDt><svcNo>01074285434</svcNo></outDataSharingDto><outDataSharingDto><efctStDt>20200513</efctStDt><svcNo>01073044224</svcNo></outDataSharingDto></outDto></return>");
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X71</appEventCd><appSendDateTime>20220303175825</appSendDateTime><appRecvDateTime>20220303175824</appRecvDateTime><appLgDateTime>20220303175824</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220303175509424</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E105</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>데이터쉐어링 결합 중인 대상이 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 69: //x69
                //실패케이스
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X69</appEventCd><appSendDateTime>20220412164939</appSendDateTime><appRecvDateTime>20220412164938</appRecvDateTime><appLgDateTime>20220412164938</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220412164940813</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E106</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>데이터쉐어링 결합 가능한 개통된 고객이 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //성공케이스
                responseXml.append(
                    "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X69</appEventCd><appSendDateTime>20220414162249</appSendDateTime><appRecvDateTime>20220414162248</appRecvDateTime><appLgDateTime>20220414162248</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220414162252205</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outDataSharingDto><rsltInd>Y</rsltInd><svcNo>01098132788</svcNo></outDataSharingDto></outDto></return>");
                break;
            case 91://신용카드 인증조회(X91)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X91</appEventCd>");
                responseXml.append("        <appSendDateTime>20240401135847</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20240401135847</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20240401135847</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");

                /*responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020240401135847448</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <trtMsg>주민번호 불일치</trtMsg>");
                responseXml.append("        <trtResult>N</trtResult>");
                responseXml.append("    </outDto>");*/


                responseXml.append("<commHeader>");
                responseXml.append("    <globalNo>moscCrdtCardAthnInfo-TEST-00010</globalNo>");
                responseXml.append("    <responseType>N</responseType>");
                responseXml.append("</commHeader>");
                responseXml.append("<outDto>");
                responseXml.append("    <crdtCardKindCd>GM</crdtCardKindCd>");
                responseXml.append("    <crdtCardNm>GM</crdtCardNm>");
                responseXml.append("    <trtMsg>정상완료</trtMsg>");
                responseXml.append("    <trtResult>Y</trtResult>");
                responseXml.append("</outDto>");



                /*
                responseXml.append("    <outDto>");
                responseXml.append("        <trtMsg>유효기간 오류</trtMsg>");
                responseXml.append("        <trtResult>N</trtResult>");
                responseXml.append("    </outDto>");


                responseXml.append("    <outDto>");
                responseXml.append("        <crdtCardKindCd>DY</crdtCardKindCd>");
                responseXml.append("        <crdtCardNm>DY</crdtCardNm>");
                responseXml.append("        <trtMsg></trtMsg>");
                responseXml.append("       <trtResult>Y</trtResult>");
                responseXml.append("    </outDto>");*/

                responseXml.append("</return>");
                break;
            default:
                logger.debug("Default MsfMplatFormService.java");
        }

        responseXml.append("</ns2:moscPerInfoResponse></soap:Body></soap:Envelope>");
        vo.setResponseXml(responseXml.toString());
        try {
            vo.toResponseParse();
        } catch (Exception e) {
            result = false;
        }
        //////////////////////////////////

        return result;
    }


    private boolean getVoEtc(String param, CommonXmlVO vo) {
        boolean result = true;
        //////////////////////////////////
        StringBuffer responseXml = new StringBuffer();

        if ("Y42".equals(param)) {
            responseXml.append(
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscRemindSmsStatMgmtResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\">");
        } else {
            responseXml.append(
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscPerInfoResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\">");
        }
        if ("D01".equals(param)) {

            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>OP0</appEventCd>");
            responseXml.append("		<appSendDateTime>20210507153839</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20210507153839</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20210507153839</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82023154</appNstepUserId>");
            responseXml.append("		<appOrderId/>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>202104191519010006</globalNo>");
            responseXml.append("		<encYn/>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode/>");
            responseXml.append("		<responseLogcd/>");
            responseXml.append("		<responseTitle/>");
            responseXml.append("		<responseBasic/>");
            responseXml.append("		<langCode/>");
            responseXml.append("		<filler/>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<psblYn>Y</psblYn>");//Y N
            responseXml.append("		<rsltCd>00</rsltCd>");
            responseXml.append("		<rsltMsg>성공</rsltMsg>");
            responseXml.append("		<bizOrgCd>01</bizOrgCd>");
            responseXml.append("		<acceptTime>09:00</acceptTime>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("D02".equals(param)) {

            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>OP0</appEventCd>");
            responseXml.append("		<appSendDateTime>20210507153839</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20210507153839</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20210507153839</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82023154</appNstepUserId>");
            responseXml.append("		<appOrderId/>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>202104191519010006</globalNo>");
            responseXml.append("		<encYn/>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode/>");
            responseXml.append("		<responseLogcd/>");
            responseXml.append("		<responseTitle/>");
            responseXml.append("		<responseBasic/>");
            responseXml.append("		<langCode/>");
            responseXml.append("		<filler/>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<deliveryOrderId>143253999</deliveryOrderId>");
            responseXml.append("		<ktOrderId>KIS2021050710013</ktOrderId>");
            responseXml.append("		<rsltCd>00</rsltCd>");
            responseXml.append("		<rsltMsg>정상</rsltMsg>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("D03".equals(param)) {

            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>OP0</appEventCd>");
            responseXml.append("		<appSendDateTime>20210507153839</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20210507153839</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20210507153839</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82023154</appNstepUserId>");
            responseXml.append("		<appOrderId/>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>202104191519010006</globalNo>");
            responseXml.append("		<encYn/>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode/>");
            responseXml.append("		<responseLogcd/>");
            responseXml.append("		<responseTitle/>");
            responseXml.append("		<responseBasic/>");
            responseXml.append("		<langCode/>");
            responseXml.append("		<filler/>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<deliveryOrderId>143253999</deliveryOrderId>");
            responseXml.append("		<ktOrderId>KIS2021050710013</ktOrderId>");
            responseXml.append("		<rsltCd>00</rsltCd>");
            responseXml.append("		<rsltMsg>성공</rsltMsg>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("Y07".equals(param)) {
            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>Y07</appEventCd>");
            responseXml.append("		<appSendDateTime>20220801133105</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20220801133110</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20220801133110</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82254451</appNstepUserId>");
            responseXml.append("		<appOrderId/>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>202104191519010006</globalNo>");
            responseXml.append("		<encYn/>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode/>");
            responseXml.append("		<responseLogcd/>");
            responseXml.append("		<responseTitle/>");
            responseXml.append("		<responseBasic/>");
            responseXml.append("		<langCode/>");
            responseXml.append("		<filler/>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<intmMdlId>K7901367</intmMdlId>");
            responseXml.append("		<intmSeq>8982300421002878851</intmSeq>");
            responseXml.append("		<pukNo1>03243060</pukNo1>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("Y39".equals(param)) {
            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KCA</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>SPT8050</appAgncCd>");
            responseXml.append("		<appEventCd>Y39</appEventCd>");
            responseXml.append("		<appSendDateTime>20240304133105</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20240304133110</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20240304133110</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82023154</appNstepUserId>");
            responseXml.append("		<appOrderId/>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>JHW15616510004</globalNo>");
            responseXml.append("		<encYn/>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode/>");
            responseXml.append("		<responseLogcd/>");
            responseXml.append("		<responseTitle/>");
            responseXml.append("		<responseBasic/>");
            responseXml.append("		<langCode/>");
            responseXml.append("		<filler/>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<ipinCi>vTAH+121212+tCH5bVO/crf7t9a3w==</ipinCi>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("Y41".equals(param)) {
            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>Y41</appEventCd>");
            responseXml.append("		<appSendDateTime>20250204153409</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20250204153407</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20250204153407</appLgDateTime>");
            responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
            responseXml.append("		<appOrderId />");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>9122533020250204153407442</globalNo>");
            responseXml.append("		<encYn />");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("		<responseCode />");
            responseXml.append("		<responseLogcd />");
            responseXml.append("		<responseTitle />");
            responseXml.append("		<responseBasic />");
            responseXml.append("		<langCode />");
            responseXml.append("		<filler />");
            responseXml.append("	</commHeader>");
            responseXml.append("</return>");
        } else if ("Y42".equals(param)) {
            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>SKY</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>SPT8050</appAgncCd>");
            responseXml.append("		<appEventCd>Y42</appEventCd>");
            responseXml.append("		<appSendDateTime>20241018105844</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20240304133110</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20241018105841</appLgDateTime>");
            responseXml.append("		<appNstepUserId>82022194</appNstepUserId>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>moscRemindSmsStat-CU-00001</globalNo>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<resultCd>00</resultCd>");
            responseXml.append("		<smsRcvBlckYn>Y</smsRcvBlckYn>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        } else if ("Y48".equals(param)) {
            responseXml.append("<return>");
            responseXml.append("	<bizHeader>");
            responseXml.append("		<appEntrPrsnId>KIS</appEntrPrsnId>");
            responseXml.append("		<appAgncCd>AA00364</appAgncCd>");
            responseXml.append("		<appEventCd>Y48</appEventCd>");
            responseXml.append("		<appSendDateTime>20250627161537</appSendDateTime>");
            responseXml.append("		<appRecvDateTime>20250627161527</appRecvDateTime>");
            responseXml.append("		<appLgDateTime>20250627161527</appLgDateTime>");
            responseXml.append("		<appNstepUserId>91225330</appNstepUserId>");
            responseXml.append("	</bizHeader>");
            responseXml.append("	<commHeader>");
            responseXml.append("		<globalNo>9122533020250627161527609</globalNo>");
            responseXml.append("		<responseType>N</responseType>");
            responseXml.append("	</commHeader>");
            responseXml.append("	<outDto>");
            responseXml.append("		<moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("			<bankCd>090</bankCd>");
            responseXml.append("			<bankNm>카카오뱅크</bankNm>");
            responseXml.append("			<efctFnsDt>99991231000000</efctFnsDt>");
            responseXml.append("			<efctStDt>20240203001727</efctStDt>");
            responseXml.append("			<repVirtlBnkacnYn>N</repVirtlBnkacnYn>");
            responseXml.append("			<virtlBnkacnNo>9206033603712</virtlBnkacnNo>");
            responseXml.append("		</moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("		<moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("			<bankCd>004</bankCd>");
            responseXml.append("			<bankNm>국민은행</bankNm>");
            responseXml.append("			<efctFnsDt>99991231000000</efctFnsDt>");
            responseXml.append("			<efctStDt>20240202235342</efctStDt>");
            responseXml.append("			<repVirtlBnkacnYn>N</repVirtlBnkacnYn>");
            responseXml.append("			<virtlBnkacnNo>71799073446495</virtlBnkacnNo>");
            responseXml.append("		</moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("		<moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("			<bankCd>088</bankCd>");
            responseXml.append("			<bankNm>신한은행</bankNm>");
            responseXml.append("			<efctFnsDt>99991231000000</efctFnsDt>");
            responseXml.append("			<efctStDt>20230403000253</efctStDt>");
            responseXml.append("			<repVirtlBnkacnYn>N</repVirtlBnkacnYn>");
            responseXml.append("			<virtlBnkacnNo>56215781702331</virtlBnkacnNo>");
            responseXml.append("		</moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("		<moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("			<bankCd>089</bankCd>");
            responseXml.append("			<bankNm>케이뱅크</bankNm>");
            responseXml.append("			<efctFnsDt>99991231000000</efctFnsDt>");
            responseXml.append("			<efctStDt>20230402232816</efctStDt>");
            responseXml.append("			<repVirtlBnkacnYn>N</repVirtlBnkacnYn>");
            responseXml.append("			<virtlBnkacnNo>70005420400822</virtlBnkacnNo>");
            responseXml.append("		</moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("		<moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("			<bankCd>011</bankCd>");
            responseXml.append("			<bankNm>농협은행</bankNm>");
            responseXml.append("			<efctFnsDt>99991231000000</efctFnsDt>");
            responseXml.append("			<efctStDt>20260402234611</efctStDt>");
            responseXml.append("			<repVirtlBnkacnYn>N</repVirtlBnkacnYn>");
            responseXml.append("			<virtlBnkacnNo>79019324019884</virtlBnkacnNo>");
            responseXml.append("		</moscVirtlBnkacnNoListInfoOutDTO>");
            responseXml.append("	</outDto>");
            responseXml.append("</return>");
        }

        if ("Y42".equals(param)) {
            responseXml.append("</ns2:moscRemindSmsStatMgmtResponse></soap:Body></soap:Envelope>");
        } else {
            responseXml.append("</ns2:moscPerInfoResponse></soap:Body></soap:Envelope>");
        }

        vo.setResponseXml(responseXml.toString());
        try {
            vo.toResponseParse();
        } catch (Exception e) {
            result = false;
        }
        //////////////////////////////////

        return result;
    }


    /**
     * mPlatForm event 호출 log Counting
     *
     * @param userId
     * @param eventCd
     * @return
     */
    public int checkMpCallEventCount(String userId, String eventCd) {
        return this.mplatFormServerAdapter.checkMpCallEventCount(userId, eventCd);
    }
}
