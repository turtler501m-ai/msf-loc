 package com.ktis.msp.rcp.rcpMgmt.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.rcpMgmt.vo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.org.prmtmgmt.mapper.PromotionMapper;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.mapper.OsstCallMapper;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpMgmtMapper;
import com.ktis.msp.util.XmlParse;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

 /**
 * @author SJLEE
 *
 */
@Service("nStepCallService")
public class NstepCallService {
	private static final Log LOGGER = LogFactory.getLog(NstepCallService.class);
	
	@Autowired
	private RcpMgmtMapper rcpMgmtMapper;
	
	@Autowired
	private OsstCallMapper osstCallMapper;
	
	@Autowired
	private PromotionMapper promotionMapper;


	@Autowired
	private FathService fathService;

	protected final static String HEADER = "commHeader";
	protected final static String GLOBAL_NO = "globalNo";
	protected final static String RESPONSE_TYPE = "responseType";
	protected final static String RESPONSE_CODE = "responseCode";
	protected final static String RESPONSE_BASIC = "responseBasic";
	
	@Crypto(decryptName="DBMSDec", fields = {"cstmr_native_rrn", 
											 "cstmr_foreigner_rrn", 
											 "req_account_number", 
											 "req_card_no", 
											 "minor_agent_rrn",
											 "cstmr_foreigner_pn",
											 "others_payment_rrn",
											 "jrdcl_agent_rrn",
											 "entrust_res_rrn",
											 "name_change_rrn",
											 "req_account_rrn",
											 "req_card_rrn"
	})
	public NstepQueryVo decryptDBMS(NstepQueryVo vo){
		return vo;
	}
	 
	public boolean isScanFile(String requestKey){
		int cnt = rcpMgmtMapper.scanFileCount(requestKey);
		return cnt > 0;
	}
	
	public NstepQueryVo getNstepCallData(String requestKey) {
		
		// -------------------------------------------------------------------------
		// Initialize. 
		// -------------------------------------------------------------------------
		NstepQueryVo nStepVo = new NstepQueryVo();
		try {
			//String queryId = "nStep.getNstepCallDataByRequestKey";
			nStepVo = rcpMgmtMapper.getNstepCallDataByRequestKey(requestKey);
		} catch (Exception e) {
			LOGGER.error(e);
		}		
		// -------------------------------------------------------------------------
		// Service Process.
		// -------------------------------------------------------------------------
		
		return nStepVo;
	}
	
	/**
	 * N-STEP전송
	 */
	public HashMap<String, String> infNstepCall(String infNstepUrl, NstepQueryVo nStepVo) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		HttpClient httpclient = null;
		try {
			URI uri = new URI(infNstepUrl) ;
			httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Field[] fields = nStepVo.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					String fieldsName = fields[i].getName();
					Object tempData =   nStepVo.getClass().getDeclaredField(fieldsName).get(nStepVo); 
					String data = tempData == null ? "" : String.valueOf(tempData);

					if(!data.equals("")) { 
						params.add(new BasicNameValuePair(fieldsName, data)); 
					}
				} catch (Exception e) {
					LOGGER.error(Util.getPrintStackTrace(e));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpPost);  
			org.apache.http.StatusLine line =  response.getStatusLine();
			httpclient.getConnectionManager().shutdown();
			/** 20230518 PMD 조치 */
			String status = String.valueOf(line.getStatusCode());
			String result =  "";
			if(status.equals("200")) {
				InputStream in = null;
				BufferedReader reader = null;
				try {
					HttpEntity responseEntity = response.getEntity();
					StringBuffer sb = new StringBuffer();
					in = responseEntity.getContent();
					reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
					String lineStr = "";
					while((lineStr = reader.readLine()) != null ) {
						sb.append(lineStr);
					}
					result = sb.toString();
				} catch(Exception ex) {
					throw ex;
				}finally {
					in.close();
					reader.close();
				}
			} else {
				throw new Exception();
			}

