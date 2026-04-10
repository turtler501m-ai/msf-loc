package com.ktmmobile.mcp.common.service;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.common.dto.KakaoDto;
import com.ktmmobile.mcp.common.dto.NiceLogDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.NiceTryLogDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.util.CommonHttpClient;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.SessionUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

import static com.ktmmobile.mcp.common.constants.Constants.CUST_AUTH;
import static com.ktmmobile.mcp.common.constants.Constants.KAKAO_AUTH;
import static com.ktmmobile.mcp.common.constants.Constants.KAKAO_AUTH_TYPE;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Service
public class KakaoCertifyImpl implements KakaoCertifyService{

    private static final Logger logger = LoggerFactory.getLogger(KakaoCertifyImpl.class);

    @Value("${inf.url}")
    private String infUrl;

    @Value("${kakao.apiKey}")
    private String key;

    @Value("${kakao.sectKey}")
    private String enCodeKey;

    @Value("${kakao.iv}")
    private String enCodeIv;

    @Autowired
    private NiceLogSvc niceLogSvc;

    @Autowired
    IpStatisticService ipstatisticService;

    @Value("${SERVER_NAME}")
    private String serverNm;

    @Value("${ext.url}")
    private String extUrl;



    @Override
    public Map<String, Object> cellKakaoCertify(KakaoDto kakaoDto) throws JSONException, SocketTimeoutException {

        Map<String, Object> reMap = new HashMap<>();
        CommonHttpClient httpClient = new CommonHttpClient(extUrl + "/kakaAuth.do");
        NameValuePair[] data = {
            new NameValuePair("userName", kakaoDto.getName()),
            new NameValuePair("phone_No", kakaoDto.getPhone_No()),
            new NameValuePair("birthday", kakaoDto.getBirthday()),
            new NameValuePair("certifyType", kakaoDto.getCertifyType())
        };
        String certifyStr = httpClient.postUtf8(data);
        if (StringUtils.isNotEmpty(certifyStr)) {
            logger.info("kakao certify data " + certifyStr);
            JSONObject jsonObject = new JSONObject(certifyStr);
            logger.info("kakao jsonObject " + jsonObject);
            kakaoDto.setTxId(jsonObject.getString("tx_id"));
            kakaoDto.setResult(jsonObject.getString("result"));

            // 카카오 본인인증 요청 정보 세션 임시 저장
            NiceResDto niceResDto= new NiceResDto();
            niceResDto.setName(kakaoDto.getName());
            niceResDto.setBirthDate(kakaoDto.getBirthday());
            niceResDto.setsMobileNo(kakaoDto.getPhone_No());
            SessionUtils.saveNiceReqTmp(niceResDto);
        }

        reMap.put("RESULT_CODE", StringUtils.isNotEmpty(certifyStr) ? "200" : "9999");
        reMap.put("kakaoAuth", kakaoDto);
        return reMap;
    }

