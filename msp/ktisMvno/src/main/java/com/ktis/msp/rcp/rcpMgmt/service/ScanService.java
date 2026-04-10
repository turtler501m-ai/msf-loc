package com.ktis.msp.rcp.rcpMgmt.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.ktds.crypto.exception.CryptoException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.rcp.rcpMgmt.EncryptUtil;
import com.ktis.msp.rcp.rcpMgmt.MultipartUtility;
import com.ktis.msp.rcp.rcpMgmt.ObjectUtils;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.mapper.ScanMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.ScanVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class ScanService {
	private static final Log LOGGER = LogFactory.getLog(ScanService.class);
	
	public static final String ACE_256_DECRYPT_EXCEPTION = "복호화(ace256Enc) 오류 발생";
	public static final String SCAN_SERVER_SEND_EXCEPTION = "스캔서버 전송이 실패하였습니다.";
	public static final String SCAN_XML_SAVE_EXCEPTION = "스캔 XML 생성이 실패하였습니다.";
	
	@Autowired
	private ScanMapper scanMapper;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	public void prodSendScan(ScanVO vo){
		
		LOGGER.info("vo ===> "+ vo);
		
		//1. 서식지 정보를 조회
		Map<String, String> requestData = scanMapper.getAppFormData(vo);
		
		LOGGER.info("requestData ===> "+ requestData);
		
		//2. 복호화 처리
		try{
			requestData.put("CSTMR_FOREIGNER_PN",  EncryptUtil.ace256Dec(requestData.get("CSTMR_FOREIGNER_PN")) ) ;
			requestData.put("CSTMR_FOREIGNER_RRN", EncryptUtil.ace256Dec(requestData.get("CSTMR_FOREIGNER_RRN")) ) ;
			requestData.put("CSTMR_NATIVE_RRN",    EncryptUtil.ace256Dec(requestData.get("CSTMR_NATIVE_RRN")) ) ;
			requestData.put("MINOR_AGENT_RRN",     EncryptUtil.ace256Dec(requestData.get("MINOR_AGENT_RRN")) ) ;
			requestData.put("ENTRUST_RES_RRN",     EncryptUtil.ace256Dec(requestData.get("ENTRUST_RES_RRN")) ) ;
			requestData.put("OTHERS_PAYMENT_RRN",  EncryptUtil.ace256Dec(requestData.get("OTHERS_PAYMENT_RRN")) ) ;
			requestData.put("NAME_CHANGE_RRN",     EncryptUtil.ace256Dec(requestData.get("NAME_CHANGE_RRN")) ) ;
			requestData.put("REQ_ACCOUNT_NUMBER",  EncryptUtil.ace256Dec(requestData.get("REQ_ACCOUNT_NUMBER")) ) ;
			requestData.put("REQ_ACCOUNT_RRN",     EncryptUtil.ace256Dec(requestData.get("REQ_ACCOUNT_RRN")) ) ;
			requestData.put("REQ_CARD_NO",         EncryptUtil.ace256Dec(requestData.get("REQ_CARD_NO")) ) ;
		} catch (CryptoException e) {
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			LOGGER.error("Connection Exception occurred");
			throw new MvnoRunException(-1, ACE_256_DECRYPT_EXCEPTION);
		}
		
		//3. 주민번호 분석 값 설정
		settingSsnData(requestData);
		
		//4. 각종 설정 값 설정
		settingData(requestData);
		
		//5. XML 파일 생성
		createXml(vo, requestData);
		
		//6. XML 파일 전송 및 삭제 처리
		xmlFileSend(vo) ;
		
		//7. 전송상태 상태값 UPDATE 처리
		scanMapper.updateAppFormXmlYn(vo);
		
	}

	/** 주민번호 분석후 값을 설정 처리
	 * @param
	 */
	public void settingSsnData(Map<String, String> reqMapData) {
		if ( reqMapData.containsKey("CSTMR_NATIVE_RRN")
				&& !StringUtils.isBlank(reqMapData.get("CSTMR_NATIVE_RRN"))
				&& reqMapData.get("CSTMR_NATIVE_RRN").length() > 7 ) {
				
			String tempVal = reqMapData.get("CSTMR_NATIVE_RRN").substring(6, 7);
			reqMapData.put("CSTMR_NATIVE_RRN" ,reqMapData.get("CSTMR_NATIVE_RRN").substring(0, 6) );
			
			if("1".equals(tempVal)) {
				reqMapData.put("CSTMR_NATIVE_RRN_M", "V");
			} else if("2".equals(tempVal)) {
				reqMapData.put("CSTMR_NATIVE_RRN_W", "V");
			} else if("3".equals(tempVal)) {
				reqMapData.put("CSTMR_NATIVE_RRN_M", "V");
			} else if("4".equals(tempVal) ) {
				reqMapData.put("CSTMR_NATIVE_RRN_W", "V");
			}
		}
		
		if ( reqMapData.containsKey("MINOR_AGENT_RRN")
				&& !StringUtils.isBlank(reqMapData.get("MINOR_AGENT_RRN"))
				&& reqMapData.get("MINOR_AGENT_RRN").length() > 7 ) {
			
			String tempVal = reqMapData.get("MINOR_AGENT_RRN").substring(6, 7);
			reqMapData.put("MINOR_AGENT_RRN" ,reqMapData.get("MINOR_AGENT_RRN").substring(0, 6) );
			
			if("1".equals(tempVal)) {
				reqMapData.put("MINOR_AGENT_M", "V");
			} else if("2".equals(tempVal)) {
				reqMapData.put("MINOR_AGENT_W", "V");
			} else if("3".equals(tempVal)) {
				reqMapData.put("MINOR_AGENT_M", "V");
			} else if("4".equals(tempVal) ) {
				reqMapData.put("MINOR_AGENT_W", "V");
			}
		}
		
		if ( reqMapData.containsKey("ENTRUST_RES_RRN")
				&& !StringUtils.isBlank(reqMapData.get("ENTRUST_RES_RRN"))
				&& reqMapData.get("ENTRUST_RES_RRN").length() > 7 ) {
			
			String tempVal = reqMapData.get("ENTRUST_RES_RRN").substring(6, 7);
			reqMapData.put("ENTRUST_RES_RRN" ,reqMapData.get("ENTRUST_RES_RRN").substring(0, 6) );
			
			if("1".equals(tempVal)) {
				reqMapData.put("ENTRUST_RES_M", "V");
			} else if("2".equals(tempVal)) {
				reqMapData.put("ENTRUST_RES_W", "V");
			} else if("3".equals(tempVal)) {
				reqMapData.put("ENTRUST_RES_M", "V");
			} else if("4".equals(tempVal) ) {
				reqMapData.put("ENTRUST_RES_W", "V");
			}
		}
		
		if ( reqMapData.containsKey("NAME_CHANGE_RRN")
				&& !StringUtils.isBlank(reqMapData.get("NAME_CHANGE_RRN"))
				&& reqMapData.get("NAME_CHANGE_RRN").length() > 7 ) {
			
			String tempVal = reqMapData.get("NAME_CHANGE_RRN").substring(6, 7);
			reqMapData.put("NAME_CHANGE_RRN" ,reqMapData.get("NAME_CHANGE_RRN").substring(0, 6) );
			
			if("1".equals(tempVal)) {
				reqMapData.put("NAME_CHANGE_RRN_M", "V");
			} else if("2".equals(tempVal)) {
				reqMapData.put("NAME_CHANGE_RRN_W", "V");
			} else if("3".equals(tempVal)) {
				reqMapData.put("NAME_CHANGE_RRN_M", "V");
			} else if("4".equals(tempVal) ) {
				reqMapData.put("NAME_CHANGE_RRN_W", "V");
			}
		}
		
		if ( reqMapData.containsKey("OTHERS_PAYMENT_RRN")
				&& !StringUtils.isBlank(reqMapData.get("OTHERS_PAYMENT_RRN"))
				&& reqMapData.get("OTHERS_PAYMENT_RRN").length() > 6 ) {
			reqMapData.put("OTHERS_PAYMENT_RRN" ,reqMapData.get("OTHERS_PAYMENT_RRN").substring(0, 6) );
		}
		
		if ( reqMapData.containsKey("CSTMR_FOREIGNER_RRN")
				&& !StringUtils.isBlank(reqMapData.get("CSTMR_FOREIGNER_RRN"))
				&& reqMapData.get("CSTMR_FOREIGNER_RRN").length() > 7 ) {
			String tempVal = reqMapData.get("CSTMR_FOREIGNER_RRN").substring(6, 7);
			reqMapData.put("CSTMR_FOREIGNER_RRN" ,reqMapData.get("CSTMR_FOREIGNER_RRN").substring(0, 6) );
			
			if("5".equals(tempVal)) {
				reqMapData.put("CSTMR_NATIVE_RRN_M", "V");
			} else if("6".equals(tempVal)) {
				reqMapData.put("CSTMR_NATIVE_RRN_W", "V");
			}
		}
	}
	
	
	/** 주민번호 분석후 값을 설정 처리
	 * @param
	 */
	public void settingData(Map<String,String> reqMapData) {
		
		//납부방법
		if ( reqMapData.containsKey("REQ_PAY_TYPE")
				&& !StringUtils.isBlank(reqMapData.get("REQ_PAY_TYPE")) ) {
			String tempVal = reqMapData.get("REQ_PAY_TYPE");
			
			if ("D".equals(tempVal) || "AA".equals(tempVal) ) {//자동이체 = 계좌번호
				reqMapData.remove("REQ_CARD_COMPANY");
				reqMapData.remove("REQ_CARD_NO");
				reqMapData.remove("REQ_CARD_YY");
				reqMapData.remove("REQ_CARD_MM");
			}else if("C".equals(tempVal)) {// 신용카드 = 카드번호
				reqMapData.remove("REQ_BANK");
				reqMapData.remove("REQ_ACCOUNT_NUMBER");
			}
		}
		
		//구매유형
		if ( reqMapData.containsKey("REQ_BUY_TYPE")
				&& !StringUtils.isBlank(reqMapData.get("REQ_BUY_TYPE"))  ) {
			String tempVal = reqMapData.get("REQ_BUY_TYPE");

			if ("UU".equals(tempVal)) {//유심 구매면 핸드폰 대금 체크 안함
				reqMapData.remove("MODEL_MONTHLY_TYPE");
				reqMapData.remove("REQ_MODEL_NAME");
				reqMapData.remove("REQ_MODEL_COLOR");
			}else {
				String modelName = Util.NVL(reqMapData.get("REQ_MODEL_NAME"),"");
				String modelColor = Util.NVL(reqMapData.get("REQ_MODEL_COLOR"),"");
				if(!modelName.equals("")) {
					reqMapData.remove("REQ_MODEL_NAME");
					reqMapData.remove("REQ_MODEL_COLOR");
					String tempName = modelName + "( " + modelColor + " )";
					reqMapData.put("REQ_MODEL_NAME", tempName);
				}
			}
		}
		
		//신청정보_무선데이터_이용_타입
		if ( reqMapData.containsKey("REQ_WIRE_TYPE")
				&& !StringUtils.isBlank(reqMapData.get("REQ_WIRE_TYPE"))
				&& reqMapData.get("REQ_WIRE_TYPE").length() > 1 ) {
			String tempVal = reqMapData.get("REQ_WIRE_TYPE");

			String [] tempArray = tempVal.split(",");
			for (int i = 0; i < tempArray.length; i++) {
				reqMapData.put("REQ_WIRE_TYPE" + i, tempArray[i]);
			}
			reqMapData.remove("REQ_WIRE_TYPE");
		}
		
		//고객구분
		if ( reqMapData.containsKey("CSTMR_TYPE_ORG")
				&& !StringUtils.isBlank(reqMapData.get("CSTMR_TYPE_ORG")) ) {
			String tempVal = reqMapData.get("CSTMR_TYPE_ORG");

			if(!"NM".equals(tempVal)){
				reqMapData.remove("NW_BLCK_AGRM_YN");
				reqMapData.remove("APP_BLCK_AGRM_YN");
				reqMapData.remove("APP_CD");
				reqMapData.remove("APP_CD_NAME");
			}
			if(!"FN".equals(tempVal)){
				//외국인이 아니면 등록번호 여권번호 국적을 제외한다
				reqMapData.remove("CSTMR_FOREIGNER_RRN");
				reqMapData.remove("CSTMR_FOREIGNER_NATION");
				reqMapData.remove("CSTMR_FOREIGNER_PN");
			}
		}
		
		//APP구분코드
		//청소년 유해차단관련 올레 자녀 안심폰서비스 , 스마트보안관 선택시 기타는 표시 안한다
		if ( reqMapData.containsKey("APP_CD")
				&& !StringUtils.isBlank(reqMapData.get("APP_CD")) ) {
			String tempVal = reqMapData.get("APP_CD");
			
			if("1".equals(tempVal) || "3".equals(tempVal)){
				reqMapData.remove("APP_CD_NAME");
			}
		}

		// 혜택 제공을 위한 제3자 제공 동의(엠모바일), 혜택 제공을 위한 제3자 제공 동의(KT)
		// 둘 다 동의라면 전체 동의만 체크
		reqMapData.put("OTHERS_TRNS_ALL_AGREE", "N");
		if (reqMapData.containsKey("OTHERS_TRNS_AGREE") && !StringUtils.isBlank(reqMapData.get("OTHERS_TRNS_AGREE"))
			&& reqMapData.containsKey("OTHERS_TRNS_KT_AGREE") && !StringUtils.isBlank(reqMapData.get("OTHERS_TRNS_KT_AGREE"))) {
			if ("Y".equals(reqMapData.get("OTHERS_TRNS_AGREE")) && "Y".equals(reqMapData.get("OTHERS_TRNS_KT_AGREE"))) {
				reqMapData.put("OTHERS_TRNS_AGREE", "N");
				reqMapData.put("OTHERS_TRNS_KT_AGREE", "N");
				reqMapData.put("OTHERS_TRNS_ALL_AGREE", "Y");
			}
		}
	}
	
	/**
	 * 초기 XML 생성
	 * @param InitializaionXml
	 * @return
	 */
	public String InitializaionXml(ScanVO vo, int totalPage){
		// --------------------------------------------------------------------
		// Initializaion.
		// --------------------------------------------------------------------
		StringBuffer xml = new StringBuffer();
		
		xml.append("<?xml version='1.0' encoding='utf-8' ?>");
		xml.append("\n").append("<ONLINE_INFORMATION>");
		xml.append("\n").append("<REGISTER_DATA>");
		
		Map<String,String> requestData = scanMapper.getAppFormUserData(vo);
		
		String cstmrName =  requestData.get("CSTMR_NAME");
		String agencyId = requestData.get("CNTPNT_SHOP_ID");
		String companyId = requestData.get("CNTPNT_SHOP_ID");
		String rgstPrsnId = vo.getUserId();
		String resNo = requestData.get("RES_NO");
		
		xml.append("\n").append("<AGENCY_ID>"+agencyId+"</AGENCY_ID>");
		xml.append("\n").append("<COMPANY_ID>"+companyId+"</COMPANY_ID>");
		xml.append("\n").append("<CUST_NM>"+cstmrName+"</CUST_NM>");
		xml.append("\n").append("<RGST_PRSN_ID>"+rgstPrsnId+"</RGST_PRSN_ID>");
		xml.append("\n").append("<RES_NO>"+resNo+"</RES_NO>");
		xml.append("\n").append("<TOTAL_PAGE>" + totalPage + "</TOTAL_PAGE>");
		xml.append("\n").append("</REGISTER_DATA>");
		
		return  xml.toString();
	}
	
	/**
	 * 서식지 XML 생성 함수
	 * @param xml
	 */
	public void saveXML(String xml, String xmlFileName) {
		
		String scanPath = propertiesService.getString("scan.form.path");
		
		LOGGER.debug("xml ===> "+ xml );
		LOGGER.debug("xmlFileName ===> "+ xmlFileName );
		LOGGER.debug("scanPath ===> "+ scanPath );
		
		FileOutputStream fos = null;
		try {
			String xmlSavePath = scanPath;
			
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(xml)));
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			
			fos = new FileOutputStream(new File(xmlSavePath + xmlFileName));
			StreamResult result = new StreamResult(fos);
			transformer.transform(source, result);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new MvnoRunException(-1,SCAN_XML_SAVE_EXCEPTION);
		} finally {
			
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
//					20200512 소스코드점검 수정
//			    	e.printStackTrace();
					//System.out.println("Connection Exception occurred");
					LOGGER.error("Connection Exception occurred");
				}
			}
		}
	}
	
	public void createXml(ScanVO vo, Map<String,String> requestData){
		File folderPath = new File(vo.getScanPath());
		
		if(!folderPath.exists()){
			folderPath.mkdirs();
		}
		
		LOGGER.info("[createXml]scanVO = " + vo);
		LOGGER.info("[createXml]requestData = " + requestData);
		
//		String tempIndex = "1";
//		boolean xmlCreate = true;
//		boolean inputTagCreate = true;
		
//		StringBuffer xml = new StringBuffer(InitializaionXml(vo));
		
		// 서식지 그룹화
		String tempPageCode = "";
		int totalPage = 0; //서식지 갯수
		boolean xmlCreate = true;
		
		String xmlFileName = "online_information" + "_" + vo.getRequestKey() + ".xml";
		
		LOGGER.info("[createXml]xmlFileName = " + xmlFileName);
		
		//서식지 그룹 코드 가져오기
		String groupCode = this.getGroupCode(requestData);
		
		LOGGER.info("[createXml]groupCode = " + groupCode);
		
		//5-1. 서식지 위치정보 가져오기
		List<Map<String, String>> appPointInfoList = scanMapper.getAppFormPointGroupList(groupCode);
		
		StringBuffer inputDataXml = new StringBuffer();
		
		for (int i = 0; i < appPointInfoList.size(); i++) {
			Map<String,String> appPointInfo = appPointInfoList.get(i);
			
			LOGGER.info("[createXml]appPointInfo = " + appPointInfo);
			
			String appFormColunmName = appPointInfo.get("COLUMN_NAME"); //데이터 저장 칼럼명
			int metaRow              = Integer.parseInt(appPointInfo.get("METAROW"));
			int metaLine             = Integer.parseInt(appPointInfo.get("METALINE"));
			String codeDataYn        = String.valueOf(Util.NVL(appPointInfo.get("CODEDATA_YN"), ""));
			String codeData          = String.valueOf(Util.NVL(appPointInfo.get("CODEDATA"), ""));
//			String pageIndex         = String.valueOf(Util.NVL(appPointInfo.get("PAGEINDEX"), ""));
			String pageCode          = String.valueOf(Util.NVL(appPointInfo.get("PAGE_CODE"), ""));
			String deleteColumnYn    = String.valueOf(Util.NVL(appPointInfo.get("DELETECOLUMNYN"), ""));
			String appFormInsertData = String.valueOf(requestData.get(appFormColunmName));
			
			if (i == 0) {
				inputDataXml.append("\n").append("<INPUT_DATA pagecode='"+pageCode+"'>");
				tempPageCode = pageCode;
				totalPage++;
			}
			
			if(!pageCode.equals(tempPageCode)) {
				inputDataXml.append("\n").append("</INPUT_DATA>");
				inputDataXml.append("\n").append("<INPUT_DATA pagecode='"+pageCode+"'>");
				tempPageCode = pageCode;
				totalPage++;
			}
			
			//마켓팅 제공 동의
			if(deleteColumnYn.equals("CLAUSE_PRI_AD_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_PRI_AD_FLAG"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}
			
			//2015-12-23제휴관련 추가
			if(deleteColumnYn.equals("CLAUSE_JEHU_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_JEHU_FLAG"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}

			//20160601 렌탈 단말배상금 안내사항 동의여부
			if(deleteColumnYn.equals("CLAUSE_RENTAL_MODEL_CP")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RENTAL_MODEL_CP"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}
			
			//20160601 렌탈 단말배상금(부분파손) 안내사항 동의여부
			if(deleteColumnYn.equals("CLAUSE_RENTAL_MODEL_CP_PR")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RENTAL_MODEL_CP_PR"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}
			
			//20160601 중고렌탈 프로그램 서비스 이용에 대한 동의서 동의여부
			if(deleteColumnYn.equals("CLAUSE_RENTAL_SERVICE")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RENTAL_SERVICE"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}
			
			//20170110 MPPS35선불요금제 제약사항 동의 여부
			if(deleteColumnYn.equals("CLAUSE_MPPS35_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_MPPS35_FLAG"));
				if( !deleteTempValue.equals("Y")) { //Y일때만 서명 입력
					continue;
				}
			}
			
			//201703 금융제휴 요금제 동의 여부
			if (deleteColumnYn.equals("CLAUSE_FINANCE_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_FINANCE_FLAG"));
				if (deleteTempValue.equals("Y")) { //Y일때만 서명 입력
					//법정대리인 서명 여부
					if ("MINOR".equals(codeData) && (StringUtils.isEmpty(requestData.get("MINOR_AGENT_NAME")) || StringUtils.isEmpty(requestData.get("MINOR_AGENT_RRN")))) {
						continue;
					}
				} else {
					continue;
				}
			}

			if(deleteColumnYn.equals("CLAUSE_5G_COVERAGE_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_5G_COVERAGE_FLAG"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}

			if(deleteColumnYn.equals("PERSONAL_INFO_COLLECT_AGREE")) {
				String deleteTempValue = String.valueOf(requestData.get("PERSONAL_INFO_COLLECT_AGREE"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}

			if(deleteColumnYn.equals("OTHERS_TRNS_AGREE")) {
				String deleteTempValue = String.valueOf(requestData.get("OTHERS_TRNS_AGREE"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}

			if(deleteColumnYn.equals("CLAUSE_PARTNER_OFFER_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_PARTNER_OFFER_FLAG"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}

			if(deleteColumnYn.equals("OTHERS_TRNS_KT_AGREE")) {
				String deleteTempValue = String.valueOf(requestData.get("OTHERS_TRNS_KT_AGREE"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}

			if(deleteColumnYn.equals("OTHERS_TRNS_ALL_AGREE")) {
				String deleteTempValue = String.valueOf(requestData.get("OTHERS_TRNS_ALL_AGREE"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}

			if(deleteColumnYn.equals("OTHERS_AD_RECEIVE_AGREE")) {
				String deleteTempValue = String.valueOf(requestData.get("OTHERS_AD_RECEIVE_AGREE"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}
			
			//메타 데이터 여부
			if(codeDataYn.equals("Y")) {
				//데이터와 메타 정보가 동일 한지 확인한다.
				if(appFormInsertData.equals(codeData)) { // 메타데이터를 V로 변환한다.
					xmlCreate = true;
					if(appFormColunmName.equals("PROD_TYPE")){//20160608 렌탈일때만 출고가 밑에 텍스트 넣어준다
						appFormInsertData = "(분실파손 단말배상금)";
					}else{
						appFormInsertData = "V";
					}
				}else {
					xmlCreate = false;
				}
			}else {
				xmlCreate = true;
			}
			
			//데이터 생성 여부
			if(xmlCreate && Util.isNotNull(appFormInsertData)) {
				inputDataXml.append("\n").append("<DATA XPosition='"+metaLine+"' YPosition='"+metaRow+"'>"+appFormInsertData+"</DATA>");
			}
			
		}// end for
		
		inputDataXml.append("\n").append("</INPUT_DATA>");
		inputDataXml.append("\n").append("</ONLINE_INFORMATION>");
		
		//REGISTER_DATA XML 생성
		StringBuffer registerDataXml = new StringBuffer(InitializaionXml(vo, totalPage));
		
		String xml = registerDataXml.append(inputDataXml).toString();
		
		saveXML(xml, xmlFileName);
			
		LOGGER.info("requestData xml ===> "+ xml );
			
	}
	
	/**
	 * 저장된 XML 전달
	 * @param xmlFileName
	 * @throws IOException
	 */
	public void xmlFileSend(ScanVO vo) {
		
		
		String xmlFileName = "online_information" + "_" + vo.getRequestKey() + ".xml";
		LOGGER.error("fileName #########################:" + xmlFileName);

		// --------------------------------------------------------------------
		// Initializaion.
		// --------------------------------------------------------------------
		//요청 Url
		String requestUrl = vo.getScanUrl();

		//인코딩
		String charSet = "UTF-8";
		// 파일 불러오는 경로
		String filePath = vo.getScanPath() + xmlFileName;

		LOGGER.error("scan url ################################: " + requestUrl);
		
		//List<String> responseList = new ArrayList<String>();

		// --------------------------------------------------------------------
		// Service Process.
		// --------------------------------------------------------------------
		File xmlFile = new File(filePath);
		try {
			MultipartUtility multipart = new MultipartUtility(requestUrl, charSet);
			multipart.addFilePart("TransferFile", xmlFile);
			//responseList = multipart.finish();
			List<String> responseList = multipart.finish();
			if (responseList != null && responseList.size() > 0) {
				LOGGER.info(ObjectUtils.convertObjectToString("JTK responseList ::" + responseList));
			}
			
		} catch (Exception e) {
//			e.printStackTrace();
			LOGGER.error(e.getMessage());
			throw new MvnoRunException(-1, SCAN_SERVER_SEND_EXCEPTION);
		} finally {
			// DELETE FILE
			if (xmlFile != null) {
				xmlFile.delete();
			}
		}
		
		LOGGER.error("xmlFileSend END ################################: " );
	}
	
	//서식지 그룹 구분하기
	private String getGroupCode(Map<String,String> requestData) {
		
		LOGGER.info("[getGroupCode] requestData :: " + requestData);
		
		String groupCode = "";
		
		boolean isFinaceRate = false; //금융제휴 요금제 여부
		boolean isRental = false;//렌탈 상품 여부
		
		String socCode = Util.NVL(requestData.get("SOC_CODE"), ""); //가입 요금제 코드
		String clauseRentalService = Util.NVL(requestData.get("CLAUSE_RENTAL_SERVICE"), ""); //렌탈상품
		
//		String socCode = requestData.containsKey("SOC_CODE") && requestData.get("SOC_CODE") != null ? requestData.get("SOC_CODE") : "";
//		String clauseRentalService = requestData.containsKey("CLAUSE_RENTAL_SERVICE") && requestData.get("CLAUSE_RENTAL_SERVICE") != null ? requestData.get("CLAUSE_RENTAL_SERVICE") : "";
		
		LOGGER.info("[getGroupCode] socCode :: " + socCode);
		LOGGER.info("[getGroupCode] clauseRentalService :: " + clauseRentalService);
		
		//금융제휴 요금제 체크
		List<Map<String, Object>> list = scanMapper.getMcpCodeList("ClauseFinanceRatesCD");
		
		if(list != null){
			for(Map<String, Object> map : list){
				if(socCode.equals((String) map.get("DTL_CD"))){
					isFinaceRate = true;
					break;
				}
			}
		}
		
		//렌탈 상품 체크
		if ("Y".equals(clauseRentalService)) {
			isRental = true;
		}
		
		if (isRental) {
			if (isFinaceRate) { //렌탈동부그룹
				groupCode = "B002";
			} else { //렌탈그룹
				groupCode = "B001";
			}
		} else {
			if (isFinaceRate) { //기본동부그룹
				groupCode = "A002";
			} else { //기본그룹
				groupCode = "A001"; //default 그룹
			}
		}
		
		LOGGER.info("[getGroupCode] getGroupCode :: " + groupCode);
		
		return groupCode;
	}
	
}
