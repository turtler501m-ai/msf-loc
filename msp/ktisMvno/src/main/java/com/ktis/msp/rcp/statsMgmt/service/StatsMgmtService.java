package com.ktis.msp.rcp.statsMgmt.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.rcp.rcpMgmt.service.MplatformService;
import com.ktis.msp.rcp.rcpMgmt.vo.MoscPrdcTrtmVO;
import com.ktis.msp.util.CommonHttpClient;
import com.ktis.msp.util.XmlParse;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;
import com.ktis.msp.rcp.statsMgmt.mapper.StatsMgmtMapper;
import com.ktis.msp.rcp.statsMgmt.vo.StatsMgmtVo;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.ktis.msp.rcp.rcpMgmt.service.MplatformService.getMoscPrdcTrtmVO;


@Service
public class StatsMgmtService extends BaseService {

	public static final String PRDC_SBSC_TRTM_CD_CANCEL = "C";
	public static final String PRDC_SBSC_TRTM_CD_REG = "A";
	@Autowired
	private StatsMgmtMapper statsMgmtMapper;
		
	@Autowired
	private RcpEncService encSvc;

	@Autowired
	private MaskingMapper maskingMapper;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

	@Resource(name = "mplatformService")
	private MplatformService mplatformService;

	protected final static String HEADER = "commHeader";
	protected final static String GLOBAL_NO = "globalNo";
	protected final static String RESPONSE_TYPE = "responseType";
	protected final static String RESPONSE_CODE = "responseCode";
	protected final static String RESPONSE_BASIC = "responseBasic";

	public static final String MPLATFORM_RESPONEXML_EMPTY_EXCEPTION = "서버(M-Platform) 점검중 입니다. 잠시후 다시 시도 하기기 바랍니다.(XML EMPTY)";

	public List<?> getOpenStatList(Map<String, Object> pRequestParamMap){
		return statsMgmtMapper.getOpenStatList(pRequestParamMap);
	}
	
	public List<?> getOpenStatListEx(Map<String, Object> pRequestParamMap){
		return statsMgmtMapper.getOpenStatListEx(pRequestParamMap);
	}

	public List<?> getCanStatList(Map<String, Object> pRequestParamMap){
		return statsMgmtMapper.getCanStatList(pRequestParamMap);
	}
	
	public List<?> getStatsRealTimeJson(Map<String, Object> pRequestParamMap){
		return statsMgmtMapper.getStatsRealTimeJson(pRequestParamMap);
	}
	
	public List<?> getRateStatsJson(Map<String, Object> pRequestParamMap){
		return statsMgmtMapper.getRateStatsJson(pRequestParamMap);
	}
	
	public List<?> getAgntStatsJson(Map<String, Object> pRequestParamMap){
		return statsMgmtMapper.getAgntStatsJson(pRequestParamMap);
	}
	
	public Map<String, Object> getTimerVal(Map<String, Object> param){
        return statsMgmtMapper.getTimerVal(param);
    }
	
