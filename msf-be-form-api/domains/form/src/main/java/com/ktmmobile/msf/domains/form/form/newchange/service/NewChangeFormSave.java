package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.commons.common.datasource.msp.MspDataSourceConfig;
import com.ktmmobile.msf.commons.common.datasource.smartform.SmartFormDataSourceConfig;
import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.form.common.code.CstmrType;
import com.ktmmobile.msf.domains.form.common.code.ReqBuyType;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.repository.MspApiDirectRepository;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestDocDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestChangeVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestSaleinfoVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpUploadPhoneInfoVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDocVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfUploadPhoneInfoVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.BulkCorporateInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.BulkCorporateInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MpPreCheckRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MsfRequestRecord;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeAdditionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeResponse;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestAdditionWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestAgentWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestCstmrWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestDvcChgWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestMoveWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestNewChangeWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestOsstWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestReqWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestSaleinfoWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpUploadPhoneInfoWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.MsfRequestOsstWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeMpReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.ToMcpNewChangeReadMapper;
import com.ktmmobile.msf.domains.shared.form.common.generate.application.port.out.GenerateKeyRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewChangeFormSave {

    private final FormCommService formCommService;
    private final MpPreCheckService mpPreCheckService;

    private final ProductInfoService productInfoService;
    private final NewChangeValidCheckService newChangeValidCheckService;

    private final McpRequestOsstWriteMapper mcpRequestOsstWriteMapper;
    private final MsfRequestOsstWriteMapper msfRequestOsstWriteMapper;

    private final AgencyCacheReader agencyCacheReader;
    private final NewChangeWriteMapper newChangeWriteMapper;
    private final NewChangeReadMapper newChangeReadMapper;
    private final ToMcpNewChangeReadMapper toMcpNewChangeReadMapper;

    private final McpRequestNewChangeWriteMapper mcpRequestNewChangeWriteMapper;
    private final MspApiDirectRepository mspApiDirectRepository;

    private final McpRequestReadMapper mcpRequestReadMapper;
    private final McpRequestCstmrWriteMapper mcpRequestCstmrWriteMapper;
    private final McpRequestAgentWriteMapper mcpRequestAgentWriteMapper;
    private final McpRequestReqWriteMapper mcpRequestReqWriteMapper;
    private final McpRequestSaleinfoWriteMapper mcpRequestSaleinfoWriteMapper;
    private final McpRequestMoveWriteMapper mcpRequestMoveWriteMapper;
    private final McpRequestDvcChgWriteMapper mcpRequestDvcChgWriteMapper;
    private final McpRequestAdditionWriteMapper mcpRequestAdditionWriteMapper;
    private final NewChangeMpReadMapper newChangeMpReadMapper;
    private final McpUploadPhoneInfoWriteMapper mcpUploadPhoneInfoWriteMapper;
    private final McpRequestWriteMapper mcpRequestWriteMapper;

    private final FormCommReadMapper formCommReadMapper;

    private final GenerateKeyRepository generateKeyRepository;

    /**
     * 신청서 임시저장
     */
    @Transactional(transactionManager = MspDataSourceConfig.MSP_TX_MANAGER)
    @SuppressWarnings("PMD.EmptyControlStatement")
    public FormResponse<NewChangeResponse> saveNewChangeFormInfo(NewChangeInfoRequest request) {
        log.debug("saveNewChangeFormInfo >> requestKey(): {}, resNo: {}, tmpStepCd: {}",
            request.getRequestKey(),
            request.getResNo(),
            request.getTmpStepCd());
        NewChangeResponse newChangeResponse = new NewChangeResponse();
        //String rsltCd = "";
        //String rsltMsg = "";

        //RES_NO 중복체크 - 작성완료 후 새로 작성에도 동일한 RES_NO 를 갖고 있음으로 방어로직 추가. (프론트에도 초기화 처리는 했음)
        //boolean isValidResNo = false;
        //if (request.getResNo() != null) {
        //    isValidResNo = newChangeReadMapper.existsResNo(request.getResNo());
        //}

        //0. 신청서번호 존재여부 체크
        boolean isFirst = false; //최초 진입을 판단하기 위한 변수
        int newChangeFormCnt = 0;
        if (request.getRequestKey() == null || request.getResNo() == null) { //신청서 일련번호와 MP연동와 연동번호 값 체크
            if (request.getRequestKey() == null) {
                isFirst = true;
                //request.setRequestKey(formCommService.generateRequestKey()); //신청서번호(REQUEST_KEY) 생성
                request.setRequestKey(generateKeyRepository.getGeneratedRequestKey()); //2026.06.30 변경
            }

            log.debug("resNo: {}", request.getResNo());
            if (request.getResNo() == null) { //작성완료 복제 신청서에는 res_no 신규생성해서 저장함.
                request.setResNo(generateKeyRepository.getGeneratedResNo()); //2026.06.30 변경
            }

            //if (request.getResNo() == null || isValidResNo) { //중복일 경우 추가 2026.07.09 (프론트에 방어했으나 추가 방어처리)
            //    //if (request.getResNo() == null) { //중복일 경우 추가 2026.07.20 - 작성완료 복제 신청서에는 res_no 신규생성해서 저장함.
            //    request.setResNo(generateKeyRepository.getGeneratedResNo()); //2026.06.30 변경
            //}

        } else {
            //안면인증에 request_key 를 기 생성하여 처리하므로 request_key 를 보내는데 신청서에 데이타가 없는 경우 저장하기 위함.
            request.setPreCheck("Y"); //신청서 존재여부 체크 시 조건절로 쓰기 위한 변수
            newChangeFormCnt = this.getNewChangeFormCnt(request);
            if (newChangeFormCnt == 0) {
                isFirst = true;
            }

            //if (isValidResNo) { //중복일 경우 추가 2026.07.09 (프론트에 방어했으나 추가 방어처리)
            //    request.setResNo(generateKeyRepository.getGeneratedResNo()); //2026.06.30 변경
            //}
        }

        //1. 필수 기본값 체크
        //1-1 상품유형 (REQ_BUY_TYPE) , 가입유형 ( OPER_TYPE_CD ) 값 필수체크
        if ("".equals(request.getReqBuyTypeCd()) || "".equals(request.getOperTypeCd())) {
            return FormResponse.of(ResponseMessage.VALID_INPUT_EMPTY); //필수 입력값이 누락되었습니다.
        }
        //1-2 신청서 상태에 따라 신청서일련번호 확인
        if ("".equals(request.getTmpStepCd())) {
            return FormResponse.of(ResponseMessage.VALID_INPUT_EMPTY); //필수 입력값이 누락되었습니다.
        } else { //신청서 작성상태인데 신청서 일련번호 존재여부 체크
            if (Integer.parseInt(request.getTmpStepCd()) > 1 && "".equals(request.getRequestKey())) {
                return FormResponse.of(ResponseMessage.VALID_INPUT_EMPTY); //필수 입력값이 누락되었습니다.
            }
        }

        //2. 신청서 작성자, 신청서 임시저장 상태 검증
        request.setPreCheck("");
        boolean isValidStatus = this.checkNewChangeInfo(request);
        if (!isValidStatus) {
            //return FormResponse.of(ResponseMessage.NO_DATA);
        }

        //3. 신청서 입력값 자릿수 체크
        boolean isValidLength = newChangeValidCheckService.checkInputLength(request);
        if (!isValidLength) {
            //고객단계에서 임시 테스트 값이 넘어와서 체크되는것 때문에 주석. 추후 처리예정
            //return FormResponse.of(ResponseMessage.VALID_INPUT_NOT_CORRECT); //입력 정보 자릿수를 확인해 주세요.
        }

        //4. 신청서 유효성체크
        boolean isValidForm = newChangeValidCheckService.checkNewChangeForm(request);
        if (!isValidForm) {
            log.debug("★ 신청서 유효성체크에 걸렸습니다. 현재 일단 프론트에서 PASS 됩니다. ★");
            return FormResponse.of(ResponseMessage.VALID_INPUT_INVALID);
            //return FormResponse.of(ResponseMessage.VALID_INPUT_INVALID, "ㄱㄱㄱ", "");
        }

        //5. 신청서 저장을 위한 사용자 정보 DATA SET ( agencyCacheReader 활용 )
        newChangeValidCheckService.setAgentUserInfo(request);

        //6. 신청서 저장
        //KTM 스마트신청서 저장 START ---------------------------------------------------------------------------------
        request.setFirst(isFirst);
        try {
            this.setMsfSave(request);
        } catch (Exception e) {
            throw new SimpleDomainException("신청서 저장에 실패했습니다. [1]");
        }
        //boolean isSave = this.setMsfSave(request);
        //KTM 스마트신청서 저장 END -----------------------------------------------------------------------------------

        //7. 개통전 사전체크 호출
        //개통전 사전체크 호출 START ------------------------------------------------------------------------------
        //NewChangeRequest newChangeRequest = newChangeMpReadMapper.selectMsfPreCheckInfoRequest(request.getRequestKey());
        MpPreCheckRequest mpPreCheckRequest = new MpPreCheckRequest();
        mpPreCheckRequest.setRequestKey(request.getRequestKey()); //MSF_REQUEST.request_key
        mpPreCheckRequest.setResNo(request.getResNo()); //MSF_REQUEST.RES_NO
        mpPreCheckRequest.setKnoteScanId(request.getKnoteScanId()); //KNOTE 서식지 아이디
        mpPreCheckRequest.setFathTransacId(request.getFathTransacId()); //안면인증 트랜잭션 아이디
        mpPreCheckRequest.setOperTypeCd(request.getOperTypeCd()); //업무유형
        mpPreCheckRequest.setTmpStepCd(request.getTmpStepCd());
        mpPreCheckRequest.setRequestPreCheck(request.getRequestPreCheck()); //사전체크 요청일 경우 Y
        //mpPreCheckRequest.setAgentCd(request.getAgentCd()); //Header 값으로 보낼 관리자할 대리점코드 (요건 변환해야해)
        log.debug(
            "개통전 사전체크 호출 input >> RequestKey: {}, ResNo: {}, KnoteScanId: {}, FathTransacId: {}, OperTypeCd: {}, TmpStepCd: {}, RequestPreCheck: {}",
            request.getRequestKey(),
            request.getResNo(),
            request.getKnoteScanId(),
            request.getFathTransacId(),
            request.getOperTypeCd(),
            request.getTmpStepCd(),
            request.getRequestPreCheck());

        //개통 전 사전체크 호출
        String preCheckCd = "";
        String preCheckMsg = "";
        if ("Y".equals(request.getRequestPreCheck())) { //개통전 사전체크 요청여부에 따라 진행
            Map<String, String> rtnMapPreCheck = mpPreCheckService.getNewChangeMpPreCheck(mpPreCheckRequest);
            log.debug("개통전 사전체크 호출 output >> rsltCd: {}, rsltMsg: {}", rtnMapPreCheck.get("rsltCd"), rtnMapPreCheck.get("rsltMsg"));
            if (rtnMapPreCheck != null) {
                preCheckCd = rtnMapPreCheck.get("rsltCd");
                preCheckMsg = rtnMapPreCheck.get("rsltMsg");
            }
        }
        log.debug("개통전 사전체크 호출 최종 :: output >> preCheckCd: {}, preCheckMsg: {}", preCheckCd, preCheckMsg);
        newChangeResponse.setPreCheckCd(preCheckCd);
        newChangeResponse.setPreCheckMsg(preCheckMsg);
        //개통전 사전체크 END --------------------------------------------------------------------------------

        //8. 고객포탈에 신청서 저장 (MCP_REQUEST)
        //KTM MOBILE 테이블에 저장 START --------------------------------------------------------------------
        //if (isFirst) {
        //최초 진입시에만 저장하며 신규가입일 때만 예약번호 조회 MP 연동 시 해당 테이블에 데이타 존재해야하므로 처리
        //임시저장 또는 작성완료 복사하기로 들어올 경우에 대한 처리는 테스트를 통해 처리해야함.
        //MCP_REQUEST : 예약번호조회(NU1) 에서 MCP_REQUEST 및 MCP_REQUEST_OSST 를 조회
        McpRequestVo mcpRequestVo = toMcpNewChangeReadMapper.selectMsfRequestTempToMcp(request.getRequestKey());
        //고객유형 변환처리
        if (("NA".equals(mcpRequestVo.getCstmrType()) || "FN".equals(mcpRequestVo.getCstmrType())) && StringUtils.hasText(request.getCstmrPrivateBizNo())) {
            mcpRequestVo.setCstmrType("PP"); //개인사업자로 변경하여 고객포탈에 저장
        }
        if ("FM".equals(mcpRequestVo.getCstmrType()) || "GO".equals(mcpRequestVo.getCstmrType())) {
            mcpRequestVo.setCstmrType("NE");
        }
        log.debug("MCP 테이블에 최초 저장을 위해 스마트 저장 후 데이타 추출하여 처리 : {}", mcpRequestVo.getProdId());

        try {
            /*** mcp 데이터 저장 ***/
            if (mcpRequestVo != null && mcpRequestVo.getRequestKey() != null) {
                int mcpCnt = mcpRequestReadMapper.selectMcpRequest(request.getRequestKey());
                if (mcpCnt > 0) {
                    mcpRequestNewChangeWriteMapper.updateMcpRequest(mcpRequestVo);
                } else {
                    mcpRequestNewChangeWriteMapper.insertMcpRequest(mcpRequestVo);
                }
            }
            //if (mcpRequestVo != null && mcpRequestVo.getRequestKey() != null) {
            //    mcpRequestNewChangeWriteMapper.insertMcpRequest(mcpRequestVo);
            //}
        } catch (Exception e) {
            /** msf 데이터 삭제 ***/
            this.setMsfDeleteTemp(request.getRequestKey());
            throw e;
        }
        //}

        //eSIM 신청서의 경우 MSF_UPLOAD_PHONE_INFO ~> MCP_UPLOAD_PHONE_INFO 로 저장
        if ("09".equals(request.getUsimKindsCd()) && request.getUploadPhoneSrlNo() != null && request.getUploadPhoneSrlNo() > 0L) {
            log.debug("eSIM 신청서 >> UsimKindsCd : {}, UploadPhoneSrlNo: {}", request.getUsimKindsCd(), request.getUploadPhoneSrlNo());
            McpUploadPhoneInfoVo mcpUploadPhoneInfoVo = null;

            //1. MSF 저장된 eid , imei1 , imei2 조회
            MsfUploadPhoneInfoVo msfUploadPhoneInfoVo = newChangeReadMapper.selectMsfUploadPhoneInfo(request.getUploadPhoneSrlNo());
            log.debug("MSF 저장된 eid , imei1 , imei2 조회 >> Eid: {}, Imei1: {}, Imei2: {}",
                msfUploadPhoneInfoVo.getEid(),
                msfUploadPhoneInfoVo.getImei1(),
                msfUploadPhoneInfoVo.getImei2());

            //2. 조회된 eid, imei1, imei2 존재여부 확인
            if (msfUploadPhoneInfoVo != null) {
                mcpUploadPhoneInfoVo = formCommReadMapper.selectMcpUploadPhoneInfo(msfUploadPhoneInfoVo);
            }

            //3. MCP 연동 (eSIM 등록 데이타 없는 경우)
            if (mcpUploadPhoneInfoVo == null) {
                try {
                    mcpUploadPhoneInfoVo = toMcpNewChangeReadMapper.selectMsfUploadPhoneInfoToMcp(request.getUploadPhoneSrlNo());
                    mcpUploadPhoneInfoVo.setReqModelName(mcpUploadPhoneInfoVo.getReqModelNm());
                    mcpUploadPhoneInfoVo.setSysRdate(Timestamp.valueOf(mcpUploadPhoneInfoVo.getCretDt()));
                    mcpUploadPhoneInfoWriteMapper.insertMcpUploadPhoneInfo(mcpUploadPhoneInfoVo);
                } catch (Exception e) {
                    //throw e;
                    log.debug("eSIM 신청서 Error : {}", e.getMessage());
                }
            }
        }
        //KT MMOBILE 테이블에 저장 END ----------------------------------------------------------------------

        //저장 성공 후 신청서번호 Return
        newChangeResponse.setRequestKey(request.getRequestKey());
        newChangeResponse.setResNo(request.getResNo());
        return FormResponse.of(ResponseMessage.SUCCESS, newChangeResponse); //성공이 아닌데도 지나감. 추후 수정이 필요함.
    }

    /**
     * 신청서 작성자, 신청서 임시저장 상태 유효성 검증
     */
    public boolean checkNewChangeInfo(NewChangeInfoRequest request) {
        NewChangeRequest newChangeRequest = new NewChangeRequest();
        boolean isValid = true;

        //임시저장 진입일 경우 신청서의 아이디와 세션의 아이디 일치 여부 확인
        //세션정보의 사용자, 대리점, 판매점조직 코드비교하여 정상여부 판단
        Long requestKey = request.getRequestKey();
        if (requestKey != null) {
            int newChangeFormCnt = this.getNewChangeFormCnt(request);
            if (newChangeFormCnt == 0) {
                isValid = false;
            }
        }

        //신청서 상태 확인
        String tmpStepCd = request.getTmpStepCd();
        if (!StringUtils.hasText(tmpStepCd)) { //
            isValid = false;
        } else {
            int tmpStepCdInt = Integer.parseInt(tmpStepCd);
            log.debug("신청서 상태 확인 : tmpStepCdInt = " + tmpStepCdInt);
            if (tmpStepCdInt < 1 || tmpStepCdInt > 3) {
                isValid = false;
            } else {
                if (tmpStepCdInt > 1) {
                    tmpStepCd = Integer.toString(tmpStepCdInt - 1);
                    newChangeRequest.setTmpStepCd(tmpStepCd);
                    int newChangeFormCnt = this.getNewChangeFormCnt(request);
                    if (newChangeFormCnt == 0) {
                        isValid = false;
                    }
                }
            }
        }

        return isValid;
    }

    /**
     * 신청서 저장 여부 판단 (로그인 사용자 정보)
     */
    public int getNewChangeFormCnt(NewChangeInfoRequest request) {
        NewChangeRequest newChangeRequest = new NewChangeRequest();
        newChangeRequest.setRequestKey(request.getRequestKey()); //신청서 일련번호
        newChangeRequest.setPreCheck(request.getPreCheck()); //신청서 일련번호만 존재하는지 확인하기 위한 변수
        newChangeRequest.setTmpStepCd("");
        newChangeRequest.setManagerCd(AuthenticationUtils.getUser().getUserId());
        newChangeRequest.setShopCd(AuthenticationUtils.getShopCode());

        int newChangeFormCnt = newChangeReadMapper.checkNewChangeFormUser(newChangeRequest);
        return newChangeFormCnt;
    }

    /**
     * 신청서 유효성 확인 (2026.07.29)
     */
    public int getNewChangeForm(NewChangeInfoRequest request) {
        NewChangeRequest newChangeRequest = new NewChangeRequest();
        newChangeRequest.setRequestKey(request.getRequestKey()); //신청서 일련번호
        newChangeRequest.setTmpStepCd(request.getTmpStepCd());
        newChangeRequest.setManagerCd(AuthenticationUtils.getUser().getUserId());

        int newChangeFormCnt = newChangeReadMapper.checkNewChangeForm(newChangeRequest);
        return newChangeFormCnt;
    }

    /**
     * MSF 저장
     */
    @BusinessContextBoundary
    @Transactional(transactionManager = SmartFormDataSourceConfig.SMARTFORM_TX_MANAGER)
    private boolean setMsfSave(NewChangeInfoRequest request) {
        BusinessContextHolder.setParentScanId(request.getParentScanId()); //외부연동(로시스) 이력을 위해 추가됨. @BusinessContextBoundary 세트임.

        log.debug("MSF 저장 시작 ====================================================== ");

        boolean isSave = true;
        request.setOnOffTypeCd("1"); //RCP0007 : 1 (오프라인)
        request.setSoCd("M"); //사업자코드 : 코드관리(M포탈) (I : KTIS, M : M모바일)
        request.setOpenTypeCd("99"); //개통유형 : 휴대폰 - 컬럼 삭제해도 될 것 같은데
        if ("09".equals(request.getUsimKindsCd())) {
            request.setOpenTypeCd("09"); //개통유형 : eSIM
        }

        //고객포탈기준 : SHOP_USM_ID 샵ID (로그인한 사용자의 아이디로 저장하고 있음.)
        request.setShopUsmId(AuthenticationUtils.getUser().getUserId());

        //고객 Cstmr_Native_Rrn 항목은 내국인성인, 내국인미성년자에 한해서만 저장 (2026.06.29)
        if (!request.getCstmrTypeCd().equals(CstmrType.NATIVE_ADULT.getCode()) && !request.getCstmrTypeCd()
            .equals(CstmrType.NATIVE_MINOR.getCode())) {
            request.setCstmrNativeRrn(""); //내국인 성인 및 내국인 미성년자의 식별번호 저장 컬럼 초기화 처리함.
        }

        //미성년자의 경우 법정대리인의 동의 및 인증방식 완료 처리 - 20260609
        if (request.getCstmrTypeCd().equals(CstmrType.NATIVE_MINOR.getCode()) || request.getCstmrTypeCd()
            .equals(CstmrType.FOREIGN_MINOR.getCode())) {
            request.setMinorAgentAgrmYn("Y");
            request.setMinorAgentSelfInqryAgrmYn("Y");
            //request.setMinorAgentSelfCertTypeCd("MOBILE");
        }

        //미성년자의 경우 BLCK_APP_DIV_CD 항목에 고정값으로 "15" 추가 (고객포탈) - AppCd 값을 AppformSvcImpl.java 에서 고정해두고 있음.
        if (request.getCstmrTypeCd().equals(CstmrType.NATIVE_MINOR.getCode()) || request.getCstmrTypeCd()
            .equals(CstmrType.FOREIGN_MINOR.getCode())) {
            request.setBlckAppDivCd("15");
        }

        //사업자번호 (내국인,외국인,법인,공공기관) 저장
        if (request.getCstmrTypeCd().equals(CstmrType.NATIVE_ADULT.getCode()) || request.getCstmrTypeCd().equals(CstmrType.FOREIGN_ADULT.getCode())) {
            request.setCstmrJuridicalBizNo("");
        } else if (request.getCstmrTypeCd().equals(CstmrType.JURIDICAL_PERSON.getCode()) || request.getCstmrTypeCd()
            .equals(CstmrType.GOVERNMENT_ORGANIZATION.getCode())) {
            request.setCstmrPrivateBizNo("");
        }

        //외국인 성인 및 외국인 미성년자의 체류기간은 2026-06-30 형식으로 넘어오면 "-" 제거 처리
        if (request.getCstmrTypeCd().equals(CstmrType.FOREIGN_ADULT.getCode()) || request.getCstmrTypeCd()
            .equals(CstmrType.FOREIGN_MINOR.getCode())) {
            request.setCstmrForeignerVdateStartDate(request.getCstmrForeignerVdateStartDate().replace("-", ""));
            request.setCstmrForeignerVdateEndDate(request.getCstmrForeignerVdateEndDate().replace("-", ""));
        }

        //법인 및 공공기관의 위임대리인 값 있으면 RPAD 처리
        if ((request.getCstmrTypeCd().equals(CstmrType.JURIDICAL_PERSON.getCode()) || request.getCstmrTypeCd()
            .equals(CstmrType.GOVERNMENT_ORGANIZATION.getCode())) && (StringUtils.hasText(request.getJrdclAgentRrn()))) {
            log.debug("request.getJrdclAgentRrn >> " + request.getJrdclAgentRrn());
            request.setJrdclAgentRrn(String.format("%-13s", request.getJrdclAgentRrn()).replace(" ", "0"));
        }


        //고객연락처 - 화면에 없는 연락처
        String cstmrTelNo = request.getCstmrTelFnNo() + request.getCstmrTelMnNo() + request.getCstmrTelRnNo();
        String cstmrMobileNo = request.getCstmrMobileFnNo() + request.getCstmrMobileMnNo() + request.getCstmrMobileRnNo();
        //String cstmrReceiveTelNo = request.getCstmrReceiveTelFnNo() + request.getCstmrReceiveTelNmNo() + request.getCstmrReceiveTelRnNo();
        if (cstmrTelNo.length() >= 9) {
            request.setCstmrReceiveTelFnNo(request.getCstmrTelFnNo());
            request.setCstmrReceiveTelNmNo(request.getCstmrTelMnNo());
            request.setCstmrReceiveTelRnNo(request.getCstmrTelRnNo());
        } else if (cstmrMobileNo.length() >= 10) {
            request.setCstmrReceiveTelFnNo(request.getCstmrMobileFnNo());
            request.setCstmrReceiveTelNmNo(request.getCstmrMobileMnNo());
            request.setCstmrReceiveTelRnNo(request.getCstmrMobileRnNo());
        }

        //신규가입:예약번호, 번호이동:사전체크번호, 기기변경:인증번호가 단계별로 달라서 데이타가 안 넘어올 경우 초기화처리
        if (request.getOpenNo().length() < 10) {
            request.setOpenNo("");
        }

        //희망번호 저장 - MSF_REQUEST.OPEN_NO 컬럼에 저장 (2026.06.23)
        request.setReqWantFnNo("");
        request.setReqWantMnNo("");
        request.setReqWantRnNo("");

        //상품 USIM + SIM정보 eSIM 선택한 경우 중고폰으로 저장 : 2026.07.24 요청수정사항
        if (ReqBuyType.USIM.getCode().equals(request.getReqBuyTypeCd()) && "09".equals(request.getUsimKindsCd())) {
            request.setRecycleYn("Y"); //USIM + eSIM 은 중고폰으로 저장
        }

        //유심단독 신청서의 경우 처리
        if ("UU".equals(request.getReqBuyTypeCd())) {
            if (request.getEnggMnthCnt() > 0) {
                request.setSprtTypeCd("SM"); //약정있는 경우 심플할인으로 설정 (스마트는 현재 USIM 작성은 약정이 없고 할인유형 선택없음)
            }

            request.setModelSalePolicyCd(""); //판매정책
            request.setModelId(""); //모델ID
            request.setModelMonthly("0"); //단말할부개월수
            request.setModelInstamt(0L); //단말할부원금
            request.setModelPrice(0L); //단말출고가격
            request.setModelPriceVat(0L); //단말출고가격부가세
            request.setModelDiscount1(0L); //제조사지원금
            request.setModelDiscount3(0L); //대리점보조금
            request.setModelSprt(0L); //공시지원금
            request.setRealMdlInstamt(0L); //실제 단말할부원금(vat포함)
            request.setHndsetSalePrice(0L); //단말기 판매가격
            request.setProdId(""); //상품ID (KT코드 - 5033 ) - MSF_REQUEST
            request.setProdNm(""); //상품명 ( 갤럭시 A17 LTE ) - MSF_REQUEST
            request.setReqModelNm(""); //모델명 ( SM-A175NK ) - MSF_REQUEST

            //USIM 일련번호로 USIM MODEL NAME 구하기
            //if (StringUtils.hasText(request.getUsimKindsCd()) && !"06".equals(request.getUsimKindsCd()) && !"".equals(request.getUsimKindsCd())) {
            //    String reqUsimNm = productInfoService.getUsimModelNm(request.getReqUsimSn());
            //    request.setReqUsimNm(reqUsimNm);
            //}
        }

        //USIM 일련번호로 USIM MODEL NAME 구하기 - 상품유형 관련없이 가능하도록 이동 ( 2026.07.08 )
        if (StringUtils.hasText(request.getUsimKindsCd()) && !"06".equals(request.getUsimKindsCd()) && !"".equals(request.getUsimKindsCd())) {
            String reqUsimNm = productInfoService.getUsimModelNm(request.getReqUsimSn());
            if (StringUtils.hasText(reqUsimNm)) {
                request.setReqUsimNm(reqUsimNm);
            }
        }

        //요금납부방법 ( D, C, 0 ) - 유형에 따라 프론트에서 넘어온 값 초기화
        String reqPayTypeCd = request.getReqPayTypeCd();
        if ("D".equals(reqPayTypeCd)) { //은행
            request.setReqCardNm("");
            request.setReqCardRrn("");
            request.setReqCardCompanyCd("");
            request.setReqCardNo("");
            request.setReqCardYy("");
            request.setReqCardMm("");
        } else if ("C".equals(reqPayTypeCd)) { //카드
            request.setReqBankCd("");
            request.setReqAccountNm("");
            request.setReqAccountRrn("");
            request.setReqAccountNo("");
        } else if ("0".equals(reqPayTypeCd)) { //통합청구
            request.setReqBankCd("");
            request.setReqAccountNm("");
            request.setReqAccountRrn("");
            request.setReqAccountRelTypeCd("");
            request.setReqAccountNo("");
            request.setReqCardNm("");
            request.setReqCardRrn("");
            request.setReqCardCompanyCd("");
            request.setReqCardNo("");
            request.setReqCardYy("");
            request.setReqCardMm("");
        }

        //본인납부 초기화 처리 및 본인납부 고객유형별 정보 세팅
        if ("N".equals(request.getOthersPaymentYn())) {
            //타인납부 항목들 초기화
            request.setReqAccountRelTypeCd("");
            request.setOthersPaymentTelFnNo("");
            request.setOthersPaymentTelMnNo("");
            request.setOthersPaymentTelRnNo("");
            request.setOthersPaymentReqNm("");
            //request.setOthersPaymentAgrYn("");
            //request.setPrntsBillNo("");

            //은행납부
            String reqAccountNm = "";
            String reqAccountRrn = "";
            //String reqAccountRelTypeCd = "";
            if ("D".equals(request.getReqPayTypeCd())) { //은행
                reqAccountRrn = request.getReqAccountRrn();
                if ("NA".equals(request.getCstmrTypeCd()) || "NM".equals(request.getCstmrTypeCd())) {
                    reqAccountNm = request.getCstmrNm();
                    //reqAccountRrn = request.getCstmrNativeRrn();
                } else if ("FN".equals(request.getCstmrTypeCd()) || "FM".equals(request.getCstmrTypeCd())) {
                    reqAccountNm = request.getCstmrNm();
                    //reqAccountRrn = request.getCstmrForeignerRrn();
                } else {
                    reqAccountNm = request.getCstmrJuridicalCname();
                    //reqAccountRrn = request.getCstmrJuridicalRrn();
                }
            }

            //카드납부
            String reqCardNm = "";
            String reqCardRrn = "";
            if ("C".equals(request.getReqPayTypeCd())) { //카드
                reqCardRrn = request.getReqCardRrn();
                if ("NA".equals(request.getCstmrTypeCd()) || "NM".equals(request.getCstmrTypeCd())) {
                    reqCardNm = request.getCstmrNm();
                    //reqCardRrn = request.getCstmrNativeRrn();
                } else if ("FN".equals(request.getCstmrTypeCd()) || "FM".equals(request.getCstmrTypeCd())) {
                    reqCardNm = request.getCstmrNm();
                    //reqCardRrn = request.getCstmrForeignerRrn();
                } else {
                    reqCardNm = request.getCstmrJuridicalCname();
                    //reqCardRrn = request.getCstmrJuridicalRrn();
                }
            }
            request.setReqAccountNm(reqAccountNm);
            request.setReqAccountRrn(reqAccountRrn);
            request.setReqCardNm(reqCardNm);
            request.setReqCardRrn(reqCardRrn);
        } else {
            request.setPrntsBillNo("");
            request.setOthersPaymentAgrYn("Y"); //타인납부 선택한 경우 타인납부 동의 Y 처리
        }

        //단말보험
        if (StringUtils.hasText(request.getInsrProdCd())) {
            request.setClauseInsrProdYn("Y"); //단말보험 코드가 넘어오는 경우 단말보험가입동의 Y 처리
        }

        //명세서 - 유효성체크
        //request.setCstmrBillSendTypeCd("");
        //request.setCstmrEmailAdr("");

        //eSIM
        if ("09".equals(request.getUsimKindsCd())) {
            //고객포탈 기준 eSIM 의 경우 셀프개통 이슈로 인해 강제 세팅하는 부분이 있어 추가함.
            //스마트의 경우 화면에서 받는 부분은 없는 것으로 알고 있음.
            request.setPhonePaymentYn("N"); //휴대폰결제이용여부
        }

        //단말 , 요금 관련 금액 설정 START --------------------------------------------------------------------
        log.debug("modelId: {}, socCode: {}, modelSalePolicyCd: {}, sprtTypeCd: {}, modelMonthly: {}, enggMnthCnt: {}, usimKindsCd: {}"
            , request.getModelId(), request.getSocCode()
            , request.getModelSalePolicyCd(), request.getSprtTypeCd()
            , request.getModelMonthly(), request.getEnggMnthCnt(), request.getUsimKindsCd());

        //약정기간이 없는 경우 할인유형 빈값처리 (2026.07.10)
        if (StringUtils.hasText(request.getSprtTypeCd()) && (request.getEnggMnthCnt() == 0 || !StringUtils.hasText(request.getEnggMnthCnt()
            .toString()))) {
            request.setSprtTypeCd("");
        }

        MspSaleSubsdMstRequest mspSaleSubsdMstRequest = new MspSaleSubsdMstRequest();
        MspSaleSubsdMstResponse mspSaleSubsdMstResponse = new MspSaleSubsdMstResponse();
        mspSaleSubsdMstRequest.setReqBuyTypeCd(request.getReqBuyTypeCd()); //구매유형
        mspSaleSubsdMstRequest.setOperTypeCd(request.getOperTypeCd()); //가입유형
        mspSaleSubsdMstRequest.setModelId(request.getModelId()); //단말코드
        mspSaleSubsdMstRequest.setRateCd(request.getSocCode()); //요금제코드
        mspSaleSubsdMstRequest.setModelMonthly(request.getModelMonthly()); //단말할부기간
        mspSaleSubsdMstRequest.setAgrmTrm(request.getEnggMnthCnt().toString()); //요금약정기간
        mspSaleSubsdMstRequest.setDataType(StringUtil.NVL(request.getDataType(), "LTE")); //데이타유형 : 3G / LTE / 5G / LTE5G >> 기본값 LTE 처리
        mspSaleSubsdMstRequest.setPrdtSctnCd(StringUtil.NVL(request.getPrdtSctnCd(), "LTE")); //데이타유형 : 3G / LTE / 5G / LTE5G >> 기본값 LTE 처리
        mspSaleSubsdMstRequest.setSalePlcyCd(request.getModelSalePolicyCd()); //판매정책코드
        mspSaleSubsdMstRequest.setSprtTp(request.getSprtTypeCd()); //할인유형
        mspSaleSubsdMstRequest.setAgentCd(request.getCntpntShopCd()); //대리점코드?
        mspSaleSubsdMstRequest.setUsimKindsCd(request.getUsimKindsCd()); //유심종류
        mspSaleSubsdMstRequest.setUsimPriceTypeCd(request.getUsimPriceTypeCd());
        mspSaleSubsdMstRequest.setUsimPayMthdCd(request.getUsimPayMthdCd()); //
        mspSaleSubsdMstRequest.setJoinPayMthdCd(request.getJoinPayMthdCd()); //선택한 가입비

        log.debug(
            "setMsfSave : >> request.getCntpntShopCd(): {}, request.getReqBuyTypeCd(): {}, request.getOperTypeCd(): {}, request.getUsimKindsCd(): {}, request.getUsimPriceTypeCd(): {}, request.getUsimPayMthdCd(): {}",
            request.getCntpntShopCd(),
            request.getReqBuyTypeCd(),
            request.getOperTypeCd(),
            request.getUsimKindsCd(),
            request.getUsimPriceTypeCd(),
            request.getUsimPayMthdCd());

        //단말, 요금, 가입비, 유심비 조회
        mspSaleSubsdMstResponse = productInfoService.getMspSalePriceInfo(mspSaleSubsdMstRequest);
        //2026.07.14 -------------------------------------
        if (mspSaleSubsdMstResponse != null) {
            log.debug(
                "REQUEST >> sprtTypeCd: {}, modelId: {}, modelMonthly: {}, enggMnthCnt: {}",
                request.getSprtTypeCd(),
                request.getModelId(),
                request.getModelMonthly(),
                request.getEnggMnthCnt());

            log.debug(
                "@JOIN & SIM >> joinPrice:{}, joinPriceTypeCd:{}, joinPayMthdCd:{}, usimPrice: {}, usimPayMthdCd: {}, usimPriceTypeCd: {}",
                mspSaleSubsdMstResponse.getJoinPrice(),
                mspSaleSubsdMstResponse.getJoinPriceTypeCd(),
                mspSaleSubsdMstResponse.getJoinPayMthdCd(),
                mspSaleSubsdMstResponse.getUsimPrice(),
                mspSaleSubsdMstResponse.getUsimPayMthdCd(),
                mspSaleSubsdMstResponse.getUsimPriceTypeCd()
            );

            log.debug(
                "@MODEL & PRICE >> getHndstAmt: {}, getSubsdAmt: {}, getInstAmt: {}, getInstCmsn: {}, getAgncySubsdAmt: {}, getSprtTp: {}, getBaseAmt: {}, getDcAmt: {}, getAddDcAmt: {}",
                mspSaleSubsdMstResponse.getHndstAmt(),
                mspSaleSubsdMstResponse.getSubsdAmt(),
                mspSaleSubsdMstResponse.getInstAmt(),
                mspSaleSubsdMstResponse.getInstCmsn(),
                mspSaleSubsdMstResponse.getAgncySubsdAmt(),
                mspSaleSubsdMstResponse.getSprtTp(),
                mspSaleSubsdMstResponse.getBaseAmt(),
                mspSaleSubsdMstResponse.getDcAmt(),
                mspSaleSubsdMstResponse.getAddDcAmt());

            //단말 및 요금
            request.setModelPrice(this.getStringToLong(mspSaleSubsdMstResponse.getHndstAmt())); //단말-출고가
            request.setHndsetSalePrice(this.getStringToLong(mspSaleSubsdMstResponse.getHndstAmt())); //단말기 판매가격
            request.setModelSprt(this.getStringToLong(mspSaleSubsdMstResponse.getSubsdAmt())); //공시지원금(vat포함)
            request.setModelDiscount3(this.getStringToLong(mspSaleSubsdMstResponse.getAgncySubsdAmt())); //대리점보조금
            request.setModelInstamt(this.getStringToLong(mspSaleSubsdMstResponse.getInstAmt())); //단말할부원금
            request.setRealMdlInstamt(this.getStringToLong(mspSaleSubsdMstResponse.getInstAmt())); //실제 단말할부원금
            request.setSocBaseChrgAmt(this.getStringToLong(mspSaleSubsdMstResponse.getBaseAmt())); //요금 - 기본료
            request.setDcAmt(this.getStringToLong(mspSaleSubsdMstResponse.getDcAmt())); //요금 - 기본할인금액
            request.setAddDcAmt(this.getStringToLong(mspSaleSubsdMstResponse.getAddDcAmt())); //요금 - 추가할인금액

            //가입비
            request.setJoinPrice(this.getStringToLong(mspSaleSubsdMstResponse.getJoinPrice())); //가입비
            request.setJoinPayMthdCd(mspSaleSubsdMstResponse.getJoinPayMthdCd()); //가입비 납부방법코드 (1 : 면제 , 2 : 일시납, 3 : 3개월분납))
            request.setJoinPriceTypeCd(mspSaleSubsdMstResponse.getJoinPriceTypeCd()); //가입비 납부유형코드 (R:완납, I:분납 , P:면제)

            //중복설정인데 합쳐야할 것 같은데
            String usimKindsCd = request.getUsimKindsCd(); //유심종류
            if ("".equals(usimKindsCd)) {
                usimKindsCd = "06"; // RCP2035 기준코드
            } else if ("01".equals(request.getUsimKindsCd())) { //일반유심 - 스마트 화면에서 값
                if ("LTE".equals(request.getPrdtSctnCd())) {
                    usimKindsCd = "02"; // RCP2035 기준코드
                } else {
                    usimKindsCd = "07"; // RCP2035 기준코드
                }
            } else if ("02".equals(request.getUsimKindsCd())) { //NFC유심 - 스마트 화면에서 값
                usimKindsCd = "08"; // RCP2035 기준코드
            } else if ("09".equals(request.getUsimKindsCd())) { //eSIM - 스마트 화면에서 값
                usimKindsCd = "09"; // RCP2035 기준코드
            }
            request.setUsimKindsCd(usimKindsCd); //유심종류

            //유심비
            if ("09".equals(usimKindsCd)) { //eSIM 예외처리
                request.setUsimPrice(this.getStringToLong(mspSaleSubsdMstResponse.getSimPrice())); //유심가격
                request.setUsimPayMthdCd(mspSaleSubsdMstResponse.getUsimPayMthdCd());
                request.setUsimPriceTypeCd(mspSaleSubsdMstResponse.getUsimPriceTypeCd());
                //request.setUsimPayMthdCd("3"); //유심납부방법
                //request.setUsimPriceTypeCd("B"); //유심납부유형
                //받아온 값으로 일단 저장. 확인필요
            } else {
                request.setUsimPrice(this.getStringToLong(mspSaleSubsdMstResponse.getUsimPrice())); //유심가격
                request.setUsimPayMthdCd(mspSaleSubsdMstResponse.getUsimPayMthdCd()); //유심납부방법 : R (즉납)  B (후청구)  N (비구매) >> MCP 저장은 1(일시납), 3(분납), 0(면제) 으로 변환하여 연동
                request.setUsimPriceTypeCd(mspSaleSubsdMstResponse.getUsimPriceTypeCd()); //유심납부유형 : B (정기)  I (즉납)
            }
            //USIM_PRICE_TYPE_CD	유심납부유형코드	"o 코드관리(M포탈) (B : 정기, I : 즉납)
            //MCP_CODE : CODE_SYS = 'NUT'
            //NUT R 즉시납부
            //NUT B 후청구
            //NUT N 비구매"
            //USIM_PAY_MTHD_CD	유심비납부방법코드	o 코드관리(M포탈) (0 : 면제, 1 : 일시납, 2 : 분납(3개월))
        }
        //단말 , 요금 관련 금액 설정 END ----------------------------------------------------------------------

        //대량 개통 법인 START ------------------------------------------------------------------------------
        if (request.getVolumeMobileNoQnty() != null && request.getVolumeMobileNoQnty() > 0) {
            long volumeMobileNoQnty = 0L;
            if (volumeMobileNoQnty > 0) {
                BulkCorporateInfoResponse bulkCorporateInfoResponse = new BulkCorporateInfoResponse();
                BulkCorporateInfoRequest bulkCorporateInfoRequest = new BulkCorporateInfoRequest();
                bulkCorporateInfoRequest.setCstmrTypeCd(request.getCstmrTypeCd());
                bulkCorporateInfoRequest.setOperTypeCd(request.getOperTypeCd());
                bulkCorporateInfoRequest.setAgentCd(request.getAgentCd());
                bulkCorporateInfoRequest.setCpntId(AuthenticationUtils.getUser().getUserId());
                bulkCorporateInfoResponse = newChangeValidCheckService.getBulkCorporateOpenInfo(bulkCorporateInfoRequest);
                if (bulkCorporateInfoResponse != null && "Y".equals(bulkCorporateInfoResponse.getCanBulkCorporateConditionYn())) {
                    request.setVolumeRepMobileNoYn("Y");
                }
            }
        }
        //대량 개통 법인 END --------------------------------------------------------------------------------

        //가입신청 기변사유정보 설정 START ---------------------------------------------------------------------
        //고객포탈은 일관되게 insert 쿼리에 하드코딩되어있어서 아래와 같이 동일한 값이 들어감.
        //select dvc_chg_type from MCP_REQUEST_DVC_CHG WHERE dvc_chg_type IS NOT NULL GROUP BY dvc_chg_type; --10
        //select dvc_chg_rsn_cd from MCP_REQUEST_DVC_CHG WHERE dvc_chg_rsn_cd IS NOT NULL GROUP BY dvc_chg_rsn_cd; --10
        //select dvc_chg_rsn_dtl_cd from MCP_REQUEST_DVC_CHG WHERE dvc_chg_rsn_dtl_cd IS NOT NULL GROUP BY dvc_chg_rsn_dtl_cd; --03
        //MsfRequestDvcChgVo msfRequestDvcChgVo = new MsfRequestDvcChgVo();
        //msfRequestDvcChgVo.setDvcChgTypeCd("10"); //기변유형코드
        //msfRequestDvcChgVo.setDvcChgRsnCd("10"); //기변사유코드
        //msfRequestDvcChgVo.setDvcChgRsnDtlCd("03"); //기변상세사유코드
        //INSTAMT_PAY_MTHD_CD :: 2026.03.22 기능 삭제
        request.setDvcChgTypeCd("10"); //기변유형코드
        request.setDvcChgRsnCd("10"); //기변사유코드
        request.setDvcChgRsnDtlCd("03"); //기변상세사유코드
        //가입신청 기변사유정보 설정 END -----------------------------------------------------------------------

        //구비서류 request & delete : 저장 전 처리
        StringBuilder stNewChangeDoc = new StringBuilder();
        List<MsfRequestDocVo> msfRequestDocDtoList = new ArrayList<>(); //DB 저장을 위한 List
        List<MsfRequestDocDto> msfRequestDocList = request.getMsfRequestDocList(); //front 에서 넘어온 값
        if (msfRequestDocList != null && !msfRequestDocList.isEmpty()) {
            for (MsfRequestDocDto docDto: msfRequestDocList) {
                MsfRequestDocVo msfRequestDocVo = new MsfRequestDocVo();
                msfRequestDocVo.setRequestKey(request.getRequestKey());
                msfRequestDocVo.setFileTypeCd(docDto.getFileTypeCd());
                msfRequestDocVo.setFileNm(docDto.getFileNm());
                msfRequestDocVo.setFilePathNm(docDto.getFilePathNm());
                msfRequestDocVo.setFilePageNo(docDto.getFilePageNo());
                msfRequestDocDtoList.add(msfRequestDocVo);

                if (!stNewChangeDoc.isEmpty()) {
                    stNewChangeDoc.append(",");
                }
            }
        }
        newChangeWriteMapper.deleteMsfRequestDocTemp(request.getRequestKey());

        //부가서비스 request & delete : 저장 전 처리
        StringBuilder stAddition = new StringBuilder();
        List<MsfRequestAdditionVo> additionDtoList = new ArrayList<>(); //DB 저장을 위한 List
        List<NewChangeAdditionRequest> msfAdditionList = request.getAdditionList(); //front 에서 넘어온 값
        String reqAdditionListNm = "";
        long reqAdditionPrice = 0L;
        if (msfAdditionList != null && !msfAdditionList.isEmpty()) {
            if (msfAdditionList.size() > 0) {
                for (NewChangeAdditionRequest dto: msfAdditionList) {
                    MsfRequestAdditionVo msfRequestAdditionVo = new MsfRequestAdditionVo();
                    msfRequestAdditionVo.setRequestKey(request.getRequestKey()); //신청서 일련번호
                    msfRequestAdditionVo.setAdditionKey(dto.getAdditionKey());
                    msfRequestAdditionVo.setAdditionId(dto.getAdditionId());
                    msfRequestAdditionVo.setAdditionNm(dto.getAdditionNm());
                    msfRequestAdditionVo.setRantal(dto.getRantal());

                    log.debug(dto.getAdditionId());
                    log.debug(dto.getAdditionNm());


                    additionDtoList.add(msfRequestAdditionVo);

                    if (stAddition.length() > 0) {
                        stAddition.append(",");
                    }
                    stAddition.append(dto.getAdditionNm().trim());
                    if (dto.getRantal() > 0) {
                        reqAdditionPrice += dto.getRantal();
                    }
                }
                reqAdditionListNm = stAddition.toString();
            }
            request.setReqAdditionListNm(reqAdditionListNm);
            request.setReqAdditionPrice(reqAdditionPrice);
        }
        newChangeWriteMapper.deleteMsfAdditionTemp(request.getRequestKey());

        if (request.isFirst()) {
            MsfRequestRecord record = MsfRequestRecord.requestToRecord(request);

            //INSERT
            newChangeWriteMapper.insertMsfRequestTemp(record.msfRequestVo()); //MSF_REQUEST
            newChangeWriteMapper.insertMsfRequestCstmrTemp(record.msfRequestCstmrVo()); //MSF_REQUEST_CSTMR
            if (!"VMY".equals(record.msfRequestCstmrVo().getCstmrVisitTypeCd())) { //방문자가 본인이 아닌 경우
                newChangeWriteMapper.insertMsfRequestAgentTemp(record.msfRequestAgentVo()); //MSF_REQUEST_AGENT
            }
            newChangeWriteMapper.insertMsfRequestSaleTemp(record.msfRequestSaleVo()); //MSF_REQUEST_SALE
            newChangeWriteMapper.insertMsfRequestBillReqTemp(record.msfRequestBillReqVo()); //MSF_REQUEST_BILL_REQ
            if ("MNP3".equals(request.getOperTypeCd())) { //번호이동 정보
                newChangeWriteMapper.insertMsfRequestMoveTemp(record.msfRequestMoveVo()); //MSF_REQUEST_MOVE
            }
            if ("HCN3".equals(request.getOperTypeCd()) || "HDN3".equals(request.getOperTypeCd())) { //기기변경 사유
                newChangeWriteMapper.insertMsfRequestDvcChgTemp(record.msfRequestDvcChgVo()); //MSF_REQUEST_DVC_CHG
            }
        } else {
            log.debug("getUsimKindsCd : {}, getUsimPrice:: {}, getUsimPayMthdCd: {}, getUsimPriceTypeCd: {} ",
                request.getUsimKindsCd(),
                request.getUsimPrice(),
                request.getUsimPayMthdCd(),
                request.getUsimPriceTypeCd());
            MsfRequestRecord record = MsfRequestRecord.requestToRecord(request);

            //신청서 확인 시 가입진행코드는 접수(00) 으로 세팅
            if ("3".equals(request.getTmpStepCd())) {
                record.msfRequestVo().setSbscProCd("00"); //가입진행코드
                record.msfRequestVo().setProSttusCd("01"); //진행상태코드 (MCP 연동 시 빈값이면 안되어서 미리 업데이트 처리)
            }

            //UPDATE
            newChangeWriteMapper.updateMsfRequestTemp(record.msfRequestVo()); //MSF_REQUEST
            newChangeWriteMapper.updateMsfRequestCstmrTemp(record.msfRequestCstmrVo()); //MSF_REQUEST_CSTMR
            if (!"VMY".equals(record.msfRequestCstmrVo().getCstmrVisitTypeCd())) { //방문자가 본인이 아닌 경우
                newChangeWriteMapper.updateMsfRequestAgentTemp(record.msfRequestAgentVo()); //MSF_REQUEST_AGENT
            }
            newChangeWriteMapper.updateMsfRequestSaleTemp(record.msfRequestSaleVo()); //MSF_REQUEST_SALE
            newChangeWriteMapper.updateMsfRequestBillReqTemp(record.msfRequestBillReqVo()); //MSF_REQUEST_BILL_REQ
            if ("MNP3".equals(request.getOperTypeCd())) { //번호이동 정보
                newChangeWriteMapper.updateMsfRequestMoveTemp(record.msfRequestMoveVo()); //MSF_REQUEST_MOVE
            }
            if ("HCN3".equals(request.getOperTypeCd()) || "HDN3".equals(request.getOperTypeCd())) { //기기변경 사유
                newChangeWriteMapper.updateMsfRequestDvcChgTemp(record.msfRequestDvcChgVo()); //MSF_REQUEST_DVC_CHG
            }

        }

        //구비서류
        if (msfRequestDocList != null && !msfRequestDocList.isEmpty() && msfRequestDocList.size() > 0) {
            newChangeWriteMapper.insertMsfRequestDocListTemp(msfRequestDocDtoList); //MSF_REQUEST_DOC
        }
        //부가서비스
        if (msfAdditionList != null && !msfAdditionList.isEmpty() && msfAdditionList.size() > 0) {
            newChangeWriteMapper.insertMsfAdditionInfoListTemp(additionDtoList); //MSF_REQUEST_ADDITION
        }

        log.debug("MSF 저장 완료 ====================================================== ");

        return isSave; //일단 무조건 성공하자
    }


    /**
     * MCP 저장
     */
    public void setMcpComplete(Long requestKey) {
        McpRequestVo mcpRequestVo = toMcpNewChangeReadMapper.selectMsfRequestToMcp(requestKey);
        McpRequestCstmrVo mcpRequestCstmrVo = toMcpNewChangeReadMapper.selectMsfRequestCstmrToMcp(requestKey);
        McpRequestReqVo mcpRequestReqVo = toMcpNewChangeReadMapper.selectMsfRequestBillReqToMcp(requestKey);
        McpRequestSaleinfoVo mcpRequestSaleinfoVo = toMcpNewChangeReadMapper.selectMsfRequestSaleinfoToMcp(requestKey);
        List<McpRequestAdditionVo> mcpRequestAdditionVoList = toMcpNewChangeReadMapper.selectMsfRequestAdditionToMcp(requestKey);
        McpRequestAgentVo mcpRequestAgentVo = null;
        McpRequestMoveVo mcpRequestMoveVo = null;
        McpRequestDvcChgVo mcpRequestDvcChgVo = null;

        if (!"NA".equals(mcpRequestVo.getCstmrType()) && !"FN".equals(mcpRequestVo.getCstmrType())) { //내국인성인과 외국인성인 외에만 데이타 있음.
            mcpRequestAgentVo = toMcpNewChangeReadMapper.selectMsfRequestAgentToMcp(requestKey);
        }
        if ("MNP3".equals(mcpRequestVo.getOperType())) { //번호이동 테이블
            mcpRequestMoveVo = toMcpNewChangeReadMapper.selectMsfRequestMoveToMcp(requestKey);
        }
        if ("HDN3".equals(mcpRequestVo.getOperType()) || "HCN3".equals(mcpRequestVo.getOperType())) { //기기변경 사유 테이블
            mcpRequestDvcChgVo = toMcpNewChangeReadMapper.selectMsfRequestDvcChgToMcp(requestKey);
        }

        //고객유형별 변환처리
        String cstmrTypeCd = mcpRequestVo.getCstmrType();
        if (("NA".equals(cstmrTypeCd) || "FN".equals(cstmrTypeCd)) && StringUtils.hasText(mcpRequestCstmrVo.getCstmrPrivateNumber())) {
            mcpRequestVo.setCstmrType("PP"); //개인사업자로 변경하여 고객포탈에 저장
            //가독성이 그닥 좋진 않지만 PMD ㅠ..ㅠ
            //if (StringUtils.hasText(mcpRequestCstmrVo.getCstmrPrivateNumber())) { //개인사업자번호가 있는 경우
            //    mcpRequestVo.setCstmrType("PP"); //개인사업자로 변경하여 고객포탈에 저장
            //}
        }
        if ("GO".equals(cstmrTypeCd) || "FM".equals(cstmrTypeCd)) { //공공기관과 외국인미성년자는 고객유형 없음으로 NE 로 전송
            mcpRequestVo.setCstmrType("NE"); //고객포탈에 없는 고객유형
        }

        // 4-1. MCP 저장
        //MCP_REQUEST
        //McpRequestVo mcpRequestVo = toMcpNewChangeReadMapper.selectMsfRequestToMcp(request.getRequestKey());
        if (mcpRequestVo != null && mcpRequestVo.getRequestKey() != null) {
            int mcpCnt = mcpRequestReadMapper.selectMcpRequest(requestKey);
            if (mcpCnt > 0) {
                mcpRequestNewChangeWriteMapper.updateMcpRequest(mcpRequestVo);
            } else {
                mcpRequestNewChangeWriteMapper.insertMcpRequest(mcpRequestVo);
            }
        }
        //MCP_REQUEST_CSTMR
        //McpRequestCstmrVo mcpRequestCstmrVo = toMcpNewChangeReadMapper.selectMsfRequestCstmrToMcp(request.getRequestKey());
        log.debug("mcpRequestCstmrVo: {}", mcpRequestCstmrVo.toString());
        //log.debug("mcpRequestCstmrVo: {}", mcpRequestCstmrVo);
        if (mcpRequestCstmrVo != null && mcpRequestCstmrVo.getRequestKey() != null) {
            int mcpCnt = mcpRequestReadMapper.selectMcpRequestCstmr(requestKey);
            //mcpRequestCstmrVo.getCstmrPrivateBizNo
            if (mcpCnt > 0) {
                mcpRequestCstmrWriteMapper.updateMcpRequestCstmr(mcpRequestCstmrVo);
            } else {
                mcpRequestCstmrWriteMapper.insertMcpRequestCstmr(mcpRequestCstmrVo);
            }
        }
        //MCP_REQUEST_AGENT
        //McpRequestAgentVo mcpRequestAgentVo = toMcpNewChangeReadMapper.selectMsfRequestAgentToMcp(request.getRequestKey());
        if (mcpRequestAgentVo != null && mcpRequestAgentVo.getRequestKey() != null) {
            int mcpCnt = mcpRequestReadMapper.selectMcpRequestAgent(requestKey);
            if (mcpCnt > 0) {
                mcpRequestAgentWriteMapper.updateMcpRequestAgent(mcpRequestAgentVo);
            } else {
                mcpRequestAgentWriteMapper.insertMcpRequestAgent(mcpRequestAgentVo);
            }
        }
        //MCP_REQUEST_REQ
        //McpRequestReqVo mcpRequestReqVo = toMcpNewChangeReadMapper.selectMsfRequestBillReqToMcp(request.getRequestKey());
        if (mcpRequestReqVo != null && mcpRequestReqVo.getRequestKey() != null) {
            int mcpCnt = mcpRequestReadMapper.selectMcpRequestReq(requestKey);
            if (mcpCnt > 0) {
                mcpRequestReqWriteMapper.updateMcpRequestReq(mcpRequestReqVo);
            } else {
                mcpRequestReqWriteMapper.insertMcpRequestReq(mcpRequestReqVo);
            }
        }
        //MCP_REQUEST_SALEINFO
        //McpRequestSaleinfoVo mcpRequestSaleinfoVo = toMcpNewChangeReadMapper.selectMsfRequestSaleinfoToMcp(request.getRequestKey());
        if (mcpRequestSaleinfoVo != null && mcpRequestSaleinfoVo.getRequestKey() != null) {
            //유심 납부방법코드 - USIM_PAY_MTHD_CD
            //if ("R".equals(mcpRequestSaleinfoVo.getUsimPayMthdCd())) { //즉납
            //    mcpRequestSaleinfoVo.setUsimPayMthdCd("2"); //일시납
            //} else if ("B".equals(mcpRequestSaleinfoVo.getUsimPayMthdCd())) { //후청구
            //    mcpRequestSaleinfoVo.setUsimPayMthdCd("3"); //분납
            //} else if ("N".equals(mcpRequestSaleinfoVo.getUsimPayMthdCd())) { //비구매
            //    mcpRequestSaleinfoVo.setUsimPayMthdCd("1"); //면제
            //}

            int mcpCnt = mcpRequestReadMapper.selectMcpRequestSaleinfo(requestKey);
            if (mcpCnt > 0) {
                mcpRequestSaleinfoWriteMapper.updateMcpRequestSaleinfo(mcpRequestSaleinfoVo);
            } else {
                mcpRequestSaleinfoWriteMapper.insertMcpRequestSaleinfo(mcpRequestSaleinfoVo);
            }
        }
        //MCP_REQUEST_ADDITION
        //List<McpRequestAdditionVo> mcpRequestAdditionVoList = toMcpNewChangeReadMapper.selectMsfRequestAdditionToMcp(request.getRequestKey());
        if (mcpRequestAdditionVoList != null && !mcpRequestAdditionVoList.isEmpty()) {
            mcpRequestAdditionWriteMapper.insertMcpRequestAddition(mcpRequestAdditionVoList);
            //int mcpCnt = mcpRequestReadMapper.selectMcpRequestAddition(requestKey);
            //if (mcpCnt > 0) {
            //    mcpRequestAdditionWriteMapper.updateMcpRequestAddition(mcpRequestAdditionVoList);
            //} else {
            //    mcpRequestAdditionWriteMapper.insertMcpRequestAddition(mcpRequestAdditionVoList);
            //}
        }
        //MCP_REQUEST_MOVE
        //McpRequestMoveVo mcpRequestMoveVo = toMcpNewChangeReadMapper.selectMsfRequestMoveToMcp(request.getRequestKey());
        if (mcpRequestMoveVo != null && mcpRequestMoveVo.getRequestKey() != null) {
            int mcpCnt = mcpRequestReadMapper.selectMcpRequestMove(requestKey);
            if (mcpCnt > 0) {
                mcpRequestMoveWriteMapper.updateMcpRequestMove(mcpRequestMoveVo);
            } else {
                mcpRequestMoveWriteMapper.insertMcpRequestMove(mcpRequestMoveVo);
            }
        }
        //MCP_REQUEST_DVC_CHG
        //McpRequestDvcChgVo mcpRequestDvcChgVo = toMcpNewChangeReadMapper.selectMsfRequestDvcChgToMcp(request.getRequestKey());
        if (mcpRequestDvcChgVo != null && mcpRequestDvcChgVo.getRequestKey() != null) {
            int mcpCnt = mcpRequestReadMapper.selectMcpRequestDvcChg(requestKey);
            if (mcpCnt > 0) {
                mcpRequestDvcChgWriteMapper.updateMcpRequestDvcChg(mcpRequestDvcChgVo);
            } else {
                mcpRequestDvcChgWriteMapper.insertMcpRequestDvcChg(mcpRequestDvcChgVo);
            }
        }

        //MCP_REQUEST_CHANGE : 2026.07.29
        McpCustRequestChangeVo mcpCustRequestChangeVo = new McpCustRequestChangeVo();
        mcpCustRequestChangeVo.setRequestKey(requestKey);
        mcpCustRequestChangeVo.setRvisnId(AuthenticationUtils.getUser().getUserId());
        mcpRequestWriteMapper.insertNmcpCustReqChange(mcpCustRequestChangeVo);

        //MCP_REQUEST_CLAUSE
        //mcpNewChangeWriteMapper.insertMcpRequestClause(request.getRequestKey());
        //MCP_REQUEST_OSST
        //mcpNewChangeWriteMapper.insertMcpRequestOsst(request.getRequestKey());
        //MCP_REQUEST_STATE
        //mcpNewChangeWriteMapper.insertMcpRequestState(request.getRequestKey());
    }

    @Transactional(transactionManager = SmartFormDataSourceConfig.SMARTFORM_TX_MANAGER)
    public void setMsfComplete(Long requestKey) {
        newChangeWriteMapper.insertMsfRequest(requestKey);
        newChangeWriteMapper.insertMsfRequestCstmr(requestKey);
        newChangeWriteMapper.insertMsfRequestAgent(requestKey);
        newChangeWriteMapper.insertMsfRequestSale(requestKey);
        newChangeWriteMapper.insertMsfRequestBillReq(requestKey);
        newChangeWriteMapper.insertMsfRequestMove(requestKey);
        newChangeWriteMapper.insertMsfRequestDvcChg(requestKey);
        newChangeWriteMapper.insertMsfRequestAddition(requestKey);
        newChangeWriteMapper.insertMsfRequestDoc(requestKey);
        newChangeWriteMapper.insertMsfRequestRec(requestKey);
    }

    /**
     * 신규/변경 신청서 삭제
     * MSF_REQUEST_TEMP 관련 테이블
     */
    public void setMsfDeleteTemp(Long requestKey) {
        newChangeWriteMapper.deleteMsfRequest(requestKey);
        newChangeWriteMapper.deleteMsfRequestCstmr(requestKey);
        newChangeWriteMapper.deleteMsfRequestAgent(requestKey);
        newChangeWriteMapper.deleteMsfRequestSale(requestKey);
        newChangeWriteMapper.deleteMsfRequestBillReq(requestKey);
        newChangeWriteMapper.deleteMsfRequestMove(requestKey);
        newChangeWriteMapper.deleteMsfRequestDvcChg(requestKey);
        newChangeWriteMapper.deleteMsfRequestAddition(requestKey);
        newChangeWriteMapper.deleteMsfRequestDoc(requestKey);
        newChangeWriteMapper.deleteMsfRequestRec(requestKey);
    }

    /**
     * 신규/변경 신청서 삭제
     * MSF_REQUEST 관련 테이블
     */
    public void setMsfDelete(Long requestKey) {
        //newChangeWriteMapper.deleteMsfRequest(requestKey);
        //newChangeWriteMapper.deleteMsfRequestCstmr(requestKey);
        //newChangeWriteMapper.deleteMsfRequestAgent(requestKey);
        //newChangeWriteMapper.deleteMsfRequestSale(requestKey);
        //newChangeWriteMapper.deleteMsfRequestBillReq(requestKey);
        //newChangeWriteMapper.deleteMsfRequestMove(requestKey);
        //newChangeWriteMapper.deleteMsfRequestDvcChg(requestKey);
        //newChangeWriteMapper.deleteMsfRequestAddition(requestKey);
        //newChangeWriteMapper.deleteMsfRequestDoc(requestKey);
        //newChangeWriteMapper.deleteMsfRequestRec(requestKey);
    }

    /**
     * String TO Long
     */
    private static long getStringToLong(String str) {
        try {
            return (str == null || str.isBlank()) ? 0L : Long.parseLong(str);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
