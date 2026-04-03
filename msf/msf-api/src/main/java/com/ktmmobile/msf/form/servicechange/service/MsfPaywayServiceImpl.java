package com.ktmmobile.msf.form.servicechange.service;

import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.system.common.constants.Constants.PAY_INFO_CREATEYN_FAIL;
import static com.ktmmobile.msf.system.common.constants.Constants.PAY_INFO_CREATEYN_REQ;
import static com.ktmmobile.msf.system.common.constants.Constants.PAY_INFO_CREATEYN_TIMEOUT;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.ktmmobile.msf.system.common.mplatform.dto.MpVirtualAccountNoDto;
import com.ktmmobile.msf.system.common.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.system.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscSimplePaySmsRes;
import com.ktmmobile.msf.system.common.mplatform.vo.MpBilPrintInfoVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.util.DateTimeUtil;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;
import com.ktmmobile.msf.payinfo.dto.PayInfoDto;
import com.ktmmobile.msf.payinfo.dto.PaywayDto;
@Service
public class MsfPaywayServiceImpl implements MsfPaywayService {

    private static Logger logger = LoggerFactory.getLogger(MsfPaywayServiceImpl.class);

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private MsfPayInfoService payInfoService;

    @Autowired
    private IpStatisticService ipStatisticService;

    /**
     * 가입정보조회 연동 규격 X01
     * */
    @Override
    public MpPerMyktfInfoVO perMyktfInfo(String ncn, String ctn, String custId) {

        MpPerMyktfInfoVO perMyktfInfo = null;
        String initActivationDate = "-";
        try {
            perMyktfInfo = mPlatFormService.perMyktfInfo(ncn, ctn, custId);
            if( perMyktfInfo !=null ) {
                initActivationDate = StringUtil.NVL(perMyktfInfo.getInitActivationDate(),"-");
                if(!"-".equals(initActivationDate)){
                    initActivationDate = initActivationDate.substring(0, 8);
                }
                initActivationDate = DateTimeUtil.changeFormat(initActivationDate,"yyyyMMdd","yyyy.MM.dd");
                perMyktfInfo.setInitActivationDate(initActivationDate);
            }
        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        }  catch(Exception e) {
            logger.debug("X01 조회 에러");
        }

        return perMyktfInfo;
    }

    /**
     * 현재 납부 방법 연동 X23
     * */
    @Override
    public MpFarChangewayInfoVO farChangewayInfo(String ncn, String ctn, String custId) {

        MpFarChangewayInfoVO mFarchangewayinfovo = null;

        try {
            mFarchangewayinfovo = mPlatFormService.farChangewayInfo(ncn, ctn, custId);
        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        }  catch(Exception e) {
            logger.debug("X23 조회 에러");
        }
        return mFarchangewayinfovo;
    }

    /**
     * 청구서조회 X49
     * */
    @Override
    public MpMoscBilEmailInfoInVO kosMoscBillInfo(String ncn, String ctn, String custId) {

        MpMoscBilEmailInfoInVO mMoscbilemailinfoinvo = null;
        try {
            mMoscbilemailinfoinvo = mPlatFormService.kosMoscBillInfo(ncn, ctn, custId);
        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        }  catch(Exception e) {
            logger.debug("X49 조회 에러");
        }

        return mMoscbilemailinfoinvo;
    }

    /**
     * 종이청구서조회 X10
     * */
    @Override
    public MpBilPrintInfoVO bilPrintInfo(String ncn, String ctn, String custId) {

        MpBilPrintInfoVO mBilprintinfovo = null;

        try {
            mBilprintinfovo = mPlatFormService.bilPrintInfo(ncn, ctn, custId);
        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        } catch(Exception e) {
            logger.debug("X10 조회 에러");
        }


        return mBilprintinfovo;

    }

