package com.ktmmobile.mcp.acen.service;

import com.ktmmobile.mcp.acen.dao.AcenDao;
import com.ktmmobile.mcp.acen.dto.AcenDto;
import com.ktmmobile.mcp.common.exception.McpMplatFormException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.dto.MPhoneNoListXmlVO;
import com.ktmmobile.mcp.common.mplatform.dto.MPhoneNoVo;
import com.ktmmobile.mcp.common.mplatform.dto.MSimpleOsstXmlVO;
import com.ktmmobile.mcp.common.mplatform.dto.McpRequestOsstDto;
import com.ktmmobile.mcp.common.mplatform.service.MplatFormService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.HttpClientUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.ktmmobile.mcp.common.constant.Constants.*;

@Service
public class AcenService {

    @Value("${api.interface.server}")
    private String apiInterfaceServer;
    private static final Logger logger = LoggerFactory.getLogger(AcenService.class);

    @Autowired
    private AcenDao acenDao;

    @Autowired
    private MplatFormService mplatFormService;

    @Value("${ext.url}")
    private String extUrl;

    private static final String ERR0001_MSG= "errAdminD";      // 관리자 삭제
    private static final String ERR0002_MSG= "errPreChk";      // 사전체크 실패
    private static final String ERR0003_MSG= "errOpenChk";     // 번호예약/개통 실패
    private static final String ERR0004_MSG= "errApi";         // 연동오류
    private static final String ERR0005_MSG= "errDayF";        // 강제 종결(사전체크 후 당일 미개통)
    private static final String ERR0006_MSG= "errPeriodF";     // 처리 기간 초과
    private static final String ERR0007_MSG= "errOpenF";       // 강제 종결(개통완료)
    private static final String ERR0008_MSG= "errCstmrM";      // 고객부재
    private static final String SELF_CSTMR_UNKNOWN= "UNDFIN";  // 확인불가(셀프개통)

    /**
     * 설명 : ACEN 연동 제약조건
     * @Date : 2024.05.27
     * @throws ParseException
     */
    public Map<String, Object> getAcenLimit() throws ParseException {

         /*
           상담사 신규가입: 08:00 ~ 21:30 (월요일-일요일)
           상담사 번호이동: 10:00 ~ 21:30 (월요일-토요일) * 신정, 설당일, 추석당일 제외
           셀프 신규가입: 08:00 ~ 23:00 (월요일-일요일)
           OSST 연동 진행중인 건 처리 : 21:30 ~ 22:00 (월요일-일요일)
        */

        Map<String,Object> acenLimit= new HashMap<>();

        boolean nacFlag = true;    // 상담사 신규가입
        boolean mnpFlag = true;    // 상담사 번호이동
        boolean osstFlag = true;   // OSST 연동 진행중인 건 처리
        boolean selfFlag = true;   // 셀프 신규가입 (항상 true)

        // 상담사 신규가입 조건 확인
        if(!DateTimeUtil.isMiddleTime("08:00", "21:30")) {
            nacFlag = false;
        }

        // 상담사 번호이동 조건 확인
        String nowDate = DateTimeUtil.getFormatString("yyyyMMdd");
        int whichDay = DateTimeUtil.whichDay(nowDate);

        if(whichDay == 1) {
            mnpFlag= false;
        } else if(0 < acenDao.getMnpLimitDayCnt(nowDate)) {
            mnpFlag= false;  // MNP 제외 일자
        } else if(!DateTimeUtil.isMiddleTime("10:00", "21:30")) {
            mnpFlag= false;
        }

        // OSST 연동 진행중인 건 처리 조건 확인
        if(!DateTimeUtil.isMiddleTime("21:31", "22:00")) {
            osstFlag= false;
        }

        acenLimit.put("nacFlag", nacFlag);
        acenLimit.put("mnpFlag", mnpFlag);
        acenLimit.put("osstFlag", osstFlag);
        acenLimit.put("selfFlag", selfFlag);

        return acenLimit;
    }

