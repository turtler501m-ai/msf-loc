/**
 *
 */

package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.msf.domains.form.common.dto.NiceLogDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.mspservice.dao.MspDao;
import com.ktmmobile.msf.domains.form.common.service.FCommonSvc;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.service.NiceLogSvc;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.form.newchange.dao.AppformDao;
import com.ktmmobile.msf.domains.form.form.ownerchange.repository.MyNameChgDao;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.MyNameChgReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.CustRequestDao;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.MypageDao;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMyinfoService;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.NICE_CERT_REQ_NULL_EXCEPTION;

/**
 * @author ANT_FX700_02
 *
 */
@Service
public class MyNameChgServiceImpl implements MyNameChgService {

    private static final Logger logger = LoggerFactory.getLogger(MyNameChgServiceImpl.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    IpStatisticService ipstatisticService;

    @Autowired
    MypageDao mypageDao;

    @Autowired
    MspDao mspDao;

    @Autowired
    MyNameChgDao myNameChgDao;

    @Autowired
    CustRequestDao custRequestDao;

    //    @Autowired
    //    MsfFarPricePlanService farPricePlanService;
    /*
    @Autowired
    FormImageMakeSvc formImageMakeSvc;
    */

    @Autowired
    private MsfMyinfoService myinfoService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private NiceLogSvc nicelog;

    //    @Autowired
    //    private AppformSvc appformSvc;

    //    @Autowired
    //    private FathService fathService;

    @Autowired
    private AppformDao appformDao;


    /**
     * @Description : MCP 휴대폰 회선관리 리스트를 가지고 온다. (명의변경)
     * @Create Date : 2022. 07. 15.
     */
    public List<McpUserCntrMngDto> selectCntrListNmChg(String userId, String contractNum) {
        //---- API 호출 S ----//
        //MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        UserSessionDto userSessionDto1 = SessionUtils.getUserCookieBean();
        if (userSessionDto1 != null) {
            params.put("customerId", userSessionDto1.getCustomerId());
        }

        if (!StringUtils.isEmpty(contractNum)) {
            params.put("contractNum", contractNum);
        }

        RestTemplate restTemplate = new RestTemplate();
        //McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrList", userId, McpUserCntrMngDto[].class);
        McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNmChg", params, McpUserCntrMngDto[].class);

        UserSessionDto userSessionDto2 = SessionUtils.getUserCookieBean();

        List<McpUserCntrMngDto> list = null;
        if (java.util.Optional.ofNullable(resultList).isPresent() && resultList.length != 0) {
            list = Arrays.asList(resultList);
        }


        //---- API 호출 E ----//
        return list;
    }

    /**
     * @Description : 명의변경 신청
     */
    @Override
    @Transactional
    public String myNameChgRequest(MyNameChgReqDto myNameChgReqDto) {
        logger.error("## 명의변경 신청 myNameChgReqDto : " + myNameChgReqDto);

        String result = "";

        NiceLogDto niceLogDto = new NiceLogDto();
        niceLogDto.setReqSeq(myNameChgReqDto.getReqSeq());
        niceLogDto.setResSeq(myNameChgReqDto.getResSeq());
        niceLogDto.setLimitMinute(60);  // 60분 이내의 이력 조회

        NiceLogDto smsNiceLogDto = nicelog.getMcpNiceHistWithTime(niceLogDto);
        if (smsNiceLogDto != null) {
            myNameChgReqDto.setSelfCstmrCi(smsNiceLogDto.getConnInfo());
        } else {
            throw new McpCommonException(NICE_CERT_REQ_NULL_EXCEPTION);
        }
        //예약번호 
        myNameChgReqDto.setMcnResNo(appformDao.generateResNo());

        //안면인증
        String fathTrgYn = myNameChgReqDto.getFathTrgYn();
        if ("Y".equals(fathTrgYn)) {
            //            FathSessionDto fathSessionDto = fathService.validateFathSession();
            //            myNameChgReqDto.setFathTransacId(fathSessionDto.getTransacId());
            //            myNameChgReqDto.setFathCmpltNtfyDt(fathSessionDto.getCmpltNtfyDt());
            //            myNameChgReqDto.setFathTelNo(myNameChgReqDto.getCstmrReceiveTelFn()+myNameChgReqDto.getCstmrReceiveTelMn()+myNameChgReqDto.getCstmrReceiveTelRn());
            //            //안면인증 관련 OSST 연동이력 MVNO_ORD_NO 컬럼데이터 '임시예약번호'를 -> '실제예약번호'로 업데이트
            //            fathService.updateFathMcpRequestOsst(myNameChgReqDto.getMcnResNo());
        }

        myNameChgDao.insertNmcpCustReqMst(myNameChgReqDto);
        myNameChgDao.insertNmcpCustReqNameChg(myNameChgReqDto);


        // 미성년자 신청일때
        if ("NM".equals(myNameChgReqDto.getGrCstmrType()) || "NM".equals(myNameChgReqDto.getCstmrType())) {
            myNameChgDao.insertNmcpCustReqNameChgAgent(myNameChgReqDto);
            // 양도인 미성년자
            if ("NM".equals(myNameChgReqDto.getGrCstmrType())) {
                myNameChgReqDto.setGrOnlineAuthInfo("");
                myNameChgReqDto.setGrOnlineAuthType("");
                // 양수인 법정대리인 인증정보는 NMCP_CUST_REQUEST_MST 에 넣지 않는다.
                myNameChgDao.updateNmcpCustReqMst(myNameChgReqDto);
            }
            // 양수인 미성년자
            if ("NM".equals(myNameChgReqDto.getCstmrType())) {
                myNameChgReqDto.setOnlineAuthInfo("");
                myNameChgReqDto.setOnlineAuthType("");
                myNameChgReqDto.setSelfCertType("");
                myNameChgReqDto.setSelfIssuExprDt("");
                myNameChgReqDto.setSelfIssuNum("");
                myNameChgReqDto.setSelfCstmrCi("");
                // 양수인 법정대리인 인증정보는 NMCP_CUST_REQUEST_NAME_CHG 에 넣지 않는다.
                myNameChgDao.updateNmcpCustReqNameChg(myNameChgReqDto);
            }
        }
        result = "SUCCESS";

        return result;
    }

    @Override
    public String grantorReqChk(MyNameChgReqDto myNameChgReqDto) {
        String result = "SUCCESS";

        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", myNameChgReqDto.getUserid());
        params.put("contractNum", myNameChgReqDto.getContractNum());
        UserSessionDto userSessionDto1 = SessionUtils.getUserCookieBean();
        if (userSessionDto1 != null) {
            params.put("customerId", userSessionDto1.getCustomerId());
        }

        RestTemplate restTemplate = new RestTemplate();
        //McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrList", userId, McpUserCntrMngDto[].class);
        McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNmChg", params, McpUserCntrMngDto[].class);

        List<McpUserCntrMngDto> list = null;
        if (java.util.Optional.ofNullable(resultList).isPresent() && resultList.length != 0) {
            list = Arrays.asList(resultList);
            // 정지회선일때
            if ("S".equals(list.get(0).getSubStatus())) {
                result = "STOP";
                return result;
            }
            // 미납회원일때
            if ("D".equals(list.get(0).getColDelinqStatus())) {
                result = "NONPAY";
                return result;
            }

            // 실사용일자 90일 이상인 회선만 신청가능
            /*
             * x83.회선 사용기간 조회 realUseDayNum 실사용기간
             */
            if ("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName)) {
                return result;
            } else {
                //                MoscWireUseTimeInfoRes moscWireUseTimeInfoRes = farPricePlanService.moscWireUseTimeInfo(list.get(0).getSvcCntrNo(),
                //                                                                                            list.get(0).getCntrMobileNo(), list.get(0).getCustId());

                //                if (moscWireUseTimeInfoRes.getRealUseDayNum() != null) {
                //                    String realUseDay = moscWireUseTimeInfoRes.getRealUseDayNum();
                //                    int realUseDayInt = Integer.parseInt(realUseDay, 10);
                //
                //                    if (realUseDayInt < 90) {
                //                        result = "LESSNINETY";
                //                        return result;
                //                    }
                //                } else {
                //                    result = "ERROR";
                //                    return result;
                //                }
            }
        } else {
            result = "FAIL";
            return result;
        }
        return result;
    }

    @Override
    public String nameChgChkTelNo(MyNameChgReqDto myNameChgReqDto) {

        String result = "SUCCESS";
        String mobileNo = "";
        String chkTelNo = myNameChgReqDto.getEtcMobile();

        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", myNameChgReqDto.getUserid());
        params.put("contractNum", myNameChgReqDto.getContractNum());

        RestTemplate restTemplate = new RestTemplate();
        McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNmChg", params, McpUserCntrMngDto[].class);
        List<McpUserCntrMngDto> list = null;
        if (java.util.Optional.ofNullable(resultList).isPresent() && resultList.length != 0) {
            list = Arrays.asList(resultList);
            mobileNo = list.get(0).getCntrMobileNo(); //회선번호
        }

        logger.error(mobileNo + "*****************" + chkTelNo + "************" + myNameChgReqDto.getCstmrType());

        // 양도인
        if ("NOR".equals(myNameChgReqDto.getChkTelType())) {
            // 명변회선과 미납연락처가 같으면 안됨
            if (mobileNo.equals(chkTelNo)) {
                result = "NOTEQUAL";
                return result;
            }
            // 미성년자 - 명변회선과 법정대리인 연락처가 같으면 안됨
            if ("NM".equals(myNameChgReqDto.getGrCstmrType())) {
                if (mobileNo.equals(myNameChgReqDto.getGrMinorAgentTel())) {
                    result = "NOTEQUAL_MINOR";
                    return result;
                }
            }
        }
        // 양수인
        else if ("NEE".equals(myNameChgReqDto.getChkTelType())) {
            // 미성년자 - 명변회선과 법정대리인 연락처가 같으면 안됨
            if ("NM".equals(myNameChgReqDto.getCstmrType())) {
                if (mobileNo.equals(myNameChgReqDto.getMinorAgentTel())) {
                    result = "NOTEQUAL_MINOR";
                    return result;
                }
                String receiveTelNm = myNameChgReqDto.getCstmrReceiveTelFn() + myNameChgReqDto.getCstmrReceiveTelMn() + myNameChgReqDto.getCstmrReceiveTelRn();
                if (receiveTelNm.equals(myNameChgReqDto.getMinorAgentTel())) {
                    result = "NOTEQUAL_MINOR2";
                    return result;
                }
            }
        }
        return result;
    }

}