    @Override
    public Map<String, Object> getPayData(String ncn, String ctn, String custId) {

        Map<String,Object> map = new HashMap<String, Object>();

        // 가입정보조회 연동 규격 X01
        MpPerMyktfInfoVO perMyktfInfo = this.perMyktfInfo(ncn, ctn, custId);
        // 현재 납부 방법 연동 X23
        MpFarChangewayInfoVO changeInfo = this.farChangewayInfo(ncn, ctn, custId);
        // 청구서조회 X49
        MpMoscBilEmailInfoInVO moscBilEmailInfo = this.kosMoscBillInfo(ncn, ctn, custId);


        String reqType = "";
        String reqTypeNm = "";
        String blaAddr = "";

        String payMethod = "";
        String blAddr = "";
        String blBankAcctNo = "";
        String billCycleDueDay = "";
        String prevCardNo = "";
        String prevExpirDt = "";
        String payTmsCd = "";
        boolean isChangeInfo = false;
        if( changeInfo != null ) {
            payMethod = StringUtil.NVL(changeInfo.getPayMethod(),"");
            blAddr = StringUtil.NVL(changeInfo.getBlAddr(),"");
            blBankAcctNo = StringUtil.NVL(changeInfo.getBlBankAcctNo(),"");
            prevCardNo = StringUtil.NVL(changeInfo.getPrevCardNo(),"");
            prevExpirDt = StringUtil.NVL(changeInfo.getPrevExpirDt(),"");
            billCycleDueDay = StringUtil.NVL(changeInfo.getBillCycleDueDay(),"");
            payTmsCd = StringUtil.NVL(changeInfo.getPayTmsCd(),"");

            if("99".equals(billCycleDueDay)) {
                billCycleDueDay = "말일";
            } else {
                billCycleDueDay = billCycleDueDay.concat("일");
            }
            if(!"".equals(prevExpirDt) && prevExpirDt.length() > 7 ) {
                prevExpirDt = prevExpirDt.substring(0, 4)+"-"+prevExpirDt.substring(4, 6)+"-"+prevExpirDt.substring(6, 8);
            }

            if("지로".equals(payMethod)) {
                reqTypeNm = "청구지";
                blaAddr = blAddr;
                isChangeInfo = true;
            }

            if("01".equals(payTmsCd)) {
                payTmsCd = "1회차(11일경)";
            }else {
                payTmsCd = "2회차(20일경)";
            }

        }

        String billTypeCd = "";
        String email = "";
        String serviceCtn = "";
        boolean isMoscBilEmailInfo = false;
        if( moscBilEmailInfo != null ) {

            billTypeCd = StringUtil.NVL(moscBilEmailInfo.getBillTypeCd(),"");
            // email = StringUtil.NVL(moscBilEmailInfo.getEmail(),"");
            email = StringUtil.NVL(moscBilEmailInfo.getMaskedEmail(),"");
            serviceCtn = StringUtil.NVL(moscBilEmailInfo.getCtn(),"");
            if(!isChangeInfo) {
                if( "CB".equals(billTypeCd) ) {
                    reqTypeNm = "메일주소";
                    blaAddr = email;
                    isMoscBilEmailInfo = true;
                } else if( "MB".equals(billTypeCd) ) {
                    reqTypeNm = "휴대폰 번호";
                    blaAddr = serviceCtn;
                    isMoscBilEmailInfo = true;
                }
            }

            if( "CB".equals(billTypeCd) ) {
                reqType = "이메일 명세서";
            } else if( "LX".equals(billTypeCd) ) {
                reqType = "우편 명세서";
            } else if( "MB".equals(billTypeCd) ) {
                reqType = "모바일 명세서(MMS)";
            }
        }

        if(!isChangeInfo && !isMoscBilEmailInfo) {
            String addr = "";
            if(perMyktfInfo !=null) {
                addr = StringUtil.NVL(perMyktfInfo.getAddr(),"");
                reqTypeNm = "청구지";

                // 마스킹해제 인증 완료 시
                if(SessionUtils.getMaskingSession() > 0 ) {
                    blaAddr = addr;
                }else {
                    blaAddr = addr+"********";
                }
            }
        }

        String initActivationDate = "";
        if(perMyktfInfo !=null) {
            initActivationDate = StringUtil.NVL(perMyktfInfo.getInitActivationDate(),"-");
        }

        map.put("reqType", reqType);
        map.put("reqTypeNm", reqTypeNm);
        map.put("blaAddr", blaAddr);
        map.put("payMethod", payMethod);
        map.put("blBankAcctNo", blBankAcctNo);
        map.put("billTypeCd", billTypeCd);
        map.put("billCycleDueDay", billCycleDueDay);
        map.put("prevCardNo", prevCardNo);
        map.put("prevExpirDt", prevExpirDt);
        map.put("initActivationDate", initActivationDate);
        map.put("payTmsCd",payTmsCd);

        return map;

    }

