package com.ktis.msp.rcp.rcpMgmt.service;

import com.ktis.msp.base.mvc.BaseService;

import com.ktis.msp.rcp.rcpMgmt.vo.HubOrderVO;
import com.ktis.msp.util.CommonHttpClient;
import com.ktis.msp.util.StringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.SocketTimeoutException;
import java.util.HashMap;

@Service
public class HubService extends BaseService {

    private static final Log LOGGER = LogFactory.getLog(HubService.class);

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;


    public HashMap<String, Object> getHubOrder(HubOrderVO vo) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        try {
            String url = propertiesService.getString("ext.url") + "/hub/getHubOrder.do";
            String orderSeq = vo.getOrderSeq();
            String sessionUserId = vo.getSessionUserId();
            String responseBody = "";

            // 1. prx 연동 데이터 세팅
            NameValuePair[] data = {
                    new NameValuePair("orderSeq", orderSeq),
                    new NameValuePair("sessionUserId", sessionUserId)
            };

            LOGGER.debug("*** getHubOrder Connect Start ***");
            LOGGER.debug("*** getHubOrder orderSeq  *** " + orderSeq);
            // 2. 연동결과 확인
            responseBody = CommonHttpClient.Mplatpost(url, data, "UTF-8", 10000);

            if (StringUtil.isEmpty(responseBody)) {
                throw new SocketTimeoutException();
            }

            rtnMap = this.checkHubOrderResponse(responseBody);
        } catch (SocketTimeoutException e) {
            rtnMap.put("returnCode", "9999");
            rtnMap.put("returnMsg", "시스템 장애로 인하여 통신이 원활하지 않습니다. 관리부서에 문의 바랍니다.");
        } catch (Exception e) {
            rtnMap.put("returnCode", "9998");
            rtnMap.put("returnMsg", "시스템 장애로 인하여 통신이 원활하지 않습니다. 관리부서에 문의 바랍니다.");
        }

        return rtnMap;
    }

    private HashMap<String, Object> checkHubOrderResponse(String responseBody) throws ParseException {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        JSONObject resObj = (JSONObject) new JSONParser().parse(responseBody);

        String returnCode = resObj.get("status").toString().replaceAll("\"", "");
        String returnMsg = resObj.get("msg").toString().replaceAll("\"", "");

        LOGGER.debug("*** getHubOrder returnCode  *** " + returnCode);
        LOGGER.debug("*** getHubOrder returnMsg  *** " + returnMsg);

        if ("200".equals(returnCode)) {
            JSONObject resultObj = (JSONObject) resObj.get("result");

            if(resultObj == null) {
                rtnMap.put("returnCode", "1001");
                rtnMap.put("returnMsg", "주문 정보가 존재하지 않습니다.");
                return rtnMap;
            } else {
                LOGGER.debug("*** getHubOrder resultObj  *** " + resultObj);

                if(!"01".equals(resultObj.get("orderState"))) {
                    rtnMap.put("returnCode", "2001");
                    rtnMap.put("returnMsg", "처리상태가 '주문접수'인 주문만 조회 가능합니다.");
                    return rtnMap;
                }

                // 미성년자의 경우 guardCI 필수
                if("J".equals(resultObj.get("customerType"))){
                    if(resultObj.get("guardCI") == null || "".equals(resultObj.get("guardCI"))){
                        rtnMap.put("returnCode", "3001");
                        rtnMap.put("returnMsg", "법정대리인 본인인증 정보가 존재하지 않아 조회에 실패했습니다.");
                        return rtnMap;
                    }
                } else {
                    // 미성년자 외 userCI 필수
                    if(resultObj.get("userCI") == null || "".equals(resultObj.get("userCI"))){
                        rtnMap.put("returnCode", "3002");
                        rtnMap.put("returnMsg", "본인인증 정보가 존재하지 않아 조회에 실패했습니다.");
                        return rtnMap;
                    }
                }
                // 주문정보 조회 성공
                rtnMap.put("data", resultObj);
                rtnMap.put("returnCode", returnCode);
                rtnMap.put("returnMsg", returnMsg);
            }
        } else if("400".equals(returnCode)){
            rtnMap.put("returnCode", returnCode);
            rtnMap.put("returnMsg", returnMsg);
        } else{
            rtnMap.put("returnCode", returnCode);
            rtnMap.put("returnMsg", "시스템 장애로 인하여 통신이 원활하지 않습니다. 관리부서에 문의 바랍니다.");
        }

        return rtnMap;
    }
}
