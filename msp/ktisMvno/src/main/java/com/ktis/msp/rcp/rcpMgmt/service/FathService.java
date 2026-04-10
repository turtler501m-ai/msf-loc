package com.ktis.msp.rcp.rcpMgmt.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.rcpMgmt.mapper.FathMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.*;
import com.ktis.msp.util.StringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class FathService extends BaseService {
	
	@Autowired
	private FathMapper fathMapper;
		
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	@Autowired
	private OrgCommonService orgCommonService;

	@Resource(name="nStepCallService")
	private NstepCallService nStepCallService;
	@Autowired
	private RcpEncService encSvc;

	/*개통 안면인증 정보확인*/
	public Map<String, String> selectFathInfo(String resNo) {
        Map<String, String> result = fathMapper.selectFathInfo(resNo);
        return result != null ? result : Collections.<String, String>emptyMap();
	}

    /*명의변경 안면인증 정보확인*/
    public Map<String, String> selectFathInfoMcn(String mcnResNo) {
        Map<String, String> result = fathMapper.selectFathInfoMcn(mcnResNo);
        return result != null ? result : Collections.<String, String>emptyMap();
    }
	
	/*안면인증 PUSH 데이터 확인*/
	public FathVO selectFathResltPush(String fathTransacId){
		//안면인증 결과 PUSH 조회 
		return fathMapper.selectFathResltPush(fathTransacId);
	}

	/**
	 * 셀프안면인증 URL 발급
	 * @param fathVO
	 * @return
	 */
	public void insertFathSelfUrl(FathVO fathVO) {

		String paramCpntId = fathVO.getCntpntShopId();
		if(!"1100011741".equals(paramCpntId)) { //직영온라인이 아닌 경우
			//(대리점)안면인증 가능 접점ID조회
			String cpntId = this.getCpntId(paramCpntId);
			if (StringUtils.isEmpty(cpntId)) { // 조회된 결과가 없는 경우 안면인증 대상이 아님
				throw new MvnoRunException(-1, "해당대리점은 대면인증으로 처리바랍니다.");
			}
		}
		String smsRcvTelNo = fathVO.getSmsRcvTelNo();
		boolean result = Pattern.matches("^[0-9]*$", smsRcvTelNo);
		if(!result) {
			throw new MvnoRunException(-1, "안면인증 연락처는 숫자만 가능합니다.");
		}
		
		String url = "";
		
		//이미 발급된 URL 중 유효한 것이 있는지 조회
		FathVO existInfo = fathMapper.selectFathSelfUrl(fathVO.getResNo());
				
		if(existInfo != null) { //유효한 URL 존재시 문자만 재발송
			url = existInfo.getUrl();
		} else { // 유효한 URL 미존재 시 신규발급 
			String uuid = UUID.randomUUID().toString();
			url = propertiesService.getString("fath.self.url") + "?u=" + uuid;
			fathVO.setUuid(uuid);
			fathVO.setUrl(url);
			fathMapper.insertFathSelfUrl(fathVO);
		}

		String operType = fathVO.getOperType();
		if(StringUtil.isEmpty(operType)) {
			throw new MvnoRunException(-1, "업무유형이 존재하지 않습니다.");
		}
		
		//안면인증 연락처 업데이트
		if("MCN".equals(operType)) { 	
			this.updateNameChgFathMobile(fathVO);	//명의변경
		} else { 
			this.updateFathMobile(fathVO);	 		//명의변경 외
		}		
		
		//문자 전송
		String templateId = "444";
		KtMsgQueueReqVO ktMsgQueueReqVO = smsMgmtMapper.getKtTemplateText(templateId);
		if (ktMsgQueueReqVO == null) {
			throw new MvnoRunException(-1, "문자템플릿이 존재하지 않습니다.");
		}


		ktMsgQueueReqVO.setMessage(ktMsgQueueReqVO.getTemplateText()
				.replaceAll(Pattern.quote("#{url}"), url));

		ktMsgQueueReqVO.setSubject(ktMsgQueueReqVO.getTemplateSubject());
		ktMsgQueueReqVO.setRcptData(smsRcvTelNo);
		ktMsgQueueReqVO.setReserved01("MSP");
		ktMsgQueueReqVO.setReserved02("fathSelfUrl");
		ktMsgQueueReqVO.setReserved03(fathVO.getSessionUserId());
		ktMsgQueueReqVO.setUserId(fathVO.getSessionUserId());

		smsMgmtMapper.insertKtMsgTmpQueue(ktMsgQueueReqVO);
		smsMgmtMapper.insertSmsSendMst(ktMsgQueueReqVO.toSmsSendVO());
	}


	/**
	 * 안면인증 연락처 변경(명의변경)
	 */
	public void updateNameChgFathMobile(FathVO fathVO){
		fathMapper.updateNameChgFathMobile(fathVO);
	}

	/**
	 * 안면인증 연락처 변경(명의변경 외)
	 */
	public void updateFathMobile(FathVO fathVO){
		fathMapper.updateFathMobile(fathVO);
	}



	/**
	 * 안면인증 대상여부 업데이트
	 */
	public void updateFathTrgYn(RcpDetailVO rcpDetailVO){
		fathMapper.updateFathTrgYn(rcpDetailVO);
	}

	/**
	 * 본인인증유형 업데이트(법정대리인)
	 */
	public void updateSelfCertTypeMinor(RcpDetailVO rcpDetailVO){
		fathMapper.updateSelfCertTypeMinor(rcpDetailVO);
	}

	/**
	 * 본인인증유형 업데이트
	 */
	public void updateSelfCertType(RcpDetailVO rcpDetailVO){
		fathMapper.updateSelfCertType(rcpDetailVO);
	}
	/**
	 * 본인인증유형 업데이트(법정대리인)
	 */
	public void updateNameChgSelfCertTypeMinor(RcpDetailVO rcpDetailVO){
		fathMapper.updateNameChgSelfCertTypeMinor(rcpDetailVO);
	}

	/**
	 * 본인인증유형 업데이트
	 */
	public void updateNameChgSelfCertType(RcpDetailVO rcpDetailVO){
		fathMapper.updateNameChgSelfCertType(rcpDetailVO);
	}
	
	/**
	 * 안면인증 트랜잭션 ID 업데이트
	 */
	public void updateFathTransacId(RcpDetailVO rcpDetailVO){
		fathMapper.updateFathTransacId(rcpDetailVO);
	}
	
	/**
	 * 안면인증 인증일자 업데이트
	 */
	public void updateFathCmpltNtfyDt(RcpDetailVO rcpDetailVO){
		fathMapper.updateFathCmpltNtfyDt(rcpDetailVO);
	}
	
	/**
	 * 신청서 진행상태 변경
	 */
	public void updateReqStateCode(RcpDetailVO rcpDetailVO){
		 fathMapper.updateReqStateCode(rcpDetailVO);
	}
	
	/**
	 * Acen 업무유형 조회
	 */
	public String selectAcenEvntType(String resNo){
		 return fathMapper.selectAcenEvntType(resNo);
	}

    /**
     * 명의변경 신청서 정보 변경
     */
    public void updateNameChgInfo(RcpDetailVO rcpDetailVO){
        fathMapper.updateNameChgInfo(rcpDetailVO);
    }

    /**
     * 명의변경 신청서 정보 변경
     */
    public Map<String, String> getNameChgInfo(String mcnResNo){
        return fathMapper.getNameChgInfo(mcnResNo);
    }

	
	/**
	 * 신분증 정보 변경(법정대리인)
	 */
	public void updateSelfCertInfoMinor(RcpDetailVO rcpDetailVO){
		fathMapper.updateSelfCertInfoMinor(rcpDetailVO);
	}
	/**
	 * 신분증 정보 변경
	 */
	public void updateSelfCertInfo(RcpDetailVO rcpDetailVO){
		fathMapper.updateSelfCertInfo(rcpDetailVO);
	}

	/**
	 * (대리점)안면인증 가능 접점ID조회
	 */
	public String getCpntId(String cntpntShopId){
		return fathMapper.getCpntId(cntpntShopId);
	}
	
	/**
	 * 명의변경 신분증 정보 변경(법정대리인)
	 */
	public void updateNameChgSelfCertInfoMinor(RcpDetailVO rcpDetailVO){
		fathMapper.updateNameChgSelfCertInfoMinor(rcpDetailVO);
	}
	
	/**
	 * 명의변경 신분증 정보 변경
	 */
	public void updateNameChgSelfCertInfo(RcpDetailVO rcpDetailVO){
		fathMapper.updateNameChgSelfCertInfo(rcpDetailVO);
	}
	
	public void osssReqVoSet(OsstReqVO osstReqVO, RcpDetailVO rcpDetailVO) throws MvnoServiceException {
		String paramCpntId = rcpDetailVO.getCntpntShopId();
		if(!"1100011741".equals(paramCpntId)) { //직영온라인이 아닌 경우
			//(대리점)안면인증 가능 접점ID조회
			paramCpntId = this.getCpntId(paramCpntId);
			if (StringUtils.isEmpty(paramCpntId)) { // 조회된 결과가 없는 경우 안면인증 대상이 아님
				throw new MvnoServiceException("해당대리점은 대면인증으로 처리바랍니다.");
			}
		}
		String selfCertType = rcpDetailVO.getSelfCertType();
		if("NM".equals(rcpDetailVO.getCstmrType())) { //미성년자인 경우 법정대리인 신분증타입
			selfCertType = rcpDetailVO.getMinorSelfCertType();
		}

		if(KtisUtil.isEmpty(selfCertType)){
			throw new MvnoServiceException("본인인증방식은 필수입니다. <br>미성년자인 경우 법정대리인 본인인증방식을 선택해주세요");
		}
		// 조회 구분 값
		String retvCdval = "";
		if("01".equals(selfCertType)) {
			retvCdval = "REGID";	// 주민등록증
		} else if ("02".equals(selfCertType)) {
			retvCdval = "DRIVE";	// 운전면허증
		} else if ("03".equals(selfCertType)) {
			retvCdval = "HANDI";	// 장애인등록증
		} else if ("04".equals(selfCertType)) {
			retvCdval = "MERIT";	// 국가유공자증
		} else if ("05".equals(selfCertType)) {
			retvCdval = "NAMEC";	// 여권
		} else if ("06".equals(selfCertType)) {
			retvCdval = "FORGN";	// 외국인등록증
		}
		
		if(KtisUtil.isEmpty(retvCdval)){
			throw new MvnoServiceException("본인인증방식을 확인해주세요.");
		}
		osstReqVO.setCpntId(paramCpntId);
		osstReqVO.setOnlineOfflnDivCd("ONLINE");
		osstReqVO.setOrgId(rcpDetailVO.getCntpntShopId());
		osstReqVO.setAsgnAgncId(rcpDetailVO.getMngmAgncId());
		osstReqVO.setRetvCdVal(retvCdval);
	}
	
	
	public Map<String, String> fathResltRtn(String resltCd){
		
		/* 
		0000 안면인증 필수
		CD01 안면인증 불필요 - 오픈일자 미도래
		CD02 안면인증 불필요 - 안면인증 스킵 권한 보유자
		CD03 안면인증 미오픈 대면 사업자 (2026-02-11 기준 미사용)
		CD04 안면인증 불필요 - MIS 장애 스킵 개통 불가
		CD05 안면인증 필수 - MIS 장애 스킵
		CD06 안면인증 불필요 - 안면인증 미대상 접점
		CD07 안면인증 불필요 - 안면인증 미대상 신분증
		CD08 안면인증 불필요 - 안면인증 실패 스킵 권한 보유자
		CD09 안면인증 불필요 - 대리인 개통 안면인증 스킵 권한 보유자
		* 안면인증 필수 = 오더 처리 시 안면인증트랜잭션ID 수록
		* 응답값 CD02 / CD08 / CD09 의 경우, 온라인 개통/명변/우수기변 시 KOS를 통해서 처리 필수(MP 통해 오더 업무 처리 불가)
		* 응답값 CD04 의 경우, MIS장애로 오더 업무 불가한 상태.  이후 MIS장애가 해소되면 안면인증 대상여부 조회부터 재수행 필수.
		*/
		
		Map<String, String> rtnMap = new HashMap<String, String>();

		//CD02 안면인증 불필요 - 안면인증 스킵 권한 보유자는 상담사 개통 시 FS8, FS9 수행을 위해 안면인증 로직을 적용함
		List<String> trgList = Arrays.asList("0000", "CD02", "CD05"); //안면인증 적용대상 
		if(trgList.contains(resltCd)) {
			rtnMap.put("resltCd", "00000");
		} else {
			List<String> notTrgList = Arrays.asList("CD01", "CD06", "CD07"); //안면인증 미대상 
			List<String> kosTrgList = Arrays.asList("CD08", "CD09"); //KOS처리 대상?
			if(notTrgList.contains(resltCd)) {
				rtnMap.put("resltCd", "00001");
				rtnMap.put("resltSbst", "안면인증 미대상입니다.");	
			} else if (kosTrgList.contains(resltCd)) {
				rtnMap.put("resltCd", "00002");
				rtnMap.put("resltSbst", "안면인증 미대상 KOS를 통해서 처리바랍니다 <br> MP통해 오더 업무 처리불가");
			} else if ("CD04".equals(resltCd)) {
				rtnMap.put("resltCd", "00003");
				rtnMap.put("resltSbst", "[CD04]MIS 장애 스킵 개통 불가 안면인증 대상조회 다시 진행바랍니다.");
			}
		}
		
		return rtnMap;
	}

	public Map<String, Object> fathSuccRtn(FathVO fathVO, RcpDetailVO rcpDetailVO){
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();

		String fathCstmrName = fathVO.getCustNm();
		String fathCstmrRrn = fathVO.getCustIdfyNo();

		Map<String, String> fathInfo = null;
		if(KtisUtil.isEmpty(rcpDetailVO.getMcnResNo())) {
			fathInfo = this.selectFathInfo(rcpDetailVO.getResNo());
		}else {
			fathInfo = this.selectFathInfoMcn(rcpDetailVO.getMcnResNo());
		}

		String rcpCstmrName = fathInfo.get("CSTMR_NAME");
		String rcpCstmrRrn = fathInfo.get("CSTMR_RRN");

		if (StringUtils.isEmpty(fathCstmrName) || StringUtils.isEmpty(fathCstmrRrn)) {
			rtnMap.put("code", "9996");
			rtnMap.put("msg", "안면인증 정보가 누락되었습니다.");
			return rtnMap;
		}

		//신청서 정보 
		if (StringUtils.isEmpty(rcpCstmrName) || StringUtils.isEmpty(rcpCstmrRrn)) {
			rtnMap.put("code", "9993");
			rtnMap.put("msg", "안면인증 정보가 누락되었습니다.");
			return rtnMap;
		}
		// 이름 비교
		if (!fathCstmrName.trim().equals(rcpCstmrName.trim())) {
			rtnMap.put("code", "1001");
			rtnMap.put("msg", "안면인증 고객명과 신청서 고객명이 일치하지 않습니다.");
			return rtnMap;
		}
		// 주민번호 비교
		if (!fathCstmrRrn.equals(rcpCstmrRrn)) {
			rtnMap.put("code", "1002");
			rtnMap.put("msg", "안면인증 주민번호와 신청서 주민번호가 일치하지 않습니다.");
			return rtnMap;
		}

		String fathIdcardTypeCd = fathVO.getRetvCdVal(); //신분증종류  (I : 주민등록증, D:운전면허증)

		String selfCertType = "";
		if ("I".equals(fathIdcardTypeCd)) {
			selfCertType = "01";    // 주민등록증
		} else if ("D".equals(fathIdcardTypeCd)) {
			selfCertType = "02";    // 운전면허증
		} else {
			selfCertType = "";
		}
		String selfIssuExprDt = fathVO.getIssDateVal();  //발급일자
		String driverSelfIssuNum = fathVO.getDriveLicnsNo();//운전면허번호
		String fathCmpltNtfyDt = fathVO.getFathCmpltNtfyDt();
		
		rcpDetailVO.setFathCmpltNtfyDt(fathCmpltNtfyDt);
		rcpDetailVO.setSelfCertType(selfCertType);
		rcpDetailVO.setSelfIssuExprDt(selfIssuExprDt);
		rcpDetailVO.setSelfIssuNum(driverSelfIssuNum);

        //2026.03 빈문자열이 암호화되는것을 막기위해 null처리(vo는 공용이기 때문에 암호화 관련일 때에만 null처리함)
        if("".equals(rcpDetailVO.getCstmrNativeRrn())) {rcpDetailVO.setCstmrNativeRrn(null);}
        if("".equals(rcpDetailVO.getReqAccountNumber())) {rcpDetailVO.setReqAccountNumber(null);}
        if("".equals(rcpDetailVO.getReqCardNo())) {rcpDetailVO.setReqCardNo(null);}
        if("".equals(rcpDetailVO.getMinorAgentRrn())) {rcpDetailVO.setMinorAgentRrn(null);}
        if("".equals(rcpDetailVO.getOthersPaymentRrn())) {rcpDetailVO.setOthersPaymentRrn(null);}
        if("".equals(rcpDetailVO.getJrdclAgentRrn())) {rcpDetailVO.setJrdclAgentRrn(null);}
        if("".equals(rcpDetailVO.getEntrustResRrn())) {rcpDetailVO.setEntrustResRrn(null);}
        if("".equals(rcpDetailVO.getReqAccountRrn())) {rcpDetailVO.setReqAccountRrn(null);}
        if("".equals(rcpDetailVO.getReqCardRrn())) {rcpDetailVO.setReqCardRrn(null);}
        if("".equals(rcpDetailVO.getCstmrForeignerRrn())) {rcpDetailVO.setCstmrForeignerRrn(null);}
        if("".equals(rcpDetailVO.getCstmrForeignerPn())) {rcpDetailVO.setCstmrForeignerPn(null);}
        if("".equals(rcpDetailVO.getSelfIssuNum())) {rcpDetailVO.setSelfIssuNum(null);}
        if("".equals(rcpDetailVO.getMinorSelfIssuNum())) {rcpDetailVO.setMinorSelfIssuNum(null);}

		RcpDetailVO encryptedRcpDetailVO = encSvc.encryptDBMSRcpDetailVO(rcpDetailVO);
		//명의변경이 아닐 때
		if(KtisUtil.isEmpty(rcpDetailVO.getMcnResNo())) {
			this.updateFathCmpltNtfyDt(rcpDetailVO); //안면인증일자 업데이트
			if("NM".equals(encryptedRcpDetailVO.getCstmrType())) { //미성년자인 경우
				this.updateSelfCertInfoMinor(encryptedRcpDetailVO); // 법정대리인 정보 변경
			} else {
				this.updateSelfCertInfo(encryptedRcpDetailVO); //신분증 정보 변경
			}
			
		//명의변경일 때
		}else {
			rcpDetailVO.setAppEventCd("FS9");
			rcpDetailVO.setMcnResNo(rcpDetailVO.getMcnResNo());
			this.updateNameChgInfo(rcpDetailVO);     //명의변경 정보 업데이트
			if("NM".equals(encryptedRcpDetailVO.getCstmrType())) { //미성년자인 경우
				this.updateNameChgSelfCertInfoMinor(encryptedRcpDetailVO); // 법정대리인 정보 변경
			} else {
				this.updateNameChgSelfCertInfo(encryptedRcpDetailVO); //신분증 정보 변경
			}
		}

		rtnMap.put("fathCmpltNtfyDt", fathCmpltNtfyDt.substring(0, 8));
		rtnMap.put("selfCertType", selfCertType);                //신분증타입
		rtnMap.put("selfIssuExprDt", selfIssuExprDt);            //발급일자
		rtnMap.put("driverSelfIssuNum", driverSelfIssuNum);      //운전면허번호
		rtnMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
		rtnMap.put("msg", "안면인증 결과확인 성공");
		
		return rtnMap;
	}
	public Map<String, Object> fathFailRtn(RcpDetailVO rcpDetailVO){
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
		rtnMap.put("msg", "안면인증 실패");
		//안정화기간 여부 확인을 위한 FT0연동 (안정화기간 종료 후 해당 로직 제거 필요) 
		OsstReqVO osstReqVO = new OsstReqVO();
		osstReqVO.setResNo(rcpDetailVO.getResNo());
		osstReqVO.setMcnResNo(rcpDetailVO.getMcnResNo());
		osstReqVO.setAppEventCd("FT0");
		try {
			this.osssReqVoSet(osstReqVO, rcpDetailVO);
		} catch (MvnoServiceException e) {
			rtnMap.put("code", "9999");
			rtnMap.put("msg", e.getMessage());
			return rtnMap;
		}
		HashMap<String, Object> map = (HashMap<String, Object>) nStepCallService.osstServiceCall((String) propertiesService.getString("simpleOpenUrl"), osstReqVO);

		if(map != null) {
			HashMap<String, Object> osstRst = (HashMap<String, Object>) map.get("osstRst");	
			String stbznPerdYn = (String) osstRst.get("stbznPerdYn");
			if("Y".equals(stbznPerdYn)) {
				rtnMap.put("msg", "안정화기간으로 성공");
			} 
		}
		return rtnMap;
	}

    public Map<String, Object> processOsstFT1(RcpDetailVO rcpDetailVO) {
		OsstReqVO osstReqVO = new OsstReqVO();
		osstReqVO.setResNo(rcpDetailVO.getResNo());
		osstReqVO.setMcnResNo(rcpDetailVO.getMcnResNo());
		osstReqVO.setAppEventCd("FT1");
		osstReqVO.setFathTransacId(rcpDetailVO.getFathTransacId());
		osstReqVO.setAsgnAgncId(rcpDetailVO.getMngmAgncId());

		Map<String, Object> resultMap = nStepCallService.osstServiceCall(propertiesService.getString("simpleOpenUrl"), osstReqVO);
		if ("0000".equals(resultMap.get("rsltCd"))) {
			if (!StringUtils.isEmpty(rcpDetailVO.getMcnResNo())) {
				this.updateNameChgInfoFathSkip(rcpDetailVO);
			}
		}

		return resultMap;
    }

    private void updateNameChgInfoFathSkip(RcpDetailVO rcpDetailVO) {
        fathMapper.updateNameChgInfoFathSkip(rcpDetailVO);
    }

	public boolean isEnabledFT1() {
		return fathMapper.isEnabledFT1();
	}
}
