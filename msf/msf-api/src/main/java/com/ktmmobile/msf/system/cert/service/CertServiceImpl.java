//////////package com.ktmmobile.mcp.cert.service;
//////////
//////////import static com.ktmmobile.mcp.common.constants.Constants.CUST_AGENT_AUTH;
//////////import static com.ktmmobile.mcp.common.constants.Constants.CUST_AUTH;
//////////import static com.ktmmobile.mcp.common.constants.Constants.INSR_PROD;
//////////import static com.ktmmobile.mcp.common.constants.Constants.OPEN_AUTH;
//////////import static com.ktmmobile.mcp.common.constants.Constants.RWD_PROD;
//////////import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.STEP_INFO_NULL_EXCEPTION;
//////////
//////////import java.io.IOException;
//////////import java.text.SimpleDateFormat;
//////////import java.util.*;
//////////import javax.servlet.http.HttpServletRequest;
//////////
//////////import com.fasterxml.jackson.databind.ObjectMapper;
//////////import com.ktmmobile.mcp.common.util.ObjectUtils;
//////////import com.ktmmobile.mcp.mypage.service.MypageUserService;
//////////import org.apache.commons.lang.StringUtils;
//////////import org.slf4j.Logger;
//////////import org.slf4j.LoggerFactory;
//////////import org.springframework.beans.factory.annotation.Autowired;
//////////import org.springframework.beans.factory.annotation.Value;
//////////import org.springframework.dao.DataAccessException;
//////////import org.springframework.stereotype.Service;
//////////import com.ktds.crypto.exception.CryptoException;
//////////import com.ktmmobile.mcp.cert.controller.CertController;
//////////import com.ktmmobile.mcp.cert.dao.CertDao;
//////////import com.ktmmobile.mcp.cert.dto.CertDto;
//////////import com.ktmmobile.mcp.common.dto.NiceResDto;
//////////import com.ktmmobile.mcp.common.dto.UserSessionDto;
//////////import com.ktmmobile.mcp.common.exception.McpCommonException;
//////////import com.ktmmobile.mcp.common.service.IpStatisticService;
//////////import com.ktmmobile.mcp.common.util.EncryptUtil;
//////////import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
//////////import com.ktmmobile.mcp.common.util.SessionUtils;
//////////
//////////@Service
//////////public class CertServiceImpl implements CertService {
//////////
//////////    private static final Logger logger = LoggerFactory.getLogger(CertController.class);
//////////
//////////    private static final String COMMON_EXCEPTION = null;
//////////
//////////    @Value("${SERVER_NAME}")
//////////    private String serverName;
//////////
//////////
//////////    @Autowired
//////////    private CertDao certDao;
//////////
//////////    @Autowired
//////////    private IpStatisticService ipstatisticService;
//////////
//////////    @Autowired
//////////    private MypageUserService mypageUserService;
//////////
//////////
//////////    /**
//////////     * 세션의 crtSeq로 스탭개수 조회
//////////     * @param
//////////     * @return int
//////////     * @author wooki
//////////     * @Date : 2023.12
//////////     */
//////////    @Override
//////////    public int getStepCnt() {
//////////        //1.세션 CERT_SEQ 가져오기
//////////        long crtSeq = SessionUtils.getCertSession();
//////////        //2.crtSeq 없으면 리턴
//////////        if(0 == crtSeq) return 0;
//////////
//////////        //3.crtSeq로 개수 조회해서 리턴
//////////        return certDao.getStepCnt(crtSeq);
//////////    }
//////////
//////////    /**
//////////     * eSim/eSimWatch에서 새로고침해서 appForm.do로 돌아갔을 때 일정 스텝 이상 삭제
//////////     * @param int step
//////////     * @return int
//////////     * @author wooki
//////////     * @Date : 2023.12
//////////     */
//////////    @Override
//////////    public int delStepInfo(int step) {
//////////        //1.파라미터 step 없으면 리턴
//////////        if(step <= 0) return 0;
//////////        //2.세션 CERT_SEQ 가져오기
//////////        long crtSeq = SessionUtils.getCertSession();
//////////        //3.crtSeq 없으면 리턴
//////////        if(crtSeq == 0) return 0;
//////////        //4.dto에 set해서 delete 보내고 리턴
//////////        CertDto reqDto = new CertDto();
//////////        reqDto.setCrtSeq(crtSeq);
//////////        reqDto.setStep(step);
//////////
//////////        return certDao.delStepInfo(reqDto);
//////////    }
//////////
//////////    /**
//////////     * 한 시퀀스의 referer update
//////////     * @return int
//////////     * @author wooki
//////////     * @Date : 2024.02
//////////     */
//////////    @Override
//////////    public int updateCrtReferer() {
//////////        //1.세션 CERT_SEQ 가져오고 없으면 리턴
//////////        long crtSeq = SessionUtils.getCertSession();
//////////        if(crtSeq == 0) return 0;
//////////
//////////        //2.pageType 가져오고 없으면 리턴
//////////        String pageType = SessionUtils.getPageSession();
//////////        if(pageType == null) return 0;
//////////
//////////        //3.dto에 set해서 update 보내고 리턴
//////////        CertDto reqDto = new CertDto();
//////////        reqDto.setCrtSeq(crtSeq);
//////////        reqDto.setcReferer(pageType);
//////////        return certDao.updateCrtReferer(reqDto);
//////////    }
//////////
//////////    /**
//////////     * moduType별 step cnt
//////////     * @return int
//////////     * @author wooki
//////////     * @Date : 2024.02
//////////     */
//////////    @Override
//////////    public int getModuTypeStepCnt(String moduType, String ncType) {
//////////        //1.moduType 없으면 리턴
//////////        if(StringUtils.isBlank(moduType)) return 0;
//////////        //2.세션 CERT_SEQ 가져오고 없으면 리턴
//////////        long crtSeq = SessionUtils.getCertSession();
//////////        if(crtSeq == 0) return 0;
//////////
//////////        //3.dto에 set해서 get 보내고 리턴
//////////        CertDto reqDto = new CertDto();
//////////        reqDto.setCrtSeq(crtSeq);
//////////        reqDto.setModuType(moduType);
//////////        reqDto.setNcType(ncType);
//////////        return certDao.getModuTypeStepCnt(reqDto);
//////////    }
//////////
//////////    /**
//////////     * 인증 개선 대상 url인지 검사
//////////     * @param request
//////////     * @return Map<String, Object>
//////////     * @author hsy
//////////     * @Date : 2023.12
//////////     */
//////////    @Override
//////////    public Map<String, String> isAuthStepApplyUrl(HttpServletRequest request){
//////////
//////////        Map<String, String> resultMap = new HashMap<>();
//////////        resultMap.put("isAuthStep", "N");
//////////
//////////        String redirectPage = "/main.do";
//////////        if("Y".equals(NmcpServiceUtils.isMobile())) redirectPage = "/m/main.do";
//////////
//////////        String requestUrl = (request.getHeader("referer") == null) ? "" : request.getHeader("referer");
//////////        if("".equals(requestUrl)) throw new McpCommonException(COMMON_EXCEPTION, redirectPage);
//////////
//////////        String uri = requestUrl.substring(request.getHeader("referer").indexOf("/", 8));
//////////        int endIndex = uri.indexOf('?');
//////////        if(endIndex > -1) uri = uri.substring(0, endIndex);
//////////
//////////        // 인증 개선 대상 URL
//////////        String[] appformUrlArr = {"appForm/appForm.do", "m/appForm/appForm.do"                  // 핸드폰, 상담사, 셀프개통, ESIM, ESIM WATCH
//////////                                 ,"request/marketRequest.do", "m/request/marketRequest.do"      // 대리점개통
//////////                                 ,"content/reqSharingView.do", "m/content/reqSharingView.do", "content/dataSharingStep3.do", "m/content/dataSharingStep3.do"  	// 데이터쉐어링
//////////                                 ,"mypage/myNameChg.do", "m/mypage/myNameChg.do"				// 명의변경 신청
//////////                                 ,"cs/ownPhoNum.do", "m/cs/ownPhoNum.do" 						// 가입번호 조회
//////////                                 ,"m/appform/setUsimNoDlvey.do"  								// 유심번호 등록 인증
//////////                                 ,"mypage/paywayWiew.do", "m/mypage/paywayWiew.do" 				// 납부방법변경
//////////                                 ,"mypage/paywayView.do", "m/mypage/paywayView.do" 				// 납부방법변경
//////////                                 ,"mypage/usimSelfChg.do", "m/mypage/usimSelfChg.do"            // 유심 셀프변경
//////////                                 ,"mypage/reqRwd.do", "m/mypage/reqRwd.do"                      // 자급제 보상서비스
//////////                                 ,"appForm/appAgentForm.do", "m/appForm/appAgentForm.do"        // 대리점 셀프개통
//////////                                 ,"appForm/appJehuForm.do", "m/appForm/appJehuForm.do"          // 제휴위탁온라인서식지
//////////                                 ,"m/apply/replaceUsimSelf.do"
//////////        };
//////////
//////////        List<String> appformurlList = Arrays.asList(appformUrlArr);
//////////
//////////        if(appformurlList.contains(uri.replaceAll("^/+",""))) resultMap.put("isAuthStep","Y");
//////////
//////////        return resultMap;
//////////    }
//////////
//////////    /**
//////////     * 인증정보 비교해서 map으로 리턴
//////////     * @param certDto
//////////     * @return Map<String, Object>
//////////     * @author wooki
//////////     * @Date : 2023.12
//////////     */
//////////    @Override
//////////    public Map<String, Object> getCertInfo(CertDto certDto) {
//////////
//////////        Map<String,Object> rtnMap = new HashMap<String, Object>();
//////////
//////////        //1.반드시 필요한 정보 compType, urlType 없으면 리턴
//////////        if(StringUtils.isBlank(certDto.getCompType())) {
//////////            rtnMap.put("RESULT_CODE", "99999"); //예상할수 없는 결과 == 예외
//////////            rtnMap.put("RESULT_DESC", "[STEP]처음부터 다시 시도해 주세요.");
//////////            return rtnMap;
//////////        }
//////////
//////////        //2.세션 CERT_SEQ 가져오기
//////////        long crtSeq = SessionUtils.getCertSession();
//////////        logger.info("[CERT] 세션에서 가져온 crtSeq : " + crtSeq);
//////////        certDto.setCrtSeq(crtSeq); //세션 있으면 crtSeq, 없으면 0 저장
//////////
//////////        //3.parameter로 받은 dto 비교해서 결과 값 받기
//////////        String strRtn = this.compCertInfo(certDto);
//////////        //비교 결과 값 없으면 리턴
//////////        if(StringUtils.isBlank(strRtn)) {
//////////            rtnMap.put("RESULT_CODE", "99999"); //예외
//////////            rtnMap.put("RESULT_DESC", "[STEP]처음부터 다시 시도해 주세요.");
//////////            return rtnMap;
//////////        }
//////////
//////////        //4.결과값에 따라 insert/merge/delete 준비
//////////        //4-1.리턴받은 비교 결과 값 set
//////////        certDto.setVeriRslt(strRtn);
//////////        //4-2.로그인 세션 가져와서 id set
//////////        UserSessionDto loginSession = SessionUtils.getUserCookieBean();
//////////        if(null != loginSession) {
//////////            certDto.setRegstId(loginSession.getUserId());
//////////            certDto.setRvisnId(loginSession.getUserId());
//////////        }
//////////
//////////        //4-3.referer 없으면 세션에서 PAGE_TYPE set - 어느 메뉴에서 진입했는지 확인용
//////////        if(StringUtils.isBlank(certDto.getcReferer())) {
//////////            certDto.setcReferer(SessionUtils.getPageSession());
//////////        }
//////////
//////////        //4-4.사용자 ip set
//////////        certDto.setRip(ipstatisticService.getClientIp());
//////////
//////////        /* strRtn
//////////            * Y : 인증비교 결과 맞음
//////////            * N : 인증비교 결과 틀림
//////////            * X : 비교 대상이 없음
//////////            * I : 단순 insert
//////////            * U : 단순 update
//////////            * D : 단순 delete  */
//////////        //4-5.strRtn 값에 따라 insert/merge/delete
//////////        if("U".equals(strRtn)) { //compType이 E일 때 U 또는 I 리턴(특수경우)
//////////            try {
//////////                //moduType이 nicePin일 때 이름, 생년월일, CI update - 20240215 수정
//////////                if("nicePin".equals(certDto.getModuType())) {
//////////                    certDao.updateNicePin(certDto);
//////////                }else if(!StringUtils.isBlank(certDto.getMobileNo())){
//////////                    certDao.updateMobileNo(certDto);
//////////                }else {
//////////                    certDao.updateEsimSeq(certDto);
//////////                }
//////////            }catch(DataAccessException e) {
//////////                strRtn = "E";
//////////                logger.error("[CERT][getCertInfo.updateEsimSeq.DataAccessException", e.getMessage());
//////////            }catch(Exception e) {
//////////                strRtn = "E";
//////////                logger.error("[CERT][getCertInfo.updateEsimSeq.Exception]", e.getMessage());
//////////            }
//////////        }else if("D".equals(strRtn)) { //compType이 G일때
//////////            try {
//////////                certDao.deleteCert(certDto);
//////////            }catch(DataAccessException e) {
//////////                strRtn = "E";
//////////                logger.error("[CERT][getCertInfo.deleteCert.DataAccessException", e.getMessage());
//////////            }catch(Exception e) {
//////////                strRtn = "E";
//////////                logger.error("[CERT][getCertInfo.deleteCert.Exception]", e.getMessage());
//////////            }
//////////        }else { //Y,N,X,I NMCP_CRT_VLD_DTL insert
//////////            try {
//////////                long rtnCrtSeq = certDao.insertCert(certDto);
//////////                //insert 시 세션에 crtSeq 저장
//////////                SessionUtils.setCertSession(rtnCrtSeq);
//////////                //insert 후에 null set
//////////                certDto.setReqSeq(null);
//////////                certDto.setResSeq(null);
//////////                certDto.setCrtEtc(null);
//////////            }catch(DataAccessException e) {
//////////                strRtn = "E";
//////////                logger.error("[CERT][getCertInfo.insertCert.DataAccessException", e.getMessage());
//////////            }catch(Exception e) {
//////////                strRtn = "E";
//////////                logger.error("[CERT][getCertInfo.insertCert.Exception]", e.getMessage());
//////////            }
//////////        }
//////////
//////////        /* rtnMap
//////////         * 00000 : [STEP]인증한 정보가 동일합니다.
//////////         * 00001 : [STEP]본인 인증한 정보가 틀립니다.<br>새로고침 후 다시 시도해 주세요.
//////////         * 00002 : [STEP]비교 대상이 없어 진행이 불가합니다.<br>처음부터 다시 시도해 주세요.
//////////         * 00003 : [STEP]오류입니다.<br>이 메시지가 계속되면 처음부터 다시 시도해 주세요.
//////////         * 99999 : [STEP]처음부터 다시 시도해 주세요. */
//////////        //6.리턴값에 따라 Map 설정(단계 중 실패하거나 최종단계 종료 시 CERT_SEQ 세션 비우기)
//////////        if("Y".equals(strRtn)) {
//////////            rtnMap.put("RESULT_CODE", "00000"); //Constants.AJAX_SUCCESS
//////////            rtnMap.put("RESULT_DESC", "[STEP]인증한 정보가 동일합니다.");
//////////        }else if("N".equals(strRtn)) {
//////////            logger.info("[CERT rtnMap][N:00001][crtSeq:" + crtSeq);
//////////            rtnMap.put("RESULT_CODE", "00001");
//////////            rtnMap.put("RESULT_DESC", "[STEP]본인 인증한 정보가 틀립니다.<br>새로고침 후 다시 시도해 주세요."); //==NICE_CERT_EXCEPTION
//////////        }else if("X".equals(strRtn)) {
//////////            logger.info("[CERT rtnMap][X:00002][crtSeq:" + crtSeq);
//////////            rtnMap.put("RESULT_CODE", "00002");
//////////            rtnMap.put("RESULT_DESC", "[STEP]비교 대상이 없어 진행이 불가합니다.<br>처음부터 다시 시도해 주세요.");
//////////        }else if("E".equals(strRtn)) {
//////////            logger.info("[CERT rtnMap][E:00003][crtSeq:" + crtSeq);
//////////            rtnMap.put("RESULT_CODE", "00003");
//////////            rtnMap.put("RESULT_DESC", "[STEP]오류입니다.<br>이 메시지가 계속되면 처음부터 다시 시도해 주세요.");
//////////        }else {
//////////            logger.info("[CERT rtnMap][exception:99999][crtSeq:" + crtSeq);
//////////            rtnMap.put("RESULT_CODE", "99999"); //예외
//////////            rtnMap.put("RESULT_DESC", "[STEP]처음부터 다시 시도해 주세요.");
//////////        }
//////////
//////////        return rtnMap;
//////////    }
//////////
//////////    /**
//////////     * cert 비교 공통 함수
//////////     * @param compType 비교유형
//////////     * @param keyArr   필수항목명
//////////     * @param valueArr 필수항목값
//////////     * @return Map<String,String>
//////////     */
//////////    @Override
//////////    public Map<String, String> vdlCertInfo(String compType, String[] keyArr, String[] valueArr) {
//////////
//////////        Map<String, String> rtnMap= new HashMap<>();
//////////
//////////        // 1. 파라미터 체크
//////////        if(StringUtils.isEmpty(compType)){
//////////            rtnMap.put("RESULT_CODE", "FAIL1");
//////////            rtnMap.put("RESULT_DESC", STEP_INFO_NULL_EXCEPTION);
//////////            return rtnMap;
//////////        }
//////////
//////////        if(keyArr == null || valueArr == null || keyArr.length < 1 || valueArr.length < 1){
//////////            rtnMap.put("RESULT_CODE", "FAIL2");
//////////            rtnMap.put("RESULT_DESC", STEP_INFO_NULL_EXCEPTION);
//////////            return rtnMap;
//////////        }
//////////
//////////        if(keyArr.length > valueArr.length){
//////////            rtnMap.put("RESULT_CODE", "FAIL3");
//////////            rtnMap.put("RESULT_DESC", STEP_INFO_NULL_EXCEPTION);
//////////            return rtnMap;
//////////        }
//////////
//////////        // 2. json으로 변환
//////////        int failIdx= -1;
//////////        StringBuilder sb = new StringBuilder("{");
//////////        for(int i=0; i<keyArr.length; i++){
//////////
//////////            // ncType은 null체크 제외
//////////            if(StringUtils.isEmpty(valueArr[i])){
//////////                if("ncType".equals(keyArr[i])) valueArr[i]= "";
//////////                else{
//////////                    failIdx= i;
//////////                    break;
//////////                }
//////////            }
//////////
//////////            if(i > 0) sb.append(",");
//////////            sb.append("\"");
//////////            sb.append(keyArr[i]);
//////////            sb.append("\"");
//////////            sb.append(":");
//////////            sb.append("\"");
//////////            sb.append(valueArr[i]);
//////////            sb.append("\"");
//////////        }
//////////
//////////        // 2-1. 필수값 누락
//////////        if(failIdx > -1){
//////////            rtnMap.put("RESULT_CODE", "FAIL4");
//////////            rtnMap.put("RESULT_DESC", STEP_INFO_NULL_EXCEPTION);
//////////            logger.debug(keyArr[failIdx] +" 값이 존재하지 않습니다.");
//////////            return rtnMap;
//////////        }
//////////
//////////        sb.append("}");
//////////        String certJson= sb.toString();
//////////        logger.debug("certJson > " + certJson);
//////////
//////////        // 3. certDto로 변환
//////////        try {
//////////            CertDto certDto= new ObjectMapper().readValue(certJson, CertDto.class);
//////////
//////////            // 3-1. compType에 따라 step 처리
//////////            certDto.setCompType(compType);
//////////            Map<String, Object> certReslt = this.getCertInfo(certDto);
//////////
//////////            rtnMap.put("RESULT_CODE", (String)certReslt.get("RESULT_CODE"));
//////////            rtnMap.put("RESULT_DESC", (String)certReslt.get("RESULT_DESC"));
//////////
//////////        } catch (IOException e) {
//////////            rtnMap.put("RESULT_CODE", "FAIL5");
//////////            rtnMap.put("RESULT_DESC", "필수항목명을 다시 확인해 주시기 바랍니다.");
//////////        }
//////////
//////////        return rtnMap;
//////////    }
//////////
//////////    /**
//////////     * 인증 개선 대상 menuType 인지 검사 (공통 sms 인증)
//////////     * @param menuType
//////////     * @param smsAuthType
//////////     * @return Map<String, String>
//////////     * @Date : 2024.01.05
//////////     **/
//////////    public Map<String, String> smsAuthCertMenuType(String menuType , String smsAuthType){
//////////
//////////        Map<String, String> resultMap = new HashMap<>();
//////////
//////////        String[] menuTypeList = null;
//////////
//////////        // 1. (/sms/smsAuthInfoPop.do, /sms/smsAuthAjax.do, /sms/smsAuthCheckAjax.do) 관련 url을 사용하는 메뉴
//////////        if("smsPop".equals(smsAuthType)){
//////////
//////////            menuTypeList= new String[]{"reRate", "phoneNumChange", "pull", "joinCert", "frndReg", "frndRegNe"
//////////                                     , "charge", "multiPhone", "reviewReg", "mcashReviewReg"};
//////////
//////////            /*
//////////                -- charge: 납부방법변경
//////////                -- reviewReg: 리뷰 등록
//////////                -- reRate: 요금할인 재약정
//////////                -- phoneNumChange: 번호변경
//////////                -- pull: 데이터 당겨쓰기
//////////                -- joinCert: 가입증명원 인쇄
//////////                -- frndReg: 친구초대
//////////                -- frndRegNe: 친구초대(올인원)
//////////                -- multiPhone: 다회선추가, 정회원인증
//////////            */
//////////
//////////        }else{
//////////            // 2. (/sms/sendCertSmsAjax.do, /sms/checkCertSmsAjax.do) 관련 url을 사용하는 메뉴
//////////            menuTypeList = new String[]{"shareData", "combine", "frndReg", "frndRegNe", "QNA", "rate"
//////////                                      , "order", "gift", "reviewReg", "reviewDel","consentGift","combineSelf","fath"};
//////////
//////////            /*
//////////                -- frndReg: 친구초대
//////////                -- combine: 아무나 결합, 아무나 가족결합
//////////                -- shareData: 함께쓰기
//////////                -- QNA: 1대1 문의작성, 1:1문의 확인
//////////                -- reviewDel: 사용후기 삭제
//////////                -- reviewReg: 사용후기 작성
//////////                -- order: 신청조회
//////////                -- gift: 사은품 신청
//////////                -- rate: 요금할인 재약정2
//////////                -- consentGift: 제세공과금 개인정보 수집이용동의
//////////                -- combineSelf: 마스터 결합
//////////             */
//////////        }
//////////
//////////        List<String> appformurlList = Arrays.asList(menuTypeList);
//////////
//////////        if(appformurlList.contains(menuType)) {
//////////            resultMap.put("menuTypeYn", "Y");
//////////        }else{
//////////            resultMap.put("menuTypeYn", "N");
//////////        }
//////////
//////////        return resultMap;
//////////    }
//////////
//////////    // ncType조회 (0: 청소년, "": 내국인 또는 외국인)
//////////    @Override
//////////    public String getNcTypeForCrt(String userSsn, String contractNum) {
//////////
//////////        String ncType= "";
//////////        String customerSsn= userSsn;
//////////
//////////        // 0. 파라미터 검사
//////////        if(StringUtils.isEmpty(userSsn) && StringUtils.isEmpty(contractNum)){
//////////            return ncType;
//////////        }
//////////
//////////        if(StringUtils.isEmpty(customerSsn)){
//////////            // 1. 개통정보 조회
//////////            Map<String, String> resOjb= mypageUserService.selectContractObj("", "" ,contractNum) ;
//////////            if (resOjb == null) return ncType;
//////////            customerSsn = resOjb.get("USER_SSN");
//////////        }
//////////
//////////        // 2. 주민번호 암호화되어 있는 경우 복호화
//////////        if (!StringUtils.isNumeric(customerSsn)) {
//////////            try {
//////////                customerSsn = EncryptUtil.ace256Dec(customerSsn);
//////////            } catch (CryptoException e) {
//////////                customerSsn= "";
//////////            }
//////////        }
//////////
//////////        // 3. 청소년 확인
//////////        if(customerSsn.length() < 7) return ncType;
//////////
//////////        String diviVal = customerSsn.substring(6, 7);
//////////        if ("|5|6|7|8".indexOf(diviVal) == -1){
//////////            int age = NmcpServiceUtils.getAge(customerSsn, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
//////////            if (19 > age)  ncType= "0";
//////////        }
//////////
//////////        return ncType;
//////////    }
//////////
//////////    // crtSeq에 해당하는 referer 조회
//////////    @Override
//////////    public String getCertReferer(long crtSeq) {
//////////        if(crtSeq == 0) return null;
//////////        return certDao.getCertReferer(crtSeq);
//////////    }
//////////
//////////    /**
//////////     * 인증정보 비교해서 결과값 리턴
//////////     * @param CertDto
//////////         CertDto.getCompType()
//////////        A : 모듈구분(moduType)에 따른 세션과 파라미터로 받은 이름/생년월일 비교
//////////        B : 본인인증 세션(CUST_AUTH)과 모듈구분(moduType)의 세션 CI값 비교
//////////        C : 단순 INSERT(비교는안함)
//////////        D : 같은 crtSeq로 디비 저장된 이전 데이터와 비교(moduType은 비교 제외)
//////////        E : uploadPhoneSrlNo(ESIM SEQ) update 용 : merge or insert
//////////        F : 로그인 세션의 USER_ID로 정회원 테이블에서 계약번호 가져와서 파라미터의 계약번호와 비교
//////////        G : DELETE(같은 crtSeq, moduType) : insert 안함
//////////     * @return String
//////////         Y : 인증비교 결과 맞음
//////////        N : 인증비교 결과 틀림
//////////        X : 비교 대상이 없음
//////////        I : 단순 insert
//////////        U : 단순 update
//////////        D : 단순 delete
//////////     * @author wooki
//////////     * @Date : 2023.12
//////////     */
//////////    private String compCertInfo(CertDto paramDto) {
//////////
//////////        String compType = paramDto.getCompType();
//////////
//////////        //A.모듈구분(moduType)에 따른 세션과 파라미터로 받은 이름/생년월일 비교
//////////        if("A".equals(compType)) {
//////////
//////////            //A-1.moduType 없으면 리턴
//////////            if(StringUtils.isBlank(paramDto.getModuType())) return "X";
//////////
//////////            //A-2.moduType 별 인증세션 가져오기
//////////            NiceResDto niceSession = this.getSession(paramDto.getModuType());
//////////            if(niceSession == null) return "X"; //인증세션 없으면 리턴
//////////            if(StringUtils.isBlank(niceSession.getName()) || StringUtils.isBlank(niceSession.getBirthDate())) return "X";  //세션에 이름 또는 생년월일이 없으면 리턴
//////////
//////////            //A-3.인증세션의 정보 set(테이블에 insert 해야함)
//////////            paramDto.setConnInfo(niceSession.getConnInfo());
//////////            paramDto.setAuthType(niceSession.getAuthType());
//////////            paramDto.setReqSeq(niceSession.getReqSeq());
//////////            paramDto.setResSeq(niceSession.getResSeq());
//////////
//////////            //A-4.세션의 이름, 생년월일 정보 비교(세션의 값과 파라미터로 받은 값 비교)
//////////            return (paramDto.getName().equalsIgnoreCase(niceSession.getName().replaceAll(" ", "")) && niceSession.getBirthDate().indexOf(paramDto.getBirthDate()) > -1) ? "Y" : "N";
//////////
//////////        //B.본인인증 세션(CUST_AUTH)과 모듈구분(moduType)의 세션 CI값 비교
//////////        }else if("B".equals(compType)) {
//////////
//////////            //B-1.moduType 없으면 리턴
//////////            if(StringUtils.isBlank(paramDto.getModuType())) return "X";
//////////
//////////            //B-2.moduType 별 인증세션 가져오기
//////////            NiceResDto niceSession = this.getSession(paramDto.getModuType());
//////////            if(niceSession == null) return "X"; //인증세션값 없으면 리턴
//////////
//////////            //B-3.본인인증(CUST_AUTH) 세션 가져오기
//////////            NiceResDto custAuthSession = SessionUtils.getNiceResCookieBean();
//////////            if(custAuthSession == null) return "X"; //본인인증 세션값 없으면 리턴
//////////
//////////            //B-4.본인인증세션(CUST_AUTH)의 connInfo set(테이블에 insert 해야함)
//////////            paramDto.setConnInfo(custAuthSession.getConnInfo());
//////////
//////////            //B-5.세션의 CI 정보 확인(세션의 값과 파라미터로 받은 값 비교)
//////////            return (niceSession.getConnInfo().equals(custAuthSession.getConnInfo())) ? "Y" : "N";
//////////
//////////        //C.단순 INSERT(비교는안함)
//////////        }else if("C".equals(compType)) {
//////////
//////////            return "I";
//////////
//////////        //D.같은 crtSeq로 디비 저장된 이전 데이터와 비교(moduType은 비교 제외)
//////////        }else if("D".equals(compType)) {
//////////
//////////            //D-1.crtSeq가 없으면 리턴
//////////            if(0 >= paramDto.getCrtSeq()) return "X";
//////////
//////////            //D-2.crtSeq로 데이터 조회
//////////            List<CertDto> certCompareList = certDao.getCompareList(paramDto);
//////////            if(null == certCompareList) return "X"; //crtSeq로 조회해 온 리스트 없으면 리턴
//////////
//////////            //D-3.certDto에서 널체크해서 비교할 개수가 몇개인지 조사
//////////            int cntComp = this.getCompareNum(paramDto);
//////////            logger.info("[CERT]getCertInfo compType==D crtSeq:" + paramDto.getCrtSeq() + ",cntComp:" + cntComp);
//////////            if(0 >= cntComp) {
//////////                if("Y".equals(paramDto.getStepEndYn())) { //스텝이 마지막이면 비교할 개수 없어도 Y
//////////                    return "Y";
//////////                }
//////////                return "X"; //비교할 개수 없으면 리턴
//////////            }
//////////
//////////            //D-4.실패카운트 가져오기 : 데이터 조회해온 리스트와 param dto의 정보 비교
//////////            //name, birthDate, mobileNo, connInfo, contractNum, reqUsimSn, reqBank, reqAccountNumber, uploadPhoneSrlNo, authType
//////////            //현재는 이항목들만 체크, 후에 추가되면 getCompreFailNum 함수, getCompreSuccNum 함수에 추가하면 됨
//////////            int cntFail = this.getCompreFailNum(certCompareList, paramDto);
//////////            logger.info("[CERT]getCertInfo compType==D crtSeq:" + paramDto.getCrtSeq() + ",cntFail:" + cntFail);
//////////            if(0 < cntFail) return "N"; //틀린게 한개라도 있으면 리턴
//////////
//////////            //D-5.성공카운트 가져오기 : 데이터 조회해온 리스트와 param dto의 정보 비교
//////////            int cntSucc = this.getCompreSuccNum(certCompareList, paramDto);
//////////            logger.info("[CERT]getCertInfo compType==D crtSeq:" + paramDto.getCrtSeq() + ",cntSucc:" + cntSucc);
//////////
//////////            //D-6.비교대상 개수와 비교성공 개수가 같으면 Y, 틀리면 N
//////////            if(cntComp != cntSucc) {
//////////                paramDto.setCrtEtc("비교대상개수:"+cntComp+"__비교성공개수:"+cntSucc);
//////////                return "N";
//////////            }
//////////
//////////            return "Y";
//////////
//////////        //E.uploadPhoneSrlNo(ESIM SEQ) update 용(특수처리)
//////////        }else if("E".equals(compType)) {
//////////            //20240215 E타입 수정
//////////            //E-1.moduType이 nicePin일 때 이름, 생년월일, CI update
//////////            if("nicePin".equals(paramDto.getModuType())) {
//////////                if(0 < paramDto.getCrtSeq()) {
//////////                    CertDto searchCertDto= new CertDto();
//////////                    searchCertDto.setCrtSeq(paramDto.getCrtSeq());
//////////                    searchCertDto.setModuType("nicePin");
//////////                    int cntData = certDao.getModuTypeStepCnt(searchCertDto);
//////////                    return (0 < cntData) ? "U" : "I";
//////////                }else{
//////////                    return "I";
//////////                }
//////////            }
//////////
//////////            //moduType 없을 때
//////////            //E-2.crtSeq와 uploadPhoneSrlNo(ESIM SEQ) 체크
//////////            if(!StringUtils.isBlank(paramDto.getUploadPhoneSrlNo())) {
//////////                if(0 < paramDto.getCrtSeq()) {
//////////                    //crtSeq와 uploadPhoneSrlNo로 데이터 존재하는지 조회
//////////                    int cntData = certDao.getDataEsimSeq(paramDto.getCrtSeq());
//////////                    //조회한 데이터 존재하면 update 아니면 insert(기존 crtSeq에 insert)
//////////                    return (0 < cntData) ? "U" : "I";
//////////                //E-3.crtSeq와 uploadPhoneSrlNo 둘 다 없으면 I 리턴(새 crtSeq에 insert)
//////////                }else {
//////////                    return "I";
//////////                }
//////////            }
//////////
//////////            //E-3.crtSeq와 moveMobileNo 체크
//////////            if(!StringUtils.isBlank(paramDto.getMobileNo())) {
//////////                if(0 < paramDto.getCrtSeq()) {
//////////                    int cntData = certDao.getDataMobileNo(paramDto.getCrtSeq());
//////////                    return (0 < cntData) ? "U" : "I";
//////////                }else {
//////////                    return "I";
//////////                }
//////////            }
//////////
//////////            return "X";
//////////
//////////        //F.로그인 세션의 USER_ID로 정회원 테이블에서 계약번호 가져와서 파라미터의 계약번호와 비교
//////////        }else if("F".equals(compType)) {
//////////
//////////            //F-1.파라미터 계약번호 없으면 리턴
//////////            if(StringUtils.isBlank(paramDto.getContractNum())) return "X";
//////////
//////////            //F-2.로그인 세션 가져오기
//////////            UserSessionDto loginSession = SessionUtils.getUserCookieBean();
//////////            if(null == loginSession) return "X"; //세션 없으면 리턴
//////////            if(StringUtils.isBlank(loginSession.getUserId())) return "X"; //세션의 userId 없으면 리턴
//////////
//////////            //F-3.정회원 테이블(MCP_USER_CNTR_MNG)에서 계약번호 가져오기
//////////            List<String> rtnContractNumList = certDao.getContractNumByUserId(loginSession.getUserId());
//////////            if(null == rtnContractNumList) return "X"; //조회해온 contractNum 리스트 없으면 리턴
//////////
//////////            //F-4.로그인 세션의 계약번호와 정회원 테이블의 계약번호 비교해서 있으면 Y 리턴
//////////            for(String rtnContractNum : rtnContractNumList) {
//////////                if(paramDto.getContractNum().equals(rtnContractNum)) return "Y";
//////////            }
//////////
//////////            //F-5.4에서 리턴되지 않았다면 같은 계약번호가 없으므로 N
//////////            paramDto.setCrtEtc("MCP_USER_CNTR_MNG에서 조회해온 contractNum 틀림");
//////////            return "N";
//////////
//////////        //G : DELETE(같은 crtSeq, moduType)
//////////        }else if("G".equals(compType)) {
//////////
//////////            //G-1.crtSeq나 moduType이 없으면 리턴
//////////            if(0 >= paramDto.getCrtSeq() || StringUtils.isBlank(paramDto.getModuType())) return "X";
//////////
//////////            return "D";
//////////        //그 외
//////////        }else {
//////////            return "X";
//////////        }
//////////    }
//////////
//////////    /**
//////////     * 비교대상 몇개인지 카운트
//////////     * @param CertDto
//////////     * @return int
//////////     * @author wooki
//////////     * @Date : 2023.12
//////////     */
//////////    private int getCompareNum(CertDto certDto) {
//////////
//////////        //crtSeq, moduType, regstDttm, regstId, resgtDt, rvisnDttm, rvisnId, step, compType, veriRslt, rip, stepEndYn 제외
//////////        int compCnt = 0;
//////////
//////////        if(!StringUtils.isBlank(certDto.getAuthType())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getBirthDate())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getConnInfo())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getContractNum())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getMobileNo())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getName())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getReqAccountNumber())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getReqBank())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getReqUsimSn())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getUploadPhoneSrlNo())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getReqSeq())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getResSeq())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getDupInfo())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getReqCardCompany())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getReqCardNo())) compCnt++;
//////////        if(!StringUtils.isBlank(certDto.getCrdtCardTermDay())) compCnt++;
//////////
//////////        return compCnt;
//////////    }
//////////
//////////
//////////    /**
//////////     * 비교항목이 있을 때 비교해서 실패개수 카운트해서 리턴
//////////     * @param List<CertDto>, CertDto
//////////     * @return int
//////////     * @author wooki
//////////     * @Date : 2023.12
//////////     */
//////////    private int getCompreFailNum(List<CertDto> certCompareList, CertDto paramDto) {
//////////
//////////        int cntDiff = 0;
//////////        //비교항목이 있는 경우 한개라도 비교값이 틀리면 실패개수+1 그리고 break
//////////        for(CertDto compareDto : certCompareList) {
//////////
//////////            StringBuffer sbLog = new StringBuffer();
//////////            sbLog.append("[crtSeq:").append(paramDto.getCrtSeq()).append("]");
//////////            sbLog.append("[step:").append(compareDto.getStep()).append("]");
//////////            sbLog.append("[paramDto**compDto]");
//////////
//////////            paramDto.setCrtEtc(null); //20240122 추가
//////////
//////////            if(!StringUtils.isBlank(paramDto.getName()) && !StringUtils.isBlank(compareDto.getName())) {
//////////                sbLog.append("[name:").append(paramDto.getName()).append("**").append(compareDto.getName()).append("]");
//////////                if(!paramDto.getName().equals(compareDto.getName())) {
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("name:"+paramDto.getName()+"__"+compareDto.getName()); //비교 틀릴 시 crtEtc에 틀린 컬럼, 값 set하여 마지막에 insert
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getBirthDate()) && !StringUtils.isBlank(compareDto.getBirthDate())) {
//////////                sbLog.append("[birthDate:").append(this.getBirthReplace(paramDto.getBirthDate())).append("**").append(this.getBirthReplace(compareDto.getBirthDate())).append("]");
//////////                if(!this.getBirthReplace(paramDto.getBirthDate()).equals(this.getBirthReplace(compareDto.getBirthDate()))) {
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("birthDate:"+this.getBirthReplace(paramDto.getBirthDate())+"__"+this.getBirthReplace(compareDto.getBirthDate()));
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getMobileNo()) && !StringUtils.isBlank(compareDto.getMobileNo())) {
//////////                sbLog.append("[mobileNo:").append(paramDto.getMobileNo()).append("**").append(compareDto.getMobileNo()).append("]");
//////////                if(!paramDto.getMobileNo().equals(compareDto.getMobileNo())) {
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("mobileNo:"+paramDto.getMobileNo()+"__"+compareDto.getMobileNo());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getConnInfo()) && !StringUtils.isBlank(compareDto.getConnInfo())) {
//////////                sbLog.append("[connInfo:").append(paramDto.getConnInfo()).append("**").append(compareDto.getConnInfo()).append("]");
//////////                if(!paramDto.getConnInfo().equals(compareDto.getConnInfo())) {
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("connInfo:"+paramDto.getConnInfo()+"__"+compareDto.getConnInfo());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getContractNum()) && !StringUtils.isBlank(compareDto.getContractNum())) {
//////////                sbLog.append("[contractNum:").append(paramDto.getContractNum()).append("**").append(compareDto.getContractNum()).append("]");
//////////                if(!paramDto.getContractNum().equals(compareDto.getContractNum())) {
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("contractNum:"+paramDto.getContractNum()+"__"+compareDto.getContractNum());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getReqUsimSn()) && !StringUtils.isBlank(compareDto.getReqUsimSn())) {
//////////                sbLog.append("[reqUsimSn:").append(paramDto.getReqUsimSn()).append("**").append(compareDto.getReqUsimSn()).append("]");
//////////                if(!paramDto.getReqUsimSn().equals(compareDto.getReqUsimSn())) {
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("reqUsimSn:"+paramDto.getReqUsimSn()+"__"+compareDto.getReqUsimSn());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getReqBank()) && !StringUtils.isBlank(compareDto.getReqBank())) {
//////////                sbLog.append("[reqBank:").append(paramDto.getReqBank()).append("**").append(compareDto.getReqBank()).append("]");
//////////                if(!paramDto.getReqBank().equals(compareDto.getReqBank())) {
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("reqBank:"+paramDto.getReqBank()+"__"+compareDto.getReqBank());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getReqAccountNumber()) && !StringUtils.isBlank(compareDto.getReqAccountNumber())) {
//////////                sbLog.append("[reqAccountNumber:").append(paramDto.getReqAccountNumber()).append("**").append(compareDto.getReqAccountNumber()).append("]");
//////////                if(!paramDto.getReqAccountNumber().equals(compareDto.getReqAccountNumber())) {
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("reqAccountNumber:"+paramDto.getReqAccountNumber()+"__"+compareDto.getReqAccountNumber());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getUploadPhoneSrlNo()) && !StringUtils.isBlank(compareDto.getUploadPhoneSrlNo())) {
//////////                sbLog.append("[uploadPhoneSrlNo:").append(paramDto.getUploadPhoneSrlNo()).append("**").append(compareDto.getUploadPhoneSrlNo()).append("]");
//////////                if(!paramDto.getUploadPhoneSrlNo().equals(compareDto.getUploadPhoneSrlNo())) {
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("uploadPhoneSrlNo:"+paramDto.getUploadPhoneSrlNo()+"__"+compareDto.getUploadPhoneSrlNo());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getAuthType()) && !StringUtils.isBlank(compareDto.getAuthType())) {
//////////                sbLog.append("[authType:").append(paramDto.getAuthType()).append("**").append(compareDto.getAuthType()).append("]");
//////////                if(!paramDto.getAuthType().equals(compareDto.getAuthType())){
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("authType:"+paramDto.getAuthType()+"__"+compareDto.getAuthType());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getReqSeq()) && !StringUtils.isBlank(compareDto.getReqSeq())) {
//////////                sbLog.append("[reqSeq:").append(paramDto.getReqSeq()).append("**").append(compareDto.getReqSeq()).append("]");
//////////                if(!paramDto.getReqSeq().equals(compareDto.getReqSeq())){
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("reqSeq:"+paramDto.getReqSeq()+"__"+compareDto.getReqSeq());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getResSeq()) && !StringUtils.isBlank(compareDto.getResSeq())) {
//////////                sbLog.append("[resSeq:").append(paramDto.getResSeq()).append("**").append(compareDto.getResSeq()).append("]");
//////////                if(!paramDto.getResSeq().equals(compareDto.getResSeq())){
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("resSeq:"+paramDto.getResSeq()+"__"+compareDto.getResSeq());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getDupInfo()) && !StringUtils.isBlank(compareDto.getDupInfo())) {
//////////                sbLog.append("[dupInfo:").append(paramDto.getDupInfo()).append("**").append(compareDto.getDupInfo()).append("]");
//////////                if(!paramDto.getDupInfo().equals(compareDto.getDupInfo())){
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("dupInfo:"+paramDto.getDupInfo()+"__"+compareDto.getDupInfo());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getReqCardCompany()) && !StringUtils.isBlank(compareDto.getReqCardCompany())) {
//////////                sbLog.append("[reqCardCompany:").append(paramDto.getReqCardCompany()).append("**").append(compareDto.getReqCardCompany()).append("]");
//////////                if(!paramDto.getReqCardCompany().equals(compareDto.getReqCardCompany())){
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("reqCardCompany:"+paramDto.getReqCardCompany()+"__"+compareDto.getReqCardCompany());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getReqCardNo()) && !StringUtils.isBlank(compareDto.getReqCardNo())) {
//////////                sbLog.append("[reqCardNo:").append(paramDto.getReqCardNo()).append("**").append(compareDto.getReqCardNo()).append("]");
//////////                if(!paramDto.getReqCardNo().equals(compareDto.getReqCardNo())){
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("reqCardNo:"+paramDto.getReqCardNo()+"__"+compareDto.getReqCardNo());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            if(!StringUtils.isBlank(paramDto.getCrdtCardTermDay()) && !StringUtils.isBlank(compareDto.getCrdtCardTermDay())) {
//////////                sbLog.append("[crdtCardTermDay:").append(paramDto.getCrdtCardTermDay()).append("**").append(compareDto.getCrdtCardTermDay()).append("]");
//////////                if(!paramDto.getCrdtCardTermDay().equals(compareDto.getCrdtCardTermDay())){
//////////                    logger.info("[CERT FAIL]" + sbLog.toString());
//////////                    paramDto.setCrtEtc("crdtCardTermDay:"+paramDto.getCrdtCardTermDay()+"__"+compareDto.getCrdtCardTermDay());
//////////                    cntDiff++;
//////////                    break;
//////////                }
//////////            }
//////////            //logger.info("[CERT SUCC]" + sbLog.toString());
//////////        }
//////////
//////////        return cntDiff;
//////////    }
//////////
//////////    /**
//////////     * 비교대상 개수와 비교성공 개수를 비교하기 위한 함수.
//////////     * 성공개수는 비교항목 당 1개만 카운트 함.(비교항목 당 성공이 2개 이상이어도 1개만 카운트)
//////////     * @param List<CertDto>, CertDto
//////////     * @return int
//////////     * @author wooki
//////////     * @Date : 2023.12
//////////     */
//////////    private int getCompreSuccNum(List<CertDto> certCompareList, CertDto paramDto) {
//////////        int cntSucc = 0;
//////////        //비교항목이 있다면 리스트에서 한개라도 맞는게 있으면 성공개수+1 그리고 break
//////////
//////////        if(!StringUtils.isBlank(paramDto.getName())) {
//////////            for (CertDto compareDto : certCompareList) {
//////////                if(paramDto.getName().equals(compareDto.getName())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getBirthDate())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(this.getBirthReplace(paramDto.getBirthDate()).equals(this.getBirthReplace(compareDto.getBirthDate()))) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getMobileNo())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getMobileNo().equals(compareDto.getMobileNo())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getConnInfo())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getConnInfo().equals(compareDto.getConnInfo())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getContractNum())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getContractNum().equals(compareDto.getContractNum())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getReqUsimSn())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getReqUsimSn().equals(compareDto.getReqUsimSn())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getReqBank())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getReqBank().equals(compareDto.getReqBank())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getReqAccountNumber())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getReqAccountNumber().equals(compareDto.getReqAccountNumber())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getUploadPhoneSrlNo())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getUploadPhoneSrlNo().equals(compareDto.getUploadPhoneSrlNo())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getAuthType())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getAuthType().equals(compareDto.getAuthType())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getReqSeq())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getReqSeq().equals(compareDto.getReqSeq())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getResSeq())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getResSeq().equals(compareDto.getResSeq())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getDupInfo())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getDupInfo().equals(compareDto.getDupInfo())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getReqCardCompany())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getReqCardCompany().equals(compareDto.getReqCardCompany())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getReqCardNo())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getReqCardNo().equals(compareDto.getReqCardNo())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        if(!StringUtils.isBlank(paramDto.getCrdtCardTermDay())) {
//////////            for(CertDto compareDto : certCompareList) {
//////////                if(paramDto.getCrdtCardTermDay().equals(compareDto.getCrdtCardTermDay())) {
//////////                    cntSucc++;
//////////                    break;
//////////                }
//////////            }
//////////        }
//////////
//////////        return cntSucc;
//////////    }
//////////
//////////    /**
//////////     * moduType별 인증세션 가져오기
//////////     * @param String
//////////     * @return NiceResDto
//////////     * @author wooki
//////////     * @Date : 2023.12
//////////     */
//////////    private NiceResDto getSession(String moduType) {
//////////
//////////        if(StringUtils.isBlank(moduType)) return null;
//////////
//////////        NiceResDto niceSession = null;
//////////
//////////        if (CUST_AUTH.equals(moduType)) {
//////////            niceSession = SessionUtils.getNiceResCookieBean();
//////////        }else if (CUST_AGENT_AUTH.equals(moduType)) {
//////////            niceSession = SessionUtils.getNiceAgentResCookieBean();
//////////        }else if (INSR_PROD.equals(moduType)) {
//////////            niceSession = SessionUtils.getNiceInsrResCookieBean();
//////////        }else if (OPEN_AUTH.equals(moduType)) {
//////////            niceSession = SessionUtils.getNiceOpenResCookieBean();
//////////        }else if(RWD_PROD.equals(moduType)) {
//////////            niceSession = SessionUtils.getNiceRwdResCookieBean();
//////////        }
//////////
//////////        return niceSession;
//////////    }
//////////
//////////    /**
//////////     * 생일을 YYMMDD 형태로 만들기
//////////     * @param String
//////////     * @return String
//////////     * @author wooki
//////////     * @Date : 2023.12
//////////     */
//////////    private String getBirthReplace(String paramBirthDate) {
//////////
//////////        if(StringUtils.isBlank(paramBirthDate)) return paramBirthDate;
//////////
//////////        String rtnBirthDate = paramBirthDate;
//////////
//////////        //생일이 숫자가 아니면 암호화된 상태이므로 복호화 해줌
//////////        if(!StringUtils.isNumeric(rtnBirthDate)) {
//////////            try {
//////////                rtnBirthDate = EncryptUtil.ace256Dec(paramBirthDate);
//////////            }catch(CryptoException e) {
//////////                return paramBirthDate; //암호와 오류면 원본 리턴
//////////            }
//////////
//////////            if(!StringUtils.isNumeric(rtnBirthDate)) { //복호화 후에도 숫자가 아니면 원본 리턴
//////////                return paramBirthDate;
//////////            }
//////////        }
//////////
//////////        //YYMMDD 형태로 리턴
//////////        if(8 == rtnBirthDate.length()) { //YYYYMMDD일 경우
//////////            return rtnBirthDate.substring(2);
//////////        }else if(8 < rtnBirthDate.length() || 7 == rtnBirthDate.length()) { //full주민번호일 경우 or //기변 (생년월일 + 뒷자리(1자리) = 총 7자리
//////////            return rtnBirthDate.substring(0, 6);
//////////        }
//////////
//////////        return rtnBirthDate;
//////////    }
//////////}
