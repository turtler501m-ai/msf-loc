package com.ktis.msp.rcp.rcpMgmt.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.vo.EventRequest;
import com.ktis.msp.rcp.rcpMgmt.vo.MoscContCustInfoAgreeChgInVO;
import com.ktis.msp.rcp.rcpMgmt.vo.MoscPrdcTrtmVO;
import com.ktis.msp.rcp.rcpMgmt.vo.PrdcTrtmVO;
import com.ktis.msp.util.CommonHttpClient;
import com.ktis.msp.util.XmlParse;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

import static com.ktis.msp.rcp.rcpMgmt.service.UsimDirDlvryChnlService.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;

@Service
public class MplatformService {
	private static final Log LOGGER = LogFactory.getLog(MplatformService.class);

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	protected final static String HEADER = "commHeader";
	protected final static String GLOBAL_NO = "globalNo";
	protected final static String RESPONSE_TYPE = "responseType";
	protected final static String RESPONSE_CODE = "responseCode";
	protected final static String RESPONSE_BASIC = "responseBasic";

	public Map<String, Object> usimValidChk(Map<String, Object> paramMap) {

		Map<String, Object> result = null;

		HashMap<String,String> param = new HashMap<String,String>();
		param.put("appEventCd", "X85");
		param.put("iccid", (String) paramMap.get("reqUsimSn"));

		// 1. 유심유효성체크 (X85) 연동
		result = this.mplatformCall(param);
		return result;
	}

	public Map<String, Object> moscContCustInfoAgreeChg(MoscContCustInfoAgreeChgInVO vo) {

		Map<String, Object> result = null;

		HashMap<String,String> param = new HashMap<String,String>();
		param.put("appEventCd", "Y41");
		param.put("ncn", vo.getNcn());
		param.put("ctn", vo.getCtn());
		param.put("custId", vo.getCustId());
		param.put("othcmpInfoAdvrCnsgAgreYn", vo.getOthcmpInfoAdvrCnsgAgreYn());
		param.put("othcmpInfoAdvrRcvAgreYn", vo.getOthcmpInfoAdvrRcvAgreYn());
		param.put("fnncDealAgreeYn", vo.getFnncDealAgreeYn());
		param.put("grpAgntBindSvcSbscAgreYn", vo.getGrpAgntBindSvcSbscAgreYn());
		param.put("cardInsrPrdcAgreYn", vo.getCardInsrPrdcAgreYn());

		// 1. 개인정보 활용동의 변경 (Y41) 연동
		result = this.mplatformCall(param);
		return result;
	}

	/** 가입정보조회 (X01) */
	public Map<String, Object> perMyktfInfo(String ncn, String ctn, String custId) {

		Map<String, Object> result = null;

		HashMap<String,String> param = new HashMap<String,String>();
		param.put("appEventCd", "X01");
		param.put("ncn", ncn);
		param.put("ctn", ctn);
		param.put("custId", custId);

		result = this.mplatformCall(param);
		return result;
	}

	/** 상품 변경 사전체크 (Y24) */
	public Map<String, Object> regSvcChgAllChk(String ncn, String ctn, String custId, String prmtId) {

		Map<String, Object> result = null;

		HashMap<String,String> param = new HashMap<String,String>();
		param.put("appEventCd", "Y24");
		param.put("ncn", ncn);
		param.put("ctn", ctn);
		param.put("custId", custId);
		param.put("prmtId",	prmtId);
		param.put("actCode", "SRG");   //작업구분코드(PCN: 요금제 변경, SRG: 부가서비스 변경, RSV: 예약 처리)
		param.put("prdcSbscTrtmCd",	"A"); //상품가입처리코드(A: 가입, C: 해지, U: PARAM변경)

		result = this.mplatformCall(param);
		return result;
	}

	/** 상품 변경 처리 (Y25) */
	public Map<String, Object> regSvcChgAll(String ncn, String ctn, String custId, String prmtId) {

		Map<String, Object> result = null;

		HashMap<String,String> param = new HashMap<String,String>();
		param.put("appEventCd", "Y25");
		param.put("ncn", ncn);
		param.put("ctn", ctn);
		param.put("custId", custId);
		param.put("prmtId",	prmtId);
		param.put("actCode", "SRG");   //작업구분코드(PCN: 요금제 변경, SRG: 부가서비스 변경, RSV: 예약 처리)
		param.put("prdcSbscTrtmCd",	"A"); //상품가입처리코드(A: 가입, C: 해지, U: PARAM변경)

		result = this.mplatformCall(param);
		return result;
	}

