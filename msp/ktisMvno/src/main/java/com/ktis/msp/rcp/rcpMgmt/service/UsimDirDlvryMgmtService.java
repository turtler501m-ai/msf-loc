/**
 * 
 */
package com.ktis.msp.rcp.rcpMgmt.service;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.mapper.UsimDirDlvryMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDirDlvryMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryMgmtVO;
import com.ktis.msp.util.CommonHttpClient;
import com.ktis.msp.util.XmlParse;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @author 
 *
 */
@Service
public class UsimDirDlvryMgmtService extends BaseService {
	private static final Log LOGGER = LogFactory.getLog(NstepCallService.class);
	
	protected final static String HEADER = "commHeader";
	protected final static String GLOBAL_NO = "globalNo";
	protected final static String RESPONSE_TYPE = "responseType";
	protected final static String RESPONSE_CODE = "responseCode";
	protected final static String RESPONSE_BASIC = "responseBasic";
	
	public static final String MPLATFORM_RESPONEXML_EMPTY_EXCEPTION = "서버(M-Platform) 점검중 입니다. 잠시후 다시 시도 하기기 바랍니다.(XML EMPTY)";
	
	@Autowired
	private RcpEncService encSvc;

	@Autowired
	private UsimDirDlvryMgmtMapper usimDirDlvryMgmtMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

