package com.ktmmobile.msf.domains.form.form.common.service;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormOsstServerAdapter;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlVO;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.form.common.dto.McpRequestOsstRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AbuseImeiHistDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MnpOsstRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeNUInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeNUInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;
import com.ktmmobile.msf.domains.shared.form.common.generate.application.port.out.GenerateKeyRepository;

/**
 * 신청서 일련번호 생성 등
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FormCommService {

    private final GenerateKeyRepository generateKeyRepository;
    private final MsfMplatFormOsstServerAdapter mplatFormOsstServerAdapter;
    private final FormCommReadMapper formCommReadMapper;
    private final FormCommWriteMapper formCommWriteMapper;
    private final AgencyCacheReader agencyCacheReader;
    private final McpApiClient mcpApiClient;
    private final NewChangeReadMapper newChangeReadMapper;

    //SQ_RCP_RES_NO_01 생성 ( MSF_REQUEST.RES_NO )
    public String generateResNo() {
        return generateKeyRepository.getGeneratedResNo();
        //return formCommMapper.generateResNo();
        // return newChangeReadMapper.generateSmartResNo(); //스마트에서 오픈전까지만 임시로 사용
    }

    //SQ_RCP_REQUEST_KEY_01 생성 ( MSF_REQUEST.REQUEST_KEY )
    public long generateRequestKey() {
        return generateKeyRepository.getGeneratedRequestKey();
        //return formCommMapper.generateRequestKey();
        // return newChangeReadMapper.generateSmartRequestKey(); //스마트에서 오픈전까지만 임시로 사용
    }

    //NMCP_CUST_REQUEST_SEQ 생성
    public long getCustRequestSeq() {
        return generateKeyRepository.getGeneratedCustRequestSeq();
        //return formCommMapper.getCustRequestSeq();
        // return newChangeReadMapper.getSmartCustRequestSeq(); //스마트에서 오픈전까지만 임시로 사용
    }

    //SQ_REQUEST_STATE_SEQ 생성 ( MSF_REQUEST_STATE.SQ_REQUEST_STATE_SEQ )
    public long generateRequestStateSeq() {
        return generateKeyRepository.getGeneratedRequestStateSeq();
        //return formCommMapper.generateRequestStateSeq();
        // return newChangeReadMapper.generateSmartRequestStateSeq(); //스마트에서 오픈전까지만 임시로 사용
    }

    /**
     * 사용자조직에 해당하는 대리점 조회
     * 사용하는 곳 : 대리점을 선택하지 않은 경우에도 조회해야함.
     * 1. getdefault(최초진입)
     * 2. 휴대폰목록조회의 매장재고조회에서 KT조직코드 추출
     * 3.
     */
    public List<AgentInfoResponse> getAgentList(AgentInfoRequest request) {
        String loginShopCode = AuthenticationUtils.getShopCode();
        request.setShopOrgnId(loginShopCode); //로그인 세션의 대리점코드
        List<AgentInfoResponse> responseDto = formCommReadMapper.selectAgentInfo(request);

        //대량 개통 법인 가능여부 조회
        if (responseDto != null && !responseDto.isEmpty()) {
            List<String> orgnIds = responseDto.stream()
                .map(AgentInfoResponse::getOrgnId)
                .filter(StringUtils::hasText)
                .distinct()
                .collect(Collectors.toList());

            //KTM 대리점코드 여부에 따라 처리
            if (!orgnIds.isEmpty()) {
                List<AgentInfoResponse> bulkOpenYnList = newChangeReadMapper.selectBulkCorporateOpenYnList(loginShopCode, orgnIds);

                //MSF_STOR_OPEN_LIMIT_TXN 조회된 경우
                if (bulkOpenYnList != null && !bulkOpenYnList.isEmpty()) {
                    Map<String, String> bulkOpenYnMap = bulkOpenYnList.stream()
                        .filter(item -> item != null && StringUtils.hasText(item.getOrgnId()))
                        .collect(Collectors.toMap(
                            AgentInfoResponse::getOrgnId,
                            AgentInfoResponse::getCanBulkCorporateOpenYn,
                            (v1, v2) -> v1
                        ));
                    for (AgentInfoResponse agent: responseDto) {
                        if (agent != null && StringUtils.hasText(agent.getOrgnId())) {
                            String canBulkCorporateOpenYn = bulkOpenYnMap.getOrDefault(agent.getOrgnId(), "N");
                            agent.setCanBulkCorporateOpenYn(canBulkCorporateOpenYn);
                        } else if (agent != null) {
                            agent.setCanBulkCorporateOpenYn("N");
                        }
                    }
                } else {
                    for (AgentInfoResponse agent: responseDto) {
                        if (agent != null) {
                            agent.setCanBulkCorporateOpenYn("N");
                        }
                    }
                }
            } else {
                for (AgentInfoResponse agent: responseDto) {
                    if (agent != null) {
                        agent.setCanBulkCorporateOpenYn("N");
                    }
                }
            }
        }

        applyAgencyCacheInfo(responseDto, loginShopCode);
        return responseDto;
    }

    private void applyAgencyCacheInfo(List<AgentInfoResponse> responseDto, String loginShopCode) {
        if (responseDto == null || responseDto.isEmpty()) {
            return;
        }

        String loginShopName = agencyCacheReader.getAgency(loginShopCode)
            .map(AgencyCache::organizationName)
            .filter(StringUtils::hasText)
            .orElseGet(() -> {
                try {
                    return AuthenticationUtils.getShopName();
                } catch (RuntimeException ignored) {
                    return "";
                }
            });

        for (AgentInfoResponse response: responseDto) {
            if (response == null) {
                continue;
            }
            response.setShopOrgnId(loginShopCode);
            response.setShopNm(loginShopName);
            response.setRealShopNm(loginShopName);
            response.setManagerNm(AuthenticationUtils.getUser().getUserName());
            String organizationId = StringUtils.hasText(response.getOrgnId())
                ? response.getOrgnId()
                : response.getKtOrgId();
            if (!StringUtils.hasText(organizationId)) {
                continue;
            }
            agencyCacheReader.getAgency(organizationId).ifPresent(agency -> applyAgencyCache(response, agency));
        }
    }

    private void applyAgencyCache(AgentInfoResponse response, AgencyCache agency) {
        if (StringUtils.hasText(agency.telephone())) {
            response.setTelephone(agency.telephone());
        }
        if (StringUtils.hasText(agency.representativeTelephone())) {
            response.setRepresentativeTelephone(agency.representativeTelephone());
        }
    }

    /**
     * MP호출 :: sendOsstService
     * 번호이동 사전동의 요청 NP1 / NP3
     */
    public MSimpleOsstXmlVO sendOsstService(MnpOsstRequest osstReqDto, String eventCd) throws SelfServiceException, SocketTimeoutException {
        MSimpleOsstXmlVO simpleOsstXmlVO = new MSimpleOsstXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("appEventCd", eventCd);
        param.put("npTlphNo", osstReqDto.getNpTlphNo());
        param.put("moveCompany", osstReqDto.getBchngNpCommCmpnCd());
        param.put("cstmrType", osstReqDto.getCstmrTypeCd());
        param.put("selfCertType", osstReqDto.getCustIdntNoIndCd());
        param.put("custIdntNo", osstReqDto.getCustIdntNo());
        param.put("cstmrName", osstReqDto.getCustNm());
        param.put("crprNo", osstReqDto.getCrprNo());
        param.put("indvBizrYn", osstReqDto.getIndvBizrYn());
        param.put("fornBrthDate", osstReqDto.getFornBrthDate());
        //param.put("slsCmpnCd", osstReqDto.getSlsCmpnCd()); //판매회사코드 (INL) - 고정값
        param.put("cntpntShopId", osstReqDto.getAgentCd()); //대리점코드

        mplatFormOsstServerAdapter.callService(param, simpleOsstXmlVO, 100000);
        return simpleOsstXmlVO;
    }


    //부정사용주장 단말 확인 (메인 메소드) - imei 갯수만큼 돌겠지요. 해봤자 2개니까~~ 최대 2회
    //사용 : 휴대폰 일련번호 유효성체크 / eSIM 유효성체크
    public boolean checkAbuseImeiList(List<String> imeis) {
        boolean isAbuse = false;

        for (String imei: imeis) {
            if (!StringUtils.hasLength(imei)) {
                continue;
            }

            //부정사용주장 단말 확인 후 부정사용단말인 경우 NMCP_ABUSE_IMEI_HIST 테이블에 저장
            isAbuse = this.existsAbuseImei(imei);
            if (isAbuse) {
                this.saveAbuseImeiHist(imei);
                break;
            }
        }
        return isAbuse;
    }

    //부정사용주장 단말 조회 >> 어디로 따로 옮겨야하려나?
    //apiInterfaceServer + "/appform/existsAbuseImei" >> parameter :: iccId
    //사용 : 휴대폰 일련번호 유효성체크
    private boolean existsAbuseImei(String imei) {
        boolean exits = false;
        exits = mcpApiClient.post(
            "/appform/existsAbuseImei",
            imei,
            Boolean.class
        );
        return exits;
    }

    //부정사용 주장 저장
    //사용 : 휴대폰 일련번호 유효성체크
    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    private void saveAbuseImeiHist(String imei) {
        //스마트 로그인한 사용자 아이디로 저장필요
        //String userId = ""; //개발기준 USER_ID 빈값으로 필수값 아님
        AbuseImeiHistDto abuseImeiHistDto = new AbuseImeiHistDto();
        abuseImeiHistDto.setImei(imei);
        abuseImeiHistDto.setAccessIp(RequestUtils.getClientIp());
        abuseImeiHistDto.setUserId(AuthenticationUtils.getUser().getUserId());
        formCommWriteMapper.insertAbuseImeiHist(abuseImeiHistDto);
    }

    //불량유심 사용제한한 사용자 업데이트 (스마트에도 해당이 되려나? 사용자는 실제 가입하려는 사람이 아니라 판매자인데..)
    //사용 : USIM 유효성체크
    public int setFailUsims(String iccId) {
        int updateFailUsim = 0;
        updateFailUsim = mcpApiClient.post(
            "/storeUsim/updateFailUsim",
            iccId,
            int.class
        );
        return updateFailUsim;

        //apiInterfaceServer + "/storeUsim/updateFailUsim" >> parameter :: iccId
        //건수를 받음.
    }

    //USIM 접점코드 조회
    //사용 : USIM 유효성체크
    public String getUsimOrgnId(String iccId) {
        String orgnId = "";
        orgnId = mcpApiClient.post(
            "/msp/sellUsimMgmtOrgnId",
            iccId,
            String.class
        );
        return orgnId;

        //apiInterfaceServer + "/msp/sellUsimMgmtOrgnId" >> parameter :: iccId
        //건수를 받음.
    }

    //MCP_REQUEST_OSST 에서 조건에 맞는 건수 확인하기
    //param : requestKey(신청서번호) prgrStatCd(진행상태코드) rsltCd(결과값-선택사항)
    public int getOsstCount(McpRequestOsstRequest request) {
        int osstCount = 0;
        if (StringUtils.hasText(request.getPrgrStatCd())) {
            osstCount = formCommReadMapper.selectOsstCount(request);
        }
        return osstCount;
    }

    //MCP_REQUEST_OSST 에서 조건에 맞는 mvnoOrdNo 를 MCP_REQUEST 에서 resNo 로 추출
    public String getMvnoOrdNo(McpRequestOsstRequest request) {
        return formCommReadMapper.selectMvnoOrdNo(request);
    }

    //MCP_REQUEST_OSST 에서 OSST_ORD_NO 조회
    public String getOsstOrdNo(McpRequestOsstRequest request) {
        return formCommReadMapper.selectOsstOrdNo(request);
    }

    //신규가입 희망번호조회 Parameter DATA SET
    public NewChangeNUInfoResponse getXmlMessageNU1(String resNo) {
        return formCommReadMapper.selectXmlMessageNU1(resNo);
    }

    //신규가입 희망번호예약 Parameter DATA SET
    public NewChangeNUInfoResponse getXmlMessageNU2(NewChangeNUInfoRequest request) {
        return formCommReadMapper.selectXmlMessageNU2(request);
    }


}
