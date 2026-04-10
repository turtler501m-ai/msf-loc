package com.ktmmobile.mcp.event.controller;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.event.dto.AgreeTermsEventDto;
import com.ktmmobile.mcp.event.dto.EventJoinDto;
import com.ktmmobile.mcp.event.service.AgreeTermsEventService;
import com.ktmmobile.mcp.event.service.EventJoinService;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.MypageUserService;

@Controller
public class EventJoinController {

    private static Logger logger = LoggerFactory.getLogger(EventJoinController.class);

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private FormDtlSvc formDtlSvc;

    @Autowired
    private MypageUserService mypageUserService;

    @Autowired
    private MypageService mypageService;


    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private EventJoinService eventJoinService;

    @Autowired
    private AgreeTermsEventService agreeTermsEventService;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private MaskingSvc maskingSvc;

    /**
     * 설명 : 이벤트 참여 화면 로딩
     */
    @RequestMapping(value = { "/event/eventJoin.do", "/m/event/eventJoin.do" })
    public String requestReView(@ModelAttribute EventJoinDto eventJoinDto, PageInfoBean pageInfoBean,
            Model model, HttpServletRequest request) {

        String returnUrl = "portal/event/eventJoin";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "mobile/event/eventJoin";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        if(Optional.ofNullable(userSession).isPresent() && Optional.ofNullable(userSession.getUserId()).isPresent()){

            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

            model.addAttribute("cntrList", cntrList);
            model.addAttribute("userId", userSession.getUserId());
            model.addAttribute("mobileNo", userSession.getMobileNo());
        }

        // 이벤트 코드
        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.EVENT_JOIN_CD);
        List<NmcpCdDtlDto> nmcpMainCdDtlDtoList = fCommonSvc.getCodeList(nmcpMainCdDtlDto);
        int eventTotal = 0;
        if(nmcpMainCdDtlDtoList !=null && !nmcpMainCdDtlDtoList.isEmpty()){
            for(NmcpCdDtlDto dto : nmcpMainCdDtlDtoList){
                //if("Y".equals(dto.getExpnsnStrVal1())) {
                    eventJoinDto.setPromotionCode(dto.getDtlCd());
                    int cnt = eventJoinService.selectEventJoinTotalCnt(eventJoinDto);
                    eventTotal += cnt;
                    dto.setEventCdCtn(cnt);

                    //공유 전용 테이블에 프로모션 코드 없을시 insert
                    List<EventJoinDto> eventShareList = eventJoinService.eventShareList(eventJoinDto);
                    if(eventShareList.isEmpty()) {
                        eventJoinService.insertEventShare(eventJoinDto);
                    }
                //}
            }
            eventJoinDto.setPromotionCode(null);
        }

        // 본인인증 세션 확인
        NiceResDto sessNiceRes =  SessionUtils.getNiceBasResCookieBean() ;

        if (sessNiceRes == null) {
             model.addAttribute("successFlag", "N");
        }else {
             model.addAttribute("successFlag", "Y");
        }

        //이벤트 공유하기 링크
        String proMoNum = request.getParameter("proMoNum");
        if(StringUtils.isNotBlank(proMoNum)) {
            model.addAttribute("proMoNum", proMoNum);
            model.addAttribute("proMoYn", "Y");
       }else {
           model.addAttribute("proMoYn", "N");
       }

