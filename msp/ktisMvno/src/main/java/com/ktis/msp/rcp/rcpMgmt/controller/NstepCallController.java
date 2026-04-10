package com.ktis.msp.rcp.rcpMgmt.controller;

import java.util.*;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;

import com.ktis.msp.rcp.rcpMgmt.service.*;
import com.ktis.msp.rcp.rcpMgmt.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;

import egovframework.rte.fdl.property.EgovPropertyService;



@Controller
public class NstepCallController extends BaseController {

	@Resource(name="nStepCallService")
	private NstepCallService nStepCallService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name="rcpMgmtService")
	private RcpMgmtService rcpMgmtService;

	@Autowired
	private MplatformService mPlatformService;
	@Autowired
	private MaskingService maskingService;
	@Autowired
	private FathService fathService;

	@Autowired
	private RcpEncService encSvc;

	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
 	//http://mcp.ktism.com/nStepData.do?requestKey=10100
	@RequestMapping("/nStepData.do")
	public String nStepData(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		try{
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			model.addAttribute("loginInfo", loginInfo);
			
			// 본사 화면인 경우
//			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
//				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
//			}
			
			// -----------------------------------------------------------------------------
			// Initialize.
			// -----------------------------------------------------------------------------
			String requestKey = request.getParameter("requestKey");
			/**개발기*/
//			String infNstepUrl = "http://112.175.98.116:8180/nStep/serviceCall.do";
			String infNstepUrl = propertiesService.getString("nStep.prx.url");
			NstepQueryVo nStepVo = new NstepQueryVo();
			// -----------------------------------------------------------------------------
			// Service.
			// -----------------------------------------------------------------------------
			nStepVo = nStepCallService.getNstepCallData(requestKey);
			
			nStepVo = nStepCallService.decryptDBMS(nStepVo);
			
			nStepCallService.infNstepCall(infNstepUrl, nStepVo); 
		
			return "";
			
		} catch (Exception e) {
			//throw new MvnoRunException(-1, e.getMessage());
			throw new MvnoErrorException(e);
		}
	}
	
	/**
	 * OSST연동
	 */
	@RequestMapping("/rcp/rcpMgmt/osstServiceCall.json")
	public String osstServiceCall(@ModelAttribute("OsstReqVO") OsstReqVO searchVO
									,RcpDetailVO rcpDetailVO
									,HttpServletRequest request
									,HttpServletResponse response
									,@RequestParam Map<String, Object> paramMap
									,ModelMap model) 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//osst전송
			logger.info("osstServiceCallTest START ================================");
			logger.info("예약번호=" + searchVO.getResNo());
            logger.info("명의변경예약번호=" + searchVO.getMcnResNo());
			logger.info("이벤트코드=" + searchVO.getAppEventCd());
			logger.info("osstServiceCallTest END   ================================");

			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
            rcpDetailVO.setSessionUserId(loginInfo.getUserId()); //userId set

			//안면인증 트랜잭션 ID확인 
			List<String> mustChkList = Arrays.asList("PC0", "FS5");
			boolean mustChkExists = mustChkList.contains(searchVO.getAppEventCd());
			String paramFathTransacId = rcpDetailVO.getFathTransacId();
			if(mustChkExists) {
				String fathTrgYn = rcpDetailVO.getFathTrgYn();
				if ("Y".equals(fathTrgYn)) {
					//안면인증 필수값 확인
					if (KtisUtil.isEmpty(paramFathTransacId)) {
						resultMap.put("code", "9999");
						resultMap.put("msg", "안면인증정보가 존재하지 않습니다.");
						model.addAttribute("result", resultMap);
						return "jsonView";
					}                            
				}
			}
			
			//상태조회시 메세지 처리( 사전체크시 개통불가 고객인 경우를 위하여)