	/** 부가서비스신청 (X21) */
	public Map<String, Object> regSvcChg(String ncn, String ctn, String custId, String soc) {

		Map<String, Object> result = null;

		HashMap<String,String> param = new HashMap<String,String>();
		param.put("appEventCd", "X21");
		param.put("ncn", ncn);
		param.put("ctn", ctn);
		param.put("custId", custId);
		param.put("soc", soc);

		result = this.mplatformCall(param);
		return result;
	}

	/** 부가서비스해지 (X38) */
	public Map<String, Object> moscRegSvcCanChg(String ncn, String ctn, String custId, String soc) {

		Map<String, Object> result = null;

		HashMap<String,String> param = new HashMap<String,String>();
		param.put("appEventCd", "X38");
		param.put("ncn", ncn);
		param.put("ctn", ctn);
		param.put("custId", custId);
		param.put("soc", soc);

		result = this.mplatformCall(param);
		return result;
	}

	/** 유심무상교체 가능여부 조회 (T01) */
	public Map<String, Object> usimChgAccept(String ncn, String ctn, String custId) {

		Map<String, Object> result = null;

		HashMap<String,String> param = new HashMap<String,String>();
		param.put("appEventCd", "T01");
		param.put("ncn", ncn);
		param.put("ctn", ctn);
		param.put("custId", custId);

		result = this.mplatformOsstCall(param);
		return result;
	}
	public static MoscPrdcTrtmVO getMoscPrdcTrtmVO(String ncn, String ctn, String custId, List<String> addSvcList, String prdcSbscTrtmCd) {
		MoscPrdcTrtmVO moscPrdcTrtmVO = new MoscPrdcTrtmVO();
		moscPrdcTrtmVO.setNcn(ncn);
		moscPrdcTrtmVO.setCtn(ctn);
		moscPrdcTrtmVO.setCustId(custId);
		moscPrdcTrtmVO.setActCode("SRG");
		List<PrdcTrtmVO> prdcList = new ArrayList<PrdcTrtmVO>();
		for (String addSvc : addSvcList) {
			PrdcTrtmVO prdcTrtmVO = new PrdcTrtmVO();
			prdcTrtmVO.setPrdcCd(addSvc);
			prdcTrtmVO.setPrdcSbscTrtmCd(prdcSbscTrtmCd);
			prdcTrtmVO.setPrdcTypeCd("R");
			prdcList.add(prdcTrtmVO);
		}
		moscPrdcTrtmVO.setPrdcList(prdcList);
		return moscPrdcTrtmVO;
	}

	// Y24(상품 변경 사전체크), Y25(상품 변경 처리) PRX 연동
	public Map<String, Object> moscPrdcTrtm(MoscPrdcTrtmVO moscPrdcTrtmVO, String appEventCd) {
		moscPrdcTrtmVO.setAppEventCd(appEventCd);
		Map<String, Object> resultMap = this.mplatformCallJson(moscPrdcTrtmVO);
		if (!"0000".equals(resultMap.get("code"))) {
			return resultMap;
		}
		resultMap.put("code", resultMap.get("rsltCd"));
		resultMap.put("msg", resultMap.get("rsltMsg"));
		return resultMap;
	}