    /**
     * 납부 방법 변경 X25
     * */
    @Override
    public Map<String, Object> farChgWayChg(PaywayDto paywayDto) {

        Map<String,Object> returnMap = new HashMap<String,Object>();
        String returnCode = "00";
        String message = "";

        String reqPayType = StringUtil.NVL(paywayDto.getReqPayType(),"");
        String blBankCode = StringUtil.NVL(paywayDto.getBlBankCode(),"");
        String cycleDueDay = StringUtil.NVL(paywayDto.getCycleDueDay(),"");
        String bankAcctNo = StringUtil.NVL(paywayDto.getBankAcctNo(),"");
        String ncn = StringUtil.NVL(paywayDto.getNcn(),"");
        String ctn = StringUtil.NVL(paywayDto.getCtn(),"");
        String custId = StringUtil.NVL(paywayDto.getCustId(),"");
        String cardExpirDate = StringUtil.NVL(paywayDto.getCardExpirDate(),"");
        String creditCardNo = StringUtil.NVL(paywayDto.getCreditCardNo(),"");
        String cardCode = StringUtil.NVL(paywayDto.getCardCode(),"");
        String adrZip = StringUtil.NVL(paywayDto.getAdrZip(),"");
        String adrPrimaryLn = StringUtil.NVL(paywayDto.getAdrPrimaryLn(),"");
        String adrSecondaryLn = StringUtil.NVL(paywayDto.getAdrSecondaryLn(),"");
        String blpymTmsIndCd = StringUtil.NVL(paywayDto.getBlpymTmsIndCd(),"");

        Map<String,String> map = new HashMap<String, String>();

        map.put("nextBlMethod", reqPayType);  //다음납부방법(공통) C:카드, D:자동이체, R:지로
        map.put("nextBlBankCode", blBankCode);  //다음납부방법 은행코드
        map.put("nextCycleDueDay", cycleDueDay);    //자동이체납부일, 납기일자 코드 21/25/27/99(매달말일)
        map.put("nextBankAcctNo", bankAcctNo);  //다음납부방법 계좌번호  (계좌이체 시)
        map.put("nextCardExpirDate", cardExpirDate);    //카드유효기간 년월 (카드) 201703
        map.put("nextCreditCardNo", creditCardNo);  //다음납부방법 신용카드번호 (카드)
        map.put("nextCardCode", cardCode);  //다음납부방법 신용카드종류 (카드) BM BC M.COM카드/BK KTF맴버스 BC카드/LK KTF멤버스LG카드/NH NH카드…

        if( "C".equals(reqPayType) ) {
            map.put("blpymTmsIndCd", "02"); //‘01’ or ’02’1 or 2 회차 각사업자별 가능한 회차 지정)
            map.put("blpymTmsIndCd", blpymTmsIndCd); // 01: 1회차(매월11일경) or  02: 2회차(매월20일경)
        } else if( "D".equals(reqPayType) ) {
            /*
             * agreIndCd 동의자료코드 (계좌이체)
             * 1. 납부방법이 자동치에이면 필수
             * 01: 서면 , 02: 공인인증 , 03:일반 인증 , 04: 녹취 , 05:ARS
             *
             * myslAthnTypeCd 본인인증타입 코드 (계좌이체)
             * 1. 동의자료 코드가 일반인증이면 필수
             * 01:SMS , 02:iPin , 03:신용카드 ,04 : 범용공인 인증
             */
            map.put("agreIndCd", "03");//동의자료코드 (계좌이체)
            map.put("myslAthnTypeCd", paywayDto.getMyslAthnTypeCd());//본인인증타입 코드 (계좌이체)
        }
        map.put("adrZip", adrZip);  //우편번호 (지로)
        map.put("adrPrimaryLn", adrPrimaryLn);  //기본주소 (지로)
        map.put("adrSecondaryLn", adrSecondaryLn);  //상세주소 (지로)

        logger.info("[WOO][WOO][WOO]X25 DATA==>" + ObjectUtils.convertObjectToString(map));

        PayInfoDto payInfoDtoResult = null;
        PayInfoDto payDto = new PayInfoDto();

        if("D".equals(reqPayType)) {

            String cstmrName = paywayDto.getCstmrName();
            String cstmrNativeRrn01 = paywayDto.getCstmrNativeRrn01();
            String reqSeq = paywayDto.getReqSeq();
            String resSeq = paywayDto.getResSeq();
            String userId = paywayDto.getUserId();

            payDto.setCstmrName(cstmrName);
            payDto.setBirthDate(cstmrNativeRrn01);
            payDto.setCtn(ctn);
            payDto.setReqBank(blBankCode);
            payDto.setReqAccountNumber(bankAcctNo);
            payDto.setResNo(reqSeq,resSeq);
            payDto.setUserId(userId);
            payDto.setCreateYn(PAY_INFO_CREATEYN_REQ);


            payInfoDtoResult = payInfoService.savePayInfoHist(payDto);
            if (!payInfoDtoResult.getResult()) {
                returnCode = "S";
                message = "납부방법 변경에 실패했습니다.";
                returnMap.put("returnCode",returnCode);
                returnMap.put("message",message);
                return returnMap;
            }
        }

        try {
            mPlatFormService.farChgWayChg(ncn, ctn, custId,map);
        } catch (SelfServiceException e) {
            returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(e.getMessage());
            payDto.setCreateYn(PAY_INFO_CREATEYN_FAIL);
        } catch (SocketTimeoutException e){
            returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
            payDto.setCreateYn(PAY_INFO_CREATEYN_TIMEOUT);
        } finally {
            if("D".equals(reqPayType)) {
                try {
                    payInfoDtoResult = payInfoService.savePayInfoHist(payDto);
                } catch(DataAccessException e) {
                    logger.debug("savePayInfoHist error");
                } catch(Exception e) {
                    logger.debug("savePayInfoHist error");
                }
            }
            returnMap.put("returnCode",returnCode);
            returnMap.put("message",message);
        }
        return returnMap;
    }