    @Override
    public Map<String, Object> selectCertVerify(KakaoDto kakaoDto) throws JSONException, SocketTimeoutException, InvalidAlgorithmParameterException, InvalidKeyException {

        Map<String, Object> reMap = new HashMap<>();

        // ==== START 카카오 인증완료 처리 전, 인증 요청데이터와 비교 ====
        NiceResDto niceResDto= SessionUtils.getNiceReqTmpCookieBean();
        if(niceResDto == null){ // 인증요청 정보 없음
            throw new McpCommonJsonException("9998" ,NICE_CERT_REQ_NULL_EXCEPTION);
        }

        String inpName= (kakaoDto.getName() == null ) ? "" : kakaoDto.getName().toUpperCase().replaceAll(" ", "");
        String inpBirthday= kakaoDto.getBirthday();
        String inpPhoneNo= kakaoDto.getPhone_No();

        String authReqName= niceResDto.getName().replaceAll(" ", ""); // niceResDto의 getName은 upper처리 되어짐

        if(!authReqName.equals(inpName)
           || !niceResDto.getBirthDate().equals(inpBirthday)
           || !niceResDto.getsMobileNo().equals(inpPhoneNo)){  // 데이터 변조
            throw new McpCommonJsonException("9998" ,NICE_CERT_EXCEPTION);
        }
        // ==== END 카카오 인증완료 처리 전, 인증 요청데이터와 비교 ====

        CommonHttpClient httpClient = new CommonHttpClient(extUrl + "/kakaVerify.do");
        NameValuePair[] data = {
            new NameValuePair("txId", kakaoDto.getTxId())
        };

        String verifyStr = httpClient.postUtf8(data);
        NiceLogDto niceLogDto = new NiceLogDto();
        NiceLogDto rtnLogDto = new NiceLogDto();

        if (StringUtils.isNotEmpty(verifyStr)) {

            logger.info("kakao verify data " + verifyStr);
            verifyStr = verifyStr.replaceAll("_","");
            JSONObject jsonObject = new JSONObject(verifyStr);
            kakaoDto.setStatus(jsonObject.getString("status"));
            kakaoDto.setCreatedAt(jsonObject.isNull("createdat") ? null : jsonObject.getString("createdat"));
            kakaoDto.setExpiredAt(jsonObject.isNull("expiredat") ? null : jsonObject.getString("expiredat"));
            kakaoDto.setViewedAt(jsonObject.isNull("viewedat") ? null : jsonObject.getString("viewedat"));
            kakaoDto.setCompletedAt(jsonObject.isNull("completedat") ? null : jsonObject.getString("completedat"));

            if ( !jsonObject.isNull("data") ) {
                JSONObject securifyData = jsonObject.getJSONObject("data");
                kakaoDto.setResult(securifyData.isNull("result") ? null : securifyData.getString("result"));
                kakaoDto.setRequestToken(securifyData.isNull("requesttoken") ? null : securifyData.getString("requesttoken"));
                kakaoDto.setConnInfo(securifyData.isNull("ci") ? null : securifyData.getString("ci"));
                if ("LOCAL".equals(serverNm) || "DEV".equals(serverNm) || "STG".equals(serverNm)) {
                    kakaoDto.setConnInfo(StringUtils.isNotEmpty(kakaoDto.getConnInfo()) ? kakaoDto.getConnInfo().substring(0,kakaoDto.getConnInfo().length()-5) + "TEST"   : null);
                } else {
                    kakaoDto.setConnInfo(StringUtils.isNotEmpty(kakaoDto.getConnInfo()) ? EncryptUtil.ace256NoPaddingDec(enCodeKey, enCodeIv, kakaoDto.getConnInfo()) : null);
                }

                // ========== 본인인증 리턴 로직 개선 START ==========

                // 1. 본인인증 이력 저장 (as-is: req과 res 모두 requestToken 사용하고 있음.. 일단 유지)
                niceLogDto.setReqSeq(kakaoDto.getRequestToken());
                niceLogDto.setResSeq(kakaoDto.getRequestToken());
                niceLogDto.setAuthType(KAKAO_AUTH);
                niceLogDto.setName(kakaoDto.getName()); //*
                niceLogDto.setBirthDate(kakaoDto.getBirthday()); //*
                niceLogDto.setConnInfo(kakaoDto.getConnInfo());
                niceLogDto.setParamR1(ipstatisticService.getReferer());  //https://서버주소/kakaoVerify.do
                niceLogDto.setParamR3(CUST_AUTH);
                niceLogDto.setEndYn("Y");
                niceLogDto.setnReferer(ipstatisticService.getReferer()); //https://서버주소/kakaoVerify.do
                niceLogDto.setsMobileNo(kakaoDto.getPhone_No());
                long test = this.niceLogSvc.saveMcpNiceHist(niceLogDto);

                // 2. 화면 리턴값
                rtnLogDto.setReqSeq(kakaoDto.getRequestToken());
                rtnLogDto.setResSeq(kakaoDto.getRequestToken());
                rtnLogDto.setAuthType(KAKAO_AUTH);

                // ========== 본인인증 리턴 로직 개선 END ==========

                logger.info("kakao verify seq " + test);
                reMap.put("niceLogSeq", test);
            }
        }

        if (StringUtils.isNotEmpty(kakaoDto.getStatus())) {

            switch (kakaoDto.getStatus()) {
                case "REQUESTED" : // REQUESTED 요청중
                    reMap.put("RESULT_MSG", "인증요청중<br/>인증을 마무리 해주세요.");
                    break;
                case "COMPLETED" : // COMPLETED 인증완료
                    reMap.put("RESULT_MSG", "인증이 성공적으로 완료 되었습니다.");
                    break;
                case "EXPIRED" : // EXPIRED 인증시간만료
                    reMap.put("RESULT_MSG", "이용시간 (10분)이 지났으니<br/>재요청 해주세요.");
                    break;
                default:
                    reMap.put("RESULT_MSG", "재시도 바랍니다.");
                    break;
            }
            reMap.put("RESULT_STATUS", kakaoDto.getStatus());
        }
        reMap.put("RESULT_CODE", StringUtils.isNotEmpty(verifyStr) ? "200" : "9999");
        // reMap.put("niceLog", niceLogDto);
        reMap.put("niceLog", rtnLogDto);

        return reMap;
    }

}
