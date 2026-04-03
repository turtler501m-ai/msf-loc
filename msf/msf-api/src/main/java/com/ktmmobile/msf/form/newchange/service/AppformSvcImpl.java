package com.ktmmobile.msf.form.newchange.service;

import com.ktmmobile.msf.form.newchange.dao.AppformDao;
import com.ktmmobile.msf.form.newchange.dao.FormDtlDao;
import com.ktmmobile.msf.form.newchange.dto.*;
import com.ktmmobile.msf.system.cert.dto.CertDto;
import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.system.common.dto.NowDlvryReqDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.dto.db.*;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.exception.McpMplatFormException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.system.common.mplatform.MplatFormOsstServerAdapter;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.dto.NowDlvryResDto;
import com.ktmmobile.msf.system.common.mplatform.vo.*;
import com.ktmmobile.msf.system.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.system.common.service.FCommonSvc;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.util.DateTimeUtil;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;
import com.ktmmobile.msf.system.common.legacy.etc.dto.GiftPromotionBas;
import com.ktmmobile.msf.system.common.legacy.etc.dto.GiftPromotionDtl;
import com.ktmmobile.msf.event.service.CoEventSvc;
import com.ktmmobile.msf.system.faceauth.dto.FathDto;
import com.ktmmobile.msf.system.faceauth.dto.FathFormInfo;
import com.ktmmobile.msf.system.faceauth.dto.FathSessionDto;
import com.ktmmobile.msf.system.faceauth.service.FathService;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.service.MyBenefitService;
import com.ktmmobile.msf.form.servicechange.service.SfMypageSvc;
import com.ktmmobile.msf.system.common.legacy.point.dto.CustPointDto;
import com.ktmmobile.msf.system.common.legacy.point.dto.CustPointTxnDto;
import com.ktmmobile.msf.point.service.PointService;
import com.ktmmobile.msf.usim.dto.UsimBasDto;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.ktmmobile.msf.system.common.constants.Constants.*;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.*;
import static com.ktmmobile.msf.system.common.util.DateTimeUtil.isMiddleDateTime2;
import static com.ktmmobile.msf.form.common.constant.PhoneConstant.FIVE_G_FOR_MSP;
import static com.ktmmobile.msf.form.common.constant.PhoneConstant.LTE_FOR_MSP;


@Service("appformSvc")
public class AppformSvcImpl implements AppformSvc {

    private static final Logger logger = LoggerFactory.getLogger(AppformSvcImpl.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private AppformDao appformDao;

    @Autowired
    private FormDtlDao formDtlDao ;

    @Autowired
    private PointService pointService;

    @Autowired
    private CoEventSvc coEventSvc;


    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private MyBenefitService myBenefitService;

    @Autowired
    private CertService certService;

    @Autowired
    private SfMypageSvc mypageService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    FathService fathService;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private MplatFormOsstServerAdapter mplatFormOsstServerAdapter;

    @Override
    public FormDtlDTO getFormDesc(FormDtlDTO formDtlDTO) {
        List<FormDtlDTO> listFormReq = formDtlDao.getFormList(formDtlDTO) ;
        if (listFormReq !=null  && listFormReq.size() > 0) {
            return listFormReq.get(listFormReq.size()-1) ;
        } else {
            return null;
        }
    }

    @Override
    public AppformReqDto getAppFormTempById(String userId) {
        return  appformDao.getAppFormTemp(userId);
    }

    @Override
    public MSimpleOsstXmlVO sendOsstService(String resNo ,String eventCd) throws SelfServiceException, SocketTimeoutException ,McpMplatFormException{

        MSimpleOsstXmlVO simpleOsstXmlVO =  new MSimpleOsstXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);
        mplatFormOsstServerAdapter.callService(param,simpleOsstXmlVO,100000);
        return simpleOsstXmlVO;
    }

    @Override
    public MSimpleOsstXmlVO sendOsstService(Map<String, String> osstParam, String eventCd) throws SelfServiceException, SocketTimeoutException ,McpMplatFormException{
        MSimpleOsstXmlVO simpleOsstXmlVO =  new MSimpleOsstXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);

        if(osstParam != null && !osstParam.isEmpty()){
            osstParam.keySet().stream().forEach(key -> param.put(key, osstParam.get(key)));
        }