    /**
     * 납부 방법 변경 X82
     * */
    @Override
    public Map<String, Object> smsFarChgWayChg(PaywayDto paywayDto) {

        Map<String,Object> returnMap = new HashMap<String,Object>();
        String RESULT_CODE = "00000";
        String RESULT_MSG = "";

        String reqPayType = StringUtil.NVL(paywayDto.getReqPayType(),"");
        String ncn = StringUtil.NVL(paywayDto.getNcn(),"");
        String ctn = StringUtil.NVL(paywayDto.getCtn(),"");
        String custId = StringUtil.NVL(paywayDto.getCustId(),"");
        String payBizCd = StringUtil.NVL(paywayDto.getPayBizCd(),"");
        /*카카오 SMS호출 건수 확인
         * 오늘 날짜
         * NCN
         */
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd", Locale.KOREA );
        Date currentTime = new Date ( );
        String strToday = formatter.format (currentTime);//현재날짜 yyyymmdd

        McpIpStatisticDto mcpIpStatisticInput = new McpIpStatisticDto();
        mcpIpStatisticInput.setPrcsMdlInd("KAKA_O_PAY");
        mcpIpStatisticInput.setTrtmRsltSmst(ncn);
        mcpIpStatisticInput.setSysDt(strToday);

        List<McpIpStatisticDto> accessTraceList = ipStatisticService.getAdminAccessTrace(mcpIpStatisticInput);
        if (accessTraceList !=null && accessTraceList.size() > 10) {
            RESULT_CODE = "05";
            RESULT_MSG = "자동납부신청을 위한 SMS 발송은 하루에 10회까지 가능합니다.\\n내일 다시 시도해주시기 바랍니다.";
        }

        //82.KAKAOPAY 가입 SMS 전송
        try {
            MoscSimplePaySmsRes moscSimplePaySmsRes = mPlatFormService.moscSimplePaySms(ncn, ctn, custId,payBizCd) ;
            if ("Y".equals(moscSimplePaySmsRes.getRsltYn())) {
                RESULT_CODE = AJAX_SUCCESS;
                RESULT_MSG = moscSimplePaySmsRes.getSvcMsg();
            }

            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("KAKA_O_PAY");
            mcpIpStatisticDto.setParameter(moscSimplePaySmsRes.getGlobalNo());
            mcpIpStatisticDto.setPrcsSbst("[RsltYn]" +moscSimplePaySmsRes.getRsltYn() + "[SvcMsg]" + moscSimplePaySmsRes.getSvcMsg());
            mcpIpStatisticDto.setTrtmRsltSmst(ncn);
            ipStatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

        } catch (SocketTimeoutException e) {
            RESULT_CODE = "06";
            RESULT_MSG = SOCKET_TIMEOUT_EXCEPTION;
        } finally {
            returnMap.put("RESULT_CODE",RESULT_CODE);
            returnMap.put("RESULT_MSG",RESULT_MSG);
        }
        return returnMap;
    }

    @Override
    public List<MpVirtualAccountNoDto> getVirtualAccountNoList(String svcCntrNo, String cntrMobileNo, String customerId) throws SelfServiceException, SocketTimeoutException {
        return mPlatFormService.moscVirtlBnkacnNoListInfo(svcCntrNo, cntrMobileNo, customerId).getMpVirtualAccountNoList().stream()
                .filter(virtualAccount -> {
                    try {
                        return DateTimeUtil.isMiddleDateTime2(virtualAccount.getEfctStDt(), virtualAccount.getEfctFnsDt());
                    } catch (ParseException e) {
                        return false;
                    }
                }).collect(Collectors.toList());
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
