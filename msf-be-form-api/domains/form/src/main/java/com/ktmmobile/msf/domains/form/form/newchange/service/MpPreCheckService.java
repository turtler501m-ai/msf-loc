package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.util.CryptoUtils;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformOsstServiceType;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.CstmrType;
import com.ktmmobile.msf.domains.form.common.code.OperType;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.newchange.dto.FormStatusRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.FormStatusResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MpPreCheckRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MpPreCheckResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeMpHC0Response;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeMpPC0Response;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PreCheckRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PreCheckResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFHC0InDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFHC0InFrmpapDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFHC0InPrdcDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFPC0InDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFPC0InFrmpapDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFPC0InNpDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFS2Request;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFS2Response;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormPreCheckResponse;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeMpFieldMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeMpReadMapper;

/**
 * PC0 / FPC0 / HC0 / FHC0 개통전 사전체크
 * FS2  서식지 상태변경
 * FT1	고객 안면인증 SKIP 요청
 * FS8	고객 안면인증 URL 요청
 * FT1	고객 안면인증 SKIP 요청
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MpPreCheckService {

    private final NewChangeMpReadMapper newChangeMpReadMapper;
    private final MsfMcpOsstPrxService msfMcpOsstPrxService;
    private final FormCommReadMapper formCommReadMapper;
    private final AgencyCacheReader agencyCacheReader;

    /**
     * 가입유형(신규가입/번호이동/기기변경) : Knote서식지아이디존재여부 >> 사전체크 MAIN (상태변경 등 포함)
     */
    //개통전 사전체크 Return Map
    public HashMap<String, String> getNewChangeMpPreCheck(MpPreCheckRequest mpPreCheckRequest) {
        HashMap<String, String> rtnMap = new HashMap<>();
        String rsltCd = ""; //결과코드
        String rsltMsg = ""; //결과메세지

        //서식지 아이디 존재여부에 따른 호출서비스가 다름.
        String resNo = mpPreCheckRequest.getResNo(); //MSF_REQUEST.RES_NO 에 저장될 값
        String knoteScanId = mpPreCheckRequest.getKnoteScanId(); //KNOTE 서식지 아이디
        String fathTransacId = mpPreCheckRequest.getFathTransacId(); //안면인증 트랜잭션 아이디
        String operTypeCd = mpPreCheckRequest.getOperTypeCd(); //업무유형
        String tmpStepCd = mpPreCheckRequest.getTmpStepCd(); //임시저장 단계 (1: 고객, 2: 상품, 3: 동의, 4: 작성완료) - 작성완료한 경우에는 본 테이블을 바라보도록 하는 것으로 할까보다.
        String agentCd = mpPreCheckRequest.getAgentCd(); //Header 값으로 보낼 관리자할 대리점코드
        String appAgncCd = mpPreCheckRequest.getMngmAgncId(); //Header 값으로 보낼 관리자할 대리점코드
        //String transationIdCheck = ""; //트랜잭션 아이디 여부 체크 Y/N
        //String preCheckFormStep = mpPreCheckRequest.getPreCheckFormStep(); //업무유형별 개통전 사전체크를 진행할 단계이다
        //String preCheckFormStep = ""; //업무유형별 개통전 사전체크를 진행할 단계이다
        String requestPreCheck = mpPreCheckRequest.getRequestPreCheck(); //사전체크 요청한 경우

        log.debug(
            "getNewChangeMpPreCheck >> resNo:{}, knoteScanId: {}, fathTransacId: {}, operTypeCd: {}, tmpStepCd: {}, agentCd: {}, appAgncCd: {}, requestPreCheck: {}",
            resNo,
            knoteScanId,
            fathTransacId,
            operTypeCd,
            tmpStepCd,
            agentCd,
            appAgncCd,
            requestPreCheck);

        String changeStatusCheck = ""; //서식지 상태변경 대상 Y/N
        String progressPreCheck = ""; //사전체크 진행대상 여부
        String preCheckSuccess = ""; //개통전 사전체크 성공여부

        String reqBuyTypeCd = ""; //구매유형 (휴대폰 MM , 유심 UU)
        String serviceTypeCd = ""; //서비스 유형코드 PO (후불) , PP (선불)
        String usimKindsCd = ""; //유심종류
        String sprtTypeCd = ""; //지원금 유형코드 (KD, PM, SM)
        String modelMonthly = ""; //단말할부개월수
        Long enggMnthCnt = 0L; //약정개월수
        String plcyType = ""; //정책유형 02 / 02 / 03

        //기기변경 고객인증을 위한 parameter SET
        String customerType = ""; //고객유형
        String customerName = ""; //고객명
        String customerRrn = ""; //고객식별번호
        String customerTelNo = ""; //고객인증 핸드폰번호

        //업무유형과 예약번호가 없다면 대상에서 제외
        if (!StringUtils.hasText(operTypeCd) || !StringUtils.hasText(resNo)) {
            rsltCd = "F"; //결과코드
            rsltMsg = "입력정보가 부족합니다."; //결과메세지
        }

        //KT 조직코드 추출 (화면에서 선택한 사용자 조직의 대리점코드 선택한 값이 있어야함)
        if (!StringUtils.hasText(appAgncCd) && StringUtils.hasText(agentCd)) {
            //KT조직코드 Cache 에서 가져오기
            Optional<AgencyCache> agentInfo = agencyCacheReader.getAgency(agentCd);
            if (agentInfo.isPresent()) {
                appAgncCd = agentInfo.get().ktOrganizationId();
            }
        }

        //유효성 체크가 업무유형별로 필요해보임.
        log.debug("getNewChangePreCheck (개통전 사전체크) >> operTypeCd: {}, tmpStepCd: {}, resNo: {}, knoteScanId: {}, fathTransacId: {}",
            operTypeCd,
            tmpStepCd,
            resNo,
            knoteScanId,
            fathTransacId);

        //서식지 아이디 존재여부 및 업무유형에 따라 진행단계 변수 설정
        //다음버튼에 단계별로 요구사항에 따라 변경하기 위해서 추가한 변수인데 현 시점 유명무실해짐.
        //if ("Y".equals(requestPreCheck)) {
        //    if (operTypeCd.equals(OperType.MOBILE_NUMBER_PORTABILITY.getCode())) { //번호이동은 번호이동 사전동의 결과조회 성공 후 호출함.
        //        preCheckFormStep = "Y";
        //    } else if (operTypeCd.equals(OperType.NEW_ACTIVATION.getCode())) { //신규가입은 고객단계 저장(가입조건조회) 시 진행됨.
        //        preCheckFormStep = "Y";
        //    } else if (operTypeCd.equals(OperType.HANDSET_EXCHANGE.getCode())) { //기기변경은 상품단계 저장 시 진행됨.
        //        preCheckFormStep = "Y";
        //    } else if (operTypeCd.equals(OperType.HANDSET_CHANGE.getCode())) { //기기변경은 상품단계 저장 시 진행됨.
        //        preCheckFormStep = "Y";
        //    }
        //}

        log.debug("getNewChangeMpPreCheck >> requestPreCheck: {}, knoteScanId: {}, fathTransacId: {}, appAgncCd: {}" + requestPreCheck,
            knoteScanId,
            fathTransacId,
            appAgncCd);

        //신청서 상태변경 대상 (서식지아이디가 있고 트랜잭션아이디가 없는 경우 신청서 상태 변경 프로세스가 있어야 함.)
        //if (StringUtils.hasText(knoteScanId) && !StringUtils.hasText(fathTransacId)) {
        if (StringUtils.hasText(knoteScanId)) { //안면인증은 Knote신분증도 있고 안면인증서식지도 있으므로 안면인증서식지 없는 경우 제외함. 2026.07.24
            changeStatusCheck = "Y";
        }

        // 1. 서식지 상태변경 (FS2) 호출
        if ("Y".equals(requestPreCheck)) { //프론트에서 보낸 값
            if ("Y".equals(changeStatusCheck)) { //서식지 아이디 및 트랜잭션 아이디에 따라 세팅해주는 값
                String frmpapStatCd = "P";
                FormStatusRequest formStatusRequest = new FormStatusRequest();
                formStatusRequest.setRequestKey(mpPreCheckRequest.getRequestKey());
                formStatusRequest.setOperTypeCd(operTypeCd); //가입유형
                formStatusRequest.setResNo(resNo); //신청서 예약번호
                formStatusRequest.setKnoteScanId(knoteScanId);
                formStatusRequest.setFrmpapStatCd(frmpapStatCd);

                log.debug("서식지 상태변경 (FS2) 호출 input >> operTypeCd: {}, resNo: {}, knoteScanId: {}, frmpapStatCd: {}",
                    operTypeCd,
                    resNo,
                    knoteScanId,
                    frmpapStatCd);

                FormStatusResponse formStatusResponse = new FormStatusResponse();
                formStatusResponse = this.callFS2(formStatusRequest);

                rsltCd = formStatusResponse.getRsltCd();
                rsltMsg = formStatusResponse.getRsltMsg();

                log.debug("서식지 상태변경 (FS2) 호출 output >> rsltCd: {}, rsltMsg: {}", rsltCd, rsltMsg);

                if ("Y".equals(rsltCd)) { //서식지상태변경 성공
                    progressPreCheck = "Y"; //개통전 사전체크 진행대상.
                } else {
                    progressPreCheck = "N"; //개통전 사전체크 진행불가.
                    preCheckSuccess = "N"; //개통전 사전체크 진행불가로 실패로 처리
                    rsltMsg = "KNOTE 서식지 상태변경 실패로 개통 전 사전체크 진행 불가입니다.";
                }
            } else {
                progressPreCheck = "Y"; //개통전 사전체크 진행대상.
            }
        } else {
            preCheckSuccess = "X";
        }
        log.debug("서식지 상태변경 결과에 따라 개통전 사전체크 진행여부 >> preCheckSuccess : " + preCheckSuccess);

        // 2. 개통전 사전체크 진행 (FPC0 , FHC0)
        PreCheckResponse preCheckResponse = new PreCheckResponse();
        if ("Y".equals(progressPreCheck)) {
            log.debug("서식지 상태변경이 성공하고 개통전 사전체크 진행하러 갑니다.");
            //기기변경일 때는 작성완료 시점에 개통전 사전체크를 해야하는데 이때 필요한 정보 추출
            if (operTypeCd.equals(OperType.HANDSET_CHANGE.getCode()) || operTypeCd.equals(OperType.HANDSET_EXCHANGE.getCode())) { // 작성완료 시점이므로 신청서 데이타를 조회해야하는 경우
                NewChangeRequest newChangePreCheckInfo = newChangeMpReadMapper.selectMsfPreCheckInfoRequest(mpPreCheckRequest.getRequestKey());

                if (newChangePreCheckInfo == null) {
                    //return "N"; //프로그램 오류로 데이타 조회가 되지 않을 경우
                    preCheckSuccess = "X"; //사전체크 성공여부 Fail 일까? 실행하지 않음으로 해야할까?
                } else {
                    //특별판매정책번호
                    reqBuyTypeCd = newChangePreCheckInfo.getReqBuyTypeCd(); //구매유형 (휴대폰 MM , 유심 UU)
                    serviceTypeCd = newChangePreCheckInfo.getServiceTypeCd(); //서비스 유형코드 PO (후불) , PP (선불)
                    usimKindsCd = newChangePreCheckInfo.getUsimKindsCd(); //유심종류
                    sprtTypeCd = newChangePreCheckInfo.getSprtTypeCd(); //지원금 유형코드 (KD, PM, SM)
                    modelMonthly = newChangePreCheckInfo.getModelMonthly(); //단말할부개월수
                    enggMnthCnt = newChangePreCheckInfo.getEnggMnthCnt(); //약정개월수
                    //plcyType = ""; //정책유형 02 / 02 / 03

                    //기기변경 고객인증을 위한 parameter SET
                    customerType = newChangePreCheckInfo.getCstmrTypeCd(); //고객유형
                    customerName = newChangePreCheckInfo.getCstmrNm(); //고객명
                    customerRrn = newChangePreCheckInfo.getCstmrNativeRrn(); //고객식별번호
                    if (StringUtils.hasText(customerType)) {
                        if (customerType.equals(CstmrType.JURIDICAL_PERSON.getCode()) || customerType.equals(CstmrType.GOVERNMENT_ORGANIZATION.getCode())) { //법인 또는 공공기관
                            customerName = newChangePreCheckInfo.getCstmrJuridicalCname(); //법인명 또는 공공기관명
                            customerRrn = newChangePreCheckInfo.getCstmrJuridicalRrn();
                        } else if (customerType.equals(CstmrType.FOREIGN_ADULT.getCode()) || customerType.equals(CstmrType.FOREIGN_MINOR.getCode())) { //외국인 또는 외국인 미성년자
                            customerRrn = newChangePreCheckInfo.getCstmrForeignerRrn();
                        }
                    }
                    customerTelNo = newChangePreCheckInfo.getOpenNo(); //개통번호 (기기변경 인증 핸드폰번호)
                    if (!StringUtils.hasText(customerTelNo)) { //임시처리
                        customerTelNo = newChangePreCheckInfo.getCstmrTelFnNo() + newChangePreCheckInfo.getCstmrTelMnNo() + newChangePreCheckInfo.getCstmrTelRnNo(); //고객인증 핸드폰번호
                    }
                }
            }

            //정책유형코드 설정
            if ("MM".equals(reqBuyTypeCd)) {
                plcyType = "01"; //단말
            } else {
                if ("09".equals(usimKindsCd)) {
                    plcyType = "03"; //eSIM
                } else {
                    plcyType = "02"; //유심
                }
            }
            //if ("09".equals(usimKindsCd)) {
            //    plcyType = "03"; //eSIM
            //} else if ("MM".equals(reqBuyTypeCd)) {
            //    plcyType = "01"; //단말
            //} else {
            //    plcyType = "02"; //기타
            //}

            //지원금 유형코드
            if ("".equals(sprtTypeCd)) {
                sprtTypeCd = "NN";
            }

            //개통전 사전체크 진행을 위한 DATA SET
            PreCheckRequest preCheckRequest = new PreCheckRequest();
            preCheckRequest.setRequestKey(mpPreCheckRequest.getRequestKey()); //신청서 일련번호
            preCheckRequest.setResNo(resNo); //신청서 예약번호
            preCheckRequest.setKnoteScanId(knoteScanId); //KNOTE 서식지 아이디
            preCheckRequest.setFathTransacId(fathTransacId); //안면인식 트랜잭션 아이디
            preCheckRequest.setOperTypeCd(operTypeCd); //업무유형코드 (NAC3, MNP3, HDN3)
            preCheckRequest.setReqBuyTypeCd(reqBuyTypeCd); //구매유형 (휴대폰 MM , 유심 UU)
            preCheckRequest.setServiceTypeCd(serviceTypeCd); //서비스 유형코드 PO (후불) , PP (선불) >> 스마트는 후불기준으로만 진행하는 프로젝트 (추후 추가될 수도 있겠죠)
            preCheckRequest.setSprtTypeCd(sprtTypeCd); //지원금 유형코드 (KD, PM, SM, NN)
            preCheckRequest.setModelMonthly(modelMonthly); //단말할부개월수
            preCheckRequest.setEnggMnthCnt(enggMnthCnt); //약정개월수
            preCheckRequest.setPlcyType(plcyType); //정책유형

            preCheckRequest.setCustomerType(customerType); //고객유형 : 기기변경 고객인증을 통해 고객아이디 추출 하기 위함.
            preCheckRequest.setCustomerName(customerName); //고객유형 : 기기변경 고객인증을 통해 고객아이디 추출 하기 위함.
            preCheckRequest.setCustomerRrn(customerRrn); //고객유형 : 기기변경 고객인증을 통해 고객아이디 추출 하기 위함.
            preCheckRequest.setCustomerTelNo(customerTelNo); //고객유형 : 기기변경 고객인증을 통해 고객아이디 추출 하기 위함.
            preCheckRequest.setAppAgncCd(appAgncCd); //고객유형 : 기기변경 헤더 하드코딩을 위함.

            //개통전 사전체크 진행!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            log.debug("진짜 개통 전 사전체크 진행하러 갑니다. input >> resNo: {}, knoteScanId: {}", resNo, knoteScanId);
            preCheckResponse = this.reqPreCheckOpen(preCheckRequest);
            preCheckSuccess = preCheckResponse.getPreCheckYn(); //개통전 사전체크 결과값 : Y / N
            log.debug("진짜 개통 전 사전체크 다녀왔습니다. output >> preCheckSuccess: {}", preCheckSuccess);

            //개통전 사전체크 실패 시 FS2 서식지 상태변경 처리  //서식지 상태를 변경한 경우, 서식지 상태 돌리는 조건에 추가함.
            if (!"Y".equals(preCheckSuccess) && "Y".equals(changeStatusCheck)) {
                log.debug("서식지 상태변경 >> knoteScanId: {}, fathTransacId: {}", knoteScanId, fathTransacId);
                //FS2 에서 서식지 상태 복구 처리
                //if (StringUtils.hasText(knoteScanId) && !StringUtils.hasText(fathTransacId)) {
                String frmpapStatCd = "R";
                FormStatusRequest formStatusRequest = new FormStatusRequest();
                formStatusRequest.setRequestKey(mpPreCheckRequest.getRequestKey());
                formStatusRequest.setOperTypeCd(operTypeCd); //가입유형
                formStatusRequest.setResNo(resNo); //신청서 예약번호
                formStatusRequest.setKnoteScanId(knoteScanId);
                formStatusRequest.setFrmpapStatCd(frmpapStatCd);

                log.debug("개통 전 사전체크가 실패했습니다. KNOTE 서식지 상태 복구(FS2) 갑니다. input >> resNo: {}, knoteScanId: {}", resNo, knoteScanId);
                FormStatusResponse formStatusResponse = new FormStatusResponse();
                formStatusResponse = this.callFS2(formStatusRequest);

                rsltCd = formStatusResponse.getRsltCd();
                rsltMsg = formStatusResponse.getRsltMsg();
                log.debug("개통 전 사전체크가 실패했습니다. KNOTE 서식지 상태 복구(FS2) 하고 왔습니다. output >> rsltCd: {}, rsltMsg: {}", rsltCd, rsltMsg);
                //}
            }


            //rsltCd = preCheckResponse.getRsltCd();
            rsltMsg = preCheckResponse.getRsltMsg();
        }

        //개통전 사전체크를 통과했거나 대상이 아닌 경우 PASS 처리
        if ("Y".equals(preCheckSuccess)) {
            rsltCd = "S"; //결과코드
            rsltMsg = "개통전 사전체크 성공입니다."; //결과메세지
        } else if ("X".equals(preCheckSuccess)) {
            rsltCd = "X"; //결과코드
            rsltMsg = "개통전 사전체크 대상이 아닙니다."; //결과메세지
        } else {
            rsltCd = "F"; //결과코드
            //rsltMsg = "개통전 사전체크 실패입니다."; //결과메세지
        }
        log.debug("개통 전 사전체크와 관련한 최종 결과입니다. >> rsltCd: {}, rsltMsg: {}", rsltCd, rsltMsg);

        rtnMap.put("rsltCd", rsltCd);
        rtnMap.put("rsltMsg", rsltMsg);
        return rtnMap;
    }


    /**
     * 개통전 사전체크
     */
    public PreCheckResponse reqPreCheckOpen(PreCheckRequest preCheckRequest) {
        log.debug("reqPreCheckOpen >> preCheckRequest.getCustomerTelNo(): {}, preCheckRequest.getCustomerName(): {}",
            preCheckRequest.getCustomerTelNo(),
            preCheckRequest.getCustomerName());

        String resNo = preCheckRequest.getResNo(); //예약번호
        String operTypeCd = preCheckRequest.getOperTypeCd(); //업무유형
        PreCheckResponse preCheckResponse = new PreCheckResponse(); //사전체크 결과

        NewChangeMpPC0Response newChangeMpPC0Response = new NewChangeMpPC0Response(); //신규가입, 번호이동
        NewChangeMpHC0Response newChangeMpHC0Response = new NewChangeMpHC0Response(); //기기변경

        NewChangeRequest newChangeRequest = new NewChangeRequest();
        newChangeRequest.setRequestKey(preCheckRequest.getRequestKey());

        //MspJuoSubInfoResponse mspJuoSubInfoResponse = new MspJuoSubInfoResponse();
        //MspJuoSubInfoRequest mspJuoSubInfoRequest = new MspJuoSubInfoRequest();
        //mspJuoSubInfoRequest.setSubscriberNo(preCheckRequest.getCustomerTelNo()); //고객 핸드폰번호
        //mspJuoSubInfoRequest.setCustomerMobileNo(preCheckRequest.getCustomerTelNo()); //고객 핸드폰번호
        //mspJuoSubInfoRequest.setCustomerLinkName(preCheckRequest.getCustomerName()); //고객명

        //String customerId = ""; //고객아이디
        //String contractNum = ""; //서비스계약번호

        if (operTypeCd.equals(OperType.HANDSET_EXCHANGE.getCode()) || operTypeCd.equals(OperType.HANDSET_CHANGE.getCode())) { //기기변경
            //특별판매번호 DATA SET
            String slsNo = formCommReadMapper.selectSlsNo(preCheckRequest);

            //개통전사전체크 - 기기변경 데이타조회
            newChangeMpHC0Response = newChangeMpReadMapper.selectMsfRequestHC0InfoRequest(newChangeRequest);

            //com.ktmmobile.msf.commons.crypto.support.exception.CryptoException: KISA SEED decryption failed.
            if (newChangeMpHC0Response.getJrdclAgentRrn() != null) {
                String jrdclAgentRrn = newChangeMpHC0Response.getJrdclAgentRrn();
                String agntBrthDate = "";
                log.debug(jrdclAgentRrn);
                if (jrdclAgentRrn.length() > 6) {
                    agntBrthDate = CryptoUtils.decrypt(jrdclAgentRrn, FieldCryptoAlgorithm.AES_GCM_SEARCHABLE);
                } else {
                    agntBrthDate = jrdclAgentRrn; //신청서에서 법인 본인(대표) 방문 시에는 저장값이 없어서 쿼리에서 20000101 하드코딩해서 처리함.
                }
                newChangeMpHC0Response.setAgntBrthDate(agntBrthDate.substring(2, 8));
                //newChangeMpHC0Response.setRsdcrtIssuDate("20260701"); //사업자교부일 추가필요 (2026.07.24)- [inDto.rsdcrtIssuDate] 항목에 [null]값은 허용되지 않은 값입니다.
            }

            log.debug("개통전사전체크 기기변경 - 추가 데이타셋 >> slsNo: {}", slsNo);
            newChangeMpHC0Response.setSpclSlsNo(slsNo); //특별판매번호

            //부가서비스 조회 (기기변경 작성 시 부가서비스 숨김으로 주석처리 - 2026.06.30)
            //List<MsfRequestAdditionVo> msfRequestAdditionVoList = newChangeMpReadMapper.selectMsfRequestAdditionVo(preCheckRequest.getRequestKey()); //부가서비스 조회
            //newChangeMpHC0Response.setAdditionVoList(msfRequestAdditionVoList);
        } else {
            //개통전사전체크 - 신규가입.번호이동 데이타조회
            //getXmlMessagePC0
            newChangeMpPC0Response = newChangeMpReadMapper.selectMsfRequestPC0Request(newChangeRequest);

            //암호화된 고객식별번호를 DB 쿼리에서는 복호화할 수 없음으로 아래와 같이 처리
            String agntIdfyNoVal = newChangeMpPC0Response.getAgntIdfyNoVal(); //법정대리인 고객식별번호
            if (StringUtils.hasText(agntIdfyNoVal)) {
                int agntIdfyNo7 = Character.getNumericValue(agntIdfyNoVal.charAt(6));
                String agntCustIdfyNoType = (agntIdfyNo7 <= 4) ? "1" : "4"; //법정대리인 식별번호 종류 (1: 주민번호, 4: 외국인등록번호)
                String agntPersonSexDiv = (agntIdfyNo7 % 2 == 1) ? "1" : "2"; //법정대리인 성별 (1: 남자, 2: 여자)
                newChangeMpPC0Response.setAgntCustIdfyNoType(agntCustIdfyNoType); //법정대리인 식별번호 종류 (1: 주민번호, 4: 외국인등록번호)
                newChangeMpPC0Response.setAgntPersonSexDiv(agntPersonSexDiv); //법정대리인 성별 (1: 남자, 2: 여자)
            }
            log.debug("newChangeMpPC0Response >> newChangeMpPC0Response: {}", newChangeMpPC0Response.toString());
        }

        String rsltCd = ""; //사전체크 Return value
        String rsltMsg = ""; //사전체크 Return value
        String nstepGlobalId = ""; //사전체크 Return value

        String osstOrdNo = ""; //MP 주문번호
        String preCheckYn = ""; //개통전 사전체크 성공여부

        //기기변경 - 서식지 아이디 있음.
        MplatFormFHC0InDtoRequest mplatFormFHC0InDtoRequest = null;
        MplatFormFHC0InFrmpapDtoRequest mplatFormFHC0InFrmpapDtoRequest = null;
        MplatFormFHC0InPrdcDtoRequest mplatFormFHC0InPrdcDtoRequest = null;

        //신규가입 , 번호이동 - 서식지 아이디 있음.
        MplatFormFPC0InDtoRequest mplatFormFPC0InDtoRequest = null;
        MplatFormFPC0InFrmpapDtoRequest mplatFormFPC0InFrmpapDtoRequest = null;

        //번호이동 - 서식지 아이디 있음.
        MplatFormFPC0InNpDtoRequest mplatFormFPC0InNpDtoRequest = null;

        MspPrxSoapResponse mspPrxSoapResponse = null;
        log.debug("preCheckRequest.getKnoteScanId(): {}", preCheckRequest.getKnoteScanId());
        //if (StringUtils.hasText(preCheckRequest.getKnoteScanId())) {
        if (StringUtils.hasText(preCheckRequest.getKnoteScanId()) || StringUtils.hasText(preCheckRequest.getFathTransacId())) {
            if (operTypeCd.equals(OperType.MOBILE_NUMBER_PORTABILITY.getCode())) {
                mplatFormFPC0InDtoRequest = NewChangeMpFieldMapper.INSTANCE.toMplatFormFPC0InDtoRequest(newChangeMpPC0Response);
                mplatFormFPC0InFrmpapDtoRequest = NewChangeMpFieldMapper.INSTANCE.toMplatFormFPC0InFrmpapDtoRequest(newChangeMpPC0Response);
                mplatFormFPC0InNpDtoRequest = NewChangeMpFieldMapper.INSTANCE.toMplatFormFPC0InNpDtoRequest(newChangeMpPC0Response);
                //mspPrxFormRequest = MspPrxFormRequest.createXmlRequest(List.of(mplatFormFPC0InDtoRequest,
                //    mplatFormFPC0InFrmpapDtoRequest,
                //    mplatFormFPC0InNpDtoRequest), MplatformOsstServiceType.NEW_CHANGE_MNP_PROCESS);

                mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstServiceNewChange(List.of(mplatFormFPC0InDtoRequest,
                        mplatFormFPC0InFrmpapDtoRequest,
                        mplatFormFPC0InNpDtoRequest),
                    MplatformOsstServiceType.NEW_CHANGE_MNP_PROCESS, resNo);
            } else if (operTypeCd.equals(OperType.NEW_ACTIVATION.getCode())) {
                mplatFormFPC0InDtoRequest = NewChangeMpFieldMapper.INSTANCE.toMplatFormFPC0InDtoRequest(newChangeMpPC0Response);
                mplatFormFPC0InFrmpapDtoRequest = NewChangeMpFieldMapper.INSTANCE.toMplatFormFPC0InFrmpapDtoRequest(newChangeMpPC0Response);
                //mspPrxFormRequest = MspPrxFormRequest.createXmlRequest(List.of(mplatFormFPC0InDtoRequest,
                //    mplatFormFPC0InFrmpapDtoRequest), MplatformOsstServiceType.NEW_CHANGE_NAC_PROCESS);

                mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstServiceNewChange(List.of(mplatFormFPC0InDtoRequest,
                        mplatFormFPC0InFrmpapDtoRequest),
                    MplatformOsstServiceType.NEW_CHANGE_NAC_PROCESS, resNo);
            } else if (operTypeCd.equals(OperType.HANDSET_CHANGE.getCode()) || operTypeCd.equals(OperType.HANDSET_EXCHANGE.getCode())) {
                mplatFormFHC0InDtoRequest = NewChangeMpFieldMapper.INSTANCE.toMplatFormFHC0InDtoRequest(newChangeMpHC0Response);
                mplatFormFHC0InFrmpapDtoRequest = NewChangeMpFieldMapper.INSTANCE.toMplatFormFHC0InFrmpapDtoRequest(newChangeMpHC0Response);
                mplatFormFHC0InPrdcDtoRequest = NewChangeMpFieldMapper.INSTANCE.toMplatFormFHC0InPrdcDtoRequest(newChangeMpHC0Response);
                //mspPrxFormRequest = MspPrxFormRequest.createXmlRequest(List.of(mplatFormFHC0InDtoRequest,
                //    mplatFormFHC0InFrmpapDtoRequest, mplatFormFHC0InPrdcDtoRequest), MplatformOsstServiceType.NEW_CHANGE_HCN_PROCESS);

                mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstService(List.of(mplatFormFHC0InDtoRequest,
                        mplatFormFHC0InFrmpapDtoRequest, mplatFormFHC0InPrdcDtoRequest),
                    MplatformOsstServiceType.NEW_CHANGE_HCN_PROCESS.getEventCd(), newChangeMpHC0Response.getAgentCd(), resNo);

                //List<Object> requestDtoList = new ArrayList<>();
                //requestDtoList.add(mplatFormFHC0InDtoRequest);
                //requestDtoList.add(mplatFormFHC0InFrmpapDtoRequest);
                //requestDtoList.add(mplatFormFHC0InPrdcDtoRequest);
                //
                // // 2. 부가서비스(R) 추가
                //List<MsfRequestAdditionVo> additionList = newChangeMpHC0Response.getAdditionVoList();
                //if (additionList != null) {
                //    for (MsfRequestAdditionVo addition: additionList) {
                //        String prdcCd = addition.getAdditionId();
                //        if (prdcCd != null && !prdcCd.isBlank()) {
                //            MplatFormFHC0InPrdcDtoRequest addPrdc = new MplatFormFHC0InPrdcDtoRequest();
                //            addPrdc.setPrdcCd(prdcCd);
                //            addPrdc.setPrdcTypeCd("R");
                //            requestDtoList.add(addPrdc);
                //        }
                //    }
                //}
                //
                //mspPrxFormRequest = MspPrxFormRequest.createXmlRequest(requestDtoList,
                //    preCheckRequest.getAppAgncCd(),
                //    MplatformOsstServiceType.NEW_CHANGE_HCN_PROCESS);
            }
        } else {
            //PC0 와 HC0 는 셀프 서비스로 2026-06-17 시점 기준 스마트에서는 사용하지 않음
            preCheckResponse.setPreCheckYn("N");
            return preCheckResponse;
        }

        //MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callXmlOsstService(mspPrxFormRequest);
        String responseType = mspPrxSoapResponse.responseType();
        String responseCode = mspPrxSoapResponse.responseCode();
        String responseBasic = mspPrxSoapResponse.responseBasic();
        String globalNo = mspPrxSoapResponse.globalNo();

        log.debug("★ MP 호출 결과 ( mspPrxSoapResponse ) >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
            responseType, responseCode, responseBasic, globalNo);
        log.debug("호출 결과 rawXml: {}", mspPrxSoapResponse.rawXml());
        try {
            MplatFormPreCheckResponse mplatFormPreCheckResponse = XmlConvertUtils.xmlReturnParser(mspPrxSoapResponse.rawXml(),
                MplatFormPreCheckResponse.class);

            preCheckYn = "N"; //사전체크 결과 성공여부
            if ("E".equals(responseType)) { //연동 오류
                rsltCd = responseCode;
                rsltMsg = responseBasic;
            } else {
                rsltCd = mplatFormPreCheckResponse.getRsltCd();
                rsltMsg = mplatFormPreCheckResponse.getRsltMsg();
                osstOrdNo = mplatFormPreCheckResponse.getOsstOrdNo();

                if ("N".equals(responseType)) {
                    preCheckYn = "Y"; //사전체크 성공!!!
                }
            }
            log.debug("osstOrdNo(): {}, rsltCd: {}, rsltMsg: {}", osstOrdNo, rsltCd, rsltMsg);
        } catch (Exception e) {
            throw new SimpleDomainException("MP 호출 오류 발생", e);
        }

        //사전체크 결과 전달하기
        preCheckResponse.setOsstOrdNo(osstOrdNo);
        preCheckResponse.setRsltCd(rsltCd);
        preCheckResponse.setRsltMsg(rsltMsg);
        preCheckResponse.setNstepGlobalId(nstepGlobalId);
        preCheckResponse.setPreCheckYn(preCheckYn);

        return preCheckResponse;
    }


    /**
     * 서식지 상태변경 FS2
     */
    public FormStatusResponse callFS2(FormStatusRequest request) {
        FormStatusResponse formStatusResponse = new FormStatusResponse();

        //신청서 내용 추출
        NewChangeRequest newChangeRequest = new NewChangeRequest();
        newChangeRequest.setRequestKey(request.getRequestKey());
        NewChangeMpPC0Response newChangeMpPC0Response = newChangeMpReadMapper.selectMsfRequestPC0Request(newChangeRequest);

        //서식지 상태변경 (FS2) 호출
        String frmpapStatCd = StringUtil.NVL(request.getFrmpapStatCd(), "P"); //P:진행, R:복구(진행중인 상태를 접수로 변경), C:취소
        MplatFormFS2Request mplatFormFS2Request = new MplatFormFS2Request();
        mplatFormFS2Request.setMngmAgncId(newChangeMpPC0Response.getMngmAgncId());
        mplatFormFS2Request.setCntpntCd(newChangeMpPC0Response.getCntpntCd());
        mplatFormFS2Request.setFrmpapId(newChangeMpPC0Response.getFrmpapId());
        mplatFormFS2Request.setFrmpapStatCd(frmpapStatCd);
        log.debug("callFS2 >> setMngmAgncId: {}, setCntpntCd: {}, setFrmpapId: {}, setFrmpapStatCd: {}",
            newChangeMpPC0Response.getMngmAgncId(),
            newChangeMpPC0Response.getCntpntCd(),
            newChangeMpPC0Response.getFrmpapId(),
            frmpapStatCd);
        MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstService(List.of(mplatFormFS2Request),
            MplatformOsstServiceType.FRMPAP_ID_STATUS_CHANGE.getEventCd(), "");

        String responseType = mspPrxSoapResponse.responseType();
        String responseCode = mspPrxSoapResponse.responseCode();
        String responseBasic = mspPrxSoapResponse.responseBasic();
        String globalNo = mspPrxSoapResponse.globalNo();
        String rsltCd = "";
        String rsltMsg = "";
        log.debug("FS2 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}", responseType, responseCode, responseBasic, globalNo);

        MplatFormFS2Response mplatFormFS2Response = new MplatFormFS2Response();
        log.debug("mspPrxSoapResponse.rawXml : ", mspPrxSoapResponse.rawXml());
        try {
            mplatFormFS2Response = XmlConvertUtils.xmlReturnParser(mspPrxSoapResponse.rawXml(), MplatFormFS2Response.class);
            if ("N".equals(responseType)) {
                rsltCd = mplatFormFS2Response.getOutDto().getRsltCd(); //N 이 성공?
                rsltMsg = mplatFormFS2Response.getOutDto().getRsltMsg();
            }
            log.debug("FS2 >> rsltCd: {}, rsltMsg: {}", rsltCd, rsltMsg);
        } catch (Exception e) {
            throw new SimpleDomainException("서식지 상태변경에 실패했습니다.");
        }

        formStatusResponse.setRsltCd(rsltCd);
        formStatusResponse.setRsltMsg(rsltMsg);
        return formStatusResponse;
    }

    /**
     * 개통전 사전체크 진행여부 확인
     */
    public MpPreCheckResponse getNewChangeMpPreCheckResult(MpPreCheckRequest mpPreCheckRequest) {
        MpPreCheckResponse mpPreCheckResponse = new MpPreCheckResponse();
        if (mpPreCheckRequest == null || mpPreCheckRequest.getPrgrStatCd() == null || mpPreCheckRequest.getRequestKey() == null) {
            mpPreCheckResponse.setRsltCd("F");
            mpPreCheckResponse.setRsltMsg("입력값 부족");
        } else {
            log.debug("개통전 사전체크 진행여부 확인 >> PrgrStatCd: {}, ResNo: {}", mpPreCheckRequest.getPrgrStatCd(), mpPreCheckRequest.getResNo());
            mpPreCheckResponse = formCommReadMapper.selectMpPreCheckResult(mpPreCheckRequest);
            if (mpPreCheckResponse == null) {
                mpPreCheckResponse = new MpPreCheckResponse();
                mpPreCheckResponse.setRsltCd("F");
                mpPreCheckResponse.setRsltMsg("개통 전 사전체크 미진행");
            } else {
                if ("0000".equals(mpPreCheckResponse.getRsltCd())) {
                    mpPreCheckResponse.setRsltCd("S");
                    mpPreCheckResponse.setRsltMsg("개통 전 사전체크 진행결과 성공");
                } else {
                    mpPreCheckResponse.setRsltCd("F");
                }
            }
        }
        return mpPreCheckResponse;
    }

}