	public List<EgovMap> getStatsSimList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
					statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
						throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
				}
		}
		
		List<EgovMap> list = statsMgmtMapper.getStatsSimList(statsMgmtVo);
		
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	public List<EgovMap> getStatsSimListExcel(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
					throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getStatsSimListExcel(statsMgmtVo);
		
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
    /**
     * @Description : 배송정보를 수정한다.
     * @Param  : statsMgmtVo
     * @Return : int
     * @Author : 권오승
     * @Create Date : 2019. 10. 10.
     */
	@Transactional(rollbackFor=Exception.class)
    public int updateDlvryInfo(StatsMgmtVo statsMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("재약정현황 배송정보 수정 START."));
        logger.info(generateLogMsg("mnfctMgmtVo == " + statsMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        
        int resultCnt = statsMgmtMapper.updateDlvryInfo(statsMgmtVo);
        
        logger.info(generateLogMsg("등록 건수 = ") + resultCnt);
        
        return resultCnt;
    }
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		
		maskFields.put("cstmrNm","CUST_NAME");
		maskFields.put("cstmrName","CUST_NAME");
		maskFields.put("subLinkName","CUST_NAME");
		maskFields.put("minorAgentName","CUST_NAME");
		maskFields.put("lglAgntNm","CUST_NAME");
		
		maskFields.put("subscriberNo","MOBILE_PHO"); //개통전화번호
		//maskFields.put("billAdInfo","EMAIL");
		maskFields.put("ctn","MOBILE_PHO");
		maskFields.put("ctnGet","MOBILE_PHO");
		maskFields.put("callNum","MOBILE_PHO");
		maskFields.put("lglAgntNo","MOBILE_PHO");
		maskFields.put("minorAgentTel","MOBILE_PHO");
		maskFields.put("iccId","USIM");
		
		//maskFields.put("cstmrAddr","ADDR");
		maskFields.put("dlvryAddr","ADDR");
		maskFields.put("dlvryAddrDtl","PASSWORD");
		maskFields.put("cstmrAddr","ADDR");
		maskFields.put("cstmrAddrDtl","PASSWORD");
		maskFields.put("recvText","ADDR");
		maskFields.put("dlvryName","CUST_NAME");
		maskFields.put("phonCtn","MOBILE_PHO");
		maskFields.put("faxNo","MOBILE_PHO");
		
		
		/*명세서재발행 마스킹 추가*/
		maskFields.put("billAdInfo1","EMAIL");  //이메일
		maskFields.put("billAdInfo2","MOBILE_PHO"); //연락처
		

		maskFields.put("reqCardNo","CREDIT_CAR"); //신용카드
		maskFields.put("reqCardYy","CARD_EXP"); //카드유효일
		maskFields.put("cstmrMail","EMAIL"); //이메일
		maskFields.put("reqAccountNumber","ACCOUNT"); //계좌번호
		maskFields.put("selfIssuNum","PASSPORT"); //여권
		
		/*OMD등록화면 마스킹 추가*/
		maskFields.put("reqPhoneSn", "DEV_NO"); //단말기일련번호
		maskFields.put("updtId","CUST_NAME"); //수정자
		
		/*통화품질불량접수 마스킹 추가*/
		maskFields.put("mobileNo","MOBILE_PHO");			//전화번호
		maskFields.put("regMobileNo","MOBILE_PHO");		//신청자연락처
		maskFields.put("regNm","CUST_NAME");		//신청자
		
		/*예약상담 신청현황 관련 마스킹 추가*/
		maskFields.put("apNm", "CUST_NAME"); // AP명
		maskFields.put("apId", "SYSTEM_ID"); // AP계정

		/*평생할인 프로모션 적용 대상 마스킹 추가*/
		maskFields.put("procId", "SYSTEM_ID"); // 처리자 아이디
		maskFields.put("regstId", "SYSTEM_ID"); // 등록자 아이디

		/*평생할인 프로모션 관련 마스킹 추가*/
		maskFields.put("custNm", "CUST_NAME"); // 고객명
		maskFields.put("procNm", "CUST_NAME"); // 처리자명

		/*M캐시 회원 정보 마스킹 추가*/
		maskFields.put("maskUserId", "SYSTEM_ID"); // 포탈 ID

		/* 평생할인 부가서비스 가입 검증 */
		maskFields.put("compId", "SYSTEM_ID"); // 완료자 ID
		maskFields.put("rtyId", "SYSTEM_ID"); // 재처리자 ID

		/* 명의변경신청 법정대리인 정보 */
		maskFields.put("norAgentName", "CUST_NAME");
		maskFields.put("neeAgentName", "CUST_NAME");
		maskFields.put("norAgentRrn", "SSN");
		maskFields.put("neeAgentRrn", "SSN");
		maskFields.put("norAgentTelNo", "MOBILE_PHO");
		maskFields.put("neeAgentTelNo", "MOBILE_PHO");
        maskFields.put("neeAgentSelfIssuNum", "PASSPORT");
		
		/* 유심변경현황 */
		maskFields.put("usimNo", "USIM"); // USIM일련번호

		return maskFields;
	}	

	
	public List<StatsMgmtVo> getModelOpenList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getOpenEndDate() == null || "".equals(statsMgmtVo.getOpenEndDate())){
						throw new MvnoRunException(-1, "개통마감일자가 존재 하지 않습니다.");
		}
		
		List<StatsMgmtVo> list = statsMgmtMapper.getModelOpenList(statsMgmtVo);
		
		List<StatsMgmtVo> result = (List<StatsMgmtVo>) encSvc.decryptDBMSCustMngtListNotEgovMap(list);	// 복호화 필수

		logger.info("#################### 마스킹 시작 #####################");
		for(StatsMgmtVo item : result) {
			maskingService.setMask(item, maskingService.getMaskFields(), paramMap);
		}
		logger.info("#################### 마스킹 종료 #####################");
		for( StatsMgmtVo resultVO : list  ){
			
			if ("JP".equals(resultVO.getCstmrType())){ //법인이면 나이(만) 를 표시하지 않게 변경
				resultVO.setAge("법인");
			} else {
				// 주민번호
				resultVO.setAge(Integer.toString(Util.getAge(resultVO.getSsn())));	
			}
			
			String[] cstmrNativeRrn = Util.getJuminNumber(resultVO.getSsn());
			resultVO.setBirth(cstmrNativeRrn[0]);

		}
		return result;
	}
	
	public List<?> getStatsOsstMgmtList(Map<String, Object> pRequestParamMap){
		return statsMgmtMapper.getStatsOsstMgmtList(pRequestParamMap);
	}
	
	public List<?> getStatsOsstMgmtDaily(Map<String, Object> pRequestParamMap){
		if(pRequestParamMap.get("stdrDate") == null || "".equals(pRequestParamMap.get("stdrDate"))){
			throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
		}
		
		return statsMgmtMapper.getStatsOsstMgmtDaily(pRequestParamMap);
	}
	public List<?> getStatsOsstMgmtDetail(Map<String, Object> pRequestParamMap){		
		return statsMgmtMapper.getStatsOsstMgmtDetail(pRequestParamMap);
	}
	
	public List<?> getStatsOsstMgmtDetailExcel(Map<String, Object> pRequestParamMap){
		return statsMgmtMapper.getStatsOsstMgmtDetailExcel(pRequestParamMap);
	}
	
	 /**
     * @Description : 명세서 재발송 리스트
     * @Param  : statsBillingStatList
     * @Author : 박효진
     * @Create Date : 2022. 05. 19.
     */
	public List<EgovMap> getStatsBillingStatList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
					statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
						throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
				}
		}
		
		List<EgovMap> list = statsMgmtMapper.getStatsBillingStatList(statsMgmtVo);
		
		HashMap<String, String> maskFields =getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		//컬럼은 1개이나  billTypeCd 유형에 따라서 email , phone 마스킹 형식이 다르기 때문에 컬럼을 두개로 나눠서 조회하고 마스킹 후 map으로 put  
		for(EgovMap map : list){
			 if ( "1".equals(map.get("billTypeCd"))){
				  map.put("billAdInfo", map.get("billAdInfo1"));
			 }else if ("2".equals(map.get("billTypeCd"))){
				 map.put("billAdInfo", map.get("billAdInfo2"));
			 }
		}
	
		return list;
	}

	/**
     * @Description : 명세서 재발송 리스트
     * @Param  : getStatsBillingStatListExcel
     * @Author : 박효진
     * @Create Date : 2022. 05. 19.
     */
	public List<EgovMap> getStatsBillingStatListExcel(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
					throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getStatsBillingStatListExcel(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		//컬럼은 1개이나  billTypeCd 유형에 따라서 email , phone 마스킹 형식이 다르기 때문에 컬럼을 두개로 나눠서 조회하고 마스킹 후 map으로 put  
				for(EgovMap map : list){
					 if ( "1".equals(map.get("billTypeCd"))){
						  map.put("billAdInfo", map.get("billAdInfo1"));
					 }else if ("2".equals(map.get("billTypeCd"))){
						 map.put("billAdInfo", map.get("billAdInfo2"));
					 }
				}
		
		return list;
	}
	
	 /**
     * @Description : 유심셀프변경
     * @Param  : StatsMgmtVo
     * @Author : 장익준
     * @Create Date : 2022. 06. 16.
     */
	public List<EgovMap> getUsimChgList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
					statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
						throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
				}
		}
		
		List<EgovMap> list = statsMgmtMapper.getUsimChgList(statsMgmtVo);
		
		HashMap<String, String> maskFields =getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		//컬럼은 1개이나  billTypeCd 유형에 따라서 email , phone 마스킹 형식이 다르기 때문에 컬럼을 두개로 나눠서 조회하고 마스킹 후 map으로 put  
		for(EgovMap map : list){
			 if ( "1".equals(map.get("billTypeCd"))){
				  map.put("billAdInfo", map.get("billAdInfo1"));
			 }else if ("2".equals(map.get("billTypeCd"))){
				 map.put("billAdInfo", map.get("billAdInfo2"));
			 }
		}
	
		return list;
	}

	/**
     * @Description : 유심셀프변경
     * @Param  : StatsMgmtVo
     * @Author : 장익준
     * @Create Date : 2022. 06. 16.
     */
	public List<EgovMap> getUsimChgListExcel(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
					throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getUsimChgListExcel(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		//컬럼은 1개이나  billTypeCd 유형에 따라서 email , phone 마스킹 형식이 다르기 때문에 컬럼을 두개로 나눠서 조회하고 마스킹 후 map으로 put  
				for(EgovMap map : list){
					 if ( "1".equals(map.get("billTypeCd"))){
						  map.put("billAdInfo", map.get("billAdInfo1"));
					 }else if ("2".equals(map.get("billTypeCd"))){
						 map.put("billAdInfo", map.get("billAdInfo2"));
					 }
				}
		
		return list;
	}
	
	 /**
     * @Description : 통화내역열람
     * @Param  : StatsMgmtVo
     * @Author : 장익준
     * @Create Date : 2022. 06. 16.
     */
	public List<EgovMap> getCallList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
					statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
						throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
				}
		}
		
		List<EgovMap> list = statsMgmtMapper.getCallList(statsMgmtVo);
		
		HashMap<String, String> maskFields =getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		List<?> mskGrp =  maskingMapper.selectList(paramMap);
		Map mskGrpRow = (Map) mskGrp.get(0);
		String mskAuthYn = StringUtils.defaultString((String) mskGrpRow.get("mskAuthYn"));
		
		for(EgovMap map : list){
			if ( "팩스".equals(map.get("recvType"))){
				if (!"Y".equals(mskAuthYn)) {
					Map<String,String> lCmnMskGrpRow = new HashMap<String,String>();
					lCmnMskGrpRow.put("mskGrpId", "PHONE");
					map.put("recvText", maskingService.getMaskString((String) map.get("recvText"),lCmnMskGrpRow));
				}
			}
		}
	
		return list;
	}

	/**
     * @Description : 통화내역열람 엑셀
     * @Param  : StatsMgmtVo
     * @Author : 장익준
     * @Create Date : 2022. 06. 16.
     */
	public List<EgovMap> getCallListExcel(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
					throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getCallListExcel(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		List<?> mskGrp =  maskingMapper.selectList(paramMap);
		Map mskGrpRow = (Map) mskGrp.get(0);
		String mskAuthYn = StringUtils.defaultString((String) mskGrpRow.get("mskAuthYn"));

		for(EgovMap map : list){
			if (!"Y".equals(mskAuthYn)) {
				if ( "팩스".equals(map.get("recvType"))){
					Map<String,String> lCmnMskGrpRow = new HashMap<String,String>();
					lCmnMskGrpRow.put("mskGrpId", "PHONE");
					map.put("recvText", maskingService.getMaskString((String) map.get("recvText"),lCmnMskGrpRow));
				}
			}
		}
		
		return list;
	}

	/**
     * @Description : 통화내역열람 상태 처리 및 합본 처리
     * @Param  : StatsMgmtVo
     * @Author : 장익준
     * @Create Date : 2022. 07. 15.
     */
	@Transactional(rollbackFor=Exception.class)
	public int updateCall(StatsMgmtVo vo, String usrId) throws EgovBizException {
		
		logger.debug("StatsMgmtVo 처리건수 : " + vo.toString());
		int resultCnt = 0;
		
		String oldScanId = "";
		String newScanId = "";
		String maxFileNum = "";
		
		// CP 처리 상태일때만 합본 한다.
		if("CP".equals(vo.getProcCd())) {
			// 합본 처리
			// 1. 계약번호로 msp_juo_sub_info에서 scan_id 를 찾아온다.
			oldScanId = statsMgmtMapper.getOldScanId(vo);
			logger.debug("oldScanId : " + oldScanId);
			
			// 2. emv_scan_file MAX FILE_NUMBER 값을 찾아온다.
			maxFileNum = statsMgmtMapper.getFileNum(oldScanId);
			logger.debug("maxFileNum : " + maxFileNum);
			
			// 3. NMCP_CUST_REQUEST_CALL_LIST CUST_REQ_SEQ 값으로 SCAN_ID를 찾아온다.
			newScanId = statsMgmtMapper.getNewScanId(vo.getCustReqSeq());
			logger.debug("newScanId : " + newScanId);
			
			// 4. scan_id로 emv_scan_file 정보를 가져와 1번의 scan_id 쪽에 insert 합본 완료
			vo.setOldScanId(oldScanId);
			vo.setFileNum(maxFileNum);
			vo.setNewScanId(newScanId);
			resultCnt = statsMgmtMapper.insertEmvFile(vo);
	
			logger.debug("resultCnt 처리건수 : " + resultCnt);
		}
		
		// 상태 변경
		// NMCP_CUST_REQUEST_CALL_LIST 테이블에 메모와 상태를 업데이트 친다.
		resultCnt = statsMgmtMapper.updateProcCd(vo);
		
		logger.debug("최종 처리 건수 : " + resultCnt);
		return resultCnt;
	}
	
	 /**
     * @Description : 명의변경
     * @Param  : StatsMgmtVo
     * @Author : 장익준
     * @Create Date : 2022. 06. 16.
     */
	public List<EgovMap> getNameChgList(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
					statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
						throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
				}
		}
		
		List<EgovMap> list = statsMgmtMapper.getNameChgList(statsMgmtVo);
		
		HashMap<String, String> maskFields =getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
	
		return list;
	}

	/**
	 * @Description : 명의변경 상세
	 * @Param  : StatsMgmtVo
	 * @Author : 박준성
	 * @Create Date : 2025. 12. 31.
	 */
	public List<EgovMap> getNameChgDtl(Map<String, Object> paramMap){

		List<EgovMap> list = statsMgmtMapper.getNameChgDtl(paramMap);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
     * @Description : 명의변경 엑셀
     * @Param  : StatsMgmtVo
     * @Author : 장익준
     * @Create Date : 2022. 06. 16.
     */
	public List<EgovMap> getNameListExcel(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
					throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getNameListExcel(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	/**
     * @Description : 명의변경 상태 처리 및 합본 처리
     * @Param  : StatsMgmtVo
     * @Author : 장익준
     * @Create Date : 2022. 07. 15.
     */
	@Transactional(rollbackFor=Exception.class)
	public int updateName(StatsMgmtVo vo, String usrId) throws EgovBizException {
		
		logger.debug("StatsMgmtVo 처리건수 : " + vo.toString());
		int resultCnt = 0;
		
		String oldScanId = "";
		String newScanId = "";
		String maxFileNum = "";
		
		// CP 처리 상태일때만 합본 한다.
		if("CP".equals(vo.getProcCd())) {
			// 합본 처리
			// 1. 계약번호로 msp_juo_sub_info에서 scan_id 를 찾아온다.
			oldScanId = statsMgmtMapper.getOldScanId(vo);
			logger.debug("oldScanId : " + oldScanId);
			
			// 2. emv_scan_file MAX FILE_NUMBER 값을 찾아온다.
			maxFileNum = statsMgmtMapper.getFileNum(oldScanId);
			logger.debug("maxFileNum : " + maxFileNum);
			
			// 3. NMCP_CUST_REQUEST_NAME_CHG CUST_REQ_SEQ 값으로 SCAN_ID를 찾아온다.
			newScanId = statsMgmtMapper.getNewScanIdNm(vo.getCustReqSeq());
			logger.debug("newScanId : " + newScanId);
			
			// 4. scan_id로 emv_scan_file 정보를 가져와 1번의 scan_id 쪽에 insert 합본 완료
			vo.setOldScanId(oldScanId);
			vo.setFileNum(maxFileNum);
			vo.setNewScanId(newScanId);
			resultCnt = statsMgmtMapper.insertEmvFile(vo);
	
			logger.debug("resultCnt 처리건수 : " + resultCnt);

			vo.setMcnStateCode("07"); //명변처리완료
		}
		// 상태 변경
		// NMCP_CUST_REQUEST_NAME_CHG 테이블에 메모와 상태를 업데이트 친다.
		resultCnt = statsMgmtMapper.updateProcCdNm(vo);
		
		logger.debug("최종 처리 건수 : " + resultCnt);
		return resultCnt;
	}
	
	/**
     * @Description : 명의변경현황
     * @Param  : StatsMgmtVo
     * @Author : 이승국
     * @Create Date : 2025. 10. 21.
     */
	public List<EgovMap> getNameChgStateList(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
					statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
						throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
				}
		}
		
		List<EgovMap> list = statsMgmtMapper.getNameChgStateList(statsMgmtVo);
		
		HashMap<String, String> maskFields =getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
	
		return list;
	}

	/**
     * @Description : 명의변경현황 엑셀
     * @Param  : StatsMgmtVo
     * @Author : 이승국
     * @Create Date : 2025. 10. 21.
     */
	public List<EgovMap> getNameStateListExcel(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
					throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getNameStateListExcel(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	/**
     * @Description : 가입신청서 출력요청 리스트 조회
     * @Param  : StatsMgmtVo
     * @Param  : Map<String, Object>
	 * @Return : List<EgovMap>
     * @Author : wooki
     * @CreateDate : 2022.08.02
     */
	public List<EgovMap> getReqJoinFormList(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())) {
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) || statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())) {
				throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getReqJoinFormList(statsMgmtVo);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
	
		return list;
	}
	
	/**
     * @Description : 가입신청서 출력요청 리스트 엑셀
     * @Param  : StatsMgmtVo
     * @Param  : Map<String, Object>
     * @Author : wooki
     * @CreateDate : 2022.08.02
     */
	public List<EgovMap> getReqJoinFormListExcel(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())) {
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) || statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())) {
				throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getReqJoinFormListExcel(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	/**
     * @Description : 가입신청서 출력요청 상태 처리
     * @Param  : StatsMgmtVo
     * @Author : wooki
     * @CreateDate : 2022.08.02
     */
	@Transactional(rollbackFor=Exception.class)
	public int updateReqJoinForm(StatsMgmtVo vo) throws EgovBizException {
		
		logger.debug("StatsMgmtVo 처리건수 : " + vo.toString());
		int resultCnt = 0;
		
		// NMCP_CUST_REQUEST_JOIN_FORM 테이블에 메모와 상태를 업데이트
		resultCnt = statsMgmtMapper.updateReqJoinForm(vo);
		
		logger.debug("최종 처리 건수 : " + resultCnt);
		return resultCnt;
	}
	
	/**
     * @Description : 유심PUK번호 열람신청 리스트 조회
     * @Param  : StatsMgmtVo
     * @Param  : Map<String, Object>
	 * @Return : List<EgovMap>
     * @Author : wooki
     * @CreateDate : 2022.08.02
     */
	public List<EgovMap> getReqUsimPukList(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())) {
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) || statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())) {
				throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getReqUsimPukList(statsMgmtVo);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
	
		return list;
	}
	
	/**
     * @Description : 유심PUK번호 열람신청 리스트 엑셀
     * @Param  : StatsMgmtVo
     * @Param  : Map<String, Object>
     * @Author : wooki
     * @CreateDate : 2022.08.02
     */
	public List<EgovMap> getReqUsimPukListExcel(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())) {
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) || statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())) {
				throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getReqUsimPukListExcel(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	/**
     * @Description : OMD단말등록현황 리스트
     * @Param  : statsMgmtVo
     * @Author : JUNA
     * @Create Date : 2022. 09. 14.
     */
	public List<EgovMap> getOmdRegList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())) {
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
				throw new MvnoRunException(-1, "신청일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getOmdRegList(statsMgmtVo);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		
		return list;
	}

	/**
     * @Description : OMD단말등록현황 리스트 엑셀
     * @Param  : statsMgmtVo
     * @Author : JUNA
     * @Create Date : 2022. 09. 14.
     */
	public List<EgovMap> getOmdRegListExcel(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())) {
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
				throw new MvnoRunException(-1, "신청일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getOmdRegListExcel(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		
		return list;
	}

	
	/**
    * @Description : 가입번호조회 요청 리스트
    * @Param  : StatsMgmtVo
    * @Author : 박준성
    * @Create Date : 2022. 10. 07.
    */
	public List<EgovMap> getOwnPhoNumReqList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
					statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
						throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
				}
		}
		
		List<EgovMap> list = statsMgmtMapper.getOwnPhoNumReqList(statsMgmtVo);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		return list;
	}

	/**
    * @Description : 가입번호조회 요청 리스트 엑셀
    * @Param  : StatsMgmtVo
    * @Author : 장익준
    * @Create Date : 2022. 06. 16.
    */
	public List<EgovMap> getOwnPhoNumReqListExcel(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
					throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getOwnPhoNumReqListExcel(statsMgmtVo);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		return list;
	}
	
	/**
     * @Description : 통화품질불량 접수 리스트
     * @Param  : statsMgmtVo
     * @Author : HYOJIN
     * @Create Date : 2022. 10. 24.
     */
	public List<EgovMap> getCallQualityAsList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())) {
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
				throw new MvnoRunException(-1, "신청일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getCallQualityAsList(statsMgmtVo);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		
		return list;
	}
	
	/**
     * @Description : 통화품질불량 접수 리스트 엑셀
     * @Param  : statsMgmtVo
     * @Author : HYOJIN
     * @Create Date : 2022.10.26.
     */
	public List<EgovMap> getCallQualityAsListExcel(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())) {
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
				throw new MvnoRunException(-1, "신청일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getCallQualityAsListExcel(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		
		return list;
	}
	
	/**
     * @Description : 통화품질불량 접수 수정
     * @Param  : StatsMgmtVo
     * @Author : HYOJIN
     * @CreateDate : 2022.10.26.
     */
	@Transactional(rollbackFor=Exception.class)
	public void updateCallQualityAs(StatsMgmtVo statsMgmtVo) {
		
		if("R".equals(statsMgmtVo.getCounselRsltCd())) {
			throw new MvnoRunException(-1, "현재 '접수'상태입니다. '처리'나 '반려'로 변경해주세요.");
		}
		 statsMgmtMapper.updateCallQualityAs(statsMgmtVo);

	}

	/**
	 * @Description 예약상담 리스트 조회
	 * @Param : statsMgmtVo
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.04.18
	 */
	public List<EgovMap> getCsResInfoList(StatsMgmtVo statsMgmtVo) {

		// 파라미터 검사 (특정 예약일자와 상담 시간대로 조회 or 예약일자 기간으로 조회)
		if(KtisUtil.isEmpty(statsMgmtVo.getCsResDt())) {
			if(KtisUtil.isEmpty(statsMgmtVo.getSrchStrtDt()) || KtisUtil.isEmpty(statsMgmtVo.getSrchEndDt())) {
				throw new MvnoRunException(-1, "예약일시가 존재 하지 않습니다.");
			}
		}

		return statsMgmtMapper.getCsResInfoList(statsMgmtVo);
	}

	/**
	 * @Description 예약상담 리스트 상세 조회
	 * @Param : statsMgmtVo
	 * @Param : paramMap
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.04.19
	 */
	public List<EgovMap> getCsResDtlInfoList(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap) {

		// 파라미터 검사
		if(KtisUtil.isEmpty(statsMgmtVo.getCsResDt()) || KtisUtil.isEmpty(statsMgmtVo.getCsResTm())){
		   throw new MvnoRunException(-1, "예약일시가 존재 하지 않습니다.");
		}

		List<EgovMap> list = statsMgmtMapper.getCsResDtlInfoList(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * @Description 시간대별 예약상담 허용인원 조회
	 * @Param : statsMgmtVo
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.04.21
	 */
    public List<EgovMap> getCsResPerCntByDt(StatsMgmtVo statsMgmtVo) {

		// 파라미터 검사
		if(KtisUtil.isEmpty(statsMgmtVo.getCsResDt())){
			throw new MvnoRunException(-1, "예약일시가 존재 하지 않습니다.");
		}

		return statsMgmtMapper.getCsResPerCntByDt(statsMgmtVo);
    }

	/**
	 * @Description 시간대별 예약상담 허용인원 등록/수정
	 * @Param : statsMgmtVo
	 * @Param : today
	 * @Param : dueDate
	 * @Return : Map<String, Object>
	 * @Author : hsy
	 * @CreateDate : 2023.04.21
	 */
	@Transactional(rollbackFor=Exception.class)
    public void mergeCsResPerCntByDt(StatsMgmtVo statsMgmtVo, String today, String dueDate) {

		// 파라미터 검사
		if(KtisUtil.isEmpty(statsMgmtVo.getCsResDt())){
			throw new MvnoRunException(-1, "예약일시가 존재하지 않습니다.");
		}

		if(KtisUtil.isEmpty(statsMgmtVo.getAmTime()) || KtisUtil.isEmpty(statsMgmtVo.getPmTime())){
			throw new MvnoRunException(-1, "예약등록 정보가 존재하지 않습니다.");
		}

		// 날짜 유효성 검사 (당일 포함 이전날짜 등록/수정 불가, 익익월까지만 등록 가능)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);

		try{
			Date toDt= sdf.parse(today); // 오늘
			Date dueDt= sdf.parse(dueDate); // 익익월 마지막날
			Date csResDt= sdf.parse(statsMgmtVo.getCsResDt()); // 예약일시

			if(csResDt.equals(toDt) || csResDt.before(toDt)){
				throw new MvnoRunException(-1, "당일 포함 이전날짜는<br/>신규 등록 또는 수정이 불가합니다.");
			}
			else if(csResDt.after(dueDt)){
				throw new MvnoRunException(-1, "익익월까지만 등록 가능합니다.");
			}

		}catch(ParseException e){
			throw new MvnoRunException(-1, "예약일시 데이터 형식이 올바르지 않습니다.");
		}

		// ==== 등록, 수정값으로 입력한 허용인원수 반영 가능여부 체크 START ====

		List<EgovMap> resCntList= statsMgmtMapper.getCsResCntByDt(statsMgmtVo);  // 현재 시간대별 예약된 고객수
		Map<String,Object> resCntMap= null;
		if(!KtisUtil.isEmpty(resCntList)) resCntMap= new HashMap(resCntList.get(0));

		// 최대 허용가능한 예약인원 수 조회
		int maxPerCnt= 20;
		try{
			maxPerCnt= Integer.parseInt(this.getMaxPerCnt());
		}catch(NumberFormatException e){
			logger.error("StatsMgmtService.mergeCsResPerCntByDt > NumberFormatException occured");
		}

		Map<String,String> possibleAmTime= this.checkReqPerCnt(statsMgmtVo.getAmTime(), resCntMap, maxPerCnt);
		if(!"SUCCESS".equals(possibleAmTime.get("rtnCode"))){  // 등록,수정 불가
			throw new MvnoRunException(-1, possibleAmTime.get("rtnMsg"));
		}

		Map<String,String> possiblePmTime= this.checkReqPerCnt(statsMgmtVo.getPmTime(), resCntMap, maxPerCnt);
		if(!"SUCCESS".equals(possiblePmTime.get("rtnCode"))){  // 등록,수정 불가
			throw new MvnoRunException(-1, possiblePmTime.get("rtnMsg"));
		}

		// ==== 등록, 수정값으로 입력한 허용인원수 반영 가능여부 체크 END ====


		// ==== 등록, 수정 START ====

		// 현재 등록되어진 예약허용인원수
		List<EgovMap> perCntList= statsMgmtMapper.getCsResPerCntByDt(statsMgmtVo);
		boolean isExistSchedule= !KtisUtil.isEmpty(perCntList);

		Iterator<String> amKeys = statsMgmtVo.getAmTime().keySet().iterator();
		Iterator<String> pmKeys = statsMgmtVo.getPmTime().keySet().iterator();

		Map<String,String> csResMergeMap= new HashMap<String, String>();
		csResMergeMap.put("csResDt", statsMgmtVo.getCsResDt());
		csResMergeMap.put("userId", statsMgmtVo.getUserId());

		String key = null;
		String prePerCnt= "";  // 기존에 등록된 허용인원수
		String nxtPerCnt= "";  // 신규등록될 허용인원수

		try{
			while(amKeys.hasNext()){
				key = amKeys.next();
				prePerCnt= (isExistSchedule) ? String.valueOf(perCntList.get(0).get(key)) : "";
				nxtPerCnt= statsMgmtVo.getAmTime().get(key);

				if(prePerCnt.equals(nxtPerCnt)) continue;

				csResMergeMap.put("csResTm", key);
				csResMergeMap.put("perCnt", statsMgmtVo.getAmTime().get(key));

				statsMgmtMapper.mergeCsResPerCntByDt(csResMergeMap);
			}

			while(pmKeys.hasNext()){
				key = pmKeys.next();
				prePerCnt= (isExistSchedule) ? String.valueOf(perCntList.get(0).get(key)) : "";
				nxtPerCnt= statsMgmtVo.getPmTime().get(key);

				if(prePerCnt.equals(nxtPerCnt)) continue;

				csResMergeMap.put("csResTm", key);
				csResMergeMap.put("perCnt", statsMgmtVo.getPmTime().get(key));

				statsMgmtMapper.mergeCsResPerCntByDt(csResMergeMap);
			}

		}catch (Exception e){
			throw new MvnoRunException(-1, "등록/수정에 실패했습니다.");
		}
		// ==== 등록, 수정 END ====
    }


	/**
	 * @Description 예약상담 최대 허용인원
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.04.25
	 */
	public String getMaxPerCnt() {
		String maxPerCnt= "20"; // DEFAULT 설정

		List<EgovMap> list= statsMgmtMapper.getMaxPerCnt();
		if(!KtisUtil.isEmpty(list)){
			maxPerCnt= (String)list.get(0).get("maxPerCnt");
		}

		return maxPerCnt;
	}


	/**
	 * @Description 예약상담 신청현황 엑셀 다운로드
	 * @Param : statsMgmtVo
	 * @Param : paramMap
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.04.25
	 */
	public List<EgovMap> getCsResListExcel(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap) {

		// 파라미터 검사
		if(KtisUtil.isEmpty(statsMgmtVo.getSrchStrtDt()) || KtisUtil.isEmpty(statsMgmtVo.getSrchEndDt())) {
			throw new MvnoRunException(-1, "예약일시가 존재 하지 않습니다.");
		}

		// 엑셀 리스트 조회
		List<EgovMap> list = statsMgmtMapper.getCsResListExcel(statsMgmtVo);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * @Description 관리자가 입력한 허용인원수 반영 가능여부 체크
	 * @Param : reqPerCnt (관리자가 반영하고자 하는 허용인원수 MAP)
	 * @Param : resCntMap (예약된 고객수 MAP)
	 * @Param : maxPerCnt (등록가능한 최대인원수)
	 * @Return : Map<String,String>
	 * @Author : hsy
	 * @CreateDate : 2023.04.27
	 */
	private Map<String,String> checkReqPerCnt(Map<String, String> reqPerCntMap, Map<String, Object> resCntMap, int maxPerCnt) {

		Map<String,String> result = new HashMap<String, String>();
		String rtnCode= "SUCCESS";
		String rtnMsg= "반영 가능합니다.";

		boolean isExistResCstmr= !KtisUtil.isEmpty(resCntMap);     // 특정 예약일시에 예약고객 존재여부

		// 각 시간대별 가능여부 체크
		Iterator<String> timeKeys = reqPerCntMap.keySet().iterator();
		String key = null;
		int reqCnt= 0;  // 관리자가 입력한 예약가능 인원수
		int resCnt= 0;  // 현재 예약된 고객수

		while(timeKeys.hasNext()){
			key= timeKeys.next();

			try {
				reqCnt = Integer.parseInt(reqPerCntMap.get(key));
				resCnt = (isExistResCstmr) ? ((BigDecimal)resCntMap.get(key)).intValue() : 0;

			}catch(NumberFormatException e){
				logger.error("StatsMgmtService.checkReqPerCnt NumberFormatException occured");
			}


			if(reqCnt < 0){
				rtnCode= "FAIL_MINUS";
				rtnMsg= "음수값은 입력하실 수 없습니다.";
				break;
			}else if(reqCnt < resCnt){ // 이미 예약된 고객수 보다 적게 입력한 경우
				rtnCode= "FAIL_LESS";
				rtnMsg= "기존 예약된 고객 수 보다 적은 수를 입력하실 수 없습니다.";
				break;
			}else if(reqCnt > maxPerCnt){ // 최대 예약가능 인원수를 초과한 경우
				rtnCode= "FAIL_MORE";
				rtnMsg= "예약 시간대 별 최대 허용인원은 " + maxPerCnt + "명 입니다.";
				break;
			}
		}

		result.put("rtnCode", rtnCode);
		result.put("rtnMsg", rtnMsg);
		return result;
	}


	/**
	 * @Description 평생할인 프로모션 가입이력 조회
	 * @Param : searchVO
	 * @Param : paramMap
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.09.27
	 */
	public List<EgovMap> getPrmtAutoAddList(StatsMgmtVo searchVO , Map<String, Object> paramMap) {

		if(KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt())) {
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다.");
		}

		List<EgovMap> list = statsMgmtMapper.getPrmtAutoAddList(searchVO);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * @Description 평생할인 프로모션 처리여부 수정
	 * @Param : searchVO
	 * @Return : int
	 * @Author : hsy
	 * @CreateDate : 2023.10.04
	 */
	public int updatePrmtAutoAddProc(StatsMgmtVo searchVO) {

		if(KtisUtil.isEmpty(searchVO.getPrcMstSeq())) {
			throw new MvnoRunException(-1, "선택된 데이터가 없습니다.<br>데이터를 선택하고 처리해주시기 바랍니다.");
		}

		return statsMgmtMapper.updatePrmtAutoAddProc(searchVO);
	}

	/**
	 * @Description 평생할인 프로모션 가입이력 상세 조회
	 * @Param : searchVO
	 * @Param : paramMap
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.10.04
	 */
	public List<EgovMap> getPrmtAutoAddDetail(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		if(KtisUtil.isEmpty(searchVO.getPrcMstSeq())) {
			throw new MvnoRunException(-1, "선택된 데이터가 없습니다.<br>데이터를 선택하고 처리해주시기 바랍니다.");
		}

		List<EgovMap> list= statsMgmtMapper.getPrmtAutoAddDetail(searchVO);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * @Description 평생할인 프로모션 가입이력 엑셀 다운로드
	 * @Param : searchVO
	 * @Param : paramMap
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.10.04
	 */
	public List<EgovMap> getPrmtAutoAddListExcel(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = statsMgmtMapper.getPrmtAutoAddListExcel(searchVO);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * @Description 평생할인 자동가입 재처리
	 * @Param : searchVO
	 * @Return : int
	 * @Author : hsy
	 * @CreateDate : 2023.10.05
	 */
	public int insertRtyPrmtAutoAddDtl(StatsMgmtVo searchVO) {
		if(KtisUtil.isEmpty(searchVO.getPrcRtySeq())) {
			String prcRtySeq = statsMgmtMapper.getPrcRtySeq();
			searchVO.setPrcRtySeq(prcRtySeq);
		}
		return statsMgmtMapper.insertRtyPrmtAutoAddDtl(searchVO);
	}

	/**
	 * @Description 평생할인 자동가입 재처리 금지 조건 체크
	 * @Param : searchVO
	 * @Return : void
	 * @Author : hsy
	 * @CreateDate : 2023.10.05
	 */
	public void reprocessPrmtAutoChk(StatsMgmtVo searchVO) throws ParseException {

		if(KtisUtil.isEmpty(searchVO.getPrcMstSeq()) || KtisUtil.isEmpty(searchVO.getPrcDtlSeq())) {
			throw new MvnoRunException(-1, "선택된 데이터가 없습니다.<br>데이터를 선택하고 처리해주시기 바랍니다.");
		}

		// 1. 고객 최종상태 확인
		Map<String, String> cstmrChkMap = statsMgmtMapper.getCstmrChk(searchVO);
		if(cstmrChkMap == null) throw new MvnoRunException(-1, "고객 정보가 존재하지 않습니다.");

		// 1-1. 로그인한 대리점과 고객 개통 대리점이 다른 경우 재처리 금지 (본사 소속은 대리점과 상관없이 재처리 가능)
		if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getCntpntShopId().equals(cstmrChkMap.get("PRMT_AGNT_CD")))
			throw new MvnoRunException(-1, "대리점은 본인 대리점만 재처리 가능 합니다.");

		// 1-2. 최초 성공여부 / 최종 성공여부 / 처리 성공여부가 성공인 경우 재처리 금지
		if("Y".equals(cstmrChkMap.get("SUCC_YN")) || "Y".equals(cstmrChkMap.get("FNL_SUCC_YN")) || "Y".equals(cstmrChkMap.get("PROC_YN")))
			throw new MvnoRunException(-1, "부가서비스 가입 성공인 경우 재처리를 할 수 없습니다.");

		// 1-3. 현재 가입된 프로모션 확인
		if(!cstmrChkMap.get("PRMT_ID").equals(cstmrChkMap.get("DIS_PRMT_ID")))
			throw new MvnoRunException(-1, "다른 프로모션 가입자 고객입니다.");

		// 1-4 프로모션 취소 고객인지 확인.
		if("SIM".equals(cstmrChkMap.get("EVNT_CD"))){
			if(!"A".equals(cstmrChkMap.get("SIM_INFO"))) throw new MvnoRunException(-1, "재약정 취소 고객입니다.");
		}

		if("C07".equals(cstmrChkMap.get("EVNT_CD"))){
			String endDttmChk = statsMgmtMapper.getDvcAgntEndDttm(cstmrChkMap.get("CONTRACT_NUM"));
			if(!"Y".equals(endDttmChk)) throw new MvnoRunException(-1, "기기변경 취소 고객입니다.");
		}
		
		// 2. 고객 SUB_INFO 정보 확인
		Map<String,String> cstmrMap = statsMgmtMapper.getCstmrSubInfo(cstmrChkMap.get("CONTRACT_NUM"));
		if(cstmrMap == null) throw new MvnoRunException(-1, "현재 회선을 사용하는 고객이 아닙니다.");

		// 3. 프로모션 적용대상 max값 확인
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("contractNum", cstmrChkMap.get("CONTRACT_NUM"));
		param.put("effectiveDate", cstmrChkMap.get("EFFECTIVE_DATE"));

		String maxEffDtRslt = statsMgmtMapper.getMaxTrgEffDt(param);
		if(!"Y".equals(maxEffDtRslt)) throw new MvnoRunException(-1, "다른 프로모션 가입자 고객입니다.");

		// 4. 프로모션 재처리 가능일 확인
		// 4-1.  공통코드 재처리 가능일 가져오기
		String rtyCanDt = statsMgmtMapper.getRtyCanDt();
		int rtyMaxDt = 0;
		if(!KtisUtil.isEmpty(rtyCanDt)) rtyMaxDt = Integer.parseInt(rtyCanDt);
		else rtyMaxDt = 30;

		// 4-2 날짜 형식 세팅
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date todt = sdf.parse(searchVO.getSrchStrtDt()); //오늘

		// 4-3 재처리 가능일 세팅
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(todt);
		calendar.add(Calendar.DATE, -rtyMaxDt);
		Date rtyDate = calendar.getTime(); //재처리 가능일

		// 4-4 . 재처리 할 프로모션 가입일 체크
		String effectiveDttm =cstmrChkMap.get("EFFECTIVE_DATE");
		String effectiveDt = effectiveDttm.substring(0,8);
		Date regstDate = sdf.parse(effectiveDt);
		if(regstDate.before(rtyDate)) throw new MvnoRunException(-1, "재처리 가능 기간이 지났습니다.");

		//5. 부가서비스 성공여부 / 재처리 횟수 체크
		List<EgovMap> chkList = statsMgmtMapper.getPrmtAutoChkList(searchVO);
		if(chkList == null) throw new MvnoRunException(-1, "잘못된 접근입니다.");
		searchVO.setSoc((String)chkList.get(0).get("soc"));

		int rtyCnt = 0;
		for(int i=0; i<chkList.size(); i++){
			//5-1. 부가서비스 가입 성공인 경우
			if("0000".equals(chkList.get(i).get("succYn")))
				throw new MvnoRunException(-1, "부가서비스 가입 성공인 경우 재처리를 할 수 없습니다.");

			//5-2. 재처리 횟수 체크
			if("RTY".equals(chkList.get(i).get("tbNm")) && "1".equals(chkList.get(i).get("seq")))
				rtyCnt++;
			if(rtyCnt >= 3) throw new MvnoRunException(-1, "재처리는 3번까지 가능합니다.");
		}

		searchVO.setSubscriberNo(cstmrMap.get("SUBSCRIBER_NO"));
		searchVO.setCustomerId(cstmrMap.get("CUSTOMER_ID"));
		searchVO.setNcn(cstmrMap.get("SVC_CNTR_NO"));
		searchVO.setContractNum(cstmrMap.get("CONTRACT_NUM"));
	}

	/**
	 * 부가서비스 가입 연동
	 * @param searchVO
	 * @return Map<String, Object>
	 * @throws JDOMException
	 * @throws IOException
	 */
	public Map<String, Object> reprocessPrmtAutoAdd(StatsMgmtVo searchVO) throws InterruptedException {

		Map<String, Object> result = null;

		HashMap<String,String> param = new HashMap<String,String>();
		param.put("appEventCd", "X21");
		param.put("ncn", searchVO.getNcn());
		param.put("ctn",searchVO.getSubscriberNo());
		param.put("custId",searchVO.getCustomerId());
		param.put("soc",searchVO.getSoc());
		param.put("mdlInd", "WEB");
		param.put("userid",searchVO.getUserId());
		param.put("ip", searchVO.getAccessIp());

		// 1. 부가서비스 (x21) 연동
		for(int reTryCount = 0; reTryCount < 2; reTryCount++){
			if(result == null){
				result = this.mpPrmtSocAddRty(param);
			} else if ("ITL_SYS_E0001".equals(result.get("code"))){
				// 1-1. MP 연동 오류인 경우만 연동 한번 더 진행
				Thread.sleep(3000);

				// 1-2. 이력 저장
				searchVO.setGlobalNo((String)result.get("globalNo"));
				searchVO.setRsltCd((String)result.get("code"));
				searchVO.setPrcsSbst((String)result.get("msg"));
				this.insertRtyPrmtAutoAddDtl(searchVO);

				// 1-3. 부가서비스 (x21) 재처리 시작
				result = this.mpPrmtSocAddRty(param);
			}
		}

		// 2. 이력 저장
		searchVO.setGlobalNo((String)result.get("globalNo"));
		searchVO.setRsltCd((String)result.get("code"));
		searchVO.setPrcsSbst((String)result.get("msg"));

		this.insertRtyPrmtAutoAddDtl(searchVO);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String resultCd="N";

		// 3. 재처리 성공인 경우
		if("0000".equals(result.get("code"))){
			resultCd = "Y";

			// 3-1. 모든 부가서비스 재처리 성공여부 조회
			int failCnt = this.getPrmtnotAddSocCnt(searchVO);
			if (failCnt < 1) {
				// 3-2. 모든 부가서비스 재처리 완료 했다면, MST 최종 성공여부 update
				statsMgmtMapper.updatePrmtFnlSuccYn(searchVO);
				resultMap.put("fnlSuccYN", "Y");
			}
		}

		resultMap.put("resultCd",resultCd);
		return resultMap;
	}

	/**
	 * @Description 평생할인 부가서비스 재처리 연동
	 * @Param : searchVO
	 * @Return : HashMap<String, Object>
	 * @Author : hsy
	 * @CreateDate : 2023.10.11
	 */
	public HashMap<String, Object> mpPrmtSocAddRty(HashMap<String, String> param) {

		String responseXml = "";
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String serverNm= KtisUtil.isEmpty(request.getServerName()) ? "" : request.getServerName();
		param.put("url", request.getRequestURI());

		try{
			if(serverNm.toLowerCase().indexOf("localhost") != -1){
				//1. 성공
				responseXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscRegSvcChgResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>INL</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20150206155656</appSendDateTime><appRecvDateTime>20150206155559</appRecvDateTime><appLgDateTime>20150206155559</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201502061201011234</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader></return></ns2:moscRegSvcChgResponse></soap:Body></soap:Envelope>";
				//2. 실패
				//responseXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscRegSvcChgResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>INL</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20150206155656</appSendDateTime><appRecvDateTime>20150206155559</appRecvDateTime><appLgDateTime>20150206155559</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201502061201011234</globalNo><encYn/><responseType>E</responseType><responseCode>ITL_SFC_E041</responseCode><responseLogcd/><responseTitle/><responseBasic>해당 부가서비스는 신청 할수있는 상품이 아닙니다.</responseBasic><langCode/><filler/></commHeader></return></ns2:moscRegSvcChgResponse></soap:Body></soap:Envelope>";
				//3. 연동 오류 실패
				//responseXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscRegSvcChgResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>INL</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X21</appEventCd><appSendDateTime>20150206155656</appSendDateTime><appRecvDateTime>20150206155559</appRecvDateTime><appLgDateTime>20150206155559</appLgDateTime><appNstepUserId>6833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9911100201502061201011234</globalNo><encYn/><responseType>E</responseType><responseCode>ITL_SYS_E0001</responseCode><responseLogcd/><responseTitle/><responseBasic>NSTEP ESB 연동 오류.</responseBasic><langCode/><filler/></commHeader></return></ns2:moscRegSvcChgResponse></soap:Body></soap:Envelope>";
				logger.error("====1. responseXml 생성 완료");
			}else{

				String getURL = this.getURL(param);
				String callUrl = (String) propertiesService.getString("mplatform.selfcare.url");

				NameValuePair[] data = {
						new NameValuePair("getURL", getURL)
				};

				logger.debug("*** M-PlatForm Connect Start ***");
				logger.debug("*** M-PlatForm Call URL *** " + callUrl);
				logger.debug("*** M-PlatForm Data *** " + data);

				responseXml = CommonHttpClient.Mplatpost(callUrl,data, "UTF-8", 10000);

				logger.debug("responseXml : " + responseXml);
			}

			if (responseXml.isEmpty()) {
				resultMap.put("globalNo", "");
				resultMap.put("code", "9998");
				resultMap.put("msg", MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
			} else {
				HashMap<String, Object> returnMap = this.toResponseParse(responseXml);

				resultMap.put("globalNo", returnMap.get("globalNo"));
				//부가서비스 재처리 실패
				if(!"N".equals(returnMap.get("responseType"))){
					resultMap.put("code", returnMap.get("responseCode"));
					resultMap.put("msg", returnMap.get("responseBasic"));
				}else{
					//부가서비스 재처리 성공
					resultMap.put("code", "0000");
					resultMap.put("msg", "성공");
				}
			}
			logger.debug("*** M-PlatForm Connect End ***");

		} catch (Exception e) {
			resultMap.put("globalNo", "");
			resultMap.put("code", "9999");
			if("".equals(Util.NVL(e.getMessage(), ""))) resultMap.put("msg", "PRX 연동 오류");
			else resultMap.put("msg", "PRX 연동 오류 ["+e.getMessage() + ("]"));
			logger.error("MPLATFORM 전송시 문제가 생긴다면 강제로 메시지 세팅 후 정상 처리.");
		}
		return resultMap;
	}

	/**
	 * @Description 평생할인 프로모션 부가서비스 최종 가입 실패 카운트
	 * @Param : searchVO
	 * @Return : int
	 * @Author : hsy
	 * @CreateDate : 2023.10.10
	 */
	public int getPrmtnotAddSocCnt(StatsMgmtVo searchVO) {
		return statsMgmtMapper.getPrmtnotAddSocCnt(searchVO);
	}

	/**
	 * 사용자 IP가져오기
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public String getClientIp()  {
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
			logger.error(e);
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
	private HashMap<String, Object> toResponseParse(String responseXml) throws IOException, JDOMException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Element root = XmlParse.getRootElement("<?xml version=\"1.0\" encoding=\"euc-kr\"?>"+responseXml);
		Element rtn = XmlParse.getReturnElement(root);

		Element commHeader = XmlParse.getChildElement(rtn, HEADER);
		resultMap.put("globalNo",     XmlParse.getChildValue(commHeader, GLOBAL_NO));
		resultMap.put("responseType", XmlParse.getChildValue(commHeader, RESPONSE_TYPE));
		resultMap.put("responseCode", XmlParse.getChildValue(commHeader, RESPONSE_CODE));
		resultMap.put("responseBasic", XmlParse.getChildValue(commHeader, RESPONSE_BASIC));

		return resultMap;
	}


	/**
	 * 평생할인 프로모션 적용 대상 회원 조회
	 * @param : searchVO
	 * @param : paramMap
	 * @return : List<EgovMap>
	 */
	public List<EgovMap> getDisPrmtTrgMgmtList(StatsMgmtVo searchVO, Map<String, Object> paramMap) {
		if(KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt()))
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다.");

		List<EgovMap> list = statsMgmtMapper.getDisPrmtTrgMgmtList(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * 평생할인 프로모션 적용 대상 회원 엑셀 다운
	 * @param : searchVO
	 * @param : paramMap
	 * @return : List
	 */
	public List<EgovMap> getDisPrmtTrgMgmtListExcel(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = statsMgmtMapper.getDisPrmtTrgMgmtListExcel(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * 업무구분별 평생할인 정책 적용 대상 상세 이력
	 * @param : searchVO
	 * @param : paramMap
	 * @return : List
	 */
	public List<EgovMap> getDisPrmtTrgMgmtDtl(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		if(KtisUtil.isEmpty(searchVO.getRegstDt()) || KtisUtil.isEmpty(searchVO.getContractNum()))
			throw new MvnoRunException(-1, "선택된 데이터가 없습니다.<br>데이터를 선택하고 처리해주시기 바랍니다.");

		List<EgovMap> list = statsMgmtMapper.getDisPrmtTrgMgmtDtl(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * 업무구분별 평생할인 정책 적용 대상 상세 이력 팝업 전체보기
	 * @param : searchVO
	 * @param : paramMap
	 * @return : List
	 */
	public List<EgovMap> getDisPrmtTrgMgmtDtlPop(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		if(KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt()))
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다.");

		List<EgovMap> list = statsMgmtMapper.getDisPrmtTrgMgmtDtlPop(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * 업무구분별 평생할인 정책 적용 대상 상세 이력 엑셀 다운로드
	 * @param : searchVO
	 * @param : paramMap
	 * @return : List
	 */
	public List<EgovMap> getDisPrmtTrgMgmtDtlPopExcel(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = statsMgmtMapper.getDisPrmtTrgMgmtDtlPopExcel(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * 평생할인 프로모션 지연 관리 조회
	 * @param : searchVO
	 * @param : paramMap
	 * @return : List
	 */
	public List<EgovMap> getDisPrmtDelayMst(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		/* 유효성 검사 */
		if(KtisUtil.isEmpty(searchVO.getSrchStrtDt())) {
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다.");
		}

		if(!KtisUtil.isEmpty(searchVO.getSearchGbn()) && KtisUtil.isEmpty(searchVO.getSearchName())) {
			throw new MvnoRunException(-1, "검색어를 입력하세요.");
		}

		List<EgovMap> list = statsMgmtMapper.getDisPrmtDelayMst(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * 평생할인 프로모션 지연 상세 페이지 - 최종 부가서비스 가입 리스트만 가져오기
	 * @param : List<EgovMap> resultList
	 * @return : List<EgovMap>
	 */
	public List<EgovMap> getPrmtDelayMaxDt(List<EgovMap> resultList) {

		List<EgovMap> maxDtList = new ArrayList<EgovMap>();

		maxDtList.add(resultList.get(0));

		//최종 부가서비스 가입 목록만 조회
		for(int i=1; i<resultList.size(); i++){
			if(!resultList.get(i-1).get("prcDtlSeq").equals(resultList.get(i).get("prcDtlSeq"))){
				maxDtList.add(resultList.get(i));
			}
		}

		/* 요청사항 : KOS 수동처리 한 경우, 수동처리완료 일시로 기준일자 변경 */
		for(int i=0; i<maxDtList.size(); i++){
			if(!"0000".equals(maxDtList.get(i).get("succYn")) && "Y".equals(maxDtList.get(i).get("procYn"))){
				maxDtList.get(i).setValue(2, maxDtList.get(i).get("procDate"));
			}
		}

		return maxDtList;
	}

	/**
	 * 평생할인 프로모션 지연 관리 조회 엑셀 다운로드
	 * @param : searchVO
	 * @param : paramMap
	 * @return : List
	 */
	public List<EgovMap> getDisPrmtDelayMstExcel(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = statsMgmtMapper.getDisPrmtDelayMstExcel(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}
	
	public List<EgovMap> getStatsInetList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
					statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
						throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
				}
		}
		
		List<EgovMap> list = statsMgmtMapper.getStatsInetList(statsMgmtVo);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}

	public List<EgovMap> getStatsInetHistList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		List<EgovMap> list = statsMgmtMapper.getStatsInetHistList(statsMgmtVo);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	public List<EgovMap> getStatsInetListExcel(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
				statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
					throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<EgovMap> list = statsMgmtMapper.getStatsInetListExcel(statsMgmtVo);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}

	/**
	 * @Description : M캐시 회원정보 리스트 조회
	 * @Param  : StatsMgmtVo
	 * @Param  : Map<String, Object>
	 * @Return : List<EgovMap>
	 * @CreateDate : 2024.05.27
	 */
	public List<EgovMap> getMcashJoinCustMgmtList(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap){

		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())) {
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
					statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
				throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}

		List<EgovMap> list = statsMgmtMapper.getMcashJoinCustMgmtList(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * @Description : M캐시 회원 수납 이력 팝업
	 * @Param  : StatsMgmtVo
	 * @Param  : Map<String, Object>
	 * @Return : List<EgovMap>
	 * @CreateDate : 2024.05.28
	 */
	public List<EgovMap> getMcashListDtlPop(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap){

		List<EgovMap> list = statsMgmtMapper.getMcashListDtlPop(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * @Description : M캐시 회원 수납 이력 합계 조회
	 * @Param  : StatsMgmtVo
	 * @Return : List<EgovMap>
	 * @CreateDate : 2024.05.28
	 */
	public List<EgovMap> getMcashDisTotal(StatsMgmtVo statsMgmtVo){

		List<EgovMap> list = statsMgmtMapper.getMcashDisTotal(statsMgmtVo);

		return list;
	}

	/**
	 * @Description : M캐시 회원정보 이력 조회
	 * @Param  : StatsMgmtVo
	 * @Param  : Map<String, Object>
	 * @Return : List<EgovMap>
	 * @CreateDate : 2024.08.07
	 */
	public List<EgovMap> getMcashJoinCustMgmtHist(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap) {

		List<EgovMap> list = statsMgmtMapper.getMcashJoinCustMgmtHist(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * @Description : M캐시 회원정보 엑셀 다운로드
	 * @Param  : StatsMgmtVo
	 * @Param  : Map<String, Object>
	 * @Return : List<EgovMap>
	 * @CreateDate : 2024.05.29
	 */
	public List<EgovMap> getMcashJoinCustMgmtListExcel(StatsMgmtVo statsMgmtVo, Map<String, Object> paramMap) {

		List<EgovMap> list = statsMgmtMapper.getMcashJoinCustMgmtListExcel(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * 서식지 연동 현황 조회
	 */
	public List<EgovMap> getAppFormSyncList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){

		List<EgovMap> list = statsMgmtMapper.getAppFormSyncList(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);


		return list;
	}

	/**
	 * 서식지 연동 현황 상세 조회
	 */
	public List<EgovMap> getAppFormSyncDetailList(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){

		List<EgovMap> list = statsMgmtMapper.getAppFormSyncDetailList(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);


		return list;
	}

	/**
	 * 서식지 연동 현황 엑셀 다운로드
	 */
	public List<EgovMap> getAppFormSyncListExcel(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){

		List<EgovMap> list = statsMgmtMapper.getAppFormSyncListExcel(statsMgmtVo);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);


		return list;
	}

	/**
	 * 평생할인 프로모션 자동 가입 검증 목록 조회
	 * @param : searchVO
	 * @param : paramMap
	 * @return : List
	 */
	public List<EgovMap> getDisPrmtChkList(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		/* 유효성 검사 */
		if(KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt())) {
			throw new MvnoRunException(-1, "조회일자가 존재하지 않습니다.");
		}

		if(!KtisUtil.isEmpty(searchVO.getSearchGbn()) && KtisUtil.isEmpty(searchVO.getSearchName())) {
			throw new MvnoRunException(-1, "검색어를 입력하세요.");
		}

		List<EgovMap> list = statsMgmtMapper.getDisPrmtChkList(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * @Description 평생할인 프로모션 검증 처리여부 수정
	 * @Param : searchVO
	 * @Return : int
	 * @CreateDate : 2025.04.01
	 */
	public int updatePrmtChkProc(StatsMgmtVo searchVO) {

		if(KtisUtil.isEmpty(searchVO.getRegSeq())) {
			throw new MvnoRunException(-1, "선택된 데이터가 없습니다.<br>데이터를 선택하고 처리해주시기 바랍니다.");
		}

		return statsMgmtMapper.updatePrmtChkProc(searchVO);
	}

	/**
	 * @Description : 평생할인 프로모션 자동 가입 검증 목록 엑셀 다운로드
	 * @param : searchVO
	 * @param : paramMap
	 * @CreateDate : 2025.03.25
	 */
	public List<EgovMap> getDisPrmtChkListExcel(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		/* 유효성 검사 */
		if(KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt())) {
			throw new MvnoRunException(-1, "조회일자가 존재하지 않습니다.");
		}

		if(!KtisUtil.isEmpty(searchVO.getSearchGbn()) && KtisUtil.isEmpty(searchVO.getSearchName())) {
			throw new MvnoRunException(-1, "검색어를 입력하세요.");
		}
		List<EgovMap> list = statsMgmtMapper.getDisPrmtChkListExcel(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	public List<String> getDisPrmtAddList(StatsMgmtVo searchVO, Map<String, Object> paramMap) {
		return statsMgmtMapper.getDisPrmtAddList(searchVO);
	}


	/**
	 * 평생할인 부가서비스 가입 검증 목록 조회
	 * @param : searchVO
	 * @param : paramMap
	 * @return : List
	 */
	public List<EgovMap> getDisAddChkList(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		/* 유효성 검사 */
		if(KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt())) {
			throw new MvnoRunException(-1, "조회일자가 존재하지 않습니다.");
		}

		if(!KtisUtil.isEmpty(searchVO.getSearchGbn()) && KtisUtil.isEmpty(searchVO.getSearchName())) {
			throw new MvnoRunException(-1, "검색어를 입력하세요.");
		}

		List<EgovMap> list = statsMgmtMapper.getDisAddChkList(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * @Description : 평생할인 부가서비스 가입 검증 목록 엑셀 다운로드
	 * @param : searchVO
	 * @param : paramMap
	 * @CreateDate : 2025.07.08
	 */
	public List<EgovMap> getDisAddChkListExcel(StatsMgmtVo searchVO, Map<String, Object> paramMap) {

		/* 유효성 검사 */
		if(KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt())) {
			throw new MvnoRunException(-1, "조회일자가 존재하지 않습니다.");
		}

		if(!KtisUtil.isEmpty(searchVO.getSearchGbn()) && KtisUtil.isEmpty(searchVO.getSearchName())) {
			throw new MvnoRunException(-1, "검색어를 입력하세요.");
		}
		List<EgovMap> list = statsMgmtMapper.getDisAddChkListExcel(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		return list;
	}

	/**
	 * 가입중인 평생할인 부가서비스 가져오기
	 * @param : searchVO
	 * @param : paramMap
	 * @return : List
	 */
	public List<EgovMap> getDisAddList(StatsMgmtVo searchVO) {

		/* 유효성 검사 */
		if(KtisUtil.isEmpty(searchVO.getContractNum())) {
			throw new MvnoRunException(-1, "계약번호를 선택해주세요.");
		}

		List<EgovMap> list = statsMgmtMapper.getDisAddList(searchVO);

		return list;
	}

	/**
	 * @Description 평생할인 프로모션 검증 완료
	 * @Param : searchVO
	 * @Return : int
	 * @CreateDate : 2025.07.08
	 */
	public int updateDisAddChkComp(StatsMgmtVo searchVO) {

		if(KtisUtil.isEmpty(searchVO.getChkDt()) || KtisUtil.isEmpty(searchVO.getContractNum())) {
			throw new MvnoRunException(-1, "선택된 데이터가 없습니다.<br>데이터를 선택하고 처리해주시기 바랍니다.");
		}

		return statsMgmtMapper.updateDisAddChkComp(searchVO);
	}


	/**
	 * @Description 평생할인 부가서비스 재처리
	 * @Param : searchVO
	 * @Return : int
	 * @CreateDate : 2025.07.08
	 */
	public Map<String, Object> disAddRty(StatsMgmtVo searchVO) {
		// 1. 검증 데이터 가져오기
		StatsMgmtVo disAddChk = statsMgmtMapper.getDisAddChk(searchVO);
		disAddChk.setUserId(searchVO.getUserId());

		// 2. 대상 재처리 검증
		this.validateDisAddRty(disAddChk);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 3. 부가서비스 해지
		// 3-1. 해지할 부가서비스 목록 가져오기
		try {
			this.cancelAddSvcList(disAddChk);
		} catch (Exception e) {
			this.updateDisAddChkRty(disAddChk, "N", e.getMessage());
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "재처리 중 오류가 발생하였습니다.<br>" + e.getMessage());
			return resultMap;
		}

		// 4. 부가서비스 가입
		boolean isSuccess = false;
		String resultMessage = "성공";
		try {
			this.regAddSvcList(disAddChk);
			isSuccess = true;
		} catch (Exception e) {
			resultMessage = e.getMessage();
		}

		this.updateDisAddChkRty(disAddChk, isSuccess ? "Y" : "N", resultMessage);

		if (isSuccess) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", resultMessage);
		} else {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "재처리 중 오류가 발생하였습니다.<br>" + resultMessage);
		}
		return resultMap;
	}

	private void validateDisAddRty(StatsMgmtVo disAddChk) {
		// 2. 재처리 가능한지 체크
		// 2-1. 재처리 불가 - 재처리 시도하지 않았거나 재처리 실패한건만 가능
		if ("Y".equals(disAddChk.getRtyYn())) {
			throw new MvnoRunException(-1, "이미 재처리가 완료된 건입니다.");
		}
		// 2-2. 재처리 불가 - 부가서비스 불일치, 부가서비스 금액 상이일때만 가능
		if (!("B".equals(disAddChk.getChkRslt()) || "C".equals(disAddChk.getChkRslt()))) {
			throw new MvnoRunException(-1, "검증결과가 부가서비스 불일치, 부가서비스 금액 상이 일때만 재처리가 가능합니다.");
		}
		// 2-3. 재처리 불가 - 검증 이후 새로운 이벤트 발생시 불가
		int evntCnt = statsMgmtMapper.disAddEvntChk(disAddChk);
		if (evntCnt > 0) {
			throw new MvnoRunException(-1, "검증 이후 변경이 발생하여 재처리가 불가능합니다.");
		}
	}

	private void cancelAddSvcList(StatsMgmtVo disAddChk) {
		String ktSocs = disAddChk.getKtSocs();
		if (StringUtils.isEmpty(ktSocs)) {
			return;
		}

		List<String> ktSocList = Arrays.asList(ktSocs.split(","));
		MoscPrdcTrtmVO moscPrdcTrtmVO = getMoscPrdcTrtmVO(disAddChk.getNcn(), disAddChk.getSubscriberNo(), disAddChk.getCustomerId(), ktSocList, PRDC_SBSC_TRTM_CD_CANCEL);

		// 부가서비스 일괄 해지 처리(Y24, Y25)
		if(!this.preCheckAndCallMoscPrdcTrtm(moscPrdcTrtmVO)){
			// -> 실패 시 단건으로 해지 처리(X38)
			Map<String, Object> resultMap = null;
			for(String ktSoc : ktSocList){
				resultMap = mplatformService.moscRegSvcCanChg(disAddChk.getNcn(), disAddChk.getSubscriberNo(), disAddChk.getCustomerId(), ktSoc);
				if (!"N".equals(resultMap.get("responseType"))) {
					throw new MvnoRunException(-1, StringUtils.defaultIfEmpty((String) resultMap.get("responseBasic"), "재처리 중 오류가 발생하였습니다."));
				}
			}
		}
	}

	private void regAddSvcList(StatsMgmtVo disAddChk) {

		String prmtSocs = disAddChk.getPrmtSocs();
		if (StringUtils.isEmpty(prmtSocs)) {
			return;
		}
		List<String> prmtAddSvcList = Arrays.asList(prmtSocs.split(","));

		MoscPrdcTrtmVO moscPrdcTrtmVO = MplatformService.getMoscPrdcTrtmVO(disAddChk.getNcn(), disAddChk.getSubscriberNo(), disAddChk.getCustomerId(), prmtAddSvcList, PRDC_SBSC_TRTM_CD_REG);

		// 부가서비스 일괄 가입 처리(Y24, Y25)
		if(!this.preCheckAndCallMoscPrdcTrtm(moscPrdcTrtmVO)){
			// -> 실패 시 단건으로 가입 처리(X21)
			Map<String, Object> resultMap = null;
			for(String prmtAddSvc : prmtAddSvcList){
				resultMap = mplatformService.regSvcChg(disAddChk.getNcn(), disAddChk.getSubscriberNo(), disAddChk.getCustomerId(), prmtAddSvc);
				if (!"N".equals(resultMap.get("responseType"))) {
					throw new MvnoRunException(-1, StringUtils.defaultIfEmpty((String) resultMap.get("responseBasic"), "재처리 중 오류가 발생하였습니다."));
				}
			}
		}
	}

	private void updateDisAddChkRty(StatsMgmtVo disAddChk, String rtyYn, String message) {
		StatsMgmtVo updateVo = new StatsMgmtVo();
		updateVo.setContractNum(disAddChk.getContractNum());
		updateVo.setChkDt(disAddChk.getChkDt());
		updateVo.setUserId(disAddChk.getUserId());
		updateVo.setRtyYn(rtyYn);
		updateVo.setRtyRslt(message);
		statsMgmtMapper.updateDisAddChkRty(updateVo);
	}

	private boolean preCheckAndCallMoscPrdcTrtm(MoscPrdcTrtmVO moscPrdcTrtmVO) {
		Map<String, Object> resultY24 = mplatformService.moscPrdcTrtm(moscPrdcTrtmVO, "Y24");
		if (!"0000".equals(resultY24.get("code"))) {
			return false;
		}
		Map<String, Object> resultY25 = mplatformService.moscPrdcTrtm(moscPrdcTrtmVO, "Y25");
		if (!"0000".equals(resultY25.get("code"))) {
			return false;
		}
		return true;
	}
	
	 /**
     * @Description : 유심변경현황 조회
     * @Param  : StatsMgmtVo
     */
	public List<EgovMap> getUsimChgHst(StatsMgmtVo statsMgmtVo , Map<String, Object> paramMap){
		
		if(statsMgmtVo.getSearchGbn() == null || "".equals(statsMgmtVo.getSearchGbn())){
			if (statsMgmtVo.getSrchStrtDt() == null || "".equals(statsMgmtVo.getSrchStrtDt()) ||
					statsMgmtVo.getSrchEndDt() == null || "".equals(statsMgmtVo.getSrchEndDt())){
						throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
				}
		}
		
		List<EgovMap> list = statsMgmtMapper.getUsimChgHst(statsMgmtVo);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}

    //명의변경 신청서 복사
    public int copyNameChg(StatsMgmtVo vo) throws EgovBizException, UnsupportedEncodingException {

        //1.custReqSeq로 현재 상태 조회
        Map<String, String> stateMap = statsMgmtMapper.getNameChgState(vo.getCustReqSeq());
        if(stateMap == null) throw new MvnoRunException(-1, "신청서 정보가 존재하지 않습니다.");

        //명의변경 완료된 신청서는 복사 불가능
        if("CP".equals(stateMap.get("PROC_CD")) || "08".equals(stateMap.get("MCN_STATE_CODE"))) {
            throw new MvnoRunException(-1, "명의변경처리 완료된 신청서는 복사할 수 없습니다.");
        }

        //2.신청서 복사
        int resultCnt = 0;
        resultCnt = statsMgmtMapper.copyNameChgMst(vo);
        resultCnt = statsMgmtMapper.copyNameChgDtl(vo);
		statsMgmtMapper.copyNameChgAgent(vo);

        String strMemo = stateMap.get("MEMO") == null ? "" : stateMap.get("MEMO").trim();
        if(resultCnt > 0){
			StringBuffer sb = new StringBuffer(strMemo);
            if(!StringUtils.isEmpty(strMemo)){
                sb.append(" /복사(");
				sb.append(vo.getNewMcnResNo());
				sb.append(")");
				strMemo = sb.toString();
                //strMemo 길이 검사 및 자르기
                byte[] bytes = strMemo.getBytes("UTF-8");
                if(bytes.length > 600) { //컬럼이 varchar(600)
                    int byteCount = 0;
                    int index = 0;
                    while (index < strMemo.length()) {
                        char c = strMemo.charAt(index);
                        int charByte = String.valueOf(c).getBytes("UTF-8").length;
                        if(byteCount + charByte > 600) {
                            break;
                        }
                        byteCount += charByte;
                        index++;
                    }
                    strMemo = strMemo.substring(0, index);
                }
            }else {
				sb.append("복사(");
				sb.append(vo.getNewMcnResNo());
				sb.append(")");
				strMemo = sb.toString();
            }

            vo.setProcCd("BK");
            vo.setClMemo(strMemo);
            resultCnt = statsMgmtMapper.updateProcCdNm(vo);
        }

        return resultCnt;
    }
}