	public HashMap<String, Object> mplatformCall(HashMap<String, String> param) {

		String responseXml = "";
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String serverNm= KtisUtil.isEmpty(request.getServerName()) ? "" : request.getServerName();
		this.putMplatformLogParameter(param);

		try{
			if (serverNm.toLowerCase().indexOf("localhost") != -1){
				if ("X85".equals(param.get("appEventCd"))) {
					responseXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscInqrUsimUsePsblResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X85</appEventCd><appSendDateTime>20230524171323</appSendDateTime><appRecvDateTime>20230524171321</appRecvDateTime><appLgDateTime>20230524171321</appLgDateTime><appNstepUserId>91083549</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>moscInqrUsimUsePsbl_202305240001</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><psblYn>Y</psblYn></outDto></return></ns2:moscInqrUsimUsePsblResponse></soap:Body></soap:Envelope>";
				} else if("X01".equals(param.get("appEventCd"))){
					// 이메일 존재
					responseXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscPerInfoResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>test@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return></ns2:moscPerInfoResponse></soap:Body></soap:Envelope>";
					// 이메일 미존재
					//responseXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscPerInfoResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email></email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return></ns2:moscPerInfoResponse></soap:Body></soap:Envelope>";
					// 연동오류
					//responseXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscPerInfoResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>E</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>NSTEP ESB 연동 오류.</responseBasic><langCode></langCode><filler></filler></commHeader></return></ns2:moscPerInfoResponse></soap:Body></soap:Envelope>";
				} else {
					//1. 성공
					responseXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscRegSvcChgResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>INL</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20150206155656</appSendDateTime><appRecvDateTime>20150206155559</appRecvDateTime><appLgDateTime>20150206155559</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201502061201011234</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader></return></ns2:moscRegSvcChgResponse></soap:Body></soap:Envelope>";
					//2. 실패
					//responseXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscRegSvcChgResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>INL</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20150206155656</appSendDateTime><appRecvDateTime>20150206155559</appRecvDateTime><appLgDateTime>20150206155559</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201502061201011234</globalNo><encYn/><responseType>E</responseType><responseCode>ITL_SFC_E041</responseCode><responseLogcd/><responseTitle/><responseBasic>해당 부가서비스는 신청 할수있는 상품이 아닙니다.</responseBasic><langCode/><filler/></commHeader></return></ns2:moscRegSvcChgResponse></soap:Body></soap:Envelope>";
					//3. 연동 오류 실패
					//responseXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscRegSvcChgResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>INL</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20150206155656</appSendDateTime><appRecvDateTime>20150206155559</appRecvDateTime><appLgDateTime>20150206155559</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201502061201011234</globalNo><encYn/><responseType>E</responseType><responseCode>ITL_SYS_E0001</responseCode><responseLogcd/><responseTitle/><responseBasic>NSTEP ESB 연동 오류.</responseBasic><langCode/><filler/></commHeader></return></ns2:moscRegSvcChgResponse></soap:Body></soap:Envelope>";
				}
				LOGGER.error("====1. responseXml 생성 완료");
			} else {

				String getURL = this.getURL(param);
				String callUrl = (String) propertiesService.getString("mplatform.selfcare.url");

				org.apache.commons.httpclient.NameValuePair[] data = {
						new org.apache.commons.httpclient.NameValuePair("getURL", getURL)
				};

				LOGGER.debug("*** M-PlatForm Connect Start ***");
				LOGGER.debug("*** M-PlatForm Call URL *** " + callUrl);
				LOGGER.debug("*** M-PlatForm Data *** " + data);

				responseXml = CommonHttpClient.Mplatpost(callUrl,data, "UTF-8", 10000);

				LOGGER.debug("responseXml : " + responseXml);
			}

			if (responseXml.isEmpty()) {
				resultMap.put("code", "9998");
				resultMap.put("msg", MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
			} else {
				resultMap = this.toResponseParse(responseXml, param.get("appEventCd"));
				if(!"N".equals(resultMap.get("responseType"))){
					resultMap.put("code", resultMap.get("responseCode"));
					resultMap.put("msg", resultMap.get("responseBasic"));
				} else {
					resultMap.put("code", "0000");
					resultMap.put("msg", "성공");
				}
			}
			LOGGER.debug("*** M-PlatForm Connect End ***");

		} catch (Exception e) {
			resultMap.put("code", "9999");
			if ("".equals(Util.NVL(e.getMessage(), ""))) {
				resultMap.put("msg", "PRX 연동 오류");
			} else {
				resultMap.put("msg", "PRX 연동 오류 ["+e.getMessage() + ("]"));
			}
			LOGGER.error("MPLATFORM 전송시 문제가 생긴다면 강제로 메시지 세팅 후 정상 처리.");
		}
		return resultMap;
	}

	public HashMap<String, Object> mplatformCallJson(EventRequest eventRequest) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			this.setMplatformLogParametersToRequest(eventRequest);
			String mplatformJsonUrl = propertiesService.getString("mplatform.selfcare.json.url");
			RestTemplate restTemplate = new RestTemplate();

			SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
			factory.setConnectTimeout(5000);
			factory.setReadTimeout(10000);
			restTemplate.setRequestFactory(factory);

			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "text/plain; charset=UTF-8");
			HttpEntity<EventRequest> entity = new HttpEntity<EventRequest>(eventRequest, headers);