    /**
     * 설명 : ACEN 연동 대상 상태 UPDATE
     * @Date : 2024.05.09
     */
    public int updAcenBatchTrg(Map<String, Object> acenLimit) {

        /*
           nacFlag: 상담사 신규가입 해피콜 가능여부
           mnpFlag: 상담사 번호이동 해피콜 가능여부
           osstFlag: OSST 연동 진행중인 건 처리여부
           selfFlag: 셀프 신규가입 해피콜 가능여부
        */

        Integer limitCnt = acenDao.getMaxProcCnt();  // 최대 max 카운트
        int updCnt = 0;                              // 연동 대상 카운트

        boolean osstFlag = (Boolean) acenLimit.get("osstFlag");

        if(osstFlag){ // OSST 연동 진행중인 건 우선 처리
            updCnt = acenDao.updateAcenOsstTrg(limitCnt);
        }

        if(updCnt < limitCnt){
            acenLimit.put("count", limitCnt - updCnt);
            updCnt+= acenDao.updateAcenTrg(acenLimit);
        }

        return updCnt;
    }

    /**
     * 설명 : 해피콜 타입 유형 별 ACEN 연동 호출
     * @Date : 2024.05.09
     */
    public Map<String, Integer> procAcenRequest() {

        Map<String, Integer> result = new HashMap<>();
        int succCascnt= 0;
        int failCascnt= 0;

        String requestKey= null;
        String evntType= null;
        String updMcpReqYn = null;

        boolean eachResult= true;   // 개별 처리 결과
        AcenDto toDto= null;        // acen 상태 update object


        // 1. PENDING 상태인 ACEN 연동 대상 조회
        List<AcenDto> acenTrg= acenDao.getAcenPendingTrg();
        logger.error("### pending data cnt={}", acenTrg.size());

        result.put("exeCasCnt", acenTrg.size());

        // 2. 개별 처리
        for(AcenDto acenDto : acenTrg){

            // 2-1. 초기화
            requestKey= acenDto.getRequestKey();
            evntType= acenDto.getEvntType();
            updMcpReqYn= null;
            eachResult= true;
            toDto= null;

            // [2024.12.03] ACEN 진행 중 상담사에 의해 개통완료된 경우, ACEN 종결 처리 & 신청서 상태 변경X
            this.getMcpRequestStateInfo(acenDto); // 신청정보 실시간 조회

            // 상담사가 직접 개통시킨 건은 ACEN 종결처리 (신청서 상태는 변경하지 않음)
            if(this.isOpendByConsultant(acenDto)){
                succCascnt++;

                toDto= new AcenDto();
                toDto.setRequestKey(acenDto.getRequestKey());
                toDto.setSuccYn("N");
                toDto.setEndStatus("Y");
                toDto.setRmk(ERR0007_MSG);  // 강제 종결(개통완료건)
                acenDao.updateAcenReqInfo(toDto);

                // 실패이력 insert
                acenDao.insertAcenFailHist(toDto.getRequestKey());
                continue;
            }

            // 신규 유심없음 바로배송 처리 (별도 처리)
            if(NAC_NOSIM_01.equals(evntType) && "02".equals(acenDto.getDlvryType())){
                evntType= NAC_NOSIM_NOW_DLVRY_01;
            }

            // 3. ACEN 연동 전처리
            switch (evntType){

                // CASE 3-1) 신청서 상태에 따라 CALL 진행여부 결정
                case NAC_NOSIM_01:
                case MNP_NOSIM_01:
                case MNP_NOSIM_02:
                case MNP_SIM_01:
                case MNP_SIM_02:

                    toDto= this.preProcByStatus(acenDto);
                    break;

                // CASE 3-2) OSST 연동 결과에 따라 CALL 진행여부 결정
                case NAC_SIM_01:
                case NAC_SIM_02:
                case NAC_NOSIM_03:
                case MNP_NOSIM_03:
                case MNP_SIM_03:
                case NAC_NOSIM_NOW_DLVRY_01:

                    toDto= this.preProcByOsst(acenDto);
                    updMcpReqYn = "Y";

                    // 예외케이스: 신규유심있음, 신규유심없음(바로배송)은 사전체크 완료 후 해피콜 호출
                    if("Y".equals(toDto.getPreCheckYn())
                       && (NAC_SIM_01.equals(evntType) || NAC_NOSIM_NOW_DLVRY_01.equals(evntType))){

                        toDto.setReqStatus("Y");
                        toDto.setAcenCall(true);

                        toDto.setRequestStateCode("02"); // 해피콜
                    }

                    break;

                // CASE 3-3) 즉시 CALL 진행
                default:
                    toDto= new AcenDto();
                    toDto.setRequestKey(acenDto.getRequestKey());
                    toDto.setReqStatus("Y");
                    toDto.setEndStatus("N");
                    toDto.setAcenCall(true);
                    break;
            }

            // 4. ACEN 연동 호출
            if(toDto.isAcenCall()) {
                eachResult = this.reqCallingUpload(requestKey, acenDto.getEvntType());
            }

            // 5. ACEN 연동 결과에 따른 상태값 바꿔치기
            if(!eachResult){
                toDto.setSuccYn("N");
                toDto.setReqStatus("N");
                toDto.setEndStatus("Y");
                failCascnt++;

                if(SLF_CST_IDT.equals(evntType)){
                    toDto.setRmk(SELF_CSTMR_UNKNOWN);
                }else{
                    toDto.setRmk(ERR0004_MSG);
                    toDto.setPstate("50"); // 자동화 실패
                }

            }else{
                succCascnt++;
            }

            // 6. ACEN 현행화 상태 UPDATE
            acenDao.updateAcenReqInfo(toDto);

            // 실패인 경우 실패이력 저장
            if("N".equals(toDto.getSuccYn())){
                acenDao.insertAcenFailHist(toDto.getRequestKey());
            }

            // 7. 신청서 상태 UPDATE
            if(!StringUtil.isEmpty(toDto.getPstate()) || !StringUtil.isEmpty(toDto.getRequestStateCode()) || "Y".equals(updMcpReqYn)){

                // [2024.12.03] ACEN 진행 중 상담사에 의해 개통완료된 경우, ACEN 종결 처리 & 신청서 상태 변경X > 방어로직
                this.getMcpRequestStateInfo(acenDto); // 신청정보 실시간 조회
                if(!this.isOpendByConsultant(acenDto)){

                    // 3차 해피콜 연동이 실패되더라도, 개통완료인 경우 자동화실패로 변경하지 않음
                    if("21".equals(acenDto.getRequestStateCode())) toDto.setPstate("00");
                    acenDao.updateMcpReqInfo(toDto);
                }
            }

        }

        result.put("succCascnt", succCascnt);
        result.put("failCascnt", failCascnt);

        return result;
    }