        mplatFormOsstServerAdapter.callService(param,simpleOsstXmlVO,100000);
        return simpleOsstXmlVO;
    }

    @Override
    public MSimpleOsstXmlVO sendOsstAddBillService(String resNo ,String eventCd ,String billAcntNo) throws SelfServiceException, SocketTimeoutException ,McpMplatFormException{

        MSimpleOsstXmlVO simpleOsstXmlVO =  new MSimpleOsstXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);
        param.put("billAcntNo", billAcntNo);
        mplatFormOsstServerAdapter.callService(param,simpleOsstXmlVO,100000);
        return simpleOsstXmlVO;
    }

    @Override
    public MSimpleOsstXmlVO sendOsstService(String resNo ,String eventCd,String gubun)throws SelfServiceException, SocketTimeoutException {
        MSimpleOsstXmlVO simpleOsstXmlVO =  new MSimpleOsstXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);
        param.put("gubun", gubun);
        mplatFormOsstServerAdapter.callService(param,simpleOsstXmlVO,100000);
        return simpleOsstXmlVO;
    }

    @Override
    public MSimpleOsstXmlVO sendOsstService(OsstReqDto osstReqDto , String eventCd)throws SelfServiceException, SocketTimeoutException {
        MSimpleOsstXmlVO simpleOsstXmlVO =  new MSimpleOsstXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("appEventCd", eventCd);
        param.put("npTlphNo", osstReqDto.getNpTlphNo());
        param.put("moveCompany", osstReqDto.getBchngNpCommCmpnCd());
        param.put("cstmrType", osstReqDto.getCustTypeCd());
        param.put("selfCertType", osstReqDto.getCustIdntNoIndCd());
        param.put("custIdntNo", osstReqDto.getCustIdntNo());
        param.put("cstmrName", osstReqDto.getCustNm());
        param.put("cntpntShopId", osstReqDto.getSlsCmpnCd());

        mplatFormOsstServerAdapter.callService(param,simpleOsstXmlVO,100000);
        return simpleOsstXmlVO;
    }


    @Override
    public MSimpleOsstXmlUc0VO sendOsstUc0Service(OsstUc0ReqDto osstUc0ReqDto , String eventCd)throws SelfServiceException, SocketTimeoutException {
        MSimpleOsstXmlUc0VO simpleOsstXmlVO =  new MSimpleOsstXmlUc0VO();
        HashMap<String, String> param = new HashMap<String, String>();
        osstUc0ReqDto.setOderTypeCd("H38");
        osstUc0ReqDto.setUsimPymnMthdCd("N");
        osstUc0ReqDto.setUsimChgRsnCd("37");

        /** TO_DO 테스트 용
         */
        //osstUc0ReqDto.setUsimPymnMthdCd("B");
        //osstUc0ReqDto.setIccId("");


        appformDao.insertMcpSelfUsimChg(osstUc0ReqDto);

        param.put("appEventCd", eventCd);
        param.put("mvnoOrdNo", osstUc0ReqDto.getMvnoOrdNo());
        param.put("custNo", osstUc0ReqDto.getCustNo());
        param.put("tlphNo", osstUc0ReqDto.getTlphNo());
        param.put("svcContId", osstUc0ReqDto.getSvcContId());
        param.put("oderTypeCd", osstUc0ReqDto.getOderTypeCd());
        param.put("cntpntCd", osstUc0ReqDto.getCntpntCd());
        param.put("slsPrsnId", "");
        param.put("usimPymnMthdCd", osstUc0ReqDto.getUsimPymnMthdCd());
        param.put("iccId", osstUc0ReqDto.getIccId());
        param.put("usimChgRsnCd", osstUc0ReqDto.getUsimChgRsnCd());


        /** TO_DO 테스트 용
         * param.put("usimPymnMthdCd", "B");// TO_DO 테스트 용
         * param.put("iccId", "");  // TO_DO 테스트 용
         * param.put("usimChgRsnCd", "37");// TO_DO 테스트 용
         */


        mplatFormOsstServerAdapter.callService(param, simpleOsstXmlVO,100000);

        if(!StringUtil.isEmpty(simpleOsstXmlVO.getOsstOrdNo())){
            osstUc0ReqDto.setOsstOrdNo(simpleOsstXmlVO.getOsstOrdNo());
            appformDao.updateMcpSelfUsimChgUC0(osstUc0ReqDto);
        }

        simpleOsstXmlVO.setMvnoOrdNo(osstUc0ReqDto.getMvnoOrdNo());
        //System.out.println("## UC0 return data : " + simpleOsstXmlVO);
        return simpleOsstXmlVO;
    }

    @Override
    public String usimChgChk(OsstUc0ReqDto osstUc0ReqDto) {

        String resultCd = appformDao.selectUsimChgResult(osstUc0ReqDto.getMvnoOrdNo());

        if(StringUtil.isEmpty(resultCd)){ // 미응답
            resultCd = "9999";
        }

        return resultCd;
    }


    @Override
    public MPhoneNoListXmlVO getPhoneNoList(String resNo ,String eventCd)throws SelfServiceException, SocketTimeoutException {
        MPhoneNoListXmlVO mPhoneNoListXmlVO =  new MPhoneNoListXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);
        mplatFormOsstServerAdapter.callService(param,mPhoneNoListXmlVO,100000);
        return mPhoneNoListXmlVO;
    }

    @Override
    public MPhoneNoListXmlVO getPhoneNoList(Map<String, String> osstParam ,String eventCd)throws SelfServiceException, SocketTimeoutException {
        MPhoneNoListXmlVO mPhoneNoListXmlVO =  new MPhoneNoListXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);

        if(osstParam != null && !osstParam.isEmpty()){
            osstParam.keySet().stream().forEach(key -> param.put(key, osstParam.get(key)));
        }

        mplatFormOsstServerAdapter.callService(param,mPhoneNoListXmlVO,100000);
        return mPhoneNoListXmlVO;
    }

    @Override
    public long generateRequestKey() {
        return appformDao.generateRequestKey();
    }

    @Override
    @Transactional
    public AppformReqDto saveAppform(AppformReqDto appformReqDto ,CustPointDto custPoint , List<GiftPromotionBas> giftPromotionBasList )  {

        //1. 서식지 기본값 추출 McpRequestAgentDto
        if (appformReqDto.getRequestKey() == 0 ) {
            appformReqDto.setRequestKey(appformDao.generateRequestKey());
        }

        // ============ STEP START ============
        //1.requestKey 이력 존재여부 확인
        if(0 < certService.getModuTypeStepCnt("requestKey", "")){
            // requestKey 관련 스텝 초기화
            CertDto certDto = new CertDto();
            certDto.setModuType("requestKey");
            certDto.setCompType("G");
            certService.getCertInfo(certDto);
        }

        //2. 최소 스텝개수 확인
        String sharingYn= "sharing".equals(appformReqDto.getCertMenuType()) ? "Y" : "N";

        boolean crtStepResult= this.crtSaveAppFormStep(appformReqDto);
        appformReqDto.setCertMenuType(null);  // 메뉴타입 초기화

        if(!crtStepResult){
            throw new McpCommonJsonException("STEP02", STEP_CNT_EXCEPTION);
        }

        //3. requestKey 저장
        // 인증종류, requestKey, 스텝종료여부
        String[] certKey= {"urlType", "moduType", "requestKey", "stepEndYn"};
        String[] certValue= {"saveRequestKey", "requestKey", appformReqDto.getRequestKey()+"", "Y"};

        if("Y".equals(sharingYn)){
            // 데이터쉐어링인 경우
            // 인증종류, requestKey
            certKey = Arrays.copyOfRange(certKey, 0, 3);
        }

        certService.vdlCertInfo("C", certKey, certValue);
        // ============ STEP END ============

        //1-1. 서식지 예약번호 추출
        appformReqDto.setResNo(appformDao.generateResNo());
        //안면인증
        String fathTrgYn = appformReqDto.getFathTrgYn();
        if("Y".equals(fathTrgYn)){
            FathSessionDto fathSessionDto = fathService.validateFathSession();
            appformReqDto.setFathTransacId(fathSessionDto.getTransacId());
            appformReqDto.setFathCmpltNtfyDt(fathSessionDto.getCmpltNtfyDt());
            //안면인증 관련 OSST 연동이력 MVNO_ORD_NO 컬럼데이터 '임시예약번호'를 -> '실제예약번호'로 업데이트
            fathService.updateFathMcpRequestOsst(appformReqDto.getResNo());
        }
        
        McpRequestDto mcpRequestDto = new McpRequestDto();
        try {
            BeanUtils.copyProperties(mcpRequestDto, appformReqDto);

        } catch (IllegalAccessException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        } catch (InvocationTargetException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }


        // 2. DB 저장 처리
        //2-0 MSP 관련 코드 조회model_Id
        /*
         * MSP 관련 코드가 있으며... MSP 코드값으로 등록
         */
        //가입비  기본값 설정
        if (StringUtils.isBlank(appformReqDto.getUsimPriceType())) {
            appformReqDto.setUsimPriceType(USIM_PRICE_TYPE_BASE);
        }

        //유심비 기본값 설정
        if (StringUtils.isBlank(appformReqDto.getJoinPriceType())) {
            appformReqDto.setJoinPriceType(JOIN_PRICE_TYPE_BASE);
        }

        if (REQ_BUY_TYPE_PHONE.equals(appformReqDto.getReqBuyType())) {

            /* 대표모델아이디와 모델아이디로 색상 값 매칭*/
            McpRequestDto mapMspPrdtCode = appformDao.getMspPrdtCode(appformReqDto);

            if (mapMspPrdtCode == null) {
                throw new McpCommonException(NO_EXSIST_MCP_MODEL_INFO);
            }

            if (!StringUtils.isBlank(mapMspPrdtCode.getReqModelName()) ) {
                mcpRequestDto.setReqModelName(mapMspPrdtCode.getReqModelName());
            }

            if (!StringUtils.isBlank(mapMspPrdtCode.getReqModelColor()) ) {
                mcpRequestDto.setReqModelColor(mapMspPrdtCode.getReqModelColor());
            }

            //대표모델 ID로 수정
            if (!StringUtils.isBlank(appformReqDto.getRprsPrdtId()) ) {
                appformReqDto.setModelId(appformReqDto.getRprsPrdtId());
            }
        } else if (REQ_BUY_TYPE_USIM.equals(appformReqDto.getReqBuyType()) ) {

            //REQ_USIM_NAME 설정
            if (!StringUtils.isBlank(appformReqDto.getModelId())) {
                AppformReqDto appformReqPara = new AppformReqDto() ;
                appformReqPara.setModelId(appformReqDto.getModelId());
                McpRequestDto mapMspPrdtCode = appformDao.getMspPrdtCode(appformReqPara);

                if (mapMspPrdtCode != null) {
                    mcpRequestDto.setReqUsimName(mapMspPrdtCode.getReqModelName());
                }
            }

        }

        if (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) {
            /** 청소년 일때.. 무조건 추가 <=== 사업부 강력 요청  사항
             * APP구분코드
             * 15 : 금번 청소년 유해앱 관련 대외 점검 시 내부적으로 설치안내APP을 "엑스키퍼가드"로 통일함에 따라  23년 11월 21일
             * */
            mcpRequestDto.setAppCd("15");
        }


        //인자값 ktmReferer 대해 session 저장후 온라인 신청서 등록 할때.. 저장 처리 .
        String ktmReferer = SessionUtils.getEtcDomainReferer() ;
        if (!StringUtils.isBlank(ktmReferer)) {
            mcpRequestDto.setKtmReferer(ktmReferer);
        }

        String ktmInflowCd = SessionUtils.getCoalitionInflow();
        if (StringUtils.isNotEmpty(ktmInflowCd)) {
            mcpRequestDto.setOpenMarketReferer(ktmInflowCd);
        }

        // 제휴사 구분 코드
        String jehuPartnerType = SessionUtils.getJehuPartnerType();
        if (StringUtils.isNotEmpty(jehuPartnerType)) {
            mcpRequestDto.setJehuPartnerType(jehuPartnerType);
        }

        /* 전화상담*/
        if ("Y".equals(appformReqDto.getTelAdvice()) ) {
            mcpRequestDto.setCntpntShopId("1100011744");   //1100011744  M모바일(간편가입)
            mcpRequestDto.setAgentCode("VKI0012");   // 대리점_코드
        }

        /* 당일배송 저장 처리
         * MCP_REQUEST_SELF_DLVRY 저장 처리
         * */
        if ("03".equals(appformReqDto.getDlvryType())) {
            String usimProdId = "02";
            NmcpCdDtlDto usimProdObj = NmcpServiceUtils.getCodeNmDto(Constants.USIM_PROD_ID_GROP_CODE, usimProdId) ;

            McpRequestSelfDlvryDto reqSelfDlvry = new McpRequestSelfDlvryDto();
            reqSelfDlvry.setRequestKey(appformReqDto.getRequestKey());
            reqSelfDlvry.setReqType("02");  //요청유형 [01 일반(유심구매) 02 신청서요청]'
            reqSelfDlvry.setDlvryType("03");
            reqSelfDlvry.setUsimProdId(usimProdId);

            if (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) {
                //청소년 법정 대리인
                reqSelfDlvry.setCstmrName(appformReqDto.getMinorAgentName());
                reqSelfDlvry.setCstmrNativeRrn(appformReqDto.getMinorAgentRrnDesc());

                // 배송관련 테이블 - 법정대리인 관련 컬럼 신규 추가
                reqSelfDlvry.setMinorAgentName(appformReqDto.getMinorAgentName());
                reqSelfDlvry.setMinorAgentAgrmYn(appformReqDto.getMinorAgentAgrmYn());
                reqSelfDlvry.setMinorOnlineAuthInfo(appformReqDto.getOnlineAuthType());

            } else if (CSTMR_TYPE_FN.equals(appformReqDto.getCstmrType())) {
                //외국인
                reqSelfDlvry.setCstmrName(appformReqDto.getCstmrName());
                reqSelfDlvry.setCstmrNativeRrn(appformReqDto.getCstmrForeignerRrnDesc());
            } else {
                reqSelfDlvry.setCstmrName(appformReqDto.getCstmrName());
                reqSelfDlvry.setCstmrNativeRrn(appformReqDto.getCstmrNativeRrnDesc());
            }

            reqSelfDlvry.setOnlineAuthType(appformReqDto.getOnlineAuthType());
            reqSelfDlvry.setOnlineAuthInfo(appformReqDto.getOnlineAuthInfo());
            reqSelfDlvry.setOnlineAuthDi(appformReqDto.getSelfCstmrDi());
            reqSelfDlvry.setDlvryName(appformReqDto.getDlvryName());
            reqSelfDlvry.setDlvryTelFn(appformReqDto.getDlvryMobileFn());
            reqSelfDlvry.setDlvryTelMn(appformReqDto.getDlvryMobileMn());
            reqSelfDlvry.setDlvryTelRn(appformReqDto.getDlvryMobileRn());
            reqSelfDlvry.setDlvryPost(appformReqDto.getDlvryPost());
            reqSelfDlvry.setDlvryAddr(appformReqDto.getDlvryAddr());
            reqSelfDlvry.setDlvryAddrDtl(appformReqDto.getDlvryAddrDtl());
            reqSelfDlvry.setDlvryMemo(appformReqDto.getDlvryMemo());
            reqSelfDlvry.setReqBuyQnty("1");
            reqSelfDlvry.setUsimUcost(usimProdObj.getExpnsnStrVal1());
            reqSelfDlvry.setUsimPrice(usimProdObj.getExpnsnStrVal1());


            List<NmcpCdDtlDto> usimProdDtlIdList = NmcpServiceUtils.getCodeList(Constants.USIM_PROD_DTL_ID_GROP_CODE);//유심코드상세 공통코드
            //유심상세 코드 설정
            //가장 정렬 우선순위 위에 있는것 설정
            if(usimProdDtlIdList != null) {
                for(NmcpCdDtlDto usimProdDtlIdValue : usimProdDtlIdList){
                    if (usimProdDtlIdValue.getExpnsnStrVal1().equals(usimProdId)) {
                        reqSelfDlvry.setUsimProdDtlId(usimProdDtlIdValue.getDtlCd());
                        break;
                    }
                }
            }

            Long selfDlvryIdx = this.insertRequestSelfDlvry(reqSelfDlvry);
            if (selfDlvryIdx < 1) {
                throw new McpCommonException(DB_EXCEPTION);
            }
        }

        String acenTrg = "Y";
        NmcpCdDtlDto jehuAgentDto = NmcpServiceUtils.getCodeNmDto(JEHU_AGENT_LIST, mcpRequestDto.getCntpntShopId());
        //제휴대리점이면서 유심미보유인 경우엔 유심 등록후 Acen대상으로 포함할것이기 때문에 대상에서 제외
        if(jehuAgentDto != null && mcpRequestDto.getReqUsimSn().isEmpty()) acenTrg = "N";

        if("Y".equals(acenTrg)) {
            // ACEN 해피콜 대상 조건 확인 및 INSERT
            NmcpCdDtlDto acenLimit= NmcpServiceUtils.getCodeNmDto("AcenLimit", "useLimit");
            if(acenLimit != null && "Y".equals(acenLimit.getExpnsnStrVal1())){

                Map<String,String> acenEtcParam= new HashMap<>();
                acenEtcParam.put("dataType", appformReqDto.getPrdtSctnCd());
                acenEtcParam.put("socCode", appformReqDto.getSocCode());

                boolean isAcenTrg= this.chkAcenReqCondition(mcpRequestDto, acenEtcParam);
                if(isAcenTrg){
                    this.insertAcenReqTrg(mcpRequestDto);
                }
            }
        }
        
        //안면인증 연락처 SET 
        appformReqDto.setFathMobileFn(appformReqDto.getCstmrMobileFn());
        appformReqDto.setFathMobileMn(appformReqDto.getCstmrMobileMn());
        appformReqDto.setFathMobileRn(appformReqDto.getCstmrMobileRn());


        //2-1 : 가입신청 테이블(MCP_REQUEST)
        appformDao.insertMcpRequest(mcpRequestDto);

        //2-2 : 가입신청_고객정보 테이블(MCP_REQUEST_CSTMR)
        appformDao.insertMcpRequestCstmr(appformReqDto);

        //2-3 :가입신청_대리인 테이블(MCP_REQUEST_AGENT)
        appformDao.insertMcpRequestAgent(appformReqDto);

        //2-4 : 가입신청_번호이동정보 테이블(MCP_REQUEST_MOVE)
        appformDao.insertMcpRequestMove(appformReqDto);

        //2-5 가입신청_선불충전 테이블(MCP_REQUEST_PAYMENT)
        appformDao.insertMcpRequestPayment(appformReqDto);


        //2-6 가입신청_판매정보 테이블(MCP_REQUEST_SALEINFO) JoinPayMthdCd
        appformDao.insertMcpRequestSaleinfo(appformReqDto);

        //2-7 가입신청_명의변경 테이블(MCP_REQUEST_CHANGE)
        appformDao.insertMcpRequestChange(appformReqDto);


        //2-8 가입신청_배송정보 테이블(MCP_REQUSET_DLVRY)
        appformDao.insertMcpRequestDlvry(appformReqDto);

        //2-9 가입신청_청구정보 테이블(MCP_REQUEST_REQ)
        appformDao.insertMcpRequestReq(appformReqDto);

        //단말기 일시납 결제 정보 테이블 저장
        if ("01".equals(appformReqDto.getSettlWayCd())) {
            //MCP_REQUEST_PAY_INFO
            appformDao.insertMcpRequestPayInfo(appformReqDto);
        }


        //가입신청_기변사유 저장
        if ( OPER_TYPE_EXCHANGE.equals(appformReqDto.getOperType()) || OPER_TYPE_CHANGE.equals(appformReqDto.getOperType()) ) {
            appformDao.insertMcpRequestDvcChg(appformReqDto);
        }

        //2-10 가입신청_부가서비스 테이블(MCP_REQUEST_ADDITION)
        String[] additionKeyList = appformReqDto.getAdditionKeyList() ;
        if (additionKeyList != null && additionKeyList.length > 0) {
            appformDao.insertMcpRequestAddition(appformReqDto);
        }

        //온라인 신청서 KT 인터넷 관련 정보(MCP_REQUEST_KT_INTER)
        if (!StringUtils.isBlank(appformReqDto.getKtInterSvcNo())  ) {
            appformDao.insertMcpRequestKtInter(appformReqDto);
        }

        //사은품 정보 INSERT 처리
        String[] prmtIdList = appformReqDto.getPrmtIdList();
        // 사은품 제품ID
        String[] prdtIdList = appformReqDto.getPrdtIdList();

        String[] prmtTypeList = appformReqDto.getPrmtTypeList();

        if (prmtIdList != null && prdtIdList != null && prmtIdList.length > 0 && prmtIdList.length == prdtIdList.length ) {
            for (int i=0 ; i < prmtIdList.length ; i++) {
                GiftPromotionDtl giftPromotionDtl = new GiftPromotionDtl();
                giftPromotionDtl.setRequestKey(appformReqDto.getRequestKey());
                giftPromotionDtl.setPrmtType(prmtTypeList[i]);
                giftPromotionDtl.setPrmtId(prmtIdList[i]);
                giftPromotionDtl.setPrdtId(prdtIdList[i]);
                giftPromotionDtl.setCretId(appformReqDto.getCretId());
                giftPromotionDtl.setRip(appformReqDto.getRip());

                //중복 등록 방지 처리
                if ( appformDao.checkGiftReqCount(giftPromotionDtl) < 1 ) {
                    appformDao.insertGiftReqTxn(giftPromotionDtl);
                }
            }
        }

        //기본 사은품 등록 처리
        if (giftPromotionBasList != null && giftPromotionBasList.size() > 0) {
            for (GiftPromotionBas promotionBas : giftPromotionBasList) {

                /*PRMT_TYPE : [D: 기본사은품, C: 선택사은품] */
                String prmtType = promotionBas.getPrmtType();

                if ("D".equals(prmtType)) {
                    List<GiftPromotionDtl> giftPromotionDtlList = promotionBas.getGiftPromotionDtlList();
                    for (GiftPromotionDtl temp : giftPromotionDtlList) {

                        GiftPromotionDtl giftPromotionDtl = new GiftPromotionDtl();
                        giftPromotionDtl.setRequestKey(appformReqDto.getRequestKey());
                        giftPromotionDtl.setPrmtType("D");
                        giftPromotionDtl.setPrmtId(temp.getPrmtId());
                        giftPromotionDtl.setPrdtId(temp.getPrdtId());
                        giftPromotionDtl.setCretId(appformReqDto.getCretId());
                        giftPromotionDtl.setRip(appformReqDto.getRip());

                        //중복 등록 방지 처리
                        if ( appformDao.checkGiftReqCount(giftPromotionDtl) < 1 ) {
                            appformDao.insertGiftReqTxn(giftPromotionDtl);
                        }
                    }
                }
            }
        }
        //사은품 정보 저장 끝



        //프로모션 관련 부가 서비스 등록 처리 (MCP_REQUEST_ADDITION)
        //<=== 확인해야 하는데...
        int resultInt = appformDao.insertMcpRequestAdditionPromotion(appformReqDto);

        //2-12 진행상태 테이블(MCP_REQUEST_STATE)
        appformDao.insertMcpRequestState(appformReqDto);



        if ("Y".equals(appformReqDto.getSesplsYn())) {
            //자급제 정보 등록
            AppformReqDto appformReqSelfPhone = new AppformReqDto();
            appformReqSelfPhone.setRequestKey(appformReqDto.getRequestKey());
            appformReqSelfPhone.setHndsetSalePrice(appformReqDto.getHndsetSalePrice());
            appformReqSelfPhone.setUsePoint(appformReqDto.getUsePoint());
            appformReqSelfPhone.setUsePointSvcCntrNo(appformReqDto.getUsePointSvcCntrNo());
            appformReqSelfPhone.setCardDcCd(appformReqDto.getCardDcCd());
            appformReqSelfPhone.setCardDcAmt(appformReqDto.getCardDcAmt());
            appformReqSelfPhone.setCardDcDivCd(appformReqDto.getCardDcDivCd());
            appformReqSelfPhone.setSesplsProdId(appformReqDto.getSesplsProdId());

            appformDao.insertNmcpRequestApd(mcpRequestDto);
            appformDao.insertNmcpRequestApdDlvry(appformReqDto);
            appformDao.insertNmcpRequestApdSaleinfo(appformReqSelfPhone);
            appformDao.insertNmcpRequestApdState(appformReqDto);

            int couoponPrice = appformReqDto.getUsePointInt() ;
            /*
             * U11 공시지원금 폰구매
             * U12 중고 폰구매   ???
             * U13 자급제 폰구매
             */
            //if ( couoponPrice > 0) {
            if (custPoint !=null && appformReqDto.getPaymentHndsetPrice() == 0  &&  couoponPrice > 0) {
                //포인트 사용
                // ------------ 포인트 차감 ------------------------------------------
                CustPointTxnDto custPointTxnDto = new CustPointTxnDto();

                custPointTxnDto.setSvcCntrNo(appformReqDto.getUsePointSvcCntrNo()); //cntrNo
                custPointTxnDto.setPoint(couoponPrice); // 사용포인트
                custPointTxnDto.setPointTrtCd(POINT_TRT_USE); // 사용사유코드
                custPointTxnDto.setPointTxnRsnCd(POINT_RSN_CD_U13); // 사용사유
                custPointTxnDto.setRemainPoint(custPoint.getTotRemainPoint() - couoponPrice); // 잔액
                custPointTxnDto.setPointDivCd("MP"); // 포인트종류
                custPointTxnDto.setCretId(appformReqDto.getUserId());
                custPointTxnDto.setCretIp(appformReqDto.getRip());
                custPointTxnDto.setPointCustSeq(custPoint.getPointCustSeq());
                custPointTxnDto.setRequestKey((int)appformReqDto.getRequestKey()); // --> 서식지번호
                custPointTxnDto.setAmdIp(appformReqDto.getRip());
                custPointTxnDto.setAmdId(appformReqDto.getUserId());
                custPointTxnDto.setPointTxnDtlRsnDesc("자급제 신청서 사용 결제금액 없음");
                custPointTxnDto.setPointTrtMemo(couoponPrice + "포인트 사용");
                pointService.editPoint(custPointTxnDto);
            }
        } else {
            int couoponPrice = appformReqDto.getUsePointInt() ;

            if (custPoint !=null && couoponPrice > 0) {
                //포인트 사용
                // ------------ 포인트 차감 ------------------------------------------
                CustPointTxnDto custPointTxnDto = new CustPointTxnDto();

                custPointTxnDto.setSvcCntrNo(appformReqDto.getUsePointSvcCntrNo()); //cntrNo
                custPointTxnDto.setPoint(couoponPrice); // 사용포인트
                custPointTxnDto.setPointTrtCd(POINT_TRT_USE); // 사용사유코드
                custPointTxnDto.setPointTxnRsnCd(POINT_RSN_CD_U12); // 사용사유
                custPointTxnDto.setRemainPoint(custPoint.getTotRemainPoint() - couoponPrice); // 잔액
                custPointTxnDto.setPointDivCd("MP"); // 포인트종류
                custPointTxnDto.setCretId(appformReqDto.getUserId());
                custPointTxnDto.setCretIp(appformReqDto.getRip());
                custPointTxnDto.setPointCustSeq(custPoint.getPointCustSeq());
                custPointTxnDto.setRequestKey((int)appformReqDto.getRequestKey()); // --> 서식지번호
                custPointTxnDto.setAmdIp(appformReqDto.getRip());
                custPointTxnDto.setAmdId(appformReqDto.getUserId());
                custPointTxnDto.setPointTxnDtlRsnDesc("휴대폰 신청서 사용");
                custPointTxnDto.setPointTrtMemo(couoponPrice + "포인트 사용");
                pointService.editPoint(custPointTxnDto);
            }
        }

        //추천이 아이디가 존재하고 유효한 정보 이면 저장 처리(MCP_REQUEST_COMMEND)
        /*
         * coEventSvc.duplicateChk(appformReqDto.getCommendId() ??? 뭐지 ???
         *
         */
        if (!StringUtils.isBlank(appformReqDto.getCommendId()) && 0 < coEventSvc.duplicateChk(appformReqDto.getCommendId()) ) {
            appformDao.insertMcpRequestCommend(appformReqDto);
        }

        //셀프개통 정보 UPDATE 처리
        if ("02".equals(appformReqDto.getDlvryType()) ) {
            McpRequestSelfDlvryDto mcpRequestSelfDlvry = new McpRequestSelfDlvryDto();
            mcpRequestSelfDlvry.setRequestKey(appformReqDto.getRequestKey());
            mcpRequestSelfDlvry.setSelfDlvryIdx(appformReqDto.getSelfDlvryIdx());
            appformDao.updateMcpRequestNowDlvryHist(mcpRequestSelfDlvry);
        }

        //신청서 상세 현행화
        AppformReqDto dtlVo = appformDao.getMcpReqDtl(appformReqDto.getRequestKey());
        dtlVo.setDesCstmrNativeRrn(dtlVo.getCstmrNativeRrnDesc());
        if (dtlVo != null) {
            if (appformDao.chkMcpReqDtl(appformReqDto.getRequestKey()) > 0) {
                appformDao.updateMcpReqDtl(dtlVo);
            } else {
                appformDao.insertMcpReqDtl(dtlVo);
            }
        }

        //KT 인터넷 가입 상담여부가 Y이면 NMCP_KT_COUNSEL_REQ insert 20260212
        if("Y".equals(appformReqDto.getKtCounselAgree())) {
            appformDao.insertKtCounsel(appformReqDto);
        }

        return appformReqDto;
    }


    @Override
    @Transactional
    public AppformReqDto saveSimpleAppform(AppformReqDto appformReqDto )  {

        //1. 서식지 기본값 추출 McpRequestAgentDto
        if (appformReqDto.getRequestKey() == 0 ) {
            appformReqDto.setRequestKey(appformDao.generateRequestKey());
        }

        //1-1. 서식지 예약번호 추출
        appformReqDto.setResNo(appformDao.generateResNo());
        // 안면인증 
        String fathTrgYn = appformReqDto.getFathTrgYn();
        if("Y".equals(fathTrgYn)){
            FathSessionDto fathSessionDto = fathService.validateFathSession();
            appformReqDto.setFathTransacId(fathSessionDto.getTransacId());
            appformReqDto.setFathCmpltNtfyDt(fathSessionDto.getCmpltNtfyDt());
            //안면인증 관련 OSST 연동이력 MVNO_ORD_NO 컬럼데이터 '임시예약번호'를 -> '실제예약번호'로 업데이트
            fathService.updateFathMcpRequestOsst(appformReqDto.getResNo());
        }
        
       /* MCP_REQUEST_AGENT  미성년자 만.. \
        MCP_REQUEST_MOVE 기변 제외
        MCP_REQUEST_REQ  기변 제외 */

        McpRequestDto mcpRequestDto = new McpRequestDto();

        try {
            BeanUtils.copyProperties(mcpRequestDto, appformReqDto);
        } catch (IllegalAccessException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        } catch (InvocationTargetException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        // 2. DB 저장 처리

        //2-0 MSP 관련 코드 조회model_Id
        /*
         * MSP 관련 코드가 있으며... MSP 코드값으로 등록
         */
        //가입비  기본값 설정
        if (StringUtils.isBlank(appformReqDto.getUsimPriceType())) {
            appformReqDto.setUsimPriceType(USIM_PRICE_TYPE_BASE);
        }

        //유심비 기본값 설정
        if (StringUtils.isBlank(appformReqDto.getJoinPriceType())) {
            appformReqDto.setJoinPriceType(JOIN_PRICE_TYPE_BASE);
        }

        if (REQ_BUY_TYPE_USIM.equals(appformReqDto.getReqBuyType()) ) {
            //REQ_USIM_NAME 설정
            AppformReqDto appformReqPara = new AppformReqDto() ;
            appformReqPara.setModelId(appformReqDto.getModelId());
            McpRequestDto mapMspPrdtCode = appformDao.getMspPrdtCode(appformReqPara);
            if (mapMspPrdtCode != null) {
                mcpRequestDto.setReqUsimName(mapMspPrdtCode.getReqModelName());
            }
        }

        //인자값 ktmReferer 대해 session 저장후 온라인 신청서 등록 할때.. 저장 처리 .
        String ktmReferer = SessionUtils.getEtcDomainReferer() ;
        if (!StringUtils.isBlank(ktmReferer)) {
            mcpRequestDto.setKtmReferer(ktmReferer);
        }

        // 유입 제휴 코드
        if (StringUtils.isNotEmpty(SessionUtils.getCoalitionInflow())) {
            mcpRequestDto.setOpenMarketReferer(SessionUtils.getCoalitionInflow());
        }

        // 제휴사 구분 코드
        String jehuPartnerType = SessionUtils.getJehuPartnerType();
        if (StringUtils.isNotEmpty(jehuPartnerType)) {
            mcpRequestDto.setJehuPartnerType(jehuPartnerType);
        }

        //안면인증 연락처 SET 
        appformReqDto.setFathMobileFn(appformReqDto.getCstmrMobileFn());
        appformReqDto.setFathMobileMn(appformReqDto.getCstmrMobileMn());
        appformReqDto.setFathMobileRn(appformReqDto.getCstmrMobileRn());
        
        //가입신청 테이블(MCP_REQUEST)
        appformDao.insertMcpRequest(mcpRequestDto);

        //가입신청_고객정보 테이블(MCP_REQUEST_CSTMR)
        appformDao.insertMcpRequestCstmr(appformReqDto);

        //가입신청_번호이동정보 테이블(MCP_REQUEST_MOVE)
        appformDao.insertMcpRequestMove(appformReqDto);

        //가입신청_판매정보 테이블(MCP_REQUEST_SALEINFO) JoinPayMthdCd
        appformDao.insertMcpRequestSaleinfo(appformReqDto);

        //가입신청_배송정보 테이블(MCP_REQUSET_DLVRY)
        appformDao.insertMcpRequestDlvry(appformReqDto);

        //진행상태 테이블(MCP_REQUEST_STATE)
        appformDao.insertMcpRequestState(appformReqDto);

        //신청서 상세 현행화
        AppformReqDto dtlVo = appformDao.getMcpReqDtl(appformReqDto.getRequestKey());
        dtlVo.setDesCstmrNativeRrn(dtlVo.getCstmrNativeRrnDesc());
        if (dtlVo != null) {
            if (appformDao.chkMcpReqDtl(appformReqDto.getRequestKey()) > 0) {
                appformDao.updateMcpReqDtl(dtlVo);
            } else {
                appformDao.insertMcpReqDtl(dtlVo);
            }
        }

        //KT 인터넷 가입 상담여부가 Y이면 NMCP_KT_COUNSEL_REQ insert 20260212
        if("Y".equals(appformReqDto.getKtCounselAgree())) {
            appformDao.insertKtCounsel(appformReqDto);
        }

        return appformReqDto;
    }

    @Override
    @Transactional
    public String saveSimpleAppformUpdate(AppformReqDto appformReqDto , List<GiftPromotionBas> giftPromotionBasList ) {

        // ============ STEP START ============
        //1.requestKey 이력 존재여부 확인
        if(0 < certService.getModuTypeStepCnt("requestKey", "")){
            // requestKey 관련 스텝 초기화
            CertDto certDto = new CertDto();
            certDto.setModuType("requestKey");
            certDto.setCompType("G");
            certService.getCertInfo(certDto);
        }

        //2. 최소 스텝개수 확인
        boolean crtStepResult= this.crtUpdateSimpleAppFormStep(appformReqDto);
        if(!crtStepResult){
            return "STEP_FAIL";
            //throw new McpCommonJsonException("STEP02", STEP_CNT_EXCEPTION);
        }

        //3. requestKey 저장
        // 인증종류, requestKey
        String[] certKey= {"urlType", "moduType", "stepEndYn", "requestKey"};
        String[] certValue= {"saveSimpleRequestKey", "requestKey", "Y", appformReqDto.getRequestKey()+""};
        certService.vdlCertInfo("C", certKey, certValue);
        // ============ STEP END ============

        String strReturn = "";
        McpRequestReqDto mcpRequestReqDto = new McpRequestReqDto() ;
        McpRequestDto mcpRequestDto = new McpRequestDto();
        McpRequestCstmrDto mcpRequestCstmrDto = new McpRequestCstmrDto();
        McpRequestAgentDto mcpRequestAgentDto = new McpRequestAgentDto();
        McpRequestMoveDto mcpRequestMoveDto = new McpRequestMoveDto();
        McpRequestPaymentDto mcpRequestPaymentDto = new McpRequestPaymentDto();
        McpRequestSaleinfoDto mcpRequestSaleinfoDto = new McpRequestSaleinfoDto();
        McpRequestDlvryDto mcpRequestDlvryDto = new McpRequestDlvryDto();
        McpRequestStateDto mcpRequestStateDto = new McpRequestStateDto();
        McpRequestChangeDto mcpRequestChangeDto = new McpRequestChangeDto();
        try {
            BeanUtils.copyProperties(mcpRequestDto, appformReqDto);
            BeanUtils.copyProperties(mcpRequestCstmrDto, appformReqDto);
            BeanUtils.copyProperties(mcpRequestAgentDto, appformReqDto);
            BeanUtils.copyProperties(mcpRequestMoveDto, appformReqDto);
            BeanUtils.copyProperties(mcpRequestPaymentDto, appformReqDto);
            BeanUtils.copyProperties(mcpRequestSaleinfoDto, appformReqDto);
            BeanUtils.copyProperties(mcpRequestDlvryDto, appformReqDto);
            BeanUtils.copyProperties(mcpRequestReqDto, appformReqDto);
            BeanUtils.copyProperties(mcpRequestStateDto, appformReqDto);
            BeanUtils.copyProperties(mcpRequestChangeDto, appformReqDto);
        } catch (IllegalAccessException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        } catch (InvocationTargetException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        //초기화 삭제
        appformDao.deleteMcpRequestReq(appformReqDto) ;
        appformDao.deleteMcpRequestAddition(appformReqDto) ;
        appformDao.deleteMcpRequestAgent(appformReqDto) ;
        appformDao.deleteRequestPayment(appformReqDto) ;
        appformDao.deleteMcpRequestChange(appformReqDto);
        appformDao.deleteMcpRequestCommend(appformReqDto);

        //가입신청_청구정보 테이블(MCP_REQUEST_REQ)
        appformDao.insertMcpRequestReq(appformReqDto);
        appformDao.updateMcpRequest(mcpRequestDto);
        appformDao.updateMcpRequestCstmr(mcpRequestCstmrDto);
        appformDao.updateMcpRequestMove(mcpRequestMoveDto);
        appformDao.updateMcpRequestSaleinfo(mcpRequestSaleinfoDto);

        //가입신청_부가서비스 테이블(MCP_REQUEST_ADDITION)
        String[] additionKeyList = appformReqDto.getAdditionKeyList() ;
        if (additionKeyList != null && additionKeyList.length > 0) {
            appformDao.insertMcpRequestAddition(appformReqDto);
        }

        //프로모션 관련 부가 서비스 등록 처리 (MCP_REQUEST_ADDITION)
        appformDao.insertMcpRequestAdditionPromotion(appformReqDto);

        //가입신청_대리인 테이블(MCP_REQUEST_AGENT)
        appformDao.insertMcpRequestAgent(appformReqDto);
        //가입신청_선불충전 테이블(MCP_REQUEST_PAYMENT)
        appformDao.insertMcpRequestPayment(appformReqDto);
        //가입신청_명의변경 테이블(MCP_REQUEST_CHANGE)
        appformDao.insertMcpRequestChange(appformReqDto);

        //온라인 신청서 KT 인터넷 관련 정보(MCP_REQUEST_KT_INTER)
        if (!StringUtils.isBlank(appformReqDto.getKtInterSvcNo())  ) {
            appformDao.insertMcpRequestKtInter(appformReqDto);
        }

        //추천이 아이디가 존재하고 유효한 정보 이면 저장 처리(MCP_REQUEST_COMMEND)
        if (!StringUtils.isBlank(appformReqDto.getCommendId()) && 0 < coEventSvc.duplicateChk(appformReqDto.getCommendId()) ) {
            appformDao.insertMcpRequestCommend(appformReqDto);
        }

        //사은품 정보 INSERT 처리
        String[] prmtIdList = appformReqDto.getPrmtIdList();
        // 사은품 제품ID
        String[] prdtIdList = appformReqDto.getPrdtIdList();
        String[] prmtTypeList = appformReqDto.getPrmtTypeList();

        if (prmtIdList != null && prdtIdList != null && prmtIdList.length > 0 && prmtIdList.length == prdtIdList.length ) {
            for (int i=0 ; i < prmtIdList.length ; i++) {
                GiftPromotionDtl giftPromotionDtl = new GiftPromotionDtl();
                giftPromotionDtl.setRequestKey(appformReqDto.getRequestKey());
                giftPromotionDtl.setPrmtType(prmtTypeList[i]);
                giftPromotionDtl.setRip(appformReqDto.getRip());
                giftPromotionDtl.setPrmtId(prmtIdList[i]);
                giftPromotionDtl.setPrdtId(prdtIdList[i]);
                giftPromotionDtl.setCretId(appformReqDto.getCretId());
                giftPromotionDtl.setRip(appformReqDto.getRip());

                //중복 등록 방지 처리
                if ( appformDao.checkGiftReqCount(giftPromotionDtl) < 1 ) {
                    appformDao.insertGiftReqTxn(giftPromotionDtl);
                }
            }
        }
        //기본 사은품 등록 처리
        if (giftPromotionBasList != null && giftPromotionBasList.size() > 0) {
            for (GiftPromotionBas promotionBas : giftPromotionBasList) {

                /*PRMT_TYPE : [D: 기본사은품, C: 선택사은품] */
                String prmtType = promotionBas.getPrmtType();

                if ("D".equals(prmtType)) {
                    List<GiftPromotionDtl> giftPromotionDtlList = promotionBas.getGiftPromotionDtlList();
                    for (GiftPromotionDtl temp : giftPromotionDtlList) {

                        GiftPromotionDtl giftPromotionDtl = new GiftPromotionDtl();
                        giftPromotionDtl.setRequestKey(appformReqDto.getRequestKey());
                        giftPromotionDtl.setPrmtType("D");
                        giftPromotionDtl.setPrmtId(temp.getPrmtId());
                        giftPromotionDtl.setPrdtId(temp.getPrdtId());
                        giftPromotionDtl.setCretId(appformReqDto.getCretId());
                        giftPromotionDtl.setRip(appformReqDto.getRip());

                        //중복 등록 방지 처리
                        if ( appformDao.checkGiftReqCount(giftPromotionDtl) < 1 ) {
                            appformDao.insertGiftReqTxn(giftPromotionDtl);
                        }
                    }
                }
            }
        }
        //사은품 정보 저장 끝

        //신청서 상세 현행화
        AppformReqDto dtlVo = appformDao.getMcpReqDtl(appformReqDto.getRequestKey());
        dtlVo.setDesCstmrNativeRrn(dtlVo.getCstmrNativeRrnDesc());
        if (dtlVo != null) {
            if (appformDao.chkMcpReqDtl(appformReqDto.getRequestKey()) > 0) {
                appformDao.updateMcpReqDtl(dtlVo);
            } else {
                appformDao.insertMcpReqDtl(dtlVo);
            }
        }

        return strReturn;
    }



    @Override
    public JuoSubInfoDto selRMemberAjax(JuoSubInfoDto juoSubInfoDto) {
        String customerLinkName = juoSubInfoDto.getCustomerLinkName();
        juoSubInfoDto.setCustomerLinkName(customerLinkName.replaceAll("\\p{Z}","").toUpperCase());//외국인인 경우이름에 공백과 대수문자를

        return appformDao.selRMemberAjax(juoSubInfoDto);
    }


    @Override
    public McpRequestDto getMcpRequest(long requestKey) {
        return  appformDao.getMcpRequest(requestKey);
    }

    @Override
    public McpRequestCstmrDto getMcpRequestCstmr(long requestKey) {
        return  appformDao.getMcpRequestCstmr(requestKey);
    }

    @Override
    public McpRequestDlvryDto getMcpRequestDlvry(long requestKey) {
        return  appformDao.getMcpRequestDlvry(requestKey);
    }

    @Override
    public McpRequestSaleinfoDto getMcpRequestSaleinfo(long requestKey) {
        return  appformDao.getMcpRequestSaleinfo(requestKey);
    }

    @Override
    public List<McpRequestAdditionDto> getMcpAdditionList (McpRequestAdditionDto mcpRequestAdditionDto) {
        return  appformDao.getMcpAdditionList(mcpRequestAdditionDto);
    }

//    @Override
//    public List<Map<String, FormDtlDTO>> setClauseMap() {
//         /*FORMREQUIRED   필수
//         FORMSELECT     선택
//         FORMINFO       고지*/
//        Map<String, FormDtlDTO> clauseMapReq = new HashMap<String, FormDtlDTO>();
//        Map<String, FormDtlDTO> clauseMapSel = new HashMap<String, FormDtlDTO>();
//        Map<String, FormDtlDTO> clauseMapInfo = new HashMap<String, FormDtlDTO>();
//
//        List<Map<String, FormDtlDTO>> rtnlist = new ArrayList<Map<String, FormDtlDTO>>();
//
//
//       //버젼이 높은 약관으로 map으로 저장한다.
//        FormDtlDTO formDtlDTO = new FormDtlDTO();
//        formDtlDTO.setCdGroupId1("FORMREQUIRED");
//        List<FormDtlDTO> listFormReq = formDtlDao.getFormList(formDtlDTO) ;
//        for (FormDtlDTO formDtl : listFormReq) {
//            clauseMapReq.put(formDtl.getCdGroupId2(), formDtl) ;
//        }
//
//
//        FormDtlDTO formDtlDTO2= new FormDtlDTO();
//        formDtlDTO2.setCdGroupId1("FORMSELECT");
//        List<FormDtlDTO> listFormSel = formDtlDao.getFormList(formDtlDTO2) ;
//        for (FormDtlDTO formDtl : listFormSel) {
//            clauseMapSel.put(formDtl.getCdGroupId2(), formDtl) ;
//        }
//
//
//        FormDtlDTO formDtlDTO3= new FormDtlDTO();
//        formDtlDTO3.setCdGroupId1("FORMINFO");
//        List<FormDtlDTO> listFormInfo = formDtlDao.getFormList(formDtlDTO3) ;
//        for (FormDtlDTO formDtl : listFormInfo) {
//            clauseMapInfo.put(formDtl.getCdGroupId2(), formDtl) ;
//        }
//
//        rtnlist.add(clauseMapReq);
//        rtnlist.add(clauseMapSel);
//        rtnlist.add(clauseMapInfo);
//
//        return rtnlist ;
//    }

//    @Override
//    public FormDtlDTO getFormDesc(FormDtlDTO formDtlDTO) {
//        List<FormDtlDTO> listFormReq = formDtlDao.getFormList(formDtlDTO) ;
//        if (listFormReq !=null  && listFormReq.size() > 0) {
//            return listFormReq.get(listFormReq.size()-1) ;
//        } else {
//            return null;
//        }
//    }

    @Override
    public int isOwnerCount(AppformReqDto appformReqDto) {
        return  appformDao.isOwnerCount(appformReqDto);
    }


    @Override
    public int checkJejuCodeCount(String rateCd){
        return  appformDao.checkJejuCodeCount(rateCd);
    }

    @Override
    public int checkClauseJehuRatecd(String rateCd){
        return  appformDao.checkClauseJehuRatecd(rateCd);
    }

    @Override
    public String selPrdtcolCd(AppformReqDto appformReqDto){
        return  appformDao.selPrdtcolCd(appformReqDto);
    }

    @Override
    public String getAtribVal(HashMap<String, Object> hm) {
        return  appformDao.getAtribVal(hm);
    }

    @Override
    public AppformReqDto getMarketRequest(AppformReqDto appformReqDto){
        return  appformDao.getMarketRequest(appformReqDto);
    }


    @Override
    public List<AppformReqDto> selectModelMonthlyList(AppformReqDto appformReqDto){
        return appformDao.selectModelMonthlyList(appformReqDto);
    }


    @Override
    public List<AppformReqDto> selectMonthlyListMarket(AppformReqDto appformReqDto){
        return appformDao.selectMonthlyListMarket(appformReqDto);
    }


    @Override
    public List<AppformReqDto> selectPrdtColorList(AppformReqDto appformReqDto){
        return appformDao.selectPrdtColorList(appformReqDto);
    }

    @Override
    public int getMcpRequestCount(AppformReqDto appformReqDto){
        return appformDao.getMcpRequestCount(appformReqDto);
    }

    @Override
    public String getAgentCode(String cntpntShopId) {
        return appformDao.getAgentCode(cntpntShopId);
    }


    @Override
    public Map<String,String> getAgentInfoOjb(String cntpntShopId){
        return appformDao.getAgentInfoOjb(cntpntShopId);
    }

    @Override
    public McpRequestOsstDto getRequestOsst(McpRequestOsstDto mcpRequestOsstDto){
        return  appformDao.getRequestOsst(mcpRequestOsstDto);
    }


    @Override
    public int requestOsstCount(McpRequestOsstDto mcpRequestOsstDto) {
        return  appformDao.requestOsstCount(mcpRequestOsstDto);
    }

    @Override
    public boolean updateMcpRequest(McpRequestDto mcpRequestDto) {
        return  appformDao.updateMcpRequest(mcpRequestDto);
    }

    @Override
    public boolean insertMcpRequestOsst(McpRequestOsstDto mcpRequestOsstDto) {
        return  appformDao.insertMcpRequestOsst(mcpRequestOsstDto);
    }

    @Override
    public boolean updateMcpRequestOsstOrdNo(McpRequestOsstDto mcpRequestOsstDto) {
        return  appformDao.updateMcpRequestOsstOrdNo(mcpRequestOsstDto);
    }

    @Override
    public boolean updateMcpRequestMove(McpRequestMoveDto mcpRequestMoveDto) {
        return  appformDao.updateMcpRequestMove(mcpRequestMoveDto);
    }

    @Override
    public List<AppformReqDto> getInsrCode() {
        return appformDao.getInsrCode();
    }

    @Override
    public List<IntmInsrRelDTO> getInsrProdList(IntmInsrRelDTO intmInsrRelDTO) {
        return appformDao.getInsrProdList(intmInsrRelDTO);
    }

    @Override
    public Long saveSelfDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry) {

        Long selfDlvryIdx = reqSelfDlvry.getSelfDlvryIdx();
        if( selfDlvryIdx <= 0 ){
            selfDlvryIdx = appformDao.getRequestSelfDlvrKey();
        }
        reqSelfDlvry.setSelfMemo("");
        reqSelfDlvry.setReqUsimSn("");
        reqSelfDlvry.setDlvryStateCode("01");
        reqSelfDlvry.setSelfStateCode("01");
        reqSelfDlvry.setDlvryNo("");
        reqSelfDlvry.setTbCd("");
        reqSelfDlvry.setContractNum("");

        reqSelfDlvry.setSelfDlvryIdx(selfDlvryIdx);
        if (appformDao.insertMcpRequestSelfDlvryHist(reqSelfDlvry)) {
            return selfDlvryIdx ;
        } else {
            return (long) -9 ;
        }

    }

    @Override
    public Long insertRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry) {

        Long selfDlvryIdx = reqSelfDlvry.getSelfDlvryIdx();
        if( selfDlvryIdx <= 0 ){
            selfDlvryIdx = appformDao.getRequestSelfDlvrKey();
        }
        reqSelfDlvry.setSelfMemo("");
        reqSelfDlvry.setReqUsimSn("");
        reqSelfDlvry.setDlvryStateCode("01");
        reqSelfDlvry.setSelfStateCode("01");
        reqSelfDlvry.setDlvryNo("");
        reqSelfDlvry.setTbCd("");
        reqSelfDlvry.setContractNum("");

        reqSelfDlvry.setSelfDlvryIdx(selfDlvryIdx);
        if (appformDao.insertRequestSelfDlvry(reqSelfDlvry)) {
            return selfDlvryIdx ;
        } else {
            return (long) -9 ;
        }

    }


    @Override
    public Long saveSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry) {
        reqSelfDlvry.setSelfMemo("");
        reqSelfDlvry.setReqUsimSn("");
        reqSelfDlvry.setDlvryStateCode("01");
        reqSelfDlvry.setSelfStateCode("01");
        reqSelfDlvry.setDlvryNo("");
        reqSelfDlvry.setTbCd("");
        reqSelfDlvry.setContractNum("");

        if (appformDao.insertMcpRequestSelfDlvry(reqSelfDlvry)) {
            return (long)1 ;
        } else {
            return (long) -9 ;
        }

    }

    /**
     * <pre>
     * 설명     : 셀프개통 배송 (MCP_REQUEST_SELF_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    @Override
    public List<McpRequestSelfDlvryDto> getMcpRequestSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry) {
        return appformDao.getMcpRequestSelfDlvry(reqSelfDlvry);
    }

    /**
     * <pre>
     * 설명     : 셀프개통 배송 (MCP_REQUEST_SELF_DLVRY_HIST) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    @Override
    public McpRequestSelfDlvryDto getMcpSelfDlvryDataHist(Long selfDlvryIdx) {
        return appformDao.getMcpSelfDlvryDataHist(selfDlvryIdx);
    }

    /**
     * <pre>
     * 설명     : 셀프개통 배송 (MCP_REQUEST_SELF_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    @Override
    public McpRequestSelfDlvryDto getMcpSelfDlvryData(Long selfDlvryIdx) {
        return appformDao.getMcpSelfDlvryData(selfDlvryIdx);
    }

    @Override
    public int deleteMcpRequestSelfDlvry(Long selfDlvryIdx) {
        return appformDao.deleteMcpRequestSelfDlvry(selfDlvryIdx);
    }


    /**
     * <pre>
     * 설명     : 셀프개통 바로배송 (MCP_REQUEST_NOW_DLVRY) 조회
     * @param requestKey
     * @return
     * @return: McpRequestSelfDlvryDto
     * </pre>
     */
    @Override
    public McpRequestSelfDlvryDto getMcpNowDlvryData(Long selfDlvryIdx) {
        return appformDao.getMcpNowDlvryData(selfDlvryIdx);
    }

    @Override
    public Long saveNowDlvryHist(McpRequestSelfDlvryDto reqSelfDlvry) {

        Long selfDlvryIdx = reqSelfDlvry.getSelfDlvryIdx();
        if( selfDlvryIdx <= 0 ){
            selfDlvryIdx = appformDao.getRequestSelfDlvrKey();
        }
        reqSelfDlvry.setSelfMemo("");
        reqSelfDlvry.setReqUsimSn("");
        reqSelfDlvry.setDlvryStateCode("01");
        reqSelfDlvry.setSelfStateCode("01");
        reqSelfDlvry.setDlvryNo("");
        reqSelfDlvry.setTbCd("");
        reqSelfDlvry.setContractNum("");
        reqSelfDlvry.setSelfDlvryIdx(selfDlvryIdx);
        // 메세지 변경
        String dlvryMemo = StringUtil.NVL(reqSelfDlvry.getDlvryMemo(),"");
        String exitTitle = StringUtil.NVL(reqSelfDlvry.getExitTitle(),"");
        StringBuffer str = new StringBuffer();
        dlvryMemo = str.append(dlvryMemo).append(" / ").append(exitTitle).toString().trim();
        reqSelfDlvry.setDlvryMemo(dlvryMemo);

        if (appformDao.insertMcpRequestNowDlvryHist(reqSelfDlvry)) {
            return selfDlvryIdx ;
        } else {
            return (long) -9 ;
        }

    }

    @Override
    public int deleteMcpRequestNowDlvry(Long selfDlvryIdx) {
        return appformDao.deleteMcpRequestNowDlvry(selfDlvryIdx);
    }

    @Override
    public Long saveNowDlvry(McpRequestSelfDlvryDto reqSelfDlvry) {
        reqSelfDlvry.setSelfMemo("");
        reqSelfDlvry.setReqUsimSn("");
        reqSelfDlvry.setDlvryStateCode("01");
        reqSelfDlvry.setSelfStateCode("01");
        reqSelfDlvry.setDlvryNo("");
        reqSelfDlvry.setTbCd("");
        reqSelfDlvry.setContractNum("");

        if (appformDao.insertMcpRequestNowDlvry(reqSelfDlvry)) {

            return (long)1 ;
        } else {
            return (long) -9 ;
        }

    }

    @Override
    public boolean updateNowDlvry(McpRequestSelfDlvryDto mcpRequestSelfDlvryDto) {
        return appformDao.updateNowDlvry(mcpRequestSelfDlvryDto);
    }

    /**
     * <pre>
     * 설명     : 셀프개통 바로 배송 (MCP_REQUEST_NOW_DLVRY_HIST) 조회
     * @param requestKey
     * @return
     * @return: getMcpNowDlvryDataHist
     * </pre>
     */
    @Override
    public Map<String,Object> nowDlvryComplete(Long selfDlvryIdx) {
        Map<String,Object> map = new HashMap<String,Object>();
        // 0. 결제테이블 조회 후 PAY_CD UPDATE
        // 1. 주문 서비스 호출 kos
        // 2. ktOrderId/ deliveryOrderId 값 MCP_REQUEST_NOW_DLVRY 테이블에  update

        McpRequestSelfDlvryDto mcpRequestSelfDlvryDto = null;
        String resultCode = "00"; // 성공
        NowDlvryResDto nowDlvryResDto = new NowDlvryResDto();
        String orderRcvTlphNo = "";
        String rsltMsg = "";
        String rsltCd = "";
        String ktOrderId = "";
        String deliveryOrderId = "";
        String d02bizOrgCd = "";
        String dlvryStateCode = "";

        NowDlvryReqDto nowDlvryReqDto = new NowDlvryReqDto();
        try{

            mcpRequestSelfDlvryDto = this.getMcpNowDlvryDataHist(selfDlvryIdx);

            if( mcpRequestSelfDlvryDto == null ){
                resultCode = "01"; // 조회 데이터 없음
                map.put("mcpRequestSelfDlvryDto", null);
                map.put("resultCode", resultCode);
                return map;
            } else {

                //MCP_REQUEST_NOW_DLVRY 에 insert 후 D02 호출 후 진행상태, 연동결과 UPDATE 처리
                mcpRequestSelfDlvryDto.setSvcRsltCd("00"); //연동결과 - 성공
                mcpRequestSelfDlvryDto.setSvcRsltMsg("성공");
                mcpRequestSelfDlvryDto.setDlvryStateCode("06"); //진행상태 - 대기

                Long resIdx = this.saveNowDlvry(mcpRequestSelfDlvryDto);

                if( resIdx < 0 ){
                    resultCode = "03"; // 주문실패
                }

                // 1. D2 호출
                StringBuffer str = new StringBuffer();
                str = str.append(mcpRequestSelfDlvryDto.getDlvryTelFn()).append(mcpRequestSelfDlvryDto.getDlvryTelMn()).append(mcpRequestSelfDlvryDto.getDlvryTelRn());
                orderRcvTlphNo = str.toString(); // 수령고객연락처
                String zipNo = StringUtil.NVL(mcpRequestSelfDlvryDto.getDlvryPost(),"").replaceAll(" ", "");    // 우편번호
                String addrTypeCd = "1";    // 1:지번,2:도로명 주소 유형

                // 도로명
                String targetAddr1 = StringUtil.NVL(mcpRequestSelfDlvryDto.getDlvryAddr(),"");// 기본주소
                targetAddr1 = targetAddr1.replaceAll("(\r|\n|\r\n|\n\r)","");
                String targetAddr2 = StringUtil.NVL(mcpRequestSelfDlvryDto.getDlvryAddrDtl(),"");// 상세주소
                targetAddr2 = targetAddr2.replaceAll("(\r|\n|\r\n|\n\r)","");
                // 도로명 끝
                // 지번
                String dlvryJibunAddr = StringUtil.NVL(mcpRequestSelfDlvryDto.getDlvryJibunAddr(),"");// 기본주소
                targetAddr1 = targetAddr1.replaceAll("(\r|\n|\r\n|\n\r)","");
                String dlvryJibunAddrDtl = StringUtil.NVL(mcpRequestSelfDlvryDto.getDlvryJibunAddrDtl(),"");// 상세주소
                targetAddr2 = targetAddr2.replaceAll("(\r|\n|\r\n|\n\r)","");
                // 지번 끝

                String custInfoAgreeYn = "Y";    // 개인정보제공동의 여부
                String rsvOrderYn = "";    // 배달예약여부
                String rsvOrderDt = "";    // 배달희망시간

                // 메세지 변경사항 homePw 항목은 디비에서 삭제예정.
                StringBuffer msg = new StringBuffer();
                String orderReqMsg = mcpRequestSelfDlvryDto.getDlvryMemo(); // 배달요청메세지
                String homePw = StringUtil.NVL(mcpRequestSelfDlvryDto.getHomePw(),"");
                orderReqMsg = msg.append(orderReqMsg).append(" ").append(homePw).toString().trim();
                // 메세지 변경사항 끝
                String acceptTime = mcpRequestSelfDlvryDto.getAcceptTime();    // 접수가능시간
                String[] arrAcceptTime;
                if(acceptTime !=null && !"".equals(acceptTime)){
                    acceptTime = acceptTime.replaceAll(" ", "");
                    arrAcceptTime = acceptTime.split(",");
                    acceptTime = arrAcceptTime[0];
                }

                String usimAmt = mcpRequestSelfDlvryDto.getUsimAmt();    // 유심금액
                String bizOrgCd = mcpRequestSelfDlvryDto.getBizOrgCd();    // 배달 업체 코드
                String entY = mcpRequestSelfDlvryDto.getEntY(); // 위도
                String entX = mcpRequestSelfDlvryDto.getEntX(); // 경도
                String usimProdId = StringUtil.NVL(mcpRequestSelfDlvryDto.getUsimProdId(),"01");
                String nfcYn = usimProdId.equals("01") ? "N" : "Y"; //NFC유심 여부

                nowDlvryReqDto.setOrderRcvTlphNo(orderRcvTlphNo);
                nowDlvryReqDto.setZipNo(zipNo);
                nowDlvryReqDto.setAddrTypeCd(addrTypeCd);
                nowDlvryReqDto.setTargetAddr1(dlvryJibunAddr);
                nowDlvryReqDto.setTargetAddr2(dlvryJibunAddrDtl);
                nowDlvryReqDto.setCustInfoAgreeYn(custInfoAgreeYn);
                nowDlvryReqDto.setRsvOrderYn(rsvOrderYn);
                nowDlvryReqDto.setRsvOrderDt(rsvOrderDt);
                nowDlvryReqDto.setOrderReqMsg("<![CDATA["+orderReqMsg+"]]>");
                nowDlvryReqDto.setAcceptTime(acceptTime);
                nowDlvryReqDto.setUsimAmt(usimAmt);
                nowDlvryReqDto.setBizOrgCd(bizOrgCd);
                nowDlvryReqDto.setRsvOrderYn("N");
                nowDlvryReqDto.setEntY(entY);
                nowDlvryReqDto.setEntX(entX);
                nowDlvryReqDto.setNfcYn(nfcYn);

                nowDlvryResDto = mPlatFormService.acceptDeliveryUsim(nowDlvryReqDto);

                rsltCd = StringUtil.NVL(nowDlvryResDto.getRsltCd(),"99");
                rsltMsg = nowDlvryResDto.getRsltMsg();
                ktOrderId = nowDlvryResDto.getKtOrderId();
                deliveryOrderId = nowDlvryResDto.getDeliveryOrderId();
                d02bizOrgCd = nowDlvryResDto.getBizOrgCd();
                dlvryStateCode = "01";
            }

        }catch(Exception e){
            resultCode = "05"; // 서비스 오류
            rsltMsg = e.getMessage();
            rsltCd = "99";
            ktOrderId = "";
            deliveryOrderId = "";
            dlvryStateCode = "06";

        } finally {

            try{

                if(!"00".equals(rsltCd)){
                    resultCode = "03"; // 주문실패
                }

                if (mcpRequestSelfDlvryDto == null) {
                    throw new McpCommonException(COMMON_EXCEPTION);
                }

                // 2. MCP_REQUEST_NOW_DLVRY 데이터 업데이트
                mcpRequestSelfDlvryDto.setKtOrdId(ktOrderId);
                mcpRequestSelfDlvryDto.setDeliveryOrderId(deliveryOrderId);
                mcpRequestSelfDlvryDto.setSvcRsltCd(rsltCd);
                mcpRequestSelfDlvryDto.setSvcRsltMsg(StringUtil.substringByBytes(rsltMsg,0,400));
                mcpRequestSelfDlvryDto.setBizOrgCd(d02bizOrgCd);
                mcpRequestSelfDlvryDto.setDlvryStateCode(dlvryStateCode);

                this.updateNowDlvry(mcpRequestSelfDlvryDto);

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes()).getRequest();

                AppformReqDto param = new AppformReqDto();
                param.setSelfDlvryIdx(mcpRequestSelfDlvryDto.getSelfDlvryIdx());
                param.setRip(request.getRemoteAddr());
                param.setUsimBuyDivCd("NOW");
                this.insertNmcpUsimBuyTxn(param);

            }catch(Exception e){
                logger.error("updateNowDlvry ERROR==>"+e.getMessage());
            }
        }

        map.put("mcpRequestSelfDlvryDto", mcpRequestSelfDlvryDto);
        map.put("resultCode", resultCode);
        return map;
    }

    @Override
    public boolean updatePayCdNowDlvry(McpRequestSelfDlvryDto reqSelfDlvry) {
        return  appformDao.updatePayCdNowDlvry(reqSelfDlvry);
    }

    @Override
    public McpRequestSelfDlvryDto getMcpNowDlvryDataHist(Long selfDlvryIdx) {
        return  appformDao.getMcpNowDlvryDataHist(selfDlvryIdx);
    }

    @Override
    public int updateSelfViewYn(Long selfDlvryIdx) {
        return  appformDao.updateSelfViewYn(selfDlvryIdx);
    }

    @Override
    public int updateNowViewYn(Long selfDlvryIdx) {
        return  appformDao.updateNowViewYn(selfDlvryIdx);
    }

    @Override
    public int checkLimitFormCount(AppformReqDto appformReqDto)  {
        return  appformDao.checkLimitFormCount(appformReqDto);
    }

    @Override
    public AppformReqDto getLimitForm(AppformReqDto appformReqDto) {
        return appformDao.getLimitForm(appformReqDto);
    }

    @Override
    public boolean updateAppForPstate(long requestKey) {
        return  appformDao.updateAppForPstate(requestKey);
    }

    @Override
    public List<AppformReqDto> getFormDlveyList(AppformReqDto appformReqDto) {
        return  appformDao.getFormDlveyList(appformReqDto);
    }

    @Override
    public boolean updateFormDlveyUsim(AppformReqDto appformReqDto){
        return  appformDao.updateFormDlveyUsim(appformReqDto);
    }

    @Override
    public long getTempRequestKey() {
        return appformDao.getTempRequestKey();
    }

    @Override
    public int insertAppFormTempSave(AppformReqDto appformReqDto) {
        return appformDao.insertAppFormTempSave(appformReqDto);
    }

    @Override
    public int insertSaleinfoTempSave(AppformReqDto appformReqDto) {
        return appformDao.insertSaleinfoTempSave(appformReqDto);
    }

    @Override
    public int insertAppFormApdTempSave(AppformReqDto appformReqDto) {
        return appformDao.insertAppFormApdTempSave(appformReqDto);
    }

    @Override
    public int insertSaleinfoApdTempSave(AppformReqDto appformReqDto) {
        return appformDao.insertSaleinfoApdTempSave(appformReqDto);
    }

    @Override
    public McpRequestDto getMcpRequestByContractNum(String contractNum) {
        McpRequestDto requestDto = new McpRequestDto();
        requestDto.setContractNum(contractNum);
        requestDto.setRequestKey(0);

        return  appformDao.getMcpRequest(requestDto);
    }

    @Override
    public AppformReqDto getAppFormTemp(long requestKey) {
        return  appformDao.getAppFormTemp(requestKey);
    }

    @Override
    public AppformReqDto getAppForm(AppformReqDto appformReqDto) {
        return  appformDao.getAppForm(appformReqDto);
    }



    @Override
    @Transactional
    public boolean updateRequestTempStep1(AppformReqDto appformReqDto) {
        appformDao.updateRequestTemp(appformReqDto);
        appformDao.updateRequestCstmrTemp(appformReqDto);
        if (!StringUtils.isBlank(appformReqDto.getMinorAgentName())) {
            appformDao.updateRequestAgentTemp(appformReqDto);
        }

        if ( !StringUtils.isBlank(appformReqDto.getDlvryName()) ) {
            appformDao.updateRequestDlvryTemp(appformReqDto);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateRequestTempStep3(AppformReqDto appformReqDto) {
        appformDao.updateRequestTemp(appformReqDto);
        if (!StringUtils.isBlank(appformReqDto.getMoveCompany())) {
            appformDao.updateRequestMoveTemp(appformReqDto);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean updateRequestTempStep4(AppformReqDto appformReqDto) {
        appformDao.updateRequestTemp(appformReqDto);
        if (!StringUtils.isBlank(appformReqDto.getRecommendInfo())) {
            appformDao.updateRequestCstmrTemp(appformReqDto);
        }

        String[] additionKeyList = appformReqDto.getAdditionKeyList() ;
        if (additionKeyList != null && additionKeyList.length > 0) {
            appformDao.deleteMcpRequestAdditionTemp(appformReqDto);
            appformDao.insertMcpRequestAdditionTemp(appformReqDto);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateRequestTempStep5(AppformReqDto appformReqDto) {
        appformDao.updateRequestTemp(appformReqDto);
        appformDao.updateRequestCstmrTemp(appformReqDto);
        appformDao.updateRequestReqTemp(appformReqDto);
        return true;
    }

    @Override
    public List<String> getAdditionTempList (AppformReqDto appformReqDto) {
        return appformDao.getAdditionTempList(appformReqDto);
    }


    @Override
    public UsimBasDto getUsimBasInfo(UsimBasDto usimBasObj) {
        return appformDao.getUsimBasInfo( usimBasObj) ;
    }

    @Override
    public AppformReqDto getNmcpRequestApdSaleinfo(Long selfDlvryIdx) {
        return appformDao.getNmcpRequestApdSaleinfo( selfDlvryIdx) ;
    }

    @Override
    @Transactional
    public boolean useSelfFormSufPay(Long requestKey) {

        AppformReqDto appformReq = appformDao.getNmcpRequestApdSaleinfo(requestKey);

        if (appformReq==null) {
            return false;
        }

        appformReq.setPstate("00");
        appformReq.setRequestStateCode("00");
        appformReq.setViewFlag("Y");

        //일반 신청서 업데이트
        //appformDao.updateAppForPstate(requestKey);
        appformDao.updateMcpRequestCallBack(appformReq) ;
        appformDao.updateMcpRequestState(appformReq);

        //자급제 신청서 업데이트
        appformDao.updateNmcpRequestApd(appformReq);
        appformDao.updateNmcpRequestApdState(appformReq);

        //포인트 사용처리

        int couoponPrice = appformReq.getUsePointInt() ;

        if ( couoponPrice > 0) {
            //포인트 사용
            //포인트 정보 조회
            CustPointDto custPoint = myBenefitService.selectCustPoint(appformReq.getUsePointSvcCntrNo());
            // ------------ 포인트 차감 ------------------------------------------
            CustPointTxnDto custPointTxnDto = new CustPointTxnDto();
            custPointTxnDto.setSvcCntrNo(appformReq.getUsePointSvcCntrNo()); //cntrNo
            custPointTxnDto.setPoint(couoponPrice); // 사용포인트
            custPointTxnDto.setPointTrtCd(POINT_TRT_USE); // 사용사유코드
            custPointTxnDto.setPointTxnRsnCd(POINT_RSN_CD_U13); // 사용사유
            custPointTxnDto.setRemainPoint(custPoint.getTotRemainPoint() - couoponPrice); // 잔액
            custPointTxnDto.setPointDivCd("MP"); // 포인트종류
            custPointTxnDto.setCretId("CallBack");
            custPointTxnDto.setCretIp(ipstatisticService.getClientIp());
            custPointTxnDto.setPointCustSeq(custPoint.getPointCustSeq());
            custPointTxnDto.setRequestKey(requestKey.intValue()); // --> 서식지번호
            custPointTxnDto.setAmdIp(ipstatisticService.getClientIp());
            custPointTxnDto.setAmdId("CallBack");
            custPointTxnDto.setPointTxnDtlRsnDesc("자급제 신청서 사용  결제후 승인");
            custPointTxnDto.setPointTrtMemo(couoponPrice + "포인트 사용");
            pointService.editPoint(custPointTxnDto);
        }


        return true;
    }

    @Override
    public AppformReqDto getCopyMcpRequest(AppformReqDto appformReq) {
        return appformDao.getCopyMcpRequest(appformReq);
    }

    @Override
    public int insertNmcpUsimBuyTxn(AppformReqDto appformReq) {
        return appformDao.insertNmcpUsimBuyTxn(appformReq);
    }

    @Override
    public String getUsimOrgnId(String usimNo) {
        RestTemplate restTemplate = new RestTemplate();
        return  restTemplate.postForObject(apiInterfaceServer + "/msp/sellUsimMgmtOrgnId", usimNo, String.class );
    }


    @Override
    public int checkUploadPhoneInfoCount(String strUploadPhoneSrlNo) {
        long uploadPhoneSrlNo = 0;
        try {
            uploadPhoneSrlNo = Long.parseLong(strUploadPhoneSrlNo);
        } catch (NumberFormatException e) {
            uploadPhoneSrlNo = -1 ;
        }

        return appformDao.checkUploadPhoneInfoCount(uploadPhoneSrlNo);
    }

    @Override
    public McpUploadPhoneInfoDto getUploadPhoneInfo(long uploadPhoneSrlNo) {
        return appformDao.getUploadPhoneInfo(uploadPhoneSrlNo);
    }

    @Override
    public int selectStolenIp(String customer) {

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/selectStolenIp", customer, Integer.class);
    }

    /**
     * <pre>
     * 설명 : [step별 검증] 상담사 개통 최종 step 확인
     * @param appformReqDto
     * @return: boolean
     * </pre>
     */
    @Override
    public boolean crtSaveAppFormStep(AppformReqDto appformReqDto) {

        // ※ 개통신청_STEP별 본인인증 검증절차 문서 참고
        // ※ 유심셀프개통(자동해피콜), esimWatch 셀프개통(자동해피콜), esim 셀프개통(자동해피콜), 데이터쉐어링,
        //    상담사 유심개통, 상담사 단말개통(기변 포함), 대리점 유심개통, 대리점 단말개통(기변 포함), 상담사 esim개통, 상담사 esimWatch개통

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestUrl = (request.getHeader("referer") == null) ? "" : request.getHeader("referer");

        // 0. 데이터 가공
        String openMarketYn = (requestUrl.contains("/request/marketRequest.do")) ? "Y" : "N"; //오픈마켓 여부
        String operType= StringUtil.NVL(appformReqDto.getOperType(), "");
        String changeYn = (OPER_TYPE_CHANGE.equals(operType) || OPER_TYPE_EXCHANGE.equals(operType)) ? "Y" : "N"; // 기기변경
        String esimWatchYn = ("09".equals(appformReqDto.getUsimKindsCd()) && !StringUtil.isBlank(appformReqDto.getPrntsContractNo())) ? "Y" : "N"; //esimWatch
        String esimYn = ("09".equals(appformReqDto.getUsimKindsCd()) && StringUtil.isBlank(appformReqDto.getPrntsContractNo())) ? "Y" : "N"; //esim

        String selfCallYn = "N"; // 자동해피콜 (esimWatch 호환을 위해 onOffType으로 구분하지 않음)
        String pageNm= SessionUtils.getPageSession();
        if(!StringUtil.isEmpty(pageNm) && pageNm.contains("Self") && !"Y".equals(appformReqDto.getTelAdvice())){
            selfCallYn= "Y";
        }

        boolean existOptionStep= true; // 옵션스텝 존재여부
        if("Y".equals(esimWatchYn) || "sharing".equals(appformReqDto.getCertMenuType())) existOptionStep= false;

        // ------------------------------------------------------------------------------------------

        // 필수로 존재해야하는 최소 step 수
        int certStep= 0;

        // 1. 개별 폼 별 default step 수
        if("Y".equals(selfCallYn)){ // 자동해피콜

            if("Y".equals(esimWatchYn)) certStep= 9; // esimWatch
            else if("Y".equals(esimYn)) certStep= 7; // esim
            else certStep= 6; // usim

        }else if("Y".equals(appformReqDto.getTelAdvice())){ // 정상해피콜

            // ** 특이케이스 처리 : 유심 신규 셀프개통 > SMS인증 후 정상해피콜로 전환한 경우 이름, 생년월일 변경 불가
            if(0 < certService.getModuTypeStepCnt("smsAuth", "")) {
                certStep= 5;
            }else{
                certStep= 0; // usim 상담사 해피콜, 핸드폰 해피콜, usim 셀프개통 해피콜

                // ** option 스텝 개별 처리
                // 청소년이 아니면서, 계좌인증 또는 유심인증을 한 경우 스텝 증가
                if(!CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType()) &&
                        ("D".equals(appformReqDto.getReqPayType()) || "C".equals(appformReqDto.getReqPayType()) || !StringUtil.isEmpty(appformReqDto.getReqUsimSn()))){
                    certStep++;
                }
            }

        }else{

            if("Y".equals(esimWatchYn)){ // esimWatch
                // 2024-12-03 상담사개통도 NICEPIN 인증하는것으로 변경
                certStep= 8;
            }else if("Y".equals(esimYn)){ // esim
                if(CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) certStep= 7; // 청소년
                else certStep= 6;

            }else if("Y".equals(changeYn)){ // 기기변경
                if("Y".equals(openMarketYn)) certStep= 6; // 오픈마켓
                else if(CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) certStep= 8; // 청소년
                else certStep= 7;

            }else if("sharing".equals(appformReqDto.getCertMenuType())){  // 데이터쉐어링
                certStep= 7;

            }else{ // usim, 핸드폰
                if("Y".equals(openMarketYn)) certStep= 4; // 오픈마켓
                else certStep= 5;
            }
        }

        // 2. option step 수
        if(existOptionStep){

            // 2-1. 계좌인증
            if("D".equals(appformReqDto.getReqPayType()) || "C".equals(appformReqDto.getReqPayType())){
                certStep+=2;
                // ** 청소년 해피콜이면서 계좌인증을 한 경우 step 추가
                if("Y".equals(appformReqDto.getTelAdvice()) && CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) certStep++;
            }

            // 2-2. 안심보험
            if(!StringUtils.isBlank(appformReqDto.getInsrProdCd())) certStep+=2;

            // 2-3. 자급제 보상서비스
            if(!StringUtils.isBlank(appformReqDto.getRwdProdCd())) certStep+=2;

            // 2-4. 유심번호
            if(!StringUtil.isEmpty(appformReqDto.getReqUsimSn())){
                if(CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType()) && !"Y".equals(changeYn)) certStep+=2;
                else certStep++;
            }
        }

        // ------------------------------------------------------------------------------------------

        int dbStep = certService.getStepCnt();
        //logger.info("============= crtSaveAppFormStep > dbStep["+ dbStep +"], certSTep["+certStep+"]");
        logger.info("============= crtSaveAppFormStep > certSeq["+SessionUtils.getCertSession()+"], dbStep["+ dbStep +"], certSTep["+certStep+"]");
        if(dbStep < certStep) return false;
        return true;

        // ---------------------------------------------------------------------------------------------
    }

    /**
     * <pre>
     * 설명 : [step별 검증] 상담사 개통 최종 정보 확인
     * @param appformReqDto
     * @return: Map<String, String>
     * </pre>
     */
    @Override
    public Map<String, String> crtSaveAppFormInfo(AppformReqDto appformReqDto) {

        // ※ 개통신청_STEP별 본인인증 검증절차 문서 참고
        // ※ 상담사 유심개통, 상담사 단말개통(기변 포함), 대리점 유심개통, 대리점 단말개통(기변 포함), 상담사 esim개통, 상담사 esimWatch개통

        Map<String,String> rtnMap = new HashMap<>();

        List<String> certKeyList= new ArrayList<>();
        List<String> certValueList= new ArrayList<>();
        List<String> agentCertKeyList= new ArrayList<>();     // 대리인 정보 검증용
        List<String> agentCertValueList= new ArrayList<>();   // 대리인 정보 검증용

        // 0. 데이터 가공
        String operType= StringUtil.NVL(appformReqDto.getOperType(), "");
        String changeYn = (OPER_TYPE_CHANGE.equals(operType) || OPER_TYPE_EXCHANGE.equals(operType)) ? "Y" : "N"; // 기기변경
        String childYn = (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) ? "Y" : "N"; // 청소년
        String esimWatchYn = ("09".equals(appformReqDto.getUsimKindsCd()) && !StringUtil.isBlank(appformReqDto.getPrntsContractNo())) ? "Y" : "N"; //esimWatch
        String esimYn = ("09".equals(appformReqDto.getUsimKindsCd()) && StringUtil.isBlank(appformReqDto.getPrntsContractNo())) ? "Y" : "N"; //esim

        String authNm= ("Y".equals(childYn)) ? appformReqDto.getMinorAgentName() : appformReqDto.getCstmrName(); // 본인인증 이름
        String authBirthDate= appformReqDto.getBirthDate(); // 본인인증 생년월일

        // ------------------------------------------------------------------------------------------

        if("Y".equals(appformReqDto.getTelAdvice())){  // 해피콜

            // ** 특이케이스 처리 : 유심 신규 셀프개통 > SMS인증 후 정상해피콜로 전환한 경우 이름, 생년월일 변경 불가
            // 1. 공통 필수정보: 이름, 생년월일
            if(0 < certService.getModuTypeStepCnt("smsAuth", "")){
                certKeyList.add("name");
                certValueList.add(authNm);
                certKeyList.add("birthDate");
                certValueList.add(authBirthDate);
            }

        }else{

            if("Y".equals(childYn)){ // 청소년

                // 1. 공통 필수정보: 본인인증 유형, 이름, 생년월일 - 청소년인 경우 대리인 정보
                agentCertKeyList.add("name");
                agentCertValueList.add(authNm);
                agentCertKeyList.add("birthDate");
                agentCertValueList.add(authBirthDate);
                agentCertKeyList.add("authType");
                agentCertValueList.add(appformReqDto.getOnlineAuthType());

                // 2. 개별 폼 필수정보 - 청소년 구분이 필요한 정보
                if("Y".equals(esimWatchYn)){ // esimWatch: ci, 고객인증 이름, 고객인증 생년월일
                    agentCertKeyList.add("connInfo");
                    agentCertValueList.add(appformReqDto.getSelfCstmrCi());
                    certKeyList.add("name");
                    certValueList.add(appformReqDto.getCstmrName());
                    certKeyList.add("birthDate");
                    certValueList.add(appformReqDto.getCstmrNativeRrn());

                }else if("Y".equals(changeYn)){ // 기기변경: 고객인증 이름, 고객인증 생년월일
                    certKeyList.add("name");
                    certValueList.add(appformReqDto.getCstmrName());
                    certKeyList.add("birthDate");
                    certValueList.add(appformReqDto.getCstmrNativeRrn());
                }

            }else{

                // 1. 공통 필수정보: 본인인증 유형, 이름, 생년월일 - 청소년인 경우 대리인 정보
                certKeyList.add("name");
                certValueList.add(authNm);
                certKeyList.add("birthDate");
                certValueList.add(authBirthDate);
                certKeyList.add("authType");
                certValueList.add(appformReqDto.getOnlineAuthType());

                // 2. 개별 폼 필수정보 - 청소년 구분이 필요한 정보
                if("Y".equals(esimWatchYn)){ // esimWatch
                    certKeyList.add("connInfo");
                    certValueList.add(appformReqDto.getSelfCstmrCi());
                }

            }

            // 3. 개별 폼 필수정보 - 청소년 구분이 필요없는 정보
            if("Y".equals(esimYn)){  // esim: esimSeq
                certKeyList.add("uploadPhoneSrlNo");
                certValueList.add(appformReqDto.getUploadPhoneSrlNo()+"");

            }else if("Y".equals(esimWatchYn)){ // esimWatch: esimSeq, 계약번호
                certKeyList.add("uploadPhoneSrlNo");
                certValueList.add(appformReqDto.getUploadPhoneSrlNo()+"");
                certKeyList.add("contractNum");
                certValueList.add(appformReqDto.getPrntsContractNo());

            }else if("Y".equals(changeYn)){ // 기기변경: 기기변경 휴대폰번호, 계약번호

                // ** controller단에서 기기변경 session 정보와 핸드폰번호 검증하고 넘어옴
                certKeyList.add("mobileNo");
                certValueList.add(appformReqDto.getCstmrMobileFn() + appformReqDto.getCstmrMobileMn() + appformReqDto.getCstmrMobileRn());
                certKeyList.add("contractNum");
                certValueList.add(appformReqDto.getContractNum());
            }
        } // end of else --------------------------------

        // 4. 납부 인증 정보 option 정보
        if("D".equals(appformReqDto.getReqPayType()) || "C".equals(appformReqDto.getReqPayType())){
            if("Y".equals(childYn)){

                // ** 계좌인증/신용카드 이름 (해피콜 호환)
                if(!agentCertKeyList.contains("name")){
                    agentCertKeyList.add("name");
                    agentCertValueList.add(authNm);
                }

                if("D".equals(appformReqDto.getReqPayType())){
                    agentCertKeyList.add("reqAccountNumber");
                    agentCertValueList.add(appformReqDto.getReqAccountNumber());
                    agentCertKeyList.add("reqBank");
                    agentCertValueList.add(appformReqDto.getReqBank());
                }else if("C".equals(appformReqDto.getReqPayType())){
                    agentCertKeyList.add("reqCardNo");
                    agentCertValueList.add(appformReqDto.getReqCardNo());
                    agentCertKeyList.add("crdtCardTermDay");
                    agentCertValueList.add(appformReqDto.getReqCardYy() + appformReqDto.getReqCardMm());
                    agentCertKeyList.add("reqCardCompany");
                    agentCertValueList.add(appformReqDto.getReqCardCompany());
                }

            }else{

                // ** 계좌인증/신용카드 이름 (해피콜 호환)
                if(!certKeyList.contains("name")){
                    certKeyList.add("name");
                    certValueList.add(authNm);
                }

                if("D".equals(appformReqDto.getReqPayType())){
                    certKeyList.add("reqAccountNumber");
                    certValueList.add(appformReqDto.getReqAccountNumber());
                    certKeyList.add("reqBank");
                    certValueList.add(appformReqDto.getReqBank());
                }else if("C".equals(appformReqDto.getReqPayType())){
                    certKeyList.add("reqCardNo");
                    certValueList.add(appformReqDto.getReqCardNo());
                    certKeyList.add("crdtCardTermDay");
                    certValueList.add(appformReqDto.getReqCardYy() + appformReqDto.getReqCardMm());
                    certKeyList.add("reqCardCompany");
                    certValueList.add(appformReqDto.getReqCardCompany());
                }
            }
        }

        // 4-2. 유심번호 (option)
        if(!StringUtil.isEmpty(appformReqDto.getReqUsimSn())){
            certKeyList.add("reqUsimSn");
            certValueList.add(appformReqDto.getReqUsimSn());
        }

        // -----------------------------------------------------------------------------------------

        // 데이터 검증
        int certKeySize= certKeyList.size();
        int agentCertKeySize= agentCertKeyList.size();

        if(certKeySize > 0){
            certKeyList.add("urlType");
            certValueList.add("saveAppform");

            // 청소년인 경우 ncType은 0으로 세팅 (esim개통은 제외)
            if("Y".equals(childYn) && !"Y".equals(esimYn)){
                certKeyList.add("ncType");
                certValueList.add("0");
            }

            Map<String, String> vldReslt= certService.vdlCertInfo("D", certKeyList.toArray(new String[certKeySize]), certValueList.toArray(new String[certValueList.size()]));
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) return vldReslt;
        }

        if(agentCertKeySize > 0){
            agentCertKeyList.add("urlType");
            agentCertValueList.add("saveAgentAppform");
            agentCertKeyList.add("ncType");
            agentCertValueList.add("1");

            Map<String, String> agentVldReslt= certService.vdlCertInfo("D", agentCertKeyList.toArray(new String[agentCertKeySize]), agentCertValueList.toArray(new String[agentCertValueList.size()]));
            if(!AJAX_SUCCESS.equals(agentVldReslt.get("RESULT_CODE"))) return agentVldReslt;
        }

        // 데이터 검증 최종 성공
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_DESC", "성공");

        return rtnMap;

    }

    /**
     * <pre>
     * 설명 : [step별 검증] 셀프개통 사전체크 최종 정보 확인
     * @param appformReqDto
     * @return: Map<String, String>
     * </pre>
     */
    @Override
    public Map<String, String> crtSaveSimpleAppFormInfo(AppformReqDto appformReqDto) {

        // ※ 개통신청_STEP별 본인인증 검증절차 문서 참고
        // ※ 유심셀프개통, esimWatch 셀프개통, esim 셀프개통

        Map<String,String> rtnMap = new HashMap<>();

        List<String> certKeyList= new ArrayList<>();
        List<String> certValueList= new ArrayList<>();

        // 0. 데이터 가공
        String esimWatchYn = ("09".equals(appformReqDto.getUsimKindsCd()) && !StringUtil.isBlank(appformReqDto.getPrntsContractNo())) ? "Y" : "N"; //esimWatch

        // ----------------------------------------------------------------

        // 1. 필수 인증 내역 확인
        // 1-1. nicePin 연동내역 확인
        if(0 >= certService.getModuTypeStepCnt("nicePin", "")){
            rtnMap.put("RESULT_CODE", "STEP01");
            rtnMap.put("RESULT_DESC", STEP_CNT_EXCEPTION);
            return rtnMap;
        }

        // 1-2. 본인인증 인증내역 확인
        if(0 >= certService.getModuTypeStepCnt(CUST_AUTH, "")){
            rtnMap.put("RESULT_CODE", "STEP02");
            rtnMap.put("RESULT_DESC", STEP_CNT_EXCEPTION);
            return rtnMap;
        }

        // 1-3. sms 인증내역 확인: 신규개통인 경우만 (esimWatch 제외)
        if(OPER_TYPE_NEW.equals(appformReqDto.getOperType()) && !"Y".equals(esimWatchYn)){
            if(0 >= certService.getModuTypeStepCnt(SMS_AUTH, "")){
                rtnMap.put("RESULT_CODE", "STEP03");
                rtnMap.put("RESULT_DESC", STEP_CNT_EXCEPTION);
                return rtnMap;
            }
        }

        // 2. 공통 필수정보: 본인인증 유형, 이름, 생년월일, CI
        certKeyList.add("authType");
        certValueList.add(appformReqDto.getOnlineAuthType());
        certKeyList.add("name");
        certValueList.add(appformReqDto.getCstmrName());
        certKeyList.add("birthDate");
        certValueList.add(appformReqDto.getBirthDate());
        certKeyList.add("connInfo");
        certValueList.add(appformReqDto.getSelfCstmrCi());

        // 3. 번호이동 필수정보: 번호이동 전화번호
        // ** esimWatch는 신규가입만 가능 > 이어하기 시 번호이동도 선택할 수 있으나 신청폼은 신규가입폼... 임시로 번호이동 검사에서 제외처리
        if(OPER_TYPE_MOVE_NUM.equals(appformReqDto.getOperType()) && !"Y".equals(esimWatchYn)){
            String moveMobileFn= StringUtil.NVL(appformReqDto.getMoveMobileFn(), "");
            String moveMobileMn= StringUtil.NVL(appformReqDto.getMoveMobileMn(), "");
            String moveMobileRn= StringUtil.NVL(appformReqDto.getMoveMobileRn(), "");

            certKeyList.add("mobileNo");
            certValueList.add(moveMobileFn+moveMobileMn+moveMobileRn);
        }

        // 4. 개별 폼 필수정보
        if("Y".equals(esimWatchYn)){ // 4-1. esimWatch 필수정보: esimSeq, 계약번호
            certKeyList.add("uploadPhoneSrlNo");
            certValueList.add(appformReqDto.getUploadPhoneSrlNo()+"");
            certKeyList.add("contractNum");
            certValueList.add(appformReqDto.getPrntsContractNo());

        }else if("09".equals(appformReqDto.getUsimKindsCd())){ // 4-2. esim 필수정보: esimSeq
            certKeyList.add("uploadPhoneSrlNo");
            certValueList.add(appformReqDto.getUploadPhoneSrlNo()+"");

        }else{ // 4-3. usim 필수정보: 유심번호
            certKeyList.add("reqUsimSn");
            certValueList.add(appformReqDto.getReqUsimSn());
        }

        // ----------------------------------------------------------------

        // 데이터 검증
        int certKeySize= certKeyList.size();
        if(certKeySize > 0){
            certKeyList.add("urlType");
            certValueList.add("chkPreOpen");

            Map<String, String> vldReslt= certService.vdlCertInfo("D", certKeyList.toArray(new String[certKeySize]), certValueList.toArray(new String[certValueList.size()]));
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) return vldReslt;
        }

        // 데이터 검증 최종 성공
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_DESC", "성공");

        return rtnMap;
    }

    /**
     * <pre>
     * 설명 : [step별 검증] 셀프개통 최종 step 확인
     * @param appformReqDto
     * @return: boolean
     * </pre>
     */
    @Override
    public boolean crtUpdateSimpleAppFormStep(AppformReqDto appformReqDto) {

        // ※ 개통신청_STEP별 본인인증 검증절차 문서 참고
        // ※ 유심셀프개통, esimWatch 셀프개통, esim 셀프개통

        // 0. 데이터 가공
        String esimWatchYn = ("09".equals(appformReqDto.getUsimKindsCd()) && !StringUtil.isBlank(appformReqDto.getPrntsContractNo())) ? "Y" : "N"; //esimWatch
        String esimYn = ("09".equals(appformReqDto.getUsimKindsCd()) && StringUtil.isBlank(appformReqDto.getPrntsContractNo())) ? "Y" : "N"; //esim

        // ------------------------------------------------------------------------------------------

        // 필수로 존재해야하는 최소 step 수
        int certStep= 0;

        // 1. 개별 폼 별 default step 수
        if("Y".equals(esimWatchYn)) certStep= 11; // esimWatch
        else if("Y".equals(esimYn)) certStep= 10; // esim
        else certStep= 10; // usim

        // 2. option step 수
        // 2-1. 계좌인증 (esimWatch의 reqPayType은 통합청구[0])
        if("D".equals(appformReqDto.getReqPayType()) || "C".equals(appformReqDto.getReqPayType())) certStep+=2;

        // 2-2. 안심보험 (esimWatch는 안심보험 가입 불가 [null])
        if(!StringUtils.isBlank(appformReqDto.getInsrProdCd())) certStep+=2;

        // 2-3. 자급제 보상서비스 (esimWatch는 안심보험 가입 불가 [null])
        if(!StringUtils.isBlank(appformReqDto.getRwdProdCd())) certStep+=2;

        // ------------------------------------------------------------------------------------------

        int dbStep = certService.getStepCnt();
        logger.info("============= crtSaveAppFormStep > dbStep["+ dbStep +"], certSTep["+certStep+"]");
        if(dbStep < certStep) return false;
        return true;
    }

    /**
     * <pre>
     * 설명 : [step별 검증] 셀프개통 최종 정보 확인
     * @param appformReqDto
     * @return: Map<String, String>
     * </pre>
     */
    @Override
    public Map<String, String> crtUpdateSimpleAppFormInfo(AppformReqDto appformReqDto) {

        // ※ 개통신청_STEP별 본인인증 검증절차 문서 참고
        // ※ 유심셀프개통, esimWatch 셀프개통, esim 셀프개통,
        //    유심셀프개통(자동해피콜), esimWatch 셀프개통(자동해피콜), esim 셀프개통(자동해피콜)

        Map<String,String> rtnMap = new HashMap<>();

        List<String> certKeyList= new ArrayList<>();
        List<String> certValueList= new ArrayList<>();

        // 0. 데이터 가공
        String esimWatchYn = ("09".equals(appformReqDto.getUsimKindsCd()) && !StringUtil.isBlank(appformReqDto.getPrntsContractNo())) ? "Y" : "N"; //esimWatch
        String onOffType = (appformReqDto.getOnOffType() == null) ? "9999" : appformReqDto.getOnOffType();
        String selfYn =("|5|7|".indexOf(onOffType) > 0) ? "Y" : "N"; // 셀프개통 (N인경우 자동해피콜)

        // ----------------------------------------------------------------

        // 1. nicePin 인증연동 확인
        if(0 >= certService.getModuTypeStepCnt("nicePin", "")){
            rtnMap.put("RESULT_CODE", "STEP01");
            rtnMap.put("RESULT_DESC", STEP_CNT_EXCEPTION);
            return rtnMap;
        }

        // 2. 공통 필수정보: 본인인증 유형, 이름, 생년월일, CI
        certKeyList.add("authType");
        certValueList.add(appformReqDto.getOnlineAuthType());
        certKeyList.add("name");
        certValueList.add(appformReqDto.getCstmrName());
        certKeyList.add("birthDate");
        certValueList.add(appformReqDto.getBirthDate());
        certKeyList.add("connInfo");
        certValueList.add(appformReqDto.getSelfCstmrCi());

        // 3. 번호이동 필수정보: 번호이동 전화번호 (자동해피콜인 경우 non)
        // ** esimWatch는 신규가입만 가능 > 이어하기 시 번호이동도 선택할 수 있으나 신청폼은 신규가입폼... 임시로 번호이동 검사에서 제외처리
        if("Y".equals(selfYn)){
            if(OPER_TYPE_MOVE_NUM.equals(appformReqDto.getOperType()) && !"Y".equals(esimWatchYn)){
                String moveMobileFn= StringUtil.NVL(appformReqDto.getMoveMobileFn(), "");
                String moveMobileMn= StringUtil.NVL(appformReqDto.getMoveMobileMn(), "");
                String moveMobileRn= StringUtil.NVL(appformReqDto.getMoveMobileRn(), "");

                certKeyList.add("mobileNo");
                certValueList.add(moveMobileFn+moveMobileMn+moveMobileRn);
            }
        }

        // 4. 개별 폼 필수정보
        if("Y".equals(esimWatchYn)){ // 4-1. esimWatch 필수정보: esimSeq, 계약번호
            certKeyList.add("uploadPhoneSrlNo");
            certValueList.add(appformReqDto.getUploadPhoneSrlNo()+"");
            certKeyList.add("contractNum");
            certValueList.add(appformReqDto.getPrntsContractNo());

        }else if("09".equals(appformReqDto.getUsimKindsCd())){ // 4-2. esim 필수정보: esimSeq
            certKeyList.add("uploadPhoneSrlNo");
            certValueList.add(appformReqDto.getUploadPhoneSrlNo()+"");

        }else{ // 4-3. usim 필수정보: 유심번호 (자동해피콜인 경우 option)

            if("Y".equals(selfYn) || !StringUtil.isEmpty(appformReqDto.getReqUsimSn())){
                certKeyList.add("reqUsimSn");
                certValueList.add(appformReqDto.getReqUsimSn());
            }
        }

        // 5. option 정보 (esimWatch의 reqPayType은 통합청구[0])
        // 5-1. 계좌인증: 계좌번호, 은행코드
        if("D".equals(appformReqDto.getReqPayType()) || "C".equals(appformReqDto.getReqPayType())){

            if("D".equals(appformReqDto.getReqPayType())){
                certKeyList.add("reqAccountNumber");
                certValueList.add(appformReqDto.getReqAccountNumber());
                certKeyList.add("reqBank");
                certValueList.add(appformReqDto.getReqBank());
            }else if("C".equals(appformReqDto.getReqPayType())){
                certKeyList.add("reqCardNo");
                certValueList.add(appformReqDto.getReqCardNo());
                certKeyList.add("crdtCardTermDay");
                certValueList.add(appformReqDto.getReqCardYy() + appformReqDto.getReqCardMm());
                certKeyList.add("reqCardCompany");
                certValueList.add(appformReqDto.getReqCardCompany());
            }
        }

        // ----------------------------------------------------------------

        // 데이터 검증
        int certKeySize= certKeyList.size();
        if(certKeySize > 0){
            certKeyList.add("urlType");
            certValueList.add("saveSimpleAppform");

            Map<String, String> vldReslt= certService.vdlCertInfo("D", certKeyList.toArray(new String[certKeySize]), certValueList.toArray(new String[certValueList.size()]));
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) return vldReslt;
        }

        // 데이터 검증 최종 성공
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_DESC", "성공");

        return rtnMap;
    }


    /* (셀프개통) 직영 평생할인 프로모션 찾아오기 */
    @Override
    public String getChrgPrmtId(AppformReqDto appformReqDto){

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/getChrgPrmtId", appformReqDto, String.class);

    }

    /* 평생할인 셀프개통 프로모션 기적용 테이블 insert */
    @Override
    public int insertDisPrmtApd(AppformReqDto appformReqDto, String evntCd) {

        /* 기적용 테이블 필수 파라미터 */
        /* requestKey, contractNum, prmtId, cntpntShopId, socCode, onOffType, operType, reqBuyType*/
        AppformReqDto disApdParam = this.getAppForm(appformReqDto);
        disApdParam.setEvntCd(evntCd);

        //직영 프로모션 아이디 조회
        String prmtId = this.getChrgPrmtId(disApdParam);
        if (!StringUtils.isBlank(prmtId)) disApdParam.setPrmtId(prmtId);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/insertDisPrmtApd", disApdParam, Integer.class);
    }

    /* (오프라인) 평생할인 프로모션 ID 찾아오기 */
    @Override
    public String getDisPrmtId(AppformReqDto appformReqDto) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/getDisPrmtId", appformReqDto, String.class);
    }

    /*Mplatform OSST 연동 처리 (ST1)*/
    @Override
    public MSimpleOsstXmlSt1VO sendOsstSt1Service(String resNo , String eventCd) throws SelfServiceException, SocketTimeoutException, McpMplatFormException {

        MSimpleOsstXmlSt1VO simpleOsstXmlSt1VO = new MSimpleOsstXmlSt1VO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);

        mplatFormOsstServerAdapter.callService(param, simpleOsstXmlSt1VO, 100000);
        return simpleOsstXmlSt1VO;
    }

    /*Mplatform OSST 연동 처리 (ST1)*/
    @Override
    public MSimpleOsstXmlSt1VO sendOsstSt1Service(Map<String, String> osstParam, String eventCd) throws SelfServiceException, SocketTimeoutException, McpMplatFormException {

        MSimpleOsstXmlSt1VO simpleOsstXmlSt1VO = new MSimpleOsstXmlSt1VO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);

        if(osstParam != null && !osstParam.isEmpty()){
            osstParam.keySet().stream().forEach(key -> param.put(key, osstParam.get(key)));
        }

        mplatFormOsstServerAdapter.callService(param, simpleOsstXmlSt1VO, 100000);
        return simpleOsstXmlSt1VO;
    }

    /*사전체크 완료상태 조회 (사전체크 작업 완료 후 MP측 DB작업 반영 상태 조회)*/
    @Override
    public Map<String,String> chkRealPc2Result(String resNo, String ncn) {

        Map<String, String> rtnMap = new HashMap<>();
        rtnMap.put("EVENT_CODE", EVENT_CODE_PRE_SCH);

        MSimpleOsstXmlSt1VO mSimpleOsstXmlSt1VO = null;

        String prntsContractNo= null;
        String customerId= null;

        Map<String, String> osstParam= new HashMap<>();
        osstParam.put("resNo", resNo);

        if(!StringUtils.isEmpty(ncn)){  // 모회선 계약번호와 고객아이디 함께 전달 (데이터쉐어링 케이스)

            Map<String,String> prntsInfo= this.getPrntsInfo(ncn);

            if(prntsInfo != null){
                prntsContractNo= prntsInfo.get("prntsContractNo");
                customerId= prntsInfo.get("customerId");
            }
        }

        if(prntsContractNo != null) osstParam.put("prntsContractNo", prntsContractNo);
        if(customerId != null) osstParam.put("custNo", customerId);

        try {

            // 최대 2번까지 재시도
            for (int i = 0; i < 2; i++) {

                // ST1 연동
                Thread.sleep(5000);
                mSimpleOsstXmlSt1VO = this.sendOsstSt1Service(osstParam, Constants.EVENT_CODE_PRE_SCH);

                // 1.응답 확인
                // ** 실제로 응답 parse 과정에서 isSuccess가 false인 경우 SelfServiceException 호출
                if (!mSimpleOsstXmlSt1VO.isSuccess()) {
                    rtnMap.put("RESULT_CODE", "ERROR_001");
                    rtnMap.put("RESULT_XML", mSimpleOsstXmlSt1VO.getResponseXml());
                    rtnMap.put("ERROR_MSG", mSimpleOsstXmlSt1VO.getSvcMsg());  // responseBasic
                    return rtnMap;
                }

                // 2.처리 결과 확인
                if(!OSST_SUCCESS.equals(mSimpleOsstXmlSt1VO.getRsltCd())){
                    rtnMap.put("RESULT_CODE", "ERROR_002");
                    rtnMap.put("RESULT_XML", mSimpleOsstXmlSt1VO.getResponseXml());
                    rtnMap.put("ERROR_MSG", mSimpleOsstXmlSt1VO.getRsltMsg());  // rsltMsg
                    return rtnMap;
                }

                // 3.상태 확인
                if(Constants.EVENT_CODE_PC_RESULT.equals(mSimpleOsstXmlSt1VO.getPrgrStatCd())){
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    break;
                }

                // 4. 상태조회 재시도
                // logger.info("========= ["+resNo+"] RETRY ST1 : prgrStatCd is " + mSimpleOsstXmlSt1VO.getPrgrStatCd());

            } // end of for----------------------------------

            if(!AJAX_SUCCESS.equals(rtnMap.get("RESULT_CODE"))){
                rtnMap.put("RESULT_CODE", "ERROR_003");
                rtnMap.put("ERROR_MSG", "[ST1] 사전체크 처리 완료 결과 확인 불가");
            }

        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "ERROR_004");
            rtnMap.put("ERROR_MSG", "[ST1] response massage is null.");
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "ERROR_005");
            rtnMap.put("ERROR_MSG", "[ST1] SocketTimeout");
        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "ERROR_006");
            rtnMap.put("ERROR_MSG", e.getMessage());  // responseCode;;;responseBasic
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "ERROR_007");
            rtnMap.put("ERROR_MSG", "[ST1] Exception");
        }

        return rtnMap;
    }


    /**
     * <pre>
     * 설명     : 셀프개통 가능 플랫폼 체크
     * @param : operType
     * @return: Map<String,String>
     * </pre>
     */
    @Override
    public Map<String, String> isSimpleApplyPlatform(String operType) {

        Map<String,String> rtnMap= new HashMap<>();

        // 1. 플랫폼 확인 (A : 앱, M: 모바일, P: PC)
        String platformCd= NmcpServiceUtils.getPlatFormCd();
        rtnMap.put("platformCd", platformCd);
        rtnMap.put("applyYn", "N");
        rtnMap.put("errMsg", SIMPLE_OPEN_PLATFORM_EXCEPTION);

        // 2. 셀프개통 가능 플랫폼 확인
        NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(Constants.SIMPLE_OPEN_PLATFORM_CODE, platformCd);

        if (nmcpCdDtlDto != null ) {

            // 2-1. 번호이동
            if(Constants.OPER_TYPE_MOVE_NUM.equals(operType) && "Y".equals(nmcpCdDtlDto.getExpnsnStrVal1())){
                rtnMap.put("applyYn", "Y");
                rtnMap.put("errMsg", "");
            }
            // 2-2. 신규가입
            else if(Constants.OPER_TYPE_NEW.equals(operType) && "Y".equals(nmcpCdDtlDto.getExpnsnStrVal2())){
                rtnMap.put("applyYn", "Y");
                rtnMap.put("errMsg", "");
            }
            else{
                rtnMap.put("applyYn", "N");
                rtnMap.put("errMsg", SIMPLE_OPEN_PLATFORM_EXCEPTION);
            }
        }

        return rtnMap;
    }


    @Override
    @Transactional
    public boolean updateDirectPhone(AppformReqDto appformReqDto ) {

        McpRequestSaleinfoDto mcpRequestSaleinfo = new McpRequestSaleinfoDto();
        mcpRequestSaleinfo.setRequestKey(appformReqDto.getRequestKey());
        mcpRequestSaleinfo.setSettlApvNo(appformReqDto.getSettlApvNo());
        mcpRequestSaleinfo.setSettlTraNo(appformReqDto.getSettlTraNo());


        //결제 정보 UPDATE 처리
        McpRequestPayInfoDto mcpRequestPayInfo = new McpRequestPayInfoDto();
        mcpRequestPayInfo.setRequestKey(appformReqDto.getRequestKey());
        mcpRequestPayInfo.setPayTrx(appformReqDto.getSettlTraNo());
        mcpRequestPayInfo.setPayRstCd("01");  //01 정상(결제완료)
        mcpRequestPayInfo.setAmdId("callBack");
        mcpRequestPayInfo.setAmdIp("");

        if( appformDao.updateMcpRequestSaleinfo(mcpRequestSaleinfo) && appformDao.updateAppForPstate(appformReqDto.getRequestKey()) && appformDao.updateMcpRequestPayInfo(mcpRequestPayInfo)  ) {
            return true;
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    public int getNacSelfCount() {

        String clientIp = ipstatisticService.getClientIp();

        // 제한 기간 조회
        NmcpCdDtlDto nacSelfLimit = NmcpServiceUtils.getCodeNmDto(CMM_PERIOD_LIMIT, "NacSelfLimit");
        if(nacSelfLimit == null || StringUtil.isEmpty(nacSelfLimit.getExpnsnStrVal1())){
            return 0;  // 제한을 두지 않음
        }

        // 제한 예외 IP 확인
        NmcpCdDtlDto exceptionIp= fCommonSvc.getDtlCodeWithNm(NAC_SELF_LIMIT_EXCEPTION_IP, clientIp);
        if(exceptionIp != null){
            return 0;  // 제한을 두지 않음
        }

        // 제한 기간 내 010 신규셀프개통 건 수 조회
        Map<String, String> param = new HashMap<>();
        param.put("rip", clientIp);
        param.put("limitDay", nacSelfLimit.getExpnsnStrVal1());
        int nacSelfCnt =  appformDao.getNacSelfCount(param);

        // 차단되는 경우 이력 저장
        if(nacSelfCnt > 0){
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NAC_DUP_IP_ERROR");
            mcpIpStatisticDto.setPrcsSbst("Rip[" + clientIp + "] NacSelfCnt[" + nacSelfCnt + "]");
            mcpIpStatisticDto.setTrtmRsltSmst("getNacSelfCount");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
        }

        return nacSelfCnt;
    }

    /** 번호이동 사전체크 일 건수 제한 */
    @Override
    public Map<String, Object> mnpPreCheckLimit(String moveMobileNo) {

        Map<String, Object> rtnMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        List<String> resNoList = new ArrayList<>();

        // 제한시간(분) 조회
        int limitDay = 0;
        int limitCnt = 0;
        String fAlertMsg = "";

        NmcpCdDtlDto limitDto = NmcpServiceUtils.getCodeNmDto(CMM_PERIOD_LIMIT, "MnpDayLimit");

        if(limitDto != null){
            limitDay = Integer.parseInt(StringUtil.NVL(limitDto.getExpnsnStrVal1(), "0"));
            limitCnt = Integer.parseInt(StringUtil.NVL(limitDto.getExpnsnStrVal2(), "0"));
            fAlertMsg = limitDto.getExpnsnStrVal3();

            // 동일 번호이동전화번호 신청서 조회
            paramMap.put("limitDay", limitDay);
            paramMap.put("moveMobileNo", moveMobileNo);
            resNoList = appformDao.getResNoByMoveMobileNum(paramMap);
        }

        // 특정기간 내 신청건 없음 → 성공처리
        if(resNoList.isEmpty()){
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        // 사전체크 시도 이력 확인
        paramMap.put("resNoList", resNoList);
        paramMap.put("prgrStatCd", Constants.EVENT_CODE_PRE_CHECK);
        int tryCnt = appformDao.getPreCheckTryCnt(paramMap);

        if(limitCnt == 0 || tryCnt < limitCnt){
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        // 실패이력 저장
        McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
        mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
        mcpIpStatisticDto.setTrtmRsltSmst(moveMobileNo);
        mcpIpStatisticDto.setPrcsSbst("Exception[PC0_DAY_LIMIT]");
        mcpIpStatisticDto.setParameter("MOVE_MOBILE_NUM[" + moveMobileNo + "] TRY_CNT["+ tryCnt +"] LIMIT_CNT[" + limitCnt + "]");
        ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

        rtnMap.put("RESULT_CODE", "-9999");
        rtnMap.put("ERROR_MSG", "PC0_TIME_LIMIT");
        rtnMap.put("ERROR_NE_MSG", fAlertMsg);
        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : Acen 연동 대상 조건 확인
     * @param  : McpRequestDto
     * @param  : etcParam
     * @return : boolean
     * </pre>
     */
    @Override
    public boolean chkAcenReqCondition(McpRequestDto mcpRequestDto, Map<String,String> etcParam) {

        // 0. 데이터쉐어링 요금제 제외
        if("KISOPMDSB".equals(etcParam.get("socCode"))){
            return false;
        }

        // 1.대리점: 기존 'M모바일(직영온라인)'에서 대리점 확장됨에 따라 공통코드의 확장필드1(개통 진행을 위한 해피콜 사용여부Y/N)의 값으로 결정
        NmcpCdDtlDto acenTargetAgent = NmcpServiceUtils.getCodeNmDto(ACEN_TARGET_AGENT, mcpRequestDto.getCntpntShopId());
        if ( acenTargetAgent == null || !"Y".equals(acenTargetAgent.getExpnsnStrVal1()) ){
            return false;
        }

        // 2.고객구분: 19세 이상 내국인
        if(!CSTMR_TYPE_NA.equals(mcpRequestDto.getCstmrType())){
            return false;
        }

        // 3.신청유형: 신규가입, 번호이동
        String operType = mcpRequestDto.getOperType();
        if(!OPER_TYPE_NEW.equals(operType) && !OPER_TYPE_MOVE_NUM.equals(operType)){
            return false;
        }

        // 4.구매유형: 유심단독
        if(!REQ_BUY_TYPE_USIM.equals(mcpRequestDto.getReqBuyType())){
            return false;
        }

        // 5. 모집경로: 온라인, 모바일, 온라인(해피콜), 모바일(해피콜)
        String onOffType= StringUtil.NVL(mcpRequestDto.getOnOffType(), "9999");
        if("|0|3|6|8|".indexOf(onOffType) <= 0){
            return false;
        }

        // 6. 데이터타입: LTE, 5G
        String dataType= etcParam.get("dataType");
        if(!LTE_FOR_MSP.equals(dataType) && !FIVE_G_FOR_MSP.equals(dataType)){
            return false;
        }

        if("09".equals(mcpRequestDto.getUsimKindsCd())){
            // 7. esimWatch 제외
            if(!StringUtil.isEmpty(mcpRequestDto.getPrntsContractNo())){
                return false;
            }

            // 8. esim 공통코드에 따라 처리
            NmcpCdDtlDto esimTargetCd = NmcpServiceUtils.getCodeNmDto("AcenLimit", "useEsim");
            String esimTargetYn = null;

            if(OPER_TYPE_NEW.equals(operType)) {
              esimTargetYn = (esimTargetCd == null || "N".equals(esimTargetCd.getExpnsnStrVal1())) ? "N" : "Y"; // esim 신규개통
            }else{
              esimTargetYn = (esimTargetCd == null || "N".equals(esimTargetCd.getExpnsnStrVal2())) ? "N" : "Y"; // esim 번호이동
            }

            if("N".equals(esimTargetYn)){
                return false;
            }
        }

        return true;
    }

    /**
     * <pre>
     * 설명     : Acen 연동 대상 insert
     * @param  : mcpRequestDto
     * @return : void
     * </pre>
     */
    @Override
    public void insertAcenReqTrg(McpRequestDto mcpRequestDto) {

        AcenDto acenDto = new AcenDto();
        acenDto.setRequestKey(mcpRequestDto.getRequestKey());
        acenDto.setResNo(mcpRequestDto.getResNo());
        acenDto.setRegstId(mcpRequestDto.getCretId());

        // esim인 경우 유심있음 처리
        boolean isNoSim = StringUtil.isEmpty(mcpRequestDto.getReqUsimSn())
                          && !"09".equals(mcpRequestDto.getUsimKindsCd());

        if(OPER_TYPE_NEW.equals(mcpRequestDto.getOperType())){
            if(isNoSim){  // 신규 유심없음
                acenDto.setEvntGrpCd("NEW_NOSIM");
                acenDto.setEvntType("NEW_NOSIM_01");
            }else{  // 신규 유심있음
                acenDto.setEvntGrpCd("NEW_SIM");
                acenDto.setEvntType("NEW_SIM_01");
            }
        }else{
            if(isNoSim){  // 번이 유심없음
                acenDto.setEvntGrpCd("MNP_NOSIM");
                acenDto.setEvntType("MNP_NOSIM_01");
            }else{  // 번이 유심있음
                acenDto.setEvntGrpCd("MNP_SIM");
                acenDto.setEvntType("MNP_SIM_01");
            }
        }

        appformDao.insertAcenReqTrg(acenDto);
    }

    private Map<String, String> getPrntsInfo(String ncn) {

        Map<String, String> prntsInfo= new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        McpUserCntrMngDto resultOut = null;

        if (userSession != null && !StringUtil.isEmpty(userSession.getUserId())) {
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        } else {
            McpUserCntrMngDto out = SessionUtils.getNonmemberSharingInfo();
            if (out == null) return null;

            resultOut = mypageService.selectCntrListNoLogin(out);
        }

        String prntsContractNo = null;  // 모회선 계약번호
        String customerId = null;       // 고객아이디

        if (cntrList != null) {
            for (McpUserCntrMngDto userCntrMng : cntrList) {
                if (userCntrMng.getSvcCntrNo().equals(ncn)) {
                    prntsContractNo = userCntrMng.getContractNum();
                    customerId = userCntrMng.getCustId();
                    break;
                }
            }
        } else {
            if (resultOut != null && resultOut.getSvcCntrNo().equals(ncn)) {
                prntsContractNo = resultOut.getContractNum();
                customerId = resultOut.getCustId();
            }
        }

        prntsInfo.put("prntsContractNo", prntsContractNo);
        prntsInfo.put("customerId", customerId);

        return prntsInfo;
    }

    @Override
    public Map<String, String> checkDupReq(AppformReqDto appformReqDto) {
        Map<String, String> result = new HashMap<>();
        result.put("RESULT_CODE", AJAX_SUCCESS);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("pstate", "00");
        paramMap.put("operType", appformReqDto.getOperType());
        paramMap.put("serviceType", "PO");
        paramMap.put("requestStateCode", "21");
        paramMap.put("baseDt", "7");
        if (!"".equals(appformReqDto.getReqUsimSn()) && appformReqDto.getReqUsimSn() != null) {
            paramMap.put("reqUsimSn", appformReqDto.getReqUsimSn());
            if (appformDao.chkDupByReqUsimSn(paramMap) > 0) {
                result.put("RESULT_CODE", "9999");
                result.put("RESULT_MSG", "해당 유심번호로 진행중인 신청 건이 있습니다.<br/>유심 번호 확인을 부탁드리며, 재신청은 현재 진행중인 신청 건 완료 후 가능합니다.");
            }
        }
        if (OPER_TYPE_MOVE_NUM.equals(appformReqDto.getOperType())) {
            String movePhoneNum = appformReqDto.getMoveMobileFn() + appformReqDto.getMoveMobileMn() + appformReqDto.getMoveMobileRn();
            paramMap.put("movePhoneNum", movePhoneNum);
            if (appformDao.chkDupByMovePhoneNum(paramMap) > 0) {
                result.put("RESULT_CODE", "9998");
                result.put("RESULT_MSG", "해당 전화번호로 진행중인 신청 건이 있습니다.<br/>번호이동 할 전화번호를 확인 부탁드리며, 재신청은 현재 진행중인 신청 건 완료 후 가능합니다.");
            }
        }
        return result;
    }
    @Override
    public List<MspSalePlcyMstDto> getSalePlcyInfo(AppformReqDto appformReq) {
        RestTemplate restTemplate = new RestTemplate();
        MspSalePlcyMstDto[] list = restTemplate.postForObject(apiInterfaceServer + "/appform/getSalePlcyInfo", appformReq, MspSalePlcyMstDto[].class);
        List<MspSalePlcyMstDto> getSalePlcyInfoList = Arrays.asList(list);
        return getSalePlcyInfoList;
    }

    @Override
    public void containsGoldNumbers(List<String> reqWantNumbers) {
        NmcpCdDtlDto nmcpCdDtlDto = new NmcpCdDtlDto();
        nmcpCdDtlDto.setCdGroupId("GoldNumberList");
        List<String> goldNumberList = fCommonSvc.getCodeList(nmcpCdDtlDto).stream()
                .map(NmcpCdDtlDto::getDtlCd)
                .collect(Collectors.toList());

        List<String> filteredNumbers = reqWantNumbers.stream()
                .filter(goldNumberList::contains)
                .collect(Collectors.toList());

        if (!filteredNumbers.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(String.join(", ", filteredNumbers)).append("]<br>");
            sb.append(ExceptionMsgConstant.CONTAINS_GOLD_NUMBER_EXCEPTION);
            throw new McpCommonJsonException("9999", sb.toString());
        }
    }


    @Override
    public Map<String, Object> isSimpleApplyObj()  {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        boolean isMacTime = true; //NAC
        boolean isMnpTime = true;

        //테스트 계정 통과 처리
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession != null && !StringUtils.isBlank(userSession.getUserId() ) ) {
            String isExceptionId = NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION , userSession.getUserId());
            if (isExceptionId != null && "Y".equals(isExceptionId) ) {
                rtnMap.put("IsMacTime", isMacTime);
                rtnMap.put("IsMnpTime", isMnpTime);
                return rtnMap;
            }
        }

        String nowDate = DateTimeUtil.getFormatString("yyyyMMdd");

        try {
            //신규
            //"08:00", "19:00"
            //08:00~21:50
            if (!DateTimeUtil.isMiddleTime("08:00", "21:50")) {    //DateTimeUtil.isMiddleTime("08:00", "21:50")
                isMacTime = false; //NAC
            }

            //번호이동
            //"10:00", "19:00"
            //10:00~19:50
            if (!DateTimeUtil.isMiddleTime("10:00", "19:50")) {    //DateTimeUtil.isMiddleTime("10:00", "19:50")
                isMnpTime = false;
            }

            //공통코드에서 작업여부 확인
            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(Constants.SIMPLE_OPEN_EXCEPTION_GROP_CODE, nowDate);
            if (nmcpCdDtlDto != null) {
                if ("Y".equals(nmcpCdDtlDto.getExpnsnStrVal1())) {
                    isMacTime = false;
                }

                if ("Y".equals(nmcpCdDtlDto.getExpnsnStrVal2())) {
                    isMnpTime = false;
                }
            }

            /*
             * 셀프프개통_번호이동의 경우 일요일 개통이 불가하여 일요일에 셀프개통 선택 시(셀프개통 가입조건 선택 화면 진입 시) 번호이동 선택 불가 처리 필요
             */
            int whichDay = DateTimeUtil.whichDay(nowDate);
            if (isMnpTime && whichDay == 1) {
                isMnpTime = false;
            }
        } catch (ParseException e) {
            throw new McpCommonException("ParseException");
        }

        rtnMap.put("IsMacTime", isMacTime);
        rtnMap.put("IsMnpTime", isMnpTime);

        return rtnMap;
    }
    @Override
    public AppformReqDto getJehuUsimlessByResNo(String resNo){
        return appformDao.getJehuUsimlessByResNo(resNo);
    }

    @Override
    public HashMap<String, Object> processOsstFt0Service(OsstFathReqDto osstFathReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        int maxRetryCount = Integer.parseInt(NmcpServiceUtils.getCodeNm("fathCertPolicy", "reTryFT0"));
        for (int i = 0; i < maxRetryCount; i++) {

            try {
                //Thread.sleep(3000);
                MSimpleOsstXmlFt0VO simpleOsstXmlFt0VO = this.sendOsstFt0Service(osstFathReqDto);
                if (simpleOsstXmlFt0VO.isSuccess()) {
                    
                    //안면인증 안정화기간 여부
                    String stbznPerdYn = simpleOsstXmlFt0VO.getStbznPerdYn();
                    SessionUtils.saveStbznPerdYn(stbznPerdYn);

                    String trtResltCd = simpleOsstXmlFt0VO.getTrtResltCd();
                    return fathService.fathResltRtn(trtResltCd, osstFathReqDto);
                } 
            } catch (Exception e) {
                logger.error("OSST FT0 연동 재시도 #" + (i+1) );
               
                //Exception 반복문 재시도 
            }
        }
        //최종 성공아닐경우 연동실패로 리턴
        rtnMap.put("RESULT_CODE", "0001");
        rtnMap.put("RESULT_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");

        //이력 정보 저장 처리
        McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
        mcpIpStatisticDto.setPrcsMdlInd("FT0_ERROR");
        mcpIpStatisticDto.setPrcsSbst("Exception[Exception]");
        ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
        
        return rtnMap;
    }

    private MSimpleOsstXmlFt0VO sendOsstFt0Service(OsstFathReqDto osstFathReqDto) throws SelfServiceException, SocketTimeoutException ,McpMplatFormException{
        MSimpleOsstXmlFt0VO simpleOsstXmlFt0VO =  new MSimpleOsstXmlFt0VO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", Constants.EVENT_CODE_FATH_TGT_YN);
        param.put("onlineOfflnDivCd", osstFathReqDto.getOnlineOfflnDivCd());
        param.put("orgId", osstFathReqDto.getOrgId());
        param.put("retvCdVal", osstFathReqDto.getRetvCdVal());
        param.put("asgnAgncId", osstFathReqDto.getAsgnAgncId());
        param.put("cpntId", osstFathReqDto.getCpntId());
        param.put("resNo", osstFathReqDto.getResNo());

        mplatFormOsstServerAdapter.callService(param,simpleOsstXmlFt0VO,100000);
        return simpleOsstXmlFt0VO;
    }

    @Override
    public HashMap<String, Object> processOsstFs8Service(OsstFathReqDto osstFathReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        MSimpleOsstXmlFs8VO simpleOsstXmlFs8VO = null; 
        
        try {
            simpleOsstXmlFs8VO = this.sendOsstFs8Service(osstFathReqDto);

            if (!simpleOsstXmlFs8VO.isSuccess()) {
                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
                return rtnMap;
            }
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "0003");
            rtnMap.put("RESULT_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("FS8_ERROR");
            mcpIpStatisticDto.setPrcsSbst("Exception[Exception]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
        }

        SessionUtils.increaseFathTryCount(); //URL전송받기 성공시 시도횟수 +

        String resltCd = simpleOsstXmlFs8VO.getResltCd();
        HashMap<String, Object> resltRtnMap = fathService.fathResltRtn(resltCd, osstFathReqDto);
        if(!AJAX_SUCCESS.equals(resltRtnMap.get("RESULT_CODE"))) {
            return resltRtnMap;
        }

        SessionUtils.saveFathTransacId(simpleOsstXmlFs8VO.getFathTransacId());   // 트랜잭션 ID 세션 저장
        //CD02, CD05는 FS9 연동하여 연동결과 리턴
        if("CD02".equals(resltCd) || "CD05".equals(resltCd)) {
            //FS9는 트래픽1회로 제한 (연동 이력이 존재하면 재연동하지않는다.)
            if("Y".equals(SessionUtils.getFathSession().getIsFs9())) {
                throw new McpCommonJsonException("9997", "이미 안면인증 결과확인을 하셨습니다.");
            }
            return this.processOsstFs9Service(osstFathReqDto, resltCd);
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        StringBuilder sb = new StringBuilder("안면인증 URL이 전송되었습니다.");
        if (this.isEnabledFT1()) {
            sb.append("<br><br>※ 현재는 시범기간으로 안면인증을 SKIP 하실 수 있습니다.");
        }
        rtnMap.put("RESULT_MSG", sb.toString());
        rtnMap.put("RESLT_SBST", simpleOsstXmlFs8VO.getResltSbst());
        rtnMap.put("FATH_TRANSAC_ID", simpleOsstXmlFs8VO.getFathTransacId());
        rtnMap.put("RESLT_CD", simpleOsstXmlFs8VO.getResltCd());
        if ("STG".equals(serverName)) {
            rtnMap.put("URL_ADR", simpleOsstXmlFs8VO.getUrlAdr());
        }

        fathService.sendMsgFathUrl(simpleOsstXmlFs8VO.getUrlAdr(), osstFathReqDto.getSmsRcvTelNo());

        // 셀프안면인증 개인화 URL인 경우 신청서에 트랜잭션 ID 저장
        if("P".equals(osstFathReqDto.getGubun())) {
            AppformReqDto appformReqDto = new AppformReqDto();
            appformReqDto.setResNo(osstFathReqDto.getResNo());
            appformReqDto.setFathTransacId(String.valueOf(rtnMap.get("FATH_TRANSAC_ID")));
            appformReqDto.setOperType(osstFathReqDto.getOperType());
            appformReqDto.setCpntId(osstFathReqDto.getCpntId());
            fathService.updateFathTransacIdByResNo(appformReqDto);
        }
        
        return rtnMap;
    }

    private MSimpleOsstXmlFs8VO sendOsstFs8Service(OsstFathReqDto osstFathReqDto) throws SelfServiceException, SocketTimeoutException ,McpMplatFormException{
        MSimpleOsstXmlFs8VO simpleOsstXmlFs8VO =  new MSimpleOsstXmlFs8VO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", Constants.EVENT_CODE_FATH_URL_RQT);
        param.put("orgId", osstFathReqDto.getOrgId());
        param.put("cpntId", osstFathReqDto.getCpntId());
        param.put("onlineOfflnDivCd", osstFathReqDto.getOnlineOfflnDivCd());
        param.put("smsRcvTelNo", osstFathReqDto.getSmsRcvTelNo());
        param.put("fathSbscDivCd", osstFathReqDto.getFathSbscDivCd());
        param.put("retvCdVal", osstFathReqDto.getRetvCdVal());
        param.put("asgnAgncId", osstFathReqDto.getAsgnAgncId());

        if(!StringUtils.isEmpty(osstFathReqDto.getResNo())) {
            param.put("resNo", osstFathReqDto.getResNo());
        }

        mplatFormOsstServerAdapter.callService(param,simpleOsstXmlFs8VO,100000);
        return simpleOsstXmlFs8VO;
    }
    
    @Override
    public HashMap<String, Object> processOsstFs9Service(OsstFathReqDto osstFathReqDto, String resltCd) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        osstFathReqDto.setFathTransacId(SessionUtils.getFathSession().getTransacId());
        osstFathReqDto.setRetvDivCd("1"); //(1 : 안면인증 트랜잭션 아이디로 조회, 2 : 서식지아이디의 최종 안면인증 내역 조회)
        if("P".equals(osstFathReqDto.getGubun())) { //개인화URL(셀프안면인증)
            if(StringUtils.isEmpty(osstFathReqDto.getOperType())){
                throw new McpCommonJsonException("9999", F_BIND_EXCEPTION);
            }
            //신청서 조회
            FathFormInfo fathFormInfo = fathService.getFathFormInfo(osstFathReqDto.getResNo(), osstFathReqDto.getOperType());
            if(fathFormInfo == null) {
                throw new McpCommonJsonException("9999", F_BIND_EXCEPTION);
            }
            osstFathReqDto.setOrgId(fathFormInfo.getCntpntShopId());
        } else {
            //임시예약번호
            String tempResNo = SessionUtils.getFathSession().getTempResNo();
            if(StringUtils.isEmpty(tempResNo)) {
                throw new McpCommonJsonException("0005", FATH_CERT_EXPIR_EXCEPTION);
            }
            osstFathReqDto.setResNo(tempResNo);
        }
        //대리점 코드 조회
        String agentCode = this.getAgentCode(osstFathReqDto.getOrgId());
        if (StringUtils.isBlank(agentCode)) {
            throw new McpCommonJsonException("9996", "시스템 오류로 메세지가 반복되면 고객센터로 문의부탁드립니다.");
        } else {
            osstFathReqDto.setAsgnAgncId(agentCode);
        }
        
        //FS9연동
        MSimpleOsstXmlFs9VO simpleOsstXmlFs9VO = null;
        try {
            simpleOsstXmlFs9VO = this.sendOsstFs9Service(osstFathReqDto);
            if (!simpleOsstXmlFs9VO.isSuccess()) { //연동실패
                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
                return rtnMap;
            }
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("FS9_ERROR");
            mcpIpStatisticDto.setPrcsSbst("Exception[Exception]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
        }
        //FS9 성공 트래픽 1회로 제한
        SessionUtils.saveIsFs9();
        
        if("SUCC".equals(simpleOsstXmlFs9VO.getFathDecideCd())) { //안면인증 성공
            if("CD02".equals(resltCd)) {
                rtnMap.put("RESULT_CODE", "00002");
                rtnMap.put("RESULT_MSG", "안면인증 없이 신분증정보 입력으로 진행됩니다.");
                return rtnMap;
            }
            
            FathDto fathDto = new FathDto();
            fathDto.setFathCmpltNtfyDt(simpleOsstXmlFs9VO.getFathCmpltNtfyDt());    //안면인증 수행일시
            fathDto.setRetvCdVal(simpleOsstXmlFs9VO.getFathIdcardTypeCd());         //신분증종류
            fathDto.setIssDateVal(simpleOsstXmlFs9VO.getFathIdcardIssDate());       //발급일자
            fathDto.setDriveLicnsNo(simpleOsstXmlFs9VO.getFathDriveLicnsNo());      //운전면허번호
            fathDto.setCustNm(simpleOsstXmlFs9VO.getFathCustNm());                  //이름
            fathDto.setCustIdfyNo(simpleOsstXmlFs9VO.getFathCustIdfyNo());          //주민등록번호
            rtnMap = fathService.fathSuccRtn(fathDto, osstFathReqDto);
        } else if ("SKIP".equals(simpleOsstXmlFs9VO.getFathDecideCd())) { // 안면인증 SKIP
            rtnMap.put("RESULT_CODE", "00002");
            rtnMap.put("RESULT_MSG", "안면인증 없이 신분증정보 입력으로 진행됩니다.");
        } else { //안면인증 실패
            rtnMap = fathService.fathFailRtn(osstFathReqDto);
        }
        
        return rtnMap;
    }

    private MSimpleOsstXmlFs9VO sendOsstFs9Service(OsstFathReqDto osstFathReqDto) throws SelfServiceException, SocketTimeoutException ,McpMplatFormException{
        MSimpleOsstXmlFs9VO simpleOsstXmlFs9VO =  new MSimpleOsstXmlFs9VO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", Constants.EVENT_CODE_FATH_URL_RETV);
        param.put("fathTransacId", osstFathReqDto.getFathTransacId());
        param.put("retvDivCd", osstFathReqDto.getRetvDivCd());
        param.put("asgnAgncId", osstFathReqDto.getAsgnAgncId());

        if(!StringUtils.isEmpty(osstFathReqDto.getResNo())) {
            param.put("resNo", osstFathReqDto.getResNo());
        }

        mplatFormOsstServerAdapter.callService(param,simpleOsstXmlFs9VO,100000);
        return simpleOsstXmlFs9VO;
    }

    @Override
    public HashMap<String, Object> processOsstFt1Service(OsstFathReqDto osstFathReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        if (!isEnabledFT1()) {
            throw new McpCommonJsonException("1004", "안면인증 SKIP이 불가능합니다.");
        }

        String fathTransacId = SessionUtils.getFathSession().getTransacId();
        if(StringUtils.isEmpty(fathTransacId)) {
            throw new McpCommonJsonException("9991", FATH_CERT_EXPIR_EXCEPTION);
        }
        osstFathReqDto.setFathTransacId(fathTransacId);
        osstFathReqDto.setCpntId(SessionUtils.getFathSession().getCpntId());

        if ("P".equals(osstFathReqDto.getGubun())) {
            if(StringUtils.isEmpty(osstFathReqDto.getOperType())){
                throw new McpCommonJsonException("9992", F_BIND_EXCEPTION);
            }
            //신청서 조회
            FathFormInfo fathFormInfo = fathService.getFathFormInfo(osstFathReqDto.getResNo(), osstFathReqDto.getOperType());
            if(fathFormInfo == null) {
                throw new McpCommonJsonException("9993", F_BIND_EXCEPTION);
            }
            osstFathReqDto.setOrgId(fathFormInfo.getCntpntShopId());
        } else {
            //임시예약번호
            String tempResNo = SessionUtils.getFathSession().getTempResNo();
            if(StringUtils.isEmpty(tempResNo)) {
                throw new McpCommonJsonException("9994", FATH_CERT_EXPIR_EXCEPTION);
            }
            osstFathReqDto.setResNo(tempResNo);
        }

        //대리점 코드 조회
        String agentCode = this.getAgentCode(osstFathReqDto.getOrgId());
        if (StringUtils.isBlank(agentCode)) {
            throw new McpCommonJsonException("9995", "시스템 오류로 메세지가 반복되면 고객센터로 문의부탁드립니다.");
        }
        osstFathReqDto.setAsgnAgncId(agentCode);

        MSimpleOsstXmlFt1VO simpleOsstXmlFt1VO = null;
        try {
            simpleOsstXmlFt1VO = this.sendOsstFt1Service(osstFathReqDto);
            if (!simpleOsstXmlFt1VO.isSuccess()) { //연동실패
                rtnMap.put("RESULT_CODE", "9996");
                rtnMap.put("RESULT_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
                return rtnMap;
            }
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("RESULT_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("FT1_ERROR");
            mcpIpStatisticDto.setPrcsSbst("Exception[Exception]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            return rtnMap;
        }

        if ("0000".equals(simpleOsstXmlFt1VO.getRsltCd())) {
            fathService.updateNameChgFathSkip(osstFathReqDto.getResNo());
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_MSG", "안면인증 SKIP이 완료되었습니다.");
        } else {
            rtnMap.put("RESULT_CODE", simpleOsstXmlFt1VO.getRsltCd());
        }
        return rtnMap;
    }

    private MSimpleOsstXmlFt1VO sendOsstFt1Service(OsstFathReqDto osstFathReqDto) throws SocketTimeoutException {
        MSimpleOsstXmlFt1VO simpleOsstXmlFt1VO =  new MSimpleOsstXmlFt1VO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", Constants.EVENT_CODE_FATH_SKIP);
        param.put("fathTransacId", osstFathReqDto.getFathTransacId());
        param.put("asgnAgncId", osstFathReqDto.getAsgnAgncId());

        if(!StringUtils.isEmpty(osstFathReqDto.getResNo())) {
            param.put("resNo", osstFathReqDto.getResNo());
        }

        mplatFormOsstServerAdapter.callService(param,simpleOsstXmlFt1VO,100000);
        return simpleOsstXmlFt1VO;
    }

    @Override
    public boolean isEnabledFT1() {
        NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("fathCertPolicy", "FT1MCP");
        if (nmcpCdDtlDto == null) {
            return false;
        }

        if (!"Y".equals(nmcpCdDtlDto.getDtlCdNm())) {
            return false;
        }

        try {
            if (!isMiddleDateTime2(nmcpCdDtlDto.getPstngStartDate(), nmcpCdDtlDto.getPstngEndDate())) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }

        return true;
    }
}