	/** Constructor */
	public UsimDirDlvryMgmtService() {
		setLogPrefix("[UsimDirDlvryMgmtService] ");
	}
	
	
	/**
	 * 신청정보(바로배송)
	 * @param paramMap
	 * @return
	 */
	public List<?> getUsimDirDlvryList(Map<String, Object> paramMap){
		
		// 필수조건 체크
		if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
			if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
				throw new MvnoRunException(-1, "검색값 누락");
			}
		} else {
			if(paramMap.get("strtDt") == null || "".equals(paramMap.get("strtDt"))){
				throw new MvnoRunException(-1, "시작일자 누락");
			}
			if(paramMap.get("endDt") == null || "".equals(paramMap.get("endDt"))){
				throw new MvnoRunException(-1, "종료일자 누락");
			}
		}
		
		List<EgovMap> list = (List<EgovMap>) usimDirDlvryMgmtMapper.getUsimDirDlvryList(paramMap);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	public List<?> getUsimDirDlvryListByExcel(Map<String, Object> paramMap){
		
		// 필수조건 체크
		if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
			if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
				throw new MvnoRunException(-1, "검색값 누락");
			}
		} else {
			if(paramMap.get("strtDt") == null || "".equals(paramMap.get("strtDt"))){
				throw new MvnoRunException(-1, "시작일자 누락");
			}
			if(paramMap.get("endDt") == null || "".equals(paramMap.get("endDt"))){
				throw new MvnoRunException(-1, "종료일자 누락");
			}
		}
		
		List<EgovMap> list = (List<EgovMap>) usimDirDlvryMgmtMapper.getUsimDirDlvryListByExcel(paramMap);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void setUsimDirDlvryInfo(UsimDirDlvryMgmtVO usimDirDlvryMgmtVO) {
		// 이력이 없으면 최초이력 생성
		if (usimDirDlvryMgmtMapper.chkUsimDirDlvryHst(usimDirDlvryMgmtVO) < 1) {
			usimDirDlvryMgmtMapper.updateUsimDirDlvryFstHst(usimDirDlvryMgmtVO);
		}
		
		usimDirDlvryMgmtMapper.updateUsimDirDlvryInfo(usimDirDlvryMgmtVO);
		
		// 상담 이력 등록
		usimDirDlvryMgmtMapper.updateUsimDirDlvryHst(usimDirDlvryMgmtVO);		
	}
	
	/**
	 * 바로배송 상담이력 조회
	 * @param paramMap
	 * @return
	 */
	public List<?> getUsimDirDlvryHst(Map<String, Object> paramMap){		
		List<EgovMap> list = (List<EgovMap>) usimDirDlvryMgmtMapper.getUsimDirDlvryHst(paramMap);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void setUsimDirDlvryStateCd(UsimDirDlvryMgmtVO usimDirDlvryMgmtVO) {
		
		usimDirDlvryMgmtMapper.updateUsimDirDlvryStateCd(usimDirDlvryMgmtVO);
		
	}
	
	
	public Map<String, Object>  dvryServiceCall(HashMap<String,String> param, UsimDirDlvryMgmtVO vo ,int timeout) throws MvnoRunException, SocketTimeoutException{

	        String responseXml = "";
	        HashMap<String, Object> resultMap = new HashMap<String, Object>();
	        try {

	            //엠플렛폼 로그 저장
	            HashMap<String, String> pMplatform = this.saveDvryUsimMplatLog(param);

	            String getURL = this.getURL(pMplatform);
	            String callUrl = (String) propertiesService.getString("dvryUsimUrl");
	            //CommonHttpClient client = new CommonHttpClient(callUrl);
	            NameValuePair[] data = {
	                new NameValuePair("getURL", getURL)
	            };

	            logger.debug("*** M-PlatForm Connect Start ***");
	            logger.debug("*** M-PlatForm Call URL *** " + callUrl);
	            logger.debug("*** M-PlatForm Data *** " + data);

	            responseXml = CommonHttpClient.Mplatpost(callUrl,data, "UTF-8",timeout);

	            logger.debug("responseXml : " + responseXml);
          
	            if (responseXml.isEmpty()) {
	                throw new MvnoRunException(-1 , MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
	            } else {
	            	HashMap<String, Object> returnMap = this.toResponseParse(responseXml, vo.getAppEventCd());
	            	
	            	LOGGER.debug("returnMap=" + returnMap);
					
					resultMap.put("code", (String) returnMap.get("rsltCd"));
					resultMap.put("msg", (String) returnMap.get("rsltMsg"));
					
					if(!"N".equals((String) returnMap.get("responseType"))){
						resultMap.put("code", (String) returnMap.get("responseCode"));
						resultMap.put("msg", (String) returnMap.get("responseBasic"));
					}else{
						if("N".equals((String) returnMap.get("responseType")) && (returnMap.get("rsltCd") == null || "".equals((String)returnMap.get("rsltCd")) ) ){
							// 이력이 없으면 최초이력 생성
							if (usimDirDlvryMgmtMapper.chkUsimDirDlvryHst(vo) < 1) {
								usimDirDlvryMgmtMapper.updateUsimDirDlvryFstHst(vo);
							}
							usimDirDlvryMgmtMapper.updateUsimDirDlvryStateCd(vo);
							resultMap.put("code", "S");
							// 상담 이력 등록
							usimDirDlvryMgmtMapper.updateUsimDirDlvryHst(vo);
						}
					}
	            }
	            logger.debug("*** M-PlatForm Connect End ***");

	        } catch (Exception e) {
	        	resultMap.put("code", "9999");
				resultMap.put("msg",  "MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
				LOGGER.error("MPLATFORM 전송시 문제가 생긴다면 강제로 메시지 세팅 후 정상 처리.");
				throw new MvnoErrorException(e); 
	        }
	        return resultMap;
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
	            logger.error(e);
	        }
	        return result;
	    }
    /**
     * 엠플렛폼 서비스 연동 로그 저장
     * @param param
     */
    private HashMap<String, String> saveDvryUsimMplatLog(HashMap<String,String> param) {
        HashMap<String, String> tmpParm = param;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            
            //mdlInd=WEB&appEventCd=X25&ncn=541166507&url=/mypage/requestView.do&ip=10.226.84.42&userid=youngtomo
            tmpParm.put("ip", this.getClientIp());
            tmpParm.put("url", request.getRequestURI());
            tmpParm.put("mdlInd", "WEB");
            tmpParm.put("userid", param.get("USER_ID"));

        } catch (Exception e) {
            logger.error("엠플렛폼 연동 정보 저장 오류 : " + e.getMessage());
        }
        return tmpParm;
    }
    
    public String getClientIp()  {
        String clientIp = "";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if(request.getHeader("X-Forwarded-For") == null) {
            clientIp = request.getRemoteAddr();
        } else {
            clientIp = request.getHeader("X-Forwarded-For");
            if (clientIp !=null && ! clientIp.equals("") && clientIp.indexOf(",")>-1) {
                clientIp =  clientIp.split("\\,")[0].trim();
            }
        }

        return clientIp;
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
		
		if("D03".equals(appEventCd)){
			resultMap.put("rsltCd",  XmlParse.getChildValue(rtn, "rsltCd"));
			resultMap.put("rsltMsg", XmlParse.getChildValue(rtn, "rsltMsg"));
		}
		return resultMap;
	}
	
    
//	
//	@Transactional(rollbackFor=Exception.class)
//	public void setUsimDlvryInfo(UsimDlvryMgmtVO usimDlvryMgmtVO) {
//		
//		usimDlvryMgmtMapper.updateUsimDlvryInfo(usimDlvryMgmtVO);
//		
//	}
//	
//	@Transactional(rollbackFor=Exception.class)
//	public void regDlvryNoList(UsimDlvryMgmtVO usimDlvryMgmtVO, String userId) {
//		
//		List<UsimDlvryMgmtVO> itemList = usimDlvryMgmtVO.getItems();
//		
//		int cnt = 1;
//		
//		for( UsimDlvryMgmtVO vo : itemList ) {
//			cnt++;
//			
//			if (vo.getSelfDlvryIdx() == null || "".equals(vo.getSelfDlvryIdx())){
//				throw new MvnoRunException(-1, "["+cnt+"행]주문번호는 필수 항목 입니다.");
//			}
//			if (vo.getTbNm() == null || "".equals(vo.getTbNm())){
//				throw new MvnoRunException(-1, "["+cnt+"행]택배사는 필수 항목 입니다.");
//			}
//			if (vo.getDlvryNo() == null || "".equals(vo.getDlvryNo())){
//				throw new MvnoRunException(-1, "["+cnt+"행]송장번호는 필수 항목 입니다.");
//			}
//			
//			String tbCd = usimDlvryMgmtMapper.getDlvryTbCd(vo.getTbNm());
//			
//			if (tbCd == null || "".equals(tbCd)) {
//				throw new MvnoRunException(-1, "["+cnt+"행]일치하는 택배사가 없습니다.");
//			}
//			
//			vo.setTbCd(tbCd);
//			String result = usimDlvryMgmtMapper.isDlvryInfoChk(vo.getSelfDlvryIdx());
//			
//			if (result == null) {
//				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]일치하는 주문번호가 없습니다.");
//			} else if (result.equals("N")) {
//				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]이미 등록된 송장번호가 있습니다.");
//			}
//			
//			String cdName = vo.getTbNm();
//			String dlvrNo = vo.getDlvryNo();
//			String mobileNo = result;
//			String templateId = "109";
//			
//			usimDlvryMgmtMapper.updateDlvryNo(vo);
//			
//			if (mobileNo != null && mobileNo.length() > 2 && mobileNo.substring(0,2).equals("01")) {
//				// SMS 템플릿 제목,내용 가져오기
//				MsgQueueReqVO msgVO = smsMgmtMapper.getTemplateText(templateId);
//				msgVO.setText((msgVO.getTemplateText())
//						.replaceAll(Pattern.quote("#{cdName}"), cdName)
//						.replaceAll(Pattern.quote("#{dlvrNo}"), dlvrNo));
//				msgVO.setMobileNo(result);
//				msgVO.setTemplateId(templateId);
//				
//				// SMS 저장
//				smsMgmtMapper.insertMsgQueue(msgVO);
//				
//				SmsSendVO sendVO = new SmsSendVO();
//				sendVO.setSendDivision("MCP");
//				sendVO.setTemplateId(templateId);
//				sendVO.setMseq(msgVO.getMseq());
//				sendVO.setRequestTime(msgVO.getRequestTime());
//				sendVO.setReqId(userId);
//				
//				// 발송내역 저장
//				smsMgmtMapper.insertSmsSendMst(sendVO);
//			}
//			
//									
//		}
//    }
//	
//	@Transactional(rollbackFor=Exception.class)
//	public void regDlvryWaitList(UsimDlvryMgmtVO usimDlvryMgmtVO) {
//		
//		List<UsimDlvryMgmtVO> itemList = usimDlvryMgmtVO.getItems();
//				
//		for( UsimDlvryMgmtVO vo : itemList ) {
//												
//			
//			String result = usimDlvryMgmtMapper.isDlvryIdxChk(vo.getSelfDlvryIdx());
//			
//			if (result == null) {
//				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]일치하는 주문번호가 없습니다.");
//			} 
//			
//			usimDlvryMgmtMapper.updateDlvryWait(vo);
//						
//		}
//    }
//	
//	@Transactional(rollbackFor=Exception.class)
//	public void regDlvryOkList(UsimDlvryMgmtVO usimDlvryMgmtVO) {
//		
//		List<UsimDlvryMgmtVO> itemList = usimDlvryMgmtVO.getItems();
//				
//		for( UsimDlvryMgmtVO vo : itemList ) {
//												
//			String result = usimDlvryMgmtMapper.isDlvryNoChk(vo.getDlvryNo());
//			if (result == null) {
//				throw new MvnoRunException(-1, "[송장번호:"+vo.getDlvryNo()+"]일치하는 송장번호가 없습니다.");
//			}
//			
//			usimDlvryMgmtMapper.updateDlvryOk(vo);
//						
//		}
//    }
//	
//	@Transactional(rollbackFor=Exception.class)
//	public void regOpenOkList(UsimDlvryMgmtVO usimDlvryMgmtVO) {
//		
//		List<UsimDlvryMgmtVO> itemList = usimDlvryMgmtVO.getItems();
//		
//		int cnt = 1;
//		
//		for( UsimDlvryMgmtVO vo : itemList ) {
//			cnt++;
//			
//			if (vo.getSelfDlvryIdx() == null || "".equals(vo.getSelfDlvryIdx())){
//				throw new MvnoRunException(-1, "["+cnt+"행]주문번호는 필수 항목 입니다.");
//			}
//			if (vo.getContractNum() == null || "".equals(vo.getContractNum())){
//				throw new MvnoRunException(-1, "["+cnt+"행]계약번호는 필수 항목 입니다.");
//			}
//									
//			String result = usimDlvryMgmtMapper.isOpenInfoChk(vo.getSelfDlvryIdx());
//			if (result == null) {
//				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]일치하는 주문번호가 없습니다.");
//			} else if (result.equals("N")) {
//				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]이미 등록된 계약번호가 있습니다.");
//			}
//			
//			usimDlvryMgmtMapper.updateOpenOk(vo);
//						
//		}
//    }
//	
//	//2020.12.10 유심일련번호 등록 로직
//	@Transactional(rollbackFor=Exception.class)
//	public void regUsimSnList(UsimDlvryMgmtVO usimDlvryMgmtVO) {
//		
//		List<UsimDlvryMgmtVO> itemList = usimDlvryMgmtVO.getItems();
//		
//		int cnt = 1;
//		
//		for( UsimDlvryMgmtVO vo : itemList ) {
//			cnt++;
//			
//			if (vo.getSelfDlvryIdx() == null || "".equals(vo.getSelfDlvryIdx())){
//				throw new MvnoRunException(-1, "["+cnt+"행]주문번호는 필수 항목 입니다.");
//			}
//			if (vo.getReqUsimSn() == null || "".equals(vo.getReqUsimSn())){
//				throw new MvnoRunException(-1, "["+cnt+"행]유심일련번호는 필수 항목 입니다.");
//			}
//									
//			String result = usimDlvryMgmtMapper.isDlvryIdxChk(vo.getSelfDlvryIdx());
//			if (result == null) {
//				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]일치하는 주문번호가 없습니다.");
//			}
//			
//			usimDlvryMgmtMapper.updateUsimSn(vo);
//						
//		}
//    }
//	
//	
	
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrName",			"CUST_NAME");
		maskFields.put("cstmrForeignerRrn",	"SSN");
		maskFields.put("cstmrMobile",		"MOBILE_PHO");
		maskFields.put("cstmrForeignerPn",	"PASSPORT");
		maskFields.put("cstmrJuridicalRrn",	"CORPORATE");
		maskFields.put("cstmrMail",			"EMAIL");
		maskFields.put("cstmrAddr",			"ADDR");
		maskFields.put("cstmrTel",			"TEL_NO");
		maskFields.put("dlvryName",			"CUST_NAME");
		maskFields.put("dlvryTel",			"TEL_NO");
		maskFields.put("dlvryMobile",		"MOBILE_PHO");
		maskFields.put("dlvryAddr",			"ADDR");
		maskFields.put("dlvryPostAddr",			"ADDR");
		maskFields.put("reqAccountNumber",	"ACCOUNT");
		maskFields.put("reqAccountName",	"CUST_NAME");
		maskFields.put("reqAccountRrn",		"SSN");
		maskFields.put("reqCardNo",			"CREDIT_CAR");
		maskFields.put("reqCardMm",			"CARD_EXP");
		maskFields.put("reqCardYy",			"CARD_EXP");
		maskFields.put("reqCardName",		"CUST_NAME");
		maskFields.put("reqCardRrn",		"SSN");
		maskFields.put("reqGuide",			"MOBILE_PHO");
		maskFields.put("moveMobile",		"MOBILE_PHO");
		maskFields.put("minorAgentName",	"CUST_NAME");
		maskFields.put("minorAgentRrn",		"SSN");
		maskFields.put("minorAgentTel",		"TEL_NO");
		maskFields.put("reqUsimSn",			"USIM");
		maskFields.put("reqPhoneSn",		"DEV_NO");
		maskFields.put("cstmrNativeRrn",	"SSN");
		maskFields.put("faxnum",			"TEL_NO");
		maskFields.put("selfIssuNum",		"PASSPORT");	// 본인인증번호
		maskFields.put("minorSelfIssuNum",	"PASSPORT");	// 본인인증번호

		maskFields.put("cstmrName2",		"CUST_NAME");
		maskFields.put("cstmrNameMask",		"CUST_NAME");
		maskFields.put("cstmrAddrDtl",		"PASSWORD");
		maskFields.put("dlvryPostAddrDtl",	"PASSWORD");
		maskFields.put("dlvryAddrDtl",		"PASSWORD");
		maskFields.put("cstmrTelMn",		"PASSWORD");
		maskFields.put("cstmrMobileMn",		"PASSWORD");
		maskFields.put("dlvryTelMn",		"PASSWORD");
		maskFields.put("dlvryMobileMn",		"PASSWORD");
		maskFields.put("moveMobileMn",		"PASSWORD");
		maskFields.put("reqUsimSnMask",		"USIM");
		maskFields.put("reqPhoneSnMask",	"DEV_NO");		
		maskFields.put("userId",			"SYSTEM_ID");
				
		maskFields.put("rvisnNm",			"CUST_NAME"); //수정자
		maskFields.put("rvisnId",			"CUST_NAME"); //수정자
		maskFields.put("cnclNm",			"CUST_NAME"); //결제취소자
		
		return maskFields;
	}

}