    /**
     * 설명 : 신청서 상태에 따라 CALL 진행여부 결정
     * @Date : 2024.06.10
     */
    private AcenDto preProcByStatus(AcenDto acenDto) {

        AcenDto rtnDto= new AcenDto();
        rtnDto.setRequestKey(acenDto.getRequestKey());

        // 신청서 상태가 정상이 아닌 경우 종결, 정상인 경우 해피콜 진행
        if("00".equals(acenDto.getPstate())) {
            rtnDto.setReqStatus("Y");
            rtnDto.setEndStatus("N");
            rtnDto.setAcenCall(true);
        }else{
            rtnDto.setEndStatus("Y");
            rtnDto.setSuccYn("N");
            rtnDto.setRmk(ERR0001_MSG);
            rtnDto.setAcenCall(false);
        }

        return rtnDto;
    }

    /**
     * 설명 : OSST 연동 결과에 따라 CALL 진행여부 결정
     * @Date : 2024.06.10
     */
    private AcenDto preProcByOsst(AcenDto acenDto) {

        AcenDto rtnDto= new AcenDto();
        rtnDto.setRequestKey(acenDto.getRequestKey());

        String reqOsstCd= null;  // 요청해야 할 OSST 이벤트 코드
        String chkOsstCd= null;  // 응답 확인해야 할 OSST 이벤트 코드
        String failRmk= null;

        if("Y".equals(acenDto.getPreCheckYn())){
            // 개통 진행
            reqOsstCd= EVENT_CODE_REQ_OPEN;
            chkOsstCd= EVENT_CODE_REQ_OPEN_RESULT;
            failRmk= ERR0003_MSG;
        }else{
            // 사전체크 진행
            reqOsstCd= EVENT_CODE_PRE_CHECK;
            chkOsstCd= EVENT_CODE_PC_RESULT;
            failRmk= ERR0002_MSG;
        }

        // ReqStatus값에 따라 OSST 결과 확인 및 요청
        if("T".equals(acenDto.getReqStatus())){

            // OSST 응답 대기상태 → OSST 응답 결과 확인
            McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
            mcpRequestOsstDto.setMvnoOrdNo(acenDto.getResNo());
            mcpRequestOsstDto.setPrgrStatCd(chkOsstCd);

            McpRequestOsstDto osstDtoRtn= acenDao.getOsstResult(mcpRequestOsstDto);

            // 응답결과 확인
            if(osstDtoRtn == null) {
                // 미응답
                rtnDto.setEndStatus("N");
                rtnDto.setAcenCall(false);
            } else {
                if (EVENT_CODE_PC_RESULT.equals(chkOsstCd)) {
                    if (OSST_SUCCESS.equals(osstDtoRtn.getRsltCd())) {
                        rtnDto.setReqStatus("N");
                        rtnDto.setEndStatus("N");
                        rtnDto.setPreCheckYn("Y");
                        rtnDto.setAcenCall(false);
                    } else {
                        // 실패
                        rtnDto.setEndStatus("Y");
                        rtnDto.setSuccYn("N");
                        rtnDto.setRmk(failRmk);
                        rtnDto.setAcenCall(false);

                        rtnDto.setPstate("50"); // 자동화 실패
                    }
                } else {
                    if (this.isSuccessOP2(osstDtoRtn.getRsltCd())) {
                        rtnDto.setReqStatus("Y");
                        rtnDto.setEndStatus("Y");
                        rtnDto.setSuccYn("Y");
                        rtnDto.setOpenYn("Y");
                        rtnDto.setAcenCall(true);
                    } else {
                        // 실패
                        rtnDto.setEndStatus("Y");
                        rtnDto.setSuccYn("N");
                        rtnDto.setRmk(failRmk);
                        rtnDto.setAcenCall(false);

                        rtnDto.setPstate("50"); // 자동화 실패
                    }
                }
            }

        }else{

            // 사전체크 또는 개통 요청
            MSimpleOsstXmlVO simpleOsstXmlVO= this.reqOsstService(acenDto, reqOsstCd);

            // 요청결과 확인
            if(simpleOsstXmlVO != null && simpleOsstXmlVO.isSuccess()) {
                // 성공
                rtnDto.setReqStatus("T");
                rtnDto.setEndStatus("N");
                rtnDto.setAcenCall(false);

                //OP0 성공시 평생할인 정책 기적용 테이블 INSERT
                if(EVENT_CODE_REQ_OPEN.equals(reqOsstCd)){
                    AcenDto apdInsertDto = new AcenDto();
                    apdInsertDto = acenDao.getAppformReq(acenDto.getRequestKey());
                    RestTemplate restTemplate = new RestTemplate();

                    /* 직영 평생할인 프로모션 ID 가져오기 */
                    String prmtId = restTemplate.postForObject(apiInterfaceServer + "/appform/getChrgPrmtId", apdInsertDto, String.class);
                    apdInsertDto.setPrmtId(prmtId);
                    apdInsertDto.setEvntCd("NAC");
                    apdInsertDto.setCretId("ACEN");

                    /* 평생할인 프로모션 기적용 테이블 insert */
                    int result = restTemplate.postForObject(apiInterfaceServer + "/appform/insertDisPrmtApd", apdInsertDto, Integer.class);
                    if(result < 1){
                        logger.error("insertDisPrmtApd Failed. requestKey={}", acenDto.getRequestKey());
                    }
                }

            }else{
                // 실패
                rtnDto.setEndStatus("Y");
                rtnDto.setSuccYn("N");
                rtnDto.setRmk(failRmk);
                rtnDto.setAcenCall(false);

                rtnDto.setPstate("50"); // 자동화 실패
            }
        }

        return rtnDto;
    }

