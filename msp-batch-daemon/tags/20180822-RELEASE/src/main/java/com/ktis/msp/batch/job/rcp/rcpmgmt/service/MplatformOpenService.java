package com.ktis.msp.batch.job.rcp.rcpmgmt.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.MplatformOpenMapper;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.MplatformOpenReqVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.MplatformOpenResVO;
import com.ktis.msp.batch.util.XmlParse;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class MplatformOpenService extends BaseService {
	
	@Autowired
	private MplatformOpenMapper openMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	protected final static String HEADER = "commHeader";
	protected final static String RESULTKEY = "globalNo";
	protected final static String RESULTTYPE = "responseType";
	protected final static String RESULTCODE = "responseCode";
	protected final static String RESULTMSG = "responseBasic";
	protected final static Integer RETRY_MAX_CNT = 3;
	
	/**
	 * 개통요청
	 * @return
	 */
	public int setMplatformOpenCall(){
		int procCnt = 0;
		
		// 개통대상조회
		List<MplatformOpenResVO> list = openMapper.getMplatformOpenTrgtList();
		
		for(MplatformOpenResVO resVO : list){
			try{
				LOGGER.debug("resNo=" + resVO.getResNo());
				
				MplatformOpenReqVO vo = new MplatformOpenReqVO();
				vo.setResNo(resVO.getResNo());
				vo.setAppEventCd("OP0");	// 개통요청
				
				HashMap<String, String> rtnMap = this.osstServiceCall(vo);
				
				// 10초에 1건씩 전송
				this.sleep(10);
				
				procCnt++;
			} catch (MvnoServiceException e) {
				e.printStackTrace();
			}
		}
		
		return procCnt;
	}
	
	/**
	 * 전일 번호예약후 미개통건에 대한 재처리
	 */
	public int setMplatformOpenRetry(){
		int procCnt = 0;
		List<MplatformOpenResVO> list = openMapper.getMplatformOpenRetryList();
		
		for(MplatformOpenResVO resVO : list){
			try{
				// 신청정보 재생성
				this.setRequestCopy(resVO);
				
				LOGGER.debug("신규예약번호=" + resVO.getResNo());
				LOGGER.debug("이전예약번호=" + resVO.getOldResNo());
				
				// 사전체크 전송
				MplatformOpenReqVO reqVO = new MplatformOpenReqVO();
				reqVO.setResNo(resVO.getResNo());
				reqVO.setAppEventCd("PC0");
				
				HashMap<String, String> rtnMap = this.osstServiceCall(reqVO);
				LOGGER.debug("*****************************************************");
				LOGGER.debug("사전체크 전송결과=" + rtnMap);
				LOGGER.debug("*****************************************************");
				
				// 최초 사전체크 송신 후 TCP 결과수신을 위하여 30초간 대기
				sleep(30);
				
				// 재시도를 위한 초기값
				boolean tcpRslt = false;
				int tryCnt = 0;
				while(!tcpRslt){
					LOGGER.debug("재시도횟수 = " + tryCnt);
					LOGGER.debug("tcpRslt=" + tcpRslt);
					
					reqVO.setAppEventCd("PC2");
					
					// TCP 결과 체크
					int rtnChk = openMapper.getTcpResult(reqVO);
					
					if(rtnChk > 0){
						LOGGER.debug("TCP 사전체크 결과 수신 성공");
						
						// 번호예약이력생성
						LOGGER.debug("번호예약이력생성");
						resVO.setAppEventCd("NU2");
						resVO.setGubun("RSV");
						openMapper.insertRequestOsstHist(resVO);
						
						// 번호예약 전송
						LOGGER.debug("번호예약전송");
						reqVO.setAppEventCd(resVO.getAppEventCd());
						reqVO.setGubun(resVO.getGubun());
						
						this.osstServiceCall(reqVO);
						
						tcpRslt = true;
						break;
					}
					else if(tryCnt < RETRY_MAX_CNT){
						LOGGER.debug("TCP결과 미수신 상태로 대기");
						// 30초간 대기
						tryCnt++;
						tcpRslt = false;
						sleep(30);
					}
					else{
						// 30초간 3회 시도 이후 응답이 없으면 PASS
						tcpRslt = false;
						break;
					}
				}
				procCnt++;
			}catch(Exception e){
				LOGGER.error(e.getMessage());
				// OSST 로그생성
			}
		}
		
		return procCnt;
	}
	
	
	
	/**
	 * 신청서 복사 및 고객정보 생성
	 */
	public void setRequestCopy(MplatformOpenResVO searchVO){
		
		try{
			LOGGER.debug("oldRequestKey=" + searchVO.getOldRequestKey());
			LOGGER.debug("requestKey=" + searchVO.getRequestKey());
			LOGGER.debug("newStateKey=" + searchVO.getNewStateKey());
			
			// 접수건 중 당일 개통을 못한 경우 신청정보를 복사하여 사전체크 진행 
			openMapper.insertCopyRequest(searchVO);
			openMapper.insertCopyRequestCstmr(searchVO);
			openMapper.insertCopyRequestSaleinfo(searchVO);
			openMapper.insertCopyRequestDlvry(searchVO);
			openMapper.insertCopyRequestReq(searchVO);
			openMapper.insertCopyRequestMove(searchVO);
			openMapper.insertCopyRequestPay(searchVO);
			openMapper.insertCopyRequestAgent(searchVO);
			openMapper.insertCopyRequestState(searchVO);
			openMapper.insertCopyRequestChange(searchVO);
			openMapper.insertCopyRequestDvcChg(searchVO);
			openMapper.insertCopyRequestAddition(searchVO);
			// 기존신청서 상태 변경( 00 -> 30 )
			openMapper.updateRequestState(searchVO);
			
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		
	}
	
	/**
	 * 개통간소화 OSST 연동
	 */
	public HashMap<String,String> osstServiceCall(MplatformOpenReqVO searchVO) throws MvnoServiceException {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		String result = "";
		String responseXml = "";
		
		LOGGER.error("resNo=" + searchVO.getResNo());
		LOGGER.error("appEventCd=" + searchVO.getAppEventCd());
		LOGGER.error("gubun=" + searchVO.getGubun());
		
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Test Client");
		PostMethod method = new PostMethod(propertiesService.getString("mplatform.simple.url"));
		
		try {
			LOGGER.info("★☆★ M-PlatForm Connect Start ☆★☆");
			
//			method.setRequestEntity((RequestEntity) searchVO);
			method.addParameter("resNo", searchVO.getResNo());
			method.addParameter("appEventCd", searchVO.getAppEventCd());
			if(searchVO.getGubun() != null){
				method.addParameter("gubun", searchVO.getGubun());
			}
			
			BufferedReader br = null;
			
			try{
				int returnCode = client.executeMethod(method);
				if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
					method.getResponseBodyAsString();
				} else {
					StringBuffer sb = new StringBuffer();
					
					br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8" ));
					String readLine;
					while(((readLine = br.readLine()) != null)) {
						sb.append(readLine);
					}
					responseXml = sb.toString();
					
					LOGGER.info("responseXml : " + responseXml);
				}
				
				if (responseXml.isEmpty()) {
					result = "response massage is null.";
					LOGGER.error(result);
				} else {
					result = "SUCCESS";
//					this.toResponseParse(responseXml, paramMap);
				}
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			
			paramMap.put("result", result);
			
			LOGGER.info("★☆★ M-PlatForm Connect End ☆★☆");
			
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
			
		} catch (Exception e) {
			paramMap.put("result", e.toString());
			LOGGER.error(result);
			throw new MvnoServiceException("EMSP1007", e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		
		return paramMap;
	}
	
	public void sleep(int time){
		// time 은 초 단위임
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) { 
			
		}
	}
	
	/**
	 * 부가서비스 셀프케어 가입처리
	 * @param searchVO
	 * @return
	 */
	public int setOpenAddSvcProc(MplatformOpenReqVO searchVO){
		int procCnt = 0;
		
		List<MplatformOpenResVO> list = openMapper.getOpenAddSvcList(searchVO);
		
		try{
			for(MplatformOpenResVO resVO : list){
				HashMap<String, String> paramMap = new HashMap<String,String>();
				paramMap.put("ncn",        resVO.getSvcCntrNo());
				paramMap.put("ctn",        resVO.getTlphNo());
				paramMap.put("custId",     resVO.getCustomerId());
				paramMap.put("appEventCd", "X21");
				paramMap.put("soc",        resVO.getSocCode());
				
				LOGGER.error("셀프케어 호출 parameter=" + paramMap);
				
				// 셀프케어 호출
				paramMap = this.SelfCareCallService(paramMap);
				
				resVO.setAppEventCd((String) paramMap.get("appEventCd"));
				resVO.setNstepGlobalId((String) paramMap.get("resultKey"));
				
				if("SUCCESS".equals((String) paramMap.get("result"))){
					if("N".equals((String) paramMap.get("resultType"))){
						resVO.setRsltCd("0000");
					}else{
						resVO.setRsltCd("9999");
						resVO.setRsltMsg((String) paramMap.get("resultMsg"));
					}
				}else{
					resVO.setRsltCd("9999");
				}
				
				// 연동이력 생성
				openMapper.insertRequestOsstHist(resVO);
				
				// 부가서비스 처리 UPDATE
				openMapper.updateRequestAddition(resVO);
				
				// 3초간 대기
				sleep(3);
				
				procCnt++;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		
		return procCnt;
	}
	
	
	/**
	 * 부가서비스 가입을 위한 셀프케어 호출 서비스
	 * @param paramMap
	 * @return
	 * @throws MvnoServiceException
	 */
	public HashMap<String,String> SelfCareCallService(HashMap<String,String> paramMap) throws MvnoServiceException {
		String result = "";
		String responseXml = "";
				
		try {
			String getURL = this.getURL(paramMap);
			
			NameValuePair[] data = {
				new NameValuePair("getURL", getURL)
			};
			
			LOGGER.info("★☆★ M-PlatForm Connect Start ☆★☆");

			HttpClient client = new HttpClient();
			client.getParams().setParameter("http.useragent", "Test Client");
			BufferedReader br = null;
			// 셀프케어 url
			PostMethod method = new PostMethod(propertiesService.getString("mplatform.selfcare.url"));
			method.setRequestBody(data);
			try{
				int returnCode = client.executeMethod(method);
				if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
					method.getResponseBodyAsString();
				} else {
					StringBuffer sb = new StringBuffer();
					
					br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8" ));
					String readLine;
					while(((readLine = br.readLine()) != null)) {
						sb.append(readLine);
					}
					responseXml = sb.toString();
					
					LOGGER.info("responseXml : " + responseXml);
				}
			} catch (Exception e) {
				LOGGER.error(e.toString());
			} finally {
				
				method.releaseConnection();
				client.getHttpConnectionManager().closeIdleConnections(0);
				if(br != null) try { br.close(); } catch (Exception fe) {LOGGER.error(fe.toString());}
			}
			
			if (responseXml.isEmpty()) {
				result = "response massage is null.";
				LOGGER.error(result);
			} else {
				result = "SUCCESS";
				paramMap = this.toResponseParse(responseXml, paramMap);
			}
			
			paramMap.put("result", result);
			
			LOGGER.info("SelfCareCallService paramMap=" + paramMap);
			
			LOGGER.info("★☆★ M-PlatForm Connect End ☆★☆");
			
		} catch (Exception e) {
			paramMap.put("result", e.toString());
			LOGGER.error(e.getMessage());
			throw new MvnoServiceException("EMSP1007", e);
		}
		
		return paramMap;
	}
	
	public HashMap<String, String> toResponseParse(String responseXml, HashMap<String,String> resultMap) throws JDOMException, IOException {
		
		Element root = XmlParse.getRootElement("<?xml version=\"1.0\" encoding=\"euc-kr\"?>"+responseXml);
		Element rtn = XmlParse.getReturnElement(root);

		Element commHeader = XmlParse.getChildElement(rtn, HEADER);
		
		resultMap.put("resultKey", XmlParse.getChildValue(commHeader, RESULTKEY));
		resultMap.put("resultType", XmlParse.getChildValue(commHeader, RESULTTYPE));
		resultMap.put("resultCd", XmlParse.getChildValue(commHeader, RESULTCODE));
		resultMap.put("resultMsg", XmlParse.getChildValue(commHeader, RESULTMSG));
		
		return resultMap;
	}

	public String getURL(HashMap<String, String> param){
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
			LOGGER.error(e.toString());
		}
		return result;
	}

	/**
	 * Mplatform 개통 후 신청정보 처리
	 * @param searchVO
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setOpenRequestProc(MplatformOpenReqVO searchVO){
		int procCnt = 0;
		
		List<MplatformOpenResVO> list = openMapper.getOpenCntrList(searchVO);
		
		try{
			for(MplatformOpenResVO resVO : list){
				LOGGER.error("개통후 신청정보 처리==================================");
				LOGGER.error("svcCntrNo=" + resVO.getSvcCntrNo());
				LOGGER.error("requestKey=" + resVO.getRequestKey());
				LOGGER.error("개통후 신청정보 처리==================================");
				
				// 신청정보 상태변경
				openMapper.updateMcpRequest(resVO);
				// 신청정보
				openMapper.insertMspRequest(resVO);
				// 고객정보
				openMapper.insertMspRequestCstmr(resVO);
				// 판매정보
				openMapper.insertMspRequestSaleinfo(resVO);
				// 배송정보
				openMapper.insertMspRequestDlvry(resVO);
				// 납부정보
				openMapper.insertMspRequestReq(resVO);
				// 번호이동
				openMapper.insertMspRequestMove(resVO);
				// 선불충전
				openMapper.insertMspRequestPay(resVO);
				// 대리인
				openMapper.insertMspRequestAgent(resVO);
				// 진행상태
				openMapper.insertMspRequestState(resVO);
				// 신청변경
				openMapper.insertMspRequestChange(resVO);
				// 기변사유
				openMapper.insertMspRequestDvcChg(resVO);
				// 부가서비스
				openMapper.insertMspRequestAddition(resVO);
				// 개통
				openMapper.updateMspRequestDtl(resVO);
				// 계약현행화
				openMapper.updateJuoSubInfo(resVO);
				
				procCnt++;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		
		return procCnt;
	}
	
	/**
	 * 미개통 신청정보 삭제
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteOpenFailList() throws MvnoServiceException{
		// 삭제대상조회
		List<MplatformOpenResVO> list = openMapper.deleteOpenFailList();
		
		try{
			for(MplatformOpenResVO vo: list){
				LOGGER.debug("requestKey=" + vo.getRequestKey());
				LOGGER.debug("resNo=" + vo.getResNo());
				
				openMapper.deleteRequest(vo);
				openMapper.deleteRequestCstmr(vo);
				openMapper.deleteRequestSaleinfo(vo);
				openMapper.deleteRequestDvlry(vo);
				openMapper.deleteRequestReq(vo);
				openMapper.deleteRequestMove(vo);
				openMapper.deleteRequestPayment(vo);
				openMapper.deleteRequestAgent(vo);
				openMapper.deleteRequestState(vo);
				openMapper.deleteRequestChange(vo);
				openMapper.deleteRequestDvcChg(vo);
				openMapper.deleteRequestAddition(vo);
				openMapper.deleteRequestOsst(vo);
			}
		}catch(Exception e){
			throw new MvnoServiceException(e.getMessage());
		}
		
		return list.size();
	}
}
