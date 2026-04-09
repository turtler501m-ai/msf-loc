package com.ktmmobile.msf.common.mplatform;

import static com.ktmmobile.msf.common.constants.Constants.EVENT_CODE_REPLACE_USIM_PRE_CHK;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.ktmmobile.msf.common.dao.MplatFormOsstDao;
import com.ktmmobile.msf.common.exception.McpMplatFormException;
import com.ktmmobile.msf.common.exception.SelfServiceException;
import com.ktmmobile.msf.common.mplatform.vo.CommonXmlVO;
import com.ktmmobile.msf.common.mplatform.vo.MpErrVO;
import com.ktmmobile.msf.common.service.IpStatisticService;
import com.ktmmobile.msf.common.util.HttpClientUtil;
import com.ktmmobile.msf.common.util.NmcpServiceUtils;

@Service
public class MsfMplatFormOsstWebServerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(MsfMplatFormOsstWebServerAdapter.class);

	@Value("${osst.url}")
	private String osstUrl;

	@Value("${SERVER_NAME}")
	private String serverLocation;

	@Autowired
	private IpStatisticService ipStatisticService;

	@Autowired
	private MplatFormOsstDao mplatFormOsstDao;

	public boolean callService(HashMap<String, String> param, CommonXmlVO vo) throws SelfServiceException, SocketTimeoutException {
		return callService(param, vo, 10000);
	}

	public boolean callService(HashMap<String,String> param, CommonXmlVO vo,int timeout) throws SelfServiceException, SocketTimeoutException{

		boolean result = false;
		String responseXml = "";

		try {

			String callUrl = osstUrl;
			HashMap<String, String> pMplatform = this.saveMplateSvcLog(param);
			String getURL = this.getURL(pMplatform);

			NameValuePair[] data = {
				new NameValuePair("getURL", getURL)
			};

			// 로컬에서 강제로 성공 처리
			if("LOCAL".equals(serverLocation)) {

				String appEventCd = param.get("appEventCd");
				if(EVENT_CODE_REPLACE_USIM_PRE_CHK.equals(appEventCd)) {

					StringBuffer selfStringBuffer = new StringBuffer();
					selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
					selfStringBuffer.append("  <soap:Body>");
					selfStringBuffer.append("    <ns2:osstNpBfacAgreeResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
					selfStringBuffer.append("      <return>");
					selfStringBuffer.append("	      <bizHeader>");
					selfStringBuffer.append("          <appEntrPrsnId>KIS</appEntrPrsnId>");
					selfStringBuffer.append("          <appAgncCd>AA00364</appAgncCd>");
					selfStringBuffer.append("          <appEventCd>TO1</appEventCd>");
					selfStringBuffer.append("          <appSendDateTime>20251103165222</appSendDateTime>");
					selfStringBuffer.append("          <appRecvDateTime>20251103165220</appRecvDateTime>");
					selfStringBuffer.append("          <appLgDateTime>20251103165220</appLgDateTime>");
					selfStringBuffer.append("          <appNstepUserId>91305414</appNstepUserId>");
					selfStringBuffer.append("          <appOrderId/>");
					selfStringBuffer.append("	      </bizHeader>");
					selfStringBuffer.append("	      <commHeader>");
					selfStringBuffer.append("          <globalNo>DEV_TEST_000007</globalNo>");
					selfStringBuffer.append("          <encYn/>");
					selfStringBuffer.append("          <responseType>N</responseType>");
					selfStringBuffer.append("          <responseCode></responseCode>");
					selfStringBuffer.append("          <responseLogcd/>");
					selfStringBuffer.append("          <responseTitle/>");
					selfStringBuffer.append("          <responseBasic></responseBasic>");
					selfStringBuffer.append("          <langCode/>");
					selfStringBuffer.append("          <filler/>");
					selfStringBuffer.append("	      </commHeader>");
					selfStringBuffer.append("	      <outDto>");
					selfStringBuffer.append("          <acceptDt>20251101000000</acceptDt>");
					selfStringBuffer.append("          <ctnStatus>A</ctnStatus>");
					selfStringBuffer.append("          <openDt>20250723</openDt>");
					selfStringBuffer.append("          <rsltCd>00</rsltCd>");
					selfStringBuffer.append("          <rsltMsg></rsltMsg>");
					selfStringBuffer.append("          <usimChgDt>20251030095005</usimChgDt>");
					selfStringBuffer.append("          <usimOnlyYn>N</usimOnlyYn>");
					selfStringBuffer.append("          <usimTypeCd>U</usimTypeCd>");
					selfStringBuffer.append("	      </outDto>");
					selfStringBuffer.append("      </return>");
					selfStringBuffer.append("    </ns2:osstNpBfacAgreeResponse>");
					selfStringBuffer.append("  </soap:Body>");
					selfStringBuffer.append("</soap:Envelope>");
					responseXml = selfStringBuffer.toString();
				}else{
					vo.setSuccess(true);
					result = true;
					return result;
				}

			}else{
				logger.info("*** M-PlatForm osstServiceCall Connect Start ***");
				responseXml = HttpClientUtil.post(callUrl, data, "UTF-8", timeout);
				logger.info("responseXml : " + responseXml);
			}

			if(responseXml == null || responseXml.isEmpty()){
				result = false;
				throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
			}else{
				if(vo != null){
					result = true;
					vo.setResponseXml(responseXml);
					vo.toResponseParse();
				}
			}

		}catch(SelfServiceException e){
			// responseType값이 N이 아닌 경우
			throw e;
		}catch(SocketTimeoutException e){
			// OSST 연동 타임아웃 이력 INSERT
			String customerId = param.get("custNo") != null ? param.get("custNo") : param.get("custId");
			MpErrVO errVO= new MpErrVO(param.get("resNo"), param.get("appEventCd"));
			errVO.setPrntsContractNo(param.get("prntsContractNo"));
			errVO.setCustomerId(customerId);
			errVO.setErrInfo(e);
			mplatFormOsstDao.insertOsstErrLog(errVO);
			throw e;
		}catch (McpMplatFormException e){
			// responseXml이 빈값인 경우
			throw e;
		}catch (Exception e){
			result = false;
		}

		return result;
	}

	private HashMap<String, String> saveMplateSvcLog(HashMap<String,String> param) {

		HashMap<String, String> tmpParm = param;

		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			tmpParm.put("ip", ipStatisticService.getClientIp());
			tmpParm.put("url", request.getRequestURI());
			tmpParm.put("mdlInd", NmcpServiceUtils.getDeviceType());
		} catch (Exception e) {
			logger.debug("saveMplateSvcLog 연동 정보 저장 오류={}", e.getMessage());
		}

		return tmpParm;
	}

	private String getURL(HashMap<String, String> param){

		String result = "";
		Set<String> keySet = param.keySet();

		for(String key : keySet){
			if(!result.equals("")){
				result = result.concat("&");
			}
			result = result.concat(key + "=" + param.get(key));
		}

		try {
			result = URLEncoder.encode(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("getURL UnsupportedEncodingException={}", e.getMessage());
		}

		return result;
	}

}