    private boolean isSuccessOP2(String rsltCd) {
        return OSST_SUCCESS.equals(rsltCd) || OSST_ERROR_SEND_GUIDE.equals(rsltCd) || OSST_ERROR_SEND_OTA.equals(rsltCd);
    }


    /**
     * 설명 : OSST 연동 - 사전체크/개통요청
     * @Date : 2024.05.09
     */
    private MSimpleOsstXmlVO reqOsstService(AcenDto acenDto, String osstEvntCd) {

        // OSST 연동 요청 → 요청 성공 후 OSST 응답 대기상태로 변경
        MSimpleOsstXmlVO simpleOsstXmlVO= new MSimpleOsstXmlVO();

        // 기존 성공 이력이 존재하는지 확인 (재실행 고려)
        Map<String, String> osstChkParam = new HashMap<>();
        osstChkParam.put("resNo", acenDto.getResNo());
        osstChkParam.put("prgrStatCd", EVENT_CODE_PRE_CHECK.equals(osstEvntCd) ? EVENT_CODE_PC_RESULT : EVENT_CODE_REQ_OPEN_RESULT);

        int preCheckCnt = acenDao.getPreCheckSuccessCnt(osstChkParam);
        if(preCheckCnt > 0){
            simpleOsstXmlVO.setSuccess(true);
            return simpleOsstXmlVO;    // 성공으로 인식
        }

        // 개통요청인 경우 → (신규가입) 번호조회 및 예약 선진행
        if(EVENT_CODE_REQ_OPEN.equals(osstEvntCd)){

            if(NAC_SIM_02.equals(acenDto.getEvntType()) || NAC_NOSIM_03.equals(acenDto.getEvntType())){

                // 번호예약 대상여부 확인 (번호예약 수행 여부)
                String ifType = acenDao.getLatestResvTlphIfType(acenDto.getResNo());
                if(ifType == null || !WORK_CODE_RES.equals(ifType)){
                    MPhoneNoVo mPhoneNoVo= this.searchPhoneNum(acenDto);
                    boolean canReqOsst= this.reqOsstResvTlphService(mPhoneNoVo);
                    if(!canReqOsst){
                        return null;  // 실패 처리
                    }
                }
            }
        }

        try{
            // OSST 요청
            Thread.sleep(3000);
            simpleOsstXmlVO= mplatFormService.sendOsstService(acenDto.getResNo(), osstEvntCd);

        }catch(McpMplatFormException e){
            // OSST 응답결과값이 빈값인 경우 McpMplatFormException 발생
            logger.error("## reqOsstService McpMplatFormException={}", e.getMessage());
        }catch(SocketTimeoutException e){
            logger.error("## reqOsstService SocketTimeoutException={}", e.getMessage());
        }catch(SelfServiceException e){
            // OSST 응답결과값이 실패인 경우 SelfServiceException 발생
            logger.error("## reqOsstService SelfServiceException={}", e.getMessage());
        }catch(Exception e){
            // OSST 응답결과값이 성공인데, PARSING 과정에 오류 발생한 경우 Exception 발생(isSuccess는 true)
            logger.error("## reqOsstService Exception={}", e.getMessage());
        }

        return simpleOsstXmlVO;
    }

