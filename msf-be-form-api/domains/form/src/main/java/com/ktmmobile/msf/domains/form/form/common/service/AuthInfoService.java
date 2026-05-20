package com.ktmmobile.msf.domains.form.form.common.service;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormOsstServerAdapter;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.KnoteScanInfoFs0Vo;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlFs0VO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlFs1VO;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.AuthInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoResponse;

/**
 * KTM모바일 고객인증, 신분증 목록 조회
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthInfoService {

    private final AuthInfoReadMapper authInfoReadMapper;
    private final MsfMplatFormOsstServerAdapter mplatFormOsstServerAdapter;

    //KTM모바일 고객인증
    public FormResponse<MspJuoSubInfoResponse> getJuoSubInfo(MspJuoSubInfoRequest request) {
        log.debug("★ KTM모바일 고객인증 ★ customerLinkName: {}, customerSsn: {}, subscriberNo: {}",
            request.getCustomerLinkName(),
            request.getCustomerSsn(),
            request.getSubscriberNo());

        //MspJuoSubInfoResponse data = authInfoReadMapper.selectKtmCustomer(condition);
        //return data;

        MspJuoSubInfoResponse data = authInfoReadMapper.selectKtmCustomer(request);
        if (data == null) {
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_FAIL);
        }
        return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_SUCCESS, data);
    }


    //Knote 신분증 목록 조회 (서식지 목록조회 FS0)
    public FormResponse<MSimpleOsstXmlFs0VO> getIdList(KnoteScanInfoRequest knoteScanInfoRequest) {
        log.debug("★ Knote 신분증 목록 조회 ★ mngmAgncId: {}", knoteScanInfoRequest.getAgentCd());

        String storCd = AuthenticationUtils.getShopCode();
        String agentCd = knoteScanInfoRequest.getAgentCd();
        if (agentCd == null || agentCd.equals("")) {
            agentCd = AuthenticationUtils.getAgentCode(); //대리점코드
        }

        KnoteScanInfoRequest request = new KnoteScanInfoRequest();
        String requestScanDate = "";
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        requestScanDate = today.format(formatter);

        //request.setMngmAgncId(AuthenticationUtils.getAgentCode()); //개통요청 대리점코드
        request.setMngmAgncId(agentCd); //개통요청 대리점코드
        request.setCntpntCd(storCd); //개통요청 접점코드 : Optional
        request.setRetvStrtDt(requestScanDate); //조회시작일시
        request.setRetvEndDt(requestScanDate); //조회종료일시
        request.setSvcApyTrtStatCd("1"); //처리상태조회 1:접수, 2:진행, 3:완료, 4:취소
        //request.setRetvSeq("0"); //Optional :: 미 입력 시 0
        //request.setSvcApyTrtStatCd("40"); //Optional :: 미 입력시 40

        log.debug("★ Knote 신분증 목록 조회 (prx) ★ mngmAgncId: {}, cntpntCd: {}, retvStrtDt: {}, retvEndDt: {}, svcApyTrtStatCd: {}",
            request.getMngmAgncId(),
            request.getCntpntCd(),
            request.getRetvStrtDt(),
            request.getRetvEndDt(),
            request.getSvcApyTrtStatCd());

        //prx 오픈 시~ 주석해제 후 진행예정
        /*MSimpleOsstXmlFs0VO data = null;
        //KnoteScanInfoFs0Vo knoteScanInfoFs0Vo = new KnoteScanInfoFs0Vo();
        try {
            data = this.sendOsstFs0Service(request);
        } catch (SocketTimeoutException e) {
        }*/

        //-------------------- 로컬 테스트 START ----------------------
        MSimpleOsstXmlFs0VO knoteScanIdList = new MSimpleOsstXmlFs0VO();

        List<KnoteScanInfoFs0Vo> list = Stream.of(
            new String[][] {
                {"8611111", "황순철", "20260519103045", "0x62E50320B59E11EE8A320080C74455C601", "1"},
                {"8608302", "김세희", "20260519103045", "0x62E50320B59E11EE8A320080C74455C602", "1"},
                {"1406303", "박준서", "20260519103045", "0x62E50320B59E11EE8A320080C74455C603", "1"},
                {"8403235", "JIANG YONG", "20260519103045", "0x62E50320B59E11EE8A320080C74455C604", "5"},
                {"9907065", "ORIPOV SHOKHBOZBEK SHUKHRATZHON UGLI", "20260519103045", "0x62E50320B59E11EE8A320080C74455C605", "5"},
                {"0311167", "LE QUOC KHANH", "20260519103045", "0x62E50320B59E11EE8A320080C74455C606", "5"},
                {"5504151", "김광한", "20260519103045", "0x62E50320B59E11EE8A320080C74455C607", "1"},
                {"9907065", "ORIPOV SHOKHBOZBEK SHUKHRATZHON UGLI", "20260519103045", "0x62E50320B59E11EE8A320080C74455C608", "5"}
            }
        ).map(v -> {
            KnoteScanInfoFs0Vo tmpData = new KnoteScanInfoFs0Vo();
            tmpData.setCustIdntNo(v[0]);
            tmpData.setCustNm(v[1]);
            tmpData.setWapplRegDate(v[2]);
            tmpData.setFrmpapId(v[3]);
            tmpData.setCustIdntNoIndCd(v[4]);
            tmpData.setCntpntCd("V000072931");
            tmpData.setMngmAgncId("1100019352");
            return tmpData;
        }).toList();
        knoteScanIdList.setList(list);
        //-------------------- 로컬 테스트 END ----------------------

        return FormResponse.of(ResponseMessage.SUCCESS, knoteScanIdList);
    }

    //Knote 신분증 상태 조회 (서식지 상태조회 FS1)
    public FormResponse<KnoteScanInfoResponse> checkIdStatus(KnoteScanInfoRequest knoteScanInfoRequest) {

        log.debug("★ Knote 신분증 상태 조회 ★ mngmAgncId: {}, cntpntCd: {}, frmpapId: {}",
            knoteScanInfoRequest.getAgentCd(),
            knoteScanInfoRequest.getCntpntCd(),
            knoteScanInfoRequest.getFrmpapId());

        //선택한 신분증이 없음.
        String frmpapId = knoteScanInfoRequest.getFrmpapId();
        if (!StringUtils.hasText(frmpapId)) {
            return FormResponse.of(ResponseMessage.NO_DATA);
        }

        String storCd = AuthenticationUtils.getShopCode(); //로그인세션의 매장코드
        String agentCd = knoteScanInfoRequest.getAgentCd();
        if (agentCd == null || agentCd.equals("")) {
            agentCd = AuthenticationUtils.getAgentCode(); //대리점코드
        }

        KnoteScanInfoRequest request = new KnoteScanInfoRequest();
        request.setMngmAgncId(agentCd); //대리점코드
        request.setCntpntCd(storCd); //판매점코드
        request.setFrmpapId(frmpapId);

        //request.setMngmAgncId(AuthenticationUtils.getAgentCode()); //개통요청 대리점코드
        //request.setCntpntCd(AuthenticationUtils.getShopCode()); //개통요청 접점코드
        //frmpapId : 서식지 아이디는 선택정보

        log.debug("★ Knote 신분증 상태 조회 (prx) ★ mngmAgncId: {}, cntpntCd: {}, frmpapId: {}",
            request.getMngmAgncId(),
            request.getCntpntCd(),
            request.getFrmpapId());

        KnoteScanInfoResponse data = new KnoteScanInfoResponse();
        //MSimpleOsstXmlFs1VO simpleOsstXmlFs1VO = new MSimpleOsstXmlFs1VO();
        /*try {
            data = this.sendOsstFs1Service(request);
        } catch (SocketTimeoutException e) {

        }*/

        //-------------------- 삭제필요!!!!!!!!!!!!!!!!! PRX 연동시작하면~~~~~~~~~~~~~~~~~~~~~
        if ("0x62E50320B59E11EE8A320080C74455C600".equals(request.getFrmpapId())) { //Error 처리
            return FormResponse.of(ResponseMessage.NO_DATA);
        } else if ("0x62E50320B59E11EE8A320080C74455C601".equals(request.getFrmpapId())) {
            data.setKnoteIdentityScanCstmrNm("황순철"); // CustNm
            data.setKnoteIdentityEssNo("8611111162611"); // RealCustIdntNo
            data.setCustNm("황순철"); //고객명 서식지 신청고객명
            data.setRealCustIdntNo("8611111162611"); //실명인증 식별번호 >> 명의자 식별번호는 암호화되어 넘어오므로 꼭 복호화해서 넘겨야함?
            data.setKnoteIdentityScanDt("20260519103014"); //서식지 등록일시 : wapplRegDate >> yyyyMMddHHmmss
            data.setCustIdntNoIndCd("1");
        } else if ("0x62E50320B59E11EE8A320080C74455C602".equals(request.getFrmpapId())) {
            data.setKnoteIdentityScanCstmrNm("김세희"); // CustNm
            data.setKnoteIdentityEssNo("8608302008591"); // RealCustIdntNo
            data.setCustNm("김세희"); //고객명 서식지 신청고객명
            data.setRealCustIdntNo("8608302008591"); //실명인증 식별번호 >> 명의자 식별번호는 암호화되어 넘어오므로 꼭 복호화해서 넘겨야함?
            data.setKnoteIdentityScanDt("20260519103024"); //서식지 등록일시 : wapplRegDate >> yyyyMMddHHmmss
            data.setCustIdntNoIndCd("1");
        } else if ("0x62E50320B59E11EE8A320080C74455C603".equals(request.getFrmpapId())) {
            data.setKnoteIdentityScanCstmrNm("박준서"); // CustNm
            data.setKnoteIdentityEssNo("1406303919114"); // RealCustIdntNo
            data.setCustNm("박준서"); //고객명 서식지 신청고객명
            data.setRealCustIdntNo("1406303919114"); //실명인증 식별번호 >> 명의자 식별번호는 암호화되어 넘어오므로 꼭 복호화해서 넘겨야함?
            data.setKnoteIdentityScanDt("20260519103034"); //서식지 등록일시 : wapplRegDate >> yyyyMMddHHmmss
            data.setCustIdntNoIndCd("1");
        } else if ("0x62E50320B59E11EE8A320080C74455C604".equals(request.getFrmpapId())) {
            data.setKnoteIdentityScanCstmrNm("JIANG YONG"); // CustNm
            data.setKnoteIdentityEssNo("8403235780596"); // RealCustIdntNo
            data.setCustNm("JIANG YONG"); //고객명 서식지 신청고객명
            data.setRealCustIdntNo("8403235780596"); //실명인증 식별번호 >> 명의자 식별번호는 암호화되어 넘어오므로 꼭 복호화해서 넘겨야함?
            data.setKnoteIdentityScanDt("20260519103051"); //서식지 등록일시 : wapplRegDate >> yyyyMMddHHmmss
            data.setCustIdntNoIndCd("5");
        } else if ("0x62E50320B59E11EE8A320080C74455C605".equals(request.getFrmpapId())) {
            data.setKnoteIdentityScanCstmrNm("ORIPOV SHOKHBOZBEK SHUKHRATZHON UGLI"); // CustNm
            data.setKnoteIdentityEssNo("9907065700013"); // RealCustIdntNo
            data.setCustNm("ORIPOV SHOKHBOZBEK SHUKHRATZHON UGLI"); //고객명 서식지 신청고객명
            data.setRealCustIdntNo("9907065700013"); //실명인증 식별번호 >> 명의자 식별번호는 암호화되어 넘어오므로 꼭 복호화해서 넘겨야함?
            data.setKnoteIdentityScanDt("20260519103052"); //서식지 등록일시 : wapplRegDate >> yyyyMMddHHmmss
            data.setCustIdntNoIndCd("5");
        } else if ("0x62E50320B59E11EE8A320080C74455C606".equals(request.getFrmpapId())) {
            data.setKnoteIdentityScanCstmrNm("LE QUOC KHANH"); // CustNm
            data.setKnoteIdentityEssNo("0311167860011"); // RealCustIdntNo
            data.setCustNm("LE QUOC KHANH"); //고객명 서식지 신청고객명
            data.setRealCustIdntNo("0311167860011"); //실명인증 식별번호 >> 명의자 식별번호는 암호화되어 넘어오므로 꼭 복호화해서 넘겨야함?
            data.setKnoteIdentityScanDt("20260519103053"); //서식지 등록일시 : wapplRegDate >> yyyyMMddHHmmss
            data.setCustIdntNoIndCd("5");
            //data.setKnoteScanId("");
        } else if ("0x62E50320B59E11EE8A320080C74455C607".equals(request.getFrmpapId())) {
            data.setKnoteIdentityScanCstmrNm("김광한"); // CustNm
            data.setKnoteIdentityEssNo("5504151222320"); // RealCustIdntNo
            data.setCustNm("김광한"); //고객명 서식지 신청고객명
            data.setRealCustIdntNo("5504151222320"); //실명인증 식별번호 >> 명의자 식별번호는 암호화되어 넘어오므로 꼭 복호화해서 넘겨야함?
            data.setKnoteIdentityScanDt("20260519113053"); //서식지 등록일시 : wapplRegDate >> yyyyMMddHHmmss
            data.setCustIdntNoIndCd("1");
        } else if ("0x62E50320B59E11EE8A320080C74455C608".equals(request.getFrmpapId())) {
            data.setKnoteIdentityScanCstmrNm("ORIPOV SHOKHBOZBEK SHUKHRATZHON UGLI"); // CustNm
            data.setKnoteIdentityEssNo("9907065700013"); // RealCustIdntNo
            data.setCustNm("ORIPOV SHOKHBOZBEK SHUKHRATZHON UGLI"); //고객명 서식지 신청고객명
            data.setRealCustIdntNo("9907065700013"); //실명인증 식별번호 >> 명의자 식별번호는 암호화되어 넘어오므로 꼭 복호화해서 넘겨야함?
            data.setKnoteIdentityScanDt("20260519123053"); //서식지 등록일시 : wapplRegDate >> yyyyMMddHHmmss
            data.setCustIdntNoIndCd("5");
        } else {
            return FormResponse.of(ResponseMessage.NO_DATA);
        }

        data.setKnoteScanId(request.getFrmpapId());
        data.setOpnYn("N"); //개통여부
        data.setSvcApyTrtSttusCd("1"); //처리상태코드

        //-------------------- 삭제필요!!!!!!!!!!!!!!!!! PRX 연동시작하면~~~~~~~~~~~~~~~~~~~~~

        return FormResponse.of(ResponseMessage.SUCCESS, data);
    }

    //Knote 신분증 목록 조회
    private MSimpleOsstXmlFs0VO sendOsstFs0Service(KnoteScanInfoRequest request)
        throws SelfServiceException, SocketTimeoutException, McpMplatFormException {
        MSimpleOsstXmlFs0VO simpleOsstXmlFs0VO = new MSimpleOsstXmlFs0VO();
        HashMap<String, String> param = new HashMap<String, String>();
        //param.put("appEventCd", Constants.EVENT_CODE_KNOTE_SCAN_ID_LIST); //Constants 파일이 정리되면 추가 예정
        param.put("appEventCd", "FS0");
        param.put("mngmAgncId", request.getMngmAgncId());
        param.put("cntpntCd", request.getCntpntCd());
        param.put("retvStrtDt", request.getRetvStrtDt());
        param.put("retvEndDt", request.getRetvEndDt());
        param.put("svcApyTrtStatCd", request.getSvcApyTrtStatCd());
        param.put("retvSeq", request.getRetvSeq());
        param.put("retvCascnt", request.getRetvCascnt());

        mplatFormOsstServerAdapter.callService(param, simpleOsstXmlFs0VO, 100000);
        return simpleOsstXmlFs0VO;
    }

    //Knote 신분증 상태 조회
    private MSimpleOsstXmlFs1VO sendOsstFs1Service(KnoteScanInfoRequest request)
        throws SelfServiceException, SocketTimeoutException, McpMplatFormException {
        MSimpleOsstXmlFs1VO simpleOsstXmlFs1VO = new MSimpleOsstXmlFs1VO();
        HashMap<String, String> param = new HashMap<String, String>();
        //param.put("appEventCd", Constants.EVENT_CODE_KNOTE_SCAN_ID_STATUS); //Constants 파일이 정리되면 추가 예정
        param.put("appEventCd", "FS1");
        param.put("mngmAgncId", request.getMngmAgncId());
        param.put("cntpntCd", request.getCntpntCd());
        param.put("frmpapId", request.getFrmpapId());

        mplatFormOsstServerAdapter.callService(param, simpleOsstXmlFs1VO, 100000);
        return simpleOsstXmlFs1VO;
    }


}
