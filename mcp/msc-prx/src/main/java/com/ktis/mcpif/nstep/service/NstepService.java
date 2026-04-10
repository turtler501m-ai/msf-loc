package com.ktis.mcpif.nstep.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;

import com.ktis.mcpif.common.KisaSeedUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.mcpif.nstep.NstepQueryVo;
import com.ktis.mcpif.nstep.NstepResponse;
import com.ktis.mcpif.nstep.NstepVo;
//import com.ktis.mcpif.nstep.nstepMapper.mapper.NstepMapper;

import egovframework.rte.fdl.property.EgovPropertyService;



@org.springframework.stereotype.Service("nStepService")
public class NstepService {
//	@Autowired
//	private NstepMapper nstepMapper;

	protected Logger logger = LogManager.getLogger(getClass());
	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;

	/**
	 * N-Step 서비스 호출
	 * @param nStepVo
	 * @return
	 * @throws SOAPException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
//  public NstepResponse nStepWebServiceCall(NstepVo nStepVo) throws SOAPException, MalformedURLException  {
	public NstepResponse nStepWebServiceCall(NstepVo nStepVo) throws Exception  {
		// -------------------------------------------------------------------
		// Initialize.
		// -------------------------------------------------------------------
		String sLogKey = nStepVo.getSrl_no();

		//Response 객체
		NstepResponse nStepResponse = new NstepResponse();

		//connection 객체 생성
		SOAPConnection soapConnection = SOAPConnectionFactory.newInstance().createConnection();

		//전송할 XML Message 메시지 생성
		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();

		//String -> XML 생성
		Document doc = null;
		DocumentBuilder docBuilder = null;
		DOMSource domSource = null;

		//Target URL
		String url = propertiesService.getString("nStep.url");

		try {
			final int iConnTimeout = 11;//nstepMapper.getNstepConnectTimeout();
//          final int iConnTimeout = nstepMapper.getNstepConnectTimeout();


			// 호출 URL
			URL requestUrl = new URL(new URL(url), "", new URLStreamHandler() {
				@Override
				protected URLConnection openConnection(URL url) throws IOException {
					URL clone = new URL(url.toString());
					URLConnection clone_connection = clone.openConnection();
					clone_connection.setConnectTimeout(1000 * iConnTimeout);
					clone_connection.setReadTimeout(1000 * iConnTimeout);

					return(clone_connection);
				}
			});
			
			//SSL 설정
//			doTrustToCertificates();
			
			//XML 메시지 생성
			logger.error("[grep:" + sLogKey + "]     =====>    N-STEP에 전송한 XML 값 출력     <=====");
			String requestXml = xmlMessageCreate(nStepVo, sLogKey);

			//파싱 객체 생성
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			// String 타입 변경
			doc = docBuilder.parse(new ByteArrayInputStream(requestXml.getBytes()));

			domSource = new DOMSource(doc);

			soapMessage.getSOAPPart().setContent(domSource);

			// SOAP CALL
			SOAPMessage responseMessage = null;

			try {
				logger.error("[grep:" + sLogKey + "]     =====>    N-STEP 전송 시작     <=====");
//              if(true) {
//                  throw new Exception();
//              }
				logger.error("[grep:" + sLogKey + "]     " + soapMessage);
				logger.error("[grep:" + sLogKey + "]     " + requestUrl);
				// 프록시 설정
				System.setProperty("java.net.useSystemProxies", "true");
				responseMessage = soapConnection.call(soapMessage, requestUrl);
				logger.error("[grep:" + sLogKey + "]     =====>    N-STEP 전송 종료     <=====");
			} catch (Exception ex){
				logger.error("[grep:" + sLogKey + "]     =====>    N-STEP 전송 에러 발생     <=====");
				throw ex;
			}
			responseMessage.writeTo(System.out);
			nStepResponse = parserXML(responseMessage);
		} catch (Exception e) {
			throw e;
		} finally {
			soapConnection.close();
		}
		return nStepResponse;
	}

	/**
	 * N-Step 전송 XML 초기 데이터 생성
	 * @param type
	 * @return
	 */
	public StringBuffer initXmlMessageCreate(String type) {

		StringBuffer xmlStringBuffer = new StringBuffer();

		if(type.equals("start")) {
			xmlStringBuffer.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:nst=\"uri://nstep.igw.common\">");
			xmlStringBuffer.append("<soapenv:Header/>");
			xmlStringBuffer.append("<soapenv:Body>");
			xmlStringBuffer.append("<nst:in_op_ON_CJH_IN_SERVER_5177_Input>");
		}else {
			xmlStringBuffer.append("</nst:in_op_ON_CJH_IN_SERVER_5177_Input>");
			xmlStringBuffer.append("</soapenv:Body>");
			xmlStringBuffer.append("</soapenv:Envelope>");
		}
		return xmlStringBuffer;
	}

	public void voPrint(NstepQueryVo nStepVo, String sLogKey) {


		Field[] fields = nStepVo.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {

			try {

				String fieldsName = fields[i].getName();
				Object tempData =   nStepVo.getClass().getDeclaredField(fieldsName).get(nStepVo);

				logger.error("[grep:" + sLogKey + "]" + fieldsName + " : " + tempData);

			} catch (Exception e) {
				logger.error("[grep:" + sLogKey + "]" + e.toString());
			}

		}

	}

	/**
	 * N-Step 전송 XML 생성
	 * @param nStepVo
	 * @return
	 */
	public String xmlMessageCreate(NstepVo nStepVo, String sLogKey) {
		// -------------------------------------------------------------------
		// Initialize.
		// -------------------------------------------------------------------
		StringBuffer requestXml = initXmlMessageCreate("start");
		Field[] fields = nStepVo.getClass().getDeclaredFields();
		String preFix = "nst:";

		// -------------------------------------------------------------------
		// Service Process.
		// -------------------------------------------------------------------
		for (int i = 0; i < fields.length; i++) {
			try {
				String fieldsName = fields[i].getName();
				Object tempData =   nStepVo.getClass().getDeclaredField(fieldsName).get(nStepVo);
				String data = tempData == null ? "" : String.valueOf(tempData);
				String childNode ="";

				if(!data.equals("")) {
					childNode = "<" + preFix + fieldsName.toUpperCase() + ">" + data.replaceAll("&", "<![CDATA[&]]>") + "</" + preFix + fieldsName.toUpperCase() + ">";
					requestXml.append(childNode);
				}
			} catch (Exception e) {
				logger.error("[grep:" + sLogKey + "]" + e);
			}
		}
		StringBuffer endXml = initXmlMessageCreate("end");
		requestXml.append(endXml.toString());
		logger.error("[grep:" + sLogKey + "]" + requestXml.toString());
		return requestXml.toString();
	}

	/**
	 * N-Step Response Parser
	 * @param responseMessage
	 * @return
	 * @throws Exception
	 */
	public  NstepResponse parserXML(SOAPMessage responseMessage) {

		NstepResponse nStepResonse = new NstepResponse();

		try {
			NodeList list = responseMessage.getSOAPBody().getChildNodes().item(0).getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {
				String nodeName = list.item(i).getNodeName();
				NodeList nodeList = list.item(i).getChildNodes();

				Node node = nodeList.item(0);
				if(node != null) {

					String value = node.getNodeValue();
					if(nodeName != null && !nodeName.equals("")) {
						String temp[] = nodeName.split(":");
						nodeName = temp[1];

					}
					if(nodeName.equals("SVC_STATUS")) {
						logger.error("******************  value : " + value);
						nStepResonse.setSvc_status(value);
					}else if(nodeName.equals("ERR_MSG")) {
						logger.error("******************  message : " + value);
						nStepResonse.setErr_msg(value);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return nStepResonse;
	}




	 public  String NVL(String parameter, String nullChange) {
		String  temp = parameter == null ?  nullChange : parameter;

		if(temp.equals("null")) {
			temp = nullChange;
		}

		return temp;
	}

	/**
	 * N-Step 객체 데이터 변환
	 * @param nStepVo
	 */
	public void nStepDataChange(NstepVo nStepVo, NstepQueryVo queryNstepVo) {

		RestTemplate restTemplate = new RestTemplate();
		String apiInterfaceServer = propertiesService.getString("api.interface.server");

		String real_yn = propertiesService.getString("nStep.real.yn");
		String cstmr_type = NVL(queryNstepVo.getCstmr_type(), "");
		String cstmr_type_org = cstmr_type;

		String cstmr_foreigner_dod = NVL(queryNstepVo.getCstmr_foreigner_dod(), "");

		//추가되야할 사항 - 06 사회보장번호/DOD
		if(cstmr_type.equals("NA")) { //  내국인
			cstmr_type = "01";
		}else if(cstmr_type.equals("NM")) { // 내국 - 미성년자
			cstmr_type = "01";
		}else if(cstmr_type.equals("FN")) { // 외국인
			cstmr_type = "05";
		}else if(cstmr_type.equals("JP")) { // 법인사업자
			cstmr_type = "03";
		}else if(cstmr_type.equals("PP")) {  //개인사업자
			cstmr_type = "01";
		}


		//채널점아이디
		String cntpnt_shop_id = queryNstepVo.getCntpnt_shop_id();
		if(real_yn.equals("Y")) {
			nStepVo.setCntpnt_shop_id(cntpnt_shop_id);
		}else {
			//개발은 테스트를 위하여 값이 넘어 오지 않아도 넘긴다. 초기에 모델아이디가 셋팅 안되어 있기 때문임
			if(cntpnt_shop_id != null) {
				nStepVo.setCntpnt_shop_id(cntpnt_shop_id);
			}else {
				nStepVo.setCntpnt_shop_id("1100011741");
			}
		}

		//int orgCnt = nstepMapper.getOrgCnt(nStepVo.getCntpnt_shop_id());
		int orgCnt = restTemplate.postForObject(apiInterfaceServer + "/nstep/getOrgCnt", nStepVo.getCntpnt_shop_id(), Integer.class);
		logger.error("nstepMapper.getOrgCnt = " + orgCnt);

		if(orgCnt > 0){
			//String newOrgnId = nstepMapper.getOrgnId(nStepVo.getCntpnt_shop_id());
			String newOrgnId = restTemplate.postForObject(apiInterfaceServer + "/nstep/getOrgnId", nStepVo.getCntpnt_shop_id(), String.class);
			logger.error("nstepMapper.getOrgnId = " + newOrgnId);

			if(newOrgnId != null){
				nStepVo.setCntpnt_shop_id(newOrgnId);
			}
			logger.error("[grep:" + queryNstepVo.getRes_no() + "]==>>>newOrgnId : " + newOrgnId);
		}

		logger.error("[grep:" + queryNstepVo.getRes_no() + "]==>>>Cntpnt_shop_id : " + nStepVo.getCntpnt_shop_id());

		//특별판매 코드
		nStepVo.setSpcl_sls_no(queryNstepVo.getSpc_code());


		String oper_type = NVL(queryNstepVo.getOper_type(), "");

		//일련번호
		nStepVo.setSrl_no(queryNstepVo.getRes_no());
		//판매회사코드
		nStepVo.setSls_cmpn_cd("KIS"); // Default



		/**
		 * NA   내국인
		 * NM   내국인(미성년자)
		 * PP   개인사업자
		 * JP   법인사업자
		 * FN   외국인
		 * NE   기타
		 */

		/**
		 *개인 / 개인사업자 01
		 *법인, 공공기관 03
		 *외국인 05
		 *사회보장번호/DOD 06
		 */

		/**

		 * 납부자에 따라 달라짐
		 * 개인 - 주민등록번호
		 * 법인 - 법인번호
		 * 외국인 - 외국인등록번호
		 */
		if(cstmr_type_org.equals("NA") || cstmr_type_org.equals("NM") || cstmr_type_org.equals("PP") ) {
			nStepVo.setCust_idnt_no(queryNstepVo.getCstmr_native_rrn());
		} else if(cstmr_type_org.equals("JP")) {
			nStepVo.setCust_idnt_no(queryNstepVo.getCstmr_juridical_rrn());
		} else if(cstmr_type_org.equals("FN")) {
			nStepVo.setCust_idnt_no(queryNstepVo.getCstmr_foreigner_rrn());
		/*}else if(cstmr_type_org.equals("PP")) {
			nStepVo.setCust_idnt_no(queryNstepVo.getCstmr_private_number());
		*/
		} else {
			nStepVo.setCust_idnt_no(queryNstepVo.getCstmr_native_rrn());
		}

		//사회 보장 번호
		if( cstmr_type_org.equals("FN") && cstmr_foreigner_dod != null && !cstmr_foreigner_dod.equals("")) {
			nStepVo.setCust_idnt_no(cstmr_foreigner_dod);
		}


		//고객식별번호
		/*if(cstmr_type.equals("01")) {
			nStepVo.setCust_idnt_no(queryNstepVo.getCstmr_native_rrn());
		}else if(cstmr_type.equals("02")) {
			if(!queryNstepVo.getCstmr_private_number().equals("")) {
				nStepVo.setCust_idnt_no(queryNstepVo.getCstmr_private_number());
			}
			if(!queryNstepVo.getCstmr_juridical_rrn().equals("")) {
				nStepVo.setCust_idnt_no(queryNstepVo.getCstmr_juridical_number());
			}
		}else if(cstmr_type.equals("03")) {
			nStepVo.setCust_idnt_no(queryNstepVo.getCstmr_juridical_rrn());
		}else if(cstmr_type.equals("05")) {
			nStepVo.setCust_idnt_no(queryNstepVo.getCstmr_foreigner_rrn());
		}*/

		//번호이동 일경우 해지미환급금액여부
		String move_refund_agree_flag = NVL(queryNstepVo.getMove_refund_agree_flag(),"");
		if(oper_type.equals("MNP3")) {
			if(move_refund_agree_flag.equals("Y")) {
				nStepVo.setTrmn_yet_rpym_amt_yn("Y");
			}else {
				nStepVo.setTrmn_yet_rpym_amt_yn("N");
			}
		}


		//고객식별번호구분코드
		nStepVo.setCust_idnt_no_ind_cd(cstmr_type);

		//사회 보장 번호
		if( cstmr_type_org.equals("FN") && cstmr_foreigner_dod != null && !cstmr_foreigner_dod.equals("")) {
			nStepVo.setCust_idnt_no_ind_cd("06");
		}


		//상품코드
		nStepVo.setPrdc_cd(queryNstepVo.getSoc_code());

		//단말기모델아이디(숫자만 들어가야 함)
		String model_id =  NVL(queryNstepVo.getModel_id(),"");

		if(real_yn.equals("Y")) {

			if(!model_id.equals("null") && !model_id.equals("")) {
				nStepVo.setHndset_mdl_id(model_id);
			}

		}else {
			//개발은 테스트를 위하여 값이 넘어 오지 않아도 넘긴다. 초기에 모델아이디가 셋팅 안되어 있기 때문임
			if(model_id != null) {
				nStepVo.setHndset_mdl_id(queryNstepVo.getModel_id());
			}else {
				nStepVo.setHndset_mdl_id("5176");
			}
		}

		//구분타입코드
		nStepVo.setInd_type_cd(queryNstepVo.getOper_type());

		//고객번호 필수 값이나 n-step 내부 부여

		//고객명
		nStepVo.setCust_nm(queryNstepVo.getCstmr_name());

		//고객세부유형코드
		if(cstmr_type_org.equals("FN")) {
			nStepVo.setCust_dtl_type_cd("F");
		}else {
			nStepVo.setCust_dtl_type_cd("N1");
		}

		//법인번호 - 법인 고객
		if(cstmr_type_org.equals("JP")) {
			nStepVo.setCrpr_no(queryNstepVo.getCstmr_juridical_rrn());
		}

		//고객유형코드 - 법인 공공 -> B
		//           나머지 -> I
		if(cstmr_type_org.equals("JP")) {
			nStepVo.setCust_type_cd("B");
		}else {
			nStepVo.setCust_type_cd("I");
		}

		//복지코드 없음X
		nStepVo.setWlfr_cd("00");

		//유선전화번호
		String cstmr_tel_fn = NVL(queryNstepVo.getCstmr_tel_fn(), "");
		String cstmr_tel_mn = NVL(queryNstepVo.getCstmr_tel_mn(), "");
		String cstmr_tel_rn = NVL(queryNstepVo.getCstmr_tel_rn(), "");
		nStepVo.setWrln_tlph_no(cstmr_tel_fn + cstmr_tel_mn + cstmr_tel_rn);

		//전화번호
		String cstmr_mobile_fn = NVL(queryNstepVo.getCstmr_mobile_fn(), "");
		String cstmr_mobile_mn = NVL(queryNstepVo.getCstmr_mobile_mn(), "");
		String cstmr_mobile_rn = NVL(queryNstepVo.getCstmr_mobile_rn(), "");
		nStepVo.setTlph_no(cstmr_mobile_fn+cstmr_mobile_mn+cstmr_mobile_rn);

		String cstmr_mail_receive_flag = NVL(queryNstepVo.getCstmr_mail_receive_flag(),"");

		//이메일주소명
/*      if(NVL(queryNstepVo.getCstmr_bill_send_code(), "").equals("CB")) {
				nStepVo.setEml_adrs_nm(queryNstepVo.getCstmr_mail());
		}*/

		if(cstmr_mail_receive_flag.equals("Y")) {
			nStepVo.setEml_adrs_nm(queryNstepVo.getCstmr_mail());
		}

		//이메일청구동의여부
		if(NVL(queryNstepVo.getCstmr_bill_send_code(), "").equals("CB")) {
			nStepVo.setEml_adrs_nm(queryNstepVo.getCstmr_mail());
			nStepVo.setEml_bill_agre_yn("Y");
		}else {
			nStepVo.setEml_bill_agre_yn("N");
		}

		//청구주소동명
		nStepVo.setBill_adrs_dong_nm(queryNstepVo.getCstmr_addr());

		//청구주소명
		nStepVo.setBill_adrs_nm(queryNstepVo.getCstmr_addr_dtl());

		//청구주소우편번호
		nStepVo.setBill_adrs_zip_no(queryNstepVo.getCstmr_post());

		//납부 타입 ( C : 카드, D : 은행계좌, R : 지로 ) ( 데이터 -> AA : 자동충전, VA : 가상계좌 )
		String pay_type = NVL(queryNstepVo.getReq_pay_type(),"");
		//boolean payType = false;

		//납부방법코드
		/*if(pay_type.equals("C") || pay_type.equals("D") || pay_type.equals("AA") || pay_type.equals("VA")) {*/
		if(pay_type.equals("C") || pay_type.equals("D")) {
			nStepVo.setBlpym_mthd_cd(pay_type);
			//payType = true;

		}else if( pay_type.equals("AA") ) { //자동충전일 경우 일단 계좌로 충전이기 때문에 계좌로 설정
			nStepVo.setBlpym_mthd_cd("");

		}else if( pay_type.equals("VA") ) { //가상계좌는 없음.
			nStepVo.setBlpym_mthd_cd("");

		}else {
			nStepVo.setBlpym_mthd_cd("R");
		}

		//납부은행명
		String req_card_company =  NVL(queryNstepVo.getReq_card_company(),"");
		String req_bank = NVL(queryNstepVo.getReq_bank(),"");

	//  if(payType) {

			//납부 계좌 : 은행명/카드회사명
			if(!req_card_company.equals("")) {
				nStepVo.setBlpym_bank_nm(req_card_company);
			}else if(!req_bank.equals("")){
				nStepVo.setBlpym_bank_nm(req_bank);
			}
	//  }

		//법인일 경우임..(추가 확인 필요) 2014-10-08 주석
		/*if(cstmr_type_org.equals("JP")) {
			nStepVo.setBlpym_cust_nm(queryNstepVo.getCstmr_juridical_cname());
		}*/

		//납부 계좌 정보 ( 계좌 번호, 계좌 소유자 이름 )
		String reqAccountNumber = NVL(queryNstepVo.getReq_account_number(),"");
		String reqAccountName = NVL(queryNstepVo.getReq_account_name(), "");
		String reqAccountRrn = NVL(queryNstepVo.getReq_account_rrn(), "");

		//납부 카드 정보 ( 카드 번호, 카드 소유자 이름 )
		String reqCardNo = NVL(queryNstepVo.getReq_card_no(),"");
		String reqCardName = NVL(queryNstepVo.getReq_card_name(),"");
		String reqCardRrn = NVL(queryNstepVo.getReq_card_rrn(),"");
		String req_card_yy = NVL(queryNstepVo.getReq_card_yy(),"");
		String req_card_mm = NVL(queryNstepVo.getReq_card_mm(),"");


		if(pay_type.equals("C")) {

			if(!reqCardNo.equals("") && !reqCardName.equals("")) {
				nStepVo.setCrdt_card_no(reqCardNo);
				nStepVo.setBlpym_cust_nm(reqCardName);

				//법인일 경우 -> 법인번호 입력
				if(cstmr_type_org.equals("JP")) {

					nStepVo.setBlpym_cust_inhb_rgst_no(queryNstepVo.getCstmr_juridical_rrn());
				}else {
					if(reqCardRrn != null && reqCardRrn.length() > 5) {
						reqCardRrn = reqCardRrn.substring(0,6);
					}
					nStepVo.setBlpym_cust_inhb_rgst_no(reqCardRrn);
				}

				nStepVo.setEfct_ym(req_card_yy+req_card_mm);
			}
		}else if(pay_type.equals("D")) {

			if(!reqAccountNumber.equals("") && !reqAccountName.equals("")) {
				nStepVo.setBlpym_bnkacn_no(reqAccountNumber);
				nStepVo.setBlpym_cust_nm(reqAccountName);

				//법인일 경우 -> 법인번호 입력
				if(cstmr_type_org.equals("JP")) {
					nStepVo.setBlpym_cust_inhb_rgst_no(queryNstepVo.getCstmr_juridical_rrn());
				}else {
					if(reqAccountRrn != null && reqAccountRrn.length() > 5) {
						reqAccountRrn = reqAccountRrn.substring(0,6);
					}
					nStepVo.setBlpym_cust_inhb_rgst_no(reqAccountRrn);
				}
			}
		}else {

			//납부계좌번호 -> 계좌가 있으면 계좌 (계좌 번호, 이름, 주민번호)
			if(!reqAccountNumber.equals("") && !reqAccountName.equals("")) {
				nStepVo.setBlpym_bnkacn_no(reqAccountNumber);
				nStepVo.setBlpym_cust_nm(reqAccountName);

				if(reqAccountRrn != null && reqAccountRrn.length() > 5) {
					reqAccountRrn = reqAccountRrn.substring(0,6);
				}
				nStepVo.setBlpym_cust_inhb_rgst_no(reqAccountRrn);
			}

			//납부계좌번호 -> 카드가 있으면 카드(계좌 번호, 이름, 주민번호, 유효년월) (카드사 이름은 넘기지 않는다.)
			if(!reqCardNo.equals("") && !reqCardName.equals("")) {
				nStepVo.setCrdt_card_no(reqCardNo);
				nStepVo.setBlpym_cust_nm(reqCardName);

				if(reqCardRrn != null && reqCardRrn.length() > 5) {
					reqCardRrn = reqCardRrn.substring(0,6);
				}
				nStepVo.setBlpym_cust_inhb_rgst_no(reqCardRrn);
				nStepVo.setEfct_ym(req_card_yy+req_card_mm);
			}
		}


		/**
		 * NA   내국인
		 * NM   내국인(미성년자)
		 * PP   개인사업자
		 * JP   법인사업자
		 * FN   외국인
		 * NE   기타
		 */

		/**
		 *개인 / 개인사업자 01
		 *법인, 공공기관 03
		 *외국인 05
		 *사회보장번호/DOD 06
		 */

		/**
		 * 납부자에 따라 달라짐
		 * 개인 - 주민등록번호
		 * 법인 - 법인번호
		 * 외국인 - 외국인등록번호
		 */

		//법인일 경우 처리 로직
		//상단에서 기본적으로 결제 정보로 설정 하며, 법인 일 경우 변경한다.
		if (cstmr_type_org.equals("JP")) {
			nStepVo.setBlpym_cust_inhb_rgst_no(queryNstepVo.getCstmr_juridical_rrn());
		}


/*  //  if(payType) {
			//납부계좌번호 -> 계좌가 있으면 계좌 (계좌 번호, 이름, 주민번호)
			if(!reqAccountNumber.equals("") && !reqAccountName.equals("")) {
				nStepVo.setBlpym_bnkacn_no(reqAccountNumber);
				nStepVo.setBlpym_cust_nm(reqAccountName);

				if(reqAccountRrn != null && reqAccountRrn.length() > 5) {
					reqAccountRrn = reqAccountRrn.substring(0,6);
				}
				nStepVo.setBlpym_cust_inhb_rgst_no(reqAccountRrn);
			}

			//납부계좌번호 -> 카드가 있으면 카드(계좌 번호, 이름, 주민번호, 유효년월) (카드사 이름은 넘기지 않는다.)
			if(!reqCardNo.equals("") && !reqCardName.equals("")) {
				nStepVo.setCrdt_card_no(reqCardNo);
				nStepVo.setBlpym_cust_nm(reqCardName);

				if(reqCardRrn != null && reqCardRrn.length() > 5) {
					reqCardRrn = reqCardRrn.substring(0,6);
				}
				nStepVo.setBlpym_cust_inhb_rgst_no(reqCardRrn);
				nStepVo.setEfct_ym(req_card_yy+req_card_mm);
			}*/
//      }
		/* 2014-10-08일 주석 (  )
		//납부고객명
		if(cstmr_type_org.equals("NA")) {
			String req_account_name = NVL(queryNstepVo.getReq_account_name(),"");
			if(!req_account_name.equals("")) {
				nStepVo.setBlpym_cust_nm(queryNstepVo.getReq_account_name());
			}else {
				nStepVo.setBlpym_cust_nm(queryNstepVo.getCstmr_name());
			}
		}else if(cstmr_type_org.equals("JP")) {
			nStepVo.setBlpym_cust_nm(queryNstepVo.getCstmr_juridical_cname());

		}else {
			nStepVo.setBlpym_cust_nm(queryNstepVo.getCstmr_name());
		}

		nStepVo.setBlpym_cust_nm(queryNstepVo.getReq_account_name());
		*/

		//납부고객주민등록번호(납부고객 정보 확인 필요 현재 가입자 주민번호로 들어감)
		//2014-10-08 주석
		/*      String cstmr_native_rrn = NVL(queryNstepVo.getCstmr_native_rrn(),"");

		if(!cstmr_native_rrn.equals("") && cstmr_native_rrn.length() > 5) {
			cstmr_native_rrn = cstmr_native_rrn.substring(0, 6);
		}

		if(cstmr_type_org.equals("NA")) {
			nStepVo.setBlpym_cust_inhb_rgst_no(cstmr_native_rrn);
		}else if(cstmr_type_org.equals("JP")) {
			nStepVo.setBlpym_cust_inhb_rgst_no(queryNstepVo.getCstmr_juridical_rrn());
		}else {
			nStepVo.setBlpym_cust_inhb_rgst_no(queryNstepVo.getCstmr_foreigner_rrn());
		}*/


		//가입비유형코드
		nStepVo.setSbscst_type_cd(queryNstepVo.getJoin_price_type());

		//usim청구유형코드
		nStepVo.setUsim_bill_type_cd(queryNstepVo.getUsim_price_type());

		//고객선호1전화번호
		nStepVo.setCust_fvrt_1_tlph_no(queryNstepVo.getReq_want_number());

		//고객선호2전화번호
		nStepVo.setCust_fvrt_2_tlph_no(queryNstepVo.getReq_want_number2());

		//현재가입텔레콤코드
		if(oper_type.equals("MNP3")) {
			nStepVo.setNow_sbsc_tlcm_cd(queryNstepVo.getMove_company());
		}

		//현재고객전화번호
		String move_mobile_fn = NVL(queryNstepVo.getMove_mobile_fn(), "");
		String move_mobile_mn = NVL(queryNstepVo.getMove_mobile_mn(), "");
		String move_mobile_rn = NVL(queryNstepVo.getMove_mobile_rn(), "");
		if(oper_type.equals("MNP3")) {
			nStepVo.setNow_cust_tlph_no(move_mobile_fn + move_mobile_mn + move_mobile_rn);
		}

		//인증항목코드
		nStepVo.setAthn_item_cd(queryNstepVo.getMove_auth_type());

		//인증처리코드
		nStepVo.setAthn_trtm_cd(queryNstepVo.getMove_auth_number());

		//단말기일련번호
		nStepVo.setMsn(queryNstepVo.getReq_phone_sn());

		//유심번호(ICC아이디)
		nStepVo.setIcc_id(queryNstepVo.getReq_usim_sn());

		//대리점 아이디(ktmmobile가 대리점 이므로 고정임)        //nStepVo.setAgnc_id("AA00364");

		String agent_code = queryNstepVo.getAgent_code();

		if(real_yn.equals("Y")) {
			nStepVo.setAgnc_id(agent_code);
		}else {
			//개발은 테스트를 위하여 값이 넘어 오지 않아도 넘긴다. 초기에 모델아이디가 셋팅 안되어 있기 때문임
			if(agent_code != null) {
				nStepVo.setAgnc_id(agent_code);
			}else {
				nStepVo.setAgnc_id("AA00364");
			}
		}

		//신청서 취소 여부
		nStepVo.setAplsht_cncl_yn("N");

		//신청서등록일자
		String req_in_day = NVL(queryNstepVo.getReq_in_day(),"");
		if(req_in_day.length() > 8) {
			nStepVo.setAplsht_rgst_date(req_in_day.substring(0,8));
		}


		//개인정보내부활동동의여부
		nStepVo.setIndv_info_iner_prcuse_agre_yn(queryNstepVo.getClause_pri_collect_flag());

		//개인정보외부제공동의 여부
		nStepVo.setIndv_info_extr_prvd_agre_yn(queryNstepVo.getClause_pri_offer_flag());


		String clause_pri_ad_flag =NVL(queryNstepVo.getClause_pri_ad_flag(), "");

		if(clause_pri_ad_flag.equals("1")) {
			queryNstepVo.setClause_pri_ad_flag("Y");
		}else if(clause_pri_ad_flag.equals("0")) {
			queryNstepVo.setClause_pri_ad_flag("N");
		}

		//개인정보외부제공동의 여부
		nStepVo.setAdvr_rcv_agre_yn(queryNstepVo.getClause_pri_ad_flag());

		//특별판매번호

		//개인정보제휴제공동의여부
		nStepVo.setIndv_info_cprt_prvd_agre_yn(queryNstepVo.getClause_pri_trust_flag());


		String minor_agent_name = NVL(queryNstepVo.getMinor_agent_name(),"");
		String minor_agent_relation = NVL(queryNstepVo.getMinor_agent_relation(), "");
		if(!minor_agent_name.equals("")) {

			//대리인주민등록번호
			nStepVo.setAgnt_inhb_rgst_no(queryNstepVo.getMinor_agent_rrn());

			//대리인명
			nStepVo.setAgnt_nm(minor_agent_name);

			//대리인업무구분코드
			nStepVo.setAgnt_wrkjob_ind_cd(minor_agent_relation);
		}


		//대리인우편번호 -> 컬럼 없음

		//대리인집전화번호
		String minor_tel_fn = NVL(queryNstepVo.getMinor_agent_tel_fn(),"");
		String minor_tel_nm = NVL(queryNstepVo.getMinor_agent_tel_mn(),"");
		String minor_tel_rn = NVL(queryNstepVo.getMinor_agent_tel_rn(),"");

		if(!minor_tel_fn.equals("")) {
			try {
				String tempTel = minor_tel_fn.substring(0, 2);
				//대리인집전화번호
				if(tempTel.equals("01")) {
					nStepVo.setAgnt_prttlp_no(minor_tel_fn+minor_tel_nm+minor_tel_rn);
				}else {
					//대리인이동전화번호
					nStepVo.setAgnt_hous_tlph_no(minor_tel_fn+minor_tel_nm+minor_tel_rn);
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}

		//실사용자주문등록번호
		if(cstmr_type_org.equals("JP")) {
			nStepVo.setReal_user_inhb_rgst_no(queryNstepVo.getCstmr_native_rrn());
		}

		//실사용자명
		if(cstmr_type_org.equals("JP")) {
			nStepVo.setReal_user_nm(queryNstepVo.getCstmr_name());
		}

		//판매점아이디
		nStepVo.setSalorg_id(queryNstepVo.getSalorg_id());

		//메모내용
		nStepVo.setMemo_sbst(queryNstepVo.getOnline_auth_info());

		//사업자등록번호
		if(cstmr_type_org.equals("JP")) {
			nStepVo.setBizr_rgst_no(queryNstepVo.getCstmr_juridical_number());
		}else if(cstmr_type_org.equals("PP")) {
			nStepVo.setBizr_rgst_no(queryNstepVo.getCstmr_private_number());
		}

		//청구서발송유형코드
		nStepVo.setRqssht_dspt_type_cd(queryNstepVo.getCstmr_bill_send_code());

		String serviceType = NVL(queryNstepVo.getService_type(),"");

		//선불 ->  I
		//일반후불은 R
		if(serviceType.equals("PP")) {

			nStepVo.setUsim_pymn_mthd_cd("I");

		}else {
			nStepVo.setUsim_pymn_mthd_cd("R");
		}

		//USIM수납방법코드(후불 가입이기때문에 R로 전달한다// 확인 필요)
		/*if(oper_type.equals("NPC3")) {
			nStepVo.setUsim_pymn_mthd_cd("I");
		}else {
			nStepVo.setUsim_pymn_mthd_cd("R");
		}*/

		String req_buy_type =  NVL(queryNstepVo.getReq_buy_type(),"");

		if(!req_buy_type.equals("") && req_buy_type.equals("UU")) {
			nStepVo.setUsim_uniq_open_yn("Y");
		}else {
			nStepVo.setUsim_uniq_open_yn("N");
		}

		nStepVo.setBond_prsr_fee_cd("R");

		//정보광고이용동의여부
		nStepVo.setInfo_advr_use_agre_yn(queryNstepVo.getClause_pri_ad_flag());

		//정보관고수신동의여부
		nStepVo.setInfo_advr_rcv_agre_yn(queryNstepVo.getClause_pri_ad_flag());

		//단말기할인1금액
		nStepVo.setHndset_dscn_1_amnt(queryNstepVo.getModel_discount1());

		//단말기할인2금액
		nStepVo.setHndset_dscn_2_amnt(queryNstepVo.getModel_discount2());

		//단말기 할부
		String model_monthly = NVL(queryNstepVo.getModel_monthly(),"");
		//약정기간
		String engg_mnth_cnt = NVL(queryNstepVo.getEngg_mnth_cnt(), "");

		//할부 기간 따로 따로 보내야함
		if(!model_monthly.equals("")) {
			nStepVo.setInst_mnth_cnt(model_monthly);//할부개월수
		}

		if(!engg_mnth_cnt.equals("")) {
			nStepVo.setEngg_mnth_cnt(engg_mnth_cnt);
		}
		
		//유심타입구분코드
		//E : ESIM , U or 값 없음 : 일반유심 
		String usim_type_div_cd = NVL(queryNstepVo.getUsim_kinds_cd(), "");
		if(usim_type_div_cd.equals("09")){ //유심종류가 09 esim 인 경우에 set
			nStepVo.setUsim_type_div_cd("E");
		}else{
			nStepVo.setUsim_type_div_cd("");
		}
		
		// 2018-10-05, 기기변경인 경우 서비스계약번호 세팅
		if("HCN3".equals(queryNstepVo.getOper_type()) && !"".equals(queryNstepVo.getSvc_cntr_no())) {
			nStepVo.setSvc_cntr_no(queryNstepVo.getSvc_cntr_no());
		}
		
		//2024-03-26 본인인증값 추가
		if (!StringUtils.isEmpty(queryNstepVo.getSelf_cstmr_ci())) {
			nStepVo.setMy_self_athn_yn("Y");
			nStepVo.setIpin_ci(queryNstepVo.getSelf_cstmr_ci());
			nStepVo.setOnline_athn_div_cd(queryNstepVo.getOnline_athn_div_cd());
		} else {
			nStepVo.setMy_self_athn_yn("N");
			nStepVo.setIpin_ci("");
			nStepVo.setOnline_athn_div_cd("");
		}
		encryptNstepVo(nStepVo);
	}

	@Crypto(encryptName="NStepEnc", fields = {"cust_idnt_no"})
	public NstepVo encryptNstep(NstepVo nStepVo){
		return nStepVo;
	}

	@Crypto(decryptName="NStepDec", fields = {"cust_idnt_no"})
	public NstepVo decryptNstep(NstepVo nStepVo){
		return nStepVo;
	}

    public void doTrustToCertificates() throws Exception {
//      Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
      TrustManager[] trustAllCerts = new TrustManager[]{
          new X509TrustManager() {
              public X509Certificate[] getAcceptedIssuers() {
                  return null;
              }

              public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                  return;
              }

              public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                  return;
              }
          }
      };

      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, trustAllCerts, new SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      HostnameVerifier hv = new HostnameVerifier() {
          public boolean verify(String urlHostName, SSLSession session) {
              if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                  logger.warn("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
              }
              return true;
          }
      };
      HttpsURLConnection.setDefaultHostnameVerifier(hv);
  }


	public NstepVo encryptNstepVo(NstepVo nStepVo){
		RestTemplate restTemplate = new RestTemplate();
		String apiInterfaceServer = propertiesService.getString("api.interface.server");
		String encryptYn = "N";
		encryptYn = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getMplatformCryptionYn", "NSTEP", String.class);
		if ("Y".equals(encryptYn)) {
			nStepVo.setCust_idnt_no              	(this.encrypt(nStepVo.getCust_idnt_no()					));
			nStepVo.setCust_nm                   	(this.encrypt(nStepVo.getCust_nm()						));
			nStepVo.setTlph_no                   	(this.encrypt(nStepVo.getTlph_no()						));
			nStepVo.setEml_adrs_nm               	(this.encrypt(nStepVo.getEml_adrs_nm()					));
			nStepVo.setBill_adrs_dong_nm         	(this.encrypt(nStepVo.getBill_adrs_dong_nm()			));
			nStepVo.setBill_adrs_nm              	(this.encrypt(nStepVo.getBill_adrs_nm()					));
			nStepVo.setBill_adrs_zip_no          	(this.encrypt(nStepVo.getBill_adrs_zip_no()				));
			nStepVo.setBlpym_mthd_cd             	(this.encrypt(nStepVo.getBlpym_mthd_cd()				));
			nStepVo.setBlpym_bnkacn_no           	(this.encrypt(nStepVo.getBlpym_bnkacn_no()				));
			nStepVo.setBlpym_cust_nm             	(this.encrypt(nStepVo.getBlpym_cust_nm()				));
			nStepVo.setBlpym_cust_inhb_rgst_no   	(this.encrypt(nStepVo.getBlpym_cust_inhb_rgst_no()		));
			nStepVo.setCrdt_card_no              	(this.encrypt(nStepVo.getCrdt_card_no()					));
			nStepVo.setNow_cust_tlph_no          	(this.encrypt(nStepVo.getNow_cust_tlph_no()				));
			nStepVo.setYet_bill_bank_bnkacn_no   	(this.encrypt(nStepVo.getYet_bill_bank_bnkacn_no()		));
			nStepVo.setYet_bill_blpym_cust_nm    	(this.encrypt(nStepVo.getYet_bill_blpym_cust_nm()		));
			nStepVo.setYet_bill_bank_inhb_rgst_no	(this.encrypt(nStepVo.getYet_bill_bank_inhb_rgst_no()	));
			nStepVo.setIcc_id                    	(this.encrypt(nStepVo.getIcc_id()						));
			nStepVo.setAgnt_inhb_rgst_no         	(this.encrypt(nStepVo.getAgnt_inhb_rgst_no()			));
			nStepVo.setAgnt_nm                   	(this.encrypt(nStepVo.getAgnt_nm()						));
			nStepVo.setAgnt_zip_no               	(this.encrypt(nStepVo.getAgnt_zip_no()					));
			nStepVo.setAgnt_adrs_nm              	(this.encrypt(nStepVo.getAgnt_adrs_nm()					));
			nStepVo.setAgnt_mnt_adrs_nm          	(this.encrypt(nStepVo.getAgnt_mnt_adrs_nm()				));
			nStepVo.setAgnt_hous_tlph_no         	(this.encrypt(nStepVo.getAgnt_hous_tlph_no()			));
			nStepVo.setAgnt_prttlp_no            	(this.encrypt(nStepVo.getAgnt_prttlp_no()				));
			nStepVo.setReal_user_inhb_rgst_no    	(this.encrypt(nStepVo.getReal_user_inhb_rgst_no()		));
			nStepVo.setReal_user_nm              	(this.encrypt(nStepVo.getReal_user_nm()					));
			nStepVo.setYet_bill_stlm_card_no     	(this.encrypt(nStepVo.getYet_bill_stlm_card_no()		));
			nStepVo.setYet_bill_stlm_inhb_rgst_no	(this.encrypt(nStepVo.getYet_bill_stlm_inhb_rgst_no()	));
			nStepVo.setClcr_tlph_no              	(this.encrypt(nStepVo.getClcr_tlph_no()					));
			nStepVo.setIpin_ci				   	 	(this.encrypt(nStepVo.getIpin_ci()						));
		}
		return nStepVo;
	}

	private String encrypt(String value) {
		String result = value;

		result = KisaSeedUtil.encrypt(value);

		if (result == null || result == "null") {
			result = "";
		}
		return result;
	}
}
