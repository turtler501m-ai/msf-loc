package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.dto.AuthSmsDto;
import com.ktmmobile.msf.domains.form.common.dto.JsonReturnDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceLogDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscDataSharingResDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.OutDataSharingDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpCommonXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.msf.domains.form.common.repository.MspApiDirectRepository;
import com.ktmmobile.msf.domains.form.common.util.EncryptUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.ObjectUtils;
import com.ktmmobile.msf.domains.form.common.util.StringMakerUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.DataSharingReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.DataSharingResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyShareDataReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyShareDataResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.SvcChgPageRepositoryImpl;
import com.ktmmobile.msf.domains.form.system.cert.service.CertService;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;

@Slf4j
@Service
public class MsfSvcMyShareDataSvcImpl {

    @Autowired
    private MsfMplatFormService mplatFormService;

    @Autowired
    private SvcChgPageRepositoryImpl svcChgPageRepositoryImpl;

    @Autowired
    private CertService certService;

    @Autowired
    private MspApiDirectRepository mspApiDirectRepository;

    /**
     * x69 데이터 쉐어링 사전체크
     */
    public MoscDataSharingResDto moscDataSharingChk(MyShareDataReqDto myShareDataReqDto) {

        MoscDataSharingResDto moscDataSharingResDto = new MoscDataSharingResDto();
        String custId = "";
        String ncn = "";
        String ctn = "";
        String crprCtn = "";

        try {
            custId = myShareDataReqDto.getCustId();
            ncn = myShareDataReqDto.getNcn();
            ctn = myShareDataReqDto.getCtn();
            crprCtn = myShareDataReqDto.getCrprCtn();

            moscDataSharingResDto = mplatFormService.moscDataSharingChk(custId, ncn, ctn, crprCtn);

        } catch (SocketTimeoutException e1) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e1) {
            throw new McpCommonException(e1.getMessage());
        }