			if(result.length() > 1) {
				String[] tempArray = result.split(",");

				resultMap.put("code", tempArray[0]);
				resultMap.put("msg", tempArray[1]);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			resultMap.put("code", "-999");
			resultMap.put("msg",  "KOS전산 지연으로 인해 잠시 후 이용바랍니다.");
			LOGGER.error("N-STEP 전송시 문제가 생긴다면 강제로 메시지 세팅 후 정상 처리.");
			LOGGER.error(Util.getPrintStackTrace(e)); 
		}
		return resultMap;
	}

	/**
	 * 개통간소화 OSST 연동
	 */
	public Map<String, Object> osstServiceCall(String osstMplatformUrl, OsstReqVO searchVO) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HttpClient httpclient = null;
		
		try {
			URI uri = new URI(osstMplatformUrl) ;
			httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Field[] fields = searchVO.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					String fieldsName = fields[i].getName();
					Object tempData = searchVO.getClass().getDeclaredField(fieldsName).get(searchVO);
					String data = tempData == null ? "" : String.valueOf(tempData);
					if(!"".equals(data)) {
						params.add(new BasicNameValuePair(fieldsName, data));
					}
				} catch (Exception e) {
					LOGGER.error(Util.getPrintStackTrace(e));
				}
			}

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpPost);
			org.apache.http.StatusLine line =  response.getStatusLine();
			httpclient.getParams().setParameter("http.connection.timeout", 5 * 1000);
			httpclient.getParams().setParameter("http.socket.timeout", 5 * 1000);
//			httpclient.getConnectionManager().shutdown();

			String status = String.valueOf(line.getStatusCode());
			StringBuffer sb = new StringBuffer();
			String responseXml =  "";
			/** 20230518 PMD 조치 */
			if(status.equals("200")) {
				InputStream in = null;
				BufferedReader reader = null;
				try {
					HttpEntity responseEntity = response.getEntity();
					in = responseEntity.getContent();
					reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
					String lineStr = "";
					while((lineStr = reader.readLine()) != null ) {
						sb.append(lineStr);
					}
					responseXml = sb.toString();
				} catch(Exception ex) {
					throw ex;
				}finally {
					in.close();
					reader.close();
				}
			} else {
				throw new Exception();
			}

			// 테스트용
//			responseXml = getST1Result().toString();
			LOGGER.debug("OSST XML=" + responseXml);

			// XML -> JSON
			if(responseXml.length() > 1){
				HashMap<String, Object> returnMap = this.toResponseParse(responseXml, searchVO.getAppEventCd());

				LOGGER.debug("returnMap=" + returnMap);

				resultMap.put("code", (String) returnMap.get("rsltCd"));
				resultMap.put("msg", (String) returnMap.get("rsltMsg"));

				// 연동결과 데이터
				resultMap.put("osstRst", returnMap);

				// 연동실패
				if (!"N".equals((String) returnMap.get("responseType"))) {
					resultMap.put("code", (String) returnMap.get("responseCode"));
					resultMap.put("msg", (String) returnMap.get("responseBasic"));
				} else {
					// 연동성공 && rsltCd 값이 존재할때 
					if ("N".equals((String) returnMap.get("responseType")) && (returnMap.get("rsltCd") == null || "".equals((String)returnMap.get("rsltCd")))) {
						resultMap.put("code", "S");
					}
				}

			} else {
				throw new Exception();
			}

			// 커넥션 close
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			resultMap.put("code", "9999");
			resultMap.put("msg",  "MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
			LOGGER.error("MPLATFORM 전송시 문제가 생긴다면 강제로 메시지 세팅 후 정상 처리.");
			throw new MvnoErrorException(e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return resultMap;
	}
	
	/**
	 * 개통간소화 OSST 연동(번호조회)
	 */
	public List<HashMap<String, Object>> getOsstSvcNoList(String osstMplatformUrl, OsstReqVO searchVO) {

		List<HashMap<String, Object>> svcNoList = new ArrayList();
		HttpClient httpclient = null;

		try {
			URI uri = new URI(osstMplatformUrl) ;
			httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Field[] fields = searchVO.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					String fieldsName = fields[i].getName();
					Object tempData = searchVO.getClass().getDeclaredField(fieldsName).get(searchVO);
					String data = tempData == null ? "" : String.valueOf(tempData);
					if(!"".equals(data)) {
						params.add(new BasicNameValuePair(fieldsName, data));
					}
				} catch (Exception e) {
					LOGGER.error(Util.getPrintStackTrace(e));
				}
			}

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpPost);
			org.apache.http.StatusLine line =  response.getStatusLine();
			httpclient.getParams().setParameter("http.connection.timeout", 15 * 1000);
			httpclient.getParams().setParameter("http.socket.timeout", 15 * 1000);
