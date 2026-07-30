package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.crypto.support.util.KisaSeedUtils;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.dto.AppformReqDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.MspSmsTemplateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.dto.UsimMspRateDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.exception.McpErropPageException;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormOsstServerAdapter;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MPhoneNoVo;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSvcContIpinVO;
import com.ktmmobile.msf.domains.form.common.repository.MspApiDirectRepository;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.dto.UsimBasDto;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestReadMapper;
import com.ktmmobile.msf.domains.form.form.common.service.AuthInfoService;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestSaleinfoVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeMpPC0Response;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.InqrSvcNoInfoInDTO;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFPC0InDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFPC0InFrmpapDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormHC0InPrdcDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormNU1Request;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormNU1Response;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormNU2Request;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormOP0InDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormPC0InDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.ResvTlphNoInDTO;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeMpFieldMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestAgentWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestCstmrWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestDvcChgWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestMoveWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestNewChangeWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestReqWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestSaleinfoWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.ToMcpNewChangeReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.util.AppformUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.SvcAppformRepositoryImpl;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.port.in.FaceAuthWriter;
import com.ktmmobile.msf.domains.shared.form.common.generate.application.port.out.GenerateKeyRepository;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.CSTMR_TYPE_NA;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.CSTMR_TYPE_NM;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.EVENT_CODE_NUMBER_REG;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.EVENT_CODE_PC_RESULT;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.EVENT_CODE_REQ_OPEN_RESULT;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.EVENT_CODE_SEARCH_NUMBER;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.OPER_TYPE_CHANGE;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.OPER_TYPE_EXCHANGE;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.OPER_TYPE_NEW;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.OSST_SUCCESS;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.REQ_BUY_TYPE_PHONE;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.REQ_BUY_TYPE_USIM;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.WORK_CODE_RES;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.DB_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.NO_EXSIST_MCP_MODEL_INFO;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.STEP_CNT_EXCEPTION;

@Slf4j
@Service
public class MsfSvcDataSharingSvcImpl {

    @Autowired
    private MspApiDirectRepository mspApiDirectRepository;

    @Autowired
    private SvcAppformRepositoryImpl svcAppformRepositoryImpl;

    @Autowired
    private GenerateKeyRepository generateKeyRepository;

    @Autowired
    private MsfMcpOsstPrxService msfMcpOsstPrxService;

    @Autowired
    private MsfMplatFormOsstServerAdapter mplatFormOsstServerAdapter;

    @Autowired
    private AuthInfoService authInfoService;

    private static final String ORGN_ID = Constants.CONTPNT_SHOP_ID_MSHOP;

    @Value("${LOCAL_TEST:false}")
    private boolean localTest;

    @Autowired
    private McpRequestReadMapper mcpRequestReadMapper;

    @Autowired
    private McpRequestNewChangeWriteMapper mcpRequestNewChangeWriteMapper;

    @Autowired
    private McpRequestCstmrWriteMapper mcpRequestCstmrWriteMapper;

    @Autowired
    private McpRequestAgentWriteMapper mcpRequestAgentWriteMapper;

    @Autowired
    private McpRequestReqWriteMapper mcpRequestReqWriteMapper;

    @Autowired
    private McpRequestSaleinfoWriteMapper mcpRequestSaleinfoWriteMapper;

    @Autowired
    private McpRequestMoveWriteMapper mcpRequestMoveWriteMapper;

    @Autowired
    private McpRequestDvcChgWriteMapper mcpRequestDvcChgWriteMapper;

    @Autowired
    private ToMcpNewChangeReadMapper toMcpNewChangeReadMapper;

    @Autowired
    private FaceAuthWriter faceAuthWriter;


    /**
     * <pre>
     * 설명     : 데이터 쉐어링 신규 개통요청 (ASIS: AppformController)
     * 서비스변경 R15 데이터쉐어링 가입 전체 처리. 신청서 저장 → KNOTE FS1 → PC0/PC2 → ST1/Y39 → NU1/NU2 → OP0/OP2
     * @param serviceChangeCompleteReqDto
     * contractNum :계약번호  NCN
     * reqUsimSn : 유심번호
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    public FormResponse<Map<String, Object>> saveDataSharingSimple(AppformReqDto dataSharingReqDto) {
        long startedAt = System.currentTimeMillis();
        String contractNum = dataSharingReqDto == null ? "" : StringUtil.NVL(dataSharingReqDto.getContractNum(), "");
        String reqUsimSn = dataSharingReqDto == null ? "" : StringUtil.NVL(dataSharingReqDto.getReqUsimSn(), "");
        log.info("[saveDataSharingSimple] request: contractNum={}, reqUsimSn={}, mobileNo={}, onOffType={}",
            contractNum, reqUsimSn, dataSharingReqDto == null ? "" : dataSharingReqDto.getMobileNo(),
            dataSharingReqDto == null ? "" : dataSharingReqDto.getOnOffType());

        HashMap<String, Object> rtnMap = new HashMap<>();

        //TO_DO
        if (!StringUtils.hasText(dataSharingReqDto.getContractNum()) || !StringUtils.hasText(dataSharingReqDto.getReqUsimSn())) {
            log.warn("[saveDataSharingSimple] invalid request: contractNum={}, reqUsimSn={}",
                contractNum, reqUsimSn);
            return FormResponse.of("0001", F_BIND_EXCEPTION, null);
        }

        //로그인 정보 확인
        McpUserCntrMngDto out = new McpUserCntrMngDto();
        out.setSvcCntrNo(dataSharingReqDto.getContractNum());
        out.setContractNum(dataSharingReqDto.getContractNum());
        String cntrMobileNo = StringUtil.NVL(dataSharingReqDto.getMobileNo(), "");
        if (!StringUtils.hasText(cntrMobileNo)) {
            cntrMobileNo = StringUtil.NVL(dataSharingReqDto.getCstmrMobileFn(), "")
                + StringUtil.NVL(dataSharingReqDto.getCstmrMobileMn(), "")
                + StringUtil.NVL(dataSharingReqDto.getCstmrMobileRn(), "");
        }
        out.setCntrMobileNo(cntrMobileNo);

        McpUserCntrMngDto resultOut = selectCntrListNoLogin(out);
        if (resultOut != null && StringUtils.hasText(resultOut.getCntrMobileNo())) {
            cntrMobileNo = resultOut.getCntrMobileNo();
        }
        log.debug("[saveDataSharingSimple] contract resolved: SvcCntrNo:{} contractNum={}, cntrMobileNo={}, resultOut={}, custId={}",
            resultOut == null ? null : resultOut.getSvcCntrNo(),
            dataSharingReqDto.getContractNum(), cntrMobileNo, resultOut, resultOut == null ? null : resultOut.getCustId());

        //서비스 번호 ContractNum 검증
        String certContractNum = null;
        String customerId = null;

        if (resultOut != null && resultOut.getSvcCntrNo().equals(dataSharingReqDto.getContractNum())) {
            cntrMobileNo = resultOut.getCntrMobileNo();
            certContractNum = resultOut.getContractNum();
            customerId = resultOut.getCustId();
            log.debug("[saveDataSharingSimple]_cntrMobileNo:{} certContractNum:{} customerId:{} ", cntrMobileNo, certContractNum, customerId);
        }

        // API 요청으로 전달된 AppformReqDto 본인인증 결과를 신청서에 세팅한다.
        String onlineAuthType = StringUtil.NVL(dataSharingReqDto.getOnlineAuthType(), "");
        String onlineAuthInfo = StringUtil.NVL(dataSharingReqDto.getOnlineAuthInfo(), "");
        String selfCstmrCi = StringUtil.NVL(dataSharingReqDto.getSelfCstmrCi(), "");
        log.debug("[saveDataSharingSimple] auth result fields: contractNum={}, onlineAuthType={}, onlineAuthInfo={}, selfCstmrCi={}",
            dataSharingReqDto.getContractNum(), onlineAuthType, onlineAuthInfo, selfCstmrCi);


        //청구계정번호
        String billAcntNo = selectBanSel(dataSharingReqDto.getContractNum());
        log.debug("[saveDataSharingSimple] bill account resolved: contractNum={}, billAcntNo={}",
            dataSharingReqDto.getContractNum(), billAcntNo);


        //1. 신청서 테이블에 저장

        // JVM 로컬 HttpSession 대신 요청값과 DB 결과로 신청서를 처리한다.
        AppformReqDto rtnAppformReqDto;
            //1-2. 계약번호에 신청서 정보 설정
            AppformReqDto dataSharingReqResult = getCopyMcpRequest(dataSharingReqDto);
            if (dataSharingReqResult == null) {
                log.warn("[saveDataSharingSimple] copy request not found: contractNum={}", dataSharingReqDto.getContractNum());
                return FormResponse.of("0006", ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION, null);
            }

            // 1-2 인증정보 주민등록 번호 확인.
            // AS-IS:
            // String cstmrNativeRrn = dataSharingReqResult.getCstmrNativeRrnDesc();
            // if (6 < cstmrNativeRrn.length()) {
            //     cstmrNativeRrn = cstmrNativeRrn.substring(0, 6);
            // }
            // if (sessNiceRes.getBirthDate().indexOf(cstmrNativeRrn) < 0) {
            //     throw new McpCommonJsonException("0007", NICE_CERT_EXCEPTION);
            // }

            dataSharingReqResult.setOperType(OPER_TYPE_NEW); //operType: NAC3  <- 고정
            dataSharingReqResult.setContractNum(dataSharingReqDto.getContractNum());
            dataSharingReqResult.setOnlineAuthType(onlineAuthType);
            dataSharingReqResult.setOnlineAuthInfo(onlineAuthInfo);
            dataSharingReqResult.setSelfCstmrCi(selfCstmrCi);
            dataSharingReqResult.setServiceType("PO");
            dataSharingReqResult.setClauseInsrProdFlag("N");
            dataSharingReqResult.setClauseRwdFlag("N");
            if ("Y".equals(NmcpServiceUtils.isMobile())) {
                dataSharingReqResult.setOnOffType("7");
            } else {
                dataSharingReqResult.setOnOffType("5");
            }
            dataSharingReqResult.setReqWantNumber(cntrMobileNo.substring(cntrMobileNo.length() - 4, cntrMobileNo.length()));
            dataSharingReqResult.setCntpntShopId(ORGN_ID);
            dataSharingReqResult.setPstate("00");
            dataSharingReqResult.setCstmrType(CSTMR_TYPE_NA);
            dataSharingReqResult.setMaxDiscount3(0);
            dataSharingReqResult.setDcAmt(0);
            dataSharingReqResult.setAddDcAmt(0);
            dataSharingReqResult.setEnggMnthCnt(0);
            dataSharingReqResult.setModelInstallment(0);
            dataSharingReqResult.setModelPriceVat(0);
            dataSharingReqResult.setModelDiscount2(0);
            dataSharingReqResult.setModelDiscount3(0);
            dataSharingReqResult.setModelPrice(0);
            dataSharingReqResult.setSocCode("KISOPMDSB");
            dataSharingReqResult.setRealMdlInstamt(0);
            dataSharingReqResult.setSettlAmt(0);
            dataSharingReqResult.setPrdtSctnCd("LTE");
            dataSharingReqResult.setJoinPrice(0);
            dataSharingReqResult.setModelMonthly("0");  //MODEL_MONTHLY
            dataSharingReqResult.setReqUsimSn(dataSharingReqDto.getReqUsimSn());

            dataSharingReqResult.setSelfCertType(dataSharingReqDto.getSelfCertType());
            dataSharingReqResult.setSelfIssuExprDt(dataSharingReqDto.getSelfIssuExprDt());
            dataSharingReqResult.setSelfInqryAgrmYn("Y");

            //안면인증값 설정
            dataSharingReqResult.setFathTrgYn(dataSharingReqDto.getFathTrgYn());
            dataSharingReqResult.setClauseFathFlag(dataSharingReqDto.getClauseFathFlag());
            dataSharingReqResult.setFathTransacId(dataSharingReqDto.getFathTransacId());
            dataSharingReqResult.setFathCmpltNtfyDt(dataSharingReqDto.getFathCmpltNtfyDt());
            if ("02".equals(dataSharingReqDto.getSelfCertType()) || "04".equals(dataSharingReqDto.getSelfCertType())) {
                dataSharingReqResult.setSelfIssuNum(dataSharingReqDto.getSelfIssuNum());
            }

            //유심비 설정
            dataSharingReqResult.setUsimPayMthdCd("1");
            dataSharingReqResult.setUsimPrice(0);
            //가입비 설정
            //직영 대리점
            /** 가입비 납부방법
             * 1 면제
             * 2 일시납
             * 3 3개월분납
             */
            NmcpCdDtlDto nmcpCdDtlDto = getCodeNmDto(Constants.GROUP_CODE_USIM_PRICE_INFO, dataSharingReqResult.getSocCode());

            log.debug("[saveDataSharingSimple] nmcpCdDtlDto: contractNum={}", dataSharingReqDto.getContractNum());

            if (nmcpCdDtlDto != null && "Y".equals(nmcpCdDtlDto.getExpnsnStrVal1())) {
                //유심비 /  가입비 설정
                UsimBasDto usimBasDtoParm = new UsimBasDto();
                usimBasDtoParm.setOperType(dataSharingReqDto.getOperTypeSmall());
                usimBasDtoParm.setDataType(dataSharingReqDto.getPrdtSctnCd());
                List<UsimMspRateDto> usimPriceList = selectJoinUsimPriceNew(usimBasDtoParm);
                int intJoinPrice = 0;

                if (usimPriceList != null && usimPriceList.size() > 0) {
                    intJoinPrice = Integer.parseInt(usimPriceList.get(0).getJoinPrice());
                }

                dataSharingReqResult.setJoinPayMthdCd("3");
                dataSharingReqResult.setJoinPrice(intJoinPrice);
            } else {
                dataSharingReqResult.setJoinPayMthdCd("1");
                dataSharingReqResult.setJoinPrice(0);
            }

            log.debug("[saveDataSharingSimple] CHKsetAdditionKeyList: contractNum={}", dataSharingReqDto.getContractNum());

            /*
             * 12 스팸차단서비스 29 발신번호표시 30 통합사서함 31 정보제공사업자번호차단
             */
            String[] additionKeyList = {"12", "29", "30", "31"};
            dataSharingReqResult.setAdditionKeyList(additionKeyList);

