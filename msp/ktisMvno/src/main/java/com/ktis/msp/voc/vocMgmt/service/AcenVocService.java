package com.ktis.msp.voc.vocMgmt.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.org.csanalyticmgmt.mapper.AcenVocAgntMapper;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;
import com.ktis.msp.rcp.rcpMgmt.EncryptUtil;
import com.ktis.msp.util.CommonHttpClient;
import com.ktis.msp.util.ParseHtmlTagUtil;
import com.ktis.msp.voc.vocMgmt.mapper.AcenVocMapper;
import com.ktis.msp.voc.vocMgmt.vo.AcenVocVo;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.commons.httpclient.NameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class AcenVocService extends BaseService {

	@Autowired
	private AcenVocMapper acenVocMapper;

	@Autowired
	private MaskingService maskingService;

	@Autowired
	private AcenVocAgntMapper acenVocAgntMapper;

	@Autowired
	private SmsMgmtMapper smsMgmtMapper;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	private static final String VOC_SMS_TEMPLATE = "372";


	/** (A'Cen) 대리점 VOC 목록 리스트 조회 */
	public List<?> getAcenVocList(AcenVocVo searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = acenVocMapper.getAcenVocList(searchVO);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		return list;
	}

	/** (A'Cen) 대리점 VOC 목록 리스트 엑셀 다운로드 */
	public List<?> getAcenVocListExcel(AcenVocVo searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = acenVocMapper.getAcenVocListExcel(searchVO);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		return list;
	}

	/** (A'Cen) 대리점 VOC 목록 리스트 수정이력 조회 */
	public List<?> getAcenVocHistList(AcenVocVo searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = acenVocMapper.getAcenVocHistList(searchVO);

		String vocContent = null;
		String ansContent = null;
		String callContent = null;

		for(EgovMap map : list){

			vocContent = (String) map.get("vocContent");
			ansContent = (String) map.get("ansContent");
			callContent = (String) map.get("callContent");

			if(vocContent != null){
				map.put("vocContent", ParseHtmlTagUtil.convertHtmlchars(vocContent));
			}

			if(ansContent != null){
				map.put("ansContent", ParseHtmlTagUtil.convertHtmlchars(ansContent));
			}

			if(callContent != null){
				map.put("callContent", ParseHtmlTagUtil.convertHtmlchars(callContent));
			}
		}

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		return list;
	}

	/** (A'Cen) 대리점 VOC 목록 리스트 수정이력 엑셀 다운로드 */
	public List<?> getAcenVocHistListExcel(AcenVocVo searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = acenVocMapper.getAcenVocHistListExcel(searchVO);

		String vocContent = null;
		String ansContent = null;
		String callContent = null;

		for(EgovMap map : list){

			vocContent = (String) map.get("vocContent");
			ansContent = (String) map.get("ansContent");
			callContent = (String) map.get("callContent");

			if(vocContent != null){
				map.put("vocContent", ParseHtmlTagUtil.convertHtmlchars(vocContent));
			}

			if(ansContent != null){
				map.put("ansContent", ParseHtmlTagUtil.convertHtmlchars(ansContent));
			}

			if(callContent != null){
				map.put("callContent", ParseHtmlTagUtil.convertHtmlchars(callContent));
			}
		}

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		return list;
	}

	/** (A'Cen) 대리점 VOC 목록 개별건 조회 */
	public AcenVocVo getAcenVocInfo(AcenVocVo searchVO) {

		AcenVocVo vocInfo = acenVocMapper.getAcenVocInfo(searchVO);

		if(vocInfo != null && !KtisUtil.isEmpty(vocInfo.getVocContent())){
			vocInfo.setVocContent(ParseHtmlTagUtil.convertHtmlchars(vocInfo.getVocContent()));
		}

		if(vocInfo != null && !KtisUtil.isEmpty(vocInfo.getAnsContent())){
			vocInfo.setAnsContent(ParseHtmlTagUtil.convertHtmlchars(vocInfo.getAnsContent()));
		}

		if(vocInfo != null && !KtisUtil.isEmpty(vocInfo.getCallContent())){
			vocInfo.setCallContent(ParseHtmlTagUtil.convertHtmlchars(vocInfo.getCallContent()));
		}

		return vocInfo;
	}

	/** (A'Cen) VOC 처리점 등록 */
	@Transactional(rollbackFor=Exception.class)
	public void updateAcenVocInfo(AcenVocVo searchVO) {

		AcenVocVo vocInfo = acenVocMapper.getAcenVocInfo(searchVO);
		if(vocInfo == null){
			throw new MvnoRunException(-1, "잘못된 파라미터입니다.");
		}

		if("|00|01|02".indexOf(vocInfo.getStatus()) <= 0){
			throw new MvnoRunException(-1, "해당건은 VOC 처리점 등록이<br>불가능한 상태입니다.");
		}

		// 진행상태 코드 검사
		if(!"01".equals(searchVO.getStatus())){
			throw new MvnoRunException(-1, "올바른 진행상태를 선택하세요.");
		}

		// 처리기한 검사
		String now = KtisUtil.convertDateToString(new Date(), KtisUtil.DATEPATTERN.YearToDayWithNoBar);
		if(now.compareTo(searchVO.getDueDt()) > 0){
			throw new MvnoRunException(-1, "올바른 처리기한을 선택하세요.");
		}

		// 대리점 코드 검사
		int orgnIdCnt = acenVocAgntMapper.getOrgnIdCnt(searchVO.getVocAgntCd());
		if(orgnIdCnt <= 0){
			throw new MvnoRunException(-1, "올바른 대리점을 입력해주세요.");
		}

		boolean dueDateChg = !vocInfo.getDueDt().equals(searchVO.getDueDt());       // 처리기한 변경여부
		boolean statusChg = !vocInfo.getStatus().equals(searchVO.getStatus());      // 상태 변경여부 (접수 → 진행중 / 기한경과 → 진행중)
		boolean agntChg = !vocInfo.getVocAgntCd().equals(searchVO.getVocAgntCd());  // 대리점 변경여부

		boolean updateFlag = dueDateChg || statusChg || agntChg;  // VOC 수정대상
		boolean smsFlag = statusChg || agntChg;                   // SMS 전송대상
		boolean callFlag = statusChg;                             // ARS 연동대상

		// VOC 수정
		if(updateFlag){
			acenVocMapper.updateAcenVocInfo(searchVO);
			acenVocMapper.insertAcenVocHist(searchVO.getReqSeq());
		}

		// SMS 전송
		if(smsFlag){
			searchVO.setVocSeq(vocInfo.getVocSeq());
			this.sendAcenVocSms(searchVO);
		}

		// ARS 연동
		if(callFlag){
			Map<String, String> callRslt = this.callAcenArsService(vocInfo.getReqSeq());
			if("0".equals(callRslt.get("returncode"))){
				throw new MvnoRunException(-1, "연동오류가 발생했습니다.<br>잠시 후 이용바랍니다.");
			}
		}
	}

	/** (A'Cen) VOC 처리내용 수정 */
	@Transactional(rollbackFor=Exception.class)
	public void updateAcenVocContent(AcenVocVo searchVO) {

		AcenVocVo vocInfo = acenVocMapper.getAcenVocInfo(searchVO);
		if(vocInfo == null){
			throw new MvnoRunException(-1, "잘못된 파라미터입니다.");
		}

		boolean isAnsMod = "ANS".equalsIgnoreCase(searchVO.getModType());

		if(isAnsMod){
			searchVO.setCallContent(null);
			if("|00|01".indexOf(vocInfo.getStatus()) <= 0){
				throw new MvnoRunException(-1, "해당건은 수정이 불가능한 상태입니다.");
			}

		}else{
			searchVO.setAnsContent(null);
			if("|05|06|07".indexOf(vocInfo.getStatus()) > 0){
				throw new MvnoRunException(-1, "검수완료된 건으로 수정이 불가합니다.");
			}

			if("|03|04".indexOf(vocInfo.getStatus()) <= 0){
				throw new MvnoRunException(-1, "미완료 건으로 검수가 불가능합니다.");
			}
		}

		// 수정 및 이력저장
		acenVocMapper.updateAcenVocInfo(searchVO);
		acenVocMapper.insertAcenVocHist(searchVO.getReqSeq());

		// 상태값 존재 시 ARS 연동
		if(!KtisUtil.isEmpty(searchVO.getStatus()) && isAnsMod){
			Map<String, String> callRslt = this.callAcenArsService(searchVO.getReqSeq());
			if("0".equals(callRslt.get("returncode"))){
				throw new MvnoRunException(-1, "연동오류가 발생했습니다.<br>잠시 후 이용바랍니다.");
			}
		}
	}

	private void sendAcenVocSms(AcenVocVo vocInfo) {

		// 대리점 담당자 정보 조회
		List<UserInfoMgmtVo> users = acenVocAgntMapper.getAcenVocAgntListByOrgnId(vocInfo.getVocAgntCd());
		if(KtisUtil.isEmpty(users)){
			return;
		}

		// 문자템플릿 조회
		KtMsgQueueReqVO msgVO = smsMgmtMapper.getKtTemplateText(VOC_SMS_TEMPLATE);
		if(msgVO == null){
			return;
		}

		String dueDt = vocInfo.getDueDt().substring(0,4) + "년 " +
			             vocInfo.getDueDt().substring(4,6) + "월 " +
			             vocInfo.getDueDt().substring(6) + "일";

		String message = msgVO.getTemplateText().replaceAll(Pattern.quote("#{vocSeq}"), vocInfo.getVocSeq())
																					  .replaceAll(Pattern.quote("#{dueDt}"), dueDt);

		msgVO.setSubject(msgVO.getTemplateSubject());
		msgVO.setMessage(message);
		msgVO.setTemplateId(VOC_SMS_TEMPLATE);
		msgVO.setReserved01("MSP");
		msgVO.setReserved02(VOC_SMS_TEMPLATE);
		msgVO.setReserved03(vocInfo.getSessionUserId());

		// 문자전송
		String calledNumber = null;
		Pattern phonePattern = Pattern.compile("^010\\d{8}$");

		for(UserInfoMgmtVo user : users){

			calledNumber = user.getMblphnNum();

			if(KtisUtil.isEmpty(calledNumber) || !phonePattern.matcher(calledNumber).matches()){
				continue;
			}

			msgVO.setRcptData(calledNumber);
			smsMgmtMapper.insertKtMsgTmpQueue(msgVO);
		}
	}

	private Map<String, String> callAcenArsService(String reqSeq) {

		Map<String, String> rtnMap = new HashMap<String, String>();

		AcenVocVo searchVO = new AcenVocVo();
		searchVO.setReqSeq(reqSeq);

		// 대리점 VOC 조회
		AcenVocVo vocInfo = acenVocMapper.getAcenVocInfo(searchVO);
		if(vocInfo == null){
			rtnMap.put("returncode", "0");
			rtnMap.put("errordescription", "파라미터 오류");
			return rtnMap;
		}

		try {

			String callUrl = propertiesService.getString("acen.req.url") + "/aiccApp/custReqArsServiceCall.do";

			String ansContent = (vocInfo.getAnsContent() == null) ? "" : vocInfo.getAnsContent();

			NameValuePair[] data = {
				new NameValuePair("ifCode", "A10"),
				new NameValuePair("qnaSeq",      vocInfo.getVocSeq()),
				new NameValuePair("ansContent",  URLEncoder.encode(ansContent, "UTF-8")),
				new NameValuePair("ansStatus",   vocInfo.getStatus()),
				new NameValuePair("ansDate",     vocInfo.getRvisnDttm()),
				new NameValuePair("ansId",       EncryptUtil.ace256Enc(vocInfo.getRvisnId())),
				new NameValuePair("contractNum", vocInfo.getContractNum())
			};

			String responseBody = CommonHttpClient.Mplatpost(callUrl, data, "UTF-8", 10000);

			logger.debug("*** ACEN-ARS responseBody={}", responseBody);

			if(KtisUtil.isEmpty(responseBody)){
				throw new MvnoRunException(-1, "ARS 연동 오류(Response Is Null)");
			}

			rtnMap = this.toResponseParse(responseBody);

		}catch(MvnoRunException e){
			rtnMap.put("returncode", "0");
			rtnMap.put("errordescription", (e.getMessage() == null) ? "연동오류" : e.getMessage());
		}catch(ParseException e){
			rtnMap.put("returncode", "0");
			rtnMap.put("errordescription", "Parsing 오류");
		}catch (Exception e) {
			rtnMap.put("returncode", "0");
			rtnMap.put("errordescription", "연동오류");
		}

		return rtnMap;
	}

	private Map<String, String> toResponseParse(String responseBody) throws ParseException {

		Map<String, String> rtnMap = new HashMap<String, String>();

		JSONObject resObj = (JSONObject) new JSONParser().parse(responseBody);

		String returncode = resObj.get("returncode").toString().replaceAll("\"", "");
		rtnMap.put("returncode", returncode);

		if("0".equals(returncode)){
			String errordescription = resObj.get("errordescription").toString().replaceAll("\"", "");
			rtnMap.put("errordescription", errordescription);
		}

		return rtnMap;
	}

	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrName", "CUST_NAME");
		maskFields.put("vocRcpName", "CUST_NAME");
		maskFields.put("rvisnName", "CUST_NAME");
		maskFields.put("regstName", "CUST_NAME");
		return maskFields;
	}

}
