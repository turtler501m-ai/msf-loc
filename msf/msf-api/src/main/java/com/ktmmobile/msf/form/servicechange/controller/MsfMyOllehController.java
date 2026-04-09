//package com.ktmmobile.msf.form.servicechange.controller;
//
//import static com.ktmmobile.msf.common.constants.Constants.AJAX_SUCCESS;
//import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
//import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
//import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.INVALID_REFERER_EXCEPTION;
//import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;
//import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION;
//import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.NUMBER_CHANGE_TIME_EXCEPTION;
//import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
//import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.STEP_CNT_EXCEPTION;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.net.SocketTimeoutException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.client.RestTemplate;
//import com.ktmmobile.msf.common.service.UsimService;
//import com.ktmmobile.msf.form.newchange.service.AppformSvc;
//import com.ktmmobile.msf.form.servicechange.dto.JehuDto;
//import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;
//import com.ktmmobile.msf.form.servicechange.dto.McpRetvRststnDto;
//import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
//import com.ktmmobile.msf.form.servicechange.dto.MspJuoAddInfoDto;
//import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
//import com.ktmmobile.msf.form.servicechange.dto.NmcpProdImgDtlDto;
//import com.ktmmobile.msf.form.servicechange.service.MsfMaskingSvc;
//import com.ktmmobile.msf.form.servicechange.service.MsfMypageSvc;
//import com.ktmmobile.msf.form.servicechange.service.MsfMypageUserService;
//import com.ktmmobile.msf.login.dto.PushSendDto;
//import com.ktmmobile.msf.login.service.AppPushSvc;
//import com.ktmmobile.msf.system.cert.service.CertService;
//import com.ktmmobile.msf.common.dto.AuthSmsDto;
//import com.ktmmobile.msf.common.dto.JsonReturnDto;
//import com.ktmmobile.msf.common.dto.ResponseSuccessDto;
//import com.ktmmobile.msf.common.dto.UserSessionDto;
//import com.ktmmobile.msf.common.dto.db.McpRequestAgrmDto;
//import com.ktmmobile.msf.common.dto.db.NmcpCdDtlDto;
//import com.ktmmobile.msf.common.exception.McpCommonException;
//import com.ktmmobile.msf.common.exception.McpCommonJsonException;
//import com.ktmmobile.msf.common.exception.SelfServiceException;
//import com.ktmmobile.msf.common.mplatform.MsfMplatFormService;
//import com.ktmmobile.msf.common.mplatform.vo.MpAddSvcInfoDto;
//import com.ktmmobile.msf.common.mplatform.vo.MpBilEmailChgVO;
//import com.ktmmobile.msf.common.mplatform.vo.MpBilPrintInfoVO;
//import com.ktmmobile.msf.common.mplatform.vo.MpCommonXmlVO;
//import com.ktmmobile.msf.common.mplatform.vo.MpFarChangewayInfoVO;
//import com.ktmmobile.msf.common.mplatform.vo.MpFarMonBillingInfoDto;
//import com.ktmmobile.msf.common.mplatform.vo.MpFarMonDetailInfoDto;
//import com.ktmmobile.msf.common.mplatform.vo.MpMonthPayMentDto;
//import com.ktmmobile.msf.common.mplatform.vo.MpMoscBilEmailInfoInVO;
//import com.ktmmobile.msf.common.mplatform.vo.MpMoscSdsInfoVo;
//import com.ktmmobile.msf.common.mplatform.vo.MpMoscSdsSvcPreChkVo;
//import com.ktmmobile.msf.common.mplatform.vo.MpMoscSpnsrItgInfoInVO;
//import com.ktmmobile.msf.common.mplatform.vo.MpNumChgeListVO;
//import com.ktmmobile.msf.common.mplatform.vo.MpPerMyktfInfoVO;
//import com.ktmmobile.msf.common.mplatform.vo.MpSuspenPosInfoVO;
//import com.ktmmobile.msf.common.mspservice.MspService;
//import com.ktmmobile.msf.common.mspservice.dto.MspRateMstDto;
//import com.ktmmobile.msf.common.mspservice.dto.MspSaleSubsdMstDto;
//import com.ktmmobile.msf.common.service.FCommonSvc;
//import com.ktmmobile.msf.common.service.IpStatisticService;
//import com.ktmmobile.msf.common.util.DateTimeUtil;
//import com.ktmmobile.msf.common.util.NmcpServiceUtils;
//import com.ktmmobile.msf.common.util.SessionUtils;
//import com.ktmmobile.msf.common.util.StringMakerUtil;
//import com.ktmmobile.msf.common.util.StringUtil;
///**
// * @project : default
// * @file : ArticleController.java
// * @author : HanNamSik
// * @date : 2013. 4. 13. 오후 6:35:30
// * @history :
// *
// * @comment : 게시물
// *
// *
// *
// */
//@Controller
//public class MsfMyOllehController {
//
//    private static Logger logger = LoggerFactory.getLogger(MsfMyOllehController.class);
//
//    @Value("${SERVER_NAME}")
//    private String serverName;
//
//    @Value("${api.interface.server}")
//    private String apiInterfaceServer;
//
//    /**조직 코드  */
//    @Value("${sale.orgnId}")
//    private String orgnId;
//
//    @Autowired
//    private MsfMypageUserService mypageUserService;
//
//    @Autowired
//    private MsfMypageSvc msfMypageSvc;
//
//    @Autowired
//    private FCommonSvc fCommonSvc;
//
//    @Autowired
//    private MsfMplatFormService mPlatFormService;
//
//    @Autowired
//    private AppformSvc appform;
//
////    @Autowired
////    private  MsfPayInfoService payInfo;
//
//    @Autowired
//    private MspService mspService;
//
////    @Autowired
////    ContSvc contSvc;
//
//    @Autowired
//    UsimService usimService ;
//
//    @Autowired
//    IpStatisticService ipstatisticService;
//
//    @Autowired
//    private AppPushSvc appPushSvc;
//
//    @Autowired
//    private CertService certService;
//
//    //번호변경 가능한 시간
//    private static final int starHour = 10 ;
//
//    private static final int endHour = 17 ;
//
//    @Autowired
//    private MsfMaskingSvc maskingSvc;
//
//
//
//    /**
//     * 가입정보조회
//     */
//    @RequestMapping("/mypage/requestView.do")
//    public String requestView(HttpServletRequest request, ModelMap model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO , PageInfoBean pageInfoBean)  {
//
//        //중복요청 체크
//        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
//        checkOverlapDto.setRedirectUrl("/mypage/requestView.do");
//
//        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
//            model.addAttribute("responseSuccessDto", checkOverlapDto);
//            model.addAttribute("MyPageSearchDto", searchVO);
//            return "/common/successRedirect";
//        }
//
//        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            ResponseSuccessDto responseSuccessDto = getMessageBox();
//            model.addAttribute("responseSuccessDto", responseSuccessDto);
//            return "/common/successRedirect";
//        }
//
//        MpMonthPayMentDto monthPay = null;
//
//        try {
//            searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
//            //가입정보 조회
//            MpPerMyktfInfoVO perMyktfInfo = mPlatFormService.perMyktfInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//
//            //현재 납부방법 조회
//            MpFarChangewayInfoVO changeInfo = mPlatFormService.farChangewayInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//            //이메일 청구서 조회
//            //MpBilEmailBillReqInfo emailBillReqInfo = mPlatFormService.bilEmailBillReqInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//            //종이청구서 조회
//            MpBilPrintInfoVO bilPrintInfo = mPlatFormService.bilPrintInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//
//            //명세서 유형 조회
//            MpMoscBilEmailInfoInVO moscBilEmailInfo = mPlatFormService.kosMoscBillInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//
//            //현재 요금제 조회
//            McpUserCntrMngDto mcpUserCntrMngDto = msfMypageSvc.selectSocDesc(searchVO.getContractNum());
//            if(mcpUserCntrMngDto == null) {
//                ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
//                responseSuccessDto.setSuccessMsg("해당 사용자의 요금제 데이터가 없습니다.");
//                responseSuccessDto.setRedirectUrl("/main.do");
//                model.addAttribute("responseSuccessDto", responseSuccessDto);
//                return "/common/successRedirect";
//            }
//
//            if(!perMyktfInfo.isSuccess()
//             ||!changeInfo.isSuccess()
//             //||!emailBillReqInfo.isSuccess()
//             ||!moscBilEmailInfo.isSuccess()
//             ||!bilPrintInfo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
//                   throw new McpCommonException(COMMON_EXCEPTION,"/main.do");
//            }
//
//            //모델 ID 에 대한 이미지 경로 가지고 온다.
//            NmcpProdImgDtlDto nmcpProdImgDtlDto = msfMypageSvc.selectHpImgPath(mcpUserCntrMngDto.getModelId());
//
//            //단말 할부개월, 단말 할부원금 가지고 온다.
//           /* HashMap<String, BigDecimal> modelSaleInfo = (HashMap<String, BigDecimal>)msfMypageSvc.selectModelSaleInfo(searchVO.getContractNum());
//            if(modelSaleInfo != null && modelSaleInfo.get("MODEL_MONTHLY").intValue() > 0) {
//                int tmp = modelSaleInfo.get("MODEL_INSTALLMENT").intValue() / modelSaleInfo.get("MODEL_MONTHLY").intValue();
//                modelSaleInfo.put("MONTH_PAY", new BigDecimal(tmp));
//            }*/
//
//            //이용중인 부가서비스 조회
//            MpAddSvcInfoDto getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//
//            //월이용요금 조회
//            MpFarMonBillingInfoDto billInfo = mPlatFormService.farMonBillingInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), DateTimeUtil.getFormatString("yyyyMM"));
//
//            if(!getAddSvcInfo.isSuccess()
//             ||!billInfo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
//                          throw new McpCommonException(COMMON_EXCEPTION,"/main.do");
//               }
//            model.addAttribute("billInfo", billInfo);
//            if( StringUtil.isNotEmpty(billInfo.getResultMessage()) ){
//                throw new SelfServiceException(billInfo.getResultMessage());
//            }
//
//            List<MpMonthPayMentDto> monthList = billInfo.getMonthList();
//            String billSeqNo = searchVO.getBillSeqNo();//청구일련번호
//
//            //MpFarMonDetailInfoDto detailInfo = null;
//
//            if(monthList != null && monthList.size() > 0){
//                if(StringUtil.isNotNull(billSeqNo)){
//                    for( MpMonthPayMentDto item : monthList ){
//                        if(StringUtil.equals(item.getBillSeqNo(), billSeqNo)){
//                            monthPay = item;
//                            break;
//                        }
//                    }
//                }
//
//                if(monthPay == null){
//                    monthPay = monthList.get(0);
//                }
//            }
//
//            //20160422 제주항공포인트 리스트
//            /*현재 페이지 번호 초기화*/
//            if(pageInfoBean.getPageNo() == 0){
//                pageInfoBean.setPageNo(1);
//            }
//            /*한페이지당 보여줄 리스트 수*/
//            pageInfoBean.setRecordCount(10);
//
//            /*카운터 조회*/
//            //---- API 호출 S ----//
//            RestTemplate restTemplate = new RestTemplate();
//            int total = restTemplate.postForObject(apiInterfaceServer + "/api/mypage/jehuListCount", searchVO.getContractNum(), int.class); // msfMypageSvc.selectJehuListCnt
//            //---- API 호출 E ----//
//            pageInfoBean.setTotalCount(total);
//
//
//            int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();    //셀렉트 하지 않고 뛰어넘을 만큼의 rownum
//            int maxResult = pageInfoBean.getRecordCount();  // Pagesize
//
//
//            List<JehuDto> jehuList=null;
//            if(mcpUserCntrMngDto!=null){
//                if(appform.checkJejuCodeCount(mcpUserCntrMngDto.getSoc())>0){// 제주항공 요금제 확인
//                    //제주항공 요금제 리스트 가져오기
//                    //---- API 호출 S ----//
//                    Map<String, Object> params = new HashMap<String, Object>();
//                    params.put("contractNum", searchVO.getContractNum());
//                    params.put("skipResult", skipResult);
//                    params.put("maxResult", maxResult);
//                    JehuDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/api/mypage/jehuList", params, JehuDto[].class); // msfMypageSvc.selectJehuList
//                    jehuList = Arrays.asList(resultList);
//                    //---- API 호출 E ----//
//                }
//            }
//
//            model.addAttribute("monthPay", monthPay);
//            //model.addAttribute("detailInfo", detailInfo);
//            model.addAttribute("changeInfo", changeInfo);
//            //model.addAttribute("emailBillReqInfo", emailBillReqInfo);
//            model.addAttribute("moscBilEmailInfo", moscBilEmailInfo);
//            model.addAttribute("bilPrintInfo", bilPrintInfo);
//            model.addAttribute("mcpUserCntrMngDto", mcpUserCntrMngDto);
//            //model.addAttribute("modelSaleInfo", modelSaleInfo);
//            model.addAttribute("nmcpProdImgDtlDto", nmcpProdImgDtlDto);
//            model.addAttribute("perMyktfInfo", perMyktfInfo);
//            model.addAttribute("getAddSvcInfo", getAddSvcInfo);
//            model.addAttribute("jehuList", jehuList);
//
//        } catch (SelfServiceException e) {
//            logger.error("========================"+e.getMessage());
//            searchVO.setMessage(getErrMsg(e.getMessage()));
//        } catch (SocketTimeoutException e){
//            searchVO.setMessage(SOCKET_TIMEOUT_EXCEPTION);
//        } catch (Exception e) {
//            logger.error("========================"+e.getMessage());
//            logger.error("========================"+e.toString());
//            searchVO.setMessage("잠시 후 다시 이용해 주세요.");
//        }
//
//        //SRM18062741675_고객포탈 마이페이지 내 위약금 정보 표현 요청 -S-
//        try {
//            //선불 요금제 여부
//            Boolean prePaymentFlag= mypageUserService.selectPrePayment(searchVO.getNcn());
//            if (!prePaymentFlag) { //선불요금제가 아니면 조회
//                //스폰서 조회(X54)
//                MpMoscSpnsrItgInfoInVO moscSpnsrItgInfo = mPlatFormService.kosMoscSpnsrItgInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//                if (StringUtil.isEmpty(moscSpnsrItgInfo.getChageDcAmt())) {
//                    moscSpnsrItgInfo.setChageDcAmt("0");
//                }
//                if (StringUtil.isEmpty(moscSpnsrItgInfo.getTrmnForecBprmsAmt())) {
//                    moscSpnsrItgInfo.setTrmnForecBprmsAmt("0");
//                }
//                if (StringUtil.isEmpty(moscSpnsrItgInfo.getRtrnAmtAndChageDcAmt())) {
//                    moscSpnsrItgInfo.setRtrnAmtAndChageDcAmt("0");
//                }
//                if (StringUtil.isEmpty(moscSpnsrItgInfo.getChageDcAmtSuprtRtrnAmt())) {
//                    moscSpnsrItgInfo.setChageDcAmtSuprtRtrnAmt("0");
//                }
//                MpFarMonDetailInfoDto farMonDetailInfoDto = null;
//                MspJuoAddInfoDto mspJuoAddInfoDto = null;
//
//                if (StringUtil.isNotBlank(moscSpnsrItgInfo.getSaleEngtNm())) {
//                    //요금조회 상세(X16) - 잔여 할부금 조회
//                    if(monthPay != null){
//                        farMonDetailInfoDto = mPlatFormService.farMonDetailInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), monthPay.getBillSeqNo(), monthPay.getBillDueDateList(), monthPay.getBillMonth(), monthPay.getBillStartDate(), monthPay.getBillEndDate());
//                        if (StringUtil.isEmpty(farMonDetailInfoDto.getInstallmentAmt())) {
//                            farMonDetailInfoDto.setInstallmentAmt("0");
//                        }
//                    }
//
//                    //할부원금 조회 MSP_JUO_ADD_INFO@DL_MSP
//                    //---- API 호출 S ----//
//                    RestTemplate restTemplate = new RestTemplate();
//                    mspJuoAddInfoDto = restTemplate.postForObject(apiInterfaceServer + "/api/mypage/mspAddInfo", searchVO.getNcn(), MspJuoAddInfoDto.class); // msfMypageSvc.selectMspAddInfo
//                    //---- API 호출 E ----//
//                }
//                //스폰서 조회
//                model.addAttribute("moscSpnsrItgInfo", moscSpnsrItgInfo);
//                //잔여 할부금 조회
//                model.addAttribute("farMonDetailInfoDto", farMonDetailInfoDto);
//                //할부 원금 조회
//                model.addAttribute("mspJuoAddInfoDto", mspJuoAddInfoDto);
//            }
//        } catch (SelfServiceException e) {
//           logger.error("위약금 조회 오류 SelfServiceException :: " + e.getMessage());
//        } catch (SocketTimeoutException e){
//            searchVO.setMessage(SOCKET_TIMEOUT_EXCEPTION);
//        } catch (Exception e) {
//            logger.error("위약금 조회 오류 :: " + e.getMessage());
//        }
//        //SRM18062741675_고객포탈 마이페이지 내 위약금 정보 표현 요청 -E-
//
//        model.addAttribute("cntrList", cntrList);
//        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
//        model.addAttribute("searchVO", searchVO);
//
//        //조회시 로그남김
//        //주석 처리 20160421 인터셉터 전체 셋팅
//        //ipstatisticService.insertIpStat(request);
//
//        return "mypage/RequestView";
//    }
//
//    /**
//     * 가입정보인쇄
//     */
//    @RequestMapping("/mypage/requestViewPrint.do")
//    public String requestViewPrint(HttpServletRequest request, ModelMap model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {
//        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            ResponseSuccessDto responseSuccessDto = getMessageBox();
//            model.addAttribute("responseSuccessDto", responseSuccessDto);
//            return "/common/successRedirect";
//        }
//        try {
//            searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
//
//            //가입정보 조회
//            MpPerMyktfInfoVO perMyktfInfo = mPlatFormService.perMyktfInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//
//            //현재 요금제 조회
//            McpUserCntrMngDto mcpUserCntrMngDto = msfMypageSvc.selectSocDesc(searchVO.getContractNum());
//            if(mcpUserCntrMngDto == null) {
//                ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
//                responseSuccessDto.setSuccessMsg("해당 사용자의 요금제 데이터가 없습니다.");
//                responseSuccessDto.setRedirectUrl("/main.do");
//                model.addAttribute("responseSuccessDto", responseSuccessDto);
//                return "/common/successRedirect";
//            }
//
//            //단말기정보
//            //---- API 호출 S ----//
//            RestTemplate restTemplate = new RestTemplate();
//            MspJuoAddInfoDto mspJuoAddInfoDto = restTemplate.postForObject(apiInterfaceServer + "/api/mypage/mspAddInfo", searchVO.getNcn(), MspJuoAddInfoDto.class); // msfMypageSvc.selectMspAddInfo
//            //---- API 호출 E ----//
//
//            //이용중인 부가서비스 조회
//            MpAddSvcInfoDto getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//
//            //현재 납부방법 조회
//            MpFarChangewayInfoVO changeInfo = mPlatFormService.farChangewayInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//            //이메일 청구서 조회
//            //MpBilEmailBillReqInfo emailBillReqInfo = mPlatFormService.bilEmailBillReqInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//            //종이청구서 조회
//            MpBilPrintInfoVO bilPrintInfo = mPlatFormService.bilPrintInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//
//            //명세서 유형 조회
//            MpMoscBilEmailInfoInVO moscBilEmailInfo = mPlatFormService.kosMoscBillInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
//
//            if(!perMyktfInfo.isSuccess()
//             ||!getAddSvcInfo.isSuccess()
//             ||!changeInfo.isSuccess()
//             //||!emailBillReqInfo.isSuccess()
//             ||!moscBilEmailInfo.isSuccess()
//             ||!bilPrintInfo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
//                throw new McpCommonException(COMMON_EXCEPTION,"/main.do");
//            }
//            String today = DateTimeUtil.getFormatString("yyyy년 M월 d일");
//
//            model.addAttribute("today", today);
//            model.addAttribute("mcpUserCntrMngDto", mcpUserCntrMngDto);
//            model.addAttribute("mspJuoAddInfoDto", mspJuoAddInfoDto);
//            model.addAttribute("perMyktfInfo", perMyktfInfo);
//            model.addAttribute("getAddSvcInfo", getAddSvcInfo);
//            model.addAttribute("changeInfo", changeInfo);
//            //model.addAttribute("emailBillReqInfo", emailBillReqInfo);
//            model.addAttribute("moscBilEmailInfo", moscBilEmailInfo);
//            model.addAttribute("bilPrintInfo", bilPrintInfo);
//        } catch (SelfServiceException e) {
//            searchVO.setMessage(getErrMsg(e.getMessage()));
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            searchVO.setMessage("잠시 후 다시 이용해 주세요.");
//        }
//        model.addAttribute("cntrList", cntrList);
//        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
//        model.addAttribute("searchVO", searchVO);
//
//        //조회시 로그남김
//        //주석 처리 20160421 인터셉터 전체 셋팅(21일현재 주석)
//        //ipstatisticService.insertIpStat(request);
//
//        return "mypage/RequestViewPrint";
//    }
//
//
//
//    /**
//     * 번호변경 신청/조회 step1
//     */
//    @RequestMapping("/mypage/numberView01.do")
//    public String numberView(HttpServletRequest request, ModelMap model,@ModelAttribute("searchVO") MyPageSearchDto searchVO)  {
//
//        //번호변경 가능한 시간은 평일 오전10시~오후8시까지 가능합니다. (주말 공휴일은 변경불가)
//        int nowHour = DateTimeUtil.getHour();
//        if (nowHour < starHour || nowHour >= endHour ) {
//            throw new McpCommonException(NUMBER_CHANGE_TIME_EXCEPTION);
//        }
//
//        //중복요청 체크
//        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
//        checkOverlapDto.setRedirectUrl("/mypage/numberView01.do");
//
//        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
//            model.addAttribute("responseSuccessDto", checkOverlapDto);
//            model.addAttribute("MyPageSearchDto", searchVO);
//            return "/common/successRedirect";
//        }
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            ResponseSuccessDto responseSuccessDto = getMessageBox();
//            model.addAttribute("responseSuccessDto", responseSuccessDto);
//            return "/common/successRedirect";
//        }
//
//        String ncn = searchVO.getNcn();
//        String custId = searchVO.getCustId();
//        String ctn = searchVO.getCtn();
//        String contractNum = searchVO.getContractNum();
//
//        // 마스킹해제
//        if(SessionUtils.getMaskingSession() > 0 ) {
//            model.addAttribute("maskingSession", "Y");
//
//            MaskingDto maskingDto = new MaskingDto();
//
//            long maskingRelSeq = SessionUtils.getMaskingSession();
//            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
//            maskingDto.setUnmaskingInfo("휴대폰번호");
//            maskingDto.setAccessIp(ipstatisticService.getClientIp());
//            maskingDto.setAccessUrl(request.getRequestURI());
//            maskingDto.setUserId(userSession.getUserId());
//            maskingDto.setCretId(userSession.getUserId());
//            maskingDto.setAmdId(userSession.getUserId());
//            maskingSvc.insertMaskingReleaseHist(maskingDto);
//        }
//
//
//        //개통일로부터 날짜 조회
//        String openingDate= null;
//        if(cntrList!=null) {
//             openingDate= cntrList.get(0).getLstComActvDate();
//
//             //개통일 기준 + N일
//             List<NmcpCdDtlDto> nmcpCdDtlDtos  = NmcpServiceUtils.getCodeList("ChangeNumberException");
//             int moreDay = nmcpCdDtlDtos.stream()
//                                                                                             .filter(dtl -> "moreDay".equals(dtl.getDtlCd()))
//                                                                                             .findFirst()
//                                                                                             .map(NmcpCdDtlDto::getExpnsnStrVal1)
//                                                                                             .filter(str -> str != null && !str.isBlank())
//                                                                                             .map(Integer::parseInt)
//                                                                                             .orElse(0);
//
//             if (moreDay != 0) {
//                 boolean isBlocked = DateTimeUtil.isBlocked(openingDate, moreDay);
//                 if (isBlocked) {
//                      throw new McpCommonException("번호변경은 개통 후 "+moreDay+"일 이후에 가능합니다. ");
//                 }
//             }
//        }
//
//        //선불 요금제 여부 조회
//        boolean prePaymentFlag = mypageUserService.selectPrePayment(contractNum);
//        model.addAttribute("searchVO", searchVO);
//        model.addAttribute("phoneNum", ctn);
//        model.addAttribute("cntrList", cntrList);
//        model.addAttribute("prePaymentFlag", prePaymentFlag);
//        return "/portal/mypage/NumberView01";
//    }
//
//    /**
//     * 번호변경 신청/조회 step2
//     */
//    @RequestMapping("/mypage/numberView02.do")
//    public String numberViewStep2(HttpServletRequest request, ModelMap model,@ModelAttribute("searchVO") MyPageSearchDto searchVO,String mCode)  {
//
//        //번호변경 가능한 시간은 평일 오전10시~오후8시까지 가능합니다. (주말 공휴일은 변경불가)
//        int nowHour = DateTimeUtil.getHour();
//        if (nowHour < starHour || nowHour >= endHour ) {
//            throw new McpCommonException(NUMBER_CHANGE_TIME_EXCEPTION);
//        }
//
//        //중복요청 체크
//        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
//        checkOverlapDto.setRedirectUrl("/mypage/numberView02.do");
//
//        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
//            model.addAttribute("responseSuccessDto", checkOverlapDto);
//            model.addAttribute("MyPageSearchDto", searchVO);
//            return "/common/successRedirect";
//        }
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            ResponseSuccessDto responseSuccessDto = getMessageBox();
//            model.addAttribute("responseSuccessDto", responseSuccessDto);
//            return "/common/successRedirect";
//        }
//
//        String ncn = searchVO.getNcn();
//        String custId = searchVO.getCustId();
//        String ctn = searchVO.getCtn();
//        String contractNum = searchVO.getContractNum();
//
//        // ============ STEP START ============
//        // 계약번호
//        String[] certKey = {"urlType", "contractNum"};
//        String[] certValue = {"chkMemberAuth", contractNum};
//        Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) throw new McpCommonException(vldReslt.get("RESULT_DESC"), "/mypage/numberView01.do");
//        // ============ STEP END ============
//
//        AuthSmsDto authSmsDto = new AuthSmsDto();
//        authSmsDto.setPhoneNum(ctn);
//        authSmsDto.setMenu("phoneNumChange");
//        authSmsDto.setCheck(true);
//        SessionUtils.checkAuthSmsSession(authSmsDto);
//
//        if(!authSmsDto.isResult()) {// 인증안된경우 redirect
//            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
//            responseSuccessDto.setRedirectUrl("/mypage/numberView01.do");
//            responseSuccessDto.setSuccessMsg(authSmsDto.getMessage());
//            model.addAttribute("responseSuccessDto", responseSuccessDto);
//            return "/common/successRedirect";
//        }
//
//        //번호변경이 가능한 수 조회.. ......
//        int searchCnt = 50;
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("cd", "NUMCH");//번호 목록조회 서비스코드
//        map.put("ncn", ncn);//NCN
//        McpRetvRststnDto mcpRetvRststnDto = msfMypageSvc.retvRstrtn(map);//번호 목록조회 여부 select
//
//        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd", Locale.KOREA );
//        Date currentTime = new Date ( );
//        String dTime = formatter.format (currentTime);//현재날짜 yyyymmdd
//
//        if(mcpRetvRststnDto==null){//번호목록 조회결과 null인경우 insert
//            msfMypageSvc.retvRstrtnInsert(map);
//        }else if(!mcpRetvRststnDto.getConnDate().equals(dTime)){//번호목록 조회결과 현재날짜가 접속날짜와 같지 않다면 현재날짜로 update 접속제한횟수초기화(50)
//            msfMypageSvc.retvRstrtnUpSysDate(map);
//        }
//
//        mcpRetvRststnDto = msfMypageSvc.retvRstrtn(map);//번호 목록조회 여부 select
//        searchCnt = mcpRetvRststnDto.getTmscnt();
//
//        model.addAttribute("searchCnt", searchCnt);
//        model.addAttribute("cntrList", cntrList);
//        model.addAttribute("maskCtn", StringMakerUtil.getPhoneNum(ctn));
//        model.addAttribute("ncn", ncn);
//        model.addAttribute("ctn", ctn);
//
//
//        model.addAttribute("searchVO", searchVO);
//        return "/portal/mypage/NumberView02";
//    }
//
////    /**
////     * 번호변경 신청/조회 step3
////     */
////    @RequestMapping("/mypage/numberView03.do")
////    public String numberViewStep3(HttpServletRequest request, ModelMap model,@ModelAttribute("searchVO") MyPageSearchDto searchVO
////            ,String resvHkCtn)  {
////        UserSessionDto userSession = SessionUtils.getUserCookieBean();
////        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())||resvHkCtn==null||StringUtils.isEmpty(resvHkCtn)) return "redirect:/loginForm.do";
////
////        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
////        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
////        if(!chk){
////            ResponseSuccessDto responseSuccessDto = getMessageBox();
////            model.addAttribute("responseSuccessDto", responseSuccessDto);
////            return "/common/successRedirect";
////        }
////
////        String[] nums = StringUtil.getMobileNum(searchVO.getCtn());
////        StringBuffer sb = new StringBuffer();
////        sb.append(nums[0]).append("-").append(nums[1]).append("-").append(nums[2]);
////
////        String[] nums2 = StringUtil.getMobileNum(resvHkCtn);
////        StringBuffer sb2 = new StringBuffer();
////        sb2.append(nums2[0]).append("-").append(nums2[1]).append("-").append(nums2[2]);
////
////        model.addAttribute("phoneVal", sb.toString());
////        model.addAttribute("searchVO", searchVO);
////        model.addAttribute("resvHkCtn", sb2.toString());
////        return "mypage/NumberView03";
////    }
//
//    /**
//     * 일시정지 신청/조회
//     */
//    @RequestMapping("/mypage/stopView01.do")
//    public String stopView01(HttpServletRequest request, ModelMap model)  {
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//        return "mypage/StopView01";
//    }
//
//    /**
//     * 분실신고 신청/해제
//     */
//    @RequestMapping("/mypage/lossView01.do")
//    public String lossView01(HttpServletRequest request, ModelMap model)  {
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//        return "mypage/LossView01";
//    }
//
//    /**
//     * 우편명세서 재발행
//     */
//    @RequestMapping("/mypage/sendReqAddr.do")
//    public @ResponseBody String sendReqAddr(HttpServletRequest request, ModelMap model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            return "false";
//        }
//        String reqMonth = DateTimeUtil.getFormatString("yyyyMM"); //현재달 청구하게 세팅
//        String billDate = reqMonth + DateTimeUtil.getFormatString("yyyyMM");
//        try {
//            mPlatFormService.bilReprintInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), billDate );
//        } catch (SocketTimeoutException e) {
//            return "false";
//        }
//        return "true";
//    }
//
//    /**
//     * 우편명세서 주소변경
//     */
//    @RequestMapping("/mypage/modReqAddr.do")
//    public @ResponseBody String modReqAddr(HttpServletRequest request, ModelMap model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
//            @RequestParam(value="addrZip") String addrZip,
//            @RequestParam(value="adrPrimaryLn") String adrPrimaryLn,
//            @RequestParam(value="adrSecondaryLn") String adrSecondaryLn)  {
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            return "false";
//        }
//        try {
//            mPlatFormService.perAddrChg(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), addrZip, adrPrimaryLn, adrSecondaryLn, "Y");
//        } catch (SocketTimeoutException e) {
//            return "false";
//        }
//
//        return "true";
//    }
//
//    /**
//     * 이메일 명세서 재발행
//     */
//    @RequestMapping("/mypage/sendReqEmail.do")
//    public @ResponseBody String sendReqEmail(HttpServletRequest request, ModelMap model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
//            @RequestParam(value="email") String email)  {
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            return "false";
//        }
//        try {
//            mPlatFormService.bilResendChgChge( searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), DateTimeUtil.getFormatString("yyyy"),
//                DateTimeUtil.changeFormat(DateTimeUtil.addMonths(DateTimeUtil.getFormatString("yyyyMMdd"),-1),"yyyyMMdd","MM"), "Y", "1", email);
//        } catch (SelfServiceException e) {
//            searchVO.setMessage(getErrMsg(e.getMessage()));
//            return "false";
//        } catch (ParseException e) {
//            logger.error(e.toString());
//            return "false";
//        } catch (SocketTimeoutException e){
//            searchVO.setMessage(SOCKET_TIMEOUT_EXCEPTION);
//        }
//      return "true";
//    }
//
//
//    /**
//     * 이메일 명세서 신청/변경/해지 ajax
//     */
//    @RequestMapping("/mypage/bilEmailChgAjax.do")
//    @ResponseBody
//    public JsonReturnDto bilEmailChgAjax(HttpServletRequest request, ModelMap model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
//            @RequestParam(value="email") String email)  {
//
//        JsonReturnDto result = new JsonReturnDto();
//        String returnCode = "99";
//        String message = "오류가 밸생했습니다. 다시 시도해 주세요.";
//        Object json = null;
//        MpBilEmailChgVO item = null;
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
//            returnCode = "21";
//            message = "로그인해 주세요.";
//        } else {
//            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//            boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//            if(!chk){
//                returnCode = "11";
//                message = "정회원 인증 후 이용하실 수 있습니다.";
//            } else {
//                try {
//                    String status = "1";
//                    String securMailYn = "Y";
//                    String ecRcvAgreYn = "N";
//                    String sendGubun = "1";
//                    item = mPlatFormService.bilEmailChg( searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(),
//                            status, email, securMailYn, ecRcvAgreYn, sendGubun);
//                    returnCode = "00";
//                    message = "";
//                } catch (SelfServiceException e) {
//                    logger.error(e.getMessage());
//                    returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
//                    message = getErrMsg(e.getMessage());
//                } catch (SocketTimeoutException e){
//                    returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
//                    message =SOCKET_TIMEOUT_EXCEPTION;
//                }
//            }
//        }
//
//        //조회시 로그남김
//        //주석 처리 20160421 인터셉터 전체 셋팅
//        //ipstatisticService.insertIpStat(request);
//
//        result.setReturnCode(returnCode);
//        result.setMessage(message);
//        result.setResult(json);
//
//        return result;
//    }
//
//
//
//    /**
//     * 일시정지가능여부 조회 ajax
//     */
//    @RequestMapping("/mypage/suspenPosInfoAjax.do")
//    public @ResponseBody JsonReturnDto suspenPosInfoAjax(HttpServletRequest request, ModelMap model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {
//        JsonReturnDto result = new JsonReturnDto();
//        String returnCode = "99";
//        String message = "오류가 밸생했습니다. 다시 시도해 주세요.";
//        Object json = null;
//        MpSuspenPosInfoVO item = null;
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
//            returnCode = "21";
//            message = "로그인해 주세요.";
//        } else {
//            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//            boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//            if(!chk){
//                returnCode = "11";
//                message = "정회원 인증 후 이용하실 수 있습니다.";
//            } else {
//                String stopRsnCd = "";
//                try {
//                    item = mPlatFormService.suspenPosInfo( searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), stopRsnCd);
//                    returnCode = "00";
//                    message = "";
//                    json = item.getMap();
//                } catch (SelfServiceException e) {
//                    logger.error(e.getMessage());
//                    returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
//                    message = getErrMsg(e.getMessage());
//                } catch (SocketTimeoutException e){
//                    returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
//                    message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
//                }
//            }
//        }
//
//        result.setReturnCode(returnCode);
//        result.setMessage(message);
//        result.setResult(json);
//        return result;
//    }
//
//
//    /**
//     * 번호목록 조회 ajax
//     */
//    @RequestMapping(value="/mypage/numChgeListAjax.do")
//    @ResponseBody
//    public Map<String,Object> numChgeListAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, String chkCtn) {
//
//        Map<String,Object> resultMap = new HashMap<String,Object>();
//
//        String returnCode = "99";
//        String message = "오류가 밸생했습니다. 다시 시도해 주세요.";
//
//        MpNumChgeListVO item;
//        int searchCnt=49;//접속제한횟수 번호 검색최초 1회 클릭으로 50에서 감소한 49를 설정한다.
//
//        //중복요청 체크
//        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
//        checkOverlapDto.setRedirectUrl("/mypage/numChgeListAjax.do");
//
//        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
//            resultMap.put("returnCode","33");
//            resultMap.put("message","동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.");
//            return resultMap;
//        }
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
//            resultMap.put("returnCode","21");
//            resultMap.put("message","로그인해 주세요.");
//            return resultMap;
//        }
//
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            resultMap.put("returnCode","11");
//            resultMap.put("message","정회원 인증 후 이용하실 수 있습니다.");
//            return resultMap;
//        }
//
//        String ncn = searchVO.getNcn();
//        String custId = searchVO.getCustId();
//        String ctn = searchVO.getCtn();
//        String contractNum = searchVO.getContractNum();
//
//        AuthSmsDto authSmsDto = new AuthSmsDto();
//        authSmsDto.setPhoneNum(ctn);
//        authSmsDto.setMenu("phoneNumChange");
//        authSmsDto.setCheck(true);
//        SessionUtils.checkAuthSmsSession(authSmsDto);
//
//        if(!authSmsDto.isResult()) {// 인증안된경우 redirect
//            resultMap.put("returnCode","33");
//            resultMap.put("message","인증한 정보가 없습니다.");
//            return resultMap;
//        }
//
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("cd", "NUMCH");//번호 목록조회 서비스코드
//        map.put("ncn", ncn);//NCN
//        McpRetvRststnDto mcpRetvRststnDto = msfMypageSvc.retvRstrtn(map);//번호 목록조회 여부 select
//
//        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd", Locale.KOREA );
//        Date currentTime = new Date();
//        String dTime = formatter.format (currentTime);//현재날짜 yyyymmdd
//
//        if(mcpRetvRststnDto != null && mcpRetvRststnDto.getTmscnt() <= 0){
//            resultMap.put("returnCode","33");
//            resultMap.put("message","하루에 조회가능 한 횟수를 초과 하셨습니다. \\n다음에 다시 조회해 주세요.");
//            return resultMap;
//        }
//
//        MpNumChgeListVO mpNumChgeListVO =  null;
//        List<Map<String, String>> list = new ArrayList<Map<String,String>>();
//        try {
//
//            //번호 변경 목록 조회
//            mpNumChgeListVO = mPlatFormService.numChgeList(ncn, ctn, custId, chkCtn);
//
//            if (mpNumChgeListVO !=null && mpNumChgeListVO.getList().size() > 0) {
//                list = mpNumChgeListVO.getList();
//            }
//
//            if(mcpRetvRststnDto==null){//번호목록 조회결과 null인경우 insert
//                msfMypageSvc.retvRstrtnInsert(map);
//            }else if(mcpRetvRststnDto.getConnDate().equals(dTime)){//번호목록 조회결과 현재날짜가 접속날짜와 같다면 접속제한횟수 -1 update
//                msfMypageSvc.retvRstrtnUpCnt(map);
//                mcpRetvRststnDto = msfMypageSvc.retvRstrtn(map);//번호 목록조회 여부 select
//                searchCnt = mcpRetvRststnDto.getTmscnt();
//            }else if(!mcpRetvRststnDto.getConnDate().equals(dTime)){//번호목록 조회결과 현재날짜가 접속날짜와 같지 않다면 현재날짜로 update 접속제한횟수초기화(50)
//                this.msfMypageSvc.retvRstrtnUpSysDate(map);
//            }
//
//            returnCode = "00";
//            message = "";
//
//        } catch (SelfServiceException e) {
//            logger.error(e.getMessage());
//            returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
//            message = getErrMsg(e.getMessage());
//        }  catch (SocketTimeoutException e){
//            returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
//            message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
//        }
//
//        resultMap.put("returnCode", returnCode);
//        resultMap.put("message", message);
//        resultMap.put("searchCnt", searchCnt);
//        resultMap.put("list", list);
//
//        return resultMap;
//    }
//
//    /**
//     * 번호변경 ajax
//     */
//    @RequestMapping(value="/mypage/numChgeChgAjax.do")
//    @ResponseBody
//    public Map<String,Object> numChgeChgAjax(HttpServletRequest request, ModelMap model,@ModelAttribute("searchVO") MyPageSearchDto searchVO,
//            String resvHkCtn, String resvHkSCtn, String resvHkMarketGubun )  {
//
//        Map<String,Object> resultMap = new HashMap<String,Object>();
//        String returnCode = "99";
//        String message = "오류가 밸생했습니다. 다시 시도해 주세요.";
//        //번호변경 가능한 시간은 평일 오전10시~오후8시까지 가능합니다. (주말 공휴일은 변경불가)
//        int nowHour = DateTimeUtil.getHour();
//        if (nowHour < starHour || nowHour >= endHour ) {
//            resultMap.put("returnCode", returnCode);
//            resultMap.put("message", NUMBER_CHANGE_TIME_EXCEPTION);
//            return resultMap;
//
//        }
//
//        //중복요청 체크
//        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
//        checkOverlapDto.setRedirectUrl("/mypage/numChgeChgAjax.do");
//        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
//            resultMap.put("returnCode",returnCode);
//            resultMap.put("message","동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.");
//            return resultMap;
//        }
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
//            resultMap.put("returnCode",returnCode);
//            resultMap.put("message","로그인해 주세요.");
//            return resultMap;
//        }
//
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            resultMap.put("returnCode",returnCode);
//            resultMap.put("message","정회원 인증 후 이용하실 수 있습니다.");
//            return resultMap;
//        }
//
//        String ncn = searchVO.getNcn();
//        String custId = searchVO.getCustId();
//        String ctn = searchVO.getCtn();
//        String contractNum = searchVO.getContractNum();
//
//        // ============ STEP START ============
//        // 1. 최소 스텝 수 체크
//        if(certService.getStepCnt() < 4 ){
//            resultMap.put("returnCode","STEP01");
//            resultMap.put("message", STEP_CNT_EXCEPTION);
//            return resultMap;
//        }
//
//        // 2. 최종 데이터 체크: step종료 여부, 계약번호, 핸드폰번호
//        String[] certKey = {"urlType", "stepEndYn", "contractNum", "mobileNo"};
//        String[] certValue = {"regNumChg", "Y", contractNum, ctn};
//        Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//            resultMap.put("returnCode","STEP02");
//            resultMap.put("message", vldReslt.get("RESULT_DESC"));
//            return resultMap;
//        }
//        // ============ STEP END ============
//
//        //선불 요금제 변경 신청 불가
//        boolean prePaymentFlag = mypageUserService.selectPrePayment(contractNum);
//        if (prePaymentFlag) {
//            resultMap.put("returnCode",returnCode);
//            resultMap.put("message","선불 요금제 가입자는 번호변경이 불가능 합니다.");
//            return resultMap;
//        }
//
//        try {
//            mPlatFormService.numChgeChg(ncn, ctn, custId, resvHkCtn, resvHkSCtn, resvHkMarketGubun);
//            returnCode = "00";
//            message = "";
//        } catch (SelfServiceException e) {
//            logger.error(e.getMessage());
//            returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
//            message = getErrMsg(e.getMessage());
//        }  catch (SocketTimeoutException e){
//            returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
//            message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
//        }
//        resultMap.put("returnCode",returnCode);
//        resultMap.put("message",message);
//
//        return resultMap;
//    }
//
//    /**
//     * <pre>
//     * 설명     : SMS 발송 처리
//     *           사용자 전화번호로 SMS 인증 문자 전송 처리
//     * @return
//     * </pre>
//     */
//    @RequestMapping(value = "/mypage/sendCertSmsAjax.do")
//    @ResponseBody
//    public JsonReturnDto sendCertSmsAjax(HttpServletRequest request,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO, String mCode )  {
//
//        JsonReturnDto result = new JsonReturnDto();
//        String returnCode = "99";
//        String message = "오류가 발생했습니다. 다시 시도해 주세요.";
//        Object json = null;
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
//            returnCode = "21";
//            message = "로그인해 주세요.";
//        } else {
//            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//            boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//            if(!chk){
//                returnCode = "11";
//                message = "정회원 인증 후 이용하실 수 있습니다.";
//            } else {
//                searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
//
//                AuthSmsDto authSmsDto = new AuthSmsDto();
//                authSmsDto.setPhoneNum(searchVO.getCtn());
//                authSmsDto.setMenu(mCode);
//                boolean check = fCommonSvc.sendAuthSms(authSmsDto);
//
//                if(check) {
//                    returnCode = "00";
//                    if("REAL".equalsIgnoreCase(serverName)) {
//                        message = "";
//                    } else {
//                        message = authSmsDto.getAuthNum();//보안정책에 걸려서 막음
//                    }
//                } else {
//                    returnCode = "41";
//                    message = "인증번호를 발송하지 못했습니다. 다시 시도해 주세요.";
//                }
//            }
//        }
//
//        result.setReturnCode(returnCode);
//        result.setMessage(message);
//        result.setResult(json);
//        return result;
//    }
//
//    /**
//     * <pre>
//     * 설명     : SMS 확인 처리
//     *           SMS 인증 문자 확인 처리
//     * @return
//     * </pre>
//     */
//    @RequestMapping(value = "/mypage/checkCertSmsAjax.do")
//    @ResponseBody
//    public JsonReturnDto checkCertSmsAjax(@ModelAttribute("searchVO") MyPageSearchDto searchVO
//            , String checkValue
//            , String mCode )  {
//        JsonReturnDto result = new JsonReturnDto();
//        String returnCode = "99";
//        String message = "오류가 밸생했습니다. 다시 시도해 주세요.";
//        Object json = null;
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
//            returnCode = "21";
//            message = "로그인해 주세요.";
//        } else {
//            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//            boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//            if(!chk){
//                returnCode = "11";
//                message = "정회원 인증 후 이용하실 수 있습니다.";
//            } else {
//                AuthSmsDto tmp = new AuthSmsDto();
//                tmp.setPhoneNum(searchVO.getCtn());
//                tmp.setAuthNum(checkValue);
//                tmp.setMenu(mCode);
//                SessionUtils.checkAuthSmsSession(tmp);
//
//                if(tmp.isResult()) {
//                    returnCode = "00";
//                    message = "";
//                } else {
//                    returnCode = "41";
//                    message = tmp.getMessage();
//                }
//            }
//        }
//
//        result.setReturnCode(returnCode);
//        result.setMessage(message);
//        result.setResult(json);
//        return result;
//    }
//
//
//    /**
//     * e-mail 청구서 변경(X04)
//     */
//    @RequestMapping("/mypage/modReqEmail.do")
//    public @ResponseBody JsonReturnDto modReqEmail(HttpServletRequest request, ModelMap model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
//            String email,
//            String securMailYn,
//            String ecRcvAgreYn,
//            String billTypeCd,
//            String billAdInfo) {
//
//        String billAdInfoTem = billAdInfo;
//        JsonReturnDto result = new JsonReturnDto();
//        String returnCode = "99";
//        String message = "오류가 밸생했습니다. 다시 시도해 주세요.";
//        Object json = null;
//        String ecRcvAgreYnTmp = ecRcvAgreYn;
//        String securMailYnTmp = securMailYn;
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
//            returnCode = "21";
//            message = "로그인해 주세요.";
//        } else {
//            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//            boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//            if(!chk){
//                returnCode = "11";
//                message = "정회원 인증 후 이용하실 수 있습니다.";
//            } else {
//
//                String status = "9";//1=신청, 9=변경, 0=해지
//                String sendGubun = "1";//1=발송, 2=발송제외, 3=해지
//                securMailYnTmp = "Y";//보안메일
//                ecRcvAgreYnTmp = "N";//이벤트 수신 동의 여부
//
//                //mms명세서 변경일경우 서비스 계약번호로 휴대폰 번호 조회
//                if ("2".equals(billTypeCd)) { //MMS명세서
//                    for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
//                        if (searchVO.getContractNum().equals(mcpUserCntrMngDto.getContractNum())) {
//                            billAdInfoTem = mcpUserCntrMngDto.getCntrMobileNo();
//                            break;
//                        }
//                    }
//                }
//
//                try {
//                    json = mPlatFormService.kosMoscBillChg(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), billTypeCd, status, billAdInfoTem, securMailYnTmp, ecRcvAgreYnTmp, sendGubun);
//                    returnCode = "00";
//                    message = "";
//                }  catch (SelfServiceException e) {
//                    returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
//                    message = getErrMsg(e.getMessage());
//                } catch (SocketTimeoutException e){
//                    returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
//                    message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
//                }
//            }
//        }
//        result.setReturnCode(returnCode);
//        result.setMessage(message);
//        result.setResult(json);
//        return result;
//    }
//
//
//    /**
//     * 우편청구서 변경(X04, X02)
//     */
//    @RequestMapping("/mypage/modReqAddress.do")
//    public @ResponseBody JsonReturnDto modReqAddress(HttpServletRequest request, ModelMap model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
//            String email, String zip, String addr1, String addr2)  {
//        JsonReturnDto result = new JsonReturnDto();
//        String returnCode = "99";
//        String message = "오류가 밸생했습니다. 다시 시도해 주세요.";
//        Object json = null;
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
//            returnCode = "21";
//            message = "로그인해 주세요.";
//        } else {
//            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//            boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//            if(!chk){
//                returnCode = "11";
//                message = "정회원 인증 후 이용하실 수 있습니다.";
//            } else {
//                String status = "0";//1=신청, 9=변경, 0=해지
//                String sendGubun = "3";//1=발송, 2=발송제외, 3=해지
//                String securMailYn = "Y";
//                String ecRcvAgreYn = "Y";
//                if(email != null && !"".equals(email)) {
//                    try {
//                        mPlatFormService.bilEmailChg(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), status, email,
//                            securMailYn, ecRcvAgreYn, sendGubun);
//                        returnCode = "00";
//                        message = "";
//                    } catch (SelfServiceException e) {
//                        returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
//                        message = getErrMsg(e.getMessage());
//                    } catch (SocketTimeoutException e){
//                        returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
//                        message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
//                    }
//                } else {
//                    returnCode = "00";
//                    message = "";
//                }
//
//                if("00".equals(returnCode)) {
//                    String bilCtnDisplay = "Y";
//                    try {
//                        mPlatFormService.perAddrChg(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), zip, addr1, addr2, bilCtnDisplay);
//                    } catch (SelfServiceException e) {
//                        returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
//                        message = getErrMsg(e.getMessage());
//                    } catch (SocketTimeoutException e){
//                        returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
//                        message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
//                    }
//                }
//            }
//        }
//        result.setReturnCode(returnCode);
//        result.setMessage(message);
//        result.setResult(json);
//        return result;
//    }
//
//    private ResponseSuccessDto getMessageBox(){
//        ResponseSuccessDto mbox = new ResponseSuccessDto();
//        mbox.setRedirectUrl("/mypage/updateForm.do");
//        if("Y".equals(NmcpServiceUtils.isMobile())){
//            mbox.setRedirectUrl("/m/mypage/updateForm.do");
//        }
//        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
//        return mbox;
//    }
//
//
//    private String getErrCd(String msg) {
//        String result;
//        String[] arg = msg.split(";;;");
//        result = arg[0]; //N:성공, S:시스템에서, E:Biz 에러
//        return result;
//    }
//
//    private String getErrMsg(String msg) {
//        String result;
//        String[] arg = msg.split(";;;");
//        if(arg.length > 1) {
//            result = arg[1];
//        } else {
//            result = arg[0];
//        }
//        return result;
//    }
//
//    /**
//     * 테스트 세션 설정
//     */
//    @RequestMapping("/getSession.do")
//    public @ResponseBody String getSessionModi(HttpServletRequest request, HttpServletResponse response, ModelMap model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO, String id, String opt)  {
//        if(opt == null) {
//            UserSessionDto userSessionDto = new UserSessionDto();
//            if(id==null || StringUtils.isEmpty(id)) {
//                logger.debug("bluemoor");
//                userSessionDto.setUserId("bluemoor");
//                userSessionDto.setName("장윤정");
//                userSessionDto.setUserDivision("01");
//            } else if(id.equals("madmin")) {
//                logger.debug("madmin");
//                userSessionDto.setUserId("madmin");
//                userSessionDto.setName("madmin");
//                userSessionDto.setUserDivision("00");
//            } else if(id.equals("monster")){
//                logger.debug("monster");
//                userSessionDto.setUserId("monster");
//                userSessionDto.setName("김한묵");
//                userSessionDto.setUserDivision("01");
//            } else if(id.equals("bsjang95")){
//                logger.debug("bsjang95");
//                userSessionDto.setUserId("bsjang95");
//                userSessionDto.setName("장봉순");
//                userSessionDto.setUserDivision("01");
//            } else if(id.equals("pulltest")) {
//                logger.debug("pulltest");
//                userSessionDto.setUserId("pulltest");
//                userSessionDto.setName("김현석");
//                userSessionDto.setUserDivision("01");
//                userSessionDto.setBirthday("19730114");
//            }
//            request.getSession().setAttribute(SessionUtils.USER_SESSION, userSessionDto);
//
//        } else if("DS".equals(opt)) {
//            request.getSession().invalidate();
//        } else if("DA".equals(opt)) {
//            SessionUtils.invalidateSession();
//        } else if("C".equals(opt)) {
//            try {
//                SessionUtils.cookieToSession();
//                if(request.getSession().getAttribute(SessionUtils.USER_SESSION) != null) {
//                    logger.debug(((UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION)).getName());
//                    logger.debug(((UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION)).getUserId());
//                }
//
//            } catch(IllegalArgumentException e) {
//                throw new McpCommonException(COMMON_EXCEPTION);
//            } catch(Exception e) {
//                throw new McpCommonException(COMMON_EXCEPTION);
//            }
//        } else if("S".equals(opt)) {
//            try {
//                SessionUtils.sessionToCookie();
//                if(request.getSession().getAttribute(SessionUtils.USER_SESSION) != null) {
//                    logger.debug(((UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION)).getName());
//                    logger.debug(((UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION)).getUserId());
//                }
//            } catch(IllegalArgumentException e) {
//                throw new McpCommonException(COMMON_EXCEPTION);
//            } catch (Exception e) {
//                throw new McpCommonException(COMMON_EXCEPTION);
//            }
//        } else {
//            if(request.getSession().getAttribute(SessionUtils.USER_SESSION) != null) {
//                logger.debug(((UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION)).getName());
//                logger.debug(((UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION)).getUserId());
//            } else {
//                logger.debug("userSession:"+request.getSession().getAttribute(SessionUtils.USER_SESSION));
//            }
//        }
//
//        return "true";
//    }
//
//
//
//    @RequestMapping(value = {"/mypage/suspenCnlComplete.do", "/m/mypage/suspenCnlComplete.do"}  )
//    public String suspenCnlComplete( ModelMap model
//            , String custNcn
//            , String subStatusDate
//            , String sndarvStatCd
//            ,@PathVariable("proModule") String proModule ){
//
//        String rtnString = "/mypage/suspenCnlComplete" ;
//        String errString = "/mypage/suspendView01.do" ;
//        if("Y".equals(NmcpServiceUtils.isMobile())){
//            rtnString= "/mobile/mypage/suspenCnlComplete"  ;
//            errString = "/m/mypage/suspendView01.do" ;
//        }
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//
//        //정회원
//        if(cntrList.size() <= 0){
//            throw new McpCommonException(F_BIND_EXCEPTION);
//        }
//
//        if (custNcn.equals("")) {
//            return "redirect:"+errString;
//        }
//
//        String custCtn = "";
//        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
//            if (custNcn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
//                custCtn = mcpUserCntrMngDto.getCntrMobileNo() ;
//                break;
//            }
//        }
//
//        model.addAttribute("custNcn", custNcn);
//        model.addAttribute("userName", userSession.getName());
//        model.addAttribute("mobileFull", StringUtil.getMobileFullNum(custCtn));
//        model.addAttribute("subStatusDate", subStatusDate);
//        model.addAttribute("sndarvStatCd", sndarvStatCd);
//        return rtnString;
//    }
//
//    /**
//     * 설명 : 요금할인 재약정 신청
//     * @author key
//     * @Date 2021.12.30
//     * @param model
//     * @param request
//     * @param searchVO
//     * @return
//     */
//    @RequestMapping(value = {"/mypage/reSpnsrPlcyDc.do", "/m/mypage/reSpnsrPlcyDc.do"}  )
//    public String reSpnsrPlcyDc(ModelMap model , HttpServletRequest request
//            , @ModelAttribute("searchVO") MyPageSearchDto searchVO){
//
//
//        String jspPageName = "/portal/mypage/reSpnsrPlcyDc.form";
//        String thisPageName ="/mypage/reSpnsrPlcyDc.do";
//        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
//            jspPageName = "/mobile/mypage/reSpnsrPlcyDc.form";
//            thisPageName ="/m/mypage/reSpnsrPlcyDc.do";
//        }
//
//        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();  //중복요청 체크
//        checkOverlapDto.setRedirectUrl(thisPageName);
//
//        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
//            model.addAttribute("responseSuccessDto", checkOverlapDto);
//            model.addAttribute("MyPageSearchDto", searchVO);
//            return "/common/successRedirect";
//        }
//
//
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            ResponseSuccessDto responseSuccessDto = getMessageBox();
//            model.addAttribute("responseSuccessDto", responseSuccessDto);
//            return "/common/successRedirect";
//        }
//
//       //현재 요금제 조회
//        McpUserCntrMngDto mcpUserCntrMngDto = msfMypageSvc.selectSocDesc(searchVO.getContractNum());
//
//
//        model.addAttribute("phoneNum", searchVO.getCtn());
//
//        // 마스킹해제
//        if(SessionUtils.getMaskingSession() > 0 ) {
//            model.addAttribute("maskingSession", "Y");
//            searchVO.setUserName(userSession.getName());
//            String[] nums = StringUtil.getMobileNum(searchVO.getCtn());
//            String telNo = nums[0]+"-"+nums[1]+"-"+nums[2];
//            searchVO.setCtn(telNo);
//            MaskingDto maskingDto = new MaskingDto();
//            long maskingRelSeq = SessionUtils.getMaskingSession();
//            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
//            maskingDto.setUnmaskingInfo("이름,휴대폰번호");
//            maskingDto.setAccessIp(ipstatisticService.getClientIp());
//            maskingDto.setAccessUrl(request.getRequestURI());
//            maskingDto.setUserId(userSession.getUserId());
//            maskingDto.setCretId(userSession.getUserId());
//            maskingDto.setAmdId(userSession.getUserId());
//            maskingSvc.insertMaskingReleaseHist(maskingDto);
//
//
//        }else {
//            searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
//            searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
//        }
//
//        model.addAttribute("mcpUserCntrMngDto", mcpUserCntrMngDto);
//        model.addAttribute("searchVO", searchVO);
//        model.addAttribute("cntrList", cntrList);
//
//        return jspPageName;
//    }
//
//    /**
//     * 설명 : 요금할인 재약정 신청 완료
//     * @author key
//     * @Date 2021.12.30
//     * @param model
//     * @param searchVO
//     * @param engtPerd
//     * @return
//     */
//    @RequestMapping(value = {"/mypage/reSpnsrPlcyDcComplete.do", "/m/mypage/reSpnsrPlcyDcComplete.do"}  )
//    public String reSpnsrPlcyDcComplete(
//            ModelMap model
//            , @ModelAttribute("searchVO") MyPageSearchDto searchVO
//            , String engtPerd
//    ){
//
//        if (searchVO.getNcn() ==null || StringUtils.isEmpty(searchVO.getNcn())) {
//            throw new McpCommonException(INVALID_REFERER_EXCEPTION);
//        }
//
//        String jspPageName = "/portal/mypage/reSpnsrPlcyDc.complete";
//        String thisPageName ="/mypage/reSpnsrPlcyDcComplete.do";
//        if("Y".equals(NmcpServiceUtils.isMobile())){
//            jspPageName = "/mobile/mypage/reSpnsrPlcyDc.complete";
//            thisPageName ="/m/mypage/reSpnsrPlcyDcComplete.do";
//        }
//
//        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();  //중복요청 체크
//        checkOverlapDto.setRedirectUrl(thisPageName);
//
//        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
//            model.addAttribute("responseSuccessDto", checkOverlapDto);
//            model.addAttribute("MyPageSearchDto", searchVO);
//            return "/common/successRedirect";
//        }
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            ResponseSuccessDto responseSuccessDto = getMessageBox();
//            model.addAttribute("responseSuccessDto", responseSuccessDto);
//            return "/common/successRedirect";
//        }
//
//       //현재 요금제 조회
//        McpUserCntrMngDto mcpUserCntrMngDto = msfMypageSvc.selectSocDesc(searchVO.getContractNum());
//
//        try {
//            //X62. 심플할인 정보조회(X62)
//            MpMoscSdsInfoVo moscSdsInfo = mPlatFormService.moscSdsInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId()) ;
//
//            BigDecimal dcSuprtAmt    = new BigDecimal(moscSdsInfo.getDcSuprtAmt() + "");  //월 할인 금액
//            BigDecimal addRate    = new BigDecimal("1.1");              //부가가치세율
//            int dcSuprtAmtVat = dcSuprtAmt.multiply(addRate).setScale(0, RoundingMode.UP).intValue();
//
//            model.addAttribute("moscSdsInfo", moscSdsInfo);
//            model.addAttribute("dcSuprtAmtVat", dcSuprtAmtVat);
//
//        } catch (SelfServiceException e) {
//            logger.error("위약금 조회 오류 SelfServiceException :: " + e.getMessage());
//        } catch (SocketTimeoutException e){
//             searchVO.setMessage(SOCKET_TIMEOUT_EXCEPTION);
//        } catch (Exception e) {
//            logger.error("위약금 조회 오류 :: " + e.getMessage());
//        }
//
//        searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
//        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
//
//        //재약정 PUSH START
//        PushSendDto pushSendDto = new PushSendDto();
//        pushSendDto.setPushSndSetSno("20220101000000000001");
//        pushSendDto.setUserId(userSession.getUserId());
//        String arg[] = {userSession.getName(),mcpUserCntrMngDto.getRateNm()};
//        pushSendDto.setMsgArr(arg);
//        //재약정 PUSH END
//
//        appPushSvc.immediatelyPushSend(pushSendDto);
//
//        model.addAttribute("mcpUserCntrMngDto", mcpUserCntrMngDto);
//        model.addAttribute("cntrList", cntrList);
//        model.addAttribute("searchVO", searchVO);
//        model.addAttribute("cntrList", cntrList);
//        model.addAttribute("engtPerd", engtPerd);
//        return jspPageName;
//    }
//
//    /**
//     * 설명 : 심플할인 사전체크(X59)
//     * @author key
//     * @Date 2021.12.30
//     * @param myPageSearchDto
//     * @return
//     */
//    @RequestMapping(value="/mypage/moscSdsSvcPreChkAjax.do")
//    @ResponseBody
//    public Map<String, Object> moscSdsSvcPreChk(@ModelAttribute("searchVO") MyPageSearchDto myPageSearchDto) {
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
//            throw new McpCommonJsonException("0099" ,NO_FRONT_SESSION_EXCEPTION);
//        }
//
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(myPageSearchDto, cntrList, userSession);
//        if(!chk){
//           throw new McpCommonJsonException("0098" ,NOT_FULL_MEMBER_EXCEPTION);
//        }
//
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//        //중복요청 체크
//        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
//        checkOverlapDto.setRedirectUrl("/mypage/moscSdsSvcPreChkAjax.do");
//
//        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
//            rtnMap.put("RESULT_CODE", "00001");
//            rtnMap.put("RESULT_MSG", "동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.");
//            return rtnMap;
//        }
//
//       try {
//           MpMoscSdsSvcPreChkVo moscSdsSvcPreChkVo = mPlatFormService.moscSdsSvcPreChk(myPageSearchDto.getNcn(), myPageSearchDto.getCtn(), myPageSearchDto.getCustId()) ;
//           rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//           rtnMap.put("GLOBAL_NO", moscSdsSvcPreChkVo.getGlobalNo());
//           rtnMap.put("SBSC_YN", moscSdsSvcPreChkVo.getSbscYn());
//           rtnMap.put("RESULT_MSG", moscSdsSvcPreChkVo.getResltMsg());
//
//           // ============ STEP START ============
//           if("Y".equals(moscSdsSvcPreChkVo.getSbscYn())){
//               // 계약번호, 전화번호
//               String[] certKey = {"urlType", "contractNum", "mobileNo"};
//               String[] certValue = {"chkPreSimple", myPageSearchDto.getContractNum(), myPageSearchDto.getCtn()};
//               certService.vdlCertInfo("C", certKey, certValue);
//           }
//           // ============ STEP END ============
//
//           return rtnMap;
//       } catch (SelfServiceException e) {
//           rtnMap.put("RESULT_CODE", "00002");
//           rtnMap.put("RESULT_MSG", getErrMsg(e.getMessage()));
//           rtnMap.put("RESULT_ERR_CD", getErrCd(e.getMessage()));
//           return rtnMap;
//       } catch (SocketTimeoutException e) {
//           throw new McpCommonJsonException("0095" ,SOCKET_TIMEOUT_EXCEPTION);
//       }
//    }
//
//    /**
//     * 설명 : 심플할인 가입(X60)
//     * @author key
//     * @Date 2021.12.30
//     * @param myPageSearchDto
//     * @param mcpRequestAgrm
//     * @param engtPerd
//     * @param mCode
//     * @return
//     */
//    @RequestMapping(value="/mypage/moscSdsSvcChgAjax.do")
//    @ResponseBody
//    public Map<String, Object> moscSdsSvcChgAjax(
//            @ModelAttribute("searchVO") MyPageSearchDto myPageSearchDto
//            ,McpRequestAgrmDto mcpRequestAgrm
//            , String engtPerd
//            , String mCode ) {
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
//            throw new McpCommonJsonException("0099" ,NO_FRONT_SESSION_EXCEPTION);
//        }
//
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(myPageSearchDto, cntrList, userSession);
//        if(!chk){
//           throw new McpCommonJsonException("0098" ,NOT_FULL_MEMBER_EXCEPTION);
//        }
//
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//
//        AuthSmsDto authSmsDto = new AuthSmsDto();
//        authSmsDto.setPhoneNum(myPageSearchDto.getCtn());
//        authSmsDto.setMenu(mCode);
//        authSmsDto.setCheck(true);
//        SessionUtils.checkAuthSmsSession(authSmsDto);
//
//        if(!authSmsDto.isResult()) {// 인증안된경우 redirect
//            rtnMap.put("RESULT_CODE", "0002");
//            rtnMap.put("RESULT_MSG", "휴대폰 인증 정보가 없습니다. ");
//            return rtnMap;
//        }
//
//        //중복요청 체크
//        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
//        checkOverlapDto.setRedirectUrl("/mypage/moscSdsSvcChgAjax.do");
//
//        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
//            rtnMap.put("RESULT_CODE", "0001");
//            rtnMap.put("RESULT_MSG", "동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.");
//            return rtnMap;
//        }
//
//        // ============ STEP START ============
//        // 1. 최소 스텝 수 체크
//        if(certService.getStepCnt() < 5 ){
//            throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
//        }
//
//        // 2. 최종 데이터 체크: step종료 여부, 계약번호, 핸드폰번호
//        String[] certKey = {"urlType", "stepEndYn", "contractNum", "mobileNo"};
//        String[] certValue = {"saveSimpleForm", "Y", myPageSearchDto.getContractNum(), myPageSearchDto.getCtn()};
//        Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))){
//            throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
//        }
//        // ============ STEP END ============
//
//       try {
//           MpCommonXmlVO commonXmlVO = mPlatFormService.moscSdsSvcPreChk(myPageSearchDto.getNcn(), myPageSearchDto.getCtn(), myPageSearchDto.getCustId(),engtPerd) ;
//           mcpRequestAgrm.setContractNum(myPageSearchDto.getNcn());
//           mcpRequestAgrm.setGlobalNo(commonXmlVO.getGlobalNo());
//
//           mcpRequestAgrm.setOrderType("01");
//           if(msfMypageSvc.insertMcpRequestArm(mcpRequestAgrm)) {
//               rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//               rtnMap.put("RESULT_MSG", commonXmlVO.getResultCode());
//           } else {
//               //재약정 취소 처리
//               MpCommonXmlVO commonXmlVO2 = mPlatFormService.moscSdsSvcCanChg(myPageSearchDto.getNcn(), myPageSearchDto.getCtn(), myPageSearchDto.getCustId()) ;
//               rtnMap.put("RESULT_CODE", "0003");
//               rtnMap.put("RESULT_MSG", "실패 하였습니다. 다시 시도 하시기 바랍니다. ");
//               rtnMap.put("RESULT_MSG2", commonXmlVO2.getResultCode());
//           }
//
//           return rtnMap;
//       } catch (SelfServiceException e) {
//           throw new McpCommonJsonException("0096" ,e.getMessage());
//       } catch (SocketTimeoutException e) {
//           throw new McpCommonJsonException("0095" ,SOCKET_TIMEOUT_EXCEPTION);
//       }
//    }
//
//    /**
//     * 설명 : 심플할인 금액 조회
//     * @author key
//     * @Date 2021.12.30
//     * @param myPageSearchDto
//     * @param rateSoc
//     * @param engtPerd
//     * @return
//     */
//    @RequestMapping(value="/mypage/getReSpnsrPriceInfoAjax.do")
//    @ResponseBody
//    public Map<String, Object> getReSpnsrPriceInfoAjax(@ModelAttribute("searchVO") MyPageSearchDto myPageSearchDto
//            , String rateSoc
//            , String engtPerd
//             ) {
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
//            throw new McpCommonJsonException("0099" ,NO_FRONT_SESSION_EXCEPTION);
//        }
//
//        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//        boolean chk = msfMypageSvc.checkUserType(myPageSearchDto, cntrList, userSession);
//        if(!chk){
//           throw new McpCommonJsonException("0098" ,NOT_FULL_MEMBER_EXCEPTION);
//        }
//
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//
//        //중복요청 체크
//        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
//        checkOverlapDto.setRedirectUrl("/mypage/getReSpnsrPriceInfoAjax.do");
//
//        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
//            rtnMap.put("RESULT_CODE", "0001");
//            rtnMap.put("RESULT_MSG", "동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.");
//            return rtnMap;
//        }
//
//       //현재 요금제 조회
//        McpUserCntrMngDto mcpUserCntrMngDto = msfMypageSvc.selectSocDesc(myPageSearchDto.getContractNum());
//        if(mcpUserCntrMngDto == null) {
//            rtnMap.put("RESULT_CODE", "0002");
//            rtnMap.put("RESULT_MSG", "해당 사용자의 요금제 데이터가 없습니다.");
//            return rtnMap;
//        }
//
//        //요금제 정보 조회
//        MspRateMstDto mspRateMst = mspService.getMspRateMst(rateSoc) ;
//        rtnMap.put("mspRateMst", mspRateMst);
//
//        //할인 금액 확인
//        MspSaleSubsdMstDto mspSaleSubsdMstDto = new MspSaleSubsdMstDto();
//
//        mspSaleSubsdMstDto.setAgrmTrm(engtPerd);//입력받은 할부기간을 약정기간
//        mspSaleSubsdMstDto.setRateCd(rateSoc);//요금제코드
//        List<MspSaleSubsdMstDto> chargeList = mspService.listMspSaleMst(mspSaleSubsdMstDto);
//
//        MspSaleSubsdMstDto saleSubsdMst = null;
//        if (chargeList != null && chargeList.size() > 0 ) {
//           saleSubsdMst = chargeList.get(0);
//           saleSubsdMst.setBaseAmt(mspRateMst.getBaseAmt());
//        }
//
//        if ("LOCAL".equals(serverName) && saleSubsdMst == null ) {
//            saleSubsdMst = new MspSaleSubsdMstDto();
//            saleSubsdMst.setRateCd("KTI3GM035");
//            saleSubsdMst.setRateNm("M 3G 망내 38");
//            saleSubsdMst.setAgrmTrm("12");
//            saleSubsdMst.setDcAmt(9000);
//            saleSubsdMst.setAddDcAmt(5200);
//        }
//
//        rtnMap.put("saleSubsdMst", saleSubsdMst);
//
//       return rtnMap;
//
//    }
//
//   /**
//   * 설명 : 번호변경 신청/조회 step2
//   * @author key
//   * @Date 2021.12.30
//   * @param request
//   * @param model
//   * @param searchVO
//   * @param engtPerd
//   * @return
//   */
//   @RequestMapping(value = {"/mypage/moscSdsSvcRegView.do","/m/mypage/moscSdsSvcRegView.do"})
//   public String moscSdsSvcRegView(HttpServletRequest request, ModelMap model,@ModelAttribute("searchVO") MyPageSearchDto searchVO, String engtPerd)  {
//
//       String returnUrl = "/portal/mypage/moscSdsSvcRegView";
//       if("Y".equals(NmcpServiceUtils.isMobile())){
//            returnUrl="/mobile/mypage/moscSdsSvcRegView";
//       }
//
//       if (engtPerd ==null || StringUtils.isEmpty(engtPerd)) {
//           throw new McpCommonException(INVALID_REFERER_EXCEPTION);
//       }
//
//       //중복요청 체크
//       ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
//       checkOverlapDto.setRedirectUrl("/mypage/moscSdsSvcRegView.do");
//
//       if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
//           model.addAttribute("responseSuccessDto", checkOverlapDto);
//           model.addAttribute("MyPageSearchDto", searchVO);
//           return "/common/successRedirectsuccessRedirect";
//       }
//
//
//       UserSessionDto userSession = SessionUtils.getUserCookieBean();
//       if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
//
//       List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//       boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
//       if(!chk){
//           ResponseSuccessDto responseSuccessDto = getMessageBox();
//           model.addAttribute("responseSuccessDto", responseSuccessDto);
//           return "/common/successRedirect";
//       }
//
//       // ============ STEP START ============
//       // 계약번호, 전화번호
//       String[] certKey = {"urlType", "contractNum", "mobileNo"};
//       String[] certValue = {"chkMemberAuth", searchVO.getContractNum(), searchVO.getCtn()};
//       Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//
//       if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))){
//           String redirectUrl= "/mypage/reSpnsrPlcyDc.do";
//           if("Y".equals(NmcpServiceUtils.isMobile())) redirectUrl= "/m/mypage/reSpnsrPlcyDc.do";
//           throw new McpCommonException(vldReslt.get("RESULT_DESC"), redirectUrl);
//       }
//       // ============ STEP END ============
//
//       List<NmcpCdDtlDto> presentList = NmcpServiceUtils.getCodeList(com.ktmmobile.msf.common.constants.Constants.GROUP_CODE_PRESENT_CODE);
//       model.addAttribute("presentList", presentList);
//       return returnUrl;
//   }
//
//
////    /**
////     * 설명 : 폰 넘버 조회
////     * @author key
////     * @Date 2021.12.30
////     * @param model
////     * @param request
////     * @param contractNum
////     * @param process
////     * @param menuType
////     * @return
////     */
////    @RequestMapping(value = {"/mypage/selPhoneNumAjax.do","/m/mypage/selPhoneNumAjax.do"})
////    @ResponseBody
////    public Map<String, Object> selPhoneNumAjax(
////            Model model,HttpServletRequest request ,@RequestParam(defaultValue = "") String contractNum, @RequestParam(defaultValue = "") String process ,@RequestParam(defaultValue = "") String menuType) {
////
////        Map<String, Object> rtnJsonMap = new HashMap<String, Object>();
////
////        String resultContractNum = "";
////        if(process.equals("add") && !StringUtil.isEmpty(contractNum)){
////            resultContractNum = contractNum;
////            SessionUtils.setCurrPhoneNcn(contractNum);
////        }else {
////            resultContractNum = SessionUtils.getCurrPhoneNcn();
////        }
////
////        rtnJsonMap.put("contractNum", resultContractNum);
////        return rtnJsonMap;
////
////    }
//
//}