            //접점코드로 대리점 코드 조회
            String agentCode = "";

            try {
                agentCode = getAgentCode(dataSharingReqResult.getCntpntShopId());
            } catch (RestClientException e) {
                log.error(e.getMessage());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            dataSharingReqResult.setAgentCode(agentCode);
            dataSharingReqResult.setManagerCode("M0001");
            dataSharingReqResult.setRid("-");
            dataSharingReqResult.setViewFlag("Y");
            dataSharingReqResult.setRequestStateCode("00");
            dataSharingReqResult.setRip(getClientIp());

            //다시 암호화 처리
            if (StringUtils.hasText(dataSharingReqResult.getReqAccountNumber())) {
                dataSharingReqResult.setReqAccountNumber(dataSharingReqResult.getReqAccountNumber());
            }
            if (StringUtils.hasText(dataSharingReqResult.getReqCardRrn())) {
                dataSharingReqResult.setReqCardRrn(dataSharingReqResult.getReqCardRrn());
            }
            if (StringUtils.hasText(dataSharingReqResult.getReqAccountRrn())) {
                dataSharingReqResult.setReqAccountRrn(dataSharingReqResult.getReqAccountRrn());
            }
            if (StringUtils.hasText(dataSharingReqResult.getReqCardNo())) {
                dataSharingReqResult.setReqCardNo(dataSharingReqResult.getReqCardNo());
            }

            /* 직영 평생할인 프로모션 ID 가져오기 */
            String prmtId = getChrgPrmtId(dataSharingReqResult);
            if (StringUtils.hasText(prmtId)) {
                dataSharingReqResult.setPrmtId(prmtId);
            }

            applyDataSharingSpclSlsNo(dataSharingReqResult);
            applyDataSharingShopContext(dataSharingReqResult);
            log.debug("[saveDataSharingSimple] 1-3.신청서 저장 contractNum={}", dataSharingReqDto.getContractNum());

            //1-3. 신청서 저장 호출
            try {

                // ============ STEP START ============
                // 1. nicePin 인증연동 확인
                if (0 >= getModuTypeStepCnt("nicePin", "")) {
                    throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
                }

                // 2. 데이터쉐어링 최종 정보 확인
                // 계약번호, 유심번호, CI
                // Excluded for smart-form scope: certService.getCertInfo/vdlCertInfo certification comparison.

                dataSharingReqResult.setCertMenuType("sharing");
                // ============ STEP END ============

                log.debug("[saveDataSharingSimple] 1-3.신청서 저장 saveAppformSTART contractNum={}", dataSharingReqDto.getContractNum());

                rtnAppformReqDto = saveAppform(dataSharingReqResult, null, null);
                log.info("[saveDataSharingSimple] appform saved: contractNum={}, requestKey={}, resNo={}, reqUsimSn={}",
                    dataSharingReqDto.getContractNum(), rtnAppformReqDto.getRequestKey(),
                    rtnAppformReqDto.getResNo(), rtnAppformReqDto.getReqUsimSn());

            } catch (McpCommonJsonException e) {
                // STEP 오류 처리
                rtnMap.put("RESULT_CODE", e.getRtnCode());
                rtnMap.put("RESULT_MSG", e.getErrorMsg());
                log.warn("[saveDataSharingSimple] appform step failed: contractNum={}, resultCode={}, resultMsg={}",
                    dataSharingReqDto.getContractNum(), e.getRtnCode(), e.getErrorMsg());
                return toDataSharingFormResponse(rtnMap);
            } catch (DataAccessException e) {
                log.error("[saveDataSharingSimple] appform DB save failed: contractNum={}",
                    dataSharingReqDto.getContractNum(), e);
                throw new McpCommonException(DB_EXCEPTION);
            } catch (Exception e) {
                log.error("[saveDataSharingSimple] appform save failed: contractNum={}",
                    dataSharingReqDto.getContractNum(), e);
                throw new McpCommonException(COMMON_EXCEPTION);
            }

        //2. 사전체크 및 고객생성 전 안면인증 예외처리 추가 20260612 (ITL_CFM_E0006 사전 방지)
        // FaceAuthWriter.requestFaceAuthIgnore >> callFaceAuthExceptionForDataSharing
        /*
        String mobileNo = rtnAppformReqDto.getCstmrMobileFn() + rtnAppformReqDto.getCstmrMobileMn() + rtnAppformReqDto.getCstmrMobileRn();
        FaceAuthSendRequest faceAuthSendRequest = new FaceAuthSendRequest(
            FaceAuthFormType.NEWCHANGE,
            FaceAuthJoinType.NEW,
            FaceAuthIdentityForm.KNOTE_IDCARD,
            FaceAuthIdentityType.REGID,
            FaceAuthCustomerType.NATIONAL,
            FaceAuthVisitType.VMY,
            rtnAppformReqDto.getResNo(),
            rtnAppformReqDto.getAgentCode(),
            null,
            null,
            mobileNo,
            null,
            null,
            null
        );
        FaceAuthIgnoreResponse ignoreResponse = faceAuthWriter.requestFaceAuthIgnore(faceAuthSendRequest);
        String faceAuthResultCode = ignoreResponse == null ? "" : StringUtil.NVL(ignoreResponse.resultCode(), "");
        String faceAuthResultMessage = ignoreResponse == null ? "response message is null." : StringUtil.NVL(ignoreResponse.resultMessage(), "");
        if (!"0000".equals(faceAuthResultCode)) {
            rtnMap.put("RESULT_CODE", "1006");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_FATH_SKIP);
            rtnMap.put("ERROR_MSG", "FaceAuth ignore failed."
                + (StringUtils.hasText(faceAuthResultCode) ? " (" + faceAuthResultCode + ")" : "")
                + (StringUtils.hasText(faceAuthResultMessage) ? " " + faceAuthResultMessage : ""));
            log.warn("[saveDataSharingSimple] FaceAuth ignore failed: contractNum={}, resNo={}, resultCode={}, resultMessage={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), faceAuthResultCode, faceAuthResultMessage);
            return toDataSharingFormResponse(rtnMap);
        }

        String fathTransacId = ignoreResponse.transactionId();
        if (!StringUtils.hasText(fathTransacId)) {
            rtnMap.put("RESULT_CODE", "1007");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_FATH_SKIP);
            rtnMap.put("ERROR_MSG", "FaceAuth ignore transactionId is empty.");
            log.warn("[saveDataSharingSimple] FaceAuth ignore transactionId empty: contractNum={}, resNo={}, resultCode={}, resultMessage={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), faceAuthResultCode, faceAuthResultMessage);
            return toDataSharingFormResponse(rtnMap);
        }

        rtnAppformReqDto.setFathTransacId(fathTransacId);
        log.info("[saveDataSharingSimple] 안면인증 예외처리 완료: contractNum={}, resNo={}, fathTransacId={}",
            dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), fathTransacId);
        */

        String fathTransacId;
        try {
            fathTransacId = callFaceAuthExceptionForDataSharing(rtnAppformReqDto);
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "1006");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_FATH_SKIP);
            rtnMap.put("ERROR_MSG", e.getMessage());
            log.warn("[saveDataSharingSimple] 안면인증 예외처리 실패: contractNum={}, resNo={}, msg={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e.getMessage());
            return toDataSharingFormResponse(rtnMap);
        }

        rtnAppformReqDto.setFathTransacId(fathTransacId);
        log.info("[saveDataSharingSimple] 안면인증 예외처리 완료: contractNum={}, resNo={}, fathTransacId={}",
            dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), fathTransacId);

        //2. 사전체크 및 고객생성(PC0)
        MSimpleOsstXmlVO simpleOsstXmlVO = null;
        try {
            Thread.sleep(3000);
            log.info("[saveDataSharingSimple] PC0 사전체크 및 고객생성 처리: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo());

            simpleOsstXmlVO = sendOsstPreCheckXmlService(rtnAppformReqDto);
            log.debug("[saveDataSharingSimple] PC0 response: contractNum={}, resNo={}, success={}, resultCode={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(),
                simpleOsstXmlVO.isSuccess(), simpleOsstXmlVO.getResultCode());
            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
                rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
            } else {
                rtnMap.put("RESULT_CODE", "1001");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                log.warn("[saveDataSharingSimple] PC0 failed: contractNum={}, resNo={}, resultCode={}",
                    dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), simpleOsstXmlVO.getResultCode());
                return toDataSharingFormResponse(rtnMap);
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "1002");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", "response massage is null.");
            log.error("[saveDataSharingSimple] PC0 mplatform error: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e);
            return toDataSharingFormResponse(rtnMap);
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "1003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", "SocketTimeout");
            log.error("[saveDataSharingSimple] PC0 timeout: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e);
            return toDataSharingFormResponse(rtnMap);
        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "1004");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", e.getMessage());
            log.error("[saveDataSharingSimple] PC0 self service error: contractNum={}, resNo={}, msg={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e.getMessage(), e);
            return toDataSharingFormResponse(rtnMap);
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "1005");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", "Exception");
            log.error("[saveDataSharingSimple] PC0 unexpected error: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e);
            return toDataSharingFormResponse(rtnMap);
        }