        FormDtlDTO formDtlDTO = new FormDtlDTO();
        formDtlDTO.setCdGroupId1("INFOPRMT");
        formDtlDTO.setCdGroupId2("INSTRUCTION002");
        FormDtlDTO eventHolder = formDtlSvc.FormDtlView(formDtlDTO);

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");

            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        }

        model.addAttribute("nmcpMainCdDtlDtoList", nmcpMainCdDtlDtoList);
        model.addAttribute("eventHolder", eventHolder);

        return returnUrl;
    }

    /**
     * 설명 : 이벤트참여 리스트 호출Ajax
     */
    @RequestMapping(value = {"/m/event/eventJoinDataAjax.do", "/event/eventJoinDataAjax.do"})
    @ResponseBody
    public Map<String,Object> reviewMAjax(@ModelAttribute EventJoinDto eventJoinDto, @RequestParam("onlyMine") boolean onlyMine, PageInfoBean pageInfoBean,Model model,HttpServletRequest request) {

        HashMap<String,Object> map = new HashMap<String, Object>();
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        // 본인인증 세션 확인
        NiceResDto sessNiceRes =  SessionUtils.getNiceBasResCookieBean() ;

        //로그인 여부 및 정회원 준회원 체크
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())){
            map.put("isLogin", false);

            //비로그인시 세션이 있을 경우
            if (sessNiceRes != null) {
                 if(onlyMine) {
                     eventJoinDto.setDupInfo(sessNiceRes.getDupInfo());
                     eventJoinDto.setMobileNo(sessNiceRes.getsMobileNo());
                 }else {
                     eventJoinDto.setDupInfo("");
                     eventJoinDto.setUserId("");
                     eventJoinDto.setMobileNo("");
                 }
            }

        }else {
                map.put("userId",userSession.getUserId());
                List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
                List<String> conNumList = new ArrayList<String>();
                if(Optional.ofNullable(cntrList).isPresent())                                                                                      {
                    for(int i = 0; i < cntrList.size(); i ++) {
                        conNumList.add(cntrList.get(i).getContractNum());
                    }
                }
                    map.put("isLogin", true);
                    map.put("conNumList", conNumList);
                    if(conNumList != null && !conNumList.isEmpty()) { //정회원
                        eventJoinDto.setUserId(userSession.getUserId());
                    }else { //준회원
                        eventJoinDto.setUserId(userSession.getUserId());
                    }

                if(onlyMine) {
                    eventJoinDto.setDupInfo(eventJoinDto.getDupInfo());
                }else {
                    eventJoinDto.setDupInfo("");
                    eventJoinDto.setUserId("");
                    eventJoinDto.setMobileNo("");
                }
        }


        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        /*한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(request.getParameter("recordCount") == null ? 20 : pageInfoBean.getRecordCount()); //폰상세보기 리뷰에서 10개씩 보여줘야 해서 수정함

        /*카운터 조회*/
        eventJoinDto.setUseYn("Y");
        int total = eventJoinService.selectEventJoinTotalCnt(eventJoinDto);
        pageInfoBean.setTotalCount(total);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

        List<EventJoinDto> eventJoinDtoList = eventJoinService.selectEventJoinList(eventJoinDto,skipResult,maxResult);

        // 이름 마스킹 및 날짜처리
        if(eventJoinDtoList !=null && !eventJoinDtoList.isEmpty()){

            for(EventJoinDto dto : eventJoinDtoList){
                dto.setSysRdt(DateTimeUtil.changeFormat(dto.getSysRdate(),"yyyy.MM.dd"));
                dto.setMkRegNm(dto.getName());
            }
        }


        /*공유 수 조회*/
        int shareTotal = eventJoinService.eventShareTotalCnt(eventJoinDto);

     // 이벤트 코드
        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.EVENT_JOIN_CD);
        List<NmcpCdDtlDto> nmcpMainCdDtlDtoList = fCommonSvc.getCodeList(nmcpMainCdDtlDto);
        int eventTotal = 0;
        if(nmcpMainCdDtlDtoList !=null && !nmcpMainCdDtlDtoList.isEmpty()){
            for(NmcpCdDtlDto dto : nmcpMainCdDtlDtoList){
                //if("Y".equals(dto.getExpnsnStrVal1())) {
                    eventJoinDto.setPromotionCode(dto.getDtlCd());
                    eventJoinDto.setUseYn("Y");
                    int cnt = eventJoinService.selectEventJoinTotalCnt(eventJoinDto);
                    eventTotal += cnt;
                    dto.setEventCdCtn(cnt);
                //}
            }
            eventJoinDto.setPromotionCode(null);
        }
        map.put("eventTotal", eventTotal);
        map.put("eventList", nmcpMainCdDtlDtoList);
        map.put("pageInfoBean", pageInfoBean);
        map.put("total", total);
        map.put("eventJoinDtoList", eventJoinDtoList);
        map.put("shareTotal", shareTotal);

        return map;
    }


    /**
     * 설명 : 이벤트 참여 등록하기 Ajax
     */
    @RequestMapping(value = {"/event/eventJoinRegAjax.do", "/m/event/eventJoinRegAjax.do"})
    @ResponseBody
    public Map<String, Object> eventJoinRegAjax(HttpServletRequest request	,
            @ModelAttribute EventJoinDto eventJoinDto,
            @RequestParam(value = "isLogin", required = false) String isLogin){

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        NiceResDto sessNiceRes =  SessionUtils.getNiceBasResCookieBean() ;

        if (sessNiceRes == null) {
            //TO_DO check .....
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("RESULT_DESC", "본인 인증 정보가 없습니다. ");
            return rtnMap;
        }



        String userId = null;
        String phone = sessNiceRes.getsMobileNo();
        String name = sessNiceRes.getName();
        String userDivision = null;

        // m모바일 회선 조회
        String contractNum = mypageUserService.selectContractNum(name,phone,"","");

        eventJoinDto.setName(name);
        eventJoinDto.setMobileNo(phone);
        eventJoinDto.setDupInfo(sessNiceRes.getDupInfo());

        //m모바일 회선이 있는 경우
        if(contractNum !=null && !contractNum.isEmpty()) {
            // 회원 여부 조회
            EventJoinDto userRegChk = eventJoinService.selectRegUserChk(eventJoinDto);

            if(userRegChk !=null) {
                     userDivision = "01";
                     userId = userRegChk.getUserId();
                     eventJoinDto.setContractNum(contractNum);
            }else {
                        //비회원인경우
                        userDivision = "99";
            }
        }else {
             // 회원 여부 조회
            EventJoinDto userChk = eventJoinService.selectUserChk(eventJoinDto);
            if(userChk !=null) {
                //준회원인경우
                userDivision = "02";
                userId = userChk.getUserId();

            }else {
                //비회원인경우
                userDivision = "99";
            }
        }


        int total = eventJoinService.selectEventJoinTotalCnt(eventJoinDto);
        if(total > 0) {
            rtnMap.put("RESULT_CODE", "0007");
            rtnMap.put("SESSNICE_RES", sessNiceRes);
            return rtnMap;
        }


        // 등록
        EventJoinDto reviewDto = new EventJoinDto();

        String promotionCode = eventJoinDto.getPromotionCode();
        String useTelCode = sessNiceRes.getsMobileCo();
        String reviewContent = eventJoinDto.getReviewContent();
        String clientIp = ipstatisticService.getClientIp();
        String dupInfo = sessNiceRes.getDupInfo();
        String reqSeq = sessNiceRes.getReqSeq();
        String resSeq = sessNiceRes.getResSeq();
        String birthDate = sessNiceRes.getBirthDate();
        String connInfo = sessNiceRes.getConnInfo();

        reviewDto.setPromotionCode(promotionCode);
        reviewDto.setUserDivision(userDivision);
        reviewDto.setContractNum(contractNum);
        reviewDto.setUserId(userId);
        reviewDto.setUseTelCode(useTelCode);
        reviewDto.setReviewContent(ParseHtmlTagUtil.parseTag(filterText(reviewContent)));
        reviewDto.setRip(clientIp);
        reviewDto.setRvisnId(userId);
        reviewDto.setUip(clientIp);
        reviewDto.setName(name);
        reviewDto.setMobileNo(phone);
        reviewDto.setDupInfo(dupInfo);
        reviewDto.setReqSeq(reqSeq);
        reviewDto.setResSeq(resSeq);
        reviewDto.setBirthDate(birthDate);
        reviewDto.setConnInfo(connInfo);

        AgreeTermsEventDto agreeTermsEventDto = new AgreeTermsEventDto();
        agreeTermsEventDto.setCi(connInfo);
        agreeTermsEventDto.setDi(dupInfo);
        agreeTermsEventDto.setUserId(userId);
        agreeTermsEventDto.setEventCode("EventJoinInfo");
        agreeTermsEventDto.setCretId(userId);
        agreeTermsEventDto.setAmdId(userId);
        agreeTermsEventDto.setRip(clientIp);
        agreeTermsEventDto.setAgreeArticle1("Y");
        agreeTermsEventDto.setAgreeArticle2("Y");
        agreeTermsEventDto.setAgreeArticle3("Y");
        agreeTermsEventDto.setAgreeArticle4("Y");

        // insert
        int resultCnt = eventJoinService.eventJoinInsert(reviewDto);

        if (resultCnt == 1) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_DESC", "성공");
            rtnMap.put("SESSNICE_RES", sessNiceRes);
            List<EventJoinDto> result =  eventJoinService.selectEventJoinList(reviewDto, 0, 20);
            if(result != null && result.size() > 0) {
                long eventKey = result.get(0).getReviewPromotionSeq();
                agreeTermsEventDto.setEventKey(eventKey);
                //이벤트 약관동의 insert
                agreeTermsEventService.agreeJoinInsert(agreeTermsEventDto);
            }

        } else {
            rtnMap.put("RESULT_CODE", "0004");
            rtnMap.put("RESULT_DESC", "DB처리 오류");
        }


        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

        return rtnMap;
    }


    /**
     * 설명 : 이벤트 참여 삭제 Ajax
     */
    @RequestMapping(value = {"/event/eventJoinDeleteAjax.do", "/m/event/eventJoinDeleteAjax.do"})
    @ResponseBody
    public Map<String, Object> eventJoinDeleteAjax(@ModelAttribute EventJoinDto eventJoinDto){
        Map<String, Object> rtnMap = new HashMap<String,Object>();

       NiceResDto sessNiceRes =  SessionUtils.getNiceBasResCookieBean() ;

       if(sessNiceRes != null) {
           eventJoinDto.setDupInfo(sessNiceRes.getDupInfo());
       }

       int delVal = eventJoinService.eventJoinDelete(eventJoinDto);

        if (delVal == 1) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_DESC", "성공");
            rtnMap.put("SESSNICE_RES", sessNiceRes);
        } else {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_DESC", "DB처리 오류");
        }

        return rtnMap;
    }


    /**
     * 설명 : 공유하기  Ajax
     */
    @RequestMapping(value = {"/event/eventJoinShareAjax.do", "/m/event/eventJoinShareAjax.do"})
    @ResponseBody
    public Map<String, Object> eventJoinShareAjax(@ModelAttribute EventJoinDto eventJoinDto){
        Map<String, Object> rtnMap = new HashMap<String,Object>();

        int shareVal = 0;

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        //로그인 여부 체크 후 공유 횟수 저장

        if(userSession != null) {
            eventJoinDto.setDupInfo(userSession.getPin());
            List<EventJoinDto> eventShareList = eventJoinService.eventShareList(eventJoinDto);
            if(eventShareList.isEmpty()) {
               shareVal = eventJoinService.insertShareAdd(eventJoinDto);
            }else {
               shareVal = eventJoinService.updateEventShare(eventJoinDto);
            }

        }else {
            shareVal = eventJoinService.updateEventShare(eventJoinDto);
        }

        if (shareVal == 1) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_DESC", "성공");
        } else {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_DESC", "DB처리 오류");
        }

        return rtnMap;
    }

    /**
     * 설명 : 내 게시글 보기(본인인증) Ajax
     */
    @RequestMapping(value = {"/event/eventMyPostAjax.do", "/m/event/eventMyPostAjax.do"})
    @ResponseBody
    public Map<String, Object> eventMyPostAjax(@ModelAttribute EventJoinDto eventJoinDto){
        Map<String, Object> rtnMap = new HashMap<String,Object>();

        NiceResDto sessNiceRes =  SessionUtils.getNiceBasResCookieBean() ;

        if (sessNiceRes == null) {
            //TO_DO check .....
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("RESULT_DESC", "본인 인증 정보가 없습니다. ");
            return rtnMap;
        }else {
             rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
             rtnMap.put("RESULT_DESC", "성공");
             rtnMap.put("SESSNICE_RES", sessNiceRes);
        }

        return rtnMap;
    }


    private String filterText(String sText){

        StringBuffer str = new StringBuffer();
        str.append("10새|10새기|10새리|10세리|10쉐이|10쉑|10스|10쌔| 10쌔기|10쎄|10알|10창|10탱|18것|18넘|18년|18노|18놈|18뇬|18럼|18롬|18새|18새끼|18색|18세끼|18세리|18섹|18쉑|18스|18아|")
        .append("ㄱㅐ|ㄲㅏ|ㄲㅑ|ㄲㅣ|ㅅㅂㄹㅁ|ㅅㅐ|ㅆㅂㄹㅁ|ㅆㅍ|ㅆㅣ|ㅆ앙|ㅍㅏ|凸| 갈보|갈보년|강아지|같은년|같은뇬|개같은|개구라|개년|개놈|개뇬|개대중|개독|개돼중|개랄|개보지|개뻥|개뿔|개새|개새기|개새끼|")
        .append("개새키|개색기|개색끼|개색키|개색히|개섀끼|개세|개세끼|개세이|개소리|개쑈| 개쇳기|개수작|개쉐|개쉐리|개쉐이|개쉑|개쉽|개스끼|개시키|개십새기| 개십새끼|개쐑|개씹|개아들|개자슥|개자지|개접|개좆|개좌식|")
        .append("개허접|걔새|걔수작|걔시끼|걔시키|걔썌|걸레|게색기|게색끼|광뇬|구녕|구라|구멍|그년|그새끼|냄비|놈현|뇬|눈깔|뉘미럴|니귀미|니기미|니미|니미랄|니미럴|니미씹|니아배|니아베|니아비|니어매|니어메|")
        .append("니어미|닝기리|닝기미|대가리|뎡신|도라이|돈놈|돌아이|돌은놈|되질래|뒈져|뒈져라|뒈진|뒈진다|뒈질| 뒤질래|등신|디져라|디진다|디질래|딩시|따식|때놈|또라이|똘아이|똘아이|뙈놈|뙤놈|뙨넘|뙨놈|")
        .append("뚜쟁|띠바|띠발|띠불|띠팔|메친넘|메친놈|미췬| 미췬|미친|미친넘|미친년|미친놈|미친새끼|미친스까이|미틴|미틴넘|미틴년| 미틴놈|바랄년|병자|뱅마|뱅신|벼엉신|병쉰|병신|부랄|부럴|불알|불할|붕가|붙어먹|")
        .append("뷰웅|븅|븅신|빌어먹|빙시|빙신|빠가|빠구리|빠굴|빠큐|뻐큐|뻑큐|뽁큐|상넘이|상놈을|상놈의|상놈이|새갸|새꺄|새끼|새새끼|새키|색끼|생쑈|세갸|세꺄|세끼|섹스|쇼하네|쉐|쉐기|쉐끼|쉐리|쉐에기|쉐키|")
        .append("쉑|쉣|쉨|쉬발|쉬밸|쉬벌|쉬뻘|쉬펄|쉽알|스패킹|스팽|시궁창|시끼|시댕|시뎅|시랄|시발|시벌|시부랄|시부럴|시부리|시불|시브랄|시팍|시팔|시펄|신발끈|심발끈|심탱|십8|십라|십새|십새끼|십세|")
        .append("십쉐|십쉐이|십스키|십쌔|십창|십탱|싶알|싸가지|싹아지|쌉년|쌍넘|쌍년|쌍놈|쌍뇬|쌔끼| 쌕|쌩쑈|쌴년|썅|썅년|썅놈|썡쇼|써벌|썩을년|썩을놈|쎄꺄|쎄엑|쒸벌|쒸뻘|쒸팔|쒸펄|쓰바|쓰박|쓰발|쓰벌|")
        .append("쓰팔|씁새|씁얼|씌파|씨8|씨끼|씨댕|씨뎅|씨바|씨바랄|씨박|씨발|씨방|씨방새|씨방세|씨밸|씨뱅|씨벌|씨벨|씨봉|씨봉알|씨부랄|씨부럴|씨부렁|씨부리|씨불|씨붕|씨브랄| 씨빠|씨빨|씨뽀랄|씨앙|씨파|씨팍|")
        .append("씨팔|씨펄|씸년|씸뇬|씸새끼|씹같|씹년|씹뇬|씹보지|씹새|씹새기|씹새끼|씹새리|씹세|씹쉐|씹스키|씹쌔|씹이|씹자지|씹질|씹창|씹탱|씹퇭|씹팔|씹할|씹헐|아가리|아갈|아갈이|아갈통|아구창|아구통|아굴|얌마|")
        .append("양넘|양년|양놈|엄창|엠병|여물통|염병|엿같|옘병|옘빙|오입|왜년|왜놈|욤병|육갑|은년|을년|이년|이새끼|이새키|이스끼|이스키|임마|자슥|잡것|잡넘|잡년|잡놈|저년|저새끼|접년|젖밥|조까|조까치|조낸|조또|")
        .append("조랭|조빠|조쟁이|조지냐|조진다|조찐|조질래|존나|존나게|존니|존만|존만한|좀물|좁년|좆|좁밥|좃까|좃또|좃만|좃밥|좃이|좃찐|좆같|좆까|좆나|좆또|좆만|좆밥|좆이|좆찐|좇같|좇이|좌식|주글|주글래|주데이|")
        .append("주뎅|주뎅이|주둥아리|주둥이|주접|주접떨|죽고잡|죽을래|죽통|쥐랄|쥐롤|쥬디|지랄|지럴|지롤|지미랄|짜식|짜아식|쪼다|쫍빱|찌랄|창녀|캐년|캐놈|캐스끼|캐스키|캐시키|탱구|팔럼|퍽큐|호로|호로놈|호로새끼|")
        .append("호로색|호로쉑|호로스까이|호로스키|후라들|후래자식|후레|후뢰|씨ㅋ발|ㅆ1발|씌발|띠발|띄발|뛰발|띠ㅋ발|뉘뮈");

        String strString = str.toString();
        Pattern p = Pattern.compile(strString, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sText);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, this.maskWord(m.group()));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String maskWord(String word) {
        StringBuffer buff = new StringBuffer();
        char[] ch = word.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            buff.append("*");
        }
        return buff.toString();
    }


}