			byte[] responseBtye = restTemplate.exchange(mplatformJsonUrl, HttpMethod.POST, entity, byte[].class).getBody();
			String responseXml = new String(responseBtye, "UTF-8");

			if (responseXml == null || responseXml.isEmpty()) {
				resultMap.put("code", "9998");
				resultMap.put("msg", MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
			} else {
				resultMap = this.toResponseParse(responseXml, eventRequest.getAppEventCd());
				if(!"N".equals(resultMap.get("responseType"))){
					resultMap.put("code", resultMap.get("responseCode"));
					resultMap.put("msg", resultMap.get("responseBasic"));
				} else {
					resultMap.put("code", "0000");
					resultMap.put("msg", "성공");
				}
			}

		} catch (Exception e) {
			resultMap.put("code", "9999");
			if ("".equals(Util.NVL(e.getMessage(), ""))) {
				resultMap.put("msg", "PRX 연동 오류");
			} else {
				resultMap.put("msg", "PRX 연동 오류 ["+e.getMessage() + ("]"));
			}
			LOGGER.error("MPLATFORM 전송시 문제가 생긴다면 강제로 메시지 세팅 후 정상 처리.");
		}
		return resultMap;
	}

	public HashMap<String, Object> mplatformOsstCall(HashMap<String, String> param) {

		String responseXml = "";
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String serverNm= KtisUtil.isEmpty(request.getServerName()) ? "" : request.getServerName();
		this.putMplatformLogParameter(param);

		try{
			String getURL = this.getURL(param);
			String callUrl = (String) propertiesService.getString("mplatform.osst.url");

			org.apache.commons.httpclient.NameValuePair[] data = {
					new org.apache.commons.httpclient.NameValuePair("getURL", getURL)
			};

			LOGGER.debug("*** M-PlatForm Connect Start ***");
			LOGGER.debug("*** M-PlatForm Call URL *** " + callUrl);
			LOGGER.debug("*** M-PlatForm Data *** " + data);

			responseXml = CommonHttpClient.Mplatpost(callUrl,data, "UTF-8", 10000);

			LOGGER.debug("responseXml : " + responseXml);
		

			if (responseXml.isEmpty()) {
				resultMap.put("code", "9998");
				resultMap.put("msg", MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
			} else {
				resultMap = this.toResponseParse(responseXml, param.get("appEventCd"));
				if(!"N".equals(resultMap.get("responseType"))){
					resultMap.put("code", resultMap.get("responseCode"));
					resultMap.put("msg", resultMap.get("responseBasic"));
				} else {
					resultMap.put("code", "0000");
					resultMap.put("msg", "성공");
				}
			}
			LOGGER.debug("*** M-PlatForm Connect End ***");

		} catch (Exception e) {
			resultMap.put("code", "9999");
			if ("".equals(Util.NVL(e.getMessage(), ""))) {
				resultMap.put("msg", "PRX 연동 오류");
			} else {
				resultMap.put("msg", "PRX 연동 오류 ["+e.getMessage() + ("]"));
			}
			LOGGER.error("MPLATFORM 전송시 문제가 생긴다면 강제로 메시지 세팅 후 정상 처리.");
		}
		return resultMap;
	}



	private void setMplatformLogParametersToRequest(EventRequest eventRequest) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = request.getSession();

		eventRequest.setIp(getClientIp());
		eventRequest.setUrl(request.getRequestURI());
		eventRequest.setMdlInd("MSP");

		if (session != null) {
			eventRequest.setUserid((String) session.getAttribute("SESSION_USER_ID"));
		}
	}

	private void putMplatformLogParameter(HashMap<String, String> param) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = request.getSession();

		param.put("url", request.getRequestURI());
		param.put("ip", this.getClientIp());
		param.put("mdlInd", "MSP");

		if (session != null) {
			param.put("userid", (String) session.getAttribute("SESSION_USER_ID"));
		}
	}

	private String getClientIp()  {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

		if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
			ipAddr = request.getHeader("REMOTE_ADDR");

		if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
			ipAddr = request.getRemoteAddr();

		return ipAddr;
	}

	private String getURL(HashMap<String, String> param) {
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
			LOGGER.error(e);
		}
		return result;
	}

	/**
	 * xml 파싱
	 * @param responseXml
	 * @return HashMap<String, Object>
	 * @throws JDOMException
	 * @throws IOException
	 */
	private HashMap<String, Object> toResponseParse(String responseXml, String appEventCd) throws IOException, JDOMException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Element root = XmlParse.getRootElement("<?xml version=\"1.0\" encoding=\"euc-kr\"?>"+responseXml);
		Element rtn = XmlParse.getReturnElement(root);

		Element commHeader = XmlParse.getChildElement(rtn, HEADER);
		resultMap.put("globalNo",     XmlParse.getChildValue(commHeader, GLOBAL_NO));
		resultMap.put("responseType", XmlParse.getChildValue(commHeader, RESPONSE_TYPE));
		resultMap.put("responseCode", XmlParse.getChildValue(commHeader, RESPONSE_CODE));
		resultMap.put("responseBasic", XmlParse.getChildValue(commHeader, RESPONSE_BASIC));

		if ("X85".equals(appEventCd)) {
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			resultMap.put("psblYn", XmlParse.getChildValue(outDto, "psblYn"));
		} else if ("X01".equals(appEventCd)) {
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			resultMap.put("email", XmlParse.getChildValue(outDto, "email"));
			resultMap.put("addr", XmlParse.getChildValue(outDto, "addr"));
			resultMap.put("homeTel", XmlParse.getChildValue(outDto, "homeTel"));
			resultMap.put("initActivationDate",  XmlParse.getChildValue(outDto, "initActivationDate"));
		} else if ("Y24".equals(appEventCd) || "Y25".equals(appEventCd)) {
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			resultMap.put("rsltCd", XmlParse.getChildValue(outDto, "rsltCd"));
			resultMap.put("rsltMsg", XmlParse.getChildValue(outDto, "rsltMsg"));
		} else if ("T01".equals(appEventCd)) {
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			resultMap.put("rsltCd", XmlParse.getChildValue(outDto, "rsltCd"));
			resultMap.put("rsltMsg", XmlParse.getChildValue(outDto, "rsltMsg"));
			resultMap.put("ctnStatus", XmlParse.getChildValue(outDto, "ctnStatus"));
			resultMap.put("openDt", XmlParse.getChildValue(outDto, "openDt"));
			resultMap.put("usimChgDt", XmlParse.getChildValue(outDto, "usimChgDt"));
			resultMap.put("acceptDt", XmlParse.getChildValue(outDto, "acceptDt"));
			resultMap.put("usimOnlyYn", XmlParse.getChildValue(outDto, "usimOnlyYn"));
			resultMap.put("usimTypeCd", XmlParse.getChildValue(outDto, "usimTypeCd"));
		} else if ("Y07".equals(appEventCd)){
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			resultMap.put("intmMdlId", XmlParse.getChildValue(outDto, "intmMdlId"));
			resultMap.put("intmSeq", XmlParse.getChildValue(outDto, "intmSeq"));
			resultMap.put("pukNo1", XmlParse.getChildValue(outDto, "pukNo1"));
		}

		return resultMap;
	}
	
	/** USIM PUK 번호 조회 조회 (Y07) */
	public Map<String, Object> usimPukAccept(String ncn, String ctn, String custId) {

		Map<String, Object> result = null;

		HashMap<String,String> param = new HashMap<String,String>();
		param.put("appEventCd", "Y07");
		param.put("ncn", ncn);
		param.put("ctn", ctn);
		param.put("custId", custId);

		result = this.mplatformCall(param);
		return result;
	}
}