//			httpclient.getConnectionManager().shutdown();
			/** 20230518 PMD 조치 */
			String status = String.valueOf(line.getStatusCode());
			StringBuffer sb = new StringBuffer();
			String responseXml =  "";

			if(status.equals("200")) {
				InputStream in = null;
				BufferedReader reader = null;
				try {
					HttpEntity responseEntity = response.getEntity();
					in = responseEntity.getContent();
					reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
					String lineStr = "";
					while((lineStr = reader.readLine()) != null ) {
						sb.append(lineStr);
					}
					responseXml = sb.toString();
				} catch(Exception ex) {
					throw ex;
				}finally {
					in.close();
					reader.close();
				}
			} else {
				throw new Exception();
			}

			// 테스트용
//			responseXml = getNU1Result().toString();

			LOGGER.debug("OSST XML=" + responseXml);

			if(responseXml.length() > 1){
				HashMap<String, Object> returnMap = this.toResponseParse(responseXml, searchVO.getAppEventCd());

				LOGGER.debug("returnMap=" + returnMap);

				svcNoList = (List<HashMap<String, Object>>) returnMap.get("list");
			}else{
				throw new Exception();
			}

			// 커넥션 close
			httpclient.getConnectionManager().shutdown();

		} catch (Exception e) {
			svcNoList = null;
//			resultMap.put("code", "9999");
//			resultMap.put("msg",  "MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
			LOGGER.error("MPLATFORM 전송시 문제가 생긴다면 강제로 메시지 세팅 후 정상 처리.");
			throw new MvnoErrorException(e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return svcNoList;
	}
	
	/**
	 * 개통간소화 OSST 연동(번호예약/취소)
	 */
	public HashMap<String, Object> setOsstSvcNoRsv(String osstMplatformUrl, OsstReqVO searchVO) {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HttpClient httpclient = null;
		
		try {
			URI uri = new URI(osstMplatformUrl) ;
			httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Field[] fields = searchVO.getClass().getDeclaredFields();
			
			for (int i = 0; i < fields.length; i++) {
				try {
					String fieldsName = fields[i].getName();
					Object tempData = searchVO.getClass().getDeclaredField(fieldsName).get(searchVO); 
					String data = tempData == null ? "" : String.valueOf(tempData);
					if(!"".equals(data)) { 
						params.add(new BasicNameValuePair(fieldsName, data)); 
					}
				} catch (Exception e) {
					LOGGER.error(Util.getPrintStackTrace(e));
				}
			}
			
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpPost);
			org.apache.http.StatusLine line =  response.getStatusLine();
			httpclient.getParams().setParameter("http.connection.timeout", 5 * 1000);
			httpclient.getParams().setParameter("http.socket.timeout", 5 * 1000);
//			httpclient.getConnectionManager().shutdown();
			
			String status = String.valueOf(line.getStatusCode());
			StringBuffer sb = new StringBuffer();
			String responseXml =  "";
			/** 20230518 PMD 조치 */
			if(status.equals("200")) {
				InputStream in = null;
				BufferedReader reader = null;
				try {
					HttpEntity responseEntity = response.getEntity();
					in = responseEntity.getContent();
					reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
					String lineStr = "";
					while((lineStr = reader.readLine()) != null ) {
						sb.append(lineStr);
					}
					responseXml = sb.toString();
				} catch(Exception ex) {
					throw ex;
				}finally {
					in.close();
					reader.close();
				}
			} else {
				throw new Exception();
			}
			
			// 테스트용
//			responseXml = getNU2Result().toString();
			
			LOGGER.debug("OSST XML=" + responseXml);
			
			// XML -> JSON
			if(responseXml.length() > 1){
				Map<String, Object> returnMap = this.toResponseParse(responseXml, searchVO.getAppEventCd());
				
				LOGGER.debug("returnMap=" + returnMap);
				
				resultMap.put("code", (String) returnMap.get("rsltCd"));
				resultMap.put("msg", (String) returnMap.get("rsltMsg"));
			}else{
				throw new Exception();
			}
			
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
//			resultMap.put("code", "9999");
//			resultMap.put("msg",  "MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
			LOGGER.error("MPLATFORM 전송시 문제가 생긴다면 강제로 메시지 세팅 후 정상 처리.");
			throw new MvnoErrorException(e); 
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		
		return resultMap;
	}
	
	/**
	 * 개통간소화 OSST 연동(번호예약/취소)
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setRequestOsstHist(OsstReqVO searchVO) {
		osstCallMapper.insertRequestOsstHist(searchVO);
	}
	
	/**
	 * TCP 결과조회
	 * @param searchVO
	 */
	public OsstResVO getTcpResult(OsstReqVO searchVO, String prgrStatCd){
		
		OsstReqVO osstReqVO = new OsstReqVO();
		
		osstReqVO.setResNo(searchVO.getResNo());
		osstReqVO.setAppEventCd(prgrStatCd);
		
		return osstCallMapper.getTcpResult(osstReqVO);
		
	}
	
	/**
	 * 번호이동사전인증
	 * @param searchVO
	 * @return
	 */
	public OsstResVO getNpReqInfo(OsstReqVO searchVO){
		return osstCallMapper.getNpReqInfo(searchVO);
	}
	
	/**
	 * xml 파싱
	 * @param responseXml
	 * @param appEventCd
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public HashMap<String, Object> toResponseParse(String responseXml, String appEventCd) throws JDOMException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Element root = XmlParse.getRootElement("<?xml version=\"1.0\" encoding=\"euc-kr\"?>"+responseXml);
		Element rtn = XmlParse.getReturnElement(root);
		
		Element commHeader = XmlParse.getChildElement(rtn, HEADER);
		resultMap.put("globalNo",     XmlParse.getChildValue(commHeader, GLOBAL_NO));
		resultMap.put("responseType", XmlParse.getChildValue(commHeader, RESPONSE_TYPE));
		resultMap.put("responseCode", XmlParse.getChildValue(commHeader, RESPONSE_CODE));
		resultMap.put("responseBasic", XmlParse.getChildValue(commHeader, RESPONSE_BASIC));
		
		if("ST1".equals(appEventCd)){
			resultMap.put("rsltCd",  XmlParse.getChildValue(rtn, "rsltCd"));
			resultMap.put("rsltMsg", XmlParse.getChildValue(rtn, "rsltMsg"));
		}
		else if("NU1".equals(appEventCd)){
			List<HashMap<String, Object>> list = new ArrayList();
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			List<Element> svcNoList = XmlParse.getChildElementList(outDto, "svcNoList");
			
			LOGGER.debug("svcNoList.size()=" + svcNoList.size());
			for(Element tlphNo : svcNoList){
//				LOGGER.debug("*** tlphNo=" + XmlParse.getChildValue(tlphNo, "tlphNo"));
//				LOGGER.debug("*** encdTlphNo=" + XmlParse.getChildValue(tlphNo, "encdTlphNo"));
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("tlphNoStatCd",         XmlParse.getChildValue(tlphNo, "tlphNoStatCd"));
				map.put("asgnAgncId",           XmlParse.getChildValue(tlphNo, "asgnAgncId"));
				map.put("tlphNoOwnCmncCmpnCd",  XmlParse.getChildValue(tlphNo, "tlphNoOwnCmncCmpnCd"));
				map.put("openSvcIndCd",         XmlParse.getChildValue(tlphNo, "openSvcIndCd"));
				map.put("encdTlphNo",           XmlParse.getChildValue(tlphNo, "encdTlphNo"));
				map.put("tlphNo",               XmlParse.getChildValue(tlphNo, "tlphNo"));
				map.put("fvrtnoAqcsPsblYn",     XmlParse.getChildValue(tlphNo, "fvrtnoAqcsPsblYn"));
				map.put("rsrvCustNo",           XmlParse.getChildValue(tlphNo, "rsrvCustNo"));
				map.put("statMntnEndPrrnDate",  XmlParse.getChildValue(tlphNo, "statMntnEndPrrnDate"));
				map.put("tlphNoChrcCd",         XmlParse.getChildValue(tlphNo, "tlphNoChrcCd"));
				map.put("tlphNoStatChngDt",     XmlParse.getChildValue(tlphNo, "tlphNoStatChngDt"));
				map.put("tlphNoUseCd",          XmlParse.getChildValue(tlphNo, "tlphNoUseCd"));
				map.put("tlphNoUseMntCd",       XmlParse.getChildValue(tlphNo, "tlphNoUseMntCd"));
				
				list.add(map);
			}
			
			resultMap.put("list", list);
		}
		else if("FS0".equals(appEventCd)){
			List<HashMap<String, Object>> list = new ArrayList();
			List<Element> outDtoList = XmlParse.getChildElementList(rtn, "outDto");
			for(Element kNote : outDtoList){

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("onlineCustTrtSttusChgCd", 	XmlParse.getChildValue(kNote, "onlineCustTrtSttusChgCd"));	//온라인고객처리상태변경코드
				map.put("custIdntNo",				XmlParse.getChildValue(kNote, "custIdntNo"));					//고객식별번호
				map.put("custNm",				  	XmlParse.getChildValue(kNote, "custNm"));						//고객명
				map.put("wapplRegDate",			 	XmlParse.getChildValue(kNote, "wapplRegDate"));				//신청서등록일시
				map.put("frmpapId",				  	XmlParse.getChildValue(kNote, "frmpapId"));					//신청서ID
				map.put("custTypeNm",				XmlParse.getChildValue(kNote, "custTypeNm"));					//고객유형명
				map.put("custIdntNoIndCd",			XmlParse.getChildValue(kNote, "custIdntNoIndCd"));			//고객식별번호유형코드
				map.put("apyTypeCd",				XmlParse.getChildValue(kNote, "apyTypeCd"));					//신청유형코드
				map.put("slsCmpnCd",				XmlParse.getChildValue(kNote, "slsCmpnCd"));					//사업자코드
				map.put("slsNm",				  	XmlParse.getChildValue(kNote, "slsNm"));						//사업자명
				map.put("svcApyTrtStatCd",			XmlParse.getChildValue(kNote, "svcApyTrtStatCd"));			//처리상태코드
				map.put("fxdformIngrsPath1Cd",		XmlParse.getChildValue(kNote, "fxdformIngrsPath1Cd"));		//서식유입경로코드
				map.put("cntpntCd",				  	XmlParse.getChildValue(kNote, "cntpntCd"));					//접점아이디
				map.put("mngmAgncId",				XmlParse.getChildValue(kNote, "mngmAgncId"));					//대리점조직아이디
				map.put("cntpntNm",				  	XmlParse.getChildValue(kNote, "cntpntNm"));					//접점명

				// 1.K-NOTE 공통코드 조회
				Map<String, String> codeParam = new HashMap<String, String>();
				codeParam.put("grpId", "CMN0272");				// K-Note 공통코드그룹ID (처리상태)
				codeParam.put("cdVal", XmlParse.getChildValue(kNote, "svcApyTrtStatCd"));
				map.put("svcApyTrtStatNm",osstCallMapper.getKnoteCode(codeParam).get("cdDsc"));
				codeParam.put("grpId", "CMN0274");				// K-Note 공통코드그룹ID (고객유형)
				codeParam.put("cdVal", XmlParse.getChildValue(kNote, "custTypeNm"));
				map.put("custTypeNm2",osstCallMapper.getKnoteCode(codeParam).get("cdDsc"));
				

				// 2.신청유형명 Set
				codeParam.put("grpId", "RCP0082");
				codeParam.put("cdVal", XmlParse.getChildValue(kNote, "onlineCustTrtSttusChgCd"));
				map.put("operTypeNm",osstCallMapper.getKnoteCode(codeParam).get("cdDsc"));

				list.add(map);
			}
			resultMap.put("list", list);
		}
		else if("FS1".equals(appEventCd)){
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			resultMap.put("frmpapId", 					XmlParse.getChildValue(outDto, "frmpapId"));					//서식지아이디
			resultMap.put("titl", 						XmlParse.getChildValue(outDto, "titl"));						//제목
			resultMap.put("mngmAgncId", 				XmlParse.getChildValue(outDto, "mngmAgncId"));				//관리대리점코드
			resultMap.put("mngmAgncNm", 				XmlParse.getChildValue(outDto, "mngmAgncNm"));				//관리대리점명
			resultMap.put("onlineCustTrtSttusChgCd", 	XmlParse.getChildValue(outDto, "onlineCustTrtSttusChgCd"));	//온라인고객처리상태변경코드
			resultMap.put("frmpapRegPathCd", 			XmlParse.getChildValue(outDto, "frmpapRegPathCd"));			//판매경로코드명
			resultMap.put("wapplRegDate", 				XmlParse.getChildValue(outDto, "wapplRegDate"));				//서식지 등록일시
			resultMap.put("fxdformIngrsCdNm", 			XmlParse.getChildValue(outDto, "fxdformIngrsCdNm"));			//판매경로명
			resultMap.put("userId", 					XmlParse.getChildValue(outDto, "userId"));					//판매자아이디
			resultMap.put("userNm", 					XmlParse.getChildValue(outDto, "userNm"));					//판매자명
			resultMap.put("cntpntCd", 					XmlParse.getChildValue(outDto, "cntpntCd"));					//접점코드
			resultMap.put("cntpntNm", 					XmlParse.getChildValue(outDto, "cntpntNm"));					//접점코드명
			resultMap.put("custIdntNoIndCd", 			XmlParse.getChildValue(outDto, "custIdntNoIndCd"));			//명의자 식별구분코드
			resultMap.put("custTypeCd", 				XmlParse.getChildValue(outDto, "custTypeCd"));				//명의자 고객유형
			resultMap.put("nflCustNm", 					XmlParse.getChildValue(outDto, "nflCustNm"));					//명의자 고객명
			resultMap.put("nflCustIdfyNo", 				XmlParse.getChildValue(outDto, "nflCustIdfyNo"));				//명의자 식별번호
			resultMap.put("custNm", 					XmlParse.getChildValue(outDto, "custNm"));					//고객명
			resultMap.put("realEvdnDataInd", 			XmlParse.getChildValue(outDto, "realEvdnDataInd"));			//실명인증 증빙자료구분
			resultMap.put("realCustIdntNo", 			XmlParse.getChildValue(outDto, "realCustIdntNo"));			//실명인증 식별번호
			resultMap.put("realIssuDate", 				XmlParse.getChildValue(outDto, "realIssuDate"));				//실명인증 발급일자
			resultMap.put("opnYn", 						XmlParse.getChildValue(outDto, "opnYn"));						//개통여부
			resultMap.put("svcApyTrtSttusCd", 			XmlParse.getChildValue(outDto, "svcApyTrtSttusCd"));			//처리상태코드
			resultMap.put("svcContId", 					XmlParse.getChildValue(outDto, "svcContId"));					//서비스계약아이디	**
			resultMap.put("saleCmpnId", 				XmlParse.getChildValue(outDto, "saleCmpnId"));				//사업자코드

			// 1. 조직 조회
			Map<String, String> orgParam = new HashMap<String, String>();
			orgParam.put("mngmAgncId", XmlParse.getChildValue(outDto, "mngmAgncId"));
			orgParam.put("cntpntCd", XmlParse.getChildValue(outDto, "cntpntCd"));
			resultMap.put("orgnId",osstCallMapper.getKnoteOrgn(orgParam).get("orgnId"));

			// 2.K-NOTE 공통코드 조회
			Map<String, String> codeParam = new HashMap<String, String>();

			codeParam.put("grpId", "CMN0272");				// K-Note 공통코드그룹ID (처리상태)
			codeParam.put("cdVal", XmlParse.getChildValue(outDto, "svcApyTrtSttusCd"));
			resultMap.put("svcApyTrtStatNm", osstCallMapper.getKnoteCode(codeParam).get("cdDsc"));

			codeParam.put("grpId", "CMN0274");				// K-Note 공통코드그룹ID (고객유형)
			codeParam.put("cdVal", XmlParse.getChildValue(outDto, "custTypeCd"));
			resultMap.put("custTypeNm",osstCallMapper.getKnoteCode(codeParam).get("cdDsc"));

			codeParam.put("grpId", "RCP0002");				// K-Note 공통코드그룹ID (고객식별번호구분코드)
			codeParam.put("cdVal", XmlParse.getChildValue(outDto, "custIdntNoIndCd"));
			resultMap.put("custIdntNoIndNm",osstCallMapper.getKnoteCode(codeParam).get("cdDsc"));


			codeParam.put("grpId", "RCP2006");				// K-Note 공통코드그룹ID (고객정보_본인인증방식)
			codeParam.put("cdVal", "");
			codeParam.put("ETC2", XmlParse.getChildValue(outDto, "realEvdnDataInd"));
			resultMap.put("realEvdnDataNm",osstCallMapper.getKnoteCode(codeParam).get("cdDsc"));
			resultMap.put("realEvdnDataCd",osstCallMapper.getKnoteCode(codeParam).get("cdVal"));

			codeParam.put("grpId", "RCP0082");
			codeParam.put("cdVal", XmlParse.getChildValue(outDto, "onlineCustTrtSttusChgCd"));
			resultMap.put("operTypeNm",osstCallMapper.getKnoteCode(codeParam).get("cdDsc"));


			String simpCstmrType ="";
			if(!"01".equals(osstCallMapper.getKnoteCode(codeParam).get("etc1"))){
				simpCstmrType = "FN";   //외국인
			}
			else{
				simpCstmrType = "NA";	//내국인
			}
			resultMap.put("simpCstmrType",simpCstmrType);		// 내국인,외국인구분
		}
		else if ("FS2".equals(appEventCd) || "FS4".equals(appEventCd) || "FT1".equals(appEventCd)) {
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			resultMap.put("rsltCd", XmlParse.getChildValue(outDto, "rsltCd"));
			resultMap.put("rsltMsg", XmlParse.getChildValue(outDto, "rsltMsg"));
		}
		else if("FS3".equals(appEventCd)){
			List<HashMap<String, Object>> list = new ArrayList();
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			List<Element> frmpapInfoDTOList = XmlParse.getChildElementList(outDto, "frmpapInfoDTO");
			for(Element kNote : frmpapInfoDTOList){

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("oderTypeCd", 				XmlParse.getChildValue(kNote, "oderTypeCd"));					//오더유형코드
				map.put("itgFrmpapSeq",				XmlParse.getChildValue(kNote, "itgFrmpapSeq"));				//통합서식지일련번호
				map.put("custNm",			 		XmlParse.getChildValue(kNote, "custNm"));						//고객명		(마스킹)
				map.put("tlphNo",				  	XmlParse.getChildValue(kNote, "tlphNo"));						//전화번호 	(마스킹)
				map.put("noMaskTlphNo",				XmlParse.getChildValue(kNote, "tlphNo"));						//전화번호	(No마스킹)
				map.put("svcContId",				XmlParse.getChildValue(kNote, "svcContId"));					//서비스계약번호
				map.put("oderCretDt",				XmlParse.getChildValue(kNote, "oderCretDt"));					//오더생성일자
				map.put("sysTrtrId",				XmlParse.getChildValue(kNote, "sysTrtrId"));					//오더처리자아이디

				// 신청유형명 Set
				Map<String, String> codeParam = new HashMap<String, String>();
				codeParam.put("grpId", "RCP0082");
				codeParam.put("cdVal", XmlParse.getChildValue(kNote, "oderTypeCd"));
				map.put("oderTypeNm",osstCallMapper.getKnoteCode(codeParam).get("cdDsc"));

				list.add(map);
			}
			resultMap.put("list", list);
		}
		else if ("FS5".equals(appEventCd)) {
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			resultMap.put("rsltCd", XmlParse.getChildValue(outDto, "SVC_STATUS"));
			resultMap.put("rsltMsg", XmlParse.getChildValue(outDto, "ERR_MSG"));
		} else if ("FT0".equals(appEventCd)) {
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			resultMap.put("trtResltCd", XmlParse.getChildValue(outDto, "trtResltCd"));
			resultMap.put("trtResltSbst", XmlParse.getChildValue(outDto, "trtResltSbst"));
			resultMap.put("stbznPerdYn", XmlParse.getChildValue(outDto, "stbznPerdYn"));
		} else if ("FS8".equals(appEventCd)) {
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			resultMap.put("urlAdr", XmlParse.getChildValue(outDto, "urlAdr"));
			resultMap.put("fathTransacId", XmlParse.getChildValue(outDto, "fathTransacId"));
			resultMap.put("resltCd", XmlParse.getChildValue(outDto, "resltCd"));
			resultMap.put("resltSbst", XmlParse.getChildValue(outDto, "resltSbst"));
		} else if ("FS9".equals(appEventCd)) {
			Element outDto = XmlParse.getChildElement(rtn, "outDto");
			resultMap.put("fathTransacId", XmlParse.getChildValue(outDto, "fathTransacId"));
			resultMap.put("fathResltCd", XmlParse.getChildValue(outDto, "fathResltCd"));
			resultMap.put("fathResltMsgSbst", XmlParse.getChildValue(outDto, "fathResltMsgSbst"));
			resultMap.put("fathDecideCd", XmlParse.getChildValue(outDto, "fathDecideCd"));
			resultMap.put("fathCmpltNtfyDt", XmlParse.getChildValue(outDto, "fathCmpltNtfyDt"));
			resultMap.put("fathIdcardTypeCd", XmlParse.getChildValue(outDto, "fathIdcardTypeCd"));
			resultMap.put("fathIdcardIssDate", XmlParse.getChildValue(outDto, "fathIdcardIssDate"));
			resultMap.put("fathDriveLicnsNo", XmlParse.getChildValue(outDto, "fathDriveLicnsNo"));
			resultMap.put("fathCustNm", XmlParse.getChildValue(outDto, "fathCustNm"));
			resultMap.put("fathCustIdfyNo", XmlParse.getChildValue(outDto, "fathCustIdfyNo"));
		}
		else{
			resultMap.put("osstOrdNo", XmlParse.getChildValue(rtn, "osstOrdNo"));
			resultMap.put("rsltCd", XmlParse.getChildValue(rtn, "rsltCd"));
			resultMap.put("rsltMsg", XmlParse.getChildValue(rtn, "rsltMsg"));
		}
		
		return resultMap;
	}
	
	public boolean isOrgChk(String requestKey){
		int cnt = osstCallMapper.isOrgChk(requestKey);
		return cnt > 0;
	}
	
	/**
	 * 기적용 테이블 INSERT
	 * @param vo
	 * @param evntCd
	 * @return
	 */
	public int insertDisApd(RcpDetailVO vo, String evntCd){
		vo.setEvntCd(evntCd);
		//프로모션 ID 찾아오기
		List<EgovMap> prmtList =promotionMapper.getChrgPrmtListCombo(vo);
		String prmtId="";
		if(KtisUtil.isNotEmpty(prmtList)) {
            prmtId= (String) prmtList.get(0).get("prmtId");
            vo.setDisPrmtId(prmtId);
        }
		return osstCallMapper.insertDisApd(vo);
	}

	public boolean knoteScanInfoChk(String frmpapId){
		int cnt  = osstCallMapper.knoteScanInfoChk(frmpapId);
		return cnt > 0;
	}

	public String getKnoteKtOrgId(String orgnId){
		String ktOrgId = osstCallMapper.getKnoteKtOrgId(orgnId);
		return ktOrgId;
	}
	
	public Map<String, Object> processOsstFs9Service(String url, RcpDetailVO rcpDetailVO, String resltCd) {

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		OsstReqVO osstReqVO = new OsstReqVO();
		osstReqVO.setResNo(rcpDetailVO.getResNo());
		osstReqVO.setMcnResNo(rcpDetailVO.getMcnResNo());
		osstReqVO.setAppEventCd("FS9");
		osstReqVO.setFathTransacId(rcpDetailVO.getFathTransacId());
		osstReqVO.setRetvDivCd("1"); //조회구분코드 1:트랙잭션 2:서식지아이디
		osstReqVO.setAsgnAgncId(rcpDetailVO.getMngmAgncId());
		
		Map<String, Object> osstRtnMap = this.osstServiceCall(url, osstReqVO);
		
		Map<String, Object> osstRst = (Map<String, Object>) osstRtnMap.get("osstRst");
		String fathDecideCd = (String) osstRst.get("fathDecideCd");
		if("SUCC".equals(fathDecideCd)) {
			
			if("CD02".equals(resltCd)) {
				rtnMap.put("resltSbst", "안면인증 결과성공[CD02] <br>결과확인 불필요");
				rtnMap.put("msg", "안면인증 결과성공[CD02] <br>결과확인 불필요");
				rtnMap.put("code", "CD02");
				return rtnMap;
			}

			FathVO fathVO = new FathVO();
			fathVO.setFathCmpltNtfyDt((String) osstRst.get("fathCmpltNtfyDt"));
			fathVO.setRetvCdVal((String) osstRst.get("fathIdcardTypeCd"));
			fathVO.setIssDateVal((String) osstRst.get("fathIdcardIssDate"));
			fathVO.setDriveLicnsNo((String) osstRst.get("fathDriveLicnsNo"));
			fathVO.setCustNm((String) osstRst.get("fathCustNm"));
			fathVO.setCustIdfyNo((String) osstRst.get("fathCustIdfyNo"));
			rtnMap = fathService.fathSuccRtn(fathVO, rcpDetailVO);
		} else if("SKIP".equals(fathDecideCd)) {
			rtnMap.put("resltSbst", "안면인증 결과성공[CD05] <br>결과확인 불필요");
			rtnMap.put("msg", "안면인증 결과성공[CD05] <br>결과확인 불필요");
			rtnMap.put("code", "CD05");
		} else {
			rtnMap = fathService.fathFailRtn(rcpDetailVO);
		}
		
		return rtnMap; 
	}
	
}
