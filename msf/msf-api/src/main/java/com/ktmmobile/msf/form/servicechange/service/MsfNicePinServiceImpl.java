package com.ktmmobile.msf.form.servicechange.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ktmmobile.msf.system.cert.dao.CertDao;
import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.dto.NiceLogDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.service.NiceLogSvc;
import com.ktmmobile.msf.system.common.util.CommonHttpClient;
import com.ktmmobile.msf.system.common.util.EncryptUtil;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MsfNicePinServiceImpl implements MsfNicePinService {

    private static Logger logger = LoggerFactory.getLogger(MsfNicePinServiceImpl.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${inf.url}")
    private String infUrl;

    @Value("${ciPrx.siteCode}")
    private String ciPrxSiteCode;

    @Value("${ciPrx.sitePw}")
    private String ciPrxSitePw;

    @Autowired
    CertService certService;

    @Autowired
    NiceLogSvc nicelog ;

    @Value("${ext.url}")
    private String extUrl;

    /**
     * 사용자 ci 조회 api-ext 연동
     * */
    @Override
    public Map<String, String> getNicePinCi(NiceLogDto niceLogDto) {
        Map<String,String> resultMap = new HashMap<>();

        // 로컬 강제 성공 처리
        if("LOCAL".equals(serverName)){
            String localConnInfo= "vTAH+121212+tCH5bVO/crf7t9a3w==";
            //MCP_NICE_HIST 이력 등록
            NiceLogDto paramDto = new NiceLogDto();
            paramDto.setName(niceLogDto.getName());
            paramDto.setBirthDate(niceLogDto.getBirthDate().substring(0, 6));
            paramDto.setConnInfo(localConnInfo);
            paramDto.setDupInfo("");
            paramDto.setAuthType("NP");
            nicelog.insertMcpNiceHist(paramDto);

            // ============ STEP START ============
            // 인증종류, 대리인구분값, 이름, 생년월일, CI
            String[] certKey= {"urlType", "moduType", "ncType", "name", "birthDate", "connInfo"};
            String[] certValue= {"nicePinAuth", "nicePin", niceLogDto.getNcType(), niceLogDto.getName(), EncryptUtil.ace256Enc(niceLogDto.getBirthDate()), localConnInfo};
            certService.vdlCertInfo("E", certKey, certValue);
            // ============ STEP END ============

            resultMap.put("returnCode", "0000");
            return resultMap;
        }

        resultMap = getNicePinAuth(niceLogDto, "CI");
        if ("0000".equals(resultMap.get("returnCode"))) {
            // ============ STEP START ============
            // 인증종류, 이름, 생년월일, CI
            String[] certKey= {"urlType", "moduType", "ncType", "name", "birthDate", "connInfo"};
            String[] certValue= {"nicePinAuth", "nicePin", niceLogDto.getNcType(), niceLogDto.getName(), EncryptUtil.ace256Enc(niceLogDto.getBirthDate()), resultMap.get("connInfo")};
            certService.vdlCertInfo("E", certKey, certValue);
            // ============ STEP END ============
        }
        return resultMap;
    }

    /**
     * 사용자 di 조회 api-ext 연동
     * */
    @Override
    public Map<String, String> getNicePinDi(NiceLogDto niceLogDto) {
        Map<String,String> resultMap = new HashMap<>();

        // 로컬 강제 성공 처리
        if("LOCAL".equals(serverName)){
            String localDupInfo= "MC0GCCqGSIb3DQIJAyEAQvLVSntViNyZ3COkfhJHV8WtApX6tN8lTMUsZuC5ao4=";
            //MCP_NICE_HIST 이력 등록
            NiceLogDto paramDto = new NiceLogDto();
            paramDto.setName(niceLogDto.getName());
            paramDto.setBirthDate(niceLogDto.getBirthDate().substring(0, 6));
            paramDto.setConnInfo("");
            paramDto.setDupInfo(localDupInfo);
            paramDto.setAuthType("NP");
            nicelog.insertMcpNiceHist(paramDto);

            resultMap.put("returnCode", "0000");
            resultMap.put("dupInfo", localDupInfo);
            return resultMap;
        }
        return getNicePinAuth(niceLogDto, "DI");
    }

    /**
     * 사용자 ci, di 조회 api-ext 연동
     * */
    public Map<String, String> getNicePinAuth(NiceLogDto niceLogDto, String type) {
        Map<String,String> resultMap = new HashMap<>();

        String url = "";
        if ("CI".equals(type)) {
            url = "/getNicePinCi.do";
        } else if ("DI".equals(type)) {
            url = "/getNicePinDi.do";
        }

        CommonHttpClient client = new CommonHttpClient(extUrl + url);
        String responseBody= null;

        // 1. prx 연동 데이터 세팅
        NameValuePair[] data = {
                new NameValuePair("sSiteCode", ciPrxSiteCode),  // NICE신용평가정보에서 발급한 사이트코드
                new NameValuePair("sSitePw", ciPrxSitePw),      // NICE신용평가정보에서 발급한 사이트패스워드
                new NameValuePair("sJumin", EncryptUtil.ace256Enc(niceLogDto.getBirthDate())), // 사용자 주민번호
                new NameValuePair("sFlag", "JID")
        };

        try {
            // 2. 연동결과 확인
            responseBody = client.postUtf8(data);

            if(StringUtil.isEmpty(responseBody)){
                throw new SocketTimeoutException();
            }

            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(responseBody);

            // 3. returnCode 확인
            String returnCode= jsonObject.get("returnCode").getAsString();

            if("0000".equals(returnCode)) {
                // 조회 성공
                String connInfo = "";
                String dupInfo = "";
                if ("CI".equals(type)) {
                    connInfo = jsonObject.get("connInfo").getAsString();
                    connInfo = EncryptUtil.ace256Dec(connInfo);
                } else if ("DI".equals(type)) {
                    dupInfo = jsonObject.get("dupInfo").getAsString();
                    dupInfo = EncryptUtil.ace256Dec(dupInfo);
                }

                //MCP_NICE_HIST 이력 등록
                NiceLogDto paramDto = new NiceLogDto();
                paramDto.setName(niceLogDto.getName());
                paramDto.setBirthDate(niceLogDto.getBirthDate().substring(0, 6));
                paramDto.setConnInfo(connInfo);
                paramDto.setDupInfo(dupInfo);
                paramDto.setAuthType("NP");
                nicelog.insertMcpNiceHist(paramDto);

                resultMap.put("returnCode", returnCode);
                resultMap.put("connInfo", connInfo);
                resultMap.put("dupInfo", dupInfo);
            }else{
                // 조회 실패
                resultMap.put("returnCode", returnCode);
                resultMap.put("returnMsg", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
            }

        }catch(SocketTimeoutException e) {
            resultMap.put("returnCode", "9999");
            resultMap.put("returnMsg", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
        } catch (Exception e) {
            resultMap.put("returnCode", "9998");
            resultMap.put("returnMsg", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
        }

        return resultMap;
    }
}
