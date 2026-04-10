package com.ktis.msp.pps.rcpmgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.rqstmgmt.service.RqstMgmtService;
import com.ktis.msp.pps.rcpmgmt.service.RcpService;
import com.ktis.msp.rcp.rcpMgmt.service.NstepCallService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.NstepQueryVo;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Controller
public class RcpController extends BaseController {

	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private RcpService rcpMgmtService;

	@Autowired
	private RqstMgmtService rqstMgmtService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private MaskingService  maskingService;
	
    @Autowired
    private FileDownService  fileDownService;
    
	@Autowired
	private NstepCallService nStepCallService;
	
	@Autowired
	private RcpMgmtService rcpmgmtService;
	
	/** Constructor */
	public RcpController() {
		setLogPrefix("[RcpController] ");
	}
	
	@RequestMapping(value = "/pps/rcpmgmt/getRcpList.do")	
	public ModelAndView getRcpList( @ModelAttribute("searchVO") RcpListVO searchVO, 
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap
			) 
	{
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);

			ModelAndView modelAndView = new ModelAndView();
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));

			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			String faxSearchUrl =  propertiesService.getString("fax.search.url");

			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);


    		model.addAttribute("startDate",orgCommonService.getWantDay(-7));
    		model.addAttribute("endDate",orgCommonService.getToDay());
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("pps/agency/rcpmgmt/rcpmgmt");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description : 상세정보 가져오기
	 * @Param  : RcpListVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
	 */
	@RequestMapping(value = "/pps/rcpmgmt/rcpListAjax.json")
	public String getRcpList(@ModelAttribute("searchVO") RcpListVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//2015-02-27 필수값 체크
		if(paramMap.isEmpty()){
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg","필수정보가 없습니다.");
			model.addAttribute("result", resultMap);
			return "jsonView";
		}
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(searchVO);
		loginInfo.putSessionToParameterMap(paramMap);
		
		try {
			// 본사, 대리점 권한
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(paramMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
    		
			int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
			if(typeCd >= 20){
				if(typeCd == 20){
					searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
				} else if(typeCd == 30){
					searchVO.setpAgentCode(loginInfo.getUserOrgnId());
				}
			}
			
			List<EgovMap> rcpList = rcpMgmtService.getRcpList(searchVO, paramMap);
	
	        resultMap.put("data", rcpList.get(0));
	        resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	        resultMap.put("msg", "");
	        
		} catch (Exception e) {
  	 		resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
        }
        
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------  
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}	

	/**
	 * @Description : 선불 신청 관리 검색조건 조회
	 * @Param  : RcpListVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
	 */
	@RequestMapping(value = "/pps/rcpmgmt/rcpListAjax2.json")
	public String getRcpList2(@ModelAttribute("searchVO") RcpListVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(searchVO);
		loginInfo.putSessionToParameterMap(paramMap);
		
		int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
		if(typeCd >= 20){
			if(typeCd == 20){
				searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
			} else if(typeCd == 30){
				searchVO.setpAgentCode(loginInfo.getUserOrgnId());
			}
		}
				
		List<EgovMap> rcpList = rcpMgmtService.rcpMgmtList(searchVO, paramMap);

		Map<String, Object> resultMap =  makeResultMultiRow(paramMap, rcpList);
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------  
		model.addAttribute("result", resultMap);
		return "jsonView";

	}	

	/**
	 * @Description : 엑셀 다운로드 기능
	 * @Param  : RcpListVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
	 */
	@RequestMapping("/pps/rcpmgmt/rcpExcelDownNew.json")
	@ResponseBody
	public String custExcelDownNew(@ModelAttribute("rcpListVO") RcpListVO rcpListVO, Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> p_reqParamMap)
	{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신청관리 엑셀 저장 START."));
		logger.info(generateLogMsg("Return Vo [rcpListVO] = " + rcpListVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		//2015-02-27 필수값 체크
		if(
			(rcpListVO.getSearchStartDt()==null 	||	"".equals(rcpListVO.getSearchStartDt())) && 
			(rcpListVO.getSearchEndDt()==null 		||	"".equals(rcpListVO.getSearchEndDt())) && 
			(rcpListVO.getpServiceType()==null 		||	"".equals(rcpListVO.getpServiceType())) && 
			(rcpListVO.getpOperType()==null 		||	"".equals(rcpListVO.getpOperType())) && 
			(rcpListVO.getCntpntShopId()==null 		||	"".equals(rcpListVO.getCntpntShopId())) && 
			(rcpListVO.getpAgentCode()==null 		||	"".equals(rcpListVO.getpAgentCode())) && 
			(rcpListVO.getpRequestStateCode()==null ||	"".equals(rcpListVO.getpRequestStateCode()))&& 
			(rcpListVO.getpSearchName()==null 		||	"".equals(rcpListVO.getpSearchName()) )&& 
			(rcpListVO.getpPstate()==null 			||	"".equals(rcpListVO.getpPstate()) )&& 
			(rcpListVO.getReqBuyType()==null 		||	"".equals(rcpListVO.getReqBuyType()))
		){
			return "<script>alert('There is no required input Parameter');history.back(-1);</script>";
		}else{
		
			String returnMsg = null;
			FileInputStream in = null;
			OutputStream out = null;
			File file = null;
			try {
				LoginInfo loginInfo = new LoginInfo(request, response);
				loginInfo.putSessionToVo(rcpListVO);
				loginInfo.putSessionToParameterMap(p_reqParamMap);
				
				// 본사, 대리점 권한
				if(!"10".equals(p_reqParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(p_reqParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(p_reqParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
					throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
				}
	    		
				int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
				if(typeCd >= 20){
					if(typeCd == 20){
						rcpListVO.setCntpntShopId(loginInfo.getUserOrgnId());
					} else if(typeCd == 30){
						rcpListVO.setpAgentCode(loginInfo.getUserOrgnId());
					}
				}			
				List<RcpListVO> rcpListVOs = new ArrayList<RcpListVO>();
				
				//리스트 조회
				rcpListVOs = rcpMgmtService.getRcpListExNew(rcpListVO,p_reqParamMap);
				
				HashMap<String, String> maskFields = new HashMap<String, String>();
	
				maskFields.put("cstmrName","CUST_NAME");
				maskFields.put("cstmrMobile","MOBILE_PHO");
				maskFields.put("cstmrAddr","ADDR");
				maskFields.put("dlvryAddr","ADDR");
				maskFields.put("cstmrTelNo","TEL_NO");
				
				maskingService.setMask(rcpListVOs, maskFields, p_reqParamMap);
				
				
				String serverInfo = propertiesService.getString("excelPath");
	
				String strFilename = serverInfo  + "신청관리_";//파일명
				String strSheetname = "신청관리";//시트명
	
				//엑셀 첫줄 
				String [] strHead = {"구분", "예약번호", "고객명", "생년월일", "성별", "구매유형", "이동전 통신사", "제품명", "제품색상",  "휴대폰번호", "대리점", "요금제", "업무구분", "고객주소", "배송주소", "배송우편번호" ,"배송휴대폰번호"
						, "유선연락처", "신청일자", "진행상태", "신청서상태" , "모집경로", "유입경로" ,"녹취여부"};//24
	
				String [] strValue = {"serviceName","resNo","cstmrName","cstmrNativeRrn", "cstmrGenderStr", "reqBuyType", "moveCompany", "prdtNm","prdtColrNm","cstmrMobile","agentName","socName","operName", "cstmrAddr", "dlvryAddr", "dlvryPost" ,"dlvryMobile" 
						, "cstmrTelNo","reqInDay","requestStateName", "pstateName" , "onOffName", "openMarketReferer" ,"recYn"};//24
	
				//엑셀 컬럼 사이즈
				int[] intWidth = {3000, 5000, 5000, 5000, 5000, 5000, 5000, 15000, 5000, 5000, 8000, 15000, 5000, 15000, 15000, 5000, 5000, 5000, 5000, 5000, 5000 , 5000 , 5000, 5000};
	
				int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0, 0 , 0, 0 , 0, 0, 0, 0 , 0, 0, 0, 0, 0};
	
				String strFileName =   "";
				
				try {
					strFileName = rqstMgmtService.excelDownProc(strFilename, strSheetname, rcpListVOs, strHead, intWidth, strValue, request, response, intLen);
				} catch (Exception e) {
					//logger.error(e);
					throw new MvnoErrorException(e);
				}
				file = new File(strFileName);
	
				response.setContentType("applicaton/download");
				response.setContentLength((int) file.length());
	
				in = new FileInputStream(file);
	
				out = response.getOutputStream();
	
				int temp = -1;
				while((temp = in.read()) != -1){
					out.write(temp);
				}
	
		          //=======파일다운로드사유 로그 START==========================================================
		            if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
		                String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
		                
		                if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
		                    ipAddr = request.getHeader("REMOTE_ADDR");
		               
		                if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
		                    ipAddr = request.getRemoteAddr();
		                
		                p_reqParamMap.put("FILE_NM"   ,file.getName());              //파일명
		                p_reqParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
		                p_reqParamMap.put("DUTY_NM"   ,"ORG");                       //업무명
		                p_reqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
		                p_reqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
		                p_reqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
		                p_reqParamMap.put("DATA_CNT", 0);                            //자료건수
		                
		                fileDownService.insertCmnFileDnldMgmtMst(p_reqParamMap);
		            }
		            //=======파일다운로드사유 로그 END==========================================================
	
	
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "다운로드성공");
	
			} catch (Exception e) {
				//logger.error(e.getMessage());
	
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg", returnMsg);
				
				throw new MvnoErrorException(e);
			} finally {
		        if (in != null) {
			          try {
			        	  in.close();
			          } catch (Exception e) {
			            //logger.error(e);
			        	  throw new MvnoErrorException(e);
			          }
			        }
			        if (out != null) {
			          try {
			        	  out.close();
			          } catch (Exception e) {
			        	 // logger.error(e);
			        	  throw new MvnoErrorException(e);
			          }
			        }
			    }
	        file.delete();
			//----------------------------------------------------------------
			// return json 
			//----------------------------------------------------------------	
			model.addAttribute("result", resultMap);
			return "jsonView"; 
		}	
	}
	
	@RequestMapping("/pps/rcpmgmt/updateRcpSend.json")
	public String updateRcpSend(RcpDetailVO rcpDetailVO, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> p_reqParamMap, SessionStatus status) 
	{
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToParameterMap(p_reqParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, String> resultNstepMap = new HashMap<String, String>();
		
		String requestKey = "";
		
		try {
			// 본사, 대리점 권한
			if(!"10".equals(p_reqParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(p_reqParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(p_reqParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
    		
			if(!StringUtils.isEmpty(rcpDetailVO.getRequestKey())){
				requestKey = rcpDetailVO.getRequestKey();
				//2015-02-27 필수값 체크
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

			
			/**개발기*/
			//String infNstepUrl = "http://112.175.98.116:8180/nStep/serviceCall.do";
			/**운영기*/
			//String infNstepUrl = "http://128.134.37.215:8180/nStep/serviceCall.do";
			String infNstepUrl = propertiesService.getString("nStep.prx.url");
			NstepQueryVo nStepVo = new NstepQueryVo();

			nStepVo = nStepCallService.getNstepCallData(requestKey);

			//강제 조직 ID 셋팅
//			nStepVo.setCntpnt_shop_id("1100005928");
			
			try {
				nStepVo = nStepCallService.decryptDBMS(nStepVo);
			} catch (Exception e) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}

			resultNstepMap =  nStepCallService.infNstepCall(infNstepUrl, nStepVo);

			if (resultNstepMap.get("code").equals("00")) {
				int returnCnt = rcpmgmtService.updateRcpSend(rcpDetailVO);
				if (returnCnt>0) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
					resultMap.put("msg","");
				}
			} else {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg",resultNstepMap.get("msg"));
			}
		} catch (Exception e) {
			//logger.error(e);
			//e.printStackTrace();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------  

		model.addAttribute("result", resultMap);

		return "jsonView";
	}  	
}
