package com.ktis.msp.rcp.rcpMgmt.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.rcp.rcpMgmt.EncryptUtil;
import com.ktis.msp.rcp.rcpMgmt.mapper.NiceLogMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.NiceLogVO;
import com.ktis.msp.util.CommonHttpClient;
import com.ktis.msp.util.StringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.SocketTimeoutException;
import java.util.HashMap;

@Service
public class NicePinService extends BaseService {

    private static final Log LOGGER = LogFactory.getLog(NicePinService.class);

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private NiceLogMapper niceLogMapper;

    public HashMap<String, Object> infNicePinCiCall(NiceLogVO niceLogVO) throws MvnoServiceException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        niceLogVO.setBirthDate(EncryptUtil.ace256Enc(niceLogVO.getCstmrRrn().substring(0, 6)));
        niceLogVO.setAuthType("NP");
        niceLogVO.setRip(this.getClientIp());
        niceLogVO.setnReferer(this.getReferer());

        // 로컬 강제 성공 처리
        if (this.getServerName().toLowerCase().indexOf("localhost") != -1){

            String localConnInfo= "vTAH+121212+tCH5bVO/crf7t9a3w==";

            niceLogVO.setConnInfo(localConnInfo);
            niceLogMapper.insertMcpNiceHist(niceLogVO);

            rtnMap.put("returnCode", "0000");
            rtnMap.put("connInfo", localConnInfo);
            return rtnMap;
        }

        try {
            String infUrlNicePin = propertiesService.getString("nicePinCi.prx.url");
            String responseBody = null;

            String ciPrxSiteCode = propertiesService.getString("ciPrx.siteCode");
            String ciPrxSitePw = propertiesService.getString("ciPrx.sitePw");

            // 1. prx 연동 데이터 세팅
            NameValuePair[] data = {
                    new NameValuePair("sSiteCode", ciPrxSiteCode),
                    new NameValuePair("sSitePw", ciPrxSitePw),
                    new NameValuePair("sJumin", EncryptUtil.ace256Enc(niceLogVO.getCstmrRrn())),
                    new NameValuePair("sFlag", "JID")
            };

            LOGGER.debug("*** NicePinCi Connect Start ***");
            LOGGER.debug("*** NicePinCi Call URL *** " + infUrlNicePin);
            LOGGER.debug("*** NicePinCi CIPrxSiteCode *** " + ciPrxSiteCode);
            LOGGER.debug("*** NicePinCi CIPrxSitePW *** " + ciPrxSitePw);
            LOGGER.debug("*** NicePinCi CstmrNativeRrn *** " + niceLogVO.getCstmrRrn());
            LOGGER.debug("*** NicePinCi ENC *** " + EncryptUtil.ace256Enc(niceLogVO.getCstmrRrn()));

            // 2. 연동결과 확인
            responseBody = CommonHttpClient.Mplatpost(infUrlNicePin, data, "UTF-8", 10000);

            LOGGER.debug("*** responseBody *** " + responseBody);

            if (StringUtil.isEmpty(responseBody)) {
                throw new SocketTimeoutException();
            }

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(responseBody);

            // 3. returnCode 확인
            String returnCode = jsonObject.get("returnCode").toString();

            if ("0000".equals(returnCode)) {

                // ci 조회 성공
                String connInfo = jsonObject.get("connInfo").toString();
                connInfo = EncryptUtil.ace256Dec(connInfo);

                // MCP_NICE_HIST 이력 등록
                niceLogVO.setConnInfo(connInfo);
                niceLogMapper.insertMcpNiceHist(niceLogVO);


                rtnMap.put("connInfo", connInfo);
                rtnMap.put("returnCode", returnCode);

            } else {
                // ci 조회 실패
                rtnMap.put("returnCode", returnCode);
                rtnMap.put("returnMsg", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
            }


        } catch (SocketTimeoutException e) {
            rtnMap.put("returnCode", "9999");
            rtnMap.put("returnMsg", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
        } catch (Exception e) {
            rtnMap.put("returnCode", "9998");
            rtnMap.put("returnMsg", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
        }

        return rtnMap;
    }

    public String getClientIp() {
        String clientIp = "";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if(request.getHeader("X-Forwarded-For") == null) {
            clientIp = request.getRemoteAddr();
        } else {
            clientIp = request.getHeader("X-Forwarded-For");
            if (clientIp != null && !clientIp.equals("") && clientIp.indexOf(",") > -1) {
                clientIp =  clientIp.split("\\,")[0].trim();
            }
        }

        return clientIp;
    }

    public String getReferer() {
        String referer = "";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        referer = request.getHeader("referer");

        return referer;

    }

    public String getServerName() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String serverNm = KtisUtil.isEmpty(request.getServerName()) ? "" : request.getServerName();

        return serverNm;
    }

}