    /**
     * 설명 : 희망번호로 번호조회
     * @Date : 2024.05.09
     */
    private MPhoneNoVo searchPhoneNum(AcenDto acenDto) {

        MPhoneNoVo mPhoneNoVo = null;

        // 1. 희망번호 3개 조회
        Map<String,String> numbers= acenDao.getWantNumbers(acenDto.getRequestKey());
        if(numbers == null || numbers.isEmpty()) return null;

        // 2. 희망번호 1순위부터 번호 조회
        String[] numArr= new String[]{numbers.get("REQ_WANT_NUMBER"), numbers.get("REQ_WANT_NUMBER2"), numbers.get("REQ_WANT_NUMBER3")};

        for(String number : numArr){

            if(StringUtil.isEmpty(number)) continue;

            mPhoneNoVo= this.reqOsstSvcNoService(acenDto.getResNo(), number);
            if(mPhoneNoVo != null) break;  // 번호조회 성공
        }

        // 3. 희망번호 내 번호 미존재시, 랜덤번호로 진행
        if(mPhoneNoVo == null){

            Random random;
            String a = "";
            String b = "";
            String c = "";
            String d = "";

            try {
                random = SecureRandom.getInstance("SHA1PRNG");
                int findMaxCnt = Integer.parseInt(numbers.get("FIND_COUNT"));

                // 랜덤번호 최대 10회 조회
                for(int i= 0; i< findMaxCnt; i++){
                    a = String.valueOf(random.nextInt(10));
                    b = String.valueOf(random.nextInt(10));
                    c = String.valueOf(random.nextInt(10));
                    d = String.valueOf(random.nextInt(10));

                    String randomNum= a + b + c + d;
                    logger.error("#### searchPhoneNum random number={}", randomNum);

                    if(StringUtil.isEmpty(randomNum)) continue;

                    // 랜덤번호로 번호조회
                    mPhoneNoVo= this.reqOsstSvcNoService(acenDto.getResNo(), randomNum);
                    if(mPhoneNoVo != null) break;  // 번호조회 성공
                }

            } catch (NoSuchAlgorithmException e) {
                logger.error("#### searchPhoneNum NoSuchAlgorithmException={}", e.getMessage());
            }

        }

        return mPhoneNoVo;
    }