//        if (localTest) {
//            try {
//                Thread.sleep(15 * 1000);
//                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//                rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
//                rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
//                return toDataSharingFormResponse(rtnMap);
//            } catch (InterruptedException e) {
//                return FormResponse.of("0007", COMMON_EXCEPTION, null);
//            }
//        }

        //3. 사전체크 확인 (PC2)
        //    - MCP_REQUEST_OSST CALL BACK 확인
        McpRequestOsstDto mcpRequestOsstDtoRtn = null;
        try {
            log.info("[saveDataSharingSimple] PC2 사전체크 확인 처리: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo());
            McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
            mcpRequestOsstDto.setMvnoOrdNo(rtnAppformReqDto.getResNo());
            mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_PC_RESULT);

            //5초 24번 120초 ...
            for (int i = 0; i < 50; i++) {
                Thread.sleep(5000);
                mcpRequestOsstDtoRtn = getRequestOsst(mcpRequestOsstDto);
                if (mcpRequestOsstDtoRtn != null) {
                    String rsltMsg = mcpRequestOsstDtoRtn.getRsltMsg();
                    String rsltCd = mcpRequestOsstDtoRtn.getRsltCd();

                    rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PC_RESULT);
                    rtnMap.put("RESULT_OBJ", mcpRequestOsstDtoRtn);
                    rtnMap.put("RESULT_MSG", rsltMsg);
                    rtnMap.put("RESULT_CODE", rsltCd);

                    if (OSST_SUCCESS.equals(rsltCd)) {
                        //saveOsstDto(mcpRequestOsstDtoRtn);  //사전 체크 정보 session 저장
                        log.debug("[saveDataSharingSimple] PC2 success: contractNum={}, resNo={}, osstOrdNo={}, resultCode={}",
                            dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(),
                            mcpRequestOsstDtoRtn.getOsstOrdNo(), rsltCd);
                        break;
                    } else {
                        rtnMap.put("RESULT_CODE", "2001");
                        log.warn("[saveDataSharingSimple] PC2 failed: contractNum={}, resNo={}, resultCode={}, resultMsg={}",
                            dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), rsltCd, rsltMsg);
                        return toDataSharingFormResponse(rtnMap);
                    }
                }
            }

            if (mcpRequestOsstDtoRtn == null) {
                rtnMap.put("RESULT_CODE", "2002");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PC_RESULT);
                rtnMap.put("ERROR_MSG", "DB 결과값 없음");
                log.warn("[saveDataSharingSimple] PC2 result empty: contractNum={}, resNo={}",
                    dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo());
                return toDataSharingFormResponse(rtnMap);
            }
        } catch (DataAccessException e) {
            rtnMap.put("RESULT_CODE", "2003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PC_RESULT);
            rtnMap.put("ERROR_MSG", "DataAccessException");
            log.error("[saveDataSharingSimple] PC2 DB lookup failed: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e);
            return toDataSharingFormResponse(rtnMap);
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "2003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PC_RESULT);
            rtnMap.put("ERROR_MSG", "Exception");
            log.error("[saveDataSharingSimple] PC2 unexpected error: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e);
            return toDataSharingFormResponse(rtnMap);
        }

        // ======= START : 사전체크 완료상태 조회 (사전체크 작업 완료 후 MP측 DB작업 반영 상태 조회) =======

        // ** issue : 사전체크 완료 소켓전송 시간과 사전체크 작업 완료 후 MP측 DB반영 시간 텀 존재
        // ** to-be : ST1 연동으로 PC2 완료상태 조회 > PC2 완료 확인 후 Y39 연동

        Map<String, String> prcSchMap = chkRealPc2Result(rtnAppformReqDto.getResNo(), dataSharingReqDto.getContractNum());
        log.debug("[saveDataSharingSimple] ST1 사전체크 완료상태 조회 결과: contractNum={}, resNo={}, resultCode={}, errorMsg={}",
            dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(),
            prcSchMap.get("RESULT_CODE"), prcSchMap.get("ERROR_MSG"));
        if (!AJAX_SUCCESS.equals(prcSchMap.get("RESULT_CODE"))) {
            // 연동오류 또는 사전체크 완료 상태 확인 불가
            rtnMap.put("RESULT_CODE", prcSchMap.get("RESULT_CODE"));
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_SCH);
            rtnMap.put("ERROR_MSG", StringUtil.NVL(prcSchMap.get("ERROR_MSG"), COMMON_EXCEPTION));
            log.warn("[saveDataSharingSimple] ST1 failed: contractNum={}, resNo={}, resultCode={}, errorMsg={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(),
                prcSchMap.get("RESULT_CODE"), prcSchMap.get("ERROR_MSG"));
            return toDataSharingFormResponse(rtnMap);
        }
        // ======= END : 사전체크 완료상태 조회 (사전체크 작업 완료 후 MP측 DB작업 반영 상태 조회) =======

        // ======= START : Y39 아이핀 Ci 조회(마이알뜰폰) =======
        MpSvcContIpinVO mpSvcContIpinVO = new MpSvcContIpinVO();

        try {
            mpSvcContIpinVO = MoscSvcContService(mcpRequestOsstDtoRtn.getOsstOrdNo());
        } catch (SocketTimeoutException e) {
            log.info("[saveDataSharingSimple] Y39 SocketTimeoutException: contractNum={}, osstOrdNo={}, msg={}",
                dataSharingReqDto.getContractNum(), mcpRequestOsstDtoRtn.getOsstOrdNo(), e.getMessage());
        } catch (Exception e) {
            log.info("[saveDataSharingSimple] Y39 Exception: contractNum={}, osstOrdNo={}, msg={}",
                dataSharingReqDto.getContractNum(), mcpRequestOsstDtoRtn.getOsstOrdNo(), e.getMessage());
        }

        if (mpSvcContIpinVO == null || !mpSvcContIpinVO.isSuccess()) {
            log.warn("[saveDataSharingSimple] Y39 failed: contractNum={}, osstOrdNo={}",
                dataSharingReqDto.getContractNum(), mcpRequestOsstDtoRtn.getOsstOrdNo());
            return FormResponse.of("7001", COMMON_EXCEPTION, null);
        }
        log.debug("[saveDataSharingSimple] Y39 success: contractNum={}, osstOrdNo={}, ipinCi={}",
            dataSharingReqDto.getContractNum(), mcpRequestOsstDtoRtn.getOsstOrdNo(), mpSvcContIpinVO.getIpinCi());

        //  =============== STEP START ===============
        // step 종료여부, CI
        // 20260612 안면인증관련 체크
        //String[] certKey = {"urlType", "stepEndYn", "connInfo"};
        //String[] certValue = {"chkPreOpenCi", "Y", mpSvcContIpinVO.getIpinCi()};

        // 운영환경 이외에서는 CI값이 제대로 리턴되지 않음. 따라서, LOCAL/DEV/STG에서는 본인인증 세션으로 대체
        // if (!EnvironmentUtils.isProduction()) {
        //     if(sessNiceRes != null && !StringUtil.isEmpty(sessNiceRes.getConnInfo())){
        //         certValue[2]= sessNiceRes.getConnInfo();
        //     }
        // }
        //
        // Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        // if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
        //     throw new McpCommonJsonException("STEP03", vldReslt.get("RESULT_DESC"));
        // }
        //  =============== STEP START ===============

        // ======= END : Y39 아이핀 Ci 조회(마이알뜰폰) =======

        //4. 번호조회(NU1)
        /*
          tlphNo:$chkRadioObj.val()
         ,tlphNoStatCd:$chkRadioObj.attr("tlphNoStatCd")
         ,tlphNoOwnCmpnCd:$chkRadioObj.attr("tlphNoOwnCmncCmpnCd")
         ,encdTlphNo:$chkRadioObj.attr("encdTlphNo")
        */
        String tlphNo = "";
        String tlphNoStatCd = "";
        String tlphNoOwnCmpnCd = "";
        String encdTlphNo = "";

        McpRequestDto mcpRequestDto = new McpRequestDto();
        mcpRequestDto.setRequestKey(rtnAppformReqDto.getRequestKey());
        mcpRequestDto.setResNo(rtnAppformReqDto.getResNo());
        mcpRequestDto.setReqWantNumber(cntrMobileNo.substring(cntrMobileNo.length() - 4, cntrMobileNo.length()));  //끝번호
        log.info("[saveDataSharingSimple] NU1 번호조회 처리: contractNum={}, resNo={}, reqWantNumber={}",
            dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), mcpRequestDto.getReqWantNumber());
        String nu1CustomerId = mcpRequestOsstDtoRtn.getCustId();

        List<MPhoneNoVo> phoneNmeverList = fnSearchNumber(
            mcpRequestDto, mcpRequestOsstDtoRtn.getOsstOrdNo(), rtnAppformReqDto.getAgentCode(),
            certContractNum, nu1CustomerId);

        if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
            tlphNo = phoneNmeverList.get(0).getTlphNo();
            tlphNoStatCd = phoneNmeverList.get(0).getTlphNoStatCd();
            tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmncCmpnCd();
            encdTlphNo = phoneNmeverList.get(0).getEncdTlphNo();
            log.debug("[saveDataSharingSimple] NU1 found by last digits: contractNum={}, resNo={}, tlphNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), tlphNo);
        } else {
            // 끝번호 조회 안되면
            // 중간 번호로 다시 시도
            mcpRequestDto.setReqWantNumber(cntrMobileNo.substring(cntrMobileNo.length() - 8, cntrMobileNo.length() - 4));  //중간번호
            log.debug("[saveDataSharingSimple] NU1 retry by middle digits: contractNum={}, resNo={}, reqWantNumber={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), mcpRequestDto.getReqWantNumber());
            phoneNmeverList = fnSearchNumber(
                mcpRequestDto, mcpRequestOsstDtoRtn.getOsstOrdNo(), rtnAppformReqDto.getAgentCode(),
                certContractNum, nu1CustomerId);
            if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
                tlphNo = phoneNmeverList.get(0).getTlphNo();
                tlphNoStatCd = phoneNmeverList.get(0).getTlphNoStatCd();
                tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmncCmpnCd();
                encdTlphNo = phoneNmeverList.get(0).getEncdTlphNo();
                log.debug("[saveDataSharingSimple] NU1 found by middle digits: contractNum={}, resNo={}, tlphNo={}",
                    dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), tlphNo);
            } else {
                // 중간 번호 조회 안되면
                // Random

                Random random;
                String a = "";
                String b = "";
                String c = "";
                String d = "";
                try {
                    random = SecureRandom.getInstance("SHA1PRNG");
                    a = String.valueOf(random.nextInt(10));
                    b = String.valueOf(random.nextInt(10));
                    c = String.valueOf(random.nextInt(10));
                    d = String.valueOf(random.nextInt(10));
                } catch (NoSuchAlgorithmException e1) {
                    throw new McpErropPageException(COMMON_EXCEPTION);
                }

                mcpRequestDto.setReqWantNumber(a + b + c + d);  //Random
                log.debug("[saveDataSharingSimple] NU1 retry by random digits: contractNum={}, resNo={}, reqWantNumber={}",
                    dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), mcpRequestDto.getReqWantNumber());
                phoneNmeverList = fnSearchNumber(
                    mcpRequestDto, mcpRequestOsstDtoRtn.getOsstOrdNo(), rtnAppformReqDto.getAgentCode(),
                    certContractNum, nu1CustomerId);
                if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
                    tlphNo = phoneNmeverList.get(0).getTlphNo();
                    tlphNoStatCd = phoneNmeverList.get(0).getTlphNoStatCd();
                    tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmncCmpnCd();
                    encdTlphNo = phoneNmeverList.get(0).getEncdTlphNo();
                    log.debug("[saveDataSharingSimple] NU1 found by random digits: contractNum={}, resNo={}, tlphNo={}",
                        dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), tlphNo);
                } else {
                    rtnMap.put("RESULT_CODE", "3001");
                    rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_SEARCH_NUMBER);
                    rtnMap.put("ERROR_MSG", "조회 전화번호 없음");
                    log.warn("[saveDataSharingSimple] NU1 failed: contractNum={}, resNo={}",
                        dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo());
                    return toDataSharingFormResponse(rtnMap);
                }
            }
        }

        //5. 번호예약/취소(NU2)
        McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
        mcpRequestOsstDto.setTlphNo(decryptTlphNo(tlphNo)); // MCP_REQUEST_OSST.TLPH_NO 컬럼(max 11자) — 복호화된 평문 번호
        mcpRequestOsstDto.setTlphNoStatCd(tlphNoStatCd);
        mcpRequestOsstDto.setTlphNoOwnCmpnCd(tlphNoOwnCmpnCd);
        mcpRequestOsstDto.setEncdTlphNo(encdTlphNo);
        mcpRequestOsstDto.setMvnoOrdNo(rtnAppformReqDto.getResNo());
        // mcpRequestOsstDto.setOsstOrdNo("22222");
        mcpRequestOsstDto.setOsstOrdNo(mcpRequestOsstDtoRtn.getOsstOrdNo());
        mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_NUMBER_REG);
        mcpRequestOsstDto.setAsgnAgncId(rtnAppformReqDto.getAgentCode());
        mcpRequestOsstDto.setOpenSvcIndCd("03"); //03 고정 (3G)
        mcpRequestOsstDto.setIfType(WORK_CODE_RES);
        mcpRequestOsstDto.setRsltCd(OSST_SUCCESS);
        log.info("[saveDataSharingSimple] NU2 번호예약 처리: contractNum={}, resNo={}, tlphNo={}, osstOrdNo={}",
            dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), tlphNo, mcpRequestOsstDto.getOsstOrdNo());

        if (insertMcpRequestOsst(mcpRequestOsstDto)) {
            MSimpleOsstXmlVO simpleOsstXmlReg = null;
            ////번호예약(NU2)
            try {
                Thread.sleep(3000);

                // osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
                Map<String, String> osstParam = new HashMap<>();
                osstParam.put("resNo", rtnAppformReqDto.getResNo());
                osstParam.put("gubun", WORK_CODE_RES);
                osstParam.put("prntsContractNo", certContractNum);
                osstParam.put("custNo", mcpRequestOsstDtoRtn.getCustId());  // PC2 콜백에서 받은 custId 사용 (ITL_SST_E0018 방지)

                simpleOsstXmlReg = reserveDataSharingNumber(mcpRequestOsstDto, osstParam);

                if (simpleOsstXmlReg.isSuccess()) {
                    log.debug("[saveDataSharingSimple] NU2 success: contractNum={}, resNo={}, tlphNo={}, resultCode={}",
                        dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), tlphNo,
                        simpleOsstXmlReg == null ? null : simpleOsstXmlReg.getResultCode());
                } else {
                    rtnMap.put("RESULT_CODE", "4001");
                    rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
                    rtnMap.put("RESULT_XML", simpleOsstXmlReg.getResponseXml());
                    rtnMap.put("ERROR_MSG", simpleOsstXmlReg.getResultCode());
                    log.warn("[saveDataSharingSimple] NU2 failed: contractNum={}, resNo={}, tlphNo={}, resultCode={}",
                        dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), tlphNo,
                        simpleOsstXmlReg == null ? null : simpleOsstXmlReg.getResultCode());
                    return toDataSharingFormResponse(rtnMap);
                }
            } catch (McpMplatFormException e) {
                rtnMap.put("RESULT_CODE", "4002");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
                rtnMap.put("ERROR_MSG", "response massage is null.");
                log.error("[saveDataSharingSimple] NU2 mplatform error: contractNum={}, resNo={}, tlphNo={}",
                    dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), tlphNo, e);
                return toDataSharingFormResponse(rtnMap);
            } catch (SelfServiceException e) {
                rtnMap.put("RESULT_CODE", "4004");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
                rtnMap.put("ERROR_MSG", e.getMessage());
                log.error("[saveDataSharingSimple] NU2 self service error: contractNum={}, resNo={}, tlphNo={}, msg={}",
                    dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), tlphNo, e.getMessage(), e);
                return toDataSharingFormResponse(rtnMap);

            } catch (InterruptedException e) {
                log.error("[saveDataSharingSimple] NU2 interrupted: contractNum={}, resNo={}, tlphNo={}",
                    dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), tlphNo, e);
            }

        } else {
            rtnMap.put("RESULT_CODE", "4005");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
            rtnMap.put("ERROR_MSG", DB_EXCEPTION);
            log.warn("[saveDataSharingSimple] NU2 history insert failed: contractNum={}, resNo={}, tlphNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), tlphNo);
            return toDataSharingFormResponse(rtnMap);
        }


        //6. 개통및수납(OP0)
        MSimpleOsstXmlVO simpleOsstXmlVO3 = new MSimpleOsstXmlVO();
        String svcMsg = "";
        try {
            Thread.sleep(3000);
            log.info("[saveDataSharingSimple] OP0 개통및수납 처리: contractNum={}, resNo={}, tlphNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), tlphNo);

            // osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
            Map<String, String> osstParam = new HashMap<>();
            osstParam.put("resNo", rtnAppformReqDto.getResNo());
            osstParam.put("billAcntNo", billAcntNo);
            osstParam.put("prntsContractNo", certContractNum);
            osstParam.put("custNo", mcpRequestOsstDtoRtn.getCustId());  // PC2 콜백 custId 사용 (appAgncCd 일치 보장)
            osstParam.put("osstOrdNo", mcpRequestOsstDtoRtn.getOsstOrdNo());
            osstParam.put("tlphNo", decryptTlphNo(tlphNo)); // EncryptAdapter가 평문을 KISA SEED 암호화

            copyMsfRequestToMcp(rtnAppformReqDto.getRequestKey());

            // TO-BE OP0: PC0/NU1/NU2와 동일한 xmlOsstServiceCall 경로를 사용한다.
            // 데이터쉐어링 OP0 전문은 저장 완료된 MSF 신청서 값으로 생성한다.
            AppformReqDto op0Appform = getDataSharingOp0Appform(rtnAppformReqDto.getRequestKey());
            simpleOsstXmlVO = sendOsstOpenXmlService(op0Appform, osstParam);

            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                log.debug("[saveDataSharingSimple] OP0 success: contractNum={}, resNo={}, resultCode={}",
                    dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(),
                    simpleOsstXmlVO.getResultCode());

                /* 데이터 쉐어링 OP0 성공 후 평생할인 기적용 ISNERT */
                if (dataSharingReqDto.getRequestKey() == 0) {
                    dataSharingReqDto.setRequestKey(rtnAppformReqDto.getRequestKey());
                }
                dataSharingReqDto.setResNo(rtnAppformReqDto.getResNo());
                dataSharingReqDto.setSocCode(rtnAppformReqDto.getSocCode());
                dataSharingReqDto.setOnOffType(rtnAppformReqDto.getOnOffType());
                dataSharingReqDto.setEnggMnthCnt(rtnAppformReqDto.getEnggMnthCnt());

                insertDisPrmtApd(dataSharingReqDto, "NAC");
            } else {
                svcMsg = simpleOsstXmlVO.getSvcMsg();
                rtnMap.put("RESULT_CODE", "5001");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
                rtnMap.put("RESULT_XML", simpleOsstXmlVO3.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO3.getResultCode());
                rtnMap.put("REQUEST_MSG", svcMsg);
                rtnMap.put("ERROR_NE_MSG", svcMsg);
                log.warn("[saveDataSharingSimple] OP0 failed: contractNum={}, resNo={}, resultCode={}, svcMsg={}",
                    dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(),
                    simpleOsstXmlVO.getResultCode(), svcMsg);
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "5002");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
            rtnMap.put("ERROR_MSG", "response massage is null.");
            rtnMap.put("ERROR_NE_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다. ");
            log.error("[saveDataSharingSimple] OP0 mplatform error: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e);

        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "5003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
            rtnMap.put("ERROR_NE_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다. ");
            log.error("[saveDataSharingSimple] OP0 timeout: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e);
            return toDataSharingFormResponse(rtnMap);

        } catch (SelfServiceException e) {
            svcMsg = simpleOsstXmlVO.getSvcMsg();
            rtnMap.put("RESULT_CODE", "5004");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
            rtnMap.put("REQUEST_MSG", svcMsg);
            rtnMap.put("ERROR_MSG", e.getMessage());
            rtnMap.put("ERROR_NE_MSG", e.getMessageNe());
            log.error("[saveDataSharingSimple] OP0 self service error: contractNum={}, resNo={}, msg={}, msgNe={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e.getMessage(), e.getMessageNe(), e);
            return toDataSharingFormResponse(rtnMap);
        } catch (InterruptedException e) {
            log.error("[saveDataSharingSimple] OP0 interrupted: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e);
        }

        //7. 개통 확인(OP2)
        //- MCP_REQUEST_OSST CALL BACK 확인
        McpRequestOsstDto mcpRequestOsstRtn = null;
        try {
            log.info("[saveDataSharingSimple] OP2 개통 확인 처리: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo());
            mcpRequestOsstDto.setMvnoOrdNo(rtnAppformReqDto.getResNo());
            mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_REQ_OPEN_RESULT);

            //5초 24번 120초 ...
            for (int i = 0; i < 50; i++) {
                Thread.sleep(5000);
                mcpRequestOsstRtn = getRequestOsst(mcpRequestOsstDto);
                if (mcpRequestOsstRtn != null) {
                    String rsltMsg = mcpRequestOsstRtn.getRsltMsg();
                    String rsltCd = mcpRequestOsstRtn.getRsltCd();

                    rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN_RESULT);
                    rtnMap.put("RESULT_OBJ", mcpRequestOsstRtn);
                    rtnMap.put("RESULT_MSG", rsltMsg);
                    rtnMap.put("RESULT_CODE", rsltCd);

                    if (isSuccessOP2(rsltCd)) {
                        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                        log.debug("[saveDataSharingSimple] OP2 success: contractNum={}, resNo={}, resultCode={}, resultMsg={}",
                            dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), rsltCd, rsltMsg);
                        break;
                    } else {
                        rtnMap.put("RESULT_CODE", "6001");
                        log.warn("[saveDataSharingSimple] OP2 failed: contractNum={}, resNo={}, resultCode={}, resultMsg={}",
                            dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), rsltCd, rsltMsg);
                        return toDataSharingFormResponse(rtnMap);
                    }
                }
            }

            if (mcpRequestOsstRtn == null) {
                rtnMap.put("RESULT_CODE", "6002");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN_RESULT);
                rtnMap.put("ERROR_MSG", "DB 결과값 없음");
                log.warn("[saveDataSharingSimple] OP2 result empty: contractNum={}, resNo={}",
                    dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo());
                return toDataSharingFormResponse(rtnMap);
            }
        } catch (IllegalArgumentException e) {
            rtnMap.put("RESULT_CODE", "6003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN_RESULT);
            rtnMap.put("ERROR_MSG", "IllegalArgumentException");
            log.error("[saveDataSharingSimple] OP2 invalid argument: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e);
            return toDataSharingFormResponse(rtnMap);
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "6003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN_RESULT);
            rtnMap.put("ERROR_MSG", "Exception");
            log.error("[saveDataSharingSimple] OP2 unexpected error: contractNum={}, resNo={}",
                dataSharingReqDto.getContractNum(), rtnAppformReqDto.getResNo(), e);
            return toDataSharingFormResponse(rtnMap);
        }

        rtnMap.put("tlphNo", tlphNo);
        rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
        rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());


        log.info("[saveDataSharingSimple] complete: contractNum={}, requestKey={}, resNo={}, tlphNo={}, elapsedMs={}",
            dataSharingReqDto.getContractNum(), rtnAppformReqDto.getRequestKey(),
            rtnAppformReqDto.getResNo(), tlphNo, System.currentTimeMillis() - startedAt);
        return toDataSharingFormResponse(rtnMap);


    }




    //NU1 번호조회. 희망번호 패턴으로 번호 목록 조회
    private List<MPhoneNoVo> fnSearchNumber(
        McpRequestDto mcpRequestDto, String osstOrdNo, String agentCode,
        String prntsContractNo, String customerId
    ) {
        log.debug("[fnSearchNumber] request: requestKey={}, resNo={}, reqWantNumber={}, osstOrdNo={}, agentCode={}, prntsContractNo={}, customerId={}",
            mcpRequestDto == null ? null : mcpRequestDto.getRequestKey(),
            mcpRequestDto == null ? null : mcpRequestDto.getResNo(),
            mcpRequestDto == null ? null : mcpRequestDto.getReqWantNumber(),
            osstOrdNo, agentCode, prntsContractNo, customerId);
        if (mcpRequestDto == null
            || mcpRequestDto.getRequestKey() < 1
            || !StringUtils.hasText(mcpRequestDto.getResNo())
            || !StringUtils.hasText(mcpRequestDto.getReqWantNumber())
            || !StringUtils.hasText(osstOrdNo)
            || !StringUtils.hasText(agentCode)
            || !StringUtils.hasText(customerId)) {
            log.warn("[fnSearchNumber] invalid request: requestKey={}, resNo={}, reqWantNumber={}, osstOrdNo={}, agentCode={}, customerId={}",
                mcpRequestDto == null ? null : mcpRequestDto.getRequestKey(),
                mcpRequestDto == null ? null : mcpRequestDto.getResNo(),
                mcpRequestDto == null ? null : mcpRequestDto.getReqWantNumber(),
                osstOrdNo, agentCode, customerId);
            throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION);
        }

        if (!svcAppformRepositoryImpl.updateMsfRequestWantNumber(mcpRequestDto)) {
            log.warn("[fnSearchNumber] updateMsfRequestWantNumber failed: requestKey={}, resNo={}, reqWantNumber={}",
                mcpRequestDto.getRequestKey(), mcpRequestDto.getResNo(), mcpRequestDto.getReqWantNumber());
            throw new McpCommonException(DB_EXCEPTION);
        }

        List<MPhoneNoVo> rtnList = null;
        try {
            Thread.sleep(3000);

            // osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
            Map<String, String> nu1HistoryParams = new HashMap<>();
            nu1HistoryParams.put("mvnoOrdNo", mcpRequestDto.getResNo());
            nu1HistoryParams.put("appEventCd", EVENT_CODE_SEARCH_NUMBER);
            if (StringUtils.hasText(prntsContractNo)) {
                nu1HistoryParams.put("prntsContractNo", prntsContractNo);
            }
            if (StringUtils.hasText(customerId)) {
                nu1HistoryParams.put("custNo", customerId);
            }

            MplatFormNU1Request request = createDataSharingNU1Request(
                mcpRequestDto.getReqWantNumber(), osstOrdNo, agentCode, customerId);
            MspPrxSoapResponse response = msfMcpOsstPrxService.callXmlOsstService(
                List.of(request), EVENT_CODE_SEARCH_NUMBER, nu1HistoryParams);

            if (response != null && "N".equals(response.responseType())) {
                rtnList = toPhoneNoList(response);
                log.debug("[fnSearchNumber] response: resNo={}, reqWantNumber={}, success=true, count={}",
                    mcpRequestDto.getResNo(), mcpRequestDto.getReqWantNumber(), rtnList.size());
            } else {
                log.debug("[fnSearchNumber] response failed: resNo={}, reqWantNumber={}, responseType={}, responseCode={}",
                    mcpRequestDto.getResNo(),
                    mcpRequestDto.getReqWantNumber(),
                    response == null ? null : response.responseType(),
                    response == null ? null : response.responseCode());
            }
        } catch (InterruptedException e) {
            log.error("[fnSearchNumber] interrupted: resNo={}, reqWantNumber={}",
                mcpRequestDto.getResNo(), mcpRequestDto.getReqWantNumber(), e);
        } catch (Exception e) {
            log.error("[fnSearchNumber] unexpected error: resNo={}, reqWantNumber={}",
                mcpRequestDto.getResNo(), mcpRequestDto.getReqWantNumber(), e);
        }
        return rtnList;
    }

    private MplatFormNU1Request createDataSharingNU1Request(
        String reqWantNumber, String osstOrdNo, String agentCode, String customerId
    ) {
        InqrSvcNoInfoInDTO inDto = new InqrSvcNoInfoInDTO();
        inDto.setAsgnAgncId(agentCode);
        inDto.setAsgnAgncYn("Y");
        inDto.setCntryCd("KOR");
        inDto.setCustNo(customerId);
        inDto.setInqrBase("0");
        inDto.setInqrCascnt("10");
        inDto.setNowSvcIndCd("03");
        inDto.setSearchGubun("2");          // 1(예약번호조회) → 2(희망번호조회): ASIS와 동일, 예약번호 없으면 count=0
        inDto.setArPrGubun("AR");
        inDto.setTlphNoChrcCd("GEN");
        inDto.setTlphNoIndCd("01");
        inDto.setTlphNoPtrn("010____" + reqWantNumber);
        inDto.setTlphNoUseCd("R");
        inDto.setTlphNoUseMntCd("");        // FUK → "": ASIS에서 선불(PP)이 아닌 경우 빈값

        MplatFormNU1Request request = new MplatFormNU1Request();
        request.setOsstOrdNo(osstOrdNo);
        request.setInqrSvcNoInfoInDTO(inDto);
        return request;
    }

    private MSimpleOsstXmlVO reserveDataSharingNumber(
        McpRequestOsstDto osstDto, Map<String, String> osstParam
    ) {
        MplatFormNU2Request request = new MplatFormNU2Request();
        request.setOsstOrdNo(osstDto.getOsstOrdNo());

        String gubun = StringUtil.NVL(osstParam.get("gubun"), WORK_CODE_RES);
        ResvTlphNoInDTO inDto = new ResvTlphNoInDTO();
        inDto.setGubun(gubun);
        inDto.setTlphNo(osstDto.getTlphNo());
        inDto.setCustNo(osstParam.get("custNo"));
        inDto.setTlphNoStatChngRsnCd(gubun);
        inDto.setTlphNoStatCd(WORK_CODE_RES.equals(gubun) ? "AR" : "AA");
        inDto.setCustTypeCd("I");
        inDto.setNowSvcIndCd(StringUtil.NVL(osstDto.getOpenSvcIndCd(), "03"));
        inDto.setEncdTlphNo(osstDto.getEncdTlphNo());
        inDto.setMpngTlphNoYn("");
        inDto.setAsgnAgncId(osstDto.getAsgnAgncId());
        request.setResvTlphNoInDTO(inDto);

        MspPrxSoapResponse response = msfMcpOsstPrxService.callXmlOsstService(
            List.of(request), EVENT_CODE_NUMBER_REG, osstDto.getMvnoOrdNo());
        MSimpleOsstXmlVO vo = new MSimpleOsstXmlVO();
        vo.setResponseXml(response == null ? null : response.rawXml());
        vo.setGlobalNo(response == null ? null : response.globalNo());
        if (response != null && response.success()) {
            vo.setSuccess(true);
            vo.setResultCode(response.responseType());
            vo.setRsltCd(OSST_SUCCESS);
            vo.setRsltMsg(StringUtil.NVL(response.responseBasic(), "NU2 success"));
            vo.setSvcMsg(StringUtil.NVL(response.responseBasic(), "NU2 success"));
            return vo;
        }

        vo.setSuccess(false);
        vo.setResultCode(response == null ? null : response.responseCode());
        vo.setRsltCd(response == null ? null : response.responseCode());
        vo.setRsltMsg(response == null ? null : response.responseBasic());
        vo.setSvcMsg(response == null ? null : response.responseBasic());
        log.warn("[reserveDataSharingNumber] failed: resNo={}, tlphNo={}, responseType={}, responseCode={}, responseBasic={}",
            osstDto.getMvnoOrdNo(), osstDto.getTlphNo(),
            response == null ? null : response.responseType(),
            response == null ? null : response.responseCode(),
            response == null ? null : response.responseBasic());
        return vo;
    }

    private void copyMsfRequestToMcp(Long requestKey) {
        McpRequestVo mcpRequestVo = toMcpNewChangeReadMapper.selectMsfRequestToMcp(requestKey);
        if (mcpRequestVo != null && mcpRequestVo.getRequestKey() != null) {
            if (mcpRequestReadMapper.selectMcpRequest(requestKey) > 0) {
                mcpRequestNewChangeWriteMapper.updateMcpRequest(mcpRequestVo);
            } else {
                mcpRequestNewChangeWriteMapper.insertMcpRequest(mcpRequestVo);
            }
        }

        McpRequestCstmrVo mcpRequestCstmrVo = toMcpNewChangeReadMapper.selectMsfRequestCstmrToMcp(requestKey);
        if (mcpRequestCstmrVo != null && mcpRequestCstmrVo.getRequestKey() != null) {
            if (mcpRequestReadMapper.selectMcpRequestCstmr(requestKey) > 0) {
                mcpRequestCstmrWriteMapper.updateMcpRequestCstmr(mcpRequestCstmrVo);
            } else {
                mcpRequestCstmrWriteMapper.insertMcpRequestCstmr(mcpRequestCstmrVo);
            }
        }

        McpRequestAgentVo mcpRequestAgentVo = toMcpNewChangeReadMapper.selectMsfRequestAgentToMcp(requestKey);
        if (mcpRequestAgentVo != null && mcpRequestAgentVo.getRequestKey() != null) {
            if (mcpRequestReadMapper.selectMcpRequestAgent(requestKey) > 0) {
                mcpRequestAgentWriteMapper.updateMcpRequestAgent(mcpRequestAgentVo);
            } else {
                mcpRequestAgentWriteMapper.insertMcpRequestAgent(mcpRequestAgentVo);
            }
        }

        McpRequestReqVo mcpRequestReqVo = toMcpNewChangeReadMapper.selectMsfRequestBillReqToMcp(requestKey);
        if (mcpRequestReqVo != null && mcpRequestReqVo.getRequestKey() != null) {
            if (mcpRequestReadMapper.selectMcpRequestReq(requestKey) > 0) {
                mcpRequestReqWriteMapper.updateMcpRequestReq(mcpRequestReqVo);
            } else {
                mcpRequestReqWriteMapper.insertMcpRequestReq(mcpRequestReqVo);
            }
        }

        McpRequestSaleinfoVo mcpRequestSaleinfoVo = toMcpNewChangeReadMapper.selectMsfRequestSaleinfoToMcp(requestKey);
        if (mcpRequestSaleinfoVo != null && mcpRequestSaleinfoVo.getRequestKey() != null) {
            if (mcpRequestReadMapper.selectMcpRequestSaleinfo(requestKey) > 0) {
                mcpRequestSaleinfoWriteMapper.updateMcpRequestSaleinfo(mcpRequestSaleinfoVo);
            } else {
                mcpRequestSaleinfoWriteMapper.insertMcpRequestSaleinfo(mcpRequestSaleinfoVo);
            }
        }

        McpRequestMoveVo mcpRequestMoveVo = toMcpNewChangeReadMapper.selectMsfRequestMoveToMcp(requestKey);
        if (mcpRequestMoveVo != null && mcpRequestMoveVo.getRequestKey() != null) {
            if (mcpRequestReadMapper.selectMcpRequestMove(requestKey) > 0) {
                mcpRequestMoveWriteMapper.updateMcpRequestMove(mcpRequestMoveVo);
            } else {
                mcpRequestMoveWriteMapper.insertMcpRequestMove(mcpRequestMoveVo);
            }
        }

        McpRequestDvcChgVo mcpRequestDvcChgVo = toMcpNewChangeReadMapper.selectMsfRequestDvcChgToMcp(requestKey);
        if (mcpRequestDvcChgVo != null && mcpRequestDvcChgVo.getRequestKey() != null) {
            if (mcpRequestReadMapper.selectMcpRequestDvcChg(requestKey) > 0) {
                mcpRequestDvcChgWriteMapper.updateMcpRequestDvcChg(mcpRequestDvcChgVo);
            } else {
                mcpRequestDvcChgWriteMapper.insertMcpRequestDvcChg(mcpRequestDvcChgVo);
            }
        }

        log.debug("[copyMsfRequestToMcp] requestKey={}", requestKey);
    }

    private List<MPhoneNoVo> toPhoneNoList(MspPrxSoapResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            MplatFormNU1Response nu1Response = XmlConvertUtils.xmlReturnParser(
                response.rawXml(), MplatFormNU1Response.class);
            if (nu1Response != null && nu1Response.getOutDto() != null
                && nu1Response.getOutDto().getSvcNoListAll() != null) {
                return nu1Response.getOutDto().getSvcNoListAll().stream()
                    .map(payload -> mapper.convertValue(payload, MPhoneNoVo.class))
                    .filter(phoneNo -> StringUtils.hasText(phoneNo.getTlphNo()))
                    .toList();
            }
        } catch (Exception e) {
            log.warn("[toPhoneNoList] rawXml parse failed. fallback to payload: responseCode={}",
                response.responseCode(), e);
        }

        List<Object> payloadList = response.payloadList("svcNoList").orElse(Collections.emptyList());
        if (payloadList.isEmpty()) {
            payloadList = response.payloadList("outDto").orElse(Collections.emptyList());
        }
        return payloadList.stream()
            .filter(Map.class::isInstance)
            .map(payload -> mapper.convertValue(payload, MPhoneNoVo.class))
            .filter(phoneNo -> StringUtils.hasText(phoneNo.getTlphNo()))
            .toList();
    }

    private boolean isSuccessOP2(String rsltCd) {
        return AppformUtil.isSuccessOP2(rsltCd);
    }

    private FormResponse<Map<String, Object>> toDataSharingFormResponse(Map<String, Object> result) {
        if (!AJAX_SUCCESS.equals(StringUtil.NVL((String) result.get("RESULT_CODE"), ""))) {
            String message = StringUtil.NVL((String) result.get("ERROR_MSG"), (String) result.get("RESULT_MSG"));
            log.warn("[toDataSharingFormResponse] error response: resultCode={}, eventCode={}, message={}",
                result.get("RESULT_CODE"), result.get("EVENT_CODE"), message);
            return FormResponse.of(
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                StringUtil.NVL(message, "데이터쉐어링 저장 중 오류가 발생했습니다."),
                result
            );
        }
        log.debug("[toDataSharingFormResponse] success response: requestKey={}, resNo={}, tlphNo={}",
            result.get("REQUEST_KET"), result.get("RES_NO"), result.get("tlphNo"));
        return FormResponse.of(ResSvcChgMessage.SUCCESS, result);
    }

    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        HashMap<String, String> params = new HashMap<>();
        params.put("svcCntrNo", userCntrMngDto.getSvcCntrNo());
        params.put("cntrMobileNo", userCntrMngDto.getCntrMobileNo());
        params.put("subLinkName", userCntrMngDto.getSubLinkName());
        return mspApiDirectRepository.query("/mypage/cntrListNoLogin", params, McpUserCntrMngDto.class);
    }

    private String selectBanSel(String contractNum) {
        return mspApiDirectRepository.query("/mypage/selectBanSel", contractNum, String.class);
    }

    private AppformReqDto getCopyMcpRequest(AppformReqDto dataSharingReqDto) {
        log.debug("[getCopyMcpRequest] request: contractNum={}, reqUsimSn={}, mobileNo={}",
            dataSharingReqDto == null ? null : dataSharingReqDto.getContractNum(),
            dataSharingReqDto == null ? null : dataSharingReqDto.getReqUsimSn(),
            dataSharingReqDto == null ? null : dataSharingReqDto.getMobileNo());
        AppformReqDto result = svcAppformRepositoryImpl.getCopyMcpRequest(dataSharingReqDto);
        log.debug("[getCopyMcpRequest] result: contractNum={}, found={}, requestKey={}, resNo={}, cstmrName={}, prntsCtn={}",
            dataSharingReqDto == null ? null : dataSharingReqDto.getContractNum(),
            result != null,
            result == null ? null : result.getRequestKey(),
            result == null ? null : result.getResNo(),
            result == null ? null : result.getCstmrName(),
            result == null ? null : result.getPrntsCtn());
        return result;
    }

    private AppformReqDto getDataSharingOp0Appform(Long requestKey) {
        AppformReqDto result = svcAppformRepositoryImpl.selectDataSharingOp0Appform(requestKey);
        if (result == null) {
            log.error("[getDataSharingOp0Appform] MSF request not found: requestKey={}", requestKey);
            throw new McpCommonJsonException("0006", ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
        }
        log.info(
            "[getDataSharingOp0Appform] loaded: requestKey={}, resNo={}, agentCode={}, cntpntShopId={}, reqUsimSn={}, socCode={}",
            requestKey, result.getResNo(), result.getAgentCode(), result.getCntpntShopId(),
            result.getReqUsimSn(), result.getSocCode());
        applyDataSharingSpclSlsNo(result);
        return result;
    }

    private String getAgentCode(String cntpntShopId) {
        String agentCode = svcAppformRepositoryImpl.getAgentCode(cntpntShopId);
        log.debug("[getAgentCode] result: cntpntShopId={}, agentCode={}", cntpntShopId, agentCode);
        return agentCode;
    }

    private String getChrgPrmtId(AppformReqDto dataSharingReqDto) {
        return mspApiDirectRepository.query("/appform/getDisPrmtId", dataSharingReqDto, String.class);
    }

    private void applyDataSharingSpclSlsNo(AppformReqDto dataSharingReqDto) {
        if (StringUtils.hasText(dataSharingReqDto.getSpcCode())) {
            return;
        }

        String spclSlsNo = mspApiDirectRepository.query("/appform/getSpclSlsNo", dataSharingReqDto, String.class);
        if (StringUtils.hasText(spclSlsNo)) {
            dataSharingReqDto.setSpcCode(spclSlsNo);
        }
        log.debug("[applyDataSharingSpclSlsNo] result: requestKey={}, spcCode={}",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getSpcCode());
    }

    private McpRequestDto getMspPrdtCode(AppformReqDto appformReqDto) {
        McpRequestDto result = mspApiDirectRepository.query("/appform/mspPrdtCode", appformReqDto, McpRequestDto.class);
        log.debug("[getMspPrdtCode] result: modelId={}, found={}, reqModelName={}, reqModelColor={}, reqUsimName={}",
            appformReqDto == null ? null : appformReqDto.getModelId(),
            result != null,
            result == null ? null : result.getReqModelName(),
            result == null ? null : result.getReqModelColor(),
            result == null ? null : result.getReqUsimName());
        return result;
    }

    private NmcpCdDtlDto getCodeNmDto(String cdGroupId, String dtlCd) {
        NmcpCdDtlDto result = svcAppformRepositoryImpl.getCodeNmDto(cdGroupId, dtlCd);
        log.debug("[getCodeNmDto] result: cdGroupId={}, dtlCd={}, found={}, expnsnStrVal1={}",
            cdGroupId, dtlCd, result != null, result == null ? null : result.getExpnsnStrVal1());
        return result;
    }

    private AppformReqDto saveAppform(AppformReqDto dataSharingReqDto, Object custPoint, Object giftPromotionBasList) {
        log.trace("saveAppform excluded args: custPoint={}, giftPromotionBasList={}", custPoint != null, giftPromotionBasList != null);
        log.debug(
            "[saveAppform] start: requestKey={}, resNo={}, contractNum={}, reqUsimSn={}, reqWantNumber={}, operType={}, cstmrType={}, agentCode={}, cntpntShopId={}",
            dataSharingReqDto.getRequestKey(),
            dataSharingReqDto.getResNo(),
            dataSharingReqDto.getContractNum(),
            dataSharingReqDto.getReqUsimSn(),
            dataSharingReqDto.getReqWantNumber(),
            dataSharingReqDto.getOperType(),
            dataSharingReqDto.getCstmrType(),
            dataSharingReqDto.getAgentCode(),
            dataSharingReqDto.getCntpntShopId());

        // ASIS_SAVE_START/END 원본은 아래 현행 MSF 저장 흐름에 흡수했다.
        // MCP 전용 테이블/외부 의존 기능은 동일 MSF 테이블이 확인될 때 별도 이관한다.
        // ASIS saveAppform 기준: requestKey 인증/STEP 검증은 대리점/판매점 서식지 범위에서 제외한다.
        if (dataSharingReqDto.getRequestKey() == 0) {
            dataSharingReqDto.setRequestKey(generateKeyRepository.getGeneratedRequestKey());
            log.debug("[saveAppform] generated requestKey: requestKey={}, contractNum={}",
                dataSharingReqDto.getRequestKey(), dataSharingReqDto.getContractNum());
        }
        if (!StringUtils.hasText(dataSharingReqDto.getResNo())) {
            dataSharingReqDto.setResNo(generateKeyRepository.getGeneratedResNo());
            log.debug("[saveAppform] generated resNo: requestKey={}, resNo={}, contractNum={}",
                dataSharingReqDto.getRequestKey(), dataSharingReqDto.getResNo(), dataSharingReqDto.getContractNum());
        }

        if (!StringUtils.hasText(dataSharingReqDto.getUsimPriceType())) {
            dataSharingReqDto.setUsimPriceType(Constants.USIM_PRICE_TYPE_BASE);
            log.debug("[saveAppform] default usimPriceType set: requestKey={}, usimPriceType={}",
                dataSharingReqDto.getRequestKey(), dataSharingReqDto.getUsimPriceType());
        }
        if (!StringUtils.hasText(dataSharingReqDto.getJoinPriceType())) {
            dataSharingReqDto.setJoinPriceType(Constants.JOIN_PRICE_TYPE_BASE);
            log.debug("[saveAppform] default joinPriceType set: requestKey={}, joinPriceType={}",
                dataSharingReqDto.getRequestKey(), dataSharingReqDto.getJoinPriceType());
        }

        if (REQ_BUY_TYPE_PHONE.equals(dataSharingReqDto.getReqBuyType())) {
            McpRequestDto mapMspPrdtCode = getMspPrdtCode(dataSharingReqDto);
            if (mapMspPrdtCode == null) {
                log.warn("[saveAppform] msp product not found: requestKey={}, modelId={}, reqBuyType={}",
                    dataSharingReqDto.getRequestKey(), dataSharingReqDto.getModelId(), dataSharingReqDto.getReqBuyType());
                throw new McpCommonException(NO_EXSIST_MCP_MODEL_INFO);
            }
            if (StringUtils.hasText(mapMspPrdtCode.getReqModelName())) {
                dataSharingReqDto.setReqModelName(mapMspPrdtCode.getReqModelName());
            }
            if (StringUtils.hasText(mapMspPrdtCode.getReqModelColor())) {
                dataSharingReqDto.setReqModelColor(mapMspPrdtCode.getReqModelColor());
            }
            if (StringUtils.hasText(dataSharingReqDto.getRprsPrdtId())) {
                dataSharingReqDto.setModelId(dataSharingReqDto.getRprsPrdtId());
            }
            log.debug("[saveAppform] phone product set: requestKey={}, modelId={}, reqModelName={}, reqModelColor={}",
                dataSharingReqDto.getRequestKey(), dataSharingReqDto.getModelId(),
                dataSharingReqDto.getReqModelName(), dataSharingReqDto.getReqModelColor());
        } else if (REQ_BUY_TYPE_USIM.equals(dataSharingReqDto.getReqBuyType()) && StringUtils.hasText(dataSharingReqDto.getModelId())) {
            AppformReqDto appformReqPara = new AppformReqDto();
            appformReqPara.setModelId(dataSharingReqDto.getModelId());
            McpRequestDto mapMspPrdtCode = getMspPrdtCode(appformReqPara);
            if (mapMspPrdtCode != null) {
                dataSharingReqDto.setReqUsimName(mapMspPrdtCode.getReqModelName());
            }
            log.debug("[saveAppform] usim product set: requestKey={}, modelId={}, reqUsimName={}",
                dataSharingReqDto.getRequestKey(), dataSharingReqDto.getModelId(), dataSharingReqDto.getReqUsimName());
        }

        if (CSTMR_TYPE_NM.equals(dataSharingReqDto.getCstmrType())) {
            dataSharingReqDto.setAppCd("15");
            log.debug("[saveAppform] minor appCd set: requestKey={}, appCd={}",
                dataSharingReqDto.getRequestKey(), dataSharingReqDto.getAppCd());
        }

        // 유입 정보는 HttpSession에서 보정하지 않고 요청 DTO에 전달된 값을 그대로 사용한다.
        // ASIS saveAppform의 jehuPartnerType 세팅은 현행 AppformReqDto에 필드가 없어 제외한다.

        if ("Y".equals(dataSharingReqDto.getTelAdvice())) {
            dataSharingReqDto.setCntpntShopId("1100011744");
            dataSharingReqDto.setAgentCode("VKI0012");
            log.debug("[saveAppform] telAdvice shop set: requestKey={}, cntpntShopId={}, agentCode={}",
                dataSharingReqDto.getRequestKey(), dataSharingReqDto.getCntpntShopId(), dataSharingReqDto.getAgentCode());
        }

        // ASIS saveAppform 기준: 안면인증 검증/OSST 예약번호 갱신은 대리점/판매점 서식지 범위에서 제외한다.
        dataSharingReqDto.setFathMobileFn(dataSharingReqDto.getCstmrMobileFn());
        dataSharingReqDto.setFathMobileMn(dataSharingReqDto.getCstmrMobileMn());
        dataSharingReqDto.setFathMobileRn(dataSharingReqDto.getCstmrMobileRn());
        log.debug("[saveAppform] fath contact set: requestKey={}, fathMobile={}-{}-{}",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getFathMobileFn(),
            dataSharingReqDto.getFathMobileMn(), dataSharingReqDto.getFathMobileRn());

        // ASIS saveAppform 기준 제외 기능:
        // insertRequestSelfDlvry, Acen condition/target, payment/payInfo/change/dlvry/KT inter,
        // gift transaction, NMCP APD, point edit, commend, now delivery history, request detail, and KT counsel.
        log.debug("[saveAppform] insert MSF_REQUEST start: requestKey={}, resNo={}, contractNum={}",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getResNo(), dataSharingReqDto.getContractNum());
        svcAppformRepositoryImpl.insertMsfRequest(dataSharingReqDto);
        log.debug("[saveAppform] insert MSF_REQUEST done: requestKey={}, resNo={}",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getResNo());

        log.debug("[saveAppform] insert MSF_REQUEST_CSTMR start: requestKey={}, cstmrName={}, selfCstmrCi={}",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getCstmrName(), dataSharingReqDto.getSelfCstmrCi());
        svcAppformRepositoryImpl.insertMsfRequestCstmr(dataSharingReqDto);
        log.debug("[saveAppform] insert MSF_REQUEST_CSTMR done: requestKey={}", dataSharingReqDto.getRequestKey());

        log.debug("[saveAppform] insert MSF_REQUEST_AGENT start: requestKey={}, cstmrType={}, minorAgentName={}",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getCstmrType(), dataSharingReqDto.getMinorAgentName());
        svcAppformRepositoryImpl.insertMsfRequestAgent(dataSharingReqDto);
        log.debug("[saveAppform] insert MSF_REQUEST_AGENT done: requestKey={}", dataSharingReqDto.getRequestKey());

        log.debug("[saveAppform] insert MSF_REQUEST_MOVE start: requestKey={}, moveCompany={}, moveMobile={}-{}-{}",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getMoveCompany(),
            dataSharingReqDto.getMoveMobileFn(), dataSharingReqDto.getMoveMobileMn(), dataSharingReqDto.getMoveMobileRn());
        svcAppformRepositoryImpl.insertMsfRequestMove(dataSharingReqDto);
        log.debug("[saveAppform] insert MSF_REQUEST_MOVE done: requestKey={}", dataSharingReqDto.getRequestKey());

        log.debug("[saveAppform] insert MSF_REQUEST_PAYMENT skipped: requestKey={}, reqAcType={}, reason=no MSF payment table",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getReqAcType());

        log.debug("[saveAppform] insert MSF_REQUEST_SALE start: requestKey={}, modelId={}, socCode={}, joinPayMthdCd={}",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getModelId(),
            dataSharingReqDto.getSocCode(), dataSharingReqDto.getJoinPayMthdCd());
        svcAppformRepositoryImpl.insertMsfRequestSale(dataSharingReqDto);
        log.debug("[saveAppform] insert MSF_REQUEST_SALE done: requestKey={}", dataSharingReqDto.getRequestKey());

        if (OPER_TYPE_EXCHANGE.equals(dataSharingReqDto.getOperType()) || OPER_TYPE_CHANGE.equals(dataSharingReqDto.getOperType())) {
            log.debug("[saveAppform] insert MSF_REQUEST_DVC_CHG start: requestKey={}, operType={}",
                dataSharingReqDto.getRequestKey(), dataSharingReqDto.getOperType());
            svcAppformRepositoryImpl.insertMsfRequestDvcChg(dataSharingReqDto);
            log.debug("[saveAppform] insert MSF_REQUEST_DVC_CHG done: requestKey={}", dataSharingReqDto.getRequestKey());
        }

        log.debug("[saveAppform] insert MSF_REQUEST_DLVRY skipped: requestKey={}, dlvryType={}, dlvryName={}, reason=no MSF delivery table",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getDlvryType(), dataSharingReqDto.getDlvryName());

        log.debug("[saveAppform] insert MSF_REQUEST_BILL_REQ start: requestKey={}, reqPayType={}, prntsBillNo={}",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getReqPayType(), dataSharingReqDto.getPrntsBillNo());
        svcAppformRepositoryImpl.insertMsfRequestBillReq(dataSharingReqDto);
        log.debug("[saveAppform] insert MSF_REQUEST_BILL_REQ done: requestKey={}", dataSharingReqDto.getRequestKey());

        // ASIS saveAppform 기준 제외: 단말기 일시불 결제 정보 저장.
        // if ("01".equals(dataSharingReqDto.getSettlWayCd())) {
        //     svcAppformRepositoryImpl.insertMcpRequestPayInfo(dataSharingReqDto);
        // }

        if (dataSharingReqDto.getAdditionKeyList() != null && dataSharingReqDto.getAdditionKeyList().length > 0) {
            log.debug("[saveAppform] insert MSF_REQUEST_ADDITION start: requestKey={}, additionKeyList={}",
                dataSharingReqDto.getRequestKey(), java.util.Arrays.toString(dataSharingReqDto.getAdditionKeyList()));
            // Oracle MCP_ADDITION 조회 → PostgreSQL MSF_REQUEST_ADDITION 개별 저장
            java.util.List<java.util.Map<String, Object>> additionList = svcAppformRepositoryImpl.getMcpAdditionList(dataSharingReqDto);
            for (java.util.Map<String, Object> addition: additionList) {
                addition.put("requestKey", dataSharingReqDto.getRequestKey());
                svcAppformRepositoryImpl.insertMsfRequestAdditionItem(addition);
            }
            log.debug("[saveAppform] insert MSF_REQUEST_ADDITION done: requestKey={}, insertedCount={}",
                dataSharingReqDto.getRequestKey(), additionList.size());
        } else {
            log.debug("[saveAppform] insert MSF_REQUEST_ADDITION skipped: requestKey={}, additionKeyList=nullOrEmpty",
                dataSharingReqDto.getRequestKey());
        }

        // ASIS saveAppform 기준 제외: KT 인터넷 정보, 사은품/프로모션 부가서비스, 자급제/APD, 포인트, 추천인, 신청서 상세, KT 상담.
        log.debug("[saveAppform] insert MSF_REQUEST_STATE start: requestKey={}, requestStateCode={}, resNo={}",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getRequestStateCode(), dataSharingReqDto.getResNo());
        svcAppformRepositoryImpl.insertMsfRequestState(dataSharingReqDto);
        log.debug("[saveAppform] insert MSF_REQUEST_STATE done: requestKey={}", dataSharingReqDto.getRequestKey());

        log.debug("[saveAppform] done: requestKey={}, resNo={}, contractNum={}, reqUsimSn={}",
            dataSharingReqDto.getRequestKey(), dataSharingReqDto.getResNo(),
            dataSharingReqDto.getContractNum(), dataSharingReqDto.getReqUsimSn());
        return dataSharingReqDto;
    }

    private MSimpleOsstXmlVO sendOsstPreCheckXmlService(AppformReqDto appformReqDto)
        throws SocketTimeoutException {
        NewChangeRequest newChangeRequest = new NewChangeRequest();
        newChangeRequest.setRequestKey(appformReqDto.getRequestKey());

        // 데이터쉐어링은 TEMP가 아니라 저장 완료된 MSF_REQUEST 계열 복사 데이터를 기준으로 MP 요청값을 구성한다.
        NewChangeMpPC0Response mpInfo = svcAppformRepositoryImpl.selectMsfRequestMpRequest(newChangeRequest);
        if (mpInfo == null) {
            log.warn("[sendOsstPreCheckXmlService] MP request data empty: requestKey={}, resNo={}",
                appformReqDto.getRequestKey(), appformReqDto.getResNo());
            throw new McpMplatFormException(ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
        }
        applyDataSharingPreCheckOverrides(mpInfo, appformReqDto);
        String cpntId = resolvePreCheckCpntId(appformReqDto);
        log.debug(
            "[sendOsstPreCheckXmlService] MP request data: requestKey={}, resNo={}, cpntId={}, cntpntCd={}, selfCertTypeSet={}, custIdntNoSet={}, rsdcrtIssuDateSet={}, brthDateSet={}, mvnoOrdNo={}",
            appformReqDto.getRequestKey(),
            appformReqDto.getResNo(),
            cpntId,
            mpInfo.getCntpntCd(),
            StringUtils.hasText(appformReqDto.getSelfCertType()),
            StringUtils.hasText(mpInfo.getCustIdntNo()),
            StringUtils.hasText(mpInfo.getRsdcrtIssuDate()) || StringUtils.hasText(normalizeDigits(appformReqDto.getSelfIssuExprDt())),
            StringUtils.hasText(mpInfo.getBrthDate()),
            mpInfo.getMvnoOrdNo());

        boolean frmpapPreCheck = isFrmpapCpntId(cpntId);
        List<Object> requestDtoList;
        String appEventCd;
        if (frmpapPreCheck) {
            MplatFormFPC0InDtoRequest inDto = NewChangeMpFieldMapper.INSTANCE.toMplatFormFPC0InDtoRequest(mpInfo);
            MplatFormFPC0InFrmpapDtoRequest inFrmpapDto =
                NewChangeMpFieldMapper.INSTANCE.toMplatFormFPC0InFrmpapDtoRequest(mpInfo);
            normalizeNullStringProperties(inDto);
            normalizeNullStringProperties(inFrmpapDto);
            requestDtoList = List.of(inDto, inFrmpapDto);
            appEventCd = "FPC0";
        } else {
            MplatFormPC0InDtoRequest inDto = NewChangeMpFieldMapper.INSTANCE.toMplatFormPC0InDtoRequest(mpInfo);
            applyDataSharingIdentityFields(inDto, appformReqDto);
            inDto.setCpntId(cpntId);
            normalizeNullStringProperties(inDto);
            applyDataSharingAsisPc0OptionalFields(inDto, appformReqDto);
            requestDtoList = List.of(inDto);
            appEventCd = Constants.EVENT_CODE_PRE_CHECK;
        }

        log.info("[sendOsstPreCheckXmlService] request: eventCode={}, requestKey={}, resNo={}, mvnoOrdNo={}",
            appEventCd, appformReqDto.getRequestKey(), appformReqDto.getResNo(), mpInfo.getMvnoOrdNo());
        MspPrxSoapResponse response = msfMcpOsstPrxService.callXmlOsstService(requestDtoList, appEventCd, mpInfo.getMvnoOrdNo());
        String responseXml = response == null ? null : response.rawXml();
        log.info("[sendOsstPreCheckXmlService] response: eventCode={}, requestKey={}, resNo={}, responseType={}, responseCode={}, length={}",
            appEventCd,
            appformReqDto.getRequestKey(),
            appformReqDto.getResNo(),
            response == null ? null : response.responseType(),
            response == null ? null : response.responseCode(),
            responseXml == null ? 0 : responseXml.length());

        if (!StringUtils.hasText(responseXml)) {
            throw new McpMplatFormException(ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
        }

        MSimpleOsstXmlVO osst = new MSimpleOsstXmlVO();
        osst.setResponseXml(responseXml);
        try {
            osst.toResponseParse();
        } catch (SelfServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new McpMplatFormException(e);
        }
        return osst;
    }

    private String callFaceAuthExceptionForDataSharing(AppformReqDto appformReqDto) {
        // [ASIS] 안면인증 예외처리 FS8(fathTransacId 획득) → FT1(스킵 등록) — KNOTE 서식지 ID로 대체
        // Step 1: FS8 - photoAthnNcstYn='Y'로 예외 등록용 fathTransacId 획득
        //FaceAuthFs8Request fs8Request = new FaceAuthFs8Request();
        //FaceAuthFs8Request.CustFathInfoDTO custFathInfo = new FaceAuthFs8Request.CustFathInfoDTO();
        //custFathInfo.setOrgId(appformReqDto.getAgentCode());
        //custFathInfo.setCpntId(ORGN_ID);
        //custFathInfo.setOnlineOfflnDivCd(FaceAuthOnlineOfflineDivision.ONLINE.getCode());
        //custFathInfo.setFathSbscDivCd("1");
        //custFathInfo.setRetvCdVal(FaceAuthIdentityType.REGID.getApiCode());
        //custFathInfo.setPhotoAthnNcstYn("Y");
        //custFathInfo.setPhotoAthnTxnSeq("");
        //custFathInfo.setScanTypeCd(FaceAuthScanType.ID.getCode());
        //fs8Request.setCustFathInfoDTO(custFathInfo);
        //String fs8Xml = XmlConvertUtils.convertObjectToXml(fs8Request);
        //
        //String mobileNo = appformReqDto.getCstmrMobileFn() + appformReqDto.getCstmrMobileMn() + appformReqDto.getCstmrMobileRn();
        //Map<String, String> fs8Params = new HashMap<>();
        //fs8Params.put(FaceAuthPrxRequestKey.APP_EVENT_CODE.getCode(), FaceAuthPrxRequestType.FS8.getCode());
        //fs8Params.put(FaceAuthPrxRequestKey.RES_NO.getCode(), appformReqDto.getResNo());
        //fs8Params.put(FaceAuthPrxRequestKey.SMS_RECV_TEL_NO.getCode(), mobileNo);
        //fs8Params.put(FaceAuthPrxRequestKey.ASGN_AGNC_ID.getCode(), appformReqDto.getAgentCode());
        //fs8Params.put(FaceAuthPrxRequestKey.SERVICE_NAME.getCode(), "OsstCustFathMgmtService");
        //fs8Params.put(FaceAuthPrxRequestKey.SERVICE_INFO.getCode(), "osst:custFathUrlRqt");
        //fs8Params.put(FaceAuthPrxRequestKey.SERVICE_VO.getCode(), "CustFathUrlRqtInVO");
        //fs8Params.put(FaceAuthPrxRequestKey.XML.getCode(), fs8Xml);
        //
        //log.info("[callFaceAuthExceptionForDataSharing] FS8 호출: resNo={}, agentCode={}",
        //    appformReqDto.getResNo(), appformReqDto.getAgentCode());
        //MspPrxSoapResponse fs8Response = msfMcpOsstPrxService.callXmlOsstServiceWithParams(
        //    fs8Params, FaceAuthPrxRequestType.FS8.getCode(), appformReqDto.getResNo());
        //
        //String fathTransacId = fs8Response.payloadText("outDto", "fathTransacId").orElse(null);
        //log.info("[callFaceAuthExceptionForDataSharing] FS8 응답: resNo={}, fathTransacId={}",
        //    appformReqDto.getResNo(), fathTransacId);
        //if (!StringUtils.hasText(fathTransacId)) {
        //    throw new SelfServiceException("안면인증 예외 처리 실패: FS8 fathTransacId 없음");
        //}
        //
        //// Step 2: FT1 - 안면인증 트랜잭션 스킵 등록 (osst:custFathTxnSkipReq)
        //FaceAuthFt1Request ft1Request = new FaceAuthFt1Request();
        //ft1Request.setFathTransacId(fathTransacId);
        //String ft1Xml = XmlConvertUtils.convertObjectToXml(ft1Request);
        //
        //Map<String, String> ft1Params = new HashMap<>();
        //ft1Params.put(FaceAuthPrxRequestKey.APP_EVENT_CODE.getCode(), FaceAuthPrxRequestType.FT1.getCode());
        //ft1Params.put(FaceAuthPrxRequestKey.RES_NO.getCode(), appformReqDto.getResNo());
        //ft1Params.put(FaceAuthPrxRequestKey.ASGN_AGNC_ID.getCode(), appformReqDto.getAgentCode());
        //ft1Params.put(FaceAuthPrxRequestKey.SERVICE_NAME.getCode(), "OsstCustFathMgmtService");
        //ft1Params.put(FaceAuthPrxRequestKey.SERVICE_INFO.getCode(), "osst:custFathTxnSkipReq");
        //ft1Params.put(FaceAuthPrxRequestKey.SERVICE_VO.getCode(), "CustFathTxnSkipReqInVO");
        //ft1Params.put(FaceAuthPrxRequestKey.XML.getCode(), ft1Xml);
        //
        //log.info("[callFaceAuthExceptionForDataSharing] FT1 호출: resNo={}, fathTransacId={}",
        //    appformReqDto.getResNo(), fathTransacId);
        //MspPrxSoapResponse ft1Response = msfMcpOsstPrxService.callXmlOsstServiceWithParams(
        //    ft1Params, FaceAuthPrxRequestType.FT1.getCode(), appformReqDto.getResNo());
        //
        //String ft1RsltCd = ft1Response.payloadText("outDto", "rsltCd").orElse("");
        //String ft1RsltMsg = ft1Response.payloadText("outDto", "rsltMsg").orElse("");
        //log.info("[callFaceAuthExceptionForDataSharing] FT1 응답: resNo={}, rsltCd={}, rsltMsg={}",
        //    appformReqDto.getResNo(), ft1RsltCd, ft1RsltMsg);
        //if (!"0000".equals(ft1RsltCd)) {
        //    throw new SelfServiceException(FaceAuthPrxRequestType.FT1.getCode() + ": " + ft1RsltMsg);
        //}
        //
        //return fathTransacId;

        // KNOTE 신분증 인증 처리 (신규쪽 AuthInfoService.checkIdStatus FS1 참조)
        // 안면인증 FATH ID 대신 서식지 ID(frmpapId)로 처리
        // 임시 테스트: 박해준 등록 frmpapId 하드코딩
        KnoteScanInfoRequest knoteReq = new KnoteScanInfoRequest();
//        knoteReq.setFrmpapId("0x3CB8BF406BB111F1BBE00080C74455C600");
//        knoteReq.setAgentCd("V000084398");

        log.info("[callFaceAuthExceptionForDataSharing] KNOTE FS1 요청: resNo={}, frmpapId={}, agentCd={}",
            appformReqDto.getResNo(), knoteReq.getFrmpapId(), knoteReq.getAgentCd());

        FormResponse<KnoteScanInfoResponse> knoteRes = authInfoService.checkIdStatus(knoteReq);

        if (knoteRes == null || !"0000".equals(knoteRes.resCode())) {
            String msg = knoteRes != null ? StringUtil.NVL(knoteRes.resMessage(), "KNOTE 응답 오류") : "KNOTE 응답 없음";
            log.warn("[callFaceAuthExceptionForDataSharing] KNOTE FS1 실패: resNo={}, frmpapId={}, resCode={}, msg={}",
                appformReqDto.getResNo(), knoteReq.getFrmpapId(),
                knoteRes != null ? knoteRes.resCode() : "", msg);
            throw new SelfServiceException("KNOTE 신분증 인증 실패: " + msg);
        }

        KnoteScanInfoResponse knoteData = knoteRes.resData();
        log.info("[callFaceAuthExceptionForDataSharing] KNOTE FS1 성공: resNo={}, frmpapId={}, custNm={}",
            appformReqDto.getResNo(), knoteReq.getFrmpapId(),
            knoteData != null ? knoteData.getCustNm() : "");

        return knoteReq.getFrmpapId(); // 서식지 ID를 FATH ID 대신 반환
    }

    private void applyDataSharingPreCheckOverrides(NewChangeMpPC0Response mpInfo, AppformReqDto appformReqDto) {
        String cstmrNativeRrnDigits = getCstmrNativeRrnDigits(appformReqDto);
        if (StringUtils.hasText(cstmrNativeRrnDigits)) {
            // AS-IS 대비 추가: 저장 데이터가 암호문이면 MP EncryptAdapter에서 재암호화되므로 복호화된 주민번호를 사용한다.
            mpInfo.setCustIdntNo(cstmrNativeRrnDigits);
        }

        // MP brthDate는 YYYYMMDD 형식이어야 하므로 8자리 숫자가 아니면 복호화 주민번호로 보정한다.
        if (!isValidBirthDate(mpInfo.getBrthDate()) && cstmrNativeRrnDigits.length() >= 7) {
            mpInfo.setBrthDate(NmcpServiceUtils.getSsnDate(cstmrNativeRrnDigits));
        }
    }

    private void applyDataSharingAsisPc0OptionalFields(MplatFormPC0InDtoRequest target, AppformReqDto appformReqDto) {
        target.setMyslfAthnYn(null);
        if (!StringUtils.hasText(appformReqDto.getOnlineAuthType())) {
            target.setOnlineAthnDivCd(null);
        }
        if (!StringUtils.hasText(appformReqDto.getSelfCstmrCi())) {
            target.setIpinCi(null);
        }
        if (!StringUtils.hasText(appformReqDto.getFathTransacId())) {
            target.setFathTransacId(null);
        }
    }

    private void applyDataSharingIdentityFields(MplatFormPC0InDtoRequest target, AppformReqDto appformReqDto) {
        // AS-IS 대비 추가: SELF_CERT_TYPE 기준으로 실명증빙코드와 부속 식별값을 함께 보정한다.
        String nativeRlnamAthnEvdnPprCd = Constants.FATH_RETV_CD_VAL.get(appformReqDto.getSelfCertType());
        if (StringUtils.hasText(nativeRlnamAthnEvdnPprCd)) {
            target.setNativeRlnamAthnEvdnPprCd(nativeRlnamAthnEvdnPprCd);
        }

        String rsdcrtIssuDate = normalizeDigits(appformReqDto.getSelfIssuExprDt());
        if (StringUtils.hasText(rsdcrtIssuDate)) {
            target.setRsdcrtIssuDate(rsdcrtIssuDate);
        }

        target.setMyslfAthnYn(
            StringUtils.hasText(appformReqDto.getSelfCstmrCi()) || StringUtils.hasText(appformReqDto.getFathTransacId())
                ? "Y"
                : "N");
        target.setOnlineAthnDivCd(resolveOnlineAthnDivCd(appformReqDto.getOnlineAuthType()));
        if (StringUtils.hasText(appformReqDto.getSelfCstmrCi())) {
            target.setIpinCi(appformReqDto.getSelfCstmrCi());
        }
        if (StringUtils.hasText(appformReqDto.getFathTransacId())) {
            target.setFathTransacId(appformReqDto.getFathTransacId());
        }
        applyDataSharingTemporaryPc0Values(target);

        String selfIssuNum = normalizeDigits(appformReqDto.getSelfIssuNumDesc());
        if ("DRIVE".equals(target.getNativeRlnamAthnEvdnPprCd()) && selfIssuNum.length() > 2) {
            target.setLcnsRgnCd(selfIssuNum.substring(0, 2));
            target.setLcnsNo(selfIssuNum.substring(2));
        } else if ("MERIT".equals(target.getNativeRlnamAthnEvdnPprCd()) && StringUtils.hasText(selfIssuNum)) {
            target.setMrtrPrsnNo(selfIssuNum);
        }

    }

    private void applyDataSharingTemporaryPc0Values(MplatFormPC0InDtoRequest target) {
        target.setM2mHndsetYn("N");
        if (!StringUtils.hasText(target.getNativeRlnamAthnEvdnPprCd())) {
            target.setNativeRlnamAthnEvdnPprCd("REGID");
        }
        if (!StringUtils.hasText(target.getRsdcrtIssuDate())) {
            target.setRsdcrtIssuDate("20200101");
        }
        applyDataSharingRequiredTestValues(target);
    }

    private void applyDataSharingRequiredTestValues(MplatFormPC0InDtoRequest target) {
        // TODO 확인: PC0 저장 데이터 세팅
        setIfBlank(target.getSlsCmpnCd(), target::setSlsCmpnCd, "KIS");
        setIfBlank(target.getCustTypeCd(), target::setCustTypeCd, "I");
        setIfBlank(target.getCustIdntNoIndCd(), target::setCustIdntNoIndCd, "01");
        setIfBlank(target.getCntrUseCd(), target::setCntrUseCd, "R");
        setIfBlank(target.getInstYn(), target::setInstYn, "N");
        setIfBlank(target.getScnhndPhonInstYn(), target::setScnhndPhonInstYn, "N");
        setIfBlank(target.getMyslAgreYn(), target::setMyslAgreYn, "Y");
        setIfBlank(target.getCrdtInfoAgreYn(), target::setCrdtInfoAgreYn, "Y");
        setIfBlank(target.getIndvInfoInerPrcuseAgreYn(), target::setIndvInfoInerPrcuseAgreYn, "Y");
        setIfBlank(target.getCnsgInfoAdvrRcvAgreYn(), target::setCnsgInfoAdvrRcvAgreYn, "N");
        setIfBlank(target.getOthcmpInfoAdvrRcvAgreYn(), target::setOthcmpInfoAdvrRcvAgreYn, "N");
        setIfBlank(target.getOthcmpInfoAdvrCnsgAgreYn(), target::setOthcmpInfoAdvrCnsgAgreYn, "N");
        setIfBlank(target.getGrpAgntBindSvcSbscAgreYn(), target::setGrpAgntBindSvcSbscAgreYn, "N");
        setIfBlank(target.getCardInsrPrdcAgreYn(), target::setCardInsrPrdcAgreYn, "N");
        setIfBlank(target.getOlngDscnHynmtrAgreYn(), target::setOlngDscnHynmtrAgreYn, "N");
        setIfBlank(target.getWlfrDscnAplyAgreYn(), target::setWlfrDscnAplyAgreYn, "N");
        setIfBlank(target.getSpamPrvdAgreYn(), target::setSpamPrvdAgreYn, "N");
        setIfBlank(target.getPrttlpStlmUseAgreYn(), target::setPrttlpStlmUseAgreYn, "N");
        setIfBlank(target.getPrttlpStlmPwdUseAgreYn(), target::setPrttlpStlmPwdUseAgreYn, "N");
        setIfBlank(target.getCustInfoChngYn(), target::setCustInfoChngYn, "N");
        setIfBlank(target.getM2mHndsetYn(), target::setM2mHndsetYn, "N");
        setIfBlank(target.getFnncDealAgreeYn(), target::setFnncDealAgreeYn, "N");
    }

    private void setIfBlank(String value, java.util.function.Consumer<String> setter, String defaultValue) {
        if (!StringUtils.hasText(value)) {
            setter.accept(defaultValue);
        }
    }

    private boolean isFrmpapCpntId(String cpntId) {
        return StringUtils.hasText(cpntId) && cpntId.startsWith("V");
    }

    private String resolveOnlineAthnDivCd(String onlineAuthType) {
        if (!StringUtils.hasText(onlineAuthType)) {
            return null;
        }
        if ("S".equals(onlineAuthType)) {
            return "71";
        }
        if ("C".equals(onlineAuthType)) {
            return "20";
        }
        if ("X".equals(onlineAuthType)) {
            return "10";
        }
        return "30";
    }

    private void normalizeNullStringProperties(Object target) {
        // AS-IS 대비 추가: JAXB 변환 시 null 문자열이 MP로 전달되지 않도록 문자열 null을 빈 값으로 정규화한다.
        BeanWrapper beanWrapper = new BeanWrapperImpl(target);
        for (java.beans.PropertyDescriptor propertyDescriptor: beanWrapper.getPropertyDescriptors()) {
            String propertyName = propertyDescriptor.getName();
            if ("class".equals(propertyName) || propertyDescriptor.getWriteMethod() == null) {
                continue;
            }
            if (String.class.equals(propertyDescriptor.getPropertyType()) && beanWrapper.getPropertyValue(propertyName) == null) {
                beanWrapper.setPropertyValue(propertyName, "");
            }
        }
    }

    private void applyDataSharingShopContext(AppformReqDto appformReqDto) {
        // 신규개발 저장 로직과 동일하게 로그인 판매점코드를 CPNT_ID로 저장한다.
        appformReqDto.setCpntId(getLoginShopCode());
        log.debug("[applyDataSharingShopContext] requestKey={}, resNo={}, cpntId={}, cntpntShopId={}",
            appformReqDto.getRequestKey(), appformReqDto.getResNo(), appformReqDto.getCpntId(), appformReqDto.getCntpntShopId());
    }

    private String resolvePreCheckCpntId(AppformReqDto appformReqDto) {
        log.trace("resolvePreCheckCpntId: requestKey={}", appformReqDto == null ? null : appformReqDto.getRequestKey());
        return Constants.CONTPNT_SHOP_ID_MSHOP;
    }

    private String getLoginShopCode() {
        return AuthenticationUtils.getShopCode();
    }

    private String getCstmrNativeRrnDigits(AppformReqDto appformReqDto) {
        String cstmrNativeRrn = appformReqDto.getCstmrNativeRrnDesc();
        return normalizeDigits(cstmrNativeRrn);
    }

    private String normalizeDigits(String value) {
        if (!StringUtils.hasText(value)) {
            return "";
        }
        return value.replaceAll("[^0-9]", "");
    }

    private boolean isValidBirthDate(String birthDate) {
        return StringUtils.hasText(birthDate) && birthDate.matches("\\d{8}");
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private MSimpleOsstXmlVO sendOsstService(Map<String, String> osstParam, String eventCode)
        throws SelfServiceException, SocketTimeoutException, McpMplatFormException {
        HashMap<String, String> param = new HashMap<>(osstParam);
        param.put("appEventCd", eventCode);
        log.debug("[sendOsstService] request: eventCode={}, resNo={}, prntsContractNo={}, custNo={}, billAcntNo={}",
            eventCode, param.get("resNo"), param.get("prntsContractNo"), param.get("custNo"), param.get("billAcntNo"));
        MSimpleOsstXmlVO osst = new MSimpleOsstXmlVO();
        mplatFormOsstServerAdapter.callService(param, osst, 100000);
        log.debug("[sendOsstService] response: eventCode={}, resNo={}, success={}, resultCode={}",
            eventCode, param.get("resNo"), osst.isSuccess(), osst.getResultCode());
        return osst;
    }

    private MSimpleOsstXmlVO sendOsstOpenXmlService(AppformReqDto appformReqDto, Map<String, String> osstParam)
        throws McpMplatFormException, SelfServiceException, SocketTimeoutException {

        MplatFormOP0InDtoRequest inDto = new MplatFormOP0InDtoRequest();

        // ASIS OP0 본문에는 OSST 오더번호와 고객번호만 포함한다.
        inDto.setOsstOrdNo(osstParam.get("osstOrdNo"));
        inDto.setCustNo(osstParam.get("custNo"));
        inDto.setTlphNo(osstParam.get("tlphNo"));
        // ASIS SimpleOpenService는 데이터쉐어링 OP0 전문 끝에 모회선 청구계정번호를 별도로 추가한다.
        String billAcntNo = osstParam.get("billAcntNo");
        inDto.setBillAcntNo(StringUtils.hasText(billAcntNo) ? billAcntNo : null);

        // USIM
        inDto.setIccId(appformReqDto.getReqUsimSn());
        inDto.setESimOpenYn("N");
        inDto.setUsimOpenYn("Y");

        // 대리점/접점
        inDto.setMngmAgncId(appformReqDto.getAgentCode());
        inDto.setCntpntCd(appformReqDto.getCntpntShopId());

        // 데이터쉐어링 저장 시 통합청구는 0이며, OP0는 MSF 청구정보의 저장값을 우선 사용한다.
        inDto.setBlpymMthdCd(StringUtil.NVL(appformReqDto.getReqPayType(), "0"));
        inDto.setBlpymMthdIdntNo("");
        inDto.setDuedatDateIndCd("21");
        String blpymHideYn = svcAppformRepositoryImpl.getMspCommonCodeEtc1("RCP2044", "BLPYHIDE");
        inDto.setBlpymMthdIdntNoHideYn(StringUtil.NVL(blpymHideYn, "Y"));
        log.info("[sendOsstOpenXmlService] integrated billing: resNo={}, billAcntNoPresent={}",
            osstParam.get("resNo"), StringUtils.hasText(inDto.getBillAcntNo()));

        // 청구서 수신
        inDto.setRqsshtPprfrmCd(appformReqDto.getCstmrBillSendCode());
        inDto.setRqsshtTlphNoHideYn("Y");
        inDto.setRqsshtEmlAdrsNm(StringUtils.hasText(appformReqDto.getCstmrMail())
            ? appformReqDto.getCstmrMail() : null);
        inDto.setBillZipNo(StringUtil.NVL(appformReqDto.getCstmrPost(), ""));
        inDto.setBillFndtCntplcSbst(StringUtil.NVL(appformReqDto.getCstmrAddr(), ""));
        inDto.setBillMntCntplcSbst(StringUtil.NVL(appformReqDto.getCstmrAddrDtl(), ""));
        if ("MB".equals(appformReqDto.getCstmrBillSendCode())) {
            inDto.setRqsshtTlphNo(osstParam.get("tlphNo"));
        }

        // 동의자료코드: 오프라인(0,3)=03, 그 외=01
        String onOffType = appformReqDto.getOnOffType();
        inDto.setAgreIndCd(("0".equals(onOffType) || "3".equals(onOffType)) ? "03" : "01");

        // 본인인증타입코드
        String onlineAuthType = appformReqDto.getOnlineAuthType();
        if ("S".equals(onlineAuthType)) {
            inDto.setMyslAthnTypeCd("01");
        } else if ("C".equals(onlineAuthType)) {
            inDto.setMyslAthnTypeCd("03");
        } else if ("X".equals(onlineAuthType)) {
            inDto.setMyslAthnTypeCd("04");
        } else {
            inDto.setMyslAthnTypeCd("03");
        }
        inDto.setBillAtchExclYn("");

        // 단말 정보 (데이터쉐어링: USIM 단독 개통)
        inDto.setIntmMdlId(null);
        inDto.setIntmSrlNo(null);
        inDto.setHndsetInstAmnt(null);
        inDto.setHndsetPrpyAmnt(null);
        inDto.setInstMnthCnt("");

        // 판매/약정 정보
        inDto.setSpclSlsNo(StringUtils.hasText(appformReqDto.getSpcCode()) ? appformReqDto.getSpcCode() : "");
        inDto.setSpnsDscnTypeCd(StringUtils.hasText(appformReqDto.getSprtTp()) ? appformReqDto.getSprtTp() : null);
        inDto.setAgncSupotAmnt(appformReqDto.getModelDiscount3() == 0
            ? "" : String.valueOf(appformReqDto.getModelDiscount3()));
        inDto.setEnggMnthCnt(appformReqDto.getEnggMnthCnt() == 0
            ? "" : String.valueOf(appformReqDto.getEnggMnthCnt()));

        // USIM 납부방법: 1→N(면제), 2→R(선납), 3→B(후납)
        String usimPay = appformReqDto.getUsimPayMthdCd();
        if ("1".equals(usimPay)) {
            inDto.setUsimPymnMthdCd("N");
        } else if ("2".equals(usimPay)) {
            inDto.setUsimPymnMthdCd("R");
        } else {
            inDto.setUsimPymnMthdCd("B");
        }

        // 가입비 납부방법: 1→P(면제), 2→R(완납), 3→I(분납)
        String joinPay = appformReqDto.getJoinPayMthdCd();
        if ("1".equals(joinPay)) {
            inDto.setSbscstPymnMthdCd("P");
            inDto.setSbscstImpsExmpRsnCd("37");
        } else if ("2".equals(joinPay)) {
            inDto.setSbscstPymnMthdCd("R");
        } else {
            inDto.setSbscstPymnMthdCd("I");
        }

        String bondFeePymnMthdCd = svcAppformRepositoryImpl.getMspCommonCodeEtc1("RCP2044", "BONDFEE");
        inDto.setBondPrsrFeePymnMthdCd(StringUtil.NVL(bondFeePymnMthdCd, ""));
        inDto.setSbscPrtlstRcvEmlAdrsNm(
            StringUtil.NVL(appformReqDto.getCstmrMail(), ""));

        log.info("[sendOsstOpenXmlService] request: resNo={}, osstOrdNo={}, custNo={}, tlphNo={}, iccId={}, blpymMthdCd={}, billAcntNoPresent={}",
            osstParam.get("resNo"), inDto.getOsstOrdNo(), inDto.getCustNo(),
            inDto.getTlphNo(), inDto.getIccId(), inDto.getBlpymMthdCd(),
            StringUtils.hasText(inDto.getBillAcntNo()));

        // 요금제 상품코드(inPrdcDto) 포함 — ASIS getXmlMessageOP0Prod와 동일한 방식
        List<Object> op0RequestList = new ArrayList<>();
        op0RequestList.add(inDto);
        String socCode = appformReqDto.getSocCode();
        if (StringUtils.hasText(socCode)) {
            MplatFormHC0InPrdcDtoRequest prdcDto = new MplatFormHC0InPrdcDtoRequest();
            prdcDto.setPrdcCd(socCode);
            prdcDto.setPrdcTypeCd("P");
            op0RequestList.add(prdcDto);
            log.info("[sendOsstOpenXmlService] inPrdcDto: socCode={}", socCode);
        }

        MspPrxSoapResponse response = msfMcpOsstPrxService.callXmlOsstService(
            op0RequestList, Constants.EVENT_CODE_REQ_OPEN, osstParam.get("resNo"));

        String responseXml = response == null ? null : response.rawXml();
        log.info("[sendOsstOpenXmlService] response: resNo={}, responseType={}, responseCode={}, length={}",
            osstParam.get("resNo"),
            response == null ? null : response.responseType(),
            response == null ? null : response.responseCode(),
            responseXml == null ? 0 : responseXml.length());

        if (!StringUtils.hasText(responseXml)) {
            throw new McpMplatFormException(ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
        }

        MSimpleOsstXmlVO osst = new MSimpleOsstXmlVO();
        osst.setResponseXml(responseXml);
        try {
            osst.toResponseParse();
        } catch (SelfServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new McpMplatFormException(e);
        }
        return osst;
    }

    /* 미사용
    private int requestOsstCount(McpRequestOsstDto mcpRequestOsstDto) {
        return svcAppformRepositoryImpl.requestOsstCount(mcpRequestOsstDto);
    }
    */

    private McpRequestOsstDto getRequestOsst(McpRequestOsstDto mcpRequestOsstDto) {
        McpRequestOsstDto result = svcAppformRepositoryImpl.getRequestOsst(mcpRequestOsstDto);
        log.debug("[getRequestOsst] result: resNo={}, prgrStatCd={}, found={}, resultCode={}",
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getMvnoOrdNo(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getPrgrStatCd(),
            result != null, result == null ? null : result.getRsltCd());
        return result;
    }

    private boolean insertMcpRequestOsst(McpRequestOsstDto mcpRequestOsstDto) {
        boolean inserted = svcAppformRepositoryImpl.insertMcpRequestOsst(mcpRequestOsstDto);
        log.debug("[insertMcpRequestOsst] result: resNo={}, prgrStatCd={}, tlphNo={}, inserted={}",
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getMvnoOrdNo(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getPrgrStatCd(),
            mcpRequestOsstDto == null ? null : mcpRequestOsstDto.getTlphNo(), inserted);
        return inserted;
    }

    private void insertDisPrmtApd(AppformReqDto dataSharingReqDto, String operType) {
        dataSharingReqDto.setEvntCd(operType);
        mspApiDirectRepository.query("/appform/insertDisPrmtApd", dataSharingReqDto, Integer.class);
    }

    private Map<String, String> chkRealPc2Result(String resNo, String contractNum) {
        // Excluded by analysis doc: additional PC2 polling/Y39 pre-schedule check is not migrated here.
        log.trace("chkRealPc2Result excluded: resNo={}, contractNum={}", resNo, contractNum);
        HashMap<String, String> result = new HashMap<>();
        result.put("RESULT_CODE", AJAX_SUCCESS);
        return result;
    }

    private int getModuTypeStepCnt(String moduType, String compType) {
        // Excluded by analysis doc: additional identity-auth step validation.
        log.trace("getModuTypeStepCnt excluded: moduType={}, compType={}", moduType, compType);
        return 1;
    }

    // Excluded for smart-form scope: certService.getCertInfo/vdlCertInfo certification comparison.

    private List<UsimMspRateDto> selectJoinUsimPriceNew(UsimBasDto usimBasDto) {
        Object result = mspApiDirectRepository.query("/storeUsim/joinUsimPriceNew", usimBasDto, List.class);
        if (!(result instanceof List<?> resultList)) {
            return null;
        }

        List<UsimMspRateDto> usimRateList = new ArrayList<>(resultList.size());
        for (Object item : resultList) {
            usimRateList.add(UsimMspRateDto.class.cast(item));
        }
        return usimRateList;
    }

    private MpSvcContIpinVO MoscSvcContService(String osstOrdNo) throws SocketTimeoutException {
        // Excluded by analysis doc: Y39 CI check is not migrated here.
        log.trace("MoscSvcContService excluded: osstOrdNo={}", osstOrdNo);
        MpSvcContIpinVO vo = new MpSvcContIpinVO();
        vo.setIpinCi("");
        vo.setSuccess(true);
        return vo;
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private MspSmsTemplateMstDto getMspSmsTemplateMst(int templateId) {
        // Excluded by analysis doc: SMS notification is not migrated here.
        log.trace("getMspSmsTemplateMst excluded: templateId={}", templateId);
        return null;
    }

    private String getClientIp() {
        return RequestUtils.getClientIp();
    }

    private String decryptTlphNo(String tlphNo) {
        if (!StringUtils.hasText(tlphNo)) {
            return tlphNo;
        }
        if (tlphNo.matches("\\d+")) {
            return tlphNo;
        }
        try {
            return KisaSeedUtils.decrypt(tlphNo);
        } catch (Exception e) {
            log.warn("[decryptTlphNo] KISA SEED 복호화 실패, null 처리: tlphNo={}", tlphNo);
            return null;
        }
    }

}