//			if("ST1".equals(searchVO.getAppEventCd())){
//				OsstResVO resVO = nStepCallService.getTcpResult(searchVO, "");
//				
//				// 상태가 정상이 아닌 경우 메세지 출력
//				if(!"0000".equals(resVO.getRsltCd())){
//					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
//					resultMap.put("msg", "[" + resVO.getRsltCd() + "]" + resVO.getRsltMsg() + "<br />" + "운영팀에 문의하세요");
//					model.addAttribute("result", resultMap);
//					return "jsonView";
//				}
//			}
			//wooki 사전승낙제 사용 판매점 체크
			if("PC0".equals(searchVO.getAppEventCd()) || "OP0".equals(searchVO.getAppEventCd())) {
				if(StringUtils.isBlank(rcpDetailVO.getShopCd())) { //rcpDEtailVO에 shopCd 없으면 넣어줌
					rcpDetailVO.setShopCd(searchVO.getCntpntCd());
				}
				
				if(rcpMgmtService.getKnoteShopYn(rcpDetailVO) > 0) { //사전승낙제 사용 판매점이면 eventCd에 F 붙여줌
					searchVO.setAppEventCd("F" + searchVO.getAppEventCd());
				}
			}

			// 사전승낙제 사전체크시 USIM 유효성 검사 체크
			if ("FPC0".equals(searchVO.getAppEventCd()) && !StringUtils.isEmpty((String) paramMap.get("reqUsimSn"))) {
				Map<String, Object> result = mPlatformService.usimValidChk(paramMap);
				if (!"Y".equals(result.get("psblYn"))) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "사용 불가한 USIM 입니다. <br/>유심번호 확인 후 사전체크 요청해주세요.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
			}

			//번호이동사전인증 처리
			if("NP1".equals(searchVO.getAppEventCd())){
				OsstResVO resVO = nStepCallService.getNpReqInfo(searchVO);
				
				if(resVO.getNpTlphNo() == null || "".equals(resVO.getNpTlphNo())){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","번호이동 정보가 없습니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}else{
					searchVO.setNpTlphNo(resVO.getNpTlphNo());
					searchVO.setMoveCompany(resVO.getMoveCompany());
					searchVO.setCstmrType(resVO.getCstmrType());
					searchVO.setSelfCertType(resVO.getSelfCertType());
					searchVO.setCustIdntNo(resVO.getCustIdntNo());
					searchVO.setCrprNo(resVO.getCrprNo());
					searchVO.setCstmrName(resVO.getCstmrName());
					searchVO.setCntpntShopId(resVO.getCntpntShopId());
				}
			}

			// 사전승낙제 적용 대상 조직일 경우 PC0(사전체크), OP0(개통요청) 사용불가
			/*
			if ("PC0".equals(searchVO.getAppEventCd()) || "OP0".equals(searchVO.getAppEventCd())) {
				if ("Y".equals(rcpMgmtService.getKnoteYn(rcpDetailVO.getAgentCode()))) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "K-Note 서식지로만 개통 가능한 대리점입니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
			}
			 */
			
			//개통처리 시 서식시 정보 체크
			if("OP0".equals(searchVO.getAppEventCd())){
				boolean isScan = nStepCallService.isScanFile(searchVO.getRequestKey());
				boolean isOrgChk = nStepCallService.isOrgChk(searchVO.getRequestKey());
				if(!isScan && !isOrgChk){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","스캔 서식지 정보가 없습니다. 서식지 스캔후 개통처리가 가능 합니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
			}

			//2024-04-18 개발팀 김동혁
			//온라인서식지 등록 EndPoint 전환(KT -> MP) FS5 추가
			if("FS5".equals(searchVO.getAppEventCd())) {

				// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
				if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getSessionUserOrgnId().equals(searchVO.getCntpntShopId())){
					throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
				}

				if(!StringUtils.isEmpty(searchVO.getRequestKey())){
					String requestKey = searchVO.getRequestKey();
					if(requestKey==null || "".equals(requestKey)){
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
						resultMap.put("msg","필수정보가 없습니다.");
						model.addAttribute("result", resultMap);
						return "jsonView";
					}
					boolean isScan = nStepCallService.isScanFile(requestKey);
					if(!isScan){
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
						resultMap.put("msg","스캔 서식지 정보가 없습니다. 서식지 스캔후 NSTEP 전송이 가능 합니다.");
						model.addAttribute("result", resultMap);
						return "jsonView";
					}
				}
				else{
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","request_key 정보가 없습니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
			}

			if("FT0".equals(searchVO.getAppEventCd()) || "FS8".equals(searchVO.getAppEventCd())) {
				
				fathService.osssReqVoSet(searchVO, rcpDetailVO);
				
			}
			if("FS8".equals(searchVO.getAppEventCd())) {
                //명의변경이 아닐 때
                if(KtisUtil.isEmpty(searchVO.getMcnResNo())) {
                    // URL 받을 전화번호
                    String smsRcvTelNo = rcpDetailVO.getFathMobileFn() + rcpDetailVO.getFathMobileMn() + rcpDetailVO.getFathMobileRn();
                    searchVO.setSmsRcvTelNo(smsRcvTelNo);

                    String operType = rcpDetailVO.getOperType();
                    // 안면인증 가입 구분 코드
                    String fathSbscDivCd = "";
                    if ("NAC3".equals(operType)) {
                        fathSbscDivCd = "1";    // 신규가입
                    } else if ("MNP3".equals(operType)) {
                        fathSbscDivCd = "2";    // 번호이동
                    } else if ("HCN3".equals(operType) || "HDN3".equals(operType)) {
                        fathSbscDivCd = "4";    // 기기변경
                    }
                    searchVO.setFathSbscDivCd(fathSbscDivCd);
                //명의변경일 때
                }else {
                    if(KtisUtil.isEmpty(searchVO.getSmsRcvTelNo())) {
                        Map<String, String> telInfoMap = fathService.getNameChgInfo(searchVO.getMcnResNo());
                        if(telInfoMap != null) {
                            searchVO.setSmsRcvTelNo(telInfoMap.get("CSTMR_RECEIVE_TEL_FN") + telInfoMap.get("CSTMR_RECEIVE_TEL_MN") + telInfoMap.get("CSTMR_RECEIVE_TEL_RN"));
                        }
                    }
                    searchVO.setFathSbscDivCd("3"); //명의변경
                }
				String smsRcvTelNo = searchVO.getSmsRcvTelNo();
				if(KtisUtil.isEmpty(smsRcvTelNo)) {
					resultMap.put("code", "9996");
					resultMap.put("msg", "안면인증 연락처가 존재하지 않습니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
				boolean result = Pattern.matches("^[0-9]*$", smsRcvTelNo);
				if(!result) {
					resultMap.put("code", "9995");
					resultMap.put("msg", "안면인증 연락처는 숫자만 가능합니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
			}

			HashMap<String, Object> map = (HashMap<String, Object>) nStepCallService.osstServiceCall((String) propertiesService.getString("simpleOpenUrl"), searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );

			if("S".equals(map.get("code"))){
				HashMap<String, Object> osstRst = (HashMap<String, Object>) map.get("osstRst");
				// 개통처리시 APD 테이블 INSERT
				if("OP0".equals(searchVO.getAppEventCd()) || "FOP0".equals(searchVO.getAppEventCd())){
					nStepCallService.insertDisApd(rcpDetailVO, "NAC");
					resultMap.put("msg", "개통요청이 접수되었습니다.");
				}
				else if("PC0".equals(searchVO.getAppEventCd()) || "FPC0".equals(searchVO.getAppEventCd())){
					resultMap.put("msg", "사전체크가 접수되었습니다.");
				}else if("MC0".equals(searchVO.getAppEventCd())){
                    resultMap.put("msg", "명의변경 사전체크가 접수되었습니다.");
                }else if("MP0".equals(searchVO.getAppEventCd())){
                    resultMap.put("msg", "명의변경 요청이 접수되었습니다.");
                }else if("FT0".equals(searchVO.getAppEventCd())) {
					
					String trtResltCd = (String) osstRst.get("trtResltCd");

					Map<String, String> fathResltRtn = fathService.fathResltRtn(trtResltCd);
					String resltCd = fathResltRtn.get("resltCd");
					String resltSbst = fathResltRtn.get("resltSbst");
					
					if("00000".equals(resltCd)) { // 안면인증 대상인경우
						rcpDetailVO.setFathTrgYn("Y");
						resltSbst = "안면인증 대상입니다. <br>URL요청을 통해 안면인증을 진행해주세요.";
					} else { //안면인증 대상아닌경우
						rcpDetailVO.setFathTrgYn("N");
					}
					resultMap.put("resltCd", resltCd);
					resultMap.put("resltSbst", resltSbst);
                    //명의변경이 아닐 때
                    if(KtisUtil.isEmpty(searchVO.getMcnResNo())) {
                        fathService.updateFathTrgYn(rcpDetailVO);        //안면인증 대상여부 업데이트
						if("NM".equals(rcpDetailVO.getCstmrType())) { //미성년자인 경우 법정대리인 신분증타입
							fathService.updateSelfCertTypeMinor(rcpDetailVO);    //본인인증유형(법정대리인) 업데이트
						} else {
							fathService.updateSelfCertType(rcpDetailVO);    //본인인증유형 업데이트	
						}
                    } else {
						//rcpDetailVO.setAppEventCd(searchVO.getAppEventCd());
						//rcpDetailVO.setMcnResNo(searchVO.getMcnResNo());
						fathService.updateNameChgInfo(rcpDetailVO);    //명의변경 정보 업데이트
						//명의변경은 신분증타입 변경불가
//						if("NM".equals(rcpDetailVO.getCstmrType())) { //미성년자인 경우 법정대리인 신분증타입
//							fathService.updateNameChgSelfCertTypeMinor(rcpDetailVO);    //본인인증유형(법정대리인) 업데이트
//						} else {
//							fathService.updateNameChgSelfCertType(rcpDetailVO);    //본인인증유형 업데이트	
//						}
					}

				} else if("FS8".equals(searchVO.getAppEventCd())) {
					
					String rtnResltCd = (String) osstRst.get("resltCd");
					Map<String, String> fathResltRtn = fathService.fathResltRtn(rtnResltCd);
					String resltCd = fathResltRtn.get("resltCd");
					String resltSbst = fathResltRtn.get("resltSbst");
					

					if(!"00000".equals(resltCd)) { //안면인증 대상이 아닌경우  
						resultMap.put("resltCd", resltCd);
						resultMap.put("resltSbst", resltSbst);
						model.addAttribute("result", resultMap);
						return "jsonView";
					}
					
					String fathTransacId = (String) osstRst.get("fathTransacId");
					
					resultMap.put("fathTransacId", fathTransacId);
					//트랜잭션 ID MCP_REQUEST에 UPDATE
					rcpDetailVO.setFathTransacId(fathTransacId);
					rcpDetailVO.setCpntId(searchVO.getCpntId());
					
					//명의변경이 아닐 때
					if(KtisUtil.isEmpty(searchVO.getMcnResNo())) {
						fathService.updateFathTransacId(rcpDetailVO);
						//명의변경일 때
					}else {
						rcpDetailVO.setAppEventCd(searchVO.getAppEventCd());
						rcpDetailVO.setMcnResNo(searchVO.getMcnResNo());
						fathService.updateNameChgInfo(rcpDetailVO);     //명의변경 정보 업데이트
					}

					//CD02, CD05는 FS9 연동하여 연동결과 리턴
					if("CD02".equals(rtnResltCd) || "CD05".equals(rtnResltCd)) {
						resultMap = nStepCallService.processOsstFs9Service((String) propertiesService.getString("simpleOpenUrl"), rcpDetailVO, resltCd);
						model.addAttribute("result", resultMap);
						return "jsonView";
					}

					resultMap.put("resltCd", resltCd);
					resultMap.put("resltSbst", "안면인증 URL전송이 완료되었습니다.");
					
					//문자 전송
					String templateId = "443";
					KtMsgQueueReqVO ktMsgQueueReqVO = smsMgmtMapper.getKtTemplateText(templateId);
					if (ktMsgQueueReqVO == null) {
						throw new MvnoRunException(-1, "문자템플릿이 존재하지 않습니다.");
					}
					
					ktMsgQueueReqVO.setMessage(ktMsgQueueReqVO.getTemplateText()
							.replaceAll(Pattern.quote("#{url}"), (String) osstRst.get("urlAdr")));

					ktMsgQueueReqVO.setSubject(ktMsgQueueReqVO.getTemplateSubject());
					ktMsgQueueReqVO.setRcptData(searchVO.getSmsRcvTelNo());
					ktMsgQueueReqVO.setReserved01("MSP");
					ktMsgQueueReqVO.setReserved02("FS8URL");
					ktMsgQueueReqVO.setReserved03(searchVO.getSessionUserId());
					ktMsgQueueReqVO.setUserId(searchVO.getSessionUserId());

					smsMgmtMapper.insertKtMsgTmpQueue(ktMsgQueueReqVO);
					smsMgmtMapper.insertSmsSendMst(ktMsgQueueReqVO.toSmsSendVO());
				} 
			}else{
				resultMap.put("msg", "[" + map.get("code") + "]" + map.get("msg"));

				//온라인 서식지 등록의 경우 별도 처리
				if("FS5".equals(searchVO.getAppEventCd())) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					if("00".equals(map.get("code"))) {
						int returnCnt = rcpMgmtService.updateRcpSend(rcpDetailVO);
						if (returnCnt>0) {
							resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
							resultMap.put("msg","N-STEP 전송이 완료되었습니다.");
						}
					}
				}
			}

		} catch (MvnoServiceException e) {
			resultMap.put("code", "9999");
			resultMap.put("msg", e.getMessage());
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg","MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
		} 
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 번호조회
	 */
	@RequestMapping("/rcp/rcpMgmt/getOsstSvcNoList.json")
	public String getOsstSvcNoList(@ModelAttribute("OsstReqVO") OsstReqVO searchVO
									,HttpServletRequest request
									,HttpServletResponse response
									,@RequestParam Map<String, Object> paramMap
									,ModelMap model) 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//osst전송
			logger.info("getOsstSvcNoList START ================================");
			logger.info("예약번호=" + searchVO.getResNo());
			logger.info("이벤트코드=" + searchVO.getAppEventCd());
			logger.info("getOsstSvcNoList END   ================================");
			
			// 사전인증 TCP 결과 확인
			OsstResVO vo = nStepCallService.getTcpResult(searchVO, "PC2");
			
			logger.debug(vo);
			
			if(vo == null){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "사전체크정보가 존재하지 않습니다. <br> 사전체크 완료 후 다시 시도해주세요.");
			}else if(!"0000".equals(vo.getRsltCd())){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				
				if(vo.getRsltMsg() != null && !"".equals(vo.getRsltMsg())){
					resultMap.put("msg", vo.getRsltMsg());
				}
				else if(vo.getPrdcChkNotiMsg() != null && !"".equals(vo.getPrdcChkNotiMsg())){
					resultMap.put("msg", vo.getPrdcChkNotiMsg());
				}else{
					resultMap.put("msg", "사전체크가 성공적으로 진행되지 않았습니다. <br> 사전체크 완료 후 다시 시도해주세요.");
				}
			}else{
				List<HashMap<String, Object>> list = nStepCallService.getOsstSvcNoList((String) propertiesService.getString("simpleOpenUrl"), searchVO);
				
				if(list == null){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "[9999] MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
				}else{
					logger.debug("list size=" + list.size());
					
					resultMap =  makeResultMultiRow(searchVO, list);
				}
			}
//			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
//			resultMap.put("msg", "");
		}catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg","MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
		}
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 번호예약/취소
	 */
	@RequestMapping("/rcp/rcpMgmt/setOsstSvcNoRsv.json")
	public String setOsstSvcNoRsv(@ModelAttribute("OsstReqVO") OsstReqVO searchVO
									,HttpServletRequest request
									,HttpServletResponse response
									,@RequestParam Map<String, Object> paramMap
									,ModelMap model) 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//osst전송
			logger.info("osstServiceCallTest START ================================");
			logger.info("예약번호=" + searchVO.getResNo());
			logger.info("이벤트코드=" + searchVO.getAppEventCd());
			logger.info("업무구분=" + searchVO.getGubun());
			logger.info("osstServiceCallTest END   ================================");
			
			// 실행전 OSST 테이블 INSERT
			nStepCallService.setRequestOsstHist(searchVO);
			
			nStepCallService.setOsstSvcNoRsv((String) propertiesService.getString("simpleOpenUrl"), searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg","MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
		}
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * K-Note OSST연동
	 */
	@RequestMapping("/rcp/rcpMgmt/osstKnoteCall.json")
	public String osstKnoteCall(@ModelAttribute("OsstReqVO") OsstReqVO searchVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> paramMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			logger.debug("osstKnoteCall START ================================");
			logger.debug("이벤트코드=" + searchVO.getAppEventCd());
			logger.debug("서식지아이디=" + searchVO.getFrmpapId());
			logger.debug("대리점코드=" + searchVO.getMngmAgncId());
			logger.debug("접점코드=" + searchVO.getCntpntCd());
			logger.debug("osstKnoteCall END   ================================");

			// 서식지 목록 조회(FS0)
			if ("FS0".equals(searchVO.getAppEventCd())) {
				if(StringUtils.isBlank(searchVO.getRetvStrtDt())) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","조회시작일자를 선택후 다시 조회해주세요.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
				if(StringUtils.isBlank(searchVO.getRetvEndDt())) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","조회종료일자를 선택후 다시 조회해주세요.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
				
				// 조회(FS0)시 대리점코드(mngmAgncId)가 조직 ID이므로 KT조직ID을 mngmAgncId에 SET
				searchVO.setMngmAgncId(nStepCallService.getKnoteKtOrgId(searchVO.getCntpntCd()));

			// 서식지 상세 조회(FS1), 서식지 상태 변경(FS2)
			} else if ("FS1".equals(searchVO.getAppEventCd()) || "FS2".equals(searchVO.getAppEventCd())) {
				if(StringUtils.isBlank(searchVO.getFrmpapId())){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","서식지 ID가 존재하지 않습니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}

				// 서식지 생성 정보 연동 내역(KIS_KNOTE_SCAN_INFO) 해당 서식지 ID 존재여부확인
				boolean knoteScanInfoChk = nStepCallService.knoteScanInfoChk(searchVO.getFrmpapId());
				if(!knoteScanInfoChk){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg", "신청정보가 아직 연동되지 않았습니다.<br> 잠시 후 다시 시도해주세요.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
				
				if ("FS2".equals(searchVO.getAppEventCd())) {
					if(StringUtils.isBlank(searchVO.getFrmpapStatCd())){
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
						resultMap.put("msg", "처리상태 변경코드가 존재하지 않습니다.");
						model.addAttribute("result", resultMap);
						return "jsonView";
					}
				}
			} else {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg", "잘못된 요청입니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}

			// 대리점 필수값
			if (StringUtils.isBlank(searchVO.getMngmAgncId())) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","관리대리점을 선택후 다시 조회해주세요.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}
			// 접점 필수값
			if (StringUtils.isBlank(searchVO.getCntpntCd())) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","접점코드를 선택후 다시 조회해주세요.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}

			//service 호출
			HashMap<String, Object> map = (HashMap<String, Object>) nStepCallService.osstServiceCall((String) propertiesService.getString("simpleOpenUrl"), searchVO);

			if(map != null) {
				// 연동결과
				HashMap<String, Object> osstRst = (HashMap<String, Object>) map.get("osstRst");
				if ("N".equals(((String) osstRst.get("responseType")))) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
					// 서식지 목록조회(FS0)
					if("FS0".equals(searchVO.getAppEventCd())) {
						List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) osstRst.get("list");

						//마스킹
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("SESSION_USER_ID", searchVO.getSessionUserId());
						maskingService.setMask(list, maskingService.getMaskFields(), param);

						logger.debug("list size=" + list.size());
						resultMap =  makeResultMultiRow(searchVO, list);

					// 서식지 상세 조회(FS1) 
					} else if ("FS1".equals(searchVO.getAppEventCd())) {
						resultMap.put("data", osstRst);

					// 서식지 상태 변경(FS2)
					} else if ("FS2".equals(searchVO.getAppEventCd())) {
						// rsltCd 로 성공여부 확인
						if (!"Y".equals(osstRst.get("rsltCd"))) {
							resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
							resultMap.put("msg", osstRst.get("rsltMsg"));
						}
					}
				} else {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
					resultMap.put("msg", "[" + osstRst.get("responseCode") + "]" + osstRst.get("responseBasic"));
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
			}

		} catch(Exception e) {
			logger.debug(e.getMessage());
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg","MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	/**
	 * 무서식지 FS3, FS4 OSST 연동
	 */
	@RequestMapping("/rcp/rcpMgmt/osstNoScanCall.json")
	public String osstCall(@ModelAttribute("OsstReqVO") OsstReqVO searchVO, HttpServletRequest request,
								HttpServletResponse response, @RequestParam Map<String, Object> paramMap, ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			logger.debug("osstKnoteCall START ================================");
			logger.debug("이벤트코드=" + searchVO.getAppEventCd());
			logger.debug("osstKnoteCall END   ================================");

			//무서식지 오더 후첨부 처리 (FS3) 필수값 확인
			if ("FS3".equals(searchVO.getAppEventCd())) {
				if(StringUtils.isBlank(searchVO.getRetvStDt())) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","조회시작일자를 선택후 다시 조회해주세요.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
				if(StringUtils.isBlank(searchVO.getRetvFnsDt())) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","조회종료일자를 선택후 다시 조회해주세요.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
			// 무서식지 오더 후첨부 처리 (FS4) 필수값 확인
			} else if ("FS4".equals(searchVO.getAppEventCd())) {
				if(StringUtils.isBlank(searchVO.getItgFrmpapSeq())){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","통합서식지일련번호가 존재하지 않습니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
				if(StringUtils.isBlank(searchVO.getTlphNo())){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","전화번호가 존재하지 않습니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
				if(StringUtils.isBlank(searchVO.getFrmpapId())){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","서식지 ID가 존재하지 않습니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
				if(StringUtils.isBlank(searchVO.getScanDt())){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","스캔일자가 존재하지 않습니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}

				if(StringUtils.isBlank(searchVO.getSvcContId())){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","계약번호가 존재하지 않습니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
			} else {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg", "잘못된 요청입니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}
			searchVO.setOderTrtOrgId(nStepCallService.getKnoteKtOrgId(searchVO.getOderTrtOrgId()));
			// 공통필수값
			if (StringUtils.isBlank(searchVO.getOderTrtOrgId())) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","접점이 존재하지 않습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}

			if (StringUtils.isBlank(searchVO.getOderTypeCd())) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","업무구분이 존재하지 않습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}

			if (StringUtils.isBlank(searchVO.getOderTrtId())) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","KOS ID가 존재하지 않습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}

			// FS4인 경우 customerId 추가 조회
			if ("FS4".equals(searchVO.getAppEventCd())) {
				String customerId= rcpMgmtService.getCustomerIdBySvcCntrNo(searchVO.getSvcContId());
				searchVO.setCustNo(customerId);
			}

			//service 호출
			HashMap<String, Object> map = (HashMap<String, Object>) nStepCallService.osstServiceCall((String) propertiesService.getString("simpleOpenUrl"), searchVO);

			if(map != null) {
				// 연동결과
				HashMap<String, Object> osstRst = (HashMap<String, Object>) map.get("osstRst");
				if ("N".equals(((String) osstRst.get("responseType")))) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
					if("FS3".equals(searchVO.getAppEventCd())) {
						List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) osstRst.get("list");

						//마스킹
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("SESSION_USER_ID", searchVO.getSessionUserId());
						maskingService.setMask(list, maskingService.getMaskFields(), param);

						logger.debug("list size=" + list.size());
						resultMap =  makeResultMultiRow(searchVO, list);
					} else if ("FS4".equals(searchVO.getAppEventCd())) {
						resultMap.put("msg", "완료되었습니다.");
					}
				} else {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
					resultMap.put("msg", "[" + osstRst.get("responseCode") + "]" + osstRst.get("responseBasic"));
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
			}
		} catch(Exception e) {
			logger.debug(e.getMessage());
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg","MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
}
