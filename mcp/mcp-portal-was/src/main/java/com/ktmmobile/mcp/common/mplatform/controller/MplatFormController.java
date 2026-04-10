package com.ktmmobile.mcp.common.mplatform.controller;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpMoscRegSvcCanChgInVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpSocVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpSuspenChgVO;
import com.ktmmobile.mcp.common.mspservice.MspService;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.McpFarPriceDto;
import com.ktmmobile.mcp.mypage.dto.SuspenChgTmlDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.MypageUserService;
import com.ktmmobile.mcp.mypage.service.RegSvcService;



/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : MplatFormController.java
 * 날짜     : 2022. 7. 28. 오후 3:50:18
 * 작성자   : papier
 * 설명     : 보안상 이슈...
 * </pre>
 */
@Controller
public class MplatFormController {
    private final Logger log = LoggerFactory.getLogger(MplatFormController.class);

    @Autowired
    private MypageService mypageService;


    @Autowired
    private IpStatisticService ipStatisticService;

    @Autowired
    private RegSvcService regSvcService;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private MspService mspService;

    @Autowired
    private MypageUserService mypageUserService;


    @Value("${juice.url}")
    private String selfcareCall;

    @Value("${osst.url}")
    private String osstCall;



    @RequestMapping("/mypage/X21Chg.do")
    public void x21Chg(HttpServletRequest request, ModelMap model){

        UserSessionDto userSession = SessionUtils.getUserCookieBean();


        if(userSession == null ) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        String isExceptionId =NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION , userSession.getUserId());
        if (isExceptionId == null || !"Y".equals(isExceptionId) ) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        List<SuspenChgTmlDto> suspenChgTmlDtoList = new ArrayList<SuspenChgTmlDto>();

        SuspenChgTmlDto reqDto = new SuspenChgTmlDto();
        reqDto.setEventCd("X21");
        suspenChgTmlDtoList = mypageService.selectSuspenChgTmp(reqDto);

        String returnCode = "99";
        String message = "";
        String ncn = "";
        String gnNo = "";
        SuspenChgTmlDto req = new SuspenChgTmlDto();
        for(SuspenChgTmlDto dto : suspenChgTmlDtoList){
            message = "";
            returnCode = "99";
            gnNo = "";
            req = new SuspenChgTmlDto();
            ncn = dto.getSvcCntrNo();
            String ctn = dto.getCntrMobileNo();
            String custId = dto.getCustId();
            String soc = dto.getExColum1(); // PL202S944
            String ftrNewParam = "";
            try{
                MpRegSvcChgVO mRegsvcchgvo = regSvcService.regSvcChg(ncn, ctn, custId, soc, ftrNewParam);

                gnNo = mRegsvcchgvo.getGlobalNo();
                returnCode = mRegsvcchgvo.getResultCode();
                // update 세팅
                req.setSvcCntrNo(ncn);
                req.setErrcode(returnCode);
                req.setGnNo(gnNo);
                req.setEventCd("X21");
                req.setExColum1(soc);
                mypageService.suspenChgUpdate(req);
            }catch (SelfServiceException e) {
                log.error(e.getMessage());
                returnCode = this.getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
                message = getErrMsg(e.getMessage());
                req.setSvcCntrNo(ncn);
                req.setErrcode(returnCode);
                req.setGnNo(gnNo);
                req.setMsg(message);
                req.setEventCd("X21");
                req.setExColum1(soc);
                mypageService.suspenChgUpdate(req);
            }  catch (SocketTimeoutException e){
                returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
                message = getErrMsg(ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
                req.setSvcCntrNo(ncn);
                req.setErrcode(returnCode);
                req.setGnNo(gnNo);
                req.setMsg(message);
                req.setEventCd("X21");
                req.setExColum1(soc);
                mypageService.suspenChgUpdate(req);
            }

        }

        suspenChgTmlDtoList = new ArrayList<SuspenChgTmlDto>();
        //suspenChgTmlDtoList = mypageService.selectSuspenChgTmpList();

        model.addAttribute("suspenChgTmlDtoList", suspenChgTmlDtoList);

    }