    /**
     * 설명 : OSST 연동 - 번호조회
     * @Date : 2024.05.09
     */
    private MPhoneNoVo reqOsstSvcNoService(String resNo, String wantTelNo) {

        // 번호 조회
        MPhoneNoListXmlVO mPhoneNoListXmlVO = new MPhoneNoListXmlVO();
        MPhoneNoVo rtnPhoneNoVo = null;

        try {
            // OSST 요청
            Thread.sleep(3000);
            mPhoneNoListXmlVO= mplatFormService.sendOsstSvcNoService(resNo, wantTelNo, EVENT_CODE_SEARCH_NUMBER);
        }catch(McpMplatFormException e) {
            // OSST 응답결과값이 빈값인 경우 McpMplatFormException 발생
            logger.error("## reqOsstSvcNoService McpMplatFormException={}", e.getMessage());
        }catch(SocketTimeoutException e) {
            logger.error("## reqOsstSvcNoService SocketTimeoutException={}", e.getMessage());
        }catch(SelfServiceException e) {
            // OSST 응답결과값이 실패인 경우 SelfServiceException 발생
            logger.error("## reqOsstSvcNoService SelfServiceException={}", e.getMessage());
        }catch(Exception e){
            // OSST 응답결과값이 성공인데, PARSING 과정에 오류 발생한 경우 Exception 발생(isSuccess는 true)
            logger.error("## reqOsstSvcNoService Exception={}", e.getMessage());
        }

        // OSST 요청 결과 확인
        if(mPhoneNoListXmlVO != null && mPhoneNoListXmlVO.isSuccess()){

            List<MPhoneNoVo> phoneNumberList =  mPhoneNoListXmlVO.getList();
            if(phoneNumberList != null && !phoneNumberList.isEmpty()) {
                // 번호조회 성공
                rtnPhoneNoVo= new MPhoneNoVo();
                rtnPhoneNoVo.setTlphNo(phoneNumberList.get(0).getTlphNo());
                rtnPhoneNoVo.setTlphNoStatCd(phoneNumberList.get(0).getTlphNoStatCd());
                rtnPhoneNoVo.setTlphNoOwnCmncCmpnCd(phoneNumberList.get(0).getTlphNoOwnCmncCmpnCd());
                rtnPhoneNoVo.setEncdTlphNo(phoneNumberList.get(0).getEncdTlphNo());
                rtnPhoneNoVo.setAsgnAgncId(phoneNumberList.get(0).getAsgnAgncId());
                rtnPhoneNoVo.setOpenSvcIndCd(phoneNumberList.get(0).getOpenSvcIndCd());
                rtnPhoneNoVo.setResNo(resNo);
            }
        }

        return rtnPhoneNoVo;
    }

