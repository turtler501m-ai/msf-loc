package com.ktmmobile.msf.domains.form.common.mplatform;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.CommCdInfoRes;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscDataSharingResDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpAddSvcInfoParamDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpFarMonBillingInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpFarMonDetailInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.RegSvcChgRes;
import com.ktmmobile.msf.domains.form.common.mplatform.provider.MplatFormResponseProvider;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpCommonXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscRegSvcCanChgInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscSdsInfoVo;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscSpnsrItgInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpNumChgeListVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSvcContIpinVO;
import com.ktmmobile.msf.domains.form.common.service.FCommonSvc;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;

@Slf4j
@Service
public class MsfMplatFormService {

    @Lazy
    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private MsfMplatFormServerAdapter mplatFormServerAdapter;

    @Autowired
    private MplatFormResponseProvider mplatFormResponseProvider;

    /**
     * X01 가입 정보를 조회한다.
     * 서비스 변경 화면의 가입 정보 조회.
     */
    public MpPerMyktfInfoVO perMyktfInfo(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        MpPerMyktfInfoVO vo = new MpPerMyktfInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X01");
        mplatFormResponseProvider.callService(param, vo, 1);
        return vo;
    }

    /**
     * X15 월별 청구 요약 정보를 조회한다.
     * 해지 예상 요금 계산.
     */
    public MpFarMonBillingInfoDto farMonBillingInfoDto(String ncn, String ctn, String custId, String productionDate)
        throws SelfServiceException, SocketTimeoutException {
        MpFarMonBillingInfoDto vo = new MpFarMonBillingInfoDto();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X15");
        param.put("productionDate", StringUtil.NVL(productionDate, ""));
        log.debug("[farMonBillingInfoDto] X15 input: ncn={}, ctn={}, custId={}, productionDate={}, userid={}",
            ncn, ctn, custId, param.get("productionDate"), param.get("userid"));
        mplatFormResponseProvider.callService(param, vo, 15);
        return vo;
    }

    /**
     * X16 월별 청구 상세 정보를 조회한다.
     * 해지 예상 잔여 할부금 계산.
     */
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
        log.debug(
            "[farMonDetailInfoDto] X16 input: ncn={}, ctn={}, custId={}, billSeqNo={}, billDueDateList={}, billMonth={}, billStartDate={}, billEndDate={}, userid={}",
            ncn,
            ctn,
            custId,
            param.get("billSeqNo"),
            param.get("billDueDateList"),
            param.get("billMonth"),
            param.get("billStartDate"),
            param.get("billEndDate"),
            param.get("userid"));

        mplatFormResponseProvider.callService(param, vo, 16);