    @RequestMapping("/mypage/X38Chg.do")
    public void x38Chg(HttpServletRequest request, ModelMap model){

        UserSessionDto userSession = SessionUtils.getUserCookieBean();


        if(userSession == null ) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        String isExceptionId =NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION , userSession.getUserId());
        if (isExceptionId == null || !"Y".equals(isExceptionId) ) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        List<SuspenChgTmlDto> suspenChgTmlDtoList = new ArrayList<SuspenChgTmlDto>();

        SuspenChgTmlDto reqDto = new SuspenChgTmlDto();
        reqDto.setEventCd("X38");
        suspenChgTmlDtoList = mypageService.selectSuspenChgTmp(reqDto);

        String returnCode = "99";
        String message = "";
        String ncn = "";
        String gnNo = "";
        SuspenChgTmlDto req = new SuspenChgTmlDto();
        for(SuspenChgTmlDto dto : suspenChgTmlDtoList){
            message = "";
            returnCode = "99";
            gnNo = "";
            req = new SuspenChgTmlDto();
            ncn = dto.getSvcCntrNo();
            String ctn = dto.getCntrMobileNo();
            String custId = dto.getCustId();
            String soc = dto.getExColum1(); // PL202S944
            try{
                MpMoscRegSvcCanChgInVO mpMoscRegSvcCanChgInVO = mPlatFormService.moscRegSvcCanChg(ncn, ctn, custId, soc);

                gnNo = mpMoscRegSvcCanChgInVO.getGlobalNo();
                returnCode = mpMoscRegSvcCanChgInVO.getResultCode();
                // update 세팅
                req.setSvcCntrNo(ncn);
                req.setErrcode(returnCode);
                req.setGnNo(gnNo);
                req.setEventCd("X38");
                req.setExColum1(soc);
                mypageService.suspenChgUpdate(req);
            }catch (SelfServiceException e) {
                log.error(e.getMessage());
                returnCode = this.getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
                message = getErrMsg(e.getMessage());
                req.setSvcCntrNo(ncn);
                req.setErrcode(returnCode);
                req.setGnNo(gnNo);
                req.setMsg(message);
                req.setEventCd("X38");
                req.setExColum1(soc);
                mypageService.suspenChgUpdate(req);
            }  catch (SocketTimeoutException e){
                returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
                message = getErrMsg(ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
                req.setSvcCntrNo(ncn);
                req.setErrcode(returnCode);
                req.setGnNo(gnNo);
                req.setMsg(message);
                req.setEventCd("X38");
                req.setExColum1(soc);
                mypageService.suspenChgUpdate(req);
            }

        }

        suspenChgTmlDtoList = new ArrayList<SuspenChgTmlDto>();

        model.addAttribute("suspenChgTmlDtoList", suspenChgTmlDtoList);

    }


