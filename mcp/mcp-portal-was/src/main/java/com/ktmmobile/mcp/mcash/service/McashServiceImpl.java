package com.ktmmobile.mcp.mcash.service;

import com.ktmmobile.mcp.common.commCode.dao.CommCodeDAO;
import com.ktmmobile.mcp.common.commCode.dto.CommCodeInstDTO;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mspservice.MspService;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mcash.dao.McashDao;
import com.ktmmobile.mcp.mcash.dto.McashApiReqDto;
import com.ktmmobile.mcp.mcash.dto.McashApiResDto;
import com.ktmmobile.mcp.mcash.dto.McashShopDto;
import com.ktmmobile.mcp.mcash.dto.McashUserDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.common.constants.Constants.*;
import static com.ktmmobile.mcp.common.util.SessionUtils.USER_SESSION;

@Service
public class McashServiceImpl implements McashService {

    private static Logger logger = LoggerFactory.getLogger(McashServiceImpl.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Value("${mcash.main.url}")
    private String mcashMainUrl;

    @Value("${mcash.inqCtgNo}")
    private String mcashInqCtgNo;

    @Value("${mcash.dspChnC}")
    private String mcashDspChnC;

    @Autowired
    private McashApiService mcashApiService;

    @Autowired
    private McashDao mcashDao;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MspService mspService;

    @Autowired
    private CommCodeDAO commCodeDAO;

    @Autowired
    private FCommonSvc fCommonSvc;

    private static final String MCASH_AUTH_USER_LIST = "McashAuthorizedUserList";   // [임시] M쇼핑할인 이용권한 사용자 목록
    private static final String SESSION_MCASH_AUTH = "IS_MCASH_AUTH";               // [임시] M쇼핑할인 이용권한 여부
    public static final String MCASH_DISCOUNT_RATE = "McashDiscountRate";           // M쇼핑할인 업체별 적립율

    /**
     * @return
     * @Description : M쇼핑할인 이용 가능 여부 체크
     * @Create Date : 2024.05.21
     */
    @Override
    public Map<String, String> chkMcashUse() {

        /*
            ======== resultMsg: LOGIN ========
            1) resultCd[0001] : 비로그인

            ======== resultMsg: GRADE ========
            1) resultCd[0001] : 이용불가 회원등급

            ======== resultMsg: JOIN =========
            1) resultCd[0001] : 신규가입
            2) resultCd[0002] : 재가입
            2) resultCd[0003] : 당일 회선 해지
            2) resultCd[0004] : 비정상 회원
            3) resultCd[0000] : 이용가능

        */

        Map<String, String> rtnMap = new HashMap<>();

        try {

            // 1. 로그인 체크
            UserSessionDto userSession = SessionUtils.getUserCookieBean();
            if (userSession == null || StringUtil.isEmpty(userSession.getUserId())) {
                throw new McpCommonJsonException("0001", "LOGIN");
            }

            // 2. M쇼핑할인 이용 가능 여부 체크 - 회원등급
            if ( !"01".equals(userSession.getUserDivision()) ) {
                throw new McpCommonJsonException("0001", "GRADE");
            }

            // 3. M쇼핑할인 가입 여부 체크
            rtnMap = this.chkMcashJoin(userSession.getUserId());

        } catch (McpCommonJsonException e) {
            rtnMap.put("resultCd", e.getRtnCode());
            rtnMap.put("resultMsg", e.getErrorMsg());
        }

        return rtnMap;
    }

    /**
     * @return
     * @Description : M쇼핑할인 가입 조건 체크
     * @Create Date : 2024.06.26
     */
    @Override
    public Map<String, String> getMcashJoinCondition(UserSessionDto userSession) {

        /*
            1) RESULT_CODE[2001] : 나이 조건 미충족
            2) RESULT_CODE[2002] : 고객 정보 없음
            3) RESULT_CODE[2003] : 고객 유형 미충족
        */

        Map<String, String> rtnMap = new HashMap<>();
        rtnMap.put("RESULT_CODE", "0000");

        try {
            // 1. 회원정보로 나이조건 확인 (MCP_USER)
            int userAge = 0;
            if(userSession.getBirthday() != null){
                userAge = NmcpServiceUtils.getBirthDateToAmericanAge(userSession.getBirthday(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            }

            if(19 > userAge){   // 나이 조건 미충족
                throw new McpCommonJsonException("2001", "만 19세 미만 고객은 M쇼핑할인 가입이 불가능합니다.");
            }

            // 2. 고객유형 확인 (법인 B)
            String customerType = "";
            if (userSession.getCustomerId() != null) {
                customerType = mypageService.selectCustomerType(userSession.getCustomerId());
            }

            if (StringUtil.isEmpty(customerType)) { // 고객 유형 조건 미충족
                throw new McpCommonJsonException("2002", "고객 정보가 존재하지 않습니다.");
            }

            if ("B".equals(customerType)) { // 고객 유형 조건 미충족
                throw new McpCommonJsonException("2003", "법인고객은 M쇼핑할인 가입이 불가능합니다.");
            }

        } catch (McpCommonJsonException e) {
            rtnMap.put("RESULT_CODE", e.getRtnCode());
            rtnMap.put("RESULT_MSG", e.getErrorMsg());
        }

        return rtnMap;
    }

    /**
     * 2024.07.19
     * M쇼핑할인 가입 정보 조회
     * @return Map<String, String>
     */
    @Override
    public McashUserDto getMcashJoinInfo(String userid) {
        return mcashDao.getMcashJoinInfo(userid);
    }

    /**
     * @return
     * @Description : M쇼핑할인 메인 화면 호출을 위한 URL을 반환한다.
     * @Create Date : 2024.08.07
     */
    @Override
    public String getMcashMainUrl(String userid) {
        String url;

        McashUserDto mcashUserDto = mcashDao.getMcashUserByUserid(userid);

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowStr = sdf.format(now);

        try {
            url = UriComponentsBuilder
                    .fromUriString(mcashMainUrl)
                    .queryParam("inqCtgNo", mcashInqCtgNo)
                    .queryParam("dspChnC", mcashDspChnC)
                    .queryParam("reqDtm", nowStr)
                    .queryParam("potalId", mcashUserDto.getUseridEnc())
                    .queryParam("svcContId", mcashUserDto.getSvcCntrNoEnc())
                    .build()
                    .encode()
                    .toUriString();
        } catch (Exception e) {
            url = "";
        }

        return url;
    }

    @Override
    public McashUserDto getMcashUserByUserid(String userid) {
        return mcashDao.getMcashUserByUserid(userid);
    }

    /**
     * @return
     * @Description : M쇼핑할인 가입 연동
     * @Create Date : 2024.07.31
     */
    @Override
    public Map<String, String> joinMcashUser(String svcCntrNo) {
        Map<String, String> resultMap = new HashMap<>();

        // 1. 가입할 유저 정보 조회
        McashUserDto mcashUserToBe = mcashDao.getUserInfoBySvcCntrNo(svcCntrNo);
        if( mcashUserToBe == null ) {
            throw new McpCommonJsonException("2001", "회선 정보를 확인할 수 없습니다.");
        }

        // 2. 기존 M쇼핑할인 유저 정보 조회
        McashUserDto mcashUserAsIs = mcashDao.getMcashUserByCustomerId( mcashUserToBe.getCustomerId() );

        // 3. AS-IS, TO-BE 유저 정보로 requestDto 구성
        McashApiReqDto requestDto = this.getJoinRequestDto(mcashUserAsIs, mcashUserToBe);

        // 4. 회원 동기화 연동(가입) 호출
        McashApiResDto responseDto = mcashApiService.syncUserInfo(requestDto);

        if( responseDto != null ){
            if( MCASH_RESPONSE_SUCCESS.equals(responseDto.getRspCode()) ) {
                mcashUserToBe.setEvntType(requestDto.getEvntType());
                mcashUserToBe.setEvntTypeDtl(requestDto.getEvntTypeDtl());
                mcashUserToBe.setStatus(MCASH_USER_STATUS_ACTIVE);

                // 5. 조회 결과 이력 테이블에 INSERT or UPDATE
                this.saveMcashUserInfo(mcashUserToBe);
            }

            resultMap.put("rsltCd", responseDto.getRspCode());
            resultMap.put("rsltMsg", responseDto.getRspMsg());
        }

        return resultMap;
    }

    /**
     * @return
     * @Description : M쇼핑할인 할인 회선 변경 연동
     * @Create Date : 2024.08.07
     */
    @Override
    public Map<String, String> changeMcashUser(String svcCntrNo) {
        Map<String, String> resultMap = new HashMap<>();

        // 1. 변경할 회선 정보 조회
        McashUserDto mcashUserToBe = mcashDao.getUserInfoBySvcCntrNo(svcCntrNo);
        if( mcashUserToBe == null ) {
            throw new McpCommonJsonException("3001", "회선 정보를 확인할 수 없습니다.");
        }

        // 2. 기존 회선 정보 조회
        McashUserDto mcashUserAsIs = mcashDao.getMcashUserByCustomerId( mcashUserToBe.getCustomerId() );

        // 3. AS-IS, TO-BE 유저 정보로 requestDto 구성
        McashApiReqDto requestDto = this.getChangeRequestDto(mcashUserAsIs, mcashUserToBe);

        // 4. 회원 동기화 연동(회선 변경) 호출
        McashApiResDto responseDto = mcashApiService.syncUserInfo(requestDto);

        if( responseDto != null ){
            if( MCASH_RESPONSE_SUCCESS.equals(responseDto.getRspCode()) ) {
                mcashUserToBe.setEvntType(requestDto.getEvntType());
                mcashUserToBe.setEvntTypeDtl(requestDto.getEvntTypeDtl());
                mcashUserToBe.setStatus(MCASH_USER_STATUS_ACTIVE);

                // 5. 조회 결과 이력 테이블에 INSERT or UPDATE
                this.saveMcashUserInfo(mcashUserToBe);
            }

            resultMap.put("rsltCd", responseDto.getRspCode());
            resultMap.put("rsltMsg", responseDto.getRspMsg());
        }

        return resultMap;
    }

    /**
     * @return
     * @Description : M쇼핑할인 회원 탈회 연동
     * @Create Date : 2024.08.07
     */
    @Override
    public Map<String, String> cancelMcashUser(String userid, String evntTypeDtl) {
        Map<String, String> resultMap = new HashMap<>();

        if( !MCASH_EVENT_CANCEL_PORTAL.equals(evntTypeDtl) && !MCASH_EVENT_CANCEL_MCASH.equals(evntTypeDtl) ) {
            throw new McpCommonJsonException("4001", "연동유형상세 값이 올바르지 않습니다.");
        }

        // 1. 회원 정보 조회
        McashUserDto mcashUserDto = mcashDao.getMcashUserByUserid(userid);

        // 2. 유저 정보로 requestDto 구성
        McashApiReqDto requestDto = this.getCancelRequestDto(mcashUserDto);
        requestDto.setEvntTypeDtl(evntTypeDtl);

        // 4. 회원 동기화 연동(회선 변경) 호출
        McashApiResDto responseDto = mcashApiService.syncUserInfo(requestDto);

        if( responseDto != null ){
            if( MCASH_RESPONSE_SUCCESS.equals(responseDto.getRspCode()) ) {
                mcashUserDto.setEvntType(requestDto.getEvntType());
                mcashUserDto.setEvntTypeDtl(requestDto.getEvntTypeDtl());
                mcashUserDto.setStatus(MCASH_USER_STATUS_CANCEL);

                // 5. 조회 결과 이력 테이블에 INSERT or UPDATE
                this.saveMcashUserInfo(mcashUserDto);
            }

            resultMap.put("rsltCd", responseDto.getRspCode());
            resultMap.put("rsltMsg", responseDto.getRspMsg());
        }

        return resultMap;
    }

    /**
     * @return
     * @Description : M쇼핑할인 잔액 조회
     * @Create Date : 2024.08.07
     */
    @Override
    public Map<String, Object> getRemainCash(String userid) {
        Map<String, Object> resultMap = new HashMap<>();

        // 1. userid로 M쇼핑할인 정보 조회
        McashUserDto mcashUserDto = mcashDao.getMcashUserByUserid(userid);
        if( mcashUserDto == null ) {
            resultMap.put("rsltCd", "9999");
            resultMap.put("rsltMsg", "회선 정보를 확인할 수 없습니다.");
            return resultMap;
        }

        // 2. requestDto set.
        McashApiReqDto requestDto = new McashApiReqDto(mcashUserDto);

        // 3. M쇼핑할인 잔액 조회 연동 호출
        McashApiResDto responseDto = mcashApiService.getRemainCash(requestDto);

        if( responseDto != null ) {
            if( MCASH_RESPONSE_SUCCESS.equals(responseDto.getRspCode()) ) {
                resultMap.put("remainCash", responseDto.getAcuCash());
            }

            resultMap.put("rsltCd", responseDto.getRspCode());
            resultMap.put("rsltMsg", responseDto.getRspMsg());
        }

        return resultMap;
    }

    /**
     * @return
     * @Description : M쇼핑할인 회선 체크
     * @Create Date : 2024.08.02
     */
    @Override
    public List<McashUserDto> getMcashAvailableCntrList(String userid) {
        List<McashUserDto> mcashAvailableCntrList = mcashDao.getMcashAvailableCntrList(userid);

        String today = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
        for (McashUserDto mcashUserDto : mcashAvailableCntrList) {
            String birthDt = mcashUserDto.getBirthDt();
            boolean isAdult = false;
            try {
                if ( birthDt.length() != 8 ) {
                    continue;
                }

                int age = NmcpServiceUtils.getBirthDateToAmericanAge(birthDt, today);
                if (age >= 19) {
                    isAdult = true;
                }
            } finally {
                mcashUserDto.setAdult(isAdult);
            }
        }

        return mcashAvailableCntrList;
    }

    /**
     * @return
     * @Description : [임시] M쇼핑할인 이용 권한 확인
     * @Create Date : 2024.08.23
     */
    @Override
    public boolean isMcashAuth() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        UserSessionDto userSession = (UserSessionDto) session.getAttribute(USER_SESSION);

        String isMcashAuth = (String) session.getAttribute(SESSION_MCASH_AUTH);
        if ( isMcashAuth == null || "".equals(isMcashAuth) ) {
            CommCodeInstDTO mcashAuthDto = commCodeDAO.getFstCodeTble(MCASH_AUTH_USER_LIST);
            if ( mcashAuthDto != null && "Y".equals(mcashAuthDto.getStatus()) ) {

                // 비로그인 시
                if ( userSession == null || "".equals(userSession.getUserId()) )
                    return false;

                String userid = userSession.getUserId();
                isMcashAuth = "N";

                NmcpCdDtlDto nmcpCdDtlDto = new NmcpCdDtlDto();
                nmcpCdDtlDto.setCdGroupId(MCASH_AUTH_USER_LIST);
                List<NmcpCdDtlDto> userList = fCommonSvc.getCodeList(nmcpCdDtlDto);

                for ( NmcpCdDtlDto user : userList ) {
                    if ( userid.equals(user.getDtlCd()) ) {
                        isMcashAuth = "Y";
                        break;
                    }
                }
            } else {
                isMcashAuth = "Y";
            }

            session.setAttribute(SESSION_MCASH_AUTH, isMcashAuth);
        }

        return "Y".equals(isMcashAuth);
    }

    @Override
    public McashUserDto getMcashUserBySvcCntrNo(McashUserDto mcashDto) {
        return mcashDao.getMcashUserBySvcCntrNo(mcashDto);
    }

    @Override
    public int getMcashJoinCnt(String userid) {
        return mcashDao.getMcashJoinCnt(userid);
    }

    @Override
    public int getMcashMenuAccessCnt() {
        return mcashDao.getMcashMenuAccessCnt();
    }

    @Override
    public List<McashShopDto> getShopDiscountRateList() {
        List<NmcpCdDtlDto> discountRateCodeList = NmcpServiceUtils.getCodeList(MCASH_DISCOUNT_RATE);

        return discountRateCodeList.stream()
                .map(code -> {
                    try {
                        double discountRate = Double.parseDouble(code.getExpnsnStrVal1());
                        return new McashShopDto(code.getDtlCd(), code.getDtlCdNm(), discountRate);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * M쇼핑할인 가입여부 체크
     * @param userid
     * @return Map<String, String>
     * @Create Date : 2024.06.26
     */
    private Map<String, String> chkMcashJoin(String userid) {

        Map<String, String> rtnMap = new HashMap<>();
        String resultCd = "";

        try {
            // M쇼핑할인 회원정보 조회
            McashUserDto mcashUserDto = this.getMcashUserByUserid(userid);
            if (mcashUserDto == null)
                throw new McpCommonJsonException("0001", "신규가입");

            if ( MCASH_USER_STATUS_CANCEL.equals(mcashUserDto.getStatus()) )
                throw new McpCommonJsonException("0002", "재가입");

            if ( MCASH_USER_STATUS_WAIT.equals(mcashUserDto.getStatus()) )
                throw new McpCommonJsonException("0003", "당일 회선 해지");

            String subStatus = mspService.getMspSubStatus( mcashUserDto.getContractNum() );
            if ( "C".equals(subStatus) )
                throw new McpCommonJsonException("0004", "비정상 회원");

            resultCd = "0000";  // 서비스 정상 이용
        } catch (McpCommonJsonException e) {
            resultCd = e.getRtnCode();
        }

        rtnMap.put("resultCd", resultCd);
        rtnMap.put("resultMsg", "JOIN");
        return rtnMap;
    }

    private McashApiReqDto getJoinRequestDto(McashUserDto mcashUserAsIs, McashUserDto mcashUserToBe) {
        McashApiReqDto requestDto;
        requestDto = new McashApiReqDto(mcashUserToBe);
        requestDto.setPotalId(mcashUserToBe.getUserid());
        requestDto.setSvcContId(mcashUserToBe.getSvcCntrNo());

        // 신규가입
        if( mcashUserAsIs == null ) {
            requestDto.setEvntType(MCASH_EVENT_JOIN);
            requestDto.setEvntTypeDtl(MCASH_EVENT_JOIN_NEW);
            return requestDto;
        }

        String potalId = mcashUserToBe.getUserid();
        String svcContId = mcashUserToBe.getSvcCntrNo();
        String bfPotalId = mcashUserAsIs.getUserid();
        String bfSvcContId = mcashUserAsIs.getSvcCntrNo();

        // 복구
        if( bfPotalId.equals(potalId) && bfSvcContId.equals(svcContId) ) {
            requestDto.setEvntType(MCASH_EVENT_REPAIR);
            requestDto.setEvntTypeDtl(MCASH_EVENT_REPAIR_RPR);
            return requestDto;
        }

        if( !bfPotalId.equals(potalId) ) {
            requestDto.setBfPotalId(bfPotalId);
        }
        if( !bfSvcContId.equals(svcContId) ) {
            requestDto.setBfSvcContId(bfSvcContId);
        }

        // 재가입
        requestDto.setEvntType(MCASH_EVENT_JOIN);
        requestDto.setEvntTypeDtl(MCASH_EVENT_JOIN_RE);

        return requestDto;
    }

    private McashApiReqDto getChangeRequestDto(McashUserDto mcashUserAsIs, McashUserDto mcashUserToBe) {
        McashApiReqDto requestDto;
        requestDto = new McashApiReqDto(mcashUserAsIs);
        requestDto.setPotalId(mcashUserToBe.getUserid());
        requestDto.setSvcContId(mcashUserToBe.getSvcCntrNo());

        requestDto.setEvntType(MCASH_EVENT_CHANGE);
        requestDto.setEvntTypeDtl(MCASH_EVENT_CHANGE_CNTR);
        requestDto.setBfPotalId(mcashUserAsIs.getUserid());
        requestDto.setBfSvcContId(mcashUserAsIs.getSvcCntrNo());

        return requestDto;
    }

    private McashApiReqDto getCancelRequestDto(McashUserDto mcashUserDto) {
        McashApiReqDto requestDto;
        requestDto = new McashApiReqDto(mcashUserDto);
        requestDto.setPotalId(mcashUserDto.getUserid());
        requestDto.setSvcContId(mcashUserDto.getSvcCntrNo());

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String todayStr = sdf.format(today);

        requestDto.setEvntType(MCASH_EVENT_CANCEL);
        requestDto.setWdYn("Y");
        requestDto.setWdDate(todayStr);

        return requestDto;
    }

    private void saveMcashUserInfo(McashUserDto mcashUserDto) {
        McashUserDto orgMcashUser = mcashDao.getMcashUserByUserid( mcashUserDto.getUserid() );
        if( orgMcashUser == null ) {
            mcashDao.insertMcashUserInfo(mcashUserDto);
        } else {
            mcashDao.updateMcashUserInfo(mcashUserDto);
        }
        mcashDao.insertMcashUserHist(mcashUserDto);
    }
}