        return vo;
    }

    /**
     * X18 실시간 요금 정보를 조회한다.
     * 해지 화면의 실시간 사용 요금 조회.
     */
    public MpFarRealtimePayInfoVO farRealtimePayInfo(String ncn, String ctn, String custId)
        throws SelfServiceException, SocketTimeoutException {
        MpFarRealtimePayInfoVO vo = new MpFarRealtimePayInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X18");

        log.debug("[farRealtimePayInfo] X18 input: ncn={}, ctn={}, custId={}, userid={}",
            ncn, ctn, custId, param.get("userid"));

        mplatFormResponseProvider.callService(param, vo, 18);

        log.debug("[farRealtimePayInfo] X18 PRX call end: ncn={}, success={}, searchDay={}, searchTime={}, sumAmt={}, listSize={}",
            ncn, vo.isSuccess(), vo.getSearchDay(), vo.getSearchTime(), vo.getSumAmt(),
            vo.getList() == null ? 0 : vo.getList().size());

        return vo;
    }

    /**
     * X21 부가서비스를 변경한다.
     * 부가서비스 가입/해지 처리.
     */
    public MpRegSvcChgVO regSvcChg(
        String ncn, String ctn, String custId,
        String soc, String ftrNewParam
    ) throws SocketTimeoutException {
        MpRegSvcChgVO vo = new MpRegSvcChgVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X21");
        param.put("soc", StringUtil.NVL(soc, ""));
        param.put("ftrNewParam", StringUtil.NVL(ftrNewParam, ""));
        mplatFormResponseProvider.callService(param, vo, 21);

        return vo;
    }

    /**
     * X21 부가서비스 변경 결과를 예외 없이 응답 객체로 반환한다.
     * 요금제 변경 부가서비스 처리.
     */
    public RegSvcChgRes regSvcChgNeTrace(MyPageSearchDto searchVO, String soc, String ftrNewParam) {
        RegSvcChgRes vo = new RegSvcChgRes();

        try {
            HashMap<String, String> param = getParamMap(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), "X21");
            param.put("soc", StringUtil.NVL(soc, ""));
            param.put("ftrNewParam", StringUtil.NVL(ftrNewParam, ""));

            mplatFormResponseProvider.callServiceNe(param, vo, 30000, 21);

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
     * X23 요금 납부 방법 변경 가능 정보를 조회한다.
     * 서비스 변경 및 데이터 쉐어링 화면의 납부 정보 확인.
     */
    public MpFarChangewayInfoVO farChangewayInfo(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        MpFarChangewayInfoVO vo = new MpFarChangewayInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X23");
        mplatFormResponseProvider.callService(param, vo, 23);
        return vo;
    }

    /**
     * X31 변경 가능한 전화번호 목록을 조회한다.
     * 번호 변경 화면의 후보 번호 조회.
     */
    public MpNumChgeListVO numChgeList(String ncn, String ctn, String custId, String chkCtn) throws SocketTimeoutException {
        MpNumChgeListVO vo = new MpNumChgeListVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X31");
        param.put("chkCtn", StringUtil.NVL(chkCtn, ""));

        mplatFormResponseProvider.callService(param, vo, 31);

        return vo;
    }

    /**
     * X38 부가서비스 해지 가능 여부를 조회한다.
     * 부가서비스 해지 사전 확인.
     */
    public MpMoscRegSvcCanChgInVO moscRegSvcCanChg(String ncn, String ctn, String custId, String soc) throws SocketTimeoutException {
        MpMoscRegSvcCanChgInVO vo = new MpMoscRegSvcCanChgInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X38");
        param.put("soc", StringUtil.NVL(soc, ""));
        mplatFormResponseProvider.callService(param, vo, 38);

        return vo;
    }

    /**
     * X38 상품 이력 순번 기준으로 부가서비스 해지 가능 여부를 조회한다.
     * 부가서비스 해지 사전 확인 보정 처리.
     */
    public MpMoscRegSvcCanChgInVO moscRegSvcCanChgSeq(String ncn, String ctn, String custId, String soc, String prodHstSeq)
        throws SocketTimeoutException {
        MpMoscRegSvcCanChgInVO vo = new MpMoscRegSvcCanChgInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X38");
        param.put("soc", StringUtil.NVL(soc, ""));
        param.put("prodHstSeq", StringUtil.NVL(prodHstSeq, ""));
        mplatFormResponseProvider.callService(param, vo, 38);

        return vo;
    }

    /**
     * X38 부가서비스 해지 가능 여부를 요금제 정책 검증 후 조회한다.
     * 요금제 변경 시 기존 부가서비스 해지 가능 여부 확인.
     */
    public RegSvcChgRes moscRegSvcCanChgNeTrace(MyPageSearchDto searchVO, String soc) {
        RegSvcChgRes vo = new RegSvcChgRes();

        boolean isCheck = true;

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
     * X49 이메일 청구서 정보를 조회한다.
     * 신규 가입/서비스 변경 유효성 확인.
     */
    public MpMoscBilEmailInfoInVO kosMoscBillInfo(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MpMoscBilEmailInfoInVO vo = new MpMoscBilEmailInfoInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X49");
        mplatFormResponseProvider.callService(param, vo, 49);

        return vo;
    }

    /**
     * X54 스폰서 약정 통합 정보를 조회한다.
     * 해지 예상 약정/위약금 조회.
     */
    public MpMoscSpnsrItgInfoInVO kosMoscSpnsrItgInfo(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MpMoscSpnsrItgInfoInVO vo = new MpMoscSpnsrItgInfoInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X54");
        log.debug("[kosMoscSpnsrItgInfo] X54 input: ncn={}, ctn={}, custId={}, userid={}",
            ncn, ctn, custId, param.get("userid"));
        mplatFormResponseProvider.callService(param, vo, 54);

        return vo;
    }

    /**
     * X62 선택약정 정보를 조회한다.
     * 해지 화면의 선택약정 정보 확인.
     */
    public MpMoscSdsInfoVo moscSdsInfo(String ncn, String ctn, String custId) throws SocketTimeoutException {
        MpMoscSdsInfoVo vo = new MpMoscSdsInfoVo();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X62");
        mplatFormResponseProvider.callService(param, vo, 62);
        return vo;
    }

    /**
     * X71 데이터 쉐어링 회선 목록을 조회한다.
     * 데이터 쉐어링 관리 화면의 회선 목록 조회.
     */
    public MoscDataSharingResDto mosharingList(String custId, String ncn, String ctn) throws SocketTimeoutException {
        MoscDataSharingResDto moscDataSharingResDto = new MoscDataSharingResDto();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X71");
        mplatFormResponseProvider.callServiceNe(param, moscDataSharingResDto, 30000, 71);

        return moscDataSharingResDto;
    }

    /**
     * X70 데이터 쉐어링 가입 또는 해지를 저장한다.
     * 데이터 쉐어링 가입/해지 처리.
     */
    public MpCommonXmlVO moscDataSharingSave(
        String custId, String ncn,
        String ctn, String opmdSvcNo
        , String opmdWorkDivCd
    ) throws SocketTimeoutException {
        MpCommonXmlVO vo = new MpCommonXmlVO();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X70");
        param.put("opmdSvcNo", opmdSvcNo);
        param.put("opmdWorkDivCd", opmdWorkDivCd);

        mplatFormResponseProvider.callService(param, vo, 60000, 70);

        return vo;

    }

    /**
     * X69 데이터 쉐어링 가능 여부를 확인한다.
     * 데이터 쉐어링 가입 가능 여부 사전 확인.
     */
    public MoscDataSharingResDto moscDataSharingChk(String custId, String ncn, String ctn, String crprCtn) throws SocketTimeoutException {

        MoscDataSharingResDto moscDataSharingResDto = new MoscDataSharingResDto();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X69");
        if (StringUtils.isNotBlank(crprCtn)) {
            param.put("crprCtn", crprCtn);
        }

        mplatFormResponseProvider.callServiceNe(param, moscDataSharingResDto, 30000, 69);

        return moscDataSharingResDto;

    }

    /**
     * X97 부가서비스 조회 파라미터 응답을 조회한다.
     * 부가서비스 가입/변경 화면의 부가서비스 파라미터 조회.
     */
    public MpAddSvcInfoParamDto getAddSvcInfoParamDto(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {

        MpAddSvcInfoParamDto vo = new MpAddSvcInfoParamDto();

        HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X97");

        mplatFormResponseProvider.callService(param, vo, 97);

        return vo;

    }

    /**
     * Y29 공통 코드 정보를 조회한다.
     * 번호이동 통신사 공통 코드 조회.
     */
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
     * Y39 서비스 계약 아이핀 CI 정보를 조회한다.
     * 데이터 쉐어링 처리 후 서비스 계약 본인 식별 정보 확인.
     */
    public MpSvcContIpinVO MoscSvcContService(String osstOrdNo) throws SocketTimeoutException {

        MpSvcContIpinVO vo = new MpSvcContIpinVO();

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", "Y39");
        param.put("osstOrdNo", osstOrdNo);

        mplatFormResponseProvider.callServiceEtc(param, vo, 30000, "Y39");
        return vo;
    }

    /**
     * M-Platform 호출에 공통으로 필요한 기본 파라미터를 생성한다.
     * 이 서비스의 M-Platform 호출 공통 파라미터 구성.
     */
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
            log.error(e.getMessage());
        }

        return param;
    }

    /**
     * 현재 세션의 사용자 ID를 조회한다.
     * M-Platform userid 파라미터 생성.
     */
    private String sesUserId() {
        String retId;
        try {
            retId = StringUtil.NVL(AuthenticationUtils.getUser().getUserId(), "");
        } catch (RuntimeException e) {
            retId = "";
        }
        log.debug("sesUserId:{}", retId);
        return retId;
    }

}