    /**
     * 설명 : OSST 연동 - 번호예약
     * @Date : 2024.05.09
     */
    private boolean reqOsstResvTlphService(MPhoneNoVo mPhoneNoVo) {

        if(mPhoneNoVo == null) return false;

        // 번호예약전 OSST 테이블 INSERT
        if(acenDao.insertOsstResvTlhp(mPhoneNoVo) <= 0) return false;

        // 번호예약
        boolean resvSucc= false;
        MSimpleOsstXmlVO simpleOsstXmlVO= new MSimpleOsstXmlVO();

        try {
            // OSST 요청
            Thread.sleep(3000);
            simpleOsstXmlVO= mplatFormService.sendOsstResvTlphService(mPhoneNoVo.getResNo(), EVENT_CODE_NUMBER_REG, WORK_CODE_RES);
        }catch(McpMplatFormException e) {
            // OSST 응답결과값이 빈값인 경우 McpMplatFormException 발생
            logger.error("## reqOsstResvTlphService McpMplatFormException={}", e.getMessage());
        }catch(SocketTimeoutException e) {
            logger.error("## reqOsstResvTlphService SocketTimeoutException={}", e.getMessage());
        }catch(SelfServiceException e) {
            // OSST 응답결과값이 실패인 경우 SelfServiceException 발생
            logger.error("## reqOsstResvTlphService SelfServiceException={}", e.getMessage());
        }catch(Exception e){
            // OSST 응답결과값이 성공인데, PARSING 과정에 오류 발생한 경우 Exception 발생(isSuccess는 true)
            logger.error("## reqOsstResvTlphService Exception={}", e.getMessage());
        }

        // OSST 요청 결과 확인
        if(simpleOsstXmlVO.isSuccess()) resvSucc= true;

        return resvSucc;
    }


    /**
     * 설명 : ACEN 해피콜 요청 CALL
     * @Date : 2024.05.09
     */
    private boolean reqCallingUpload(String requestKey, String evntType) {

        boolean callSuccess= false;

        // 연동 데이터 세팅
        NameValuePair[] data = {
            new NameValuePair("requestKey", requestKey),
            new NameValuePair("evntType", evntType)
        };
        String result = "";
        // 연동 시작 (최대 2회)
        for(int i=0; i<2; i++){

            try{

                result= HttpClientUtil.post(extUrl+"/aiccApp/reqCallingUpload.do", data, "UTF-8");

                if(StringUtil.isEmpty(result)) return false;

                // 연동 결과 PARSING
                JSONObject jsonObject = new JSONObject(result);
                String rsltCd= jsonObject.has("returncode") ? jsonObject.getString("returncode") : "0";
                String procCd= null;

                if(!"0".equals(rsltCd)) procCd= jsonObject.getString("procCd");

                if("1".equals(rsltCd) && "0000".equals(procCd)) callSuccess= true;
                else callSuccess= false;

            }catch(SocketTimeoutException e){
                logger.error("## reqCallingUpload SocketTimeoutException={}", e.getMessage());
            }catch(JSONException e){
                logger.error("## reqCallingUpload JSONException={}", e.getMessage());
            }catch(Exception e){
                logger.error("## reqCallingUpload Exception={}", e.getMessage());
            }

            if(callSuccess) break;
        }


        return callSuccess;
    }

    /**
     * 설명 : 신청상태 실시간 조회
     * @Date : 2024.12.03
     */
    private void getMcpRequestStateInfo(AcenDto acenDto) {

        AcenDto requestInfo= acenDao.getMcpRequestStateInfo(acenDto.getRequestKey());
        if(requestInfo != null) {
            acenDto.setPstate(requestInfo.getPstate());
            acenDto.setRequestStateCode(requestInfo.getRequestStateCode());
            acenDto.setRvisnId(requestInfo.getRvisnId());
        }
    }

    /**
     * 설명 : 상담사에 의해 개통된 신청서인지 확인
     * @Date : 2024.12.03
     */
    private boolean isOpendByConsultant(AcenDto acenDto) {

        // 셀프개통
        if(SLF_CST_IDT.equals(acenDto.getEvntType())) return false;
        // 개통X
        if(!"21".equals(acenDto.getRequestStateCode())) return false;
        // ACEN에 의해서 개통
        if("ACEN".equals(acenDto.getRvisnId())) return false;

        return true;
    }

