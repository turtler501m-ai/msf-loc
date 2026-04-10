package com.ktis.msp.rcp.courtMgmt.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.courtMgmt.service.CourtLcMgmtService;
import com.ktis.msp.rcp.courtMgmt.vo.CourtLcMgmtVO;
import com.ktis.msp.rcp.courtMgmt.vo.RegstCrCstmrVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @Class Name : CourtLcMgmtController
 * @Description : 부채증명서 관리
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2023.02.21 김동혁 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2023. 02. 21.
 */
@Controller
public class CourtLcMgmtController extends BaseController {

    @Autowired
    private OrgCommonService orgCommonService;

    /** courtLcMgmtService */
    @Autowired
    private CourtLcMgmtService courtLcMgmtService;

    /** propertiesService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private MenuAuthService menuAuthService;

    @Autowired
    private FileDownService fileDownService;

    @Autowired
    private LoginService loginService;

    /** Constructor */
    public CourtLcMgmtController() {
        setLogPrefix("[CourtLcMgmtController] ");
    }

    @RequestMapping(value = "/rcp/courtMgmt/courtLcMgmt.do")
    public ModelAndView courtLcMgmt(@ModelAttribute("searchVO") CourtLcMgmtVO searchVO,
                                     ModelMap model,
                                     HttpServletRequest pRequest,
                                     HttpServletResponse pResponse,
                                     @RequestParam Map<String, Object> pRequestParamMap
    )
    {
        ModelAndView modelAndView = new ModelAndView();

        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(searchVO);
            String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

            // 본사 화면인 경우
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

            //조회 기간에 날짜 set
            model.addAttribute("strtDt",orgCommonService.getWantDay(-7));
            model.addAttribute("endDt",orgCommonService.getToDay());
            model.addAttribute("maskingYn", maskingYn);

            //----------------------------------------------------------------
            // jsp 지정
            //----------------------------------------------------------------
            modelAndView.setViewName("/rcp/courtMgmt/msp_court_1001");

            return modelAndView;
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }
    @RequestMapping(value="/rcp/courtMgmt/getCourtLcMgmtList.json")
    public String getCourtLcMgmtList(@ModelAttribute("searchVO") CourtLcMgmtVO searchVO,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam Map<String, Object> pRequestParamMap,
                                      ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("부채증명서 발급 목록 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<?> resultList = courtLcMgmtService.getCourtLcMgmtList(pRequestParamMap);

            resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
            {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/getCourtLcMgmtListByExcel.json")
    public String getCourtLcMgmtListByExcel(@ModelAttribute("searchVO") CourtLcMgmtVO searchVO,
                                             HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestParam Map<String, Object> pRequestParamMap,
                                             ModelMap model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("부채증명서 발급 목록 엑셀 다운 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String returnMsg = null;
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;

        try {
            /* 로그인조직정보 및 권한체크 */
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            List<?> resultList = courtLcMgmtService.getCourtLcMgmtListByExcel(pRequestParamMap);

            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo  + "부채증명서_발급목록_";//파일명
            String strSheetname = "부채증명서 발급목록";//시트명

            String [] strHead = {
            		"번호","발급번호","진행상태","이름","식별번호","미납요금/원(A)","단말할부금/원(B)","합계/원(A+B)","증명서 발급자","증명서 발급일시","출력여부","등록자","등록일시","수정자","수정일시"};

            String [] strValue = {
            		"lcSeq","lcNum","lcStatCd","cstmrName","cstmrRrn","unpdPrc","instPrc","total","issueId","issueDttm","printYn","regstId","regstDttm","rvisnId","rvisnDttm"};

            //엑셀 컬럼 사이즈
            int[] intWidth = {
                    2000, 6000, 3500, 3000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    3000, 3000, 5000, 3000, 5000
            };
            int[] intLen = {
                    0, 0, 0, 0, 0,
                    1, 1, 1, 0, 0,
                    0, 0, 0, 0, 0
            };

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
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
				
				pRequestParamMap.put("FILE_NM"	,file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());				//파일경로
				pRequestParamMap.put("DUTY_NM"	,"RCP");						//업무명
				pRequestParamMap.put("IP_INFO"	,ipAddr);						//IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());			//파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId"));	//메뉴ID
				pRequestParamMap.put("DATA_CNT", resultList.size());			//자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId());	//사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
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

    @RequestMapping(value="/rcp/courtMgmt/getCourtLcMgmtDtlList.json")
    public String getCourtLcMgmtDtlList(@ModelAttribute("searchVO") CourtLcMgmtVO searchVO,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam Map<String, Object> pRequestParamMap,
                                  ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("부채증명서 상세 미납 목록 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<?> resultList = courtLcMgmtService.getCourtLcMgmtDtlList(pRequestParamMap);

            resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
            {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";

    }
    
    @RequestMapping(value="/rcp/courtMgmt/checkLcMst.json")
    public String checkLcMst(CourtLcMgmtVO courtLcMgmtVo,
    							@RequestBody String data,
    							HttpServletRequest pRequest,
    							HttpServletResponse pResponse,
    							@RequestParam Map<String, Object> pRequestParamMap,
    							ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("부채증명서 중복 발급 체크 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        int chkLcMst = 0;
        
        try {
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(courtLcMgmtVo);

            // 본사 권한
            if(!"10".equals(courtLcMgmtVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            chkLcMst = courtLcMgmtService.checkLcMst(courtLcMgmtVo);
            
            if(chkLcMst > 0) {
                resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
                resultMap.put("msg", "기존에 등록된 정보가 존재합니다. 새 부채증명서를 등록하시려면 기존에 등록/발급된 부채증명서를 폐기해주세요.");
            } else {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            }
            
        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/insertLcList.json")
    public String insertLcList(CourtLcMgmtVO courtLcMgmtVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("부채증명서/미납목록 등록 START."));
        logger.info(generateLogMsg("================================================================="));

    	
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(courtLcMgmtVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if(!"10".equals(courtLcMgmtVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            // 합계 : 미납요금+단말할부금
            try{
                courtLcMgmtVo.setTotalPrc(Integer.toString(Integer.parseInt(courtLcMgmtVo.getUnpdPrc())+Integer.parseInt(courtLcMgmtVo.getInstPrc())));
            }
            catch (NumberFormatException ex){
            	// 20250523 취약점 소스 코드 수정
				logger.error("Connection Exception occurred");
            }
            
            courtLcMgmtVo.setContactNum(courtLcMgmtVo.getTelFn()+ courtLcMgmtVo.getTelMn()+ courtLcMgmtVo.getTelRn());
            
            courtLcMgmtService.insertLcList(courtLcMgmtVo);
            
            resultMap.put("lcMstSeq", courtLcMgmtVo.getLcMstSeq());
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/updateLcDtl.json")
    public String updateLcDtl(CourtLcMgmtVO courtLcMgmtVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("미납목록 수정 START."));
        logger.info(generateLogMsg("================================================================="));
    	
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(courtLcMgmtVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			
			if(!"Y".equals(maskingYn) && !"1".equals(maskingYn)) {
	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	            resultMap.put("msg", "수정권한이 없습니다.");
			} else {
	            if(!"10".equals(courtLcMgmtVo.getSessionUserOrgnTypeCd())){
	                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	            }

	            try{
	                courtLcMgmtVo.setTotalPrc(Integer.toString(Integer.parseInt(courtLcMgmtVo.getUnpdPrc())+Integer.parseInt(courtLcMgmtVo.getInstPrc())));
	            }
	            catch (NumberFormatException ex){
	            	// 20250523 취약점 소스 코드 수정
					logger.error("Connection Exception occurred");
	            }

	            courtLcMgmtVo.setContactNum(courtLcMgmtVo.getTelFn()+ courtLcMgmtVo.getTelMn()+ courtLcMgmtVo.getTelRn());
				
		        courtLcMgmtService.updateLcDtl(courtLcMgmtVo);

	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	            resultMap.put("msg", "");
			}

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping(value="/rcp/courtMgmt/deleteLcDtl.json")
    public String deleteLcDtl(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , @ModelAttribute CourtLcMgmtVO courtLcMgmtVO
            , ModelMap modelMap
            , @RequestParam Map<String, Object> paramMap) {
    	
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("미납목록 삭제 START."));
        logger.info(generateLogMsg("================================================================="));


        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(courtLcMgmtVO);
            loginInfo.putSessionToParameterMap(paramMap);

            //본사화면일경우
            if(!"10".equals(courtLcMgmtVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            courtLcMgmtService.deleteLcDtl(courtLcMgmtVO);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
            resultMap.put("msg", "재전송에 실패 했습니다.");
            throw new MvnoErrorException(e);
        }

        modelMap.addAttribute("result", resultMap);

        return "jsonView";
    }
    
    @RequestMapping("/rcp/courtMgmt/issueLcMst.json")
    public String issueLcMst(CourtLcMgmtVO courtLcMgmtVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("부채증명서 발급 START."));
        logger.info(generateLogMsg("================================================================="));

    	
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(courtLcMgmtVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if(!"10".equals(courtLcMgmtVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            
            courtLcMgmtService.issueLcMst(courtLcMgmtVo);
            
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }
    
    @RequestMapping("/rcp/courtMgmt/chkMaskingLc.json")
    public String chkMaskingLc(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("부채증명서 마스킹권한 체크 START."));
        logger.info(generateLogMsg("================================================================="));
    	
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(regstCrCstmrVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			
			if(!"Y".equals(maskingYn) && !"1".equals(maskingYn)) {
	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	            resultMap.put("msg", "권한이 없어 부채증명서를 출력할 수 없습니다.");
			} else {
	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	            resultMap.put("msg", "");
			}

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }
    
    @RequestMapping(value="/rcp/courtMgmt/discardLcMst.json")
    public String discardLcMst(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , @ModelAttribute CourtLcMgmtVO courtLcMgmtVO
            , ModelMap modelMap
            , @RequestParam Map<String, Object> paramMap) {
    	
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("부채증명서 폐기 START."));
        logger.info(generateLogMsg("================================================================="));


        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(courtLcMgmtVO);
            loginInfo.putSessionToParameterMap(paramMap);

            //본사화면일경우
            if(!"10".equals(courtLcMgmtVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            courtLcMgmtService.discardLcMst(courtLcMgmtVO);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
            resultMap.put("msg", "재전송에 실패 했습니다.");
            throw new MvnoErrorException(e);
        }

        modelMap.addAttribute("result", resultMap);

        return "jsonView";
    }
}