        return moscDataSharingResDto;
    }

    /**
     * x70 데이터 쉐어링 저장 & 해지
     */
    public void moscDataSharingSave(MyShareDataReqDto myShareDataReqDto) {

        String custId = "";
        String ncn = "";
        String ctn = "";
        String opmdSvcNo = "";
        String opmdWorkDivCd = "";

        try {
            custId = myShareDataReqDto.getCustId();
            ncn = myShareDataReqDto.getNcn();
            ctn = myShareDataReqDto.getCtn();
            opmdSvcNo = myShareDataReqDto.getOpmdSvcNo();
            opmdWorkDivCd = myShareDataReqDto.getOpmdWorkDivCd();

            mplatFormService.moscDataSharingSave(custId, ncn, ctn, opmdSvcNo, opmdWorkDivCd);

        } catch (SocketTimeoutException e1) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e1) {
            throw new McpCommonException(e1.getMessage());
        }
    }

    @BusinessContextBoundary
    public void moscDataSharingSaveWithParentScanId(MyShareDataReqDto myShareDataReqDto, String parentScanId) {
        BusinessContextHolder.setParentScanId(parentScanId);
        moscDataSharingSave(myShareDataReqDto);
    }

    /**
     * x71 데이터쉐어링 결합중인 대상 조회
     */
    public MoscDataSharingResDto mosharingList(MyShareDataReqDto myShareDataReqDto) {

        MoscDataSharingResDto res = new MoscDataSharingResDto();
        String custId = "";
        String ncn = "";
        String ctn = "";

        try {
            custId = myShareDataReqDto.getCustId();
            ncn = myShareDataReqDto.getNcn();
            ctn = myShareDataReqDto.getCtn();

            res = mplatFormService.mosharingList(custId, ncn, ctn);

        } catch (SocketTimeoutException e1) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e1) {
            throw new McpCommonException(e1.getMessage());
        }

        return res;
    }


    // 데이터 쉐어링 가입 상태를 조회한다.
    public FormResponse<DataSharingResDto> dataSharingList(DataSharingReqDto req) {
        if (!hasDataSharingBase(req)) {
            log.warn("[dataSharingList] invalid request: custIdPresent={}, ncn={}, ctnPresent={}",
                req != null && !"".equals(StringUtil.NVL(req.getCustId(), "")),
                req != null ? StringUtil.NVL(req.getNcn(), "") : "",
                req != null && !"".equals(normalizePhone(req.getCtn())));
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }
        try {
            log.info("[dataSharingList] X71 request: ncn={}, custIdPresent={}, ctnPresent={}",
                req.getNcn(), !"".equals(StringUtil.NVL(req.getCustId(), "")), !"".equals(normalizePhone(req.getCtn())));
            MoscDataSharingResDto platformRes = mplatFormService.mosharingList(
                req.getCustId(), req.getNcn(), normalizePhone(req.getCtn()));
            if (platformRes == null || !platformRes.isSuccess()) {
                log.info("[dataSharingList] X71 no subscribed target: ncn={}, resultCode={}, message={}",
                    req.getNcn(),
                    platformRes != null ? platformRes.getResultCode() : "",
                    platformRes != null ? StringUtil.NVL(platformRes.getSvcMsg(), "") : "");
                return FormResponse.of(ResSvcChgMessage.SUCCESS, new DataSharingResDto());
            }
            DataSharingResDto res = toDataSharingRes(platformRes);
            log.info("[dataSharingList] X71 success: ncn={}, resultCode={}, subscribed={}, itemCount={}, targetNoPresent={}",
                req.getNcn(),
                platformRes != null ? platformRes.getResultCode() : "",
                res.isSubscribed(),
                res.getItems() != null ? res.getItems().size() : 0,
                !"".equals(StringUtil.NVL(res.getTargetNo(), "")));
            return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
        } catch (SocketTimeoutException e) {
            log.warn("[dataSharingList] mplatform timeout: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, SOCKET_TIMEOUT_EXCEPTION, null);
        } catch (SelfServiceException e) {
            String message = dataSharingPlatformMessage(e);
            log.warn("[dataSharingList] mplatform business failed: ncn={}, resultCode={}, message={}",
                req.getNcn(), e.getResultCode(), message);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, null);
        } catch (McpCommonException e) {
            String message = StringUtil.NVL(e.getErrorMsg(), e.getMessage());
            log.warn("[dataSharingList] mplatform failed: ncn={}, message={}", req.getNcn(), message);
            return FormResponse.of(
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                StringUtil.NVL(message, "데이터쉐어링 가입 여부 조회 중 오류가 발생했습니다."),
                null
            );
        } catch (Exception e) {
            log.error("[dataSharingList] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "데이터쉐어링 가입 여부 조회 중 오류가 발생했습니다.", null);
        }
    }

    //데이터 쉐어링 가입 상태를 조회한다.
    public FormResponse<DataSharingResDto> dataSharingStep2(DataSharingReqDto req) {
        if (!hasDataSharingLookupKey(req)) {
            log.warn("[dataSharingStep2] invalid lookup key: ncn={}, contractNum={}, ctnPresent={}",
                req != null ? StringUtil.NVL(req.getNcn(), "") : "",
                req != null ? StringUtil.NVL(req.getContractNum(), "") : "",
                req != null && !"".equals(normalizePhone(req.getCtn())));
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        try {
            resolveDataSharingContractInfo(req);
            String ncn = StringUtil.NVL(req.getNcn(), "");
            String ctn = normalizePhone(req.getCtn());
            String custId = StringUtil.NVL(req.getCustId(), "");
            String contractNum = StringUtil.NVL(req.getContractNum(), ncn);
            if ("".equals(ncn) || "".equals(ctn) || "".equals(custId)) {
                log.warn("[dataSharingStep2] invalid resolved contract: ncn={}, contractNum={}, custIdPresent={}, ctnPresent={}",
                    ncn, contractNum, !"".equals(custId), !"".equals(ctn));
                return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
            }

            log.info("[dataSharingStep2] start: ncn={}, contractNum={}, custIdPresent={}, ctnPresent={}",
                ncn, contractNum, !"".equals(custId), !"".equals(ctn));

            DataSharingResDto res = new DataSharingResDto();
            res.setCustId(custId);
            res.setNcn(ncn);
            res.setContractNum(contractNum);
            res.setCtn(ctn);
            res.setSubStatus(StringUtil.NVL(req.getSubStatus(), ""));
            McpUserCntrMngDto socDesc = selectSocDesc(contractNum);
            if (socDesc != null) {
                res.setSoc(StringUtil.NVL(socDesc.getSoc(), ""));
                res.setRateNm(StringUtil.NVL(socDesc.getRateNm(), ""));
            }

            //20260609 확인필요  getCachedCodeList > isSharingRateSoc
            boolean socChk = isSharingRateSoc(socDesc);
            res.setSocChkYn(socChk ? "Y" : "N");

            String customerType = selectCustomerType(custId);
            boolean customerBlocked = "G".equals(customerType) || "B".equals(customerType);
            res.setCustomerType(customerBlocked ? "Y" : StringUtil.NVL(customerType, ""));
            res.setSubStatusYn("S".equals(StringUtil.NVL(req.getSubStatus(), "")) ? "Y" : "");

            String changeYn = resolveDataSharingBillChangeYn(ncn, ctn, custId);
            res.setChangeYn(changeYn);
            res.setIsMacTime("Y");

            String platformMessage = "";
            if (!customerBlocked) {
                log.info("[dataSharingStep2] X71 request: ncn={}, soc={}, socChkYn={}",
                    ncn, res.getSoc(), res.getSocChkYn());
                MoscDataSharingResDto platformRes = mplatFormService.mosharingList(custId, ncn, ctn);
                if (platformRes != null) {
                    res.setResultCode(StringUtil.NVL(platformRes.getResultCode(), ""));
                }
                if (platformRes != null && platformRes.isSuccess()) {
                    copyDataSharingState(toDataSharingRes(platformRes), res);
                } else {
                    platformMessage = platformRes != null ? StringUtil.NVL(platformRes.getSvcMsg(), "") : "";
                    log.info("[dataSharingStep2] X71 no subscribed target: ncn={}, resultCode={}, message={}",
                        ncn,
                        platformRes != null ? platformRes.getResultCode() : "",
                        platformMessage);
                }
            }

            boolean parentUnavailableByPlatform = false; //"ITL_SFC_E098".equals(res.getResultCode()); //20260622 확인필요
            if (res.isSubscribed()) {
                res.setAvailable(true);
                res.setParentAvailable(true);
                res.setMessage(StringUtil.NVL(res.getMessage(), ""));
            } else if (customerBlocked) {
                res.setAvailable(false);
                res.setParentAvailable(false);
                res.setMessage("데이터쉐어링 가입이 제한된 고객 유형입니다.");
            } else if (!socChk) {
                res.setAvailable(false);
                res.setParentAvailable(false);
                res.setMessage("결합 가능 모회선이 아닙니다.");
            } else if ("Y".equals(changeYn)) {
                res.setAvailable(false);
                res.setParentAvailable(false);
                res.setMessage("청구계약 정보로 데이터쉐어링 대상이 제한됩니다.");
            } else if (parentUnavailableByPlatform) {
                res.setAvailable(false);
                res.setParentAvailable(false);
                res.setMessage(platformMessage);
            } else {
                res.setAvailable(true);
                res.setParentAvailable(true);
                res.setMessage("");
            }

            log.info(
                "[dataSharingStep2] end: ncn={}, subscribed={}, parentAvailable={}, soc={}, socChkYn={}, customerType={}, changeYn={}, itemCount={}, targetNoPresent={}",
                ncn,
                res.isSubscribed(),
                res.isParentAvailable(),
                res.getSoc(),
                res.getSocChkYn(),
                res.getCustomerType(),
                res.getChangeYn(),
                res.getItems() != null ? res.getItems().size() : 0,
                !"".equals(StringUtil.NVL(res.getTargetNo(), "")));

            return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
        } catch (SocketTimeoutException e) {
            log.warn("[dataSharingStep2] mplatform timeout: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, SOCKET_TIMEOUT_EXCEPTION, null);
        } catch (SelfServiceException e) {
            String message = dataSharingPlatformMessage(e);
            log.warn("[dataSharingStep2] mplatform business failed: ncn={}, resultCode={}, message={}",
                req.getNcn(), e.getResultCode(), message);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, null);
        } catch (McpCommonException e) {
            log.warn("[dataSharingStep2] no-login contract lookup failed: ncn={}, contractNum={}, ctnPresent={}, message={}",
                req.getNcn(), req.getContractNum(), !"".equals(normalizePhone(req.getCtn())), e.getMessage());
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        } catch (Exception e) {
            log.error("[dataSharingStep2] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "데이터쉐어링 가능 여부 조회 중 오류가 발생했습니다.", null);
        }
    }

    private boolean hasDataSharingLookupKey(DataSharingReqDto req) {
        return req != null
            && (!"".equals(StringUtil.NVL(req.getNcn(), ""))
            || !"".equals(StringUtil.NVL(req.getContractNum(), ""))
            || !"".equals(normalizePhone(req.getCtn())));
    }

    private McpUserCntrMngDto resolveDataSharingContractInfo(DataSharingReqDto req) {
        McpUserCntrMngDto search = new McpUserCntrMngDto();
        String lookupNcn = StringUtil.NVL(req.getNcn(), req.getContractNum());
        String lookupCtn = normalizePhone(req.getCtn());
        if (!"".equals(lookupNcn)) {
            search.setSvcCntrNo(lookupNcn);
        } else if (!"".equals(lookupCtn)) {
            search.setCntrMobileNo(lookupCtn);
        }

        McpUserCntrMngDto cntrInfo = selectCntrListNoLogin(search);
        if (cntrInfo == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        req.setNcn(StringUtil.NVL(cntrInfo.getSvcCntrNo(), lookupNcn));
        req.setContractNum(StringUtil.NVL(cntrInfo.getContractNum(), req.getNcn()));
        req.setCtn(StringUtil.NVL(cntrInfo.getCntrMobileNo(), req.getCtn()));
        req.setCustId(StringUtil.NVL(cntrInfo.getCustId(), req.getCustId()));
        req.setSubStatus(StringUtil.NVL(cntrInfo.getSubStatus(), req.getSubStatus()));

        log.info("[dataSharingStep2] no-login contract resolved: ncn={}, contractNum={}, custIdPresent={}, ctnPresent={}, subStatus={}",
            req.getNcn(),
            req.getContractNum(),
            !"".equals(StringUtil.NVL(req.getCustId(), "")),
            !"".equals(normalizePhone(req.getCtn())),
            StringUtil.NVL(req.getSubStatus(), ""));
        return cntrInfo;
    }

    private boolean isSharingRateSoc(McpUserCntrMngDto socDesc) {
        if (socDesc == null || "".equals(StringUtil.NVL(socDesc.getSoc(), ""))) {
            return false;
        }
        String soc = socDesc.getSoc();
        boolean result = mspApiDirectRepository.selectIsSharingRateSoc(soc);
        log.debug("[isSharingRateSoc] soc={}, result={}", soc, result);
        return result;
    }

    private String resolveDataSharingBillChangeYn(String ncn, String ctn, String custId) {
        try {
            MpFarChangewayInfoVO changeInfo = farChangewayInfo(ncn, ctn, custId);
            if (changeInfo != null
                && "지로".equals(changeInfo.getPayMethod())
                && !"".equals(StringUtil.NVL(changeInfo.getBlAddr(), ""))) {
                return "Y";
            }
        } catch (Exception e) {
            log.warn("[dataSharingStep2] farChangewayInfo failed: ncn={}, message={}", ncn, e.getMessage());
        }
        return "N";
    }

    private MpFarChangewayInfoVO farChangewayInfo(String ncn, String ctn, String custId) {
        try {
            return mplatFormService.farChangewayInfo(ncn, ctn, custId);
        } catch (SelfServiceException e) {
            log.info("Exception e : {}", e.getMessage());
        } catch (Exception e) {
            log.debug("X23 조회 에러");
        }
        return null;
    }

    private void copyDataSharingState(DataSharingResDto source, DataSharingResDto target) {
        target.setSubscribed(source.isSubscribed());
        target.setAvailable(source.isAvailable());
        target.setTargetNo(source.getTargetNo());
        target.setMessage(StringUtil.NVL(source.getMessage(), ""));
        target.setItems(source.getItems());
    }

    @SuppressWarnings("unchecked")
    private McpUserCntrMngDto selectSocDesc(String svcCntrNo) {
        return mspApiDirectRepository.query("/mypage/socDesc", svcCntrNo, McpUserCntrMngDto.class);
    }

    private String selectCustomerType(String custId) {
        return mspApiDirectRepository.query("/mypage/customerType", custId, String.class);
    }

    private McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        if (userCntrMngDto == null
            || (userCntrMngDto.getSvcCntrNo() == null && userCntrMngDto.getCntrMobileNo() == null)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        return svcChgPageRepositoryImpl.selectCntrListNoLogin(userCntrMngDto);
    }

    private List<McpUserCntrMngDto> selectCntrList(String userId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);

        List<McpUserCntrMngDto> list = mspApiDirectRepository.query("/mypage/cntrList", params, List.class);
        if (list != null) {
            String today = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
            for (McpUserCntrMngDto dto : list) {
                String strUnUserSSn = dto.getUnUserSSn();
                dto.setAge(Integer.toString(NmcpServiceUtils.getAge(strUnUserSSn, today)));
                if (strUnUserSSn != null && strUnUserSSn.length() > 5) {
                    dto.setBirth(strUnUserSSn.substring(0, 6));
                } else if (strUnUserSSn != null) {
                    dto.setBirth(strUnUserSSn);
                }
            }
        }
        return list;
    }

    public FormResponse<DataSharingResDto> dataSharingCheck(DataSharingReqDto req) {
        if (!hasDataSharingBase(req) || "".equals(normalizePhone(req.getOpmdSvcNo()))) {
            log.warn("[dataSharingCheck] invalid request: custIdPresent={}, ncn={}, ctnPresent={}, opmdSvcNoPresent={}",
                req != null && !"".equals(StringUtil.NVL(req.getCustId(), "")),
                req != null ? StringUtil.NVL(req.getNcn(), "") : "",
                req != null && !"".equals(normalizePhone(req.getCtn())),
                req != null && !"".equals(normalizePhone(req.getOpmdSvcNo())));
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }
        try {
            String opmdSvcNo = normalizePhone(req.getOpmdSvcNo());
            log.info("[dataSharingCheck] X69 request: ncn={}, custId={}, ctn={}, opmdSvcNo={}, opmdWorkDivCd={}, contractNum={}",
                req.getNcn(), req.getCustId(), req.getCtn(), opmdSvcNo, req.getOpmdWorkDivCd(), req.getContractNum());
            MoscDataSharingResDto platformRes = mplatFormService.moscDataSharingChk(
                req.getCustId(), req.getNcn(), normalizePhone(req.getCtn()), "");
            if (platformRes == null || !platformRes.isSuccess()) {
                DataSharingResDto res = new DataSharingResDto();
                res.setTargetNo(opmdSvcNo);
                res.setAvailable(false);
                res.setMessage(platformRes != null
                    ? StringUtil.NVL(platformRes.getSvcMsg(), "데이터쉐어링 가입이 불가능합니다.")
                    : "데이터쉐어링 가입 가능 여부를 확인하지 못했습니다.");
                log.info("[dataSharingCheck] X69 unavailable: ncn={}, opmdSvcNoPresent={}, resultCode={}, message={}",
                    req.getNcn(),
                    !"".equals(opmdSvcNo),
                    platformRes != null ? platformRes.getResultCode() : "",
                    res.getMessage());
                return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
            }
            DataSharingResDto res = toDataSharingRes(platformRes);
            res.setTargetNo(opmdSvcNo);
            res.setAvailable(hasAvailableSharingTarget(platformRes));
            if (!res.isAvailable() && "".equals(StringUtil.NVL(res.getMessage(), ""))) {
                res.setMessage("데이터쉐어링 가입이 불가능합니다.");
            }
            log.info("[dataSharingCheck] X69 success: ncn={}, opmdSvcNoPresent={}, resultCode={}, available={}, itemCount={}, messagePresent={}",
                req.getNcn(),
                !"".equals(opmdSvcNo),
                platformRes != null ? platformRes.getResultCode() : "",
                res.isAvailable(),
                res.getItems() != null ? res.getItems().size() : 0,
                !"".equals(StringUtil.NVL(res.getMessage(), "")));
            return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
        } catch (SelfServiceException e) {
            String message = dataSharingPlatformMessage(e);
            log.warn("[dataSharingCheck] mplatform business failed: ncn={}, opmdSvcNo={}, resultCode={}, message={}",
                req.getNcn(), req.getOpmdSvcNo(), e.getResultCode(), message);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, null);
        } catch (McpCommonException e) {
            String message = StringUtil.NVL(e.getErrorMsg(), e.getMessage());
            log.warn("[dataSharingCheck] mplatform failed: ncn={}, opmdSvcNo={}, message={}",
                req.getNcn(), req.getOpmdSvcNo(), message);
            return FormResponse.of(
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                StringUtil.NVL(message, "데이터쉐어링 가입 가능 여부 확인 중 오류가 발생했습니다."),
                null
            );
        } catch (Exception e) {
            log.error("[dataSharingCheck] failed: ncn={}, opmdSvcNo={}", req.getNcn(), req.getOpmdSvcNo(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "데이터쉐어링 가입 가능 여부 확인 중 오류가 발생했습니다.", null);
        }
    }

    @BusinessContextBoundary
    public FormResponse<Void> processDataSharing(ServiceChangeCompleteReqDto req) {
        ServiceChangeCompleteReqDto.DataSharing dataSharing = req.getDataSharing();
        if (dataSharing == null) {
            log.warn("[serviceChangeComplete] R15 dataSharing is empty: ncn={}",
                req.getNcn());
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        String workDivCd = resolveDataSharingWorkDivCd(dataSharing.getShareUseState());
        String opmdSvcNo = normalizePhone("C".equals(workDivCd)
            ? StringUtil.NVL(dataSharing.getDataSharingTargetNo(), dataSharing.getSharePhoneNum())
            : dataSharing.getSharePhoneNum());
        log.info(
            "[serviceChangeComplete] R15 request: ncn={}, shareUseState={}, workDivCd={}, opmdSvcNoPresent={}, usimPresent={}, confirmCompleted={}",
            req.getNcn(),
            dataSharing.getShareUseState(),
            workDivCd,
            !"".equals(opmdSvcNo),
            !"".equals(StringUtil.NVL(dataSharing.getShareUsimNum(), "")),
            dataSharing.getDataSharingConfirmCompleted());
        if ("".equals(workDivCd) || "".equals(opmdSvcNo)) {
            log.warn("[serviceChangeComplete] R15 invalid dataSharing: ncn={}, shareUseState={}, workDivCd={}, opmdSvcNoPresent={}",
                req.getNcn(), dataSharing.getShareUseState(), workDivCd, !"".equals(opmdSvcNo));
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }
        if (!Boolean.TRUE.equals(dataSharing.getDataSharingConfirmCompleted())) {
            log.warn("[serviceChangeComplete] R15 confirm incomplete: ncn={}, shareUseState={}, workDivCd={}",
                req.getNcn(), dataSharing.getShareUseState(), workDivCd);
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID, "데이터쉐어링 작성 완료 상태가 아닙니다.", null);
        }
        if ("A".equals(workDivCd)
            && (!Boolean.TRUE.equals(dataSharing.getDataSharingAuthCompleted())
            || !Boolean.TRUE.equals(dataSharing.getDataSharingUsimCheckCompleted())
            || !Boolean.TRUE.equals(dataSharing.getDataSharingAvailableChecked())
            || !Boolean.TRUE.equals(dataSharing.getDataSharingAgreementCompleted()))) {
            log.warn("[serviceChangeComplete] R15 required check incomplete: ncn={}, auth={}, usim={}, available={}, agreement={}",
                req.getNcn(),
                dataSharing.getDataSharingAuthCompleted(),
                dataSharing.getDataSharingUsimCheckCompleted(),
                dataSharing.getDataSharingAvailableChecked(),
                dataSharing.getDataSharingAgreementCompleted());
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID, "데이터쉐어링 가입 필수 확인이 완료되지 않았습니다.", null);
        }

        MyShareDataReqDto shareReq = new MyShareDataReqDto();
        shareReq.setCustId(req.getCustId());
        shareReq.setNcn(req.getNcn());
        shareReq.setCtn(req.getCtn());
        shareReq.setCrprCtn(req.getCtn());
        shareReq.setOpmdSvcNo(opmdSvcNo);
        shareReq.setOpmdWorkDivCd(workDivCd);
        shareReq.setIccId(StringUtil.NVL(dataSharing.getShareUsimNum(), ""));

        try {
            if ("A".equals(workDivCd)) {
                log.info("[serviceChangeComplete] R15 X69 precheck request: ncn={}, crprCtnPresent={}, ctnPresent={}",
                    req.getNcn(), false, !"".equals(StringUtil.NVL(shareReq.getCtn(), "")));
                MoscDataSharingResDto chkRes = mplatFormService.moscDataSharingChk(
                    shareReq.getCustId(), shareReq.getNcn(), shareReq.getCtn(), "");
                if (!hasAvailableSharingTarget(chkRes)) {
                    log.warn("[serviceChangeComplete] R15 X69 precheck failed: ncn={}, opmdSvcNoPresent={}, resultCode={}, itemCount={}",
                        req.getNcn(),
                        !"".equals(opmdSvcNo),
                        chkRes != null ? chkRes.getResultCode() : "",
                        chkRes != null && chkRes.getSharingList() != null ? chkRes.getSharingList().size() : 0);
                    return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "데이터쉐어링 가입 가능한 대상이 아닙니다.", null);
                }
                log.info("[serviceChangeComplete] R15 X69 precheck success: ncn={}, opmdSvcNoPresent={}, resultCode={}, itemCount={}",
                    req.getNcn(),
                    !"".equals(opmdSvcNo),
                    chkRes != null ? chkRes.getResultCode() : "",
                    chkRes != null && chkRes.getSharingList() != null ? chkRes.getSharingList().size() : 0);
            }

            BusinessContextHolder.setParentScanId(req.getParentScanId());
            log.info("[serviceChangeComplete] R15 X70 save request: ncn={}, workDivCd={}, opmdSvcNoPresent={}, opmdSvcNo={}",
                req.getNcn(), workDivCd, !"".equals(shareReq.getOpmdSvcNo()), shareReq.getOpmdSvcNo());
            MpCommonXmlVO saveRes = mplatFormService.moscDataSharingSave(
                shareReq.getCustId(), shareReq.getNcn(), shareReq.getCtn(), shareReq.getOpmdSvcNo(), shareReq.getOpmdWorkDivCd());
            if (saveRes != null && !saveRes.isSuccess()) {
                log.warn("[serviceChangeComplete] R15 X70 save failed: ncn={}, workDivCd={}, resultCode={}, message={}",
                    req.getNcn(), workDivCd, saveRes.getResultCode(), saveRes.getSvcMsg());
                return FormResponse.of(
                    ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                    StringUtil.NVL(saveRes.getSvcMsg(), "데이터쉐어링 처리에 실패했습니다."),
                    null
                );
            }
            log.info("[serviceChangeComplete] R15 X70 save success: ncn={}, workDivCd={}, resultCode={}",
                req.getNcn(), workDivCd, saveRes != null ? saveRes.getResultCode() : "");
            return FormResponse.of(ResSvcChgMessage.SUCCESS, null);
        } catch (McpCommonException e) {
            log.warn("[serviceChangeComplete] R15 failed: ncn={}, opmdSvcNo={}, workDivCd={}, message={}",
                req.getNcn(), opmdSvcNo, workDivCd, e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        } catch (Exception e) {
            log.error("[serviceChangeComplete] R15 unexpected error: ncn={}, opmdSvcNo={}, workDivCd={}",
                req.getNcn(), opmdSvcNo, workDivCd, e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "데이터쉐어링 처리 중 오류가 발생했습니다.", null);
        }
    }

    private String resolveDataSharingWorkDivCd(String shareUseState) {
        if ("shareUseState1".equals(shareUseState)) {
            return "A";
        }
        if ("shareUseState2".equals(shareUseState)) {
            return "C";
        }
        return "";
    }

    private boolean hasDataSharingBase(DataSharingReqDto req) {
        return req != null
            && !"".equals(StringUtil.NVL(req.getCustId(), ""))
            && !"".equals(StringUtil.NVL(req.getNcn(), ""))
            && !"".equals(normalizePhone(req.getCtn()));
    }

    private DataSharingResDto toDataSharingRes(MoscDataSharingResDto platformRes) {
        DataSharingResDto res = new DataSharingResDto();
        List<OutDataSharingDto> items = platformRes == null || platformRes.getSharingList() == null
            ? new ArrayList<>()
            : platformRes.getSharingList();
        res.setItems(items);
        OutDataSharingDto first = firstDataSharingItem(items);
        if (first != null) {
            res.setSubscribed(true);
            res.setTargetNo(normalizePhone(first.getSvcNo()));
            res.setAvailable("Y".equals(first.getRsltInd()));
            res.setMessage(StringUtil.NVL(first.getRsltMsg(), ""));
        }
        return res;
    }

    private OutDataSharingDto firstDataSharingItem(List<OutDataSharingDto> items) {
        if (items == null) {
            return null;
        }
        for (OutDataSharingDto item : items) {
            if (item != null && !"".equals(StringUtil.NVL(item.getSvcNo(), ""))) {
                return item;
            }
        }
        return null;
    }

    private String normalizePhone(String value) {
        return StringUtil.NVL(value, "").replaceAll("[^0-9]", "");
    }

    private String dataSharingPlatformMessage(SelfServiceException e) {
        if (e == null) {
            return "데이터쉐어링 가입 가능 여부 확인 중 오류가 발생했습니다.";
        }
        String message = StringUtil.NVL(e.getMessageNe(), "");
        if (!"".equals(message)) {
            return message;
        }
        message = StringUtil.NVL(e.getMessage(), "");
        int delimiterIndex = message.indexOf(";;;");
        if (delimiterIndex >= 0 && delimiterIndex + 3 < message.length()) {
            return message.substring(delimiterIndex + 3);
        }
        return "".equals(message) ? "데이터쉐어링 가입 가능 여부 확인 중 오류가 발생했습니다." : message;
    }


    private boolean hasAvailableSharingTarget(MoscDataSharingResDto chkRes) {
        if (chkRes == null || chkRes.getSharingList() == null) {
            return false;
        }
        for (OutDataSharingDto dto : chkRes.getSharingList()) {
            if (dto != null && "Y".equals(dto.getRsltInd())) {
                return true;
            }
        }
        return false;
    }


    public Map<String, Object> doMySharingCntrInfo(HttpServletRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        String userRtnUrl = "/content/mySharingView.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            userRtnUrl = "/m/content/mySharingView.do";
        }

        try {
            AuthenticationUtils.getUser();
            rtnMap.put("redirectUrl", userRtnUrl);
        } catch (RuntimeException e) {
            log.debug("[doMySharingCntrInfo] authenticated user is unavailable: {}", e.getMessage());
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    public Map<String, Object> doMySharingView(
        HttpServletRequest request
        , MyPageSearchDto searchVO
        , String menuType
        , String phoneNum
    ) {

        HashMap<String, Object> rtnMap = new HashMap<>();

        // 기존 화면 반환 URL은 REST 응답 구조에서 사용하지 않는다.
        String rtnUrl = "/mySharingCntrInfo.do";
        MyShareDataReqDto myShareDataReqDto = new MyShareDataReqDto();

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            rtnUrl = "/m/mySharingCntrInfo.do";
        }

        // 기존 중복 요청 체크의 성공 redirect URL은 REST 응답 구조에서 사용하지 않는다.

        AuthSmsDto authSmsDto = null;
        UserSessionDto userSessionDto = getAuthenticatedUserSession();

        if(userSessionDto == null &&  authSmsDto  == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        String userType = ""; //외국인여부
        String[] certKey= null;
        String[] certValue= null;

        //비회원로그인
        if(authSmsDto != null) {

            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            if(phoneNum != null) {
                userCntrMngDto.setCntrMobileNo(phoneNum);
            } else {
                userCntrMngDto.setSvcCntrNo(searchVO.getNcn());
            }

            McpUserCntrMngDto cntrList =  selectCntrListNoLogin(userCntrMngDto);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }

            if(cntrList.getUnUserSSn() != null && cntrList.getUnUserSSn().length() > 12) {
                userType = cntrList.getUnUserSSn().substring(6,7);
                if("5".equals(userType) || "6".equals(userType)) {
                    userType = "Y";
                    rtnMap.put("userType", userType);
                }

            }

            searchVO.setCustId(cntrList.getCustId());
            searchVO.setContractNum(cntrList.getContractNum());
            searchVO.setNcn(cntrList.getSvcCntrNo());
            searchVO.setCtn(cntrList.getCntrMobileNo());
            searchVO.setSubStatus(cntrList.getSubStatus());
            searchVO.setUserDivisionYn("02");

            myShareDataReqDto.setCustId(searchVO.getCustId());
            myShareDataReqDto.setNcn(searchVO.getNcn());
            myShareDataReqDto.setCtn(searchVO.getCtn());
            myShareDataReqDto.setCrprCtn(searchVO.getCtn());
            rtnMap.put("searchVO", searchVO);

            // ============ STEP START ============
            // 이름, 생년월일, 계약번호
            certKey= new String[]{"urlType", "name", "birthDate", "contractNum"};
            certValue= new String[]{"memberAuth", cntrList.getUserName(), EncryptUtil.ace256Enc(cntrList.getUnUserSSn()), cntrList.getContractNum()};
            // ============ STEP END ============

        } else {

            String userId = userSessionDto.getUserId();
            List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();

            // 본인이 가지고 있는 회선정보
            cntrList = selectCntrList(userId);

            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
            }

            if("Y".equals(searchVO.getUserType())) {
                rtnMap.put("userType", searchVO.getUserType());
            }

            myShareDataReqDto.setCustId(searchVO.getCustId());
            myShareDataReqDto.setNcn(searchVO.getNcn());
            myShareDataReqDto.setCtn(searchVO.getCtn());
            myShareDataReqDto.setCrprCtn(searchVO.getCtn());
            rtnMap.put("cntrList", cntrList);

        }

        // 현재 요금제 조회
        // 서비스계약번호
        McpUserCntrMngDto mcpUserCntrMngDto = selectSocDesc(searchVO.getContractNum());
        MoscDataSharingResDto moscDataSharingResDto  = new MoscDataSharingResDto();

        //고객구분여부
        String customerType = selectCustomerType(searchVO.getCustId());

        if("G".equals(customerType) || "B".equals(customerType)) {
            customerType = "Y";
            rtnMap.put("customerType", customerType); // 현재
        }

        String resultCode = "";
        String message ="";
        String socChkYn = "";
        if (mcpUserCntrMngDto != null
            && !"".equals(StringUtil.NVL(mcpUserCntrMngDto.getSoc(), ""))
            && mspApiDirectRepository.selectIsSharingRateSoc(mcpUserCntrMngDto.getSoc())) {
            socChkYn = "Y";
        }

        //x71
        if(!"Y".equals(customerType) && !"Y".equals(userType)) {
            moscDataSharingResDto = this.mosharingList(myShareDataReqDto);
            if(moscDataSharingResDto.isSuccess()) {
                resultCode = "00";
                message = "";
            }
        }

        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
        String subStatusYn = "";

        //일시정지
        if("S".equals(searchVO.getSubStatus())) {
            subStatusYn = "Y";
        }

        //청구계약조회
        MpFarChangewayInfoVO changeInfo = farChangewayInfo(myShareDataReqDto.getNcn(),
            myShareDataReqDto.getCtn(),
            myShareDataReqDto.getCustId());

        String changeYn = "N";
        if (changeInfo != null
            && "지로".equals(changeInfo.getPayMethod()) && !"".equals(changeInfo.getBlAddr())) {
            changeYn = "Y";
        }

        // ============ STEP START ============
        // 비회원 쉐어링 가능한 경우만 STEP 시작
        if (certKey != null
            && !"Y".equals(changeYn) && !"Y".equals(customerType) && "Y".equals(socChkYn) && !"Y".equals(userType)
            && (moscDataSharingResDto.getSharingList() == null || moscDataSharingResDto.getSharingList().isEmpty())) {
            certService.vdlCertInfo("C", certKey, certValue);
        }
        // ============ STEP END ============

        rtnMap.put("changeYn", changeYn); // 청구계약
        rtnMap.put("resultCode", resultCode); //
        rtnMap.put("message", message); //
        rtnMap.put("mcpUserCntrMngDto", mcpUserCntrMngDto); //
        rtnMap.put("subStatusYn", subStatusYn); //
        rtnMap.put("moscDataSharingResDto", moscDataSharingResDto); //
        rtnMap.put("menuType", menuType); //
        rtnMap.put("socChkYn", socChkYn); // 쉐어링 불가 요금제


        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    public Map<String, Object> dataSharingStep1() {

        HashMap<String, Object> rtnMap = new HashMap<>();

        // 기존 화면 반환 URL은 REST 응답 구조에서 사용하지 않는다.
        String userRtnUrl = "/content/dataSharingStep2.do";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            userRtnUrl = "/m/content/dataSharingStep2.do";
        }

        try {
            AuthenticationUtils.getUser();
            rtnMap.put("redirectUrl", userRtnUrl);
        } catch (RuntimeException e) {
            log.debug("[dataSharingStep1] authenticated user is unavailable: {}", e.getMessage());
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    public Map<String, Object> dataSharingStep3(
        HttpServletRequest request
        , String contractNum
        , String onOffType
        , String cstmrType
        , MyPageSearchDto searchVO
    ) {

        HashMap<String, Object> rtnMap = new HashMap<>();

        String rtnUrl = "/content/dataSharingStep2.do";
        // 기존 화면 반환 URL은 REST 응답 구조에서 사용하지 않는다.
        String stepErrReturnUrl = "/main.do";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            rtnUrl = "/m/content/dataSharingStep2.do";
            stepErrReturnUrl = "/m/main.do";
        }

        if(contractNum == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        MyShareDataReqDto myShareDataReqDto = new MyShareDataReqDto();

        NiceLogDto niceLogDto= new NiceLogDto();

        AuthSmsDto authSmsDto = null;
        UserSessionDto userSessionDto = getAuthenticatedUserSession();

        if(userSessionDto == null &&  authSmsDto  == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        String[] certKey= null;
        String[] certValue= null;
        Map<String,String> vldReslt= null;

        //비회원
        if(authSmsDto != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(contractNum);
            McpUserCntrMngDto cntrList =  selectCntrListNoLogin(userCntrMngDto);

            if(cntrList  == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }
            myShareDataReqDto.setBirthday(cntrList.getDobyyyymmdd());
            myShareDataReqDto.setCtn(StringMakerUtil.getPhoneNum(cntrList.getCntrMobileNo())); // 전화번호
            myShareDataReqDto.setNcn(cntrList.getSvcCntrNo());
            myShareDataReqDto.setOpmdSvcNo(cntrList.getUnSvcNo());
            myShareDataReqDto.setName(cntrList.getUserName());
            myShareDataReqDto.setContractNum(cntrList.getContractNum());

            // ============ STEP START ============
            // 이름, 생년월일, 계약번호
            certKey= new String[]{"urlType", "name", "birthDate", "contractNum"};
            certValue= new String[]{"chkMemberAuth", cntrList.getUserName(), EncryptUtil.ace256Enc(cntrList.getUnUserSSn()), cntrList.getContractNum()};

            vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonException(vldReslt.get("RESULT_DESC"), stepErrReturnUrl);
            }
            // ============ STEP END ============

            niceLogDto.setName(cntrList.getUserName());
            niceLogDto.setBirthDate(cntrList.getUnUserSSn());

            // 고객인증 sms인증 세션은 데이터 쉐어링 개통요청 시점에 null > 비로그인 데이터쉐어링 고객 인증정보 별도 보관
            McpUserCntrMngDto mcpUserCntrMngDto= new McpUserCntrMngDto();
            mcpUserCntrMngDto.setSubLinkName(authSmsDto.getAuthNum());   // 이름
            mcpUserCntrMngDto.setCntrMobileNo(authSmsDto.getPhoneNum()); // 전화번호

        } else {

            searchVO.setNcn(contractNum);

            List<McpUserCntrMngDto> cntrList = selectCntrList(userSessionDto.getUserId());

            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
            }

            String userSsn= null;
            for(McpUserCntrMngDto dto : cntrList) {
                if(contractNum.equals(dto.getSvcCntrNo())) {
                    myShareDataReqDto.setBirthday(userSessionDto.getBirthday());
                    myShareDataReqDto.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn())); // 전화번호
                    myShareDataReqDto.setNcn(searchVO.getNcn());
                    myShareDataReqDto.setOpmdSvcNo(dto.getUnSvcNo());
                    myShareDataReqDto.setName(userSessionDto.getName());
                    myShareDataReqDto.setContractNum(dto.getContractNum());
                    userSsn= dto.getUnUserSSn();
                }
            }

            // ============ STEP START ============
            // 계약번호
            certKey= new String[]{"urlType", "contractNum"};
            certValue= new String[]{"chkMemberAuth", myShareDataReqDto.getContractNum()};

            vldReslt= certService.vdlCertInfo("F", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonException(vldReslt.get("RESULT_DESC"), stepErrReturnUrl);
            }

            // 이름, 생년월일, 계약번호
            certKey= new String[]{"urlType", "name", "birthDate", "contractNum"};
            certValue= new String[]{"memberAuth", myShareDataReqDto.getName(), EncryptUtil.ace256Enc(userSsn), myShareDataReqDto.getContractNum()};
            vldReslt= certService.vdlCertInfo("C", certKey, certValue);
            // ============ STEP END ============

            niceLogDto.setName(myShareDataReqDto.getName());
            niceLogDto.setBirthDate(userSsn);
        }

        // ============ STEP START ============
        // nicePin연동
        Map<String, String> nicePinRtn= null;//nicePinService.getNicePinCi(niceLogDto);
        if (!"0000".equals(nicePinRtn.get("returnCode"))) {
            throw new McpCommonException(nicePinRtn.get("returnMsg"), stepErrReturnUrl);
        }
        // ============ STEP END ============

        log.info("[WOO][WOO][WOO]MyShareDataReqDto==>" + ObjectUtils.convertObjectToString(myShareDataReqDto));
        rtnMap.put("cstmrType", cstmrType);
        rtnMap.put("contractNum", contractNum);
        rtnMap.put("onOffType", onOffType);
        rtnMap.put("myShareDataReqDto", myShareDataReqDto);
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    public Map<String, Object> dataSharingStep4(
        HttpServletRequest request
        , MyShareDataReqDto myShareDataReqDto
    ) {

        HashMap<String, Object> rtnMap = new HashMap<>();

        // 기존 화면 반환 URL은 REST 응답 구조에서 사용하지 않는다.
        String rtnUrl = "/content/dataSharingStep2.do";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            rtnUrl = "/m/content/dataSharingStep2.do";
        }

        String rtnOpmdSvcNo = myShareDataReqDto.getOpmdSvcNo();

        if(rtnOpmdSvcNo == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        MyShareDataResDto myShareDataResDto = new MyShareDataResDto();
        String svcNo = "";  //본인핸드폰번호
        String socNm = "";  //본인요금제
        String opmdSvcNoContractNum = ""; //개통이 완료된 서비스계약번호
        String opmdSvcSocNm = ""; //가입계약 요금제

        McpUserCntrMngDto nonMemberSess = null;
        UserSessionDto userSessionDto = getAuthenticatedUserSession();

        if(userSessionDto == null &&  nonMemberSess  == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        if(nonMemberSess != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(myShareDataReqDto.getNcn());
            McpUserCntrMngDto cntrList =  selectCntrListNoLogin(userCntrMngDto);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }
            svcNo = StringMakerUtil.getPhoneNum(cntrList.getCntrMobileNo());
            myShareDataReqDto.setCustId(cntrList.getCustId());
            myShareDataReqDto.setNcn(cntrList.getContractNum());
            myShareDataReqDto.setCtn(cntrList.getCntrMobileNo());
            myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());
        } else {
            List<McpUserCntrMngDto> cntrList = selectCntrList(userSessionDto.getUserId());
            if (cntrList != null && cntrList.size() > 0) {
                for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                    if (myShareDataReqDto.getNcn().equals(mcpUserCntrMngDto.getContractNum())) {
                        svcNo = StringMakerUtil.getPhoneNum(mcpUserCntrMngDto.getUnSvcNo());
                        myShareDataReqDto.setCustId(mcpUserCntrMngDto.getCustId());
                        myShareDataReqDto.setNcn(mcpUserCntrMngDto.getContractNum());
                        myShareDataReqDto.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
                        myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());
                        break;
                    }
                }
            }
        }

        McpUserCntrMngDto result = selectSocDesc(myShareDataReqDto.getNcn());

        if(result !=null) {
            socNm = result.getRateNm();
        }

        if(!StringUtil.isBlank(opmdSvcNoContractNum)) {
            McpUserCntrMngDto opmdSvcNoDto = selectSocDesc(opmdSvcNoContractNum); //가입하는 데이터쉐어링 요금제
            if(opmdSvcNoDto !=null) {
                opmdSvcSocNm =opmdSvcNoDto.getRateNm();
            }
        }

        myShareDataResDto.setSvcNo(svcNo);
        myShareDataResDto.setSocNm(socNm);
        myShareDataResDto.setOpmdSvcSocNm(opmdSvcSocNm);
        myShareDataResDto.setOpmdSvcNo(StringMakerUtil.getPhoneNum(myShareDataReqDto.getOpmdSvcNo()));
        rtnMap.put("myShareDataResDto", myShareDataResDto);
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    public JsonReturnDto domyCntrListAjax(
        HttpServletRequest request
        , MyPageSearchDto searchVO
        , String contractNum
    ) {

        JsonReturnDto jsonReturnDto = new JsonReturnDto();
        MyShareDataReqDto myShareDataReqDto = new MyShareDataReqDto();
        Map<String, Object> map = new HashMap<String, Object>();

        AuthSmsDto authSmsDto = null;
        UserSessionDto userSessionDto = getAuthenticatedUserSession();

        if(userSessionDto == null &&  authSmsDto  == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        //비회원
        if(authSmsDto != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(contractNum);

            McpUserCntrMngDto cntrList =  selectCntrListNoLogin(userCntrMngDto);
            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }

            searchVO.setCustId(cntrList.getCustId());
            searchVO.setContractNum(cntrList.getContractNum());
            searchVO.setNcn(cntrList.getSvcCntrNo());
            searchVO.setCtn(cntrList.getCntrMobileNo());
            searchVO.setSubStatus(cntrList.getSubStatus());
            myShareDataReqDto.setCustId(searchVO.getCustId());
            myShareDataReqDto.setNcn(searchVO.getNcn());
            myShareDataReqDto.setCtn(searchVO.getCtn());
            myShareDataReqDto.setCrprCtn(searchVO.getCtn());
        } else {

            String userId = userSessionDto.getUserId();
            List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();

            // 본인이 가지고 있는 회선정보
            cntrList = selectCntrList(userId);

            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                throw new McpCommonJsonException("0098" ,NOT_FULL_MEMBER_EXCEPTION);
            }

            myShareDataReqDto.setCustId(searchVO.getCustId());
            myShareDataReqDto.setNcn(searchVO.getNcn());
            myShareDataReqDto.setCtn(searchVO.getCtn());
            myShareDataReqDto.setCrprCtn(searchVO.getCtn());

        }


        Object result = null;
        String resultCode = "";
        String message ="";
        MoscDataSharingResDto  moscDataSharingResDto = new MoscDataSharingResDto();
        //x71
        moscDataSharingResDto = this.mosharingList(myShareDataReqDto);

        if(moscDataSharingResDto != null) {
            result = moscDataSharingResDto.getSharingList();
        }
        if(moscDataSharingResDto.isSuccess()) {
            resultCode = "00";
            message = "";
        }

        String subStatusYn = "";
        //일시정지
        if("S".equals(searchVO.getSubStatus())) {
            subStatusYn = "Y";
        }
        map.put("subStatusYn", subStatusYn);
        jsonReturnDto.setResultMap(map);
        jsonReturnDto.setMessage(message);
        jsonReturnDto.setReturnCode(resultCode);
        jsonReturnDto.setResult(result);
        return jsonReturnDto;

    }

    //4001-01 쉐어링 신청
    public Map<String, Object> dorReqSharingView(
        HttpServletRequest request
        , String contractNum
        , MyPageSearchDto searchVO
    ) {

        HashMap<String, Object> rtnMap = new HashMap<>();

        String rtnUrl = "/content/mySharingView.do";
        // 기존 화면 반환 URL은 REST 응답 구조에서 사용하지 않는다.
        String stepErrReturnUrl = "/main.do";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            rtnUrl = "/m/content/mySharingView.do";
            stepErrReturnUrl = "/m/main.do";
        }

        if(contractNum == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        MyShareDataReqDto myShareDataReqDto = new MyShareDataReqDto();

        NiceLogDto niceLogDto= new NiceLogDto();

        AuthSmsDto authSmsDto = null;
        UserSessionDto userSessionDto = getAuthenticatedUserSession();

        if(userSessionDto == null &&  authSmsDto  == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        String[] certKey= null;
        String[] certValue= null;
        Map<String,String> vldReslt= null;

        //비회원
        if(authSmsDto != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(contractNum);
            McpUserCntrMngDto cntrList =  selectCntrListNoLogin(userCntrMngDto);

            if(cntrList  == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }
            myShareDataReqDto.setBirthday(cntrList.getDobyyyymmdd());
            myShareDataReqDto.setCtn(StringMakerUtil.getPhoneNum(cntrList.getCntrMobileNo())); // 전화번호
            myShareDataReqDto.setNcn(cntrList.getSvcCntrNo());
            myShareDataReqDto.setOpmdSvcNo(cntrList.getUnSvcNo());
            myShareDataReqDto.setName(cntrList.getUserName());
            myShareDataReqDto.setContractNum(cntrList.getContractNum());

            // ============ STEP START ============
            // 이름, 생년월일, 계약번호
            certKey= new String[]{"urlType", "name", "birthDate", "contractNum"};
            certValue= new String[]{"chkMemberAuth", cntrList.getUserName(), EncryptUtil.ace256Enc(cntrList.getUnUserSSn()), cntrList.getContractNum()};

            vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonException(vldReslt.get("RESULT_DESC"), stepErrReturnUrl);
            }
            // ============ STEP END ============

            niceLogDto.setName(cntrList.getUserName());
            niceLogDto.setBirthDate(cntrList.getUnUserSSn());

            // 고객인증 sms인증 세션은 데이터 쉐어링 개통요청 시점에 null > 비로그인 데이터쉐어링 고객 인증정보 별도 보관
            McpUserCntrMngDto mcpUserCntrMngDto= new McpUserCntrMngDto();
            mcpUserCntrMngDto.setSubLinkName(authSmsDto.getAuthNum());   // 이름
            mcpUserCntrMngDto.setCntrMobileNo(authSmsDto.getPhoneNum()); // 전화번호

        }else {

            searchVO.setNcn(contractNum);

            List<McpUserCntrMngDto> cntrList = selectCntrList(userSessionDto.getUserId());

            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
            }

            String userSsn= null;
            for(McpUserCntrMngDto dto : cntrList) {
                //if(contractNum.equals(dto.getContractNum())) {
                if(contractNum.equals(dto.getSvcCntrNo())) {
                    myShareDataReqDto.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn())); // 전화번호
                    myShareDataReqDto.setNcn(searchVO.getNcn());
                    myShareDataReqDto.setOpmdSvcNo(dto.getUnSvcNo());
                    myShareDataReqDto.setBirthday(userSessionDto.getBirthday());
                    myShareDataReqDto.setName(userSessionDto.getName());
                    myShareDataReqDto.setContractNum(dto.getContractNum());
                    userSsn= dto.getUnUserSSn();
                }
            }

            // ============ STEP START ============
            // 계약번호
            certKey= new String[]{"urlType", "contractNum"};
            certValue= new String[]{"chkMemberAuth", myShareDataReqDto.getContractNum()};

            vldReslt= certService.vdlCertInfo("F", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonException(vldReslt.get("RESULT_DESC"), stepErrReturnUrl);
            }

            // 이름, 생년월일, 계약번호
            certKey= new String[]{"urlType", "name", "birthDate", "contractNum"};
            certValue= new String[]{"memberAuth", myShareDataReqDto.getName(), EncryptUtil.ace256Enc(userSsn), myShareDataReqDto.getContractNum()};
            vldReslt= certService.vdlCertInfo("C", certKey, certValue);
            // ============ STEP END ============

            niceLogDto.setName(myShareDataReqDto.getName());
            niceLogDto.setBirthDate(userSsn);
        }

        // ============ STEP START ============
        // nicePin연동
        Map<String, String> nicePinRtn= null;//nicePinService.getNicePinCi(niceLogDto);
        if (!"0000".equals(nicePinRtn.get("returnCode"))) {
            throw new McpCommonException(nicePinRtn.get("returnMsg"), stepErrReturnUrl);
        }
        // ============ STEP END ============

        rtnMap.put("contractNum", contractNum);
        rtnMap.put("myShareDataReqDto", myShareDataReqDto);
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    //4001-02 데이타쉐어링
    public HashMap<String, Object> doinsertOpenRequestAjax(
        HttpServletRequest request,
                                                           MyShareDataReqDto myShareDataReqDto
    ) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        McpUserCntrMngDto nonMemberSess = null;
        UserSessionDto userSessionDto = getAuthenticatedUserSession();

        if(userSessionDto == null &&  nonMemberSess  == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        if(nonMemberSess != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(myShareDataReqDto.getNcn());
            McpUserCntrMngDto cntrList =  selectCntrListNoLogin(userCntrMngDto);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }

            myShareDataReqDto.setCustId(cntrList.getCustId());
            myShareDataReqDto.setNcn(cntrList.getSvcCntrNo());
            myShareDataReqDto.setCtn(cntrList.getCntrMobileNo());
            myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());

        }else {

            List<McpUserCntrMngDto> cntrList = selectCntrList(userSessionDto.getUserId());
            if (cntrList != null && cntrList.size() > 0) {
                for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                    if (myShareDataReqDto.getNcn().equals(mcpUserCntrMngDto.getContractNum())) {
                        myShareDataReqDto.setCustId(mcpUserCntrMngDto.getCustId());
                        myShareDataReqDto.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
                        myShareDataReqDto.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
                        myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());
                        break;
                    }
                }
            }
        }

        MoscDataSharingResDto moscDataSharingChk  = new MoscDataSharingResDto();

        if(myShareDataReqDto.getSelfShareYn() != null && "Y".equals(myShareDataReqDto.getSelfShareYn())) { //셀프개통 사전체크
            myShareDataReqDto.setCrprCtn("");

            //x69 개통 사전체크
            moscDataSharingChk  = this.moscDataSharingChk(myShareDataReqDto);
        } else {
            rtnMap.put("RESULT_CODE", "E");
        }

        String shareDataYn = "";

        if(moscDataSharingChk.getSharingList() != null && moscDataSharingChk.getSharingList().size() > 0) {
            List<OutDataSharingDto> sharingList = moscDataSharingChk.getSharingList();

            for(OutDataSharingDto dto : sharingList) {
                if("Y".equals(dto.getRsltInd())) {
                    shareDataYn = "Y";
                    break;
                }
            }
        } else {
            rtnMap.put("RESULT_CODE", "E");
        }

        if("Y".equals(shareDataYn)) {
            //x70 쉐어링 가입
            this.moscDataSharingSave(myShareDataReqDto);
            rtnMap.put("RESULT_CODE", "S");
        } else {
            rtnMap.put("RESULT_CODE", "E");
        }

        return rtnMap;
    }


    /**
     * 데이터 쉐어링 완료 view
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param myShareDataReqDto
     * @return
     */

    public Map<String, Object> doReqSharingCompleteView(
        HttpServletRequest request
        , MyShareDataReqDto myShareDataReqDto
    ) {

        HashMap<String, Object> rtnMap = new HashMap<>();

        // 기존 화면 반환 URL은 REST 응답 구조에서 사용하지 않는다.
        String rtnUrl = "/content/mySharingView.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            rtnUrl = "/m/content/mySharingView.do";
        }

        String rtnOpmdSvcNo = myShareDataReqDto.getOpmdSvcNo();

        if(rtnOpmdSvcNo == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        MyShareDataResDto myShareDataResDto = new MyShareDataResDto();
        String svcNo = "";  //본인핸드폰번호
        String socNm = "";  //본인요금제
        String opmdSvcNoContractNum = ""; //개통이 완료된 서비스계약번호
        String opmdSvcSocNm = ""; //가입계약 요금제

        McpUserCntrMngDto nonMemberSess = null;
        UserSessionDto userSessionDto = getAuthenticatedUserSession();

        if(userSessionDto == null &&  nonMemberSess  == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        if(nonMemberSess != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(myShareDataReqDto.getNcn());
            McpUserCntrMngDto cntrList =  selectCntrListNoLogin(userCntrMngDto);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }
            svcNo = StringMakerUtil.getPhoneNum(cntrList.getCntrMobileNo());
            myShareDataReqDto.setCustId(cntrList.getCustId());
            myShareDataReqDto.setNcn(cntrList.getContractNum());
            myShareDataReqDto.setCtn(cntrList.getCntrMobileNo());
            myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());


        }else {

            List<McpUserCntrMngDto> cntrList = selectCntrList(userSessionDto.getUserId());

            if (cntrList != null && cntrList.size() > 0) {
                for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                    if (myShareDataReqDto.getNcn().equals(mcpUserCntrMngDto.getContractNum())) {
                        svcNo = StringMakerUtil.getPhoneNum(mcpUserCntrMngDto.getUnSvcNo());
                        myShareDataReqDto.setCustId(mcpUserCntrMngDto.getCustId());
                        myShareDataReqDto.setNcn(mcpUserCntrMngDto.getContractNum());
                        myShareDataReqDto.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
                        myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());
                        break;
                    }
                }
            }
        }

        McpUserCntrMngDto result = selectSocDesc(myShareDataReqDto.getNcn());

        if(result !=null) {
            socNm = result.getRateNm();
        }

        if(!StringUtil.isBlank(opmdSvcNoContractNum)) {
            McpUserCntrMngDto opmdSvcNoDto = selectSocDesc(opmdSvcNoContractNum); //가입하는 데이터쉐어링 요금제
            if(opmdSvcNoDto !=null) {
                opmdSvcSocNm =opmdSvcNoDto.getRateNm();
            }
        }

        myShareDataResDto.setSvcNo(svcNo);
        myShareDataResDto.setSocNm(socNm);
        myShareDataResDto.setOpmdSvcSocNm(opmdSvcSocNm);
        myShareDataResDto.setOpmdSvcNo(StringMakerUtil.getPhoneNum(myShareDataReqDto.getOpmdSvcNo()));
        rtnMap.put("myShareDataResDto", myShareDataResDto);
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    private UserSessionDto getAuthenticatedUserSession() {
        try {
            UserSessionDto userSessionDto = new UserSessionDto();
            userSessionDto.setUserId(AuthenticationUtils.getUser().getUserId());
            userSessionDto.setName(AuthenticationUtils.getUser().getUserName());
            userSessionDto.setUserDivision("01");
            return userSessionDto;
        } catch (RuntimeException e) {
            log.debug("[getAuthenticatedUserSession] authenticated user is unavailable: {}", e.getMessage());
            return null;
        }
    }

    private boolean checkUserType(
        MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList,
        UserSessionDto userSession
    ) {
        if (!StringUtil.equals(userSession.getUserDivision(), "01")) {
            return false;
        }

        if(cntrList == null) {
            return false;
        }

        if (cntrList.size() <= 0) {
            return false;
        }

        String userType = "";

        if (StringUtil.isEmpty(searchVO.getNcn())) {
            searchVO.setNcn(cntrList.get(0).getSvcCntrNo());
            searchVO.setCtn(cntrList.get(0).getCntrMobileNo());
            searchVO.setCustId(cntrList.get(0).getCustId());
            searchVO.setModelName(cntrList.get(0).getModelName());
            searchVO.setContractNum(cntrList.get(0).getContractNum());
            searchVO.setSubStatus(cntrList.get(0).getSubStatus());
            userType = cntrList.get(0).getUnUserSSn();
            if(userType != null && userType.length() == 13) {
                userType = userType.substring(6,7);
                if("5".equals(userType) || "6".equals(userType)) {
                    userType = "Y";
                    searchVO.setUserType(userType);
                }
            }
        }

        for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
            String ctn = mcpUserCntrMngDto.getCntrMobileNo();
            String ncn = mcpUserCntrMngDto.getSvcCntrNo();
            String custId = mcpUserCntrMngDto.getCustId();
            String modelName = mcpUserCntrMngDto.getModelName();
            String contractNum = mcpUserCntrMngDto.getContractNum();
            String subStatus = mcpUserCntrMngDto.getSubStatus();

            mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
            mcpUserCntrMngDto.setSvcCntrNo(ncn);
            mcpUserCntrMngDto.setCustId(custId);
            mcpUserCntrMngDto.setModelName(modelName);
            mcpUserCntrMngDto.setContractNum(contractNum);

            if (StringUtil.equals(searchVO.getNcn(), String.valueOf(mcpUserCntrMngDto.getSvcCntrNo()))) {
                searchVO.setNcn(ncn);
                searchVO.setCtn(ctn);
                searchVO.setCustId(custId);
                searchVO.setModelName(modelName);
                searchVO.setContractNum(contractNum);
                searchVO.setSubStatus(subStatus);
                userType = mcpUserCntrMngDto.getUnUserSSn();

                if(userType != null && userType.length() == 13) {
                    userType = userType.substring(6,7);
                    if("5".equals(userType) || "6".equals(userType) || "7".equals(userType) || "8".equals(userType) ) {
                        userType = "Y";
                        searchVO.setUserType(userType);
                    }
                }
            }
        }

        return true;
    }

}