    /**
     * 설명 : ACEN 연동 미완료건 종결 처리
     * @Param : endType (01: 당일 미개통, 02: 기간종결)
     * @Date : 2024.06.12
     */
    public Map<String, Integer> updAcenEndTrg(String endType) {

        Map<String, Integer> result = new HashMap<>();

        int succCascnt= 0;
        int failCascnt= 0;

        List<AcenDto> acenEndList= null;

        if("01".equals(endType)) acenEndList= acenDao.getAcenEndTrg();
        else acenEndList= acenDao.getAcenPeriodEndTrg();

        if(acenEndList == null || acenEndList.size() == 0){
            result.put("exeCasCnt", 0);
            result.put("succCascnt", succCascnt);
            result.put("failCascnt", failCascnt);
            return result;
        }

        // 종결처리 파라미터 세팅
        AcenDto acenDto= new AcenDto();
        acenDto.setEndStatus("Y");
        acenDto.setSuccYn("N");
        acenDto.setPstate("50");

        if("01".equals(endType)) acenDto.setRmk(ERR0005_MSG);
        else acenDto.setRmk(ERR0006_MSG);

        for(AcenDto item : acenEndList){

            acenDto.setRequestKey(item.getRequestKey());

            // acen 종결 처리
            acenDao.updateAcenReqInfo(acenDto);

            // acen 실패 이력 저장
            acenDao.insertAcenFailHist(acenDto.getRequestKey());

            // 신청서 상태 update (+ 개통완료인 건은 자동화실패 처리X)
            if("00".equals(item.getPstate()) && !SLF_CST_IDT.equals(item.getEvntType()) && !"21".equals(item.getRequestStateCode())){
                acenDao.updateMcpReqInfo(acenDto);
            }

            succCascnt++;
        }

        result.put("exeCasCnt", acenEndList.size());
        result.put("succCascnt", succCascnt);
        result.put("failCascnt", failCascnt);

        return result;
    }

    /**
     * 설명 : 자동화실패+개통완료인 신청 건 정상+개통완료 상태로 update
     * @Date : 2024.11.22
     */
    public Map<String, Integer> updateNormalPState() {

        Map<String, Integer> result = new HashMap<>();

        int succCascnt= 0;
        int failCascnt= 0;

        // 신청서 상태 정상으로 update (수정자와 수정일시는 update X)
        int exeCasCnt = acenDao.updateNormalPState();
        result.put("exeCasCnt", exeCasCnt);

        if (0 < exeCasCnt) {
            succCascnt = exeCasCnt;
        } else {
            failCascnt = exeCasCnt;
        }

        result.put("succCascnt", succCascnt);
        result.put("failCascnt", failCascnt);
        return result;
    }

    /**
     * 설명 :  Acen 응답결과 미존재건 → 실패(고객부재) 처리
     * @Date : 2025.04.17
     */
    public Map<String, Integer> updAcenNoResponseTrg() {

        Map<String, Integer> result = new HashMap<>();
        int succCascnt= 0;
        int failCascnt= 0;

        List<AcenDto> acenList = acenDao.getAcenNoResponseTrg();

        if(acenList == null || acenList.size() == 0){
            result.put("exeCasCnt", 0);
            result.put("succCascnt", succCascnt);
            result.put("failCascnt", failCascnt);
            return result;
        }

        // 종결처리 파라미터 세팅
        AcenDto acenDto= new AcenDto();
        acenDto.setEndStatus("Y");
        acenDto.setSuccYn("N");
        acenDto.setPstate("50");
        acenDto.setRmk(ERR0008_MSG);

        for(AcenDto item : acenList){

            acenDto.setRequestKey(item.getRequestKey());

            // acen 종결처리
            acenDao.updateAcenReqInfo(acenDto);

            // acen 실패이력 저장
            acenDao.insertAcenFailHist(acenDto.getRequestKey());

            // 신청서 상태 update (+개통완료인 건은 자동화실패 처리X)
            if("00".equals(item.getPstate()) && !SLF_CST_IDT.equals(item.getEvntType()) && !"21".equals(item.getRequestStateCode())){
                acenDao.updateMcpReqInfo(acenDto);
            }

            succCascnt++;
        }

        result.put("exeCasCnt", acenList.size());
        result.put("succCascnt", succCascnt);
        result.put("failCascnt", failCascnt);

        return result;
    }

}