    @RequestMapping("/mypage/suspenChg.do")
    public void suspenChg(HttpServletRequest request, ModelMap model){


        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null ) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        String isExceptionId =NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION , userSession.getUserId());
        if (isExceptionId == null || !"Y".equals(isExceptionId) ) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        List<SuspenChgTmlDto> suspenChgTmlDtoList = new ArrayList<SuspenChgTmlDto>();
        SuspenChgTmlDto reqDto = new SuspenChgTmlDto();
        reqDto.setEventCd("X29");
        suspenChgTmlDtoList = mypageService.selectSuspenChgTmp(reqDto);

        String returnCode = "99";
        String message = "";
        String ncn = "";
        String gnNo = "";
        String cpStartDt = "";
        String reasonCode = "";

        SuspenChgTmlDto req = new SuspenChgTmlDto();
        for(SuspenChgTmlDto dto : suspenChgTmlDtoList){
            message = "";
            returnCode = "99";
            gnNo = "";
            req = new SuspenChgTmlDto();
            ncn = dto.getSvcCntrNo();

            String cpPwdInsert = "741219";
            String ctn = dto.getCntrMobileNo();
            String custId = dto.getCustId();
            String userMemo = "긴급요청으로 첨부대상자 발신정지 처리";
            String cpDateYnTmp = "N";
            String cpEndDt = "";
            cpStartDt = StringUtil.NVL(dto.getExColum1(),"");
            reasonCode = StringUtil.NVL(dto.getExColum2(),"");

            try{
                MpSuspenChgVO suspenChgVO = mPlatFormService.suspenChg(ncn, ctn, custId, reasonCode, userMemo, cpDateYnTmp, cpEndDt, cpStartDt, cpPwdInsert );
                gnNo = suspenChgVO.getGlobalNo();
                returnCode = suspenChgVO.getResultCode();
                // update 세팅
                req.setSvcCntrNo(ncn);
                req.setErrcode(returnCode);
                req.setGnNo(gnNo);
                req.setEventCd("X29");
                mypageService.suspenChgUpdate(req);
            }catch (SelfServiceException e) {

                log.error(e.getMessage());
                returnCode = this.getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
                message = getErrMsg(e.getMessage());
                req.setSvcCntrNo(ncn);
                req.setErrcode(returnCode);
                req.setGnNo(gnNo);
                req.setMsg(message);
                req.setEventCd("X29");
                mypageService.suspenChgUpdate(req);
            }  catch (SocketTimeoutException e){

                returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
                message = getErrMsg(ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
                req.setSvcCntrNo(ncn);
                req.setErrcode(returnCode);
                req.setGnNo(gnNo);
                req.setMsg(message);
                req.setEventCd("X29");
                mypageService.suspenChgUpdate(req);
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }


    }

    /*
     * SRM25062633202 평생할인 정상화 처리 요청 건(1,066명 명단 원복)
     *
     *  1. 최종명단 데이터 임시 테이블(TMP_CHRG_PRMT_CHECK)에 insert
     *	2. 조건 적용(해지자 제외, 최종명단의 요금제와 현재요금제 다른사람 제외)
     *	3. 부가서비스 조회(X20) 후 평생할인 부가서비스 해지(X38) 데이터 생성(SUSPENCHGTMP에 insert), 공통코드에서 해지 리스트만
     *	4. 최종명단의 데이터 기반으로 부가서비스 신청(X21) 데이터 생성(SUSPENCHGTMP에 insert)
     *  5. insert 데이터 확인 후 부가서비스 해지 호출(https://www.ktmmobile.com/mypage/X38Chg.do)
     *  6. insert 데이터 확인 후 부가서비스 신청 호출(https://www.ktmmobile.com/mypage/X21Chg.do)
     *	7. 최종명단, 해지, 신청 결과 확인
     */
    @RequestMapping("/mypage/chrgPrmtCheck.do")
    public void chrgPrmtCheck(HttpServletRequest request){

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        if(userSession == null ) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        String isExceptionId =NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION , userSession.getUserId());
        if (isExceptionId == null || !"Y".equals(isExceptionId) ) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        String svcCntrNo = "";
        String ctn = "";
        String customerId = "";
        String subStatus = "";

        //임시테이블 데이터 넣은 후, 부가서비스 가입/해지를 위한 리스트 조회
        List<SuspenChgTmlDto> suspenChgTmlDtoList = mypageService.selectChrgPrmtCheckTmp();

        if (CollectionUtils.isEmpty(suspenChgTmlDtoList)) {
            throw new McpCommonJsonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        //공통코드에 있는 부가서비스 리스트만 해지처리
        List<NmcpCdDtlDto> targetSocList  = NmcpServiceUtils.getCodeList("X38List");
        Set<String> targetSocCodes = targetSocList.stream()
                .map(NmcpCdDtlDto::getDtlCdNm)
                .collect(Collectors.toSet());

        for(SuspenChgTmlDto dto : suspenChgTmlDtoList) {
            subStatus = mspService.getMspSubStatus(dto.getSvcCntrNo());

            //검증 1.회선 해지건 제외
            if("C".equals(subStatus)) {
                dto.setErrcode("Y");
                dto.setMsg("회선 해지자");
                mypageService.updateChrgPrmtCheckTmp(dto);
                continue;
            }

            Map<String, String> resOjb = mypageUserService.selectContractObj("","",dto.getSvcCntrNo());

            if (resOjb == null || resOjb.get("SUBSCRIBER_NO") == null || resOjb.get("CUSTOMER_ID") == null) {
                dto.setErrcode("Y");
                dto.setMsg("필수값 누락");
                mypageService.updateChrgPrmtCheckTmp(dto);
                continue;
            }

            svcCntrNo = dto.getSvcCntrNo();
            ctn = resOjb.get("SUBSCRIBER_NO");
            customerId = resOjb.get("CUSTOMER_ID");

            //현재 요금제 조회
            McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(svcCntrNo);

            //검증 2.요금제 비교(최종명단 데이터의 요금제와 현재 요금제가 다르면 제외)
            if(mcpFarPriceDto == null || !dto.getRateCd().equals(mcpFarPriceDto.getPrvRateCd())) {
                dto.setErrcode("Y");
                dto.setMsg("현재 요금제 :" + mcpFarPriceDto.getPrvRateCd() + " / 명단과 요금제 불일치");
                mypageService.updateChrgPrmtCheckTmp(dto);
                continue;
            }

            try {
                //처리 1.부가서비스 조회(X20)
                MpAddSvcInfoDto getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(svcCntrNo, ctn, customerId);
                String globalNo = getAddSvcInfo.getGlobalNo();

                if(!getAddSvcInfo.isSuccess()){
                    dto.setErrcode("Y");
                    dto.setMsg("부가서비스 조회 오류");
                    dto.setGnNo(globalNo);
                    mypageService.updateChrgPrmtCheckTmp(dto);
                    continue;
                }

                if(ObjectUtils.isEmpty(getAddSvcInfo) || ObjectUtils.isEmpty(getAddSvcInfo.getList())) {
                    dto.setErrcode("Y");
                    dto.setMsg("부가서비스 없음");
                    dto.setGnNo(globalNo);
                    mypageService.updateChrgPrmtCheckTmp(dto);
                    continue;
                }

                //처리 2.평생할인 부가서비스 해지 임시테이블 insert
                //부가서비스 조회 후 평생할인 건만 해지 insert처리
                //공통코드에 있는 부가서비스만 해지
                for(MpSocVO socVo : getAddSvcInfo.getList()) {
                    if(0 > socVo.getSocRateVat() && targetSocCodes.contains(socVo.getSoc())) {
                        dto.setSvcCntrNo(svcCntrNo);
                        dto.setCustId(customerId);
                        dto.setCntrMobileNo(ctn);
                        dto.setEventCd("X38");
                        dto.setExColum1(socVo.getSoc());
                        mypageService.insertSuspenChgTmp(dto);
                    }
                }

                //처리 3.검증 후 평생할인 부가서비스 가입 임시테이블 insert
                String[] exColValues = {
                        dto.getExColum2(),
                        dto.getExColum3(),
                        dto.getExColum4(),
                        dto.getExColum5(),
                        dto.getExColum6()
                };

                for (String exCol : exColValues) {
                    if (StringUtils.isNotBlank(exCol)) {
                        dto.setSvcCntrNo(svcCntrNo);
                        dto.setCustId(customerId);
                        dto.setCntrMobileNo(ctn);
                        dto.setEventCd("X21");
                        dto.setExColum1(exCol);
                        mypageService.insertSuspenChgTmp(dto);
                    }
                }

                //모든 처리 이후 정상 건
                dto.setErrcode("N");
                dto.setMsg("검증 통과");
                dto.setGnNo(globalNo);
                mypageService.updateChrgPrmtCheckTmp(dto);

            }catch(SelfServiceException e) {
                dto.setErrcode("Y");
                dto.setMsg(getErrMsg(e.getMessage()));
                mypageService.updateChrgPrmtCheckTmp(dto);
                continue;
            }catch(Exception e){
                dto.setErrcode("Y");
                dto.setMsg(getErrMsg(e.getMessage()));
                mypageService.updateChrgPrmtCheckTmp(dto);
                continue;
            }

        }

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
