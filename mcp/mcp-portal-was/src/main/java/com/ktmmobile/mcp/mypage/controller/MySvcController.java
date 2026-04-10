package com.ktmmobile.mcp.mypage.controller;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.appform.service.AppformSvc;
import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpBilEmailChgVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpBilPrintInfoVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarMonBillingInfoDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarMonDetailInfoDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpMonthPayMentDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpMoscSpnsrItgInfoInVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpNumChgeListVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.JehuDto;
import com.ktmmobile.mcp.mypage.dto.McpRetvRststnDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MspJuoAddInfoDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.dto.NmcpProdImgDtlDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.MypageUserService;

/**
 * @project : default
 * @file : ArticleController.java
 * @author : HanNamSik
 * @date : 2013. 4. 13. 오후 6:35:30
 * @history :
 *
 * @comment : 게시물
 *
 *
 *
 */
@Controller
public class MySvcController {

    private static Logger logger = LoggerFactory.getLogger(MySvcController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private MypageUserService mypageUserService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private AppformSvc appform;





    /**
     * 번호목록 조회 ajax
     */
    @RequestMapping(value="/m/mypage/numChgeListAjax.do")
    @ResponseBody
    public JsonReturnDto numChgeListAjax(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            String chkCtn )  {

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 밸생했습니다. 다시 시도해 주세요.";
        Object json = null;
        Map<String,Object> resultMap = new HashMap<String,Object>();
        MpNumChgeListVO item;
        int searchCnt=49;//접속제한횟수 번호 검색최초 1회 클릭으로 50에서 감소한 49를 설정한다.

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("cd", "NUMCH");//번호 목록조회 서비스코드
        map.put("ncn", searchVO.getContractNum());//NCN
        McpRetvRststnDto mcpRetvRststnDto = mypageService.retvRstrtn(map);//번호 목록조회 여부 select

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd", Locale.KOREA );
        Date currentTime = new Date ( );
        String dTime = formatter.format (currentTime);//현재날짜 yyyymmdd

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            returnCode = "21";
            message = "로그인해 주세요.";
        }else if(mcpRetvRststnDto != null && mcpRetvRststnDto.getTmscnt() <= 0){
            returnCode = "33";
            message = "하루에 조회가능 한 횟수를 초과 하셨습니다. \n다음에 다시 조회해 주세요.";
        }else {
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            if( ! this.checkUserType(searchVO, cntrList, userSession) ){
                returnCode = "11";
                message = "정회원 인증 후 이용하실 수 있습니다.";
            } else {
                try {
                        item = mPlatFormService.numChgeList(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), chkCtn);
                        returnCode = "00";
                        message = "";
                        if( item == null ){
                            json = "0";
                        }else{
                            json = item.getList();
                        }

                        if(mcpRetvRststnDto==null){//번호목록 조회결과 null인경우 insert
                            this.mypageService.retvRstrtnInsert(map);
                        }else if(mcpRetvRststnDto.getConnDate().equals(dTime)){//번호목록 조회결과 현재날짜가 접속날짜와 같다면 접속제한횟수 -1 update
                            this.mypageService.retvRstrtnUpCnt(map);
                            mcpRetvRststnDto = mypageService.retvRstrtn(map);//번호 목록조회 여부 select
                            searchCnt = mcpRetvRststnDto.getTmscnt();
                        }else if(!mcpRetvRststnDto.getConnDate().equals(dTime)){//번호목록 조회결과 현재날짜가 접속날짜와 같지 않다면 현재날짜로 update 접속제한횟수초기화(50)
                            this.mypageService.retvRstrtnUpSysDate(map);
                        }
                        resultMap.put("searchCnt", ""+searchCnt);

                } catch (SelfServiceException e) {
                    returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
                    message = getErrMsg(e.getMessage());
                } catch (SocketTimeoutException e){
                    returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
                    message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
                }

            }
        }
        //조회시 로그남김
        //주석 처리 20160421 인터셉터 전체 셋팅(21일현재 주석)
        //ipstatisticService.insertIpStat(request);
        result.setResultMap(resultMap);

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(json);
        return result;
    }

    /**
     * 번호변경 ajax
     */
    @RequestMapping(value="/m/mypage/numChgeChgAjax.do")
    @ResponseBody
    public JsonReturnDto numChgeChgAjax(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            String resvHkCtn, String resvHkSCtn, String resvHkMarketGubun )  {

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 밸생했습니다. 다시 시도해 주세요.";
        Object json = null;

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/m/mypage/numChgeChgAjax.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            message = "동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.";
            result.setReturnCode(returnCode);
            result.setMessage(message);
            return result;
        }


        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            returnCode = "21";
            message = "로그인해 주세요.";
        } else {
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            if( ! this.checkUserType(searchVO, cntrList, userSession) ){
                returnCode = "11";
                message = "정회원 인증 후 이용하실 수 있습니다.";
            } else {
                try {
                    mPlatFormService.numChgeChg(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), resvHkCtn, resvHkSCtn, resvHkMarketGubun);
                    returnCode = "00";
                    message = "";
                } catch (SelfServiceException e) {
                    returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
                    message = getErrMsg(e.getMessage());
                } catch (SocketTimeoutException e){
                    returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
                    message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
                }

            }
        }

        //조회시 로그남김
        //주석 처리 20160421 인터셉터 전체 셋팅(21일현재 주석)
        //ipstatisticService.insertIpStat(request);

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(json);

        return result;
    }


    @Deprecated
    private ResponseSuccessDto getMessageBox(){
        ResponseSuccessDto mbox = new ResponseSuccessDto();
        mbox.setRedirectUrl("/m/mypage/updateForm.do");
        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");

        return mbox;
    }

    private boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList, UserSessionDto userSession){
        if( ! StringUtil.equals(userSession.getUserDivision(), "01") ){
            return false;
        }

		if(cntrList == null) {
        	return false;
        }

        if(cntrList.size() <= 0){
            return false;
        }

        if( StringUtil.isEmpty(searchVO.getNcn()) ){
            searchVO.setNcn(cntrList.get(0).getSvcCntrNo() );
            searchVO.setCtn(cntrList.get(0).getCntrMobileNo() );
            searchVO.setCustId(cntrList.get(0).getCustId());
            searchVO.setModelName(cntrList.get(0).getModelName());
            searchVO.setContractNum(cntrList.get(0).getContractNum());
        }

        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            String ctn = mcpUserCntrMngDto.getCntrMobileNo();
            String ncn = mcpUserCntrMngDto.getSvcCntrNo();
            String custId = mcpUserCntrMngDto.getCustId();
            String modelName = mcpUserCntrMngDto.getModelName();
            String contractNum = mcpUserCntrMngDto.getContractNum();

            mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
            mcpUserCntrMngDto.setSvcCntrNo(ncn);
            mcpUserCntrMngDto.setCustId(custId);
            mcpUserCntrMngDto.setModelName(modelName);
            mcpUserCntrMngDto.setContractNum(contractNum);
            if(StringUtil.equals(searchVO.getNcn(), String.valueOf(mcpUserCntrMngDto.getSvcCntrNo()))){
                searchVO.setNcn( ncn );
                searchVO.setCtn( ctn );
                searchVO.setCustId(custId);
                searchVO.setModelName(modelName);
                searchVO.setContractNum(contractNum);
            }
        }

        return true;
    }


    private String getErrCd(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        result = arg[0]; //N:성공, S:시스템에서, E:Biz 에러
        return result;
    }

    private String getErrMsg(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        if(arg.length > 1) {
            result = arg[1];
        } else {
            result = arg[0];
        }
        return result;
    }

}
